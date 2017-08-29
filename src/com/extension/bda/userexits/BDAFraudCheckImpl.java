package com.extension.bda.userexits;

import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.yantra.pca.ycd.japi.ue.YCDProcessOrderFraudCheckUE;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.ycp.core.YCPEntityApi;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

public class BDAFraudCheckImpl implements YCDProcessOrderFraudCheckUE {

	private static YFCLogCategory cat = YFCLogCategory.instance(BDAFraudCheckImpl.class.getName());
    
    public Document processOrderFraudCheck(YFSEnvironment env, Document inXML) throws YFSUserExitException {
        String response = "SUCCESS";
        String msg = "Order passed fraud check.";
        YFCDocument inDoc = YFCDocument.getDocumentFor(inXML);
        YFCElement order = inDoc.getDocumentElement();
        String ipAddress = order.getAttribute("SourceIPAddress");
        if (YFCObject.equals("1.1.1.1",ipAddress)) {
            response = "FAILED";
            msg = "Source IP Address is restricted.";
        }
        String email = order.getAttribute("CustomerEMailID");
        if(!YFCCommon.isVoid(email) && YFCCommon.equals(email, "cdior@gmal.fr")){
        	response = "FAILED";
        	msg = "Blacklisted customer, cancel order.";
        	
        	YFCDocument dCancelOrder = YFCDocument.createDocument("Order");
        	YFCElement eCancel = dCancelOrder.getDocumentElement();
        	eCancel.setAttribute("OrderHeaderKey", order.getAttribute("OrderHeaderKey"));
        	eCancel.setAttribute("Action", "CANCEL");
        	eCancel.setAttribute("ModificationReasonCode", "FRAUD_CANCEL");
        	eCancel.setAttribute("ModificationReasonText", msg);
        	try {
        		CallInteropServlet.invokeApi(dCancelOrder, null, "cancelOrder", "http://oms.omfulfillment.com:9080");
        	} catch (Exception e){
        		e.printStackTrace();
        	}
        } else if(!YFCCommon.isVoid(email) && YFCCommon.equals(email, "marcog@gmail.com")){
        	response = "FAILED";
        	msg = "Manual validation required!";
        }
        order.setAttribute("FraudCheckResponseCode",response);
        YFCElement messages = order.createChild("FraudCheckResponseMessages");
        YFCElement message = messages.createChild("FraudCheckResponseMessage");
        message.setAttribute("Text",msg);
        YFCDocument outDoc = YFCDocument.createDocument();
        YFCElement root = (YFCElement)outDoc.importNode(order,true);
        outDoc.appendChild(root);
        return outDoc.getDocument();
    }

}
