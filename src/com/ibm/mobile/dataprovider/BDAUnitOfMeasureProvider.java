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

public class BDAUnitOfMeasureProvider implements IBDADataProvider {
		
	public void addAdditionalData(YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
		if(interestingElement.hasAttribute("DistanceUOM") && !interestingElement.hasAttribute("DistanceUOMDesc") && YFCCommon.equals(sAttribute,"DistanceUOMDesc")){
			String uom = interestingElement.getAttribute("DistanceUOM");
			if(!YFCCommon.isVoid(uom)){
				interestingElement.setAttribute("DistanceUOMDesc", BDADataProviderUtils.getUomDescription(context, uom, "DIMENSION"));
			}
		}
	}
}
