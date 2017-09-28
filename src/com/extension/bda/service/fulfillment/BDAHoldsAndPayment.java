package com.extension.bda.service.fulfillment;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAHoldsAndPayment implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "resolveHoldsAndPayment";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub

	}

	@Override
	public Document invoke(YFSEnvironment env, Document inputDoc) {
		YFCDocument output = YFCDocument.createDocument("Results");
		YFCElement eResults = output.getDocumentElement();
		try {	
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			try {
				env.setApiTemplate("getOrderDetails", CompleteOrder.getOrderDetailsTemplate());
				l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
				eResults.setAttribute("OrderHeaderKey", l_OutputXml.getDocumentElement().getAttribute("OrderHeaderKey"));
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
			if (!YFCCommon.isVoid(l_OutputXml)){
				try {
		            CompleteOrder.removeHolds(env, l_OutputXml, eResults, localApi);
		            CompleteOrder.processOrderPayments(env, l_OutputXml, true, eResults, localApi);
				} catch(Exception ex){
					eResults.setAttribute("Error", ex.toString());
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return eResults.getOwnerDocument().getDocument();
	}
}
