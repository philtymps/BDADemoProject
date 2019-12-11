package com.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class WSConnection {

	private String _sDatabase = null;
	private String _sPort = null;
	private String _sServer = null;
	private String _sSchema = null;
	private String _sUsername = null;
	private String _sPassword = null;
	private String _dbType = "DB2";
	private Properties _properties = null;
	
	
	public WSConnection (String sServer, String sPort, String sDatabase, String sSchema, String sUsername, String sPassword){
		_sServer = sServer;
		_sPort = sPort;
		_sDatabase = sDatabase;
		_sSchema = sSchema;
		_sUsername = sUsername;
		_sPassword = sPassword;
	}
	
	/* public WSConnection (InputStream input) throws IOException {
		_properties = new Properties();
		_properties.load(input);
		_dbType = _properties.getProperty("dbType", "DB2");
		_sServer = _properties.getProperty("host", "192.168.1.1");
		_sPort = _properties.getProperty("port", "50000");
		_sDatabase = _properties.getProperty("database", "WCC");
		_sSchema = _properties.getProperty("schema", "DEMO");
		_sUsername = _properties.getProperty("username", "db2inst1");
		_sPassword = _properties.getProperty("password", "db2inst1");
	} 
	
	public WSConnection() throws IOException {
		this(WSConnection.class.getResourceAsStream("oms.properties"));
	} */
	
	public Properties getProperties(){
		return _properties;
	}
	
	public String getDBURL(){
		String jdbc = "";
		if (_dbType.equals("DB2")){
			jdbc = "jdbc:db2://"+_sServer+":"+_sPort+"/"+_sDatabase;
		} else if (_dbType.equals("Oracle")){
			jdbc = "jdbc:oracle:thin:@"+_sServer+":"+_sPort+":"+_sDatabase;
		} else if (_dbType.equals("sqlite")){
			jdbc = "jdbc:sqlite:" + _sDatabase;
		}
		return jdbc;
	}
	
	public String getJDBCDriver(){
		if (_dbType.equals("DB2")){
			return "com.ibm.db2.jcc.DB2Driver";
		} else if (_dbType.equals("Oracle")){
			return "oracle.jdbc.driver.OracleDriver";
		} else if (_dbType.equals("sqlite")) {
			return "org.sqlite.JDBC";
		}
		return null;
	}
	
	public Connection getDBConnection() throws ClassNotFoundException, SQLException{
		
		if (_dbType.equals("sqlite")){
			System.setProperty("sqlite.purejava", "true");
			Class.forName(getJDBCDriver());
			Connection jdbcConnection = (Connection)DriverManager.getConnection(getDBURL());
			return jdbcConnection;
			
		}
		Class.forName(getJDBCDriver());
		Connection jdbcConnection = (Connection)DriverManager.getConnection(getDBURL(), getUsername(), getPassword());
		return jdbcConnection;
	}
	
	public String getServer(){
		return _sServer;
	}
	
	public String getPort(){
		return _sPort;
	}
	
	public String getDatabase(){
		return _sDatabase;
	}
	
	public String getSchema(){
		return _sSchema;
	}
	
	public String getUsername(){
		return _sUsername;
	}
	
	public String getPassword(){
		return _sPassword;
	}
	
	public void setServer(String sServer){
		_sServer = sServer;
	}
	
	public void setPort(String sPort){
		_sPort = sPort;
	}

	public void setDatabase(String sDatabase){
		_sDatabase = sDatabase;
	}

	public void setSchema(String sSchema){
		_sSchema = sSchema;
	}

	public void setUsername(String sUsername){
		_sUsername = sUsername;
	}

	public void setPassword(String sPassword){
		_sPassword = sPassword;
	}
}
