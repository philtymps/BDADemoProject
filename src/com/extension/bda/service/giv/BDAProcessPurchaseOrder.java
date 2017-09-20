package com.extension.bda.service.giv;

import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAProcessPurchaseOrder implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "processPurchaseOrder";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args){
		
	}

	
	public static String confirmShipment(YFSEnvironment env, Document dOrder, YFCElement eOutput, YIFApi m_YifApi) {
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
			HashMap<String, YFCDocument> confirmShipments = new HashMap<String, YFCDocument>();
			HashMap<String, YFCDocument> createShipments = new HashMap<String, YFCDocument>();
			YFCElement eOrderOut = YFCDocument.getDocumentFor(getOrderDetailsOutput).getDocumentElement();
			int i = 0;
			for (YFCElement eOrderLine : eOrderOut.getChildElement("OrderLines", true).getChildren()){
				for (YFCElement eOrderLineStatus : eOrderLine.getChildElement("OrderStatuses", true).getChildren()){
					if (eOrderLineStatus.getAttribute("Status").compareTo("1201") < 0 ||  eOrderLineStatus.getAttribute("Status").compareTo("3200") >= 0) {
						String shipmentID = "OrderHeaderKey";
						if(!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey"))){
							shipmentID = "OrderReleaseKey";
						}
						if (!eOrderLine.getBooleanAttribute("IsBundleParent", false)){
							YFCDocument dShipment;
							if(YFCCommon.equals(eOrderOut.getAttribute("DocumentType"), "0006")){
								dShipment = createShipments.get(eOrderLineStatus.getAttribute(shipmentID));
								if (YFCCommon.isVoid(dShipment)){
									dShipment = YFCDocument.createDocument("Shipment");
									YFCElement eShipment = dShipment.getDocumentElement();
									eShipment.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute(shipmentID) + "_S");
									//eShipment.setAttribute("TrackingNo", "1B"+eOrderLineStatus.getAttribute("OrderReleaseKey"));
									eShipment.setAttribute("TrackingNo", "JD0002215620664620");
									createShipments.put(eOrderLineStatus.getAttribute(shipmentID), dShipment);
								}
							} else {
								dShipment = confirmShipments.get(eOrderLineStatus.getAttribute(shipmentID));
								if (YFCCommon.isVoid(dShipment)){
									dShipment = YFCDocument.createDocument("Shipment");
									YFCElement eShipment = dShipment.getDocumentElement();
									eShipment.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute(shipmentID) + "_S");
									//eShipment.setAttribute("TrackingNo", "1B"+eOrderLineStatus.getAttribute("OrderReleaseKey"));
									eShipment.setAttribute("TrackingNo", "JD0002215620664620");
									confirmShipments.put(eOrderLineStatus.getAttribute(shipmentID), dShipment);
								}
							}
							
							YFCElement eShipmentLine = dShipment.getDocumentElement().getChildElement("ShipmentLines", true).createChild("ShipmentLine"); 
							eShipmentLine.setAttribute("DocumentType", eOrderOut.getAttribute("DocumentType"));
							eShipmentLine.setAttribute("ItemID", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
							eShipmentLine.setAttribute("UnitOfMeasure", eOrderLine.getChildElement("Item", true).getAttribute("UnitOfMeasure"));
							eShipmentLine.setAttribute("ProductClass", eOrderLine.getChildElement("Item", true).getAttribute("ProductClass"));
							eShipmentLine.setAttribute("PrimeLineNo", eOrderLine.getAttribute("PrimeLineNo"));
							eShipmentLine.setAttribute("SubLineNo", eOrderLine.getAttribute("SubLineNo"));
							eShipmentLine.setAttribute("OrderHeaderKey", eOrderOut.getAttribute("OrderHeaderKey"));
							eShipmentLine.setAttribute("ShipmentLineNo", ++i);
							eShipmentLine.setAttribute("ShipmentKey", eOrderLineStatus.getAttribute(shipmentID) + "_S");
							eShipmentLine.setAttribute("Quantity", eOrderLineStatus.getAttribute("StatusQty"));
							if(!YFCCommon.isVoid(eOrderLineStatus.getAttribute("OrderReleaseKey"))){
								eShipmentLine.setAttribute("OrderReleaseKey", eOrderLineStatus.getAttribute("OrderReleaseKey"));
							}
							linesExist = true;
						}
					}
				}
			}
			
			if (linesExist){
				for(String key : confirmShipments.keySet()){
					try {
						getOrderDetailsOutput = m_YifApi.invoke(env, "confirmShipment", confirmShipments.get(key).getDocument());
						YFCElement eCreatedShipment = eOutput.getChildElement("Shipments", true).createChild("Shipment");
						eCreatedShipment.setAttribute("ShipmentKey", key + "_S");
						eCreatedShipment.setAttribute("Confirm", "Y");
						eCreatedShipment.setAttribute("Create", "Y");
					} catch(Exception yex) {
			        	System.out.println("The api call confirmShipment failed using the following input xml: " + confirmShipments.get(key));
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        }
				}
				env.setApiTemplate("createShipment", CompleteOrder.getCreateShipmentTemplate());
				for(String key : createShipments.keySet()){
					try {
						getOrderDetailsOutput = m_YifApi.invoke(env, "createShipment", createShipments.get(key).getDocument());
						YFCElement eCreatedShipment = eOutput.getChildElement("Shipments", true).createChild("Shipment");
						eCreatedShipment.setAttribute("ShipmentKey", key + "_S");
						eCreatedShipment.setAttribute("Confirm", "N");
						eCreatedShipment.setAttribute("Create", "Y");
					} catch(Exception yex) {
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
	@Override
	public Document invoke(YFSEnvironment env, Document inputDoc){
		YFCDocument output = YFCDocument.createDocument("Results");
		YFCElement eResults = output.getDocumentElement();
		try {	
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			try {
				env.setApiTemplate("getOrderDetails", CompleteOrder.getOrderDetailsTemplate());
				l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
				eResults.setAttribute("OrderHeaderKey", l_OutputXml.getDocumentElement().getAttribute("OrderHeaderKey"));
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
			if (!YFCCommon.isVoid(l_OutputXml)){
				YFCElement eOrder = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
				if(YFCCommon.equals(eOrder.getAttribute("DocumentType"), "0005") || YFCCommon.equals(eOrder.getAttribute("DocumentType"), "0006")){
					try {
			           	confirmShipment(env, l_OutputXml, eResults, localApi);
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

}
