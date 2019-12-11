package com.extension.conditions;

import java.util.Map;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAIBAPrioritization implements YCPDynamicConditionEx {

	private Map props;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YFCDocument dPriorityCustomers = YFCDocument.getDocumentForXMLFile(BDAServiceApi.getScriptsPath(env) + "/priorityCustomers.xml");
		if(!YFCCommon.isVoid(dPriorityCustomers)){
			YFCElement ePriorityCustomers = dPriorityCustomers.getDocumentElement();
			String sLevel = getProperty("Level", mapData);
			if(YFCCommon.isVoid(sLevel)){
				sLevel = "VIP";
			}
			System.out.println("Evaluating Priority Level: " + sLevel);
			if(YFCCommon.equals(sLevel, "Standard")){
				return true;
			} else {
				for (YFCElement eLevel : ePriorityCustomers.getChildren()){
					if(YFCCommon.equals(eLevel.getAttribute("Level"), sLevel)){
						for(YFCElement eData : eLevel.getChildren()){
							for (String Att : eData.getAttributes().keySet()){
								if(mapData.containsKey(Att)){
									if(!YFCCommon.equals(mapData.get(Att), eData.getAttribute(Att))){
										return false;
									}
								}
							}
							return true;
						}					
					}
				}
			}
			
		} else {
			System.out.println(BDAServiceApi.getScriptsPath(env) + "/priorityCustomers.xml is empty or undefined");
		}
		
		return false;
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
