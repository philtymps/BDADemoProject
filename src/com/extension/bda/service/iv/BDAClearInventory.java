package com.extension.bda.service.iv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bda.utils.BDACommon;
import com.bda.utils.BDAXmlUtil;
import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

import edu.emory.mathcs.backport.java.util.Arrays;

public class BDAClearInventory extends BDAServiceApi implements IBDAService {
	private static String[] stores = {"tmobile_S1", "tmobile_S2", "tmobile_S3", "tmobile_S4", "tmobile_S5", "tmobile_S6", "tmobile_S7", "tmobile_S8"};
	private static String[] items = {"TABLETSA774_0003_0" };
	private static List<String> _nodes;
	
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
		Document dResponse = BDAXmlUtil.createDocument("Items");
		Element eSupplies = BDAXmlUtil.createChild(dResponse.getDocumentElement(), "Supplies");
		wipeSupplies(env, eSupplies, true);
		Element eDemands = BDAXmlUtil.createChild(dResponse.getDocumentElement(), "Demands");
		wipeDemands(env, eDemands, true);
		return dResponse;
	}

	
	private static String getVariableFile(){
		return "/opt/Sterling/Scripts/variables.xml";
	}
	
	private HashMap<String, String> replaceVariables(YFCDocument dFileInput){
		HashMap<String, String> variable = new HashMap<String, String>();
		YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile());
		for (YFCElement eChild : temp.getDocumentElement().getChildren()){
			if(YFCCommon.equals(eChild.getAttribute("TYPE"), "ITEM")) {
				variable.put(eChild.getAttribute("Name"), eChild.getAttribute("Value"));
			}			
		}
		return variable;
	}
	
	private Collection<String> getVariableItems() {
		try {
			YFCDocument dVariables = YFCDocument.getDocumentForXMLFile(getVariableFile());
			if(!YFCCommon.isVoid(dVariables)){
				HashMap<String, String> vars = replaceVariables(dVariables);
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
				YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile());
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
	
	private void wipeSupplies(YFSEnvironment env, Element output, boolean adjust) {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			obj.put("supplies", array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		for(String p : getVariableItems()) {
			System.out.println("Retrieve Supply for " + p);
			for(String sNode : getShipNodes(env)) {
				Document dResponse = this.callService(env, getSupplyForNode(p, "EACH", sNode), null, "BDACallIVService");
			
				//System.out.println(BDAXmlUtil.getString(dResponse));
				try {
					if(array.size() >= 200) {
						
						pushSupplyUpdate(env, obj, adjust);
						array = new JSONArray();
						
						obj.put("supplies", array);
					}
					// System.out.println(obj.toString());
					createSupplyRecord(dResponse, array, output, adjust);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}	
		}
		try {
			if(array.size() >= 0) {
				obj.put("supplies", array);
				pushSupplyUpdate(env, obj, adjust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void wipeDemands(YFSEnvironment env, Element eDemands, boolean adjust) {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			obj.put("demands", array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		for(String p : getVariableItems()) {
			System.out.println("Retrieving Demand for " + p);
			for(String sNode : getShipNodes(env)) {
				Document dResponse = this.callService(env, getDemandForNode(p, "EACH", sNode), null, "BDACallIVService");
			
				//System.out.println(BDAXmlUtil.getString(dResponse));
				try {
					if(array.size() >= 200) {
						
						
						pushDemandUpdate(env, obj, adjust);
						array = new JSONArray();
						
						obj.put("demands", array);
					}
					// System.out.println(obj.toString());
					createDemandRecord(dResponse, array, eDemands, adjust);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}	
		}
		try {
			if(array.size() >= 0) {
				obj.put("demands", array);
				pushDemandUpdate(env, obj, adjust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void createSupplyRecord(Document dResponse,  JSONArray array, Element eSupplies, boolean adjust) throws Exception {
		Element eResponse = dResponse.getDocumentElement();

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
				
				Element eSup = BDAXmlUtil.createChild(eSupplies, "Supply");
				eSup.setAttribute("itemId", eSupply.getAttribute("itemId"));
				eSup.setAttribute("shipNode", eSupply.getAttribute("shipNode"));
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
	
	private void pushSupplyUpdate(YFSEnvironment env, JSONObject obj, boolean adjust) {
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
	
	private void pushDemandUpdate(YFSEnvironment env, JSONObject obj, boolean adjust) {
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
