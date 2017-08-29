package com.kmart.userexit;


import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.pca.ycd.japi.ue.YCDGetAppeasementOffersUE;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDoubleUtils;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
 
public class YCDGetAppeasementOffersUEImpl implements YCDGetAppeasementOffersUE {
    private String preferredType;
 
    private boolean isPreferredTypeSet;
 
    private static YFCLogCategory cat = YFCLogCategory
            .instance(YCDGetAppeasementOffersUEImpl.class);
 
    public YCDGetAppeasementOffersUEImpl() {
        this.preferredType = "FLAT_AMOUNT_ORDER";
        this.isPreferredTypeSet = false;
    }
 
    public Document getAppeasementOffers(YFSEnvironment env, Document inDoc)
            throws YFSUserExitException {
        YFCDocument dIn = YFCDocument.getDocumentFor(inDoc);
        if (dIn == null) {
            return null;
        }
        YFCDocument dOut = YFCDocument.createDocument("AppeasementOffers");
        YFCElement eOut = dOut.getDocumentElement();
 
        YFCElement eIn = dIn.getDocumentElement();
 
        YFCElement order = eIn.getChildElement("Order");
 
        eOut = setAppeasementOffersForOrder(eOut, eIn);
 
        return dOut.getDocument();
    }
 
    private YFCElement setAppeasementOffersForOrder(YFCElement eOut,
            YFCElement eIn) {
        YFCElement eOrder = eIn.getChildElement("Order");
        YFCElement eSelectedReason = eOrder
                .getChildElement("AppeasementReason");
        String sAppeasementReasonCode = eSelectedReason
                .getAttribute("ReasonCode");
 
        YFCElement eOrderIn = eIn.getChildElement("Order");
        String sOrderHeaderKey = eOrderIn.getAttribute("OrderHeaderKey");
 
        setChargeCataegoryAndNameForAppeasementOffers(eOut);
 
        eOut.setAttribute("OrderHeaderKey", sOrderHeaderKey);
        eOut.setAttribute("ReasonCode", sAppeasementReasonCode);
 
        YFCElement eAppeasementOffers = getAppeasementOffersList(eIn);
        if (eAppeasementOffers != null) {
            Iterator iteratorAppeasementOffers = eAppeasementOffers
                    .getChildren();
            while ((iteratorAppeasementOffers != null)
                    && (iteratorAppeasementOffers.hasNext())) {
                YFCElement eAppeasementOffer = (YFCElement) iteratorAppeasementOffers
                        .next();
                eOut = setAppeasementOffer(eOrderIn, eOut, eAppeasementOffer);
            }
        }
 
        eOut = setPreferredOffer(eOut);
        return eOut;
    }
 
    private YFCElement setAppeasementOffer(YFCElement eOrderIn,
            YFCElement eOut, YFCElement eAppeasementOffer) {
        String offerType = eAppeasementOffer.getAttribute("OfferType");
        if (offerType.equalsIgnoreCase("PERCENT_FUTURE_ORDER")) {
            eOut.importNode(eAppeasementOffer);
            return eOut;
        }
        YFCElement eOrderAppeasementOffer = eAppeasementOffer
                .createChild("Order");
        YFCElement eOrderLinesAppeasementOffer = eAppeasementOffer
                .createChild("OrderLines");
        eOrderAppeasementOffer.setAttribute("HeaderOfferAmount", "");
        setChargeCataegoryAndNameForAppeasementOffers(eOrderAppeasementOffer);
        double discountPercent = 0.0D;
        double offerAmount = 0.0D;
        String strDiscountPercent = eAppeasementOffer
                .getAttribute("DiscountPercent");
        String strOfferAmount = eAppeasementOffer.getAttribute("OfferAmount");
        if ((strDiscountPercent.trim() != null)
                && (!(strDiscountPercent.equalsIgnoreCase("")))) {
            discountPercent = Double.parseDouble(strDiscountPercent);
        }
        if ((offerType.equalsIgnoreCase("FLAT_AMOUNT_ORDER"))
                || (offerType.equalsIgnoreCase("APPEASE_SHIPPING_CHARGES"))
                || (offerType.equalsIgnoreCase("VARIABLE_PERCENT_AMOUNT_ORDER"))) {
            discountPercent = 0.0D;
        }
 
        if ((strOfferAmount.trim() != null)
                && (!(strOfferAmount.equalsIgnoreCase(""))))
            offerAmount = YFCDoubleUtils.roundOff(
                    Double.parseDouble(strOfferAmount), 2);
        double totalLineOfferAmounts = 0.0D;
        YFCElement eOrderLines = eOrderIn.getChildElement("OrderLines");
 
        Iterator iteratorOrderLines = eOrderLines.getChildren();
        while ((iteratorOrderLines != null) && (iteratorOrderLines.hasNext())) {
            YFCElement eOrderLine = (YFCElement) iteratorOrderLines.next();
            YFCElement eOrderLineAppeasementOffer = eOrderLinesAppeasementOffer
                    .createChild("OrderLine");
            String sOrderLineKey = eOrderLine.getAttribute("OrderLineKey");
            eOrderLineAppeasementOffer.setAttribute("OrderLineKey",
                    sOrderLineKey);
            String sOrderedQty = eOrderLine.getAttribute("OrderedQty");
            eOrderLineAppeasementOffer.setAttribute("OrderedQty", sOrderedQty);
            if (discountPercent != 0.0D) {
                YFCElement eLineOverallTotals = eOrderLine
                        .getChildElement("LineOverallTotals");
                double lineGrandTotal = Double.parseDouble(eLineOverallTotals
                        .getAttribute("LineTotal"));
                double lineOfferAmount = lineGrandTotal * 0.01D
                        * discountPercent;
                eOrderLineAppeasementOffer.setAttribute("LineOfferAmount",
                        YFCDoubleUtils.roundOff(lineOfferAmount, 2));
                totalLineOfferAmounts += lineOfferAmount;
            } else {
                eOrderLineAppeasementOffer.setAttribute("LineOfferAmount",
                        "0.0");
            }
            setChargeCataegoryAndNameForAppeasementOffers(eOrderLineAppeasementOffer);
        }
 
        if ((offerType.equalsIgnoreCase("FLAT_AMOUNT_ORDER"))
                || (offerType.equalsIgnoreCase("VARIABLE_AMOUNT_ORDER"))
                || (offerType.equalsIgnoreCase("APPEASE_SHIPPING_CHARGES"))
                || (offerType.equalsIgnoreCase("VARIABLE_PERCENT_AMOUNT_ORDER"))) {
            eOrderAppeasementOffer.setAttribute("HeaderOfferAmount",
                    YFCDoubleUtils.roundOff(
                            offerAmount - totalLineOfferAmounts, 2));
        }
        eOut.importNode(eAppeasementOffer);
        return eOut;
    }
 
