package com.ibm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

	public static String getDBURL(String _dbType, String _sServer, String _sPort, String _sDatabase){
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
	
	public static String getJDBCDriver(String _dbType){
		if (_dbType.equals("DB2")){
			return "com.ibm.db2.jcc.DB2Driver";
		} else if (_dbType.equals("Oracle")){
			return "oracle.jdbc.driver.OracleDriver";
		} else if (_dbType.equals("sqlite")) {
			return "org.sqlite.JDBC";
		}
		return null;
	}
	
	public static Connection getDBConnection(String _dbType, String _sServer, String _sPort, String _sDatabase, String _sUserName, String _sPassword) throws ClassNotFoundException, SQLException{
		Class.forName(getJDBCDriver(_dbType));
		Connection jdbcConnection = (Connection)DriverManager.getConnection(getDBURL(_dbType, _sServer, _sPort, _sDatabase), _sUserName, _sPassword);
		return jdbcConnection;
	}
}
