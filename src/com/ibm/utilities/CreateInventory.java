package com.ibm.utilities;

import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.utilities.FileReader;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class CreateInventory {

	private String getServer(){
		return "http://oms.omfulfillment.com:9080";
	}
	
	private static YFCDocument getInput(){
		YFCDocument dOutput = YFCDocument.createDocument("Item");
		YFCElement eInput = dOutput.getDocumentElement();
		eInput.setAttribute("CallingOrganizationCode", "greenwheels");
		YFCElement ePriInfo = eInput.createChild("PrimaryInformation");
		ePriInfo.setAttribute("Status", "3000");
		YFCElement eCatFilter = eInput.createChild("CategoryFilter");
		eCatFilter.setAttribute("CategoryPath", "/bikes");
		eCatFilter.setAttribute("CategoryPathQryType", "FLIKE");
		return dOutput;
	}
	
	private static YFCDocument getItemTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("ItemList");
		YFCElement eInput = dOutput.getDocumentElement();
		YFCElement eItem = eInput.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement ePriInfo = eItem.createChild("PrimaryInformation");
		ePriInfo.setAttribute("DefaultProductClass", "");
		return dOutput;
	}
	
	private static YFCDocument getShipNodeTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("ShipNodeList");
		YFCElement eInput = dOutput.getDocumentElement();
		YFCElement eItem = eInput.createChild("ShipNode");
		eItem.setAttribute("NodeType", "");
		eItem.setAttribute("ShipNode", "");
		return dOutput;
	}
	
	private static YFCDocument getShipNodeListInput(){
		YFCDocument dOutput = YFCDocument.createDocument("ShipNode");
		YFCElement eInput = dOutput.getDocumentElement();
		eInput.setAttribute("OwnerKey", "Aurora");
		return dOutput;
	}
	
	public static void main(String[] args){
		CreateInventory t = new CreateInventory();
		Document temp = t.getAdjustInventoryInput(null, null);
		if (!YFCCommon.isVoid(temp)){
			YFCDocument dTemp = YFCDocument.getDocumentFor(temp);
			System.out.println(dTemp);
			FileReader.writeXML("d:\\AdjustInventory.xml", dTemp);
		}		
	}
	
	public Document getAdjustInventoryInput (YFSEnvironment env, Document input){
		YFCDocument dInput;
		if (!YFCCommon.isVoid(input)){
			dInput = YFCDocument.getDocumentFor(input);	
		} else {
			dInput = YFCDocument.createDocument("Input");
		}
		YFCElement eInput = dInput.getDocumentElement();
		
		YFCDocument dItems = YFCDocument.createDocument("Items");
		YFCElement eItems = dItems.getDocumentElement();
		YFCDocument dItemList = CallInteropServlet.invokeApi(getInput(), getItemTemplate(), "getItemList", getServer());
		
		YFCDocument dShipNodeList = CallInteropServlet.invokeApi(getShipNodeListInput(), getShipNodeTemplate(), "getShipNodeList", getServer());
		
		for (YFCElement eItem : dItemList.getDocumentElement().getChildren()){
			if (eItem.getAttribute("ItemID").length() <= 40){
			for (YFCElement eShipNode : dShipNodeList.getDocumentElement().getChildren()){
				if (YFCCommon.equals(eShipNode.getAttribute("ShipNode"),"Auro_Store_4")){
					YFCElement eAdjustment = eItems.createChild("Item");
					eAdjustment.setAttribute("AdjustmentType", "ADJUSTMENT");
					eAdjustment.setAttribute("Availability", "TRACK");
					eAdjustment.setAttribute("ItemID", eItem.getAttribute("ItemID"));
					if (!YFCCommon.isVoid(eItem.getChildElement("PrimaryInformation").getAttribute("DefaultProductClass"))){
						eAdjustment.setAttribute("ProductClass", eItem.getChildElement("PrimaryInformation").getAttribute("DefaultProductClass"));
					}
					if (!YFCCommon.isVoid(eItem.getAttribute("UnitOfMeasure"))){
						eAdjustment.setAttribute("UnitOfMeasure", eItem.getAttribute("UnitOfMeasure"));
					}
					eAdjustment.setAttribute("Quantity", eInput.getDoubleAttribute("Quantity", 1000));
					eAdjustment.setAttribute("SupplyType", "ONHAND");
					eAdjustment.setAttribute("ShipNode", eShipNode.getAttribute("ShipNode"));
				}
			}
			}
		}
		CallInteropServlet.invokeApi(dItems, null, "adjustInventory", getServer());
		return dItems.getDocument();
		
		
	}
}
