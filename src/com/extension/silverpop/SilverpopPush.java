package com.extension.silverpop;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yantra.yfc.dom.YFCElement;

public class SilverpopPush extends SilverpopRest {
	
	public SilverpopPush(){
		super();
	}
	
	public String getRestApi(){
		return getTransactURL() + "rest/channels/push/sends";
	}
	
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "silverpopPush";
	}
	
	public static void main(String[] args){
		SilverpopPush t = new SilverpopPush();
		t.invoke(null, null); 
	}
	
	public JSONObject getRequest(YFCElement eInput){
		
		JSONObject root = new JSONObject();
		
		JSONArray qualifiers = new JSONArray();
		qualifiers.put("apobfui4yF");
		root.put("channelQualifiers", qualifiers);
		
		JSONObject content = new JSONObject();
		//content.put("contentId", null);
		
		JSONObject simple = new JSONObject();
		
		JSONObject apns = new JSONObject();
		JSONObject aps = new JSONObject();
		aps.put("content-available", 1);
		apns.put("aps", aps);
		JSONObject dynamicaps = new JSONObject();
		dynamicaps.put("alert", "You have an order to pick!");
		dynamicaps.put("badge", 1);
		apns.put("dynamic-aps", dynamicaps);
		
		simple.put("apns", apns);
		content.put("simple", simple);
		
		root.put("content", content);
		JSONArray contacts = new JSONArray();
		JSONObject channel = new JSONObject();
		channel.put("appKey", "apobfui4yF");
		channel.put("userId", "VRR7QYiAr0dml7Yw");
		channel.put("channelId", "WglCCrTG");
		JSONObject t = new JSONObject();
		t.put("channel", channel);
		contacts.put(t);
		root.put("contacts", contacts);
		root.put("campaignName", "PickNotification");
		return root;
	}
}
