package com.extension.silverpop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class SilverpopSMS extends SilverpopRest {
	
	private static YFCLogCategory logger = YFCLogCategory.instance(SilverpopSMS.class); 

	public SilverpopSMS(){
		super();
	}
	
	public String getRestApi(){
		logger.debug("Inside getRestApi");
		logger.debug("getTransactURL is " + getTransactURL());
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
		logger.debug("Inside invoke");
		logger.debug("dInput is " + YFCDocument.getDocumentFor(dInput).getString());
		return sendSMSMessage(env, dInput);
	}
	
	public Document sendSMSMessage(YFSEnvironment env, Document dInput){
		logger.debug("Inside sendSMSMessage");
		logger.debug("dInput is " + YFCDocument.getDocumentFor(dInput).getString());
		String sMessage = getMessage();
		logger.debug("sMessage is " + sMessage);
		YFCElement eInput;
		if (!YFCCommon.isVoid(dInput)){
			logger.debug("Inside if condition for !YFCCommon.isVoid(dInput)");
			eInput = YFCDocument.getDocumentFor(dInput).getDocumentElement();
			logger.debug("OrderNo is " + eInput.getAttribute("OrderNo") + " FirstName is " + eInput.getAttribute("CustomerFirstName") + " LastName is " + eInput.getAttribute("CustomerLastName"));
			sMessage = sMessage.replaceAll("OrderNo", eInput.getAttribute("OrderNo"));
			sMessage = sMessage.replaceAll("FirstName", eInput.getAttribute("CustomerFirstName"));
			sMessage = sMessage.replaceAll("LastName", eInput.getAttribute("CustomerLastName"));
			logger.debug("sMessage after replaceAll is " + sMessage);
		} else {
			logger.debug("Inside else condition for !YFCCommon.isVoid(dInput)");			
			eInput = YFCDocument.createDocument("Input").getDocumentElement();
		}
		
		eInput.setAttribute("Message", sMessage);
		eInput.setAttribute("PhoneNumbers",  getPhoneNumber());
		logger.debug("eInput is " + eInput);
		callRequest(eInput);
		return dInput;
	}
	
	
	public String getPhoneNumber(){
		logger.debug("Inside getPhoneNumber");
		if(!YFCCommon.isVoid(getProperty("PhoneNumber"))){
			logger.debug("PhoneNumber is " + getProperty("PhoneNumber"));			
			return (String) getProperty("PhoneNumber");
		} 
		return "16165465215";
		//return "16177858344";
	}
	
	public String getMessage(){
		logger.debug("Inside getMessage");
		if(!YFCCommon.isVoid(getProperty("Message"))){
			logger.debug("Message is " + getProperty("Message"));			
			return (String) getProperty("Message");
		} 
		return "You have order(s) you need to pick!";
	}
	
	public JSONObject getRequest(YFCElement eInput){
		logger.debug("Inside getRequest");
		String sMessage = eInput.getAttribute("Message");
		String sPhoneNumbers = eInput.getAttribute("PhoneNumbers");
		logger.debug("sMessage is " + sMessage + " and sPhoneNumbers is " + sPhoneNumbers);
		JSONObject root = new JSONObject();
		root.put("content", sMessage);
		root.put("channelQualifier", "131918");
		
		JSONArray contacts = new JSONArray();
		
		String ph = sPhoneNumbers;
		logger.debug("Before if condition with ph " + ph);
		if(ph.indexOf(",") > -1){
			logger.debug("Inside if condition");
			String[] ps = ph.split(",");
			for(String p : ps){
				JSONObject contact = new JSONObject();
				contact.put("phoneNumber", p.trim());
				logger.debug("Before contact.put with phone no " + p.trim());
				contacts.put(contact);
			}			
		} else {
			logger.debug("Inside else condition");
			JSONObject contact = new JSONObject();
			contact.put("phoneNumber", ph);
			contacts.put(contact);
		}		
		
		root.put("contacts", contacts);
		return root;
	}
   
}
