package com.ibm.mobile.dataprovider;

import com.yantra.interop.japi.YIFApi;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAInventoryDataProvider implements IBDADataProvider {

	@Override
	public void addAdditionalData(YIFApi localApi, YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput,
			YFCElement interestingElement, String sAttribute) {
		// TODO Auto-generated method stub
		if(interestingElement != null){
			if(interestingElement.hasAttribute("SupplyType") && !interestingElement.hasAttribute("SupplyTypeDescription")){
				String desc = BDADataProviderUtils.getSupplyTypeDesc(context, localApi, interestingElement.getAttribute("SupplyType"));
				interestingElement.setAttribute("SupplyTypeDescription", desc);
			}
			if(interestingElement.hasAttribute("DemandType") && !interestingElement.hasAttribute("DemandTypeDescription")){
				String desc = BDADataProviderUtils.getDemandTypeDesc(context, localApi, interestingElement.getAttribute("DemandType"));
				interestingElement.setAttribute("DemandTypeDescription", desc);
			}
		}
		
	}

}
