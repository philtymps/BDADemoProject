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

public class BDAOrganizationDataProvider implements IBDADataProvider {

	@Override
	public void addAdditionalData(YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
		
		if(interestingElement != null){
			if(interestingElement.getNodeName().equals("Organization")){
				if (interestingElement.hasAttribute("LocaleCode") && !interestingElement.hasAttribute("Currency")){
					YFCElement eLocale = BDADataProviderUtils.getLocaleDetails(context, interestingElement.getAttribute("LocaleCode"));
					if(!YFCCommon.isVoid(eLocale)){
						interestingElement.setAttribute("Currency", eLocale.getAttribute("Currency"));
					}
				}
			}else{
				if (interestingElement.hasAttribute("EnterpriseCode") && !interestingElement.hasAttribute("EnterpriseName") && YFCCommon.equals(sAttribute, "EnterpriseName")){
					String temp = BDADataProviderUtils.getOrganizationName(context, interestingElement.getAttribute("EnterpriseCode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("EnterpriseName", temp);
					}
				}
				if (interestingElement.hasAttribute("OrganizationCode") && !interestingElement.hasAttribute("OrganizationName") && YFCCommon.equals(sAttribute, "OrganizationName")){
					String temp = BDADataProviderUtils.getOrganizationName(context, interestingElement.getAttribute("OrganizationCode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("OrganizationName", temp);
					}
				}
				if (interestingElement.hasAttribute("SellerOrganizationCode") && !interestingElement.hasAttribute("SellerOrganizationName") && YFCCommon.equals(sAttribute, "SellerOrganizationName")){
					String temp = BDADataProviderUtils.getOrganizationName(context, interestingElement.getAttribute("SellerOrganizationCode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("SellerOrganizationName", temp);
					}
				}
				if (interestingElement.hasAttribute("BuyerOrganizationCode") && !interestingElement.hasAttribute("BuyerOrganizationName") && YFCCommon.equals(sAttribute, "BuyerOrganizationName")){
					String temp = BDADataProviderUtils.getOrganizationName(context, interestingElement.getAttribute("BuyerOrganizationCode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("BuyerOrganizationName", temp);
					}
				}
				if (interestingElement.hasAttribute("ShipnodeKey") &&  !YFCCommon.isVoid(interestingElement.getAttribute("ShipnodeKey")) && !interestingElement.hasAttribute("ShipnodeDescription") && YFCCommon.equals(sAttribute, "ShipnodeDescription")){
					String temp = BDADataProviderUtils.getShipnodeDescription(context, interestingElement.getAttribute("ShipnodeKey"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("ShipnodeDescription", temp);
					}
				}
				if (interestingElement.hasAttribute("NodeKey") &&  !YFCCommon.isVoid(interestingElement.getAttribute("NodeKey")) && !interestingElement.hasAttribute("ShipnodeDescription") && YFCCommon.equals(sAttribute, "ShipnodeDescription")){
					String temp = BDADataProviderUtils.getShipnodeDescription(context, interestingElement.getAttribute("NodeKey"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("ShipnodeDescription", temp);
					}
				}
				if (interestingElement.hasAttribute("ProcureFromNode") &&  !YFCCommon.isVoid(interestingElement.getAttribute("ProcureFromNode")) && !interestingElement.hasAttribute("ShipnodeDescription") && YFCCommon.equals(sAttribute, "ShipnodeDescription")){
					String temp = BDADataProviderUtils.getShipnodeDescription(context, interestingElement.getAttribute("ProcureFromNode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("ShipnodeDescription", temp);
					}
				}
				if (interestingElement.hasAttribute("ShipNode") && !YFCCommon.isVoid(interestingElement.getAttribute("ShipNode")) && !interestingElement.hasAttribute("ShipnodeDescription") && YFCCommon.equals(sAttribute, "ShipnodeDescription")){
					String temp = BDADataProviderUtils.getShipnodeDescription(context, interestingElement.getAttribute("ShipNode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("ShipnodeDescription", temp);
					}
				}
				if (interestingElement.hasAttribute("ShipNode")  && !YFCCommon.isVoid(interestingElement.getAttribute("ShipNode")) && !interestingElement.hasAttribute("ShipnodeFullDescription") && YFCCommon.equals(sAttribute, "ShipnodeFullDescription")){
					String temp = BDADataProviderUtils.getShipnodeFullDescription(context, interestingElement.getAttribute("ShipNode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("ShipnodeFullDescription", temp);
					}
				}
				if (interestingElement.hasAttribute("ReceivingNode")  && !YFCCommon.isVoid(interestingElement.getAttribute("ReceivingNode")) && !interestingElement.hasAttribute("ReceivingNodeDescription") && YFCCommon.equals(sAttribute, "ReceivingNodeDescription")){
					String temp = BDADataProviderUtils.getShipnodeDescription(context, interestingElement.getAttribute("ReceivingNode"));
					if(!YFCCommon.isVoid(temp)){
						interestingElement.setAttribute("ReceivingNodeDescription", temp);
					}
				}
			}
		}
	}

	
}
