// Decompiled by DJ v3.12.12.100 Copyright 2015 Atanas Neshkov  Date: 4/2/2020 9:06:46 PM
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   BDAScanAndAddItemToOrder.java

package com.extension.wsc.order.quickCheckOut;

import com.ibm.wsc.order.quickCheckOut.WSCScanAndAddItemToOrder;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.sterlingcommerce.ui.web.framework.context.SCUIContext;
import com.sterlingcommerce.ui.web.framework.mashup.SCUIMashupMetaData;
import org.w3c.dom.Element;

public class BDAScanAndAddItemToOrder extends WSCScanAndAddItemToOrder
{

    public BDAScanAndAddItemToOrder()
    {
    }

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
}
