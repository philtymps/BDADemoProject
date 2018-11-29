package com.extension.sci.object;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;

public class SCILocation extends SCIObject {

	public SCILocation() {
		super();
		//this.setString("source", "OMSSynch");
	}
	
	public SCILocation(YFCElement eOrganization) {
		super();
		//this.setString("source", "OMSSynch");
		this.setID(eOrganization.getAttribute("OrganizationCode"));
		this.setLocationName(eOrganization.getAttribute("OrganizationName"));
		YFCElement eNode = eOrganization.getChildElement("Node", true);
		this.setCoordinates(eNode.getAttribute("Latitude"), eNode.getAttribute("Longitude"));
		this.setLocationType(eNode.getAttribute("NodeType"));
		YFCElement eAddress = eNode.getChildElement("ShipNodePersonInfo", true);
		this.setCity(eAddress.getAttribute("City"));
		this.setPostalCode(eAddress.getAttribute("ZipCode"));
		this.setStreet(eAddress.getAttribute("AddressLine1"));
		this.setStateProvince(eAddress.getAttribute("State"));
		this.setCountry(eAddress.getAttribute("Country"));
	}
	
	public static YFCDocument getTemplateForOMS() throws SAXException, IOException {
		return YFCDocument.parse(SCILocation.class.getResourceAsStream("templates/getOrganizationList.xml"));
	}
	@Override
	public String getBulkAPIURL() {
		return "/api/locations/bulk";
	}

	public String getID() {
		return getString("_id");
	}
	public void setID(String _id) {
		setString("_id", _id);
	}
	public String getCity() {
		return getString("city");
	}
	public void setCity(String value) {
		setString("city", value);
	}
	public String getCoordinates() {
		return getString("coordinates");
	}
	public void setCoordinates(String sLatitude, String sLongitude) {
		setString("coordinates", sLatitude + ", " + sLongitude);
	}
	public String getLocationName() {
		return getString("locationName");
	}
	public void setLocationName(String value) {
		setString("locationName", value);
	}
	public String getLocationType() {
		return getString("locationType");
	}
	public void setLocationType(String value) {
		setString("locationType", value);
	}
	public String getPostalCode() {
		return getString("postalCode");
	}
	public void setPostalCode(String value) {
		setString("postalCode", value);
	}
	public String getStateProvince() {
		return getString("stateProvince");
	}
	public void setStateProvince(String value) {
		setString("stateProvince", value);
	}
	public String getStreet() {
		return getString("street");
	}
	public void setStreet(String value) {
		setString("street", value);
	}
	public String getCountry() {
		return getString("country");
	}
	public void setCountry(String value) {
		setString("country", value);
	}
	

	
}
