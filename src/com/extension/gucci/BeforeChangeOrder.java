package com.extension.gucci;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSBeforeChangeOrderUE;

public class BeforeChangeOrder implements YFSBeforeChangeOrderUE {

	@Override
	public Document beforeChangeOrder(YFSEnvironment env, Document doc) throws YFSUserExitException {
		YFCDocument dInput = YFCDocument.getDocumentFor(doc);
		YFCElement eInput = dInput.getDocumentElement();
		if(!YFCCommon.isVoid(eInput.getAttribute("CustomerEMailID")) && YFCCommon.equals(eInput.getAttribute("CustomerEMailID"), "robertp@gmail.com")){
			eInput.setAttribute("PriorityCode", "VIG");
		} else if(!YFCCommon.isVoid(eInput.getChildElement("PersonInfoBillTo")) && !YFCCommon.isVoid(eInput.getChildElement("PersonInfoBillTo").getAttribute("EMailID"))  && YFCCommon.equals(eInput.getChildElement("PersonInfoBillTo").getAttribute("EMailID"), "robertp@gmail.com")){
			eInput.setAttribute("PriorityCode", "VIG");
		}
		return doc;
	}

}
