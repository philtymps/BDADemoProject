package com.extension.bda.service.fulfillment;

import java.io.File;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.CallInteropServlet;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAServiceApi {
	protected static String BASE_URL = "http://localhost:9080";
	protected Properties p;
	
	public BDAServiceApi(){
		p = new Properties();
	}
	
	
	public void setProperties(Properties properties) throws Exception {
		this.p = properties;
	}
	
	
	public String getOutputLocation(){
		if (p.containsKey("OutputLocation")){
			return p.getProperty("OutputLocation");
		} else {
			return "/opt/Sterling/Fulfillment/output";
		}
	}
	
	public Object getProperty(String sProp){
		return this.p.get(sProp);
	}
	
	public static Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName) {
		return callApi(env, inDoc, dTemplate, sApiName, false);
	}
	
	public static Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName, boolean externalScope){
		if(!YFCCommon.isVoid(env) && !externalScope) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.invoke(env, sApiName, inDoc);
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!YFCCommon.isVoid(dOrderOutput)){
				return dOrderOutput;
			}
			return null;
		} else {
			if(!YFCCommon.isVoid(dTemplate)) {
				return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, BASE_URL).getDocument();
			}
			return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), null, sApiName, BASE_URL).getDocument();
		}
		
	
	}
	public static Document callService(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName){
		if(!YFCCommon.isVoid(env)) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.executeFlow(env, sApiName, inDoc);
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!YFCCommon.isVoid(dOrderOutput)){
				return dOrderOutput;
			}
			return null;
		} else {
			if(!YFCCommon.isVoid(dTemplate)) {
				return CallInteropServlet.invokeService(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, BASE_URL).getDocument();
			}
			return CallInteropServlet.invokeService(YFCDocument.getDocumentFor(inDoc), null, sApiName, BASE_URL).getDocument();
		}
		
	
	}
	
	private static HashMap<String, String> _properties;
	public static synchronized void clearMap() {
		_properties = new HashMap<String, String>();
	}
	
	public static synchronized String getPropertyValue(YFSEnvironment env, String sProperty) {
		if(YFCCommon.isVoid(_properties)) {
			_properties = new HashMap<String, String>();
		}
		if(!_properties.containsKey(sProperty)) {

			YFCDocument input = YFCDocument.createDocument("GetProperty");
			YFCElement eInput = input.getDocumentElement();
			eInput.setAttribute("PropertyName", sProperty);
			
			try {
				Document dResponse = BDAServiceApi.callApi(env, input.getDocument(), null, "getProperty");
				_properties.put(sProperty, dResponse.getDocumentElement().getAttribute("PropertyValue"));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return _properties.get(sProperty);
	}
	
	public static synchronized String getScriptsPath(YFSEnvironment env) {
		String path = getPropertyValue(env, "bda.scripts.filepath");
		if(YFCCommon.isVoid(path)) {
			path = "/opt/Sterling/Scripts";
		}
		return path;
	}
	
	public static synchronized String getAgentsPath(YFSEnvironment env) {
		String path = getPropertyValue(env, "bda.agents.filepath");
		if(YFCCommon.isVoid(path)) {
			path = "/opt/Sterling/Agents";
		}
		return path;
	}

	public boolean writeXML(String sPath, String sFile, YFCDocument output){
		validatePath(sPath);
		FileWriter fout;
		try{
			deleteExistingFile(sPath + File.separator + sFile);
			char buffer[] = new char[output.toString().length()];
			output.toString().getChars(0,output.toString().length(), buffer, 0);
			fout = new FileWriter(sPath + File.separator + sFile);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	

	protected static void validatePath(String sFilePath){
		File temp = new File(sFilePath);
		temp.mkdirs();
	}
	
	private static void deleteExistingFile(String sFile){
		File temp = new File(sFile);
		if(temp.exists()){
			temp.delete();
		}
	}
	
}
