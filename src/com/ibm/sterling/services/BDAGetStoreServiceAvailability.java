package com.ibm.sterling.services;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetStoreServiceAvailability {

	private static Document getFindInventoryInput(YFCElement eInput, YFCElement eNode){
		YFCDocument dOutput = YFCDocument.createDocument("Promise");
		YFCElement eOutput = dOutput.getDocumentElement();
		eOutput.setAttribute("OrganizationCode", eInput.getAttribute("OrganizationCode"));
		eOutput.setAttribute("IgnoreMinNotificationTime", eInput.getAttribute("IgnoreMinNotificationTime", "Y"));
		YFCElement ePromiseServiceLines = eOutput.createChild("PromiseServiceLines");
		YFCElement eOrderLines = eInput.getChildElement("OrderLines");
		for(YFCElement eOrderLine : eOrderLines.getChildren()){
			YFCElement ePromiseServiceLine = ePromiseServiceLines.createChild("PromiseServiceLine");
			YFCElement eItem = eOrderLine.getChildElement("Item");
			if(!YFCCommon.isVoid(eItem)){
				ePromiseServiceLine.setAttribute("ItemID", eItem.getAttribute("ItemID"));
				ePromiseServiceLine.setAttribute("UnitOfMeasure", eItem.getAttribute("UnitOfMeasure"));
				if(!YFCCommon.isVoid(eItem.getAttribute("ProductClass"))){
					ePromiseServiceLine.setAttribute("ProductClass", eItem.getAttribute("ProductClass"));
				}
				if(!YFCCommon.isVoid(eOrderLine.getAttribute("RequiredQty"))){
					ePromiseServiceLine.setAttribute("RequiredQty", eOrderLine.getAttribute("RequiredQty"));
				}
				YFCElement eShipNode = ePromiseServiceLine.createChild("ShipNodes").createChild("ShipNode");
				eShipNode.setAttribute("Node", eNode.getAttribute("ShipNode"));
				YFCElement eShipToAddress = eOutput.createChild("ShipToAddress");
				eShipToAddress.setAttributes(eInput.getChildElement("NodeSearch", true).getChildElement("ShipToAddress", true).getAttributes());
			}
		}
		return dOutput.getDocument();
	}
	
	private static Document getFindInventoryTemplate(){
		YFCDocument dT = YFCDocument.createDocument("Promise");
		YFCElement eOutput = dT.getDocumentElement();
		eOutput.setAttribute("OrganizationCode","");
		YFCElement eOption = eOutput.createChild("SuggestedOption").createChild("Option");
		eOption.setAttribute("AvailableFromUnplannedInventory", "");
		eOption.setAttribute("FirstDate", "");
		eOption.setAttribute("LastDate", "");
		eOption.setAttribute("HasAnyUnavailableQty", "");
		return dT.getDocument();
	}
	
	private static void addAvailability (YFCElement eNode, YFCElement ePromiseOutput){
		YFCElement eAvailability = eNode.createChild("Availability");
		eAvailability.setAttribute("IsAvailable", "N");
		YFCElement eSuggestedOption = ePromiseOutput.getChildElement("SuggestedOption");
		if(!YFCCommon.isVoid(eSuggestedOption)){
			YFCElement eOption = eSuggestedOption.getChildElement("Option");
			if(!YFCCommon.isVoid(eOption)){
				eAvailability.setAttribute("IsAvailable", !eOption.getBooleanAttribute("HasAnyUnavailableQty", true));
				eAvailability.setAttribute("ShipNode", eNode.getAttribute("ShipNode"));
				if(eAvailability.getBooleanAttribute("IsAvailable")){
					eAvailability.setAttribute("AvailableDate", eOption.getAttribute("FirstDate"));
				}
			}
		}
	}
	
	public Document getStoreServiceAvailability(YFSEnvironment env, Document input){
		YFCDocument dAlternateStores = YFCDocument.createDocument("AlternateStores");
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("findInventory", getFindInventoryTemplate());
			YFCElement eOutput = dAlternateStores.getDocumentElement();
			
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eInput = dInput.getDocumentElement();
			eOutput.setAttribute("OrganizationCode", eInput.getAttribute("OrganizationCode"));
			YFCElement eNodeList = eInput.getChildElement("NodeList");
			if(!YFCCommon.isVoid(eNodeList)){
				YFCElement outNodeList = eOutput.createChild("NodeList");
				for(YFCElement eNode : eNodeList.getChildren()){
					Document inventoryOutput = localApi.findInventory(env, getFindInventoryInput(eInput, eNode));
					YFCElement outNode = (YFCElement) outNodeList.importNode(eNode);
					addAvailability(outNode, YFCDocument.getDocumentFor(inventoryOutput).getDocumentElement());
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return dAlternateStores.getDocument();
	}
}
