package com.extension.bda.service.shipments;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDARemoveInventoryOnBackorder extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "removeInventoryOnBackorder";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dShipment = YFCDocument.getDocumentFor(input);
		YFCElement eShipment = dShipment.getDocumentElement();
		
		for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()) {
			if(eShipmentLine.getDoubleAttribute("QuantityReduced", 0) > 0) {
				Document docInventorySupply = getInventorySupply(env, eShipment, eShipmentLine);
				YFCDocument dIS = YFCDocument.getDocumentFor(docInventorySupply);
				YFCElement eIS = dIS.getDocumentElement();
				double quantity = eShipmentLine.getDoubleAttribute("Quantity", 0);
				for(YFCElement eInventorySupply : eIS.getChildElement("Supplies", true).getChildren()) {
					if(quantity > eInventorySupply.getDoubleAttribute("Quantity")) {
						quantity -= eInventorySupply.getDoubleAttribute("Quantity");
					} else if(quantity == 0) {
						moveInventory(env, eShipment, eShipmentLine, eInventorySupply, true);
					} else {
						quantity -= eInventorySupply.getDoubleAttribute("Quantity");
						moveInventory(env, eShipment, eShipmentLine, eInventorySupply, false);
					}
				}
			}
		}
		
		
		return input;
	}
	
	public void moveInventory(YFSEnvironment env, YFCElement eShipment, YFCElement eShipmentLine, YFCElement eExistingSupply, boolean removeAll) {
		YFCDocument dInput = YFCDocument.createDocument("Items");
		YFCElement eReduceItem = dInput.getDocumentElement().createChild("Item");
		eReduceItem.setAttribute("SupplyType", "ONHAND");
		YFCElement eIncreaseItem = dInput.getDocumentElement().createChild("Item");
		eIncreaseItem.setAttribute("SupplyType", "MISSING.ex");
		eIncreaseItem.setAttribute("ETA", YDate.newDate());
		if(removeAll) {
			eReduceItem.setAttribute("Quantity", "-" + eExistingSupply.getAttribute("Quantity"));
			eIncreaseItem.setAttribute("Quantity", eExistingSupply.getAttribute("Quantity"));
		} else {
			eReduceItem.setAttribute("Quantity", "-" + (eExistingSupply.getDoubleAttribute("Quantity") - eShipmentLine.getDoubleAttribute("Quantity")));
			eIncreaseItem.setAttribute("Quantity", (eExistingSupply.getDoubleAttribute("Quantity") - eShipmentLine.getDoubleAttribute("Quantity")));
		}

		createAdjustment(eReduceItem, eShipment.getAttribute("ShipNode"), eShipmentLine, eExistingSupply);
		createAdjustment(eIncreaseItem, eShipment.getAttribute("ShipNode"), eShipmentLine, eExistingSupply);
		this.callApi(env, dInput.getDocument(), null, "adjustInventory");
	}
	
	private void createAdjustment(YFCElement eRoot, String sShipNode, YFCElement eShipmentLine, YFCElement eInventorySupply) {
		eRoot.setAttribute("AdjustmentType", "ADJUSTMENT");
		eRoot.setAttribute("Availability", "TRACK");
		eRoot.setAttribute("ItemID", eShipmentLine.getAttribute("ItemID"));
		eRoot.setAttribute("UnitOfMeasure", eShipmentLine.getAttribute("UnitOfMeasure"));
		eRoot.setAttribute("ShipNode", sShipNode);
		if(!YFCCommon.isVoid(eShipmentLine.getAttribute("ProductClass"))) {
			eRoot.setAttribute("ProductClass", eShipmentLine.getAttribute("ProductClass"));
		}
		if(!YFCCommon.isVoid(eInventorySupply.getAttribute("Segment"))) {
			eRoot.setAttribute("Segment", eInventorySupply.getAttribute("Segment"));
		}
		if(!YFCCommon.isVoid(eInventorySupply.getAttribute("SegmentType"))) {
			eRoot.setAttribute("SegmentType", eInventorySupply.getAttribute("SegmentType"));
		}
	}
	
	public static Document getInventorySupplyTemplate() {
		YFCDocument dItem = YFCDocument.createDocument("Item");
		YFCElement eItem = dItem.getDocumentElement();
		eItem.setAttribute("ItemID", "");
		YFCElement eInventorySupply = eItem.createChild("Supplies").createChild("InventorySupply");
		eInventorySupply.setAttribute("Quantity", "");
		eInventorySupply.setAttribute("Segment", "");
		eInventorySupply.setAttribute("SegmentType", "");
		eInventorySupply.setAttribute("SupplyType", "");
		eInventorySupply.setAttribute("InventoryItemKey", "");
		eInventorySupply.setAttribute("InventorySupplyKey", "");
		return dItem.getDocument();
	}

	public Document getInventorySupply(YFSEnvironment env, YFCElement eShipment, YFCElement eShipmentLine) {
		YFCDocument dInput = YFCDocument.createDocument("InventorySupply");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("CallingOrganizationCode", eShipment.getAttribute("EnterpriseCode"));
		eInput.setAttribute("ShipNode", eShipment.getAttribute("ShipNode"));
		eInput.setAttribute("SupplyType", "ONHAND");
		eInput.setAttribute("ItemID", eShipmentLine.getAttribute("ItemID"));
		eInput.setAttribute("UnitOfMeasure", eShipmentLine.getAttribute("UnitOfMeasure"));
		if(!YFCCommon.isVoid(eShipmentLine.getAttribute("ProductClass"))) {
			eInput.setAttribute("ProductClass", eShipmentLine.getAttribute("ProductClass"));
		}
	
		return this.callApi(env, dInput.getDocument(), getInventorySupplyTemplate(), "getInventorySupply");
	}
	
}
