package com.mirakl.sdf.condition;

import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class IsMiraklOffer implements YCPDynamicConditionEx {

	private Map properties = null;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		if(mapData.containsKey("ItemID") && !YFCCommon.isVoid(mapData.get("ItemID"))){
			if(!YFCCommon.isVoid(properties)){
				if (properties.containsKey("OfferPrefix")) {
					if(((String)mapData.get("ItemID")).toUpperCase().startsWith(((String)properties.get("OfferPrefix")).toUpperCase())){
						return true;
					}
				} 
			}
			if(((String)mapData.get("ItemID")).startsWith("MKO_")){
				return true;
			}
		}			
		return false;
	}

	@Override
	public void setProperties(Map map) {
		properties = map;
	}

}
