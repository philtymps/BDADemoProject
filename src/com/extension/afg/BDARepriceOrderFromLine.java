package com.extension.afg;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDARepriceOrderFromLine implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "repriceOrderFromLine";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		if(!YFCCommon.isVoid(input)){
			YFCElement eInput = YFCDocument.getDocumentFor(input).getDocumentElement();
			String sOrderHeaderKey;
			if(!YFCCommon.isVoid(eInput.getAttribute("OrderHeaderKey"))){
				sOrderHeaderKey = eInput.getAttribute("OrderHeaderKey");
			} else {
				sOrderHeaderKey = getOrderHeaderKey(env, eInput);
			}
			if(!YFCCommon.isVoid(sOrderHeaderKey)){
				YFCDocument dOrder = YFCDocument.createDocument("Order");
				dOrder.getDocumentElement().setAttribute("OrderHeaderKey", sOrderHeaderKey);
				dOrder.getDocumentElement().setAttribute("ForceReprice", "Y");
				try {
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					return localApi.invoke(env, "repriceOrder", dOrder.getDocument());
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	
	private String getOrderHeaderKeyForLine(YFSEnvironment env, String sOrderLineKey){
		if(!YFCCommon.isVoid(sOrderLineKey)){
			try {
				YFCDocument dInput = YFCDocument.createDocument("OrderLine");
				dInput.getDocumentElement().setAttribute("OrderLineKey", sOrderLineKey);
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				YFCDocument dTemplate = YFCDocument.createDocument("OrderLine");
				dTemplate.getDocumentElement().setAttribute("OrderHeaderKey", "");
				env.setApiTemplate("getOrderLineDetails", dTemplate.getDocument());
				Document l_OutputXml = localApi.invoke(env, "getOrderLineDetails", dInput.getDocument());
				YFCElement eResponse = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				if(!YFCCommon.isVoid(eResponse)){
					return eResponse.getAttribute("OrderHeaderKey");
				}
			} catch(Exception yex) {
				yex.printStackTrace();
	        } 
		}
		return null;
	}
	
	private String getOrderHeaderKey(YFSEnvironment env, YFCElement eItem){
		if(!YFCCommon.isVoid(eItem.getAttribute("OrderHeaderKey"))){
			return eItem.getAttribute("OrderHeaderKey");
		} else if(!YFCCommon.isVoid(eItem.getAttribute("OrderLineKey"))){
			return getOrderHeaderKeyForLine(env, eItem.getAttribute("OrderLineKey"));
		} else {
			for(YFCElement eChild : eItem.getChildren()){
				String response = getOrderHeaderKey(env, eChild);
				if(!YFCCommon.isVoid(response)){
					return response;
				}
			}
		}
		return null;
	}
}
