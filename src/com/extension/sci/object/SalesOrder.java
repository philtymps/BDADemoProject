package com.extension.sci.object;

import java.util.HashMap;

import org.json.JSONObject;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SalesOrder extends SCIObject {

	public SalesOrder(YFCElement eOrderLine, YFCElement eOrder){
		super(eOrderLine.getAttribute("OrderLineKey"));
		if(!YFCCommon.isVoid(eOrder.getAttribute("BillToID"))){
			strings.put("customer", eOrder.getAttribute("BillToID"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("CustomerPONo"))){
			strings.put("customerPoNumber", eOrder.getAttribute("CustomerPONo"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("Status"))){
			strings.put("orderStatus", eOrder.getAttribute("Status"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getChildElement("Item", true).getAttribute("ItemID"))){
			strings.put("product", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("OrderDate"))){
			dates.put("datePlaced", eOrder.getYDateAttribute("OrderDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ExpectedDeliveryDate"))){
			dates.put("estimatedDeliveryDate", eOrderLine.getYDateAttribute("ExpectedDeliveryDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ExpectedShipmentDate"))){
			dates.put("plannedShipDate", eOrderLine.getYDateAttribute("ExpectedShipmentDate"));
		}
	}
	
	public SalesOrder(String orderLineKey, String customer, String customerPoNumber, String orderStatus, String product,
			String shipment, YDate datePlaced, YDate estimatedDeliveryDate, YDate plannedShipDate,
			YDate requestedDeliveryDate, YDate requestedShipDate, double orderValue, double quantity) {
		super(orderLineKey);
		strings = new HashMap<String, String>();
		dates = new HashMap<String, YDate>();
		doubles = new HashMap<String, Double>();
		
		strings.put("customer", customer);
		strings.put("customerPoNumber", customerPoNumber);
		strings.put("orderStatus", orderStatus);
		strings.put("product", product);
		strings.put("shipment", shipment);
		dates.put("datePlaced", datePlaced);
		dates.put("estimatedDeliveryDate", estimatedDeliveryDate);
		dates.put("plannedShipDate", plannedShipDate);
		dates.put("requestedDeliveryDate", requestedDeliveryDate);
		dates.put("requestedShipDate", requestedShipDate);
		doubles.put("orderValue", orderValue);
		doubles.put("quantity", quantity);
	}
	private HashMap<String, String> strings;
	private HashMap<String, YDate> dates;
	private HashMap<String, Double> doubles;
		
	public String getCustomer() {
		return strings.get("customer");
	}
	public void setCustomer(String customer) {
		strings.put("customer", customer);
	}
	public String getCustomerPoNumber() {
		return strings.get("customerPoNumber");
	}
	public void setCustomerPoNumber(String customerPoNumber) {
		strings.put("customerPoNumber", customerPoNumber);
	}
	public String getOrderStatus() {
		return strings.get("orderStatus");
	}
	public void setOrderStatus(String orderStatus) {
		strings.put("orderStatus", orderStatus);
	}
	public String getProduct() {
		return strings.get("product");
	}
	public void setProduct(String product) {
		strings.put("product", product);
	}
	public String getShipment() {
		return strings.get("shipment");
	}
	public void setShipment(String shipment) {
		strings.put("shipment", shipment);
	}
	public YDate getDatePlaced() {
		return dates.get("datePlaced");
	}
	public void setDatePlaced(YDate datePlaced) {
		dates.put("datePlaced", datePlaced);
	}
	public YDate getEstimatedDeliveryDate() {
		return dates.get("estimatedDeliveryDate");
	}
	public void setEstimatedDeliveryDate(YDate estimatedDeliveryDate) {
		dates.put("estimatedDeliveryDate", estimatedDeliveryDate);
	}
	public YDate getPlannedShipDate() {
		return dates.get("plannedShipDate");
	}
	public void setPlannedShipDate(YDate plannedShipDate) {
		dates.put("plannedShipDate", plannedShipDate);
	}
	public YDate getRequestedDeliveryDate() {
		return dates.get("requestedDeliveryDate");
	}
	public void setRequestedDeliveryDate(YDate requestedDeliveryDate) {
		dates.put("requestedDeliveryDate", requestedDeliveryDate);
	}
	public YDate getRequestedShipDate() {
		return dates.get("requestedShipDate");
	}
	public void setRequestedShipDate(YDate requestedShipDate) {
		dates.put("requestedShipDate", requestedShipDate);
	}
	public double getOrderValue() {
		return doubles.get("orderValue");
	}
	public void setOrderValue(double orderValue) {
		doubles.put("orderValue", orderValue);
	}
	public double getQuantity() {
		return doubles.get("quantity");
	}
	public void setQuantity(double quantity) {
		doubles.put("quantity", quantity);
	}
	@Override
	public String getBulkAPIURL() {
		return "/api/salesorders/bulk";
	}

	
	@Override
	public JSONObject getBulkObject() {
		JSONObject obj = new JSONObject();
		for(String key : strings.keySet()){
			obj.put(key, strings.get(key));
		}
		for(String key : dates.keySet()){
			obj.put(key, dates.get(key).getString("yyyy-MM-ddThh:mm:ss"));
		}
		for(String key : doubles.keySet()){
			obj.put(key, doubles.keySet());
		}
		return obj;
	}
	
}
