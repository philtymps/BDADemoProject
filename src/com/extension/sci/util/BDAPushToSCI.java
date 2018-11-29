package com.extension.sci.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSEnvironment;

public abstract class BDAPushToSCI implements IBDAService {

	public static final String IBM_CLIENT_ID = "X-IBM-Client-Id";
	public static final String IBM_CLIENT_SECRET = "X-IBM-Client-Secret";
	public static final String IBM_USER_SECRET = "X-IBM-User-Secret";
	public static final String SCI_BULK_SALES_ORDER = "https://api.ibm.com/scinsights/run/api/salesorders/bulk";
	public static final String SCI_BULK_SALES_SHIPMENT = "https://api.ibm.com/scinsights/run/api/salesshipments/bulk";
	public static final String SCI_BULK_PURCHASE_ORDER = "https://api.ibm.com/scinsights/run/api/supplyorders/bulk";
	public static final String SCI_BULK_PURCHASE_SHIPMENT = "https://api.ibm.com/scinsights/run/api/supplyshipments/bulk";
	public static final String SCI_BULK_LOCATIONS = "https://api.ibm.com/scinsights/run/api/locations/bulk";
	
 	private Properties properties = null;
	
	public BDAPushToSCI(){
		properties = new Properties();
	}	
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public void setProperty(String name, String value){
		if(!YFCCommon.isVoid(properties)){
			this.properties.put(name, value);
		}
	}
	
	public String getProperty(String sProp){
		return (String) this.properties.get(sProp);
	}
	
	public abstract Document invoke(YFSEnvironment env, YFCElement eInput);
	
	public Document invoke(YFSEnvironment env, Document input){
		return invoke(env, YFCDocument.getDocumentFor(input).getDocumentElement());
	}
	private String getClientID(){
		if(!YFCCommon.isVoid(getProperty("ClientID"))){
			return getProperty("ClientID");
		}
		// Primary Tenant
		// return "7cbf0b5c-1405-40dc-86b5-50e61dc8f6ef";
		
		// Development Demo Tenant
		return "0676e2bc-436c-4ba4-9d75-a230a78ecbe3";
	}
	
	private String getClientSecret(){
		if(!YFCCommon.isVoid(getProperty("ClientSecret"))){
			return getProperty("ClientSecret");
		}
		// Primary Tenant
		// return "X7bV0lC4bA4wU5dM7rN1pT2cE8jS2qG5yG0rN7wK6qV5rD0yJ6";
		
		// Development Demo Tenant
		return "B4fT4pE5kM7hH0hP1yN2nA7yN3vF3fU1fH4fD2eD1dC5yK1yO5";
	}
	
	private String getUserSecret(){
		if(!YFCCommon.isVoid(getProperty("UserSecret"))){
			return getProperty("UserSecret");
		}
		// Primary Tenant
		// return "ef786ec3-6ce3-40e7-80cd-db258bf3d4b8";
		
		// Development Demo Tenant
		return "80a9750a-6374-489a-8396-c9f2ed24c634";
	}
	
	public void callRequest(String postURL, JSONObject input){
		try {
			String sOutput = input.toString();
			System.out.println("SCI Request to: " + postURL);
			System.out.println("SCI Request: " + sOutput);
			URL url = new URL(postURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
			connection.setRequestProperty(IBM_CLIENT_ID, getClientID());
			connection.setRequestProperty(IBM_CLIENT_SECRET, getClientSecret());
			connection.setRequestProperty(IBM_USER_SECRET, getUserSecret());
			
			// Write data
			OutputStream os = connection.getOutputStream();
			os.write(sOutput.getBytes());
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String res;
			while ((res = in.readLine()) != null) {
				sb.append(res);
			}
			in.close();
			System.out.println("SCI Response: " + sb);
		
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
