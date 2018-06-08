package com.extension.bda.service.returns;

import java.rmi.RemoteException;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDACreateReturnTransfer implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "createReturnTransfer";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub

	}
	
	private static Document getReceiptTemplate() {
		YFCDocument dReceipt = YFCDocument.createDocument("Receipt");
		dReceipt.getDocumentElement().setAttribute("ReceiptHeaderKey", "");
		dReceipt.getDocumentElement().setAttribute("DocumentType", "");
		return dReceipt.getDocument();
	}

	private Document createReceipt(YFSEnvironment env, YFCDocument dOrder) throws Exception {
		YFCElement eOrder=  dOrder.getDocumentElement();
		
		YFCDocument dInput = YFCDocument.createDocument("Receipt");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("DocumentType", "0003");
		eInput.setAttribute("ReceiptNo", System.currentTimeMillis());
		YFCElement eShipment = eInput.getChildElement("Shipment", true);
		eShipment.setAttribute("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
			if(YFCCommon.isVoid(eInput.getAttribute("ReceivingNode")) && !YFCCommon.isVoid(eOrderLine.getAttribute("ShipNode"))) {
				eInput.setAttribute("ReceivingNode", eOrderLine.getAttribute("ShipNode"));
			}
			YFCElement eReceiptLine = eInput.getChildElement("ReceiptLines", true).createChild("ReceiptLine");
			eReceiptLine.setAttribute("OrderLineKey", eOrderLine.getAttribute("OrderLineKey"));
			eReceiptLine.setAttribute("ItemID", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
			eReceiptLine.setAttribute("Quantity", eOrderLine.getAttribute("OrderedQty"));
			eReceiptLine.setAttribute("DispositionCode", YFCCommon.isVoid(eOrderLine.getAttribute("DispositionCode")) ? eOrderLine.getAttribute("DispositionCode") : "QC-PASSED");
			eReceiptLine.setAttribute("InspectedBy", "awhite");
		}
		
		if(!YFCCommon.isVoid(eInput.getAttribute("ReceivingNode"))) {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("receiveOrder", getReceiptTemplate());
			System.out.println("receiveOrder Input: " + dInput);
			Document response = localApi.invoke(env, "receiveOrder", dInput.getDocument());
			YFCDocument dResponse = YFCDocument.getDocumentFor(response);
			System.out.println("receiveOrder Output: " + dResponse);
			if(!YFCCommon.isVoid(dResponse) && !YFCCommon.isVoid(dResponse.getDocumentElement().getAttribute("ReceiptHeaderKey"))) {
				try {
					localApi.invoke(env, "closeReceipt", dResponse.getDocument());
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
			return response;
		}
		return null;
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCDocument dOutput = YFCDocument.createDocument("Response");
		
		YFCDocument dOrder = this.getOrderDetails(env, dInput.getDocumentElement().getAttribute("OrderHeaderKey"));
		System.out.println("getOrderDetails: " + dOrder);
		YFCElement eOrder = dOrder.getDocumentElement();
		
		Document response = createReceipt(env, dOrder);
		if(!YFCCommon.isVoid(response)) {
			dOutput.getDocumentElement().importNode(YFCDocument.getDocumentFor(response).getDocumentElement());
			YFCDocument derivedOrder = YFCDocument.createDocument("Order");
			YFCElement eDerived = derivedOrder.getDocumentElement();
			eDerived.setAttribute("DocumentType", "0006");
			eDerived.setAttribute("EnterpriseCode", eOrder.getAttribute("EnterpriseCode"));
			eDerived.setAttribute("OrderType", "Repair_Order");
			eDerived.setAttribute("BillToKey", "2014020414191319231");
			eDerived.setAttribute("ShipToKey", "2014020414191319231");
			
			boolean hasLines = false;
			
			for(YFCElement eOrderLine :eOrder.getChildElement("OrderLines", true).getChildren()) {
				if(YFCCommon.equals(eOrderLine.getAttribute("DispositionCode"), "QC-FAILED") && (YFCCommon.equals(eOrderLine.getAttribute("Status"), "Created") || YFCCommon.equals(eOrderLine.getAttribute("Status"), "Required Reconstruction"))) {
					YFCElement eDLine = eDerived.getChildElement("OrderLines", true).createChild("OrderLine");
					hasLines = true;
					eDLine.setAttribute("OrderedQty", eOrderLine.getAttribute("OrderedQty"));
					eDLine.createChild("DerivedFrom").setAttribute("OrderLineKey", eOrderLine.getAttribute("OrderLineKey"));
					if(!YFCCommon.isVoid(eDLine.getAttribute("ShipNode"))){
						eDLine.setAttribute("ShipNode", eDLine.getAttribute("ShipNode"));
					}
					eDLine.setAttribute("ReceivingNode", "Aurora_WH1");
				}
			}
			
			if(hasLines) {
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				System.out.println("createOrder input: " + derivedOrder);
				response = localApi.invoke(env, "createOrder", derivedOrder.getDocument());
				dOutput.getDocumentElement().importNode(YFCDocument.getDocumentFor(response).getDocumentElement());
			}
		}
		return dOutput.getDocument();		
	}
	
	private static Document getOrderDetailsTemplate() {
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("DocumentType", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("DispositionCode", "");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("ShipNode", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("ProductClass", "");
		
		return dOrder.getDocument();
	}
	
	private YFCDocument getOrderDetails(YFSEnvironment env, String sOrderHeaderKey) throws YIFClientCreationException, YFSException, RemoteException {
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("getOrderDetails",  getOrderDetailsTemplate());
		
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrderHeaderKey", sOrderHeaderKey);
		Document response = localApi.invoke(env, "getOrderDetails", dInput.getDocument());
		return YFCDocument.getDocumentFor(response);
	}

}
