package com.extension.bda.service.expose;

import java.util.Collection;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.services.api.ApiRepositoryUtil;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.ycp.core.YCPEntityApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAEntityApi implements IBDAService {

	private Properties p;
	
	public BDAEntityApi(){
		p = new Properties();
	}
	
	public String getServiceName(){
		return "callEntityApi";
	}
	
	public void setProperties(Properties properties){
		this.p = properties;
	}
	
	public Object getProperty(String sProp){
		return this.p.get(sProp);
	}
	
	public String getEntityApiName(YFCDocument dInput) throws Exception{
		if(!YFCCommon.isVoid(dInput)){
			YFCElement eInput = dInput.getDocumentElement();
			if(eInput.hasAttribute("ApiName")){
				String sApiName = eInput.getAttribute("ApiName");
				p.setProperty("ApiName", eInput.getAttribute("ApiName"));
				eInput.removeAttribute("ApiName");
				return sApiName;
			}
		}
		if (p.containsKey("ApiName")){
			if(!YFCCommon.isVoid(p.get("ApiName")) && p.get("ApiName") instanceof String){
				return (String) p.getProperty("ApiName");
			}
		}
		throw new Exception("API Not Defined");
	}
	
	public Document invoke (YFSEnvironment env, Document dInput) {
		try {
			return getEntityApi(env, dInput);
		} catch (Exception e){
			YFCDocument dOutput = YFCDocument.createDocument("Error");
			YFCElement eOutput = dOutput.getDocumentElement();
			eOutput.setAttribute("Message", "Error invoking entity api");
			eOutput.setAttribute("Exception", e.getMessage());
			return dOutput.getDocument();
		}
		
	}
	public Document getEntityApi(YFSEnvironment env, Document dInput) throws Exception{
		YCPEntityApi api = YCPEntityApi.getInstance();
		YFCDocument inputDoc = YFCDocument.getDocumentFor(dInput);
        return api.invoke((YFSContext) env, getEntityApiName(inputDoc), inputDoc ).getDocument();
	}
	
	public Document getApiList(YFSEnvironment env, Document dInput) throws Exception {
		Collection<String> apis = ApiRepositoryUtil.getApiNames();
		YFCDocument dOutput = YFCDocument.createDocument("ApiList");
		YFCElement eApis = dOutput.getDocumentElement();
		for(String api : apis){
			eApis.createChild("Api").setAttribute("Name", api);
		}
		return dOutput.getDocument();
	}
}
