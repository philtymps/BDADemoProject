package com.kmart.userexit;

import java.util.Properties;

import org.w3c.dom.Document;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSBeforeCreateOrderUE;

public class BeforeOrderCreateUEImpl implements YFSBeforeCreateOrderUE{
    private Properties props=null;
    @Override
    public Document beforeCreateOrder(YFSEnvironment env, Document inDoc)
            throws YFSUserExitException {
        YFCDocument inputXML = YFCDocument.getDocumentFor(inDoc);
        System.out.println(inputXML);
        //fetch the root element from the Input xml.
        YFCElement eleOrder = inputXML.getDocumentElement();
        String strCustomerType = props.getProperty("CustomerType");

        eleOrder.setAttribute("SourcingClassification", strCustomerType);


        return inputXML.getDocument();
    }

    @Override
    public String beforeCreateOrder(YFSEnvironment arg0, String arg1)
            throws YFSUserExitException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * To get the service argument.
     */
    public void setProperties(Properties paramProperties) throws Exception {
        this.props=paramProperties;
    }

}
