package com.extension.bda.service.manufacturing;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAInventorySynch extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "productionInventorySynch";
	}
	
	public boolean evaluateProduction(YFSEnvironment env, Document docSupplies) {
	
		if(YFCCommon.equals(env.getTxnObject("productionInventorySynch"), "Y")){
			env.setTxnObject("productionInventorySynch", "N");
			return false;
		};
		if(!YFCCommon.isVoid(docSupplies)) {
			YFCDocument dSupplies = YFCDocument.getDocumentFor(docSupplies);
			YFCElement eSupplies = dSupplies.getDocumentElement();
			for(YFCElement eSupply : eSupplies.getChildren()) {
				if(YFCCommon.equals(eSupply.getAttribute("InventoryOrganizationCode"), "Lastar_Prod")) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public Document invoke(YFSEnvironment env, Document docSupplies) throws Exception {
		YFCDocument dInput = YFCDocument.createDocument("Promise");
		YFCElement eInput = dInput.getDocumentElement();
		
		if(evaluateProduction(env, docSupplies)) {
			eInput.setAttribute("OrganizationCode", "Lastar_Prod");
			eInput.setAttribute("ShipNode", "Production1");
			YFCElement ePromiseLine = eInput.getChildElement("PromiseLines", true).createChild("PromiseLine");
			ePromiseLine.setAttribute("ItemID", "Switch");
			ePromiseLine.setAttribute("LineId", "1");
			ePromiseLine.setAttribute("UnitOfMeasure", "EACH");
			Document dPromiseOutput = this.callApi(env, dInput.getDocument(), null, "getAvailableInventory");
			boolean update = false;
			YFCDocument dAdjust = YFCDocument.createDocument("Items");
			YFCElement eAdjust = dAdjust.getDocumentElement();
			if(!YFCCommon.isVoid(dPromiseOutput)) {
				for(YFCElement ePL : YFCDocument.getDocumentFor(dPromiseOutput).getDocumentElement().getChildElement("PromiseLines", true).getChildren()) {
					if(YFCCommon.equals(ePL.getAttribute("LineId"), "1")) {
						YFCElement eAvailableInventory = ePL.getChildElement("Availability", true).getChildElement("AvailableInventory");
						if(!YFCCommon.isVoid(eAvailableInventory)) {
							for(YFCElement eInventory : eAvailableInventory.getChildElement("ShipNodeAvailableInventory", true).getChildren()) {
								update = true;
								YFCElement eItem = eAdjust.createChild("Item");
								eItem.setAttribute("AdjustmentType", "ABSOLUTE");
								eItem.setAttribute("Availability", "TRACK");
								eItem.setAttribute("ItemID", ePL.getAttribute("ItemID"));
								eItem.setAttribute("UnitOfMeasure", ePL.getAttribute("UnitOfMeasure"));
								eItem.setAttribute("ShipNode", eInventory.getAttribute("Node"));
								eItem.setAttribute("Quantity", eInventory.getAttribute("AvailableQuantity"));
								eItem.setAttribute("OrganizationCode", "Lastar");
								eItem.setAttribute("SupplyType", "ONHAND");
							}
						}
					}
				}
			}
			if(update) {
				env.setTxnObject("productionInventorySynch", "Y");
				return this.callApi(env, dAdjust.getDocument(), null, "adjustInventory");
			}
		}
		
		return null;
	}

	
	
}
