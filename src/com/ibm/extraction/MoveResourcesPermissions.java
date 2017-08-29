package com.ibm.extraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.extraction.commerce.BDASynchronization;

public class MoveResourcesPermissions extends BDASynchronization {

	public static void main(String[] args){
		MoveResourcesPermissions.copyResourcePermissions();
	}
	public static void copyResources(){
		try {
			Connection dbConn = getOMSConnection("oms.master.isdemocloud.info");
			Connection db2Conn = getOMSConnection("diab.tympsnet.com");
			String sSql = "SELECT RESOURCE_KEY, RESOURCE_ID, RESOURCE_DESC, PARENT_RESOURCE_ID, RESOURCE_TYPE, RESOURCE_CREATE_TYPE, RESOURCE_SEQ, CAN_ADD_TO_MENU, IS_PERMISSION_CONTROLLED, APPLICATION_NAME, APPLICATION_CODE, SUPPRESS_HELP, ROLLBACK_ONLY_MODE, IS_REPORT, STATUS FROM OMDB.YFS_RESOURCE WHERE PARENT_RESOURCE_ID LIKE 'ISCCS%' AND MODIFYTS > '2016-01-01' ORDER BY MODIFYTS DESC";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			PreparedStatement insertS = db2Conn.prepareStatement("INSERT INTO OMDB.YFS_RESOURCE(RESOURCE_KEY, RESOURCE_ID, RESOURCE_DESC, PARENT_RESOURCE_ID, RESOURCE_TYPE, RESOURCE_CREATE_TYPE, RESOURCE_SEQ, CAN_ADD_TO_MENU, IS_PERMISSION_CONTROLLED, APPLICATION_NAME, APPLICATION_CODE, SUPPRESS_HELP, ROLLBACK_ONLY_MODE, IS_REPORT, STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			while ( rs.next() ) {
				insertS.setString(1, rs.getString(1));
				insertS.setString(2, rs.getString(2));
				insertS.setString(3, rs.getString(3));
				insertS.setString(4, rs.getString(4));
				insertS.setString(5, rs.getString(5));
				insertS.setString(6, rs.getString(6));
				insertS.setInt(7, rs.getInt(7));
				insertS.setString(8, rs.getString(8));
				insertS.setString(9, rs.getString(9));
				insertS.setString(10, rs.getString(10));
				insertS.setString(11, rs.getString(11));
				insertS.setString(12, rs.getString(12));
				insertS.setString(13, rs.getString(13));
				insertS.setString(14, rs.getString(14));
				insertS.setInt(15, rs.getInt(15));
				insertS.executeUpdate();
			}
			dbConn.close();
			insertS.close();
			db2Conn.close();
				
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
	
	public static void copyResourcePermissions(){
		try {
			Connection dbConn = getOMSConnection("oms.master.isdemocloud.info");
			Connection db2Conn = getOMSConnection("diab.tympsnet.com");
			String sSql = "SELECT RESOURCE_PERMISSION_KEY, RESOURCE_KEY, USERGROUP_KEY, ACTIVATE_FLAG, READ_ONLY_FLAG, RIGHTS FROM OMDB.YFS_RESOURCE_PERMISSION RP WHERE RESOURCE_KEY IN (SELECT RESOURCE_KEY FROM OMDB.YFS_RESOURCE WHERE PARENT_RESOURCE_ID LIKE 'ISCCS%' AND MODIFYTS > '2016-01-01')";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			PreparedStatement insertS = db2Conn.prepareStatement("INSERT INTO OMDB.YFS_RESOURCE_PERMISSION(RESOURCE_PERMISSION_KEY, RESOURCE_KEY, USERGROUP_KEY, ACTIVATE_FLAG, READ_ONLY_FLAG, RIGHTS) VALUES(?,?,?,?,?,?)");
			
			while ( rs.next() ) {
				insertS.setString(1, rs.getString(1));
				insertS.setString(2, rs.getString(2));
				insertS.setString(3, rs.getString(3));
				insertS.setString(4, rs.getString(4));
				insertS.setString(5, rs.getString(5));
				insertS.setInt(6, rs.getInt(6));
				insertS.executeUpdate();
			}
			dbConn.close();
			insertS.close();
			db2Conn.close();
				
		} catch (ClassNotFoundException cE){
			cE.printStackTrace();
		} catch (SQLException sE) {
			sE.printStackTrace();
		}
	}
}
