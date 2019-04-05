package com.extension.bda.userexits;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.extension.bda.service.expose.BDARestCall;
import com.google.gson.Gson;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSExtnLineTaxCalculationInputStruct;
import com.yantra.yfs.japi.YFSExtnTaxCalculationOutStruct;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSRecalculateLineTaxUE;

public class BDARecalculateLineTax extends BDARestCall implements YFSRecalculateLineTaxUE {

	@Override
	public YFSExtnTaxCalculationOutStruct recalculateLineTax(YFSEnvironment env,
			YFSExtnLineTaxCalculationInputStruct inStruct) throws YFSUserExitException {
		Gson gson = new Gson();
		String obj = gson.toJson(inStruct);
		
		String sURL = "https://remoteomsservices.us-south.cf.appdomain.cloud/oms/userexits/recalculateLineTax";
		if (!YFCCommon.isVoid(sURL)) {
			
			try {				
				URL url = new URL(sURL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				if(obj != null) {
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
					outputStreamWriter.write(obj);
					outputStreamWriter.flush();
				}
				System.out.println("Before Request");
				int responseCode = connection.getResponseCode();
				System.out.println("Response Code: " + responseCode);
				if(responseCode < 300) {
					BufferedInputStream response = new BufferedInputStream(connection.getInputStream());
					byte[] content = new byte[1024];
					StringBuffer sb = new StringBuffer();
					int bytesRead = 0;
					while((bytesRead = response.read(content)) != -1) {
						sb.append(new String(content, 0, bytesRead));
					}
					System.out.println("Response: " + sb.toString());
					return gson.fromJson(sb.toString(), YFSExtnTaxCalculationOutStruct.class);
				}
				return null;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
		
	}

}
