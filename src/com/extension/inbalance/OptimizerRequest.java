package com.extension.inbalance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.yantra.yfc.date.YTimestamp;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class OptimizerRequest {

	private final OAuth20Service service;
	private OAuth2AccessToken accessToken;
	
	public static void main(String[] args) throws SAXException, IOException{
		OptimizerRequest or = new OptimizerRequest();
		YFCDocument dInput = YFCDocument.parse(OptimizerRequest.class.getResourceAsStream("DummyOrder.xml"));
		YFCElement eInput = dInput.getDocumentElement();
		
		eInput.setAttribute("OrderDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
		eInput.setAttribute("OrderNo","OM" + System.currentTimeMillis());
		Document output = or.optimizeSourcing(null, dInput.getDocument());
		YFCDocument dOutput = YFCDocument.getDocumentFor(output);
		System.out.println(dOutput);
	}
	
	public OptimizerRequest(){
		service = new ServiceBuilder().apiKey("OTMZClient").apiSecret("OTMZ").build(OptimizerApi.instance());
		System.out.println(service.getAuthorizationUrl());
		try {
			accessToken = service.getAccessToken(null);
		} catch (Exception e) {
			e.printStackTrace();
			accessToken = null;
		}	
	}
	
	
	
	
	public Document optimizeSourcing(YFSEnvironment ctx, Document input){
		Document dOutput = null;
		if(accessToken != null){
			OAuthRequest request = new OAuthRequest(Verb.POST, "https://omfulfillment.com:9444/otmz/services/optimizer/kohls", service);
			request.addHeader("Content-Type", "application/xml");
			request.addHeader("Accept", "application/xml");
			service.signRequest(accessToken, request);
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			request.addPayload(dInput.toString());
			Response output = request.send();
			try{
				System.out.println("Service Output: " + output.getBody());
				YFCDocument dTemp = YFCDocument.getDocumentFor(output.getBody());
				return dTemp.getDocument();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return dOutput;
	}
}
