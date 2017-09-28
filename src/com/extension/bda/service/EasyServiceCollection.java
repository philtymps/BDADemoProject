package com.extension.bda.service;

import java.util.HashMap;

import com.extension.bda.service.expose.BDAEntityApi;
import com.extension.bda.service.fulfillment.BDAHoldsAndPayment;
import com.extension.bda.service.giv.BDAGetDemandOrders;
import com.extension.bda.service.giv.BDAInventoryShortAlert;
import com.extension.bda.service.giv.BDAProcessPurchaseOrder;
import com.extension.bda.service.promotions.BDAGetPreviouslyViewedItems;
import com.extension.bda.service.shipments.BDASetCarrierOnShipmentIfRequired;
import com.extension.gucci.GucciGetLinesToStealFrom;
import com.extension.gucci.GucciStealInventory;
import com.scripts.CompleteOrder;
import com.yantra.yfc.util.YFCCommon;

public class EasyServiceCollection {

	private static EasyServiceCollection collection;
	
	HashMap<String, IBDAService> map;
	
	public EasyServiceCollection() {
		map = new HashMap<String, IBDAService>();
		map.put("callEntityApi", new BDAEntityApi());
		map.put("getPreviouslyViewedItems", new BDAGetPreviouslyViewedItems());
		map.put("shortInventory", new BDAInventoryShortAlert());
		map.put("processPurchaseOrder", new BDAProcessPurchaseOrder());
		map.put("getDemandOrders", new BDAGetDemandOrders());
		map.put("completeOrder", new CompleteOrder());
		map.put("stealInventoryFromLine", new GucciStealInventory());
		map.put("getOrdersToStealFrom", new GucciGetLinesToStealFrom());
		map.put("resolveHoldsAndPayment", new BDAHoldsAndPayment());
		map.put("setCarrierOnShipment", new BDASetCarrierOnShipmentIfRequired());
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
