package com.extension.sci.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.sci.object.SCISalesShipment;
import com.extension.sci.util.BDAPushToSCI;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASendPurchaseShipmentToSCI extends BDAPushToSCI {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "sendPurchaseShipmentToSCI";
	}

	@Override
	public Document invoke(YFSEnvironment env, YFCElement eShipment) {
		JSONObject bulk = new JSONObject();
		bulk.put("allOrNothing", false);
		JSONArray upserts = new JSONArray();
		SCISalesShipment sciso = new SCISalesShipment(eShipment);
		upserts.put(sciso.getBulkObject());
		bulk.put("upserts", upserts);
		
		this.callRequest(SCI_BULK_PURCHASE_SHIPMENT, bulk);
		return null;
	}

}
