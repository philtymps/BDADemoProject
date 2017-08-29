/* ******************************************************************************
IBM and Sterling Commerce Confidential
OCO Source Materials
IBM Sterling Selling and Fulfillment Suite - Foundation
(C) Copyright Sterling Commerce, an IBM Company 2010, 2012
The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
*******************************************************************************/
package com.sterlingcommerce.integration.sfdc;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.message.MessageElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.QueryOptions;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SessionHeader;
import com.sforce.soap.partner.SforceServiceLocator;
import com.sforce.soap.partner.SoapBindingStub;
import com.sforce.soap.partner.fault.InvalidSObjectFault;
import com.sforce.soap.partner.fault.UnexpectedErrorFault;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperBindingStub;
import com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperServiceLocator;
import com.sterlingcommerce.integration.sfdc.customapi.DefaultSFDCIntegrationBridge;
import com.sterlingcommerce.integration.sfdc.customapi.ISFDCIntegrationBridge;
import com.sterlingcommerce.integration.sfdc.customapi.SFDCErrorCodes;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.core.YFSSystem;

public class SFDCIntegrationUtils {
    public static String PROPERTY_TRUE = "true";
    
	private static YFCLogCategory cat = YFCLogCategory.instance(SFDCIntegrationUtils.class.getName());
	
