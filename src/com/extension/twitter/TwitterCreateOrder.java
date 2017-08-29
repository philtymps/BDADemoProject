package com.extension.twitter;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.mobile.InteropJSONServlet;
import com.ibm.sterling.G11N;
import com.ibm.sterling.Utils;
import com.yantra.interop.client.ClientVersionSupport;
import com.yantra.interop.client.ContainerUserIdHolder;
import com.yantra.interop.client.InteropEnvStub;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.ui.backend.servlets.BaseServlet;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSException;

public class TwitterCreateOrder extends BaseServlet {
	private static final long serialVersionUID = 9151009009435772553L;
	
	private static YFCLogCategory cat = YFCLogCategory.instance(InteropJSONServlet.class);

	@Override
  protected void processRequest (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	cat.debug("Entered process request");
   		handleApiRequest(req, res);
	}
    
	protected void handleApiRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String apiName = "createOrder";
		cat.debug("Processing api: " + apiName);
		cat.assertLog(null != apiName, "Api Name in the request cannot be null");

		String isFlow = "N";
		String envUserId = "twitter";
		String envProgId = "twitter";
		String resourceId = getParameter(req, "YFSEnvironment.resourceId");
		String adapterName = getParameter(req, "YFSEnvironment.adapterName");
		String systemName = getParameter(req, "YFSEnvironment.systemName");
		String localeCode = getParameter(req, "YFSEnvironment.locale");
		String version = getParameter(req, "YFSEnvironment.version");
		String rollbackOnly = getParameter(req, "YFSEnvironment.rollbackOnly");
		boolean isRollbackOnly = Utils.isTrue(rollbackOnly);

		// ADC UNMESH CR 83667 83860
		HttpSession session = req.getSession(false);

		cat.debug("UserId: " + envUserId);
		cat.debug("ProgId: " + envProgId);
		cat.debug("ResourceId: " + resourceId);
		cat.debug("RollbackOnly: " + isRollbackOnly);
		String apiData = getParameter(req, "InteropApiData");
		String templateData = getParameter(req, "TemplateData");
		
		if(YFCCommon.isVoid(envUserId)){
			envUserId = "admin";
		}
		if(YFCCommon.isVoid(envProgId)){
			envProgId = "mobileStore";
		}
		cat.verbose("Api Data is: " + apiData);
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
		cat.assertLog(req.getContentLength() > 0, "Content length of servlet request must be positive");

		Principal principal = req.getUserPrincipal();
		if (principal != null) {
			((ContainerUserIdHolder) localApi).setContainerUserId(principal.getName());
		}

		try {
			res.setHeader("InteropSentData", "true");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Request-Method", "*");
			res.setHeader("Access-Control-Allow-Credentials", "true");
			res.setContentType("text/json; charset=UTF-8");

			YFCDocument apiDoc = YFCDocument.parse(apiData);
			YFCDocument templateDoc = null;
			if (!YFCObject.isVoid(templateData)) {
				templateDoc = YFCDocument.getDocumentFor(templateData);
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
				if (!YFCCommon.isVoid(isFlow) && Utils.isTrue(isFlow)) {
					preInvoke(envStub, apiName, apiDoc.getDocument(), true);
					retDoc = localApi.executeFlow(envStub, apiName, apiDoc.getDocument());
				} else {
					preInvoke(envStub, apiName, apiDoc.getDocument(), false);
					retDoc = localApi.invoke(envStub, apiName, apiDoc.getDocument());
				}
			}

			if (null == retDoc) {
				cat.debug("API returned a null doc. will create an empty doc");
				retDoc = YFCDocument.createDocument("ApiSuccess").getDocument();
			}
			if (!YFCCommon.isVoid(retDoc)){
				YFCDocument dDoc = YFCDocument.getDocumentFor(retDoc);
				try {
					if (!YFCCommon.isVoid(templateData)){
						evaluateDataProviders(envStub, templateDoc, dDoc, apiDoc);
					}
				} catch (Exception e){
					
				}
				JSONObject obj = XML.toJSONObject(dDoc.toString());
				res.getWriter().write(obj.toString(3));
			}
			//YFCDocument.getDocumentFor(retDoc).serialize(res.getWriter());
		} catch (SAXException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "SAX Exception while invoking api {1}", new String[] { apiName }), e);
			res.getWriter().write(new YFCException(e).getXMLErrorBuf());
		} catch (IOException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "IO Exception while invoking api {1}", new String[] { apiName }), e);
			res.getWriter().write(new YFCException(e).getXMLErrorBuf());
		} catch (YFSException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "YFS Exception while invoking api {1}", new String[] { apiName }), e);
			res.getWriter().write(e.getEncodedMessage());
		} catch (YFCException e) {
			cat.fatal(G11N.getString(G11N.AFC_DEFAULT_BUNDLE, "YFC Exception while invoking api {1}", new String[] { apiName }), e);
			res.getWriter().write(e.getXMLErrorBuf());
		}
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

}
