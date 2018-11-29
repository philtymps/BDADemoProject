package com.extension.bda.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class FulfillmentPackage extends BDAEntityObject {
	
	private List<FulfillmentDetails> fulfillmentDetails;
	
	public FulfillmentPackage(){
		fulfillmentDetails = new ArrayList<FulfillmentDetails>();
	}
	public FulfillmentPackage (YFSEnvironment env, int sFulfillmentPackageKey){
		this();
		if(!YFCCommon.isVoid(sFulfillmentPackageKey) && sFulfillmentPackageKey > 0){
			loadRecordForKey(env, sFulfillmentPackageKey);
		}
	}
	
	public FulfillmentPackage (YFSEnvironment env, String sOrganizationCode, String sFulfillmentType){
		this();
		if(!YFCCommon.isVoid(sOrganizationCode) && !YFCCommon.isVoid(sFulfillmentType)){
			Map <String, String> m = new HashMap<String, String>();
			m.put("FULFILLMENT_TYPE", sFulfillmentType);
			m.put("ORGANIZATION_CODE", sOrganizationCode);
			loadOneRecord(env, m);
		}
	}
	
	public FulfillmentPackage (YFCElement eFulfillmentPackage){
		this();
		loadEntityData(eFulfillmentPackage);
	}
	
	public FulfillmentPackage (String sOrganizationCode, String sFulfillmentType, String sPackageName, String sAllocationRuleKey, String sSourcingConfigKey){
		this();
		YFCDocument dFP = YFCDocument.createDocument("FulfillmentPackage");
		YFCElement eFP = dFP.getDocumentElement();
		eFP.setAttribute("OrganizationCode", sOrganizationCode);
		eFP.setAttribute("FulfillmentType", sFulfillmentType);
		eFP.setAttribute("PackageName", sPackageName);
		eFP.setAttribute("AllocationRuleKey", sAllocationRuleKey);
		eFP.setAttribute("SourcingConfigKey", sSourcingConfigKey);
		loadEntityData(eFP);
	}
	
	protected String getEntityType() {
		return "BDA_FULFILLMENT_PACKAGE";
	}

	protected static Document getSourcingRuleHeaderTemplate(){
		try{
			YFCDocument dDoc = YFCDocument.parse(FulfillmentPackage.class.getResourceAsStream("getSourcingRuleListTemplate.xml"));
			return dDoc.getDocument();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public YFCElement save(YFSEnvironment env){
		if(isDirty()){
			YFCElement eResponse = saveRecord(env, eEntityData);
			if(!YFCCommon.isVoid(eResponse)){
				setClean();
			} else {
				Map <String, String> m = new HashMap<String, String>();
				m.put("FULFILLMENT_TYPE", eEntityData.getAttribute("FulfillmentType"));
				m.put("ORGANIZATION_CODE", eEntityData.getAttribute("OrganizationCode"));
				loadOneRecord(env, m);
			}
			return eResponse;
		}
		return eEntityData;
	}
	
	private void createFlagsForSourcingRuleHeader(YFSEnvironment env, YFCElement eSourcingRuleHeader){
		YFCElement eSourcingRuleHeaderConfig = eSourcingRuleHeader.getChildElement("SourcingRuleHeaderConfig", true);
		eSourcingRuleHeaderConfig.setAttribute("DefineSellerOrg", YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("SellerOrganizationCode")));
		eSourcingRuleHeaderConfig.setAttribute("DefineSourcingClass", YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("OrderSourcingClassification")));
		if(!YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("RegionKey"))){
			eSourcingRuleHeaderConfig.setAttribute("DestinationLimit", "Region");
		} else if (!YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("ToNodeKey"))){
			eSourcingRuleHeaderConfig.setAttribute("DestinationLimit", "Node");
		} else if(!YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("NodeTypeID"))){
			eSourcingRuleHeaderConfig.setAttribute("DestinationLimit", "NodeType");
		} else {
			eSourcingRuleHeaderConfig.setAttribute("DestinationLimit", "N");
		}
		eSourcingRuleHeaderConfig.setAttribute("ProductClassification", !YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("ItemID")) || !YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("ItemClassification")) || !YFCCommon.isVoid(eSourcingRuleHeader.getAttribute("ItemClassification2")));
		YFCElement eSRDList = eSourcingRuleHeader.getChildElement("SourcingRuleDetails", true);
		for (YFCElement eSRD : eSRDList.getChildren()){
			FulfillmentDetails fd = new FulfillmentDetails(env, eSRD.getAttribute("SourcingRuleDetailKey"));
			if(!YFCCommon.isVoid(fd.getPrimaryKey())){
				eSRD.setAttributes(fd.getXml().getAttributes());
				fulfillmentDetails.add(fd);
			}			
		}
	}
	
	public void loadSourcingRules(YFSEnvironment env){
		YFCDocument dSRH = YFCDocument.createDocument("SourcingRuleHeader");
		YFCElement eSRH = dSRH.getDocumentElement();
		eSRH.setAttribute("FulfillmentType", super.getXml().getAttribute("FulfillmentType"));
		eSRH.setAttribute("OrganizationCode", super.getXml().getAttribute("OrganizationCode"));
		loadRemoteData(env, dSRH.getDocument(), getSourcingRuleHeaderTemplate(), "getSourcingRuleList", false);
		YFCElement eSourcingRuleHeaders = super.getXml().getChildElement("SourcingRuleHeaders");
		for(YFCElement eHeader : eSourcingRuleHeaders.getChildren()){
			createFlagsForSourcingRuleHeader(env, eHeader);
		}
	}
	
	public void loadAllocationRule(YFSEnvironment env){
		if(!YFCCommon.isVoid(super.getXml().getAttribute("AllocationRuleKey"))){
			YFCDocument dAR = YFCDocument.createDocument("AllocationRule");
			YFCElement eAR = dAR.getDocumentElement();
			eAR.setAttribute("AllocationRuleKey", super.getXml().getAttribute("AllocationRuleKey"));
			loadRemoteData(env, dAR.getDocument(), getSourcingRuleHeaderTemplate(), "getAllocationRuleList", true);
		}
	}
	
	public YFCElement getXml(){
		YFCElement eXml = super.getXml();
		
		return eXml;
	}

}
