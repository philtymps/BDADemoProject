package com.ue;

import java.rmi.RemoteException;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.shared.dbi.YFS_Organization;
import com.yantra.shared.ycp.YCPFactory;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.ydm.japi.ue.YDMBeforeCreateShipment;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;

public class YDMBeforeCreateShipmentUEImpl implements YDMBeforeCreateShipment {

    @Override
    public Document beforeCreateShipment(YFSEnvironment env, Document doc) throws YFSUserExitException {
        YFCElement inElem = YFCDocument.getDocumentFor(doc).getDocumentElement();
        String sOrgCode = inElem.getAttribute("EnterpriseCode");
        String sCatOrg = "";
        YFS_Organization oOrgInXML = YCPFactory.getInstance().getOrganization((YFSContext)env, sOrgCode);
        if(oOrgInXML!=null)sCatOrg=oOrgInXML.getCatalog_Organization_Code();
        YFCNodeList<YFCElement> nodes = inElem.getElementsByTagName("ShipmentLine");
        for(YFCElement e:nodes){
            YFCDocument indocToGetItemDetails = YFCDocument.createDocument("Item");
            YFCElement inElemToGetItemDetails = indocToGetItemDetails.getDocumentElement();
            inElemToGetItemDetails.setAttribute("ItemID", e.getAttribute("ItemID"));
            inElemToGetItemDetails.setAttribute("OrganizationCode", sCatOrg);
            try {
                Document outdoc =
                    YIFClientFactory.getInstance().getApi()
                        .invoke(env, "getItemList", indocToGetItemDetails.getDocument());
                YFCNodeList<YFCElement> items =
                    YFCDocument.getDocumentFor(outdoc).getDocumentElement()
                        .getElementsByTagName("PrimaryInformation");
                if (items != null)
                    e.setAttribute("PickLocation", items.item(0).getAttribute("ItemType"));
            } catch (YFSException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (YIFClientCreationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return doc;
    }

}
