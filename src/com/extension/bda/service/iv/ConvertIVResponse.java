package com.extension.bda.service.iv;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.ibm.sterling.afc.jsonutil.PLTJSONUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class ConvertIVResponse extends BDAServiceApi {

	public Document invoke(YFSEnvironment env, Document dInput) {
		YFCDocument docInput = YFCDocument.getDocumentFor(dInput);
		YFCElement eInput = docInput.getDocumentElement();
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getChildElement("Output"))){
			if(!YFCCommon.isVoid(eInput.getChildElement("Output").getNodeValue())) {
				try {
					String sResponse = eInput.getChildElement("Output").getNodeValue();
					if(sResponse.startsWith("[")) {
						sResponse = "{root: " + sResponse + "}";
					}
					return PLTJSONUtils.getXmlFromJSON(sResponse, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return dInput;
		
	}
}
