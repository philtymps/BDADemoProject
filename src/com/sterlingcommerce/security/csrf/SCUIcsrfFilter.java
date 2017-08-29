package com.sterlingcommerce.security.csrf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yantra.ycp.rcp.backend.YRCServlet;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCException;

public class SCUIcsrfFilter
  implements Filter
{
  private static final YFCLogCategory cat = YFCLogCategory.instance(SCUIcsrfFilter.class);
  private static final String CONSOLE_CSRF_DEFAULT_FORWARD_PAGE = "console_csrf_default_forward_page";
  private static final String CONSOLE_CSRF_REQUEST_WARNING = "console-csrf-request-warning-mode";
  private static final String EXCEPTION_IN_CSRF_TOKEN_VALIDATION = "exception in authenticating csrf token";
  private static final String CSRF_TOKEN_VALIDATION_RCP = "csrf_token_validation_rcp";
  private String defaultForwardPage = null;
  private boolean throwExceptions = true;

  public void destroy()
  {
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    if (!(req instanceof HttpServletRequest)) {
      chain.doFilter(req, res);
      return;
    }
    Exception exception = null;
    SCUIcsrfValidationResponse validationResponse = null;
    HttpServletRequest request = (HttpServletRequest)req;
    if (!SCUIcsrfHelper.isSecurityEnabled(request)) {
      chain.doFilter(req, res);
      return;
    }
    HttpServletResponse response = (HttpServletResponse)res;
    try {
      validationResponse = validateRequest(request, response);
    } catch (Exception e) {
      exception = e;
    }
    if ((validationResponse != null) && (validationResponse.status)) {
      chain.doFilter(req, getResponseWrapper(response, request));
      return;
    }
   // handleErrors(request, response, chain, exception, validationResponse);
  }

  private String getDefaultForwardPage(HttpServletRequest request) {
    if (this.defaultForwardPage == null) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        this.defaultForwardPage = session.getServletContext().getInitParameter("console_csrf_default_forward_page");
      }
      else {
        this.defaultForwardPage = request.getSession(true).getServletContext().getInitParameter("console_csrf_default_forward_page");
      }
    }
    if (this.defaultForwardPage == null) {
      this.defaultForwardPage = "/console/login.jsp";
    }
    return this.defaultForwardPage;
  }

  private SCUIcsrfResponseWrapper getResponseWrapper(HttpServletResponse response, HttpServletRequest request) {
    if ((response instanceof SCUIcsrfResponseWrapper)) {
      return (SCUIcsrfResponseWrapper)response;
    }
    return new SCUIcsrfResponseWrapper(response, request);
  }

  private void handleErrors(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Exception exception, SCUIcsrfValidationResponse validationResponse) throws ServletException, IOException {
    if ((exception == null) && (!validationResponse.status)) {
      exception = validationResponse.securityException == null ? validationResponse.securityException : new YFCException("exception in authenticating csrf token :" + request.getRequestURI());
    }
    if (this.throwExceptions) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.invalidate();
      }
      cat.error("exception in authenticating csrf token", exception);
      if (SCUIcsrfHelper.isRCPRequest(request)) {
        YRCServlet.sendLogonResponse(response, request, false, "csrf_token_validation_rcp", null);
        return;
      }
      String fUrl = validationResponse == null ? null : validationResponse.forwardURL;
      request.getRequestDispatcher(fUrl == null ? getDefaultForwardPage(request) : fUrl).forward(request, response);
    } else {
      StringBuilder sb = new StringBuilder("exception in authenticating csrf token").append("URL = ").append(request.getRequestURL()).append(" ',headers : ");

      cat.error(sb.toString(), exception);
      chain.doFilter(request, getResponseWrapper(response, request));
    }
  }

  public void init(FilterConfig config)
    throws ServletException
  {
    if (cat.isDebugEnabled()) {
      cat.debug("initializing csrf security configurations");
    }
    SCUIcsrfHelper.init(config);
    if ("debug".equals(config.getServletContext().getInitParameter("console-csrf-request-warning-mode"))) {
      if (cat.isDebugEnabled()) {
        cat.debug("validtion mode set to 'debug' ");
      }
      this.throwExceptions = false;
    }
  }

  private SCUIcsrfValidationResponse validateRequest(HttpServletRequest request, HttpServletResponse response) {
    if ((request.getSession(false) != null) && (SCUIcsrfHelper.isLocalUri(request))) {
      SCUIcsrfValidationResponse validationresponse = SCUIcsrfHelper.getTokenValidator(request).validate(request, response);
      return validationresponse;
    }
    if (cat.isDebugEnabled()) {
      cat.debug("skipping csrf check for uri :" + request.getRequestURI());
    }

    return SCUIcsrfHelper.SUCCESS_VALIDATION_RESPONSE;
  }
}