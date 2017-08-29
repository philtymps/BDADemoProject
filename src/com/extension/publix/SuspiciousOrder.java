package com.extension.publix;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.shared.ycd.YCDConstants;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNode;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class SuspiciousOrder implements YIFCustomApi {

	private Properties props; // holds the values passed as arguments from the configurator
    private static YFCLogCategory cat = YFCLogCategory.instance(SuspiciousOrder.class.getName());

    public void setProperties(Properties prop) throws Exception {
        props = prop;
    }

    private ArrayList<String> aSteak;
	private ArrayList<String> aAlcohol;

	
	private ArrayList<String> getSteak(){
		if(YFCCommon.isVoid(aSteak)){
			aSteak = new ArrayList<String>();
			YFCDocument dA = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/Steak.xml");
			if(!YFCCommon.isVoid(dA)){
				YFCElement eItems = dA.getDocumentElement();
				for(YFCElement eItem : eItems.getChildren()){
					aSteak.add(eItem.getAttribute("ItemID"));
				}
			}
		}
		return aSteak;
	}
	
	private ArrayList<String> getAlcohol(){
		if(YFCCommon.isVoid(aAlcohol)){
			aAlcohol = new ArrayList<String>();
			YFCDocument dA = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/Alcohol.xml");
			if(!YFCCommon.isVoid(dA)){
				YFCElement eItems = dA.getDocumentElement();
				for(YFCElement eItem : eItems.getChildren()){
					aAlcohol.add(eItem.getAttribute("ItemID"));
				}
			}
		}
		return aAlcohol;
	}
	
	
	
	public boolean evaluateCondition(YFSEnvironment env, YFCDocument dInput) {
		YFCElement eInput = dInput.getDocumentElement();
		double steak = 0;
		double alcohol = 0;
	
		for(YFCElement eOrderLine : eInput.getChildElement("OrderLines").getChildren()){
			YFCElement eItem = eOrderLine.getChildElement("Item", true);
			
			if(getAlcohol().contains(eItem.getAttribute("ItemID"))){
				alcohol += eOrderLine.getChildElement("ComputedPrice").getDoubleAttribute("ExtendedPrice");
			}
			if(getSteak().contains(eItem.getAttribute("ItemID"))){
				steak += eOrderLine.getChildElement("ComputedPrice").getDoubleAttribute("ExtendedPrice");
			}
		}
		if(steak + alcohol > 100){
			return true;
		}
		return false;
	}
	
	private Document getOrderDetailsTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOutput.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("OrderNo", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("OrderLineKey", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		YFCElement eLinePriceInfo = eOrderLine.createChild("ComputedPrice");
		eLinePriceInfo.setAttribute("ExtendedPrice", "");
		return dOutput.getDocument();
	}
	
    public Document invoke(YFSEnvironment env, Document inDoc) {
        YFCDocument orderDoc = YFCDocument.getDocumentFor(inDoc);
        YFCElement holdTypesToProcess = orderDoc.getDocumentElement().getChildElement("HoldTypesToProcess");
        YIFApi localApi;
        Document dOrderOutput = null;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			Document dAppointmentOptions = null;
			env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
			dOrderOutput = localApi.invoke(env, "getOrderDetails", inDoc);
		} catch (YIFClientCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (YFSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String status = YCDConstants.YCP_HOLD_TYPE_RESOLVED;
		if(!YFCCommon.isVoid(dOrderOutput)){
			 if (evaluateCondition(env, YFCDocument.getDocumentFor(dOrderOutput))) {
	            //Order is a potential duplicate, set the hold status to rejected
	            status = YCDConstants.YCP_HOLD_TYPE_REJECTED;
	        }
		}
       
       
        YFCDocument outDoc = YFCDocument.createDocument();
        YFCNode root = outDoc.importNode(orderDoc.getDocumentElement(),true);
        outDoc.appendChild(root);
        YFCElement order = outDoc.getDocumentElement();
        YFCElement processedHoldTypes = order.createChild("ProcessedHoldTypes");
        YFCElement processedHold;
        for (YFCElement holdToProcess : holdTypesToProcess.getChildren()) {
            processedHold = processedHoldTypes.createChild("OrderHoldType");
            processedHold.setAttribute("HoldType",holdToProcess.getAttribute("HoldType"));
            processedHold.setAttribute("ReasonText",holdToProcess.getAttribute("ReasonText"));
            processedHold.setAttribute("Status",status);
        }
        order.removeChild(order.getChildElement("HoldTypesToProcess"));
        return outDoc.getDocument();
    }

}
