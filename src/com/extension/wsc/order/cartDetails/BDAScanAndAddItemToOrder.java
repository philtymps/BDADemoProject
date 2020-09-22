package com.extension.wsc.order.cartDetails;

import com.ibm.wsc.order.cartDetails.WSCScanAndAddItemToOrder;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.sterlingcommerce.ui.web.framework.context.SCUIContext;
import com.sterlingcommerce.ui.web.framework.mashup.SCUIMashupMetaData;
import org.w3c.dom.Element;


public class BDAScanAndAddItemToOrder extends WSCScanAndAddItemToOrder {

    public Element massageInput(Element inputEl, SCUIMashupMetaData mashupMetaData, SCUIContext uiContext)
    {
        Element eBarCode = SCXmlUtil.getChildElement(inputEl, "BarCode");
        String sBarCode = eBarCode.getAttribute("BarCodeData");
        if(sBarCode.indexOf(":") > -1)
        {
            System.out.println((new StringBuilder("Save LotNumber: ")).append(sBarCode.split(":")[1]).toString());
            uiContext.setAttribute("LotNumber", sBarCode.split(":")[1]);
            eBarCode.setAttribute("BarCodeData", sBarCode.split(":")[0]);
        } else
        {
            uiContext.removeAttribute("LotNumber");
        }
        return super.massageInput(inputEl, mashupMetaData, uiContext);
    }

    public Element massageOutput(Element outEl, SCUIMashupMetaData mashupMetaData, SCUIContext uiContext)
    {
        return super.massageOutput(outEl, mashupMetaData, uiContext);
    }
}
