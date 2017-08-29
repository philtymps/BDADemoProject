// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WCContextCookieFilter.java

package com.ibm.commerce.filters;

import com.ibm.commerce.foundation.common.util.logging.LoggingHelper;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WCEXTNContextCookieFilter
    implements Filter
{

    public WCEXTNContextCookieFilter()
    {
        extParams = new ArrayList(0);
    }

    public void destroy()
    {
    }

    public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)
        throws IOException, ServletException
    {
        String METHODNAME = "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)";
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)");
        if(extParams.size() > 0)
        {
            HttpServletRequest request = (HttpServletRequest)sRequest;
            HttpServletResponse response = (HttpServletResponse)sResponse;
            if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)", (new StringBuilder("requestURL = ")).append(request.getRequestURL()).toString());
            String value = "";
            for(Iterator iterator = extParams.iterator(); iterator.hasNext();)
            {
                String param = (String)iterator.next();
                String paramVal = request.getParameter(param);
                if(paramVal == null)
                {
                    if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                        LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)", (new StringBuilder("Request doesn't contain the parameter '")).append(param).append("' - skipping ...").toString());
                } else
                {
                    value = (new StringBuilder(String.valueOf(value))).append("&").append(param).append("=").append(URLEncoder.encode(paramVal, "UTF-8")).toString();
                    if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                        LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)", (new StringBuilder("Adding parameter '")).append(param).append("' with original value '").append(paramVal).append("' as new value of '").append(URLEncoder.encode(paramVal, "UTF-8")).append("'to the WCS Context cookie value").toString());
                }
            }

            if(value.trim().length() > 0)
            {
                Cookie cookie = new Cookie("WCContextCookie", value);
                cookie.setMaxAge(-1);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
                if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                    LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)", (new StringBuilder("Context Parameters")).append(value).append("added to the cookie").toString());
            } else
            if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)", "No Context Parameters present in the input request");
        }
        chain.doFilter(sRequest, sResponse);
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)");
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        String METHODNAME = "init(FilterConfig filterConfig)";
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "init(FilterConfig filterConfig)");
        if(filterConfig == null)
        {
            if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "init(FilterConfig filterConfig)", "No filter config!");
            return;
        }
        String paramValue = filterConfig.getInitParameter("Parameters");
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "init(FilterConfig filterConfig)", (new StringBuilder("InitParameter - Parameters=")).append(paramValue).toString());
        if(paramValue != null && paramValue.length() > 0)
        {
            for(StringTokenizer st = new StringTokenizer(paramValue, ",", false); st.hasMoreTokens();)
            {
                String param = st.nextToken().trim();
                if(param.length() > 0)
                {
                    if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                        LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "init(FilterConfig filterConfig)", (new StringBuilder("adding parameter [")).append(param).append("] to the list").toString());
                    extParams.add(param);
                }
            }

        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "init(FilterConfig filterConfig)");
    }

    private static final String CLASSNAME = WCEXTNContextCookieFilter.class.getName();
    private static final java.util.logging.Logger LOGGER = LoggingHelper.getLogger(WCEXTNContextCookieFilter.class);
    private ArrayList extParams;

}
