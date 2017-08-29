package com.extension.bda.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import com.extension.bda.object.DatabaseConnection;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public abstract class BDATable {
	private BDAEntity e = null;
	
	protected abstract String getEntityType();
	protected BDAEntity getEntity() {
		if(e == null){
			e = new BDAEntity(getEntityType());
		}
		return e;
	}
	

	protected YFCElement saveRecord(YFCElement eElement){
		Connection conn = null;
		try {
			conn = DatabaseConnection.getConnection();
			if (eElement.hasAttribute(getEntity().getXmlTableKeyName())){
				String sSql = "UPDATE " + DatabaseConnection.getDBSchema() + "." + getEntity().getTableName() + " SET ";
				boolean first = true;
				ArrayList<YFCElement> values = new ArrayList<YFCElement>();
				for (String sKey : eElement.getAttributes().keySet()){
					if(!YFCCommon.equals(sKey, getEntity().getXmlTableKeyName())){
						if(!YFCCommon.isVoid(getEntity().getAttributeDetails(sKey, true))){
							if(!first){
								sSql += ", ";
							}
							sSql += getEntity().getAttributeDetails(sKey, true).getAttribute("Name") + " = ?";
							values.add(getEntity().getAttributeDetails(sKey, true));
							first = false;
						}
					}
				}
				sSql += " WHERE " + getEntity().getTableKeyName() + " = ?";
				values.add(getEntity().getAttributeDetails(getEntity().getTableKeyName(), false));
				System.out.println("SQL: " + sSql);
				PreparedStatement ps = conn.prepareStatement(sSql);
				populateValues(ps, values, eElement);
				if (ps.executeUpdate() > 0){
					return getRecordForKey(eElement.getIntAttribute(getEntity().getXmlTableKeyName()));
				}
				return null;
			} else {
				String sSql = "INSERT INTO " + DatabaseConnection.getDBSchema() + "." + getEntity().getTableName() + " (";
				boolean first = true;
				ArrayList<YFCElement> values = new ArrayList<YFCElement>();
				String sValues = "VALUES (";
				for (String sKey : eElement.getAttributes().keySet()){
					if(!YFCCommon.equals(sKey, getEntity().getXmlTableKeyName())){
						if(!YFCCommon.isVoid(getEntity().getAttributeDetails(sKey, true))){
							if(!first){
								sSql += ", ";
								sValues += ", ";
							}
							YFCElement eAtt = getEntity().getAttributeDetails(sKey, true);
							sSql += eAtt.getAttribute("Name");
							sValues += "?";
							values.add(eAtt);
							first = false;
						}
					}
				}
				
				PreparedStatement ps = conn.prepareStatement(sSql + ") " + sValues + ")", Statement.RETURN_GENERATED_KEYS);
				System.out.println("SQL: " + sSql + ") " + sValues + ")");
				populateValues(ps, values, eElement);
				if ( ps.executeUpdate() > 0){
					try (ResultSet generatedKeys = ps.getGeneratedKeys()){
						if(generatedKeys.next()){
							return getRecordForKey(generatedKeys.getInt(1));
						}
					}
				}
				
				return null;
			}
		} catch (Exception e){
			e.printStackTrace();
			if(conn != null){
				try {
					conn.close();
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
	protected YFCElement getOneRecord(Map<String, String> nameValuePairs){
		YFCDocument dOutput = YFCDocument.createDocument(getEntity().getXmlName());
		YFCElement eResponse = dOutput.getDocumentElement();
		YFCElement eList = getRecords(nameValuePairs, getEntity());
		if(!YFCCommon.isVoid(eList)){
			eResponse.setAttributes(eList.getChildElement(getEntity().getXmlName(), true).getAttributes());
		}
		return eResponse;
	}
	
	protected YFCElement getRecordForKey(int primaryKey) {
		Connection conn = null;
		try {
			conn = DatabaseConnection.getConnection();
			String sSql = "SELECT * FROM " + DatabaseConnection.getDBSchema() + "." + getEntity().getTableName() + " WHERE " + getEntity().getTableKeyName() + " = ?"; 
			System.out.println("SQL: " + sSql);
			PreparedStatement ps = conn.prepareStatement(sSql);
			ps.setInt(1, primaryKey);
			YFCDocument dOutput = getXmlData(ps.executeQuery(), getEntity());
			if(!YFCCommon.isVoid(dOutput)){
				YFCElement eList = dOutput.getDocumentElement();
				return eList.getChildElement(getEntity().getXmlName(), true);
			}
		} catch (Exception e){
			if(conn != null){
				try {
					conn.close();
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return null;		
	}
	
	
	public static YFCElement getRecords(Map<String, String> nameValuePairs, BDAEntity entity){
		Connection conn = null;
		try {
			conn = DatabaseConnection.getConnection();
			
			String sSql = "SELECT * FROM " + DatabaseConnection.getDBSchema() + "." + entity.getTableName() + " WHERE "; 
			boolean first = true;
			ArrayList<YFCElement> values = new ArrayList<YFCElement>();
			YFCElement eValues = YFCDocument.createDocument("Values").getDocumentElement();
			for (String sKey : nameValuePairs.keySet()){
				YFCElement eAtt = entity.getAttributeDetails(sKey, false);
				if(!YFCCommon.isVoid(eAtt)){
					if(!first){
						sSql += "AND ";
					}
					sSql += sKey + " = ? ";
					eValues.setAttribute(eAtt.getAttribute("XmlName"), nameValuePairs.get(sKey));
					values.add(eAtt);
					first = false;
				}
			}
			System.out.println("SQL: " + sSql);
			PreparedStatement ps = conn.prepareStatement(sSql);
			populateValues(ps, values, eValues);			
			YFCDocument dOutput = getXmlData(ps.executeQuery(), entity);
			if(!YFCCommon.isVoid(dOutput)){
				System.out.println(dOutput);
				return dOutput.getDocumentElement();
			}
		} catch (Exception e){
			if(conn != null){
				try {
					conn.close();
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return null;	
	}
	
	private static YFCDocument getXmlData(ResultSet rs, BDAEntity entity){
		YFCDocument output = YFCDocument.createDocument(entity.getXmlName() + "List");
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			YFCElement eList = output.getDocumentElement();
			while (rs.next()){
				YFCElement eItem = eList.createChild(entity.getXmlName());
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++){
					String name = rsmd.getColumnName(i);
					if(!YFCCommon.isVoid(entity.getAttributeDetails(name, false))){
						if(!YFCCommon.isVoid(rs.getString(name))){
							eItem.setAttribute(entity.getAttributeDetails(name, false).getAttribute("XmlName"), rs.getString(name).trim());
						} else {
							eItem.setAttribute(entity.getAttributeDetails(name, false).getAttribute("XmlName"), "");
						}
					}					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	private static void populateValues(PreparedStatement ps, ArrayList<YFCElement> values, YFCElement eData) throws SQLException{
		
		for (int i = 0; i < values.size(); i++){
			YFCElement eAtt = values.get(i);
			if (eAtt.getAttribute("DataType").toLowerCase().equals("integer")){
				System.out.println((i+1 + " :: " + eAtt.getAttribute("Name") + " :: " + eData.getIntAttribute(eAtt.getAttribute("XmlName"))));
				ps.setInt(i+1, eData.getIntAttribute(eAtt.getAttribute("XmlName")));
			} else {
				System.out.println((i+1 + " :: " + eAtt.getAttribute("Name") + " :: " + eData.getAttribute(eAtt.getAttribute("XmlName"))));
				ps.setString(i+1, eData.getAttribute(eAtt.getAttribute("XmlName")).trim());
			}					
		}
	}
		
}
