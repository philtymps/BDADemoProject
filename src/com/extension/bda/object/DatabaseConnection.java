package com.extension.bda.object;

import static com.yantra.yfc.dom.YFCDocument.parse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.utilities.ConnectionUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class DatabaseConnection {
private static Properties properties = new Properties();
private static YFCElement eProperties = null;
	
	public DatabaseConnection(){
		properties = new Properties();
		
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	private static YFCElement getXmlProps(){
		if(!YFCCommon.isVoid(eProperties)){
			try {
				YFCDocument props = YFCDocument.parse(DatabaseConnection.class.getResourceAsStream("BDADatabase.xml"));
				if(!YFCCommon.isVoid(props)){
					eProperties = props.getDocumentElement();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return eProperties;
	}
	
	public static Object getProperty(String sProp){
		if (!YFCCommon.isVoid(properties) && properties.containsKey(sProp)){
			return properties.get(sProp);
		} else if (!YFCCommon.isVoid(getXmlProps()) && getXmlProps().hasAttribute(sProp)){
			return getXmlProps().getAttribute(sProp);
		}
		return null;
	}
	
	protected static String getDBType(){
		if (!YFCCommon.isVoid(getProperty("DBType"))){
			return (String) getProperty("DBType");
		}		
		return "DB2";
	}
	
	protected static String getDBServer(){
		if (!YFCCommon.isVoid(getProperty("DBServer"))){
			return (String) getProperty("DBServer");
		}
		return "oms.omfulfillment.com";
	}
		
	protected static String getDBPort(){
		if (!YFCCommon.isVoid(getProperty("DBPort"))){
			return (String) getProperty("DBPort");
		}
		return "50000";
	}
	
	protected static String getDatabase(){
		if (!YFCCommon.isVoid(getProperty("Database"))){
			return (String) getProperty("Database");
		}
		return "OMDB";
	}	
	
	protected static String getDBUsername(){
		if (!YFCCommon.isVoid(getProperty("Username"))){
			return (String) getProperty("Username");
		}
		return "demouser";
	}
	
	protected static String getDBPassword(){
		if (!YFCCommon.isVoid(getProperty("Password"))){
			return (String) getProperty("Password");
		}
		return "meeting265bridge";
	}
	
	public static String getDBSchema(){
		if (!YFCCommon.isVoid(getProperty("Schema"))){
			return (String) getProperty("Schema");
		}
		return "OMDB";
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection dbConn = ConnectionUtils.getDBConnection(getDBType(), getDBServer(), getDBPort(), getDatabase(), getDBUsername(), getDBPassword());
		return dbConn;
	}
	
}
