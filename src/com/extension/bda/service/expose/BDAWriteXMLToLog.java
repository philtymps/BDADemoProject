package com.extension.bda.service.expose;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAWriteXMLToLog extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		return "BDAWriteXMLToLog";
	}
	
	public String getFileName() {
		if(!YFCCommon.isVoid(this.getProperty("FileName"))) {
			return (String) this.getProperty("FileName");
		}
		return "output.xml";
	}
	
	public String getPath() {
		if(!YFCCommon.isVoid(this.getProperty("Path"))) {
			return (String) this.getProperty("Path");
		}
		return "/opt/Sterling/Scripts";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		this.writeXML(getPath(), getFileName(), YFCDocument.getDocumentFor(input));
		return input;
	}

	
}
