package com.extension.sci.object;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SCIPurchaseOrder extends SCIObject {

	public SCIPurchaseOrder(YFCElement eOrderLine, YFCElement eOrder){
		super(eOrderLine.getAttribute("OrderLineKey"), eOrder.getAttribute("OrderHeaderKey"));
		

		if(!YFCCommon.isVoid(eOrderLine.getAttribute("Status"))){
			setString("orderStatus", eOrderLine.getAttribute("Status"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getChildElement("Item", true).getAttribute("ItemID"))){
			setString("item", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
		}
		
		if(!YFCCommon.isVoid(eOrder.getAttribute("OrderDate"))){
			setDate("datePlaced", eOrder.getYDateAttribute("OrderDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ExpectedDeliveryDate"))){
			setDate("estimatedDeliveryDate", eOrderLine.getYDateAttribute("ExpectedDeliveryDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ExpectedShipmentDate"))){
			setDate("plannedShipDate", eOrderLine.getYDateAttribute("ExpectedShipmentDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReqShipDate"))){
			setDate("requestedShipDate", eOrderLine.getYDateAttribute("ReqShipDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReqDeliveryDate"))){
			setDate("requestedShipDate", eOrderLine.getYDateAttribute("ReqDeliveryDate"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getChildElement("ComputedPrice", true).getAttribute("LineTotal"))){
			setDouble("orderValue", eOrderLine.getChildElement("ComputedPrice", true).getDoubleAttribute("LineTotal"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("OrderedQty"))){
			setDouble("quantity", eOrderLine.getDoubleAttribute("OrderedQty"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReceivingNode"))){
			setString("destination", eOrderLine.getAttribute("ReceivingNode"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("SellerOrganizationCode"))){
			setString("supplier", eOrderLine.getAttribute("SellerOrganizationCode"));
		}
		
	}

	@Override
	public String getBulkAPIURL() {
		// TODO Auto-generated method stub
		return null;
	}

}
