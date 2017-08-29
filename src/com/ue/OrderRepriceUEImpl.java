package com.ue;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSOrderRepricingUE;

public class OrderRepriceUEImpl implements YFSOrderRepricingUE {

	@Override
	public Document orderReprice(YFSEnvironment arg0, Document arg1) throws YFSUserExitException {
		System.out.println(YFCDocument.getDocumentFor(arg1));
		return arg1;
	}

}
