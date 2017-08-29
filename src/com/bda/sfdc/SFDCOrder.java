package com.bda.sfdc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;

public class SFDCOrder {

	public static void main(String[] args){
		SFDCOrder o = new SFDCOrder("2017041214215723269327", "OM1000025", "jsmith@jsmith.com", "Created", 43.56f, new Timestamp(System.currentTimeMillis()));
		try {
			o.sendToSFDC("AuroraTel");
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public SFDCOrder(String sOrderHeaderKey, String sOrderNo, String sCustomerEmail, String sStatus, float fOrderTotal,
			Timestamp orderDate) {
		super();
		this.sOrderHeaderKey = sOrderHeaderKey;
		this.sOrderNo = sOrderNo;
		this.sCustomerEmail = sCustomerEmail;
		this.sStatus = sStatus;
		this.fOrderTotal = fOrderTotal;
		this.orderDate = orderDate;
	}
	
	String sOrderHeaderKey, sOrderNo, sCustomerEmail, sStatus;
	float fOrderTotal;
	Timestamp orderDate;
	
	public String getOrderHeaderKey() {
		return sOrderHeaderKey;
	}
	public void setOrderHeaderKey(String sOrderHeaderKey) {
		this.sOrderHeaderKey = sOrderHeaderKey;
	}
	public String getOrderNo() {
		return sOrderNo;
	}
	public void setOrderNo(String sOrderNo) {
		this.sOrderNo = sOrderNo;
	}
	public String getCustomerEmail() {
		return sCustomerEmail;
	}
	public void setCustomerEmail(String sCustomerEmail) {
		this.sCustomerEmail = sCustomerEmail;
	}
	public String getStatus() {
		return sStatus;
	}
	public void setStatus(String sStatus) {
		this.sStatus = sStatus;
	}
	public float getOrderTotal() {
		return fOrderTotal;
	}
	public void setOrderTotal(float fOrderTotal) {
		this.fOrderTotal = fOrderTotal;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getDate(){
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(getOrderDate());
	}
	
	public JSONObject getSFDCData() throws JSONException {
		JSONObject jsonInputObject = new JSONObject();
		jsonInputObject.put("Name", this.getOrderNo());
		jsonInputObject.put("Email__c", this.getCustomerEmail());
		jsonInputObject.put("Order_Number__c", this.getOrderNo());
		jsonInputObject.put("Status__c", this.getStatus());
		jsonInputObject.put("Ordered_Date__c", this.getDate());
		jsonInputObject.put("Order_Total__c", this.getOrderTotal());
		
		return jsonInputObject;
	}
	
	public void sendToSFDC(String sEnterpriseCode) throws JSONException{
		String sURL = SFDCUtils.getConfiguredAuthSite(sEnterpriseCode) + "/services/data/v39.0/sobjects/Order__c/Order_Header_Key__c/" + this.getOrderHeaderKey();
		JSONObject jsonInputObject = new JSONObject();
		jsonInputObject.put("Name", this.getOrderNo());
		jsonInputObject.put("Email__c", this.getCustomerEmail());
		jsonInputObject.put("Order_Number__c", this.getOrderNo());
		jsonInputObject.put("Status__c", this.getStatus());
		jsonInputObject.put("Ordered_Date__c", this.getDate());
		jsonInputObject.put("Order_Total__c", this.getOrderTotal());
		
		try {
			SFDCUtils.updateRecord(sURL, jsonInputObject.toString(), "AuroraTel");
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
