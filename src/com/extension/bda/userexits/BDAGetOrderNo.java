package com.extension.bda.userexits;

import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.RemoteServiceRequest;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSGetOrderNoUE;

public class BDAGetOrderNo extends RemoteServiceRequest implements YFSGetOrderNoUE {

	public static void main(String[] args) {
		
	}
	@Override
	public String getOrderNo(YFSEnvironment env, Map inMap) throws YFSUserExitException {
		String strDocumentType = (String) (inMap.get("DocumentType"));
		String strEnterpriseCode = (String) (inMap.get("EnterpriseCode"));
		Properties p = new Properties();
		p.setProperty("URL", "http://RemoteOMSServices.us-south.cf.appdomain.cloud/oms/userexits/getOrderNo/" + strDocumentType + "/" + strEnterpriseCode);
		p.setProperty("Method", "GET");
		try {
			setProperties(p);
			Document response = invoke(env, null);
			return response.getDocumentElement().getAttribute("OrderNo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
