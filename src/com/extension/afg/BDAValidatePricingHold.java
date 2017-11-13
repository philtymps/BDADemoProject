package com.extension.afg;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.shared.ycd.YCDConstants;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAValidatePricingHold implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "validatePricingHold";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub

	}

	private Document getOrderDetailsTemplate(){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		eOrder.setAttribute("BillToID", "");
		eOrder.setAttribute("BuyerUserId", "");
		eOrder.setAttribute("CustomerContactID", "");
		eOrder.getChildElement("PriceInfo", true).setAttribute("Currency", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("SubLineNo", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		return dOrder.getDocument();
	}
	
	private boolean hasPrice(YFSEnvironment env, YFCElement eOrder, YFCElement eOrderLine){
		if(!YFCCommon.isVoid(eOrder)){
			for(YFCElement eOL : eOrder.getChildElement("OrderLines", true).getChildren()){
				if(YFCCommon.equals(eOL.getAttribute("OrderLineKey"), eOrderLine.getAttribute("OrderLineKey"))){
					YFCDocument dItemPrice = YFCDocument.createDocument("ItemPrice");
					YFCElement eItemPrice = dItemPrice.getDocumentElement();
					setExistingAttribute(eOrder, eItemPrice, "EnterpriseCode");
					setExistingAttribute(eOrder, eItemPrice, "SellerOrganizationCode", "OrganizationCode");
					setExistingAttribute(eOrder, eItemPrice, "BuyerUserId");
					setExistingAttribute(eOrder, eItemPrice, "BillToID", "CustomerID");
					setExistingAttribute(eOrder, eItemPrice, "CustomerContactID");
					setExistingAttribute(eOrder.getChildElement("PriceInfo", true), eItemPrice, "Currency");
					YFCElement eLine = eItemPrice.getChildElement("LineItems", true).createChild("LineItem");
					setExistingAttribute(eOL, eLine, "OrderLineKey", "LineID");
					setExistingAttribute(eOL, eLine, "OrderedQty", "Quantity");
					setExistingAttribute(eOL.getChildElement("Item", true), eLine, "ItemID");
					setExistingAttribute(eOL.getChildElement("Item", true), eLine, "UnitOfMeasure");
					try {
						YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
						Document l_OutputXml = localApi.invoke(env, "getItemPrice", dItemPrice.getDocument());
						YFCElement eResponse = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
						if(!YFCCommon.isVoid(eResponse)){
							return eResponse.getChildElement("LineItems", true).getChildElement("LineItem", true).getDoubleAttribute("UnitPrice", 0) > 0;
						}
					} catch(Exception yex) {
						yex.printStackTrace();
			        } 
				}
			}
		}
		return false;
	}
	
	private void setExistingAttribute(YFCElement eFrom, YFCElement eTo, String sAttribute){
		if(!YFCCommon.isVoid(eFrom.getAttribute(sAttribute))){
			eTo.setAttribute(sAttribute, eFrom.getAttribute(sAttribute));
		}
	}
	
	private void setExistingAttribute(YFCElement eFrom, YFCElement eTo, String sFromAttribute, String sToAttribute){
		if(!YFCCommon.isVoid(eFrom.getAttribute(sFromAttribute))){
			eTo.setAttribute(sToAttribute, eFrom.getAttribute(sFromAttribute));
		}
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document inDoc) {
		YFCDocument orderDoc = YFCDocument.getDocumentFor(inDoc);
		YFCElement eOrderLines = orderDoc.getDocumentElement().getChildElement("OrderLines");
		if(!YFCCommon.isVoid(eOrderLines)){
			
			try {
				env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				YFCDocument dOrderInput = YFCDocument.createDocument("Order");
				YFCElement eOrderInput = dOrderInput.getDocumentElement();
				eOrderInput.setAttribute("OrderHeaderKey", orderDoc.getDocumentElement().getAttribute("OrderHeaderKey"));
				Document l_OutputXml = localApi.invoke(env, "getOrderDetails", dOrderInput.getDocument());
				YFCElement eOrder = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				for(YFCElement eOrderLine : eOrderLines.getChildren()){
					YFCElement holdTypesToProcess = eOrderLine.getChildElement("HoldTypesToProcess");
					if(!YFCCommon.isVoid(holdTypesToProcess)){
						 for (YFCElement holdToProcess : holdTypesToProcess.getChildren()) {
							 if(YFCCommon.equals(holdToProcess.getAttribute("HoldType"), "PreOrder")){
								 if(hasPrice(env, eOrder, eOrderLine)){
									YFCElement processedHold = eOrderLine.getChildElement("ProcessedHoldTypes", true).createChild("OrderHoldType");
									processedHold.setAttribute("HoldType",holdToProcess.getAttribute("HoldType"));
						            processedHold.setAttribute("ReasonText", "Price Found");
						            processedHold.setAttribute("Status",YCDConstants.YCP_HOLD_TYPE_RESOLVED);
						            holdTypesToProcess.removeChild(holdToProcess);
								 }
							 }
				        }
					}
				}
			} catch(Exception yex) {
				yex.printStackTrace();
	        } 
			
		}
       
        return inDoc;
	}

}
