package com.ibm.extraction.commerce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.extension.bda.object.DatabaseConnection;
import com.ibm.utilities.ConnectionUtils;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASynchronization {

	private static Properties properties = new Properties();
	
	public BDASynchronization(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		properties = properties;
	}
	
	public static Object getProperty(String sProp){
		return properties.get(sProp);
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
		return "wc8dev";
	}
	
	protected static String getOMSDBServer(){
		if (!YFCCommon.isVoid(getProperty("OMSDBServer"))){
			return (String) getProperty("OMSDBServer");
		}
		return "oms.innovationcloud.info";
	}
	
	protected static String getDBPort(){
		if (!YFCCommon.isVoid(getProperty("DBPort"))){
			return (String) getProperty("DBPort");
		}
		return "50000";
	}
	
	protected static String getOMSDBPort(){
		if (!YFCCommon.isVoid(getProperty("OMSDBPort"))){
			return (String) getProperty("OMSDBPort");
		}
		return "50000";
	}
	
	protected static String getDatabase(){
		if (!YFCCommon.isVoid(getProperty("Database"))){
			return (String) getProperty("Database");
		}
		return "inst1db";
	}
	
	protected static String getOMSDatabase(){
		if (!YFCCommon.isVoid(getProperty("OMSDatabase"))){
			return (String) getProperty("OMSDatabase");
		}
		return "OMDB";
	}
	
	
	protected static String getDBUsername(){
		if (!YFCCommon.isVoid(getProperty("DBUserName"))){
			return (String) getProperty("DBUserName");
		}
		return "db2admin";
	}
	
	protected static String getOMSDBUsername(){
		if (!YFCCommon.isVoid(getProperty("OMSDBUsername"))){
			return (String) getProperty("OMSDBUsername");
		}
		return "demouser";
	}

	
	protected static String getDBPassword(){
		if (!YFCCommon.isVoid(getProperty("DBPassword"))){
			return (String) getProperty("DBPassword");
		}
		return "skiing740TIME_";
		//return "goatheard35rox!";
		//return "ExPl0re847447444sf4$";
	}
	
	protected static String getOMSDBPassword(){
		if (!YFCCommon.isVoid(getProperty("OMSDBPassword"))){
			return (String) getProperty("OMSDBPassword");
		}
		return "meeting265bridge";
	}
	
	protected static String getOMSDBSchema(){
		if (!YFCCommon.isVoid(getProperty("OMSDBSchema"))){
			return (String) getProperty("OMSDBSchema");
		}
		return "OMDB";
	}

	protected static String getImageServer(){
		if (!YFCCommon.isVoid(getProperty("ImageServer"))){
			return (String) getProperty("ImageServer");
		}
		return "https://oms.innovationcloud.info";
	}
	
	protected static Connection getCommerceConnection() throws SQLException, ClassNotFoundException {
		Connection dbConn = ConnectionUtils.getDBConnection(getDBType(), getDBServer(), getDBPort(), getDatabase(), getDBUsername(), getDBPassword());
		return dbConn;
	}
	
	public static Connection getOMSConnection(YFSEnvironment env) throws SQLException, ClassNotFoundException {
		return DatabaseConnection.getConnection(env);
	}
	
	public static Connection getOMSConnection(String sHost) throws SQLException, ClassNotFoundException {
		Connection dbConn = ConnectionUtils.getDBConnection(getDBType(), sHost, getOMSDBPort(), getOMSDatabase(), getOMSDBUsername(), getOMSDBPassword());
		return dbConn;
	}
}
