package com.extension.reedelsevier;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAIsProvisionedItem implements YCPDynamicConditionEx {

	Map<String, String> p;
	
	
	public BDAIsProvisionedItem (){
		p = new HashMap<String, String>();
	}
	
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YFCDocument dList = YFCDocument.getDocumentForXMLFile(getOutputLocation());
		YFCElement eList = dList.getDocumentElement();
		for(YFCElement eItem : eList.getChildren()){
			if(YFCCommon.equals(eItem.getAttribute("ItemID"), mapData.get("ItemID"))){
				return true;
			}
		}
		
		return false;
	}
	
	public String getOutputLocation(){
		if (p.containsKey("OutputLocation")){
			return p.get("OutputLocation");
		} else {
			return "/opt/Sterling/Demo/provisionItems.xml";
		}
	}
	
	public Object getProperty(String sProp){
		return this.p.get(sProp);
	}

	@Override
	public void setProperties(Map mapData) {
		p = mapData;
	}

}
