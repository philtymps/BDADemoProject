package com.objects;

import com.yantra.yfc.dom.YFCElement;

public class OrderLine {

	public OrderLine(int qty, String sku, String carrier_service, String zipcode) {
		super();
		this.qty = qty;
		this.sku = sku;
		this.carrier_service = carrier_service;
		this.zipcode = zipcode;
	}
	private int qty;
	private String sku, carrier_service, zipcode;
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getCarrier_service() {
		String[] types = {"Standard Ground",  "1BUSDAY", "2BUSDAY"};
		if(carrier_service.equals("TBD-GRND") || carrier_service.equals("GRND")){
			return "Standard Ground";
		} else if (carrier_service.equals("TBD-SR2DAY")){
			return "2BUSDAY";
		} else if (carrier_service.equals("TBD-TDAY")){
			return "1BUSDAY";
		}
		return "Standard Ground";
	}
	public void setCarrier_service(String carrier_service) {
		this.carrier_service = carrier_service;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public void createLine(YFCElement eOrderLines, int lineNo){
		YFCElement eOrderLine = eOrderLines.createChild("OrderLine");
		eOrderLine.setAttribute("DeliveryMethod", "SHP");
		eOrderLine.setAttribute("OrderedQty", getQty());
		eOrderLine.setAttribute("PrimeLineNo", lineNo);
		YFCElement eItem = eOrderLine.createChild("Item");
		eOrderLine.setAttribute("CarrierServiceCode", getCarrier_service());
		eItem.setAttribute("ItemID", this.getSku());
		eItem.setAttribute("UnitOfMeasure", "EACH");

	}
}
