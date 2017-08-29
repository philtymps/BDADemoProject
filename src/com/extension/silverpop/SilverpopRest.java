package com.extension.silverpop;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;

public class SilverpopRest {
	
	public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String PARAM_REFRESH_TOKEN = "refresh_token";
    public static final String PARAM_GRANT_TYPE = "grant_type";
    public static final String GRANT_TYPE = "refresh_token";
 
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(SilverpopEmail.class);
	
	public SilverpopRest(){
		properties = new Properties();
	}
	

	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	protected String getTransactURL(){
		if(!YFCCommon.isVoid(getProperty("TransactURL"))){
			return (String) getProperty("TransactURL");
		} 
		return "https://api3.silverpop.com:443/";
	}

    private HttpClient httpClient;
    private String responseText;
 
 
    public String retrieveToken(String clientId, String clientSecret, String refereshToken) {
       if(!YFCCommon.isVoid(responseText)){
    	   return getTokenFromResponse();
       } else {
    	   HttpPost post = createPost(clientId, clientSecret, refereshToken);
    	   
           if(httpClient == null){
        	   httpClient = HttpClients.createDefault();
           }
           try {
        	   HttpResponse response = httpClient.execute(post);
               responseText = getResponseText(response);
               return getTokenFromResponse();
           } catch (Exception e) {
           	e.printStackTrace();
               throw new RuntimeException(e);
           }
       }
    	
    }
 
    private HttpPost createPost(String clientId, String clientSecret, String refereshToken) {
        HttpPost post = new HttpPost(getAuthURL());
        List<NameValuePair> params = new ArrayList<NameValuePair>();
       
        params.add(new BasicNameValuePair(PARAM_CLIENT_ID, clientId));
        params.add(new BasicNameValuePair(PARAM_CLIENT_SECRET, clientSecret));
        params.add(new BasicNameValuePair(PARAM_REFRESH_TOKEN, refereshToken));
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
 
    protected String getTokenFromResponse() {
    	String clientId = "8bddc347-dc91-46b5-aa7b-4c839ec3d64c";
	    String clientSecret = "0085e74a-e39d-4328-a0b5-1764e6065d81";
	    String refreshToken = "rnMbxKGBAp6hZt63dqX9PnI4aUIk_eQSGdC_4kdagiiIS1";
	
    	if(responseText == null){
    		return retrieveToken(clientId, clientSecret, refreshToken);
    	} else {
    		JsonParser p = new JsonParser();
        	JsonElement d = p.parse(responseText);
        	return d.getAsJsonObject().get("access_token").getAsString();
    	}
    	
    }
 
    
	private String getAuthURL(){
		return "https://engage3.silverpop.com/oauth/token";
	}
}
