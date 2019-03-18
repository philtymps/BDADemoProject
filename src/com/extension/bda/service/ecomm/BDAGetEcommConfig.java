package com.extension.bda.service.ecomm;

import java.io.File;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetEcommConfig extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		return "getECommConfig";
	}

	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		String file = "/opt/Sterling/Scripts/eCommConfig.xml";
		if(!YFCCommon.isVoid(this.getProperty("filename"))) {
			file = (String) this.getProperty("filename");
		}
		File f = new File(file);
		if(f.exists()) {
			return YFCDocument.getDocumentForXMLFile(file).getDocument();
		}
		return null;
	}

}
