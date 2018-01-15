package com.scripts;

import java.io.File;
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
		boolean removeAll = false;
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
			YFCElement eExtra = dExtra.getDocumentElement();
			boolean found = false;
			
			File services = new File("/opt/Sterling/Scripts/service_items.xml");
			if(services.exists()){
				YFCDocument dServiceItems = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/service_items.xml");
				if(!YFCCommon.isVoid(dServiceItems)){
					YFCElement eServiceItems = dServiceItems.getDocumentElement();
					for(YFCElement eServiceItem : eServiceItems.getChildren()){
						for(YFCElement eLine : eInput.getChildElement("OrderLines", true).getChildren()){
							if(YFCCommon.equals(eLine.getAttribute("ItemID"), eServiceItem.getAttribute("ItemID"))){
								eExtra.importNode(eLine);
								eInput.getChildElement("OrderLines", true).removeChild(eLine);
								found = true;
							} 
						}
					}
				}
			}
			for(YFCElement eLine : eInput.getChildElement("OrderLines", true).getChildren()){
				if(YFCCommon.equals(eLine.getAttribute("ItemID"), "Delivery") || YFCCommon.equals(eLine.getAttribute("ItemID"), "Return")){
					eExtra.importNode(eLine);
					eInput.getChildElement("OrderLines", true).removeChild(eLine);
					found = true;
				} 
			}
			if(!eInput.getChildElement("OrderLines", true).hasChildNodes()){
				removeAll = true;
				env.setTxnObject("removedAll", "Y");
			}
			if(found){
				env.setTxnObject("serviceItems", dExtra);
			}
		} else {
			YFCDocument dExtra = YFCDocument.createDocument("LineItems");
			YFCElement eExtra = dExtra.getDocumentElement();
			boolean found = false;
			File services = new File("/opt/Sterling/Scripts/service_items.xml");
			
			if(services.exists()){
				YFCDocument dServiceItems = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/service_items.xml");
				if(!YFCCommon.isVoid(dServiceItems)){
					YFCElement eServiceItems = dServiceItems.getDocumentElement();
					for(YFCElement eServiceItem : eServiceItems.getChildren()){
						for(YFCElement eLine : eInput.getChildElement("LineItems", true).getChildren()){
							if(YFCCommon.equals(eLine.getAttribute("ItemID"), eServiceItem.getAttribute("ItemID"))){
								eExtra.importNode(eLine);
								eInput.getChildElement("OrderLines", true).removeChild(eLine);
								found = true;
							} 
						}
					}
				}
			}
			for(YFCElement eLine : eInput.getChildElement("LineItems", true).getChildren()){
				if(YFCCommon.equals(eLine.getAttribute("ItemID"), "Delivery") || YFCCommon.equals(eLine.getAttribute("ItemID"), "Return")){
					eExtra.importNode(eLine);
					eInput.getChildElement("LineItems", true).removeChild(eLine);
					found = true;
				}
			}
			if(!eInput.getChildElement("LineItems", true).hasChildNodes()){
				removeAll = true;
				env.setTxnObject("removedAll", "Y");
			}
			if(found){
				env.setTxnObject("serviceItems", dExtra);
			}
		}
		
		env.setTxnObject("orderInput", eInput);
		if(removeAll){
			eInput.setAttribute("RemovedAll", true);
		}
		return YFCDocument.getDocumentFor(eInput.getString()).getDocument();
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
				File services = new File("/opt/Sterling/Scripts/service_items.xml");
				YFCDocument dExternalServiceItems = null;
				env.setTxnObject("removedAll", "N");
				if(services.exists()){
					dExternalServiceItems = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/service_items.xml");
				}		
				if(eInput.getNodeName().equals("ItemPrice")){
					for(YFCElement eLine : dServiceItems.getDocumentElement().getChildren()){
						boolean found = false;
						if(!YFCCommon.isVoid(dExternalServiceItems)){
							for(YFCElement eExternal : dExternalServiceItems.getDocumentElement().getChildren()){
								if(YFCCommon.equals(eExternal.getAttribute("ItemID"), eLine.getAttribute("ItemID"))){
									found = true;
									eLine.setAttribute("ListPrice", eExternal.getDoubleAttribute("UnitPrice"));
									eLine.setAttribute("LinePrice", eExternal.getDoubleAttribute("UnitPrice") * eLine.getDoubleAttribute("Quantity"));
									eLine.setAttribute("LineAdjustment",0);
									eLine.setAttribute("AbsoluteAdjustment", 0);
									eLine.setAttribute("AdditionalLinePrice", 0);
									eLine.setAttribute("ExtendedPrice", eExternal.getDoubleAttribute("UnitPrice") * eLine.getDoubleAttribute("Quantity"));
									eLine.setAttribute("PercentAdjustment", 0);
									eLine.setAttribute("UnitPrice", eExternal.getDoubleAttribute("UnitPrice"));
									eInput.getChildElement("LineItems", true).importNode(eLine);
								}
							}							
						}
						if(!found){
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
						
					}
				} else {
					for(YFCElement eLine : dServiceItems.getDocumentElement().getChildren()){
						boolean found = false;
						if(!YFCCommon.isVoid(dExternalServiceItems)){
							for(YFCElement eExternal : dExternalServiceItems.getDocumentElement().getChildren()){
								if(YFCCommon.equals(eExternal.getAttribute("ItemID"), eLine.getAttribute("ItemID"))){
									found = true;
									eLine.setAttribute("ListPrice", eExternal.getDoubleAttribute("UnitPrice"));
									eLine.setAttribute("LinePrice", eExternal.getDoubleAttribute("UnitPrice") * eLine.getDoubleAttribute("Quantity"));
									eLine.setAttribute("LineAdjustment",0);
									eLine.setAttribute("LineTotal", eExternal.getDoubleAttribute("UnitPrice") * eLine.getDoubleAttribute("Quantity"));
									eLine.setAttribute("OrderAdjustment", 0);
									eLine.setAttribute("AbsoluteAdjustment", 0);
									eLine.setAttribute("AdditionalLinePrice", 0);
									eLine.setAttribute("AdditionalLinePriceAdjustment", 0);
									eLine.setAttribute("ExtendedPrice", eExternal.getDoubleAttribute("UnitPrice") * eLine.getDoubleAttribute("Quantity"));
									eLine.setAttribute("PercentAdjustment", 0);
									eLine.setAttribute("UnitPrice", eExternal.getDoubleAttribute("UnitPrice"));
									eInput.getChildElement("OrderLines", true).importNode(eLine);
								}
							}							
						}
						if(!found){
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
