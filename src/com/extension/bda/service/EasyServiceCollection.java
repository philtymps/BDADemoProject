package com.extension.bda.service;

import java.util.HashMap;

import com.extension.afg.BDARepriceOrderFromLine;
import com.extension.bda.service.ecomm.BDAGetEcommConfig;
import com.extension.bda.service.expose.BDADataProvider;
import com.extension.bda.service.expose.BDAEntityApi;
import com.extension.bda.service.expose.BDARestCall;
import com.extension.bda.service.expose.BDAWriteXMLToLog;
import com.extension.bda.service.fulfillment.BDADecomposeDates;
import com.extension.bda.service.fulfillment.BDAHoldsAndPayment;
import com.extension.bda.service.fulfillment.BDASetRequestedDeliveryFromClassification;
import com.extension.bda.service.giv.BDAGetDemandOrders;
import com.extension.bda.service.giv.BDAGetMyAlerts;
import com.extension.bda.service.giv.BDAInventoryShortAlert;
import com.extension.bda.service.giv.BDAProcessPurchaseOrder;
import com.extension.bda.service.giv.BDAProcessReturnOrder;
import com.extension.bda.service.iv.BDAGetDemandDetails;
import com.extension.bda.service.iv.BDAGetSupplyDetails;
import com.extension.bda.service.promotions.BDAGetPreviouslyViewedItems;
import com.extension.bda.service.returns.BDACreateReturnTransfer;
import com.extension.bda.service.shipments.BDARemoveInventoryOnBackorder;
import com.extension.bda.service.shipments.BDASetCarrierOnShipmentIfRequired;
import com.extension.bda.service.store.BDAGetNodeCapacityDetails;
import com.extension.bda.service.store.BDAStoreCustomerProfile;
import com.extension.bda.userexits.BDADuplicateOrderImpl;
import com.extension.gucci.GucciGetLinesToStealFrom;
import com.extension.gucci.GucciStealInventory;
import com.extension.silverpop.SilverpopEmailSegment;
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
		map.put("processReturnOrder", new BDAProcessReturnOrder());
		map.put("getDemandOrders", new BDAGetDemandOrders());
		map.put("completeOrder", new CompleteOrder());
		map.put("CompleteOrder", new CompleteOrder());
		map.put("stealInventoryFromLine", new GucciStealInventory());
		map.put("getOrdersToStealFrom", new GucciGetLinesToStealFrom());
		map.put("resolveHoldsAndPayment", new BDAHoldsAndPayment());
		map.put("setCarrierOnShipment", new BDASetCarrierOnShipmentIfRequired());
		map.put("silverpopEmailSegment", new SilverpopEmailSegment());
		map.put("repriceOrderFromLine", new BDARepriceOrderFromLine());
		map.put("getMyAlerts", new BDAGetMyAlerts());
		map.put("createReturnTransfer", new BDACreateReturnTransfer());
		map.put("getNodeCapacityDetails", new BDAGetNodeCapacityDetails());
		map.put("bdaDataProvider", new BDADataProvider());
		map.put("getDuplicateOrders", new BDADuplicateOrderImpl());
		map.put("removeInventoryOnBackorder", new BDARemoveInventoryOnBackorder());
		map.put("getRecommendedProducts", new BDAStoreCustomerProfile());
		map.put("setRequestedDeliveryDateFromClassification", new BDASetRequestedDeliveryFromClassification());
		map.put("getECommConfig", new BDAGetEcommConfig());
		map.put("decomposeAvailability", new BDADecomposeDates());
		map.put("callRestService", new BDARestCall());
		map.put("writeXMLToLog", new BDAWriteXMLToLog());
		map.put("getSupplyDetails", new BDAGetSupplyDetails());
		map.put("getDemandDetails", new BDAGetDemandDetails());
		map.put("clearPropertyCache", new ClearPropertyCache());
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
