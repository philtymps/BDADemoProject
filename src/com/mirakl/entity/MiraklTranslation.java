package com.mirakl.entity;

import java.io.File;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class MiraklTranslation {

	private static MiraklTranslation _instance = null;
	private final YFCElement eTranslation;

	public static MiraklTranslation getInstance(String sTranslationFile, boolean reset){
		if(_instance == null || reset){
			_instance = new MiraklTranslation(sTranslationFile);
		}
		return _instance;
	}
	
	public MiraklTranslation(String sTranslationFile){
		File f = new File(sTranslationFile);
		if(f.exists()){
			this.eTranslation = YFCDocument.getDocumentFor(f).getDocumentElement();
		} else {
			this.eTranslation = null;
		}
	}
		
	public String getMiraklValue(String sMiraklAttribute, String sOMSValue){
		if(!YFCCommon.isVoid(eTranslation)){
			for(YFCElement type : eTranslation.getChildren()){
				if(type.getNodeName().equals(sMiraklAttribute)){
					for(YFCElement eReplace : type.getChildren()){
						if(eReplace.getAttribute("key").equals(sOMSValue)){
							return eReplace.getNodeValue();
						}	
					}
					break;
				}
			}
		}
		return null;
	}
	
	public String getOMSValue(String sMiraklAttribute, String sMiraklValue){
		if(!YFCCommon.isVoid(eTranslation) && !YFCCommon.isVoid(sMiraklValue)){
			for(YFCElement type : eTranslation.getChildren()){
				if(type.getNodeName().equals(sMiraklAttribute)){
					for(YFCElement eReplace : type.getChildren()){
						if(eReplace.getNodeValue().equals(sMiraklValue)){
							return eReplace.getAttribute("key");
						}	
					}
					break;
				}
			}
		}
		return null;
	}
	
}
