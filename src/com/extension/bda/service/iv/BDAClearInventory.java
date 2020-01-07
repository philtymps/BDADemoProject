package com.extension.bda.service.iv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bda.utils.BDACommon;
import com.bda.utils.BDAXmlUtil;
import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.extension.bda.service.iv.BDAGetSupplyDetails.GetSupplyDetailAsync;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

import edu.emory.mathcs.backport.java.util.Arrays;

public class BDAClearInventory extends BDAServiceApi implements IBDAService {
	private static String[] stores = {"tmobile_S1", "tmobile_S2", "tmobile_S3", "tmobile_S4", "tmobile_S5", "tmobile_S6", "tmobile_S7", "tmobile_S8"};
	private static String[] items = {"TABLETSA774_0003_0" };
	private static List<String> _nodes;
	private static boolean running = false;
	public static synchronized boolean isRunning() {
		return running;
	}
	public static synchronized void setRunning(boolean value) {
		running = value;
	}
	
	class GetDetailAsync {
		protected String itemID;
		protected String shipNode;
		protected JSONArray array;
		protected boolean adjust;
		protected Document output;
		
		public GetDetailAsync(String sItemID, String sShipNode, boolean adjust) {
			this.itemID = sItemID;
			this.shipNode = sShipNode;
			this.array = new JSONArray();
			this.adjust = adjust;
			
		}
		
		public JSONArray getAdjustmentArray() {
			return array;
		}
		
		public Element getAdjustmentElements() {
			return output.getDocumentElement();
		}
		
	}
	
	class GetSupplyDetailAsync extends GetDetailAsync implements Runnable {

	
		public GetSupplyDetailAsync(String sItemID, String sShipNode, boolean adjust) {
			super(sItemID, sShipNode, adjust);
			output = BDAXmlUtil.createDocument("Supply");
			output.getDocumentElement().setAttribute("ItemID", sItemID);
			output.getDocumentElement().setAttribute("sShipNode", sShipNode);
		}
		
