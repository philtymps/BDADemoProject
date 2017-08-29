package com.ibm.extraction.commerce.organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShipNode extends Organization {

	public ShipNode(ResultSet rs, Map<String, Organization> organizations, String sServer) throws SQLException {
		super(rs, organizations, sServer);
		this.isNode = true;
	}

}
