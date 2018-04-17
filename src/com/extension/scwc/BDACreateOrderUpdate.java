package com.extension.scwc;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDACreateOrderUpdate {

	public Document massageInput(YFSEnvironment env, Document input){
		
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		
		YFCElement eOrder = dInput.getDocumentElement();
		for(YFCElement ePayment : eOrder.getChildElement("PaymentMethods", true).getChildren()){
			if(!YFCCommon.isVoid(ePayment.getAttribute("DisplayCreditCardNo"))){
				ePayment.setAttribute("DisplayCreditCardNo", "1111");
			}
		}
		
		return dInput.getDocument();
	}
}
