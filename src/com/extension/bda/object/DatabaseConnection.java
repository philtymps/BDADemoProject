package com.extension.bda.object;
import java.sql.Connection;
import java.util.Properties;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSConnectionHolder;
import com.yantra.yfs.japi.YFSEnvironment;

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
	
	public static Object getProperty(YFSEnvironment env, String sProp){
		if (!YFCCommon.isVoid(env) && !YFCCommon.isVoid(BDAServiceApi.getPropertyValue(env, "bda." + sProp))) {
			return BDAServiceApi.getPropertyValue(env, "bda." + sProp);
		} else if (!YFCCommon.isVoid(properties) && properties.containsKey(sProp)){
			return properties.get(sProp);
		} else if (!YFCCommon.isVoid(getXmlProps()) && getXmlProps().hasAttribute(sProp)){
			return getXmlProps().getAttribute(sProp);
		}
		return null;
	}
	
	protected static String getDBType(){
		if (!YFCCommon.isVoid(getProperty(null, "DBType"))){
			return (String) getProperty(null, "DBType");
		}		
		return "DB2";
	}
	
	public static String getDBServer(YFSEnvironment env){
		if (!YFCCommon.isVoid(getProperty(env, "DBServer"))){
			return (String) getProperty(env, "DBServer");
		}
		return "oms.innovationcloud.info";
	}
		
	protected static String getDBPort(YFSEnvironment env){
		if (!YFCCommon.isVoid(getProperty(env, "DBPort"))){
			return (String) getProperty(env, "DBPort");
		}
		return "50000";
	}
	
	protected static String getDatabase(YFSEnvironment env){
		if (!YFCCommon.isVoid(getProperty(env, "Database"))){
			return (String) getProperty(env, "Database");
		}
		return "OMDB";
	}	
	
	protected static String getDBUsername(YFSEnvironment env){
		if (!YFCCommon.isVoid(getProperty(env, "Username"))){
			return (String) getProperty(env, "Username");
		}
		return "demouser";
	}
	
	protected static String getDBPassword(YFSEnvironment env){
		if (!YFCCommon.isVoid(getProperty(env, "Password"))){
			return (String) getProperty(env, "Password");
		}
		return "meeting265bridge";
	}
	
	public static String getDBSchema(YFSEnvironment env){
		if (!YFCCommon.isVoid(getProperty(env, "Schema"))){
			return (String) getProperty(env, "Schema");
		}
		return "OMDB";
	}

	public static Connection getConnection(YFSEnvironment env){
	    YFSConnectionHolder yfsConnHolder = (YFSConnectionHolder)env;
	    return yfsConnHolder.getDBConnection();
	}
	
}
