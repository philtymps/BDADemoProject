/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/
package com.ibm.mobile.dataprovider;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.ui.backend.util.APIManager;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCI18NUtils;
import com.yantra.yfc.util.YFCLocale;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDADataProviderUtils {

	private static String sReturnDocType = null;
	private static String sSalesDocType = null;
	
	private static BDASimpleCache <String, String> holdTypeMap;
	private static BDASimpleCache <String, String> chargeNameMap;
	private static BDASimpleCache <String, String> codeLongDescriptionMap;
	private static BDASimpleCache <String, String> codeShortDescriptionMap;
	private static BDASimpleCache <String, String> carrierServiceCodeMap;
	private static BDASimpleCache <String, String> unitOfMeasureMap;
	private static BDASimpleCache <String, String> customerMasterOrgMap;
	private static BDASimpleCache <String, String> organizationNameMap;
	private static BDASimpleCache <String, YFCElement> localeNameMap;
	private static BDASimpleCache <String, String> orgLocaleMap;
	private static BDASimpleCache <String, String> ruleMap;
	private static BDASimpleCache <String, String> statusMap;
	private static BDASimpleCache <String, String> paymentStatusMap;
	
	public static boolean flushCache(){
		try {
			getHoldTypeCache().clear();
			getChargeNameMap().clear();
			getCodeLongDescriptionMap().clear();
			getCodeShortDescriptionMap().clear();
			getCarrierServiceCodeMap().clear();
			getUnitOfMeasureMap().clear();
			getCustomerMasterOrg().clear();
			getOrgNameMap().clear();
			getLocaleMap().clear();
			getOrgLocaleMap().clear();
			getRuleMap().clear();
			getPaymentStatusMap().clear();
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	private static BDASimpleCache <String, String> getHoldTypeCache(){
		if (holdTypeMap == null){
			holdTypeMap = new BDASimpleCache<String, String>(200);
		}
		return holdTypeMap;
	}
	
	private static BDASimpleCache <String, String> getChargeNameMap(){
		if (chargeNameMap == null){
			chargeNameMap = new BDASimpleCache<String, String>(2000);
		}
		return chargeNameMap;
	}
	
	private static BDASimpleCache <String, String> getCodeLongDescriptionMap(){
		if (codeLongDescriptionMap == null){
			codeLongDescriptionMap = new BDASimpleCache<String, String>(2000);
		}
		return codeLongDescriptionMap;
	}
	
	private static BDASimpleCache <String, String> getCodeShortDescriptionMap(){
		if (codeShortDescriptionMap == null){
			codeShortDescriptionMap = new BDASimpleCache<String, String>(99999);
		}
		return codeShortDescriptionMap;
	}
		
	private static BDASimpleCache <String, String> getCarrierServiceCodeMap(){
		if (carrierServiceCodeMap == null){
			carrierServiceCodeMap = new BDASimpleCache<String, String>(99999);
		}
		return carrierServiceCodeMap;
	}
		
	private static BDASimpleCache <String, String> getUnitOfMeasureMap(){
		if (unitOfMeasureMap == null){
			unitOfMeasureMap = new BDASimpleCache<String, String>(2000);
		}
		return unitOfMeasureMap;
	}
		
	private static BDASimpleCache <String, String> getCustomerMasterOrg(){
		if (customerMasterOrgMap == null){
			customerMasterOrgMap = new BDASimpleCache<String, String>(200);
		}
		return customerMasterOrgMap;
	}
	
	private static BDASimpleCache <String, String> getOrgNameMap(){
		if (organizationNameMap == null){
			organizationNameMap = new BDASimpleCache<String, String>(200);
		}
		return organizationNameMap;
	}
	
	private static BDASimpleCache <String, YFCElement> getLocaleMap(){
		if (localeNameMap == null){
			localeNameMap = new BDASimpleCache<String, YFCElement>(200);
		}
		return localeNameMap;
	}
	
	private static BDASimpleCache <String, String> getOrgLocaleMap(){
		if (orgLocaleMap == null){
			orgLocaleMap = new BDASimpleCache<String, String>(100);
		}
		return orgLocaleMap;
	}
	
	private static BDASimpleCache <String, String> getRuleMap(){
		if (ruleMap == null){
			ruleMap = new BDASimpleCache<String, String>(10,60000);
		}
		return ruleMap;
	}
	
	private static BDASimpleCache <String, String> getStatusMap(){
		if (statusMap == null){
			statusMap = new BDASimpleCache<String, String>(400);
		}
		return statusMap;
		
	}
	
	private static BDASimpleCache <String, String> getPaymentStatusMap(){
		if(paymentStatusMap == null){
			paymentStatusMap = new BDASimpleCache<String, String>(50);
		}
		return paymentStatusMap;
	}
	
	public static String getOrganizationCode(YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement) {
		if (!YFCCommon.isVoid(interestingElement.getAttribute("OrganizationCode"))){
			return interestingElement.getAttribute("OrganizationCode");
		} else if(!YFCCommon.isVoid(interestingElement.getAttribute("EnterpriseCode"))){
			return interestingElement.getAttribute("EnterpriseCode");
		} else if(apiOutput.getNodeName().equals("Order")){
			return apiOutput.getAttribute("EnterpriseCode");
		} else if(apiOutput.getNodeName().equals("Shipment")) {
			return apiOutput.getAttribute("EnterpriseCode");
		} else if(apiOutput.getNodeName().equals("ItemList")) {
			return apiOutput.getAttribute("CallingOrganizationCode");
		} else if(apiOutput.getNodeName().equals("AlternateStores")){
			return apiOutput.getAttribute("OrganizationCode");
		} else if(apiOutput.getNodeName().equals("InvoiceDetail")) {
			YFCElement elemInvoiceHeader = (YFCElement) interestingElement.getParentNode().getParentNode().getParentNode();
			return elemInvoiceHeader.getChildElement("Order", true).getAttribute("EnterpriseCode");
		} else if(!YFCCommon.isVoid(apiInput) && !YFCCommon.isVoid(apiInput.getAttribute("EnterpriseCode"))){
			return apiInput.getAttribute("EnterpriseCode");
		} else if(!YFCCommon.isVoid(apiInput) && !YFCCommon.isVoid(apiInput.getAttribute("OrganizationCode"))){
			return apiInput.getAttribute("OrganizationCode");
		} else if(!YFCCommon.isVoid(apiInput) && !YFCCommon.isVoid(apiInput.getAttribute("CallingOrganizationCode"))){
			return apiInput.getAttribute("CallingOrganizationCode");
		} else if(apiOutput.getNodeName().equals("OrderLineList") && !YFCCommon.isVoid(apiOutput.getChildElement("Order")) && !YFCCommon.isVoid(apiOutput.getChildElement("Order").getAttribute("EnterpriseCode"))){
			return apiOutput.getChildElement("Order").getAttribute("EnterpriseCode");
		} else if (interestingElement.getNodeName().equals("OrderLine")) {
			return interestingElement.getChildElement("Order", true).getAttribute("EnterpriseCode");
		} else if(interestingElement.getNodeName().equals("ShipmentLine")){
			return interestingElement.getChildElement("Shipment", true).getAttribute("EnterpriseCode");
		} else if(interestingElement.getNodeName().equals("ContainerDetail")){ 
			return apiOutput.getChildElement("Shipment", true).getAttribute("EnterpriseCode");
		}else if (apiOutput.getNodeName().equals("OrderLine")) {
			return apiOutput.getChildElement("Order", true).getAttribute("EnterpriseCode");
		} else {
			YFCElement elemOrderLine = (YFCElement) interestingElement.getParentNode();
			return elemOrderLine.getChildElement("Order", true).getAttribute("EnterpriseCode");
		}
	}
	
	public static String getUomDescription (YFSEnvironment context, YIFApi localApi, String sUnitOfMeasure, String sUomType) {
		BDASimpleCache<String, String> map = getUnitOfMeasureMap();
		if (!map.containsKey(sUomType + "_" + sUnitOfMeasure)) {
			YFCDocument dInput = YFCDocument.createDocument("Uom");
			YFCElement eInput = dInput.getDocumentElement();
			if (!YFCCommon.isVoid(sUomType)){
				eInput.setAttribute("UomType", sUomType);
			}
			if (!YFCCommon.isVoid(sUnitOfMeasure)){
				eInput.setAttribute("Uom", sUnitOfMeasure);
			}
			YFCDocument dTemplate = YFCDocument.createDocument("UomList");
			YFCElement eU = dTemplate.getDocumentElement().createChild("Uom");
			eU.setAttribute("Uom", "");
			eU.setAttribute("OrganizationCode", "");
			eU.setAttribute("UomDescription", "");
			eU.setAttribute("UomType", "");
			
			YFCElement eOutput = invokeApi(localApi, context,"getUomList", dInput.getDocument(), null);
			for (YFCElement eOrg : eOutput.getChildren()){
				map.put(eOrg.getAttribute("UomType") + "_" + eOrg.getAttribute("Uom"), eOrg.getAttribute("UomDescription"));
			}
		}
		return map.get(sUomType + "_" + sUnitOfMeasure);
	}
	
	public static String getShipmentStatusDescription (YFSEnvironment context, YIFApi localApi, String sStatus, String sOrganizationCode) {
		BDASimpleCache<String, String> map = getStatusMap();
		if (!map.containsKey(sStatus + "_" + sOrganizationCode)) {
			YFCDocument dInput = YFCDocument.createDocument("Status");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("ProcessTypeKey", "ORDER_DELIVERY");
			if (!YFCCommon.isVoid(sOrganizationCode)){
				eInput.setAttribute("CallingOrganizationCode", sOrganizationCode);
			}
			YFCDocument dTemplate = YFCDocument.createDocument("StatusList");
			YFCElement eU = dTemplate.getDocumentElement().createChild("Status");
			eU.setAttribute("Status", "");
			eU.setAttribute("Description", "");
			eU.setAttribute("StatusName", "");
			
			YFCElement eOutput = invokeApi(localApi, context,"getStatusList", dInput.getDocument(), dTemplate.getDocument());
			for (YFCElement eStatus : eOutput.getChildren()){
				map.put(eStatus.getAttribute("Status") + "_" + sOrganizationCode, eStatus.getAttribute("StatusName"));
			}
		}
		return map.get(sStatus + "_" + sOrganizationCode);
	}
	
	public static String getCarrierServiceDescription (YFSEnvironment context, YIFApi localApi, String sCallingOrg, String sCarrierServiceCode) {
		BDASimpleCache<String, String> map = getCarrierServiceCodeMap();
		if (!map.containsKey("CARRIER_" + sCallingOrg + "_" + sCarrierServiceCode)) {
			YFCDocument dInput = YFCDocument.createDocument("CarrierService");
			YFCElement eInput = dInput.getDocumentElement();
			if (!YFCCommon.isVoid(sCallingOrg)){
				eInput.setAttribute("CallingOrganizationCode", sCallingOrg);
			}
			YFCDocument dTemplate = YFCDocument.createDocument("CarrierServiceList");
			YFCElement eC = dTemplate.getDocumentElement().createChild("CarrierService");
			eC.setAttribute("CarrierServiceCode", "");
			eC.setAttribute("CarrierServiceDesc", "");
			YFCElement eOutput = invokeApi(localApi, context,"getCarrierServiceList", dInput.getDocument(), dTemplate.getDocument());
			for (YFCElement eOrg : eOutput.getChildren()){
				map.put("CARRIER_" + sCallingOrg + "_" + eOrg.getAttribute("CarrierServiceCode"), eOrg.getAttribute("CarrierServiceDesc"));
			}
		}
		return map.get("CARRIER_" + sCallingOrg + "_" + sCarrierServiceCode);
	}
	
	public static String getSCACName (YFSEnvironment context, YIFApi localApi, String sCallingOrg, String sScac) {
		BDASimpleCache<String, String> map = getCarrierServiceCodeMap();
		if (!map.containsKey("SCAC_" + sCallingOrg + "_" + sScac)) {
			YFCDocument dInput = YFCDocument.createDocument("Scac");
			YFCElement eInput = dInput.getDocumentElement();
			if (!YFCCommon.isVoid(sCallingOrg)){
				eInput.setAttribute("CallingOrganizationCode", sCallingOrg);
			}
			YFCDocument dTemplate = YFCDocument.createDocument("ScacList");
			YFCElement eO = dTemplate.getDocumentElement().createChild("Scac");
			eO.setAttribute("Scac", "");
			eO.setAttribute("ScacDesc", "");
			eO.setAttribute("OrganizationCode", "");
			YFCElement eOutput = invokeApi(localApi, context,"getScacList", dInput.getDocument(), dTemplate.getDocument());
			for (YFCElement eOrg : eOutput.getChildren()){
				map.put("SCAC_" + sCallingOrg + "_" + eOrg.getAttribute("Scac"), eOrg.getAttribute("ScacDesc"));
			}
		}
		return map.get("SCAC_" + sCallingOrg + "_" + sScac);
	}
	
	public static String getPaymentStatusDesc(YFSEnvironment context, YIFApi localApi, String sPaymentStatus){
		BDASimpleCache<String, String> map = getPaymentStatusMap();
		if (!map.containsKey(sPaymentStatus)) {
			YFCDocument dInput = YFCDocument.createDocument("PaymentStatus");
			YFCElement eInput = dInput.getDocumentElement();
			YFCDocument dTemplate = YFCDocument.createDocument("PaymentStatusList");
			YFCElement eO = dTemplate.getDocumentElement().createChild("PaymentStatus");
			eO.setAttribute("CodeType", "");
			eO.setAttribute("Description", "");
			YFCElement eOutput = invokeApi(localApi, context,"getPaymentStatusList", dInput.getDocument(), dTemplate.getDocument());
			for (YFCElement eOrg : eOutput.getChildren()){
				map.put(eOrg.getAttribute("CodeType"), eOrg.getAttribute("Description"));
			}
		}
		return map.get(sPaymentStatus);
	}
	public static String getUnitOfMeasureDescription(YFSEnvironment context, YIFApi localApi,String uom, String callingOrg, String itemGroupCode) {
		BDASimpleCache<String, String> mapDesc = getUnitOfMeasureMap();
		String key = getUOMKey(uom, callingOrg, itemGroupCode);
		if (mapDesc.containsKey(key)){
			return mapDesc.get(key);
		} else {
			populateUOMDesc(context, localApi, uom, callingOrg, itemGroupCode, mapDesc);
			return mapDesc.get(key);
		}
	}
	
	private static void populateUOMDesc(YFSEnvironment context, YIFApi localApi, String uom,String callingOrg, String itemGroupCode, BDASimpleCache<String, String> mapDesc) {
		YFCDocument dInput = YFCDocument.createDocument("ItemUOMMaster");
		YFCElement eInput = dInput.getDocumentElement();

		eInput.setAttribute("UnitOfMeasure", uom);
		eInput.setAttribute("CallingOrganizationCode", callingOrg);
		eInput.setAttribute("ItemGroupCode", itemGroupCode);
		YFCDocument dTemplate = YFCDocument.createDocument("ItemUOMMasterList");
		YFCElement eU = dTemplate.getDocumentElement().createChild("ItemUOMMaster");
		eU.setAttribute("Description","");
		eU.setAttribute("OrganizationCode","");
		eU.setAttribute("UnitOfMeasure","");
		eU.setAttribute("ItemGroupCode","");
		
		YFCElement eOutput = invokeApi(localApi, context,"getItemUOMMasterList", dInput.getDocument(), dTemplate.getDocument());
		for (YFCElement eUOM : eOutput.getChildren()){
			mapDesc.put(getUOMKey(eUOM.getAttribute("UnitOfMeasure"), callingOrg, itemGroupCode), eUOM.getAttribute("Description"));
		}
	}
	

	private static String getUOMKey(String uom, String callingOrg, String itemGroupCode) {
		return uom+callingOrg+itemGroupCode;
	}
	
	public static YFCElement getOrgAndDocTypeForOrderHeaderKey(YFSEnvironment context, YIFApi localApi, String sOrderHeaderKey){
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		
		if (!YFCCommon.isVoid(sOrderHeaderKey)){
			eInput.setAttribute("OrderHeaderKey", sOrderHeaderKey);
			YFCDocument dTemplate = YFCDocument.createDocument("OrderList");
			YFCElement eOrder = dTemplate.getDocumentElement().createChild("Order");
			eOrder.setAttribute("OrderHeaderKey", "");
			eOrder.setAttribute("DocumentType", "");
			eOrder.setAttribute("EnterpriseCode", "");
			eOrder.setAttribute("SellerOrganizationCode", "");
			YFCElement eOutput = invokeApi(localApi, context,"getOrderList", dInput.getDocument(), dTemplate.getDocument());
			if (!YFCCommon.isVoid(eOutput.getChildElement("Order"))){
				return eOutput.getChildElement("Order");
			}
		}
		return null;
	}
	
	public static String getUOMDisplayRuleValue(YFSEnvironment context, YIFApi localApi, String callingOrg){
		BDASimpleCache<String, String> map = getRuleMap();
		String key = callingOrg + "_" + "YCD_USE_TRANSACTIONAL_QUANTITY";
		if (!map.containsKey(key)) {
			YFCDocument dInput = YFCDocument.createDocument("Rules");
			YFCElement eRuleInput = dInput.getDocumentElement();
			eRuleInput.setAttribute("RuleSetFieldName", "YCD_USE_TRANSACTIONAL_QUANTITY");
			eRuleInput.setAttribute("CallingOrganizationCode", callingOrg);
			YFCDocument dTemplate = YFCDocument.createDocument("Rules");
			YFCElement eR = dTemplate.getDocumentElement();
			eR.setAttribute("RuleSetValue","");
			eR.setAttribute("RuleType","");
			eR.setAttribute("OrganizationCode","");
			eR.setAttribute("DocumentType","");
			YFCElement ruleOutput = invokeApi(localApi, context,"getRuleDetails", dInput.getDocument(), dTemplate.getDocument());
			String ruleValue = ruleOutput.getAttribute("RuleSetValue");
			
			map.put(key, ruleValue);
		}
		return map.get(key);
	}
	// ===================================================================================================================================================
	// (REO) Methods for fetching descriptions for common codes (copied from SCCSOrderLineDataProvider.java)
	// ===================================================================================================================================================
	/*public static String getCommonCodeDescription (YFSEnvironment context, YIFApi localApi, String sCodeValue, String sCodeType, String sEnterpriseCode, String sDocumentType){
		return getCodeValue(context, sEnterpriseCode, sDocumentType, sCodeType, sCodeValue);
	}
	
	public static String getPaymentTypeDescription (YFSEnvironment context, YIFApi localApi, String sPaymentType, String sEnterpriseCode){
		return getPaymenType(context, sEnterpriseCode, sPaymentType);
	}*/
	public static String getLocaleForOrg(YFSEnvironment context, YIFApi localApi, String sOrganizationCode){ 
		BDASimpleCache<String, String> map = getOrgLocaleMap();
		if (map != null && map.containsKey(sOrganizationCode)){
			return map.get(sOrganizationCode);
		}
		populateOrgLocale(map, context, localApi, sOrganizationCode);
		return map.get(sOrganizationCode);
	}
	
	public static String getCurrencyForOrg(YFSEnvironment context, YIFApi localApi, String sOrganizationCode){
		String sLocale = getLocaleForOrg(context, localApi, sOrganizationCode);
		if(!YFCCommon.isVoid(sLocale)){
			YFCElement eLocale = getLocaleDetails(context, localApi, sLocale);
			if (!YFCCommon.isVoid(eLocale)){
				return eLocale.getAttribute("Currency");
			}
		}
		return null;
	}
	
	public static String getPaymentTypeDescription (YFSEnvironment context, YIFApi localApi, String sPaymentType, String sEnterpriseCode){
		BDASimpleCache<String, String> map = getCodeShortDescriptionMap();
		String codeKey = getCodeKey(sPaymentType, sEnterpriseCode, "PAYMENT_TYPE");
		if (map != null && map.containsKey(codeKey)){
			return map.get(codeKey);
		}
		populatePaymenType(map, context, localApi, sEnterpriseCode, sPaymentType);
		return map.get(codeKey);
	}
	
	public static String getCommonCodeDescription (YFSEnvironment context, YIFApi localApi, String sCodeValue, String sCodeType, String sEnterpriseCode, String sDocumentType){
		BDASimpleCache<String, String> map = getCodeLongDescriptionMap();
		String codeKey = getCodeKey(sCodeValue, sEnterpriseCode, sDocumentType, sCodeType);
		if (map != null && map.containsKey(codeKey)){
			return map.get(codeKey);
		}
		populateCode(map, context, localApi, sEnterpriseCode, sDocumentType, sCodeType);
		return map.get(codeKey);
	}
	
	public static String getCommonCodeShortDescription(YFSEnvironment context, YIFApi localApi, String sCodeValue, String sCodeType, String sEnterpriseCode, String sDocumentType){
		BDASimpleCache<String, String> map = getCodeShortDescriptionMap();
		String codeKey = getCodeKey(sCodeValue, sEnterpriseCode, sDocumentType, sCodeType);
		if (map != null && map.containsKey(codeKey)){
			return map.get(codeKey);
		}
		populateShortCode(map, context, localApi, sEnterpriseCode, sDocumentType, sCodeType);
		return map.get(codeKey);
	}
	
	public static String getChargeNameDescription (YFSEnvironment context, YIFApi localApi, String sChargeName, String sEnterpriseCode, String sDocumentType){
		BDASimpleCache<String, String> map = getChargeNameMap();
		String codeKey = getCodeKey(sChargeName, sEnterpriseCode, sDocumentType);
		if (map != null && map.containsKey(codeKey)){
			return map.get(codeKey);
		}
		populateChargeName(map, context, localApi, sEnterpriseCode, sDocumentType, sChargeName);
		return map.get(codeKey);
	}
	
	public static String getHoldTypeDescription (YFSEnvironment context, YIFApi localApi, String sHoldType, String sEnterpriseCode, String sDocumentType, String sProcessTypeKey){
		BDASimpleCache<String, String> map = getHoldTypeCache();
		String codeKey = getCodeKey(sHoldType, sEnterpriseCode, sDocumentType, sProcessTypeKey);
		if (map != null && map.containsKey(codeKey)){
			return map.get(codeKey);
		}
		populateHoldType(map, context, localApi, sEnterpriseCode, sDocumentType, sHoldType, sProcessTypeKey);
		return map.get(codeKey);
	}
	
	public static String getCustomerMasterOrg(YFSEnvironment context, YIFApi localApi, String org){
		BDASimpleCache<String, String> mapOrg = getCustomerMasterOrg();
		if (mapOrg.containsKey(org)){
			return mapOrg.get(org);
		} else {
			YFCDocument dInput = YFCDocument.createDocument("Organization");
			YFCElement eInput = YFCDocument.createDocument("Organization").getDocumentElement();
			eInput.setAttribute("OrganizationCode", org);
			YFCDocument dTemplate = YFCDocument.createDocument("OrganizationList");
			YFCElement eOrganization = dTemplate.getDocumentElement().createChild("Organization");
			eOrganization.setAttribute("OrganizationCode", "");
			eOrganization.setAttribute("LocaleCode", "");
			eOrganization.setAttribute("OrganizationName", "");
			eOrganization.setAttribute("CustomerMasterOrganizationCode", "");
			YFCElement eOutput = invokeApi(localApi, context,"getOrganizationList", dInput.getDocument(), dTemplate.getDocument());
			for (YFCElement eOrg : eOutput.getChildren()){
				mapOrg.put(eOrg.getAttribute("OrganizationCode"), eOrg.getAttribute("CustomerMasterOrganizationCode"));
			}
			return mapOrg.get(org);
		}

	}
	
	public static String getOrganizationName(YFSEnvironment context, YIFApi localApi, String sCode){
		BDASimpleCache<String, String> mapOrg = getOrgNameMap();
		if (mapOrg.containsKey(sCode)){
			return mapOrg.get(sCode);
		} else {
			YFCDocument dInput = YFCDocument.createDocument("Organization");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("OrganizationCode", sCode);
			YFCDocument dTemplate = YFCDocument.createDocument("OrganizationList");
			YFCElement eOrganization = dTemplate.getDocumentElement().createChild("Organization");
			eOrganization.setAttribute("OrganizationCode", "");
			eOrganization.setAttribute("LocaleCode", "");
			eOrganization.setAttribute("OrganizationName", "");
			YFCElement eOutput = invokeApi(localApi, context,"getOrganizationList", dInput.getDocument(), dTemplate.getDocument());
			for (YFCElement eOrg : eOutput.getChildren()){
				mapOrg.put(eOrg.getAttribute("OrganizationCode"), eOrg.getAttribute("OrganizationName"));
			}
			return mapOrg.get(sCode);
		}
	}
	
	public static String getShipnodeDescription(YFSEnvironment context, YIFApi localApi, String shipnodeKey) {
		BDASimpleCache<String, String> mapOrg = getOrgNameMap();
		if (mapOrg.containsKey("SHIP_NODE_" + shipnodeKey)){
			return mapOrg.get("SHIP_NODE_" + shipnodeKey);
		} else {
			YFCDocument dInput = YFCDocument.createDocument("ShipNode");
			YFCElement shipnodeInput = dInput.getDocumentElement();
			shipnodeInput.setAttribute("ShipnodeKey", shipnodeKey);
			YFCDocument dTemplate = YFCDocument.createDocument("ShipNodeList");
	        YFCElement eShipNode = dTemplate.getDocumentElement().createChild("ShipNode");
	        eShipNode.setAttribute("ShipNode","");
	        eShipNode.setAttribute("Description","");
	        
			YFCElement eOutput = invokeApi(localApi, context,"getShipNodeList", dInput.getDocument(), null);

			for (YFCElement eOrg : eOutput.getChildren()){
				mapOrg.put("SHIP_NODE_" + shipnodeKey, eOrg.getAttribute("Description"));
			}
			return mapOrg.get("SHIP_NODE_" + shipnodeKey);
		}
	}
	
	public static String getShipnodeFullDescription(YFSEnvironment context, YIFApi localApi, String shipnodeKey){
		BDASimpleCache<String, String> mapOrg = getOrgNameMap();
		if (mapOrg.containsKey("SHIP_NODE_DESC" + shipnodeKey)){
			return mapOrg.get("SHIP_NODE_DESC" + shipnodeKey);
		} else {
			YFCDocument dInput = YFCDocument.createDocument("ShipNode");
			YFCElement shipnodeInput = dInput.getDocumentElement();
			shipnodeInput.setAttribute("ShipnodeKey", shipnodeKey);
			YFCDocument dTemplate = YFCDocument.createDocument("ShipNodeList");
	        YFCElement eShipNode = dTemplate.getDocumentElement().createChild("ShipNode");
	        eShipNode.setAttribute("ShipNode","");
	        eShipNode.setAttribute("Description","");
	        eShipNode.setAttribute("ShipNodeKey","");
	        eShipNode.createChild("ShipNodePersonInfo");
			YFCElement eOutput = invokeApi(localApi, context,"getShipNodeList", dInput.getDocument(), dTemplate.getDocument());
            String city = "";
           
			for (YFCElement eOrg : eOutput.getChildren()){
				String shipNodeDesc = eOrg.getAttribute("Description");
				YFCNodeList list  = eOrg.getElementsByTagName("ShipNodePersonInfo");
				for(int  i = 0; i < list.getLength(); i++){
					YFCElement shipNodePersonInfo = (YFCElement)list.item(i);
					city = shipNodePersonInfo.getAttribute("City");
					if(city != null){
						String bundleEntry = YFCI18NUtils.getString("ShipnodeFullDescription", YFCLocale.getDefaultLocale());
						Object[] args =  new Object[]{eOrg.getAttribute("Description"),city};
						String sShipnodeFullDesc = MessageFormat.format(bundleEntry, args);						
						mapOrg.put("SHIP_NODE_DESC" + shipnodeKey, sShipnodeFullDesc);
					}
				}				
			}
			return mapOrg.get("SHIP_NODE_DESC" + shipnodeKey);
		}
	}

	
	public static YFCElement getLocaleDetails(YFSEnvironment context, YIFApi localApi, String localeCode){
		BDASimpleCache<String, YFCElement> mapLocale = getLocaleMap();
		if (mapLocale.containsKey(localeCode)){
			return mapLocale.get(localeCode);
		} else {
			YFCDocument dInput = YFCDocument.createDocument("Locale");
			YFCElement eOutput = invokeApi(localApi, context,"getLocaleList", dInput.getDocument(), null);
			for (YFCElement eLocale : eOutput.getChildren()){
				mapLocale.put(eLocale.getAttribute("Localecode"), eLocale);
			}
			return mapLocale.get(localeCode);
		}

	}
	
	private static String getCodeKey(String sCodeValue, String sEnterpriseCode, String sDocumentType){
		String temp = sCodeValue;
		if (!YFCCommon.isVoid(sDocumentType)){
			temp = sDocumentType + "_" + temp;
		}
		if (!YFCCommon.isVoid(sEnterpriseCode)){
			temp = sEnterpriseCode + "_" + temp;
		}
		return temp;
	}
	
	private static String getCodeKey(String sCodeValue, String sEnterpriseCode, String sDocumentType, String sExtra){
		String temp = getCodeKey(sCodeValue, sEnterpriseCode, sDocumentType);
		if (!YFCCommon.isVoid(sExtra)){
			temp = temp + "_" + sExtra;
		}
		return temp;
	}

	private static void populateCode(BDASimpleCache<String, String> codes, YFSEnvironment context, YIFApi localApi, String sEnterpriseCode, String sDocumentType, String sCodeType){
		YFCDocument dInput = YFCDocument.createDocument("CommonCode");
		YFCElement eInput = dInput.getDocumentElement();
		if (!YFCCommon.isVoid(sEnterpriseCode)){
			eInput.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		}
		if (!YFCCommon.isVoid(sDocumentType)){
			eInput.setAttribute("DocumentType", sDocumentType);
		}
		eInput.setAttribute("CodeType", sCodeType);
		YFCDocument dTemplate = YFCDocument.createDocument("CommonCodeList");
		YFCElement eChargeName = dTemplate.getDocumentElement().createChild("CommonCode");
		eChargeName.setAttribute("CodeValue","");
		eChargeName.setAttribute("OrganizationCode","");
		eChargeName.setAttribute("DocumentType","");
		eChargeName.setAttribute("EnterpriseCode","");
		eChargeName.setAttribute("CodeName","");
		eChargeName.setAttribute("CodeType","");
		eChargeName.setAttribute("CodeLongDescription","");
		YFCElement eOutput = invokeApi(localApi, context,"getCommonCodeList", dInput.getDocument(), dTemplate.getDocument());
		for (YFCElement eOrg : eOutput.getChildren()){
			codes.put(getCodeKey(eOrg.getAttribute("CodeValue"), sEnterpriseCode, sDocumentType, eOrg.getAttribute("CodeType")), eOrg.getAttribute("CodeLongDescription"));
		}
	}

	private static void populateShortCode(BDASimpleCache<String, String> codes, YFSEnvironment context, YIFApi localApi, String sEnterpriseCode, String sDocumentType, String sCodeType){
		YFCDocument dInput = YFCDocument.createDocument("CommonCode");
		YFCElement eInput = dInput.getDocumentElement();
		if (!YFCCommon.isVoid(sEnterpriseCode)){
			eInput.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		}
		if (!YFCCommon.isVoid(sDocumentType)){
			eInput.setAttribute("DocumentType", sDocumentType);
		}
		eInput.setAttribute("CodeType", sCodeType);
		YFCDocument dTemplate = YFCDocument.createDocument("CommonCodeList");
		YFCElement eChargeName = dTemplate.getDocumentElement().createChild("CommonCode");
		eChargeName.setAttribute("CodeValue","");
		eChargeName.setAttribute("OrganizationCode","");
		eChargeName.setAttribute("DocumentType","");
		eChargeName.setAttribute("EnterpriseCode","");
		eChargeName.setAttribute("CodeName","");
		eChargeName.setAttribute("CodeType","");
		eChargeName.setAttribute("CodeShortDescription","");
		YFCElement eOutput = invokeApi(localApi, context,"getCommonCodeList", dInput.getDocument(), dTemplate.getDocument());
		for (YFCElement eOrg : eOutput.getChildren()){
			codes.put(getCodeKey(eOrg.getAttribute("CodeValue"), sEnterpriseCode, sDocumentType, eOrg.getAttribute("CodeType")), eOrg.getAttribute("CodeShortDescription"));
		}
	}

	
	private static void populateHoldType(BDASimpleCache<String, String> holds, YFSEnvironment context, YIFApi localApi, String sEnterpriseCode, String sDocumentType, String sHoldType, String sProcessType){
		YFCDocument dInput = YFCDocument.createDocument("HoldType");
		YFCElement holdsInput = dInput.getDocumentElement();
		holdsInput.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		holdsInput.setAttribute("DocumentType", sDocumentType);
		holdsInput.setAttribute("ProcessTypeKey", sProcessType);
		YFCDocument dTemplate = YFCDocument.createDocument("HoldTypeList");
		YFCElement eChargeName = dTemplate.getDocumentElement().createChild("HoldType");
		eChargeName.setAttribute("HoldType","");
		eChargeName.setAttribute("HoldTypeDescription","");
		YFCElement holdList = invokeApi(localApi, context,"getHoldTypeList", dInput.getDocument(), dTemplate.getDocument());
		for (YFCElement eOrg : holdList.getChildren()){
			holds.put(getCodeKey(eOrg.getAttribute("HoldType"), sEnterpriseCode, sDocumentType, sProcessType), eOrg.getAttribute("HoldTypeDescription"));
		}
	}	
	
	private static void populateChargeName(BDASimpleCache<String, String> charges, YFSEnvironment context, YIFApi localApi, String sEnterpriseCode, String sDocumentType, String sChargeName){
		YFCDocument dInput = YFCDocument.createDocument("ChargeName");
		YFCElement eInput = YFCDocument.createDocument("ChargeName").getDocumentElement();
		if (!YFCCommon.isVoid(sEnterpriseCode)){
			eInput.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		}
		if (!YFCCommon.isVoid(sDocumentType)){
			eInput.setAttribute("DocumentType", sDocumentType);
		}
		if (!YFCCommon.isVoid(sDocumentType)){
			eInput.setAttribute("ChargeName", sChargeName);
		}
		YFCDocument dTemplate = YFCDocument.createDocument("ChargeNameList");
		YFCElement eChargeName = dTemplate.getDocumentElement().createChild("ChargeName");
		eChargeName.setAttribute("ChargeName","");
		eChargeName.setAttribute("Description","");
		YFCElement eOutput = invokeApi(localApi, context,"getChargeNameList", dInput.getDocument(), dTemplate.getDocument());
		for (YFCElement eOrg : eOutput.getChildren()){
			charges.put(getCodeKey(eOrg.getAttribute("ChargeName"), sEnterpriseCode, sDocumentType), eOrg.getAttribute("Description"));
		}
	}	
	
	private static void populatePaymenType(BDASimpleCache<String, String> codes, YFSEnvironment context, YIFApi localApi, String sEnterpriseCode, String sPaymentType){
		YFCDocument dInput = YFCDocument.createDocument("PaymentType");
		YFCElement eInput = dInput.getDocumentElement();
		if (!YFCCommon.isVoid(sEnterpriseCode)){
			eInput.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		}
		YFCDocument dTemplate = YFCDocument.createDocument("PaymentTypeList");
		YFCElement ePaymentType = dTemplate.getDocumentElement().createChild("PaymentType");
		ePaymentType.setAttribute("PaymentType","");
		ePaymentType.setAttribute("PaymentTypeDescription","");
		YFCElement eOutput = invokeApi(localApi, context,"getPaymentTypeList", dInput.getDocument(), dTemplate.getDocument());
		for (YFCElement eOrg : eOutput.getChildren()){
			codes.put(getCodeKey(eOrg.getAttribute("PaymentType"), sEnterpriseCode, "PAYMENT_TYPE"), eOrg.getAttribute("PaymentTypeDescription"));
		}
	}
	
	private static void populateOrgLocale(BDASimpleCache<String, String>map, YFSEnvironment context, YIFApi localApi, String sOrganizationCode){
		YFCDocument dInput = YFCDocument.createDocument("Organization");
		YFCElement eInput = dInput.getDocumentElement();
		if (!YFCCommon.isVoid(sOrganizationCode)){
			eInput.setAttribute("OrganizationCode", sOrganizationCode);
		}
		YFCDocument dOrganizationTemplate = YFCDocument.createDocument("OrganizationList");
		YFCElement eOrganization = dOrganizationTemplate.getDocumentElement().createChild("Organization");
		eOrganization.setAttribute("OrganizationCode", "");
		eOrganization.setAttribute("LocaleCode", "");
		eOrganization.setAttribute("OrganizationName", "");
		YFCElement eOutput = invokeApi(localApi, context,"getOrganizationList", dInput.getDocument(), dOrganizationTemplate.getDocument());
		for (YFCElement eOrg : eOutput.getChildren()){
			map.put(eOrg.getAttribute("OrganizationCode"), eOrg.getAttribute("LocaleCode"));
		}
	}
	
	public static String getReturnOrderType(YIFApi localApi, YFSEnvironment context){
		if(!YFCCommon.isVoid(sReturnDocType)){
			return sReturnDocType;
		}
		YFCElement list = getDocTypeDeter(localApi, context);
		if(!YFCCommon.isVoid(list)){
			for (YFCElement eDocTypeDeter : list.getChildren()){
				if (YFCCommon.equals(eDocTypeDeter.getAttribute("DocumentTypeName"), "RETURN_ORDER")){
					sReturnDocType = eDocTypeDeter.getAttribute("DocumentType");
					break;
				}
			}
		}
		return sReturnDocType;
	}
	
	public static String getSalesOrderType(YIFApi localApi, YFSEnvironment context){
		if(!YFCCommon.isVoid(sSalesDocType)){
			return sSalesDocType;
		}
		YFCElement list = getDocTypeDeter(localApi, context);
		if(!YFCCommon.isVoid(list)){
			for (YFCElement eDocTypeDeter : list.getChildren()){
				if (YFCCommon.equals(eDocTypeDeter.getAttribute("DocumentTypeName"), "SALES_ORDER")){
					sSalesDocType = eDocTypeDeter.getAttribute("DocumentType");
					break;
				}
			}
		}
		return sSalesDocType;
	}
	
	public static YFCElement getDocTypeDeter(YIFApi localApi, YFSEnvironment context) {
		YFCDocument dDoc = YFCDocument.createDocument("DocTypeDeter");
		YFCElement eDocTypeDeter = dDoc.getDocumentElement();
		eDocTypeDeter.setAttribute("PCAModuleCode", "YCD");
		
		try {
			return invokeApi(localApi, context,"getDocTypeDeterminationList", dDoc.getDocument(), null);
		}catch (APIManager.XMLExceptionWrapper e) {
			throw e;
		}
	}
	
	public static String getOrderCurrency(String sOrderHeaderKey, YIFApi localApi, YFSEnvironment context){
		if (!YFCCommon.isVoid(sOrderHeaderKey)){
			YFCDocument dOrder = YFCDocument.createDocument("Order");
			YFCElement eOrder = dOrder.getDocumentElement();
			eOrder.setAttribute("OrderHeaderKey", sOrderHeaderKey);
			YFCDocument dOrderTemplate = YFCDocument.createDocument("Order");
			dOrderTemplate.getDocumentElement().createChild("PriceInfo").setAttribute("Currency","");
			try {
				YFCElement eOutput = invokeApi(localApi, context,"getOrderDetails", dOrder.getDocument(), dOrderTemplate.getDocument());
				YFCElement ePriceInfo = eOutput.getChildElement("PriceInfo");
				return ePriceInfo.getAttribute("Currency");
			} catch (APIManager.XMLExceptionWrapper e){
				throw e;
			}
		}
		return null;
	}
	
	public static String getChargeCategoryDescription (YFSEnvironment context, YIFApi localApi, String sChargeCategory, String sEnterpriseCode, String sDocumentType){
		BDASimpleCache<String, String> map = getChargeNameMap();
		String codeKey = getCodeKey(sChargeCategory, sEnterpriseCode, sDocumentType);
		if (map != null && map.containsKey(codeKey)){
			return map.get(codeKey);
		}
		populateChargeName(map, context, localApi, sEnterpriseCode, sDocumentType, sChargeCategory);
		return map.get(codeKey);
	}	
	
	public static String getSupplyTypeDesc(YFSEnvironment context, YIFApi localApi, String sSupplyType){
		BDASimpleCache<String, String> map = getCodeShortDescriptionMap();
		if(map != null && map.containsKey("SupplyType_" + sSupplyType)){
			return map.get("SupplyType_" + sSupplyType);
		}
		populateSupplyTypes(map, context, localApi);
		return map.get("SupplyType_" + sSupplyType);
	}
	
	public static String getDemandTypeDesc(YFSEnvironment context, YIFApi localApi, String sDemandType){
		BDASimpleCache<String, String> map = getCodeShortDescriptionMap();
		if(map != null && map.containsKey("DemandType_" + sDemandType)){
			return map.get("DemandType_" + sDemandType);
		}
		populateDemandTypes(map, context, localApi);
		return map.get("DemandType_" + sDemandType);
	}
	
	private static void populateSupplyTypes(BDASimpleCache<String, String> codes, YFSEnvironment context, YIFApi localApi){
		YFCElement eOutput = invokeApi(localApi, context,"getInventorySupplyTypeList", YFCDocument.createDocument("InventorySupplyType").getDocument(), null);
		for (YFCElement eOrg : eOutput.getChildren()){
			codes.put("SupplyType_" + eOrg.getAttribute("SupplyType"), eOrg.getAttribute("Description"));
		}
	}
	
	private static void populateDemandTypes(BDASimpleCache<String, String> codes, YFSEnvironment context, YIFApi localApi){
		YFCElement eOutput = invokeApi(localApi, context,"getInventoryDemandTypeList", YFCDocument.createDocument("InventoryDemandType").getDocument(), null);
		for (YFCElement eOrg : eOutput.getChildren()){
			codes.put("DemandType_" + eOrg.getAttribute("DemandType"), eOrg.getAttribute("Description"));
		}
	}
	
	private static void populateChargeCategory(BDASimpleCache<String, String> charges, YFSEnvironment context, YIFApi localApi, String sEnterpriseCode, String sDocumentType, String sChargeCategory){
		YFCDocument dInput = YFCDocument.createDocument("ChargeName");
		YFCElement eInput = dInput.getDocumentElement();
		if (!YFCCommon.isVoid(sEnterpriseCode)){
			eInput.setAttribute("CallingOrganizationCode", sEnterpriseCode);
		}
		if (!YFCCommon.isVoid(sDocumentType)){
			eInput.setAttribute("DocumentType", sDocumentType);
		}
		if (!YFCCommon.isVoid(sDocumentType)){
			eInput.setAttribute("ChargeName", sChargeCategory);
		}
		
		YFCElement eOutput = invokeApi(localApi, context, "getChargeCategoryList", dInput.getDocument(), null);
		for (YFCElement eOrg : eOutput.getChildren()){
			charges.put(getCodeKey(eOrg.getAttribute("ChargeCategory"), sEnterpriseCode, sDocumentType), eOrg.getAttribute("Description"));
		}
	}	
	
	private static YFCElement invokeApi(YIFApi localApi, YFSEnvironment context, String sApiName, Document dInput, Document dTemplate){
		if (!YFCCommon.isVoid(dTemplate)){
			context.setApiTemplate(sApiName, dTemplate);	
		}
		
		Document output;
		try {
			output = localApi.invoke(context, sApiName, dInput);
			YFCDocument dOutput = YFCDocument.getDocumentFor(output);
			return dOutput.getDocumentElement();
		} catch (YFSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// ===================================================================================================================================================
	
}
