package com.ibm.extraction.commerce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;

public class Item {

	private String sItemID = "";
	private String sItemGroupCode = "";
	private String sItemKey = "";
	private String sUnitOfMeasure = "";
	private String sOrganizationCode = "";
	private String sShortDescription = "";
	private String sProductName = "";
	private String sLongDescription = "";
	private String sSubCatalogOrgCode = null;
	private YFCDate startDate;
	private YFCDate endDate;
	private String sThumbNail;
	private String sImage;
	private String sURL;
	private String sItemType;
	private String manName = "";
	private String manItemID = "";
	private String sHeight = "";
	private String sLength = "";
	private String sWeight = "";
	private String sWidth = "";
	private String sCommerceID = "";
	private Item modelParent;
	private ArrayList<AdditionalAttribute> additionalAttributes;
	private ArrayList<Association> associatedItems;
	private ArrayList<Item> modelChildren;
	private String sBundleID = "";
	private String sConfigurationBOM = "";
	private String sKitCode = "";
	private boolean bExisting = false;
	public ArrayList<AdditionalAttribute> getAdditionalAttributes(){
		return additionalAttributes;
	}
	
	public void addAdditionalAttribute(AdditionalAttribute attr){
		if (!additionalAttributes.contains(attr)){
			additionalAttributes.add(attr);
		}
	}
	public Item getModelParent(){
		return modelParent;
	}
	
	public void setModelParent(Item parent){
		if (parent != null){
			parent.modelChildren.add(this);
		}
		modelParent = parent;
	}
	
	public boolean isModelParent(){
		return modelChildren.size() > 0;
	}
		
	public boolean hasMultipleModels(){
		if (isModelParent()){
			return modelChildren.size() > 1;
		}
		return false;
	}
	
	public void removeModelFromChild(){
		for (Item i : modelChildren){
			i.setModelParent(null);
		}
	}
	public String getItemID(){
		return sItemID;
	}
	
	public void setItemID(String sItemID){
		this.sItemID = sItemID;
	}
	
	public String getItemGroupCode(){
		return sItemGroupCode;
	}
	
	public void setItemGroupCode(String sItemGroupCode){
		this.sItemGroupCode = sItemGroupCode;
	}
	
	public String getItemKey(){
		return sItemKey;
	}
	
	public void setItemKey(String sItemKey){
		this.sItemKey = sItemKey;
	}
	
	public String getUnitOfMeasure(){
		return sUnitOfMeasure;
	}
	
	public void setUnitOfMeasure(String sUnitOfMeasure){
		this.sUnitOfMeasure = sUnitOfMeasure;
	}
	
	public String getOrganizationCode(){
		return sOrganizationCode;
	}
	
	public String getSubCatalogOrgCode(){
		return sSubCatalogOrgCode;
	}
	
	public String getCatalogID(){
		return getOrganizationCode()  + "MasterCatalog";
	}
	
	public void setOrganizationCode(String sOrganizationCode){
		this.sOrganizationCode = sOrganizationCode;
	}
	
	public String getShortDescription(){
		return sShortDescription;
	}
	
	public void setShortDescription(String sShortDescription){
		this.sShortDescription = sShortDescription;
	}
	
	public String getProductName(){
		return sProductName;
	}
	
	public void setProductName(String sProductName){
		this.sProductName = sProductName;
	}
	
	public String getLongDescription(){		
		if (sLongDescription == null){
			return "";
		}
		if (sLongDescription.length() > 2000){
			return sLongDescription.substring(0, sLongDescription.substring(0, 2000).lastIndexOf('.'));
		}
		return sLongDescription;
	}
	
	public void setLongDescription(String sLongDescription){
		this.sLongDescription = sLongDescription;
	}
	
	public YFCDate getStartDate(){
		return startDate;
	}
	
	public void setStartDate(YFCDate startDate){
		this.startDate = startDate;
	}
	
	public YFCDate getEndDate(){
		return endDate;
	}
	
	public void setEndDate(YFCDate endDate){
		this.endDate = endDate;
	}
	
	public String getThumbLocation(String sImageServer) {
		if (!YFCCommon.isVoid(sThumbNail)){
			if(sThumbNail.indexOf("http://") > -1){
				return sThumbNail;
			}
			return sImageServer + "/" + sThumbNail.substring(0, sThumbNail.lastIndexOf('/'));
		}
		return null;
	}
	
