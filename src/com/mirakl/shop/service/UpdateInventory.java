package com.mirakl.shop.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.mirakl.entity.MiraklTranslation;
import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class UpdateInventory implements IBDAService {

	Properties p = null;
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateInventory(){
		p = new Properties();
	}
	public String getApiKey() {
		if(p.containsKey("API-KEY")){
			return p.getProperty("API-KEY");
		}
		System.out.println("Set API-KEY in your UpdateInventory");
		return "44cfe66f-6ef9-4091-b76b-c777a94b64a1";
	}
	
	public String getDomain() {
		if(p != null && p.contains("DOMAIN")){
			return p.getProperty("DOMAIN");
		}
		System.out.println("Set DOMAIN in your Update Inventory Service");
		return "ibm-dev.mirakl.net";
	}
	
	public String getSellerOrg(){
		if(p != null && p.contains("SELLER-ORG-CODE")){
			return p.getProperty("SELLER-ORG-CODE");
		}
		System.out.println("Set SELLER-ORG-CODE in your Update Inventory Service");
		return "SS_USA";
	}
	
	protected String getURL(String sRestPath){
		if(!sRestPath.startsWith("/")){
			sRestPath = "/" + sRestPath;
		}
		if(getDomain().startsWith("http")){
			return getDomain() + sRestPath;
		}
		return "https://" + getDomain() + sRestPath;
	}
	
	@Override
	public void setProperties(Properties props) {
		p = props;
	}

	private String getDefaultDistribution(){
		if(p.containsKey("DefaultDistributionId")){
			return p.getProperty("DefaultDistributionId");
		}
		return "SS_USA_DC";
	}
	
	protected String getMiraklTranslationDoc(YFSEnvironment env){
		if(p != null && p.contains("TRANSFORM-DOC")){
			return p.getProperty("TRANSFORM-DOC");
		}
		return BDAServiceApi.getScriptsPath(env) + "/MiraklTranslate.xml";
	}
	
	protected String getMiraklShopDirectory(YFSEnvironment env){
		if(p != null && p.containsKey("DIRECTORY")){
			return p.getProperty("DIRECTORY");
		}
		return BDAServiceApi.getScriptsPath(env) + "/MiraklShopData";
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dInventoryItem = YFCDocument.getDocumentFor(input);
		YFCElement eInventoryItem = dInventoryItem.getDocumentElement();
		
		for(YFCElement eAvailabilityChange : eInventoryItem.getChildElement("AvailabilityChanges", true).getChildren()){
			
			if(!YFCCommon.isVoid(eAvailabilityChange.getAttribute("DistributionRuleId")) && YFCCommon.equals(getDefaultDistribution(), eAvailabilityChange.getAttribute("DistributionRuleId"))){
				YFCDocument dOffer = YFCDocument.createDocument("offer");
				YFCElement eOffer = dOffer.getDocumentElement();
				eOffer.createChild("available_ended").setNodeValue("null");
				eOffer.createChild("available_started").setNodeValue("null");
				eOffer.createChild("description").setNodeValue("null");
				eOffer.createChild("internal_description").setNodeValue("null");
				eOffer.createChild("shop_sku").setNodeValue(eInventoryItem.getAttribute("ItemID"));
				eOffer.createChild("update_delete").setNodeValue("update");
				String sMiraklValue = MiraklTranslation.getInstance(getMiraklTranslationDoc(env), false).getMiraklValue("state_code", eInventoryItem.getAttribute("ProductClass", "Good"));
				if(YFCCommon.isVoid(sMiraklValue)){
					sMiraklValue = "11";
				}
				eOffer.createChild("state_code").setNodeValue(sMiraklValue);
				eOffer.createChild("quantity").setNodeValue(eAvailabilityChange.getIntAttribute("OnhandAvailableQuantity", 0));
				eOffer.createChild("price").setNodeValue(getItemPrice(env, getSellerOrg(), eInventoryItem.getAttribute("ItemID"), eInventoryItem.getAttribute("UnitOfMeasure")));
				MiraklUtils.writeXML(getMiraklShopDirectory(env), eInventoryItem.getAttribute("ItemID") + "_update.xml", dOffer);
				return dOffer.getDocument();
			}
			
		}
		
		return input;
	}
	
	
	private double getItemPrice(YFSEnvironment env, String sOrganizationCode, String sItemID, String sUnitOfMeasure){
		YFCDocument dResponse = callItemPrice(env, sOrganizationCode, sItemID, sUnitOfMeasure);
		YFCElement eItemPrice = dResponse.getDocumentElement();
		return eItemPrice.getChildElement("LineItems", true).getChildElement("LineItem", true).getDoubleAttribute("UnitPrice", 0);
	}
	
	private YFCDocument callItemPrice(YFSEnvironment env, String sOrganizationCode, String sItemID, String sUnitOfMeasure){
		YFCDocument dInput = YFCDocument.createDocument("ItemPrice");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("Currency", "USD");
		eInput.setAttribute("OrganizationCode", sOrganizationCode);
		YFCElement eLineItem = eInput.createChild("LineItems").createChild("LineItem");
		eLineItem.setAttribute("ItemID", sItemID);
		eLineItem.setAttribute("UnitOfMeasure", sUnitOfMeasure);
		eLineItem.setAttribute("Quantity", 1);
		
		return MiraklUtils.callApi(env, dInput, null, "getItemPrice");
	}
	


}
