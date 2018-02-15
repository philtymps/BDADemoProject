package com.ibm.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.json.JSONException;
import org.json.XML;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.sterling.afc.jsonutil.PLTJSONUtils;
import com.ibm.mobile.dataprovider.IBDADataProvider;
import com.ibm.sterling.G11N;
import com.ibm.sterling.Utils;
import com.yantra.interop.client.ClientVersionSupport;
import com.yantra.interop.client.ContainerUserIdHolder;
import com.yantra.interop.client.InteropEnvStub;
import com.yantra.interop.client.InteropHttpServlet;
import com.yantra.ycp.ui.backend.YCPBackendUtils;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class InteropJSONServlet extends InteropHttpServlet {

	private static final long serialVersionUID = 9151009009435772553L;
	
	private static YFCLogCategory cat = YFCLogCategory.instance(InteropJSONServlet.class);

	private static HashMap<String, IBDADataProvider> dataProviders;
	
	
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
	
	protected JSONObject getRequestInput(HttpServletRequest request){
		
		try {
			InputStream inputStreamObject = request.getInputStream();
		
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStreamObject, "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
		
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
		
			JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		
			//returns the json object
			return jsonObject;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(YFCCommon.equals(request.getContentType(), "application/json")){
			
			//Create input stream
			JSONObject input = getRequestInput(request);
			if(!YFCCommon.isVoid(input)){
				String apiName = input.getString("InteropApiName");
				if(isAuthorized(apiName, request)){
					HttpSession session = request.getSession(false);
					if(apiName != null){
						handleJSONApiRequest(request, input, response);
					}
				}else {
		    		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		    	}	
			}
		
		} else {
			String apiName = getParameter(request, "InteropApiName");
			if(isAuthorized(apiName, request)) {
				if(apiName != null){
					handleApiRequest(request, response);
				}
	    	} else {
	    		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    	}			
		}
		
	}

	protected boolean isSecurityEnabled() {
		return false;
	}

	public static String getParameter(HttpServletRequest req, String name) {
		String retVal = req.getParameter(name);
		if (retVal == null)
			return "";
		if (!YFCCommon.isVoid(YFSSystem.getProperty("yfs.request.encoding"))) {
			try {
				byte[] ba = retVal.getBytes(YFSSystem.getProperty("yfs.request.encoding"));
				retVal = new String(ba, "UTF-8");
			} catch (Exception e) {
				// e.printStackTrace();
				return ("");
			}
		}

		retVal = retVal.trim();
		return retVal;
	}
	
	protected boolean isAuthorized(String apiName, HttpServletRequest req) throws YFSException, RemoteException{
		return true;
	}
	
	protected InteropEnvStub getEnvStub(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		String envProgId = null;
		if(!YFCCommon.isVoid(envProgId)){
			envProgId = getParameter(req, "YFSEnvironment.progId");
		}
		if(YFCCommon.isVoid(envProgId)){
			envProgId = "mobileStore";
		}
		String envUserId = null;
		if(!YFCCommon.isVoid(session) && !YFCCommon.isVoid(session.getAttribute("CurrentUser"))){
			envUserId = (String) session.getAttribute("CurrentUser");
		} else if(YFCCommon.isVoid(envUserId)){
			envUserId = "admin";
		}
		
		String resourceId = getParameter(req, "YFSEnvironment.resourceId");
		String adapterName = getParameter(req, "YFSEnvironment.adapterName");
		String systemName = getParameter(req, "YFSEnvironment.systemName");
		String localeCode = getParameter(req, "YFSEnvironment.locale");
		String version = getParameter(req, "YFSEnvironment.version");
		String rollbackOnly = getParameter(req, "YFSEnvironment.rollbackOnly");
		boolean isRollbackOnly = Utils.isTrue(rollbackOnly);

		
		InteropEnvStub envStub = new InteropEnvStub(envUserId, envProgId);
		envStub.setAdapterName(adapterName);
		envStub.setSystemName(systemName);
		envStub.setResourceId(resourceId);
		envStub.setLocaleCode(localeCode);
		envStub.setVersion(version);
		envStub.setRollbackOnly(isRollbackOnly);
		
		setClientIP(envStub, req);
		if (envStub instanceof ClientVersionSupport) {
			if (cat.isDebugEnabled()) {
				cat.debug("setting client properties ...");
			}
			HashMap<String, String> properties = new HashMap<String, String>();
			String clientProperties = getParameter(req, "YFSEnvironment.clientProperties");
			YFCDocument inDoc = null;
			if (!YFCCommon.isVoid(clientProperties)) {
				inDoc = YFCDocument.getDocumentFor(clientProperties);
				YFCElement clientPropsElem = inDoc.getDocumentElement();
				for (YFCElement eClientProperties : clientPropsElem.getChildren()) {
					String sKey = eClientProperties.getAttribute("propertyName");
					String sValue = eClientProperties.getAttribute("propertyValue");
					if (!YFCCommon.isVoid(sKey))
						properties.put(sKey, sValue);
				}
				((ClientVersionSupport) envStub).setClientProperties(properties);
				if (!properties.isEmpty()) {
					if (cat.isDebugEnabled())
						cat.debug("Client Properties Values : " + properties.toString());
				}
			}
		} else if (cat.isDebugEnabled()) {
			cat.debug("YFSEnviorenment instance is not of type ClientVersionSupport, client properites could not be set");
		}
		String tokenId = null;
		if (session != null && !YFCCommon.isVoid(session.getAttribute("UserToken"))) {
			tokenId = (String) session.getAttribute("UserToken");
		} else if (!YFCCommon.isVoid(req.getAttribute("UserToken"))) {
			tokenId = (String) req.getAttribute("UserToken");
		} else {
			tokenId = getParameter(req, "YFSEnvironment.userToken");
		}
		if (tokenId != null) {
			envStub.setTokenID(tokenId);
		}
		
		return envStub;
	}
	protected void handleJSONApiRequest(HttpServletRequest req, JSONObject input, HttpServletResponse res) throws ServletException, IOException {
		String apiName = input.getString("InteropApiName");
		cat.debug("Processing api: " + apiName);
		cat.assertLog(null != apiName, "Api Name in the request cannot be null");

		String isFlow = null;
		if(input.has("IsFlow")){
			isFlow = input.getString("IsFlow");
		}
		String envProgId = null;
		if(input.has("progId")){
			envProgId = input.getString("progId");
		}
		
		String envUserId = getParameter(req, "YFSEnvironment.userId");
		

		JSONObject apiData = null;
		if(input.has("InteropApiData")){
			apiData = input.getJSONObject("InteropApiData");
		}
		JSONObject templateData = null;
		if(input.has("TemplateData")){
			templateData = input.getJSONObject("TemplateData");
		}
		
		if (input.has("FlushDataProvider")){
			dataProviders = null;
		}
		
		
		
		cat.verbose("Api Data is: " + apiData);
		
		InteropEnvStub envStub = getEnvStub(req);
		
		cat.assertLog(req.getContentLength() > 0, "Content length of servlet request must be positive");

		Principal principal = req.getUserPrincipal();
		if (principal != null) {
			((ContainerUserIdHolder) localApi).setContainerUserId(principal.getName());
		}
		YFCDocument apiDoc = null;
		try {
			if(!YFCCommon.isVoid(apiData)){
				apiDoc = YFCDocument.getDocumentFor(PLTJSONUtils.getXmlFromJSON(apiData.toString(), null));
			}
		} catch (Exception e){
			
		}
	
		
		YFCDocument templateDoc = null;
		try{
			if (!YFCObject.isVoid(templateData)) {
				templateDoc = YFCDocument.getDocumentFor(PLTJSONUtils.getXmlFromJSON(templateData.toString(), null));
			}
		} catch (Exception e){
			
		}
		
		getResponse(envStub, req, res, apiDoc, templateDoc, apiName, isFlow);
		
		
	}
	
	protected void handleApiRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String apiName = getParameter(req, "InteropApiName");
		cat.debug("Processing api: " + apiName);
		cat.assertLog(null != apiName, "Api Name in the request cannot be null");
		String isFlow = getParameter(req, "IsFlow");
		String apiData = getParameter(req, "InteropApiData");
		String templateData = getParameter(req, "TemplateData");
		
		if (!YFCCommon.isVoid(getParameter(req, "FlushDataProvider"))){
			dataProviders = null;
		}
		
		cat.verbose("Api Data is: " + apiData);
		InteropEnvStub envStub = getEnvStub(req);
		
		cat.assertLog(req.getContentLength() > 0, "Content length of servlet request must be positive");

		Principal principal = req.getUserPrincipal();
		if (principal != null) {
			((ContainerUserIdHolder) localApi).setContainerUserId(principal.getName());
		}
		YFCDocument apiDoc = null;
		try {
			if(apiData.contains("{")){
				apiDoc = YFCDocument.getDocumentFor(PLTJSONUtils.getXmlFromJSON(apiData, null));
			} else {
				apiDoc = YFCDocument.parse(apiData);
			}
		} catch (Exception e){
			
		}
		
		YFCDocument templateDoc = null;
		try {
			if(!YFCCommon.isVoid(templateData)){
				if(templateData.contains("{")){
					templateDoc = YFCDocument.getDocumentFor(PLTJSONUtils.getXmlFromJSON(templateData, null));
				} else {
					templateDoc = YFCDocument.getDocumentFor(templateData);;
				}
			}
		} catch (Exception e){
			
		}		
		getResponse(envStub, req, res, apiDoc, templateDoc, apiName, isFlow);
		
	}
	
	protected Document handleCustomApi(InteropEnvStub envStub, HttpServletRequest req, String apiName, YFCDocument apiDoc, String isFlow, YFCDocument templateDoc){
		return null;
	}
	
	private void getResponse(InteropEnvStub envStub, HttpServletRequest req, HttpServletResponse res, YFCDocument apiDoc, YFCDocument templateDoc, String apiName, String isFlow ) throws IOException{
		try {
			res.setHeader("InteropSentData", "true");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Request-Method", "*");
			res.setHeader("Access-Control-Allow-Credentials", "true");
			res.setContentType("text/json; charset=UTF-8");
			
			
			if (!YFCObject.isVoid(templateDoc)) {
				envStub.setApiTemplate(apiName, templateDoc.getDocument());
			}
			
			

			Document retDoc = null;
			if (apiName.equals("createEnvironment")) {
				// localApi.createEnvironment(apiDoc.getDocument());
			}

			// We will uncomment this when we are ready to authenticate
			// authenticate(req, envStub);

			// If security is disabled, mark this as a system call.
			envStub.setSystemCall(!isSecurityEnabled());

			if (!apiName.equals("createEnvironment") && !apiName.equals("releaseEnvironment")) {
				retDoc = handleCustomApi(envStub, req, apiName, apiDoc, isFlow, templateDoc);
				if(YFCCommon.isVoid(retDoc)){
					if (!YFCCommon.isVoid(isFlow) && Utils.isTrue(isFlow)) {
						preInvoke(envStub, apiName, apiDoc.getDocument(), true);
						retDoc = localApi.executeFlow(envStub, apiName, apiDoc.getDocument());
					} else {
						preInvoke(envStub, apiName, apiDoc.getDocument(), false);
						retDoc = localApi.invoke(envStub, apiName, apiDoc.getDocument());
					}
				}				
			}

			if (null == retDoc) {
				cat.debug("API returned a null doc. will create an empty doc");
				retDoc = YFCDocument.createDocument("ApiSuccess").getDocument();
			}
			if (!YFCCommon.isVoid(retDoc)){
				YFCDocument dDoc = YFCDocument.getDocumentFor(retDoc);
				try {
					if (!YFCCommon.isVoid(templateDoc)){
						evaluateDataProviders(envStub, templateDoc, dDoc, apiDoc);
					}
				} catch (Exception e){
					
				}
				authenticate(envStub, apiName, req, dDoc);
				org.apache.commons.json.JSONObject obj = PLTJSONUtils.getJSONObjectFromXML(dDoc.getDocument().getDocumentElement(), null, null);
				res.getWriter().write(obj.toString());
			}
			//YFCDocument.getDocumentFor(retDoc).serialize(res.getWriter());
		} catch (IOException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "IO Exception while invoking api {1}", new String[] { apiName }), e);
			res.getWriter().write(XML.toJSONObject(new YFCException(e).getXMLErrorBuf()).toString());
		} catch (YFSException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "YFS Exception while invoking api {1}", new String[] { apiName }), e);
			try {
				YFCDocument output = YFCDocument.getDocumentFor(e.getEncodedMessage());
				res.getWriter().write(XML.toJSONObject(output.toString()).toString());
			} catch (Exception ex){
				JSONObject error = new JSONObject();
				error.put("Error", e.getEncodedMessage());
				res.getWriter().write(error.toString());
			}
		} catch (YFCException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "YFC Exception while invoking api {1}", new String[] { apiName }), e);
			res.getWriter().write(XML.toJSONObject(e.getXMLErrorBuf()).toString());
		} catch (JSONException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "YFC Exception while invoking api {1}", new String[] { apiName }), e);
			JSONObject error = new JSONObject();
			error.put("Error", e.getMessage());
			res.getWriter().write(error.toString());
		}
	}

	
	protected void authenticate(InteropEnvStub envStub, String apiName, HttpServletRequest req, YFCDocument dDoc) throws YFSException, RemoteException {
		if(YFCCommon.equals(apiName, "login")){
			if(!YFCCommon.equals(dDoc.getNodeName(), "Errors")){
				HttpSession session = req.getSession(true);
				session.setAttribute("CurrentUser", dDoc.getDocumentElement().getAttribute("LoginID"));
			}
		}
	}
	private void runDataProviderLogic(YFSEnvironment context, IBDADataProvider dataProvider, YFCDocument response, YFCDocument dInput, String sNode, String sAttribute){
		YFCNodeList nodes = response.getElementsByTagName(sNode);
		for (Object node : nodes){
			YFCElement eElement = (YFCElement) node;
			dataProvider.addAdditionalData(localApi, context, dInput.getDocumentElement(), response.getDocumentElement(), eElement, sAttribute);
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

	private void setClientIP(InteropEnvStub envStub, HttpServletRequest req){    	
    	YCPBackendUtils.setClientIPAddress(req, envStub);
    }
}
