package com.mirakl.sdf.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.extension.bda.service.IBDAService;
import com.mirakl.entity.MiraklTranslation;
import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class CreateMiraklOrder extends MiraklBase implements IBDAService {

	
	@Override
	public String getServiceName() {
		return "CreateMiraklOrder";
	}
	
	private YFCDocument getSalesOrderTemplate(){
		if(getProperties().contains("salesOrderTemplatePath")){
			File f = new File(getProperties().getProperty("salesOrderTemplatePath"));
			if(f.exists()){
				return YFCDocument.getDocumentFor(f);
			}
			InputStream inSource = CreateMiraklOrder.class.getResourceAsStream(getProperties().getProperty("salesOrderTemplatePath"));
			if(!YFCCommon.isVoid(inSource)){
				try {
					return YFCDocument.parse(inSource);
				} catch (IOException ex){
					System.out.println("Unable to open input source at: " + getProperties().getProperty("salesOrderTemplatePath"));
				} catch (SAXException e) {
					System.out.println("Unable to parse source at: " + getProperties().getProperty("salesOrderTemplatePath"));
				}
			}
		}
		
		try {
			return YFCDocument.parse("SalesOrderTemplate.xml");
		} catch (IOException ex){
			System.out.println("Unable to open input source at: SalesOrderTemplate.xml");
		} catch (SAXException e) {
			System.out.println("Unable to parse source at:  SalesOrderTemplate.xml");
		}
		return null;
	}
	
	
	private YFCDocument postOrder(YFCDocument dInput) throws Exception{
		YFCDocument dResponse = null;
		URL url;
		url = new URL(getURL("/api/orders"));
		String sInput = dInput.getString();
		System.out.println("Mirakl Input: " + sInput);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
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
	
	private void confirmOrder(String sOrderHeaderKey) throws Exception{
		URL url;
		String sURL = getURL("/api/orders/valid/" + sOrderHeaderKey);
		url = new URL(sURL);
		System.out.println("Calling: " + sURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Authorization", getApiKey());
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		
		if(conn.getResponseCode() != 204) {
			throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
		}
		
		conn.disconnect();
	}
	
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		if(!YFCCommon.isVoid(input)){
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eOrder = dInput.getDocumentElement();
			
			MiraklTranslation.getInstance(getMiraklTranslationDoc(), true);
			
			YFCDocument dMiraklInput = YFCDocument.createDocument("order");
			YFCElement eMirakl = dMiraklInput.getDocumentElement();
			
			createNodeTranslate(eMirakl, "channel_code", eOrder.getAttribute("EntryType"), false);
			createNodeTranslate(eMirakl, "locale", "en_US", false);
			
			createNode(eMirakl, "commercial_id", eOrder.getAttribute("OrderHeaderKey"));
			createNode(eMirakl, "payment_workflow", "PAY_ON_DELIVERY");
			createNode(eMirakl, "scored", "false");
			createNode(eMirakl, "shipping_zone_code", "DOM1");
			YFCElement eCustomer = eMirakl.createChild("customer");
			
		
			
			boolean first = true;
			for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
				if(first){
					createNode(eCustomer, "customer_id", eOrderLine.getChildElement("PersonInfoShipTo").getAttribute("EMailID"), "no_email");
					createNode(eCustomer, "email", eOrderLine.getChildElement("PersonInfoShipTo").getAttribute("EMailID"), "no_email");
					createNode(eCustomer, "firstname", eOrderLine.getChildElement("PersonInfoShipTo").getAttribute("FirstName"));
					createNode(eCustomer, "lastname", eOrderLine.getChildElement("PersonInfoShipTo").getAttribute("LastName"));
					if(!YFCCommon.isVoid(eOrderLine.getAttribute("ChainedFromOrderHeaderKey"))){
						YFCDocument dSalesOrder = YFCDocument.createDocument("Order");
						dSalesOrder.getDocumentElement().setAttribute("OrderHeaderKey", eOrderLine.getAttribute("ChainedFromOrderHeaderKey"));
						YFCDocument dSalesOrderOutput = MiraklUtils.callApi(env, dSalesOrder, this.getSalesOrderTemplate(), "getOrderDetails");
						if(!YFCCommon.isVoid(dSalesOrderOutput)){
							YFCElement eSalesOrder = dSalesOrderOutput.getDocumentElement();
							if(!YFCCommon.isVoid(eSalesOrder) && !YFCCommon.isVoid(eSalesOrder.getChildElement("PersonInfoBillTo"))){
								createAddress(eCustomer, eSalesOrder.getChildElement("PersonInfoBillTo"), "billing_address");
								createNode(eCustomer, "customer_id", eSalesOrder.getChildElement("PersonInfoBillTo").getAttribute("EMailID"), "no_email");
								createNode(eCustomer, "email", eSalesOrder.getChildElement("PersonInfoBillTo").getAttribute("EMailID"), "no_email");
								createNode(eCustomer, "firstname", eSalesOrder.getChildElement("PersonInfoBillTo").getAttribute("FirstName"));
								createNode(eCustomer, "lastname", eSalesOrder.getChildElement("PersonInfoBillTo").getAttribute("LastName"));
							} 
						}
					}
					if(YFCCommon.isVoid(eCustomer.getChildElement("billing_address"))){
						createAddress(eCustomer, eOrder.getChildElement("PersonInfoBillTo"), "billing_address");
					}
					
					createAddress(eCustomer, eOrderLine.getChildElement("PersonInfoShipTo"), "shipping_address");
					
					first = false;
				}
				YFCElement eOffer = eMirakl.getChildElement("offers", true).createChild("offer");
				createNodeTranslate(eOffer, "currency_iso_code", eOrder.getChildElement("PriceInfo").getAttribute("Currency", "USD"), false, "USD");
				createNode(eOffer, "offer_id", eOrderLine.getChildElement("Item").getAttribute("ItemID").replace("MKO_", ""));
				createNodeTranslate(eOffer, "shipping_type_code", eOrderLine.getAttribute("CarrierServiceCode"), false, "STD");
				createNode(eOffer, "quantity", eOrderLine.getAttribute("OrderedQty"));
				createNode(eOffer, "order_line_id", eOrderLine.getAttribute("OrderLineKey"));
				
				YFCElement eChainedLine = eOrderLine.getChildElement("ChainedFromOrderLine", true);
				createNode(eOffer, "price", eChainedLine.getChildElement("ComputedPrice").getAttribute("ExtendedPrice"), "0.00");
				createNode(eOffer, "offer_price", eChainedLine.getChildElement("ComputedPrice").getAttribute("UnitPrice"), "0.00");
				
				boolean shippingFound = false;
				for(YFCElement eCharge : eChainedLine.getChildElement("LineCharges", true).getChildren()){
					if(eCharge.getBooleanAttribute("IsShippingCharge", false)){
						createNode(eOffer, "shipping_price", eCharge.getAttribute("ChargeAmount", "0.00"));
						shippingFound = true;
					}
				}
				if(!shippingFound){
					createNode(eOffer, "shipping_price", "0.00");
				}
				YFCElement eTaxes = eOffer.createChild("taxes");
				for(YFCElement eTax : eChainedLine.getChildElement("LineTaxes", true).getChildren()){
					if(eTax.getDoubleAttribute("Tax", 0) > 0){
						if(!eTax.getAttribute("TaxName").equals("Shipping Tax")){
							YFCElement eNewTax = eTaxes.createChild("tax");
							createNode(eNewTax, "amount", eTax.getAttribute("Tax", "0.00"));
							createNodeTranslate(eNewTax, "code", eTax.getAttribute("TaxName", "SalesTax"), false);
						} else {
							YFCElement eShippngTax = eOffer.createChild("shipping_taxes").createChild("shipping_tax");
							createNode(eShippngTax, "amount", eTax.getAttribute("Tax", "0.00"));
							createNodeTranslate(eShippngTax, "code", eTax.getAttribute("TaxName", "Shipping Tax"), false);
						}						
					}
				}
				
			}
			try {
				YFCDocument response = postOrder(dMiraklInput);
				updateShipNode(env, response);
				Thread.sleep(1000);
				confirmOrder(eOrder.getAttribute("OrderHeaderKey"));
				
				return response.getDocument();
			} catch (Exception e){
				throw new Exception("Invalid Response from Mirakl", e);
			}
			
		}
		return input;		
	}

	private void updateShipNode(YFSEnvironment env, YFCDocument dMiraklResponse){
		System.out.println("Update Shipnode on Order");
		if(!YFCCommon.isVoid(dMiraklResponse)){
			for(YFCElement eOrder : dMiraklResponse.getDocumentElement().getChildElement("orders", true).getChildren()){
				YFCDocument dUpdate = YFCDocument.createDocument("Order");
				YFCElement eUpdate = dUpdate.getDocumentElement();
				eUpdate.setAttribute("OrderHeaderKey", eOrder.getChildElement("commercial_id").getNodeValue());
				eUpdate.setAttribute("ShipNode", "MRK_SHOP_" + eOrder.getChildElement("shop_id").getNodeValue());
				eUpdate.setAttribute("Override", "Y");
				for(YFCElement eOrderLine : eOrder.getChildElement("order_lines", true).getChildren()){
					YFCElement eUpdateLine = eUpdate.getChildElement("OrderLines", true).createChild("OrderLine");
					eUpdateLine.setAttribute("OrderLineKey", eOrderLine.getChildElement("order_line_id", true).getNodeValue());
					eUpdateLine.setAttribute("ShipNode", "MRK_SHOP_" + eOrder.getChildElement("shop_id").getNodeValue());
					eUpdateLine.setAttribute("Override", "Y");
					eUpdateLine.getChildElement("CustomAttributes", true).setAttribute(getMiraklOrderID(), eOrder.getChildElement("order_id").getNodeValue());
				}
				System.out.println("Call changeOrder: " + dUpdate);
				MiraklUtils.callApi(env, dUpdate, null, "changeOrder");
			}
		}
	}
	
	private void createAddress(YFCElement eMiraklCustomer, YFCElement ePersonInfo, String sRootNode){
		YFCElement eAddress = eMiraklCustomer.createChild(sRootNode);
		createNode(eAddress, "city", ePersonInfo.getAttribute("City"));
		createNode(eAddress, "country", ePersonInfo.getAttribute("Country"));
		createNode(eAddress, "civility", ePersonInfo.getAttribute("Title"), "Mr");
		createNode(eAddress, "firstname", ePersonInfo.getAttribute("FirstName"), "Aurora");
		createNode(eAddress, "lastname", ePersonInfo.getAttribute("LastName"), "Customer");
		createNode(eAddress, "phone", ePersonInfo.getAttribute("DayPhone"));
		createNode(eAddress, "phone_secondary", ePersonInfo.getAttribute("EveningPhone"));
		createNode(eAddress, "state", ePersonInfo.getAttribute("State"));
		createNode(eAddress, "street_1", ePersonInfo.getAttribute("AddressLine1"));
		createNode(eAddress, "street_2", ePersonInfo.getAttribute("AddressLine2"));
		createNode(eAddress, "zip_code", ePersonInfo.getAttribute("ZipCode"));
		createNodeTranslate(eAddress, "country_iso_code", ePersonInfo.getAttribute("Country"), true);		
	}

}
