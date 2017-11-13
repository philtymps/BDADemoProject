package com.extension.silverpop;

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
	
	public String getRestApi(){
		return getTransactURL() + "rest/channels/sms/externalconsentsends";
	}

	public static void main(String[] args){
	    SilverpopSMS t = new SilverpopSMS();	    
		t.sendSMSMessage(null, null); 
	}
	
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "silverpopSMS";
	}
	
	public Document invoke(YFSEnvironment env, Document dInput){
		return sendSMSMessage(env, dInput);
	}
	
	public Document sendSMSMessage(YFSEnvironment env, Document dInput){
		String sMessage = getMessage();
		YFCElement eInput;
		if (!YFCCommon.isVoid(dInput)){
			eInput = YFCDocument.getDocumentFor(dInput).getDocumentElement();
			sMessage.replaceAll("%OrderNo%", eInput.getAttribute("OrderNo"));
			sMessage.replaceAll("%FirstName%", eInput.getAttribute("CustomerFirstName"));
			sMessage.replaceAll("%LastName%", eInput.getAttribute("CustomerLastName"));
		} else {
			eInput = YFCDocument.createDocument("Input").getDocumentElement();
		}
		
		eInput.setAttribute("Message", sMessage);
		eInput.setAttribute("PhoneNumbers",  getPhoneNumber());
		callRequest(eInput);
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
	
	public JSONObject getRequest(YFCElement eInput){
		String sMessage = eInput.getAttribute("Message");
		String sPhoneNumbers = eInput.getAttribute("PhoneNumbers");
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
