package com.ue;

import org.w3c.dom.Document;

import com.yantra.ydm.japi.ue.YDMDetermineShipmentToConsolidateWith;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

public class DetermineConsolidation implements
		YDMDetermineShipmentToConsolidateWith {

	@Override
	public Document determineShipmentToConsolidateWith(YFSEnvironment env,
			Document input) throws YFSUserExitException {
		YFCDocument dDetermine = YFCDocument.getDocumentFor(input);
		YFCElement eShipment = dDetermine.getDocumentElement().getChildElement("Shipment");
		YFCDocument dOutput = YFCDocument.createDocument("Shipment");
		if (!YFCCommon.isVoid(eShipment.getAttribute("ShipNode"))){
			String sShipNode = eShipment.getAttribute("ShipNode");
			if(!sShipNode.equals("Aurora_WH3")){
				String sToAddress = null;
				if(!YFCCommon.isVoid(eShipment.getAttribute("ToAddressKey"))){
					sToAddress = eShipment.getAttribute("ToAddressKey");
				}
				
				YFCElement eShipments = dDetermine.getDocumentElement().getChildElement("Shipments", true);
				for (YFCElement eExistingShipment : eShipments.getChildren()){
					if(YFCCommon.equals(eExistingShipment.getAttribute("ShipNode"), sShipNode)){
						if(!YFCCommon.isVoid(eExistingShipment.getAttribute("ToAddressKey")) && YFCCommon.equals(eExistingShipment.getAttribute("ToAddressKey"), sToAddress)){
							dOutput.getDocumentElement().setAttribute("ShipmentKey", eExistingShipment.getAttribute("ShipmentKey"));
							break;
						} else if(YFCCommon.isVoid(eExistingShipment.getAttribute("ToAddressKey"))){
							dOutput.getDocumentElement().setAttribute("ShipmentKey", eExistingShipment.getAttribute("ShipmentKey"));
							break;
						}
					}
				}
				
			}
			
		}
		if(YFCCommon.isVoid(dOutput.getDocumentElement().getAttribute("ShipmentKey"))){
			dOutput.getDocumentElement().setAttribute("CreateNewShipment", "Y");
		}
		return dOutput.getDocument();
	}

}
