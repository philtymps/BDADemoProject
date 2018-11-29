package com.extension.sci.object;

public class SCIOrderLine extends SCIObject {

	private String _type = "sales";
	public SCIOrderLine(String type) {
		super();
		this._type = type;
	}

	public SCIOrderLine(String sOrderLineNumber, String productId, double quantity, String type) {
		super();
		this._type = type;
		this.setOrderLineNumber(sOrderLineNumber);
		this.setProduct(productId);
		this.setQuantity(quantity);
	}

	@Override
	public String getBulkAPIURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProduct() {
		if(_type.equals("sales")) {
			return getString("product");
		}
		return getString("item");
	}
	public void setProduct(String product) {
		if(_type.equals("sales")) {
			setString("product", product);
		}
		setString("item", product);
	}
	public double getQuantity() {
		return getDouble("quantity");
	}
	public void setQuantity(double quantity) {
		setDouble("quantity", quantity);
	}
	public String getOrderLineNumber() {
		return getString(_type + "OrderLineNumber");
	}
	public void setOrderLineNumber(String value) {
		setString(_type + "OrderLineNumber", value);
	}
	
	public String getShipment() {
		return getString(_type + "Shipment");
	}
	public void setShipment(String shipment) {
		setString(_type + "Shipment", shipment);
	}
}
