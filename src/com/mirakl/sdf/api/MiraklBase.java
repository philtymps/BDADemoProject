package com.mirakl.sdf.api;

import java.util.HashMap;
import java.util.Properties;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.mirakl.entity.MiraklTranslation;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class MiraklBase {
	private Properties p;
	
	public void setProperties(Properties props) {
		p = props;
	}
	
	protected Properties getProperties(){
		return p;
	}
	
	protected String getMiraklTranslationDoc(YFSEnvironment env){
		if(p != null && p.contains("TRANSFORM-DOC")){
			return p.getProperty("TRANSFORM-DOC");
		}
		return BDAServiceApi.getScriptsPath(env) + "/MiraklTranslate.xml";
	}
	
	public String getApiKey() {
		if(p != null && p.contains("API-KEY")){
			return p.getProperty("API-KEY");
		}
		System.out.println("Set API-KEY in your Mirakl Order Service");
		return "e67db8ef-0387-4bb3-9903-c2b323e78e19";
	}
	
	public String getDomain() {
		if(p != null && p.contains("DOMAIN")){
			return p.getProperty("DOMAIN");
		}
		System.out.println("Set DOMAIN in your Mirakl Order Service");
		return "ibm-dev.mirakl.net";
	}
	
	public String getMiraklOrderID(){
		if(p != null && p.contains("MIRAKL-ORDER-ID")){
			return p.getProperty("MIRAKL-ORDER-ID");
		}
		System.out.println("Set MIRAKL-ORDER-ID in your Mirakl Order Service");
		return "MiraklOrderID";
	}
	
	public String getMiraklEnterprise(){
		if(p != null && p.contains("MIRAKL-ENTERPRISE-CODE")){
			return p.getProperty("MIRAKL-ENTERPRISE-CODE");
		}
		System.out.println("Set MIRAKL-ENTERPRISE-CODE in your Mirakl Order Service");
		return "Mirakl";
	}
	
	protected String getURL(String sRestPath){
		if(!sRestPath.startsWith("/")){
			sRestPath = "/" + sRestPath;
		}
		if(getDomain().startsWith("http")){
			return getDomain() + sRestPath;
		}
		return "https://" + getDomain() + sRestPath;
	}
	
	protected void createNode(YFCElement eParent, String sNodeName, String sValue) {
		if(!YFCCommon.isVoid(sValue) && !YFCCommon.isVoid(sNodeName) && !YFCCommon.isVoid(eParent)){
			eParent.getChildElement(sNodeName, true).setNodeValue(sValue);
		}
	}
	
	protected void createNode(YFCElement eParent, String sNodeName, double sValue) {
		if(!YFCCommon.isVoid(sValue) && !YFCCommon.isVoid(sNodeName) && !YFCCommon.isVoid(eParent)){
			eParent.getChildElement(sNodeName, true).setNodeValue(sValue);
		}
	}
	
	protected void createNode(YFCElement eParent, String sNodeName, int sValue) {
		if(!YFCCommon.isVoid(sValue) && !YFCCommon.isVoid(sNodeName) && !YFCCommon.isVoid(eParent)){
			eParent.getChildElement(sNodeName, true).setNodeValue(sValue);
		}
	}
	
	protected void createNode(YFCElement eParent, String sNodeName, String sValue, String sDefault) {
		if(!YFCCommon.isVoid(sValue) && !YFCCommon.isVoid(sNodeName) && !YFCCommon.isVoid(eParent)){
			createNode(eParent, sNodeName, sValue);
		} else if(!YFCCommon.isVoid(sDefault) && !YFCCommon.isVoid(sNodeName) && !YFCCommon.isVoid(eParent)){
			createNode(eParent, sNodeName, sDefault);
		}
	}
	
	protected void createNodeTranslate(YFSEnvironment env, YFCElement eParent, String sNodeName, String sValue, boolean required){
		if(!YFCCommon.isVoid(sNodeName) && !YFCCommon.isVoid(sValue) && !YFCCommon.isVoid(eParent)){
			String sMiraklValue = MiraklTranslation.getInstance(getMiraklTranslationDoc(env), false).getMiraklValue(sNodeName, sValue);
			if(!YFCCommon.isVoid(sMiraklValue)){
				createNode(eParent, sNodeName, sMiraklValue);
			} else if(!required){
				createNode(eParent, sNodeName, sValue);	
			}			
		} 
	}
	
	protected void createNodeTranslate(YFSEnvironment env, YFCElement eParent, String sNodeName, String sValue, boolean required, String sDefault){
		if(!YFCCommon.isVoid(sNodeName) && (!YFCCommon.isVoid(sValue) || !YFCCommon.isVoid(sDefault)) && !YFCCommon.isVoid(eParent)){
			if(!YFCCommon.isVoid(sValue)){
				createNodeTranslate(env, eParent, sNodeName, sValue, required);
			} else {
				createNodeTranslate(env, eParent, sNodeName, sDefault, required);
			}
		}
	}
}
