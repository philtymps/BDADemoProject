package com.extension.dbschenker;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASplitPartialBackorder extends BDAServiceApi {

	public Document splitPartialForSubstitution(YFSEnvironment env, Document dInput){
		boolean found = false;
		YFCDocument splitOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = splitOrder.getDocumentElement();
		YFCElement eInput = YFCDocument.getDocumentFor(dInput).getDocumentElement();
		eOrder.setAttributes(eInput.getAttributes());
		for(YFCElement eOrderLine : eInput.getChildElement("OrderLines", true).getChildren()){
			for(YFCElement eBackOrderedFrom : eOrderLine.getChildElement("StatusBreakupForBackOrderedQty", true).getChildren()){
				if(eBackOrderedFrom.getDoubleAttribute("BackOrderedQuantity", 0) < eOrderLine.getDoubleAttribute("OrderedQty", 0)){
					if (!YFCCommon.equals(eOrderLine.getChildElement("Item").getAttribute("ItemID"), eBackOrderedFrom.getChildElement("Details").getAttribute("OverrideItemID"))){
						found = true;
						YFCElement eSplitLine = eOrder.getChildElement("OrderLines", true).createChild("OrderLine");
						eSplitLine.setAttribute("OrderLineKey", eOrderLine.getAttribute("OrderLineKey"));
						eSplitLine.setAttribute("QuantityToSplit", eBackOrderedFrom.getDoubleAttribute("BackOrderedQuantity", 0));
						eSplitLine.setAttribute("FromStatus", "1300");
						YFCElement eSplit = eSplitLine.getChildElement("SplitLines", true).createChild("SplitLine");
						eSplit.setAttribute("OrderedQty", eBackOrderedFrom.getDoubleAttribute("BackOrderedQuantity", 0));
					}
				}
			}
		}
		if(found){
			return callApi(env, splitOrder.getDocument(), null, "splitLine");
		}
		return dInput;
	}
	
}
