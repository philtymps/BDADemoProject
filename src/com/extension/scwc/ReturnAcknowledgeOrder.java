package com.extension.scwc;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class ReturnAcknowledgeOrder {

	public Document getAcknowledgement(YFSEnvironment env, Document input){
		
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		
		YFCDocument dResponse = YFCDocument.createDocument("_ord:AcknowledgeOrder");
		YFCElement eResponse = dResponse.getDocumentElement();
		eResponse.setAttribute("xmlns:_ord", "http://www.ibm.com/xmlns/prod/commerce/9/order");
		eResponse.setAttribute("xmlns:_wcf", "http://www.ibm.com/xmlns/prod/commerce/9/foundation");
		YFCElement eUniqueID = eResponse.createChild("_ord:DataArea").createChild("_ord:Order").createChild("_ord:OrderIdentifier").createChild("_wcf:UniqueID");
		
		YFCElement eInput = dInput.getDocumentElement();
		eUniqueID.setNodeValue(eInput.getAttribute("OrderNo").replace("WC_", ""));
		return dResponse.getDocument();
	}
}
