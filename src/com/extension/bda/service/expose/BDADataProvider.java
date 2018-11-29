package com.extension.bda.service.expose;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.extension.bda.service.IBDAService;
import com.ibm.mobile.InteropJSONServlet;
import com.ibm.mobile.dataprovider.IBDADataProvider;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDADataProvider implements IBDAService{

	private static HashMap<String, IBDADataProvider> dataProviders;
	private static YIFApi localApi;
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "bdaDataProvider";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		
		//System.out.println("Input: " + eInput);
		YFCDocument dResponse = YFCDocument.getDocumentFor(eInput.getChildElement("ApiResponse", true).getFirstChildElement().toString());
		if(!YFCCommon.isVoid(eInput.getChildElement("ApiResponse", true).getFirstChildElement())) {
			try {
				if(YFCCommon.isVoid(localApi)) {
					localApi = YIFClientFactory.getInstance().getLocalApi();
				}
			} catch (Exception e) {
				System.out.println("Unable to get localApi");
			}
			
			if(!YFCCommon.isVoid(localApi)) {
		
				
				YFCElement eTemplate = eInput.getChildElement("Template");
				String sTemplateID = eTemplate.getAttribute("TemplateId");
				String sTOrganization = YFCCommon.isVoid(eTemplate.getAttribute("OrganizationCode")) ? "DEFAULT" : eTemplate.getAttribute("OrganizationCode");
				String sTType = "00";
				String sApiName = eTemplate.getAttribute("ApiName");
				
				YFCDocument dTInput = YFCDocument.createDocument("ApiTemplate");
				YFCElement eTInput = dTInput.getDocumentElement();
				eTInput.setAttribute("TemplateId", sTemplateID);
				eTInput.setAttribute("OrganizationCode", sTOrganization);
				eTInput.setAttribute("TemplateType", sTType);
				eTInput.setAttribute("ApiName", sApiName);
				Document tResponse = localApi.getApiTemplateDetails(env, dTInput.getDocument());
				YFCDocument dApiInput = null;
				if(!YFCCommon.isVoid(eInput.getChildElement("Input")) && !YFCCommon.isVoid(eInput.getChildElement("Input").getFirstChildElement())) {
					dApiInput = YFCDocument.getDocumentFor(eInput.getChildElement("Input").getFirstChildElement().toString());
				}

				
				if(!YFCCommon.isVoid(tResponse)) {
					YFCDocument dTemplateResponse = YFCDocument.getDocumentFor(tResponse);
					YFCDocument dTemplate = YFCDocument.getDocumentFor(dTemplateResponse.getDocumentElement().getChildElement("TemplateData").getFirstChildElement().toString());

					//System.out.println("Template: " + dTemplate);
					
					//System.out.println(dTemplate.getDocumentElement().getNodeName());
					dResponse = YFCDocument.getDocumentFor(eInput.getChildElement("ApiResponse", true).getFirstChildElement().toString().replaceAll("BDAResponse", dTemplate.getDocumentElement().getNodeName()));
					//System.out.println("Before Response: " + dResponse);
					evaluateDataProviders(env, dTemplate, dResponse, dApiInput);
					//System.out.println("After Response: " + dResponse);
				}
			}
			return dResponse.getDocument();
		}
		return null;
		
	}

	private void runDataProviderLogic(YFSEnvironment context, IBDADataProvider dataProvider, YFCDocument response, YFCDocument dInput, String sNode, String sAttribute){
		if(!YFCCommon.isVoid(localApi)) {
			YFCNodeList nodes = response.getElementsByTagName(sNode);
			for (Object node : nodes){
				YFCElement eElement = (YFCElement) node;
				dataProvider.addAdditionalData(localApi, context, dInput.getDocumentElement(), response.getDocumentElement(), eElement, sAttribute);
			}
		}		
	}
	
	private void evaluateDataProviders(YFSEnvironment context, YFCDocument dTemplate, YFCDocument response, YFCDocument dInput){
		YFCElement eTemplate = dTemplate.getDocumentElement();
		for(String sPath : getDataProviders().keySet()){
			YFCElement eEvalNode = eTemplate;
			String evalPath = sPath;
			if (evalPath.startsWith("/")){
				evalPath = evalPath.substring(1);
			}
			String[] elements = evalPath.split("/");
			if (YFCCommon.equals(eTemplate.getNodeName(), elements[0])){
				for(int i = 1; i < elements.length; i++){
					if (elements[i].startsWith("@") && eEvalNode.hasAttribute(elements[i].substring(1))){
						IBDADataProvider clazz = getDataProviders().get(sPath);
						runDataProviderLogic(context, clazz, response, dInput, elements[i-1], elements[i].substring(1));
					} else if (!YFCCommon.isVoid(eEvalNode.getChildElement(elements[i]))){
						eEvalNode = eEvalNode.getChildElement(elements[i]);
					} else {
						break;
					}
					
				}
			}			
		}
		
	}

	private static HashMap<String, IBDADataProvider> getDataProviders(){
		if(dataProviders == null){
			dataProviders = new HashMap<String, IBDADataProvider>();
			try {
				YFCDocument dDocument = YFCDocument.parse(InteropJSONServlet.class.getResourceAsStream("BDADataProvider.xml"));
				YFCElement eAdditionalDataProviders = dDocument.getDocumentElement();
				HashMap<String, IBDADataProvider> existingClasses = new HashMap<String, IBDADataProvider>();
				for(YFCElement eAdditionalDataProvider : eAdditionalDataProviders.getChildren()){
					try {
						if (!existingClasses.containsKey(eAdditionalDataProvider.getAttribute("class"))){
							Class<?> clazz = Class.forName(eAdditionalDataProvider.getAttribute("class"));
							IBDADataProvider temp = (IBDADataProvider) clazz.newInstance();
							existingClasses.put(eAdditionalDataProvider.getAttribute("class"), temp);
						}
						IBDADataProvider dataClass = existingClasses.get(eAdditionalDataProvider.getAttribute("class"));
						if (!YFCCommon.isVoid(dataClass)){
							YFCElement eInterestedIn = eAdditionalDataProvider.getChildElement("InterestedIn", true);
							for(YFCElement eElement : eInterestedIn.getChildren("Element")){
								for(YFCElement eNewAttribute : eAdditionalDataProvider.getChildElement("NewAttributes", true).getChildren()){
									String sXPath = eElement.getAttribute("path") + "/@" + eNewAttribute.getAttribute("name");
									dataProviders.put(sXPath, dataClass);
								}
							}
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return dataProviders;
	}
	
}
