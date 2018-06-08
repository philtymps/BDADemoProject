package com.extension.bda.service.store;

import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetNodeCapacityDetails implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getNodeCapacityDetails";
	}

	@Override
	public void setProperties(Properties props) {
		
	}

	private YFCDocument getOrgTemplate() {
		YFCDocument dOrg = YFCDocument.createDocument("Organization");
		YFCElement eOrganization = dOrg.getDocumentElement();
		eOrganization.setAttribute("OrganizationCode", "");
		eOrganization.setAttribute("CapacityOrganizationCode", "");
		eOrganization.setAttribute("InventoryOrganizationCode", "");
		return dOrg;
	}
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		try {
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eInput = dInput.getDocumentElement();
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			
			if(!YFCCommon.isVoid(eInput.getAttribute("Node")) && !YFCCommon.isVoid(eInput.getAttribute("ProviderOrganizationCode"))) {
				if(YFCCommon.isVoid(eInput.getAttribute("CapacityOrganizationCode"))) {
					env.setApiTemplate("getOrganizationHierarchy", getOrgTemplate().getDocument());
					YFCDocument dOrgInput = YFCDocument.createDocument("Organization");
					dOrgInput.getDocumentElement().setAttribute("OrganizationCode", eInput.getAttribute("ProviderOrganizationCode"));
					Document orgOutput = localApi.invoke(env, "getOrganizationHierarchy", dOrgInput.getDocument());
					eInput.setAttribute("CapacityOrganizationCode", orgOutput.getDocumentElement().getAttribute("CapacityOrganizationCode"));
				}
				
				Document l_OutputXml = localApi.invoke(env, "getResourcePoolCapacity", dInput.getDocument());
				YFCElement eResponse = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				return eResponse.getOwnerDocument().getDocument();
			}			
		} catch(Exception yex) {
			yex.printStackTrace();
        } 
		return null;
	}

}
