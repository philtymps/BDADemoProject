package com.extension.bda.service.fulfillment;

import org.w3c.dom.Document;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;

public class BDAChangeOrder {

	private YFCDocument dInput;
	boolean hasLines = false;
	
	public BDAChangeOrder(String sOrderHeaderKey) {
		dInput = YFCDocument.createDocument("Order");
		dInput.getDocumentElement().setAttribute("OrderHeaderKey", sOrderHeaderKey);
		hasLines = false;
	}
	
	public void addLine(String sOrderLineKey, YDate sReqDeliveryDate) {
		hasLines = true;
		YFCElement eLine = dInput.getDocumentElement().getChildElement("OrderLines", true).createChild("OrderLine");
		eLine.setAttribute("OrderLineKey", sOrderLineKey);
		eLine.setAttribute("ReqDeliveryDate", sReqDeliveryDate);
	}
	
	public Document changeOrder() {
		if(hasLines) {
			return dInput.getDocument();
		}
		return null;
	}
}
