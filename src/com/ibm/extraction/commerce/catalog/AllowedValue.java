package com.ibm.extraction.commerce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class AllowedValue {
	
	private boolean isDefaultValue = false;
	private int iSequence = 0;
	private String sDescription = "";
	private String sValue = "";
	
	private static int sequence = 0;
	
	public boolean isDefaultValue(){
		return isDefaultValue;
	}
	
	public void setDefaultValue(int isDefaultValue){
		this.isDefaultValue = !YFCCommon.isVoid(isDefaultValue) && isDefaultValue == 2;
	}
	
	public AllowedValue(ResultSet rs) throws SQLException{
		setDefaultValue(rs.getInt("VALUSAGE"));
		setSequence(rs.getInt("SEQUENCE"));
		sDescription = rs.getString("VALUE");
		sValue = rs.getString("VALUE");
	}
	
	public String getDescription(){
		return sDescription;
	}
	
	public void setDescription(String sDesc){
		this.sDescription = sDesc;
	}
	
	public String getValue(){
		return this.sValue;
	}
	
	public void setValue(String sValue){
		this.sValue = sValue;
	}
	
	private void setSequence(int sequence){
		if (!YFCCommon.isVoid(sequence) && sequence > 0){
			iSequence = sequence;
			AllowedValue.sequence++;
		} else {
			iSequence = AllowedValue.sequence++;
		}
	}
	
	public int getSequence(){
		return iSequence;
	}
	
	
	public void addRecord(YFCElement eAttributeAllowedValueList){
		YFCElement eAllowed =eAttributeAllowedValueList.createChild("AttributeAllowedValue");
		eAllowed.setAttribute("IsDefaulValue", isDefaultValue());
		eAllowed.setAttribute("SequenceNo", getSequence());
		eAllowed.setAttribute("ShortDescription", getDescription());
		eAllowed.setAttribute("Value", getValue());
	}
	public YFCElement getAllowedValues(){
		YFCElement eAllowed = YFCDocument.createDocument("AttributeAllowedValue").getDocumentElement();
		eAllowed.setAttribute("IsDefaulValue", isDefaultValue());
		eAllowed.setAttribute("SequenceNo", getSequence());
		eAllowed.setAttribute("ShortDescription", getDescription());
		eAllowed.setAttribute("Value", getValue());
			
		return eAllowed;
	}
}