		@Override
		public void run() {
			Document dSupplies = BDAServiceApi.callService(null, getSupplyForNode(itemID, "EACH", shipNode), null, "BDACallIVService");
			try {
				createSupplyRecord(dSupplies, array, output.getDocumentElement(), adjust);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	class GetDemandDetailAsync extends GetDetailAsync implements Runnable {
		
		public GetDemandDetailAsync(String sItemID, String sShipNode, boolean adjust) {
			super(sItemID, sShipNode, adjust);
			output = BDAXmlUtil.createDocument("Demand");
			output.getDocumentElement().setAttribute("ItemID", sItemID);
			output.getDocumentElement().setAttribute("sShipNode", sShipNode);
		}
		
		@Override
		public void run() {
			Document dDemands = BDAServiceApi.callService(null, getDemandForNode(itemID, "EACH", shipNode), null, "BDACallIVService");
			try {
				createDemandRecord(dDemands, array, output.getDocumentElement(), adjust);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws Exception {
		BASE_URL = "https://oms.innovationcloud.info";
		BDAClearInventory bdaci = new BDAClearInventory();
		System.out.println(BDAXmlUtil.getString(bdaci.invoke(null, null)));
	}
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		Document dResponse = BDAXmlUtil.createDocument("Response");
		if(isRunning()) {
			dResponse.getDocumentElement().setAttribute("Message", "Already running!");
		} else {
			setRunning(true);
			Element eSupplies = BDAXmlUtil.createChild(dResponse.getDocumentElement(), "Supplies");
			Element eDemands = BDAXmlUtil.createChild(dResponse.getDocumentElement(), "Demands");	
			
			ExecutorService pool = Executors.newFixedThreadPool(20);
			
			ArrayList<GetSupplyDetailAsync> supplyThreads = new ArrayList<GetSupplyDetailAsync>();
			ArrayList<GetDemandDetailAsync> demandThreads = new ArrayList<GetDemandDetailAsync>();
			
			for(String p : getVariableItems(env)) {
				for(String sNode : getShipNodes(env)) {
					GetSupplyDetailAsync supplyRunnable = new GetSupplyDetailAsync(p, sNode, true);
					supplyThreads.add(supplyRunnable);
					pool.execute(supplyRunnable);
					GetDemandDetailAsync demandRunnable = new GetDemandDetailAsync(p, sNode, true);
					demandThreads.add(demandRunnable);
					pool.execute(demandRunnable);
				}
			}
			pool.shutdown();
			pool.awaitTermination(1, TimeUnit.MINUTES);
			
			JSONObject supplyObj = new JSONObject();
			JSONArray array = new JSONArray();
			supplyObj.put("supplies", array);
			for(GetSupplyDetailAsync supply : supplyThreads) {
				if(array.size() >= 200) {
					
					pushSupplyUpdate(env, supplyObj, true);
					array = new JSONArray();
					
					supplyObj.put("supplies", array);
				}
				if(supply.getAdjustmentArray().size() > 0) {
					array.addAll(supply.getAdjustmentArray());
					BDAXmlUtil.importElement(eSupplies, supply.getAdjustmentElements());
				}
			}
			try {
				if(array.size() >= 0) {
					pushSupplyUpdate(env, supplyObj, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONObject demandObj = new JSONObject();
			array = new JSONArray();
			demandObj.put("demands", array);
			for(GetDemandDetailAsync demand : demandThreads) {
				if(array.size() >= 200) {
					
					pushDemandUpdate(env, demandObj, true);
					array = new JSONArray();
					
					demandObj.put("demands", array);
				}
				if(demand.getAdjustmentArray().size() > 0) {
					array.addAll(demand.getAdjustmentArray());
					BDAXmlUtil.importElement(eDemands, demand.getAdjustmentElements());
				}
			}
			try {
				if(array.size() >= 0) {
					pushDemandUpdate(env, demandObj, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			setRunning(false);
		}
		
		return dResponse;

	}

	
	private static String getVariableFile(YFSEnvironment env){
		return BDAServiceApi.getScriptsPath(env) + "/variables.xml";
	}
	
	private static HashMap<String, String> replaceVariables(YFSEnvironment env, YFCDocument dFileInput){
		HashMap<String, String> variable = new HashMap<String, String>();
		YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
		for (YFCElement eChild : temp.getDocumentElement().getChildren()){
			if(YFCCommon.equals(eChild.getAttribute("TYPE"), "ITEM")) {
				variable.put(eChild.getAttribute("Name"), eChild.getAttribute("Value"));
			}			
		}
		return variable;
	}
	
	private static Collection<String> getVariableItems(YFSEnvironment env) {
		try {
			YFCDocument dVariables = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
			if(!YFCCommon.isVoid(dVariables)){
				HashMap<String, String> vars = replaceVariables(env, dVariables);
				return vars.values();
			}
		} catch (Exception e) {
			
		}

		List<String> list = Arrays.asList(items);
		return list;
	}
			
	private synchronized static Collection<String> getShipNodes(YFSEnvironment env) {
		if(YFCCommon.isVoid(_nodes)) {
			try {
				_nodes = new ArrayList<String>();
				YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
				for (YFCElement eChild : temp.getDocumentElement().getChildren()){
					if(YFCCommon.equals(eChild.getAttribute("TYPE"), "NODE")) {
						_nodes.add(eChild.getAttribute("Value"));
					}			
				}
			} catch (Exception e) {
				
			}
		}
		if(_nodes.size() == 0) {
			_nodes = Arrays.asList(stores);
		}
		
		return _nodes;
	}

	
	private static void createSupplyRecord(Document dResponse,  JSONArray array, Element eResponse, boolean adjust) throws Exception {
		for(Element eSupply : BDAXmlUtil.getChildrenList(dResponse.getDocumentElement())) {
			if(BDAXmlUtil.getDoubleAttribute(eSupply, "quantity") != 0) {
				System.out.println(BDAXmlUtil.getString(eSupply));
				JSONObject obj = new JSONObject();
				obj.put("itemId", eSupply.getAttribute("itemId"));
				obj.put("unitOfMeasure", eSupply.getAttribute("unitOfMeasure"));
				obj.put("shipNode", eSupply.getAttribute("shipNode"));
				obj.put("type", eSupply.getAttribute("type"));
				if(adjust) {
					obj.put("changedQuantity", -(BDAXmlUtil.getDoubleAttribute(eSupply, "quantity")));
				} else {
					obj.put("quantity", 0);
				}
				obj.put("eta", eSupply.getAttribute("eta"));
				obj.put("shipByDate", eSupply.getAttribute("shipByDate"));
				if(!BDACommon.isVoid(eSupply.getAttribute("segment")))
					obj.put("segment", eSupply.getAttribute("segment"));
				if(!BDACommon.isVoid(eSupply.getAttribute("segmentType")))	
					obj.put("segmentType", eSupply.getAttribute("segmentType"));
				if(!BDACommon.isVoid(eSupply.getAttribute("tagNumber")))
					obj.put("tagNumber",eSupply.getAttribute("tagNumber"));
				if(!BDACommon.isVoid(eSupply.getAttribute("productClass")))
					obj.put("productClass", eSupply.getAttribute("productClass"));
				if(!BDACommon.isVoid(eSupply.getAttribute("reference")))
					obj.put("reference", eSupply.getAttribute("reference"));
				if(!BDACommon.isVoid(eSupply.getAttribute("referenceType")))
					obj.put("referenceType", eSupply.getAttribute("referenceType"));
				if(!BDACommon.isVoid(eSupply.getAttribute("lineReference"))) {
					obj.put("lineReference", eSupply.getAttribute("lineReference"));
				}
				TimeZone tz = TimeZone.getTimeZone("UTC");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
				df.setTimeZone(tz);
				String nowAsISO = df.format(new Date());
				obj.put("sourceTs", nowAsISO);
				array.add(obj);
				System.out.println(obj.toString());
				
				Element eSup = BDAXmlUtil.createChild(eResponse, "Supply");
				eSup.setAttribute("Type", eSupply.getAttribute("type"));
				BDAXmlUtil.setAttribute(eSup, "changedQuantity", -(BDAXmlUtil.getDoubleAttribute(eSupply, "quantity")));
			}
	
		}
	}
	
	private static void createDemandRecord(Document dResponse, JSONArray array, Element eDemands, boolean adjust) throws Exception {
		Element eResponse = dResponse.getDocumentElement();

		for(Element eSupply : BDAXmlUtil.getChildrenList(dResponse.getDocumentElement())) {
			if(BDAXmlUtil.getDoubleAttribute(eSupply, "quantity") != 0) {
				JSONObject obj = new JSONObject();
				obj.put("itemId", eSupply.getAttribute("itemId"));
				obj.put("unitOfMeasure", eSupply.getAttribute("unitOfMeasure"));
				obj.put("shipNode", eSupply.getAttribute("shipNode"));
				obj.put("type", eSupply.getAttribute("type"));
				if(adjust) {
					obj.put("changedQuantity", -(BDAXmlUtil.getDoubleAttribute(eSupply, "quantity")));
				} else {
					obj.put("quantity", 0);
				}
				
				obj.put("cancelDate", eSupply.getAttribute("cancelDate"));
				obj.put("shipDate", eSupply.getAttribute("shipDate"));
				obj.put("minShipByDate", eSupply.getAttribute("minShipByDate"));
				if(!BDACommon.isVoid(eSupply.getAttribute("segment")))
					obj.put("segment", eSupply.getAttribute("segment"));
				if(!BDACommon.isVoid(eSupply.getAttribute("segmentType")))	
					obj.put("segmentType", eSupply.getAttribute("segmentType"));
				if(!BDACommon.isVoid(eSupply.getAttribute("tagNumber")))
					obj.put("tagNumber",eSupply.getAttribute("tagNumber"));
				if(!BDACommon.isVoid(eSupply.getAttribute("productClass")))
					obj.put("productClass", eSupply.getAttribute("productClass"));
				if(!BDACommon.isVoid(eSupply.getAttribute("reference")))
					obj.put("reference", eSupply.getAttribute("reference"));
				if(!BDACommon.isVoid(eSupply.getAttribute("referenceType")))
					obj.put("referenceType", eSupply.getAttribute("referenceType"));
				if(!BDACommon.isVoid(eSupply.getAttribute("lineReference"))) {
					obj.put("lineReference", eSupply.getAttribute("lineReference"));
				}
				TimeZone tz = TimeZone.getTimeZone("UTC");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
				df.setTimeZone(tz);
				String nowAsISO = df.format(new Date());
				obj.put("sourceTs", nowAsISO);
				array.add(obj);
				
				Element eSup = BDAXmlUtil.createChild(eDemands, "Demand");
				eSup.setAttribute("itemId", eSupply.getAttribute("itemId"));
				eSup.setAttribute("shipNode", eSupply.getAttribute("shipNode"));
				BDAXmlUtil.setAttribute(eSup, "changedQuantity", -(BDAXmlUtil.getDoubleAttribute(eSupply, "quantity")));
			}
	
		}
		
	}
	
	private static Document callForNodes(YFSEnvironment env) { 
		Document dInput = BDAXmlUtil.createDocument("InventoryVisibilityAPI");
		Element eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		eInput.setAttribute("HTTPMethod", "GET");
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/configuration/shipNodes");
		return callService(env, dInput, null, "BDACallIVService");
	}
	
	private static void pushSupplyUpdate(YFSEnvironment env, JSONObject obj, boolean adjust) {
		Document dInput = BDAXmlUtil.createDocument("InventoryVisibilityAPI");
		Element eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		if(adjust) {
			eInput.setAttribute("HTTPMethod", "POST");
		} else {
			eInput.setAttribute("HTTPMethod", "PUT");
		}		
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/supplies");
		Element input = BDAXmlUtil.createChild(eInput, "Input");
		input.setTextContent(obj.toString());
		System.out.println(BDAXmlUtil.getString(dInput));
		Document dResponse = callService(env, dInput, null, "BDACallIVService");
		
	}
	
	private static void pushDemandUpdate(YFSEnvironment env, JSONObject obj, boolean adjust) {
		Document dInput = BDAXmlUtil.createDocument("InventoryVisibilityAPI");
		Element eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		if(adjust) {
			eInput.setAttribute("HTTPMethod", "POST");
		} else {
			eInput.setAttribute("HTTPMethod", "PUT");
		}
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/demands");
		Element input = BDAXmlUtil.createChild(eInput, "Input");
		input.setTextContent(obj.toString());
		System.out.println(BDAXmlUtil.getString(dInput));
		Document dResponse = callService(env, dInput, null, "BDACallIVService");
		
	}
	
	private static Document getSupplyForNode(String sItemID, String sUnitOfMeasure, String sShipNode) {
		Document dInput = BDAXmlUtil.createDocument("InventoryVisibilityAPI");
		Element eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		eInput.setAttribute("HTTPMethod", "GET");
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/supplies");
		Element eRequestParameters = BDAXmlUtil.createChild(eInput, "RequestParameters");
		Element eRequestParameter = BDAXmlUtil.createChild(eRequestParameters, "RequestParameter");
		eRequestParameter.setAttribute("Name", "itemId");
		eRequestParameter.setAttribute("Value", sItemID);
		eRequestParameter = BDAXmlUtil.createChild(eRequestParameters, "RequestParameter");
		eRequestParameter.setAttribute("Name", "unitOfMeasure");
		eRequestParameter.setAttribute("Value", sUnitOfMeasure);
		eRequestParameter = BDAXmlUtil.createChild(eRequestParameters, "RequestParameter");
		eRequestParameter.setAttribute("Name", "shipNode");
		eRequestParameter.setAttribute("Value", sShipNode);
		//System.out.println(BDAXmlUtil.getString(dInput));
		return dInput;
	}
	
	private static Document getDemandForNode(String sItemID, String sUnitOfMeasure, String sShipNode) {
		Document dInput = BDAXmlUtil.createDocument("InventoryVisibilityAPI");
		Element eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		eInput.setAttribute("HTTPMethod", "GET");
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/demands");
		Element eRequestParameters = BDAXmlUtil.createChild(eInput, "RequestParameters");
		Element eRequestParameter = BDAXmlUtil.createChild(eRequestParameters, "RequestParameter");
		eRequestParameter.setAttribute("Name", "itemId");
		eRequestParameter.setAttribute("Value", sItemID);
		eRequestParameter = BDAXmlUtil.createChild(eRequestParameters, "RequestParameter");
		eRequestParameter.setAttribute("Name", "unitOfMeasure");
		eRequestParameter.setAttribute("Value", sUnitOfMeasure);
		eRequestParameter = BDAXmlUtil.createChild(eRequestParameters, "RequestParameter");
		eRequestParameter.setAttribute("Name", "shipNode");
		eRequestParameter.setAttribute("Value", sShipNode);
		//System.out.println(BDAXmlUtil.getString(dInput));
		return dInput;
	}
	
}
