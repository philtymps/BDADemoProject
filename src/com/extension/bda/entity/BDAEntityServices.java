package com.extension.bda.entity;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAEntityServices {

	public static YFCElement getObjectList(YFSEnvironment env, YFCElement eInput, String sEntityType) {
		Map<String,String> search = new HashMap<String, String>();
		BDAEntity e = new BDAEntity(sEntityType);
		for(String sAtt : eInput.getAttributes().keySet()){
			YFCElement eDataType = e.getAttributeDetails(sAtt, true);
			if(!YFCCommon.isVoid(eDataType)){
				search.put(eDataType.getAttribute("Name"), eInput.getAttribute(sAtt));
			}
		}
		YFCElement response = BDATable.getRecords(env, search, e);
		if(!YFCCommon.isVoid(response)){
			return response;
		}
		return null;
	}
	
	public static Document getObjectList(YFSEnvironment env, Document docInput, String sEntityType) {
		YFCDocument dInput = YFCDocument.getDocumentFor(docInput);
		YFCElement eInput = dInput.getDocumentElement();
		YFCElement response = getObjectList(env, eInput, sEntityType);
		if(!YFCCommon.isVoid(response)){
			return response.getOwnerDocument().getDocument();
		}
		return null;
	}
}
