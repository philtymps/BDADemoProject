package com.extension.sci.object;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SCISalesShipment extends SCIObject {

	public SCISalesShipment(){
		super();
	}
	
	public SCISalesShipment(YFCElement eShipment){
		super();
		this.setString("_id", eShipment.getAttribute("ShipmentKey"));
			
		if(!YFCCommon.isVoid(eShipment.getAttribute("ActualShipmentDate"))){
			setDate("actualShipDate", eShipment.getYDateAttribute("ActualShipmentDate"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("ActualDeliveryDate"))){
			setDate("actualTimeOfArrival", eShipment.getYDateAttribute("ActualDeliveryDate"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("ExpectedDeliveryDate"))){
			setDate("committedTimeOfArrival", eShipment.getYDateAttribute("ExpectedDeliveryDate"));
			setDate("estimatedDeliveryDate", eShipment.getYDateAttribute("ExpectedDeliveryDate"));
			setDate("predictedDeliveryDate", eShipment.getYDateAttribute("ExpectedDeliveryDate"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("RequestedDeliveryDate"))){
			setDate("requestedTimeOfArrival", eShipment.getYDateAttribute("RequestedDeliveryDate"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("SCAC"))){
			setString("carrier", eShipment.getAttribute("SCAC"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("ReceivingNode"))){
			setString("destination", eShipment.getAttribute("ReceivingNode"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("ShipNode"))){
			setString("origin", eShipment.getAttribute("ShipNode"));
		}
		if(!YFCCommon.isVoid(eShipment.getChildElement("ShipmentLines", true).getChildElement("ShipmentLine"))){
			setString("salesOrder", eShipment.getChildElement("ShipmentLines", true).getChildElement("ShipmentLine").getAttribute("OrderNo"));
		}
		if(!YFCCommon.isVoid(eShipment.getAttribute("CarrierServiceCode"))){
			setBoolean("expeditedShipping", eShipment.getAttribute("CarrierServiceCode").toLowerCase().contains("express") || eShipment.getAttribute("CarrierServiceCode").toLowerCase().contains("priority"));		
		} else {
			setBoolean("expeditedShipping", false);
		}		
	}
	
	public static YFCDocument getShipmentListTemplate(){
		YFCDocument dShipmentList = YFCDocument.createDocument("ShipmentList");
		YFCElement eShipment = dShipmentList.getDocumentElement().createChild("Shipment");
		eShipment.setAttribute("ActualShipmentDate", "");
		eShipment.setAttribute("ActualDeliveryDate", "");
		eShipment.setAttribute("ExpectedDeliveryDate", "");
		eShipment.setAttribute("RequestedDeliveryDate", "");
		eShipment.setAttribute("SCAC", "");
		eShipment.setAttribute("ReceivingNode", "");
		eShipment.setAttribute("ShipNode", "");
		eShipment.setAttribute("CarrierServiceCode", "");
		eShipment.setAttribute("RequestedShipmentDate", "");
		eShipment.setAttribute("Status", "");
		eShipment.setAttribute("StatusDate", "");
		eShipment.setAttribute("TotalEstimatedCharge", "");
		eShipment.setAttribute("TotalActualCharge", "");
		YFCElement eShipmentLine = eShipment.createChild("ShipmentLines").createChild("ShipmentLine");
		eShipmentLine.setAttribute("OrderLineKey", "");
		eShipmentLine.setAttribute("OrderHeaderKey", "");
		eShipmentLine.setAttribute("OrderNo", "");
		return dShipmentList;
	}
	
	
	public YDate getActualShipDate() {
		return getDate("actualShipDate");
	}
	public void setActualShipDate(YDate actualShipDate) {
		setDate("actualShipDate", actualShipDate);
	}
	public YDate getActualTimeofArrival() {
		return getDate("actualTimeofArrival");
	}
	public void setActualTimeofArrival(YDate actualTimeofArrival) {
		setDate("actualTimeofArrival", actualTimeofArrival);
	}
	public YDate getCommittedTimeOfArrival() {
		return getDate("committedTimeOfArrival");
	}
	public void setCommittedTimeOfArrival(YDate committedTimeOfArrival) {
		setDate("committedTimeOfArrival", committedTimeOfArrival);
	}
	public YDate getEstimatedTimeOfArrival() {
		return getDate("estimatedTimeOfArrival");
	}
	public void setEstimatedTimeOfArrival(YDate estimatedTimeOfArrival) {
		setDate("estimatedTimeOfArrival", estimatedTimeOfArrival);
	}
	public YDate getPredicatedTimeOfArrival() {
		return getDate("predicatedTimeOfArrival");
	}
	public void setPredicatedTimeOfArrival(YDate predicatedTimeOfArrival) {
		setDate("predicatedTimeOfArrival", predicatedTimeOfArrival);
	}
	public YDate getRequestedTimeOfArrival() {
		return getDate("RequestedTimeOfArrival");
	}
	public void setRequestedTimeOfArrival(YDate requestedTimeOfArrival) {
		setDate("requestedTimeOfArrival", requestedTimeOfArrival);
	}
	public String getAirwayMasterNumber() {
		return getString("airwayMasterNumber");
	}
	public void setAirwayMasterNumber(String airwayMasterNumber) {
		setString("airwayMasterNumber", airwayMasterNumber);
	}
	public String getBillOfLadingNumber() {
		return getString("billOfLadingNumber");
	}
	public void setBillOfLadingNumber(String billOfLadingNumber) {
		setString("billOfLadingNumber", billOfLadingNumber);
	}
	public String getCarrier() {
		return getString("carrier");
	}
	public void setCarrier(String carrier) {
		setString("carrier", carrier);
	}
	public String getCarrierContainer() {
		return getString("carrierContainer");
	}
	public void setCarrierContainer(String carrierContainer) {
		setString("carrierContainer", carrierContainer);
	}
	public String getCurrentLocationCoordinates() {
		return getString("currentLocationCoordinates");
	}
	public void setCurrentLocationCoordinates(String currentLocationCoordinates) {
		setString("currentLocationCoordinates", currentLocationCoordinates);
	}
	public String getCurrentRegion() {
		return getString("currentRegion");
	}
	public void setCurrentRegion(String currentRegion) {
		setString("currentRegion", currentRegion);
	}
	public String getDestination() {
		return getString("destination");
	}
	public void setDestination(String destination) {
		setString("destination", destination);
	}
	public String getExpectedPathOfShipment() {
		return getString("expectedPathOfShipment");
	}
	public void setExpectedPathOfShipment(String expectedPathOfShipment) {
		setString("expectedPathOfShipment", expectedPathOfShipment);
	}
	public String getFreightForwarder() {
		return getString("freightForwarder");
	}
	public void setFreightForwarder(String freightForwarder) {
		setString("freightForwarder", freightForwarder);
	}
	public String getHouseAirwayBill() {
		return getString("houseAirwayBill");
	}
	public void setHouseAirwayBill(String houseAirwayBill) {
		setString("houseAirwayBill", houseAirwayBill);
	}
	public String getOrigin() {
		return getString("origin");
	}
	public void setOrigin(String origin) {
		setString("origin", origin);
	}
	public String getParcelTrackingNumber() {
		return getString("parcelTrackingNumber");
	}
	public void setParcelTrackingNumber(String parcelTrackingNumber) {
		setString("parcelTrackingNumber", parcelTrackingNumber);
	}
	public String getPredictedTimeOfArrivalLocation() {
		return getString("predictedTimeOfArrivalLocation");
	}
	public void setPredictedTimeOfArrivalLocation(String predictedTimeOfArrivalLocation) {
		setString("predictedTimeOfArrivalLocation", predictedTimeOfArrivalLocation);
	}
	public String getSalesOrder() {
		return getString("salesOrder");
	}
	public void setSalesOrder(String salesOrder) {
		setString("salesOrder", salesOrder);
	}
	public String getTransportDescription() {
		return getString("transportDescription");
	}
	public void setTransportDescription(String transportDescription) {
		setString("transportDescription", transportDescription);
	}
	public String getTransportMode() {
		return getString("transportMode");
	}
	public void setTransportMode(String transportMode) {
		setString("transportMode", transportMode);
	}
	public boolean isExpeditedShipping() {
		return getBoolean("expeditedShipping");
	}
	public void setExpeditedShipping(boolean expeditedShipping) {
		setBoolean("expeditedShipping", expeditedShipping);
	}
	public double getShippingCost() {
		return getDouble("shippingCost");
	}
	public void setShippingCost(double shippingCost) {
		setDouble("shippingCost", shippingCost);
	}
	public double getExpeditedShippingCost() {
		return getDouble("expeditedShippingCost");
	}
	public void setExpeditedShippingCost(double expeditedShippingCost) {
		setDouble("expeditedShippingCost", expeditedShippingCost);
	}

	@Override
	public String getBulkAPIURL() {
		return "/api/saleshipments/bulk";
	}

	
	
}