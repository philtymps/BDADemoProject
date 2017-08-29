package com.ibm.utilities;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class updatePasswords {

	private Document getUserListTemplate(){
		YFCDocument dUserList = YFCDocument.createDocument("UserList");
		YFCElement eUserList = dUserList.getDocumentElement();
		YFCElement eUser = eUserList.createChild("User");
		eUser.setAttribute("Loginid","");
		eUser.setAttribute("Activateflag", "");
		eUser.setAttribute("Localecode","");
		return dUserList.getDocument();
	}
	public Document updatePasswords(YFSEnvironment env, Document dInput){
		if (!YFCCommon.isVoid(dInput) && !YFCCommon.isVoid(env)){
			YFCElement eInput = YFCDocument.getDocumentFor(dInput).getDocumentElement();
			String sAugment = eInput.getAttribute("PasswordDiff");
			if (YFCCommon.isVoid(sAugment) && eInput.getBooleanAttribute("SpecialOverride", false)){
				sAugment = "";
			} else {
				sAugment = "BDA#3";
			}
			YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
			YFCElement eMultiApi = dMultiApi.getDocumentElement();
			try {
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				Document l_OutputXml = null;
				try {
					YFCDocument dOrg = YFCDocument.createDocument("User");
					env.setApiTemplate("getUserList", getUserListTemplate());
					l_OutputXml = localApi.invoke(env, "getUserList", dOrg.getDocument());
					if(!YFCCommon.isVoid(l_OutputXml)){
						YFCElement eUserList = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
						for (YFCElement eUser : eUserList.getChildren()){
							YFCElement eApi = eMultiApi.createChild("API");
							eApi.setAttribute("Name", "modifyUserHierarchy");
							YFCElement eUserI = eApi.createChild("Input").createChild("User");
							eUserI.setAttribute("Loginid", eUser.getAttribute("Loginid"));
							eUserI.setAttribute("Password", eUser.getAttribute("Loginid") + sAugment);
						}
					}
				} catch(Exception yex) {
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
		        } 
				if (eMultiApi.hasChildNodes()){
					try {
						return localApi.invoke(env, "multiApi", dMultiApi.getDocument());
						
					} catch(Exception yex) {
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        } 
				}
				
			} catch (YIFClientCreationException e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
