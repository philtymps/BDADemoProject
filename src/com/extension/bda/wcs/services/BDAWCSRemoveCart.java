package com.extension.bda.wcs.services;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.expose.BDARestCall;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAWCSRemoveCart extends BDARestCall implements IBDAService {
	
	public static void main(String[] args) {
		BDARestCall test = new BDARestCall();
		Properties p = new Properties();
		p.setProperty("URL", "http://localhost:3000/url");
		p.setProperty("Method", "GET");
		try {
			test.setProperties(p);
			Document d = test.invoke(null, null);
			System.out.println(YFCDocument.getDocumentFor(d));
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public void setHeaders (HttpURLConnection connection) {
		
	}

	public Document invoke(YFSEnvironment env, Document dInput) {
		String sURL = this.getUrl();
		if(YFCCommon.isVoid(sURL)) { 
			sURL = "https://wc8dev.ibm.com/wcs/resources/store/10201/cart";
		}
		boolean invoke = false;
		if (!YFCCommon.isVoid(sURL)) {
			if(!YFCCommon.isVoid(dInput)) {
				YFCDocument docInput = YFCDocument.getDocumentFor(dInput);
				YFCElement eInput = docInput.getDocumentElement();
				
				if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("CartId"))) {
					sURL += "/" + eInput.getAttribute("CartId") + "/csrcancel_order";
					invoke = true;
				} else if (!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("OrderNo"))) {
					sURL += "/" + eInput.getAttribute("OrderNo").replace("WC_", "") + "/csrcancel_order";
					invoke = true;
				}
				
			}
			
			if(invoke) {
				
				try {				

					System.out.println("Full URL: " + sURL);
					URL url = new URL(sURL);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setRequestMethod(this.getMethod());
					connection.setRequestProperty("Content-Type", "application/json");
					connection.setRequestProperty("Authorization", "Basic d2NzYWRtaW46c2tpaW5nNzQwVElNRV8=");
					setHeaders(connection);
					connection.setConnectTimeout(5000);
					updateConnection(env, connection);
					int responseCode = connection.getResponseCode();
					System.out.println("Response Code: " + responseCode);
					if(responseCode < 300) {
						JSONObject response = new JSONObject(new BufferedInputStream(connection.getInputStream()));
						System.out.println(response);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			
		}
		return dInput;
	}
	
}
