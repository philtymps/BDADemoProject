package com.extension.bda.service;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import org.json.JSONArray;
import org.json.JSONObject;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.extension.bda.service.expose.BDARestCall;
import com.yantra.yfs.japi.YFSEnvironment;

public class RemoteServiceRequest extends BDARestCall implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "callRemoteBDARest";
	}

	@Override
	protected void updateConnection(YFSEnvironment env, HttpURLConnection connection) {
		connection.setRequestProperty("Authorization", "Bearer " + buildJWT(env));
	}
	
	private String buildJWT(YFSEnvironment env) {
	
		try {
		    Algorithm algorithm = Algorithm.HMAC256("bda-rest");
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .withClaim("ip", getPublicIP())
		        .withClaim("user", env.getUserId())
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return null;
	}
	
	public static String getPublicIP() {
		try {

			URL url = new URL("http://gw/skytap");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			} else {
				JSONObject response = new JSONObject(new BufferedInputStream(conn.getInputStream()));
				conn.disconnect();
				
				JSONArray interfaces = response.getJSONArray("Interfaces");
				if(interfaces.length() > 0) {
					JSONObject _interface = interfaces.getJSONObject(0);
					if(_interface.has("public_ips")) {
						JSONArray public_ips = _interface.getJSONArray("public_ips");
						if(public_ips.length() > 0) {
							return public_ips.getJSONObject(0).getString("address");
						}
					}
				}
			}

			

		  } catch (Exception e) {
			  try {
				  return InetAddress.getLocalHost().getHostAddress();
			  } catch (UnknownHostException ex) {
				  ex.printStackTrace();
				 
			  }
		  }
		 return "127.0.0.1";
	}

}
