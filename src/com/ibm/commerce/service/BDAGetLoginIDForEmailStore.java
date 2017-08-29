package com.ibm.commerce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Document;

import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetLoginIDForEmailStore extends BDASynchronization {

	private static YFCLogCategory cat = YFCLogCategory.instance(BDAGetLoginIDForEmailStore.class);
	
	public Document getLoginID(YFSEnvironment env, Document input){
		if(!YFCCommon.isVoid(input)){
			YFCElement eInput = YFCDocument.getDocumentFor(input).getDocumentElement();
			if(YFCCommon.isVoid(eInput.getAttribute("LoginId")) && !YFCCommon.isVoid(eInput.getAttribute("EmailID"))){
				Connection c = null;
				try {
					c = getCommerceConnection();
			
					String sSql = "SELECT LOGONID FROM DB2ADMIN.USERREG WHERE USERS_ID IN (SELECT MEMBER_ID FROM DB2ADMIN.ADDRESS WHERE EMAIL1 = ?)";
					PreparedStatement ps = c.prepareStatement(sSql);
					ps.setString(1, eInput.getAttribute("EmailID"));
					ResultSet rs = ps.executeQuery();
					while ( rs.next() ) {
						if(!YFCCommon.isVoid(rs.getString("LOGONID"))){
							eInput.setAttribute("LoginId", rs.getString("LOGONID"));
							break;
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(c != null){
						try {
							c.close();
						} catch (Exception e){
							e.printStackTrace();
						}
						
					}
				}
			}
			cat.debug("Output: " + eInput);
		}
		
		return input;
	}
}
