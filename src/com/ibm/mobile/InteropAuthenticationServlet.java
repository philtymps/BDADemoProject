package com.ibm.mobile;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.yantra.interop.client.InteropEnvStub;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.shared.dbclasses.PLT_CallOut_ImplDBHome;
import com.yantra.shared.dbclasses.YFS_User_ActivityDBHome;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.ycp.core.YCPContext;
import com.yantra.ycp.core.YCPEntityApi;
import com.yantra.ycp.customdb.PLT_CallOut_ImplImpl;
import com.yantra.ycp.customdb.YFS_User_ActivityImpl;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.entityapi.YFCEntityApiImpl;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSException;

public class InteropAuthenticationServlet extends InteropJSONServlet {
	
	private static final long serialVersionUID = 9151009009435772554L;

	protected boolean isAuthorized(String apiName, HttpServletRequest req) throws YFSException, RemoteException{
		HttpSession session = req.getSession(false);
    	boolean authorized = false;
    	if(!YFCCommon.isVoid(session) && !YFCCommon.isVoid(session.getAttribute("CurrentUser"))){
    		try {	
    			YFCEntityApiImpl userActivity = new YFCEntityApiImpl(
        				YFS_User_ActivityDBHome.getInstance(),
    					YFS_User_ActivityImpl.class);
        		
        		YFCDocument dInput = YFCDocument.createDocument("registerLogin");
    			YFCElement eInput = dInput.getDocumentElement();
    			eInput.setAttribute("UserId", (String) session.getAttribute("CurrentUser"));
    			eInput.setAttribute("SessionId", session.getId());
    			
    			YFCDocument dTemplate = YFCDocument.createDocument("UserActivityList");
    			YFCElement eTemplate = dTemplate.getDocumentElement().createChild("UserActivity");
    			eTemplate.setAttribute("UserActivityKey", "");
    			eTemplate.setAttribute("Modifyts", "");

                YFCElement eResponse = userActivity.list(new YCPContext(eInput.getAttribute("UserId"), "InteropAuthenticationServlet"), eInput, dTemplate.getDocumentElement(), YFCDocument.createDocument());	
                Timestamp twoHoursAgo = new Timestamp(System.currentTimeMillis() - (1000 * 60 * 60 * 2));
        		
                if(!YFCCommon.isVoid(eResponse) && !YFCCommon.isVoid(eResponse.getChildElement("UserActivity"))){
                	if(eResponse.getChildElement("UserActivity").getDateTimeAttribute("Modifyts").before(twoHoursAgo)){
                		localApi.registerLogout(getEnvStub(req), dInput.getDocument());
                	} else {
                		authorized = true;
                      	localApi.registerLogin(getEnvStub(req), dInput.getDocument());
                	}
                }
                
        		
    		} catch (Exception e){
    			authorized = false;
    		}  
    	}
    	return (authorized || YFCCommon.equals(apiName, "login"));
	}
	
	protected void authenticate(InteropEnvStub envStub, String apiName, HttpServletRequest req, YFCDocument dDoc) throws YFSException, RemoteException{
		if(YFCCommon.equals(apiName, "login")){
			if(!YFCCommon.equals(dDoc.getNodeName(), "Errors")){
				HttpSession session = req.getSession(true);
				session.setAttribute("CurrentUser", dDoc.getDocumentElement().getAttribute("LoginID"));
				YFCDocument dInput = YFCDocument.createDocument("registerLogin");
				YFCElement eInput = dInput.getDocumentElement();
				eInput.setAttribute("UserId", dDoc.getDocumentElement().getAttribute("LoginID"));
				eInput.setAttribute("SessionId", session.getId());
				localApi.registerLogin(envStub, dInput.getDocument());
			}
		}
	}
	
	protected Document handleCustomApi(InteropEnvStub envStub, HttpServletRequest req, String apiName, YFCDocument apiDoc, String isFlow, YFCDocument templateDoc){
		
		return null;
	}

}
