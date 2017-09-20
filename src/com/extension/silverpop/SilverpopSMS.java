package com.extension.silverpop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class SilverpopSMS extends SilverpopRest {

	public SilverpopSMS(){
		super();
	}
	

	private String getSMSApi(){
		return getTransactURL() + "rest/channels/sms/externalconsentsends";
	}

	public static void main(String[] args){
	    SilverpopSMS t = new SilverpopSMS();
		t.callRequest(t.getMessage(), t.getPhoneNumber()); 
	}
	
	public Document sendSMSMessage(YFSEnvironment env, Document dInput){
		String sMessage = getMessage();
		if (!YFCCommon.isVoid(dInput)){
			YFCElement eInput = YFCDocument.getDocumentFor(dInput).getDocumentElement();
			sMessage.replaceAll("%OrderNo%", eInput.getAttribute("OrderNo"));
			sMessage.replaceAll("%FirstName%", eInput.getAttribute("CustomerFirstName"));
			sMessage.replaceAll("%LastName%", eInput.getAttribute("CustomerLastName"));
		}
		callRequest(sMessage, getPhoneNumber());
		return dInput;
	}
	
	
	public String getPhoneNumber(){
		if(!YFCCommon.isVoid(getProperty("PhoneNumber"))){
			return (String) getProperty("PhoneNumber");
		} 
		return "16165465215";
		//return "16177858344";
	}
	
	public String getMessage(){
		if(!YFCCommon.isVoid(getProperty("Message"))){
			return (String) getProperty("Message");
		} 
		return "You have order(s) you need to pick!";
	}
	
	public void callRequest(String sMessage, String sPhoneNumbers){
		try {
			String sOutput = getRequest(sMessage, sPhoneNumbers).toString();
			URL url = new URL(getSMSApi());
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
	        connection.setRequestProperty("Authorization", "Bearer " +  getTokenFromResponse());
	        
	       // connection.setRequestProperty("Authorization", value);
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
			System.out.println(sb);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private JSONObject getRequest(String sMessage, String sPhoneNumbers){
		JSONObject root = new JSONObject();
		root.put("content", sMessage);
		root.put("channelQualifier", "131918");
		
		JSONArray contacts = new JSONArray();
		
		String ph = sPhoneNumbers;
		if(ph.indexOf(",") > -1){
			String[] ps = ph.split(",");
			for(String p : ps){
				JSONObject contact = new JSONObject();
				contact.put("phoneNumber", p.trim());
				contacts.put(contact);
			}			
		} else {
			JSONObject contact = new JSONObject();
			contact.put("phoneNumber", ph);
			contacts.put(contact);
		}
		
		
		root.put("contacts", contacts);
		return root;
	}
    
  
 
 
    
   
}
