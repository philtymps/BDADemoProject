package com.extension.sci.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.sci.object.SCISalesOrder;
import com.extension.sci.util.BDAPushToSCI;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASendOrderToSCI extends BDAPushToSCI {
	
	public static void main(String[] args){
		BDASendOrderToSCI order = new BDASendOrderToSCI();
		YFCDocument input = YFCDocument.getDocumentForXMLFile("/Users/pfaiola/Desktop/salesOrder.xml");
		order.invoke(null, input.getDocument());
	}

	@Override
	public String getServiceName() {
		return "sendOrderToSCI";
	}

	
	@Override
	public Document invoke(YFSEnvironment env, YFCElement eOrder) {
		
		JSONObject bulk = new JSONObject();
		bulk.put("allOrNothing", false);
		JSONArray upserts = new JSONArray();
		SCISalesOrder sciso = new SCISalesOrder(eOrder);
		upserts.put(sciso.getBulkObject());
		bulk.put("upserts", upserts);
		
		this.callRequest(SCI_BULK_SALES_ORDER, bulk);
		return null;
	}

}
