package com.ibm.extraction;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.ibm.extraction.commerce.BDASynchronization;
import com.ibm.extraction.commerce.catalog.AdditionalAttribute;
import com.ibm.extraction.commerce.catalog.AllowedValue;
import com.ibm.extraction.commerce.catalog.Association;
import com.ibm.extraction.commerce.catalog.Attribute;
import com.ibm.extraction.commerce.catalog.Category;
import com.ibm.extraction.commerce.catalog.CategoryAttribute;
import com.ibm.extraction.commerce.catalog.Item;
import com.utilities.FileReader;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASynchronizeItems extends BDASynchronization {
	private HashMap<String, String> orgToCatalog;
	private HashMap<String, String> suffixForOrg;
	private HashMap<String, String> getCatalogOrgForOrg;
	
	private String catalogId = "10001";
	private String language = "-5";
	private String currency = "USD";
	
	public BDASynchronizeItems(){
		super();
	}
	
	private String getServer(){
		if (!YFCCommon.isVoid(getProperty("server"))){
			return (String)getProperty("server");
		}
		return "http://oms.innovationcloud.info:9080";
	}
	
	public static void main(String[] args){
		BDASynchronizeItems temp = new BDASynchronizeItems();
		YFCDocument dInput = YFCDocument.createDocument("Input");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("LoadItems", true);
		eInput.setAttribute("ItemUpdate", false);
		eInput.setAttribute("OrgFilter", "");
		eInput.setAttribute("ItemFilter", "");
		eInput.setAttribute("LoadCatalogItems", true);
		Document output = temp.syncronizeItems(null, dInput.getDocument());
		YFCDocument dOutput = YFCDocument.getDocumentFor(output);
		FileReader.writeXML("/Users/pfaiola/Output.xml", dOutput);
	}
	
	private Document getItemListTemplate(){
		YFCDocument dItemList = YFCDocument.createDocument("ItemList");
		YFCElement eItemList = dItemList.getDocumentElement();
		YFCElement eItem = eItemList.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("ItemKey", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("OrganizationCode", "");
		return dItemList.getDocument();
	}
	
	private YFCDocument getOrganizationTemplate(){
		YFCDocument dItemList = YFCDocument.createDocument("Organization");
		YFCElement eOrganization = dItemList.getDocumentElement();
		eOrganization.setAttribute("OrganizationCode","");
		eOrganization.setAttribute("OrganizationSuffix","");
		eOrganization.setAttribute("CatalogOrganizationCode","");
		return dItemList;
	}
	
	private void loadOrg(YFSEnvironment env, String sOrganization){
		if (YFCCommon.isVoid(suffixForOrg) || YFCCommon.isVoid(getCatalogOrgForOrg)){
			suffixForOrg = new HashMap<String, String>();
			getCatalogOrgForOrg = new HashMap<String, String>();
		}
		try {
			YFCDocument dOrg = YFCDocument.createDocument("Organization");
			YFCElement eOrg = dOrg.getDocumentElement();
			eOrg.setAttribute("OrganizationCode", sOrganization);
			YFCDocument dOrgOutput = CallInteropServlet.invokeApi(dOrg, getOrganizationTemplate(), "getOrganizationHierarchy", getServer());
			if(!YFCCommon.isVoid(dOrgOutput)){
				YFCElement eOrgOutput = dOrgOutput.getDocumentElement();
				if (!YFCCommon.isVoid(eOrgOutput.getAttribute("OrganizationSuffix"))){
					suffixForOrg.put(sOrganization, eOrgOutput.getAttribute("OrganizationSuffix"));
					getCatalogOrgForOrg.put(sOrganization, "Aurora-Corp");
				}
			}
		} catch(Exception yex) {
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
		
	}
	private String getSuffixForOrg(YFSEnvironment env, String sOrganization){
		if (YFCCommon.isVoid(sOrganization)){
			return null;
		}
		if (suffixForOrg == null){
			loadOrg(env, sOrganization);
		}
		if (YFCCommon.isVoid(suffixForOrg.get(sOrganization))){
			loadOrg(env, sOrganization);
		}
		if (YFCCommon.isVoid(suffixForOrg.get(sOrganization))){
			suffixForOrg.put(sOrganization, "AURO");
		}
		return suffixForOrg.get(sOrganization);
	}
	
	private String getCatalogOrganization(YFSEnvironment env, String sOrganization){
		if (YFCCommon.isVoid(sOrganization)){
			return null;
		}
		if (getCatalogOrgForOrg == null){
			loadOrg(env, sOrganization);
		}
		if (YFCCommon.isVoid(getCatalogOrgForOrg.get(sOrganization))){
			loadOrg(env, sOrganization);
		}
		if (YFCCommon.isVoid(getCatalogOrgForOrg.get(sOrganization))){
			getCatalogOrgForOrg.put(sOrganization, "Aurora-Corp");
		}
		return getCatalogOrgForOrg.get(sOrganization);
	}
	
	
	private ArrayList<String> getExistingCategoryDomains(){
		ArrayList<String> cDomains = new ArrayList<String>();
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection();
			String sSql = "SELECT CATEGORY_DOMAIN_KEY, CATEGORY_DOMAIN, ORGANIZATION_CODE, ATTRIBUTE_NAME, AUTH_SUB_CAT_ORG_CODE FROM OMDB.YFS_CATEGORY_DOMAIN";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				cDomains.add(rs.getString("CATEGORY_DOMAIN_KEY").trim());
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return cDomains;
	}
	
	private HashMap<String, HashMap<String, String>> getExistingItems(){
		HashMap<String, HashMap<String, String>> existingItems = new HashMap<String, HashMap<String, String>>();
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection();
			String sSql = "SELECT TRIM(ITEM_KEY) ITEM_KEY, TRIM(ITEM_ID) ITEM_ID, TRIM(ORGANIZATION_CODE) ORGANIZATION_CODE FROM OMDB.YFS_ITEM";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				HashMap<String, String> org = existingItems.get(rs.getString("ORGANIZATION_CODE"));
				if (org == null){
					org = new HashMap<String, String>();
					existingItems.put(rs.getString("ORGANIZATION_CODE"), org);
				}
				org.put(rs.getString("ITEM_ID"), rs.getString("ITEM_KEY"));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return existingItems;
	}
	
	private static ArrayList<String> existingCategories = null;
	
	public static ArrayList<String> getExistingCategories(){
		if(existingCategories != null){
			return existingCategories;
		}
		existingCategories = new ArrayList<String>();
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection();
			String sSql = "SELECT TRIM(CATEGORY_KEY) CATEGORY_KEY FROM OMDB.YFS_CATEGORY";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				existingCategories.add(rs.getString("CATEGORY_KEY"));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return existingCategories;
	}
	
	public Document syncronizeItems(YFSEnvironment env, Document inputDoc){
		YFCDocument dMultiDoc = YFCDocument.createDocument("MultiApi");
		YFCElement eMultiDoc = dMultiDoc.getDocumentElement();
		
		TreeMap<String, Item> items = new TreeMap<String, Item>();
		HashMap<String, HashMap<String, Attribute>> attributes = new HashMap<String, HashMap<String, Attribute>>();
		HashMap<String, HashMap<String, Category>> categories = new HashMap<String, HashMap<String, Category>>();
		
		
		loadProductAttributes(attributes, env);
		loadAllowedValues(attributes, env);
		
		getProductRecords(items, env);
		updateModelRecords(items);
		
		addAdditionalAttribute(items, attributes, env);
		
		HashMap<String, ArrayList<String>> rootRecords = loadCategories(categories, env);
		linkCategoryItems(categories, items, env);
		linkAssociationItems(items, env);
		loadItemAttributeGroups(attributes, categories, env);
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		YFCElement eServiceInput = dInput.getDocumentElement();
		if (eServiceInput.getBooleanAttribute("LoadItems", true)){
			
			if (!eServiceInput.getBooleanAttribute("ItemUpdate", false)){
				YFCElement eApi = eMultiDoc.createChild("API");
				System.out.println("Items Created: " + items.size());
				eApi.setAttribute("Name", "manageAttributeGroup");
				YFCElement eInput = eApi.createChild("Input");
				YFCElement eAttributeGroupList = eInput.createChild("AttributeGroupList");
				for (String sOrgCode : attributes.keySet()){
					YFCElement eAttributeGroup = eAttributeGroupList.createChild("AttributeGroup");
					eAttributeGroup.setAttribute("AttributeDomainID", "ItemAttribute");
					if (sOrgCode.equals("Aurora-Corp")){
						eAttributeGroup.setAttribute("AttributeGroupID", "Aurora");
					} else {
						eAttributeGroup.setAttribute("AttributeGroupID", sOrgCode);
					}
					eAttributeGroup.setAttribute("OrganizationCode", sOrgCode);
					YFCElement eAttributeList = eAttributeGroup.createChild("AttributeList");
					for (Attribute attr : attributes.get(sOrgCode).values()){
						if(!attr.attributeExists(getServer())){
							attr.createAttribute(eAttributeList);
						}
					}
				}
				ArrayList<String> existingDomains = getExistingCategoryDomains();
				//YFCElement eCatDomains = YFCDocument.createDocument("MultiApi").getDocumentElement();
				YFCElement eCatDomains = eMultiDoc;
				for(String sKey : categories.keySet()){
					for (String sNode : categories.get(sKey).keySet()){
						Category t = categories.get(sKey).get(sNode);
						if(!existingDomains.contains(t.getCategoryDomain("_CD0"))){
							existingDomains.add(t.getCategoryDomain("_CD0"));
							eApi = eCatDomains.createChild("API");
							eApi.setAttribute("Name", "createCategoryDomain");
							YFCElement eCategoryDomain = eApi.createChild("Input").createChild("CategoryDomainList").createChild("CategoryDomain");
							eCategoryDomain.setAttribute("CategoryDomainKey", t.getCategoryDomain("_CD0"));
							eCategoryDomain.setAttribute("OrganizationCode", t.getOrganizationCode());
							eCategoryDomain.setAttribute("CategoryDomain", t.getOrganizationCode() + "MasterCatalog");
							eCategoryDomain.setAttribute("IsClassification", "N");
							eCategoryDomain.setAttribute("Description", "Catalog for " + t.getOrganizationCode());
							eCategoryDomain.setAttribute("ShortDescription", "Catalog for " + t.getOrganizationCode());
						}
						if(!existingDomains.contains(t.getCategoryDomain("_CD2"))){
							existingDomains.add(t.getCategoryDomain("_CD2"));
							eApi = eCatDomains.createChild("API");
							eApi.setAttribute("Name", "createCategoryDomain");
							YFCElement eCategoryDomain = eApi.createChild("Input").createChild("CategoryDomainList").createChild("CategoryDomain");
							eCategoryDomain.setAttribute("CategoryDomainKey", t.getCategoryDomain("_CD2"));
							eCategoryDomain.setAttribute("OrganizationCode", t.getOrganizationCode());
							eCategoryDomain.setAttribute("CategoryDomain", t.getOrganizationCode() + "StyleItem");
							eCategoryDomain.setAttribute("IsClassification", "Y");
							eCategoryDomain.setAttribute("AttributeName", "ItemType");
							eCategoryDomain.setAttribute("Description", "Styles for " + t.getOrganizationCode());
							eCategoryDomain.setAttribute("ShortDescription", "Styles for " + t.getOrganizationCode());
						}
						if(!existingDomains.contains(t.getCategoryDomain("_CD5"))){
							existingDomains.add(t.getCategoryDomain("_CD5"));
							eApi = eCatDomains.createChild("API");
							eApi.setAttribute("Name", "createCategoryDomain");
							YFCElement eCategoryDomain = eApi.createChild("Input").createChild("CategoryDomainList").createChild("CategoryDomain");
							eCategoryDomain.setAttribute("CategoryDomainKey", t.getCategoryDomain("_CD5"));
							eCategoryDomain.setAttribute("OrganizationCode", t.getOrganizationCode());
							eCategoryDomain.setAttribute("CategoryDomain", t.getDomainCode() + "Catalog");
							eCategoryDomain.setAttribute("IsClassification", "N");
							eCategoryDomain.setAttribute("Description", "Catalog for " + t.getDomainCode());
							eCategoryDomain.setAttribute("ShortDescription", "Catalog for " + t.getDomainCode());
						}
					}
				}
				/*
				try {
					System.out.println("Category Domains: " + eCatDomains);
				    YFCDocument dOutput = CallInteropServlet.invokeApi(eCatDomains.getOwnerDocument(), null, "multiApi", getServer());
					System.out.println("Response: " + dOutput);
				} catch(Exception yex) {
					dMultiDoc = YFCDocument.createDocument("Output");
					YFCElement eElem = dMultiDoc.getDocumentElement();
					eElem.setNodeValue(yex.toString());
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
		            return dMultiDoc.getDocument();
		        }
				*/
				for (String sOrg : rootRecords.keySet()){
					for (String sRootNode : rootRecords.get(sOrg)){
						if(categories.containsKey(sOrg)){
							Category root = categories.get(sOrg).get(sRootNode);
							if(!YFCCommon.isVoid(root)){
								root.getCatalogCategories("MasterCatalog", "_CD0", getServer(), eCatDomains);
								root.getCatalogCategories("StyleItem", "_CD2", getServer(), eCatDomains);
								root.getCatalogCategories("Catalog", "_CD5", getServer(), eCatDomains);
							}
						}
					}
				}
				
			}
			YFCElement eApi = eMultiDoc.createChild("API");
			eApi.setAttribute("Name", "manageItem");
			YFCElement eInput = eApi.createChild("Input");
			YFCElement eItemList = eInput.createChild("ItemList");
			for (String item : items.keySet()){
				Item i = items.get(item);
				if(!i.isExistingItem()){
					if (!YFCCommon.isVoid(eServiceInput.getAttribute("OrgFilter")) && !YFCCommon.isVoid(eServiceInput.getAttribute("ItemFilter"))){
						if (i.getOrganizationCode().equals(eServiceInput.getAttribute("OrgFilter")) && i.getItemID().indexOf(eServiceInput.getAttribute("ItemFilter")) > -1){
							i.createItemRecord(eItemList, getImageServer());
						}
					} else if (!YFCCommon.isVoid(eServiceInput.getAttribute("ItemFilter"))){
						if (i.getItemID().indexOf(eServiceInput.getAttribute("ItemFilter")) > -1){
							i.createItemRecord(eItemList, getImageServer());
						}
					} else {
						i.createItemRecord(eItemList, getImageServer());
					}
				}
			}
			/*
			try {
			    YFCDocument dOutput = CallInteropServlet.invokeApi(dMultiDoc, null, "multiApi", getServer());
				System.out.println("Response: " + dOutput);
			} catch(Exception yex) {
				dMultiDoc = YFCDocument.createDocument("Output");
				YFCElement eElem = dMultiDoc.getDocumentElement();
				eElem.setNodeValue(yex.toString());
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	            return dMultiDoc.getDocument();
	        }*/
			getListPrices(eMultiDoc);
		}
		if (eServiceInput.getBooleanAttribute("LoadCatalogItems", true)){
			
			try {
				Connection dbConn = getOMSConnection();
				/*String sDeleteSql = "DELETE FROM " + this.getOMSDBSchema() + ".YFS_CATEGORY_ITEM WHERE MODIFYTS > '2015-01-01'";
				PreparedStatement p = dbConn.prepareStatement(sDeleteSql);
				p.execute();*/
				HashMap<String, ArrayList<String>> map = getExistingCategoryMap();
				String sSql = "INSERT INTO " + this.getOMSDBSchema() + ".YFS_CATEGORY_ITEM (CATEGORY_ITEM_KEY, CATEGORY_KEY, ITEM_KEY) VALUES (?,?,?)";
				String sCheck = "Select * from " + this.getOMSDBSchema() + ".YFS_CATEGORY_ITEM WHERE ITEM_KEY = ? AND CATEGORY_KEY = ?";
				String sCategoryCheck = "SELECT * FROM " + this.getOMSDBSchema() + ".YFS_CATEGORY WHERE CATEGORY_KEY = ?";
				PreparedStatement ps = dbConn.prepareStatement(sSql);
				PreparedStatement catCheck = dbConn.prepareStatement(sCategoryCheck);
				PreparedStatement recCheck = dbConn.prepareStatement(sCheck);
				int key = 0;
				for (String sOrg : categories.keySet()){
					for (Category cat : categories.get(sOrg).values()){
						if (cat.hasItems()){
							for (Item it : cat.getItems()){
								if(it.isExistingItem()){
									String sKey = System.currentTimeMillis() + "_" + (key++);
									ArrayList<String> t = map.get(cat.getCategoryKey("_CD0"));
									/*if(YFCCommon.isVoid(t) || !t.contains(it.getItemKey())){
										try {
											System.out.println("New Relationship: " + cat.getCategoryKey("_CD0") + " :: " + it.getItemKey());
											ps.setString(1, sKey);
											ps.setString(2, cat.getCategoryKey("_CD0"));
											ps.setString(3, it.getItemKey());
											ps.executeUpdate();
											if(YFCCommon.isVoid(t)){
												t = new ArrayList<String>();
												map.put(cat.getCategoryKey("_CD0"), t);
											}
											t.add(it.getItemKey());
										} catch (SQLException sE) {
											sE.printStackTrace();
											System.out.println("Key: " + sKey);
											System.out.println("Category: " + cat.getCategoryKey("_CD0"));
											System.out.println("ItemKey:" + it.getItemKey());
										}
									}*/
									String sCatKey = cat.getCategoryKey("_CD5").replace("AURO", "VTT");
									t = map.get(sCatKey);
									if(YFCCommon.isVoid(t) || !t.contains(it.getItemKey())){
										try{
											
											catCheck.setString(1, sCatKey);
											ResultSet c = catCheck.executeQuery();
											if(c.next()){
												System.out.println("New Relationship: " + sCatKey + " :: " + it.getItemKey());
												sKey = System.currentTimeMillis() + "_" + (key++);
												
												ps.setString(1, sKey);
												ps.setString(2, cat.getCategoryKey("_CD5"));
												ps.setString(3, it.getItemKey());
												ps.executeUpdate();
												if(YFCCommon.isVoid(t)){
													t = new ArrayList<String>();
													map.put(cat.getCategoryKey("_CD5"), t);
												}
												t.add(it.getItemKey());
											} else {
											    sCatKey = cat.getCategoryKey("_CD5").replace("AURO", "VTT");
												catCheck.setString(1, sCatKey);
											    c = catCheck.executeQuery();
											    if(c.next()){
											    	recCheck.setString(1, it.getItemKey());
											    	recCheck.setString(2, sCatKey);
											    	ResultSet r = catCheck.executeQuery();
											    	if(!r.next()){
											    		System.out.println("New Relationship: " + sCatKey + " :: " + it.getItemKey());
											    		ps.setString(1, sKey);
														ps.setString(2, sCatKey);
														ps.setString(3, it.getItemKey());
														ps.executeUpdate();
											    	}
											    }
											}
											
										} catch (SQLException sE) {
											sE.printStackTrace();
											System.out.println("Key: " + sKey);
											System.out.println("Category: " + sCatKey);
											System.out.println("ItemKey:" + it.getItemKey());
										}
									}
								}
							}
						}
					}
				}
				dbConn.close();
			} catch (ClassNotFoundException cE){
				cE.printStackTrace();
			} catch (SQLException sE) {
				sE.printStackTrace();
			}
		}
		
		
		/*for (Item item : items.values()){
			if (item.hasAssociations()){
				eApi = eMultiDoc.createChild("API");
				eApi.setAttribute("Name", "modifyItemAssociation");
				eInput = eApi.createChild("Input");
				item.createModifyAssociation(eInput);
				try {
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					localApi.invoke(env, "modifyItemAssociation", dMultiDoc.getDocument());
				} catch(Exception yex) {
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
		        } 
			}
		
		}	*/
		this.writeXML("/Users/pfaiola/", "multiApi.xml", dMultiDoc);
		return dMultiDoc.getDocument();
	}
	
	public static void writeXML(String sPath, String sFile, YFCDocument output){
		validatePath(sPath);
		FileWriter fout;
		try{
			deleteExistingFile(sPath + File.separator + sFile);
			char buffer[] = new char[output.toString().length()];
			output.toString().getChars(0,output.toString().length(), buffer, 0);
			fout = new FileWriter(sPath + File.separator + sFile);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
		}catch(Exception e){
			System.out.println("Error Opening File");
		}
		System.out.println("Writing File: " + sPath + "/" + sFile);
	}
	
	public static void writeLine(String sPath, String sFile, String sLine){
		validatePath(sPath);
		FileWriter fout;
		try{
			char buffer[] = new char[sLine.toString().length()];
			sLine.toString().getChars(0,sLine.toString().length(), buffer, 0);
			fout = new FileWriter(sPath + File.separator + sFile, true);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.write('\n');
			fout.close();
		}catch(Exception e){
			System.out.println("Error Opening File");
		}
	}
	
	private static void deleteExistingFile(String sFile){
		File temp = new File(sFile);
		if(temp.exists()){
			temp.delete();
		}
	}
	
	public static void validatePath(String sFilePath){
		File temp = new File(sFilePath);
		temp.mkdirs();
	}
	
	private String getOrgForCatalog(String sCatalogId){
		HashMap<String, String> temp = getOrgToCatalog();
		if (temp.containsKey(sCatalogId)){
			return temp.get(sCatalogId);
		}
		return "Aurora";
	}
	
	private HashMap<String, String> getOrgToCatalog(){
		if (orgToCatalog == null){
		//	orgToCatalog = CommerceIntegrationMap.getOMSValues("catalogIdToOrganizationCode");
		}
		return orgToCatalog;
	}
	
	private void getListPrices(YFCElement eMultiDoc){
		Connection dbConn = null;
		Connection omdbConn = null;
		try {
			dbConn = getCommerceConnection();
			String sSql = "SELECT C.CATENTRY_ID, C.PARTNUMBER, L.LISTPRICE, SE.STOREENT_ID FROM CATENTRY C INNER JOIN LISTPRICE L ON L.CATENTRY_ID = C.CATENTRY_ID INNER JOIN STOREENT SE ON SE.MEMBER_ID = C.MEMBER_ID WHERE L.CURRENCY LIKE '" + currency + "%'";
			PreparedStatement ps = dbConn.prepareStatement(sSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			
			ResultSet rs = ps.executeQuery();
			
			omdbConn = getOMSConnection();
			String sOMSql = "SELECT TRIM(I.ITEM_ID) ITEM_ID, TRIM(C.CATEGORY_DOMAIN_KEY) CATEGORY_DOMAIN_KEY FROM OMDB.YFS_ITEM I INNER JOIN OMDB.YFS_CATEGORY_ITEM CI ON I.ITEM_KEY = CI.ITEM_KEY INNER JOIN OMDB.YFS_CATEGORY C ON C.CATEGORY_KEY = CI.CATEGORY_KEY WHERE C.CATEGORY_DOMAIN_KEY LIKE '%_CD5%' AND I.ITEM_ID NOT IN (SELECT ITEM_ID FROM OMDB.YPM_PRICELIST_LINE) ORDER BY C.CATEGORY_DOMAIN_KEY";
			PreparedStatement pso = omdbConn.prepareStatement(sOMSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rso = pso.executeQuery();
			HashMap<String, ArrayList<String>> missing = new HashMap<String, ArrayList<String>>();
			while (rso.next()){
				String sOrgCode = rso.getString("CATEGORY_DOMAIN_KEY").split("_")[0];
				ArrayList<String> items = missing.get(sOrgCode);
				if (YFCCommon.isVoid(items)){
					items = new ArrayList<String>();
					missing.put(sOrgCode, items);
				}
				items.add(rso.getString("ITEM_ID"));
			}
			
			sOMSql = "SELECT PRICELIST_HDR_KEY, ORGANIZATION_CODE FROM OMDB.YPM_PRICELIST_HDR";
			pso = omdbConn.prepareStatement(sOMSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rso = pso.executeQuery();
			HashMap<String, String> pricelist_hdr = new HashMap<String, String>();
			while (rso.next()){
				pricelist_hdr.put(rso.getString("ORGANIZATION_CODE"), rso.getString("PRICELIST_HDR_KEY"));
			}
		
			for (String sOrgCode : missing.keySet()){
				if(!pricelist_hdr.containsKey(sOrgCode)){
					YFCElement eApi = eMultiDoc.createChild("API");
					eApi.setAttribute("Name", "managePricelistHeader");
					YFCElement eList = eApi.createChild("Input").createChild("PricelistHeader");
					eList.setAttribute("OrganizationCode", sOrgCode);
					eList.setAttribute("Currency", currency);
					eList.setAttribute("AddAllItemsFromCatalog", "N");
					eList.setAttribute("Description", sOrgCode + " Price List");
					eList.setAttribute("EndDateActive", "2025-01-01");
					eList.setAttribute("StartDateActive", "2015-01-01");
					eList.setAttribute("PricingStatus","ACTIVE");
					eList.setAttribute("PricelistName", sOrgCode + "Pricelist");
					eList.setAttribute("PricelistHeaderKey", sOrgCode + "Pricelist");
					pricelist_hdr.put(sOrgCode, sOrgCode + "Pricelist");
				}
				
				
				YFCElement eApi = eMultiDoc.createChild("API");
				eApi.setAttribute("Name", "managePricelistLine");
				YFCElement eList = eApi.createChild("Input").createChild("PricelistLineList");
				eList.setAttribute("OrganizationCode", sOrgCode);
				
				
				rs.first();
				while ( rs.next() ) {
					if (missing.get(sOrgCode).contains(rs.getString("PARTNUMBER"))){
						YFCElement ePrice = eList.createChild("PricelistLine");
						ePrice.setAttribute("ItemID", rs.getString("PARTNUMBER"));
						ePrice.setAttribute("ListPrice", rs.getDouble("LISTPRICE"));
						ePrice.setAttribute("OrganizationCode", sOrgCode);
						ePrice.setAttribute("PricelistHeaderKey", pricelist_hdr.get(sOrgCode));
				
						//
						ePrice.setAttribute("PricingStatus", "ACTIVE");
						ePrice.setAttribute("UnitOfMeasure", "EACH");
						missing.get(sOrgCode).remove(rs.getString("PARTNUMBER"));
					}
				}
			}

			
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		} finally {
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(omdbConn != null){
				try {
					omdbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void loadItemAttributeGroups(HashMap<String, HashMap<String, Attribute>> attributes, HashMap<String, HashMap<String, Category>> categories, YFSEnvironment env){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT DISTINCT R.CATGROUP_ID, A.ATTR_ID, A.IDENTIFIER, A.SEQUENCE, CA.USAGE, R.CATALOG_ID FROM CATGPENREL R INNER JOIN CATENTRY C ON C.CATENTRY_ID = R.CATENTRY_ID	INNER JOIN CATENTRYATTR CA ON CA.CATENTRY_ID = C.CATENTRY_ID INNER JOIN ATTR A ON A.ATTR_ID = CA.ATTR_ID";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				String sOrganizationCode = getCatalogOrganization(env, getOrgForCatalog(rs.getString("CATALOG_ID")));
				if (!YFCCommon.isVoid(sOrganizationCode)){
					if (attributes.get(sOrganizationCode).containsKey(rs.getString("ATTR_ID"))){
						if(categories.containsKey(rs.getString("CATALOG_ID"))){
							Category cat = categories.get(rs.getString("CATALOG_ID")).get(rs.getString("CATGROUP_ID"));
							if (!YFCCommon.isVoid(cat)){
								cat.addCategoryAttribute(new CategoryAttribute(rs, attributes.get(sOrganizationCode).get(rs.getString("ATTR_ID"))));
							}
						}
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
	
	private void loadProductAttributes (HashMap<String, HashMap<String, Attribute>> attributes, YFSEnvironment env){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT A.ATTR_ID, A.IDENTIFIER, A.ATTRTYPE_ID, A.ATTRUSAGE, A.SEQUENCE, D.NAME, D.DESCRIPTION, C.CATALOG_ID FROM ATTR A INNER JOIN ATTRDESC D ON D.ATTR_ID = A.ATTR_ID INNER JOIN CATGRPTPC C ON C.STORE_ID = A.STOREENT_ID WHERE LANGUAGE_ID = " + language;
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				String sOrganizationCode = getCatalogOrganization(env, getOrgForCatalog(rs.getString("CATALOG_ID")));
				if (!YFCCommon.isVoid(sOrganizationCode)){
					HashMap<String, Attribute> temp = attributes.get(sOrganizationCode);
					if (temp == null){
						temp = new HashMap<String, Attribute>();
						attributes.put(sOrganizationCode, temp);
					}
					Attribute newAtt = new Attribute(rs, sOrganizationCode, getSuffixForOrg(env, sOrganizationCode));
					boolean found = false;
					for(Attribute t : temp.values()){
						if(t.getAttributeID().equals(newAtt.getAttributeID())){
							found = true;
						}
					}
					if(!found){
						temp.put(rs.getString("ATTR_ID"), newAtt);
					}
					
				}
				
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
		
	private void loadAllowedValues(HashMap<String, HashMap<String, Attribute>> attributes, YFSEnvironment env){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT V.ATTR_ID, V.IDENTIFIER, V.VALUSAGE, D.SEQUENCE, D.VALUE, D.STRINGVALUE, D.INTEGERVALUE, D.FLOATVALUE, C.CATALOG_ID  FROM ATTRVAL V INNER JOIN ATTRVALDESC D ON D.ATTRVAL_ID = V.ATTRVAL_ID INNER JOIN CATGRPTPC C ON C.STORE_ID = V.STOREENT_ID WHERE D.LANGUAGE_ID = " + language;
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {		
				String sOrganizationCode = getCatalogOrganization(env, getOrgForCatalog(rs.getString("CATALOG_ID")));
				if (!YFCCommon.isVoid(sOrganizationCode)){
					Attribute temp = attributes.get(sOrganizationCode).get(rs.getString("ATTR_ID"));
					if (!YFCCommon.isVoid(temp)){
						temp.addAllowedValue(new AllowedValue(rs), temp.getDataType());
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
	private void getProductRecords (Map<String, Item> createdItems, YFSEnvironment env){
		HashMap<String, HashMap<String, String>> existing = getExistingItems();
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT C.BASEITEM_ID, C.CATENTRY_ID, C.ITEMSPC_ID, C.PARTNUMBER AS ITEM_ID, 'PROD' AS ITEM_GROUP_CODE, TRIM(CD.NAME) AS NAME, CD.SHORTDESCRIPTION, CD.LONGDESCRIPTION, "
					+ "C.MFNAME, C.MFPARTNUMBER, C.STARTDATE, C.ENDDATE, C.URL, CS.WEIGHT, CS.HEIGHT, CS.LENGTH, CS.WIDTH, CD.FULLIMAGE, CD.THUMBNAIL, 'EACH' AS UNIT_OF_MEASURE, TRIM(C.CATENTRY_ID) AS ITEM_KEY, "
					+ "CG.CATALOG_ID, m.REFERENCE as BUNDLE_CODE, m.CONFIGURATION, C.CATENTTYPE_ID "
					+ "FROM CATENTRY C	"
					+ "INNER JOIN CATENTDESC CD ON C.CATENTRY_ID = CD.CATENTRY_ID 	"
					+ "LEFT OUTER JOIN CATENTSHIP CS ON CS.CATENTRY_ID = C.CATENTRY_ID " 
					+ "INNER JOIN STOREENT SE ON SE.MEMBER_ID = C.MEMBER_ID "
					+ "INNER JOIN CATGRPTPC CG ON CG.STORE_ID = SE.STOREENT_ID "
					+ "LEFT JOIN CATCONFINF m ON m.CATENTRY_ID = C.CATENTRY_ID "
					+ "WHERE CD.LANGUAGE_ID = " + language + " AND C.MARKFORDELETE <> 1";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {	
				String sOrganizationCode = getCatalogOrganization(env, getOrgForCatalog(rs.getString("CATALOG_ID")));
				if (!YFCCommon.isVoid(sOrganizationCode)){
					if(YFCCommon.isVoid(existing.get(sOrganizationCode)) || !existing.get(sOrganizationCode).containsKey(rs.getString("ITEM_ID"))){
						Item temp = new Item(rs, sOrganizationCode, getSuffixForOrg(env, sOrganizationCode), null);
						createdItems.put(temp.getCommerceID(), temp);
					} else {
						Item temp = new Item(rs, sOrganizationCode, getSuffixForOrg(env, sOrganizationCode), existing.get(sOrganizationCode).get(rs.getString("ITEM_ID")));
						createdItems.put(temp.getCommerceID(), temp);
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
	private void updateModelRecords(Map<String, Item> items){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT C.CATENTRY_ID_CHILD AS CATENTRY_ID, C.CATENTRY_ID_PARENT AS BASEENTRY_ID FROM CATENTREL C ORDER BY C.CATENTRY_ID_PARENT";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			String sBaseNumber = "";
			String sPreviousChild = "";
			int i = 0;
			while ( rs.next() ) {	
				if (YFCCommon.equals(sBaseNumber, rs.getString("BASEENTRY_ID"))){
					i++;
				} else {
					if (i == 1){
						items.remove(sBaseNumber);
						if(!YFCCommon.isVoid(sPreviousChild) && !YFCCommon.isVoid(items.get(sPreviousChild))){
							items.get(sPreviousChild).setModelParent(null);
						}
					}
					i = 1;
					sBaseNumber = rs.getString("BASEENTRY_ID");
				}
				sPreviousChild = rs.getString("CATENTRY_ID");
				Item parent = items.get(rs.getString("BASEENTRY_ID"));
				if(!YFCCommon.isVoid(parent)){
					Item variation = items.get(rs.getString("CATENTRY_ID"));
					if (!YFCCommon.isVoid(variation)){
						variation.setModelParent(parent);	
					}
				}
			} 
			dbConn.close();
		}catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
		
		ArrayList<String> sRemoveKeys = new ArrayList<String>();
		for (Item i : items.values()){
			if (i.isModelParent() && !i.hasMultipleModels()){
				i.removeModelFromChild();
				sRemoveKeys.add(i.getCommerceID());
			}
		}
		
		for (String sKey : sRemoveKeys){
			items.remove(sKey);
		}
	}
	

	
	
	
	private void addAdditionalAttribute(Map<String, Item> items, HashMap<String, HashMap<String, Attribute>> attributes, YFSEnvironment env){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT CA.ATTR_ID, A.IDENTIFIER, C.CATENTRY_ID, CA.USAGE, C.PARTNUMBER, CA.ATTRVAL_ID, D.VALUE, D.STRINGVALUE, D.INTEGERVALUE, D.FLOATVALUE, CG.CATALOG_ID FROM CATENTRYATTR CA INNER JOIN CATENTRY C ON C.CATENTRY_ID = CA.CATENTRY_ID INNER JOIN ATTR A ON A.ATTR_ID = CA.ATTR_ID  INNER JOIN ATTRVAL V ON V.ATTRVAL_ID = CA.ATTRVAL_ID INNER JOIN ATTRVALDESC D ON D.ATTRVAL_ID = V.ATTRVAL_ID INNER JOIN STOREENT SE ON SE.MEMBER_ID = C.MEMBER_ID INNER JOIN CATGRPTPC CG ON CG.STORE_ID = SE.STOREENT_ID WHERE CA.USAGE = '1' AND D.LANGUAGE_ID = " + language;
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {	
				String sOrganizationCode = getCatalogOrganization(env, getOrgForCatalog(rs.getString("CATALOG_ID")));
				if (!YFCCommon.isVoid(sOrganizationCode)){
					Item item = items.get(rs.getString("CATENTRY_ID"));
					Attribute attr = attributes.get(sOrganizationCode).get(rs.getString("ATTR_ID"));
					if (!YFCCommon.isVoid(item) && !YFCCommon.isVoid(attr)){
						item.addAdditionalAttribute(new AdditionalAttribute(rs, attr));
					}
				}
				
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
	private HashMap<String, ArrayList<String>> loadCategories(HashMap<String, HashMap<String, Category>> categories, YFSEnvironment env){
		HashMap<String, ArrayList<String>> rootParent = new HashMap<String, ArrayList<String>>();
		String sCatalogs = "";
		int i = 0;
		for(String sCatalog : getOrgToCatalog().keySet()){
			if(!getOrgToCatalog().get(sCatalog).equals("Aurora-Corp")){
				if(i > 0){
					sCatalogs += ",";
				}
				sCatalogs += ("'" + sCatalog + "'");
				i++;
			}
		}
		try {
			Connection dbConn = getCommerceConnection();
			String sSql1 = "SELECT DISTINCT cp.CATGROUP_ID, cp.CATALOG_ID, G.IDENTIFIER FROM cattogrp cp INNER JOIN CATGROUP G ON cp.CATGROUP_ID = G.CATGROUP_ID WHERE cp.CATGROUP_ID > 12100 AND cp.CATALOG_ID = " + catalogId + " order by cp.CATALOG_ID";
			System.out.println(sSql1);
			PreparedStatement parents = dbConn.prepareStatement(sSql1);
			ResultSet r = parents.executeQuery();
			while (r.next()){
				
				if (!YFCCommon.isVoid(r.getString("CATALOG_ID"))){
					ArrayList<String> temp = rootParent.get(r.getString("CATALOG_ID"));
					if (temp == null){
						temp = new ArrayList<String>();
						rootParent.put(r.getString("CATALOG_ID"), temp);	
					}
					String sOrganizationCode = getOrgForCatalog(r.getString("CATALOG_ID"));
					HashMap<String, Category> parentCategories = null;
					if(categories.containsKey(r.getString("CATALOG_ID"))){
						parentCategories = categories.get(r.getString("CATALOG_ID"));
					} else {
						parentCategories = new HashMap<String, Category>();
						categories.put(r.getString("CATALOG_ID"), parentCategories);
					}
					parentCategories.put(r.getString("CATGROUP_ID"), new Category(r.getString("IDENTIFIER"), r.getString("CATGROUP_ID"), sOrganizationCode, r.getString("CATALOG_ID")));
					temp.add(r.getString("CATGROUP_ID"));
				}
			}
			//String sSql = "SELECT G1.IDENTIFIER AS PARENT_NAME, G1.CATGROUP_ID AS PARENT_ID, G2.IDENTIFIER AS CHILD_NAME, G2.CATGROUP_ID AS CHILD_ID, R.SEQUENCE, R.CATALOG_ID FROM CATGRPREL R INNER JOIN CATGROUP G1 ON R.CATGROUP_ID_PARENT = G1.CATGROUP_ID	INNER JOIN CATGROUP G2 ON R.CATGROUP_ID_CHILD = G2.CATGROUP_ID ORDER BY G1.CATGROUP_ID";
			String sSql = "SELECT G1.IDENTIFIER AS PARENT_NAME, G1.CATGROUP_ID AS PARENT_ID, G2.IDENTIFIER AS CHILD_NAME, G2.CATGROUP_ID AS CHILD_ID, R.SEQUENCE, R.CATALOG_ID FROM CATGRPREL R INNER JOIN CATGROUP G1 ON R.CATGROUP_ID_PARENT = G1.CATGROUP_ID	INNER JOIN CATGROUP G2 ON R.CATGROUP_ID_CHILD = G2.CATGROUP_ID WHERE CATALOG_ID = " + catalogId + " AND G2.CATGROUP_ID > 12000 ORDER BY G1.CATGROUP_ID";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				String sOrganizationCode = getOrgForCatalog(rs.getString("CATALOG_ID"));
				if (!YFCCommon.isVoid(sOrganizationCode) && !YFCCommon.isVoid(rs.getString("CATALOG_ID"))){
					if(YFCCommon.isVoid(categories.get(rs.getString("CATALOG_ID")))){
						categories.put(rs.getString("CATALOG_ID"), new HashMap<String, Category>());
					}
					if (!categories.get(rs.getString("CATALOG_ID")).containsKey(rs.getString("PARENT_ID"))){
						categories.get(rs.getString("CATALOG_ID")).put(rs.getString("PARENT_ID"), new Category(rs.getString("PARENT_NAME"), rs.getString("PARENT_ID"), sOrganizationCode,rs.getString("CATALOG_ID")));
					}
				/*	for (String sGroup : getDuplicateGroups(rs.getString("PARENT_ID"))){
						if (!identifierName.containsKey(sGroup)){
							identifierName.put(sGroup, rs.getString("PARENT_NAME") + "_" + sGroup.split("_")[1]);
						}
					}*/
					if (!categories.get(rs.getString("CATALOG_ID")).containsKey(rs.getString("CHILD_ID"))){
						categories.get(rs.getString("CATALOG_ID")).put(rs.getString("CHILD_ID"), new Category(rs.getString("CHILD_NAME"), rs.getString("CHILD_ID"), sOrganizationCode,rs.getString("CATALOG_ID")));
					}
					/*for (String sGroup : getDuplicateGroups(rs.getString("CHILD_ID"))){
						if (!identifierName.containsKey(sGroup)){
							identifierName.put(sGroup, rs.getString("CHILD_NAME") + "_" + sGroup.split("_")[1]);
						}
					}*/
					
				
					
					categories.get(rs.getString("CATALOG_ID")).get(rs.getString("CHILD_ID")).setParent(categories.get(rs.getString("CATALOG_ID")).get(rs.getString("PARENT_ID")));
					categories.get(rs.getString("CATALOG_ID")).get(rs.getString("PARENT_ID")).addChild(categories.get(rs.getString("CATALOG_ID")).get(rs.getString("CHILD_ID"))); 	
				} else {
					
					System.out.println("Missing Catalog: "+ rs.getString("CATALOG_ID") + " : " + rs.getString("PARENT_NAME") + " : " + rs.getString("CHILD_NAME"));
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
		return rootParent;
	}
	
	private void linkAssociationItems(Map<String, Item> items, YFSEnvironment env){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT MASSOCTYPE_ID, CATENTRY_ID_FROM, RANK, CATENTRY_ID_TO, QUANTITY FROM MASSOCCECE";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {	
				Item parent = items.get(rs.getString("CATENTRY_ID_FROM"));
				if (!YFCCommon.isVoid(parent)){
					Item child = items.get(rs.getString("CATENTRY_ID_TO"));
					if (!YFCCommon.isVoid(child)){
						parent.addAssociation(new Association(rs, child));
					}
				}
				
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		} finally {
			
		}
	}
	
	private void removeExistingRelationships(HashMap<String, HashMap<String, Category>> categories, Map<String, Item> items, YFSEnvironment env){
		try {
			Connection dbConn = getOMSConnection();
			String sSql = "SELECT TRIM(C.CATEGORY_ID) CATEGORY_ID, TRIM(C.ORGANIZATION_CODE) ORGANIZATION_CODE, TRIM(I.ITEM_ID) ITEM_ID, TRIM(C.CATEGORY_KEY) CATEGORY_KEY FROM " + this.getOMSDBSchema() + ".YFS_CATEGORY_ITEM CI INNER JOIN " + this.getOMSDBSchema() + ".YFS_CATEGORY C ON C.CATEGORY_KEY = CI.CATEGORY_KEY INNER JOIN " + this.getOMSDBSchema() + ".YFS_ITEM I ON CI.ITEM_KEY = I.ITEM_KEY";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				if (!YFCCommon.isVoid(rs.getString("ORGANIZATION_CODE"))){
					HashMap<String, Category> orgMap = categories.get(rs.getString("ORGANIZATION_CODE"));
					if (!YFCCommon.isVoid(orgMap)){
						if (rs.getString("CATEGORY_KEY").indexOf("-") > -1){
							String sCommerceID = rs.getString("CATEGORY_KEY").split("-")[1].split("_")[0];
							Category category = orgMap.get(sCommerceID);
							if (!YFCCommon.isVoid(category)){
								String sKey = rs.getString("ITEM_KEY");
								try {
									sKey = rs.getString("ITEM_KEY").split("-")[1];	
								} catch (Exception e){
									e.printStackTrace();
								}
								Item item = items.get(sKey);
								if (!YFCCommon.isVoid(item)){
									category.removeItem(item);
								}	
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
	private HashMap<String, ArrayList<String>> getExistingCategoryMap(){
		HashMap<String, ArrayList<String>> t = new HashMap<String, ArrayList<String>>();
		try {
			Connection dbConn = getOMSConnection();
			
			String sSql = "Select TRIM(CATEGORY_KEY) CATEGORY_KEY, TRIM(ITEM_KEY) ITEM_KEY FROM OMDB.YFS_CATEGORY_ITEM ORDER BY CATEGORY_KEY, ITEM_KEY";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				ArrayList<String> list = t.get(rs.getString("CATEGORY_KEY"));
				if(YFCCommon.isVoid(list)){
					list = new ArrayList<String>();
					t.put(rs.getString("CATEGORY_KEY"), list);
				}
				list.add(rs.getString("ITEM_KEY"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	private void linkCategoryItems(HashMap<String, HashMap<String, Category>> categories, Map<String, Item> items, YFSEnvironment env){
		try {
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT DISTINCT R.CATGROUP_ID, G.IDENTIFIER, C.CATENTRY_ID, C.PARTNUMBER, CA.ATTR_ID, R.CATALOG_ID, CA.USAGE FROM CATGPENREL R INNER JOIN CATGROUP G ON G.CATGROUP_ID = R.CATGROUP_ID INNER JOIN CATENTRY C ON C.CATENTRY_ID = R.CATENTRY_ID LEFT JOIN CATENTRYATTR CA ON CA.CATENTRY_ID = C.CATENTRY_ID ORDER BY R.CATGROUP_ID, C.CATENTRY_ID";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {	
				String sOrganizationCode = getCatalogOrganization(env, getOrgForCatalog(rs.getString("CATALOG_ID")));
				if (!YFCCommon.isVoid(rs.getString("CATALOG_ID")) && categories.containsKey(rs.getString("CATALOG_ID"))){
					Category category = categories.get(rs.getString("CATALOG_ID")).get(rs.getString("CATGROUP_ID"));
					if(!YFCCommon.isVoid(category)){
						Item item = items.get(rs.getString("CATENTRY_ID"));
						if (!YFCCommon.isVoid(item)){
							if(!YFCCommon.isVoid(rs.getObject("USAGE")) && rs.getInt("USAGE") == 1){
								item.setItemType(category.getCategoryID());
							}
							category.addItem(item);
						}
					} else {
						Item item = items.get(rs.getString("CATENTRY_ID"));
						if(item != null && !item.isExistingItem()){
							//Category.categoryExists(rs.getString("CATGROUP_ID"), sServer)
							System.out.println("Missing Category: " + rs.getString("CATGROUP_ID") + " from Catalog: " + rs.getString("CATALOG_ID") + " : " + rs.getString("IDENTIFIER") + " for Item: " + rs.getString("PARTNUMBER"));
						}
					}
				}
			}
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
}
