package com.mirakl.entity;

import com.ibm.CallInteropServlet;
import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class MiraklOnboard {
	
	private static MiraklOnboard onboard;
	public static MiraklOnboard getInstance(){
		if(YFCCommon.isVoid(onboard)){
			onboard = new MiraklOnboard();
		}
		return onboard;
	}

	private boolean exists = false;
	private String sOrgName;
	private String sOrgCode;
	
	public String getOrgCode() {
		return sOrgCode;
	}
	public String getOrgName() {
		return sOrgName;
	}
	
	public MiraklOnboard(){
		this.sOrgCode = "Mirakl";
		this.sOrgName = "Mirakl";
		this.exists = false;
	}
	public boolean shopExists() {
		if(!exists){
			exists = MiraklUtils.organizationExists(sOrgCode);
		}
		return exists;
	}
	
	private void createAddress(String sNodeName, YFCElement eParent){
		YFCElement eAddress = eParent.createChild(sNodeName);
		eAddress.setAttribute("AddressLine1", "100 Dover St");
		eAddress.setAttribute("City", "Somerville");
		eAddress.setAttribute("State", "MA");
		eAddress.setAttribute("Country", "US");
		eAddress.setAttribute("ZipCode", "02144");
	}
	
	public void createEnterprise(){
		if(!shopExists()){
			YFCDocument dOrg = YFCDocument.createDocument("Organization");
			YFCElement eOrg = dOrg.getDocumentElement();
			eOrg.setAttribute("OrganizationCode", sOrgCode);
			eOrg.setAttribute("OrganizationName", sOrgName);
			eOrg.setAttribute("CatalogOrganizationCode", sOrgCode);
			eOrg.setAttribute("InventoryOrganizationCode", sOrgCode);
			eOrg.setAttribute("CapacityOrganizationCode", sOrgCode);
			eOrg.setAttribute("PricingOrganizationCode", sOrgCode);
			eOrg.setAttribute("CustomerMasterOrganizationCode", sOrgCode);
			eOrg.setAttribute("InventoryPublished", "Y");
			eOrg.setAttribute("IsLegalEntity", "Y");
			eOrg.setAttribute("IsEnterprise", "Y");
			eOrg.setAttribute("IsHubOrganization", "N");
			eOrg.setAttribute("IsSeller", "Y");
			eOrg.setAttribute("LocaleCode", "en_US_EST");
			eOrg.setAttribute("IsSourcingKept", "Y");
			eOrg.setAttribute("PrimaryEnterpriseKey", sOrgCode);
			
			createAddress("CorporatePersonInfo", eOrg);
			createAddress("ContactPersonInfo", eOrg);
			createAddress("BillingPersonInfo", eOrg);
			
			YFCDocument dResponse = CallInteropServlet.invokeApi(dOrg, null, "manageOrganizationHierarchy", "http://oms.omfulfillment.com:9080");			
		}
	}
}
