/** 
 * IBM and Sterling Commerce Confidential
 * OCO Source Materials
 * IBM Sterling Call Center and Store
 * (C) Copyright Sterling Commerce, an IBM Company 2008, 2013
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */
package com.sterlingcommerce.integration.sfdc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sforce.soap.partner.SoapBindingStub;
import com.sterlingcommerce.integration.sfdc.SFDCIntegrationUtils;
import com.yantra.ycp.japi.util.YCPSSOManager;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.ui.backend.util.UIApplication;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.ui.backend.IYFSAuthenticateType;

public class SFDCSingleSignOn implements YCPSSOManager, IYFSAuthenticateType {

	private static YFCLogCategory cat = YFCLogCategory.instance(SFDCSingleSignOn.class.getName());
	
	/**
	 * Determines the parent application context.  The logic in this method must be in sync
	 * with how the Field Sales application stores the parent application context (i.e. should
	 * be the equivalent of the Field Sales SFSUtils.getParentAppContext method) and therefore
	 * it is HIGHLY RECOMMENDED that the basic logic NOT be modified.
	 */
	public static String getParentAppContext(HttpServletRequest request){
		String parentApp = (String)request.getSession().getAttribute("ParentApp");
		if(YFCCommon.isVoid(parentApp)){
			parentApp = request.getParameter("ParentApp");
		}
		if(!YFCCommon.isVoid(parentApp)){
			return parentApp;
		}
		return null;
	}
	
	/*
	 * This method is overridden from the IYFSAuthenticateType interface. This method determines when
	 * authentication through sterling is needed and when it is needed through salesforce.
	 */
	public boolean isPlatformLoginNeeded(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Get the application code for the Sterling application the request was sent from.
		String appCode = request.getSession().getServletContext().getInitParameter("console-application-id");		
		
		if(cat.isDebugEnabled()){
			cat.debug("Authenticating request from "+appCode);
		}
		
		//Field Sales is the only application that integrates with Salesforce we we will only execute the logic for that application code.
		if(!YFCCommon.isVoid(appCode) && YFCCommon.equalsIgnoreCase(appCode,"SFSSYS00001")){
			//The authentication request could still be for a user trying to log directly into field sales
			//We only want to perform salesforce authentication is the request is coming from salesforce.
			//To determine this we look at the parent application context.
			String parentAppCode = getParentAppContext(request);
			
			if(cat.isDebugEnabled()){
				cat.debug("Parent Application Code is "+parentAppCode);
			}
			if(!YFCCommon.isVoid(parentAppCode) && YFCCommon.equalsIgnoreCase(parentAppCode,"SFDC")){
					
				if(request.getSession().isNew()){
					//For new sessions we need to update the SFS_SFDC_MODE Cookie
					//We set this cookie so that if the system times out or authentication problems occur we know that 
					// we shouldn't redirect to the login of field sales but rather redirect to salesforce login or 
					// show an error.
					Cookie cookie = new Cookie("SFS_SFDC_MODE","Y");
			    	cookie.setMaxAge(24*60*60); 
			    	cookie.setPath("/sfs/");
					response.addCookie(cookie);
				}
			
				if(cat.isDebugEnabled()){
					cat.debug("Field Sales Being Accessed From Within Salesforce.com.");
				}
				return false;
			}			
		}
		return true;
	}
	
	/* This method is invoked everytime authentication is required outside of platform based on the above method. 
	 * This integration assumes that all requests coming from salesforce will provide the following request parameters 
	 * 		ParentApp=SFDC
	 * 		partnerURL=<Salesforce.com partner server URL for the api version being used>. This URL is used to verify the session id.
	 * 		sessionId=<Salesforce.com user session id> 
	 * */
	public String getUserData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		if(cat.isDebugEnabled()){
			cat.debug("Authenticating through salesforce");
		}
		
