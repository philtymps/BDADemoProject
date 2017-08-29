package com.extension.inbalance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.extension.inbalance.extract.NodeCapacity;
import com.ibm.commerce.otmz.client.MIME;
import com.ibm.commerce.otmz.client.OTMZWebClient;
import com.ibm.extraction.commerce.BDASynchronization;
import com.sterlingcommerce.baseutil.SCUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.statistics.YFCStatisticsContextFactory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.OMPGetExternalCostForOptionsUE;
import com.yantra.yfs.japi.ue.OMPGetSourcingCorrectionsUE;
import com.yantra.yfs.japi.ue.YFSCheckOrderBeforeProcessingUE;

public class OptimizerCostOptions implements OMPGetExternalCostForOptionsUE, YFSCheckOrderBeforeProcessingUE, OMPGetSourcingCorrectionsUE{

	private static YFCLogCategory cat = YFCLogCategory.instance(OptimizerCostOptions.class.getName());
	private static final YFCLogCategory LOG = YFCLogCategory.instance(OptimizerCostOptions.class.getName());
	
	public static final String E_COSTS = "Costs";
	public static final String E_OTMZ_ERROR = "OptimizerError";
	public static final String E_SHIP_TO_ADDRESS = "ShipToAddress";
	public static final String A_ERROR_STRING = "ErrorString";
	public static final String A_OTMZ_CALL_ERR = "OptimizerCallError";
	public static final String A_NODE_PRIORITY_COST_PER_UNIT = "NodePriorityCostPerUnit";
	public static final String A_NODE_SHP_CAPACITY_COST_PER_UNIT = "NodeShippingCapacityCostPerUnit";
	public static final String A_OB_SHP_HANDLING_COST_PER_UNIT = "OutboundShipmentHandlingCostPerUnit";
	public static final String A_ITEM_INV_COST_PER_UNIT = "ItemInventoryCostPerUnit";
	public static final String A_ASSIGNMENT_PRIORITY = "AssignmentPriority";
	public static final String A_LINES_HAVE_ASSIGNMENTS = "AllLinesHaveAssignments";
	public static final String A_INVOKE_OPTIMIZER_FLAG = "InvokeOptimizerFlag";
	public static final String ZERO_COST = "0.00";

	public static final String INB_WEBSERVICE_GET_EXTERNAL_COST_OPTIONS_TIME = "inBalanceOTMZWebserviceTime";
	public static final String INB_WEBSERVICE_ERROR = "InBalanceOptError";

	public static final String[] VALID_PAYMENT_STATUSES = {"PAID","AUTHORIZED","INVOICED","NOT_APPLICABLE"};
	//private final OptimizerRequest optRequest;
	
	public static void main(String[] args) throws SAXException, IOException, YFSUserExitException{
		OptimizerCostOptions o = new OptimizerCostOptions();
		YFCDocument dInput = YFCDocument.getDocumentForXMLFile("/Users/pfaiola/DummyOrder.xml");
		YFCElement eInput = dInput.getDocumentElement();
		
		eInput.setAttribute("OrderNo","OM" + System.currentTimeMillis());
		YFCDocument.getDocumentFor(o.getExternalCostForOptions(null, dInput.getDocument()));
	}
	
	private static String getPropertyValue(String sPropertyName) {
		String sPropertyValue = "";
		if(!YFCCommon.isVoid(OTMZWebClient.props)){
			if(!YFCCommon.isVoid(OTMZWebClient.props.getProperty(sPropertyName))){
				return OTMZWebClient.props.getProperty(sPropertyName);
			};
		}
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("/opt/Sterling/runtime/properties/otmz.webclient.properties"));
			if(!YFCCommon.isVoid(props.get(sPropertyName))){
				return props.getProperty(sPropertyName);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(sPropertyName.equals("tenantId")){
			return "sales";
		} else if(sPropertyName.equals("rank.service.distance")){
			return "500";
		} else if(sPropertyName.equals("rank.service.nodes.limit")){
			return "30";
		}
		return sPropertyValue;
	}
	
	public OptimizerCostOptions(){
	//	optRequest = new OptimizerRequest();
	}
	
