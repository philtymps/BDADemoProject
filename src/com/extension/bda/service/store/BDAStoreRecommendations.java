package com.extension.bda.service.store;

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
import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.pca.bridge.YCDFoundationBridge;
import com.yantra.shared.plt.PLTSharedErrors;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;


public class BDAStoreRecommendations implements YIFCustomApi {

	private static List<String> VALID_TYPES = Arrays.asList(new String[]{"CrossSell", "UpSell"});
    //"CrossSell", "UpSell", "Competitive.Y", "Alternative.Y"
	@Override
	public void setProperties(Properties arg0) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public Document getRecommendedItemList(YFSEnvironment env,Document inDoc){
		//Validating the inDoc  
		if(YFCObject.isVoid(inDoc)){
			throw new YFCException(PLTSharedErrors.YFS_BLANK_XML_DATA);
		}
		YFSContext ctx = (YFSContext) env;
		YFCDocument itemListTemplate = YCDFoundationBridge.getInstance().getAPITemplate(ctx, "getRecommendedItemListFromAssociation", null, null);		
		
		YIFApi localApi;
		Document inDocForApi = getItemListForOrderingInput(YFCDocument.getDocumentFor(inDoc).getDocumentElement());
		Document dItemListOutput = null;
		//HashMap<String, YFCElement> relatedProducts = new HashMap<String, YFCElement>();
		int maxRecords = Integer.parseInt(inDoc.getDocumentElement().getAttribute("MaximumRecords"));
		YFCDocument dOutput = YFCDocument.createDocument("RecommendedItemList");
		YFCElement eRecommendedItemList = dOutput.getDocumentElement();
		eRecommendedItemList.setAttribute("TotalItemList", "0");
		eRecommendedItemList.setAttribute("TotalNumberOfRecords", "0");
		
		if(!YFCCommon.isVoid(inDocForApi)){
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(itemListTemplate)){
					env.setApiTemplate("getItemListForOrdering", itemListTemplate.getDocument());
				}
				else{
					env.setApiTemplate("getItemListForOrdering", getItemListForOrderingemplate());
				}
				dItemListOutput = localApi.invoke(env, "getItemListForOrdering", inDocForApi);
				
				processRelatedItems(dItemListOutput,eRecommendedItemList,maxRecords);
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
		
		return dOutput.getDocument();
		
		
	}
	
	
	private Document getItemListForOrderingInput(YFCElement recommendedItemsInput){
		YFCDocument dInput = YFCDocument.createDocument("Item");
		YFCElement eItem = dInput.getDocumentElement();
		eItem.setAttribute("CallingOrganizationCode", recommendedItemsInput.getAttribute("CallingOrganizationCode"));
		eItem.setAttribute("SellerOrganizationCode", recommendedItemsInput.getAttribute("SellerOrganizationCode"));
		eItem.setAttribute("IsForOrdering", "Y");
		eItem.setAttribute("ItemGroupCode", "PROD");
		eItem.setAttribute("GetAvailabilityFromCache", "Y");
		eItem.setAttribute("Currency", recommendedItemsInput.getAttribute("Currency"));
		if(!YFCCommon.equals(recommendedItemsInput.getAttribute("EnterpriseCode"), recommendedItemsInput.getAttribute("SellerOrganizationCode"))){
			YFCElement eShipNode = eItem.createChild("ShipNodes").createChild("ShipNode");
			eShipNode.setAttribute("Node", recommendedItemsInput.getAttribute("SellerOrganizationCode"));
		}
		
		if(recommendedItemsInput.getChildElement("ItemList", true).getChildren().getTotalCount() == 0){
			return null;
		} else if(recommendedItemsInput.getChildElement("ItemList", true).getChildren().getTotalCount() == 1){
			eItem.setAttribute("ItemID", recommendedItemsInput.getChildElement("ItemList").getChildElement("Item").getAttribute("ItemID"));
		} else {
			YFCElement eComplexQuery = eItem.createChild("ComplexQuery");
			YFCElement eOr = eComplexQuery.createChild("Or");
			for(YFCElement tItem : recommendedItemsInput.getChildElement("ItemList", true).getChildren()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "ItemID");
				eExp.setAttribute("Value", tItem.getAttribute("ItemID"));
			}
		}
		return dInput.getDocument();
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
	
	private Document getItemListForOrderingemplate(){
		String template = "<ItemList TotalItemList=\"\" TotalNumberOfRecords=\"\">\n    <Item ItemID=\"\" UnitOfMeasure=\"\" ItemGroupCode=\"\">\n        <AssociationTypeList>\n            <AssociationType Type=\"\">\n                <AssociationList>\n                    <Association AssociatedKey=\"\" AssociatedQuantity=\"\" AssociationGroup=\"\" AssociationType=\"\" EffectiveFrom=\"\" EffectiveTo=\"\" Priority=\"\">\n                        <Item ItemID=\"\" UnitOfMeasure=\"\" OrganizationCode=\"\">\n                        </Item>\n                    </Association>\n                </AssociationList>\n            </AssociationType>\n        </AssociationTypeList>\n    </Item >\n</ItemList>";
		return YFCDocument.getDocumentFor(template).getDocument();
	}
}
