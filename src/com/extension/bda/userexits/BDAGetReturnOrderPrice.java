package com.extension.bda.userexits;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.OMPGetReturnOrderPriceUE;

public class BDAGetReturnOrderPrice extends BDAServiceApi implements OMPGetReturnOrderPriceUE, IBDAService {

	public static void main(String[] args) {
		BDAGetReturnOrderPrice m = new BDAGetReturnOrderPrice();
		try {
			Document response = m.getReturnOrderPrice(null, YFCDocument.getDocumentForXMLFile("/Users/pfaiola/Box Sync/Projects/QVC/UEInput.xml").getDocument());
			
			System.out.println(YFCDocument.getDocumentFor(response));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getReturnOrderPrice";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		// TODO Auto-generated method stub
		return getReturnOrderPrice(env, input);
	}

	@Override
	public Document getReturnOrderPrice(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		YFCDocument dInput = YFCDocument.getDocumentFor(inDoc);
		YFCElement eOrderInput = dInput.getDocumentElement();
		
		ArrayList<String> derivedOrders = new ArrayList<String>();
		for(YFCElement eOrderLine : eOrderInput.getChildElement("OrderLines", true).getChildren()) {
			if(!YFCCommon.isVoid(eOrderLine.getAttribute("DerivedFromOrderHeaderKey"))) {
				if(!derivedOrders.contains(eOrderLine.getAttribute("DerivedFromOrderHeaderKey"))) {
					derivedOrders.add(eOrderLine.getAttribute("DerivedFromOrderHeaderKey"));
				}
			}			
		}
		
		if(!derivedOrders.isEmpty()) {
			YFCDocument dOrderListInput = YFCDocument.createDocument("Order");
			YFCElement eOrderListInput = dOrderListInput.getDocumentElement();
			YFCElement eOr = eOrderListInput.createChild("ComplexQuery").createChild("Or");
			for(String sKey : derivedOrders) {
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "OrderHeaderKey");
				eExp.setAttribute("Value", sKey);
			}
			Document dOrderList = this.callApi(env, dOrderListInput.getDocument(), getOrderListTemplate(), "getOrderList");
			YFCDocument dSalesList = YFCDocument.getDocumentFor(dOrderList);
			System.out.println(dSalesList);
			for(YFCElement eSalesOrder : dSalesList.getDocumentElement().getChildren()) {
				Document getOrderPriceInput = constructOrderPrice(eSalesOrder, eOrderInput);
				
				Document getOrderPriceOutput = this.callApi(env, getOrderPriceInput, getOrderPriceTemplate(), "getOrderPrice");
				YFCDocument dOutput = YFCDocument.getDocumentFor(getOrderPriceOutput);
				System.out.println(dOutput);
				updateResponse(eSalesOrder, dOutput.getDocumentElement(), eOrderInput);
			}
		}
		
		
		
		
		return dInput.getDocument();
	}
	
