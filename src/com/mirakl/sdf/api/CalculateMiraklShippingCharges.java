package com.mirakl.sdf.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.extension.bda.userexits.ShippingChargeBreakdown;
import com.mirakl.entity.MiraklTranslation;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.ypm.japi.ue.YPMCalculateShippingChargeUE;

public class CalculateMiraklShippingCharges extends MiraklBase implements YPMCalculateShippingChargeUE {

	@Override
	public Document calculateShippingCharge(YFSEnvironment env, Document input) throws YFSUserExitException {
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOutput = dOutput.getDocumentElement();
		YFCElement eOutputShipping = eOutput.createChild("Shipping");
		eOutputShipping.setAttribute("ShippingCharge", "0");
		YIFApi localApi;
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			Document dOrder = getOrderDetails(localApi, env, eInput.getAttribute("OrderReference"));
			if(!YFCCommon.isVoid(dOrder)){
				YFCElement eOrder = YFCDocument.getDocumentFor(dOrder).getDocumentElement();
				eOutputShipping.setAttribute("ShippingCharge", generateShippingCharges(eOrder));
			}
			
		} catch (YIFClientCreationException e) {
			e.printStackTrace();
		}
		return dOutput.getDocument();
	}

	
	private double generateMiraklShippingCharges(YFCElement eOrder){
		double shippingCharge = 0;
	
		String sQueryString = "offers=";
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			if(YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod"), "SHP")){
				if(eOrderLine.getChildElement("Item").getAttribute("ItemID").contains("MKO_")){
					if(!YFCCommon.equals(sQueryString, "offers=")){
						sQueryString += ",";
					}
					sQueryString += eOrderLine.getChildElement("Item").getAttribute("ItemID").replace("MKO_", "") + "|" + eOrderLine.getAttribute("OrderedQty");
					String sCode = MiraklTranslation.getInstance(getMiraklTranslationDoc(), false).getMiraklValue("shipping_type_code", eOrderLine.getAttribute("CarrierServiceCode", eOrder.getAttribute("CarrierServiceCode", "STANDARD")));
					if(!YFCCommon.isVoid(sCode)){
						sQueryString += "|" + sCode;
					}
				}
			}
		}
		
		if(!YFCCommon.equals(sQueryString, "offers=")){
			sQueryString += "&shipping_zone_code=DOM1";
			try {
				URL url = new URL(getURL("/api/shipping/rates?" + sQueryString ));
				
				System.out.println("Calling: " + url.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", getApiKey());
				conn.setRequestProperty("Accept", "application/xml");
				
				
				if(conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
				}
				
				StringBuffer sb = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String res;
				while ((res = in.readLine()) != null) {
					sb.append(res);
				}		
				in.close();
				
				try {
					YFCDocument dResponse = YFCDocument.getDocumentFor(sb.toString());
					System.out.println("Mirakl Response: " + dResponse);
					
					YFCElement eBody = dResponse.getDocumentElement();
					for(YFCElement eMiraklOrder : eBody.getChildElement("orders", true).getChildren()){
						String sSelectedCode = eMiraklOrder.getChildElement("selected_shipping_type", true).getChildElement("code", true).getNodeValue();
						for(YFCElement eShippingType : eMiraklOrder.getChildElement("shipping_types", true).getChildren()){
							if(eShippingType.getChildElement("code", true).getNodeValue().equals(sSelectedCode)){
								shippingCharge += Double.parseDouble(eShippingType.getChildElement("total_shipping_price").getNodeValue());
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Response is not a document");
					System.out.println(sb.toString());
				}
			} catch (Exception e){
				
			}
		}
		return shippingCharge;
		
	}
	
	private double generateShippingCharges(YFCElement eOrder){
		HashMap<String, YFCElement> carriers = new HashMap<String, YFCElement>();
		carriers = getCarrierServiceData(carriers);
		double shippingCharge = 0;
		HashMap<String, ShippingChargeBreakdown> charges = new HashMap<String, ShippingChargeBreakdown>();
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			if(YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod"), "SHP")){
				if(!eOrderLine.getChildElement("Item").getAttribute("ItemID").contains("MKO_")){
					YFCElement eCarrierPricing = null;
					String sCarrierServiceCode = eOrderLine.getAttribute("CarrierServiceCode", eOrder.getAttribute("CarrierServiceCode", ""));
					String sScac = eOrderLine.getAttribute("SCAC", eOrder.getAttribute("SCAC", ""));
					if(!YFCCommon.isVoid(sScac) && !YFCCommon.isVoid(sCarrierServiceCode)){
						eCarrierPricing = carriers.get(sScac + "_" + sCarrierServiceCode);
					}
					if(YFCCommon.isVoid(eCarrierPricing) && !YFCCommon.isVoid(sCarrierServiceCode)){
						eCarrierPricing = carriers.get("_" + sCarrierServiceCode);
					}
					if(!YFCCommon.isVoid(eCarrierPricing)){
						ShippingChargeBreakdown t = charges.get(sScac + "_" + sCarrierServiceCode);
						double qty = eOrderLine.getDoubleAttribute("OrderedQty", 0);
						double pricePerQty = qty * eCarrierPricing.getDoubleAttribute("PricePerUnit", 0);
						double weight = eOrderLine.getChildElement("Item", true).getDoubleAttribute("ItemWeight", 0);
						double weightPrice = qty * weight * eCarrierPricing.getDoubleAttribute("PricePerPound", 0);
						double flatFee = eCarrierPricing.getDoubleAttribute("FlatFee", 0);
						if(!YFCCommon.isVoid(t)){
							t.addUnitFee(pricePerQty);
							t.addWeightFee(weightPrice);
						} else {
							charges.put(sScac + "_" + sCarrierServiceCode, new ShippingChargeBreakdown(flatFee, weightPrice, pricePerQty));
						}
					}
				}
			}
		}
		for(String sKey : charges.keySet()){
			shippingCharge += charges.get(sKey).getFee();
		}
		shippingCharge += this.generateMiraklShippingCharges(eOrder);
		return shippingCharge;
	}
	
	private HashMap<String, YFCElement> getCarrierServiceData(HashMap<String, YFCElement> mapCarriers){
		if(mapCarriers.size() > 0){
			return mapCarriers;
		}
		File external = new File("/opt/Sterling/Scripts/carrierService.xml");
		YFCDocument dOutput = YFCDocument.createDocument("CarrierServiceCodes");
		if(external.exists()){
			dOutput = YFCDocument.getDocumentFor(external);
		} else {
			try {
				dOutput = YFCDocument.parse(CalculateMiraklShippingCharges.class.getResourceAsStream("carrierService.xml"));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(YFCElement eCarrierService : dOutput.getDocumentElement().getChildren()){
			mapCarriers.put(eCarrierService.getAttribute("SCAC", "") + "_" + eCarrierService.getAttribute("CarrierServiceCode", ""), eCarrierService);
		}
		return mapCarriers;
	}
	
	private Document getOrderDetails(YIFApi localApi, YFSEnvironment env, String sOrderHeaderKey){
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrderHeaderKey", sOrderHeaderKey);
		try {
			env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
			return localApi.getOrderDetails(env, dInput.getDocument());
		} catch (YFSException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Document getOrderDetailsTemplate(){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("CarrierServiceCode", "");
		eOrder.setAttribute("SCAC", "");
		eOrder.setAttribute("ScacAndService", "");
		eOrder.setAttribute("OrderHeaderKey", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("CarrierServiceCode", "");
		eOrderLine.setAttribute("SCAC", "");
		eOrderLine.setAttribute("ScacAndService", "");
		eOrderLine.setAttribute("ItemGroupCode", "");
		YFCElement eItemDetails = eOrderLine.createChild("Item");
		eItemDetails.setAttribute("ItemID", "");
		eItemDetails.setAttribute("UnitOfMeasure", "");
		eItemDetails.setAttribute("ItemWeight", "");
		eItemDetails.setAttribute("ItemWeightUOM", "");
		return dOrder.getDocument();
	}

}
