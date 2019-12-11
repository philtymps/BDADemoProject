package com.extension.dbschenker;

import java.io.File;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAContainsRental implements YCPDynamicConditionEx {
private Map props = null;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YFCDocument dInput = YFCDocument.getDocumentFor(doc);

		YFCElement eShipment = dInput.getDocumentElement();
		for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()){
			if(!YFCCommon.isVoid(eShipmentLine.getAttribute("ItemID"))){
				if(isRentalItem(env, eShipmentLine.getAttribute("ItemID"))){
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isRentalItem(YFSEnvironment env, String sItemID){
		File temp = new File(BDAServiceApi.getScriptsPath(env) + "/rentalItems.xml");
		if(temp.exists()){
			YFCDocument rentalItems = YFCDocument.getDocumentForXMLFile(BDAServiceApi.getScriptsPath(env) + "/rentalItems.xml");
			YFCElement eRentalItems = rentalItems.getDocumentElement();
			for(YFCElement eRentalItem : eRentalItems.getChildren()){
				if(YFCCommon.equals(eRentalItem.getAttribute("ItemID"), sItemID)){
					return true;
				}				
			}
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
	
	private String getItemID(Map mapData){
		String sOutput = getProperty("ItemID", mapData);
		if(!YFCCommon.isVoid(sOutput)){
			return sOutput;
		}
		return "PART004";
	}
	@Override
	public void setProperties(Map map) {
		props = map;
	}
}
