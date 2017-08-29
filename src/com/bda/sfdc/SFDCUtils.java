package com.bda.sfdc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.SSLContext;

import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.core.YFSSystem;

public class SFDCUtils {
	
	
	public static void main(String[] args){
		try {
    		System.out.println(SFDCUtils.oAuthLogin("AuroraTel"));
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	/*try {
    		System.out.println(helper.oAuthLogin("iuser@us.ibm.com.oms", "passw0rd"));
    	} catch (Exception e){
    		e.printStackTrace();
    	}*/
    	
    }
 
	private static String sessionId = null;
	private static long authTokenTime = 0;

	@SuppressWarnings("deprecation")
	public static JSONObject batchRecords(String baseUrl, String sContent, String sOrganization) throws IOException, URISyntaxException, JSONException{
		SSLContext sslContext = SSLContexts.createDefault();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
		        new String[]{"TLSv1.1", "TLSv1.2"},
		        null,
		        new NoopHostnameVerifier());
			
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		
		HttpPost HttpPost = new HttpPost(baseUrl);
		HttpPost.setEntity(new StringEntity(sContent));
		
		HttpPost.setHeader("Content-Type", "application/json");
		HttpPost.setHeader("Authorization", "Bearer " + oAuthLogin(sOrganization));
    	
		CloseableHttpResponse response = httpClient.execute(HttpPost);
		
		System.out.println("Response Status: " + response.getStatusLine().toString());
		try {
			String res = EntityUtils.toString(response.getEntity());
			return new JSONObject(res);
		} catch (Exception e){
			e.printStackTrace();
			
			return null;
		} finally {
			response.close();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void updateRecord(String baseUrl, String sContent, String sOrganization) throws IOException, URISyntaxException, JSONException{
		SSLContext sslContext = SSLContexts.createDefault();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
		        new String[]{"TLSv1.1", "TLSv1.2"},
		        null,
		        new NoopHostnameVerifier());
			
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		
		HttpPatch httpPatch = new HttpPatch(baseUrl);
		httpPatch.setEntity(new StringEntity(sContent));
		
		httpPatch.setHeader("Content-Type", "application/json");
		httpPatch.setHeader("Authorization", "Bearer " + oAuthLogin(sOrganization));
    	
		CloseableHttpResponse response = httpClient.execute(httpPatch);
		
		System.out.println("Response Status: " + response.getStatusLine().toString());
	}
	public static synchronized String oAuthLogin(String sOrganization) throws IOException, URISyntaxException, JSONException {
		if(authTokenTime + 600000 < System.currentTimeMillis()) {
			sessionId = null;
			authTokenTime = System.currentTimeMillis();
		}
    	if(sessionId == null){
    		StringBuilder sb = new StringBuilder("grant_type=password&client_id=");
        	sb.append(SFDCUtils.getConfiguredClientKey(sOrganization)).append("&client_secret=").append(SFDCUtils.getConfiguredClientSecret(sOrganization)).append("&username=")
        	.append(SFDCUtils.getConfiguredSFDCUsername(sOrganization)).append("&password=").append(SFDCUtils.getConfiguredSFDCPassword(sOrganization));
        	
        	String baseUrl = SFDCUtils.getConfiguredAuthSite(sOrganization) + "/services/oauth2/token";
        	String encodedData = sb.toString();
        	        	
        	URL obj = new URL(baseUrl);
        	HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        	conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
        	conn.setDoOutput(true);
        	
        	conn.setRequestMethod("POST");
        	
        	
        	OutputStream os = conn.getOutputStream();
        	os.write(encodedData.getBytes());
        	os.flush();
        	os.close();
            
        	StringBuilder responseStrBuilder = new StringBuilder();
         	BufferedReader br;
        	try {
        		 br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	} catch (Exception e){
        		 br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        	}
        	
        	String inputStr;
        	while ((inputStr = br.readLine()) != null)
        	responseStrBuilder.append(inputStr);

        	JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
        	sessionId = jsonObject.getString("access_token");
    	}
		
    	return sessionId;
    }
    
	/**
     * Returns the enterprise's configured SFDC username.
     * 
     * @return the enterprise's configured SFDC username.
     */
    public static String getConfiguredSFDCUsername(String enterpriseCode)
    {
    	String userId = null;
    	try {
    		userId = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".userid");
    	} catch (Exception e){
    		userId = "iuser@us.ibm.com.oms";
    	} 
    	return userId;
    }
    
    /**
     * Returns the enterprise's configured SFDC password.
     * 
     * @return the enterprise's configured SFDC password.
     */
    public static String getConfiguredSFDCPassword(String enterpriseCode)
    {
    	String userId = null;
    	try {
    		userId = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".password");
    	} catch (Exception e){
    		userId = "passw0rd";
    	} 
    	return userId;
    }
    
    public static String getConfiguredSciAccessToken(String enterpriseCode){
    	String accessCode = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".access_token");
    	return accessCode;
    }
    
    public static String getConfiguredClientKey(String enterpriseCode){
    	try {
    		String key = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".client_key");
        	if(YFCCommon.isVoid(key)){
        		key = "3MVG99E3Ry5mh4zpeV4uM2gCsfM5GaLQZPecdUpvLdftwPaz0xQe0PWzyucWodHiXX3iYYYUyzZSaLW4rzeO1";
        	}
        	return key;
    	} catch (Exception e){
    		return "3MVG99E3Ry5mh4zpeV4uM2gCsfM5GaLQZPecdUpvLdftwPaz0xQe0PWzyucWodHiXX3iYYYUyzZSaLW4rzeO1";
    	}
    
    }
    
    public static String getConfiguredClientSecret(String enterpriseCode){
    	try {
    		String key = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".client_secret");
        	if(YFCCommon.isVoid(key)){
        		key = "8163598957402179497";
            }
        	return key;
    	} catch (Exception E){
    		return "8163598957402179497";
    	}
    	
    }
    
    public static String getConfiguredAuthSite(String enterpriseCode){
    	try {
    		String key = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".auth_site");
        	if(YFCCommon.isVoid(key)){
        		key = "https://ibmofficial--oms.cs67.my.salesforce.com";
        	}
        	return key;
    	} catch (Exception e){
    		return "https://ibmofficial--oms.cs67.my.salesforce.com";
    	}
    	
    }
    
   
}
