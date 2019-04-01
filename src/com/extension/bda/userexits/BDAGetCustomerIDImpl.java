package com.extension.bda.userexits;

import java.rmi.RemoteException;
import java.util.Map;

import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSGetCustomerIDUE;

public class BDAGetCustomerIDImpl implements YFSGetCustomerIDUE {

	@Override
	public String getCustomerID(YFSEnvironment env, Document eInput) throws YFSUserExitException {
		String highestID = getNewCustomerID(env, eInput.getDocumentElement().getAttribute("OrganizationCode"));
		if (!YFCCommon.isVoid(highestID)){
			System.out.println("CustomerID: " + highestID);
			return highestID;
		}
		return null;
	}


	/* This gets the last order no for the defined order prefixes */
	private String getNewCustomerID(YFSEnvironment env, String sEnterpriseCode) {

		YFCElement eCustomer = YFCDocument.createDocument("Customer").getDocumentElement();
		eCustomer.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		eCustomer.setAttribute("CustomerID", "CUST_");
		eCustomer.setAttribute("CustomerIDQryType", "FLIKE");
		eCustomer.setAttribute("MaximumRecords", "1");
		YFCElement eAttr = eCustomer.createChild("OrderBy").createChild("Attribute");
		eAttr.setAttribute("Name", "CustomerID");
		eAttr.setAttribute("Desc", "Y");
		String strCustomerID =  "CUST_" + "1000000";
		YFCElement eTemplate = YFCDocument.createDocument("CustomerList").getDocumentElement();
		YFCElement eOTemplate = eTemplate.createChild("Customer");
		eOTemplate.setAttribute("CustomerID", "");
		eOTemplate.setAttribute("OrganizationCode", "");
		
		Document docCustomerList = callApi(env, eCustomer.getOwnerDocument().getDocument(), eTemplate.getOwnerDocument().getDocument(), "getCustomerList");

		if (!YFCCommon.isVoid(docCustomerList)){
			YFCElement docOrder = YFCDocument.getDocumentFor(docCustomerList).getDocumentElement().getChildElement("Customer");
			if (!YFCCommon.isVoid(docOrder) && !YFCCommon.isVoid(docOrder.getAttribute("CustomerID"))){
				String customerID = docOrder.getAttribute("CustomerID");
				String number = customerID.substring(customerID.length() - 7, customerID.length());
				int i = Integer.parseInt(number) + 1;
				strCustomerID = "CUST_" + i;
			}
		}
		return strCustomerID;
	}
	
	protected Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName){
		if(!YFCCommon.isVoid(env)) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.invoke(env, sApiName, inDoc);
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
			if(!YFCCommon.isVoid(dOrderOutput)){
				return dOrderOutput;
			}
			return null;
		} else {
			return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, "http://oms.omfulfillment.com").getDocument();
		}
		
	
	}
}
