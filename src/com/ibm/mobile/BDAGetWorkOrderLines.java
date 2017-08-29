package com.ibm.mobile;

import java.rmi.RemoteException;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAGetWorkOrderLines {

	private Document getWorkOrderDetailsTemplate(){
		YFCDocument dWorkOrder = YFCDocument.createDocument("WorkOrder");
		YFCElement eWorkOrder = dWorkOrder.getDocumentElement();
		eWorkOrder.setAttribute("WorkOrderKey", "");
		eWorkOrder.setAttribute("Status", "");
		eWorkOrder.setAttribute("WorkOrderNo", "");
		eWorkOrder.setAttribute("StatusDescription", "");
		eWorkOrder.setAttribute("ServiceItemGroupCode", "");
		eWorkOrder.setAttribute("Purpose", "");
		eWorkOrder.setAttribute("PromisedApptEndDate", "");
		eWorkOrder.setAttribute("PromisedApptStartDate", "");
		eWorkOrder.setAttribute("OrderHeaderKey", "");
		eWorkOrder.setAttribute("OrderLineKey", "");
		eWorkOrder.setAttribute("EnterpriseCode", "");
		eWorkOrder.setAttribute("ApptStatusDescription", "");
		YFCElement eWorkOrderServiceLine = eWorkOrder.createChild("WorkOrderServiceLines").createChild("WorkOrderServiceLine");
		eWorkOrderServiceLine.setAttribute("ItemGroupCode", "");
		eWorkOrderServiceLine.setAttribute("IsComplete", "");
		eWorkOrderServiceLine.setAttribute("CauseAppointmentChange", "");
		eWorkOrderServiceLine.setAttribute("QuantityRequired", "");
		eWorkOrderServiceLine.setAttribute("UOM", "");
		YFCElement eOrderLine = eWorkOrderServiceLine.createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		YFCElement eWorkOrderProdDelivery = eWorkOrder.createChild("WorkOrderProdDeliveries").createChild("WorkOrderProdDelivery");
		eWorkOrderProdDelivery.setAttribute("ItemGroupCode", "");
		eWorkOrderProdDelivery.setAttribute("IsComplete", "");
		eWorkOrderProdDelivery.setAttribute("CauseAppointmentChange", "");
		eWorkOrderProdDelivery.setAttribute("QuantityRequired", "");
		eWorkOrderProdDelivery.setAttribute("UOM", "");
		eOrderLine = eWorkOrderProdDelivery.createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		return dWorkOrder.getDocument();
	}
	
	
	public Document getWorkOrderProdLines(YFSEnvironment env, Document input){
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		if(!YFCCommon.isVoid(dInput) && !YFCCommon.isVoid(dInput.getDocumentElement().getAttribute("WorkOrderKey"))){
			try{
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				env.setApiTemplate("getWorkOrderDetails", getWorkOrderDetailsTemplate());
				Document dWorkOrderOutput = localApi.getWorkOrderDetails(env, input);
				if(!YFCCommon.isVoid(dWorkOrderOutput)){
					YFCElement eWorkOrder = YFCDocument.getDocumentFor(dWorkOrderOutput).getDocumentElement();
					if(!YFCCommon.isVoid(eWorkOrder.getChildElement("WorkOrderServiceLines"))){
						YFCElement eProductServiceLines = getWorkOrderServiceLines(env, localApi, eWorkOrder.getChildElement("WorkOrderServiceLines"));
						if(!YFCCommon.isVoid(eProductServiceLines)){
							YFCElement eOrderLines = getWorkOrderProdLines(env, localApi, eProductServiceLines);
							YFCElement eProductLines = eWorkOrder.createChild("ProductLines");
							for(YFCElement eOrderLine : eOrderLines.getChildren()){
								eProductLines.importNode(eOrderLine);
							}
						}
					}
					return dWorkOrderOutput;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		YFCDocument dWorkOrder = YFCDocument.createDocument("WorkOrder");
		return dWorkOrder.getDocument();
	}
	
	private YFCElement getWorkOrderServiceLines(YFSEnvironment env, YIFApi localApi, YFCElement eWorkOrderServiceLines) throws YFSException, RemoteException{
		Document dInput = getServiceOrderLineListInput(eWorkOrderServiceLines);
		if(!YFCCommon.isVoid(dInput)){
			env.setApiTemplate("getOrderLineList", getWorkOrderServiceLineTemplate());
			Document dOrderLineList = localApi.getOrderLineList(env, dInput);
			return YFCDocument.getDocumentFor(dOrderLineList).getDocumentElement();
		}
		return null;
	}
	
	private YFCElement getWorkOrderProdLines(YFSEnvironment env, YIFApi localApi, YFCElement eServiceLines) throws YFSException, RemoteException{
		Document dInput = getProdOrderLineListInput(eServiceLines);
		if(!YFCCommon.isVoid(dInput)){
			env.setApiTemplate("getOrderLineList", getProdLineTemplate(env));
			Document dOrderLineList = localApi.getOrderLineList(env, dInput);
			return YFCDocument.getDocumentFor(dOrderLineList).getDocumentElement();
		}
		return null;
	}
	
	private static Document getProdLineTemplate(YFSEnvironment env){
		Document temp = (Document) env.getApiTemplate("getWorkOrderProdLines");
		if(!YFCCommon.isVoid(temp)){
			return temp;
		}
		YFCDocument dOutput = YFCDocument.createDocument("OrderLineList");
		YFCElement eOrderLine = dOutput.getDocumentElement().createChild("OrderLine");
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("DeliveryMethodName", "");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("ItemGroupCode", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("DisplayUnitOfMeasure", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("ItemID", "");
		YFCElement eOrder = eOrderLine.createChild("Order");
		eOrder.setAttribute("EnterpriseCode", "");
		YFCElement eItemDetails = eOrderLine.createChild("ItemDetails");
		eItemDetails.setAttribute("ItemID", "");
		YFCElement ePrimaryInfo = eItemDetails.createChild("PrimaryInformation");
		ePrimaryInfo.setAttribute("ShortDescription", "");
		ePrimaryInfo.setAttribute("ExtendedDisplayDescription", "");
		ePrimaryInfo.setAttribute("ImageID", "");
		ePrimaryInfo.setAttribute("ImageLocation", "");
		YFCElement eLineTotal = eOrderLine.createChild("LineOverallTotals");
		eLineTotal.setAttribute("LineTotal", "");
		return dOutput.getDocument();
	}
	private static Document getServiceOrderLineListInput(YFCElement eServiceLines){
		if(!YFCCommon.isVoid(eServiceLines) && eServiceLines.hasChildNodes()){
			YFCElement eInput = YFCDocument.createDocument("OrderLine").getDocumentElement();
			YFCElement eComplexQuery = eInput.createChild("ComplexQuery");
			YFCElement eOr = eComplexQuery.createChild("Or");
			for(YFCElement eServiceLine : eServiceLines.getChildren()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "OrderLineKey");
				eExp.setAttribute("Value", eServiceLine.getChildElement("OrderLine").getAttribute("OrderLineKey"));
			}
			return eInput.getOwnerDocument().getDocument();
		}
		return null;
	}
	private static Document getProdOrderLineListInput(YFCElement eServiceLines){
		YFCElement eInput = YFCDocument.createDocument("OrderLine").getDocumentElement();
		YFCElement eComplexQuery = eInput.createChild("ComplexQuery");
		YFCElement eOr = eComplexQuery.createChild("Or");
		if(!YFCCommon.isVoid(eServiceLines) && eServiceLines.hasChildNodes()){
			for(YFCElement eOrderLine : eServiceLines.getChildren()){
				YFCElement eProductAssociations = eOrderLine.getChildElement("ProductAssociations");
				for(YFCElement eProductAssociation : eProductAssociations.getChildren()){
					YFCElement eExp = eOr.createChild("Exp");
					eExp.setAttribute("Name", "OrderLineKey");
					eExp.setAttribute("Value", eProductAssociation.getChildElement("ProductLine").getAttribute("OrderLineKey"));
				}
			}
			return eInput.getOwnerDocument().getDocument();
		}
		return null;
	}
	
	private static Document getWorkOrderServiceLineTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("OrderLineList");
		YFCElement eOutput = dOutput.getDocumentElement();
		YFCElement eOrderLine = eOutput.createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		YFCElement eProductAssociation = eOrderLine.createChild("ProductAssociations").createChild("ProductAssociation");
		YFCElement eProduct = eProductAssociation.createChild("ProductLine");
		eProduct.setAttribute("OrderLineKey", "");
		eProduct = eProductAssociation.createChild("ServiceLine");
		eProduct.setAttribute("OrderLineKey", "");
		return dOutput.getDocument();
	}

}
