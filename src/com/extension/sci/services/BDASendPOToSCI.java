package com.extension.sci.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.sci.object.SCIPurchaseOrder;
import com.extension.sci.util.BDAPushToSCI;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASendPOToSCI extends BDAPushToSCI {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "sendPOToSCI";
	}

	@Override
	public Document invoke(YFSEnvironment env, YFCElement eOrder) {

		JSONObject bulk = new JSONObject();
		bulk.put("allOrNothing", false);
		JSONArray upserts = new JSONArray();
		SCIPurchaseOrder sciso = new SCIPurchaseOrder(eOrder);
		upserts.put(sciso.getBulkObject());
		bulk.put("upserts", upserts);
		
		this.callRequest(SCI_BULK_PURCHASE_ORDER, bulk);
		return null;
	}

}
