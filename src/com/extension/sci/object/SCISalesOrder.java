package com.extension.sci.object;

import java.util.ArrayList;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SCISalesOrder extends SCIObject {

	private ArrayList<SCIOrderLine> lines;
	
	public SCISalesOrder(YFCElement eOrder){
		super();
		setString("_id", eOrder.getAttribute("OrderNo"));
	
		
		if(!YFCCommon.isVoid(eOrder.getAttribute("BillToID"))){
			setString("customer", eOrder.getAttribute("BillToID"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("CustomerPONo"))){
			setString("customerPoNumber", eOrder.getAttribute("CustomerPONo"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("OrderDate"))){
			setDate("datePlaced", eOrder.getYDateAttribute("OrderDate"));
		}
		if(!YFCCommon.isVoid(eOrder.getChildElement("PriceInfo", true).getAttribute("TotalAmount"))){
			setDouble("orderValue", eOrder.getChildElement("PriceInfo", true).getDoubleAttribute("TotalAmount"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("Status"))){
			setString("orderStatus", eOrder.getAttribute("Status"));
		}
		
		YFCElement eFirstLine = eOrder.getChildElement("OrderLines").getFirstChildElement();

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
			SCIOrderLine ol = new SCIOrderLine(eOrderLine.getAttribute("OrderLineKey"), eOrderLine.getChildElement("Item", true).getAttribute("ItemID"), eOrderLine.getDoubleAttribute("OrderedQty"), "sales");
			this.addToArray("salesOrderLines", ol.getBulkObject());
		}
	
	}
	
	public static YFCDocument getSalesOrderListTemplate(){
		YFCDocument orderList = YFCDocument.createDocument("OrderList");
		YFCElement eOrderList = orderList.getDocumentElement();
		YFCElement eOrder = eOrderList.createChild("Order");
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("OrderDate", "");
		eOrder.setAttribute("CustomerPONo", "");
		eOrder.setAttribute("BillToID", "");
		eOrder.setAttribute("OrderDate", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("ExpectedDeliveryDate", "");
		eOrderLine.setAttribute("ExpectedShipmentDate", "");
		eOrderLine.setAttribute("ReqShipDate", "");
		eOrderLine.setAttribute("ReqDeliveryDate", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("ShipNode", "");
		eOrderLine.setAttribute("ReceivingNode", "");
		eOrderLine.setAttribute("OrderLineKey", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("ShortDescription", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement eComputedPrice = eOrderLine.createChild("ComputedPrice");
		eComputedPrice.setAttribute("LineTotal", "");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("Currency", "");
		ePriceInfo.setAttribute("TotalAmount", "");
		
		return orderList;
	}
	
	public static YFCDocument getSalesOrderTemplate(){
		YFCDocument order = YFCDocument.createDocument("Order");
		YFCElement eOrder = order.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("OrderDate", "");
		eOrder.setAttribute("CustomerPONo", "");
		eOrder.setAttribute("BillToID", "");
		eOrder.setAttribute("OrderDate", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("ExpectedDeliveryDate", "");
		eOrderLine.setAttribute("ExpectedShipmentDate", "");
		eOrderLine.setAttribute("ReqShipDate", "");
		eOrderLine.setAttribute("ReqDeliveryDate", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("ShipNode", "");
		eOrderLine.setAttribute("ReceivingNode", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("ShortDescription", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement eComputedPrice = eOrderLine.createChild("ComputedPrice");
		eComputedPrice.setAttribute("LineTotal", "");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("Currency", "");
		ePriceInfo.setAttribute("TotalAmount", "");
		
		return order;
	}
		
	public String getCustomer() {
		return getString("customer");
	}
	public void setCustomer(String customer) {
		setString("customer", customer);
	}
	public String getCustomerPoNumber() {
		return getString("customerPoNumber");
	}
	public void setCustomerPoNumber(String customerPoNumber) {
		setString("customerPoNumber", customerPoNumber);
	}
	public String getOrderStatus() {
		return getString("orderStatus");
	}
	public void setOrderStatus(String orderStatus) {
		setString("orderStatus", orderStatus);
	}

	public YDate getDatePlaced() {
		return getDate("datePlaced");
	}
	public void setDatePlaced(YDate datePlaced) {
		setDate("datePlaced", datePlaced);
	}
	public YDate getEstimatedDeliveryDate() {
		return getDate("estimatedDeliveryDate");
	}
	public void setEstimatedDeliveryDate(YDate estimatedDeliveryDate) {
		setDate("estimatedDeliveryDate", estimatedDeliveryDate);
	}
	public YDate getPlannedShipDate() {
		return getDate("plannedShipDate");
	}
	public void setPlannedShipDate(YDate plannedShipDate) {
		setDate("plannedShipDate", plannedShipDate);
	}
	public YDate getRequestedDeliveryDate() {
		return getDate("requestedDeliveryDate");
	}
	public void setRequestedDeliveryDate(YDate requestedDeliveryDate) {
		setDate("requestedDeliveryDate", requestedDeliveryDate);
	}
	public YDate getRequestedShipDate() {
		return getDate("requestedShipDate");
	}
	public void setRequestedShipDate(YDate requestedShipDate) {
		setDate("requestedShipDate", requestedShipDate);
	}
	public double getOrderValue() {
		return getDouble("orderValue");
	}
	public void setOrderValue(double orderValue) {
		setDouble("orderValue", orderValue);
	}

	@Override
	public String getBulkAPIURL() {
		return "/api/salesorders/bulk";
	}

	
}
