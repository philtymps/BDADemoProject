package com.extension.bda.service.fulfillment;

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

public class ScheduleOnSuccess implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "scheduleOnSuccess";
	}

	@Override
	public void setProperties(Properties props) {
			
	}

	private Document findInventoryTemplate() {
		YFCDocument dF = YFCDocument.createDocument("Promise");
		YFCElement eP = dF.getDocumentElement();
		YFCElement eOption = eP.createChild("SuggestedOption").createChild("Option");
		eOption.setAttribute("HasAnyUnavailableQty", "");
		YFCElement ePromiseLine = eOption.createChild("PromiseLines").createChild("PromiseLine");
		ePromiseLine.setAttribute("ItemID", "");
		ePromiseLine.setAttribute("LineId", "");
		YFCElement eAssignment = ePromiseLine.createChild("Assignments").createChild("Assignment");
		eAssignment.setAttribute("TransferNo", "");
		eAssignment.setAttribute("ShipNode", "");
		YFCElement eMulti = eOption.createChild("MultiStopTransfers").createChild("MultiStopTransfer");
		eMulti.setAttribute("DeliveryDate", "");
		eMulti.setAttribute("DestinationNode", "");
		eMulti.setAttribute("OriginNode", "");
		eMulti.setAttribute("TransferNo", "");
		eMulti.setAttribute("ShipDate", "");
		YFCElement eStop = eMulti.createChild("Stops").createChild("Stop");
		eStop.setAttribute("ArrivalDate", "");
		eStop.setAttribute("Node", "");
		eStop.setAttribute("ShipDate", "");
		eStop.setAttribute("StopNo", "");
		return dF.getDocument();
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eOrder = dInput.getDocumentElement();
		
		YFCDocument dFindInventory = YFCDocument.createDocument("Promise");
		YFCElement eFindI = dFindInventory.getDocumentElement();
		eFindI.setAttribute("CheckInventory", "N");
		eFindI.setAttribute("CheckCapacity", "N");
		eFindI.setAttribute("OrganizationCode", eOrder.getAttribute("EnterpriseCode"));
		boolean found = false;
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
			try {
				YFCElement eOrderLines = getChainedInfo(env, eOrderLine.getAttribute("OrderLineKey"));
				if(!YFCCommon.isVoid(eOrderLines) && !YFCCommon.isVoid(eOrderLines.getChildElement("OrderLine"))) {
					for(YFCElement eChainedLine : eOrderLines.getChildren()) {
						if(eChainedLine.getIntAttribute("OrderedQty", 0) > 0 && !YFCCommon.isVoid(eChainedLine.getAttribute("ReceivingNode")) && !YFCCommon.isVoid(eChainedLine.getAttribute("ShipNode"))) {
							YFCElement ePromiseLine = eFindI.getChildElement("PromiseLines", true).createChild("PromiseLine");
							found = true;
							ePromiseLine.setAttribute("LineId", eChainedLine.getAttribute("OrderHeaderKey") + ":" + eChainedLine.getAttribute("OrderLineKey"));
							ePromiseLine.setAttribute("ReceivingNode", eChainedLine.getAttribute("ReceivingNode"));
							ePromiseLine.setAttribute("ShipNode", eChainedLine.getAttribute("ShipNode"));
							ePromiseLine.setAttribute("RequiredQty", eChainedLine.getIntAttribute("OrderedQty", 1));
							ePromiseLine.setAttribute("ItemID", eChainedLine.getChildElement("Item", true).getAttribute("ItemID"));
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Unabled to fetch chained line: " + eOrderLine.getAttribute("OrderLineKey"));
			}
		}
		
		if(found) {
			updateChainedOrders(env, dFindInventory);
		}
		
		return input;
	}
	
	private void updateChainedOrders(YFSEnvironment env, YFCDocument dFindInventory) throws YIFClientCreationException, YFSException, RemoteException {
		
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("findInventory", this.findInventoryTemplate());
		System.out.println("findInventory: " + dFindInventory);
		Document l_OutputXml = localApi.invoke(env, "findInventory", dFindInventory.getDocument());
		YFCElement eResponse = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
		
		YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
		boolean found = false;
		for(YFCElement ePromiseLine : eResponse.getChildElement("SuggestedOption", true).getChildElement("Option", true).getChildElement("PromiseLines", true).getChildren()) {
			for(YFCElement eAssignment : ePromiseLine.getChildElement("Assignments", true).getChildren()) {
				if(eAssignment.getIntAttribute("TransferNo", 0) > 0) {
					for(YFCElement eMultiStopTransfer : eResponse.getChildElement("SuggestedOption", true).getChildElement("Option", true).getChildElement("MultiStopTransfers", true).getChildren()) {
						
						if(eAssignment.getIntAttribute("TransferNo", 0) == eMultiStopTransfer.getIntAttribute("TransferNo", -1)) {
							found = true;
							YFCElement eAPI = dMultiApi.getDocumentElement().createChild("API");
							eAPI.setAttribute("Name", "changeOrder");
							YFCElement eInput = eAPI.createChild("Input");
							YFCElement eOrder = eInput.createChild("Order");
							eOrder.setAttribute("OrderHeaderKey", ePromiseLine.getAttribute("LineId").split(":")[0]);
							YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
							eOrderLine.setAttribute("OrderLineKey", ePromiseLine.getAttribute("LineId").split(":")[1]);
							for(YFCElement eStop : eMultiStopTransfer.getChildElement("Stops", true).getChildren()) {
								YFCElement eReference = eOrderLine.getChildElement("References", true).createChild("Reference");
								eReference.setAttribute("Name", "Hop-" + eStop.getAttribute("StopNo"));
								eReference.setAttribute("Value", eStop.getAttribute("Node"));
								eReference = eOrderLine.getChildElement("References", true).createChild("Reference");
								eReference.setAttribute("Name", "ShipDate-" + eStop.getAttribute("StopNo"));
								eReference.setAttribute("Value", eStop.getAttribute("ShipDate"));
								eReference = eOrderLine.getChildElement("References", true).createChild("Reference");
								eReference.setAttribute("Name", "ArrivalDate-" + eStop.getAttribute("StopNo"));
								eReference.setAttribute("Value", eStop.getAttribute("ArrivalDate"));
							}
							
						}
						
					}
				}
			}
		}
		if(found) {
			System.out.println("multiApi: " + dMultiApi);
			localApi.invoke(env, "multiApi", dMultiApi.getDocument());
		}
	}
	
	private static Document getOrderLineDetailsTemplate() {
		YFCDocument dR = YFCDocument.createDocument("OrderLineList");
		YFCElement eR = dR.getDocumentElement().createChild("OrderLine");
		eR.setAttribute("OrderLineKey", "");
		eR.setAttribute("OrderHeaderKey", "");
		eR.setAttribute("ReceivingNode", "");
		eR.setAttribute("ShipNode", "");
		eR.setAttribute("OrderedQty", "");
		YFCElement eI = eR.createChild("Item");
		eI.setAttribute("ItemID", "");
		eI.setAttribute("UnitOfMeasure", "");
		return dR.getDocument();
	}
	
	public YFCElement getChainedInfo(YFSEnvironment env, String sOrderLineKey) throws YIFClientCreationException, YFSException, RemoteException {
		YFCDocument dInput = YFCDocument.createDocument("OrderLine");
		dInput.getDocumentElement().setAttribute("ChainedFromOrderLineKey", sOrderLineKey);
		
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("getOrderLineList", getOrderLineDetailsTemplate());
		Document l_OutputXml = localApi.invoke(env, "getOrderLineList", dInput.getDocument());
		YFCElement eResponse = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
		System.out.println("getOrderLineList output: " + eResponse);
		return eResponse;
	}

}
