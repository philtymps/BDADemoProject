package com.scripts;

import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class WCSIntegrationOrderPriceFix {

	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(WCSIntegrationOrderPriceFix.class);
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public Document storeOrderInput(YFSEnvironment env, Document inputDoc){
		YFCDocument input = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eInput = (YFCElement) input.getDocumentElement().cloneNode(true);
		logger.debug("storeOrderInput - inputDoc - " + input);
		YFCElement modifyInput = input.getDocumentElement();
		if (YFCCommon.equals(eInput.getNodeName(), "Order") && !YFCCommon.isVoid(modifyInput.getChildElement("PaymentMethod"))){
			YFCElement ePayment = modifyInput.getChildElement("PaymentMethod");
			if (ePayment.getAttribute("PaymentType").equals("PayInStore")){
				ePayment.setAttribute("PaymentType", "CREDIT_CARD");
				ePayment.setAttribute("CreditCardType", "VISA");
				if (YFCCommon.isVoid(ePayment.getAttribute("CreditCardName"))){
					ePayment.setAttribute("CreditCardName", "Test user");
				}
			}
		}
		if(YFCCommon.equals(eInput.getNodeName(), "Order")){
			YFCDocument dExtra = YFCDocument.createDocument("OrderLines");
			boolean found = false;
			for(YFCElement eLine : eInput.getChildElement("OrderLines", true).getChildren()){
				if(YFCCommon.equals(eLine.getAttribute("ItemID"), "Delivery") || YFCCommon.equals(eLine.getAttribute("ItemID"), "Return")){
					dExtra.importNode(eLine, true);
					eInput.getChildElement("OrderLines", true).removeChild(eLine);
					found = true;
				}
			}
			if(found){
				env.setTxnObject("serviceItems", dExtra);
			}
		} else {
			YFCDocument dExtra = YFCDocument.createDocument("LineItems");
			
			boolean found = false;
			for(YFCElement eLine : eInput.getChildElement("LineItems", true).getChildren()){
				if(YFCCommon.equals(eLine.getAttribute("ItemID"), "Delivery") || YFCCommon.equals(eLine.getAttribute("ItemID"), "Return")){
					dExtra.getDocumentElement().importNode(eLine);
					eInput.getChildElement("LineItems", true).removeChild(eLine);
					found = true;
				}
			}
			if(found){
				env.setTxnObject("serviceItems", dExtra);
			}
		}
		
		env.setTxnObject("orderInput", eInput);
		return eInput.getOwnerDocument().getDocument();
	}
	
	private Document handleOrderPrice(YFSEnvironment env, Document inputDoc) throws YIFClientCreationException{
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		Document l_OutputXml = null;
		try {
			
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			String sOrganizationCode = eInput.getAttribute("OrganizationCode");
			String sEnterpriseCode = eInput.getAttribute("EnterpriseCode");
			eInput.setAttribute("OrganizationCode", "Aurora-Corp");
			eInput.setAttribute("EnterpriseCode", "Aurora-Corp");
			l_OutputXml = localApi.invoke(env, "getOrderPrice", inputDoc);
			if (!YFCCommon.isVoid(l_OutputXml)){
				YFCElement eOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				eOutput.setAttribute("OrganizationCode", sOrganizationCode);
				eOutput.setAttribute("EnterpriseCode", sEnterpriseCode);
			}
			return l_OutputXml;
		} catch(Exception yex) {
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
		return null;
	}
	private Document handleItemPriceList(YFSEnvironment env, Document inputDoc) throws YIFClientCreationException{
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		Document l_OutputXml = null;
		try {
			
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			String sOrganizationCode = eInput.getAttribute("OrganizationCode");
			String sEnterpriseCode = eInput.getAttribute("EnterpriseCode");
			eInput.setAttribute("OrganizationCode", "Aurora-Corp");
			eInput.setAttribute("EnterpriseCode", "Aurora-Corp");
			l_OutputXml = localApi.invoke(env, "getItemPrice", inputDoc);
			if (!YFCCommon.isVoid(l_OutputXml)){
				YFCElement eOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				eOutput.setAttribute("OrganizationCode", sOrganizationCode);
				eOutput.setAttribute("EnterpriseCode", sEnterpriseCode);
			}
			return l_OutputXml;
		} catch(Exception yex) {
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
		return null;
	}
	
	public Document checkForErrors(YFSEnvironment env, Document inputDoc){
		YFCDocument input = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eInput = input.getDocumentElement();
		if (!eInput.getNodeName().equals("Errors")){
			if(!YFCCommon.isVoid(env.getTxnObject("serviceItems"))){
				YFCDocument dServiceItems = (YFCDocument) env.getTxnObject("serviceItems");
				if(eInput.getNodeName().equals("ItemPrice")){
					for(YFCElement eLine : dServiceItems.getDocumentElement().getChildren()){
						eLine.setAttribute("ListPrice", 0);
						eLine.setAttribute("LinePrice", 0);
						eLine.setAttribute("LineAdjustment",0);
						eLine.setAttribute("AbsoluteAdjustment", 0);
						eLine.setAttribute("AdditionalLinePrice", 0);
						eLine.setAttribute("ExtendedPrice", 0);
						eLine.setAttribute("PercentAdjustment", 0);
						eLine.setAttribute("UnitPrice", 0);
						eInput.getChildElement("LineItems", true).importNode(eLine);
					}
				} else {
					for(YFCElement eLine : dServiceItems.getDocumentElement().getChildren()){
						eLine.setAttribute("ListPrice", 0);
						eLine.setAttribute("LinePrice", 0);
						eLine.setAttribute("LineAdjustment",0);
						eLine.setAttribute("LineTotal", 0);
						eLine.setAttribute("OrderAdjustment", 0);
						eLine.setAttribute("AbsoluteAdjustment", 0);
						eLine.setAttribute("AdditionalLinePrice", 0);
						eLine.setAttribute("AdditionalLinePriceAdjustment", 0);
						eLine.setAttribute("ExtendedPrice", 0);
						eLine.setAttribute("PercentAdjustment", 0);
						eLine.setAttribute("UnitPrice", 0);
						eInput.getChildElement("OrderLines", true).importNode(eLine);
					}
				}
			}
			
			env.setTxnObject("serviceItems", null);
			return input.getDocument();
		}
		YFCElement request = (YFCElement)env.getTxnObject("orderInput");
		if (!YFCCommon.isVoid(request) && YFCCommon.equals(request.getNodeName(), "ItemPrice")){
			try {
				return handleItemPriceList(env, request.getOwnerDocument().getDocument());
			} catch (Exception e){
				e.printStackTrace();
			}		
		} else if (!YFCCommon.isVoid(request) && YFCCommon.equals(request.getNodeName(), "Order")){
			try {
				return handleOrderPrice(env, request.getOwnerDocument().getDocument());
			} catch (Exception e){
				e.printStackTrace();
			}		
		}

		return inputDoc;
	}
}
