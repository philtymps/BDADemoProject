package com.extension.sci.object;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SCIPurchaseOrder extends SCIObject {

	public SCIPurchaseOrder(YFCElement eOrder){
		super();
		setString("_id", eOrder.getAttribute("OrderNo"));
		
		if(!YFCCommon.isVoid(eOrder.getAttribute("OrderDate"))){
			setDate("datePlaced", eOrder.getYDateAttribute("OrderDate"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("Status"))){
			setString("orderStatus", eOrder.getAttribute("Status"));
		}
		if(!YFCCommon.isVoid(eOrder.getChildElement("PriceInfo", true).getAttribute("TotalAmount"))){
			setDouble("orderValue", eOrder.getChildElement("PriceInfo", true).getDoubleAttribute("TotalAmount"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("SellerOrganizationCode"))){
			setString("supplier", eOrder.getAttribute("SellerOrganizationCode"));
		}
		
				
		YFCElement eFirstLine = eOrder.getChildElement("OrderLines").getFirstChildElement();
		
		if(!YFCCommon.isVoid(eFirstLine.getAttribute("ReceivingNode"))){
			setString("destination", eFirstLine.getAttribute("ReceivingNode"));
		}

		if(!YFCCommon.isVoid(eFirstLine.getAttribute("ExpectedShipmentDate"))){
			setDate("plannedShipDate", eFirstLine.getYDateAttribute("ExpectedShipmentDate"));
		}
		if(!YFCCommon.isVoid(eFirstLine.getAttribute("ReqShipDate"))){
			setDate("requestedShipDate", eFirstLine.getYDateAttribute("ReqShipDate"));
		}
		if(!YFCCommon.isVoid(eFirstLine.getAttribute("ReqDeliveryDate"))){
			setDate("requestedShipDate", eFirstLine.getYDateAttribute("ReqDeliveryDate"));
		}		
		if(!YFCCommon.isVoid(eFirstLine.getAttribute("ExpectedDeliveryDate"))){
			setDate("estimatedDeliveryDate", eFirstLine.getYDateAttribute("ExpectedDeliveryDate"));
		}

		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
			SCIOrderLine ol = new SCIOrderLine(eOrderLine.getAttribute("OrderLineKey"), eOrderLine.getChildElement("Item", true).getAttribute("ItemID"), eOrderLine.getDoubleAttribute("OrderedQty"), "supply");
			this.addToArray("supplyOrderLines", ol.getBulkObject());
		}
		
	}

	@Override
	public String getBulkAPIURL() {
		// TODO Auto-generated method stub
		return null;
	}

}
