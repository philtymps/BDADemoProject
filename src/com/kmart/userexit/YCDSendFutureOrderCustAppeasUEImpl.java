package com.kmart.userexit;

import org.w3c.dom.Document;

import com.kmart.util.ExUtils;
import com.yantra.pca.ycd.japi.ue.YCDSendFutureOrderCustomerAppeasementUE;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

public class YCDSendFutureOrderCustAppeasUEImpl implements YCDSendFutureOrderCustomerAppeasementUE{
	//initialize logger
	private static final YFCLogCategory LOGGER = YFCLogCategory.instance(YCDSendFutureOrderCustAppeasUEImpl.class);
	@Override
	public Document sendFutureOrderCustomerAppeasement(YFSEnvironment env,
			Document inDoc) throws YFSUserExitException {
		YFCDocument inputXml = YFCDocument.getDocumentFor(inDoc);
		System.out.println(inputXml);
		if (LOGGER.isVerboseEnabled()) {
		    LOGGER.verbose("docInput to processInvoice: " + inputXml.toString());
		   }
		YFCElement eleOrder = inputXml.getDocumentElement();
		String strOrderHeaderKey = eleOrder.getAttribute("OrderHeaderKey");
		YFCDocument yfcDocgetOrderList = YFCDocument.createDocument("Order");
        YFCElement eleOrderIp = yfcDocgetOrderList.getDocumentElement();
        eleOrderIp.setAttribute("OrderHeaderKey"  , strOrderHeaderKey);
        
        YFCDocument yfcDocGetOrderListOp = ExUtils.invokeService(env,"GetOrderDetail",  yfcDocgetOrderList);
        YFCElement eleOrderOp = yfcDocGetOrderListOp.getDocumentElement();
        YFCElement eleOrderLines = eleOrderOp.createChild("OrderLines");
        YFCElement eleOrderLine = eleOrderLines.createChild("OrderLine");
        eleOrderLine.setAttribute("OrderedQty", "1");
        YFCElement eleItem = eleOrderLine.createChild("Item");
        eleItem.setAttribute("ItemID", "GiftCard");
        eleItem.setAttribute("UnitOfMeasure", "EACH");
        YFCElement eleLinePriceInfo = eleOrderLine.createChild("LinePriceInfo");
        eleLinePriceInfo.setAttribute("UnitPrice", "10");
        eleOrderOp.setAttribute("PaymentStatus", "PAID");
        eleOrderOp.setAttribute("PaymentRuleId", "APPEASEMENT");
        YFCElement elePaymentMethods = eleOrderOp.createChild("PaymentMethods");
        YFCElement elePaymentMethod = elePaymentMethods.createChild("PaymentMethod");
        elePaymentMethod.setAttribute("PaymentType", "APPEASEMENT");
        YFCElement elePaymentDetailsList = eleOrderOp.createChild("PaymentDetailsList");
        YFCElement elePaymentDetails = elePaymentDetailsList.createChild("PaymentDetails");
        elePaymentDetails.setAttribute("ChargeType", "CHARGE");
        System.out.println(yfcDocGetOrderListOp);
        ExUtils.invokeService(env,"PostAppeaseMsg",  yfcDocGetOrderListOp.getDocument());
        YFCDocument yfcDocSuccess = YFCDocument.createDocument("Success");
        return yfcDocSuccess.getDocument();
	}

	
}
