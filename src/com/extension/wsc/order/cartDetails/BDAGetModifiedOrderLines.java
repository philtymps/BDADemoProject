// Decompiled by DJ v3.12.12.100 Copyright 2015 Atanas Neshkov  Date: 4/2/2020 9:01:44 PM
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   BDAGetModifiedOrderLines.java

package com.extension.wsc.order.cartDetails;

import com.ibm.wsc.order.cartDetails.WSCGetModifiedOrderLines;
import com.sterlingcommerce.baseutil.SCUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.sterlingcommerce.ui.web.framework.context.SCUIContext;
import com.sterlingcommerce.ui.web.framework.mashup.SCUIMashupMetaData;
import org.w3c.dom.Element;

public class BDAGetModifiedOrderLines extends WSCGetModifiedOrderLines
{

    public BDAGetModifiedOrderLines()
    {
    }

    public Element massageInput(Element inputEl, SCUIMashupMetaData mashupMetaData, SCUIContext uiContext)
    {
        Element eResponse = super.massageInput(inputEl, mashupMetaData, uiContext);
        System.out.println("BDAGetModifiedOrderLines - massageInput");
        if(!SCUtil.isVoid(uiContext.getAttribute("LotNumber")))
        {
            System.out.println((new StringBuilder("LotNumber::")).append(uiContext.getAttribute("LotNumber")).toString());
            Element eOrderLine = SCXmlUtil.getChildElement(SCXmlUtil.getChildElement(eResponse, "OrderLines"), "OrderLine");
            Element eInv = SCXmlUtil.getChildElement(eOrderLine, "OrderLineInvAttRequest", true);
            eInv.setAttribute("LotNumber", (String)uiContext.getAttribute("LotNumber"));
            System.out.println("Remove LotNumber");
            uiContext.removeAttribute("LotNumber");
        }
        System.out.println(SCXmlUtil.getString(eResponse));
        return eResponse;
    }
}