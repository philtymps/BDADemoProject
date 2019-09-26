package com.extension.bda.service.manufacturing;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDACreateProductionOrder extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "createProductionOrder";
	}

	public static YFCDocument getOrderDetailsTemplate() {
		try {
			return YFCDocument.parse(BDACreateProductionOrder.class.getResourceAsStream("OrderDetailTemplate.xml"));
		} catch (Exception e) {
		
		}
		return null;
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		Document poOrder = this.callApi(env, input, getOrderDetailsTemplate().getDocument(), "getOrderDetails");
		YFCElement ePO = YFCDocument.getDocumentFor(poOrder).getDocumentElement();
		
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eChained = dInput.getDocumentElement();
		eChained.setAttribute("EnterpriseCode", ePO.getAttribute("SellerOrganizationCode"));
		eChained.setAttribute("SellerOrganizationCode", ePO.getAttribute("SellerOrganizationCode"));
		eChained.setAttribute("ShipToKey", ePO.getAttribute("ShipToKey"));
		eChained.setAttribute("BillToKey", ePO.getAttribute("BillToKey"));
		eChained.setAttribute("DocumentType", "0001");
		for(YFCElement eOrderLine : ePO.getChildElement("OrderLines", true).getChildren()){
			YFCElement eLine = eChained.getChildElement("OrderLines", true).createChild("OrderLine");
			eLine.setAttribute("PrimeLineNo", eOrderLine.getAttribute("PrimeLineNo"));
			eLine.setAttribute("SubLineNo", eOrderLine.getAttribute("SubLineNo"));
			eLine.setAttribute("OrderedQty", eOrderLine.getAttribute("OrderedQty"));
			eLine.setAttribute("DeliveryMethod", eOrderLine.getAttribute("DeliveryMethod"));
			eLine.setAttribute("ReceivingNode", eOrderLine.getAttribute("ReceivingNode"));
			eLine.setAttribute("ShipNode", eOrderLine.getAttribute("ShipNode"));
			eLine.setAttribute("ReqShipDate", eOrderLine.getAttribute("ReqShipDate"));
			eLine.setAttribute("DerivedFromOrderHeaderKey", ePO.getAttribute("OrderHeaderKey"));
			YFCElement eItem = eLine.createChild("Item");
			eItem.setAttribute("ItemID", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
			eItem.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item", true).getAttribute("UnitOfMeasure"));
		}
		return this.callApi(env, dInput.getDocument(), null, "createOrder");
	}

	
}
