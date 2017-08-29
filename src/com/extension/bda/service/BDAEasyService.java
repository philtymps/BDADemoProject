package com.extension.bda.service;

import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAEasyService {
	public Document callEasyService(YFSEnvironment env, Document input){
		
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		
		if(eInput.getNodeName().equals("Service")){
			if (!YFCCommon.isVoid(eInput.getAttribute("ServiceName"))){
				String sServiceName = eInput.getAttribute("ServiceName");
				IBDAService service = EasyServiceCollection.getInstance().getService(sServiceName);
				Document serviceInput = null;
				if(!YFCCommon.isVoid(eInput.getChildElement("Input"))){
					YFCElement eServiceInput = eInput.getChildElement("Input");
					if(!YFCCommon.isVoid(eServiceInput.getFirstChildElement())){
						YFCElement eActualInput = eServiceInput.getFirstChildElement();
						YFCDocument dServiceInput = YFCDocument.getDocumentFor(eActualInput.getString());
						serviceInput = dServiceInput.getDocument();
					}
				}
				if(!YFCCommon.isVoid(eInput.getChildElement("Properties"))){
					Properties p = new Properties();
					for(YFCElement eProp : eInput.getChildElement("Properties").getChildren()){
						p.setProperty(eProp.getAttribute("Name"), eProp.getAttribute("Value"));
					}
					service.setProperties(p);
				}
				
				if(!YFCCommon.isVoid(service)){
					return service.invoke(env, serviceInput);
				} else {
					YFCDocument output = YFCDocument.createDocument("Error");
					YFCElement eOutput = output.getDocumentElement();
					eOutput.setAttribute("Message", "Service " + sServiceName + " not defined in Service Collection");
					return output.getDocument();
				}
			} 
		}
		YFCDocument output = YFCDocument.createDocument("Error");
		YFCElement eOutput = output.getDocumentElement();
		eOutput.setAttribute("Message", "Service input is invalid, please make sure you provided input with a ServiceName");
		return output.getDocument();
		
	}
}
