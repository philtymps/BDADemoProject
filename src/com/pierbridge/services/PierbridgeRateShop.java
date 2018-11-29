package com.pierbridge.services;

import org.w3c.dom.Document;

import com.pierbridge.PierbridgeUtilities;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class PierbridgeRateShop extends PierbridgeUtilities {
	
	private static YFCLogCategory cat = YFCLogCategory.instance("com.pierbridge.services.PierbridgeRateShop");
	
	public Document rateShop(YFSEnvironment env, Document inDoc) throws YFSException {
		 if (cat.isTimerEnabled()) {
			 cat.beginTimer("rateShop");
		 }
		 String strTxn = "RateShop";
		 String serviceFilter = YFCDocument.getDocumentFor(inDoc).getDocumentElement().getAttribute("ServiceFilter");
		 
		 Document dShipment = callApi(env, inDoc, null, "getShipmentDetails", true);
		 
		 if(YFCCommon.isVoid(dShipment)) {
			throw new YFSException("No Shipment Found");
		 }
		 YFCElement eShipment = YFCDocument.getDocumentFor(dShipment).getDocumentElement();
		 validateShipment(eShipment, strTxn);
		 
		 if(!YFCCommon.isVoid(serviceFilter)) {
			 eShipment.setAttribute("ServiceFilter", serviceFilter);
		 }
		 
		 String shipmentKey = eShipment.getAttribute("ShipmentKey");
	     String carrier = eShipment.getAttribute("SCAC");
		    
	}
	
	private void validateShipment(YFCElement eShipment, String strTxn) throws YFSException {
		String sScac = eShipment.getAttribute("SCAC");
	    String sCarrierServiceCode = eShipment.getAttribute("CarrierServiceCode");
	    if (strTxn.equalsIgnoreCase("RateRequest")) {
	      if ((sScac.isEmpty()) || (sCarrierServiceCode.isEmpty())) {
	        throw new YFCException("EN008");
	      }
	      String strIsShipmentLevelIntegration = eShipment.getAttribute(" IsShipmentLevelIntegration");
	      if ((strIsShipmentLevelIntegration != null) && (!strIsShipmentLevelIntegration.equalsIgnoreCase("N"))) {
	        throw new YFCException("EN022");
	      }
	    }
	    String totalWeight = eShipment.getAttribute("TotalWeight");
	    if ((totalWeight.isEmpty()) || (0.0D == Double.parseDouble(totalWeight))) {
	      throw new YFCException("EN017");
	    }
	    YFCElement toAddress = eShipment.getChildElement("ToAddress");
	    String zip = toAddress.getAttribute("ZipCode");
	    String country = toAddress.getAttribute("Country");
	    String state = toAddress.getAttribute("State");
	    String city = toAddress.getAttribute("City");
	    if ((zip.isEmpty()) || (country.isEmpty()) || (state.isEmpty()) || (city.isEmpty())) {
	      throw new YFCException("EN018");
	    }
	    String addressLine1 = toAddress.getAttribute("AddressLine1");
	    String addressLine2 = toAddress.getAttribute("AddressLine2");
	    String addressLine3 = toAddress.getAttribute("AddressLine3");
	    String addressLine4 = toAddress.getAttribute("AddressLine4");
	    if ((addressLine1.isEmpty()) && (addressLine2.isEmpty()) && (addressLine3.isEmpty()) && (addressLine4.isEmpty())) {
	      throw new YFCException("EN018");
	    }
	    String shipmentStatus = eShipment.getAttribute("Status");
	    int status = Integer.parseInt(shipmentStatus.substring(0, shipmentStatus.indexOf(".")));
	    int shipmentShippedStatus = 1400;
	    if (status >= shipmentShippedStatus) {
	      throw new YFCException("EN007");
	    }
	}
}
