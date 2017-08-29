// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractSterlingUserExitImpl.java

package com.ibm.commerce.sterling.ue;

import com.ibm.commerce.foundation.common.datatypes.*;
import com.ibm.commerce.foundation.common.security.auth.callback.handler.IdentityTokenCallbackHandlerImpl;
import com.ibm.commerce.foundation.common.security.auth.callback.handler.WebSessionTokenCallbackHandlerImpl;
import com.yantra.interop.client.ClientVersionSupport;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public abstract class BDAAbstractSterlingUserExitImpl
{

	private static YFCLogCategory logger = YFCLogCategory.instance(BDAAbstractSterlingUserExitImpl.class);
    public BDAAbstractSterlingUserExitImpl()
    {
        iDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        iClientProperties = null;
    }

    protected void initClientProperties(YFSEnvironment environment)
    {
        ClientVersionSupport clientContext = (ClientVersionSupport)environment;
        iClientProperties = clientContext.getClientProperties();
    }

    protected CallbackHandler createCallbackHandler()
    {
        CallbackHandler callbackHandler = null;
        if(iClientProperties != null)
            if(iClientProperties.containsKey("WC_USERACTIVITY"))
            {
                String authenticationCookie = (String)iClientProperties.get("WC_USERACTIVITY");
                try
                {
                    callbackHandler = new WebSessionTokenCallbackHandlerImpl(URLEncoder.encode(authenticationCookie, "UTF-8"));
                }
                catch(UnsupportedEncodingException e)
                {
                	logger.debug("createCallbackHandler() - Error creating WebSessionTokenCallbackHandlerImpl, defaulting to null");
                }
            } else
            if(iClientProperties.containsKey("identityId") && iClientProperties.containsKey("identitySignature"))
            {
                String identity = (String)iClientProperties.get("identityId");
                String signature = (String)iClientProperties.get("identitySignature");
                callbackHandler = new IdentityTokenCallbackHandlerImpl(identity, signature);
            }
        return callbackHandler;
    }

    protected BusinessContextType createBusinessContext(String sOrganizationCode)
    {
        BusinessContextType businessContext = CommerceFoundationFactory.eINSTANCE.createBusinessContextType();
        
        String storeId = getStoreId(sOrganizationCode);
        if(storeId != null && !storeId.trim().isEmpty())
        {
            ContextDataType storeContext = CommerceFoundationFactory.eINSTANCE.createContextDataType();
            storeContext.setName("storeId");
            storeContext.setValue(storeId);
            businessContext.getContextData().add(storeContext);
        }
        if(iClientProperties != null)
        {
            if(iClientProperties.containsKey("workspaceName"))
            {
                ContextDataType workspaceContext = CommerceFoundationFactory.eINSTANCE.createContextDataType();
                workspaceContext.setName("workspace.name");
                workspaceContext.setValue((String)iClientProperties.get("workspaceName"));
                businessContext.getContextData().add(workspaceContext);
                businessContext.setIntent("Authoring");
            }
            if(iClientProperties.containsKey("workspaceTaskGroup"))
            {
                ContextDataType taskGroupContext = CommerceFoundationFactory.eINSTANCE.createContextDataType();
                taskGroupContext.setName("workspace.taskGroup");
                taskGroupContext.setValue((String)iClientProperties.get("workspaceTaskGroup"));
                businessContext.getContextData().add(taskGroupContext);
            }
            if(iClientProperties.containsKey("workspaceTask"))
            {
                ContextDataType taskContext = CommerceFoundationFactory.eINSTANCE.createContextDataType();
                taskContext.setName("workspace.task");
                taskContext.setValue((String)iClientProperties.get("workspaceTask"));
                businessContext.getContextData().add(taskContext);
            }
        }
        if(getLocaleCode() != null)
        {
            ContextDataType localeContext = CommerceFoundationFactory.eINSTANCE.createContextDataType();
            localeContext.setName("locale");
            localeContext.setValue(getLocaleCode());
            businessContext.getContextData().add(localeContext);
        }
        return businessContext;
    }

    protected String getCurrency()
    {
        String currency = null;
        if(iClientProperties != null)
            currency = (String)iClientProperties.get("currency");
        return currency;
    }

    protected String getStoreId(String sOrganizationCode)
    {
        String storeId = CommerceIntegrationMap.getCommerceValue("storeIdToOrganizationCode", sOrganizationCode);
        if(iClientProperties != null && iClientProperties.containsKey("storeId"))
            storeId = (String)iClientProperties.get("storeId");
        return storeId;
    }

    protected String getContractId(String sOrganizationCode)
    {
        String contractId = null;
        if(iClientProperties != null)
            contractId = (String)iClientProperties.get("contractId");
        return contractId;
    }

    protected String getCatalogId(String sOrganizationCode)
    {
    	String catalogId = CommerceIntegrationMap.getCommerceValue("catalogIdToOrganizationCode", sOrganizationCode);
        if(iClientProperties != null && iClientProperties.containsKey("catalogId"))
            catalogId = (String)iClientProperties.get("catalogId");
        return catalogId;
    }

    protected String getLocaleCode(){
    	String sLocale = "en_US";
    	if(iClientProperties != null && iClientProperties.containsKey("locale")){
    		sLocale = iClientProperties.get("locale");
    	}
    	return sLocale;	
    }
    protected static String convertDocumentToString(Document xmlDocument)
    {
        String response = null;
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(xmlDocument);
            transformer.transform(source, result);
            response = result.getWriter().toString();
        }
        catch(TransformerConfigurationException e)
        {
            e.printStackTrace();
        }
        catch(TransformerFactoryConfigurationError e)
        {
            e.printStackTrace();
        }
        catch(TransformerException e)
        {
            e.printStackTrace();
        }
        return response;
    }
    protected SimpleDateFormat iDateFormat;
    protected Map<String, String> iClientProperties;
    protected static final String UNIT_OF_MEASURE = "EACH";
    protected static final String COMMERCE_UNIT_OF_MEASURE = "C62";

}