	private void updateResponse(YFCElement eOriginalSalesOrder, YFCElement eRepricedOrder, YFCElement eResponseOrder) {
		
		if(!YFCCommon.isVoid(eResponseOrder.getChildElement("HeaderCharges"))) {
			eResponseOrder.removeChild(eResponseOrder.getChildElement("HeaderCharges"));
		}
		compareCharges(eOriginalSalesOrder, eRepricedOrder, eResponseOrder, "Header");
		for(YFCElement eResponseLine : eResponseOrder.getChildElement("OrderLines", true).getChildren()) {
			if(YFCCommon.equals(eOriginalSalesOrder.getAttribute("OrderHeaderKey"), eResponseLine.getAttribute("DerivedFromOrderHeaderKey"))) {
				for(YFCElement eSalesOrderLine : eOriginalSalesOrder.getChildElement("OrderLines", true).getChildren()) {
					if(YFCCommon.equals(eSalesOrderLine.getAttribute("OrderLineKey"), eResponseLine.getAttribute("DerivedFromOrderLineKey"))) {
						for(YFCElement eRepricedLine : eRepricedOrder.getChildElement("OrderLines", true).getChildren()) {
							if(YFCCommon.equals(eRepricedLine.getAttribute("LineID"), eSalesOrderLine.getAttribute("OrderLineKey"))) {
								if(!YFCCommon.isVoid(eResponseLine.getChildElement("LineCharges"))) {
									eResponseLine.removeChild(eResponseLine.getChildElement("LineCharges"));
								}
								compareCharges(eSalesOrderLine, eRepricedLine, eResponseLine, "Line");
							}
						}
					}
				}
			}
		}
		YFCElement eHeaderCharge = eResponseOrder.getChildElement("HeaderCharges", true).createChild("HeaderCharge");
		eHeaderCharge.setAttribute("ChargeCategory", "RESTOCK");
		eHeaderCharge.setAttribute("ChargeName", "Restocking");
		eHeaderCharge.setAttribute("ChargeAmount", "10.95");
		eHeaderCharge.setAttribute("IsBillable", "Y");
		eHeaderCharge.setAttribute("IsDiscount", "N");
	}
	
	
	private void compareCharges(YFCElement eOriginal, YFCElement eRepriced, YFCElement eResponse, String sChargeLevel) {
		String sAdjustment = sChargeLevel.equals("Line") ? "Line" : "";
		
		for(YFCElement eCharge : eOriginal.getChildElement(sChargeLevel + "Charges").getChildren()) {
			if(eCharge.getBooleanAttribute("IsRefundable", true)) {
				boolean found = false;
				for(YFCElement eAdjustment : eRepriced.getChildElement(sAdjustment + "Adjustments", true).getChildren()) {
					if(YFCCommon.equals(eCharge.getAttribute("ChargeCategory"), eAdjustment.getAttribute("ChargeCategory"))) {
						if(YFCCommon.equals(eCharge.getAttribute("ChargeName"), eAdjustment.getAttribute("ChargeName"))) {
							found = true;
							if(YFCCommon.equals(sChargeLevel, "Line")) {
								if(eCharge.getDoubleAttribute("ChargePerUnit", 0) > eAdjustment.getDoubleAttribute("AdjustmentPerUnit", 0)) {
									YFCElement eRCharge = eResponse.getChildElement(sChargeLevel + "Charges", true).createChild(sChargeLevel + "Charge");
									setAttributeIfExists(eCharge, eRCharge, "ChargeCategory", "ChargeCategory");
									setAttributeIfExists(eCharge, eRCharge, "ChargeName", "ChargeName");
									setAttributeIfExists(eCharge, eRCharge, "IsDiscount", "IsDiscount");
									setAttributeIfExists(eCharge, eRCharge, "IsBillable", "IsBillable");
									eRCharge.setAttribute("ChargePerUnit", eCharge.getDoubleAttribute("ChargePerUnit") - eAdjustment.getDoubleAttribute("AdjustmentPerUnit"));
								} else if(eCharge.getDoubleAttribute("ChargePerLine", 0) > eAdjustment.getDoubleAttribute("AdjustmentPerLine")) {
									YFCElement eRCharge = eResponse.getChildElement(sChargeLevel + "Charges", true).createChild(sChargeLevel + "Charge");
									setAttributeIfExists(eCharge, eRCharge, "ChargeCategory", "ChargeCategory");
									setAttributeIfExists(eCharge, eRCharge, "ChargeName", "ChargeName");
									setAttributeIfExists(eCharge, eRCharge, "IsDiscount", "IsDiscount");
									setAttributeIfExists(eCharge, eRCharge, "IsBillable", "IsBillable");
									eRCharge.setAttribute("ChargePerLine", eCharge.getDoubleAttribute("ChargePerLine") - eAdjustment.getDoubleAttribute("AdjustmentPerLine"));
								}
							} else {
								if(eCharge.getDoubleAttribute("ChargeAmount", 0) > eAdjustment.getDoubleAttribute("AdjustmentApplied")) {
									YFCElement eRCharge = eResponse.getChildElement(sChargeLevel + "Charges", true).createChild(sChargeLevel + "Charge");
									setAttributeIfExists(eCharge, eRCharge, "ChargeCategory", "ChargeCategory");
									setAttributeIfExists(eCharge, eRCharge, "ChargeName", "ChargeName");
									setAttributeIfExists(eCharge, eRCharge, "IsDiscount", "IsDiscount");
									setAttributeIfExists(eCharge, eRCharge, "IsBillable", "IsBillable");
									eRCharge.setAttribute("ChargeAmount", eCharge.getDoubleAttribute("ChargeAmount") - eAdjustment.getDoubleAttribute("AdjustmentApplied"));
								}
							}
						}
					}
				}
				if(!found) {
					if(YFCCommon.equals(sChargeLevel, "Line")) {
						YFCElement eRCharge = eResponse.getChildElement(sChargeLevel + "Charges", true).createChild(sChargeLevel + "Charge");
						setAttributeIfExists(eCharge, eRCharge, "ChargeCategory", "ChargeCategory");
						setAttributeIfExists(eCharge, eRCharge, "ChargeName", "ChargeName");
						setAttributeIfExists(eCharge, eRCharge, "IsDiscount", "IsDiscount");
						setAttributeIfExists(eCharge, eRCharge, "IsBillable", "IsBillable");
						
						eRCharge.setAttribute("ChargePerUnit", eCharge.getDoubleAttribute("ChargePerUnit"));
						eRCharge.setAttribute("ChargePerLine", eCharge.getDoubleAttribute("ChargePerLine"));
					} else {
						YFCElement eRCharge = eResponse.getChildElement(sChargeLevel + "Charges", true).createChild(sChargeLevel + "Charge");
						setAttributeIfExists(eCharge, eRCharge, "ChargeCategory", "ChargeCategory");
						setAttributeIfExists(eCharge, eRCharge, "ChargeName", "ChargeName");
						setAttributeIfExists(eCharge, eRCharge, "IsDiscount", "IsDiscount");
						setAttributeIfExists(eCharge, eRCharge, "IsBillable", "IsBillable");
						eRCharge.setAttribute("ChargeAmount", eCharge.getDoubleAttribute("ChargeAmount"));
					}
				}
			}
			
		}
	}
	
