package com.extension.bda.userexits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.w3c.dom.Document;

import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.ydm.japi.ue.YDMBeforeCreateShipment;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

public class BDABeforeCreateShipment implements YDMBeforeCreateShipment {

	@Override
	public Document beforeCreateShipment(YFSEnvironment env, Document dInput) throws YFSUserExitException {
		YFCDocument inDoc = YFCDocument.getDocumentFor(dInput);
		
		YFCElement eShipment = inDoc.getDocumentElement();
		for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()){
			if(YFCCommon.isVoid(eShipmentLine.getAttribute("DepartmentCode"))){
				String sDepartment = getDepartmentForSku(eShipmentLine.getAttribute("ItemID"));
				if(!YFCCommon.isVoid(sDepartment)){
					eShipmentLine.setAttribute("DepartmentCode", sDepartment);
				}
			}
		}
		return inDoc.getDocument();
	}

	private String getDepartmentForSku(String sSku){
		Connection conn = null;
		String sDepartment = "";
		try {
			conn = BDASynchronization.getOMSConnection();
			String sSql = "SELECT DEPARTMENT FROM OMDB.YFS_ITEM WHERE TRIM(ITEM_ID) = ?";
			PreparedStatement ps = conn.prepareStatement(sSql);
			ps.setString(1, sSku);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				if(!YFCCommon.isVoid(rs.getString("DEPARTMENT"))){
					sDepartment = rs.getString("DEPARTMENT");
					break;
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (conn != null){
				try {
					conn.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return sDepartment;
	}
}
