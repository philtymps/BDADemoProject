package com.objects;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.extension.bda.object.DatabaseConnection;
import com.utilities.WSConnection;

public class PostalCode {

	private String _country, _postalcode, _city, _state;
	private float _longitude, _latitude;
	
	public static void main(String[] args){
		String line;
		InputStream fis;
		int i = 0;
		ArrayList<String> used = new ArrayList<String>();
		try {
			Connection getConn = DatabaseConnection.getConnection(null);
			fis = new FileInputStream("C:\\Users\\IBM_ADMIN\\workspace\\BDADemoProject\\src\\com\\objects\\UKZips.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
			   PostalCode temp = new PostalCode(line);
			   if (!used.contains(temp._postalcode)){
				   PreparedStatement ps = getConn.prepareStatement(temp.getInsert(i++));
				   ps.execute();
				   used.add(temp._postalcode);
			   }
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public PostalCode(String sLine){
		String[] codes = sLine.split("\t");
		_country = codes[0].trim();
		_postalcode = codes[1].trim();
		_city = codes[2].trim();
		_state = codes[3].trim();
		_longitude = Float.parseFloat(codes[10].trim());
		_latitude = Float.parseFloat(codes[9].trim());
	}
	
	public String getInsert(int index){
		String insert =  "Insert Into OMDB.YFS_ZIP_CODE_LOCATION(ZIP_CODE_LOCATION_KEY, COUNTRY, STATE, ZIP_CODE, CITY, LATITUDE, LONGITUDE) VALUES ('"+_country + "_" + index + "','" + _country + "','" + _state + "','" + _postalcode + "','" + _city + "'," + _latitude + "," + _longitude +")";
		return insert;
	}
}