	private Document getOrderPriceTemplate() {
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOutput.getDocumentElement();
		eOrder.setAttribute("OrderReference", "");
		eOrder.setAttribute("OrderAdjustment", "");
		eOrder.setAttribute("OrderTotal", "");
		
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("LineID", "");
		eOrderLine.setAttribute("UnitPrice", "");
		eOrderLine.setAttribute("Quantity", "");
		
		YFCElement eLineAdjustment = eOrderLine.createChild("LineAdjustments").createChild("Adjustment");
		eLineAdjustment.setAttribute("AdjustmentApplied", "");
		eLineAdjustment.setAttribute("AdjustmentPerLine", "");
		eLineAdjustment.setAttribute("AdjustmentPerUnit", "");
		eLineAdjustment.setAttribute("AdjustmentAvailable", "");
		eLineAdjustment.setAttribute("AdjustmentQuantity", "");
		eLineAdjustment.setAttribute("ChargeCategory", "");
		eLineAdjustment.setAttribute("ChargeName", "");
		
		YFCElement eLineTax = eOrderLine.createChild("LineTaxes").createChild("LineTax");
		eLineTax.setAttribute("ChargeCategory", "");
		eLineTax.setAttribute("ChargeName", "");
		eLineTax.setAttribute("InvoicedTax", "");
		eLineTax.setAttribute("RemainingTax", "");
		eLineTax.setAttribute("Tax", "");
		eLineTax.setAttribute("TaxName", "");
		eLineTax.setAttribute("TaxPercentage", "");
		
		YFCElement eAdjustment = eOrderLine.createChild("Adjustments").createChild("Adjustment");
		eAdjustment.setAttribute("AdjustmentApplied", "");
		eAdjustment.setAttribute("AdjustmentPerLine", "");
		eAdjustment.setAttribute("AdjustmentPerUnit", "");
		eAdjustment.setAttribute("AdjustmentAvailable", "");
		eAdjustment.setAttribute("AdjustmentQuantity", "");
		eAdjustment.setAttribute("ChargeCategory", "");
		eAdjustment.setAttribute("ChargeName", "");
		
		YFCElement eHeaderTax = eOrder.createChild("HeaderTaxes").createChild("HeaderTax");
		eHeaderTax.setAttribute("ChargeCategory", "");
		eHeaderTax.setAttribute("ChargeName", "");
		eHeaderTax.setAttribute("InvoicedTax", "");
		eHeaderTax.setAttribute("RemainingTax", "");
		eHeaderTax.setAttribute("Tax", "");
		eHeaderTax.setAttribute("TaxName", "");
		eHeaderTax.setAttribute("TaxPercentage", "");
		
		return dOutput.getDocument();
	}
	
