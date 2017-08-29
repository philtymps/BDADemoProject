package com.extension.conditions;

import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAEmailContainsCondition implements YCPDynamicConditionEx {

	private Map props = null;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YFCDocument dInput = YFCDocument.getDocumentFor(doc);

		YFCElement eInput = dInput.getDocumentElement();
		String sResponse = "";
		try{
			String sXPath = (String) getAttribute(mapData);
			XPath xPath = XPathFactory.newInstance().newXPath();
			sResponse = xPath.evaluate(sXPath, eInput.getDOMNode());
		} catch(Exception e){
			
		}
		
		if(YFCCommon.isVoid(sResponse)){
			sResponse = eInput.getAttribute(getAttribute(mapData));
		}
		if(!YFCCommon.isVoid(sResponse)){
			return sResponse.toLowerCase().indexOf(getContains(mapData).toLowerCase()) > -1;
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
