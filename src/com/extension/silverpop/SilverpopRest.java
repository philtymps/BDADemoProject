package com.extension.silverpop;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public abstract class SilverpopRest implements IBDAService {
	
	public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String PARAM_REFRESH_TOKEN = "refresh_token";
    public static final String PARAM_GRANT_TYPE = "grant_type";
    public static final String GRANT_TYPE = "refresh_token";
   
    private HttpClient httpClient;
    private HashMap<String, SilverAuth> silverAuths;
 
	private Properties properties;
	
	public SilverpopRest(){
		properties = new Properties();
		silverAuths = new HashMap<String, SilverAuth>();
	}
	
	public abstract JSONObject getRequest(YFCElement eInput);
	public abstract String getRestApi();
	  
	    
	private class SilverAuth {
		
		public SilverAuth(String sResponse){
			this.sResponse = sResponse;
			this.created = System.currentTimeMillis();
			
			JsonParser p = new JsonParser();
        	JsonElement d = p.parse(sResponse);
        	try {
        		this.sKey = d.getAsJsonObject().get("access_token").getAsString();
        		valid = true;
        	} catch (Exception e){
        		System.out.println("Token Response Failure: " + sResponse);
        		valid = false;
        	}
		}
		
		public String getKey(){
			return sKey;
		}
		public boolean isValid(){
			if(created > System.currentTimeMillis() - (1000 * 60 * 60)){
				return valid;
			}
			return false;
		}

		private String sKey;
		private String sResponse;
		private long created;
		private boolean valid;
	}
	
	public Document invoke(YFSEnvironment env, Document dInput){
		if(!YFCCommon.isVoid(dInput)){
			YFCDocument inputDoc = YFCDocument.getDocumentFor(dInput);
			YFCElement eInput = inputDoc.getDocumentElement();
			callRequest(eInput);
		} else {
			callRequest(null);
		}
		return dInput;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public void setProperty(String name, String value){
		if(!YFCCommon.isVoid(properties)){
			this.properties.put(name, value);
		}
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	protected String getPod(){
		if(!YFCCommon.isVoid(getProperty("Pod"))){
			return (String) getProperty("Pod");
		}
		return "3";
	}
	protected String getTransactURL(){
		if(!YFCCommon.isVoid(getProperty("TransactURL"))){
			return (String) getProperty("TransactURL");
		} 
		return "https://api" + getPod() + ".silverpop.com:443/";
	}

  
 
    private HttpPost createPost() {
        HttpPost post = new HttpPost(getAuthURL());
        List<NameValuePair> params = new ArrayList<NameValuePair>();
       
        params.add(new BasicNameValuePair(PARAM_CLIENT_ID, getClientID()));
        params.add(new BasicNameValuePair(PARAM_CLIENT_SECRET, getClientSecret()));
        params.add(new BasicNameValuePair(PARAM_REFRESH_TOKEN, getRefreshToken()));
        params.add(new BasicNameValuePair(PARAM_GRANT_TYPE, GRANT_TYPE));
        try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return post;
    }
 
 
 
    private String getResponseText(HttpResponse response) throws IOException {
    	HttpEntity entity = response.getEntity();
    	if(entity != null){
    		 InputStream is = entity.getContent();
    		 try{
    			 String str = "";
    			 Scanner scanner = new Scanner(is).useDelimiter("\\A");
    			 try{
    				 str  = scanner.hasNext() ? scanner.next() : "";
    			 } finally {
    				 scanner.close();
    			 }    			
        		 return  str;
    		 } finally {    			 
    			 is.close();
       		 }    		
    	}
    	return null;
    }
 
    private String getClientID(){
    	if(!YFCCommon.isVoid(getProperty("ClientID"))){
			return (String) getProperty("ClientID");
		} 
		return "8bddc347-dc91-46b5-aa7b-4c839ec3d64c";
    }
    
    private String getClientSecret(){
    	if(!YFCCommon.isVoid(getProperty("ClientSecret"))){
			return (String) getProperty("ClientSecret");
		} 
		return "0085e74a-e39d-4328-a0b5-1764e6065d81";
    }
    
    private String getRefreshToken(){
    	if(!YFCCommon.isVoid(getProperty("RefreshToken"))){
			return (String) getProperty("RefreshToken");
		} 
		return "rnMbxKGBAp6hZt63dqX9PnI4aUIk_eQSGdC_4kdagiiIS1";
    }
    
    private void callForToken(){
    	HttpPost post = createPost();
   	   
        if(httpClient == null){
     	   httpClient = HttpClients.createDefault();
        }
        try {
     	   HttpResponse response = httpClient.execute(post);
     	   SilverAuth temp = new SilverAuth(getResponseText(response));
     	   if(temp.isValid()){
     		   silverAuths.put(getClientID(), temp);
     	   }
        } catch (Exception e) {
     	   e.printStackTrace();
        }
    }
    
    protected String getTokenFromResponse() {	
    	if(YFCCommon.isVoid(silverAuths)){
    		silverAuths = new HashMap<String, SilverAuth>();
    	}
    	if(!YFCCommon.isVoid(silverAuths.get(getClientID()))){
    		SilverAuth auth = silverAuths.get(getClientID());
    		if(!auth.isValid()){
    			callForToken();
    		}
    	} else {
    		callForToken();
    	}
    	if(silverAuths.containsKey(getClientID())){
    		return silverAuths.get(getClientID()).getKey();
    	}
    	return null;    	
    }
   
    public void callRequest(YFCElement eInput){
		try {
			String sOutput = getRequest(eInput).toString();
			System.out.println("Silverpop Request to: " + getRestApi());
			System.out.println("Silverpop Request: " + sOutput);
			if(!YFCCommon.isVoid(getTokenFromResponse())){
				URL url = new URL(getRestApi());
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestMethod("POST");
		        connection.setRequestProperty("Content-Type", "application/json");
		        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
		        connection.setRequestProperty("Authorization", "Bearer " +  getTokenFromResponse());
		        
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
				System.out.println("Silverpop Response: " + sb);
			} else {
				System.out.println("Invalid Token");
			}
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

    
	private String getAuthURL(){
		String output = "https://engage" + getPod() + ".silverpop.com/oauth/token";
		System.out.println("Token Request to: " + output);
		return output;
	}
}
