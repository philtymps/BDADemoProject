/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/
package com.ibm.mobile.dataprovider;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAStatusDataProvider implements IBDADataProvider {
	
	@Override
	public void addAdditionalData(YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
		if(interestingElement != null) {
			if (YFCCommon.equals(interestingElement.getNodeName(), "Customer")) {
				if (interestingElement.hasAttribute("AggregateStatus") && !interestingElement.hasAttribute("AggregateStatusDescription") && YFCCommon.equals(sAttribute, "AggregateStatusDescription")) {
					String sCodeValue = interestingElement.getAttribute("AggregateStatus");
					String description = BDADataProviderUtils.getCommonCodeShortDescription(context, sCodeValue,  "CUSTOMER_STATUS",  null, null);
					if(!YFCCommon.isVoid(description)){
						interestingElement.setAttribute("AggregateStatusDescription", description);
					}
				}
			} else if (YFCCommon.equals(interestingElement.getNodeName(), "Shipment")) {
				if (interestingElement.hasAttribute("Status") && !interestingElement.hasAttribute("StatusDescription") && YFCCommon.equals("StatusDescription", sAttribute)) {
					String sStatus = interestingElement.getAttribute("Status");
					String sOrgCode = BDADataProviderUtils.getOrganizationCode(apiInput, apiOutput, interestingElement);
					if(!YFCCommon.isVoid(sStatus) && !YFCCommon.isVoid(sOrgCode)){
						String sDescription = BDADataProviderUtils.getShipmentStatusDescription(context, sStatus, sOrgCode);
						if(!YFCCommon.isVoid(sDescription)){
							interestingElement.setAttribute("StatusDescription", sDescription);
						} else {
							interestingElement.setAttribute("StatusDescription", sStatus);
						}
					}
				}
			}
		}
	}
	
	
	
}
