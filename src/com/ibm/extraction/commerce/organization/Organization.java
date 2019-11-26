package com.ibm.extraction.commerce.organization;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.xml.sax.SAXException;

import com.ibm.CallInteropServlet;
//import com.ibm.commerce.sterling.ue.CommerceIntegrationMap;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class Organization {

	private String sOrgName = "";
	private String sOrgCode = "";
	private String sCommerceCode = "";
	private String sCatalog = "";
	private String catalogOrg;
	private String inventoryOrg;
	private String capacityOrg;
	private String pricingOrg;
	private String customerOrg;
	private boolean isEnterpriseOrg = true;
	protected boolean isSellerOrg = true;
	protected boolean isNode = false;
	private boolean isBuyerOrg = false;
	private boolean isSourcingKept = true;
	private boolean isHubOrganization = false;
	private boolean isCarrier = false;
	private boolean isExisting = false;
	private PersonInfo contact = null;
	private PersonInfo corporate = null;
	private String sDefaultFulfillmentType = "PRODUCT_SOURCING";
	private String sDefaultPaymentRuleId = "_DEFAULT";
	private String sLocaleCode = "en_US_EST";
	private String sOrgSuffix = "";
	
	
	private YFCDocument getOrgTemplate(){
		YFCDocument out = YFCDocument.createDocument("Organization");
		try {
			return YFCDocument.parse(Organization.class.getResourceAsStream("getOrganizationHierarchyTemplate.xml"));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	public String toString(){
		return sOrgName + " (" + sOrgCode + ") - Cat: " + catalogOrg + " Inv: " + inventoryOrg;
	}
	
	public Organization(ResultSet rs, Map<String, Organization> organizations, String sServer) throws SQLException{
		sCommerceCode = rs.getString("STOREENT_ID");
		String sOMSOrgCode = "Aurora";// CommerceIntegrationMap.getOMSValue("storeIdToOrganizationCode", sCommerceCode);
		if (!YFCCommon.isVoid(sOMSOrgCode)){
			YFCDocument input = YFCDocument.createDocument("Organization");
			YFCElement eInput = input.getDocumentElement();
			eInput.setAttribute("OrganizationCode", sOMSOrgCode);
			YFCDocument dOrg = CallInteropServlet.invokeApi(input, getOrgTemplate(), "getOrganizationHierarchy", sServer);
			YFCElement eOrg = dOrg.getDocumentElement();
			sOrgName = eOrg.getAttribute("OrganizationName");
			sOrgCode = eOrg.getAttribute("OrganizationCode");
			sLocaleCode = eOrg.getAttribute("Localecode");
			sDefaultPaymentRuleId = eOrg.getAttribute("DefaultPaymentRuleId");
			sOrgSuffix = eOrg.getAttribute("OrganizationSuffix");
			sDefaultFulfillmentType = eOrg.getAttribute("DefaultFulfillmentType");
			isExisting = true;
			isEnterpriseOrg = eOrg.getBooleanAttribute("IsEnterprise", true);
			isSellerOrg = eOrg.getBooleanAttribute("IsSeller", true);
			isNode = eOrg.getBooleanAttribute("IsNode", false);
			isBuyerOrg = eOrg.getBooleanAttribute("IsBuyer", false);
			isSourcingKept = eOrg.getBooleanAttribute("IsSourcingKept", true);
			this.catalogOrg = eOrg.getAttribute("CatalogOrganizationCode");
			this.customerOrg = eOrg.getAttribute("CustomerMasterOrganizationCode");
			this.inventoryOrg = eOrg.getAttribute("InventoryOrganizationCode");
			this.pricingOrg = eOrg.getAttribute("PricingOrganizationCode");
			capacityOrg = eOrg.getAttribute("CapacityOrganizationCode");
			
		} else {
			sOrgName = rs.getString("IDENTIFIER");
			sOrgCode = rs.getString("IDENTIFIER");
			sCatalog = rs.getString("CATALOG_ID");
			contact = new PersonInfo(rs.getString("CONT_ADDRESS1"), rs.getString("CONT_ADDRESS2"),
					rs.getString("CONT_CITY"),rs.getString("CONT_STATE"),rs.getString("CONT_COUNTRY"),
					rs.getString("CONT_ZIPCODE"),rs.getString("CONT_PHONE"),rs.getString("CONT_FAX"),
					rs.getString("CONT_EMAIL"),rs.getString("CONT_FIRSTNAME"),rs.getString("CONT_LASTNAME"));
			corporate = new PersonInfo(rs.getString("LOC_ADDRESS1"), rs.getString("LOC_ADDRESS2"),
					rs.getString("LOC_CITY"),rs.getString("LOC_STATE"),rs.getString("LOC_COUNTRY"),
					rs.getString("LOC_ZIPCODE"),rs.getString("LOC_PHONE"),rs.getString("LOC_FAX"),
					rs.getString("LOC_EMAIL"),rs.getString("LOC_FIRSTNAME"),rs.getString("LOC_LASTNAME"));
			for(int i = 0; i < sOrgName.length(); i++){
				if (Character.isUpperCase(sOrgName.charAt(i))){
					this.sOrgSuffix += sOrgName.charAt(i);
				}
			}
			if (this.sOrgSuffix.length() < 2){
				this.sOrgSuffix = sOrgName.substring(0, 4).toUpperCase();
			}
			if (rs.getString("CATALOG_STOREENT_ID").equals(sCommerceCode)){
				catalogOrg = sOrgCode;
			/*} else if (!YFCCommon.isVoid(CommerceIntegrationMap.getOMSValue("storeIdToCatalogOrganizationCode", sCommerceCode))){
				catalogOrg = CommerceIntegrationMap.getOMSValue("storeIdToCatalogOrganizationCode", sCommerceCode);
			} else if (!YFCCommon.isVoid(organizations.get(rs.getString("CATALOG_STOREENT_ID")))){
				catalogOrg = organizations.get(rs.getString("CATALOG_STOREENT_ID")).getOrgCode();*/
			}
			/*if (!YFCCommon.isVoid(CommerceIntegrationMap.getOMSValue("storeIdToInventoryOrganizationCode", sCommerceCode))){
				inventoryOrg = CommerceIntegrationMap.getOMSValue("storeIdToInventoryOrganizationCode", sCommerceCode);
			} else */
			if (!catalogOrg.equals(sOrgCode)) {
				for (Organization org : organizations.values()){
					if (org.getOrgCode().equals(catalogOrg)){
						inventoryOrg = org.getInventoryOrg();
					} 
				}
			} else {
				inventoryOrg = sOrgCode;
			}
			capacityOrg = inventoryOrg;
			customerOrg = sOrgCode;
			pricingOrg = sOrgCode;
		}
	}
	
	public String getOrgName(){
		return sOrgName;
	}
	public void setOrgName(String sOrgName){
		this.sOrgCode = sOrgName;
	}
	public String getCommerceID(){
		return sCommerceCode;
	}
	public void setCommerceID(String sCommerceID){
		this.sCommerceCode = sCommerceID;
	}
	public String getDefaultPaymentRuleId(){
		return getOrgCode()  + this.sDefaultPaymentRuleId;
	}
	public String getOrgCode(){
		return sOrgCode;
	}
	public String getOrgKey(){
		return sOrgCode;
	}
	public String getOrgSuffix(){
		return sOrgSuffix;
	}
	public String getCatalogOrg(){
		return catalogOrg;
	}
	public String getInventoryOrg(){
		return inventoryOrg;
	}
	public String getCapacityOrg(){
		return capacityOrg;
	}
	public String getCustomerMasterOrg(){
		return customerOrg;
	}
	
	public String getPricingOrg(){
		return pricingOrg;
	}
	

	public YFCDocument getOrganizationHierarchy(){
		YFCDocument dOrg = YFCDocument.createDocument("Organization");
		YFCElement eOrg = dOrg.getDocumentElement();
		eOrg.setAttribute("OrganizationCode", getOrgCode());
		eOrg.setAttribute("OrganzationKey", getOrgKey());
		eOrg.setAttribute("CapacityOrganizationCode", getCapacityOrg());
		eOrg.setAttribute("CatalogOrganizationCode", getCatalogOrg());
		eOrg.setAttribute("CustomerMasterOrganizationCode", getCustomerMasterOrg());
		eOrg.setAttribute("CustomerMaintaindExterally", "N");
		eOrg.setAttribute("DefaultFulfillmentType", sDefaultFulfillmentType);
		eOrg.setAttribute("DefaultPaymentRuleId", getDefaultPaymentRuleId());
		eOrg.setAttribute("InventoryKeptExternally", "N");
		eOrg.setAttribute("InventoryOrganizationCode", getInventoryOrg());
		eOrg.setAttribute("IsSourcingKept", isSourcingKept);
		eOrg.setAttribute("LocaleCode", this.sLocaleCode);
		eOrg.setAttribute("OrganizationName", getOrgName());
		eOrg.setAttribute("OrganizationSuffix", getOrgSuffix());
		eOrg.setAttribute("PricingOrganizationCode", getPricingOrg());
		eOrg.setAttribute("XrefOrganizationCode", this.getCommerceID());
		if (!YFCCommon.isVoid(contact)){
			contact.createPersonInfo("ContactPersonInfo", eOrg);
		}
		if (!YFCCommon.isVoid(corporate)){
			corporate.createPersonInfo("CorporatePersonInfo", eOrg);
		}
		
		try {
			YFCDocument dPaymentTypes = YFCDocument.parse(Organization.class.getResourceAsStream("OrganizationPaymentTypes.xml"));
			if (!YFCCommon.isVoid(dPaymentTypes)){
				eOrg.importNode(dPaymentTypes.getDocumentElement());
			}
		} catch (SAXException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dOrg;
	}
}
