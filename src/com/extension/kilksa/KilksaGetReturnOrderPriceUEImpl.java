package com.extension.kilksa;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.OMPGetReturnOrderPriceUE;

public class KilksaGetReturnOrderPriceUEImpl implements OMPGetReturnOrderPriceUE {

	private static HashMap<String, ArrayList<String>> itemMap = null;
	
	private void loadMap(boolean reload){
		if (itemMap == null || reload){
			itemMap = new HashMap<String, ArrayList<String>>();
			YFCDocument eLookFor = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/ue/KilkReturnItems.xml");
			for (YFCElement eItem : eLookFor.getDocumentElement().getChildren()){
				if (!YFCCommon.isVoid(eItem.getAttribute("ItemID"))){
					ArrayList<String> children = null;
					for (YFCElement eChild : eItem.getChildElement("FreeItems", true).getChildren()){
						if (!YFCCommon.isVoid(eChild.getAttribute("ItemID"))){
							if (children == null){
								children = new ArrayList<String>();
							}
							children.add(eChild.getAttribute("ItemID"));
						}
					}
					if (children != null){
						itemMap.put(eItem.getAttribute("ItemID"), children);
					}
				}
			}
		}
	}
	
	private Document getOrderLineListTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("OrderLineList");
		YFCElement eOrderLine = dOutput.getDocumentElement().createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		YFCElement eLinePriceInfo = eOrderLine.createChild("ComputedPrice");
		eLinePriceInfo.setAttribute("UnitPrice", "");
		eLinePriceInfo.setAttribute("Discount", "");
		eLinePriceInfo.setAttribute("Charges", "");
		eLinePriceInfo.setAttribute("ExtendedPrice", "");
		eLinePriceInfo.setAttribute("Tax", "");
		eLinePriceInfo.setAttribute("LineTotal", "");
		return dOutput.getDocument();
	}
	
	private YFCElement eGetOriginalOrder(YFSEnvironment env, String sOrderHeaderKey){
		try {
			YFCDocument dInput = YFCDocument.createDocument("OrderLine");
			dInput.getDocumentElement().setAttribute("OrderHeaderKey", sOrderHeaderKey);
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("getOrderLineList", getOrderLineListTemplate());
			Document l_OutputXml = localApi.invoke(env, "getOrderLineList", dInput.getDocument());
			YFCElement output = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
			return output;
		} catch(Exception yex) {
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
		return null;
	}
	
	@Override
	public Document getReturnOrderPrice(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		HashMap<String, YFCElement> dOrders = new HashMap<String, YFCElement>();
		loadMap(true);
		if(!YFCCommon.isVoid(itemMap) && !itemMap.isEmpty()){
			YFCElement eOrder = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
			for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
				YFCElement eItem = eOrderLine.getChildElement("Item");
				if (!YFCCommon.isVoid(eItem) && !YFCCommon.isVoid(eItem.getAttribute("ItemID"))){
					if (itemMap.containsKey(eItem.getAttribute("ItemID"))){
						ArrayList<String> childKeys = itemMap.get(eItem.getAttribute("ItemID"));
						if (!dOrders.containsKey(eOrderLine.getAttribute("DerivedOrderHeaderKey"))){
							dOrders.put(eOrderLine.getAttribute("DerivedOrderHeaderKey"), eGetOriginalOrder(env, eOrderLine.getAttribute("DerivedOrderHeaderKey")));
						}
						if (dOrders.containsKey(eOrderLine.getAttribute("DerivedOrderHeaderKey"))){
							YFCElement eOrderLineList = dOrders.get(eOrderLine.getAttribute("DerivedOrderHeaderKey"));
							if (!YFCCommon.isVoid(eOrderLineList)){
								for (YFCElement eDerivedOrderLine : eOrderLineList.getChildren()){
									if (childKeys.contains(eDerivedOrderLine.getChildElement("Item", true).getAttribute("ItemID"))){
										boolean skip = false;
										for(YFCElement eOtherLines : eOrder.getChildElement("OrderLines", true).getChildren()){
											if (YFCCommon.equals(eOtherLines.getChildElement("Item").getAttribute("ItemID"), eDerivedOrderLine.getChildElement("Item", true).getAttribute("ItemID"))){
												skip = true;
											}
										}
										if (!skip){
											eOrderLine.setAttribute("IsPriceLocked", "Y");
											YFCElement eLineCharges = eOrderLine.getChildElement("LineCharges", true);
											boolean found = false;
											for (YFCElement eLineCharge : eLineCharges.getChildren()){
												if (YFCCommon.equals(eLineCharge.getAttribute("ChargeCategory"), "Fee") && YFCCommon.equals(eLineCharge.getAttribute("ChargeName"), "Fee for Free Item - " + eDerivedOrderLine.getChildElement("Item").getAttribute("ItemID"))){
													found = true;
												}
											}
											if (!found){
												YFCElement eLineCharge = eLineCharges.createChild("LineCharge");
												eLineCharge.setAttribute("ChargeCategory",  "Fee");
												eLineCharge.setAttribute("ChargeName",  "Fee for Free Item - " + eDerivedOrderLine.getChildElement("Item").getAttribute("ItemID"));
												eLineCharge.setAttribute("ChargePerLine", eDerivedOrderLine.getChildElement("ComputedPrice").getDoubleAttribute("ExtendedPrice", 0));
											}
										} else {
											YFCElement eLineCharges = eOrderLine.getChildElement("LineCharges", true);
											boolean found = false;
											for (YFCElement eLineCharge : eLineCharges.getChildren()){
												if (YFCCommon.equals(eLineCharge.getAttribute("ChargeCategory"), "Fee") && YFCCommon.equals(eLineCharge.getAttribute("ChargeName"), "Fee for Free Item - " + eDerivedOrderLine.getChildElement("Item").getAttribute("ItemID"))){
													eLineCharges.removeChild(eLineCharge);
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return inDoc;
	}

}
