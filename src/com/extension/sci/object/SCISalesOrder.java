package com.extension.sci.object;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SCISalesOrder extends SCIObject {

	public SCISalesOrder(YFCElement eOrderLine, YFCElement eOrder){
		super(eOrderLine.getAttribute("OrderLineKey"), eOrder.getAttribute("OrderHeaderKey"));
		
	
		
		if(!YFCCommon.isVoid(eOrder.getAttribute("BillToID"))){
			setString("customer", eOrder.getAttribute("BillToID"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("CustomerPONo"))){
			setString("customerPoNumber", eOrder.getAttribute("CustomerPONo"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getAttribute("Status"))){
			setString("orderStatus", eOrderLine.getAttribute("Status"));
		}
		if(!YFCCommon.isVoid(eOrderLine.getChildElement("Item", true).getAttribute("ItemID"))){
			setString("product", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
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
	
	public SCISalesOrder(String orderLineKey, String customer, String customerPoNumber, String orderStatus, String product,
			String shipment, YDate datePlaced, YDate estimatedDeliveryDate, YDate plannedShipDate,
			YDate requestedDeliveryDate, YDate requestedShipDate, double orderValue, double quantity) {
		super(orderLineKey);
		
		setString("customer", customer);
		setString("customerPoNumber", customerPoNumber);
		setString("orderStatus", orderStatus);
		setString("product", product);
		setString("shipment", shipment);
		setDate("datePlaced", datePlaced);
		setDate("estimatedDeliveryDate", estimatedDeliveryDate);
		setDate("plannedShipDate", plannedShipDate);
		setDate("requestedDeliveryDate", requestedDeliveryDate);
		setDate("requestedShipDate", requestedShipDate);
		setDouble("orderValue", orderValue);
		setDouble("quantity", quantity);
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
	public String getProduct() {
		return getString("product");
	}
	public void setProduct(String product) {
		setString("product", product);
	}
	public String getShipment() {
		return getString("shipment");
	}
	public void setShipment(String shipment) {
		setString("shipment", shipment);
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
	public double getQuantity() {
		return getDouble("quantity");
	}
	public void setQuantity(double quantity) {
		setDouble("quantity", quantity);
	}
	@Override
	public String getBulkAPIURL() {
		return "/api/salesorders/bulk";
	}

	
}
