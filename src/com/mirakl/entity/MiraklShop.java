package com.mirakl.entity;

import java.util.ArrayList;

import org.json.JSONObject;
import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class MiraklShop {
	private YFCDocument dShop = null;
	private YFCElement eShop = null;
	private boolean exists = false;
	
	public MiraklShop(){
		dShop = YFCDocument.createDocument("Shop");
		eShop = dShop.getDocumentElement();
	}
	
	public MiraklShop(YFCDocument dShop) {
		if(!YFCCommon.isVoid(dShop)){
			this.dShop = dShop;
			this.eShop = dShop.getDocumentElement();
		}		
	}
	
	public MiraklShop(YFCElement eShop) {
		if(!YFCCommon.isVoid(eShop)){
			this.dShop = eShop.cloneNode(true).getOwnerDocument();
			this.eShop = dShop.getDocumentElement();
		}		
	}
	
	public String getShopID() {
		return eShop.getAttribute("shop-id");
	}
	
	public String getShopName() {
		return eShop.getAttribute("shop-name");
	}
	
	public MiraklShop(JSONObject shop){
		this();
		eShop.setAttribute("shop-id", shop.getInt("shop_id"));
		eShop.setAttribute("shop-name", shop.getString("shop_name"));
		eShop.setAttribute("shop-state", shop.getString("shop_state"));
		YFCElement eBillTo = eShop.createChild("Address");
		try {
			JSONObject contact = shop.getJSONObject("contact_informations");
			eBillTo.setAttribute("City", contact.getString("city"));
			eBillTo.setAttribute("Country", contact.getString("country"));
			eBillTo.setAttribute("State", contact.getString("state"));
			eBillTo.setAttribute("AddressLine1", contact.getString("street1"));
			eBillTo.setAttribute("AddressLine2", contact.getString("street2"));
			eBillTo.setAttribute("ZipCode", contact.getString("zip_code"));
		} catch (Exception e){
			System.out.println("Unable to retrieve Address for " + eShop.getAttribute("shop-id"));
		}
	
	}
	
	public Document getShopXML() {
		return dShop.getDocument();
	}
	public void createShop(){
		if(!shopExists()){
			CallInteropServlet.invokeApi(getCreateShipNodeXML(), null, "manageOrganizationHierarchy", "http://oms.omfulfillment.com:9080");		
		}
	}
	
	private void createAddress(String sNodeName, YFCElement eParent){
		if(!YFCCommon.isVoid(eShop.getChildElement("Address"))){
			YFCElement eAddress = eParent.createChild(sNodeName);
			eAddress.setAttributes(eShop.getChildElement("Address").getAttributes());	
		}
	}
	
	public boolean shopExists() {
		MiraklOnboard.getInstance().createEnterprise();
		if(!exists){
			exists = MiraklUtils.organizationExists(eShop.getAttribute("shop-id"));
		}
		return exists;
	}
	
	public YFCDocument getCreateShipNodeXML(){
		YFCDocument dOrganization = YFCDocument.createDocument("Organization");
		YFCElement eOrganization = dOrganization.getDocumentElement();
		eOrganization.setAttribute("OrganizationCode", eShop.getAttribute("shop-id"));
		eOrganization.setAttribute("OrganizationName", eShop.getAttribute("shop-name"));
		eOrganization.setAttribute("PrimaryEnterpriseKey", MiraklOnboard.getInstance().getOrgCode());
		eOrganization.setAttribute("CatalogOrganizationCode", MiraklOnboard.getInstance().getOrgCode());
		eOrganization.setAttribute("InventoryOrganizationCode", MiraklOnboard.getInstance().getOrgCode());
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
		eNode.setAttribute("IdentifiedByParentAs", eShop.getAttribute("shop-id"));
		eNode.setAttribute("InterfaceType", "YFX");
		eNode.setAttribute("Inventorytype", "TRAK");
		eNode.setAttribute("Localecode", "en_US_EST");
		eNode.setAttribute("MaintainInventoryCost", "N");
		eNode.setAttribute("NodeOrgCode", eShop.getAttribute("shop-id"));
		eNode.setAttribute("NodeType", "DC");
		eNode.setAttribute("ProcureToShopAllowed", "Y");
		eNode.setAttribute("ReceiptProcessingTime", "3");
		eNode.setAttribute("ReceivingNode", "N");
		eNode.setAttribute("RequireTransferAcceptance", "N");
		eNode.setAttribute("ReturnCenterFlag", "N");
		eNode.setAttribute("ReturnsNode", "N");
		eNode.setAttribute("SerialTracking", "Y");
		eNode.setAttribute("ShipNode", eShop.getAttribute("shop-id"));
		eNode.setAttribute("ShipnodeKey", eShop.getAttribute("shop-id"));
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
