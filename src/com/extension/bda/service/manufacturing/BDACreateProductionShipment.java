package com.extension.bda.service.manufacturing;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.mirakl.entity.MiraklOrder;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDACreateProductionShipment extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "createProductionShipment";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		Document dProductionOrders =  getProductionOrdersFromShipment(env, input);
		if(!YFCCommon.isVoid(dProductionOrders)) {
			YFCDocument	dShipment = YFCDocument.createDocument("Shipment");
			YFCElement eConfirmShipment = dShipment.getDocumentElement();
			YFCElement eInput = YFCDocument.getDocumentFor(input).getDocumentElement();
			eConfirmShipment.setAttribute("ShipNode", eInput.getAttribute("ShipNode"));
			eConfirmShipment.setAttribute("ReceivingNode", eInput.getAttribute("ReceivingNode"));
			eConfirmShipment.setAttribute("EnterpriseCode", "Lastar");
			for(YFCElement eOrder : YFCDocument.getDocumentFor(dProductionOrders).getDocumentElement().getChildren()) {
				for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
					if(!YFCCommon.isVoid(eOrderLine.getAttribute("DerivedFromOrderHeaderKey"))) {
						YFCElement eProductionShipmentLine = getShipmentLine(eInput, eOrder.getAttribute("OrderHeaderKey"), eOrderLine.getAttribute("PrimeLineNo"), eOrderLine.getAttribute("SubLineNo"));
						YFCElement eShipmentLine = eConfirmShipment.getChildElement("ShipmentLines", true).createChild("ShipmentLine");
						eShipmentLine.setAttribute("ItemID", eOrderLine.getChildElement("Item").getAttribute("ItemID"));
						eShipmentLine.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item").getAttribute("UnitOfMeasure"));
						eShipmentLine.setAttribute("OrderHeaderKey", eOrderLine.getAttribute("DerivedFromOrderHeaderKey"));
						eShipmentLine.setAttribute("PrimeLineNo", eOrderLine.getAttribute("PrimeLineNo"));
						eShipmentLine.setAttribute("SubLineNo", eOrderLine.getAttribute("SubLineNo"));
						eShipmentLine.setAttribute("Quantity", eProductionShipmentLine.getAttribute("Quantity"));
					}
				}
			}
			return this.callApi(env, dShipment.getDocument(), null, "confirmShipment");
		}
		
		return null;	
	}


	private YFCElement getShipmentLine(YFCElement eShipment, String sOrderHeaderKey, String sPrimeLineNo, String sSubLineNo) {
		for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()) {
			if (
					YFCCommon.equals(eShipmentLine.getAttribute("PrimeLineNo"), sPrimeLineNo) &&
					YFCCommon.equals(eShipmentLine.getAttribute("SubLineNo"), sSubLineNo) &&
					YFCCommon.equals(eShipmentLine.getAttribute("OrderHeaderKey"), sOrderHeaderKey)
			) {
				return eShipmentLine;
			}
		}
		return null;
	}
	public static YFCDocument getOrderListTemplate() {
		try {
			return YFCDocument.parse(BDACreateProductionOrder.class.getResourceAsStream("OrderListTemplate.xml"));
		} catch (Exception e) {
		
		}
		return null;
	}
	
	
	private Document getProductionOrdersFromShipment(YFSEnvironment env, Document dShipment) {
		if(!YFCCommon.isVoid(dShipment)) {
			YFCElement eShipment = YFCDocument.getDocumentFor(dShipment).getDocumentElement();
			ArrayList<String> ohks = new ArrayList<String>();
			for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()) {
				if(!ohks.contains(eShipmentLine.getAttribute("OrderHeaderKey"))) {
					ohks.add(eShipmentLine.getAttribute("OrderHeaderKey"));
				}
			}
			YFCDocument getOrderList = YFCDocument.createDocument("Order");
			YFCElement eOrder = getOrderList.getDocumentElement();
			if(ohks.size() > 0) {
				if(ohks.size() == 1) {
					eOrder.setAttribute("OrderHeaderKey", ohks.get(0));
				} else {
					for(String key : ohks) {
						YFCElement eExp = eOrder.getChildElement("ComplexQuery", true).getChildElement("Or", true).createChild("Exp");
						eExp.setAttribute("Name", "OrderHeaderKey");
						eExp.setAttribute("Value", key);
					}
				}
				return this.callApi(env, getOrderList.getDocument(), getOrderListTemplate().getDocument(), "getOrderList");
			}
		}
		
		return null;
	}

}
