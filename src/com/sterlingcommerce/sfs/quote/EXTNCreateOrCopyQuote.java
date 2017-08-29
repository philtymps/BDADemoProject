package com.sterlingcommerce.sfs.quote;

import org.w3c.dom.Element;

import com.sterlingcommerce.ui.web.framework.context.SCUIContext;
import com.sterlingcommerce.ui.web.framework.helpers.SCUIMashupHelper;
import com.sterlingcommerce.ui.web.framework.mashup.SCUIMashupMetaData;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.ui.backend.util.DynamicXML;
import com.yantra.yfc.util.YFCCommon;

public class EXTNCreateOrCopyQuote implements com.sterlingcommerce.ui.web.framework.extensions.ISCUIMashup {


	@Override
	public Object execute(Object input, SCUIMashupMetaData mashupMetaData, SCUIContext uiContext) {
		String sExistingQuote = null;
		if(!YFCCommon.isVoid(input)){
			Element eInput = (Element) input;
			YFCElement eMashupInput = YFCDocument.getDocumentFor(mashupMetaData.getMashupElement().getOwnerDocument()).getDocumentElement();
			DynamicXML d = new DynamicXML(eMashupInput.getChildElement("API").getChildElement("Input").getFirstChildElement());
			YFCElement eReplace = d.getElement(uiContext.getRequest());
			if(!YFCCommon.isVoid(eInput.getAttribute("BillToID")) && !YFCCommon.isVoid(eReplace.getAttribute("EnterpriseCode"))){
				sExistingQuote = getExistingQuote(eInput.getAttribute("BillToID"), eReplace.getAttribute("EnterpriseCode"), uiContext);
				eInput.setAttribute("CopyFromOrderHeaderKey", sExistingQuote);
			}
		}
		if(!YFCCommon.isVoid(sExistingQuote)){
			return SCUIMashupHelper.invokeMashup("extn_copyQuote", input, uiContext);
		} else {
			return SCUIMashupHelper.invokeMashup("extn_createQuote", input, uiContext);
			
		}
	}
	
	private String getExistingQuote(String sBillToID, String sEnterpriseCode, SCUIContext uiContext){
		if(!YFCCommon.isVoid(sBillToID) && !YFCCommon.isVoid(sEnterpriseCode)){
			YFCDocument dInput = YFCDocument.createDocument("Order");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("BillToID", sBillToID);
			eInput.setAttribute("EnterpriseCode", sEnterpriseCode);
			eInput.setAttribute("DocumentType", "0015");
			eInput.setAttribute("Status", "1500");
			eInput.setAttribute("StatusQryType", "GE");
			YFCElement eAttr = eInput.createChild("OrderBy").createChild("Attribute");
			eAttr.setAttribute("Name", "OrderDate");
			eAttr.setAttribute("Desc", "Y");
			Element output = (Element) SCUIMashupHelper.invokeMashup("extn_getExistingQuotes", dInput.getDocument().getDocumentElement(), uiContext);
			if(!YFCCommon.isVoid(output)){
				YFCDocument dOutput = YFCDocument.getDocumentFor(output.getOwnerDocument());
				YFCElement eOutput = dOutput.getDocumentElement();
				for(YFCElement eOrder : eOutput.getChildren()){
					if(eOrder.getAttribute("MaxOrderStatus").contains("1500")){
						return eOrder.getAttribute("OrderHeaderKey");
					}
				}
			}
		}
		return null;
	}
}
