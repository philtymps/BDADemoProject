package com.objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.CallInteropServlet;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;

public class Order {

	private static HashMap<String, Order> orders;
	private static ArrayList<String> aSkus;
	public static HashMap<String, Order> getOrders(){
		if(orders == null){
			loadOrders();
		}
		return orders;
	}
	public static boolean isItemUsed(String sSku){
		if(aSkus == null){
			loadOrders();
		}
		return aSkus.contains(sSku);
	}
	private static void loadOrders(){
		orders = new HashMap<String, Order>();
		aSkus = new ArrayList<String>();
		BufferedReader br = null;
	    String line = "";
	    String cvsSplitBy = ",";
	    try {
		    br = new BufferedReader(new FileReader("/Users/pfaiola/bonton_data/ff_inbalance_ecommerce_orders1.out"));
		    while ((line = br.readLine()) != null) {
		    	try {    // use comma as separator
		    		String[] orderLine = line.split(cvsSplitBy);
		    		if(!orders.containsKey(orderLine[1].trim())){
		    			orders.put(orderLine[1].trim(), new Order(orderLine[1].trim(), orderLine[5].trim(), "Bonton"));
		    		}
		    		Order o = orders.get(orderLine[1].trim());
		    		aSkus.add(orderLine[2].trim());
		    		o.addOrderLine(new OrderLine(Integer.parseInt(orderLine[3].trim()), orderLine[2].trim(), orderLine[4].trim(), orderLine[5].trim()));
		    	} catch (Exception e){
		    		e.printStackTrace();
		    	}
	        }
	   } catch (Exception e){
		   e.printStackTrace();
	   }
	    System.out.println("Loaded " + orders.size() + " Orders : " + aSkus.size() + " items");
	}
	
	public static void main(String[] args){
		HashMap<String, Order> orders = Order.getOrders();
		for(Order o : orders.values()){
			 System.out.println(CallInteropServlet.invokeApi(o.getOrderXml(), null, "createOrder", "http://oms.omfulfillment.com:9080"));
		}
	}
	
	public Order(String order_no, String zip_code, String enterprise_code) {
		super();
		this.order_no = order_no;
		this.zip_code = zip_code;
		this.enterprise_code = enterprise_code;
	}
	
	public YFCDocument getOrderXml(){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("DocumentType", "0001");
		eOrder.setAttribute("EnterpriseCode", this.getEnterprise_code());
		eOrder.setAttribute("DraftOrderFlag", "N");
		eOrder.setAttribute("AuthorizedClient", "web");
		eOrder.setAttribute("EntryType", "Web");
		eOrder.setAttribute("OrderType",  "Regular");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("Currency", "USD");
		int count = 1;
		for(OrderLine orderLine : this.getOrderLines()){
			orderLine.createLine(eOrder.getChildElement("OrderLines", true), count++);
		}
		//createLine(eOrder.getChildElement("OrderLines", true), 1);
		createAddress("PersonInfoBillTo", eOrder);
		createAddress("PersonInfoShipTo", eOrder);
		
		/*YFCElement ePayment = eOrder.createChild("PaymentMethods").createChild("PaymentMethod");
		ePayment.setAttribute("CreditCardExpDate", "12/2023");
		ePayment.setAttribute("CreditCardName", "Bon Ton");
		ePayment.setAttribute("CreditCardNo", "4111111111111111");
		ePayment.setAttribute("CreditCardType", "VISA");
		ePayment.setAttribute("PaymentType", "CREDIT_CARD");
		ePayment.setAttribute("UnlimitedCharges", "Y");*/
		return dOrder;
	}
	
	private void createAddress(String NodeName, YFCElement eOrder){
		YFCElement eAdd = eOrder.createChild(NodeName);
		eAdd.setAttribute("FirstName", "Bon");
		eAdd.setAttribute("LastName", "Ton");
		eAdd.setAttribute("City", this.getCity());
		eAdd.setAttribute("State", this.getState());
		eAdd.setAttribute("ZipCode", this.getZip_code());
		eAdd.setAttribute("Country", "US");
	}
	
	
	
	private String order_no, zip_code, city, state, enterprise_code;
	private ArrayList<OrderLine> orderLines;
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getEnterprise_code() {
		return enterprise_code;
	}
	public void setEnterprise_code(String enterprise_code) {
		this.enterprise_code = enterprise_code;
	}
	public ArrayList<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void addOrderLine(OrderLine oLine){
		if(orderLines == null){
			orderLines = new ArrayList<OrderLine>();
		}
		orderLines.add(oLine);
	}
	public String getCity(){
		if(city == null){
			try {
				getCityState();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return city;
	}
	public String getState(){
		if(state == null){
			try {
				getCityState();
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
		return state;
	}
	private void getCityState() throws Exception{
		/*int responseCode = 0;
	    String api = "https://www.zipcodeapi.com/rest/CvHcbOm2blI91DfBOEFQJOYQw4kPzEwbadol1Knc66m8JCJxgon0clQJqbyZQ4b0/info.xml/" + this.zip_code + "/degrees";
	    
	    System.out.println("URL : "+api);
	    URL url = new URL(api);
	    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	    httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    if(responseCode == 200)
	    {
	      YFCDocument document = YFCDocument.parse(httpConnection.getInputStream());
	      YFCElement eResponse = document.getDocumentElement();
	      city = eResponse.getChildElement("city").getNodeValue();
	      state = eResponse.getChildElement("state").getNodeValue();
	    }*/
	}
}
