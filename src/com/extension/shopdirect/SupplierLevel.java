package com.extension.shopdirect;

import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class SupplierLevel implements YCPDynamicConditionEx {

	private Map properties = null;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String sName, Map mapData, Document inDoc) {
	
		YFCDocument dSupplierLevels = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/supplierLevel.xml");
		if(!YFCCommon.isVoid(dSupplierLevels)){
			YFCElement eSupplierLevels = dSupplierLevels.getDocumentElement();
			String sLevel = "Platinum";
			if(!YFCCommon.isVoid(mapData) && !YFCCommon.isVoid(mapData.get("Level"))){
				sLevel = (String) mapData.get("Level");
			} else if(!YFCCommon.isVoid(properties) && !YFCCommon.isVoid(properties.get("Level"))){
				sLevel = (String) properties.get("Level");
			} else {
				System.out.println("Evaluate Supplier Level: <undefined>");
			}
			System.out.println("Evaluating Supplier Level: " + sLevel);
			
			if(!YFCCommon.isVoid(mapData.get("SellerOrganizationCode"))){
				System.out.println("Input Doc: " + mapData.get("SellerOrganizationCode"));
				for(YFCElement eSupplierLevel : eSupplierLevels.getChildren()){
					if(YFCCommon.equals(eSupplierLevel.getAttribute("Level"), sLevel)){
						for(YFCElement eSupplier : eSupplierLevel.getChildren()){
							if(YFCCommon.equals(eSupplier.getAttribute("OrganizationCode"), mapData.get("SellerOrganizationCode"))){
								return true;
							}
						}
					}
				}
			}
		} else {
			System.out.println("/opt/Sterling/Scripts/supplierLevel.xml is empty or undefined");
		}
		
		return false;
	}

	@Override
	public void setProperties(Map mapData) {
		properties = mapData;
	}

}
