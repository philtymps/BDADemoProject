package com.extension.bda.service.shipments;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASetCarrierOnShipmentIfRequired implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "setCarrierOnShipment";
	}

	@Override
	public void setProperties(Properties props) {
	
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		YFCDocument dTemplate = YFCDocument.createDocument("Shipment");
		YFCElement eTemplate = dTemplate.getDocumentElement();
		eTemplate.setAttribute("ShipmentKey", "");
		eTemplate.setAttribute("DeliveryMethod", "");
		eTemplate.setAttribute("SCAC", "");
		
		YFCDocument dInput = YFCDocument.createDocument("Shipment");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("ShipmentKey", input.getDocumentElement().getAttribute("ShipmentKey"));
		
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			env.setApiTemplate("getShipmentDetails", dTemplate.getDocument());
			l_OutputXml = localApi.invoke(env, "getShipmentDetails", dInput.getDocument());
			if(YFCCommon.isVoid(l_OutputXml.getDocumentElement().getAttribute("SCAC")) && YFCCommon.equals(l_OutputXml.getDocumentElement().getAttribute("DeliveryMethod"), "SHP")){
				l_OutputXml.getDocumentElement().setAttribute("SCAC", "Y_ANY");
				localApi.invoke(env, "changeShipment", l_OutputXml);
			}
		} catch(Exception yex) {
        	System.out.println("The error thrown was: " );    
        	System.out.println(yex.toString());
            yex.printStackTrace();
        } 
		return input;
		
	}

}
