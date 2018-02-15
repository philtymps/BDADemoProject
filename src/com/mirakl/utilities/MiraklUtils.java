package com.mirakl.utilities;

import com.ibm.CallInteropServlet;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class MiraklUtils {

	public static boolean organizationExists(String sOrgCode){
		YFCDocument dInput = YFCDocument.createDocument("Organization");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrganizationCode", sOrgCode);
		YFCDocument dTemplate = YFCDocument.createDocument("OrganizationList");
		YFCElement eTemplate = dTemplate.getDocumentElement();
		eTemplate.createChild("Organization").setAttribute("OrganizationCode","");
		
		YFCDocument dResponse = CallInteropServlet.invokeApi(dInput, dTemplate, "getOrganizationList", "http://oms.omfulfillment.com:9080");
		if(!YFCCommon.isVoid(dResponse)){
			YFCElement eResponse = dResponse.getDocumentElement();
			for(YFCElement eChild : eResponse.getChildren()){
				if(YFCCommon.equals(eChild.getAttribute("OrganizationCode"), sOrgCode)) {
					return true;
				}
			}
		}
		return false;
	}
}
