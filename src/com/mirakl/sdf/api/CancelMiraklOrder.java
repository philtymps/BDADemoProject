package com.mirakl.sdf.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class CancelMiraklOrder extends MiraklBase implements IBDAService {

	@Override
	public String getServiceName() {
		return "CancelMiraklOrder";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {

		// Input is CHANGE_ORDER.ON_CANCEL event input
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eOrder = dInput.getDocumentElement();
		
		YFCDocument dMiraklInput = YFCDocument.createDocument("body");
		YFCElement eCancelations = dMiraklInput.getDocumentElement().createChild("cancelations");
		
		boolean cancelRequired = false;
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			if(eOrderLine.getDoubleAttribute("ChangeInOrderedQty", 0) != 0){
				cancelRequired = true;
				YFCElement eCancelation = eCancelations.createChild("cancelation");
				createNode(eCancelation, "order_line_id", eOrderLine.getAttribute("OrderLineKey"));
				createNode(eCancelation, "amount", Math.abs(eOrderLine.getChildElement("ChainedFromOrderLine", true).getChildElement("LinePriceInfo", true).getDoubleAttribute("UnitPrice", 0)) * Math.abs(eOrderLine.getIntAttribute("ChangeInOrderedQty", 0)));
				createNode(eCancelation, "quantity", Math.abs(eOrderLine.getIntAttribute("ChangeInOrderedQty", 0)));
				createNodeTranslate(eCancelation, "reason_code", eOrder.getChildElement("OrderAudit", true).getAttribute("ReasonCode"), false, "CHANGE_OF_MIND");
				createNode(eCancelation, "shipping_amount", 0);
			}
		}
		
		if(cancelRequired){
			input = postCancel(dMiraklInput).getDocument();
		}
		
		return input;
	}
	
	private YFCDocument postCancel(YFCDocument dInput) throws Exception{
		YFCDocument dResponse = null;
		URL url;
		url = new URL(getURL("/api/orders/cancel"));
		String sInput = dInput.getString();
		System.out.println("Mirakl Input: " + sInput);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Authorization", getApiKey());
		conn.setRequestProperty("Content-Type", "application/xml");
		conn.setRequestProperty("Accept", "application/xml");
		conn.setRequestProperty("Content-Length",  String.valueOf(sInput.length()));
		conn.setDoOutput(true);
		
		OutputStream os = conn.getOutputStream();
		os.write(sInput.getBytes());
		
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String res;
		while ((res = in.readLine()) != null) {
			sb.append(res);
		}		
		in.close();
		
		if(conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
		}
		
		try {
			dResponse = YFCDocument.getDocumentFor(sb.toString());
			System.out.println("Mirakl Response: " + dResponse);
		} catch (Exception e) {
			System.out.println("Response is not a document");
			System.out.println(sb.toString());
		}
		conn.disconnect();
	
		return dResponse;
	}

}
