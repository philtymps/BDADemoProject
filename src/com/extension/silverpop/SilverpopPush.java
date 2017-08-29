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

public class SilverpopPush extends SilverpopRest {
	
	public SilverpopPush(){
		super();
	}
	
	private String getContactApi(){
		return getTransactURL() + "rest/channels/push/sends";
	}
	
	public static void main(String[] args){
		String clientId = "8bddc347-dc91-46b5-aa7b-4c839ec3d64c";
	    String clientSecret = "0085e74a-e39d-4328-a0b5-1764e6065d81";
	    String refreshToken = "rnMbxKGBAp6hZt63dqX9PnI4aUIk_eQSGdC_4kdagiiIS1";
	        
		SilverpopPush t = new SilverpopPush();
	
		String accessToken = t.retrieveToken(clientId, clientSecret, refreshToken);
	   
		t.callRequest(); 
	}
	
	public void callRequest(){
		try {
			String sOutput = getRequest().toString();
			URL url = new URL(getContactApi());
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
	
	private JSONObject getRequest(){
		
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
