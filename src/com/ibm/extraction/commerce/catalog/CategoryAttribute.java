package com.ibm.extraction.commerce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.yantra.yfc.dom.YFCElement;

public class CategoryAttribute {

	private static int sequence = 0;
	ArrayList<String> attributeType;
	private int iSequence = 0;
	private Attribute attr;
	private String sDomainID = "ItemAttribute";
	
	private void setSequence(int sequence){
		if (sequence > 0){
			iSequence = sequence;
			CategoryAttribute.sequence++;
		} else {
			iSequence = CategoryAttribute.sequence++;
		}
	}
	
	public int getSequence(){
		return iSequence;
	}
	public CategoryAttribute(ResultSet rs, Attribute attr) throws SQLException{
		attributeType = new ArrayList<String>();
		if (rs.getString("USAGE").equals("1")){
			attributeType.add("DISTINCT_ATTRIBUTES");
		}
		attributeType.add("DISPLAY");
		attributeType.add("FOR_FILTER");
		attributeType.add("ASSIGNMENT");
		this.attr = attr;
		setSequence(rs.getInt("SEQUENCE"));
	}
	
	public void addItemAttribute(YFCElement eItemAttributeList){
		for (String type : attributeType){
			YFCElement eItemAttr = eItemAttributeList.createChild("ItemAttribute");
			eItemAttr.setAttribute("AttributeDomainID", sDomainID);
			eItemAttr.setAttribute("AttributeGroupID", attr.getAttributeGroupID());
			eItemAttr.setAttribute("ItemAttributeDescription", attr.getName());
			eItemAttr.setAttribute("ItemAttributeGroupType", type);
			eItemAttr.setAttribute("ItemAttributeName", attr.getAttributeID());
			eItemAttr.setAttribute("SequenceNo", getSequence());
		}
	}
}
