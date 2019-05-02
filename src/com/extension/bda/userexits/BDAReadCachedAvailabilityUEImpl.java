package com.extension.bda.userexits;

import java.rmi.RemoteException;

import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.ibm.iv.adapter.IVReadCachedAvailabilityUEImpl;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.OMPGetInventoryFromCacheUE;

public class BDAReadCachedAvailabilityUEImpl extends IVReadCachedAvailabilityUEImpl implements OMPGetInventoryFromCacheUE {

	@Override
	public Document getInventoryFromCache(YFSEnvironment env, Document input) throws YFSUserExitException {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();		
		
		if(eInput.getBooleanAttribute("UseDefaultDistributionRuleId", false) && YFCCommon.isVoid(eInput.getAttribute("DistributionRuleId"))) {
			YFCDocument dTemplate = YFCDocument.createDocument("Organization");
			YFCElement eTemplate = dTemplate.getDocumentElement();
			eTemplate.setAttribute("DefaultDistributionRuleId", "");
			eTemplate.setAttribute("OrganizationCode", "");
			
			YFCDocument dOrgInput = YFCDocument.createDocument("Organization");
			YFCElement eOrgInput = dOrgInput.getDocumentElement();
			eOrgInput.setAttribute("OrganizationCode", eInput.getAttribute("InventoryOrganizationCode"));
			Document orgOutput = callApi(env, dOrgInput.getDocument(), dTemplate.getDocument(), "getOrganizationHierarchy");
			YFCElement eOrg = YFCDocument.getDocumentFor(orgOutput).getDocumentElement();
			if(!YFCCommon.isVoid(eOrg) && !YFCCommon.isVoid(eOrg.getAttribute("DefaultDistributionRuleId"))) {
				eInput.setAttribute("DistributionRuleId", eOrg.getAttribute("DefaultDistributionRuleId"));
			}
		}
		
		return super.getInventoryFromCache(env, dInput.getDocument());
	}
	
	protected Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName){
		if(!YFCCommon.isVoid(env)) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.invoke(env, sApiName, inDoc);
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!YFCCommon.isVoid(dOrderOutput)){
				return dOrderOutput;
			}
			return null;
		} else {
			if(!YFCCommon.isVoid(dTemplate)) {
				return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, "https://oms.innovationcloud.info").getDocument();
			}
			return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), null, sApiName, "https://oms.innovationcloud.info").getDocument();
		}
		
	
	}

}
