package com.extension.silverpop;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SilverpopEmailSegment extends SilverpopRest {

	public static void main(String[] args){
		SilverpopEmailSegment seg = new SilverpopEmailSegment();
		seg.setProperty("DatabaseId", "76741");
		seg.setProperty("Segment", "Kids");
		seg.setProperty("ClientID", "33dcf340-d2d8-4738-b74a-4294f9073031");
		seg.setProperty("ClientSecret", "9ea7c9d9-5948-4a30-8e83-77685784392b");
		seg.setProperty("RefreshToken", "r7tzfUShXtNCVj8Ud-_C2wWir6QpR2c1gLKD5JLwbNooS1");
		seg.setProperty("Pod", "0");
		YFCDocument input = YFCDocument.createDocument("MultiApi");
		seg.invoke(null, input.getDocument());
	}
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "silverpopEmailSegment";
	}
	
	public String getRestApi(){
		return getTransactURL() + "rest/databases/" + getDatabase()  + "/contacts";
	}
	
	private String getDatabase(){
		if(!YFCCommon.isVoid(getProperty("DatabaseId"))){
			return (String) getProperty("DatabaseId");
		} 
		return "76741";
	}
	
	private String getSegment(){
		if(!YFCCommon.isVoid(getProperty("Segment"))){
			return (String) getProperty("Segment");
		}
		return "Kids";
	}
	
	private String getEmailAddress(YFCElement eInput){
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("ToEmailID"))){
			return eInput.getAttribute("ToEmailID");
		} else if (!YFCCommon.isVoid(getProperty("Email"))){
			try{
				String sXPath = (String) getProperty("Email");
				XPath xPath = XPathFactory.newInstance().newXPath();
				String sResponse = xPath.evaluate(sXPath, eInput.getDOMNode());
				if(!YFCCommon.isVoid(sResponse)){
					return sResponse;
				}
			} catch(Exception e){
				
			}
			return (String)getProperty("Email");
		} 
		return "pfaiola@us.ibm.com";
	}
	
	public JSONObject getRequest(YFCElement eInput){
		JSONObject root = new JSONObject();
		root.put("email", getEmailAddress(eInput));
		JSONArray customFields = new JSONArray();
		
		String segment = getSegment();
		JSONObject seg = new JSONObject();
		seg.put("name", "Segment");
		seg.put("value", segment);
		customFields.put(seg);		
		
		
		root.put("customFields", customFields);
		return root;
	}
	

	
}
