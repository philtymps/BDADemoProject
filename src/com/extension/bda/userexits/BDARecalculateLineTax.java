package com.extension.bda.userexits;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.expose.BDARestCall;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.google.gson.Gson;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSExtnLineChargeStruct;
import com.yantra.yfs.japi.YFSExtnLineTaxCalculationInputStruct;
import com.yantra.yfs.japi.YFSExtnTaxBreakup;
import com.yantra.yfs.japi.YFSExtnTaxCalculationOutStruct;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSRecalculateLineTaxUE;

public class BDARecalculateLineTax extends BDARestCall implements YFSRecalculateLineTaxUE {

	@Override
	public YFSExtnTaxCalculationOutStruct recalculateLineTax(YFSEnvironment env,
			YFSExtnLineTaxCalculationInputStruct inStruct) throws YFSUserExitException {
		Gson gson = new Gson();
		JSONObject obj = convertToJson(env, inStruct);
		
		String sURL = "https://remoteomsservices.us-south.cf.appdomain.cloud/oms/userexits/recalculateLineTax";

		try {				
			URL url = new URL(sURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			if(obj != null) {
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
				outputStreamWriter.write(obj.toString());
				outputStreamWriter.flush();
			}
			System.out.println("Before Request");
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			if(responseCode < 300) {
				BufferedInputStream response = new BufferedInputStream(connection.getInputStream());
				byte[] content = new byte[1024];
				StringBuffer sb = new StringBuffer();
				int bytesRead = 0;
				while((bytesRead = response.read(content)) != -1) {
					sb.append(new String(content, 0, bytesRead));
				}
				System.out.println("Response: " + sb.toString());
				JSONObject output = new JSONObject(sb.toString());
				return convertFromJson(output);
			}
			return null;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	
	}
	
	private Document getOrgTemplate() {
		YFCDocument dOutput = YFCDocument.createDocument("Organization");
		YFCElement eOrg = dOutput.getDocumentElement();
		eOrg.setAttribute("OrganizationCode", "");
		YFCElement eNode = eOrg.createChild("Node");
		eNode.setAttribute("ShipNode", "");
		YFCElement eShipNodePersonInfo = eNode.createChild("ShipNodePersonInfo");
		eShipNodePersonInfo.setAttribute("Country", "");
		eShipNodePersonInfo.setAttribute("City", "");
		eShipNodePersonInfo.setAttribute("State", "");
		eShipNodePersonInfo.setAttribute("ZipCode", "");
		eShipNodePersonInfo.setAttribute("AddressLine1", "");
		return dOutput.getDocument();
	}
	
	private Document getShipNodeAddress(YFSEnvironment env, String sShipNode) {
		YFCDocument dInput = YFCDocument.createDocument("Organization");
		YFCElement eOrg = dInput.getDocumentElement();
		eOrg.setAttribute("OrganizationCode", sShipNode);
		
		return BDAServiceApi.callApi(env, dInput.getDocument(), getOrgTemplate(), "getOrganizationHierarchy");
	}
	
	private YFSExtnTaxCalculationOutStruct convertFromJson(JSONObject obj) {
		YFSExtnTaxCalculationOutStruct output = new YFSExtnTaxCalculationOutStruct();
		List<YFSExtnTaxBreakup> taxes = new ArrayList<YFSExtnTaxBreakup>();
		output.tax = 0;
		output.taxPercentage = 0;
		output.colTax = taxes;
		if(obj.getDouble("tax") > 0) {
			output.tax = obj.getDouble("tax");
			output.taxPercentage = obj.getDouble("taxPercentage");
			for(int i = 0; i < obj.getJSONArray("colTax").length(); i++) {
				addSalesTax((JSONObject) obj.getJSONArray("colTax").get(i), output.colTax);
			}
		}
		return output;
	}
	
	private void addSalesTax(JSONObject output, List<YFSExtnTaxBreakup> taxes){
		YFSExtnTaxBreakup tb = new YFSExtnTaxBreakup();
		tb.chargeCategory = output.getString("chargeCategory");
		tb.chargeName = output.getString("chargeName");
		tb.tax = output.getDouble("tax");
		tb.taxName = output.getString("taxName");
		tb.taxPercentage = output.getDouble("taxPercentage");
		tb.taxableFlag = output.getString("taxableFlag");
		taxes.add(tb);
	}
	
	private JSONObject convertToJson(YFSEnvironment env, YFSExtnLineTaxCalculationInputStruct struct) {
		JSONObject obj = new JSONObject();
		obj.put("orderHeaderKey", struct.orderHeaderKey);
		obj.put("orderLineKey", struct.orderLineKey);
		obj.put("bForInvoice", struct.bForInvoice);
		obj.put("hasPendingChanges", struct.hasPendingChanges);
		obj.put("bLastInvoiceForOrderLine", struct.bLastInvoiceForOrderLine);
		obj.put("bForPreSettlement", struct.bForPreSettlement);
		obj.put("bForPacklistPrice", struct.bForPacklistPrice);
		obj.put("sShipNode", struct.sShipNode);
		obj.put("itemId", struct.itemId);
		obj.put("unitPrice", struct.unitPrice);
		obj.put("currentUnitPrice", struct.currentUnitPrice);
		obj.put("originalUnitPrice", struct.originalUnitPrice);
		obj.put("invoicedPricingQty", struct.invoicedPricingQty);
		obj.put("lineQty", struct.lineQty);
		obj.put("currentQty", struct.currentQty);
		obj.put("invoicedQty", struct.invoicedQty);
		obj.put("shipToId", struct.shipToId);
		if(!YFCCommon.isVoid(struct.shipToCountry) || YFCCommon.isVoid(struct.sShipNode)) {
			obj.put("shipToCity", struct.shipToCity);
			obj.put("shipToState", struct.shipToState);
			obj.put("shipToZipCode", struct.shipToZipCode);
			obj.put("shipToCountry", struct.shipToCountry);
		} else {
			Document dOrg = getShipNodeAddress(env, struct.sShipNode);
			if(!YFCCommon.isVoid(dOrg)) {
				YFCElement eOrg = YFCDocument.getDocumentFor(dOrg).getDocumentElement();
				obj.put("shipToCity", eOrg.getChildElement("Node", true).getChildElement("ShipNodePersonInfo", true).getAttribute("City"));
				obj.put("shipToState", eOrg.getChildElement("Node", true).getChildElement("ShipNodePersonInfo", true).getAttribute("State"));
				obj.put("shipToZipCode", eOrg.getChildElement("Node", true).getChildElement("ShipNodePersonInfo", true).getAttribute("ZipCode"));
				obj.put("shipToCountry", eOrg.getChildElement("Node", true).getChildElement("ShipNodePersonInfo", true).getAttribute("Country"));
			} else {
				obj.put("shipToCity", struct.shipToCity);
				obj.put("shipToState", struct.shipToState);
				obj.put("shipToZipCode", struct.shipToZipCode);
				obj.put("shipToCountry", struct.shipToCountry);
			}

		}

		obj.put("purpose", struct.purpose);
		obj.put("enterpriseCode", struct.enterpriseCode);
		obj.put("documentType", struct.documentType);
		obj.put("taxpayerId", struct.taxpayerId);
		obj.put("taxJurisdiction", struct.taxJurisdiction);
		obj.put("taxExemptionCertificate", struct.taxExemptionCertificate);
		obj.put("taxExemptFlag", struct.taxExemptFlag);
		JSONArray a = new JSONArray();
		for(Object charge: struct.colCharge) {
			YFSExtnLineChargeStruct c  = (YFSExtnLineChargeStruct) charge;
			JSONObject lineCharge = new JSONObject();
			lineCharge.put("chargeAmount", c.chargeAmount);
			lineCharge.put("chargePerLine", c.chargePerLine);
			lineCharge.put("chargePerUnit", c.chargePerUnit);
			lineCharge.put("invoicedExtended", c.invoicedExtended);
			lineCharge.put("invoicedPerLine", c.invoicedPerLine);
			lineCharge.put("chargeCategory", c.chargeCategory);
			lineCharge.put("chargeName", c.chargeName);
			a.put(lineCharge);
		}
		a = new JSONArray();
		for(Object charge: struct.colTax) {
			YFSExtnTaxBreakup c  = (YFSExtnTaxBreakup) charge;
			JSONObject lineCharge = new JSONObject();
			lineCharge.put("invoicedTax", c.invoicedTax);
			lineCharge.put("tax", c.tax);
			lineCharge.put("taxPercentage", c.taxPercentage);
			lineCharge.put("totalCurrentCharge", c.totalCurrentCharge);
			lineCharge.put("totalInvoicedCharge", c.totalInvoicedCharge);
			lineCharge.put("chargeCategory", c.chargeCategory);
			lineCharge.put("chargeName", c.chargeName);
			lineCharge.put("taxName", c.taxName);
			lineCharge.put("reference1", c.reference1);
			lineCharge.put("reference2", c.reference2);
			lineCharge.put("reference3", c.reference3);
			a.put(lineCharge);
		}
		obj.put("totaloptionprice", struct.totaloptionprice);
		obj.put("orderedQty", struct.orderedQty);
		obj.put("totalShippingCharges", struct.totalShippingCharges);
		obj.put("totalHandlingCharges", struct.totalHandlingCharges);
		obj.put("totalPersonalizeCharges", struct.totalPersonalizeCharges);
		obj.put("tax", struct.tax);
		obj.put("taxPercentage", struct.taxPercentage);
		obj.put("invoiceMode", struct.invoiceMode);
		return obj;
	}

}
