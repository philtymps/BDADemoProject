package com.extension.wsc.order.cartDetails;

import org.w3c.dom.Element;

import com.ibm.wsc.order.cartDetails.WSCScanAndAddItemToOrder;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.sterlingcommerce.ui.web.framework.context.SCUIContext;
import com.sterlingcommerce.ui.web.framework.mashup.SCUIMashupMetaData;

public class BDAScanAndAddItemToOrder extends WSCScanAndAddItemToOrder {

	public Element massageInput(Element inputEl, SCUIMashupMetaData mashupMetaData, SCUIContext uiContext) {
		System.out.println("Input - Cart Details Scan Item");
		System.out.println(SCXmlUtil.getString(inputEl));
		
		return super.massageInput(inputEl, mashupMetaData, uiContext);
	}
	
	public Element massageOutput(Element outEl, SCUIMashupMetaData mashupMetaData, SCUIContext uiContext) {
		System.out.println("Output - Cart Details Scan Item");
		System.out.println(SCXmlUtil.getString(outEl));
		
		return super.massageOutput(outEl, mashupMetaData, uiContext);
	}
}
