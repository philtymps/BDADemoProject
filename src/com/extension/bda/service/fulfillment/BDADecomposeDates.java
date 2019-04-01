package com.extension.bda.service.fulfillment;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDADecomposeDates extends BDAServiceApi implements IBDAService {

	public static void main(String[] args) throws Exception {
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "2019040110251328559115");
		
		BDADecomposeDates dd = new BDADecomposeDates();
		Document dOutput = dd.invoke(null, dOrder.getDocument());
		if(!YFCCommon.isVoid(dOutput)) {
			System.out.println(YFCDocument.getDocumentFor(dOutput));
		}
	}
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "decomposeAvailability";
	}

	private void setOrderLineAvailability(YFCElement eOrderLine, YFCElement orderLineOut, boolean isChained) {
		YFCElement eOrderLineAvailabilities = orderLineOut.createChild("OrderLineAvailabilities");
		YFCElement eInventoryParameters = eOrderLine.getChildElement("ItemDetails", true).getChildElement("InventoryParameters", true);
		// orderLineOut.importNode(eOrderLine.getChildElement("Schedules", true));
		for(YFCElement eOrderStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()) {
			YFCElement eAvailability = eOrderLineAvailabilities.createChild("OrderLineAvailability");
			eAvailability.setAttributes(eOrderStatus.getAttributes());
			eAvailability.setAttributes(eOrderStatus.getChildElement("Details", true).getAttributes());
			eAvailability.setAttributes(eOrderStatus.getChildElement("ShipNode", true).getAttributes());
			eAvailability.setAttributes(eInventoryParameters.getAttributes());
				
			for(YFCElement eSchedule : eOrderLine.getChildElement("Schedules", true).getChildren()) {
				if(isChained && YFCCommon.equals(eSchedule.getAttribute("ProcureFromNode"), eOrderLine.getAttribute("ShipNode"))) {
					eAvailability.importNode(eSchedule);
				}
				if(!isChained && YFCCommon.isVoid(eSchedule.getAttribute("ProcureFromNode"))) {
					eAvailability.importNode(eSchedule);
				}
			}
			

		}
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dOutput = YFCDocument.createDocument("OrderLines");
		Document dOrder = this.getOrderDetails(env, input);
		if(!YFCCommon.isVoid(dOrder)) {
			YFCDocument orderDoc = YFCDocument.getDocumentFor(dOrder);
			//System.out.println(orderDoc);
			Document dChainedLines = this.getChainedLines(env, orderDoc);
			for(YFCElement eOrderLine : orderDoc.getDocumentElement().getChildElement("OrderLines", true).getChildren()) {
				YFCElement orderLineOut = dOutput.getDocumentElement().createChild("OrderLine");
				orderLineOut.setAttributes(eOrderLine.getAttributes());
				setOrderLineAvailability(eOrderLine, orderLineOut, true);
				if(orderLineOut.getBooleanAttribute("HasChainedLines", false) && !YFCCommon.isVoid(dChainedLines)) {
					YFCDocument chainedLinesDoc = YFCDocument.getDocumentFor(dChainedLines);
					for(YFCElement eChainedLine : chainedLinesDoc.getDocumentElement().getChildren()) {
						for(YFCElement eAvailability : orderLineOut.getChildElement("OrderLineAvailabilities", true).getChildren()) {
							if(YFCCommon.equals(eChainedLine.getAttribute("OrderLineKey"), eAvailability.getAttribute("ChainedToOrderLineKey"))) {
								YFCElement eTransferLine = eAvailability.getChildElement("TransferLines", true).createChild("TransferLine");
								eTransferLine.setAttributes(eChainedLine.getAttributes());
								eTransferLine.importNode(eChainedLine.getChildElement("OrderLineAvailabilities", true));
							}
						}						
					}
				}
			}			
		}
		return dOutput.getDocument();
	}

	
	private Document getOrderDetailsTemplate() {
		YFCDocument dT = YFCDocument.createDocument("Order");
		YFCElement eOrder = dT.getDocumentElement();
		eOrder.setAttribute("ReqDeliveryDate", "");
		eOrder.setAttribute("ReqShipDate", "");
		eOrder.setAttribute("CarrierServiceCode", "");
		eOrder.setAttribute("ShipNode", "");
		eOrder.setAttribute("ReceivingNode", "");
		eOrder.setAttribute("OrderHeaderKey", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("SubLineNo", "");
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("ReqDeliveryDate", "");
		eOrderLine.setAttribute("ReqShipDate", "");
		eOrderLine.setAttribute("CarrierServiceCode", "");
		eOrderLine.setAttribute("ShipNode", "");
		eOrderLine.setAttribute("ReceivingNode", "");
		eOrderLine.setAttribute("ChainedFromOrderHeaderKey", "");
		eOrderLine.setAttribute("ChainedFromOrderLineKey", "");
		eOrderLine.setAttribute("DerivedFromOrderReleaseKey", "");
		eOrderLine.setAttribute("DerivedFromOrderLineKey", "");
		eOrderLine.setAttribute("DerivedFromOrderHeaderKey", "");
		eOrderLine.setAttribute("FutureAvailabilityDate", "");
		eOrderLine.setAttribute("HasChainedLines", "");
		eOrderLine.setAttribute("HasDerivedChild", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("Segment", "");
		eOrderLine.setAttribute("SegmentType", "");
		eOrderLine.setAttribute("FutureAvailabilityDate", "");
		YFCElement eItem = eOrderLine.createChild("ItemDetails");
		eItem.setAttribute("ItemID", "");

		YFCElement eInventoryParams = eItem.createChild("InventoryParameters");
		eInventoryParams.setAttribute("ATPRule", "");
		eInventoryParams.setAttribute("AdvanceNotificationTime", "");
		eInventoryParams.setAttribute("LeadTime", "");
		eInventoryParams.setAttribute("MaximumNotificationTime", "");
		eInventoryParams.setAttribute("MinNotificationTime", "");
		eInventoryParams.setAttribute("ProcessingTime", "");
		
		YFCElement eSchedule = eOrderLine.createChild("Schedules").createChild("Schedule");
		eSchedule.setAttribute("Quantity", "");
		eSchedule.setAttribute("ShipNode", "");
		eSchedule.setAttribute("ReceivingNode", "");
		eSchedule.setAttribute("ProductAvailabilityDate", "");
		eSchedule.setAttribute("ProcureFromNode", "");
		eSchedule.setAttribute("OrderLineScheduleKey", "");
		
		YFCElement eOrderStatus = eOrderLine.createChild("OrderStatuses").createChild("OrderStatus");
		eOrderStatus.setAttribute("Status", "");
		eOrderStatus.setAttribute("ShipNode", "");
		eOrderStatus.setAttribute("ReceivingNode", "");
		eOrderStatus.setAttribute("StatusQty", "");
		eOrderStatus.setAttribute("ChainedToOrderLineKey", "");
		eOrderStatus.setAttribute("ChainedToOrderHeaderKey", "");
		eOrderStatus.setAttribute("OrderReleaseKey", "");
		eOrderStatus.setAttribute("OrderReleaseStatusKey", "");
		YFCElement eDetails = eOrderStatus.createChild("Details");
		eDetails.setAttribute("ExpectedDeliveryDate", "");
		eDetails.setAttribute("ExpectedShipmentDate", "");
		eDetails.setAttribute("ShipNode", "");
		eDetails.setAttribute("ShipByDate", "");
		eDetails.setAttribute("ReceivingNode", "");
		YFCElement eShipNode = eOrderStatus.createChild("ShipNode");
		eShipNode.setAttribute("ReceiptProcessingTimeForForwarding", "");
		eShipNode.setAttribute("ReceiptProcessingTimeForFutureInventory", "");
		eShipNode.setAttribute("ReceiptProcessingTime", "");
		return dT.getDocument();
	}
	
	private Document getOrderLineListTemplate() {
		YFCDocument dT = YFCDocument.createDocument("OrderLines");
		YFCElement eOrderLine = dT.getDocumentElement().createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("SubLineNo", "");
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("ReqDeliveryDate", "");
		eOrderLine.setAttribute("ReqShipDate", "");
		eOrderLine.setAttribute("CarrierServiceCode", "");
		eOrderLine.setAttribute("ShipNode", "");
		eOrderLine.setAttribute("ReceivingNode", "");
		eOrderLine.setAttribute("ChainedFromOrderHeaderKey", "");
		eOrderLine.setAttribute("ChainedFromOrderLineKey", "");
		eOrderLine.setAttribute("DerivedFromOrderReleaseKey", "");
		eOrderLine.setAttribute("DerivedFromOrderLineKey", "");
		eOrderLine.setAttribute("DerivedFromOrderHeaderKey", "");
		eOrderLine.setAttribute("FutureAvailabilityDate", "");
		eOrderLine.setAttribute("HasChainedLines", "");
		eOrderLine.setAttribute("HasDerivedChild", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("Segment", "");
		eOrderLine.setAttribute("SegmentType", "");
		eOrderLine.setAttribute("FutureAvailabilityDate", "");
		YFCElement eItem = eOrderLine.createChild("ItemDetails");
		eItem.setAttribute("ItemID", "");
		YFCElement eInventoryParams = eItem.createChild("InventoryParameters");
		eInventoryParams.setAttribute("ATPRule", "");
		eInventoryParams.setAttribute("AdvanceNotificationTime", "");
		eInventoryParams.setAttribute("LeadTime", "");
		eInventoryParams.setAttribute("MaximumNotificationTime", "");
		eInventoryParams.setAttribute("MinNotificationTime", "");
		eInventoryParams.setAttribute("ProcessingTime", "");
		YFCElement eOrderStatus = eOrderLine.createChild("OrderStatuses").createChild("OrderStatus");
		eOrderStatus.setAttribute("Status", "");
		eOrderStatus.setAttribute("ShipNode", "");
		eOrderStatus.setAttribute("ReceivingNode", "");
		eOrderStatus.setAttribute("StatusQty", "");
		eOrderStatus.setAttribute("OrderReleaseKey", "");
		eOrderStatus.setAttribute("OrderReleaseStatusKey", "");
		YFCElement eDetails = eOrderStatus.createChild("Details");
		eDetails.setAttribute("ExpectedDeliveryDate", "");
		eDetails.setAttribute("ExpectedShipmentDate", "");
		eDetails.setAttribute("ShipNode", "");
		eDetails.setAttribute("ShipByDate", "");
		eDetails.setAttribute("ReceivingNode", "");
		YFCElement eShipNode = eOrderStatus.createChild("ShipNode");
		eShipNode.setAttribute("ReceiptProcessingTimeForForwarding", "");
		eShipNode.setAttribute("ReceiptProcessingTimeForFutureInventory", "");
		eShipNode.setAttribute("ReceiptProcessingTime", "");
		YFCElement eSchedule = eOrderLine.createChild("Schedules").createChild("Schedule");
		eSchedule.setAttribute("Quantity", "");
		eSchedule.setAttribute("ShipNode", "");
		eSchedule.setAttribute("ReceivingNode", "");
		eSchedule.setAttribute("ProductAvailabilityDate", "");
		eSchedule.setAttribute("ProcureFromNode", "");
		eSchedule.setAttribute("OrderLineScheduleKey", "");
		return dT.getDocument();
	}
	
	private Document getChainedLines(YFSEnvironment env, YFCDocument dOrder) { 
		YFCElement eOrder = dOrder.getDocumentElement();
		boolean hasChainedLines = false;
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
			if(eOrderLine.getBooleanAttribute("HasChainedLines", false)) {
				hasChainedLines = true;
				break;
			}
		}
		
		if(hasChainedLines) {
			YFCDocument dOrderLine = YFCDocument.createDocument("OrderLine");
			YFCElement eOrderLine = dOrderLine.getDocumentElement();
			eOrderLine.setAttribute("ChainedFromOrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
			Document chainedLinesDoc = this.getOrderLineList(env, dOrderLine.getDocument());
			
			if(!YFCCommon.isVoid(chainedLinesDoc)) {
				YFCElement eChainedLines = YFCDocument.getDocumentFor(chainedLinesDoc).getDocumentElement();
				Document dTransferSchedule = this.getNodeTransferSchedule(env, eChainedLines.getOwnerDocument());
				for(YFCElement eChainedLine : eChainedLines.getChildren()) {
					setOrderLineAvailability(eChainedLine, eChainedLine, false);
					
					if(!YFCCommon.isVoid(dTransferSchedule)) {
						YFCDocument transferSchedulesDoc = YFCDocument.getDocumentFor(dTransferSchedule);
						for(YFCElement eAPI : transferSchedulesDoc.getDocumentElement().getChildren()) {
							YFCElement eNodeTransferSchedules = eAPI.getChildElement("Output", true).getChildElement("NodeTransferSchedules", true);
							for(YFCElement eNodeTransferSchedule : eNodeTransferSchedules.getChildren()) {
							
								if (YFCCommon.equals(eNodeTransferSchedule.getAttribute("ToNode"), eChainedLine.getAttribute("ReceivingNode")) &&
										YFCCommon.equals(eNodeTransferSchedule.getAttribute("FromNode"), eChainedLine.getAttribute("ShipNode"))
								) {
									setTransitDays(eNodeTransferSchedule, eChainedLine.getYDateAttribute("ReqShipDate"), eChainedLine);
								}
							}
						}
						
					}
					
				}
				System.out.println(eChainedLines);
			}
			return chainedLinesDoc;
			
		}
		return null;
	}
	
	private String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	private void setTransitDays(YFCElement eTransitSchedule, YDate transferDate, YFCElement eChainedLine) {
		int dayOfWeek = transferDate.getDayOfWeek();
		int offset = 0;
		while (!eTransitSchedule.getBooleanAttribute(daysOfWeek[((dayOfWeek - 1) + offset) % 7] + "Ship", true) && offset < 7) {
			offset++;
		}
		
		if(offset < 8) {
			if(eTransitSchedule.getDoubleAttribute(daysOfWeek[((dayOfWeek - 1) + offset) % 7] + "TransitTime") > 0) {
				eChainedLine.setAttribute("ActualTransitDays", eTransitSchedule.getDoubleAttribute(daysOfWeek[((dayOfWeek - 1) + offset) % 7] + "TransitTime") + offset);
			} else {
				eChainedLine.setAttribute("ActualTransitDays", eTransitSchedule.getDoubleAttribute("DefaultTransitTime") + offset);
			}
		} else {
			eChainedLine.setAttribute("ActualTransitDays", eTransitSchedule.getDoubleAttribute("DefaultTransitTime"));
		}
	
	}
	
	private Document getNodeTransferSchedule(YFSEnvironment env, YFCDocument dOrderLineList) {
		if(!YFCCommon.isVoid(dOrderLineList)) {
			boolean hasAPIs = false;
			YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
			YFCElement eMultiApi = dMultiApi.getDocumentElement();
			for(YFCElement eOrderLine : dOrderLineList.getDocumentElement().getChildren()) {
				if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReceivingNode")) && !YFCCommon.isVoid(eOrderLine.getAttribute("ShipNode"))) {
					hasAPIs = true;
					YFCElement eApi = eMultiApi.createChild("API");
					eApi.setAttribute("Name", "getNodeTransferScheduleList");
					YFCElement eNode = eApi.createChild("Input").createChild("NodeTransferSchedule");
					eNode.setAttribute("FromNode", eOrderLine.getAttribute("ShipNode"));
					eNode.setAttribute("ToNode", eOrderLine.getAttribute("ReceivingNode"));
					YFCElement eOutput = eApi.createChild("Template").createChild("NodeTransferSchedules").createChild("NodeTransferSchedule");
					eOutput.setAttribute("DefaultTransitTime", "");
					eOutput.setAttribute("TransitUnitOfMeasure", "");
					eOutput.setAttribute("EffectiveFromDate", "");
					eOutput.setAttribute("EffectiveToDate", "");
					
					eOutput.setAttribute("FromNode", "");
					eOutput.setAttribute("ToNode", "");
					eOutput.setAttribute("HasDateOverrides", "");
					eOutput.setAttribute("MondayShip", "");
					eOutput.setAttribute("MondayTransitTime", "");
					eOutput.setAttribute("TuesdayShip", "");
					eOutput.setAttribute("TusedayTransitTime", "");
					eOutput.setAttribute("WednesdayShip", "");
					eOutput.setAttribute("WednesdayTransitTime", "");
					eOutput.setAttribute("ThursdayShip", "");
					eOutput.setAttribute("ThursdayTransitTime", "");
					eOutput.setAttribute("FridayShip", "");
					eOutput.setAttribute("FridayTransitTime", "");
					eOutput.setAttribute("SaturdayShip", "");
					eOutput.setAttribute("SaturdayTransitTime", "");
					eOutput.setAttribute("SundayShip", "");
					eOutput.setAttribute("SundayTransitTime", "");
					
				}
			}
			
			if(hasAPIs) {
				return this.callApi(env, dMultiApi.getDocument(), null, "multiApi");
			}
		}
		
		return null;
	}
	
	private Document getOrderDetails(YFSEnvironment env, Document input) {
		return this.callApi(env, input, getOrderDetailsTemplate(), "getCompleteOrderDetails");
	}
	private Document getOrderLineList(YFSEnvironment env, Document input) {
		return this.callApi(env, input, getOrderLineListTemplate(), "getCompleteOrderLineList");
	}
}
