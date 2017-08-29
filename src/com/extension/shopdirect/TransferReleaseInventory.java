package com.extension.shopdirect;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class TransferReleaseInventory {

	public Document transferSupplierInventoryToEnterprise(YFSEnvironment env, Document input){
		if(!YFCCommon.isVoid(input)){
			YFCElement eOrder = YFCDocument.getDocumentFor(input).getDocumentElement();
			String sBuyerOrg = eOrder.getAttribute("BuyerOrganizationCode");
			String sSellerOrg = eOrder.getAttribute("SellerOrganizationCode");
			System.out.println("RELEASE.ON_SUCCESS: " + eOrder);
			try {
				YFCDocument dOrg = YFCDocument.createDocument("Organization");
				YFCElement eOrg = dOrg.getDocumentElement();
				
				YFCDocument getOrgTemplate = YFCDocument.createDocument("Organization");
				YFCElement template = getOrgTemplate.getDocumentElement();
				template.setAttribute("OrgnaizationCode", "");
				template.setAttribute("InventoryOrganizationCode", "");
				env.setApiTemplate("getOrganizationHierarchy", getOrgTemplate.getDocument());
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				eOrg.setAttribute("OrganizationCode", sBuyerOrg);
				Document dBuyer = localApi.invoke(env, "getOrganizationHierarchy", dOrg.getDocument());
				
				eOrg.setAttribute("OrganizationCode", sSellerOrg);
				Document dSeller = localApi.invoke(env, "getOrganizationHierarchy", dOrg.getDocument());
				
				if(!YFCCommon.equals(dBuyer.getDocumentElement().getAttribute("InventoryOrganizationCode"), dSeller.getDocumentElement().getAttribute("InventoryOrganizationCode"))){
					YFCDocument dItems = YFCDocument.createDocument("Items");
					YFCElement eItems = dItems.getDocumentElement();
					int count = 0;
					for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
						for(YFCElement eOrderStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
							YFCElement eItem = eItems.createChild("Item");
							eItem.setAttribute("FromOrganizationCode", dSeller.getDocumentElement().getAttribute("InventoryOrganizationCode"));
							eItem.setAttribute("ToOrganizationCode", dBuyer.getDocumentElement().getAttribute("InventoryOrganizationCode"));
							eItem.setAttribute("ItemID", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
							eItem.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item", true).getAttribute("UnitOfMeasure"));
							eItem.setAttribute("SupplyType", "ONHAND");
							eItem.setAttribute("Quantity", eOrderStatus.getAttribute("StatusQty"));
							eItem.setAttribute("ShipNode", eOrderStatus.getAttribute("ShipNode"));
						}
							
						count++;
					}
					if(count > 0){
						localApi.invoke(env, "transferInventoryOwnership", dItems.getDocument());
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}	
			
		}
		return input;
	}
	
	
}
