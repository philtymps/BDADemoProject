package com.ue;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSGetOverallStatusUE;

public class DemoGetOverallStatus  implements YFSGetOverallStatusUE {

	@Override
	public Document getOverallStatus(YFSEnvironment env, Document inXML) throws YFSUserExitException {
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOutput = dOutput.getDocumentElement();
		
		YFCElement eInput  = YFCDocument.getDocumentFor(inXML).getDocumentElement();
		eOutput.setAttribute("OrderHeaderKey", eInput.getAttribute("OrderHeaderKey"));
		eOutput.setAttribute("EnterpriseCode", eInput.getAttribute("EnterpriseCode"));
		eOutput.setAttribute("OrderNo", eInput.getAttribute("OrderNo"));
		if (!YFCCommon.isVoid(eInput.getAttribute("Status"))){
			eOutput.setAttribute("OverallStatus", eInput.getAttribute("Status") + " OS");
		} else {
			eOutput.setAttribute("OverallStatus", "Unknown");
		}
		YFCElement eOutputLines = eOutput.createChild("OrderLines");
		for (YFCElement eOrderLine : eInput.getChildElement("OrderLines", true).getChildren()){
			YFCElement eOutputLine = eOutputLines.createChild("OrderLine");
			eOutputLine.setAttribute("OrderLineKey", eOrderLine.getAttribute("OrderLineKey"));
			eOutputLine.setAttribute("PrimeLineNo", eOrderLine.getAttribute("PrimeLineNo"));
			eOutputLine.setAttribute("SubLineNo", eOrderLine.getAttribute("SubLineNo"));
			if (!YFCCommon.isVoid(eOrderLine.getAttribute("Status"))){
				eOutputLine.setAttribute("OverallStatus", eOrderLine.getAttribute("Status") + " OS");
			} else {
				eOutputLine.setAttribute("OverallStatus", "Unknown");
			}
		}
		return dOutput.getDocument();
	}

}
