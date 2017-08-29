package com.ibm.commerce.sterling.ue;

import java.io.IOException;
import java.util.HashMap;

import org.xml.sax.SAXException;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;

public class CommerceIntegrationMap {

	private static HashMap<String, HashMap<String, String>> map;
	private static HashMap<String, HashMap<String, String>> omsMap;
	public static String getOMSValue(String sMap, String sCommerceValue){
		if (map == null){
			loadMap();
		}
		HashMap<String, String> temp = map.get(sMap);
		if (!YFCCommon.isVoid(temp)){
			return temp.get(sCommerceValue);
		}
		return "";
	}
	
	public static HashMap<String, String> getOMSValues(String sMap){
		if (map == null){
			loadMap();
		}
		HashMap<String, String> temp = map.get(sMap);
		if (!YFCCommon.isVoid(temp)){
			return temp;
		}
		return null;
	}
	
	public static String getCommerceValue(String sMap, String sOMSValue){
		if (omsMap == null){
			loadOMSMap();
		}
		HashMap<String, String> temp = omsMap.get(sMap);
		if (!YFCCommon.isVoid(temp)){
			return temp.get(sOMSValue);
		}
		return "";
	}
	
	private static void loadOMSMap(){
		omsMap = new HashMap<String, HashMap<String, String>>();
		try {
			YFCDocument dValueMap = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Runtime/properties/ValueMaps.xml");
			if (!YFCCommon.isVoid(dValueMap)){
				YFCElement eRoot = dValueMap.getDocumentElement();
				for (YFCElement eMap : eRoot.getChildren()){
					HashMap<String, String> temp = new HashMap<String, String>();
					for (YFCElement eEntry : eMap.getChildren()){
						temp.put(eEntry.getNodeValue(),eEntry.getAttribute("key"));
					}
					omsMap.put(eMap.getAttribute("name"), temp);
				}
			}
		} catch (Exception e){
			YFCDocument dValueMap;
			try {
				dValueMap = YFCDocument.parse(CommerceIntegrationMap.class.getResourceAsStream("ValueMaps.xml"));
		
				if (!YFCCommon.isVoid(dValueMap)){
					YFCElement eRoot = dValueMap.getDocumentElement();
					for (YFCElement eMap : eRoot.getChildren()){
						HashMap<String, String> temp = new HashMap<String, String>();
						for (YFCElement eEntry : eMap.getChildren()){
							temp.put(eEntry.getNodeValue(),eEntry.getAttribute("key"));
						}
						omsMap.put(eMap.getAttribute("name"), temp);
					}
				}
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	private static void loadMap(){
		map = new HashMap<String, HashMap<String, String>>();
		try {
		YFCDocument dValueMap = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Runtime/properties/ValueMaps.xml");
		if (!YFCCommon.isVoid(dValueMap)){
			YFCElement eRoot = dValueMap.getDocumentElement();
			for (YFCElement eMap : eRoot.getChildren()){
				HashMap<String, String> temp = new HashMap<String, String>();
				for (YFCElement eEntry : eMap.getChildren()){
					temp.put(eEntry.getAttribute("key"), eEntry.getNodeValue());
				}
				map.put(eMap.getAttribute("name"), temp);
			}
		}
		} catch (Exception e){
			YFCDocument dValueMap;
			try {
				dValueMap = YFCDocument.parse(CommerceIntegrationMap.class.getResourceAsStream("ValueMaps.xml"));
		
				if (!YFCCommon.isVoid(dValueMap)){
					YFCElement eRoot = dValueMap.getDocumentElement();
					for (YFCElement eMap : eRoot.getChildren()){
						HashMap<String, String> temp = new HashMap<String, String>();
						for (YFCElement eEntry : eMap.getChildren()){
							temp.put(eEntry.getAttribute("key"), eEntry.getNodeValue());
						}
						map.put(eMap.getAttribute("name"), temp);
					}
				}
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
