package com.mirakl.entity;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class MiraklOffer  {
	private YFCDocument dOffer = null;
	private YFCElement eOffer = null;
	
	public MiraklOffer(String sCatalogOrg, String sInventoryOrg, String sClassification){
		dOffer = YFCDocument.createDocument("Offer");
		eOffer = dOffer.getDocumentElement();
		eOffer.setAttribute("CatalogOrganizationCode", sCatalogOrg);
		eOffer.setAttribute("InventoryOrganizationCode", sInventoryOrg);
		eOffer.setAttribute("Classification", sClassification);
	}
	
	public MiraklOffer(ArrayList<String> header, String[] record, String sCatalogOrg, String sInventoryOrg, String sClassification){
		this(sCatalogOrg, sInventoryOrg, sClassification);
		for(int i = 0; i < header.size(); i++){
			String value = record[i].trim().substring(1, record[i].trim().length() - 1);
			if(!YFCCommon.isVoid(value)){
				eOffer.setAttribute(header.get(i), value);
			}
			
		}
	}
	
	public MiraklOffer(YFCDocument dOffer) {
		if(!YFCCommon.isVoid(dOffer)){
			this.eOffer = dOffer.getDocumentElement();
		}		
	}
	
	public MiraklOffer(YFCElement eOffer){
		if(!YFCCommon.isVoid(eOffer)){
			dOffer = YFCDocument.getDocumentFor(eOffer.toString());
			this.eOffer = dOffer.getDocumentElement();
		}	
	}
	
	public Document getXmlDocument() {
		return dOffer.getDocument();
	}
	
	public void appendToElement(YFCElement eRoot){
		eRoot.importNode(eOffer);
	}
	
	public void appendInventoryAdjustment(YFCElement eRoot){
		YFCElement eItem = eRoot.createChild("Item");
		eItem.setAttribute("ItemID", this.getItemID());
		eItem.setAttribute("UnitOfMeasure", this.getUnitOfMeasure());
		eItem.setAttribute("AdjustmentType", "ABSOLUTE");
		eItem.setAttribute("Quantity", this.getQuantity());
		eItem.setAttribute("ShipNode", this.getShopId());
		eItem.setAttribute("SupplyType", "ONHAND");	
	}
	
	public void appendItem(YFCElement eRoot){
		YFCElement eItem = eRoot.createChild("Item");
		eItem.setAttribute("ItemID", this.getItemID());
		eItem.setAttribute("UnitOfMeasure", this.getUnitOfMeasure());
				
		eItem.setAttribute("ItemGroupCode", "PROD");
		eItem.setAttribute("OrganizationCode", this.getCatalogOrganizationCode());
		YFCElement ePrimaryInfo = eItem.createChild("PrimaryInformation");
		ePrimaryInfo.setAttribute("MinOrderQuantity", this.getMinQuantityAlert());
		if(!YFCCommon.isVoid(getAvailableStartDate())){
			ePrimaryInfo.setAttribute("EffectiveStartDate", this.getAvailableStartDate());	
		}
		if(eOffer.getBooleanAttribute("active", true)){
			ePrimaryInfo.setAttribute("Status", "3000");
		} else {
			ePrimaryInfo.setAttribute("Status", "2000");
		}	
		ePrimaryInfo.setAttribute("IsPickupAllowed", "N");
		ePrimaryInfo.setAttribute("IsShippingAllowed", "Y");
		ePrimaryInfo.setAttribute("IsDeliveryAllowed", "N");
		ePrimaryInfo.setAttribute("IsReturnable", "N");
		if(!YFCCommon.isVoid(getClassification())){
			YFCElement eClassificationCode = eItem.createChild("ClassificationCodes");
			eClassificationCode.setAttribute("StorageType", getClassification());
		}		
	}
	
	public boolean isActive() {
		return eOffer.getBooleanAttribute("active", true);
	}
	public void setActive(boolean active) {
		eOffer.setAttribute("active", active);
	}
	public String getCatalogOrganizationCode(){
		return eOffer.getAttribute("CatalogOrganizationCode");
	}
	public void setCatalogOrganizationCode(String sOrgCode){
		eOffer.setAttribute("CatalogOrganizationCode", sOrgCode);
	}
	public String getClassification(){
		return eOffer.getAttribute("Classification");
	}
	public void setClassification(String sClassification){
		eOffer.setAttribute("Classification", sClassification);
	}
	public boolean isAllowQuoteRequests() {
		return eOffer.getBooleanAttribute("allow-quote-requests", false);
	}
	public void setAllowQuoteRequests(boolean allow_quote_requests) {
		eOffer.setAttribute("allow-quote-requests", allow_quote_requests);
	}
	public String getProductSku(){
		return eOffer.getAttribute("product-sku");
	}
	public boolean isPremium() {
		return eOffer.getBooleanAttribute("premium", false);
	}
	public void setPremium(boolean premium) {
		eOffer.setAttribute("premium", premium);
	}
	public String getDescription() {
		return eOffer.getAttribute("description");
	}
	public void setDescription(String description) {
		eOffer.setAttribute("description", description);
	}
	public String getMinShippingType() {
		return eOffer.getAttribute("min-shipping-type");
	}
	public void setMinShippingType(String min_shipping_type) {
		eOffer.setAttribute("min-shipping-type", min_shipping_type);
	}
	public String getMinShippingZone() {
		return eOffer.getAttribute("min-shipping-zone");
	}
	public void setMinShippingZone(String min_shippin_zone) {
		eOffer.setAttribute("min-shipping-zone", min_shippin_zone);
	}
	public String getPriceAdditionalInfo() {
		return eOffer.getAttribute("price-additional-info");
	}
	public void setPriceAdditionalInfo(String price_additional_info) {
		eOffer.setAttribute("price-additional-info", price_additional_info);
	}
	public String getProductTaxCode() {
		return eOffer.getAttribute("product-tax-code");
	}
	public void setProductTaxCode(String product_tax_code) {
		eOffer.setAttribute("product-tax-code", product_tax_code);
	}
	public String getShopName() {
		return eOffer.getAttribute("shop-name");
	}
	public void setShopName(String shop_name) {
		eOffer.setAttribute("shop-name", shop_name);
	}
	public String getStateCode() {
		return eOffer.getAttribute("state-code");
	}
	public void setStateCode(String state_code) {
		eOffer.setAttribute("state-code", state_code);
	}
	public String getCurrencyIsoCode() {
		return eOffer.getAttribute("currency-iso-code");
	}
	public void setCurrencyIsoCode(String currency_iso_code) {
		eOffer.setAttribute("currency-iso-code", currency_iso_code);
	}
	public String getItemID() {
		return "MKO_" + getOfferId();
	}
	public String getUnitOfMeasure() {
		return "EACH";
	}
	public int getOfferId() {
		return eOffer.getIntAttribute("offer-id");
	}
	public void setOfferId(int offer_id) {
		eOffer.setAttribute("offer-id", offer_id);
	}
	public int getQuantity() {
		return eOffer.getIntAttribute("quantity");
	}
	public void setQuantity(int quantity) {
		eOffer.setAttribute("quantity", quantity);
	}
	public String getShopId() {
		return "MRK_SHOP_" + eOffer.getIntAttribute("shop-id");
	}
	public void setShopId(int shop_id) {
		eOffer.setAttribute("shop-id", shop_id);
	}
	public int getMinQuantityAlert() {
		return eOffer.getIntAttribute("min-quantity-alert");
	}
	public void setMinQuantityAlert(int min_quantity_alert) {
		eOffer.setAttribute("min-quantity-alert", min_quantity_alert);
	}
	public double getPrice() {
		return eOffer.getDoubleAttribute("price");
	}
	public void setPrice(double price) {
		eOffer.setAttribute("price", price);
	}
	public double getUnitDiscountPrice() {
		return eOffer.getDoubleAttribute("unit-discount-price");
	}
	public void setUnitDiscountPrice(double unit_discount_price) {
		eOffer.setAttribute("unit-discount-price", unit_discount_price);
	}
	public double getUnitOriginPrice() {
		return eOffer.getDoubleAttribute("unit-origin-price");
	}
	public void setUnitOriginPrice(double unit_origin_price) {
		eOffer.setAttribute("unit-origin-price", unit_origin_price);
	}
	public double getFavoriteRank() {
		return eOffer.getDoubleAttribute("favorite-rank");
	}
	public void setFavoriteRank(double favorite_rank) {
		eOffer.setAttribute("favorite-rank", favorite_rank);
	}
	public double getLeadtimeToShip() {
		return eOffer.getDoubleAttribute("leadtime-to-ship");
	}
	public void setLeadtimeToShip(double leadtime_to_ship) {
		eOffer.setAttribute("leadtime-to-ship", leadtime_to_ship);
	}
	public double getMinShippingPrice() {
		return eOffer.getDoubleAttribute("min-shipping-price");
	}
	public void setMinShippingPrice(double min_shipping_price) {
		eOffer.setAttribute("min-shipping-price", min_shipping_price);
	}
	public double getMinShippingPriceAdditional() {
		return eOffer.getDoubleAttribute("min-shipping-price-additional");
	}
	public void setMinShippingPriceAdditional(double min_shipping_price_additional) {
		eOffer.setAttribute("min-shipping-price-additional", min_shipping_price_additional);
	}
	public double getNbEvaluation() {
		return eOffer.getDoubleAttribute("nb-evaluation");
	}
	public void setNbEvaluation(double nb_evaluation) {
		eOffer.setAttribute("nb-evaluation", nb_evaluation);
	}
	public double getShopGrade() {
		return eOffer.getDoubleAttribute("shop-grade");
	}
	public void setShopGrade(double shop_grade) {
		eOffer.setAttribute("shop-grade", shop_grade);
	}
	public double getTotalPrice() {
		return eOffer.getDoubleAttribute("total-price");
	}
	public void setTotalPrice(double total_price) {
		eOffer.setAttribute("total-price", total_price);
	}
	public Date getDiscountEndDate() {
		return eOffer.getDateAttribute("discount-end-date");
	}
	public void setDiscountEndDate(YDate discount_end_date) {
		eOffer.setAttribute("discount-end-date", discount_end_date);
	}
	public Date getDiscountStartDate() {
		return eOffer.getDateAttribute("discount-start-date");
	}
	public void setDiscountStartDate(YDate discount_start_date) {
		eOffer.setAttribute("discount-start-date", discount_start_date);
	}
	public Date getAvailableEndDate() {
		return eOffer.getDateAttribute("available-end-date");
	}
	public void setAvailableEndDate(YDate available_end_date) {
		eOffer.setAttribute("available-end-date", available_end_date);
	}
	public YDate getAvailableStartDate() {
		return eOffer.getYDateAttribute("available-start-date");
	}
	public void setAvailableStartDate(YDate available_start_date) {
		eOffer.setAttribute("available-start-date", available_start_date);
	}
	
	
	
		
}
