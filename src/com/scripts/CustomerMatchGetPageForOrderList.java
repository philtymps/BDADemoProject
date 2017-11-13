package com.scripts;

import org.w3c.dom.Document;

import com.ue.DemoCreateCustomerForOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class CustomerMatchGetPageForOrderList {

	public Document getPage(YFSEnvironment env, Document inputDoc){
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eApi = dInput.getDocumentElement().getChildElement("API");
		if (!YFCCommon.isVoid(eApi)){
			YFCElement eInput = eApi.getChildElement("Input");
			if (!YFCCommon.isVoid(eInput)){
				YFCElement eOrder = eInput.getChildElement("Order");
				//if (!YFCCommon.isVoid(eOrder.getChildElement("ComplexQuery"))){
				//	return inputDoc;
				//}
				YFCElement eContact = getCustomerContact(env, eOrder);
				if (!YFCCommon.isVoid(eContact)){
					YFCElement eComplexQuery = eOrder.getChildElement("ComplexQuery", true);
					YFCElement eOr = eComplexQuery.getChildElement("Or", true);
					YFCElement eExp = eOr.createChild("Exp");
					eExp.setAttribute("Name", "BuyerUserId");
					eExp.setAttribute("Value", eOrder.getAttribute("BuyerUserId"));
					if (!YFCCommon.isVoid(eContact.getChildElement("Customer").getAttribute("CustomerID"))){
						eExp = eOr.createChild("Exp");
						eExp.setAttribute("Name", "BillToID");
						eExp.setAttribute("Value", eContact.getChildElement("Customer").getAttribute("CustomerID"));
					}
					if (!YFCCommon.isVoid(eContact.getAttribute("EmailID"))){
						eExp = eOr.createChild("Exp");
						eExp.setAttribute("Name", "CustomerEMailID");
						eExp.setAttribute("Value", eContact.getAttribute("EmailID"));
					}
					if(!YFCCommon.isVoid(eOrder.getYDateAttribute("BuyerUserId"))){
						eOrder.removeAttribute("BuyerUserId");
					}
					if (!YFCCommon.isVoid(eOrder.getAttribute("BuyerUserIdQryType"))){
						eOrder.removeAttribute("BuyerUserIdQryType");
					}
				}
			}
		}
		return inputDoc;
	}
	
	public static YFCElement getCustomerContact(YFSEnvironment env, YFCElement eOrder){
		YFCDocument dGetCustomerListInput = YFCDocument.createDocument("CustomerContact");
		YFCElement eContact = dGetCustomerListInput.getDocumentElement();
		
		if(!YFCCommon.isVoid(eOrder.getAttribute("BuyerUserId"))){
			eContact.setAttribute("UserID", eOrder.getAttribute("BuyerUserId"));
		} else if(!YFCCommon.isVoid(eOrder.getChildElement("ComplexQuery"))){
			String sContact = eOrder.getChildElement("ComplexQuery", true).getChildElement("Or", true).getChildElement("Exp", true).getAttribute("Value");
			if(!YFCCommon.isVoid(sContact)){
				eContact.setAttribute("UserID", sContact);	
			}
		}
		if(!YFCCommon.isVoid(eContact.getAttribute("UserID"))){
			YFCElement eCustInput = eContact.getChildElement("Customer",true);
			eCustInput.setAttribute("CallingOrganizationCode", eOrder.getAttribute("EnterpriseCode"));
			
			try {
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				env.setApiTemplate("getCustomerContactList", DemoCreateCustomerForOrder.getCustomerContactListTemplate());
				Document l_OutputXml = localApi.invoke(env, "getCustomerContactList", dGetCustomerListInput.getDocument());
				YFCElement output = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				return output.getFirstChildElement();
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
		}
		
		return null;
	}
}
