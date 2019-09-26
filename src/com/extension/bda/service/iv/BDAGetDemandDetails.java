package com.extension.bda.service.iv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetDemandDetails extends BDAServiceApi implements IBDAService {

	private static HashMap<String, YFCDocument> cachedInventory;
	
	private static HashMap<String, YFCDocument> getInstance() {
		if(YFCCommon.isVoid(cachedInventory)) {
			cachedInventory = new HashMap<String, YFCDocument>();
			
		}
		return cachedInventory;
	}
	

	
	class GetDemandDetailAsync extends BDAServiceApi implements Runnable {
		private YFSEnvironment env;
		private YFCElement eRequestInput;
		private String sShipNode;
		private YFCDocument dShipNode;
		private int remainingrequests = 10000;
		
		private HashMap<String, ArrayList<YFCElement>> DemandTypes;
		
		GetDemandDetailAsync(YFSEnvironment env, YFCElement eRequestInput, String sShipNode){
			this.env = env;
			this.eRequestInput = eRequestInput;
			this.sShipNode = sShipNode;
			this.DemandTypes = new HashMap<String, ArrayList<YFCElement>>();
		}
		
		private String getKey() {
			return eRequestInput.getAttribute("ItemID") + "_" + eRequestInput.getAttribute("UnitOfMeasure") + "_" + sShipNode;
		}

		public HashMap<String, ArrayList<YFCElement>> getDemandTypes() {
			return this.DemandTypes;
		}
		
		@Override
		public void run() {
			YFCDocument response;
			response = YFCDocument.getDocumentFor(this.callService(null, getDemandForNode(eRequestInput.getAttribute("ItemID"), eRequestInput.getAttribute("UnitOfMeasure"), sShipNode), null, "BDA_IVInvokeRestAPI"));
			dShipNode = YFCDocument.createDocument("ShipNode");
			YFCElement eShipNode = dShipNode.getDocumentElement();
			eShipNode.setAttribute("ShipNode", sShipNode);
			eShipNode.setAttribute("Quantity", "0");
			YFCElement eDemands = eShipNode.createChild("Demands");
			try {
				YFCElement eResponse = response.getDocumentElement();
				for(YFCElement eHeader : eResponse.getChildElement("ResponseHeaders", true).getChildren()) {
					if(YFCCommon.equals(eHeader.getAttribute("Name"), "X-RateLimit-Remaining-hour")) {
						remainingrequests = eHeader.getIntAttribute("Value");
					}
				}
				JSONArray output = new JSONArray(eResponse.getChildElement("Output").getNodeValue());
				
				for(int i = 0; i < output.size(); i++) {
					JSONObject Demand = output.getJSONObject(i);
					if(YFCCommon.isVoid(eRequestInput.getAttribute("DemandType")) || YFCCommon.equals(Demand.getString("type"), eRequestInput.getAttribute("DemandType"))) {
						YFCElement eDemand = eDemands.createChild("Demand");
						eDemand.setAttribute("Quantity", Demand.getDouble("quantity"));
						eDemand.setAttribute("DemandType", Demand.getString("type"));
						eDemand.setAttribute("ShipNode", sShipNode);
						YFCElement eDemandDetail = eDemand.createChild("DemandDetails");
						eDemandDetail.setAttribute("ShipDate", Demand.getString("shipDate"));
						eDemandDetail.setAttribute("Quantity", Demand.getString("quantity"));
						eDemandDetail.setAttribute("DemandType", Demand.getString("type"));
						eDemandDetail.setAttribute("CancelDate", Demand.getString("cancelDate"));
						eDemandDetail.setAttribute("MinShipByDate", Demand.getString("minShipByDate"));
						eShipNode.setAttribute("Quantity", eShipNode.getDoubleAttribute("Quantity") + Demand.getDouble("quantity"));
						if(!DemandTypes.containsKey(Demand.getString("type"))) {
							DemandTypes.put(Demand.getString("type"), new ArrayList<YFCElement>());
						} 
						DemandTypes.get(Demand.getString("type")).add(eDemand);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public YFCElement getShipNodeResponse() throws JSONException {
			return dShipNode.getDocumentElement();
		}
		
		public int getRemainingRequests() {
			return remainingrequests;
		}
		
		
	}
	private static YFCDocument DemandInput;
	
	public static void main(String[] args) throws Exception {
		BDAGetDemandDetails t = new BDAGetDemandDetails();
		YFCDocument dInput = YFCDocument.createDocument("Input");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrganizationCode", "Aurora");
		eInput.setAttribute("ItemID", "AuroraWMDRS-007");
		eInput.setAttribute("UnitOfMeasure", "EACH");
		eInput.setAttribute("DistributionRuleId", "OptimizerNodes");
		Document response = t.invoke(null, dInput.getDocument());
		System.out.println(YFCDocument.getDocumentFor(response));
	}
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getDemandDetails";
	}

	@Override
	public void setProperties(Properties props) throws Exception {
		// TODO Auto-generated method stub

	}
	
	private Document getDemandForNode(String sItemID, String sUnitOfMeasure, String sShipNode) {
		YFCDocument dInput = YFCDocument.createDocument("InventoryVisibilityAPI");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("Content-Type", "application/json");
		eInput.setAttribute("HTTPMethod", "GET");
		eInput.setAttribute("RetrySafe", "true");
		eInput.setAttribute("URL", "https://edge-api.watsoncommerce.ibm.com/inventory/12345/v1/demands");
		YFCElement eRequestParameters = eInput.createChild("RequestParameters");
		YFCElement eRequestParameter = eRequestParameters.createChild("RequestParameter");
		eRequestParameter.setAttribute("Name", "itemId");
		eRequestParameter.setAttribute("Value", sItemID);
		eRequestParameter = eRequestParameters.createChild("RequestParameter");
		eRequestParameter.setAttribute("Name", "unitOfMeasure");
		eRequestParameter.setAttribute("Value", sUnitOfMeasure);
		eRequestParameter = eRequestParameters.createChild("RequestParameter");
		eRequestParameter.setAttribute("Name", "shipNode");
		eRequestParameter.setAttribute("Value", sShipNode);
		return dInput.getDocument();
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		long start = System.currentTimeMillis();
		YFCElement eRequestInput = YFCDocument.getDocumentFor(input).getDocumentElement();
		if(!YFCCommon.isVoid(eRequestInput.getAttribute("DistributionRuleId"))) {
			YFCDocument dDistInput = YFCDocument.createDocument("ItemShipNode");
			YFCElement eDInput = dDistInput.getDocumentElement();
			eDInput.setAttribute("CallingOrganizationCode", eRequestInput.getAttribute("OrganizationCode"));
			eDInput.setAttribute("DistributionRuleId", eRequestInput.getAttribute("DistributionRuleId"));
			Document dOutput = this.callApi(env, dDistInput.getDocument(), null, "getDistributionList");
			System.out.println(YFCDocument.getDocumentFor(dOutput));
			ArrayList<GetDemandDetailAsync> threads = new ArrayList<GetDemandDetailAsync>();
			ExecutorService pool = Executors.newFixedThreadPool(30);
			
			for(YFCElement eShipNode : YFCDocument.getDocumentFor(dOutput).getDocumentElement().getChildren()) {
				GetDemandDetailAsync runnable = new GetDemandDetailAsync(env, eRequestInput, eShipNode.getAttribute("ShipnodeKey"));
				threads.add(runnable);
				pool.execute(runnable);
			}
			pool.shutdown();
			pool.awaitTermination(1, TimeUnit.MINUTES);
			
			YFCDocument dResponse = YFCDocument.createDocument("Item");
			YFCElement eOutput = dResponse.getDocumentElement();
			eOutput.setAttribute("ItemID", eRequestInput.getAttribute("ItemID"));
			eOutput.setAttribute("UnitOfMeasure", eRequestInput.getAttribute("UnitOfMeasure"));
			eOutput.setAttribute("DistributionRuleId", eRequestInput.getAttribute("DistributionRuleId"));
			eOutput.setAttribute("Requests", threads.size());
			
			YFCElement eShipNodes = eOutput.createChild("ShipNodes");
			YFCElement eDemandTypes = eOutput.createChild("DemandTypes");
			HashMap<String, YFCElement> types = new HashMap<String, YFCElement>();
			for(GetDemandDetailAsync thread : threads) {
				if(YFCCommon.isVoid(eOutput.getAttribute("RemainingRequests")) || eOutput.getIntAttribute("RemainingRequests") > thread.getRemainingRequests()) {
					eOutput.setAttribute("RemainingRequests", thread.getRemainingRequests());
				}
				for(String type : thread.getDemandTypes().keySet()) {
					if(!types.containsKey(type)) {
						YFCElement eDemandType = eDemandTypes.createChild("DemandType");
						eDemandType.setAttribute("DemandType", type);
						eDemandType.setAttribute("Quantity", 0);
						types.put(type, eDemandType);
					}
					for(YFCElement eDemandTypeDemand : thread.getDemandTypes().get(type)) {
						types.get(type).importNode(eDemandTypeDemand);
						types.get(type).setAttribute("Quantity", types.get(type).getDoubleAttribute("Quantity", 0) + eDemandTypeDemand.getDoubleAttribute("Quantity", 0));
					}
					
				}
				eShipNodes.importNode(thread.getShipNodeResponse());
			}
		
			long end = System.currentTimeMillis() - start;
			return dResponse.getDocument();
			
		}
		return null;
		
	}

	
}
