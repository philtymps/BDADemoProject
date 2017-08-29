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

public class EXTNCreateOrUpdateOrderFromQuote {
	
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(EXTNCreateOrUpdateOrderFromQuote.class);
	
	public EXTNCreateOrUpdateOrderFromQuote(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private boolean isUpdateAllowed(){
		return properties.containsKey("UpdateOrder");
	}
	
	public Document createOrUpdateOrder(YFSEnvironment env, Document inputDoc){
		String sExistingOrder = null;
		if(isUpdateAllowed() && !YFCCommon.isVoid(inputDoc)){
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			sExistingOrder = getExistingOrderKey(eInput, env);
		}
	
		if(!YFCCommon.isVoid(sExistingOrder)){
			return invokeApi(env, inputDoc, "changeOrder", getCreateOrderOutputTemplate());
		} else {
			return invokeApi(env, inputDoc, "createOrderFromQuote", getCreateOrderOutputTemplate());
			
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
	private String getExistingOrderKey(YFCElement eOrder, YFSEnvironment env){
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		if(!YFCCommon.isVoid(eOrder.getAttribute("OrderHeaderKey"))){
			eInput.setAttribute("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
		} else if(!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode")) && !YFCCommon.isVoid(eOrder.getAttribute("DocumentType")) && !YFCCommon.isVoid(eOrder.getAttribute("OrderNo"))){
			eInput.setAttribute("EnterpriseCode", eOrder.getAttribute("EnterpriseCode"));
			eInput.setAttribute("DocumentType", eOrder.getAttribute("DocumentType"));
			eInput.setAttribute("OrderNo", eOrder.getAttribute("OrderNo"));
		}
			
		Document output = invokeApi(env, dInput.getDocument(), "getOrderDetails", getOrderDetailsTemplate());
		if(!YFCCommon.isVoid(output)){
			YFCDocument dOutput = YFCDocument.getDocumentFor(output);
			YFCElement eOutput = dOutput.getDocumentElement();
			if(!YFCCommon.isVoid(eOutput.getChildElement("CustomAttributes")) && !YFCCommon.isVoid(eOutput.getChildElement("CustomAttributes").getAttribute("OriginalOrderHeaderKey"))){
				return eOutput.getChildElement("CustomAttributes").getAttribute("OriginalOrderHeaderKey");
			}
		}
		return null;
	}
	
	private Document getChangeOrderInput(String sQuoteKey, String sOrderHeaderKey, YFSEnvironment env){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", sOrderHeaderKey);
		eOrder.setAttribute("LinkedSourceKey", sQuoteKey);
		YFCElement eOrderLines = eOrder.getChildElement("OrderLines", true);
		
		return dOrder.getDocument();
	}
	
	private YFCElement getExistingOrder(String sOrderHeaderKey, YFSEnvironment env){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", sOrderHeaderKey);
		Document output = invokeApi(env, dOrder.getDocument(), "getCompleteOrderDetails", getOrderDetailsTemplate());
		if(!YFCCommon.isVoid(output)){
			
		}
		return eOrder;
	}
	private static Document getCreateOrderOutputTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOutput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		return dOutput.getDocument();
	}
	private static Document getOrderDetailsTemplate(){
		YFCDocument dTemp = YFCDocument.createDocument("Order");
		YFCElement eOrder = dTemp.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.getChildElement("CustomAttributes", true).setAttribute("OriginalOrderHeaderKey", "");
		return dTemp.getDocument();
	}
	
}
