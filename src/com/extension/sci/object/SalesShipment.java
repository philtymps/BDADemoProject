package com.extension.sci.object;

import java.util.HashMap;

import org.json.JSONObject;

import com.yantra.yfc.date.YDate;

public class SalesShipment extends SCIObject {

	public SalesShipment(String _internalID){
		super(_internalID);
		dates = new HashMap<String, YDate>();
		strings = new HashMap<String, String>();
		doubles = new HashMap<String, Double>();
	}
	
	public SalesShipment(String _internalID, YDate actualShipDate, YDate actualTimeofArrival,
			YDate committedTimeOfArrival, YDate estimatedTimeOfArrival, YDate predicatedTimeOfArrival,
			YDate requestedTimeOfArrival, String airwayMasterNumber, String billOfLadingNumber, String carrier,
			String carrierContainer, String currentLocationCoordinates, String currentRegion, String destination,
			String expectedPathOfShipment, String freightForwarder, String houseAirwayBill, String origin,
			String parcelTrackingNumber, String predictedTimeOfArrivalLocation, String salesOrder,
			String transportDescription, String transportMode, boolean expeditedShipping, double shippingCost,
			double expeditedShippingCost) {
		super(_internalID);
		dates = new HashMap<String, YDate>();
		strings = new HashMap<String, String>();
		doubles = new HashMap<String, Double>();
		
		dates.put("actualShipDate", actualShipDate);
		dates.put("actualTimeofArrival", actualTimeofArrival);
		dates.put("committedTimeOfArrival", committedTimeOfArrival);
		dates.put("estimatedTimeOfArrival", estimatedTimeOfArrival);
		dates.put("predicatedTimeOfArrival", predicatedTimeOfArrival);
		dates.put("requestedTimeOfArrival", requestedTimeOfArrival);
		strings.put("airwayMasterNumber", airwayMasterNumber);
		strings.put("billOfLadingNumber", billOfLadingNumber);
		strings.put("carrier", carrier);
		strings.put("carrierContainer", carrierContainer);
		strings.put("currentLocationCoordinates", currentLocationCoordinates);
		strings.put("currentRegion", currentRegion);
		strings.put("destination", destination);
		strings.put("expectedPathOfShipment", expectedPathOfShipment);
		strings.put("freightForwarder", freightForwarder);
		strings.put("houseAirwayBill", houseAirwayBill);
		strings.put("origin", origin);
		strings.put("parcelTrackingNumber", parcelTrackingNumber);
		strings.put("predictedTimeOfArrivalLocation", predictedTimeOfArrivalLocation);
		strings.put("salesOrder", salesOrder);
		strings.put("transportDescription", transportDescription);
		strings.put("transportMode", transportMode);
		this.expeditedShipping = expeditedShipping;
		doubles.put("shippingCost", shippingCost);
		doubles.put("expeditedShippingCost", expeditedShippingCost);
	}
	
	private HashMap<String, YDate> dates;
	private HashMap<String, String> strings;
	private HashMap<String, Double> doubles;
	
