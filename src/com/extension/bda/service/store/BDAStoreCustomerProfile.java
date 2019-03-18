/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Sterling Selling and Fulfillment Suite - Foundation, IBM Order Management (5737-D18)
 * (C) Copyright IBM Corp. 2017 All Rights Reserved. 
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/
package com.extension.bda.service.store;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
public class BDAStoreCustomerProfile extends BDAServiceApi implements IBDAService {
	
	private static List<String> VALID_TYPES = Arrays.asList(new String[]{"CrossSell", "UpSell"});

	@Override
	public void setProperties(Properties arg0)  {
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		// TODO Auto-generated method stub
		return getRecommendedItemList(env, input);
	}
	
	private YFCDocument getCompleteItemListInput(YFCElement eInput, YFCElement eItemList){
		YFCDocument dInput = YFCDocument.createDocument("Item");
		YFCElement eItem = dInput.getDocumentElement();
		eItem.setAttribute("CallingOrganizationCode", eInput.getAttribute("CallingOrganizationCode"));
		eItem.setAttribute("DisplayLocalizedFieldInLocale", eInput.getAttribute("DisplayLocalizedFieldInLocale"));
		eItem.setAttribute("IsForOrdering", "Y");
		eItem.setAttribute("GetAvailabilityFromCache", "Y");
		eItem.setAttribute("IgnoreInvalidItems", "Y");
		eItem.setAttribute("ItemGroupCode", "PROD");
		eItem.setAttribute("SellerOrganizationCode", eInput.getAttribute("SellerOrganizationCode"));
		eItem.getChildElement("ItemAssociationTypeList", true).createChild("ItemAssociationType").setAttribute("Type", "CrossSell");
		eItem.getChildElement("ItemAssociationTypeList", true).createChild("ItemAssociationType").setAttribute("Type", "UpSell");
		if(eItemList.getChildren().getTotalCount() == 0){
			return null;
		} else if(eItemList.getChildren().getTotalCount() == 1){
			eItem.setAttribute("ItemID", eItemList.getChildElement("Item").getAttribute("ItemID"));
		} else {
			YFCElement eComplexQuery = eItem.createChild("ComplexQuery");
			YFCElement eOr = eComplexQuery.createChild("Or");
			for(YFCElement tItem : eItemList.getChildren()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "ItemID");
				eExp.setAttribute("Value", tItem.getAttribute("ItemID"));
			}
		}
		return dInput;
	}
	
	public Document getRecommendedItemList(YFSEnvironment env,Document inDoc){
		YFCDocument dOutput = YFCDocument.createDocument("RecommendedItemList");
		YFCElement eRecommendedItemList = dOutput.getDocumentElement();
		eRecommendedItemList.setAttribute("TotalItemList", "0");
		eRecommendedItemList.setAttribute("TotalNumberOfRecords", "0");
		if(!YFCCommon.isVoid(inDoc)) {
			YFCDocument dInput = YFCDocument.getDocumentFor(inDoc);
			YFCElement eInput = dInput.getDocumentElement();
			
			if(!YFCCommon.isVoid(eInput.getChildElement("ItemList")) && !YFCCommon.isVoid(eInput.getChildElement("ItemList").getChildElement("Item"))) {
				YFCDocument dGetItemListInput = getCompleteItemListInput(eInput, eInput.getChildElement("ItemList"));
				Document response = callApi(env, dGetItemListInput.getDocument(), this.getItemListForOrderingTemplate(), "getItemListForOrdering");
				int maxRecords = eInput.getIntAttribute("MaximumRecords", 6);
				
				processRelatedItems(response, eRecommendedItemList, maxRecords);
			}			
		}
		return dOutput.getDocument();
	}

	private Document getItemListForOrderingTemplate(){
		String template = "<ItemList TotalItemList=\"\" TotalNumberOfRecords=\"\">\n    <Item ItemID=\"\" UnitOfMeasure=\"\" ItemGroupCode=\"\">\n        <AssociationTypeList>\n            <AssociationType Type=\"\">\n                <AssociationList>\n                    <Association AssociatedKey=\"\" AssociatedQuantity=\"\" AssociationGroup=\"\" AssociationType=\"\" EffectiveFrom=\"\" EffectiveTo=\"\" Priority=\"\">\n                        <Item ItemID=\"\" UnitOfMeasure=\"\" OrganizationCode=\"\">\n                        </Item>\n                    </Association>\n                </AssociationList>\n            </AssociationType>\n        </AssociationTypeList>\n    </Item >\n</ItemList>";
		return YFCDocument.getDocumentFor(template).getDocument();
	}
	
	private void processRelatedItems(Document dItemListOutput, YFCElement eRecommendedItemList, int maxRecords) {
		HashMap<String, YFCElement> relatedProducts = new HashMap<String, YFCElement>();
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
			
			//Remove the items that are present in the input
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
				eRecommendedItemList.importNode(eProduct.getChildElement("Item"));
				count++;
				
				if(count > maxRecords-1){
					break;
				}
			}
			eRecommendedItemList.setAttribute("TotalItemList", count);
			eRecommendedItemList.setAttribute("TotalNumberOfRecords", count);
		}
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

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getStoreRecommendations";
	}

	
	
}
