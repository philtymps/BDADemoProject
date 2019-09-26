package com.extension.bda.service.iv;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfs.japi.YFSEnvironment;

public class GetIVDetails extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getIVDetails";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		
		return null;
	}

}
