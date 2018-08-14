package com.extension.conditions;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAOrderLineItemAttributeValue implements YCPDynamicConditionEx {

	private Map props = null;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YFCDocument dInput = YFCDocument.getDocumentFor(doc);
		YFCElement eInput = dInput.getDocumentElement();
		
		System.out.println("BDAOrderLineItemAttributeValue - Input :: " + eInput);
		HashMap<String, String> attributes = getItemAttributes(env, eInput);
		try{
			String attribute = (String) getAttribute(mapData);
			System.out.println("Attribute: " + attribute);
			if(attributes.containsKey(attribute)) {
				String attributeValue = attributes.get(attribute);
				System.out.println("Attribute's Value: " + attributeValue);
				String requestedValue = getContains(mapData);
				System.out.println("Requested Value: " + requestedValue);
				return YFCCommon.equals(requestedValue, attributeValue);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static Document getItemListForOrderingTemplate() {
		YFCDocument dOutput = YFCDocument.createDocument("ItemList");
		YFCElement eOutput = dOutput.getDocumentElement();
		YFCElement eItem = eOutput.createChild("Item");
		eItem.setAttribute("ItemID", "");
		YFCElement eItemAttribute = eItem.createChild("ItemAttributeGroupTypeList").createChild("ItemAttributeGroupType").createChild("ItemAttributeGroupList").createChild("ItemAttributeGroup").createChild("ItemAttributeList").createChild("ItemAttribute");
		eItemAttribute.setAttribute("ItemAttributeName", "");
		eItemAttribute.setAttribute("Value", "");

		return dOutput.getDocument();
	}
	
	private HashMap<String, String> getItemAttributes(YFSEnvironment env, YFCElement eOrderLine) {
		HashMap<String, String> map = new HashMap<String, String>();
		YFCDocument dItem = YFCDocument.createDocument("Item");
		YFCElement eItemInput = dItem.getDocumentElement();
		eItemInput.setAttribute("CallingOrganizationCode", eOrderLine.getChildElement("Order").getAttribute("EnterpriseCode"));
		eItemInput.setAttribute("ItemID", eOrderLine.getChildElement("Item").getAttribute("ItemID"));
		
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("getItemListForOrdering", getItemListForOrderingTemplate());
			Document l_OutputXml = localApi.invoke(env, "getItemListForOrdering", dItem.getDocument());
			for(YFCElement eItem : YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement().getChildren()) {
				for(YFCElement eItemAttributeGroupType : eItem.getChildElement("ItemAttributeGroupTypeList", true).getChildren()) {
					for(YFCElement eItemAttributeGroup : eItemAttributeGroupType.getChildElement("ItemAttributeGroupList", true).getChildren()) {
						for(YFCElement eItemAttribute: eItemAttributeGroup.getChildElement("ItemAttributeList", true).getChildren()) {
							map.put(eItemAttribute.getAttribute("ItemAttributeName"), eItemAttribute.getAttribute("Value"));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private String getAttribute(Map mapData){
		String sOutput = getProperty("Attribute", mapData);
		if(!YFCCommon.isVoid(sOutput)){
			return sOutput;
		}
		return "/Order/@CustomerEMailID";
	}
	
	private String getContains(Map mapData){
		String sOutput = getProperty("AttributeValue", mapData);
		if(!YFCCommon.isVoid(sOutput)){
			return sOutput;
		}
		return "PRESCRIPTION";
	}
	

	@Override
	public void setProperties(Map map) {
		props = map;
	}

	private String getProperty(String sProperty, Map mapData){
		if(mapData.containsKey(sProperty)){
			return (String) mapData.get(sProperty);
		} else if(!YFCCommon.isVoid(props) && props.containsKey(sProperty)){
			return (String) props.get(sProperty);
		}
		return null;
	}
}
