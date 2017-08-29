package com.ibm.sterling.services;

import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASendQuoteApprovalEmail {
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(BDASendQuoteApprovalEmail.class);
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public Document sendQuoteApprovalEmail(YFSEnvironment env, Document input){
		if(!YFCCommon.isVoid(input)){
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eInput = dInput.getDocumentElement();
			logger.debug("sendQuoteApprovalEmail: " + eInput);
			
			
			
		}
		return input;
	}
}
