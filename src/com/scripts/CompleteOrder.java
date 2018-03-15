package com.scripts;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.date.YTimestamp;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class CompleteOrder implements IBDAService {

	public CompleteOrder(){
	
	}
	
	public static boolean hasStatusBetween(YFCElement eOrder, String lowStatus, String highStatus){
		for (YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			for (YFCElement eOrderStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
				if (statusGreaterThan(getBaseStatus(eOrderStatus.getAttribute("Status")), lowStatus) && statusLessThan(getBaseStatus(eOrderStatus.getAttribute("Status")), highStatus)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static boolean hasStatus(YFCElement eOrder, String sStatus){
		for (YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			for (YFCElement eOrderStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
				if (YFCCommon.equals(eOrderStatus.getAttribute("Status"), sStatus)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static void createOrderInvoice(YFSEnvironment env, Document dOrder, YIFApi m_YifApi){
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		eOrder.setAttribute("TransactionId", "TenderOrder.0001.ex");
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		try {
			m_YifApi.invoke(env, "createOrderInvoice", getOrderDetailsInput.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call scheduleOrder failed using the following input xml: " + eOrder);
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
	}
	
	public void completeTPOrder(YFSEnvironment env, Document dOrder, YFCElement eResults, YIFApi localApi, Document inputDoc){
		if(!YFCCommon.isVoid(dOrder)){
			try {
				removeHolds(env, dOrder, eResults, localApi);
				if (statusLessThan(getBaseStatus(dOrder.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
	            	scheduleOrder(env, dOrder, localApi);
	            	eResults.setAttribute("ScheduleInvoked", "Y");
	            	env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
					dOrder = localApi.invoke(env, "getOrderDetails", inputDoc);
            	}
				YFCElement eGetOrderDetailsOutput = YFCDocument.getDocumentFor(dOrder).getDocumentElement();
            	if (hasStatusBetween(eGetOrderDetailsOutput,"1500","3200")){
		            releaseOrder(env, dOrder, localApi, "Y");
		            eResults.setAttribute("ReleaseInvoked", "Y");
		            env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
					dOrder = localApi.invoke(env, "getOrderDetails", inputDoc);
            	}
            	//confirmShipment(env, dOrder, eResults, localApi, false, false);
            	env.setApiTemplate("getCompleteOrderDetails", getOrderDetailsTemplate());
    			dOrder = localApi.invoke(env, "getCompleteOrderDetails", inputDoc);
    			eGetOrderDetailsOutput = YFCDocument.getDocumentFor(dOrder).getDocumentElement();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public Document invoke(YFSEnvironment env, Document inputDoc){
		return completeOrder(env, inputDoc);
	}
	
	public Document completeOrder(YFSEnvironment env, Document inputDoc){
		YFCDocument output = YFCDocument.createDocument("Results");
		YFCElement eResults = output.getDocumentElement();
		try {	
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			try {
				env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
				l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
				eResults.setAttribute("OrderHeaderKey", l_OutputXml.getDocumentElement().getAttribute("OrderHeaderKey"));
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
			boolean invoicedOrder = false;
			if (!YFCCommon.isVoid(l_OutputXml)){
				YFCElement eOrder = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				if(YFCCommon.equals(eOrder.getAttribute("DocumentType"), "0005") || YFCCommon.equals(eOrder.getAttribute("DocumentType"), "0006")){
					completeTPOrder(env,l_OutputXml,eResults, localApi,inputDoc);
					invoicedOrder = false;
				} else {
					try {
			            removeHolds(env, l_OutputXml, eResults, localApi);
			            if (processOrderPayments(env, l_OutputXml, true, eResults, localApi)){
			            	YFCElement eGetOrderDetailsOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
			            	if(hasStatus(eGetOrderDetailsOutput, "1100.90")){
			            		createOrderInvoice(env, l_OutputXml, localApi);
			            		invoicedOrder = true;
		
			            	}
			            	if (statusLessThan(getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
				            	scheduleOrder(env, l_OutputXml, localApi);
				            	eResults.setAttribute("ScheduleInvoked", "Y");
				            	env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
								l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			            	}
			            	eGetOrderDetailsOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
			            	if (hasStatusBetween(eGetOrderDetailsOutput,"1500","3200")){
					            releaseOrder(env, l_OutputXml, localApi, "Y");
					            eResults.setAttribute("ReleaseInvoked", "Y");
					            env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
								l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			            	}
			            	confirmShipment(env, l_OutputXml, eResults, localApi, false, false);
			            	env.setApiTemplate("getCompleteOrderDetails", getOrderDetailsTemplate());
	            			l_OutputXml = localApi.invoke(env, "getCompleteOrderDetails", inputDoc);
	            			eGetOrderDetailsOutput = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
			            	if (statusGreaterThan(getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MaxOrderStatus")), "3700")){
				            	YFCElement eShipments = eGetOrderDetailsOutput.getChildElement("Shipments");
				            	if (!YFCCommon.isVoid(eShipments)){
				            		for (YFCElement eShipment : eShipments.getChildren()){
				            			if (statusGreaterThan(getBaseStatus(eShipment.getAttribute("Status")), "1400") && !eShipment.getBooleanAttribute("InvoiceComplete", false)){
				            				createShipmentInvoice(env, eShipment.getAttribute("ShipmentKey"), eOrder.getAttribute("DocumentType"), localApi);
				            				YFCElement eOutputShipments = eResults.getChildElement("Shipments", true);
				            				boolean foundShipment = false;
				            				for (YFCElement eOutputShipment : eOutputShipments.getChildren()){
				            					if (eOutputShipment.getAttribute("ShipmentKey").equals(eShipment.getAttribute("ShipmentKey"))){
				            						eOutputShipment.setAttribute("ShipmentInvoiced", "Y");
				            						foundShipment = true;
				            					}
				            				}
				            				if (!foundShipment){
				            					YFCElement eOutputShipment = eOutputShipments.createChild("Shipment");
				            					eOutputShipment.setAttribute("ShipmentKey", eShipment.getAttribute("ShipmentKey"));
				            					eOutputShipment.setAttribute("ShipmentInvoiced", "Y");
				            				}
				            			}
				            		}
				            	}
				            	invoicedOrder = true;
			            	}
			            	if(invoicedOrder){
			            		processOrderPayments(env, l_OutputXml, false, eResults, localApi);
			            	}
			            }
			        } catch(Exception yex) {
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        }
				}
				
			}
		}
		catch (Exception e ) {
			System.out.println("Could not initialize the yifclient.properties.");
			System.out.println("Bad stuff happened trying to initialize the Yif Client.");
			e.printStackTrace();
		}
		return output.getDocument();
	}
	
	public static String getBaseStatus(String sStatus){
		if (sStatus.length() > 4){
			return sStatus.substring(0,4);
		}
		return sStatus;
	}
	
	public static boolean statusLessThan(String status1, String status2){
		if (status1.compareToIgnoreCase(status2) < 0){
			return true;
		}
		return false;
	}
	
	public static boolean statusGreaterThan(String status1, String status2){
		if (status1.compareToIgnoreCase(status2) >= 0){
			return true;
		}
		return false;
	}
	
	public static Document getOrderDetailsTemplate(){
		YFCDocument output = YFCDocument.createDocument("Order");
		YFCElement eOrder = output.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey","");
		eOrder.setAttribute("OrderNo","");
		eOrder.setAttribute("EnterpriseCode","");
		eOrder.setAttribute("DocumentType","");
		eOrder.setAttribute("PaymentStatus", "");
		eOrder.setAttribute("MinOrderStatus","");
		eOrder.setAttribute("MaxOrderStatus", "");
		YFCElement eOrderHoldType = eOrder.getChildElement("OrderHoldTypes", true).getChildElement("OrderHoldType", true);
		eOrderHoldType.setAttribute("HoldType", "");
		eOrderHoldType.setAttribute("Status", "");
		YFCElement eOrderLine = eOrder.getChildElement("OrderLines", true).getChildElement("OrderLine", true);
		eOrderLine.setAttribute("OrderLineKey", "");
		YFCElement eOrderStatus = eOrderLine.getChildElement("OrderStatuses", true).getChildElement("OrderStatus", true);
		eOrderStatus.setAttribute("Status", "");
		YFCElement eShipment = eOrder.getChildElement("Shipments", true).getChildElement("Shipment", true);
		eShipment.setAttribute("ShipmentKey", "");
		eShipment.setAttribute("InvoiceComplete", "");
		eShipment.setAttribute("Status","");
		return output.getDocument();
	}
	
	public static void removeHolds (YFSEnvironment env, Document getOrderDetailsOutput, YFCElement eResults, YIFApi m_YifApi) throws Exception {
		if (!YFCCommon.isVoid(getOrderDetailsOutput)){
			boolean resolve = false;
			YFCDocument changeOrder = YFCDocument.createDocument("Order");
			YFCElement eChangeOrderInput = changeOrder.getDocumentElement();
			eChangeOrderInput.setAttribute("OrderHeaderKey", getOrderDetailsOutput.getDocumentElement().getAttribute("OrderHeaderKey"));
			YFCElement eGetOrderDetailsOutput = YFCDocument.getDocumentFor(getOrderDetailsOutput).getDocumentElement();
			for (YFCElement eOrderHoldType : eGetOrderDetailsOutput.getChildElement("OrderHoldTypes", true).getChildren()){
				resolve = true;
				if (!eOrderHoldType.getAttribute("Status").equals("1100")){
					resolve = false;
					break;
				} else {
					YFCElement eOrderHoldTypeInput = eChangeOrderInput.getChildElement("OrderHoldTypes", true).createChild("OrderHoldType");
					eOrderHoldTypeInput.setAttribute("HoldType",eOrderHoldType.getAttribute("HoldType"));
					eOrderHoldTypeInput.setAttribute("Status", "1300");
					YFCElement eResolve = eResults.getChildElement("ResolveHold", true).createChild("Hold");
					eResolve.setAttribute("HoldType", eOrderHoldType.getAttribute("HoldType"));
				}
			}
			if (resolve){
				try {
					//m_YfsEnv.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
					getOrderDetailsOutput = m_YifApi.invoke(env, "changeOrder", changeOrder.getDocument());
					eResults.getChildElement("ResolveHold", true).setAttribute("Successful", "Y");
				} catch(Exception yex) {
		        	System.out.println("The api call changeOrder failed using the following input xml: " + eChangeOrderInput);
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
		        } 
			}
		}
	}
	
	public static boolean processOrderPayments(YFSEnvironment env, Document dOrder, boolean auth, YFCElement eOutput, YIFApi m_YifApi){
		if(dOrder.getDocumentElement().getAttribute("PaymentStatus").equals("NOT_APPLICABLE")){
			eOutput.setAttribute("Authorized", "NA");
			return true;
		}
		if (auth && (dOrder.getDocumentElement().getAttribute("PaymentStatus").equals("AUTHORIZED") || dOrder.getDocumentElement().getAttribute("PaymentStatus").equals("INVOICED"))){
			eOutput.setAttribute("Pre-Authorized", "Y");
			return true;
		} else if (!auth && dOrder.getDocumentElement().getAttribute("PaymentStatus").equals("PAID")){
			eOutput.setAttribute("Pre-Charged", "Y");
			return true;
		}
		
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		Document processOrderPaymentsOutput = null;
		try {
			env.setApiTemplate("requestCollection", getOrderDetailsTemplate());
			processOrderPaymentsOutput = m_YifApi.invoke(env, "requestCollection", getOrderDetailsInput.getDocument());
			((YFSContext)env).commit();
			m_YifApi.invoke(env, "executeCollection", getOrderDetailsInput.getDocument());
			processOrderPaymentsOutput = m_YifApi.invoke(env, "requestCollection", getOrderDetailsInput.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call processOrderPayments failed using the following input xml: " + eOrder);
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
		if (!YFCCommon.isVoid(processOrderPaymentsOutput)){
			if (auth){
				eOutput.setAttribute("PaymentAuthorized", processOrderPaymentsOutput.getDocumentElement().getAttribute("PaymentStatus").equals("AUTHORIZED"));
				return processOrderPaymentsOutput.getDocumentElement().getAttribute("PaymentStatus").equals("AUTHORIZED");
			} else {
				eOutput.setAttribute("PaymentCharged", processOrderPaymentsOutput.getDocumentElement().getAttribute("PaymentStatus").equals("PAID"));
				return processOrderPaymentsOutput.getDocumentElement().getAttribute("PaymentStatus").equals("PAID");
			}
		}
		return false;
	}
	
	public static void scheduleOrder(YFSEnvironment env, Document dOrder, YIFApi m_YifApi){
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		try {
			m_YifApi.invoke(env, "scheduleOrder", getOrderDetailsInput.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call scheduleOrder failed using the following input xml: " + eOrder);
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
	}
	
	public static void releaseOrder(YFSEnvironment env, Document dOrder, YIFApi m_YifApi, String sIgnoreReleaseDate){
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		eOrder.setAttribute("IgnoreReleaseDate", sIgnoreReleaseDate);
		try {
			m_YifApi.invoke(env, "releaseOrder", getOrderDetailsInput.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call releaseOrder failed using the following input xml: " + eOrder);
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
	}
	
	public static Document getOrderDetailsForShipmentTemplate(){
		YFCElement eOrder = YFCDocument.createDocument("Order").getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("OrderNo", "");
		YFCElement eOrderLine = eOrder.getChildElement("OrderLines", true).getChildElement("OrderLine", true);
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("SubLineNo", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("IsBundleParent","");
		eOrderLine.setAttribute("ShipNode","");
		eOrderLine.setAttribute("OrderHeaderKey", "");
		YFCElement eItem = eOrderLine.getChildElement("Item", true);
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("ProductClass", "");
		YFCElement eSchedule = eOrderLine.getChildElement("Schedules", true).getChildElement("Schedule", true);
		eSchedule.setAttribute("BatchNo", "");
		eSchedule.setAttribute("LotNumber", "");
		eSchedule.setAttribute("RevisionNo", "");
		eSchedule.setAttribute("Quantity", "");
		eSchedule.setAttribute("OrderLineScheduleKey", "");
		YFCElement eShipmentLine = eOrderLine.getChildElement("ShipmentLines", true).getChildElement("ShipmentLine", true);
		eShipmentLine.setAttribute("ActualQuantity", "");
		eShipmentLine.setAttribute("OrderReleaseKey", "");
		eShipmentLine.setAttribute("Quantity", "");
		eShipmentLine.setAttribute("ShipmentLineKey", "");
		eShipmentLine.setAttribute("ShipmentKey", "");
		YFCElement eShipmentTagSerial = eShipmentLine.getChildElement("ShipmentTagSerials", true).getChildElement("ShipmentTagSerial", true);
		eShipmentTagSerial.setAttribute("BatchNo", "");
		eShipmentTagSerial.setAttribute("SerialNo", "");
		eShipmentTagSerial.setAttribute("LotNumber", "");
		eShipmentTagSerial.setAttribute("RevisionNo", "");
		eShipmentTagSerial.setAttribute("Quantity", "");
		YFCElement eOrderStatus = eOrderLine.getChildElement("OrderStatuses", true).getChildElement("OrderStatus",true);
		eOrderStatus.setAttribute("OrderLineKey", "");
		eOrderStatus.setAttribute("OrderReleaseKey", "");
		eOrderStatus.setAttribute("OrderLineScheduleKey", "");
		eOrderStatus.setAttribute("ShipNode", "");
		eOrderStatus.setAttribute("Status", "");
		eOrderStatus.setAttribute("StatusQty", "");
		eOrderStatus.setAttribute("OrderHeaderKey", "");
		return eOrder.getOwnerDocument().getDocument();
	}
	
	public static YFCElement getScheduleForScheduleKey(YFCElement eOrderLine, String sScheduleKey){
		YFCElement eSchedules = eOrderLine.getChildElement("Schedules", true);
		for(YFCElement eSchedule : eSchedules.getChildren()){
			if(eSchedule.getAttribute("OrderLineScheduleKey").equals(sScheduleKey)){
				return eSchedule;
			}
		}
		return null;
	}

	public static String getSerialForLine(long serialNo, YFCElement eOriginalOrderLine, YFCElement eSerialItem){
		for(YFCElement eOSL : eOriginalOrderLine.getChildElement("ShipmentLines", true).getChildren()){
			for(YFCElement eSTS : eOSL.getChildElement("ShipmentTagSerials", true).getChildren()){
				if(!YFCCommon.isVoid(eSTS.getAttribute("SerialNo"))){
					eSTS.setAttribute("Used", true);
					return eSTS.getAttribute("SerialNo");
				}
			}
		}
		String serial =  serialNo + "-X39";
		if(!YFCCommon.isVoid(eSerialItem.getAttribute("Serial"))){
			serial = eSerialItem.getAttribute("Serial").replace("{serial}", serialNo + "");
		} 
		return serial;
	}
	
	public static YFCDocument createShipment(String sIdentifier, YFCElement eOrderLineStatus, boolean bCashAndCarry, HashMap<String, YFCDocument> shipments){
		if(shipments.containsKey(eOrderLineStatus.getAttribute(sIdentifier))){
			return shipments.get(eOrderLineStatus.getAttribute(sIdentifier));
		}
		YFCDocument	dShipment = YFCDocument.createDocument("Shipment");
		shipments.put(eOrderLineStatus.getAttribute(sIdentifier), dShipment);
		YFCElement eShipment = dShipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute(sIdentifier) + "_S");
		eShipment.setAttribute("TrackingNo", "1B" + eOrderLineStatus.getAttribute(sIdentifier));
		if (bCashAndCarry)
			eShipment.setAttribute("ShipmentType", "CashAndCarry");
		return dShipment;
	}
	
	public static YFCElement createShipmentTag(YFCElement eSchedule, YFCElement eShipmentLine, int quantity, String serial){
		if(!YFCCommon.isVoid(serial) || (!YFCCommon.isVoid(eSchedule) && (!YFCCommon.isVoid(eSchedule.getAttribute("BatchNo")) || !YFCCommon.isVoid(eSchedule.getAttribute("LotNumber")) || !YFCCommon.isVoid(eSchedule.getAttribute("RevisionNo"))))){
			YFCElement eShipmentTagSerial = eShipmentLine.getChildElement("ShipmentTagSerials", true).createChild("ShipmentTagSerial");
			eShipmentTagSerial.setAttribute("Quantity", quantity);
			if(!YFCCommon.isVoid(serial)){
				eShipmentTagSerial.setAttribute("SerialNo", serial);
			}
			if(!YFCCommon.isVoid(eSchedule.getAttribute("BatchNo"))){
				eShipmentTagSerial.setAttribute("BatchNo", eSchedule.getAttribute("BatchNo"));
			}
			if(!YFCCommon.isVoid(eSchedule.getAttribute("LotNumber"))){
				eShipmentTagSerial.setAttribute("LotNumber", eSchedule.getAttribute("LotNumber"));
			}
			if(!YFCCommon.isVoid(eSchedule.getAttribute("RevisionNo"))){
				eShipmentTagSerial.setAttribute("RevisionNo", eSchedule.getAttribute("RevisionNo"));
			}
			return eShipmentTagSerial;
		}
		return null;
	}
	
	public static YFCElement createShipmentLine(YFCDocument dShipment, YFCElement eOrderLine, YFCElement eOrderLineStatus, int lineNo, String sDocumentType){
		YFCElement eShipmentLine = dShipment.getDocumentElement().getChildElement("ShipmentLines", true).createChild("ShipmentLine"); 
		eShipmentLine.setAttribute("DocumentType", sDocumentType);
		eShipmentLine.setAttribute("ItemID", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
		eShipmentLine.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item", true).getAttribute("UnitOfMeasure"));
		eShipmentLine.setAttribute("ProductClass", eOrderLine.getChildElement("Item", true).getAttribute("ProductClass"));
		eShipmentLine.setAttribute("PrimeLineNo", eOrderLine.getAttribute("PrimeLineNo"));
		eShipmentLine.setAttribute("SubLineNo", eOrderLine.getAttribute("SubLineNo"));
		eShipmentLine.setAttribute("ShipmentLineNo", lineNo);
		eShipmentLine.setAttribute("OrderHeaderKey", eOrderLine.getAttribute("OrderHeaderKey"));
		eShipmentLine.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute("OrderReleaseKey") + "_S");
		eShipmentLine.setAttribute("Quantity", eOrderLineStatus.getAttribute("StatusQty"));
		if(!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey"))){
			eShipmentLine.setAttribute("OrderReleaseKey", eOrderLineStatus.getAttribute("OrderReleaseKey"));
		}
		return eShipmentLine;
	}
	
	public static YFCElement createShipmentResponse(YFCElement eOutput, String sShipmentKey, boolean confirm, boolean create, String error){
		YFCElement eCreatedShipment = eOutput.getChildElement("Shipments", true).createChild("Shipment");
		eCreatedShipment.setAttribute("ShipmentKey", sShipmentKey);
		eCreatedShipment.setAttribute("Confirm", confirm);
		eCreatedShipment.setAttribute("Create", create);
		if(!YFCCommon.isVoid(error)){
			eCreatedShipment.setAttribute("Error", error);
		}		
		return eCreatedShipment;
	}
	
	private static HashMap<String, YFCElement> _nodeCache;
	
	public static HashMap<String, YFCElement> getNodeCache(){
		if(YFCCommon.isVoid(_nodeCache)){
			_nodeCache = new HashMap<String, YFCElement>();
		}
		return _nodeCache;
	}
	
	private static Document getNodeTemplate(){
		YFCDocument dTemp = YFCDocument.createDocument("ShipNodeList");
		YFCElement eList = dTemp.getDocumentElement();
		YFCElement eShipNode = eList.createChild("ShipNode");
		eShipNode.setAttribute("ShipNode", "");
		eShipNode.setAttribute("NodeType", "");
		eShipNode.setAttribute("ShipnodeType", "");
		return dTemp.getDocument();
	}
	
	public static boolean isStoreNode(YFSEnvironment env, YIFApi m_YifApi, String sShipNode) throws YFSException, RemoteException{
		if(!getNodeCache().containsKey(sShipNode)){
			YFCDocument dInput = YFCDocument.createDocument("ShipNode");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("ShipNode", sShipNode);
			
			env.setApiTemplate("getShipNodeList", getNodeTemplate());
			Document dResponse = m_YifApi.invoke(env, "getShipNodeList", dInput.getDocument());
			YFCDocument dShipNodeList = YFCDocument.getDocumentFor(dResponse);
			for(YFCElement eShipNode : dShipNodeList.getDocumentElement().getChildren()){
				getNodeCache().put(eShipNode.getAttribute("ShipNode"), eShipNode);
			}
		}
		
		if(getNodeCache().containsKey(sShipNode)){
			YFCElement eNode = getNodeCache().get(sShipNode);
			return eNode.getAttribute("NodeType").toUpperCase().contains("STORE") || eNode.getAttribute("ShipnodeType").toUpperCase().contains("STORE");
		} else {
			return sShipNode.toUpperCase().contains("STORE") && sShipNode.toUpperCase().contains("_S");
		}
	}
	
	public static String confirmShipment(YFSEnvironment env, Document dOrder, YFCElement eOutput, YIFApi m_YifApi, boolean pushBackroom, boolean bCashAndCarry) throws YFSException, RemoteException {
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
		Document getOrderDetailsOutput = null;
		try {
			env.setApiTemplate("getCompleteOrderDetails", getOrderDetailsForShipmentTemplate());
			getOrderDetailsOutput = m_YifApi.invoke(env, "getCompleteOrderDetails", getOrderDetailsInput.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call getOrderDetails failed using the following input xml: " + eOrder);
        	System.out.println("The error thrown was: " );    
            yex.printStackTrace();
        } 
		
		
		if (!YFCCommon.isVoid(getOrderDetailsOutput)){
			boolean linesExist = false;
			HashMap<String, YFCDocument> confirmShipments = new HashMap<String, YFCDocument>();
			HashMap<String, YFCDocument> createShipments = new HashMap<String, YFCDocument>();
			YFCElement eOrderOut = YFCDocument.getDocumentFor(getOrderDetailsOutput).getDocumentElement();
			int i = 0;
			for (YFCElement eOrderLine : eOrderOut.getChildElement("OrderLines", true).getChildren()){
				if (eOrderLine.getAttribute("DeliveryMethod").equals("SHP")){
					for (YFCElement eOrderLineStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
						if (!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey")) && eOrderLineStatus.getAttribute("Status").compareTo("3350") < 0) {
							if (!eOrderLine.getBooleanAttribute("IsBundleParent", false)){
								YFCDocument dShipment;
								boolean confirm = false;
								if(!YFCCommon.equals(eOrderOut.getAttribute("DocumentType"), "0001") || YFCCommon.isVoid(eOrderLineStatus.getAttribute("ShipNode")) || !isStoreNode(env, m_YifApi, eOrderLineStatus.getAttribute("ShipNode"))){
									dShipment = createShipment("OrderReleaseKey", eOrderLineStatus, bCashAndCarry, confirmShipments);
									confirm = true;
								} else {
									dShipment = createShipment("OrderReleaseKey", eOrderLineStatus, bCashAndCarry, createShipments);
									Calendar c = Calendar.getInstance();
									if(c.get(Calendar.HOUR_OF_DAY) > 18){
										c.add(Calendar.DATE, 1);
									} 
									c.set(Calendar.HOUR_OF_DAY, 18);
									c.set(Calendar.MINUTE, 0);
									c.set(Calendar.SECOND, 0);
									dShipment.getDocumentElement().setAttribute("ExpectedShipmentDate", YTimestamp.newMutableTimestamp(c.getTimeInMillis()));
								}
									
								YFCElement eShipmentLine = createShipmentLine(dShipment, eOrderLine, eOrderLineStatus, ++i, eOrderOut.getAttribute("DocumentType")); 
								YFCElement eSchedule = getScheduleForScheduleKey(eOrderLine, eOrderLineStatus.getAttribute("OrderLineScheduleKey"));
								
								
								boolean serialize = false;
								if(confirm){
									File temp = new File("/opt/Sterling/Scripts/serialItems.xml");
									if(temp.exists()){
										YFCDocument serialItems = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/serialItems.xml");
										YFCElement eSerialItems = serialItems.getDocumentElement();
										for(YFCElement eSerialItem : eSerialItems.getChildren()){
											if(YFCCommon.equals(eOrderLine.getChildElement("Item", true).getAttribute("ItemID"), eSerialItem.getAttribute("ItemID"))){
												long serialNo = System.currentTimeMillis();					
												serialize = true;
												for(int j = 0; j < eOrderLineStatus.getIntAttribute("StatusQty"); j++){
													createShipmentTag(eSchedule, eShipmentLine, 1, getSerialForLine(serialNo + j, eOrderLine, eSerialItem));
												}
											}
										}										
									}									
								}
								if(!serialize){
									createShipmentTag(eSchedule, eShipmentLine, eOrderLineStatus.getIntAttribute("StatusQty"), null);
								}
								
								linesExist = true;
							}
						}
					}
				} else if (eOrderLine.getAttribute("DeliveryMethod").equals("PICK")){
					for (YFCElement eOrderLineStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
						if (!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey")) && eOrderLineStatus.getAttribute("Status").compareTo("3350") < 0) {
							if (!eOrderLine.getBooleanAttribute("IsBundleParent", false)){
								YFCDocument dShipment = null;
								if (bCashAndCarry){
									dShipment = confirmShipments.get(eOrderLineStatus.getAttribute("OrderReleaseKey"));
									if (YFCCommon.isVoid(dShipment)){
										dShipment = YFCDocument.createDocument("Shipment");
										YFCElement eShipment = dShipment.getDocumentElement();
										eShipment.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute("OrderReleaseKey") + "_S");
										eShipment.setAttribute("ShipNode", eOrderLineStatus.getAttribute("ShipNode"));
										eShipment.setAttribute("ShipmentType", "CashAndCarry");
										confirmShipments.put(eOrderLineStatus.getAttribute("OrderReleaseKey"), dShipment);
									}
								} else {
									dShipment = createShipments.get(eOrderLineStatus.getAttribute("OrderReleaseKey"));
									if (YFCCommon.isVoid(dShipment)){
										dShipment = YFCDocument.createDocument("Shipment");
										YFCElement eShipment = dShipment.getDocumentElement();
										eShipment.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute("OrderReleaseKey") + "_S");
										eShipment.setAttribute("ShipNode", eOrderLineStatus.getAttribute("ShipNode"));
										createShipments.put(eOrderLineStatus.getAttribute("OrderReleaseKey"), dShipment);
										Calendar c = Calendar.getInstance();
										if(c.get(Calendar.HOUR_OF_DAY) > 18){
											c.add(Calendar.DATE, 1);
											c.set(Calendar.HOUR_OF_DAY, 9);
											c.set(Calendar.MINUTE, 0);
											c.set(Calendar.SECOND, 0);
										} else if(c.get(Calendar.HOUR_OF_DAY) < 9){
											c.set(Calendar.HOUR_OF_DAY, 9);
											c.set(Calendar.MINUTE, 0);
											c.set(Calendar.SECOND, 0);
										} else {
											c.add(Calendar.MINUTE, 30);
										}
										dShipment.getDocumentElement().setAttribute("ExpectedShipmentDate", YTimestamp.newMutableTimestamp(c.getTimeInMillis()));
									}
								}
								createShipmentLine(dShipment, eOrderLine, eOrderLineStatus, ++i, eOrderOut.getAttribute("DocumentType")); 
								linesExist = true;
							}
						}
					}
				}
			}
			
			if (linesExist){
				for(String key : confirmShipments.keySet()){
					try {
						getOrderDetailsOutput = m_YifApi.invoke(env, "confirmShipment", confirmShipments.get(key).getDocument());
						createShipmentResponse(eOutput, key + "_S", true, true, null);
					} catch(Exception yex) {
						createShipmentResponse(eOutput, key + "_S", false, false, yex.toString());
						System.out.println("The api call confirmShipment failed using the following input xml: " + confirmShipments.get(key));
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        }
				}
				env.setApiTemplate("createShipment", getCreateShipmentTemplate());
				for(String key : createShipments.keySet()){
					try {
						getOrderDetailsOutput = m_YifApi.invoke(env, "createShipment", createShipments.get(key).getDocument());
						createShipmentResponse(eOutput, key + "_S", false, true, null);
						YFCElement eCreatedShipment = createShipmentResponse(eOutput, key + "_S", false, true, null);
						if (pushBackroom){
							backroomPick(env, getOrderDetailsOutput, eCreatedShipment, m_YifApi);
						}
						
					} catch(Exception yex) {
						createShipmentResponse(eOutput, key + "_S", false, false, yex.toString());
			        	System.out.println("The api call confirmShipment failed using the following input xml: " + createShipments.get(key));
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        }
				}
				
			}
		}		
		return null;
	}
	

	
	private static void backroomPick(YFSEnvironment env, Document dShipment, YFCElement eCreatedShipment, YIFApi m_YifApi) {
		YFCElement eShipment = YFCDocument.getDocumentFor(dShipment).getDocumentElement();
		YFCDocument dChangeShipment = YFCDocument.createDocument("Shipment");
		YFCElement eInput = dChangeShipment.getDocumentElement();
		eInput.setAttribute("EnterpriseCode", eShipment.getAttribute("EnterpriseCode"));
		eInput.setAttribute("SellerOrganizationCode", eShipment.getAttribute("SellerOrganizationCode"));
		eInput.setAttribute("ShipNode", eShipment.getAttribute("ShipNode"));
		eInput.setAttribute("ShipmentKey", eShipment.getAttribute("ShipmentKey"));
		for (YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()){
			YFCElement eInputLine = eInput.getChildElement("ShipmentLines", true).createChild("ShipmentLine");
			eInputLine.setAttribute("BackroomPickedQuantity", eShipmentLine.getAttribute("ActualQuantity"));
			eInputLine.setAttribute("ShipmentLineNo", eShipmentLine.getAttribute("ShipmentLineNo"));
		}
		try{
			m_YifApi.invoke(env, "changeShipment", dChangeShipment.getDocument());
			eCreatedShipment.setAttribute("BackroomPick", "Y");
		} catch(Exception yex) {
			System.out.println("The error thrown was: " );    
	    	System.out.println(yex.toString());
	        yex.printStackTrace();
	    }
		YFCDocument dChangeStatus = YFCDocument.createDocument("Shipment");
		eInput = dChangeStatus.getDocumentElement();
		eInput.setAttribute("BaseDropStatus", "1100.70.06.30");
		eInput.setAttribute("ShipmentKey", eShipment.getAttribute("ShipmentKey"));
		eInput.setAttribute("TransactionId", "YCD_BACKROOM_PICK");
		try{
			m_YifApi.invoke(env, "changeShipmentStatus", dChangeStatus.getDocument());
			eCreatedShipment.setAttribute("UpdateStatus", "Y");
		} catch(Exception yex) {
			System.out.println("The error thrown was: " );    
	    	System.out.println(yex.toString());
	        yex.printStackTrace();
	    }
	}
	
	public static Document getCreateShipmentTemplate(){
		YFCDocument dShipment = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dShipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey","");
		eShipment.setAttribute("EnterpriseCode","");
		eShipment.setAttribute("SellerOrganizationCode","");
		eShipment.setAttribute("ShipNode","");
		eShipment.setAttribute("DeliveryMethod","");
		YFCElement eShipmentLine = eShipment.createChild("ShipmentLines").createChild("ShipmentLine");
		eShipmentLine.setAttribute("ShipmentLineNo", "");
		eShipmentLine.setAttribute("IsPickable", "");
		eShipmentLine.setAttribute("ActualQuantity", "");
		eShipmentLine.setAttribute("ItemID", "");
		eShipmentLine.setAttribute("ProductClass", "");
		eShipmentLine.setAttribute("UnitOfMeasure", "");
		return dShipment.getDocument();
	}
	
	public static void createShipmentInvoice (YFSEnvironment env, String sShipmentKey, String sDocumentType, YIFApi m_YifApi){
		YFCDocument shipment = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = shipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", sShipmentKey);
		if(YFCCommon.equals(sDocumentType, "0001")){
			eShipment.setAttribute("TransactionId", "CREATE_SHMNT_INVOICE.0001");
		} else if(YFCCommon.equals(sDocumentType, "0005")) {
			eShipment.setAttribute("TransactionId", "CREATE_DROP_SHIP_INVOICE.0005.ex");
		}
		try {
			m_YifApi.invoke(env, "createShipmentInvoice", shipment.getDocument());
		} catch(Exception yex) {
        	System.out.println("The api call createShipmentInvoice failed using the following input xml: " + eShipment);
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
	}
	public void callAPI(YFSEnvironment env, Document inputXml, String sAPI, YIFApi m_YifApi) throws Exception {
		try {
			m_YifApi.invoke(env, sAPI, inputXml);
		} catch(Exception yex) {
	        	System.out.println("The api call " + sAPI + " failed using the following input xml: " + YFCDocument.getDocumentFor(inputXml));
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        }
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "completeOrder";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub
		
	}
}
