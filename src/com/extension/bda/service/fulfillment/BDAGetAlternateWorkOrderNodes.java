package com.extension.bda.service.fulfillment;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetAlternateWorkOrderNodes implements IBDAService {

	public static void main(String[] args) {
		BDAGetAlternateWorkOrderNodes t = new BDAGetAlternateWorkOrderNodes();
		YFCDocument dInput = YFCDocument.createDocument("AlternateNodes");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrganizationCode", "Aurora");
		eInput.setAttribute("OrderHeaderKey", "2020011017225329738149");
		YFCElement eShipping = eInput.createChild("ShipToAddress");
		eShipping.setAttribute("Country", "US");
		eShipping.setAttribute("ZipCode", "01821");
		try {
			Document output = t.invoke(null, dInput.getDocument());
			System.out.println(YFCDocument.getDocumentFor(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getAlternateWorkOrderNodes";
	}

	@Override
	public void setProperties(Properties props) throws Exception {
		// TODO Auto-generated method stub

	}

	private static Document getNodeTemplate() {
		YFCDocument dOutput = YFCDocument.createDocument("GetSurroundingNodeList");
		YFCElement eOutput = dOutput.getDocumentElement();
		YFCElement eNode = eOutput.createChild("NodeList").createChild("Node");
		eNode.setAttribute("ShipNode", "");
		eNode.setAttribute("Description", "");
		eNode.setAttribute("DistanceFromShipToAddress", "");
		
		return dOutput.getDocument();
	}
	
	private static Document getOrderDetails() {
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOutput = dOutput.getDocumentElement();
		eOutput.setAttribute("OrderHeaderKey", "");
		eOutput.setAttribute("EnterpriseCode", "");
		YFCElement eOrderLine = eOutput.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("ItemGroupCode", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		YFCElement eWorkOrder = eOrderLine.createChild("WorkOrders").createChild("WorkOrder");
		eWorkOrder.setAttribute("WorkOrderKey", "");
		eWorkOrder.setAttribute("NodeKey", "");
		eWorkOrder.setAttribute("WorkOrderKey", "");
		eWorkOrder.setAttribute("QuantityRequested", "");
		eWorkOrder.setAttribute("ServiceItemID", "");
		eWorkOrder.setAttribute("ServiceUom", "");
		return dOutput.getDocument();
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		
		YFCDocument dSurrounding = YFCDocument.createDocument("GetSurroundingNodeList");
		YFCElement eSur = dSurrounding.getDocumentElement();
		
		if(!YFCCommon.isVoid(eInput.getAttribute("OrganizationCode")) && !YFCCommon.isVoid(eInput.getChildElement("ShipToAddress"))) {
			eSur.setAttribute("OrganizationCode", eInput.getAttribute("OrganizationCode"));
			eSur.setAttribute("DistanceToConsider", YFCCommon.isVoid(eInput.getAttribute("DistanceToConsider")) ? "25" : eInput.getAttribute("DistanceToConsider"));
			eSur.setAttribute("DistanceToConsiderUOM", YFCCommon.isVoid(eInput.getAttribute("DistanceToConsider")) ? "MILE" : eInput.getAttribute("DistanceToConsiderUOM"));
			if(!YFCCommon.isVoid(eInput.getAttribute("FulfillmentType")))
				eSur.setAttribute("FulfillmentType", eInput.getAttribute("FulfillmentType"));
			if(!YFCCommon.isVoid(eInput.getAttribute("NodeType")))
				eSur.setAttribute("NodeType", eInput.getAttribute("NodeType"));
			eSur.importNode(eInput.getChildElement("ShipToAddress"));
			Document dOutput = BDAServiceApi.callApi(env, dSurrounding.getDocument(), getNodeTemplate(), "getSurroundingNodeList");
			Document dOrder = getOrderDetails(env, input);
			if(!YFCCommon.isVoid(dOrder)) {
				
				return findInventory(env, dOrder, dOutput, input);
			}
			return dOutput;
		}
		
		return input;
	}
	
	private Document getOrderDetails(YFSEnvironment env, Document input) {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		
		if(!YFCCommon.isVoid(eInput.getAttribute("OrderHeaderKey"))) {
			YFCDocument inDoc = YFCDocument.createDocument("Order");
			inDoc.getDocumentElement().setAttribute("OrderHeaderKey", eInput.getAttribute("OrderHeaderKey"));
			return BDAServiceApi.callApi(env, inDoc.getDocument(), getOrderDetails(), "getCompleteOrderDetails");
		}
		return null;
	}
	
	private Document findInventory(YFSEnvironment env, Document dOrder, Document dNodes, Document originalInput) {
		YFCElement eOrder = YFCDocument.getDocumentFor(dOrder).getDocumentElement();
		YFCDocument dInput = YFCDocument.createDocument("Promise");
		YFCElement eInput = dInput.getDocumentElement();
		boolean hasLines = false;
		eInput.setAttribute("OrganizationCode", eOrder.getAttribute("EnterpriseCode"));
		eInput.setAttribute("MaximumRecords", "100");
		YFCElement eNodeList = YFCDocument.getDocumentFor(dNodes).getDocumentElement().getChildElement("NodeList");
		if(!eNodeList.hasChildNodes()) {
			return null;
		}
		
		eInput.importNode(YFCDocument.getDocumentFor(originalInput).getDocumentElement().getChildElement("ShipToAddress"));
	
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
			if(YFCCommon.equals(eOrderLine.getAttribute("ItemGroupCode"), "PS")) {
				hasLines = true;
				YFCElement ePromiseLine = eInput.getChildElement("PromiseServiceLines", true).createChild("PromiseServiceLine");
				ePromiseLine.setAttribute("ItemID", eOrderLine.getChildElement("Item").getAttribute("ItemID"));
				ePromiseLine.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item").getAttribute("UnitOfMeasure"));
				ePromiseLine.setAttribute("LineId", eOrderLine.getAttribute("OrderLineKey"));
				ePromiseLine.setAttribute("RequiredQty", eOrderLine.getAttribute("OrderedQty"));
				YDate d = YDate.newMutableDate(YDate.newDate(), 30);
				ePromiseLine.setAttribute("ReqEndDate", d);
			}
		}
		if(!hasLines) {
			return null;
		}
		YFCDocument dOutput = YFCDocument.createDocument("AvailableNodes");
		YFCElement eOutput = dOutput.getDocumentElement();
		for(YFCElement eNode : eNodeList.getChildren()) {
			eInput.setAttribute("ShipNode", eNode.getAttribute("ShipNode"));
			Document dResponse = BDAServiceApi.callApi(env, dInput.getDocument(), null, "findInventory");
			YFCElement eResponse = YFCDocument.getDocumentFor(dResponse).getDocumentElement();
			if(!YFCCommon.isVoid(eResponse) && !eResponse.getChildElement("SuggestedOption", true).getChildElement("Option", true).getBooleanAttribute("HasAnyUnavailableQty", true)) {
				YFCElement eOption = eResponse.getChildElement("SuggestedOption", true).getChildElement("Option", true);
				YFCElement eAvailableNode = eOutput.createChild("AvailableNode");
				eAvailableNode.setAttributes(eNode.getAttributes());
				eAvailableNode.setAttribute("FirstDate", eOption.getAttribute("FirstDate"));
			}
		}
		return dOutput.getDocument();
		
		
	}

}
