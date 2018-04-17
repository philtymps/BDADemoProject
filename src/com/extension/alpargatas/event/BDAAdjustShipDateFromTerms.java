package com.extension.alpargatas.event;

import org.w3c.dom.Document;

import com.ibm.icu.util.Calendar;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAAdjustShipDateFromTerms {

	public Document setTermDays(YFSEnvironment env, Document input){
		
		if(!YFCCommon.isVoid(input)){
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			System.out.println("setTermDays Input: " + dInput);
			YFCElement eOrder = dInput.getDocumentElement();
			
			if(!YFCCommon.isVoid(eOrder.getAttribute("TermsCode")) && YFCCommon.isVoid(eOrder.getAttribute("ReqShipDate"))){
				try {
					if(eOrder.getAttribute("TermsCode").length() == 4){
						String days = eOrder.getAttribute("TermsCode").substring(0, 2);
						int d = Integer.parseInt(days);
						
						Calendar c = Calendar.getInstance();
						c.setTimeInMillis(System.currentTimeMillis());
						c.add(Calendar.DATE, d);
						YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();

						YFCDocument dQOrder = YFCDocument.createDocument("Order");
						YFCElement eSOrder = dQOrder.getDocumentElement();
						eSOrder.setAttribute("LinkedSourceKey", eOrder.getAttribute("OrderHeaderKey"));
						Document dOrderList = localApi.getOrderList(env, dQOrder.getDocument());
						
						YFCDocument dSalesOrder = YFCDocument.createDocument("Order");
						YFCElement eSalesOrder = dSalesOrder.getDocumentElement();
						YFCDocument dResponse = YFCDocument.getDocumentFor(dOrderList);
						eSalesOrder.setAttribute("OrderHeaderKey", dResponse.getDocumentElement().getChildElement("Order").getAttribute("OrderHeaderKey"));
						eSalesOrder.setAttribute("ReqShipDate", new YFCDate(c.getTimeInMillis(), true));
						System.out.println("changeOrder input: " + dSalesOrder);
						Document dOrderLine = localApi.changeOrder(env, dSalesOrder.getDocument());
						
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		
		}
		
		return input;
	}
}
