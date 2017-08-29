package com.extension.publix;

import java.util.ArrayList;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class PublixAlertCondition implements YCPDynamicConditionEx {
	private Map props = null;
	
	private ArrayList<String> aSteak;
	private ArrayList<String> aAlcohol;

	
	private ArrayList<String> getSteak(){
		if(YFCCommon.isVoid(aSteak)){
			aSteak = new ArrayList<String>();
			YFCDocument dA = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/Steak.xml");
			if(!YFCCommon.isVoid(dA)){
				YFCElement eItems = dA.getDocumentElement();
				for(YFCElement eItem : eItems.getChildren()){
					aSteak.add(eItem.getAttribute("ItemID"));
				}
			}
		}
		return aSteak;
	}
	
	private ArrayList<String> getAlcohol(){
		if(YFCCommon.isVoid(aAlcohol)){
			aAlcohol = new ArrayList<String>();
			YFCDocument dA = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/Alcohol.xml");
			if(!YFCCommon.isVoid(dA)){
				YFCElement eItems = dA.getDocumentElement();
				for(YFCElement eItem : eItems.getChildren()){
					aAlcohol.add(eItem.getAttribute("ItemID"));
				}
			}
		}
		return aAlcohol;
	}
	
	
	
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YFCDocument dInput = YFCDocument.getDocumentFor(doc);
		YFCElement eInput = dInput.getDocumentElement();
		double steak = 0;
		double alcohol = 0;
	
		for(YFCElement eOrderLine : eInput.getChildElement("OrderLines").getChildren()){
			YFCElement eItem = eOrderLine.getChildElement("Item", true);
			
			if(getAlcohol().contains(eItem.getAttribute("ItemID"))){
				alcohol += eOrderLine.getChildElement("LinePriceInfo").getDoubleAttribute("ExtendedPrice");
			}
			if(getSteak().contains(eItem.getAttribute("ItemID"))){
				steak += eOrderLine.getChildElement("LinePriceInfo").getDoubleAttribute("ExtendedPrice");
			}
		}
		if(steak + alcohol > 100){
			return true;
		}
		return false;
	}
	private String getProperty(String sProperty, Map mapData){
		if(mapData.containsKey(sProperty)){
			return (String) mapData.get(sProperty);
		} else if(!YFCCommon.isVoid(props) && props.containsKey(sProperty)){
			return (String) props.get(sProperty);
		}
		return null;
	}
	
	private String getAttribute(Map mapData){
		String sOutput = getProperty("Attribute", mapData);
		if(!YFCCommon.isVoid(sOutput)){
			return sOutput;
		}
		return "/Order/@CustomerEMailID";
	}
	
	private String getContains(Map mapData){
		String sOutput = getProperty("Contains", mapData);
		if(!YFCCommon.isVoid(sOutput)){
			return sOutput;
		}
		return "ibm.com";
	}
	@Override
	public void setProperties(Map map) {
		props = map;
	}

}