    private YFCElement setPreferredOffer(YFCElement eOut) {
        return eOut;
    }
 
    private YFCElement getAppeasementOffersList(YFCElement eIn) {
        YFCElement eAppeasementOffers = YFCDocument.createDocument(
                "AppeasementOffers").getDocumentElement();
        YFCElement eOrderIn = eIn.getChildElement("Order");
        YFCElement eOverallTotalIn = eOrderIn.getChildElement("OverallTotals");
        double orderGrandTotal = Double.parseDouble(eOverallTotalIn
                .getAttribute("GrandTotal"));
 
        Properties propertyMap = new Properties();
        try {
            propertyMap
                    .load(YCDGetAppeasementOffersUEImpl.class
                            .getResourceAsStream("/resources/ycd_appeasement_variable.properties"));
        } catch (IOException e) {
            cat.debug("IO Exception", e);
        }
        String flatAmountOrder = "";
        String percentOrder = "";
        String percentFutureOrder = "";
        flatAmountOrder = propertyMap.getProperty("FLAT_AMOUNT_ORDER");
        percentOrder = propertyMap.getProperty("PERCENT_ORDER");
        percentFutureOrder = propertyMap.getProperty("PERCENT_FUTURE_ORDER");
        this.preferredType = propertyMap.getProperty("YCD_PREFERRED");
        String[] flatAmountOrders = flatAmountOrder.split(",");
        String[] percentOrders = percentOrder.split(",");
        String[] percentFutureOrders = percentFutureOrder.split(",");
        String showVariableOffer = propertyMap
                .getProperty("VARIABLE_AMOUNT_ORDER");
        String showVariablePercentOffer = propertyMap
                .getProperty("VARIABLE_PERCENT_AMOUNT_ORDER");
        String showShippingChargesOffer = propertyMap
                .getProperty("APPEASE_SHIPPING_CHARGES");
        String showVariablePercentFutureOffer = propertyMap
                .getProperty("VARIABLE_PERCENT_FUTURE_AMOUNT_ORDER");
        String showVariableFutureOffer = propertyMap
                .getProperty("VARIABLE_FUTURE_AMOUNT_ORDER");
        for (int i = 0; i < flatAmountOrders.length; ++i) {
            if (!(YFCCommon.isVoid(flatAmountOrders[i])))
                eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                        "FLAT_AMOUNT_ORDER",
                        Double.parseDouble(flatAmountOrders[i]),
                        orderGrandTotal);
        }
        for (int i = 0; i < percentOrders.length; ++i) {
            if (!(YFCCommon.isVoid(percentOrders[i])))
                eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                        "PERCENT_ORDER", Double.parseDouble(percentOrders[i]),
                        orderGrandTotal);
        }
        for (int i = 0; i < percentFutureOrders.length; ++i) {
            if (!(YFCCommon.isVoid(percentFutureOrders[i])))
                eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                        "PERCENT_FUTURE_ORDER",
                        Double.parseDouble(percentFutureOrders[i]),
                        orderGrandTotal);
        }
        if ("Y".equalsIgnoreCase(showVariableOffer)) {
            eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                    "VARIABLE_AMOUNT_ORDER", 0.0D, orderGrandTotal);
        }
        if ("Y".equalsIgnoreCase(showVariableFutureOffer)) {
            eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                    "VARIABLE_FUTURE_AMOUNT_ORDER", 0.0D, orderGrandTotal);
        }
        if ("Y".equalsIgnoreCase(showVariablePercentOffer)) {
            eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                    "VARIABLE_PERCENT_AMOUNT_ORDER", 0.0D, orderGrandTotal);
        }
        if ("Y".equalsIgnoreCase(showVariablePercentFutureOffer)) {
            eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                    "VARIABLE_PERCENT_FUTURE_AMOUNT_ORDER", 0.0D,
                    orderGrandTotal);
        }
        if ("Y".equalsIgnoreCase(showShippingChargesOffer)) {
            eAppeasementOffers = createAppeasementOffer(eAppeasementOffers,
                    "APPEASE_SHIPPING_CHARGES", 0.0D, orderGrandTotal);
        }
 
        return eAppeasementOffers;
    }
 
    private YFCElement createAppeasementOffer(YFCElement eAppeasementOffers,
            String sOfferType, double iDiscountPercent, double orderGrandTotal) {
        YFCElement eAppeasementOffer = YFCDocument.createDocument(
                "AppeasementOffer").getDocumentElement();
        if (sOfferType.equalsIgnoreCase("APPEASE_SHIPPING_CHARGES")) {
            eAppeasementOffer.setAttribute("IsShippingChargeOffer", "Y");
            eAppeasementOffer.setAttribute("TotalToConsider",
                    String.valueOf(orderGrandTotal));
        }
        if (sOfferType.equalsIgnoreCase("VARIABLE_FUTURE_AMOUNT_ORDER")) {
            eAppeasementOffer.setAttribute("IsVariableFutureOrderOffer", "Y");
        }
 
        if (sOfferType.equalsIgnoreCase("VARIABLE_PERCENT_AMOUNT_ORDER")) {
            eAppeasementOffer.setAttribute("IsVariablePercentOffer", "Y");
            eAppeasementOffer.setAttribute("TotalToConsider",
                    String.valueOf(orderGrandTotal));
        }
        if (sOfferType.equalsIgnoreCase("VARIABLE_PERCENT_FUTURE_AMOUNT_ORDER")) {
            eAppeasementOffer.setAttribute("IsVariableFuturePercentOrderOffer",
                    "Y");
            eAppeasementOffer.setAttribute("TotalToConsider",
                    String.valueOf(orderGrandTotal));
        }
        eAppeasementOffer.setAttribute("OfferType", sOfferType);
        if (iDiscountPercent != 0.0D) {
            eAppeasementOffer.setAttribute("DiscountPercent", iDiscountPercent);
            double offerAmount = 0.0D;
            if (!(sOfferType.equalsIgnoreCase("PERCENT_FUTURE_ORDER"))) {
                if (sOfferType.equalsIgnoreCase("FLAT_AMOUNT_ORDER"))
                    offerAmount = iDiscountPercent;
                else {
                    offerAmount = iDiscountPercent * 0.01D * orderGrandTotal;
                }
            }
           eAppeasementOffer.setAttribute("OfferAmount",
                    YFCDoubleUtils.roundOff(offerAmount, 2));
        } else {
            eAppeasementOffer.setAttribute("DiscountPercent", "");
            if (sOfferType.equalsIgnoreCase("FLAT_AMOUNT_ORDER"))
                eAppeasementOffer.setAttribute("OfferAmount",
                        YFCDoubleUtils.roundOff(10.0D, 2));
            else {
                eAppeasementOffer.setAttribute("OfferAmount", "");
            }
        }
        if (YFCCommon.isVoid(this.preferredType)) {
            this.preferredType = "FLAT_AMOUNT_ORDER";
        }
        if ((YFCCommon.equalsIgnoreCase(sOfferType, this.preferredType))
                && (!(this.isPreferredTypeSet))) {
            eAppeasementOffer.setAttribute("Preferred", "Y");
            this.isPreferredTypeSet = true;
        } else {
            eAppeasementOffer.setAttribute("Preferred", "N");
        }
        eAppeasementOffers.importNode(eAppeasementOffer);
        return eAppeasementOffers;
    }
 
    private void setChargeCataegoryAndNameForAppeasementOffers(YFCElement eOut) {
        eOut.setAttribute("ChargeCategory", "CUSTOMER_APPEASEMENT");
        eOut.setAttribute("ChargeName", "CUSTOMER_APPEASEMENT");
    }
}