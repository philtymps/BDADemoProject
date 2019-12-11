package com.scripts;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.ibm.icu.util.Calendar;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class InvokeApiFromFile {
	
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(InvokeApiFromFile.class);
	private HashMap<String, YFCElement> variables;
	public InvokeApiFromFile(){
		
	}
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private String getVariableFile(YFSEnvironment env){
		/*if (!YFCCommon.isVoid(getProperty("variableFile"))){
			return (String) getProperty("variableFile");
		}*/
		return BDAServiceApi.getScriptsPath(env) + "/variables.xml";
	}
	
	private void replaceChildVariables(YFCElement eParent){
		if (!YFCCommon.isVoid(eParent) && eParent.getAttributes() != null && eParent.getAttributes().size() > 0){
			for (String sAttribute : eParent.getAttributes().keySet()){
				while (eParent.getAttribute(sAttribute).indexOf("#{") > -1 && eParent.getAttribute(sAttribute).indexOf("}") > -1) {
					String sVariable = eParent.getAttribute(sAttribute);
					String content = sVariable.substring(sVariable.indexOf("#{") + 2, sVariable.indexOf("}"));
					if(content.startsWith("NOW")){
						YDate now = YDate.newDate();
						if(YFCCommon.equals(content, "NOW")){
							eParent.setAttribute(sAttribute, now);
						} else if (content.contains("+")) {
							String[] args = content.split("[+]");
							int days = Integer.parseInt(args[1]);
							eParent.setAttribute(sAttribute, YDate.newDate(now, days));
						} else {
							String[] args = content.split("[-]");
							int days = Integer.parseInt(args[1]);
							eParent.setAttribute(sAttribute, YDate.newDate(now, (days * -1)));
						}
					} else if (content.startsWith("TODAY")) {
						Calendar now = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (content.contains("+")) {
							String[] args = content.split("[+]");
							int days = Integer.parseInt(args[1]);
							now.add(Calendar.DATE, days);
						} else if(content.contains("-")) {
							String[] args = content.split("[-]");
							int days = Integer.parseInt(args[1]);
							now.add(Calendar.DATE, (days * -1));
						}
						String newValue = sVariable.replaceAll("#\\{" + content + "\\}", sdf.format(now.getTime()));
						eParent.setAttribute(sAttribute, newValue);
					} else if(content.startsWith("MINUTE")){
						Calendar now = Calendar.getInstance();
						if(YFCCommon.equals(content, "MINUTE")){
							eParent.setAttribute(sAttribute, YDate.newDate(now.getTimeInMillis()));
						} else if (content.contains("+")) {
							String[] args = content.split("[+]");
							int days = Integer.parseInt(args[1]);
							eParent.setAttribute(sAttribute, YDate.newDate(now.getTimeInMillis() + (days * 60000)));
						} else {
							String[] args = content.split("[-]");
							int days = Integer.parseInt(args[1]);
							eParent.setAttribute(sAttribute, YDate.newDate(now.getTimeInMillis() - (days * 60000)));
						}
					} else if (variables.containsKey(content)){
						try {
							String newValue = sVariable.replaceAll("#\\{" + content + "\\}", variables.get(content).getAttribute("Value"));
							if(YFCCommon.equals(variables.get(content).getAttribute("TYPE"), "ITEM")) {
								if(variables.get(content).hasAttribute("UnitOfMeasure")) {
									eParent.setAttribute("UnitOfMeasure", variables.get(content).getAttribute("UnitOfMeasure"));
								} else if(eParent.hasAttribute("UnitOfMeasure")) {
									eParent.setAttribute("UnitOfMeasure", "");
								}								
							}
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
	private void addVariable(String sName, YFCElement sValue){
		if(YFCCommon.isVoid(variables)){
			variables = new HashMap<String, YFCElement>();
		}
		variables.put(sName, sValue);
	}
	
	private YFCDocument replaceVariables(YFCDocument dFileInput){
		YFCElement eFileInput = dFileInput.getDocumentElement();
		replaceChildVariables(eFileInput);
		return dFileInput;
	}
	
	private void loadVariableFile(YFSEnvironment env){
		YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
		for (YFCElement eChild : temp.getDocumentElement().getChildren()){
			addVariable(eChild.getAttribute("Name"), eChild);
		}
	}
	
	public Document invokeApiFromFile(YFSEnvironment env, Document inputDoc){
		variables = new HashMap<String, YFCElement>();
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eInput = dInput.getDocumentElement();
		String sApiName = eInput.getAttribute("ApiName");
		boolean isService = eInput.getBooleanAttribute("IsService");
		String sFileName = eInput.getAttribute("FileName");
		YFCElement eVariables = eInput.getChildElement("Variables");
		loadVariableFile(env);
		if(!YFCCommon.isVoid(eVariables)){
			for(YFCElement eVariable : eVariables.getChildren()){
				addVariable(eVariable.getAttribute("Name"), eVariable);
			}
		}
		if (!YFCCommon.isVoid(sApiName) && !YFCCommon.isVoid(sFileName)){
			File tmp = new File(sFileName);
			if (tmp.exists()){
				YFCDocument dFileInput = YFCDocument.getDocumentForXMLFile(sFileName);
	
				dFileInput = replaceVariables(dFileInput);
				if(isService) {
					return BDAServiceApi.callService(env, dFileInput.getDocument(), null, sApiName);
				} else {
					return BDAServiceApi.callApi(env, dFileInput.getDocument(), null, sApiName);
				}
			
			}
		} else if (!YFCCommon.isVoid(eInput.hasChildNodes())) {
			YFCDocument dOutput = YFCDocument.createDocument("Output");
			YFCElement eOutput = dOutput.getDocumentElement();
			for (YFCElement eApi : eInput.getChildren()){
				sApiName = eApi.getAttribute("ApiName");
				isService = eInput.getBooleanAttribute("IsService");
				sFileName = eApi.getAttribute("FileName");
				if (!YFCCommon.isVoid(sApiName) && !YFCCommon.isVoid(sFileName)){
					File tmp = new File(sFileName);
					if (tmp.exists()){
						YFCDocument dFileInput = YFCDocument.getDocumentForXMLFile(sFileName);
						dFileInput = replaceVariables(dFileInput);
						Document temp;
						if(isService) {
							temp = BDAServiceApi.callService(env, dFileInput.getDocument(), null, sApiName);
						} else {
							temp = BDAServiceApi.callApi(env, dFileInput.getDocument(), null, sApiName);
						}
						YFCElement eApiOut = eOutput.createChild("Api");
						eApiOut.setAttribute("API", sApiName);
						if (!YFCCommon.isVoid(temp)){
							eApiOut.importNode(YFCDocument.getDocumentFor(temp).getDocumentElement());
						}
					
					}
				}
			}
			return dOutput.getDocument();
		}
		return null;
	}

}
