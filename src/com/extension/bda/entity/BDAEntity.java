package com.extension.bda.entity;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class BDAEntity {
	private YFCElement entity;
	
	private static final YFCElement entities;
	static{
		YFCElement eResponse = null;
		try {
			YFCDocument props = YFCDocument.parse(BDATable.class.getResourceAsStream("BDAEntity.xml"));
			if(!YFCCommon.isVoid(props)){
				eResponse = props.getDocumentElement();
			}
		} catch (Exception e){
			e.printStackTrace();
			
		}
		entities = eResponse;
	};
		
	public BDAEntity(String sType){
		if (YFCCommon.isVoid(entity)){
			for (YFCElement eEntity : entities.getChildren()){
				if(YFCCommon.equals(eEntity.getAttribute("TableName"), sType)){
					entity = eEntity;
					break;
				}
			}
		}
	}
	
	private YFCElement getEntity(){
		return entity;
	}
	
	public String getTableKeyName(){
		for (YFCElement eAttribute : getEntity().getChildElement("Attributes", true).getChildren()){
			if(eAttribute.getBooleanAttribute("PrimaryKey", true)){
				return eAttribute.getAttribute("Name");
			}
		}
		return null;
	}
	
	public String getXmlTableKeyName(){
		for (YFCElement eAttribute : getEntity().getChildElement("Attributes", true).getChildren()){
			if(eAttribute.getBooleanAttribute("PrimaryKey", true)){
				return eAttribute.getAttribute("XmlName");
			}
		}
		return null;
	}
	
	public String getTableName(){
		return getEntity().getAttribute("TableName");
	}
	public String getXmlName(){
		return getEntity().getAttribute("XmlName");
	}
	
	public YFCElement getAttributeDetails(String sKey, boolean xmlValue){
		if(!YFCCommon.isVoid(getEntity())){
			for (YFCElement eAttribute : getEntity().getChildElement("Attributes", true).getChildren()){
				if(xmlValue && eAttribute.getAttribute("XmlName").equals(sKey)){
					return eAttribute;
				} else if (!xmlValue && eAttribute.getAttribute("Name").equals(sKey)){
					return eAttribute;
				}
			}
		}		
		return null;
	}
	
}
