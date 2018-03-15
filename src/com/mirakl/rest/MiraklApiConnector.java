package com.mirakl.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONObject;

import com.mirakl.transform.IMiraklCSVParser;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.util.YFCCommon;

public class MiraklApiConnector implements YIFCustomApi {

	private Properties props;
	
	@Override
	public void setProperties(Properties prop) throws Exception {
		if(!YFCCommon.isVoid(prop)){
			this.props = prop;
		}
	}
	
	public String getApiKey() {
		if(!YFCCommon.isVoid(props)){
			if(props.containsKey("API-KEY")){
				return props.getProperty("API-KEY");
			}
		}
		return "e67db8ef-0387-4bb3-9903-c2b323e78e19";
	}
	
	public String getDomain() {
		if(!YFCCommon.isVoid(props)){
			if(props.containsKey("DOMAIN")){
				return props.getProperty("DOMAIN");
			}
		}
		return "ibm-dev.mirakl.net";
	}

	private String getURL(String sRestPath){
		if(!sRestPath.startsWith("/")){
			sRestPath = "/" + sRestPath;
		}
		if(getDomain().startsWith("http")){
			return getDomain() + sRestPath;
		}
		return "https://" + getDomain() + sRestPath;
	}
	
	public void processCSV(InputStreamReader inputStream, IMiraklCSVParser parser){
		String line = "";
		ArrayList<String> header = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(inputStream);
			while ((line = br.readLine()) != null) {
				try {    // use comma as separator
					String[] arguments = line.split(parser.getDelimiter());
					if(header.size() == 0){
						for(String argument : arguments){
							header.add(argument);
						}
					} else {
						parser.parseLine(header, arguments);
					}
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			br.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseCSVResponse(String sMethod, String sRestPath, JSONObject body, IMiraklCSVParser parser){
		URL url;
		try {
			url = new URL(getURL(sRestPath));
		
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(sMethod);
			if(!YFCCommon.isVoid(body)){
				OutputStream os = conn.getOutputStream();
				os.write(body.toString().getBytes());
				os.flush();				
			}	
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
			}
			
			processCSV(new InputStreamReader(conn.getInputStream()), parser);

			
				
		
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public JSONObject invokeJsonApi(String sMethod, String sRestPath, JSONObject body) {
		URL url;
		try {
			url = new URL(getURL(sRestPath));
		
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(sMethod);
			if(!YFCCommon.isVoid(body)){
				OutputStream os = conn.getOutputStream();
				os.write(body.toString().getBytes());
				os.flush();				
			}
			conn.setRequestProperty("Accept", "application/json");
			
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			StringBuilder responseStrBuilder = new StringBuilder();
		
			String inputStr;
			while ((inputStr = br.readLine()) != null) 
				responseStrBuilder.append(inputStr);
		
			conn.disconnect();
			JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
			
			//returns the json object
			return jsonObject;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;			
	}
}
