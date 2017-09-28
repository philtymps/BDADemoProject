package com.scripts;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class InvokeApiFromFile {
	
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(InvokeApiFromFile.class);
	private HashMap<String, String> variables;
	public InvokeApiFromFile(){
		
	}
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private String getVariableFile(){
		/*if (!YFCCommon.isVoid(getProperty("variableFile"))){
			return (String) getProperty("variableFile");
		}*/
		return "/opt/Sterling/Scripts/variables.xml";
	}
	
	private void replaceChildVariables(YFCElement eParent){
		if (!YFCCommon.isVoid(eParent) && eParent.getAttributes() != null && eParent.getAttributes().size() > 0){
			for (String sAttribute : eParent.getAttributes().keySet()){
				while (eParent.getAttribute(sAttribute).indexOf("#{") > -1 && eParent.getAttribute(sAttribute).indexOf("}") > -1) {
					String sVariable = eParent.getAttribute(sAttribute);
					String content = sVariable.substring(sVariable.indexOf("#{") + 2, sVariable.indexOf("}"));
					if (variables.containsKey(content)){
						try {
							String newValue = sVariable.replaceAll("#\\{" + content + "\\}", variables.get(content));
							eParent.setAttribute(sAttribute, newValue);
						} catch (Exception e){
							logger.debug(e.getMessage());
						}
					} else {
						eParent.setAttribute(sAttribute, content);
					}
				}
			}
		}
		
		for (YFCElement eChild : eParent.getChildren()){
			replaceChildVariables(eChild);
		}
	}
	private void addVariable(String sName, String sValue){
		if(YFCCommon.isVoid(variables)){
			variables = new HashMap<String, String>();
		}
		variables.put(sName, sValue);
	}
	
	private YFCDocument replaceVariables(YFCDocument dFileInput){
		YFCElement eFileInput = dFileInput.getDocumentElement();
		replaceChildVariables(eFileInput);
		return dFileInput;
	}
	
	private void loadVariableFile(){
		YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile());
		for (YFCElement eChild : temp.getDocumentElement().getChildren()){
			addVariable(eChild.getAttribute("Name"), eChild.getAttribute("Value"));
		}
	}
	
	public Document invokeApiFromFile(YFSEnvironment env, Document inputDoc){
		variables = new HashMap<String, String>();
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eInput = dInput.getDocumentElement();
		String sApiName = eInput.getAttribute("ApiName");
		String sFileName = eInput.getAttribute("FileName");
		YFCElement eVariables = eInput.getChildElement("Variables");
		loadVariableFile();
		if(!YFCCommon.isVoid(eVariables)){
			for(YFCElement eVariable : eVariables.getChildren()){
				addVariable(eVariable.getAttribute("Name"), eVariable.getAttribute("Value"));
			}
		}
		if (!YFCCommon.isVoid(sApiName) && !YFCCommon.isVoid(sFileName)){
			File tmp = new File(sFileName);
			if (tmp.exists()){
				YFCDocument dFileInput = YFCDocument.getDocumentForXMLFile(sFileName);
				try {	
					dFileInput = replaceVariables(dFileInput);
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					return localApi.invoke(env, sApiName, dFileInput.getDocument());
				} catch(YFSException yex) {
					try {
						YFCDocument temp = YFCDocument.parse(yex.getMessage());
						return temp.getDocument();
					} catch (Exception e){
						logger.debug(yex.toString());
					}
				} catch (Exception yex) {
					logger.debug(yex.toString());
				} 
			}
		} else if (!YFCCommon.isVoid(eInput.hasChildNodes())) {
			YFCDocument dOutput = YFCDocument.createDocument("Output");
			YFCElement eOutput = dOutput.getDocumentElement();
			for (YFCElement eApi : eInput.getChildren()){
				sApiName = eApi.getAttribute("ApiName");
				sFileName = eApi.getAttribute("FileName");
				if (!YFCCommon.isVoid(sApiName) && !YFCCommon.isVoid(sFileName)){
					File tmp = new File(sFileName);
					if (tmp.exists()){
						YFCDocument dFileInput = YFCDocument.getDocumentForXMLFile(sFileName);
						try {	
							dFileInput = replaceVariables(dFileInput);
							YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
							Document temp = localApi.invoke(env, sApiName, dFileInput.getDocument());
							YFCElement eApiOut = eOutput.createChild("Api");
							eApiOut.setAttribute("API", sApiName);
							if (!YFCCommon.isVoid(temp)){
								eApiOut.importNode(YFCDocument.getDocumentFor(temp).getDocumentElement());
							}
						} catch(YFSException yex) {
							try {
								YFCDocument temp = YFCDocument.parse(yex.getMessage());
								return temp.getDocument();
							} catch (Exception e){
								logger.debug(yex.toString());
							}
						} catch(Exception yex) {
							logger.debug(yex.toString());
						} 
					}
				}
			}
			return dOutput.getDocument();
		}
		return null;
	}

}
