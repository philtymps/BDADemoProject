package com.extension.bda.inventory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.expose.BDAEntityApi;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAAdjustInventory extends BDAServiceApi implements IBDAService {

	
	private static HashMap<String, String> _nodeTypes;

	
	private static JSONObject currentToken;
	
	public static synchronized String getNodeTypeForNode(YFSEnvironment env, String node) {
		if(_nodeTypes == null) {
			_nodeTypes = new HashMap<String, String>();
		}
	
		if(!YFCCommon.isVoid(_nodeTypes.get(node))) {
			return _nodeTypes.get(node);
		} else {
			YFCDocument dInput = YFCDocument.createDocument("ShipNode");
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("ShipnodeKey", node);
			
			YFCDocument dTemplate = YFCDocument.createDocument("ShipNodeList");
			YFCElement eNode = dTemplate.getDocumentElement().createChild("ShipNode");
			eNode.setAttribute("NodeType", "");
			eNode.setAttribute("ShipnodeKey", "");
			YFCDocument response = YFCDocument.getDocumentFor(callApi(env, dInput.getDocument(), dTemplate.getDocument(), "getShipNodeList"));
			YFCElement eOutput = response.getDocumentElement();
			_nodeTypes.put(node, eOutput.getChildElement("ShipNode").getAttribute("NodeType"));
			return eOutput.getChildElement("ShipNode").getAttribute("NodeType");
		}
	}
	
	@Override
	public String getServiceName() {
		return "BDAAdjustInventory";
	}

	private boolean isStoreAdjustment(YFSEnvironment env, String sNode) {
		String nodeType = getNodeTypeForNode(env, sNode);
		if(!YFCCommon.isVoid(nodeType) && YFCCommon.equalsIgnoreCase(sNode, "store")) {
			return true;
		}
		return false;
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dResponse  = YFCDocument.createDocument("AdjustInventory");
		YFCElement eResponse = dResponse.getDocumentElement();
		if(!YFCCommon.isVoid(input)) {
			int level = getLevelOfIntegration(env);
			eResponse.setAttribute("Level", level);
			if(level == 1) {
				eResponse.setAttribute("Message",  "OMS adjustInventory Called");
				BDAServiceApi.callApi(env, input, null, "adjustInventory");
			} else {
				YFCDocument dInput = YFCDocument.getDocumentFor(input);
				YFCElement eItemList = dInput.getDocumentElement();
				JSONObject objInput = new JSONObject();
				JSONArray supplies = new JSONArray();
				objInput.put("supplies", supplies);
				for(YFCElement eItem : eItemList.getChildren()) {
					if(level == 2 || !YFCCommon.equals(eItem.getAttribute("SupplyType"), "ONHAND") || !isStoreAdjustment(env, eItem.getAttribute("ShipNode"))) {
						
						supplies.add(convertInventoryAdjustment(eItem));
						if(supplies.size() >= 200) { 
							YFCElement eIVCall = eResponse.getChildElement("IVCalls", true).createChild("IVCall");
							eIVCall.setAttribute("NumberOfRecords", supplies.size());
								
							this.callIVService(env, this.adjustSupply(objInput));
							supplies = new JSONArray();
							objInput.put("supplies", supplies);
						}
					} else {
						YFCElement eSIMCall = eResponse.getChildElement("SIMCalls", true).createChild("SIMCall");
						eSIMCall.setAttribute("ShipNode", eItem.getAttribute("ShipNode"));
						eSIMCall.setAttribute("Location", eItem.getAttribute("Location"));
						callSIMService(env, convertStoreInventoryAdjustment(eItem), eItem.getAttribute("ShipNode"), YFCCommon.isVoid(eItem.getAttribute("Location")) ? "STORE" : eItem.getAttribute("Location"));
					}
				}
				
				if(supplies.size() > 0) {
					YFCElement eIVCall = eResponse.getChildElement("IVCalls", true).createChild("IVCall");
					eIVCall.setAttribute("NumberOfRecords", supplies.size());
					this.callIVService(env, this.adjustSupply(objInput));
				}
			}
			
		}
		return dResponse.getDocument();
	}

	

	/** Get Level of Integration 
	 * 1 - GIV
	 * 2 - IV
	 * 3 - IV + SIM
	 */
	
	private int getLevelOfIntegration(YFSEnvironment env) {
		
		String inLevel = getIVIntegrationLevel(env);
		if(!inLevel.startsWith("2")) {
			return 1;
		}
		String simTenantId = getPropertyValue(env, "yfs.jwt.create.claims.simTenantId.value");
		if(!YFCCommon.isVoid(simTenantId)) {
			return 3;
		}
		return 2;
	}
	
	private String getIVIntegrationLevel(YFSEnvironment env) {
		YFCDocument input = YFCDocument.createDocument("Event");
		YFCElement eInput = input.getDocumentElement();
		eInput.setAttribute("ApiName", "getBaseRules");
		eInput.setAttribute("BaseRulesKey", "IV_1");
		
		try {
			Document dResponse = BDAEntityApi.getEntityApi(env, input.getDocument(), null);
			return dResponse.getDocumentElement().getAttribute("RuleSetValue");
		} catch (Exception e) {
			return "0.0";
		}
	}
	
	private void createAttributeIfDefined(JSONObject obj, YFCElement ele, String objAttribute, String eleAttribute) throws JSONException {
		if (YFCCommon.equals("Quantity", eleAttribute)) {
			obj.put(objAttribute, ele.getDoubleAttribute(eleAttribute));
		} else if(!YFCCommon.isVoid(ele.getAttribute(eleAttribute))) {
			obj.put(objAttribute, ele.getAttribute(eleAttribute));
		} else if(YFCCommon.equals("ETA", eleAttribute) || YFCCommon.equals("SourceTS", eleAttribute)) {
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
			df.setTimeZone(tz);
			String nowAsISO = df.format(new Date());
			obj.put(objAttribute, nowAsISO);
		} else if(YFCCommon.equals("ShipByDate", eleAttribute)) {
			obj.put(objAttribute, "2500-01-01T00:00:00Z");
		} else if(YFCCommon.equals("Tag", eleAttribute)) {
			if(!YFCCommon.isVoid(ele.getChildElement("Tag"))) {
				YFCElement eTag = ele.getChildElement("Tag");
				String tag = "";
				dealWithTag(tag, eTag, "BatchNo");
				dealWithTag(tag, eTag, "LotAttribute1");
				dealWithTag(tag, eTag, "LotAttribute2");
				dealWithTag(tag, eTag, "LotAttribute3");
				dealWithTag(tag, eTag, "LotKeyReference");
				dealWithTag(tag, eTag, "LotNumber");
				dealWithTag(tag, eTag, "ManufacturingDate");
				dealWithTag(tag, eTag, "RevisionNo");
			}
		}
	}
	
	private void dealWithTag(String currentTag, YFCElement eTag, String attribute) {
		if(!YFCCommon.isVoid(eTag.getAttribute(attribute))) {
			if(!YFCCommon.isVoid(currentTag)) {
				currentTag += ":";
			}
			currentTag += eTag.getAttribute(attribute);
		}
	}
	private JSONObject convertInventoryAdjustment(YFCElement eItem) throws JSONException {
		JSONObject supply = new JSONObject();
		createAttributeIfDefined(supply, eItem, "adjustmentReason", "ReasonCode");
		createAttributeIfDefined(supply, eItem, "changedQuantity", "Quantity");
		createAttributeIfDefined(supply, eItem, "eta", "ETA");
		createAttributeIfDefined(supply, eItem, "itemId", "ItemID");
		createAttributeIfDefined(supply, eItem, "lineReference", "Reference_2");
		createAttributeIfDefined(supply, eItem, "productClass", "ProductClass");
		createAttributeIfDefined(supply, eItem, "reference", "Reference_1");
		createAttributeIfDefined(supply, eItem, "segment", "Segment");
		createAttributeIfDefined(supply, eItem, "segmentType", "segmentType");
		createAttributeIfDefined(supply, eItem, "shipByDate", "ShipByDate");
		createAttributeIfDefined(supply, eItem, "shipNode", "ShipNode");
		createAttributeIfDefined(supply, eItem, "sourceTs", "SourceTS");
		createAttributeIfDefined(supply, eItem, "tagNumber", "Tag");
		createAttributeIfDefined(supply, eItem, "type", "SupplyType");
		createAttributeIfDefined(supply, eItem, "unitOfMeasure", "UnitOfMeasure");	
		return supply;
	}
	
	private JSONObject convertStoreInventoryAdjustment(YFCElement eItem) throws JSONException {
		JSONObject supply = new JSONObject();
		createAttributeIfDefined(supply, eItem, "addQuantity", "Quantity");
		createAttributeIfDefined(supply, eItem, "inventoryStatus", "InventoryStatus");
		createAttributeIfDefined(supply, eItem, "productClass", "ProductClass");
		createAttributeIfDefined(supply, eItem, "productId", "ItemID");
		createAttributeIfDefined(supply, eItem, "segment", "Segment");
		createAttributeIfDefined(supply, eItem, "segmentType", "SegmentType");
		createAttributeIfDefined(supply, eItem, "unitOfMeasure", "UnitOfMeasure");
		if(!YFCCommon.isVoid(eItem.getChildElement("Tag"))) {
			YFCElement eTag = eItem.getChildElement("Tag");
			createAttributeIfDefined(supply, eTag, "batchNo", "BatchNo");
			createAttributeIfDefined(supply, eTag, "lotNo", "LotNumber");
			createAttributeIfDefined(supply, eTag, "manufacturedDate", "ManufacturingDate");
			createAttributeIfDefined(supply, eTag, "tagNo", "TagNo");
		}
		return supply;	
	}
	private Document adjustSupply(JSONObject obj) {
		YFCDocument dInput = YFCDocument.createDocument("InventoryVisibilityAPI");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		eInput.setAttribute("HTTPMethod", "POST");
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/supplies");
		YFCElement input = eInput.createChild("Input");
		
		input.setNodeValue(obj.toString());
		return dInput.getDocument();
	}
	
	private YFCDocument callIVService(YFSEnvironment env, Document dInput) {
		return YFCDocument.getDocumentFor(BDAServiceApi.callService(env, dInput, null, "BDA_IVInvokeRestAPI"));
	}
	
	private void callSIMService(YFSEnvironment env, JSONObject obj, String sShipNode, String sLocation) throws JSONException {
		StringBuilder url = new StringBuilder("https://sim.watsoncommerce.ibm.com/");
		url.append(getPropertyValue(env, "bda.sim.integration.tenant_id"));
		url.append("/v1/stores/");
		url.append(sShipNode);
		url.append("/locations/");
		url.append(sLocation);
		url.append("/inventory:add");
		
		try {
			URL requestURL = new URL(url.toString());
			HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + getAuthToken(env, getPropertyValue(env, "bda.sim.integration.tenant_id"), getPropertyValue(env, "bda.sim.integration.client_id"), getPropertyValue(env, "bda.sim.integration.secret")));
			connection.setConnectTimeout(5000);
		

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
			outputStreamWriter.write(obj.toString());
			outputStreamWriter.flush();
			int responseCode = connection.getResponseCode();
			if(responseCode < 300) {
				new JSONObject(new BufferedInputStream(connection.getInputStream()));
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String getAuthToken(YFSEnvironment env, String tenant, String clientId, String secret) throws JSONException{
		if(!YFCCommon.isVoid(currentToken)) {
			if(currentToken.getInt("expires_at") > System.currentTimeMillis()) {
				return currentToken.getString("access_token");
			}
		}
		String sURL = "https://sim.watsoncommerce.ibm.com/";
		sURL += getPropertyValue(env, "bda.sim.integration.tenant_id");
		sURL += "/auth/oauth2/token";
		
		try {
			URL url = new URL(sURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setConnectTimeout(5000);
			
			JSONObject obj = new JSONObject();
			obj.put("client_id", clientId);
			obj.put("client_secret", secret);
			obj.put("grant_type", "client_credentials");
			
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
			outputStreamWriter.write(obj.toString());
			outputStreamWriter.flush();
			
			System.out.println("Before Request");
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			if(responseCode < 300) {
				JSONObject response = new JSONObject(new BufferedInputStream(connection.getInputStream()));
				response.put("expires_at", System.currentTimeMillis() + response.getInt("expires_in") - 10);
				currentToken = response;
				return currentToken.getString("access_token");
			}
			
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}

}
