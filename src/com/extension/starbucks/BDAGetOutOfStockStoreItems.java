package com.extension.starbucks;

import java.rmi.RemoteException;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.core.YFCIterable;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAGetOutOfStockStoreItems {

	public static Document getItemListForOrderingTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("ItemList");
		YFCElement eItemList = dOutput.getDocumentElement();
		YFCElement eItem = eItemList.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("ItemKey", "");
		eItem.setAttribute("OrganizationCode", "");
		YFCElement ePI = eItem.createChild("PrimaryInformation");
		ePI.setAttribute("ShortDescription", "");
		ePI.setAttribute("ExtendedDescription", "");
		ePI.setAttribute("ImageID", "");
		ePI.setAttribute("ImageLocation", "");
		YFCElement eAvail = eItem.createChild("Availability");
		eAvail.setAttribute("CurrentAvailableQty", "");
		return dOutput.getDocument();
	}
	
	public static Document getInventoryAlertsListTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("InventoryItemList");
		YFCElement eItems = dOutput.getDocumentElement();
		YFCElement eItem = eItems.createChild("InventoryItem");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement eInventoryAlert = eItem.createChild("InventoryAlertsList").createChild("InventoryAlerts");
		eInventoryAlert.setAttribute("OnhandAvailableQuantity", "");
		return dOutput.getDocument();
	}
	
	public static Document getInventoryAlertsListInput(String sOrganizationCode, String sShipNode){
		YFCDocument dOutput = YFCDocument.createDocument("InventoryAlerts");
		YFCElement eItem = dOutput.getDocumentElement();
		eItem.setAttribute("Node", sShipNode);
		eItem.setAttribute("OrganizationCode", sOrganizationCode);
		eItem.setAttribute("AlertType", "REALTIME_ONHAND");
		return dOutput.getDocument();
	}
	
	public static Document getOrganizationHierarchyTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Organization");
		YFCElement eOrganization = dOutput.getDocumentElement();
		eOrganization.setAttribute("OrganizationCode", "");
		eOrganization.setAttribute("InventoryOrganizationCode", "");
		return dOutput.getDocument();
	}
	

	public static Document getOrganizationHierarchyInput(String sOrganizationCode){
		YFCDocument dOutput = YFCDocument.createDocument("Organization");
		YFCElement eOrganization = dOutput.getDocumentElement();
		eOrganization.setAttribute("OrganizationCode", sOrganizationCode);
		return dOutput.getDocument();
	}
	
	public Document getOutOfStockItems(YFSEnvironment env, Document input){
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		YFCElement eShipNode =eInput.getChildElement("ShipNodes", true).getChildElement("ShipNode", true);
		if(YFCCommon.isVoid(eShipNode.getAttribute("Node"))){
			eShipNode.setAttribute("Node", eInput.getAttribute("CallingOrganizationCode"));
		}
		YIFApi localApi;
		YFCDocument dNewItemList = YFCDocument.createDocument("ItemList");
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
		//	env.setApiTemplate("getOrganizationHierarchy", getOrganizationHierarchyTemplate());
			env.setApiTemplate("getItemListForOrdering", getItemListForOrderingTemplate());
		//	env.setApiTemplate("getInventoryAlertsList", getInventoryAlertsListTemplate());
			Document dItemList = localApi.invoke(env, "getItemListForOrdering", input);
			if(!YFCCommon.isVoid(dItemList)){
				/*Document dOrg = localApi.invoke(env, "getOrganizationHierarchy", getOrganizationHierarchyInput(eInput.getAttribute("CallingOrganizationCode")));
				if(!YFCCommon.isVoid(dOrg)){
					YFCElement eOrg = YFCDocument.getDocumentFor(dOrg).getDocumentElement();
					Document dAlerts = localApi.invoke(env, "getInventoryAlertsList", getInventoryAlertsListInput(eOrg.getAttribute("InventoryOrganizationCode"), eInput.getAttribute("CallingOrganizationCode")));
					ArrayList<String> inventoryItems = new ArrayList<String>();
					if(!YFCCommon.isVoid(dAlerts)){
						YFCDocument dAlertList = YFCDocument.getDocumentFor(dAlerts);
						YFCElement eAlertList = dAlertList.getDocumentElement();
						for(YFCElement eInventoryItem : eAlertList.getChildren()){
							if(!YFCCommon.isVoid(eInventoryItem.getChildElement("InventoryAlertsList"))){
								for(YFCElement eInventoryAlert : eInventoryItem.getChildElement("InventoryAlertsList").getChildren()){
									if (eInventoryAlert.getDoubleAttribute("OnhandAvailableQuantity", 0) > 0){
										inventoryItems.add(eInventoryItem.getAttribute("ItemID"));
										break;
									}
								}
							}
						}
					}*/
					
					YFCDocument dItems = YFCDocument.getDocumentFor(dItemList);
					YFCElement eItems = dItems.getDocumentElement();
					YFCElement eNewItems = dNewItemList.getDocumentElement();
					for(YFCIterable<YFCElement> itemList = eItems.getChildren(); itemList.hasNext();){
						YFCElement eItem = itemList.next();
						YFCElement eAvailability = eItem.getChildElement("Availability",true);
						if(eAvailability.getDoubleAttribute("CurrentAvailableQty", 0) == 0){
							eNewItems.importNode(eItem);
						}
					}
				//}
			}
			return dNewItemList.getDocument();
	
			
		} catch (YIFClientCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (YFSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return YFCDocument.createDocument("ItemList").getDocument();
	}
}
