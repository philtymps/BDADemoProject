package com.extension.bda.service;

import java.util.HashMap;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.bda.utils.BDAXmlUtil;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.ibm.sterling.afc.jsonutil.PLTJSONUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class AddTenantToIV extends BDAServiceApi implements IBDAService {

	private static final YFCLogCategory LOG = YFCLogCategory.instance(AddTenantToIV.class.getName());
	private static HashMap<String, String> _map;
	
	public static synchronized String getCachedValue(String sKey) {
		if(!YFCCommon.isVoid(_map)) {
			return _map.get(sKey);
		}
		return null;
	}
	
	public static synchronized void clearMap() {
		_map = new HashMap<String, String>();
	}
	
	public static synchronized void addToCache(String sKey, String sValue) {
		if(YFCCommon.isVoid(_map)) {
			_map = new HashMap<String, String>();
		}
		_map.put(sKey,  sValue);
	}
	
	public Document invoke(YFSEnvironment env, Document inputDoc) {
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		
		LOG.debug("Input To AddTenantToIV");
		YFCElement eInput = dInput.getDocumentElement();
		String sUrl = "";
		if(!YFCCommon.isVoid(eInput.getAttribute("URL"))) {
			String existingUrl = eInput.getAttribute("URL");
			String[] parts = existingUrl.split("/");
			for(int i = 0; i < parts.length; i++) {
				if((i - 1) > 0 && parts[i - 1].equals("inventory")) {
					sUrl += (getPropertyValue(env, "bda.iv.integration.tenant_id") + "/");
				} else if(parts[i].indexOf("ibm.com") > -1) {
					sUrl += getPropertyValue(env, "bda.iv.integration.url") + "/";
				} else if(i+1 < parts.length) {
					sUrl += (parts[i] + "/");
				} else {
					sUrl += parts[i];
				}
			}
		} else {
			sUrl = "https://";
			sUrl += getPropertyValue(env, "bda.iv.integration.url");
			sUrl += "/inventory/";
			sUrl += getPropertyValue(env, "bda.iv.integration.tenant_id");
			sUrl += this.getProperty("ApiExtn");
		}
		eInput.setAttribute("URL", sUrl);
		if(!YFCCommon.isVoid(this.getProperty("HTTPMethod"))) {
			eInput.setAttribute("HTTPMethod", (String) this.getProperty("HTTPMethod")); 			
		}
		
		if(
			!YFCCommon.isVoid(eInput.getChildElement("Input")) &&
			YFCCommon.isVoid(eInput.getChildElement("Input").getNodeValue()) &&
			!YFCCommon.isVoid(eInput.getChildElement("Input").getFirstChildElement())) {
			System.out.println(eInput.getChildElement("Input"));
			 try {
				JSONObject Input = PLTJSONUtils.getJSONObjectFromXML(BDAXmlUtil.getChildElement(inputDoc.getDocumentElement(), "Input"), null, null);
				System.out.println("Input JSON");
				System.out.println(Input.toString());
				eInput.removeChild(eInput.getChildElement("Input"));
				if(Input.containsKey("Input")) {
					Input = Input.getJSONObject("Input");
				}
				if(Input.containsKey("lines") && !(Input.get("lines") instanceof JSONArray)) {
					System.out.println("Has lines not JSONArray");
					JSONArray t = new JSONArray();
					t.add(Input.get("lines"));
					Input.put("lines", t);
				}
				if(Input.containsKey("supplies") && !(Input.get("supplies") instanceof JSONArray)) {
					System.out.println("Has supplies not JSONArray");
					JSONArray t = new JSONArray();
					t.add(Input.get("supplies"));
					Input.put("supplies", t);
				}
				if(Input.containsKey("demands") && !(Input.get("demands") instanceof JSONArray)) {
					System.out.println("Has demands not JSONArray");
					JSONArray t = new JSONArray();
					t.add(Input.get("demands"));
					Input.put("demands", t);
				}
				System.out.println(Input.toString());
				
				eInput.getChildElement("Input", true).setNodeValue(Input.toString());
			
			 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		System.out.println("URL::" + sUrl);
		return inputDoc;
	}

	private String getPropertyValue(YFSEnvironment env, String input) {
		if(!YFCCommon.isVoid(getCachedValue(input))) {
			return getCachedValue(input);
		}
		YFCDocument dInput = YFCDocument.createDocument("Property");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("PropertyName", input);
		Document response = this.callApi(env, dInput.getDocument(), null, "getProperty");
		LOG.debug("Property::" + input + "::Value::" + response.getDocumentElement().getAttribute("PropertyValue"));
		AddTenantToIV.addToCache(input, response.getDocumentElement().getAttribute("PropertyValue"));
		return response.getDocumentElement().getAttribute("PropertyValue");
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "addURLToIvService";
	}

}
