// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WCMsgContext.java

package com.ibm.commerce.msgservice;

import com.comergent.api.msgService.MsgContext;
import com.comergent.dcm.core.ComergentRequest;
import com.ibm.commerce.foundation.common.util.logging.LoggingHelper;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

public class WCCMGTMsgContext extends MsgContext
{

    public WCCMGTMsgContext()
    {
        addWCContextInfo();
    }

    private void addWCContextInfo()
    {
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "addWCContextInfo()");
        HashMap<String, String> wcContextParamMap = getWCContextParamMap();
        if(wcContextParamMap != null && !wcContextParamMap.isEmpty())
        {
            String storeId = (String)wcContextParamMap.get("storeId");
            if(storeId != null && storeId.trim().length() > 0)
            {
                setAttribute("WCStoreId", storeId);
                setAttribute("YFSEnvironment.storeId", storeId);
                
                StringBuilder properties = new StringBuilder("<Props>");
                for(String key : wcContextParamMap.keySet()){
                	properties.append("<Prop propertyName=\"").append(key).append("\" propertyValue=\"").append(wcContextParamMap.get(key)).append("\" />");
                }
                properties.append("</Props>");
                
                setAttribute("YFSEnvironment.clientProperties",properties.toString());
              //  setAttribute("clientProperties", wcContextParamMap);
                
                if(LoggingHelper.isTraceEnabled(LOGGER))
                    LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "addWCContextInfo()", (new StringBuilder("Set WCStoreId ")).append(storeId).append(" into MsgContext").toString());
            }
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "addWCContextInfo()");
    }

    private static HashMap<String, String> getWCContextParamMap()
    {
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "getWCContextParamMap()");
        HashMap<String, String> wcContextParamMap = new HashMap<String, String>();
        String wcContextParams = getWCContextCookieValue();
        if(wcContextParams != null && wcContextParams.trim().length() > 0)
        {
            String paramsArray[] = wcContextParams.split("\\&");
            if(paramsArray != null)
            {
                String as[];
                int j = (as = paramsArray).length;
                for(int i = 0; i < j; i++)
                {
                    String param = as[i];
                    if(param != null && param.trim().length() > 0)
                    {
                        String paramValuePair[] = param.split("=");
                        if(paramValuePair != null && paramValuePair[0] != null && paramValuePair[1] != null)
                            wcContextParamMap.put(paramValuePair[0], paramValuePair[1]);
                    }
                }

            }
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "getWCContextParamMap()");
        return wcContextParamMap;
    }

    public static String getWCContextCookieValue()
    {
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "getWCContextCookieValue()");
        String wcContextParams = "";
        ComergentRequest request = ComergentRequest.getCurrentRequest();
        if(request != null)
        {
            Cookie cookies[] = request.getCookies();
            if(cookies != null)
            {
                Cookie acookie[];
                int j = (acookie = cookies).length;
                for(int i = 0; i < j; i++)
                {
                    Cookie cookie = acookie[i];
                    if("WCContextCookie".equals(cookie.getName()))
                    {
                        String value = cookie.getValue();
                        if(value != null && value.trim().length() > 0){
                            wcContextParams = value;
                            break;
                        }
                    }
                }

            }
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "getWCContextCookieValue()", wcContextParams);
        return wcContextParams;
    }

    private static final String CLASSNAME = WCCMGTMsgContext.class.getName();
    private static final Logger LOGGER = LoggingHelper.getLogger(WCCMGTMsgContext.class);

}
