package com.mirakl.sdf.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class PayMiraklOrder extends MiraklBase implements IBDAService {

	@Override
	public String getServiceName() {
		return "PayMiraklOrder";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		
		System.out.println("In PayMiraklOrder");
		// Input is CHANGE_ORDER.ON_CANCEL event input
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eOrder = dInput.getDocumentElement();
		
		YFCDocument dMiraklInput = YFCDocument.createDocument("body");
		YFCElement eOrders = dMiraklInput.getDocumentElement().createChild("orders");
		
		ArrayList<String> orderids = new ArrayList<String>();
		
		
		for(YFCElement eInvoiceCollection : eOrder.getChildElement("InvoiceCollections", true).getChildren()){
			System.out.println("In Invoice Collection: " + eInvoiceCollection);
			
			YFCElement eShipment = eInvoiceCollection.getChildElement("Shipment");
			if(!YFCCommon.isVoid(eShipment)){
				if(eShipment.getAttribute("SellerOrganizationCode").equals(this.getMiraklEnterprise())){
					System.out.println("Is " + this.getMiraklEnterprise() + " Shipment");
					for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()){
						YFCElement eOrderLine = eShipmentLine.getChildElement("OrderLine");
						if(!YFCCommon.isVoid(eOrderLine) && !YFCCommon.isVoid(eOrderLine.getChildElement("CustomAttributes")) && !YFCCommon.isVoid(eOrderLine.getChildElement("CustomAttributes").getAttribute(this.getMiraklOrderID()))){
							System.out.println("Order has " + this.getMiraklOrderID() + " :: " + eOrderLine.getChildElement("CustomAttributes").getAttribute(this.getMiraklOrderID()));
							String orderid = eOrderLine.getChildElement("CustomAttributes").getAttribute(this.getMiraklOrderID());							
							if(!orderids.contains(orderid)){
								YFCElement order = eOrders.createChild("order");
								
								try {
									YFCDocument dMiraklOrders = getMiraklOrder(orderid);
									YFCElement eMiraklOrder = dMiraklOrders.getDocumentElement().getChildElement("orders", true).getChildElement("order", true);
									Double total = eMiraklOrder.getChildElement("total_price").getDoubleNodeValue();
									if(total <= eInvoiceCollection.getChildElement("OrderInvoice").getDoubleAttribute("AmountCollected")){
										createNode(order, "amount", total);
									} else {
										createNode(order, "amount", eInvoiceCollection.getChildElement("OrderInvoice").getAttribute("AmountCollected"));
									}								
								} catch (Exception e){
									createNode(order, "amount", eInvoiceCollection.getChildElement("OrderInvoice").getAttribute("AmountCollected"));
								
								}
								createNode(order, "customer_id", eOrder.getAttribute("CustomerEMailID", "no_email"));
								createNode(order, "order_id", orderid);
								if(YFCCommon.equals(eInvoiceCollection.getChildElement("OrderInvoice").getAttribute("AmountCollected"), eInvoiceCollection.getChildElement("OrderInvoice").getAttribute("TotalAmount"))){
									createNode(order, "payment_status", "OK");
								} else {
									createNode(order, "payment_status", "REFUSED");
								}
								//createNode(order, "transaction_date", eInvoiceCollection.getChildElement("OrderInvoice").getAttribute("TransactionDate"));
								orderids.add(orderid);
							}
						}
					}
				}
			}
		}
		
		if(!orderids.isEmpty()){
			input = postPayment(dMiraklInput).getDocument();
		}
		
		return input;
	}
	
	
	private YFCDocument getMiraklOrder(String sOrderHeaderKey) throws MalformedURLException, IOException{
		YFCDocument dResponse = null;
		URL url;
		url = new URL(getURL("/api/orders?order_ids=" + sOrderHeaderKey));
		System.out.println("Mirakl Input: " + url.toString());
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", getApiKey());
		conn.setRequestProperty("Accept", "application/xml");
		conn.setDoOutput(true);
			
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
	
	private YFCDocument postPayment(YFCDocument dInput) throws Exception{
		YFCDocument dResponse = null;
		URL url;
		url = new URL(getURL("/api/payment/debit"));
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
		
		if(conn.getResponseCode() == 204){
			conn.disconnect();
			return dInput;
		} else if(conn.getResponseCode() != 200) {
			conn.disconnect();
			//throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
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
