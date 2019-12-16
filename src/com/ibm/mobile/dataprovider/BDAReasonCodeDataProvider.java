package com.ibm.mobile.dataprovider;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAReasonCodeDataProvider implements IBDADataProvider {

	@Override
	public void addAdditionalData(YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput,
			YFCElement interestingElement, String sAttribute) {
		if(interestingElement != null){
			if (interestingElement.hasAttribute("ReasonCode") && !interestingElement.hasAttribute("ReasonCodeDesc") && !YFCCommon.isVoid(interestingElement.getAttribute("ReasonCode")) && YFCCommon.equals(sAttribute, "ReasonCodeDesc")){
				
				String temp = BDADataProviderUtils.getCommonCodeShortDescription(context, interestingElement.getAttribute("ReasonCode"), "NOTES_REASON", null, null);
				if(!YFCCommon.isVoid(temp)){
					interestingElement.setAttribute("ReasonCodeDesc", temp);
				}
			}
		}
	}

}