	private boolean expeditedShipping = false;
	public YDate getActualShipDate() {
		return dates.get("actualShipDate");
	}
	public void setActualShipDate(YDate actualShipDate) {
		dates.put("actualShipDate", actualShipDate);
	}
	public YDate getActualTimeofArrival() {
		return dates.get("actualTimeofArrival");
	}
	public void setActualTimeofArrival(YDate actualTimeofArrival) {
		dates.put("actualTimeofArrival", actualTimeofArrival);
	}
	public YDate getCommittedTimeOfArrival() {
		return dates.get("committedTimeOfArrival");
	}
	public void setCommittedTimeOfArrival(YDate committedTimeOfArrival) {
		dates.put("committedTimeOfArrival", committedTimeOfArrival);
	}
	public YDate getEstimatedTimeOfArrival() {
		return dates.get("estimatedTimeOfArrival");
	}
	public void setEstimatedTimeOfArrival(YDate estimatedTimeOfArrival) {
		dates.put("estimatedTimeOfArrival", estimatedTimeOfArrival);
	}
	public YDate getPredicatedTimeOfArrival() {
		return dates.get("predicatedTimeOfArrival");
	}
	public void setPredicatedTimeOfArrival(YDate predicatedTimeOfArrival) {
		dates.put("predicatedTimeOfArrival", predicatedTimeOfArrival);
	}
	public YDate getRequestedTimeOfArrival() {
		return dates.get("RequestedTimeOfArrival");
	}
	public void setRequestedTimeOfArrival(YDate requestedTimeOfArrival) {
		dates.put("requestedTimeOfArrival", requestedTimeOfArrival);
	}
	public String getAirwayMasterNumber() {
		return strings.get("airwayMasterNumber");
	}
	public void setAirwayMasterNumber(String airwayMasterNumber) {
		strings.put("airwayMasterNumber", airwayMasterNumber);
	}
	public String getBillOfLadingNumber() {
		return strings.get("billOfLadingNumber");
	}
	public void setBillOfLadingNumber(String billOfLadingNumber) {
		strings.put("billOfLadingNumber", billOfLadingNumber);
	}
	public String getCarrier() {
		return strings.get("carrier");
	}
	public void setCarrier(String carrier) {
		strings.put("carrier", carrier);
	}
	public String getCarrierContainer() {
		return strings.get("carrierContainer");
	}
	public void setCarrierContainer(String carrierContainer) {
		strings.put("carrierContainer", carrierContainer);
	}
	public String getCurrentLocationCoordinates() {
		return strings.get("currentLocationCoordinates");
	}
	public void setCurrentLocationCoordinates(String currentLocationCoordinates) {
		strings.put("currentLocationCoordinates", currentLocationCoordinates);
	}
	public String getCurrentRegion() {
		return strings.get("currentRegion");
	}
	public void setCurrentRegion(String currentRegion) {
		strings.put("currentRegion", currentRegion);
	}
	public String getDestination() {
		return strings.get("destination");
	}
	public void setDestination(String destination) {
		strings.put("destination", destination);
	}
	public String getExpectedPathOfShipment() {
		return strings.get("expectedPathOfShipment");
	}
	public void setExpectedPathOfShipment(String expectedPathOfShipment) {
		strings.put("expectedPathOfShipment", expectedPathOfShipment);
	}
	public String getFreightForwarder() {
		return strings.get("freightForwarder");
	}
	public void setFreightForwarder(String freightForwarder) {
		strings.put("freightForwarder", freightForwarder);
	}
	public String getHouseAirwayBill() {
		return strings.get("houseAirwayBill");
	}
	public void setHouseAirwayBill(String houseAirwayBill) {
		strings.put("houseAirwayBill", houseAirwayBill);
	}
	public String getOrigin() {
		return strings.get("origin");
	}
	public void setOrigin(String origin) {
		strings.put("origin", origin);
	}
	public String getParcelTrackingNumber() {
		return strings.get("parcelTrackingNumber");
	}
	public void setParcelTrackingNumber(String parcelTrackingNumber) {
		strings.put("parcelTrackingNumber", parcelTrackingNumber);
	}
	public String getPredictedTimeOfArrivalLocation() {
		return strings.get("predictedTimeOfArrivalLocation");
	}
	public void setPredictedTimeOfArrivalLocation(String predictedTimeOfArrivalLocation) {
		strings.put("predictedTimeOfArrivalLocation", predictedTimeOfArrivalLocation);
	}
	public String getSalesOrder() {
		return strings.get("salesOrder");
	}
	public void setSalesOrder(String salesOrder) {
		strings.put("salesOrder", salesOrder);
	}
	public String getTransportDescription() {
		return strings.get("transportDescription");
	}
	public void setTransportDescription(String transportDescription) {
		strings.put("transportDescription", transportDescription);
	}
	public String getTransportMode() {
		return strings.get("transportMode");
	}
	public void setTransportMode(String transportMode) {
		strings.put("transportMode", transportMode);
	}
	public boolean isExpeditedShipping() {
		return expeditedShipping;
	}
	public void setExpeditedShipping(boolean expeditedShipping) {
		this.expeditedShipping = expeditedShipping;
	}
	public double getShippingCost() {
		return doubles.get("shippingCost");
	}
	public void setShippingCost(double shippingCost) {
		doubles.put("shippingCost", shippingCost);
	}
	public double getExpeditedShippingCost() {
		return doubles.get("expeditedShippingCost");
	}
	public void setExpeditedShippingCost(double expeditedShippingCost) {
		doubles.put("expeditedShippingCost", expeditedShippingCost);
	}

	@Override
	public String getBulkAPIURL() {
		return "/api/saleshipments/bulk";
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
		obj.put("expeditedShipping", expeditedShipping ? "Yes" : "No");
		return obj;
	}
}