	public String getThumbID(){
		if (!YFCCommon.isVoid(sThumbNail)){
			return sThumbNail.substring(sThumbNail.lastIndexOf('/') + 1);
		}
		return null;
	}
	
	public void setThumbNail(String sThumbNail){
		this.sThumbNail = sThumbNail;
	}
	
	public String getImageLocation(String sImageServer) {
		if (!YFCCommon.isVoid(sImage)){
			if(sImage.contains("http://")){
				return sImage.substring(0, sImage.lastIndexOf('/'));
			} else if(sImage.contains("https://")){
				return sImage.substring(0, sImage.lastIndexOf('/'));
			}
			return sImageServer + "/" + sImage.substring(0, sImage.lastIndexOf('/'));
		}
		return null;
	}
	
	public String getImageID(){
		if (!YFCCommon.isVoid(sImage)){
			return sImage.substring(sImage.lastIndexOf('/') + 1);
		}
		return null;
	}
	
	public void setImage(String sImage){
		this.sImage = sImage;
	}
	
	public String getManufactureName(){
		return manName;
	}
	
	public void setManufactureName(String manName){
		this.manName = manName;
	}
	public String getManufactureItemID(){
		return manItemID;
	}
	
	public void setManufactureItemID(String manItemID){
		this.manItemID = manItemID;
	}
	
	public String getHeight(){
		return sHeight;
	}
	
	public String getLength(){
		return sLength;
	}
	
	public String getWidth(){
		return sWidth;
	}
	
	public String getWeight(){
		return sWeight;
	}
	
	public void setHeight(String sHeight){
		this.sHeight = sHeight;
	}
	
	public void setLength(String sLength){
		this.sLength = sLength;
	}
	
	public void setWidth(String sWidth){
		this.sWidth = sWidth;
	}
	
	public void setWeight(String sWeight){
		this.sWeight = sWeight;
	}
	
	public String getURL(){
		return sURL;
	}
	
	public void setURL(String sURL){
		this.sURL = sURL;
	}
	
	public String getItemType(){
		return this.sItemType;
	}
	
	public void setItemType(String sItemType){
		this.sItemType = sItemType;
	}
	
	public String getCommerceID(){
		return this.sCommerceID;
	}
	
	public Item(ResultSet rs, String sOrganizationCode, String sOrgSuffix, String sItemKey) throws SQLException{
		sItemID = rs.getString("ITEM_ID");
		sItemGroupCode = rs.getString("ITEM_GROUP_CODE");
		sCommerceID = rs.getString("ITEM_KEY");
		if(!YFCCommon.isVoid(sItemKey)){
			bExisting = true;
			this.sItemKey = sItemKey;
		} else {
			bExisting = false;
			this.sItemKey = sOrgSuffix + "-" + sCommerceID; 
		}
		
		sUnitOfMeasure = rs.getString("UNIT_OF_MEASURE"); 
		this.sOrganizationCode = sOrganizationCode; 
		sShortDescription = rs.getString("SHORTDESCRIPTION"); 
		sProductName = rs.getString("NAME"); 
		sLongDescription = rs.getString("LONGDESCRIPTION"); 
		
		
		if (!YFCCommon.isVoid(rs.getDate("STARTDATE"))){
			startDate = new YFCDate(rs.getDate("STARTDATE"));
		}
		if (!YFCCommon.isVoid(rs.getDate("ENDDATE"))){
			endDate = new YFCDate(rs.getDate("ENDDATE"));
		}
		if (!YFCCommon.isVoid(rs.getString("THUMBNAIL"))){
			sThumbNail = rs.getString("THUMBNAIL");
		}
		if (!YFCCommon.isVoid(rs.getString("FULLIMAGE"))){
			sImage = rs.getString("FULLIMAGE");
		}
		if (!YFCCommon.isVoid(rs.getString("URL"))){
			sURL = rs.getString("URL");
		}
		if (YFCCommon.equals(rs.getString("CATENTTYPE_ID").trim(), "DynamicKitBean")){
			sKitCode = "BUNDLE";
			sBundleID = rs.getString("BUNDLE_CODE");
			if (!YFCCommon.isVoid(rs.getString("CONFIGURATION"))){
				this.sConfigurationBOM = rs.getString("CONFIGURATION");
			}
		}
		manName = rs.getString("MFNAME");
		manItemID = rs.getString("MFPARTNUMBER");

		sHeight = rs.getString("HEIGHT");
		sLength = rs.getString("LENGTH");
		sWeight = rs.getString("WEIGHT");
		sWidth = rs.getString("WIDTH");
		additionalAttributes = new ArrayList<AdditionalAttribute>();
		associatedItems = new ArrayList<Association>();
		modelChildren = new ArrayList<Item>();
	}
	
