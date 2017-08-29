package com.kmart.userexit;

import org.w3c.dom.Document;

import com.yantra.util.YFCUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSBeforeChangeOrderUE;

public class BeforeChangeOrderUEImpl implements YFSBeforeChangeOrderUE{

    public Document beforeChangeOrder(YFSEnvironment env, Document inDoc)
            throws YFSUserExitException {
        YFCDocument inputXML = YFCDocument.getDocumentFor(inDoc);

        //fetch the root element from the Input xml.
        YFCElement eleOrder = inputXML.getDocumentElement();
        String strCustomerBillToId = eleOrder.getAttribute("CustomerContactID");
        YFCDocument yfcDocGetCustomerListIp = YFCDocument.createDocument("Customer");
        YFCElement eleCustomerIp = yfcDocGetCustomerListIp.getDocumentElement();
        eleCustomerIp.setAttribute("CustomerID", strCustomerBillToId);
        Document docGetCustomerListOp = com.kmart.util.ExUtils.invokeAPI(env, "getCustomerList", yfcDocGetCustomerListIp.getDocument());
        YFCDocument yfcDocCustomerListOp = YFCDocument.getDocumentFor(docGetCustomerListOp);
        YFCElement eleCutomerList = yfcDocCustomerListOp.getDocumentElement();
        YFCElement eleCustomerOp = eleCutomerList.getChildElement("Customer");
        String strCustomerType = eleCustomerOp.getAttribute("CustomerType");
        if (YFCUtils.equals(strCustomerType, "01")){
            eleOrder.setAttribute("SourcingClassification", "B2B");
        } else {
            eleOrder.setAttribute("SourcingClassification", "B2C");
        }
        return inputXML.getDocument();
    }

}
