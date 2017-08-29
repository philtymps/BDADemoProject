package com.extension.starbucks;

import java.rmi.RemoteException;
import java.sql.SQLException;

import org.w3c.dom.Document;

import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAAddItemToShipment {

	public Document addItemToShipment(YFSEnvironment env, Document input) {
		YIFApi localApi;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
		
			
			if(!YFCCommon.isVoid(input)){
				YFCElement eInput = YFCDocument.getDocumentFor(input).getDocumentElement();
				String sShipmentKey = eInput.getAttribute("ShipmentKey");
				for(YFCElement eOrderLine : eInput.getChildElement("OrderLines", true).getChildren()){
					if(YFCCommon.isVoid(eOrderLine.getAttribute("OrderedQty"))){
						eOrderLine.setAttribute("OrderedQty", 1);
					}
					if(eOrderLine.hasAttribute("OriginalOrderLineKey")){
						YFCElement eShipmentLines = getShipmentLines(env, localApi, eOrderLine.getAttribute("OriginalOrderLineKey"));
						if(!YFCCommon.isVoid(eShipmentLines)){
							for(YFCElement eShipmentLine : eShipmentLines.getChildren()){
								String sShipmentLineKey = eShipmentLine.getAttribute("ShipmentLineKey");
								
								sShipmentKey = eShipmentLine.getAttribute("ShipmentKey");
								eOrderLine.setAttribute("ShipNode", eShipmentLine.getChildElement("Shipment").getAttribute("ShipNode"));
								eOrderLine.setAttribute("DeliveryMethod", eShipmentLine.getChildElement("Shipment").getAttribute("DeliveryMethod"));
								cancelShipmentLine(env, localApi, sShipmentKey, sShipmentLineKey);
							}
						}
						eOrderLine.removeAttribute("OriginalOrderLineKey");
					}
				}
				Document dOrderOutput = changeOrder(env, localApi, input);
				
				YFCDocument dOrderInput = YFCDocument.createDocument("Order");
				YFCElement eOrder = dOrderInput.getDocumentElement();
				eOrder.setAttribute("OrderHeaderKey", eInput.getAttribute("OrderHeaderKey"));
				eOrder.setAttribute("PaymentStatus", "AUTHORIZED");
				dOrderOutput = changeOrder(env, localApi, dOrderInput.getDocument());
				updateOrder(env, localApi, dOrderOutput, dOrderInput.getDocument());
				System.out.println("updateOrder: " + dOrderOutput);
				addToShipment(env, localApi, dOrderInput.getDocument(), sShipmentKey);
			}
		} catch (YIFClientCreationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Document changeOrder(YFSEnvironment env, YIFApi localApi, Document input){
		try {
			env.setApiTemplate("changeOrder", CompleteOrder.getOrderDetailsTemplate());
			return localApi.changeOrder(env, input);
		} catch (YFSException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void updateOrder(YFSEnvironment env, YIFApi localApi, Document l_OutputXml, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOutput = dOutput.getDocumentElement();
		eOutput.setAttribute("OrderHeaderKey", l_OutputXml.getDocumentElement().getAttribute("OrderHeaderKey"));
		if (!YFCCommon.isVoid(l_OutputXml)){
			try {
	            CompleteOrder.removeHolds(env, l_OutputXml, eOutput, localApi);
	            if (CompleteOrder.processOrderPayments(env, l_OutputXml, true, eOutput, localApi)){
	            	YFCElement eGetOrderDetailsOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
	            	if(CompleteOrder.hasStatus(eGetOrderDetailsOutput, "1100.90")){
	            		CompleteOrder.createOrderInvoice(env, l_OutputXml, localApi);
	            	}
	            	if (CompleteOrder.statusLessThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
		            	CompleteOrder.scheduleOrder(env, l_OutputXml, localApi);
		            	eOutput.setAttribute("ScheduleInvoked", "Y");
		            	env.setApiTemplate("getOrderDetails", CompleteOrder.getOrderDetailsTemplate());
						l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
	            	}
	            	eGetOrderDetailsOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
	            	if (CompleteOrder.hasStatusBetween(eGetOrderDetailsOutput,"1500","3200")){
			            CompleteOrder.releaseOrder(env, l_OutputXml, localApi, "N");
			            eOutput.setAttribute("ReleaseInvoked", "Y");
			            env.setApiTemplate("getOrderDetails", CompleteOrder.getOrderDetailsTemplate());
						l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
	            	}
	            }
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        }
		}
	}
	
	public static void addToShipment(YFSEnvironment env, YIFApi m_YifApi, Document dOrder, String sShipmentKey) {
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		Document getOrderDetailsOutput = null;
		try {
			env.setApiTemplate("getCompleteOrderDetails", CompleteOrder.getOrderDetailsForShipmentTemplate());
			getOrderDetailsOutput = m_YifApi.invoke(env, "getCompleteOrderDetails", getOrderDetailsInput.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call getOrderDetails failed using the following input xml: " + eOrder);
        	System.out.println("The error thrown was: " );    
            yex.printStackTrace();
        } 
		
		if (!YFCCommon.isVoid(getOrderDetailsOutput)){
			boolean linesExist = false;
			YFCElement eOrderOut = YFCDocument.getDocumentFor(getOrderDetailsOutput).getDocumentElement();
			int i = 0;
			YFCDocument dShipment = YFCDocument.createDocument("Shipment");
			YFCElement eShipment = dShipment.getDocumentElement();
			eShipment.setAttribute("ShipmentKey", sShipmentKey);
			int lines = 10;
			for (YFCElement eOrderLine : eOrderOut.getChildElement("OrderLines", true).getChildren()) {
				for (YFCElement eOrderLineStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
					if (!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey")) && eOrderLineStatus.getAttribute("Status").compareTo("3350") < 0 && eOrderLineStatus.getAttribute("Status").compareTo("3200") >= 0) {
						if (!eOrderLine.getBooleanAttribute("IsBundleParent", false)){
							YFCElement eShipmentLines = eOrderLine.getChildElement("ShipmentLines");
							if(YFCCommon.isVoid(eShipmentLines) || YFCCommon.isVoid(eShipmentLines.getChildElement("ShipmentLine"))){
								
								YFCElement eShipmentLine = eShipment.getChildElement("ShipmentLines", true).createChild("ShipmentLine"); 
								eShipmentLine.setAttribute("DocumentType", eOrderOut.getAttribute("DocumentType"));
								eShipmentLine.setAttribute("ItemID", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
								eShipmentLine.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item", true).getAttribute("UnitOfMeasure"));
								eShipmentLine.setAttribute("ProductClass", eOrderLine.getChildElement("Item", true).getAttribute("ProductClass"));
								eShipmentLine.setAttribute("PrimeLineNo", eOrderLine.getAttribute("PrimeLineNo"));
								eShipmentLine.setAttribute("SubLineNo", eOrderLine.getAttribute("SubLineNo"));
								eShipmentLine.setAttribute("Quantity", eOrderLineStatus.getAttribute("StatusQty"));
								eShipmentLine.setAttribute("OrderReleaseKey", eOrderLineStatus.getAttribute("OrderReleaseKey"));
								eShipmentLine.setAttribute("ShipmentSubLineNo", "15");
								eShipmentLine.setAttribute("ShipmentLineNo", (lines++ + eOrderLine.getIntAttribute("PrimeLineNo")));
								linesExist = true;
							}
						}
					} else if(!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey")) && eOrderLineStatus.getAttribute("Status").compareTo("3350") >= 0){
						lines++;
					}
				} 
			}
			
			if (linesExist){
			
				try {
					((YFSContext)env).commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			//	env.setApiTemplate("changeShipment", CompleteOrder.getCreateShipmentTemplate());
				try {
					getOrderDetailsOutput = m_YifApi.invoke(env, "changeShipment", dShipment.getDocument());
					
					
				} catch(Exception yex) {
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
		        }
				
				try{
					YFCDocument dShip = YFCDocument.createDocument("Shipment");
					YFCElement eShip = dShip.getDocumentElement();
					eShip.setAttribute("TransactionId", "Shipment_Fixed.0001.ex");
					eShip.setAttribute("ShipmentKey", dShipment.getDocumentElement().getAttribute("ShipmentKey"));
					m_YifApi.invoke(env,  "changeShipmentStatus", dShip.getDocument());
				} catch(Exception yex) {
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
		        }
			}
		}		
	}
	
	private void cancelShipmentLine(YFSEnvironment env, YIFApi localApi, String sShipmentKey, String sShipmentLineKey){
		YFCDocument dShipment = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dShipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", sShipmentKey);
		eShipment.setAttribute("CancelRemovedQuantity", "Y");
		YFCElement eShipmentLine = eShipment.createChild("ShipmentLines").createChild("ShipmentLine");
		eShipmentLine.setAttribute("ShipmentLineKey", sShipmentLineKey);
		eShipmentLine.setAttribute("Action", "Cancel");
		try {
			localApi.changeShipment(env, dShipment.getDocument());
		} catch (YFSException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	private YFCElement getShipmentLines(YFSEnvironment env, YIFApi localApi, String sOrderLineKey){
		YFCDocument dOrder = YFCDocument.createDocument("ShipmentLine");
		YFCElement eInput = dOrder.getDocumentElement();
		eInput.setAttribute("OrderLineKey", sOrderLineKey);
		
		try {
			env.setApiTemplate("getShipmentLineList", getShipmentLineListTemplate());
			Document dOutput = localApi.getShipmentLineList(env, dOrder.getDocument());
			
			if(!YFCCommon.isVoid(dOutput)){
				return YFCDocument.getDocumentFor(dOutput).getDocumentElement();
			}
		} catch (YFSException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Document getShipmentLineListTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("ShipmentLines");
		YFCElement eShipmentLine = dOutput.getDocumentElement().createChild("ShipmentLine");
		eShipmentLine.setAttribute("ShipmentKey", "");
		eShipmentLine.setAttribute("ShipmentLineKey", "");
		eShipmentLine.setAttribute("Quantity", "");
		eShipmentLine.setAttribute("ShortageQty", "");
		YFCElement eShipment = eShipmentLine.createChild("Shipment");
		eShipment.setAttribute("DeliveryMethod", "");
		eShipment.setAttribute("ShipNode", "");
		return dOutput.getDocument();
	}
}
