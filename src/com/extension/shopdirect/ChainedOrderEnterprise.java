package com.extension.shopdirect;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class ChainedOrderEnterprise {
	
	public Document setChainedOrderEnterprise(YFSEnvironment env, Document input){
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		
		YFCDocument dOrg = YFCDocument.createDocument("Organization");
		YFCElement eOrg = dOrg.getDocumentElement();
		if(!YFCCommon.isVoid(eInput.getAttribute("SellerOrganizationCode"))){
			if(!YFCCommon.equals(eInput.getAttribute("DocumentType"), "0001")){
				eOrg.setAttribute("OrganizationCode", eInput.getAttribute("SellerOrganizationCode"));
				YFCDocument getOrgTemplate = YFCDocument.createDocument("Organization");
				YFCElement template = getOrgTemplate.getDocumentElement();
				template.setAttribute("OrgnaizationCode", "");
				template.setAttribute("PrimaryEnterpriseKey", "");
				try {
					env.setApiTemplate("getOrganizationHierarchy", getOrgTemplate.getDocument());
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document response = localApi.invoke(env, "getOrganizationHierarchy", dOrg.getDocument());
					YFCElement eResponse = YFCDocument.getDocumentFor(response).getDocumentElement();
					eInput.setAttribute("EnterpriseCode", eResponse.getAttribute("PrimaryEnterpriseKey"));
				} catch (Exception e){
					e.printStackTrace();
				}	
			}
			
		}
		
		return dInput.getDocument();
		
	}
}
