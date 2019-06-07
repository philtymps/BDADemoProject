package com.extension.bda.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.extension.bda.service.expose.BDARestCall;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class RemoteServiceRequest extends BDARestCall implements IBDAService {

	private static String _ip;
	
	public static void main(String[] args) {
		System.out.println(RemoteServiceRequest.getPublicIP());
	}
	
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
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

	        ByteArrayOutputStream result = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = inputStream.read(buffer)) != -1) {
	            result.write(buffer, 0, length);
	        }

	        return result.toString(StandardCharsets.UTF_8.name());

	    }
	
	public static String getPublicIP() {
		try {
			if(!YFCCommon.isVoid(_ip)) {
				return _ip;
			}
			URL url = new URL("http://gw/skytap");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept", "application/xml");
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			} else {
				String output = convertInputStreamToString(conn.getInputStream());
				YFCDocument dResponse = YFCDocument.getDocumentFor(output);
				System.out.println(dResponse);
				conn.disconnect();
				
				YFCElement interfaces = dResponse.getDocumentElement().getChildElement("interfaces");
				for(YFCElement eInterface : interfaces.getChildren()) {
					if(!YFCCommon.isVoid(eInterface.getChildElement("public_ips"))) {
						for(YFCElement ePublicIP : eInterface.getChildElement("public_ips").getChildren()) {
							RemoteServiceRequest._ip = ePublicIP.getChildElement("address").getNodeValue();
							return ePublicIP.getChildElement("address").getNodeValue();
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
