package com.mirakl.entity;

import org.json.JSONObject;
import org.w3c.dom.Document;

import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class MiraklShop {
	private YFCDocument dShop = null;
	private YFCElement eShop = null;
	private boolean exists = false;
	private boolean distributionExists = false;

	public MiraklShop(String sNodeType, String sCatalogOrg, String sInventoryOrg, String sDistributionRuleID){
		dShop = YFCDocument.createDocument("Shop");
		eShop = dShop.getDocumentElement();
		eShop.setAttribute("node-type", sNodeType);
		eShop.setAttribute("CatalogOrganizationCode", sCatalogOrg);
		eShop.setAttribute("InventoryOrganizationCode", sInventoryOrg);
		eShop.setAttribute("DistributionRuleID", sDistributionRuleID);
	}
	
	public MiraklShop(YFCDocument dShop) {
		if(!YFCCommon.isVoid(dShop)){
			this.dShop = dShop;
			this.eShop = dShop.getDocumentElement();
		}		
	}
	
	public MiraklShop(YFCElement eShop) {
		if(!YFCCommon.isVoid(eShop)){
			dShop = YFCDocument.getDocumentFor(eShop.toString());
			this.eShop = dShop.getDocumentElement();
		}		
	}
	
	public String getShopID() {
		return "MRK_SHOP_" + eShop.getAttribute("shop-id");
	}
	
	public String getShopName() {
		return eShop.getAttribute("shop-name");
	}
	
	public MiraklShop(JSONObject shop, String sNodeType, String sCatalogOrg, String sInventoryOrg, String sDistributionRuleID){
		this(sNodeType, sCatalogOrg, sInventoryOrg, sDistributionRuleID);
		eShop.setAttribute("shop-id", shop.getInt("shop_id"));
		eShop.setAttribute("shop-name", shop.getString("shop_name"));
		eShop.setAttribute("shop-state", shop.getString("shop_state"));
		YFCElement eBillTo = eShop.createChild("Address");
		try {
			JSONObject contact = shop.getJSONObject("contact_informations");
			setAttribute(eBillTo, "City", contact, "city");
			setAttribute(eBillTo, "Country", contact, "country");
			setAttribute(eBillTo, "State", contact, "state");
			setAttribute(eBillTo, "AddressLine1", contact, "street1");
			setAttribute(eBillTo, "AddressLine2", contact, "street2");
			setAttribute(eBillTo, "ZipCode", contact, "zip_code");
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Unable to retrieve Address for " + eShop.getAttribute("shop-id"));
		}
	}
	
	private void setAttribute(YFCElement eElement, String sAttribute, JSONObject obj, String sProperty){
		if(!YFCCommon.isVoid(eElement) && !YFCCommon.isVoid(obj) && obj.has(sProperty) && obj.get(sProperty) instanceof String && !YFCCommon.isVoid(obj.get(sProperty).toString())){
			eElement.setAttribute(sAttribute, obj.getString(sProperty));
		}
	}
	
	public Document getShopXML() {
		return dShop.getDocument();
	}
	public void createShop(YFSEnvironment env){
		if(!shopExists(env)){
			MiraklUtils.callApi(env, getCreateShipNodeXML(), null, "manageOrganizationHierarchy");
		}
		addDistrubtionRuleXML(env);
	}
	
	private void createAddress(String sNodeName, YFCElement eParent){
		if(!YFCCommon.isVoid(eShop.getChildElement("Address"))){
			YFCElement eAddress = eParent.createChild(sNodeName);
			eAddress.setAttributes(eShop.getChildElement("Address").getAttributes());	
		}
	}
	
	public boolean shopExists(YFSEnvironment env) {
		MiraklOnboard.getInstance().createEnterprise(env, eShop.getAttribute("CatalogOrganizationCode"), eShop.getAttribute("InventoryOrganizationCode"));
		if(!exists){
			exists = MiraklUtils.organizationExists(getShopID(), env);
		}
		return exists;
	}
	
	public boolean distributionExists(YFSEnvironment env){
		if(!distributionExists){
			distributionExists = MiraklUtils.distributionExists(eShop.getAttribute("DistributionRuleID"), eShop.getAttribute("InventoryOrganizationCode"), getShopID(), env);
		}
		return distributionExists;
	}
	
	public YFCDocument getObjectXML() {
		return this.dShop;
	}
	
	public void addDistrubtionRuleXML(YFSEnvironment env){
		if(!distributionExists(env)){
			YFCDocument dInput = YFCDocument.createDocument("ItemShipNode");
			YFCElement eItemShipNode = dInput.getDocumentElement();
			eItemShipNode.setAttribute("DistributionRuleId", eShop.getAttribute("DistributionRuleID"));
			eItemShipNode.setAttribute("ItemId", "ALL");
			eItemShipNode.setAttribute("ItemType", "ALL");
			eItemShipNode.setAttribute("ActiveFlag", true);
			eItemShipNode.setAttribute("Priority", 10d);
			eItemShipNode.setAttribute("OwnerKey", eShop.getAttribute("InventoryOrganizationCode"));
			eItemShipNode.setAttribute("ShipnodeKey", getShopID());
			MiraklUtils.callApi(env, dInput, null, "createDistribution");
		}		
	}
	
	public YFCDocument getCreateShipNodeXML(){
		YFCDocument dOrganization = YFCDocument.createDocument("Organization");
		YFCElement eOrganization = dOrganization.getDocumentElement();
		eOrganization.setAttribute("OrganizationCode", getShopID());
		eOrganization.setAttribute("OrganizationName", getShopName());
		eOrganization.setAttribute("PrimaryEnterpriseKey", MiraklOnboard.getInstance().getOrgCode());
		eOrganization.setAttribute("CatalogOrganizationCode", eShop.getAttribute("CatalogOrganizationCode"));
		eOrganization.setAttribute("InventoryOrganizationCode", eShop.getAttribute("InventoryOrganizationCode"));
		eOrganization.setAttribute("CapacityOrganizationCode", MiraklOnboard.getInstance().getOrgCode());
		eOrganization.setAttribute("PricingOrganizationCode", MiraklOnboard.getInstance().getOrgCode());
		eOrganization.setAttribute("CustomerMasterOrganizationCode", MiraklOnboard.getInstance().getOrgCode());
		eOrganization.setAttribute("LocaleCode", "en_US_EST");
		eOrganization.setAttribute("IsNode", "Y");
		eOrganization.setAttribute("ParentOrganizationCode", MiraklOnboard.getInstance().getOrgCode());
		YFCElement eNode = eOrganization.createChild("Node");
		eNode.setAttribute("AcceptanceRequired", "N");
		eNode.setAttribute("AdvanceNotificationTime", "0");
		eNode.setAttribute("CanShipToDC", "Y");
		eNode.setAttribute("CanShipToOtherAddresses", "Y");
		eNode.setAttribute("CanShipToStore", "Y");
		eNode.setAttribute("Description", eShop.getAttribute("shop-name"));
		eNode.setAttribute("IdentifiedByParentAs", getShopID());
		eNode.setAttribute("InterfaceType", "YFX");
		eNode.setAttribute("Inventorytype", "TRAK");
		eNode.setAttribute("Localecode", "en_US_EST");
		eNode.setAttribute("MaintainInventoryCost", "N");
		eNode.setAttribute("NodeOrgCode", getShopID());
		eNode.setAttribute("NodeType", eShop.getAttribute("node-type"));
		eNode.setAttribute("ProcureToShopAllowed", "Y");
		eNode.setAttribute("ReceiptProcessingTime", "3");
		eNode.setAttribute("ReceivingNode", "N");
		eNode.setAttribute("RequireTransferAcceptance", "N");
		eNode.setAttribute("ReturnCenterFlag", "N");
		eNode.setAttribute("ReturnsNode", "N");
		eNode.setAttribute("SerialTracking", "Y");
		eNode.setAttribute("ShipNode", getShopID());
		eNode.setAttribute("ShipnodeKey", getShopID());
		eNode.setAttribute("ShippingNode", "Y");
		eNode.setAttribute("SubstitutionAllowed", "Y");
		eNode.setAttribute("ThreePlNode", "N");
		eNode.setAttribute("TimeDiff", "0");
		eNode.setAttribute("WorkOrderCreationAllowed", "Y");
		createAddress("ShipNodePersonInfo", eNode);
		createAddress("ContactPersonInfo", eNode);
		createAddress("CorporatePersonInfo", eOrganization);
		createAddress("ContactPersonInfo", eOrganization);
		createAddress("BillingPersonInfo", eOrganization);
		return dOrganization;
	}
}
