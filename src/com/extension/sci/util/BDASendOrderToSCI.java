package com.extension.sci.util;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASendOrderToSCI implements IBDAService {

	@Override
	public String getServiceName() {
		return "sendOrderToSCI";
	}

	@Override
	public void setProperties(Properties props) {
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		
		return null;
	}

}
