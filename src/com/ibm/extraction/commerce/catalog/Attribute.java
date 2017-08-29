package com.ibm.extraction.commerce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.CallInteropServlet;
import com.ibm.extraction.BDASynchronizeItems;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;

public class Attribute {
	private static YFCLogCategory logger = YFCLogCategory.instance(BDASynchronizeItems.class);
	private static final String[] attributeOverride = {"ShoesSize", "Shoe Size", "GirlsDressesSkirtsSize"};
	private static ArrayList<String> attributeIdentifiers;
	private static int sequence = 0;
	
	public static void reset(){
		attributeIdentifiers = new ArrayList<String>();
		sequence = 0;
	}
	private String sAttributeID = "";
	private String sIdentifier = "";
	private String sAttrTypeID = "";
	private String sName = "";
	private long lAttributeKey;
	private String sOrganizationCode = "";
	private String sOrgSuffix = "";
	private int iSequence = 0;
	private boolean bAllowedValueDefined = false;
	private ArrayList<AllowedValue> allowedValues;
	
	public Attribute(ResultSet rs, String sOrganizationCode, String sOrgSuffix) throws SQLException{
		setAttributeID(rs.getString("IDENTIFIER"));
		lAttributeKey = rs.getLong("ATTR_ID");
		setIsAllowedValueDefined(rs.getInt("ATTRUSAGE"));
		setDataType(rs.getString("ATTRTYPE_ID"));
		setSequence(rs.getInt("SEQUENCE"));
		sName = rs.getString("NAME");
		this.sOrganizationCode = sOrganizationCode;
		this.sOrgSuffix = sOrgSuffix;
		allowedValues = new ArrayList<AllowedValue>();
	}
	
	public ArrayList<AllowedValue> getAllowedValues(){
		return allowedValues;
	}
	
	public void addAllowedValue(AllowedValue allowed, String sDataType){
		if(sDataType.equals("INTEGER")){
			try{
				allowed.setValue(Integer.parseInt(allowed.getValue()) + "");
			} catch (Exception e){
				return;
			}
			
		}
		if (allowedValues.size() > 0){
			for (AllowedValue v : allowedValues){
				if (v.getValue().equals(allowed.getValue())){
					return;
				}
			}
		}
		allowedValues.add(allowed);
	}
	
	public String getAttributeID(){
		return getSafeID(sAttributeID);
	}
	
	public String getOrgCode(){
		return sOrganizationCode;
	}
	
	public String getAttributeGroupID(){
		if (sOrganizationCode.equals("Aurora-Corp")){
			return "Aurora";
		} else {
			return sOrganizationCode;
		}
	}
	public String getName(){
		return sName;
	}
	
	public void setName(String sName){
		this.sName = sName;
	}
	public void setAttributeID(String sId){
		sIdentifier = sId;
		if (attributeIdentifiers == null){
			attributeIdentifiers = new ArrayList<String>();
		}
		String sAttributeID = sId;
		int i = 0;
		if (sAttributeID.equals("ComputersComponentsOperating System Class")){
			sAttributeID = "Computer OS Class";
		}
		sAttributeID = sAttributeID.replace("hardware_fasteners_bolts", "HFB_");
		sAttributeID = sAttributeID.replace("hardware_fasteners_screws", "HFS_");
		
		while (attributeIdentifiers.contains(sAttributeID)){
			sAttributeID = sId + "-" + ++i;
			logger.info("New ID: "+ sAttributeID);
		}
		if (sAttributeID.length() > 40){
			logger.info("Attribute Too Big: " + sAttributeID);
		}
		this.sAttributeID = sAttributeID;
	}
	
	public String getAttributeKey(){
		long temp = lAttributeKey - 6999999999999990000L;
		if (temp > 0L){
			return sOrgSuffix + "-" + temp;
		} 
		return sOrgSuffix + "-" + lAttributeKey;
	}
	