	private Document constructOrderPrice(YFCElement eSalesOrderDetails, YFCElement eReturnOrder) {
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		setAttributeIfExists(eSalesOrderDetails, eOrder, "BuyerUserId", "BuyerUserId");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "CustomerContactID", "CustomerContactID");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "BillToID", "CustomerID");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "DocumentType", "DocumentType");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "EnterpriseCode", "EnterpriseCode");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "EnterpriseCode", "OrganizationCode");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "SellerOrganizationCode", "SellerOrganizationCode");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "OrderHeaderKey", "OrderReference");
		setAttributeIfExists(eSalesOrderDetails, eOrder, "OrderDate", "PricingDate");
		setAttributeIfExists(eSalesOrderDetails.getChildElement("PriceInfo", true), eOrder, "Currency", "Currency");
		for(YFCElement eSalesLine : eSalesOrderDetails.getChildElement("OrderLines", true).getChildren()) {
			YFCElement eOrderLine = eOrder.getChildElement("OrderLines", true).createChild("OrderLine");
			setAttributeIfExists(eSalesLine, eOrderLine, "DeliveryMethod", "DeliveryMethod");
			setAttributeIfExists(eSalesLine, eOrderLine, "IsPriceLocked", "IsPriceLocked");
			setAttributeIfExists(eSalesLine, eOrderLine, "OrderLineKey", "LineID");
			setAttributeIfExists(eSalesLine, eOrderLine, "OrderedQty", "Quantity");
			setAttributeIfExists(eSalesLine.getChildElement("Item", true), eOrderLine, "ItemID", "ItemID");
			setAttributeIfExists(eSalesLine.getChildElement("Item", true), eOrderLine, "UnitOfMeasure", "UnitOfMeasure");
			for(YFCElement eReturnLine : eReturnOrder.getChildElement("OrderLines", true).getChildren()) {
				if(YFCCommon.equals(eReturnLine.getAttribute("DerivedFromOrderLineKey"), eSalesLine.getAttribute("OrderLineKey"))) {
					eOrderLine.setAttribute("Quantity", (eOrderLine.getIntAttribute("Quantity", 0) - eReturnLine.getIntAttribute("OrderedQty", 0)));
				}
			}
		}
		
		for(YFCElement eSalesPromotion : eSalesOrderDetails.getChildElement("Promotions", true).getChildren()) {
			YFCElement eCoupon = eOrder.getChildElement("Coupons", true).createChild("Coupon");
			eCoupon.setAttribute("CouponID", eSalesPromotion.getAttribute("PromotionId"));
		}
		
		
		return dOrder.getDocument();
	}
	
	private void setAttributeIfExists(YFCElement eFrom, YFCElement eTo, String sFrom, String sTo) {
		if(!YFCCommon.isVoid(eFrom) && !YFCCommon.isVoid(eTo)){
			if(!YFCCommon.isVoid(eFrom.getAttribute(sFrom))) {
				eTo.setAttribute(sTo, eFrom.getAttribute(sFrom));
			}
		}
	}
	
	private Document getOrderListTemplate() {
		YFCDocument dOrderList = YFCDocument.createDocument("OrderList");
		YFCElement eOrderList = dOrderList.getDocumentElement();
		YFCElement eOrder = eOrderList.createChild("Order");
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("BillToID", "");
		eOrder.setAttribute("CustomerContactID", "");
		eOrder.setAttribute("BuyerUserId", "");
		eOrder.setAttribute("OrderDate", "");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("Currency", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("DeliveryMethod", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement eLineCharge = eOrderLine.createChild("LineCharges").createChild("LineCharge");
		eLineCharge.setAttribute("ChargeAmount", "");
		eLineCharge.setAttribute("ChargeCategory", "");
		eLineCharge.setAttribute("ChargeName", "");
		eLineCharge.setAttribute("ChargeNameKey", "");
		eLineCharge.setAttribute("ChargePerLine", "");
		eLineCharge.setAttribute("ChargePerUnit", "");
		eLineCharge.setAttribute("IsRefundable", "");
		eLineCharge.setAttribute("IsDiscount", "");
		eLineCharge.setAttribute("IsBillable", "");
		eLineCharge.setAttribute("IsManual", "");
		YFCElement eLineTax = eOrderLine.createChild("LineTaxes").createChild("LineTax");
		eLineTax.setAttribute("ChargeCategory", "");
		eLineTax.setAttribute("ChargeName", "");
		eLineTax.setAttribute("InvoicedTax", "");
		eLineTax.setAttribute("RemainingTax", "");
		eLineTax.setAttribute("Tax", "");
		eLineTax.setAttribute("TaxName", "");
		eLineTax.setAttribute("TaxPercentage", "");
		YFCElement eHeaderCharge = eOrder.createChild("HeaderCharges").createChild("HeaderCharge");
		eHeaderCharge.setAttribute("ChargeAmount", "");
		eHeaderCharge.setAttribute("ChargeCategory", "");
		eHeaderCharge.setAttribute("ChargeName", "");
		eHeaderCharge.setAttribute("ChargeNameKey", "");
		eHeaderCharge.setAttribute("InvoicedChargeAmount", "");
		eHeaderCharge.setAttribute("IsRefundable", "");
		eHeaderCharge.setAttribute("IsDiscount", "");
		eHeaderCharge.setAttribute("IsBillable", "");
		eHeaderCharge.setAttribute("IsManual", "");
		YFCElement eHeaderTax = eOrder.createChild("HeaderTaxes").createChild("HeaderTax");
		eHeaderTax.setAttribute("ChargeCategory", "");
		eHeaderTax.setAttribute("ChargeName", "");
		eHeaderTax.setAttribute("InvoicedTax", "");
		eHeaderTax.setAttribute("RemainingTax", "");
		eHeaderTax.setAttribute("Tax", "");
		eHeaderTax.setAttribute("TaxName", "");
		eHeaderTax.setAttribute("TaxPercentage", "");
		YFCElement ePromotion = eOrder.createChild("Promotions").createChild("Promotion");
		ePromotion.setAttribute("PromotionId", "");
		ePromotion.setAttribute("PromotionGroup", "");
		ePromotion.setAttribute("PromotionApplied", "");
		return dOrderList.getDocument();
	}
	

}
