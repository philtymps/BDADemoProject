package com.extension.sci.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.sci.object.SCILocation;
import com.extension.sci.object.SCISalesShipment;
import com.extension.sci.util.BDAPushToSCI;
import com.ibm.CallInteropServlet;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAProcessor extends BDAPushToSCI {

	public static void main(String[] args) {
		BDAProcessor bp = new BDAProcessor();
		bp.processLocations();
	}
	
	public void processLocations() {
		
		JSONObject bulk = new JSONObject();
		bulk.put("allOrNothing", false);
		JSONArray upserts = new JSONArray();
		bulk.put("upserts", upserts);
		
		YFCDocument dInput = YFCDocument.createDocument("Organization");
		YFCElement eOrg = dInput.getDocumentElement();
		eOrg.setAttribute("InventoryOrganizationCode", "Aurora");
		eOrg.setAttribute("IsNode", "Y");
		try {
    		YFCDocument dResponse = CallInteropServlet.invokeApi(dInput, SCILocation.getTemplateForOMS(), "getOrganizationList", "http://pat.omfulfillment.com:9080");
    		YFCElement eOrgList = dResponse.getDocumentElement();
    		for(YFCElement eNode : eOrgList.getChildren()) {
    			SCILocation l = new SCILocation(eNode);
    			upserts.put(l.getBulkObject());
    		}
    	} catch (Exception e){
    		e.printStackTrace();
    	}
		
		if(upserts.length() > 0) {
			this.callRequest(SCI_BULK_LOCATIONS, bulk);
		}

	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document invoke(YFSEnvironment env, YFCElement eInput) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
