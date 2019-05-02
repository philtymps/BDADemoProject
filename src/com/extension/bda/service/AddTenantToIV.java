package com.extension.bda.service;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class AddTenantToIV extends BDAServiceApi implements IBDAService {

	public Document invoke(YFSEnvironment env, Document inputDoc) {
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eInput = dInput.getDocumentElement();
		
		String sUrl = "https://";
		sUrl += getPropertyValue(env, "bda.iv.integration.url");
		sUrl += "/inventory/";
		sUrl += getPropertyValue(env, "bda.iv.integration.tenant_id");
		sUrl += this.getProperty("ApiExtn");
		
		eInput.setAttribute("URL", sUrl);
		eInput.setAttribute("HTTPMethod", (String) this.getProperty("HTTPMethod")); 
		return inputDoc;
	}

	private String getPropertyValue(YFSEnvironment env, String input) {
		YFCDocument dInput = YFCDocument.createDocument("Property");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("PropertyName", input);
		Document response = this.callApi(env, dInput.getDocument(), null, "getProperty");
		return response.getDocumentElement().getAttribute("PropertyValue");
	}
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "addURLToIvService";
	}

}
