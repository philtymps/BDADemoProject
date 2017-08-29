package com.ibm.argos.ue;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.OMPGetCarrierServiceOptionsForOrderingUE;
import com.yantra.ypm.japi.ue.YPMCalculateShippingChargeUE;

public class CalculateShippingChargeUEImpl implements YPMCalculateShippingChargeUE, OMPGetCarrierServiceOptionsForOrderingUE {

	private static YFCLogCategory logger = YFCLogCategory.instance(CalculateShippingChargeUEImpl.class);
		
	private static YFCDocument getShippingPrices(){
		YFCDocument dShipmentPrices;
		try {
			dShipmentPrices = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/ue/shipmentPricing.xml");
		} catch (Exception e) {
			dShipmentPrices = YFCDocument.createDocument("ShippingCharges");
			YFCElement eShippingCharges = dShipmentPrices.getDocumentElement();
			eShippingCharges.setAttribute("Currency", "GBP");
			YFCElement eShippingCharge = eShippingCharges.createChild("ShippingCharge");
			eShippingCharge.setAttribute("Charge", "3.95");
			eShippingCharge.setAttribute("CarrierServiceCode", "STANDARD");
			YFCElement eItems = eShippingCharge.createChild("Items");
			YFCElement eItem = eItems.createChild("Item");
			eItem.setAttribute("ItemID", "HFU032_321401");
			eItem.setAttribute("Charge", "8.95");
			eShippingCharge = eShippingCharges.createChild("ShippingCharge");
			eShippingCharge.setAttribute("Charge", "5.95");
			eShippingCharge.setAttribute("CarrierServiceCode", "EXPRESS");
			eItems = eShippingCharge.createChild("Items");
			eItem = eItems.createChild("Item");
			eItem.setAttribute("ItemID", "HFU032_321401");
			eItem.setAttribute("Charge", "11.95");
		}
		logger.debug("Shipping Map: " + dShipmentPrices);
		return dShipmentPrices;
	}
	@Override
	public Document calculateShippingCharge(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		YFCDocument dInput = YFCDocument.getDocumentFor(inDoc);
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOutput.getDocumentElement();
		if (!YFCCommon.isVoid(dInput)){
			YFCDocument dShipmentPrices = getShippingPrices();
			YFCElement eInput = dInput.getDocumentElement();
			String sDefaultDelivery = eInput.getAttribute("DeliveryMethod");
			String sDefaultCarrier = eInput.getAttribute("CarrierServiceCode");
			double dShippingCharge = 0;
			for (YFCElement eOrderLine : eInput.getChildElement("OrderLines", true).getChildren()){
				boolean found = false;
				if (YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod", sDefaultDelivery), "SHP")){
					for (YFCElement eShippingCharge : dShipmentPrices.getDocumentElement().getChildren()){
						if (YFCCommon.equals(eInput.getChildElement("Shipping", true).getAttribute("CarrierServiceCode", sDefaultCarrier), eShippingCharge.getAttribute("CarrierServiceCode"))){
							for (YFCElement eItem : eShippingCharge.getChildElement("Items", true).getChildren()){
								if (YFCCommon.equals(eItem.getAttribute("ItemID"), eOrderLine.getAttribute("ItemID"))){
									dShippingCharge += eItem.getDoubleAttribute("Charge", 0);
									found = true;
									break;
								}
							}
							if (found){
								break;
							} else {
								dShippingCharge += eShippingCharge.getDoubleAttribute("Charge", 0);
								break;
							}
						}
					}
				}
			}
			YFCElement eShipping = eOrder.createChild("Shipping");
			eShipping.setAttribute("ShippingCharge", dShippingCharge);
		}
		return dOutput.getDocument();
	}
	
	
	@Override
	public Document getCarrierServiceOptionsForOrdering(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		YFCElement eOrder = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCDocument dShipmentPrices = getShippingPrices();
		String sCurrency =  dShipmentPrices.getDocumentElement().getAttribute("Currency", "USD");
		for (YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			YFCElement eItemLine = eOrderLine.getChildElement("Item");
			for (YFCElement eCarrierService : eOrderLine.getChildElement("CarrierServiceList", true).getChildren()){
				boolean found = false;
				for (YFCElement eShippingCharge : dShipmentPrices.getDocumentElement().getChildren()){
					if (YFCCommon.equals(eCarrierService.getAttribute("CarrierServiceCode"), eShippingCharge.getAttribute("CarrierServiceCode"))){
						for (YFCElement eItem : eShippingCharge.getChildElement("Items", true).getChildren()){
							if (YFCCommon.equals(eItem.getAttribute("ItemID"), eItemLine.getAttribute("ItemID"))){
								eCarrierService.setAttribute("Price", eItem.getDoubleAttribute("Charge", 0));
								eCarrierService.setAttribute("Currency", sCurrency);
								found = true;
								break;
							}
						}
						if (found){
							break;
						} else {
							eCarrierService.setAttribute("Price", eShippingCharge.getDoubleAttribute("Charge", 0));
							eCarrierService.setAttribute("Currency", sCurrency);
							break;
						}
					}
				}
			}
		}
		return inDoc;
	}

	
}
