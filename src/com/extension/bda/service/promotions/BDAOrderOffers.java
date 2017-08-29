package com.extension.bda.service.promotions;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;


public class BDAOrderOffers {
	
	private static List<String> VALID_TYPES = Arrays.asList(new String[]{"CrossSell", "UpSell", "Competitive.Y", "Alternative.Y"});
	
	public static void main(String[] args){
		
	}
	public Document getOrderOffers(YFSEnvironment env, Document inputDoc) {
		YFCDocument dOutput = YFCDocument.createDocument("Offers");
		YFCElement eOffers = dOutput.getDocumentElement();
		YFCElement ePromotions = eOffers.createChild("Promotions");
		createPromotion(ePromotions, "Free shipping on orders over $100", "FREE_SHIP");
		createPromotion(ePromotions, "Beach sale, save 10% on orders over $150", "BEACH_SALE");
		createPromotion(ePromotions, "Buy one dress, get a pair of shoes 50% off", "Dress_50_Off_Shoes");
		
		YFCElement eProducts = eOffers.createChild("Products");
		YFCElement eOrder = getOrderItems(env, inputDoc);
		if(!YFCCommon.isVoid(eOrder)){
			addRelatedItems(env, eOrder, eProducts);
			for(YFCElement eAward : eOrder.getChildElement("Awards", true).getChildren()){
				for (YFCElement ePromotion : ePromotions.getChildren()){
					if (YFCCommon.equals(ePromotion.getAttribute("PricingRuleName"), eAward.getAttribute("AwardId"))){
						ePromotions.removeChild(ePromotion);
					}
				}
			}
			
			for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
				for(YFCElement eAward : eOrderLine.getChildElement("Awards", true).getChildren()){
					for (YFCElement ePromotion : ePromotions.getChildren()){
						if (YFCCommon.equals(ePromotion.getAttribute("PricingRuleName"), eAward.getAttribute("AwardId"))){
							ePromotions.removeChild(ePromotion);
						}
					}
				}
			}		
		}
		return dOutput.getDocument();
	}

	private void createPromotion(YFCElement ePromotions, String sDescription){
		YFCElement ePromotion = ePromotions.createChild("Promotion");
		ePromotion.setAttribute("Description", sDescription);
	}
	
	private void createPromotion(YFCElement ePromotions, String sDescription, String sKey){
		YFCElement ePromotion = ePromotions.createChild("Promotion");
		ePromotion.setAttribute("Description", sDescription);
		ePromotion.setAttribute("PricingRuleName", sKey);
	}
	
	private Document getItemListForOrderingInput(YFCElement eOrder){
		YFCDocument dInput = YFCDocument.createDocument("Item");
		YFCElement eItem = dInput.getDocumentElement();
		eItem.setAttribute("CallingOrganizationCode", eOrder.getAttribute("SellerOrganizationCode"));
		eItem.setAttribute("SellerOrganizationCode", eOrder.getAttribute("SellerOrganizationCode"));
		eItem.setAttribute("IsForOrdering", "Y");
		eItem.setAttribute("ItemGroupCode", "PROD");
		eItem.setAttribute("GetAvailabilityFromCache", "Y");
		eItem.setAttribute("Currency", eOrder.getAttribute("Currency"));
		if(!YFCCommon.equals(eOrder.getAttribute("EnterpriseCode"), eOrder.getAttribute("SellerOrganizationCode"))){
			YFCElement eShipNode = eItem.createChild("ShipNodes").createChild("ShipNode");
			eShipNode.setAttribute("Node", eOrder.getAttribute("SellerOrganizationCode"));
		}
		
		if(eOrder.getChildElement("OrderLines", true).getChildren().getTotalCount() == 0){
			return null;
		} else if(eOrder.getChildElement("OrderLines", true).getChildren().getTotalCount() == 1){
			eItem.setAttribute("ItemID", eOrder.getChildElement("OrderLines").getChildElement("OrderLine").getChildElement("Item").getAttribute("ItemID"));
		} else {
			YFCElement eComplexQuery = eItem.createChild("ComplexQuery");
			YFCElement eOr = eComplexQuery.createChild("Or");
			for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "ItemID");
				eExp.setAttribute("Value", eOrderLine.getChildElement("Item").getAttribute("ItemID"));
			}
		}
		return dInput.getDocument();
	}
	
	
	
	private YFCElement getOrderItems(YFSEnvironment env, Document inDoc){
		YIFApi localApi;
        Document dOrderOutput = null;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("getCompleteOrderDetails", getOrderDetailsTemplate());
			dOrderOutput = localApi.invoke(env, "getCompleteOrderDetails", inDoc);
		} catch (YIFClientCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (YFSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!YFCCommon.isVoid(dOrderOutput)){
			return YFCDocument.getDocumentFor(dOrderOutput).getDocumentElement();
		}
		return null;
	}
	
	private Document getOrderDetailsTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOutput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		YFCElement ePromotion = eOrder.createChild("Awards").createChild("Award");
		ePromotion.setAttribute("AwardId", "");
		ePromotion.setAttribute("Description", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("OrderLineKey", "");
		ePromotion = eOrderLine.createChild("Awards").createChild("Award");
		ePromotion.setAttribute("AwardId", "");
		ePromotion.setAttribute("Description", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		YFCElement eLinePriceInfo = eOrderLine.createChild("ComputedPrice");
		eLinePriceInfo.setAttribute("ExtendedPrice", "");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("Currency", "");
		ePriceInfo.setAttribute("TotalAmount", "");
		return dOutput.getDocument();
	}
	
	
	private Document getItemListForOrderingTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("ItemList");
		YFCElement eItemList = dOutput.getDocumentElement();
		YFCElement eItem = eItemList.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("ItemGroupCode", "");
	
		YFCElement eAssociationType = eItem.createChild("AssociationTypeList").createChild("AssociationType");
		eAssociationType.setAttribute("Type", "");
		eAssociationType.setAttribute("CodeShortDescription", "");
		eAssociationType.setAttribute("CodeLongDescription", "");
		YFCElement eAssociation = eAssociationType.createChild("AssociationList").createChild("Association");
		eAssociation.setAttribute("Priority", "");
		eAssociation.setAttribute("AssociatedQuantity", "");
		eAssociation.setAttribute("AssociationType", "");
		eAssociation.setAttribute("EffectiveFrom", "");
		eAssociation.setAttribute("EffectiveTo", "");
		YFCElement eAItem = eAssociation.createChild("Item");
		eAItem.setAttribute("ItemID", "");
		eAItem.setAttribute("UnitOfMeasure", "");
		YFCElement ePInfo = eAItem.createChild("PrimaryInformation");
		ePInfo.setAttribute("ImageID", "");
		ePInfo.setAttribute("ImageLocation", "");
		ePInfo.setAttribute("ImageLabel", "");
		ePInfo.setAttribute("IsShippingAllowed", "");
		ePInfo.setAttribute("IsPickupAllowed", "");
		ePInfo.setAttribute("IsValid", "");
		ePInfo.setAttribute("ShortDescription", "");
		YFCElement eCPrice = eAItem.createChild("ComputedPrice");
		eCPrice.setAttribute("ListPrice", "");
		eCPrice.setAttribute("UnitPrice", "");
		eCPrice.setAttribute("RetailPrice", "");
		YFCElement eAvailablity = eAItem.createChild("Availability");
		eAvailablity.setAttribute("OnhandAvailableQuantity","");
		return dOutput.getDocument();
	}
	
	public static  LinkedList<YFCElement> sortByWeight( Collection<YFCElement> collection ) {
		LinkedList<YFCElement> list = new LinkedList<>( collection );
	    Collections.sort(list, new Comparator<YFCElement>()
	    {
	        @Override
	        public int compare( YFCElement o1, YFCElement o2 )
	        {
	            return new Double(o1.getDoubleAttribute("Weight")).compareTo(o2.getDoubleAttribute("Weight"));
	        }
	    } );
	
	   
	    return list;
	}
	
	private void addRelatedItems(YFSEnvironment env, YFCElement eOrder, YFCElement eProducts){
		YIFApi localApi;
		Document inDoc = getItemListForOrderingInput(eOrder);
		Document dItemListOutput = null;
		HashMap<String, YFCElement> relatedProducts = new HashMap<String, YFCElement>();
		if(!YFCCommon.isVoid(inDoc)){
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				env.setApiTemplate("getItemListForOrdering", getItemListForOrderingTemplate());
				dItemListOutput = localApi.invoke(env, "getItemListForOrdering", inDoc);
				if(!YFCCommon.isVoid(dItemListOutput)){
					YFCElement eItemList = YFCDocument.getDocumentFor(dItemListOutput).getDocumentElement();
					for(YFCElement eItem : eItemList.getChildren()){
						for(YFCElement eAssociationType : eItem.getChildElement("AssociationTypeList", true).getChildren()){
							if(VALID_TYPES.contains(eAssociationType.getAttribute("Type"))){
								for(YFCElement eAssociation : eAssociationType.getChildElement("AssociationList", true).getChildren()){
									double weight = (VALID_TYPES.indexOf(eAssociationType.getAttribute("Type")) * 100) + eAssociation.getDoubleAttribute("Priority", 10);
									eAssociation.setAttribute("Weight", weight);
									
									if(eAssociation.getDateAttribute("EffectiveFrom").before(YDate.newDate()) && eAssociation.getDateAttribute("EffectiveTo").after(YDate.newDate())){
										relatedProducts.put(eAssociation.getChildElement("Item").getAttribute("ItemID"), eAssociation);
									}
									
									
								}	
							}							
						}
					}
					for(YFCElement eItem : eItemList.getChildren()){
						Iterator <Map.Entry<String, YFCElement>> iter = relatedProducts.entrySet().iterator();
						while(iter.hasNext()){
							Map.Entry<String, YFCElement> entry = iter.next();
							if(eItem.getAttribute("ItemID").contains(entry.getKey())){
								iter.remove();
							}
						}
					}
					
					LinkedList<YFCElement> products = sortByWeight(relatedProducts.values());
					int count = 0;
					for(YFCElement eProduct : products){
						eProducts.importNode(eProduct.getChildElement("Item"));
						count++;
						
						if(count > 5){
							break;
						}
					}
				}
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
