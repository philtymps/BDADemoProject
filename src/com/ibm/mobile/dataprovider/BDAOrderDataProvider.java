/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/
package com.ibm.mobile.dataprovider;

import com.yantra.interop.japi.YIFApi;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAOrderDataProvider implements IBDADataProvider {
	
	@Override
	public void addAdditionalData(YIFApi localApi, YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
		if(interestingElement != null){
			if (interestingElement.hasAttribute("EntryType") && !interestingElement.hasAttribute("DisplayEntryType") && !YFCCommon.isVoid(interestingElement.getAttribute("EntryType")) && YFCCommon.equals(sAttribute, "DisplayEntryType")){
				String sCodeValue = interestingElement.getAttribute("EntryType");
				String description = BDADataProviderUtils.getCommonCodeShortDescription(context, localApi, sCodeValue,  "YCD_CHANNEL",  null, null);
				if(YFCCommon.isVoid(description)){
					description=sCodeValue;
				}
				interestingElement.setAttribute("DisplayEntryType", description);
			}
			if (interestingElement.hasAttribute("ExchangeType") && !interestingElement.hasAttribute("DisplayExchangeType") && !YFCCommon.isVoid(interestingElement.getAttribute("ExchangeType")) && YFCCommon.equals(sAttribute, "DisplayEntryType")){
				String sCodeValue = interestingElement.getAttribute("ExchangeType");
				String description = BDADataProviderUtils.getCommonCodeShortDescription(context, localApi, sCodeValue,  "EXCHANGE_TYPE",  null, null);
				if(YFCCommon.isVoid(description)){
					description=sCodeValue;
				}
				interestingElement.setAttribute("DisplayExchangeType", description);
			}
			if (interestingElement.hasAttribute("BillToID") && !interestingElement.hasAttribute("CustomerMasterOrganizationCode") && !YFCCommon.isVoid(interestingElement.getAttribute("BillToID")) && !YFCCommon.isVoid(interestingElement.getAttribute("EnterpriseCode")) && YFCCommon.equals(sAttribute, "DisplayEntryType")){
				String enterpriseCode = interestingElement.getAttribute("EnterpriseCode");
				interestingElement.setAttribute("CustomerMasterOrganizationCode", BDADataProviderUtils.getCustomerMasterOrg(context, localApi, enterpriseCode));
			}
		}
	}

}
