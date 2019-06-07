package com.extension.bda.wcs.services;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.extension.bda.service.promotions.BDAGetPreviouslyViewedItems;
import com.yantra.pca.bridge.YCDFoundationBridge;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetOnlineCartForCustomer extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		return "getOnlineCartForCustomer";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document inDoc) throws Exception {
		YFCDocument dResponse = YFCDocument.createDocument("CartList");
		YFCElement eResponse = dResponse.getDocumentElement();
		int records = 0;
		YFCElement inputElement = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCElement eCustomer = BDAGetPreviouslyViewedItems.getCustomerDetails(env, inputElement);
		String sUserID = null;
		if(!YFCCommon.isVoid(eCustomer)) {
			for(YFCElement eCustomerContact : eCustomer.getChildElement("CustomerContactList", true).getChildren()) {
				if(!YFCCommon.isVoid(eCustomerContact.getAttribute("UserID"))) {
					sUserID = eCustomerContact.getAttribute("UserID");
					break;
				}
			}
		}
		if(!YFCCommon.isVoid(sUserID)) {
			YFCDocument dServiceInput = YFCDocument.createDocument("Order");
			YFCElement eServiceInput = dServiceInput.getDocumentElement();
			eServiceInput.setAttribute("LoginId", sUserID);
			eServiceInput.setAttribute("StoreId", "Aurora");
			Document dServiceOutput = this.callService(env, dServiceInput.getDocument(), null, "SCWC_SDF_GetCartList");
			if(!YFCCommon.isVoid(dServiceOutput)) {
				YFCElement eCartList = YFCDocument.getDocumentFor(dServiceOutput).getDocumentElement();
				
				for(YFCElement eCart : eCartList.getChildren()) {
					records++;
					String sCartID = eCart.getAttribute("CartId");
					YFCDocument dCartDetailsInput = YFCDocument.createDocument("Order");
					YFCElement eCartDetailsInput = dCartDetailsInput.getDocumentElement();
					eCartDetailsInput.setAttribute("CartId", sCartID);
					
					Document dCartOutput = this.callService(env, dCartDetailsInput.getDocument(), null, "SCWC_SDF_GetCartDetails");
				
					if(!YFCCommon.isVoid(dCartOutput)) {
						YFCElement eOrder = YFCDocument.getDocumentFor(dCartOutput).getDocumentElement();
						YFCElement eCartResponse = eResponse.createChild("Cart");
						eCartResponse.setAttribute("CartID", eOrder.getAttribute("CartId"));
						eCartResponse.setAttribute("Enterprise", eOrder.getAttribute("EnterpriseCode"));
						eCartResponse.setAttribute("CartDate", eOrder.getAttribute("OrderDate"));
						eCartResponse.setAttribute("Currency", eOrder.getChildElement("PriceInfo").getAttribute("Currency"));
						YFCElement eItemListResponse = eCartResponse.createChild("ItemList");
						int quantity = 0;
						for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
							quantity++;
							YFCElement eItem = eOrderLine.getChildElement("Item", true);
							YFCElement eItemResponse = eItemListResponse.createChild("Item");
							eItemResponse.setAttribute("ItemID", eItem.getAttribute("ItemID"));
							eItemResponse.setAttribute("UnitOfMeasure", eItem.getAttribute("UnitOfMeasure"));
							eItemResponse.setAttribute("OrganizationCode", eOrder.getAttribute("EnterpriseCode"));
							eItemResponse.setAttribute("CartDeliveryMethod", eOrderLine.getAttribute("DeliveryMethod"));
							if(!YFCCommon.isVoid(eOrderLine.getAttribute("ShipNode"))) {
								eItemResponse.setAttribute("ShipNode", eOrderLine.getAttribute("ShipNode"));
							}
							
							eItemResponse.setAttribute("OrderedQuantity", eOrderLine.getAttribute("OrderedQty"));
							if(!YFCCommon.isVoid(eItem.getAttribute("ProductClass"))) {
								eItemResponse.setAttribute("ProductClass", eItem.getAttribute("ProductClass"));
							}
						}
						eItemListResponse.setAttribute("TotalNumberOfRecords", quantity);
						eCartResponse.setIntAttribute("TotalNumberOfItems", quantity);
					}
					
				}
				
			}
		}
		if(records == 0) {
			eResponse.createChild("Cart").createChild("ItemList").setAttribute("TotalNumberOfRecords", 0);
		}
		eResponse.setAttribute("TotalNumberOfRecords", records);
		return dResponse.getDocument();
	}


}