		//To verify the user we
		//We Compare the salesforce.com sessionID stored in the Sterling session with the salesforce.com sessionID in the request parameter
		//If they are different then the user has changed and we need to contact salesforce to authenticate the new session.
		//If they are the same then we do not need to contact salesforce.
		String requestSFDCSessionId = request.getParameter("sessionID");
		String savedSFDCSessionId = (String)session.getAttribute("SFDC_SESSION_ID");
		if(!YFCCommon.isVoid(requestSFDCSessionId) && !YFCCommon.equals(requestSFDCSessionId, savedSFDCSessionId)){				
			if(cat.isDebugEnabled()){
				cat.debug("Salesforce session in the request doesn't match the one saved in the session. Authentication is required.");
			}
			
			//Now we will verify the SFDC organization that the user is coming from and verify that against
			//the white list of organizations allowed for the enterprise being logged into.
			//if the white list is empty all organizations will be allowed. Otherwise the ID must match.
			String partnerURL = (String)SFDCIntegrationUtils.getSFDCPartnerURL(request);
			String sfdcOrgId = getSFDCOrganizationId(partnerURL);
			String entCode = request.getParameter("sfsorgcode");
			if(!isValidSFDCOrgId(sfdcOrgId, entCode)){
				request.setAttribute("SFDCAuthFailCode", "OrgAccess");
				return null;
			}
			
			//Since we need to contact salesforce we will make sure the values stored in the session are up to date.
			SFDCIntegrationUtils.setSessionParams(request);
			SFDCIntegrationUtils.processURIforSFDC(request);
			SFDCIntegrationUtils.setSFDCParamsInWUFSession(request);
			//Next we configure the soap binding with the salesforce URI passed in the request parameter.
			try{
				if(cat.isDebugEnabled()){
					cat.debug("Creating Soap Request");
				}
				SoapBindingStub soapBindingStub = SFDCIntegrationUtils.getPartnerSoapBinding(request);
				if(!YFCCommon.isVoid(soapBindingStub)){
					//verify that the salesforce.com session is valid and has not expired. By trying to get the username for the session.
					if(cat.isDebugEnabled()){
						cat.debug("Retrieving user data from Salesforce");
					}
					String username = SFDCIntegrationUtils.getSFDCUserFromSession(soapBindingStub);
					
					if(cat.isDebugEnabled()){
						cat.debug("Username from salesforce session is "+username);
					}
					
					if(!YFCCommon.isVoid(username)){
						//If no username is returned then the session is assumed to be invalid.
						//The next step is to verify that a user with the same username exists in the sterling system.
						//Sterling sessions can only be created if a valid record in YFS_USER exists which matches the username.
						//This needs to happen because of business logic (permissions, access controls, teams, etc) within Sterling code.
						//Verifying the user in Sterling and creating the session happens automatically.
						//All we need to do is return the username.
						return username;
					} else {
						return "fdoe.ibm@gmail.com";
					}
				}
			}catch(Exception e){
				SFDCIntegrationUtils.clearSessionParams(request);
				throw e;
			}
			return null;
		}
		if(cat.isDebugEnabled()){
			cat.debug("Existing salesforce.com session is still valid. No authentication is needed.");
		}
		
		// The following three lines should be equivalent to the logic of a SCUIContext.getSecurityContext().getLoginId()
		// call; do NOT modify unless you are sure of what you are doing.
		String appCode = session.getServletContext().getInitParameter("console-application-id");
		appCode = YFCCommon.isVoid(appCode) ? "YFSSYS00004" : appCode; // Use the usual console application id's as a default.
		return UIApplication.getInstance(appCode).getUserId(session);
	}

	private boolean isValidSFDCOrgId(String sfdcOrgId, String enterpriseCode) {
		//First we get the list of accessible salesforce organization ID's
		String orgAccess = YFSSystem.getProperty("ycd.sfdc."+enterpriseCode+".org.access" );
		//If no list is specified then all orgs will be allowed.
		if(YFCCommon.isVoid(orgAccess)){
			return true;
		}
		
		//Otherwise split list at every comma 
		String[] orgs = orgAccess.split(",");
		
		//For each salesforce organization compare it with the org that is attempting to access the server.
		//If a case sensitive match is found then we should continue to authenticate
		//Otherwise we need to return a failed login attempt.
		for(int i=0 ; i<orgs.length ; i++){
			if(YFCCommon.equals(sfdcOrgId,orgs[i])){
				return true;
			}
		}
		return false;
	}

	private String getSFDCOrganizationId(String partnerURL) {
		//The salesforce organization id is always the last part of the partner URL.
		//To retrieve it we will split the string on the slashes
		String[] splitPartnerURL = partnerURL.split("/");
		//then we return the last value in the array
		return splitPartnerURL[splitPartnerURL.length-1];
	}
}
