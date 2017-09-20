package com.extension.gucci;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfs.japi.YFSEnvironment;

public class GucciGetLinesToStealFrom implements IBDAService {

	private GucciStealInventory gsi;
	public GucciGetLinesToStealFrom(){
		gsi = new GucciStealInventory();
	}
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getOrdersToStealFrom";
	}

	@Override
	public void setProperties(Properties props) {
		gsi.setProperties(props);
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		return gsi.getOrdersToStealFrom(env, input);
	}

}
