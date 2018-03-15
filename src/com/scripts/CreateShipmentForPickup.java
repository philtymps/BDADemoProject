package com.scripts;

import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;

public class CreateShipmentForPickup {

	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(CreateShipmentForPickup.class);
	
	public CreateShipmentForPickup(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public boolean processBackroomPick(){
		if (!YFCCommon.isVoid(getProperty("ProcessBackroomPick"))){
			return (YFCCommon.equals(((String)getProperty("ProcessBackroomPick")).toUpperCase(), "Y") || YFCCommon.equals(((String)getProperty("ProcessBackroomPick")).toUpperCase(), "TRUE"));
		}
		return true;
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
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("MinLineStatus", "");
		eOrderLine.setAttribute("MaxLineStatus", "");
		eOrderLine.setAttribute("OrderedQty", "");
		YFCElement ePaymentMethod = eOrder.getChildElement("PaymentMethods", true).getChildElement("PaymentMethod", true);
		ePaymentMethod.setAttribute("PaymentType", "");
		ePaymentMethod.setAttribute("MaxChargeLimit", "");
		ePaymentMethod.setAttribute("UnlimitedCharges", "");
		ePaymentMethod.setAttribute("PaymentTypeGroup", "");
		ePaymentMethod.setAttribute("PaymentKey", "");
		YFCElement ePriceInfo = eOrder.getChildElement("PriceInfo", true);
		ePriceInfo.setAttribute("TotalAmount", "");
		ePriceInfo.setAttribute("Currency", "");
		return output.getDocument();
	}
	
	private boolean onlyPickup(YFCElement eOrder){
		if (!eOrder.getChildElement("OrderLines", true).hasChildNodes()){
			return false;
		}
		for (YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			if (!YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod"), "PICK")){
				return false;
			}
		}
		return true;
	}
	
	private boolean newWithReadylines(YFCElement eOrder){
		if (!eOrder.getChildElement("OrderLines", true).hasChildNodes()){
			return false;
		}
		boolean someProcessed = false;
		for (YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			if (!YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod"), "PICK")){
				return false;
			}
			if (YFCCommon.equals(eOrderLine.getAttribute("MaxLineStatus"), "3350.20")){
				someProcessed = true;
			}
			if (!YFCCommon.equals(eOrderLine.getAttribute("MaxLineStatus"), "3350.20") && !YFCCommon.equals(eOrderLine.getAttribute("MaxLineStatus"), "1100") && !YFCCommon.equals(eOrderLine.getAttribute("MaxLineStatus"), "9000")){
				return false;
			}
		}
		return someProcessed;
	}
	
	
	public static void updateChargeAmount(YFSEnvironment env, YFCElement eOrder, YFCElement eOutput, YIFApi localApi){
		double orderTotal = eOrder.getChildElement("PriceInfo", true).getDoubleAttribute("TotalAmount", 0);
		if (orderTotal > 0){
			double maxCharge = 0;
			YFCElement ePayLater = null;
			for (YFCElement ePayment : eOrder.getChildElement("PaymentMethods", true).getChildren()){
				if (ePayment.getBooleanAttribute("UnlimitedCharges", false)){
					maxCharge = orderTotal;
				} else {
					maxCharge += ePayment.getDoubleAttribute("MaxChargeLimit", 0);
				}
				if (YFCCommon.isVoid(ePayLater) && YFCCommon.equals("PayInStore", ePayment.getAttribute("PaymentType"))){
					ePayLater = ePayment;
				}
			}
			if (!YFCCommon.isVoid(ePayLater) && orderTotal > maxCharge){
				double existing = ePayLater.getDoubleAttribute("MaxChargeLimit", 0);
				double remainder = orderTotal - maxCharge;
				double newMax = existing + remainder + 1;
				YFCElement eInput = YFCDocument.createDocument("Order").getDocumentElement();
				eInput.setAttribute("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
				YFCElement ePayIn = eInput.getChildElement("PaymentMethods", true).createChild("PaymentMethod");
				ePayIn.setAttribute("PaymentKey", ePayLater.getAttribute("PaymentKey"));
				ePayIn.setAttribute("MaxChargeLimit", newMax);
				YFCElement ePayDetails = ePayIn.createChild("PaymentDetails");
				ePayDetails.setDateTimeAttribute("AuthorizationExpirationData", YFCDate.HIGH_DATE);
				ePayDetails.setAttribute("AuthorizationID", "A"+System.currentTimeMillis());
				ePayDetails.setAttribute("ChargeType", "AUTHORIZATION");
				ePayDetails.setAttribute("HoldAgainstBook", "Y");
				ePayDetails.setAttribute("RequestAmount", remainder + 1);
				ePayDetails.setAttribute("RequestProcessed", "Y");
				ePayDetails.setAttribute("ProcessedAmount", remainder + 1);
				try {
					//m_YfsEnv.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
					localApi.invoke(env, "changeOrder", eInput.getOwnerDocument().getDocument());
					eOutput.setAttribute("AdjustedPayment", "Y");
				} catch(Exception yex) {
		        	System.out.println("The api call changeOrder failed using the following input xml: " + eInput);
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		        	eOutput.setAttribute("AdjustedPayment", "Failed");
		            yex.printStackTrace();
		        } 
			}
		}
	}
	
	public Document processNewLines(YFSEnvironment env, Document inputDoc){
		YFCDocument output = YFCDocument.createDocument("Results");
		YFCElement eOutput = output.getDocumentElement();
		try {	
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			try {
				env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
				l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			} catch(Exception yex) {
				logger.debug("The error thrown was: " );    
				logger.debug(yex.toString());
	        } 
			if (!YFCCommon.isVoid(l_OutputXml)){
				YFCElement eOrder = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				if (newWithReadylines(eOrder)){
					try {
			            CompleteOrder.removeHolds(env, l_OutputXml, eOutput, localApi);
			            updateChargeAmount(env, eOrder, eOutput, localApi);
			            if (CompleteOrder.processOrderPayments(env, l_OutputXml, true, eOutput, localApi)){
			            	if (CompleteOrder.statusLessThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
			            		CompleteOrder.scheduleOrder(env, l_OutputXml, localApi);
				            	eOutput.setAttribute("ScheduleInvoked", "Y");
				            	env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
								l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			            	}
			            	if (CompleteOrder.statusLessThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "3200") && CompleteOrder.statusGreaterThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
			            		CompleteOrder. releaseOrder(env, l_OutputXml, localApi, "N");
					            eOutput.setAttribute("ReleaseInvoked", "Y");
					            env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
								l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			            	}
			            	CompleteOrder.confirmShipment(env, l_OutputXml, eOutput, localApi, true, true);
			            	YFCElement eShipments = eOutput.getChildElement("Shipments");
			            	if (!YFCCommon.isVoid(eShipments)){
			            		for (YFCElement eShipment : eShipments.getChildren()){
			            			if (eShipment.getBooleanAttribute("Confirm", false)){
			            				CompleteOrder.createShipmentInvoice(env, eShipment.getAttribute("ShipmentKey"), eOrder.getAttribute("DocumentType"), localApi);
				            			eShipment.setAttribute("ShipmentInvoiced", "Y");
			            			}
			            		}
			            	}
			            	if (CompleteOrder.statusGreaterThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MaxOrderStatus")), "3700")){
			            		CompleteOrder.processOrderPayments(env, l_OutputXml, false, eOutput, localApi);
			            	}
			            }
			        }
			        catch(Exception yex) {
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        }
				}
			}
		} catch (Exception e ) {
			logger.debug("Could not initialize the yifclient.properties.");
			logger.debug("Bad stuff happened trying to initialize the Yif Client.");
			logger.debug(e);
		}
		return output.getDocument();
	}
	
	public Document processPickupLines(YFSEnvironment env, Document inputDoc){
		YFCDocument output = YFCDocument.createDocument("Results");
		YFCElement eOutput = output.getDocumentElement();
		try {	
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			try {
				env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
				l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			} catch(Exception yex) {
				logger.debug("The error thrown was: " );    
				logger.debug(yex.toString());
	        } 
			if (!YFCCommon.isVoid(l_OutputXml)){
				YFCElement eOrder = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				if (onlyPickup(eOrder)){
					try {
			            CompleteOrder.removeHolds(env, l_OutputXml, eOutput, localApi);
			            if (CompleteOrder.processOrderPayments(env, l_OutputXml, true, eOutput, localApi)){
			            	if (CompleteOrder.statusLessThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
			            		CompleteOrder.scheduleOrder(env, l_OutputXml, localApi);
				            	eOutput.setAttribute("ScheduleInvoked", "Y");
				            	env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
								l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			            	}
			            	if (CompleteOrder.statusLessThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "3200") && CompleteOrder.statusGreaterThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
			            		CompleteOrder. releaseOrder(env, l_OutputXml, localApi, "N");
					            eOutput.setAttribute("ReleaseInvoked", "Y");
					            env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
								l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
			            	}
			            	CompleteOrder.confirmShipment(env, l_OutputXml, eOutput, localApi, processBackroomPick(), false);
			            	YFCElement eShipments = eOutput.getChildElement("Shipments");
			            	if (!YFCCommon.isVoid(eShipments)){
			            		for (YFCElement eShipment : eShipments.getChildren()){
			            			if (eShipment.getBooleanAttribute("Confirm", false)){
			            				CompleteOrder.createShipmentInvoice(env, eShipment.getAttribute("ShipmentKey"), eOrder.getAttribute("DocumentType"), localApi);
				            			eShipment.setAttribute("ShipmentInvoiced", "Y");
			            			}
			            		}
			            	}
			            	if (CompleteOrder.statusGreaterThan(CompleteOrder.getBaseStatus(l_OutputXml.getDocumentElement().getAttribute("MaxOrderStatus")), "3700")){
			            		CompleteOrder.processOrderPayments(env, l_OutputXml, false, eOutput, localApi);
			            	}
			            }
			        }
			        catch(Exception yex) {
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        }
				}
			}
		} catch (Exception e ) {
			logger.debug("Could not initialize the yifclient.properties.");
			logger.debug("Bad stuff happened trying to initialize the Yif Client.");
			logger.debug(e);
		}
		return output.getDocument();
	}
}
