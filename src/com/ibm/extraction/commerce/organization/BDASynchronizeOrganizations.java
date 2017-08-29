package com.ibm.extraction.commerce.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;

import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASynchronizeOrganizations extends BDASynchronization {
	
	public BDASynchronizeOrganizations(){
		super();
	}
	
	
	
	private String getServer(){
		if (!YFCCommon.isVoid(getProperty("server"))){
			return (String)getProperty("server");
		}
		return "http://oms.omfulfillment.com:9080";
	}
	
	public static void main(String[] args){
		BDASynchronizeOrganizations temp = new BDASynchronizeOrganizations();
		temp.syncronizeOrgs(null, null);
	}
	
	private Map<String, Organization> loadOrgs(){
		TreeMap<String, Organization> map = new TreeMap<String, Organization>();
		try {
			
			Connection dbConn = getCommerceConnection();
			String sSql = "SELECT S.STOREENT_ID, S.SETCCURR, S.IDENTIFIER, CX.CATALOG_ID, SC.STOREENT_ID AS CATALOG_STOREENT_ID, ADD.ADDRESS1 as LOC_ADDRESS1, ADD.CITY LOC_CITY, ADD.ADDRESS2 LOC_ADDRESS2, ADD.COUNTRY LOC_COUNTRY, ADD.EMAIL1 LOC_EMAIL, ADD.FAX1 LOC_FAX, ADD.PHONE1 LOC_PHONE, ADD.STATE LOC_STATE, ADD.ZIPCODE LOC_ZIPCODE, ADD.FIRSTNAME LOC_FIRSTNAME, ADD.LASTNAME LOC_LASTNAME, CONT.ADDRESS1 as CONT_ADDRESS1, CONT.CITY CONT_CITY, CONT.ADDRESS2 CONT_ADDRESS2, CONT.COUNTRY CONT_COUNTRY, CONT.EMAIL1 CONT_EMAIL, CONT.FAX1 CONT_FAX, CONT.PHONE1 CONT_PHONE, CONT.STATE CONT_STATE, CONT.ZIPCODE CONT_ZIPCODE, CONT.FIRSTNAME CONT_FIRSTNAME, CONT.LASTNAME CONT_LASTNAME  FROM STOREENT S LEFT JOIN CATGRPTPC CX ON CX.STORE_ID = S.STOREENT_ID INNER JOIN STORECAT SC ON SC.CATALOG_ID = CX.CATALOG_ID INNER JOIN STOREENTDS SL ON SL.STOREENT_ID = S.STOREENT_ID LEFT JOIN STADDRESS ADD ON ADD.STADDRESS_ID = SL.STADDRESS_ID_LOC LEFT JOIN STADDRESS CONT ON CONT.STADDRESS_ID = SL.STADDRESS_ID_CONT WHERE S.TYPE = 'S' AND SL.LANGUAGE_ID = -1 ORDER BY S.STOREENT_ID";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				Enterprise org = new Enterprise(rs, map, getServer());
				map.put(org.getCommerceID(), org);
			}
			dbConn.close();
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
		return map;
	}
	
	public Document syncronizeOrgs(YFSEnvironment env, Document inputDoc){
		Map<String, Organization> orgs = loadOrgs();
		for (Organization org : orgs.values()){
			System.out.println(org.getOrganizationHierarchy());
		}
		
		return null;
	}
}
