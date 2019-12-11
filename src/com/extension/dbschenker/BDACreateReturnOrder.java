package com.extension.dbschenker;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDACreateReturnOrder extends BDAServiceApi implements IBDAService {

	private Properties p;
	
	public Document invoke(YFSEnvironment env, Document input){
		boolean hasLines = false;
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		
		YFCDocument dRentalOrder = YFCDocument.createDocument("Order");
		YFCElement eRental = dRentalOrder.getDocumentElement();
		eRental.setAttribute("DocumentType", getDocumentType());
		eRental.setAttribute("EnterpriseCode", eInput.getAttribute("EnterpriseCode"));
		eRental.setAttribute("BillToKey", eInput.getAttribute("ToAddressKey"));
		for(YFCElement eShipmentLine : eInput.getChildElement("ShipmentLines", true).getChildren()){
			if(BDAContainsRental.isRentalItem(env, eShipmentLine.getAttribute("ItemID"))){
				createRentalLine(eRental, eShipmentLine, eInput.getAttribute("ShipNode"));
				hasLines = true;
			}
		}
		if(hasLines){
			return callApi(env, dRentalOrder.getDocument(), null, "createOrder");
		}
		return input;
	}
	
	private void createRentalLine(YFCElement eRental, YFCElement eShipmentLine, String sShipNode){
		YFCElement eRentalLine = eRental.getChildElement("OrderLines", true).createChild("OrderLine");
		eRentalLine.setAttribute("OrderedQty", eShipmentLine.getAttribute("Quantity"));
		eRentalLine.setAttribute("ShipNode", sShipNode);
		YFCElement eDerivedLine = eRentalLine.createChild("DerivedFrom");
		eDerivedLine.setAttribute("OrderLineKey", eShipmentLine.getAttribute("OrderLineKey"));
		
	}

	private String getDocumentType(){
		if(!YFCCommon.isVoid(p) && p.containsKey("DocumentType")){
			return p.getProperty("DocumentType");
		}
		return "0033.ex";
	}
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "autoCreateReturn";
	}

	@Override
	public void setProperties(Properties props) {
		p = props;
	}
}
