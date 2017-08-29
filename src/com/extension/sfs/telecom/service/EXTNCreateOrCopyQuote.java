package com.extension.sfs.telecom.service;

import java.rmi.RemoteException;
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
import com.yantra.yfs.japi.YFSException;

public class EXTNCreateOrCopyQuote {

	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(EXTNCreateOrCopyQuote.class);
	
	
	public EXTNCreateOrCopyQuote(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private boolean isCopyOrderAllowed(){
		return properties.containsKey("CopyQuote");
	}
	
	public Document createOrCopyQuote(YFSEnvironment env, Document inputDoc){
		String sExistingQuote = null;
		if(isCopyOrderAllowed() && !YFCCommon.isVoid(inputDoc)){
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			if(!YFCCommon.isVoid(eInput.getAttribute("BillToID")) && !YFCCommon.isVoid(eInput.getAttribute("EnterpriseCode"))){
				sExistingQuote = getExistingQuote(eInput.getAttribute("BillToID"), eInput.getAttribute("EnterpriseCode"), env);
				eInput.setAttribute("CopyFromOrderHeaderKey", sExistingQuote);
				
				String sOriginalOrderHeaderKey = getOrderFromQuote(sExistingQuote, eInput.getAttribute("EnterpriseCode"), env);
				if(!YFCCommon.isVoid(sOriginalOrderHeaderKey)){
					eInput.createChild("CustomAttributes").setAttribute("OriginalOrderHeaderKey", sOriginalOrderHeaderKey);
				}
			}
		}
	
		if(!YFCCommon.isVoid(sExistingQuote)){
			return invokeApi(env, inputDoc, "copyOrder", getCreateOrderOutputTemplate());
		} else {
			return invokeApi(env, inputDoc, "createOrder", getCreateOrderOutputTemplate());
			
		}
	}
	
	
	private Document invokeApi(YFSEnvironment env, Document inDoc, String sAPIName, Document template){
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate(sAPIName, template);
			return localApi.invoke(env, sAPIName, inDoc);
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
		return null;
	}
	
	private String getOrderFromQuote(String sQuoteKey, String sEnterpriseCode, YFSEnvironment env){
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("LinkedSourceKey", sQuoteKey);
		eInput.setAttribute("DocumentType", "0001");
		eInput.setAttribute("EnterpriseCode", sEnterpriseCode);
		YFCElement eAttr = eInput.createChild("OrderBy").createChild("Attribute");
		eAttr.setAttribute("Name", "OrderDate");
		eAttr.setAttribute("Desc", "Y");
		Document output = invokeApi(env, dInput.getDocument(), "getOrderList", getOrderListTemplate());
		if(!YFCCommon.isVoid(output)){
			YFCDocument dOutput = YFCDocument.getDocumentFor(output);
			YFCElement eOutput = dOutput.getDocumentElement();
			for(YFCElement eOrder : eOutput.getChildren()){
				return eOrder.getAttribute("OrderHeaderKey");
			}
		}
		return null;
	}
	private String getExistingQuote(String sBillToID, String sEnterpriseCode, YFSEnvironment env){
		if(!YFCCommon.isVoid(sBillToID) && !YFCCommon.isVoid(sEnterpriseCode)){
			YFCDocument dInput = YFCDocument.createDocument("Order");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("BillToID", sBillToID);
			eInput.setAttribute("EnterpriseCode", sEnterpriseCode);
			eInput.setAttribute("DocumentType", "0015");
			eInput.setAttribute("Status", "1500");
			eInput.setAttribute("StatusQryType", "GE");
			YFCElement eAttr = eInput.createChild("OrderBy").createChild("Attribute");
			eAttr.setAttribute("Name", "OrderDate");
			eAttr.setAttribute("Desc", "Y");
			Document output = invokeApi(env, dInput.getDocument(), "getOrderList", getOrderListTemplate());
			if(!YFCCommon.isVoid(output)){
				YFCDocument dOutput = YFCDocument.getDocumentFor(output);
				YFCElement eOutput = dOutput.getDocumentElement();
				for(YFCElement eOrder : eOutput.getChildren()){
					if(eOrder.getAttribute("MaxOrderStatus").contains("1500")){
						return eOrder.getAttribute("OrderHeaderKey");
					}
				}
			}
		}
		return null;
	}
	
	private static Document getCreateOrderOutputTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOutput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("BillToID", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("OrderName", "");
		eOrder.setAttribute("ExpirationDate", "");
		eOrder.setAttribute("CustomerContactID", "");
		YFCElement eInstruction = eOrder.createChild("Instructions").createChild("Instruction");
		eInstruction.setAttribute("InstructionDetailKey", "");
		eInstruction.setAttribute("InstructionText", "");
		eInstruction.setAttribute("InstructionType", "");
		YFCElement eUserNote = eOrder.createChild("Notes").createChild("Note").createChild("User");
		eUserNote.setAttribute("Username","");
		YFCElement eShipTo = eOrder.createChild("PersonInfoShipTo");
		eShipTo.setAttribute("AddressLine1", "");
		eShipTo.setAttribute("AddressLine2", "");
		eShipTo.setAttribute("City", "");
		eShipTo.setAttribute("State", "");
		eShipTo.setAttribute("Country", "");
		eShipTo.setAttribute("ZipCode", "");
		eShipTo.setAttribute("FirstName", "");
		eShipTo.setAttribute("Company", "");
		eShipTo.setAttribute("LastName", "");
		eShipTo.setAttribute("EMailID", "");
		eShipTo.setAttribute("MiddleName", "");
		eShipTo.setAttribute("IsCommercialAddress", "");
		eShipTo.setAttribute("Title", "");
		eShipTo.setAttribute("DayPhone", "");
		YFCElement eBillTo = eOrder.createChild("PersonInfoBillTo");
		eBillTo.setAttribute("AddressLine1", "");
		eBillTo.setAttribute("AddressLine2", "");
		eBillTo.setAttribute("City", "");
		eBillTo.setAttribute("State", "");
		eBillTo.setAttribute("Country", "");
		eBillTo.setAttribute("ZipCode", "");
		eBillTo.setAttribute("FirstName", "");
		eBillTo.setAttribute("Company", "");
		eBillTo.setAttribute("LastName", "");
		eBillTo.setAttribute("EMailID", "");
		eBillTo.setAttribute("MiddleName", "");
		eBillTo.setAttribute("IsCommercialAddress", "");
		eBillTo.setAttribute("Title", "");
		eBillTo.setAttribute("DayPhone", "");
		YFCElement eOpportunity = eOrder.createChild("Opportunity");
		eOpportunity.setAttribute("OpportunityID", "");
		eOpportunity.setAttribute("OpportunityKey", "");
		eOpportunity.setAttribute("OpportunityName", "");
		eOpportunity.setAttribute("OwnerUserID", "");
		eOpportunity.setAttribute("CoOwnerUserID", "");
		YFCElement eOwnerUser = eOpportunity.createChild("OwnerUser");
		eOwnerUser.setAttribute("Username", "");
		YFCElement eCoOwnerUser = eOpportunity.createChild("CoOwnerUser");
		eCoOwnerUser.setAttribute("Username", "");
		return dOutput.getDocument();
	}
	private static Document getOrderListTemplate(){
		YFCDocument dTemp = YFCDocument.createDocument("OrderList");
		YFCElement eTemp = dTemp.getDocumentElement();
		YFCElement eOrder = eTemp.createChild("Order");
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("MinOrderStatus", "");
		eOrder.setAttribute("MaxOrderStatus", "");
		return dTemp.getDocument();
	}
}
