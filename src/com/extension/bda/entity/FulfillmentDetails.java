package com.extension.bda.entity;

import java.util.HashMap;
import java.util.Map;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class FulfillmentDetails extends BDAEntityObject {

	private FulfillmentPackage parent;
	private int seqNo = -1;
		
	public FulfillmentDetails(YFSEnvironment env, int key){
		loadRecordForKey(env, key);
	}
	
	public FulfillmentDetails(YFCElement eFulfillmentDetails){
		this.loadEntityData(eFulfillmentDetails);
	}
	
	public FulfillmentDetails(YFCElement eSourcingRuleDetail, int SequenceNo, int FulfillmentPackageKey){
		seqNo = SequenceNo;
		YFCElement eFulfillmentDetails = YFCDocument.createDocument(getEntity().getXmlName()).getDocumentElement();
		loadAttribute(eFulfillmentDetails, eSourcingRuleDetail, "Description");
		loadAttribute(eFulfillmentDetails, eSourcingRuleDetail, "FulfillmentDetailsKey");
		loadAttribute(eFulfillmentDetails, eSourcingRuleDetail, "ItemType");
		loadAttribute(eFulfillmentDetails, eSourcingRuleDetail, "Level");
		if(FulfillmentPackageKey > 0){
			eFulfillmentDetails.setAttribute("FulfillmentPackageKey", FulfillmentPackageKey);
		}
		this.loadEntityData(eFulfillmentDetails);
	}
	
	private void loadAttribute(YFCElement eFulfillmentDetails, YFCElement eSourcingRuleDetails, String sAttribute){
		if(!YFCCommon.isVoid(eSourcingRuleDetails.getAttribute(sAttribute))){
			eFulfillmentDetails.setAttribute(sAttribute, eSourcingRuleDetails.getAttribute(sAttribute));
			eSourcingRuleDetails.removeAttribute(sAttribute);
		}
	}
	
	public FulfillmentDetails(YFSEnvironment env, String sSourcingRuleDetailKey){
		Map<String, String> nameValuePairs = new HashMap<String, String>();
		nameValuePairs.put("SOURCING_RULE_DETAIL_KEY", sSourcingRuleDetailKey);
		loadOneRecord(env, nameValuePairs);
	}
	
	public FulfillmentPackage getFulfillmentPackage(YFSEnvironment env){
		if (parent == null){
			parent = new FulfillmentPackage(env, eEntityData.getIntAttribute("FulfillmentPackageKey"));
		}
		return parent;
	}	
	
	public int getSequenceNo(){
		return seqNo;
	}
	
	public YFCElement save(YFSEnvironment env){
		if(isDirty()){
			YFCElement eResponse = saveRecord(env, eEntityData);
			if(!YFCCommon.isVoid(eResponse)){
				setClean();
			} else {
				YFCElement eDetails = (YFCElement)getXml().cloneNode(true);
				Map <String, String> m = new HashMap<String, String>();
				m.put("SOURCING_RULE_DETAIL_KEY", eEntityData.getAttribute("SourcingRuleDetailKey"));
				m.put("FULFILLMENT_PACKAGE_KEY", eEntityData.getAttribute("FulfillmentPackageKey"));
				loadOneRecord(env, m);
				eDetails.setAttribute("FulfillmentDetailKey", getPrimaryKey());
				loadEntityData(eDetails);
				eResponse = saveRecord(env, eDetails);
			}
			return eResponse;
		}
		return eEntityData;
	}
	public void updateSourcingRuleDetailKey(String sKey){
		setDirty();
		getXml().setAttribute("SourcingRuleDetailKey", sKey);
	}
	
	public void updateFulfillmentPackageKey(int iKey){
		setDirty();
		getXml().setAttribute("FulfillmentPackageKey", iKey);
	}
	

	@Override
	protected String getEntityType() {
		return "BDA_FULFILLMENT_DETAIL";
	}

}
