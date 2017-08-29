package com.ibm.extraction.commerce.catalog;

import java.util.ArrayList;

import com.ibm.CallInteropServlet;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class Category {

	private Category parent;
	private ArrayList<Category> children;
	private String sCategoryID, sCategoryKey;
	private String sOrganizationCode;
	private String sCatalogID;
	private String sOrganizationSuffix;
	private String sDomainCode;
	private ArrayList<Item> items;
	private ArrayList<CategoryAttribute> itemAttributes;
	
	public Category(String sCategoryID, String sCategoryKey, String sOrganizationCode, String sCatalogID){
		this.sCategoryID = sCategoryID;  
		this.sCategoryKey = sCategoryKey;
		this.sDomainCode = sOrganizationCode;
		this.sCatalogID = sCatalogID;
		this.sOrganizationCode = "Aurora-Corp";
		if(sDomainCode.equals("Aurora")){
			this.sOrganizationSuffix = "ACD";
		} else if(sDomainCode.equals("AuroraB2B")){
			this.sOrganizationSuffix = "AB2B";
		} else {
			this.sOrganizationSuffix = "AURO";
		}
	
		items = new ArrayList<Item>();
		itemAttributes = new ArrayList<CategoryAttribute>();
		children = new ArrayList<Category>();
	}
	
	public void addChild(Category cat){
		if (!children.contains(cat)){
			children.add(cat);
		}
	}
	
	public ArrayList<Category> getChildren(){
		return children;
	}
	public void addCategoryAttribute(CategoryAttribute attr){
		if (!itemAttributes.contains(attr)){
			itemAttributes.add(attr);
		}
	}
	
	public boolean hasItems(){
		if (items != null && items.size() > 0){
			return true;
		}
		return false;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public void addItem(Item item){
		if (!items.contains(item)){
			items.add(item);
		}
	}
	
	public void removeItem(Item item){
		if (!YFCCommon.isVoid(items)){
			if (items.contains(item)){
				items.remove(item);
			} else {
				for (Item tmp : items){
					if (tmp.getItemID().equals(item.getItemID())){
						items.remove(item);
						break;
					}
				}
			}
		}
	}
	
	public void setParent(String sCategoryID){
		
	}
	public void setParent(Category cat){
		parent = cat;
	}
	
	public Category getParent(){
		return parent;
	}
	
	public String getCategoryID(){
		return getSafeID(sCategoryID);
	}
	
	public String getCategoryKey(String sCategoryKeyExtn){
		if(YFCCommon.equals("_CD5", sCategoryKeyExtn)){
			if(this.sCatalogID.contains("10601")){
				return "PXN-" + this.sCategoryKey + sCategoryKeyExtn;
			} else if(this.sCatalogID.contains("10602")){
				return "PXC2-" + this.sCategoryKey + sCategoryKeyExtn;
			}
			//System.out.println(sOrganizationSuffix + " : " + this.sCatalogID + " : " + sOrganizationCode + " : " + sCategoryKey); 
		}
		return sOrganizationSuffix + "-" + this.sCategoryKey + sCategoryKeyExtn;
	}
	
	public String getDescription(){
		return sCategoryID;
	}
	
	public String toString(){
		if (!YFCCommon.isVoid(this.parent)){
			return parent.toString() + ":" + getCategoryID();
		}
		return getCategoryID();
	}
	
	public static String getSafeID(String input){
		if (!YFCCommon.isVoid(input)){
			return input.replaceAll(" ", "");
		}
		return "";
	}
	
	public String getCategoryPath(String sCategoryRootPath){
		if (!YFCCommon.isVoid(getParent())){
			return getParent().getCategoryPath(sCategoryRootPath) + "/" + getCategoryID();
		} else {
			return "/" + sCategoryRootPath + "/" + getCategoryID();
		}		
	}
	
	public String getOrganizationCode(){
		return sOrganizationCode;
	}
	
	public String getDomainCode(){
		if(this.sCatalogID.contains("10602")){
			return "PublixCatalogV2";
		}
		return sDomainCode;
	}
	
	public String getCategoryDomain(String sCategoryKeyExt){
		if(!YFCCommon.equals("_CD5", sCategoryKeyExt)){
			return getOrganizationCode() + sCategoryKeyExt;
		}
		if(this.sCatalogID.contains("10601")){
			return "PublixNative";
		} else if(this.sCatalogID.contains("10602")){
			return "PublixCatalogV2";
		}
		return  getDomainCode() + sCategoryKeyExt;
	}
	
	public String getRootCategoryPath(String sCategoryRootPath){
		if(!YFCCommon.equals("Catalog", sCategoryRootPath)){
			return getOrganizationCode() + sCategoryRootPath;
		}
		return getDomainCode() + sCategoryRootPath;
	}
	

	public static boolean categoryExists(String sKey, String sServer){
		try {
			YFCDocument dInput = YFCDocument.createDocument("Category");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("CategoryKey", sKey);
			YFCDocument dOutput = CallInteropServlet.invokeApi(dInput, null, "getCategoryDetails", sServer);
			if(!YFCCommon.isVoid(dOutput)){
				if(dOutput.getDocumentElement().getNodeName().equals("Errors")){
					System.out.println("Missing CategoryKey: " + sKey);
					return false;
				}
				return true;
			}
			System.out.println("Response: " + dOutput);
		} catch(Exception yex) {
			System.out.println("Missing CategoryKey: " + sKey);
			return false;
        }
		System.out.println("Missing CategoryKey: " + sKey);
		return false;
	}
	public void getCatalogCategories(String sCategoryRootPath, String sCategoryKeyExt, String sServer, YFCElement eRoot) {
		
		String sKey = getCategoryKey(sCategoryKeyExt);
		if(!categoryExists(sKey, sServer)){
			YFCDocument dCategories = YFCDocument.createDocument("Categories");
			
			YFCElement eCategory = dCategories.getDocumentElement().createChild("Category");
			eCategory.setAttribute("CategoryDomainKey", getCategoryDomain(sCategoryKeyExt));
			eCategory.setAttribute("CategoryID", getCategoryID());
			eCategory.setAttribute("CategoryKey", getCategoryKey(sCategoryKeyExt));
			if(YFCCommon.equals("_CD5", sCategoryKeyExt)){
				eCategory.setAttribute("CategoryPath", getCategoryPath(getDomainCode() + sCategoryRootPath));
			} else {
				eCategory.setAttribute("CategoryPath", getCategoryPath(getOrganizationCode() + sCategoryRootPath));	
			}
			eCategory.setAttribute("OrganizationCode", getOrganizationCode());
			eCategory.setAttribute("ShortDescription", getDescription());
			eCategory.setAttribute("Status", "3000");
			if (itemAttributes.size() > 0){
				YFCElement eItemAttributeList = eCategory.getChildElement("ItemAttributeList",true);
				for (CategoryAttribute att : itemAttributes){
					att.addItemAttribute(eItemAttributeList);
				}
			}
			try{
				if(YFCCommon.equals("_CD5", sCategoryKeyExt)){
					System.out.println("Input manageCategory: " + dCategories);
					YFCElement eApi = eRoot.createChild("API");
					eApi.setAttribute("Name", "manageCategory");
					YFCElement eInput = eApi.createChild("Input");
					eInput.importNode(dCategories.getDocumentElement());
					//YFCDocument dOutput = CallInteropServlet.invokeApi(dCategories, null, "manageCategory", sServer);
					//sSystem.out.println("manageCategory: " + dOutput);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		
			
		}
		for (Category child : getChildren()){
			child.getCatalogCategories(sCategoryRootPath, sCategoryKeyExt, sServer, eRoot);
		}
		
	}
	

	public void getModifyCategoryItem(YFCElement eModifyCategoryItems, String sCategoryRootPath){
		if (!YFCCommon.isVoid(items) && items.size() > 0){
			YFCElement eCategory = eModifyCategoryItems.createChild("Category");
			if(!YFCCommon.equals("Catalog", sCategoryRootPath)){
				eCategory.setAttribute("CategoryPath", getCategoryPath(getOrganizationCode() + sCategoryRootPath));
			} else {
				eCategory.setAttribute("CategoryPath", getCategoryPath(getDomainCode() + sCategoryRootPath));	
			}eCategory.setAttribute("OrganizationCode", getOrganizationCode());
			YFCElement eCategoryItemList = eCategory.getChildElement("CategoryItemList", true);
			for (Item item : items){
				if (!YFCCommon.isVoid(item)){
					YFCElement eCategoryItem = eCategoryItemList.createChild("CategoryItem");
					eCategoryItem.setAttribute("ItemID", item.getItemID());
					eCategoryItem.setAttribute("OrganizationCode", item.getOrganizationCode());
					eCategoryItem.setAttribute("UnitOfMeasure", item.getUnitOfMeasure());
				}	
			}
		}
	}
}
