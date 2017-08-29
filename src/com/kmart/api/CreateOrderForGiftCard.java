package com.kmart.api;

import org.w3c.dom.Document;

import com.kmart.util.ExUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class CreateOrderForGiftCard {
	
	 private static final YFCLogCategory LOGGER = YFCLogCategory.instance(CreateOrderForGiftCard.class);

     public Document createOrder(YFSEnvironment env, Document inDoc) {
             YFCDocument inputXML = YFCDocument.getDocumentFor(inDoc);
             
             //fetch the root element from the Input xml.
             YFCElement eleOrder = inputXML.getDocumentElement();
             String strOrderNo = eleOrder.getAttribute("OrderNo");
             String strOrderHeaderKey=eleOrder.getAttribute("OrderHeaderKey");
             String strUnitPrice = eleOrder.getAttribute("UnitPrice");
             
             YFCDocument yfcDocgetOrderList = YFCDocument.createDocument("Order");
             YFCElement eleOrderIp = yfcDocgetOrderList.getDocumentElement();
             eleOrderIp.setAttribute("OrderHeaderKey"  , strOrderHeaderKey);
             
             YFCDocument yfcDocGetOrderListOp = ExUtils.invokeService(env,"GetOrderList",  yfcDocgetOrderList);
             YFCElement eleOrderOp = yfcDocGetOrderListOp.getDocumentElement();
             YFCElement eleOrderLines = eleOrderOp.createChild("OrderLines");
             YFCElement eleOrderLine = eleOrderLines.createChild("eleOrderLine");
             eleOrderLine.setAttribute("OrderQuantity", "1");
             eleOrderLine.setAttribute("PrimeLineNo", "1");
             YFCElement eleItem = eleOrderLine.createChild("Item");
             eleItem.setAttribute("ItemID", "GiftCard");
             eleItem.setAttribute("UnitOfMeasure", "EACH");
             YFCElement eleLinePriceInfo = eleOrderLine.createChild("LinePriceInfo");
             eleLinePriceInfo.setAttribute("UnitPrice", strUnitPrice);
             eleOrderOp.setAttribute("PaymentStatus", "PAID");
             eleOrderOp.setAttribute("PaymentRuleId", "APPEASEMENT");
             YFCElement elePaymentMethods = eleOrderOp.createChild("PaymentMethods");
             YFCElement elePaymentMethod = elePaymentMethods.createChild("PaymentMethod");
             elePaymentMethod.setAttribute("PaymentType", "APPEASEMENT");
             YFCElement elePaymentDetailsList = eleOrderOp.createChild("PaymentDetailsList");
             YFCElement elePaymentDetails = elePaymentDetailsList.createChild("PaymentDetails");
             elePaymentDetails.setAttribute("ChargeType", "CHARGE");
             Document yfcDocCreateOrderOp = ExUtils.invokeAPI(env,"createOrder",  yfcDocgetOrderList.getDocument());
             return yfcDocCreateOrderOp;
          
 }
}
