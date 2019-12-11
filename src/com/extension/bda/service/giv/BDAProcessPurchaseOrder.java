package com.extension.bda.service.giv;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

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

	
	
	public static String confirmShipment(YFSEnvironment env, YFCElement eResults, boolean confirm) throws YIFClientCreationException, YFSException, RemoteException {
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", eResults.getAttribute("OrderHeaderKey"));
		Document getOrderDetailsOutput = null;
		YIFApi m_YifApi = YIFClientFactory.getInstance().getLocalApi();
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
							if(!confirm || (YFCCommon.equals(eOrderOut.getAttribute("DocumentType"), "0006"))){
								dShipment = CompleteOrder.createShipment(shipmentID, eOrderLineStatus, false, null, null, createShipments);
							} else {
								dShipment = CompleteOrder.createShipment(shipmentID, eOrderLineStatus, false, null, null, confirmShipments);
							}
							
							YFCElement eShipmentLine = CompleteOrder.createShipmentLine(dShipment, eOrderLine, eOrderLineStatus, ++i, eOrderOut.getAttribute("DocumentType")); 
							YFCElement eSchedule = CompleteOrder.getScheduleForScheduleKey(eOrderLine, eOrderLineStatus.getAttribute("OrderLineScheduleKey"));
							boolean serialize = false;
							File temp = new File(BDAServiceApi.getScriptsPath(env) + "/serialItems.xml");
							if(temp.exists()){
								YFCDocument serialItems = YFCDocument.getDocumentForXMLFile(BDAServiceApi.getScriptsPath(env) + "/serialItems.xml");
								YFCElement eSerialItems = serialItems.getDocumentElement();
								for(YFCElement eSerialItem : eSerialItems.getChildren()){
									if(YFCCommon.equals(eOrderLine.getChildElement("Item", true).getAttribute("ItemID"), eSerialItem.getAttribute("ItemID"))){
										long serialNo = System.currentTimeMillis();					
										serialize = true;
										for(int j = 0; j < eOrderLineStatus.getIntAttribute("StatusQty"); j++){
											String serial = CompleteOrder.getSerialForLine(serialNo + j, eOrderLine, eSerialItem);
											YFCElement eShipmentTagSerial = CompleteOrder.createShipmentTag(eSchedule, eShipmentLine, 1, serial);
											eShipmentTagSerial.setAttribute("SerialNo", serial);	
										}
									}
								}										
							}	
							if(!serialize){
								CompleteOrder.createShipmentTag(eSchedule, eShipmentLine, eOrderLineStatus.getIntAttribute("StatusQty"), null);
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
						CompleteOrder.createShipmentResponse(eResults, key + "_S", true, true, null);
					} catch(Exception yex) {
						CompleteOrder.createShipmentResponse(eResults, key + "_S", false, false, yex.toString());
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
						CompleteOrder.createShipmentResponse(eResults, key + "_S", false, true, null);
										
					} catch(Exception yex) {
						CompleteOrder.createShipmentResponse(eResults, key + "_S", false, false, yex.toString());
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
				try {
		           	confirmShipment(env, eResults, true);
		        } catch(Exception yex) {
		        	System.out.println("The error thrown was: " );    
		        	System.out.println(yex.toString());
		            yex.printStackTrace();
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