	@Override
	public boolean checkOrderBeforeProcessing(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		YFCDocument dInput = YFCDocument.getDocumentFor(inDoc);
		YFCElement eOrder = dInput.getDocumentElement();
		env.setTxnObject("scheduleOrderInput", eOrder);
		return Arrays.asList(VALID_PAYMENT_STATUSES).contains(eOrder.getAttribute("PaymentStatus").toUpperCase());
	}
	
	public Document getExternalCostForOptions(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		cat.beginTimer("getExternalCostForOptions"); 
		Document outDoc = null;
		YFSContext ctx = (YFSContext)env;
		String sError = null;
		long ts = System.currentTimeMillis();
		Element inpEle = inDoc.getDocumentElement();
		HashMap<String, ArrayList<Element>> promiseLineSku = new HashMap<String, ArrayList<Element>>();
		
		try{
			System.out.println("Input document to UE " + SCXmlUtil.getString(inDoc));
			cat.debug("Input document to UE " + SCXmlUtil.getString(inDoc));
			removeAttributes(inpEle);
			if(ctx == null || (!YFCCommon.isVoid(ctx.getCallName()) && ctx.getCallName().toUpperCase().contains("SCHEDULE"))){
				if(ctx != null){
					if(!YFCCommon.isVoid(ctx.getTxnObject("scheduleOrderInput"))){
						YFCElement eOrderIn = (YFCElement) env.getTxnObject("scheduleOrderInput");
						if(eOrderIn.hasAttribute("OrderNo")){
							String ip = InetAddress.getLocalHost().getHostAddress();
							if(!ip.contains("127.0.0.1")){
								inpEle.setAttribute("OrderNo", eOrderIn.getAttribute("OrderNo") + "_" + ip);
							}
							
						} else {
							inpEle.setAttribute("OrderNo", UUID.randomUUID().toString());
						}
						if(eOrderIn.hasAttribute("OrderDate")){
							inpEle.setAttribute("OrderDate", eOrderIn.getAttribute("OrderDate"));
						} else {
							inpEle.setAttribute("OrderDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
						}
					} else {
						inpEle.setAttribute("OrderNo", UUID.randomUUID().toString());
						inpEle.setAttribute("OrderDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
					}
				} else {
					inpEle.setAttribute("OrderNo", UUID.randomUUID().toString());
					inpEle.setAttribute("OrderDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
				}
			} else {
				//inpEle.setAttribute("OrderNo", UUID.randomUUID().toString());
				inpEle.setAttribute("OrderNo", "Unpromised");
				if(ctx != null){
					inpEle.setAttribute("OrderDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(ctx.getDBDate()));
				} 
			}
			
			if(ctx != null){
				updateCapacityConsumed(inpEle, !YFCCommon.isVoid(ctx.getCallName()) && ctx.getCallName().toUpperCase().contains("SCHEDULE"), promiseLineSku);
			} else {
				updateCapacityConsumed(inpEle, false, promiseLineSku);
			}
			
			
			//outDoc = optRequest.optimizeSourcing(ctx, inDoc);
			
			OTMZWebClient instance = OTMZWebClient.getInstance(getPropertyValue("tenantId"));
			
			if(instance != null){
				System.out.println(SCXmlUtil.getString(inDoc));
				String srcPlanXmlStr = instance.invokeOptimizerApi(SCXmlUtil.getString(inDoc), MIME.APPLICATION_XML, "optimizer");
				outDoc = SCXmlUtil.createFromString(srcPlanXmlStr);
			} else {
				sError = "OTMZWebClient not initialized";
			}
		

		}catch  (Exception ex){
			cat.error(ex);
			ex.printStackTrace();
			sError = "Exception : " + ex.getMessage();
		}		

		if(!SCUtil.isVoid(sError)){
			Element eleOptimizerError = inDoc.createElement(E_OTMZ_ERROR);
			inDoc.getDocumentElement().appendChild(eleOptimizerError);
			eleOptimizerError.setAttribute(A_ERROR_STRING,	sError);

			inDoc.getDocumentElement().setAttribute(A_OTMZ_CALL_ERR, "YES");
			cat.error("Error occurred in optimizer call ::" + sError);
			cat.debug("Error occurred returning input  ::" + SCXmlUtil.getString(inDoc));

			YFCStatisticsContextFactory.getContext().addToStatistic(INB_WEBSERVICE_ERROR, 1);
			outDoc = inDoc;
		}else{
			outDoc = formUserExitOutput(outDoc, promiseLineSku);
			System.out.println("Processed Order: " + inpEle.getAttribute("OrderNo"));
		}
		YFCStatisticsContextFactory.getContext().addToStatistic(INB_WEBSERVICE_GET_EXTERNAL_COST_OPTIONS_TIME, (double)(System.currentTimeMillis() - ts));
		cat.endTimer("getExternalCostForOptions");
		return outDoc;
	}

	
	public static HashMap<String, NodeCapacity> getExternalCapacity(ArrayList<String> nodes) {
		HashMap<String, NodeCapacity> output = new HashMap<String, NodeCapacity>();
		try {
			String sNodes = "";
			for (int i = 0; i < nodes.size(); i++) {
				if (i != 0) {
					sNodes += ",";
				}
				sNodes += nodes.get(i);
			}
			URL url = new URL("http://woo1.omfulfillment.com:8080/api/kpiNodes/" + sNodes);
			// System.out.println("Connecting to: " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);

			BufferedReader streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
			JSONArray resArray = new JSONArray(responseStrBuilder.toString());
			for (int i = 0; i < resArray.length(); i++) {
				JSONObject kpiNode = resArray.getJSONObject(i);
				output.put(kpiNode.getString("nodeid"), new NodeCapacity(kpiNode.getString("nodeid"), 0f,
						(float) kpiNode.getDouble("unitsassigned")));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	public static HashMap<String, NodeCapacity> getCapacityInfo(ArrayList<String> n, int addDays){
		Connection dbConn = null;
		HashMap<String, NodeCapacity> nodes = new HashMap<String, NodeCapacity>();
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
		
		String sNodes = "";
		for(int i = 0; i < n.size(); i++){
			if(i > 0){
				sNodes += ", ";
			}
			sNodes += "'" + n.get(i) + "'";
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
				
		String sAddDays = "";
		if(addDays > 1){
			sAddDays = " + " + addDays + " DAYS ";
		} else if(addDays == 1){
			sAddDays = " + 1 DAY ";
		}
		String sSql = "SELECT TRIM(R.NODE_KEY), R.CAPACITY_UOM, C.ALLOCATED_CAPACITY_CONSUMED, O.CAPACITY, O.SUPPLEMENTAL_CAPACITY, D." + formatter.format(c.getTime()).toUpperCase() + "_CAPACITY, D." + formatter.format(c.getTime()).toUpperCase() + "_SUPPL_CAPACITY FROM OMDB.YFS_RES_POOL R LEFT JOIN OMDB.YFS_RES_POOL_STD_CAPCTY_PERD P ON (P.RESOURCE_POOL_KEY = R.RESOURCE_POOL_KEY AND EFFECTIVE_FROM_DATE < (CURRENT_DATE " + sAddDays + ") AND EFFECTIVE_TO_DATE > (CURRENT_DATE" + sAddDays + ")) INNER JOIN OMDB.YFS_RES_POOL_DOW_CAPCTY D ON D.RES_POOL_STD_CAPCTY_PERD_KEY = P.RES_POOL_STD_CAPCTY_PERD_KEY LEFT JOIN OMDB.YFS_RES_POOL_CAPCTY_OVERRIDE O ON (O.RESOURCE_POOL_KEY = R.RESOURCE_POOL_KEY AND O.CAPACITY_DATE = (CURRENT_DATE" + sAddDays + ") ) LEFT JOIN OMDB.YFS_RES_POOL_CAPCTY_CONSMPTN C ON (C.RESOURCE_POOL_KEY = R.RESOURCE_POOL_KEY AND C.CONSUMPTION_DATE = (CURRENT_DATE" + sAddDays + ")) WHERE R.NODE_KEY IN (" + sNodes + ") AND R.ITEM_GROUP_CODE = 'PROD' AND R.PURPOSE = 'INVENTORY'";
		
		try {
			dbConn = BDASynchronization.getOMSConnection();
			
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				if(rs.getString(4) == null){
					nodes.put(rs.getString(1), new NodeCapacity(rs.getString(1), rs.getString(2), rs.getFloat(6), rs.getFloat(7), -1, -1, rs.getFloat(3)));
				} else {
					nodes.put(rs.getString(1), new NodeCapacity(rs.getString(1), rs.getString(2), rs.getFloat(6), rs.getFloat(7), rs.getFloat(4), rs.getFloat(5), rs.getFloat(3)));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return nodes;
	}
	
	public static void removeAttributes(Element ele) {
		NamedNodeMap attrs = ele.getAttributes();
		while (attrs.getLength() > 0) {
			attrs.removeNamedItem(attrs.item(0).getNodeName());
		}
	}

	private String getTranslatedCarrierService(String sCarrierService){
		if(!YFCCommon.isVoid(sCarrierService)){
			if (sCarrierService.equals("1BUSDAY") || sCarrierService.contains("1") || sCarrierService.toUpperCase().contains("PREMIUM")){
				return "1BUSDAY";
			} else if (sCarrierService.equals("2BUSDAY") || sCarrierService.contains("2") || sCarrierService.toUpperCase().contains("EXPRESS")){
				return "2BUSDAY";
			}
		}
		return "Standard Ground";
	}
	
	public static void getSupplyForItemNode(Element eAssignment, String sItemID, String sNode){
		if(YFCCommon.isVoid(SCXmlUtil.getChildElement(eAssignment, "Supplies"))){
			Connection dbConn = null;

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dbConn = BDASynchronization.getOMSConnection();
				StringBuilder sSqlBuild = new StringBuilder()
				.append("SELECT II.ITEM_ID, SUP.SHIPNODE_KEY, SUP.SUPPLY_TYPE, SUP.ETA, SUP.QUANTITY, SUM(ID.QUANTITY) AS DEMAND_QTY ")
				.append("FROM OMDB.YFS_INVENTORY_SUPPLY SUP ")
				.append("INNER JOIN OMDB.YFS_INVENTORY_ITEM II ON II.INVENTORY_ITEM_KEY = SUP.INVENTORY_ITEM_KEY ")
				.append("LEFT JOIN OMDB.YFS_INVENTORY_DEMAND ID ON (ID.INVENTORY_ITEM_KEY = II.INVENTORY_ITEM_KEY AND SUP.SHIPNODE_KEY = ID.SHIPNODE_KEY) ")
				.append("WHERE ITEM_ID = ? AND SUP.SHIPNODE_KEY = ? ")
				.append("GROUP BY II.ITEM_ID, SUP.SHIPNODE_KEY, SUP.SUPPLY_TYPE, SUP.ETA, SUP.QUANTITY ")
				.append("ORDER BY SUP.ETA");

				PreparedStatement ps = dbConn.prepareStatement(sSqlBuild.toString());
				ps.setString(1, sItemID);
				ps.setString(2, sNode);
				ResultSet rs = ps.executeQuery();
				Element eSupplies = SCXmlUtil.createChild(eAssignment, "Supplies");
				double demand_consumed = -1;
				
				while ( rs.next() ) {
					Element eSupply = SCXmlUtil.createChild(eSupplies, "Supply");
					eSupply.setAttribute("SupplyID", rs.getString("SUPPLY_TYPE"));
					eSupply.setAttribute("Track", "Y");
					
					if(rs.getDate("ETA").compareTo(new Date()) <= 0){
						SCXmlUtil.setAttribute(eSupply, "FirstShipDate", sf.format(new Date()));
					} else {
						Date eta = rs.getDate("ETA");
						SCXmlUtil.setAttribute(eSupply, "FirstShipDate", sf.format(eta));
					}
					if(YFCCommon.isVoid(rs.getDouble("DEMAND_QTY"))){
						SCXmlUtil.setAttribute(eSupply, "AvailableQuantity", rs.getDouble("QUANTITY"));
						SCXmlUtil.setAttribute(eSupply, "SafetyQuantity", 1);
					} else {
						if(demand_consumed < 0){
							demand_consumed = rs.getDouble("DEMAND_QTY");
							
						}
						if (rs.getDouble("QUANTITY") > demand_consumed){
							SCXmlUtil.setAttribute(eSupply, "AvailableQuantity", rs.getDouble("QUANTITY") - demand_consumed);
							SCXmlUtil.setAttribute(eSupply, "SafetyQuantity", 1);
							
						} else {
							SCXmlUtil.setAttribute(eSupply, "AvailableQuantity", 0);
							demand_consumed -= rs.getDouble("QUANTITY");
							SCXmlUtil.setAttribute(eSupply, "SafetyQuantity", 1);
						}
					}
					
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if(dbConn != null){
					try {
						dbConn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	
	}
	private void updateCapacityConsumed(Element ePromise, boolean consumeCapacity, HashMap<String, ArrayList<Element>> promiseLineSkus){
		ArrayList<String> nodes = new ArrayList<String>();
		String sCarrierService = null;
		
		for(Element ePromiseLine : SCXmlUtil.getChildrenList(SCXmlUtil.getChildElement(ePromise, "PromiseLines", true))){
			if(promiseLineSkus.containsKey(ePromiseLine.getAttribute("ItemID"))){
				promiseLineSkus.get(ePromiseLine.getAttribute("ItemID")).add(ePromiseLine);
			} else {
				ArrayList<Element> line = new ArrayList<Element>();
				line.add(ePromiseLine);
				promiseLineSkus.put(ePromiseLine.getAttribute("ItemID"), line);
			}
			for(Element eAssignment : SCXmlUtil.getChildrenList(SCXmlUtil.getChildElement(ePromiseLine, "PossibleAssignments", true))){
				if(!nodes.contains(eAssignment.getAttribute("ShipNode"))){
					nodes.add(eAssignment.getAttribute("ShipNode"));
				}
				if(sCarrierService != null){
					eAssignment.setAttribute("CarrierServiceCode", sCarrierService);
				} else {
					if(SCUtil.isVoid(eAssignment.getAttribute("CarrierServiceCode"))){
						eAssignment.setAttribute("CarrierServiceCode", "Standard Ground");
						sCarrierService = "Standard Ground";
					} else {
						sCarrierService =  getTranslatedCarrierService(eAssignment.getAttribute("CarrierServiceCode"));
						eAssignment.setAttribute("CarrierServiceCode", sCarrierService);
					}
				}
				
			}
			
			if(sCarrierService != null){
				ePromiseLine.setAttribute("CarrierServiceCode", sCarrierService);
			} else {
				if(SCUtil.isVoid(ePromiseLine.getAttribute("CarrierServiceCode"))){
					ePromiseLine.setAttribute("CarrierServiceCode", "Standard Ground");
					sCarrierService = "Standard Ground";
				} else {
					sCarrierService = getTranslatedCarrierService(ePromiseLine.getAttribute("CarrierServiceCode"));
					ePromiseLine.setAttribute("CarrierServiceCode", sCarrierService);
				}
			}
		}
		
		ePromise.removeChild(SCXmlUtil.getChildElement(ePromise, "PromiseLines"));
	
		Element ePromiseLines = SCXmlUtil.createChild(ePromise, "PromiseLines");
		
		if(nodes.size() > 0){
			//HashMap<String, NodeCapacity> cap = getCapacityInfo(nodes, 0);
			HashMap<String, NodeCapacity> cap = getExternalCapacity(nodes);
			for (String sSku : promiseLineSkus.keySet()){
				ArrayList<Element> promiseLines = promiseLineSkus.get(sSku);
				Element ePromiseLine = promiseLines.get(0);
				if(SCUtil.equals(ePromiseLine.getAttribute("DeliveryMethod"), "SHP")){
					ArrayList<String> usedNodes = new ArrayList<String>();
					for(Element eAssignment : SCXmlUtil.getChildrenList(SCXmlUtil.getChildElement(ePromiseLine, "PossibleAssignments", true))){
						getSupplyForItemNode(eAssignment, sSku, eAssignment.getAttribute("ShipNode"));
						if(usedNodes.contains(eAssignment.getAttribute("ShipNode"))){
							SCXmlUtil.getChildElement(ePromiseLine, "PossibleAssignments", true).removeChild(eAssignment);
						} else {
							usedNodes.add(eAssignment.getAttribute("ShipNode"));
							if(cap.containsKey(eAssignment.getAttribute("ShipNode"))){
								if(consumeCapacity) {
									SCXmlUtil.setAttribute(eAssignment, "CapacityConsumed", cap.get(eAssignment.getAttribute("ShipNode")).getUtilized() + SCXmlUtil.getChildrenList(SCXmlUtil.getChildElement(ePromise, "PromiseLines", true)).size());
								} else {
									SCXmlUtil.setAttribute(eAssignment, "CapacityConsumed", cap.get(eAssignment.getAttribute("ShipNode")).getUtilized());
								}
								//SCXmlUtil.setAttribute(eAssignment, "Capacity", cap.get(eAssignment.getAttribute("ShipNode")).getRemainingCap() - 1);
								SCXmlUtil.setAttribute(eAssignment, "CapacityUnitOfMeasure", cap.get(eAssignment.getAttribute("ShipNode")).getUOM());
							} else {
								if(consumeCapacity) {
									SCXmlUtil.setAttribute(eAssignment, "CapacityConsumed", + SCXmlUtil.getChildrenList(SCXmlUtil.getChildElement(ePromise, "PromiseLines", true)).size());
								} else {
									SCXmlUtil.setAttribute(eAssignment, "CapacityConsumed", 0);
								}
								
							}
						}
						
					}
				}	
				SCXmlUtil.importElement(ePromiseLines, ePromiseLine);
			}
		}
		
	}
	public Document formUserExitOutput(Document optimizerOutput, HashMap<String, ArrayList<Element>> promiseLineSku) {
		cat.beginTimer("formUserExitOutput");

		NodeList nlCosts = optimizerOutput.getElementsByTagName(E_COSTS);
		int iLength = nlCosts.getLength();

		for (int i = 0; i < iLength; i++) {
			Element eleCost = (Element) nlCosts.item(i);

			eleCost.setAttribute(A_NODE_PRIORITY_COST_PER_UNIT,	ZERO_COST);
			eleCost.setAttribute(A_NODE_SHP_CAPACITY_COST_PER_UNIT,	ZERO_COST);
			eleCost.setAttribute(A_OB_SHP_HANDLING_COST_PER_UNIT,ZERO_COST);
			if (!SCUtil.isVoid(eleCost.getAttribute(A_ASSIGNMENT_PRIORITY))) {
				eleCost.setAttribute(A_ITEM_INV_COST_PER_UNIT,eleCost.getAttribute(A_ASSIGNMENT_PRIORITY));
				eleCost.removeAttribute(A_ASSIGNMENT_PRIORITY);
			} else {
				eleCost.setAttribute(A_ITEM_INV_COST_PER_UNIT,ZERO_COST);
			}
		}
		Element ePromiseLine = SCXmlUtil.getChildElement(optimizerOutput.getDocumentElement(), "PromiseLines", true);
		for(String sSku : promiseLineSku.keySet()){
			ArrayList<Element> aPromiseLine = promiseLineSku.get(sSku);
			if(aPromiseLine.size() > 1){
				for(int i = 1; i < aPromiseLine.size(); i++){
					SCXmlUtil.importElement(ePromiseLine, aPromiseLine.get(i));
				}
			}
		}

		cat.endTimer("formUserExitOutput");
		return optimizerOutput;
	}

	public Document getSourcingCorrections(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		LOG.beginTimer("getSourcingCorrections in GetSourcingCorrection");
		Document outDoc = null;
		if(!YFCCommon.isVoid(inDoc)){
			YFCDocument root = YFCDocument.getDocumentFor(inDoc);
			try  {
				String inputJson = convertXml2Json(root);
				LOG.debug("Converted2JSON: " + inputJson);
				String tenantID = getPropertyValue("tenantId");
				String serviceName = "rank";
				LOG.debug("tenantID:: " + tenantID + " serviceName::" + serviceName);
				String outputJson = OTMZWebClient.getInstance(tenantID).invokeOptimizerApi(inputJson, MIME.APPLICATION_JSON, serviceName);

				System.out.println("\n outputJson >>>>>>>>>" + outputJson);

				YFCDocument outputDoc = convertJson2Xml(outputJson);
				System.out.println("\n" + outputDoc.toString());
				outDoc = outputDoc.getDocument();
			} catch (Exception e){
				e.printStackTrace();
				outDoc = inDoc;
			}
		}
		LOG.endTimer("getSourcingCorrections in GetSourcingCorrection");
		
		return outDoc;
	}
	
	private YFCDocument convertJson2Xml(String outputJson) throws JSONException {

		System.out.println("outputJson : " + outputJson);

		JSONObject output = new JSONObject(outputJson);
		YFCDocument outDoc = YFCDocument.createDocument("Promise");
		YFCElement outElem = outDoc.getDocumentElement();
		outElem.setAttribute("OrderReference", output.getString("orderId"));
		YFCElement linesElem = outElem.createChild("PromiseLines");
		JSONArray lines = output.getJSONArray("promiseLines");
		for (Object line : lines) {
			JSONObject jsonLine = (JSONObject) line;
			YFCElement lineElem = linesElem.createChild("PromiseLine");
			lineElem.setAttribute("LineId", jsonLine.getString("lineId"));
			YFCElement sourcingRuleDetails = lineElem.createChild("SourcingRuleDetails");
			YFCElement sourcingRuleDetail = sourcingRuleDetails.createChild("SourcingRuleDetail");
			sourcingRuleDetail.setAttribute("SeqNo", "1");
			YFCElement shipNodes = sourcingRuleDetail.createChild("ShipNodes");
			JSONArray nodes = jsonLine.getJSONArray("shipNodes");
			for (Object node : nodes) {
				JSONObject jsonNode = (JSONObject) node;
				YFCElement shipNode = shipNodes.createChild("ShipNode");
				shipNode.setAttribute("ShipNode", jsonNode.getString("shipNode"));
			}

		}

		return outDoc;
	}
	

	private String convertXml2Json(YFCDocument documentFor)	throws JSONException {
		YFCElement rootElem = documentFor.getDocumentElement();
		
		//YFCNodeList linesElem = rootElem.getElementsByTagName("PromiseLine");
		JSONObject inputJson = new JSONObject();

		//Element firstPromiseLine = SCXmlUtil.getFirstChildElement(SCXmlUtil
		//		.getChildElement(inDoc.getDocumentElement(), "PromiseLines"));
		/*if(!YFCCommon.isVoid(rootElem.getChildElement("PromiseLines", true).getChildElement("PromiseLine"))){
			inputJson.put("orderId", rootElem.getChildElement("PromiseLines", true).getChildElement("PromiseLine").getAttribute("OrderLineReference", "MissingOrderLineReference"));
		}*/
		
		inputJson.put("orderId", "Junk-Number-111");
		inputJson.put("distToConsider",	getPropertyValue("rank.service.distance"));
		inputJson.put("rankNodesLimit",	getPropertyValue("rank.service.nodes.limit"));

		// inputJson.put("distToConsider", "10");
		//
		// inputJson.put("rankNodesLimit", "20");

		YFCElement addressElem = rootElem.getChildElement("ShipToAddress");
		JSONObject address = new JSONObject();
		address.put("zipCode", addressElem.getAttribute("ZipCode"));
		address.put("state", addressElem.getAttribute("State"));
		address.put("country", addressElem.getAttribute("Country"));
		inputJson.put("shipToAddress", address);
		JSONArray lines = new JSONArray();
		for (YFCElement lineElem : rootElem.getChildElement("PromiseLines", true).getChildren()) {
			JSONObject line = new JSONObject();
			line.put("itemId", lineElem.getAttribute("ItemID"));
			line.put("lineId", lineElem.getAttribute("LineId"));
			line.put("requiredQuantity", lineElem.getAttribute("RequiredQty"));
			line.put("unitOfMeasure", "EACH");
			if(!YFCCommon.isVoid(lineElem.getAttribute("CarrierServiceCode"))){
				line.put("carrierServiceCode",this.getTranslatedCarrierService(lineElem.getAttribute("CarrierServiceCode")));
			} else {
				line.put("carrierServiceCode", "Standard Ground");
			}			
			YFCElement lineAddressElem = lineElem.getChildElement("ShipToAddress");
			JSONObject lineAddress = new JSONObject();
			lineAddress.put("zipCode", lineAddressElem.getAttribute("ZipCode"));
			lineAddress.put("state", lineAddressElem.getAttribute("State"));
			lineAddress.put("country", lineAddressElem.getAttribute("Country"));
			line.put("shipToAddress", lineAddress);
			YFCNodeList nodesElem = lineElem.getElementsByTagName("ShipNode");
			JSONArray nodes = new JSONArray();
			for (int j = 0; j < nodesElem.getLength(); j++) {
				YFCElement nodeElem = (YFCElement) nodesElem.item(j);
				JSONObject node = new JSONObject();
				node.put("shipNode", nodeElem.getAttribute("ShipNode"));
				nodes.add(node);
			}
			line.put("shipNodes", nodes);
			lines.add(line);
		}
		inputJson.put("promiseLines", lines);

		System.out.println("inputJson in convertXml2Json: " + inputJson);

		return inputJson.toString();
	}


}


