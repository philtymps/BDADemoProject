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

public class BDAItemDataProvider implements IBDADataProvider {

	@Override
	public void addAdditionalData(YIFApi localApi, YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
	
		if(interestingElement != null){
			String callingOrg = getOrganizationCode(apiOutput, interestingElement, apiInput);
			if (interestingElement.getNodeName().equals("AlternateUOM") && YFCCommon.isVoid(callingOrg)){
				callingOrg = getOrganizationCode(apiOutput, interestingElement.getParentElement().getParentElement(), apiInput);
			}
			if(interestingElement.hasAttribute("UnitOfMeasure") && !interestingElement.hasAttribute("DisplayUnitOfMeasure")){
				String sHideUOMRule = BDADataProviderUtils.getUOMDisplayRuleValue(context, localApi, callingOrg);
				String uom = interestingElement.getAttribute("UnitOfMeasure");
				String itemGroupCode = interestingElement.getAttribute("ItemGroupCode");
				if (interestingElement.getNodeName().equals("AlternateUOM") && YFCCommon.isVoid(itemGroupCode)){
					itemGroupCode = interestingElement.getParentElement().getParentElement().getAttribute("ItemGroupCode");
				}
				if(!YFCCommon.isVoid(uom) && !YFCCommon.isVoid(callingOrg) && !YFCCommon.isVoid(itemGroupCode)){
					String desc = BDADataProviderUtils.getUnitOfMeasureDescription(context,localApi,uom,callingOrg,itemGroupCode);
					interestingElement.setAttribute("DisplayUnitOfMeasure", desc);
				}
				if(!YFCCommon.equals("H",sHideUOMRule)){
					//Rule is configured to use transactional UOM. Evaluating transaction UOM description and set display format from bundle entry.
					interestingElement.setAttribute("UOMDisplayFormat", "formattedQty");
				}else {
					//Rule is configured to not show UOM. Skipping description evaluation and return formating string for only Quantity.
					interestingElement.setAttribute("UOMDisplayFormat", "qtyOnly");
				}
			}
			
			if(interestingElement.hasAttribute("ComponentUnitOfMeasure") && !interestingElement.hasAttribute("DisplayComponentUnitOfMeasure")){
				String sHideUOMRule = BDADataProviderUtils.getUOMDisplayRuleValue(context, localApi, callingOrg);
				
				if(!YFCCommon.equals("H",sHideUOMRule)){
					//Rule is configured to use transactional UOM. Evaluating transaction UOM description and set display format from bundle entry.
					interestingElement.setAttribute("UOMDisplayFormat", "formattedQty");
					
					String uom = interestingElement.getAttribute("ComponentUnitOfMeasure");
					String itemGroupCode = interestingElement.getAttribute("ComponentItemGroupCode");
					
					if(!YFCCommon.isVoid(uom) && !YFCCommon.isVoid(callingOrg) && !YFCCommon.isVoid(itemGroupCode)){
						String desc = BDADataProviderUtils.getUnitOfMeasureDescription(context,localApi,uom,callingOrg,itemGroupCode);
						interestingElement.setAttribute("DisplayComponentUnitOfMeasure", desc);
					}
				}else {
					//Rule is configured to not show UOM. Skipping description evaluation and return formating string for only Quantity.
					interestingElement.setAttribute("UOMDisplayFormat", "qtyOnly");
				}
			}
		}
	}
	
	private String getOrganizationCode(YFCElement apiOutput, YFCElement interestingElement, YFCElement apiInput) {
		if(apiOutput.getNodeName().equals("Item")){
			return apiOutput.getAttribute("OrganizationCode");
		} else if(interestingElement.getNodeName().equals("Component")) {
			return interestingElement.getAttribute("ComponentOrganizationCode");
		}else if(interestingElement.getNodeName().equals("Item")){
			if(!YFCCommon.isVoid(apiInput) && !YFCCommon.isVoid(apiInput.getAttribute("CallingOrganizationCode"))){
				return apiInput.getAttribute("CallingOrganizationCode");
			}else if(!YFCCommon.isVoid(apiOutput.getAttribute("CallingOrganizationCode"))){
				return apiOutput.getAttribute("CallingOrganizationCode");
			}
			return interestingElement.getAttribute("OrganizationCode");
		}
		else{
			return null;
		}
	}
	
	
	/*private String getOrganizationCode(Element apiOutput, Element interestingElement) {
		if(interestingElement.getNodeName().equals("Item")){
			return interestingElement.getAttribute("OrganizationCode");
		} else if(interestingElement.getNodeName().equals("Component")) {
			return interestingElement.getAttribute("ComponentOrganizationCode");
		} else{
			return null;
		}
	}*/
	
}
