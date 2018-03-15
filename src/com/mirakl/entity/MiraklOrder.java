package com.mirakl.entity;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.mirakl.utilities.MiraklUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class MiraklOrder {

	private YFCDocument dOrder = null;
	private YFCElement eOrder = null;
	private YFCElement eExistingOrder = null;
	
	public MiraklOrder(){
		dOrder = YFCDocument.createDocument("Order");
		eOrder = dOrder.getDocumentElement();
	}
	
	public MiraklOrder(YFCDocument dOrder) {
		if(!YFCCommon.isVoid(dOrder)){
			this.dOrder = dOrder;
			this.eOrder = dOrder.getDocumentElement();
		}		
	}
	
	public MiraklOrder(YFCElement eOrder) {
		if(!YFCCommon.isVoid(eOrder)){
			dOrder = YFCDocument.getDocumentFor(eOrder.toString());
			this.eOrder = dOrder.getDocumentElement();
		}		
	}
	
	public MiraklOrder(JSONObject order){
		this();
		setAttribute(eOrder, "OrderHeaderKey", order, "commercial_id");
		setAttribute(eOrder, "MiraklOrderID", order, "order_id");
		setAttribute(eOrder, "Status", order, "order_state");
		setAttribute(eOrder, "ShopID", order, "shop_id");
		setAttribute(eOrder, "ShipName", order, "shop_name");
		setAttribute(eOrder, "TrackingNo", order, "shipping_tracking");
		setAttribute(eOrder, "ShippingCarrierCode", order, "shipping_carrier_code");
		setAttribute(eOrder, "ShippingTypeCode", order, "shipping_type_code");
		setAttribute(eOrder, "ShippingCompany", order, "shipping_company");
		setAttribute(eOrder, "Currency", order, "currency_iso_code");
		setAttribute(eOrder, "OrderDate", order, "created_date");
		
		
		
		JSONArray orderlines = order.getJSONArray("order_lines");
		for(int j = 0; j < orderlines.length(); j++){
			JSONObject orderline = orderlines.getJSONObject(j);
			YFCElement eOrderLine = eOrder.getChildElement("OrderLines", true).createChild("OrderLine");
			setAttribute(eOrderLine, "OrderLineKey", orderline, "order_line_id");
			setAttribute(eOrderLine, "Status", orderline, "order_line_state");
			setAttribute(eOrderLine, "Quantity", orderline, "quantity");
			
			YFCElement ePrice = eOrderLine.getChildElement("Price");
			setAttribute(ePrice, "LinePrice", orderline, "price");
			setAttribute(ePrice, "ShippingPrice", orderline, "shipping_price");
			setAttribute(ePrice, "TotalPrice", orderline, "total_price");
			
			YFCElement eDates = eOrderLine.createChild("OrderDates");
			setAttribute(eDates, "ReceivedDate", orderline, "received_date");
			setAttribute(eDates, "ShippedDate", orderline, "shipped_date");
			setAttribute(eDates, "CreatedDate", orderline, "created_date");			
		}
		
		System.out.println("Mirakl Order: " + eOrder);
	}
	
	public Document getObjectXML(){
		return dOrder.getDocument();
	}
	
	private void setAttribute(YFCElement eElement, String sAttribute, JSONObject obj, String sProperty){
		if(!YFCCommon.isVoid(eElement) && !YFCCommon.isVoid(obj) && obj.has(sProperty) && obj.get(sProperty) instanceof String && !YFCCommon.isVoid(obj.get(sProperty).toString())){
			if(!YFCCommon.isVoid(obj.getString(sProperty))){
				eElement.setAttribute(sAttribute, obj.getString(sProperty));
			}		
		} else if(!YFCCommon.isVoid(eElement) && !YFCCommon.isVoid(obj) && obj.has(sProperty) && obj.get(sProperty) instanceof Integer) {
			eElement.setAttribute(sAttribute, obj.getInt(sProperty));
		}
	}
	
	public YFCElement getExistingOrder(YFSEnvironment env) {
		if(YFCCommon.isVoid(eExistingOrder)){
			eExistingOrder = MiraklUtils.getExistingOrder(env, eOrder.getChildElement("commercial_id").getNodeValue());
		}
		return eExistingOrder;
	}
	
	public void updateOrder(YFSEnvironment env, String sTransactionID, String sTranslationFile){
		YFCElement eExistingOrder = getExistingOrder(env);
		if(!YFCCommon.isVoid(eExistingOrder)){
			HashMap<String, YFCDocument> apiInput = new HashMap<String, YFCDocument>();
			String sSCAC = MiraklTranslation.getInstance(sTranslationFile, false).getOMSValue("shipping_carrier_code", eOrder.getChildElement("shipping_type_code").getNodeValue());
			if(!YFCCommon.isVoid(sSCAC)){
				eOrder.setAttribute("SCAC", sSCAC);
			}
			for(YFCElement eMiraklOrderLine : eOrder.getChildElement("order_lines").getChildren()){
				String sNewOMSStatus = MiraklTranslation.getInstance(sTranslationFile, false).getOMSValue("order_line_state", eMiraklOrderLine.getChildElement("order_line_state").getNodeValue());
				addLineToApi(eMiraklOrderLine, eExistingOrder, apiInput, sNewOMSStatus, sTransactionID);	
			}
			for(String sApiName : apiInput.keySet()){
				System.out.println("Calling " + sApiName + " with: " + apiInput.get(sApiName));
				MiraklUtils.callApi(env, apiInput.get(sApiName), null, sApiName);
			}
		}
	}
	
	public void validateOrderLines(YFSEnvironment env, String sOrganizationCode){
		YFCDocument dPromise = YFCDocument.createDocument("Promise");
		YFCElement ePromise = dPromise.getDocumentElement();
		ePromise.setAttribute("OrganizationCode", sOrganizationCode);
		for(YFCElement eOrderLine : eOrder.getChildElement("order_lines", true).getChildren()){
			YFCElement ePromiseLine = ePromise.getChildElement("PromiseLines", true).createChild("PromiseLine");
			ePromiseLine.setAttribute("LineId", eOrderLine.getChildElement("order_line_id", true).getNodeValue());
			ePromiseLine.setAttribute("ItemID", eOrderLine.getChildElement("offer_sku", true).getNodeValue());
			ePromiseLine.setAttribute("UnitOfMeasure", "EACH");
			ePromiseLine.setAttribute("RequiredQty", eOrderLine.getChildElement("quantity", true).getNodeValue());
			ePromiseLine.setAttribute("DeliveryMethod", "SHP");
		}
		YFCDocument dResponse = MiraklUtils.callApi(env, dPromise, null, "findInventory");
		
	//	YFCElement eSuggestedOption = dResponse.getDocumentElement().getChildElement("SuggestedOption");
		
	}
	
	private static void setAttribute(YFCElement eNode, String sAttribute, String sValue){
		if(!YFCCommon.isVoid(sValue)){
			eNode.setAttribute(sAttribute, sValue);
		}
	}
	
	public static YFCElement createShipmentLine(YFCElement eShipment, YFCElement eOrderLine, String sQuantity, String sOrderHeaderKey){
		YFCElement eShipmentLine = eShipment.getChildElement("ShipmentLines", true).createChild("ShipmentLine"); 
		setAttribute(eShipmentLine, "OrderLineKey", eOrderLine.getAttribute("OrderLineKey"));
		setAttribute(eShipmentLine, "OrderHeaderKey", sOrderHeaderKey);
		setAttribute(eShipmentLine, "Quantity", sQuantity);
		
		return eShipmentLine;
	}
	
	private void addLineToApi(YFCElement eMiraklOrderLine, YFCElement eExistingOrder, HashMap<String, YFCDocument> mApis, String sNewOMSStatus, String sTransactionID){
		for(YFCElement eExistingLine : eExistingOrder.getChildElement("OrderLines", true).getChildren()){
			if(YFCCommon.equals(eExistingLine.getAttribute("OrderLineKey"), eMiraklOrderLine.getChildElement("order_line_id", true).getNodeValue())){
				if(!YFCCommon.isVoid(sNewOMSStatus)){
					boolean found = false;
					for(YFCElement eLineStatus : eExistingLine.getChildElement("OrderStatuses", true).getChildren()){
						if(YFCCommon.equals(eLineStatus.getAttribute("Status"), sNewOMSStatus) && YFCCommon.equals(eLineStatus.getAttribute("StatusQty"), eMiraklOrderLine.getChildElement("quantity", true).getNodeValue())){
							found = true;
							break;
						}
					}
					if(!found){
						if(sNewOMSStatus.equals("3700")){
							if(!mApis.containsKey("confirmShipment")){
								YFCDocument	dShipment = YFCDocument.createDocument("Shipment");
								dShipment.getDocumentElement().setAttribute("ShipmentKey", eOrder.getChildElement("order_id", true).getNodeValue());
								dShipment.getDocumentElement().setAttribute("ShipNode", "MRK_SHOP_" + eOrder.getChildElement("shop_id", true).getNodeValue());
								setAttribute(dShipment.getDocumentElement(), "TrackingNo", eOrder.getChildElement("shipping_tracking", true).getNodeValue());
								mApis.put("confirmShipment", dShipment);
							}
							createShipmentLine(mApis.get("confirmShipment").getDocumentElement(), eExistingLine, eMiraklOrderLine.getChildElement("quantity", true).getNodeValue(), eExistingOrder.getAttribute("OrderHeaderKey"));
						} else if(sNewOMSStatus.equals("9000")){
							if(!mApis.containsKey("cancelOrder")){
								YFCDocument	dOrder = YFCDocument.createDocument("Order");
								dOrder.getDocumentElement().setAttribute("OrderHeaderKey", eExistingOrder.getAttribute("OrderHeaderKey"));
								dOrder.getDocumentElement().setAttribute("Override", true);
								mApis.put("changeOrder", dOrder);
							}
							YFCElement eOrderLineInput = mApis.get("cancelOrder").getDocumentElement().getChildElement("OrderLines", true).createChild("OrderLine");
							eOrderLineInput.setAttribute("OrderLineKey", eMiraklOrderLine.getChildElement("order_line_id", true).getNodeValue());
							eOrderLineInput.setAttribute("Override", true);
							eOrderLineInput.setAttribute("OrderedQty", eMiraklOrderLine.getChildElement("quantity", true).getNodeValue());
						} else if(sNewOMSStatus.startsWith("1")){
							if(eMiraklOrderLine.getIntAttribute("Quantity", 0) > 0){
								if(!mApis.containsKey("changeOrderStatus")){
									YFCDocument dOrderStatusChange = YFCDocument.createDocument("OrderStatusChange");
									YFCElement eOrderStatus = dOrderStatusChange.getDocumentElement();
									eOrderStatus.setAttribute("TransactionId", sTransactionID);
									eOrderStatus.setAttribute("OrderHeaderKey", eExistingOrder.getAttribute("OrderHeaderKey"));
									mApis.put("changeOrderStatus", dOrderStatusChange);
								}
								YFCElement eOrderLineInput = mApis.get("changeOrderStatus").getDocumentElement().getChildElement("OrderLines", true).createChild("OrderLine");
								eOrderLineInput.setAttribute("OrderLineKey", eMiraklOrderLine.getChildElement("order_line_id", true).getNodeValue());
								eOrderLineInput.setAttribute("BaseDropStatus", sNewOMSStatus);
								eOrderLineInput.setAttribute("Quantity", eMiraklOrderLine.getChildElement("quantity", true).getNodeValue());
							}
						}		
					} else {
						if(!YFCCommon.isVoid(eOrder.getAttribute("TrackingNo"))){
							if(!mApis.containsKey("changeShipment")){
								YFCDocument	dShipment = YFCDocument.createDocument("Shipment");
								dShipment.getDocumentElement().setAttribute("ShipmentKey", eOrder.getChildElement("order_id", true).getNodeValue());
								setAttribute(dShipment.getDocumentElement(), "TrackingNo", eOrder.getChildElement("shipping_tracking", true).getNodeValue());
								setAttribute(dShipment.getDocumentElement(), "SCAC", eOrder.getAttribute("SCAC"));
								mApis.put("changeShipment", dShipment);
							}
						}
					}
				}			
				break;
			}
		}
	}
}
