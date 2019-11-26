package com.ibm.commerce.service;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetApplicableContracts {

	public Document getApplicableContracts(YFSEnvironment env, Document input) {
		YFCDocument dOutput = YFCDocument.createDocument("Contracts");
		YFCElement eContracts = dOutput.getDocumentElement();
		YFCElement eContract = eContracts.createChild("Contract");
		eContract.setAttribute("ContractName", "Standard");
		eContract.setAttribute("ContractValue", "100001");
		eContract = eContracts.createChild("Contract");
		eContract.setAttribute("ContractName", "Education");
		eContract.setAttribute("ContractValue", "100002");
		return dOutput.getDocument();
	}
}