    protected static final String ALMOST_SFDC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    
	public static void setSFDCUIURLs(HttpServletRequest request, SoapBindingStub soapBindingStub, String[] sObjects) throws InvalidSObjectFault, UnexpectedErrorFault, RemoteException{
		if(cat.isDebugEnabled()){
			cat.debug("Retrieving SFDC Object URLs");
		}
		HttpSession session = request.getSession();
		StringBuffer objectURLMap = new StringBuffer("{");
		DescribeSObjectResult descSObjectRslt;
        boolean firstURL=true;
		for(int i=0 ; i<sObjects.length ; i++){
        	String sObject = sObjects[i];
        	if(cat.isDebugEnabled()){
    			cat.debug("Getting URL for "+sObject);
    		}
        	descSObjectRslt = soapBindingStub.describeSObject(sObject);
            if (descSObjectRslt != null)
	        {
            	if(!firstURL){
            		objectURLMap.append(",");
            	}
            	if(cat.isDebugEnabled()){
        			cat.debug("URL for "+sObject+" is "+ descSObjectRslt.getUrlDetail());
        		}
            	objectURLMap.append(sObject).append(":'").append(descSObjectRslt.getUrlDetail()).append("'");
            	if(firstURL){
            		firstURL=false;
            	}
	        }
        }
        objectURLMap.append("}");
        session.setAttribute("SFDC_OBJECT_URL_MAP", objectURLMap.toString());
	}
	public static SciQuotingHelperBindingStub getCustomSoapBinding(HttpServletRequest request){
		String sessionId = SFDCIntegrationUtils.getSFDCSessionId(request);
		String serverURL = null;
		serverURL = SFDCIntegrationUtils.getSFDCCustomServiceURL(request);
		
		// make sure there are SSO values
		if (sessionId == null || serverURL == null) {
			return null;
		} else {
			sessionId = sessionId.trim();
			serverURL = serverURL.trim();
			if ("".equals(sessionId) || "".equals(serverURL)) {
				return null;
			}
		}
		SciQuotingHelperBindingStub quotingHelperBinding = null;

		try {
			quotingHelperBinding = (SciQuotingHelperBindingStub) new SciQuotingHelperServiceLocator().getSciQuotingHelper();
			// Time out after a minute
	        quotingHelperBinding.setTimeout(60000);
	
	        SessionHeader sh = new SessionHeader();
	        sh.setSessionId(sessionId);
	        
	        // set the session header for subsequent call authentication
	        quotingHelperBinding.setHeader(new SciQuotingHelperServiceLocator().getServiceName().getNamespaceURI(), "SessionHeader", sh);
	        
	        quotingHelperBinding._setProperty(SoapBindingStub.ENDPOINT_ADDRESS_PROPERTY, serverURL);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
       					
		return quotingHelperBinding;
	}
	public static SoapBindingStub getPartnerSoapBinding(HttpServletRequest request){
		String sessionId = SFDCIntegrationUtils.getSFDCSessionId(request);
		String serverURL = null;
		serverURL = SFDCIntegrationUtils.getSFDCPartnerURL(request);
		
		// make sure there are SSO values
		if (sessionId == null || serverURL == null) {
			return null;
		} else {
			sessionId = sessionId.trim();
			serverURL = serverURL.trim();
			if ("".equals(sessionId) || "".equals(serverURL)) {
				return null;
			}
		}
		SforceServiceLocator serviceLocator =new SforceServiceLocator();
		SoapBindingStub soapBindingStub=null;
		
		try {
			soapBindingStub = (SoapBindingStub) serviceLocator.getSoap();
			soapBindingStub.setTimeout(60000);
			soapBindingStub._setProperty(SoapBindingStub.ENDPOINT_ADDRESS_PROPERTY,	serverURL);
			SessionHeader sessionHeader = new SessionHeader(sessionId);
			String nameSpace = serviceLocator.getServiceName().getNamespaceURI();
			soapBindingStub.setHeader(nameSpace, "SessionHeader",	sessionHeader);
		} catch (ServiceException e) {
			cat.error(e);
		}
				
		return soapBindingStub;
	}
	
	public static String getSFDCUserFromSession(SoapBindingStub soapBindingStub){
		if(!YFCCommon.isVoid(soapBindingStub)){
			try {
				// set the header
				String userid = "";
				
				// invoke web service method and get the user id
				GetUserInfoResult userInfoResult = soapBindingStub.getUserInfo();
				if (!YFCCommon.isVoid(userInfoResult)) {
					//Since the user information was retrieved we know we have a valid salesforce session
					//Return the login id for the user to ensure a corresponding record exists in the Sterling.
					
					userid = userInfoResult.getUserName();
				}
				 
		        return userid;
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/*public static boolean validateSObject(SObject sObject, String[] requiredAttrs){
		MessageElement[] messageElements = sObject.get_any();
		int attrCount = requiredAttrs.length;
		for(int i=0 ; i<messageElements.length ; i++){
			MessageElement messageElement = messageElements[i];
            String name = messageElement.getName();
            
            for(int j=0 ; j<requiredAttrs.length ; j++){
            	String reqName = requiredAttrs[j];
            	if(reqName.equals(name)){
            		if(YFCCommon.isVoid(messageElement.getValue())){
            			return false;
            		}else{
            			attrCount--;
            		}
            	}
            }
		}
		return attrCount == 0;
	}*/
	
	protected static String getAttrFromMessage(MessageElement[] messageElements, String attrName)
    {
        for(int i=0; i < messageElements.length; i++)
        {
            MessageElement messageElement = messageElements[i];
            String name = messageElement.getName();
            if(attrName.equals(name)){
            	return messageElement.getValue();
            }
        }
        return null;
    }
	
	protected static QueryResult runQuery(SoapBindingStub binding, int batchSize, String[] requestedFields, String objectName, String whereClause) throws Exception
    {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setBatchSize(250);
        binding.setHeader(new SforceServiceLocator().getServiceName().getNamespaceURI(), "QueryOptions", queryOptions);
        
        StringBuffer queryStringBuffer = new StringBuffer("select ");
        int numbFields = requestedFields.length;
        for(int i=0; i < numbFields; i++)
        {
            queryStringBuffer.append(requestedFields[i]);
            if((i+1) != numbFields)
            {
                queryStringBuffer.append(",");
            }
            queryStringBuffer.append(" ");
        }
        queryStringBuffer.append("from ");
        queryStringBuffer.append(objectName);
        if(whereClause != null)
        {
            queryStringBuffer.append(" ");
            queryStringBuffer.append(whereClause);
        }
        
        QueryResult queryResult = binding.query(queryStringBuffer.toString());
        
        return queryResult;
    }
	
	public static String getSFDCSessionId(HttpServletRequest request){
		HttpSession session = request.getSession();
		String sessionId = "";
		if(!YFCCommon.isVoid(session)){
			sessionId = (String)session.getAttribute("SFDC_SESSION_ID");
		}
		if(YFCCommon.isVoid(sessionId)){
			sessionId = request.getParameter("sessionID");
			session.setAttribute("SFDC_SESSION_ID", sessionId);
		}
		return sessionId;
	}
	
	public static void setSessionParams(HttpServletRequest request){
		if(cat.isDebugEnabled()){
			cat.debug("Setting SFDC Session Attributes");
			cat.debug("SFDC_SESSION_ID:"+request.getParameter("sessionID"));
			cat.debug("SFDC_PARTNER_URL:"+request.getParameter("partnerURL"));
		}
		request.getSession().setAttribute("SFDC_SESSION_ID", request.getParameter("sessionID"));
		request.getSession().setAttribute("SFDC_PARTNER_URL", request.getParameter("partnerURL"));
	}
	
	public static void clearSessionParams(HttpServletRequest request) {
		if(cat.isDebugEnabled()){
			cat.debug("Clearing SFDC Session Attributes");
		}
		request.getSession().removeAttribute("SFDC_SESSION_ID");
		request.getSession().removeAttribute("SFDC_PARTNER_URL");
		request.getSession().removeAttribute("SFDC_OBJECT_URL_MAP");
		
	}
	
	public static String getSFDCPartnerURL(HttpServletRequest request){
		HttpSession session = request.getSession();
		String serverURL = "";
		if(!YFCCommon.isVoid(session)){
			serverURL = (String)session.getAttribute("SFDC_PARTNER_URL");
		}
		if(YFCCommon.isVoid(serverURL)){
			serverURL = request.getParameter("partnerURL");
			session.setAttribute("SFDC_PARTNER_URL", serverURL);
		}
		return serverURL;
	}
	
	public static String getSFDCCustomServiceURL(HttpServletRequest request){
		HttpSession session = request.getSession();
		String serverURL = "";
		if(!YFCCommon.isVoid(request)){
			serverURL = (String)session.getAttribute("SFDC_CUSTOM_URL");
		}
		if(YFCCommon.isVoid(serverURL)){
			serverURL = request.getParameter("customURL");
			session.setAttribute("SFDC_CUSTOM_URL", serverURL);
		}
		return serverURL;
	}
	
	public static void processURIforSFDC(HttpServletRequest request){
		//We need to make sure the objects have been set in the session so we can refer to them later. 
		SFDCIntegrationUtils.setSessionParams(request);
		SoapBindingStub soapBindingStub = SFDCIntegrationUtils.getPartnerSoapBinding(request);
		String[] sObjects = {"Opportunity"};
		try {
			SFDCIntegrationUtils.setSFDCUIURLs(request, soapBindingStub, sObjects);
		} catch (InvalidSObjectFault e) {
			cat.error(e);
		} catch (UnexpectedErrorFault e) {
			cat.error(e);
		} catch (RemoteException e) {
			cat.error(e);
		}
	}
	
	public static void setSFDCParamsInWUFSession(HttpServletRequest request){
		String sessionID = getSFDCSessionId(request);
		
		Element outputElem = YFCDocument.getDocumentFor("<SFDC SessionID=\""+sessionID+"\" />").getDocument().getDocumentElement();
		request.getSession().setAttribute("SFDCParams", outputElem);		
	}
    
    /**
     * Returns the enterprise's configured SFDC username.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the SFDC username for. 
     * @return the enterprise's configured SFDC username.
     */
    public static String getConfiguredSFDCUsername(String enterpriseCode)
    {
        String userId = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".userid" );
        
        return userId;
    }
    
    /**
     * Returns the enterprise's configured SFDC password.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the SFDC username for. 
     * @return the enterprise's configured SFDC password.
     */
    public static String getConfiguredSFDCPassword(String enterpriseCode)
    {
        String password = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".password");
        
        return password;
    }

    /**
     * Returns the enterprise's configured SciQuotingHelper URL.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredSciQuotingHelperUrl(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".url");
        
        return value;
    }

    /**
     * Returns the enterprise's configured SFDC "force single currency" value.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredSFDCForceSingleCurrency(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".forceSingleCurrency");
        
        return value;
    }
    
    /**
     * Returns the enterprise's configured SFDC integration bridge class value.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredIntegrationBridgeClass(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".customIntegrationBridgeClass");
        
        return value;
    }
    
    /**
     * Returns the enterprise's configured createUserHierarchy override service value.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredCreateUserHierarchyOverrideService(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".createUserHierarchyOverrideService");
        
        return value;
    }
    
    /**
     * Returns the enterprise's configured modifyUserHierarchy override service value.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredModifyUserHierarchyOverrideService(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".modifyUserHierarchyOverrideService");
        
        return value;
    }
    
    /**
     * Returns the enterprise's configured getUserHierarchy override service value.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredGetUserHierarchyOverrideService(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".getUserHierarchyOverrideService");
        
        return value;
    }
    
    /**
     * Returns the enterprise's configured getUserHierarchy override service API value.
     * 
     * @param enterpriseCode the enterprise code of the enterprise
     *                       to obtain the value for. 
     * @return the enterprise's configured value.
     */
    public static String getConfiguredGetUserHierarchyOverrideServiceApi(String enterpriseCode)
    {
        String value = YFSSystem.getProperty("ycd.sfdc." + enterpriseCode + ".getUserHierarchyOverrideServiceApi");
        
        return value;
    }
    
    /**
     * Returns an instance of the proper SFDC integration bridge class for the given
     * enterprise.  If the enterprise is configured for a custom integration bridge class,
     * an instance of that class will be returned; otherwise an instance of DefaultSFDCIntegrationBridge
     * will be returned.
     * 
     * @param enterpriseCode the enterprise code of the enterprise to obtain the bridge class for.
     * @return an an instance of the proper SFDC integration bridge class for the given
     *         enterprise.
     */
    public static ISFDCIntegrationBridge getIntegrationBridgeImpl(String enterpriseCode) throws Exception
    {
        String className = SFDCIntegrationUtils.getConfiguredIntegrationBridgeClass(enterpriseCode);
        
        if(!YFCCommon.isVoid(className))
        {
            try
            {
                ISFDCIntegrationBridge impl = (ISFDCIntegrationBridge)Class.forName(className).newInstance();
                return impl;
            }
            catch(ClassNotFoundException e)
            {
                YFCException yfce = new YFCException(e, SFDCErrorCodes.SFDC_INVALID_CUSTOM_BRIDGE_CLASS_NONEXISTENT);
                yfce.setAttribute("EnterpriseCode", enterpriseCode);
                yfce.setAttribute("customIntegrationBridgeClass", className);
                throw yfce;
            }
            catch(ClassCastException e)
            {
                YFCException yfce = new YFCException(e, SFDCErrorCodes.SFDC_INVALID_CUSTOM_BRIDGE_CLASS_TYPE);
                yfce.setAttribute("EnterpriseCode", enterpriseCode);
                yfce.setAttribute("customIntegrationBridgeClass", className);
                throw yfce;
            }
        }
        else
        {
            return new DefaultSFDCIntegrationBridge();
        }
    }

    /**
     * Converts the given date into an SFDC compatible date/time format.
     * 
     * @param date the date to convert.
     * @return the given date in SFDC compatible date/time format.
     */
    public static String convertToSFDCDateTimeFormat(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(ALMOST_SFDC_DATE_FORMAT);
        StringBuffer sb = new StringBuffer(sdf.format(date));
        sb.insert(sb.length() - 2, ':'); // Yes, the colon really IS required or else -0700 gets interpreted as "minus 700 hours" throwing the time off by almost a month
        
        return sb.toString();
    }

    /**
     * Builds a string representation of the given document.
     * This method is useful for logging and debugging purposes.
     * 
     * @param doc the document.
     * @return a string representation of the given document.
     */
    public static String getStringFromDocument(Document doc)
    {
        if(doc == null)
        {
            return "[getStringFromDocument: null]";
        }
        
        try
        {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(TransformerException ex)
        {
            cat.error(ex);
            return "[getStringFromDocument: TransformerException]";
        }
    }

    /**
     * Builds a string representation of the given document.
     * This method is useful for logging and debugging purposes.
     * 
     * @param doc the document.
     * @return a string representation of the given document.
     */
    public static String getStringFromDocument(YFCDocument doc)
    {
        if(doc == null)
        {
            return getStringFromDocument((Document) null);
        }
        else
        {
            return getStringFromDocument(doc.getDocument());
        }
    }
}