	public void addAssociation(Association related){
		if (!YFCCommon.isVoid(related)){
			associatedItems.add(related);
		}
	}
	
	public boolean hasAssociations(){
		if (associatedItems == null || associatedItems.size() == 0){
			return false;
		}
		return true;
	}
	public boolean isExistingItem(){
		return bExisting;
	}
	public void createModifyAssociation(YFCElement eParent){
		YFCElement eAssociationList = eParent.createChild("AssociationList");
		eAssociationList.setAttribute("ItemKey", this.getItemKey());
		for (Association a : associatedItems){
			a.createAssociation(eAssociationList);
		}
		
	}
	
	public void createItemRecord (YFCElement eItemList, String sImageServer){
		YFCElement eItem = eItemList.createChild("Item");
		
		eItem.setAttribute("ItemID", getItemID());
		eItem.setAttribute("Action", "Manage");
		eItem.setAttribute("ItemGroupCode", getItemGroupCode());
		//eItem.setAttribute("ItemKey", getItemKey());
		eItem.setAttribute("UnitOfMeasure", getUnitOfMeasure());
		eItem.setAttribute("OrganizationCode", getOrganizationCode());
		if(!YFCCommon.isVoid(getSubCatalogOrgCode())){
			eItem.setAttribute("SubCatalogOrganizationCode", getSubCatalogOrgCode());
		}
		YFCElement ePrimaryInfo = eItem.getChildElement("PrimaryInformation", true);
		ePrimaryInfo.setAttribute("AllowGiftWrap", "Y");
		ePrimaryInfo.setAttribute("AssumeInfiniteInventory", "N");
		ePrimaryInfo.setAttribute("CapacityQuantityStrategy","ORD");
		ePrimaryInfo.setAttribute("Description", getShortDescription());
		ePrimaryInfo.setAttribute("ShortDescription", getProductName());
		ePrimaryInfo.setAttribute("ExtendedDescription", getLongDescription());
		if (!YFCCommon.isVoid(getStartDate())){
			ePrimaryInfo.setAttribute("EffectiveStartDate", getStartDate());
		}
		if (!YFCCommon.isVoid(getEndDate())){
			ePrimaryInfo.setAttribute("EffectiveEndDate", getEndDate());
		}
		if (!YFCCommon.isVoid(getImageLocation(sImageServer))){
			ePrimaryInfo.setAttribute("ImageLocation", getThumbLocation(sImageServer));
			ePrimaryInfo.setAttribute("ImageID", getThumbID());
			ePrimaryInfo.setAttribute("ImageLabel", getProductName());
		}
		if (!YFCCommon.isVoid(this.sKitCode)){
			ePrimaryInfo.setAttribute("KitCode", sKitCode);
			if (!YFCCommon.isVoid(sBundleID)){
				ePrimaryInfo.setAttribute("IsConfigurable", "Y");
				ePrimaryInfo.setAttribute("ConfigurationModelName", sBundleID);
				ePrimaryInfo.setAttribute("BundleFulfillmentMode", "00");
				ePrimaryInfo.setAttribute("BundlePricingStrategy", "ALL");
				if (!YFCCommon.isVoid(this.sConfigurationBOM)){
					ePrimaryInfo.setAttribute("BOMXML", sConfigurationBOM);
					ePrimaryInfo.setAttribute("IsPreConfigured", "Y");
				}
			}
		}
		ePrimaryInfo.setAttribute("IsDeliveryAllowed", "Y");
		ePrimaryInfo.setAttribute("IsShippingAllowed", "Y");
		ePrimaryInfo.setAttribute("IsPickupAllowed", "Y");
		ePrimaryInfo.setAttribute("IsHazmat", "N");
		ePrimaryInfo.setAttribute("IsReturnable", "Y");
		ePrimaryInfo.setAttribute("IsModelItem", "N");
		ePrimaryInfo.setAttribute("MasterCatalogID", getCatalogID());
		ePrimaryInfo.setAttribute("ManufacturerName", getManufactureName());
		ePrimaryInfo.setAttribute("ManufacturerItem", getManufactureItemID());
		ePrimaryInfo.setAttribute("MaxOrderQuantity", 10000);
		ePrimaryInfo.setAttribute("MinOrderQuantity", 1);
		ePrimaryInfo.setAttribute("MinimumCapacityQuantity", 0);
		ePrimaryInfo.setAttribute("PrimaryEnterpriseCode", getOrganizationCode());
		ePrimaryInfo.setAttribute("Status", "3000");
		ePrimaryInfo.setAttribute("TaxableFlag", "Y");
		ePrimaryInfo.setAttribute("UnitHeight", getHeight());
		ePrimaryInfo.setAttribute("UnitLength", getLength());
		ePrimaryInfo.setAttribute("UnitWeight", getWeight());
		ePrimaryInfo.setAttribute("UnitWidth", getWidth());
		if (!YFCCommon.isVoid(getItemType())){
			ePrimaryInfo.setAttribute("ItemType", getItemType());
		}
		YFCElement eClassificationCodes = eItem.getChildElement("ClassificationCodes", true);
		eClassificationCodes.setAttribute("StorageType", "Standard");
		if (!YFCCommon.isVoid(modelParent)){
			eClassificationCodes.setAttribute("Model", modelParent.getItemID());
			ePrimaryInfo.setAttribute("ModelItemUnitOfMeasure", modelParent.getUnitOfMeasure());
		}
		if (isModelParent()){
			ePrimaryInfo.setAttribute("IsModelItem", "Y");
		}
		if (!YFCCommon.isVoid(getURL())){
			YFCElement eAssetList = eItem.getChildElement("AssetList", true);
			YFCElement eAssetUrl = eAssetList.createChild("Asset");
			eAssetUrl.setAttribute("AssetID", "URL");
			eAssetUrl.setAttribute("Description", getURL());
			eAssetUrl.setAttribute("Type", "URL");
		}
		if (!YFCCommon.isVoid(getImageLocation(sImageServer))){
			YFCElement eAssetList = eItem.getChildElement("AssetList", true);
			YFCElement eAssetUrl = eAssetList.createChild("Asset");
			eAssetUrl.setAttribute("Type", "ITEM_IMAGE_1");
			eAssetUrl.setAttribute("Description", getProductName());
			eAssetUrl.setAttribute("AssetID", "IMG_" + getItemID());
			eAssetUrl.setAttribute("ContentLocation", getImageLocation(sImageServer));
			eAssetUrl.setAttribute("ContentID", getImageID());
		}
		
		if (!YFCCommon.isVoid(additionalAttributes) && additionalAttributes.size() > 0){
			YFCElement eAdditionalAttributeList = eItem.getChildElement("AdditionalAttributeList", true);
			eAdditionalAttributeList.setAttribute("Reset", "Y");
			for (AdditionalAttribute attr : additionalAttributes){
				attr.addAdditionalAttribute(eAdditionalAttributeList);
			}
		}
		 
		if (getItemGroupCode().equals("PROD")){
			YFCElement eItemServiceAssocList = eItem.getChildElement("ItemServiceAssocList", true);
			eItemServiceAssocList.setAttribute("Reset","Y");
			addItemServiceAssoc(eItemServiceAssocList, "Delivery");
			addItemServiceAssoc(eItemServiceAssocList, "Return");
		}
	}
	
	private void addItemServiceAssoc(YFCElement eItemServiceAssocList, String sItemID){
		YFCElement eDel = eItemServiceAssocList.createChild("ItemServiceAssoc");
		eDel.setAttribute("HoldSchedTillCompletion", "N");
		eDel.setAttribute("PricingProductQuantity", "0.00");
		eDel.setAttribute("PricingServiceQuantity", "0.00");
		eDel.setAttribute("ProductQuantity", "1.00");
		eDel.setAttribute("ServiceItemDesc", sItemID);
		eDel.setAttribute("ServiceItemGroupCode", "DS");
		eDel.setAttribute("ServiceItemId", sItemID);
		eDel.setAttribute("ServiceOrganizationCode", getOrganizationCode());
		eDel.setAttribute("ServiceQuantity", "1.00");
		eDel.setAttribute("ServiceUOM", "UNIT");
		eDel.setAttribute("TimeOffsetInMinutes", "0");
	}
}
