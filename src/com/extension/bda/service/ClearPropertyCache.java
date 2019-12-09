package com.extension.bda.service;

import org.w3c.dom.Document;

import com.extension.bda.inventory.BDAAdjustInventory;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfs.japi.YFSEnvironment;

public class ClearPropertyCache extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "clearPropertyCache";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		AddTenantToIV.clearMap();
		BDAAdjustInventory.clearMap();
		
		return input;
	}

}
