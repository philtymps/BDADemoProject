package com.ibm.extraction.commerce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class AdditionalAttribute {

	private String sDomainID = "ItemAttribute";
	private String sGroupID = "Aurora";
	private Attribute attr;
	private String sValue = "";
	private int iValue;
	private double dValue;
	
	public AdditionalAttribute(ResultSet rs, Attribute attr) throws SQLException{
		this.attr = attr;
		setValue(rs);
	}
	
	public String getDomainID(){
		return sDomainID;
	}
	
	public String getGroupID(){
		return attr.getAttributeGroupID();
	}
	
	public Attribute getAttribute(){
		return attr;
	}
	
	public void setAttribute(Attribute attr){
		this.attr = attr;
	}
	
	public String getValue(){
		return sValue;
	}
	
	public int getIntValue(){
		return iValue;
	}
	
	public double getDoubleValue(){
		return dValue;
	}
	
	private void setValue(ResultSet rs) throws SQLException{
		if(attr.getDataType().equals("INTEGER") && !YFCCommon.isVoid(rs.getString("INTEGERVALUE")) && Double.valueOf(rs.getString("INTEGERVALUE")) != Integer.valueOf(rs.getString("INTEGERVALUE")).doubleValue()){
			attr.setDataType("DECIMAL");
			sValue = rs.getString("INTEGERVALUE");
		} else if (attr.getDataType().equals("DECIMAL")){
			dValue = rs.getFloat("FLOATVALUE");
			sValue = rs.getString("FLOATVALUE");
		} else if (attr.getDataType().equals("INTEGER")) {
			iValue = rs.getInt("INTEGERVALUE");
			sValue = rs.getString("INTEGERVALUE");
			
		} else {
			if (!YFCCommon.isVoid(rs.getString("FLOATVALUE"))){
				sValue = rs.getString("FLOATVALUE");
			} else if (!YFCCommon.isVoid(rs.getString("INTEGERVALUE"))){
				sValue = rs.getString("INTEGERVALUE");
			} else if (!YFCCommon.isVoid(rs.getString("STRINGVALUE"))){
				sValue = rs.getString("STRINGVALUE");
			}
		}
		
	}
	
	public  void addAdditionalAttribute(YFCElement eAdditionalAttributeList){
		YFCElement eAddAtt = eAdditionalAttributeList.createChild("AdditionalAttribute");
		eAddAtt.setAttribute("AttributeDomainID", getDomainID());
		eAddAtt.setAttribute("AttributeGroupID", getGroupID());
		eAddAtt.setAttribute("Name", getAttribute().getAttributeID());
		eAddAtt.setAttribute("Value", getValue());
		if (!YFCCommon.isVoid(getIntValue()) && attr.getDataType().equals("INTEGER")){
			eAddAtt.setAttribute("IntegerValue", getIntValue());
		} else if(!YFCCommon.isVoid(getIntValue()) && attr.getDataType().equals("DECIMAL")){
			eAddAtt.setAttribute("DoubleValue", getIntValue());
		}
		if (!YFCCommon.isVoid(getDoubleValue())){
			eAddAtt.setAttribute("DoubleValue", getDoubleValue());
		}	
	}
}
