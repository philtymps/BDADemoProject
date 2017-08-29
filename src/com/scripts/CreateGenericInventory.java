package com.scripts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.utilities.WSConnection;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class CreateGenericInventory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CreateGenericInventory();

	}

	private ArrayList<String> nonStandardInventory = null;
	private WSConnection demoConn = null;
	
	public CreateGenericInventory(){
		nonStandardInventory = new ArrayList<String>();
		YFCDocument dAdjustInventory = YFCDocument.getDocumentForXMLFile("/home/pfaiola/workspace/OMS/Use Cases/PreDemo_adjustInventory.xml");
		if (!YFCCommon.isVoid(dAdjustInventory)){
			for (YFCElement eItem : dAdjustInventory.getDocumentElement().getChildren()){
				String sItemID = eItem.getAttribute("ItemID");
				if (!nonStandardInventory.contains(sItemID)){
					nonStandardInventory.add(sItemID);
				}
			}
		}
		
		Connection insertConn = null; 
		Connection getConn = null; 
		String sStatement = null;
		try {
			demoConn = new WSConnection(WSConnection.class.getResourceAsStream("oms.properties"));
			getConn = demoConn.getDBConnection();
			insertConn = demoConn.getDBConnection();
			String sSql = "SELECT TRIM(ITEM_ID) ITEM_ID FROM "+ demoConn.getSchema() + ".YFS_ITEM WHERE ITEM_ID NOT IN (SELECT ITEM_ID FROM "+ demoConn.getSchema() + ".YFS_INVENTORY_ITEM)";
			PreparedStatement ps = getConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				//if (!nonStandardInventory.contains(rs.getString("ITEM_ID"))){
					sStatement = "INSERT INTO " + demoConn.getSchema() + ".YFS_INVENTORY_ITEM (INVENTORY_ITEM_KEY, ORGANIZATION_CODE, ITEM_ID, UOM) values('" + rs.getString("ITEM_ID") + "', 'Aurora', '"+ rs.getString("ITEM_ID")+ "','EACH')";
					PreparedStatement ps2 = insertConn.prepareStatement(sStatement);
					ps2.execute();
				//}
			}
		} catch (IOException e){
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(sStatement);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (insertConn != null){
				try {
					insertConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (getConn != null){
				try {
					getConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