	public void setAttributeKey(long sAttributeKey){
		this.lAttributeKey = sAttributeKey;
	}
	
	public boolean isAllowedValueDefined(){
		return this.bAllowedValueDefined;
	}
	
	public void setIsAllowedValueDefined(int attrusage){
		this.bAllowedValueDefined = attrusage == 1;
	}

	public static String getSafeID(String input){
		if (!YFCCommon.isVoid(input)){
			return input.replaceAll(" ", "");
		}
		return "";
	}
	
	private boolean isOverridden(){
		for (String a : attributeOverride){
			if (sIdentifier.contains(a)){
				return true;
			}
		}
		return false;
	}
	
	public void setDataType(String sAttrTypeID){
		this.sAttrTypeID = sAttrTypeID;
	}
	
	public String getDataType(){
		if (isOverridden()){
			return "TEXT";
		} else {
			if (sAttrTypeID.toUpperCase().startsWith("STRING")){
				return "TEXT";
			} else if (sAttrTypeID.toUpperCase().startsWith("INTEGER")) {
				return "INTEGER";
			} else if (sAttrTypeID.toUpperCase().startsWith("FLOAT")) {
				return "DECIMAL";
			} 
		}
		return "TEXT";
	}
	
	private void setSequence(int sequence){
		if (sequence > 0){
			iSequence = sequence;
			Attribute.sequence++;
		} else {
			iSequence = Attribute.sequence++;
		}
	}
	
	public int getSequence(){
		return iSequence;
	}
	
	public boolean attributeExists(String service){
		YFCElement eInput = YFCDocument.createDocument("Attribute").getDocumentElement();
		if (sOrganizationCode.equals("Aurora-Corp")){
			eInput.setAttribute("AttributeGroupID", "Aurora");
		} else {
			eInput.setAttribute("AttributeGroupID", sOrganizationCode);
		}
		eInput.setAttribute("AttributeDomainID", "ItemAttribute");
		eInput.setAttribute("AttributeID", getAttributeID());
		eInput.setAttribute("CallingOrganizationCode", sOrganizationCode);
		try {
			YFCDocument dOutput = CallInteropServlet.invokeApi(eInput.getOwnerDocument(), null, "getAttributeList", service);
			if(!YFCCommon.isVoid(dOutput)){
				return dOutput.getDocumentElement().hasChildNodes();
			}
		} catch (Exception e){
			return false;
		}
		
		return false;
	}
	
	public void createAttribute (YFCElement eAttributeList){
				
		YFCElement eAttribute = eAttributeList.createChild("Attribute");
		eAttribute.setAttribute("AllowMultipleValues","N");
		eAttribute.setAttribute("AttributeDomainID", "ItemAttribute");
		eAttribute.setAttribute("AttributeID", getAttributeID());
		if (sOrganizationCode.equals("Aurora-Corp")){
			eAttribute.setAttribute("AttributeGroupID", "Aurora");
		} else {
			eAttribute.setAttribute("AttributeGroupID", sOrganizationCode);
		}
		eAttribute.setAttribute("AttributeKey", getAttributeKey());
		eAttribute.setAttribute("IsAllowedValueDefined", isAllowedValueDefined());
		eAttribute.setAttribute("DataType", getDataType());
		eAttribute.setAttribute("SequenceNo", getSequence());
		eAttribute.setAttribute("IsValueMandatory","N");
		eAttribute.setAttribute("OrganizationCode", sOrganizationCode);
		eAttribute.setAttribute("ShortDescription", getName());
		if (allowedValues != null && allowedValues.size() > 0){
			YFCElement eAttributeAllowedValueList = eAttribute.getChildElement("AttributeAllowedValueList", true);
			eAttributeAllowedValueList.setAttribute("Reset", "Y");
			for (AllowedValue temp : allowedValues){
				temp.addRecord(eAttributeAllowedValueList);
			}
		}
	}
}
