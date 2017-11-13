package com.extension.bda.service.giv;

import java.rmi.RemoteException;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.extension.bda.service.IBDAService;
import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAProcessReturnOrder implements IBDAService {

	@Override
	public String getServiceName() {
		return "processReturnOrder";
	}

	@Override
	public void setProperties(Properties props) {
	
	}

	@Override
	public Document invoke(YFSEnvironment env, Document inputDoc) {
	
		YFCDocument output = YFCDocument.createDocument("Results");
		YFCElement eResults = output.getDocumentElement();
		
		try {
			YFCDocument dOrder = getOrderDetails(env, inputDoc);
			eResults.setAttribute("OrderHeaderKey", dOrder.getDocumentElement().getAttribute("OrderHeaderKey"));
			if(CompleteOrder.hasStatus(dOrder.getDocumentElement(), "1100")){
				dOrder = authorizeReturn(env, eResults);
			}
			if (CompleteOrder.statusLessThan(CompleteOrder.getBaseStatus(dOrder.getDocumentElement().getAttribute("MinOrderStatus")), "1500")){
				dOrder = scheduleOrder(env, eResults);
			}
			BDAProcessPurchaseOrder.confirmShipment(env, eResults, false);
			confirmShipment(env, eResults);
		} catch (YFSException ex){
			eResults.setAttribute("Error", ex.getMessage());
		} catch (RemoteException ex){
			eResults.setAttribute("Error", "Cannot connect");
		} catch (YIFClientCreationException ex){
			eResults.setAttribute("Error", "Unable to Create Client");
		}		
		return output.getDocument();
	}

	private YFCDocument getOrderDetails(YFSEnvironment env, Document inputDoc) throws YFSException, RemoteException, YIFClientCreationException {
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("getOrderDetails", CompleteOrder.getOrderDetailsTemplate());
		Document response = localApi.invoke(env, "getOrderDetails", inputDoc);
		return YFCDocument.getDocumentFor(response);
	}
	
	private YFCDocument getOrderDetails(YFSEnvironment env, String sOrderHeaderKey) throws YFSException, RemoteException, YIFClientCreationException {
		YFCDocument inputDoc = YFCDocument.createDocument("Order");
		inputDoc.getDocumentElement().setAttribute("OrderHeaderKey", sOrderHeaderKey);
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("getOrderDetails", CompleteOrder.getOrderDetailsTemplate());
		Document response = localApi.invoke(env, "getOrderDetails", inputDoc.getDocument());
		return YFCDocument.getDocumentFor(response);
	}
	
	private void confirmShipment(YFSEnvironment env, YFCElement eResults) throws YIFClientCreationException, YFSException, RemoteException{
		for(YFCElement eShip : eResults.getChildElement("Shipments", true).getChildren()){
			if(!eShip.getBooleanAttribute("Confirm", false)){
				YFCDocument dShipment = YFCDocument.createDocument("Shipment");
				YFCElement eShipment = dShipment.getDocumentElement();
				eShipment.setAttribute("ShipmentKey", eShip.getAttribute("ShipmentKey"));
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				localApi.invoke(env, "confirmShipment", dShipment.getDocument());
				eShip.setAttribute("Confirm", true);
			}
			
		}
	}
	private YFCDocument authorizeReturn(YFSEnvironment env, YFCElement eResults) throws YFSException, RemoteException, YIFClientCreationException{
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		try {
			YFCDocument dInput = YFCDocument.createDocument("OrderStatusChange");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("ChangeForAllAvailableQty", "Y");
			eInput.setAttribute("BaseDropStatus", "1100.01");
			eInput.setAttribute("OrderHeaderKey", eResults.getAttribute("OrderHeaderKey"));
			eInput.setAttribute("TransactionId", "AUTHORIZE_RETURN.0003");
			eInput.setAttribute("IgnoreTransactionDependencies", "Y");
			localApi.changeOrderStatus(env, dInput.getDocument());
			eResults.setAttribute("AuthorizedReturn", "Y");
		} catch (Exception e){
			eResults.setAttribute("AuthorizedReturn", "N");
		}
		return getOrderDetails(env, eResults.getAttribute("OrderHeaderKey"));
	}
	
	public YFCDocument scheduleOrder(YFSEnvironment env, YFCElement eResults) throws YFSException, RemoteException, YIFClientCreationException{
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		
		YFCDocument getOrderDetailsInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = getOrderDetailsInput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", eResults.getAttribute("OrderHeaderKey"));
		try {
			localApi.invoke(env, "scheduleOrder", getOrderDetailsInput.getDocument());
			eResults.setAttribute("Scheduled", "Y");
		} catch(Exception yex) {
			eResults.setAttribute("Scheduled", "N");
        } 
		return getOrderDetails(env, getOrderDetailsInput.getDocument());
	}
	
}
