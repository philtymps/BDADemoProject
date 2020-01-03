package com.dte.condition;
import java.util.Map;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
public class DTEItemTypeCheck implements YCPDynamicConditionEx  {

	private Map properties = null;
	
	private static Document getTemplate(String primaryAttribute) {
		YFCDocument dItemList = YFCDocument.createDocument("ItemList");
		YFCElement eItem = dItemList.getDocumentElement().createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement ePrimaryInfo = eItem.createChild("PrimaryInformation");
		ePrimaryInfo.setAttribute(primaryAttribute, "");
		return dItemList.getDocument();
	}
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		
		/*if(!YFCCommon.isVoid(doc)) {
			System.out.println("Input Condition");
			System.out.println(YFCDocument.getDocumentFor(doc));
		}

		if(!YFCCommon.isVoid(mapData)) {
			System.out.println("Map Data");
			for(Object key : mapData.keySet()) {
				System.out.println(key + " :: " + mapData.get(key));
			}
		}

		if(!YFCCommon.isVoid(properties)) {
			System.out.println("Properties");
			for(Object key : properties.keySet()) {
				System.out.println(key + " :: " + properties.get(key));
			}
		}*/

		if(mapData.containsKey("ItemID") && !YFCCommon.isVoid(mapData.get("ItemID"))){
			YFCDocument dItemInput = YFCDocument.createDocument("Item");
			YFCElement eItemInput = dItemInput.getDocumentElement();
			eItemInput.setAttribute("ItemID", (String) mapData.get("ItemID"));
			eItemInput.setAttribute("CallingOrganizationCode", (String) mapData.get("SellerOrganizationCode"));
			
			if(!YFCCommon.isVoid(properties) && properties.containsKey("ItemAttribute") && properties.containsKey("AttributeValue")) {
				Document dItemResponse = BDAServiceApi.callApi(env, dItemInput.getDocument(), getTemplate((String) properties.get("ItemAttribute")), "getItemList");
				YFCElement eItemList = YFCDocument.getDocumentFor(dItemResponse).getDocumentElement();
				YFCElement eItem = eItemList.getChildElement("Item");
				if(!YFCCommon.isVoid(eItem)) {
					YFCElement ePrimaryInfo = eItem.getChildElement("PrimaryInformation");
					if(!YFCCommon.isVoid(ePrimaryInfo)) {
						String[] values = ((String)properties.get("AttributeValue")).split(",");
						for(String value : values) {
							if(YFCCommon.equalsIgnoreCase(ePrimaryInfo.getAttribute("ItemAttribute"), value.trim())) {
								return true;
							}
						}
					}
				}
			} 
		}			
		return false;
	}

	@Override
	public void setProperties(Map map) {
		properties = map;
	}

}
