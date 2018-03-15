package com.mirakl.shop.extract;

import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class BDAGetProductForOffer {

	public static void main(String[] args){
		new BDAGetProductForOffer();
	}
	
	private String getURL(String sRestPath){
		if(!sRestPath.startsWith("/")){
			sRestPath = "/" + sRestPath;
		}
		if(getDomain().startsWith("http")){
			return getDomain() + sRestPath;
		}
		return "https://" + getDomain() + sRestPath;
	}
	
	public String getShopApiKey() {
		
		return "44cfe66f-6ef9-4091-b76b-c777a94b64a1";
	}
	
	public String getOperatorApiKey() {
		
		return "e67db8ef-0387-4bb3-9903-c2b323e78e19";
	}
	
	private String getDomain(){
		return "ibm-dev.mirakl.net";
	}
	
	private String getShopID(){
		return "4";
	}
	
	private YFCDocument dPetItems;
	
	private HashMap<String, String> idToOffer;
	

	public BDAGetProductForOffer(){
		idToOffer = new HashMap<String, String>();
		String sProductFile = "/Users/pfaiola/Box Sync/Projects/Mirakl/SS_USA/3-manageItem.xml";
		File products = new File(sProductFile);
		if(products.exists()){
			dPetItems = YFCDocument.getDocumentFor(products);
		}
		YFCDocument dExport = YFCDocument.createDocument("ItemList");
		YFCElement eItemList = dExport.getDocumentElement();
		processProducts(eItemList);
		
		MiraklUtils.writeXML("/Users/pfaiola/Box Sync/Projects/Mirakl/Shop/API/", "manageItem.xml", dExport);
		
		String categoryItems = "/Users/pfaiola/Box Sync/Projects/Mirakl/SS_USA/4-modifyCategoryItem.xml";
		File ci = new File(categoryItems);
		if(ci.exists()){
			YFCDocument dCategoryItems = YFCDocument.getDocumentFor(ci);
			YFCElement eParent = dCategoryItems.getDocumentElement();
			for(YFCElement eCategory : eParent.getChildren()){
				for(YFCElement eCategoryItem : eCategory.getChildElement("CategoryItemList", true).getChildren()){
					if(idToOffer.containsKey(eCategoryItem.getAttribute("ItemID"))){
						eCategoryItem.setAttribute("ItemID", idToOffer.get(eCategoryItem.getAttribute("ItemID")));
					}
				}
			}
			MiraklUtils.writeXML("/Users/pfaiola/Box Sync/Projects/Mirakl/Shop/API/", "modifyCategoryItem.xml", dCategoryItems);
			
		}
		
		String pricelist = "/Users/pfaiola/Box Sync/Projects/Mirakl/SS_USA/6-managePricelistLine.xml";
		File pl = new File(pricelist);
		if(pl.exists()){
			YFCDocument dCategoryItems = YFCDocument.getDocumentFor(pl);
			YFCElement eParent = dCategoryItems.getDocumentElement();
		
			for(YFCElement ePricelistLine : eParent.getChildren()){
				if(idToOffer.containsKey(ePricelistLine.getAttribute("ItemID"))){
					ePricelistLine.setAttribute("ItemID", idToOffer.get(ePricelistLine.getAttribute("ItemID")));
				}
			}
			MiraklUtils.writeXML("/Users/pfaiola/Box Sync/Projects/Mirakl/Shop/API/", "managePricelistLine.xml", dCategoryItems);
		}
		
		String inventory = "/Users/pfaiola/Box Sync/Projects/Mirakl/SS_USA/7-adjustInventory.xml";
		File inv = new File(inventory);
		if(inv.exists()){
			YFCDocument dCategoryItems = YFCDocument.getDocumentFor(inv);
			YFCElement eParent = dCategoryItems.getDocumentElement();
		
			for(YFCElement ePricelistLine : eParent.getChildren()){
				if(idToOffer.containsKey(ePricelistLine.getAttribute("ItemID"))){
					ePricelistLine.setAttribute("ItemID", idToOffer.get(ePricelistLine.getAttribute("ItemID")));
				}
			}
			MiraklUtils.writeXML("/Users/pfaiola/Box Sync/Projects/Mirakl/Shop/API/", "adjustInventory.xml", dCategoryItems);
		}
		
		
	}
	
	public void processProducts(YFCElement eItems){
		YFCDocument dProducts = getAllProducts();
		if(!YFCCommon.isVoid(dProducts)){
			for(YFCElement eProduct : dProducts.getDocumentElement().getChildElement("products", true).getChildren()){
				for(YFCElement eShop : eProduct.getChildElement("shop_skus", true).getChildren()){
					if(YFCCommon.equals(eShop.getChildElement("shop_id", true).getNodeValue(), getShopID())){
						processProduct(eProduct, eItems);					
					}
				}
			}
		}
	}
	
	private void processProduct(YFCElement eProduct, YFCElement eItems){
		YFCElement eItem = eItems.createChild("Item");
		eItem.setAttribute("ItemID", eProduct.getChildElement("sku", true).getNodeValue());
		eItem.setAttribute("UnitOfMeasure", "EACH");
		eItem.setAttribute("OrganizationCode", "SS_USA");
		eItem.setAttribute("ItemGroupCode", "PROD");
		
		YFCElement ePriInfo = eItem.createChild("PrimaryInformation");
		ePriInfo.setAttribute("ShortDescription", eProduct.getChildElement("title", true).getNodeValue());
		
		ePriInfo.setAttribute("Description", eProduct.getChildElement("title", true).getNodeValue());
		ePriInfo.setAttribute("ExtendedDescription", eProduct.getChildElement("description", true).getNodeValue());
		ePriInfo.setAttribute("AssumeInfiniteInventory", "N");
		ePriInfo.setAttribute("IsDeliveryAllowed", "N");
		ePriInfo.setAttribute("IsModelItem", "N");
		ePriInfo.setAttribute("IsPickupAllowed", "Y");
		ePriInfo.setAttribute("TaxableFlag", "Y");
		
		YFCElement eClassification = eItem.createChild("ClassificationCodes");
		eClassification.setAttribute("StorageType", "Standard");
		
		if(YFCCommon.equals(eProduct.getChildElement("active", true), "false")){
			ePriInfo.setAttribute("Status", "2000");
		} else {
			ePriInfo.setAttribute("Status", "3000");
		}
		
		YFCDocument dOffers = getOfferDetails(eItem.getAttribute("ItemID"));
		YFCElement eOffer = dOffers.getDocumentElement().getChildElement("products", true).getChildElement("product", true).getChildElement("offers", true);
		YFCElement ePetItem = getPETItem("MKO_" + eOffer.getChildElement("offer_id").getNodeValue());
		idToOffer.put("MKO_" + eOffer.getChildElement("offer_id").getNodeValue(), eItem.getAttribute("ItemID"));
		if(!YFCCommon.isVoid(ePetItem)){
			
			YFCElement ePetPri = ePetItem.getChildElement("PrimaryInformation");
			ePriInfo.setAttribute("ImageLocation", ePetPri.getAttribute("ImageLocation"));
			ePriInfo.setAttribute("ImageID", ePetPri.getAttribute("ImageID"));
			eItem.importNode(ePetItem.getChildElement("AssetList"));
			
			for(YFCElement eAttribute : ePetItem.getChildElement("AdditionalAttributeList", true).getChildren()){
				if(eAttribute.getAttribute("Name").equals("mmOfferStates")){
					if(!eAttribute.getAttribute("Value").equals("New")){
						ePriInfo.setAttribute("DefaultProductClass", eAttribute.getAttribute("Value"));
					}
				} else {
					eItem.getChildElement("AdditionalAttributeList", true).importNode(eAttribute);
				}
			}
		}		
	}
	
	private YFCElement getPETItem(String sItemID){
		if(!YFCCommon.isVoid(dPetItems)){
			for(YFCElement eItem : dPetItems.getDocumentElement().getChildren()){
				if(eItem.getAttribute("ItemID").equals(sItemID)){
					return eItem;
				}
			}
		}
		return null;
	}
	
	private YFCDocument getOfferDetails(String sProduct_ID){
		String sProductFile = "/Users/pfaiola/Box Sync/Projects/Mirakl/Shop/product_" + sProduct_ID + ".xml";
		File products = new File(sProductFile);
		YFCDocument dProducts = null;
		if(products.exists()){
			dProducts = YFCDocument.getDocumentFor(products);
		} else {
			URL url;
			try {
			
				url = new URL(getURL("/api/products/offers?product_ids=" + sProduct_ID));
				System.out.println("Calling: " + url.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", getOperatorApiKey());
				conn.setRequestProperty("Accept", "application/xml");
				
				if(conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
				}
				
				dProducts = YFCDocument.parse(conn.getInputStream());
				conn.disconnect();
				FileWriter sw = new FileWriter(sProductFile);
				dProducts.serialize(sw);
				sw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dProducts;
	}
	
	private YFCDocument getAllProducts(){
		String sProductFile = "/Users/pfaiola/Box Sync/Projects/Mirakl/Shop/products.xml";
		File products = new File(sProductFile);
		YFCDocument dProducts = null;
		if(products.exists()){
			dProducts = YFCDocument.getDocumentFor(products);
		} else {
			URL url;
			try {
			
				url = new URL(getURL("/api/products/export"));
				System.out.println("Calling: " + url.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", getOperatorApiKey());
				conn.setRequestProperty("Accept", "application/xml");
				
				if(conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
				}
				
				dProducts = YFCDocument.parse(conn.getInputStream());
				conn.disconnect();
				FileWriter sw = new FileWriter(sProductFile);
				dProducts.serialize(sw);
				sw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dProducts;
	}
	
	
}

