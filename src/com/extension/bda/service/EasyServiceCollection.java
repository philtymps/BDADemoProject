package com.extension.bda.service;

import java.util.HashMap;

import com.extension.bda.service.expose.BDAEntityApi;
import com.extension.bda.service.giv.BDAInventoryShortAlert;
import com.extension.bda.service.promotions.BDAGetPreviouslyViewedItems;
import com.yantra.yfc.util.YFCCommon;

public class EasyServiceCollection {

	private static EasyServiceCollection collection;
	
	HashMap<String, IBDAService> map;
	
	public EasyServiceCollection() {
		map = new HashMap<String, IBDAService>();
		map.put("callEntityApi", new BDAEntityApi());
		map.put("getPreviouslyViewedItems", new BDAGetPreviouslyViewedItems());
		map.put("shortInventory", new BDAInventoryShortAlert());
	}
	
	public IBDAService getService(String sServiceName){
		return map.get(sServiceName);
	}
	
	public static EasyServiceCollection getInstance() {
		if(YFCCommon.isVoid(collection)){
			collection = new EasyServiceCollection();
		}
		return collection;
	}
}
