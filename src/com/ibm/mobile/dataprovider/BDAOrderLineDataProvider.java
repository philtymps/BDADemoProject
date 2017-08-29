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
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;


public class BDAOrderLineDataProvider implements IBDADataProvider {
	private static YFCLogCategory cat = YFCLogCategory.instance(BDAOrderLineDataProvider.class.getName());
	
	@Override
	public void addAdditionalData(YIFApi localApi, YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
		if(interestingElement != null){
			String callingOrg = BDADataProviderUtils.getOrganizationCode(apiInput, apiOutput, interestingElement);
			if (interestingElement.hasAttribute("DeliveryMethod") && !interestingElement.hasAttribute("DeliveryMethodName") && YFCCommon.equals(sAttribute, "DeliveryMethodName")){
				String temp = BDADataProviderUtils.getCommonCodeShortDescription(context, localApi, interestingElement.getAttribute("DeliveryMethod"), "DELIVERY_METHOD", null, null);
				if(!YFCCommon.isVoid(temp)){
					interestingElement.setAttribute("DeliveryMethodName", temp);
				}
			}
			if (interestingElement.hasAttribute("ReturnReason") && !interestingElement.hasAttribute("ReturnReasonDesc") && YFCCommon.equals(sAttribute, "ReturnReasonDesc")){
				String temp = BDADataProviderUtils.getCommonCodeShortDescription(context, localApi, interestingElement.getAttribute("ReturnReason"), "RETURN_REASON", callingOrg, null);
				if(!YFCCommon.isVoid(temp)){
					interestingElement.setAttribute("ReturnReasonDesc", temp);
				}
			}
			// 408373: Service name is not displaying in Line summary screen 
		/*	if (interestingElement.hasAttribute("LevelOfService") && !interestingElement.hasAttribute("LevelOfServiceName")){
				String temp = SCCSDataProviderUtils.getCommonCodeShortDescription(context, localApi, interestingElement.getAttribute("LevelOfService"), "LEVEL_OF_SERVICE", null, null);
				if(!YFCCommon.isVoid(temp)){
					interestingElement.setAttribute("LevelOfServiceName", temp);
				}
			}*/
			if (interestingElement.hasAttribute("CarrierServiceCode") && !interestingElement.hasAttribute("CarrierServiceName") && !YFCCommon.isVoid(interestingElement.getAttribute("CarrierServiceCode")) && YFCCommon.equals(sAttribute, "CarrierServiceName")) {
				interestingElement.setAttribute("CarrierServiceName", BDADataProviderUtils.getCarrierServiceDescription(context, localApi, callingOrg, interestingElement.getAttribute("CarrierServiceCode")));
			}
			if (interestingElement.hasAttribute("SCAC") && !interestingElement.hasAttribute("SCACName") && !YFCCommon.isVoid(interestingElement.getAttribute("SCAC")) && YFCCommon.equals(sAttribute, "SCACName")) {
				interestingElement.setAttribute("SCACName", BDADataProviderUtils.getSCACName(context, localApi, callingOrg, interestingElement.getAttribute("SCAC")));
			}
			/* if (interestingElement.hasAttribute("ShipToKey") && !interestingElement.hasAttribute("AddressDisplay") && !YFCCommon.isVoid(interestingElement.getAttribute("ShipToKey"))) {
				interestingElement.setAttribute("AddressDisplay", SCCSDataProviderUtils.getPersonInfoDescription(context, localApi, interestingElement.getAttribute("ShipToKey")));
			} */

			if(interestingElement.hasAttribute("TransactionalUOM") && !interestingElement.hasAttribute("DisplayTransactionalUOM") && YFCCommon.equals(sAttribute, "DisplayTransactionalUOM")){
				String sHideUOMRule = BDADataProviderUtils.getUOMDisplayRuleValue(context, localApi, callingOrg);
				
				if(YFCCommon.equals("Y",sHideUOMRule)){
					YFCElement elemOrderLine = (YFCElement) interestingElement.getParentNode();
					if (!(elemOrderLine.getNodeName().equals("OrderLine")) ) {
						elemOrderLine = interestingElement;
					}
					String itemGroupCode = null;
					String uom = interestingElement.getAttribute("TransactionalUOM");
					
					if(elemOrderLine != null){
						itemGroupCode = elemOrderLine.getAttribute("ItemGroupCode");
					}
					if(YFCCommon.isVoid(uom)) {
						interestingElement.setAttribute("DisplayTransactionalUOM", "");
					} else if(!YFCCommon.isVoid(callingOrg) && !YFCCommon.isVoid(itemGroupCode)){
						String desc = BDADataProviderUtils.getUnitOfMeasureDescription(context, localApi,uom,callingOrg,itemGroupCode);
						interestingElement.setAttribute("DisplayTransactionalUOM", desc);
						//Rule is configured to use transactional UOM. Evaluating transaction UOM description and set display format from bundle entry.
						interestingElement.setAttribute("UOMDisplayFormat", "formattedQty");
					}else{
						cat.error("Could not resolve UOM Display Name because either UnitOfMeasure, ItemGroupCode, or OrgCode was not available");
					}
				}else if(YFCCommon.equals("H",sHideUOMRule)){
					//Rule is configured to not show UOM. Skipping description evaluation and return formating string for only Quantity.
					interestingElement.setAttribute("UOMDisplayFormat", "qtyOnly");
				}
				
				//In the case of shipment tracking we need to provide the UOM information on each piece of tracking data that displays quantity
				//because the forward bindings will not be able to see the attributes on the order line element
				YFCElement trackingData = apiOutput.getChildElement("LineTracking");
				if(!YFCCommon.isVoid(trackingData)){
					copyUOMDisplayInfoToTrackingData(trackingData, interestingElement, "DisplayTransactionalUOM");
				}
			}
			
			if(interestingElement.hasAttribute("UnitOfMeasure") && !interestingElement.hasAttribute("DisplayUnitOfMeasure") && YFCCommon.equals(sAttribute, "DisplayUnitOfMeasure")){
				String sHideUOMRule = BDADataProviderUtils.getUOMDisplayRuleValue(context, localApi, callingOrg);
				
				if(YFCCommon.equals("Y",sHideUOMRule)){
					String itemGroupCode = null;
					String uom = interestingElement.getAttribute("UnitOfMeasure");
	
					if(apiOutput.getNodeName().equals("AlternateStores")){
						itemGroupCode = "PROD";
					} else if(interestingElement.getParentNode().getNodeName().equals("OrderLine")){
						itemGroupCode = interestingElement.getParentElement().getAttribute("ItemGroupCode");
					}else if(interestingElement.getNodeName().equals("ShipmentLine")){
						YFCElement oLine = interestingElement.getChildElement("OrderLine");
						if(!YFCCommon.isVoid(oLine)){
							itemGroupCode = oLine.getAttribute("ItemGroupCode");
						}
					}else if(interestingElement.getNodeName().equals("ContainerDetail")){
						YFCElement shipLine = interestingElement.getChildElement("ShipmentLine");
						if(!YFCCommon.isVoid(shipLine)){
							YFCElement oLine = shipLine.getChildElement("OrderLine");
							if(!YFCCommon.isVoid(oLine)){
								itemGroupCode = oLine.getAttribute("ItemGroupCode", "PROD");
							}
						}
					}else{
						itemGroupCode = interestingElement.getAttribute("ItemGroupCode");
					}
					if(YFCCommon.isVoid(uom)) {
						interestingElement.setAttribute("DisplayUnitOfMeasure", "");
					} else if(!YFCCommon.isVoid(callingOrg) && !YFCCommon.isVoid(itemGroupCode)){
						String desc = BDADataProviderUtils.getUnitOfMeasureDescription(context, localApi,uom,callingOrg,itemGroupCode);
						interestingElement.setAttribute("DisplayUnitOfMeasure", desc);
						//Rule is configured to use inventory UOM. Evaluating inventory UOM description and set display format from bundle entry.
						interestingElement.setAttribute("UOMDisplayFormat", "formattedQty");
					}else{
						cat.error("Could not resolve UOM Display Name because either UnitOfMeasure, ItemGroupCode, or OrgCode was not available");
					}
				}else if(YFCCommon.equals("H",sHideUOMRule)){
					//Rule is configured to not show UOM. Skipping description evaluation and return formating string for only Quantity.
					interestingElement.setAttribute("UOMDisplayFormat", "qtyOnly");
				}
				//In the case of shipment tracking we need to provide the UOM information on each piece of tracking data that displays quantity
				//because the forward bindings will not be able to see the attributes on the order line element
				YFCElement trackingData = apiOutput.getChildElement("LineTracking");
				if(!YFCCommon.isVoid(trackingData)){
					copyUOMDisplayInfoToTrackingData(trackingData, interestingElement, "DisplayUnitOfMeasure");
				}
			}
		}
	}

	private void copyUOMDisplayInfoToTrackingData(YFCElement trackingData, YFCElement interestingElement, String interestingAttribute){
		String uomDisplay = interestingElement.getAttribute(interestingAttribute);
		String format = interestingElement.getAttribute("UOMDisplayFormat");
		
		YFCElement breakups = trackingData.getChildElement("LineBreakups");
		for(YFCElement breakup : breakups.getChildren()){
			if(!YFCCommon.isVoid(format)){
				breakup.setAttribute("UOMDisplayFormat", format);
			}
			if(!YFCCommon.isVoid(uomDisplay)){
				breakup.setAttribute(interestingAttribute, uomDisplay);
			}
		}
		YFCElement exceptions = trackingData.getChildElement("LineExceptions");
		for(YFCElement exception : exceptions.getChildren()){
			exception.setAttribute("UOMDisplayFormat", format);
			if(!YFCCommon.isVoid(uomDisplay)){
				exception.setAttribute(interestingAttribute, uomDisplay);
			}
		}
	}
	
	
	
}
