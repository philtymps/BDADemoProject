package com.scripts;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.utilities.WSConnection;
public class updateBDAs {

	public static void main(String[] args) throws IOException{
		String[] ips  = {"158.85.133.195","158.85.133.197","158.85.133.199","158.85.133.201","158.85.133.203","158.85.133.205","158.85.133.207","158.85.133.209","158.85.133.211","158.85.133.213","158.85.133.215","158.85.133.217"};
		for (String sIp : ips){
			WSConnection demoConn = new WSConnection(WSConnection.class.getResourceAsStream("oms.properties"));
			demoConn.setServer(sIp);
			try {
				String sStatement = "DELETE FROM " + demoConn.getSchema() + ".YFS_USER_EXIT_IMPL WHERE USER_EXIT_IMPL_KEY LIKE '201410081343471007943%'";
				PreparedStatement ps = demoConn.getDBConnection().prepareStatement(sStatement);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void update() throws IOException{
		String[] ips  = {"158.85.133.195","158.85.133.197","158.85.133.199","158.85.133.201","158.85.133.203","158.85.133.205","158.85.133.207","158.85.133.209","158.85.133.211","158.85.133.213","158.85.133.215","158.85.133.217"};
		for (String sIp : ips){
			WSConnection demoConn = new WSConnection(WSConnection.class.getResourceAsStream("oms.properties"));
			demoConn.setServer(sIp);
			try {
				String sStatement = "DELETE FROM " + demoConn.getSchema() + ".YFS_USER_EXIT_IMPL WHERE USER_EXIT_IMPL_KEY LIKE '201410081343471007943%'";
				PreparedStatement ps = demoConn.getDBConnection().prepareStatement(sStatement);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
		

		
	}
}
