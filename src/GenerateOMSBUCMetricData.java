/*
 *-----------------------------------------------------------------
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * 5725-Y11
 *
 * (C) Copyright IBM Corp. 2019
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 *-----------------------------------------------------------------
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateOMSBUCMetricData {

	///////////////////////////////////////////////////////////////////////
	// SAMPLE JSON FOR Nodes.json
	//	{ "nodes" :
	//		[
	//				{"id" : "Yorkdale Centre", "name" : "Yorkdale Centre",        "type" : "store", "sfsCapacity" : "200", "bopisCapacity" : "200" },
	//				{"id" : "Warden Plaza",    "name" : "Warden Plaza",           "type" : "store", "sfsCapacity" : "200", "bopisCapacity" : "200" },
	//				{"id" : "Vancouver Mall",  "name" : "Vancouver Mall",         "type" : "store", "sfsCapacity" : "200", "bopisCapacity" : "200" },
	//				{"id" : "Auro_Store_500",  "name" : "Aurora - Lowell",        "type" : "store", "sfsCapacity" : "100", "bopisCapacity" : "100" },
	//				{"id" : "Auro_Store_198",  "name" : "Aurora - Grand Central", "type" : "store", "sfsCapacity" : "100", "bopisCapacity" : "100" },
	//				{"id" : "Auro_Store_3",    "name" : "Aurora - San Francisco", "type" : "store", "sfsCapacity" : "200", "bopisCapacity" : "200" },
	//				{"id" : "Auro_Store_95",   "name" : "Aurora - The Galleria",  "type" : "store", "sfsCapacity" : "200", "bopisCapacity" : "200" },
	//				{"id" : "Auro_Store_1",    "name" : "Aurora - Boston",        "type" : "store", "sfsCapacity" : "100", "bopisCapacity" : "100" }
	//		]	
	//	}	

	// SAMPLE JSON FOR Workspaces.json
	//	{ "workspaceNodeIds" :
	//		[
	//			{ "nodeIds" : [ "Yorkdale Centre", "Warden Plaza" ] },
	//			{ "nodeIds" : [ "Warden Plaza" ] },
	//			{ "nodeIds" : [ "Yorkdale Centre" ] }
	//		]
	//	}

	// SAMPLE JSON FOR Alerts.json
	//	{ "alerts" :
	//		[
	//				{"type" : "fill-rate",               "nodeIds" : ["Yorkdale Centre", "Warden Plaza"] },
	//				{"type" : "sla-rate",                "nodeIds" : [] },
	//				{"type" : "utilization-rate-ship",   "nodeIds" : ["Auro_Store_500"] },
	//				{"type" : "utilization-rate-pickup", "nodeIds" : ["Auro_Store_95"] }
	//		]
	//	}
	
	////////////////////////////////////////////////////////////////
	// CHANGE ONLY TO MODIFY THE METRICS GENERATION ALGORITHM

	// These rules should match what is set in the Thresholds configuration
	// Value format: severity|above/below|threshold|days
	// severity: 30=high, 20=medium

	// Store Threshold Group
	final static String thresholdRuleFillRateStore             = "30|below|80|1";
	final static String thresholdRuleSlaRateStore              = "30|below|85|2";
	final static String thresholdRuleUtilizationRateSFSStore   = "20|below|80|2";
	final static String thresholdRuleUtilizationRateBOPISStore = "20|below|80|2";

	// Warehouse Threshold Group
	final static String thresholdRuleFillRateWarehouse             = "30|below|80|1";
	final static String thresholdRuleSlaRateWarehouse              = "30|below|85|2";
	final static String thresholdRuleUtilizationRateSFSWarehouse   = "20|below|80|2";
	final static String thresholdRuleUtilizationRateBOPISWarehouse = "20|below|80|2";

	// ranges to control the metric generation
	final static public Range rangeNormalNodeFillRate        = new Range(81, 95);
	final static public Range rangeAlertNodeFillRate         = new Range(60, 79);

	final static public Range rangeNormalNodeUtilizationRate = new Range(81, 95);
	final static public Range rangeAlertNodeUtilizationRate  = new Range(60, 79);

	// what percentage of orders are SLA met
	final static public Range rangeSlaMet = new Range (70, 85);
	// what percentage of non fulfilled orders are pending vs. pick decline
	final static public Range rangePending = new Range (80, 90);

	// what percentage of orders are ship from warehouse
	final static public Range rangeShipFromWarehouse = new Range (80, 90);

	// number of orders handled each hour of the day
	final static public Range rangeTodayMetrics = new Range (2, 15);

	// percentage of orders per day
	final static public double [] orderAgeRange = {1, 0.6, 0.3, 0.1, 0.0, 0.0, 0.0, 0.0};

	final static int DAY_TO_START_ANOMALY = 26;

	final static int CHANGE_TO_PERCENTAGE = 100;
	
	final static String OUTPUT_FILE_NAME      = "OMS-BUC-DEMO-DATA.csv";

	final static String NODES_INPUT_FILE      = "Nodes.json";
	final static String WORKSPACES_INPUT_FILE = "Workspaces.json";
	final static String ALERTS_INPUT_FILE     = "Alerts.json";

	/////////////////////////////////////////////////////////////
	// DO NOT CHANGE
	static Random randomNumberGenerator = new Random();
	static public StringBuilder sb = new StringBuilder();
	private static final String COMMA = ",";
	private static final String SEPARATOR = "|";

	private static Map<String, Node> nodeMap = new HashMap<>();

	static int severityTypeFillRate = 10;
	static int severityTypeSlaRate = 10;
	static int severityTypeUtilizationRateSFS = 10;
	static int severityTypeUtilizationRateBOPIS = 10;

	static List<String> alertsFillRate = new ArrayList<>();
	static List<String> alertsSlaRate = new ArrayList<>();
	static List<String> alertsUtilizationRateSFS = new ArrayList<>();
	static List<String> alertsUtilizationRateBOPIS = new ArrayList<>();
	
	static FileOutputStream fileOutputStream;

	/*
	 * java -classpath bin;C:\Users\HOWARDBorenstein\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-databind\2.8.9\4dfca3975be3c1a98eacb829e70f02e9a71bc159\jackson-databind-2.8.9.jar;C:\Users\HOWARDBorenstein\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-core\2.8.9\569b1752705da98f49aabe2911cc956ff7d8ed9d\jackson-core-2.8.9.jar;C:\Users\HOWARDBorenstein\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-annotations\2.8.9\e0e758381a6579cb2029dace23a7209b90ac7232\jackson-annotations-2.8.9.jar;C:\Users\HOWARDBorenstein\.gradle\caches\modules-2\files-2.1\org.apache.commons\commons-lang3\3.3.2\90a3822c38ec8c996e84c16a3477ef632cbc87a3\commons-lang3-3.3.2.jar GenerateOMSBUCMetricData abc.csv
	 */
	/**
	 * Run this program to output CSV data for OMS BUS Demo Data Load
	 * @param args
	 */
	public static void main(String[] args) {

		String fileName = OUTPUT_FILE_NAME;
		if (args.length > 0) {
			fileName = args[0];
		}
		File outputFile = new File(fileName);
		try {
			fileOutputStream = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			// this is a problem
			System.out.println(e);
		}

		NodeInputList nodeInputList = null;
		WorkspaceInputList workspacesInputList = null;
		AlertInputList alertsInputList = null;

		File nodesInputFile = new File(NODES_INPUT_FILE);
		if (nodesInputFile.isFile()){

			ObjectMapper mapper = new ObjectMapper();
			try {
				nodeInputList = mapper.readValue(nodesInputFile, NodeInputList.class);
			} catch (IOException e) {
				// this is a problem
				System.out.println(e);
			}
		}

		File workspacesInputFile = new File(WORKSPACES_INPUT_FILE);
		if (workspacesInputFile.isFile()){
			ObjectMapper mapper = new ObjectMapper();
			try {
				workspacesInputList = mapper.readValue(workspacesInputFile, WorkspaceInputList.class);
			} catch (IOException e) {
				// this is a problem
				System.out.println(e);
			}
		}

		File alertsInputFile = new File(ALERTS_INPUT_FILE);
		if (alertsInputFile.isFile()){
			ObjectMapper mapper = new ObjectMapper();
			try {
				alertsInputList = mapper.readValue(alertsInputFile, AlertInputList.class);
				
				for (AlertInput alertInput : alertsInputList.getAlerts()) {
					if ("fill-rate".equals(alertInput.getType())) {
						alertsFillRate = alertInput.getNodeIds();
					} else if ("sla-rate".equals(alertInput.getType())) {
						alertsSlaRate = alertInput.getNodeIds();
					} else if ("utilization-rate-ship".equals(alertInput.getType())) {
						alertsUtilizationRateSFS = alertInput.getNodeIds();
					} else if ("utilization-rate-pickup".equals(alertInput.getType())) {
						alertsUtilizationRateBOPIS = alertInput.getNodeIds();
					}
				}
			} catch (IOException e) {
				// this is a problem
				System.out.println(e);
			}
		}

		StringTokenizer st = new StringTokenizer(thresholdRuleFillRateStore, SEPARATOR);
		severityTypeFillRate = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(thresholdRuleSlaRateStore, SEPARATOR);
		severityTypeSlaRate = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(thresholdRuleUtilizationRateSFSStore, SEPARATOR);
		severityTypeUtilizationRateSFS = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(thresholdRuleUtilizationRateBOPISStore, SEPARATOR);
		severityTypeUtilizationRateBOPIS = Integer.valueOf(st.nextToken());

		printLine("Location","Key","Value",new StringBuilder("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30"));

		/*
		 * graphs, endDate, 2019-03-20
		 */
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		printLine("graphs","endDate", sdf.format(new Date()));

		List<String> allNodeIds = new ArrayList<>();

		Node node;

		// get all the nodes
		if (nodeInputList != null) {
			for (NodeInput nodeInput : nodeInputList.getNodes()) {
				String id = nodeInput.getId();
				String nodeName = nodeInput.getName();
				String nodeType = nodeInput.getType();
				String sfsCapacity = nodeInput.getSfsCapacity();
				String bopisCapacity = nodeInput.getBopisCapacity();

				node = new Node(id, nodeName, nodeType, Integer.valueOf(sfsCapacity), Integer.valueOf(bopisCapacity));
				allNodeIds.add(id);
				nodeMap.put(id,  node);
			}
		}

		// print list and details data for all nodes
		for (String nodeId: allNodeIds) {
			Node n = nodeMap.get(nodeId);

			String id = n.id;
			String nodeName = n.name;

			/*
node_widget, Warden Plaza, Warden Plaza
node_widget, Yorkdale Centre, Yorkdale Centre
node_widget, Auro_Store_3, Aurora - San Francisco
node_widget, Auro_Store_1, Aurora - Boston
node_widget, Auro_Store_95, Aurora - The Galleria
node_widget, Vancouver Mall, Vancouver Mall
node_widget, Auro_Store_500, Aurora - Lowell
node_widget, Auro_Store_198, Aurora - Grand Central
			 */
			printLine("node_widget", id, nodeName);

			int maxSeverity = 10;
			if (alertsFillRate.contains(id)) {
				maxSeverity = Math.max(severityTypeFillRate, maxSeverity);
			}
			if (alertsSlaRate.contains(id)) {
				maxSeverity = Math.max(severityTypeSlaRate, maxSeverity);
			}
			if (alertsUtilizationRateSFS.contains(id)) {
				maxSeverity = Math.max(severityTypeUtilizationRateSFS, maxSeverity);
			}
			if (alertsUtilizationRateBOPIS.contains(id)) {
				maxSeverity = Math.max(severityTypeUtilizationRateBOPIS, maxSeverity);
			}
			/*
node_list_Warden Plaza, maxAlertSeverity, 30
			 */
			printLine("node_list_" + id, "maxAlertSeverity", maxSeverity);
			n.printNodeInfo();
		}


		// print widget and period data for all nodes
		printWorkspaceInfo("", allNodeIds);

		if (workspacesInputList != null) {
			int workspaceCounter = 0;
			// print widget and period data for each workspace
			for (WorkspaceInput workspaceInput : workspacesInputList.getWorkspaceNodeIds()) {
				workspaceCounter++;

				String workspaceId = String.valueOf(workspaceCounter);

				List<String> workspaceNodeIds = workspaceInput.getNodeIds();

				Collections.sort(workspaceNodeIds);
				StringBuilder nodes = new StringBuilder();
				for (String id: workspaceNodeIds) {
					if (nodes.length() > 0) {
						nodes.append(SEPARATOR);
					}
					nodes.append(id);
				}

				printLine("workspace", nodes.toString(), workspaceId);			
				printWorkspaceInfo(workspaceId, workspaceNodeIds);
			}
		}

		try {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		} catch (IOException e) {
			// this is a problem
			System.out.println(e);
		}
	}

	public static void printWorkspaceInfo(String workspaceId, List<String> nodeIds) {

		int workspaceTotalSentToday = 0;
		int workspaceTotalSentSevenDays = 0;
		int workspaceTotalSentThirtyDays = 0;
		int workspaceTotalPendingToday = 0;
		int workspaceTotalPendingSevenDays = 0;
		int workspaceTotalPendingThirtyDays = 0;

		List<Node> nodesList = new ArrayList<>();

		for (String nodeId : nodeIds) {

			Node n = nodeMap.get(nodeId);
			nodesList.add(n);

			workspaceTotalSentToday += n.getTotalSent(1);
			workspaceTotalSentSevenDays += n.getTotalSent(7);
			workspaceTotalSentThirtyDays += n.getTotalSent(30);
			workspaceTotalPendingToday += n.getTotalPending(1);
			workspaceTotalPendingSevenDays += n.getTotalPending(7);
			workspaceTotalPendingThirtyDays += n.getTotalPending(30);
		}

		/*
node_widget_fillRate_today, nodes, Warden Plaza|Yorkdale Centre|Auro_Store_95|Auro_Store_198
node_widget_fillRate_7days, nodes, Warden Plaza|Yorkdale Centre|Auro_Store_95|Auro_Store_198
node_widget_fillRate_30days, nodes, Warden Plaza|Yorkdale Centre|Auro_Store_95|Auro_Store_198

node_widget_-fillRate_today, nodes, Auro_Store_500|Auro_Store_1|Vancouver Mall|Auro_Store_3
node_widget_-fillRate_7days, nodes, Auro_Store_500|Auro_Store_1|Vancouver Mall|Auro_Store_3
node_widget_-fillRate_30days, nodes, Auro_Store_500|Auro_Store_1|Vancouver Mall|Auro_Store_3

node_widget_utilShipFromStore_today, nodes, Auro_Store_500|Yorkdale Centre|Auro_Store_1|Auro_Store_198
node_widget_utilShipFromStore_7days, nodes, Auro_Store_198|Auro_Store_95|Vancouver Mall|Auro_Store_3
node_widget_utilShipFromStore_30days, nodes, Yorkdale Centre|Warden Plaza|Vancouver Mall|Auro_Store_1

node_widget_utilPickupInStore_today, nodes, Auro_Store_500|Yorkdale Centre|Warden Plaza|Auro_Store_198
node_widget_utilPickupInStore_7days, nodes, Auro_Store_500|Warden Plaza|Auro_Store_198|Auro_Store_3
node_widget_utilPickupInStore_30days, nodes, Auro_Store_198|Yorkdale Centre|Vancouver Mall|Auro_Store_3
		 */
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "fillRate", "today", "getFillRateToday", 0);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "fillRate", "7days", "getFillRateSevenDays", 0);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "fillRate", "30days", "getFillRateThirtyDays", 0);

		printNodeListPerSortAndPeriod(workspaceId, nodesList, "-fillRate", "today", "getFillRateToday", 1);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "-fillRate", "7days", "getFillRateSevenDays", 1);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "-fillRate", "30days", "getFillRateThirtyDays", 1);

		printNodeListPerSortAndPeriod(workspaceId, nodesList, "utilShipFromStore", "today", "getUtilizationRateSFSToday", 0);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "utilShipFromStore", "7days", "getUtilizationRateSFSSevenDays", 0);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "utilShipFromStore", "30days", "getUtilizationRateSFSThirtyDays", 0);

		printNodeListPerSortAndPeriod(workspaceId, nodesList, "utilPickupInStore", "today", "getUtilizationRateBOPISToday", 0);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "utilPickupInStore", "7days", "getUtilizationRateBOPISSevenDays", 0);
		printNodeListPerSortAndPeriod(workspaceId, nodesList, "utilPickupInStore", "30days", "getUtilizationRateBOPISThirtyDays", 0);

		/*
delivery_method_widget_7days, shipFromWarehouse, 620
delivery_method_widget_7days, shipFromStore, 70
delivery_method_widget_7days, pickupFromStore, 250
delivery_method_widget_7days, deliveryFromStore, 60

delivery_method_widget_30days, shipFromWarehouse, 6710
delivery_method_widget_30days, shipFromStore, 472
delivery_method_widget_30days, pickupFromStore, 2200
delivery_method_widget_30days, deliveryFromStore, 418 
		 */
		int shipFromWarehouse = Double.valueOf(workspaceTotalSentSevenDays * getRandomPercentage(rangeShipFromWarehouse)).intValue();
		int shipFromStore = Double.valueOf((workspaceTotalSentSevenDays - shipFromWarehouse) * getRandomPercentage(rangeShipFromWarehouse)).intValue();
		int pickupFromStore = Double.valueOf((workspaceTotalSentSevenDays - shipFromWarehouse - shipFromStore) * getRandomPercentage(rangeShipFromWarehouse)).intValue();
		int deliveryFromStore = workspaceTotalSentSevenDays - shipFromWarehouse - shipFromStore - pickupFromStore;
		printLine(workspaceId + "delivery_method_widget_7days", "shipFromWarehouse", shipFromWarehouse);
		printLine(workspaceId + "delivery_method_widget_7days", "shipFromStore", shipFromStore);
		printLine(workspaceId + "delivery_method_widget_7days", "pickupFromStore", pickupFromStore);
		printLine(workspaceId + "delivery_method_widget_7days", "deliveryFromStore", deliveryFromStore);

		shipFromWarehouse = Double.valueOf(workspaceTotalSentThirtyDays * getRandomPercentage(rangeShipFromWarehouse)).intValue();
		shipFromStore = Double.valueOf((workspaceTotalSentThirtyDays - shipFromWarehouse) * getRandomPercentage(rangeShipFromWarehouse)).intValue();
		pickupFromStore = Double.valueOf((workspaceTotalSentThirtyDays - shipFromWarehouse - shipFromStore) * getRandomPercentage(rangeShipFromWarehouse)).intValue();
		deliveryFromStore = workspaceTotalSentThirtyDays - shipFromWarehouse - shipFromStore - pickupFromStore;
		printLine(workspaceId + "delivery_method_widget_30days", "shipFromWarehouse", shipFromWarehouse);
		printLine(workspaceId + "delivery_method_widget_30days", "shipFromStore", shipFromStore);
		printLine(workspaceId + "delivery_method_widget_30days", "pickupFromStore", pickupFromStore);
		printLine(workspaceId + "delivery_method_widget_30days", "deliveryFromStore", deliveryFromStore);

		/*
order_age_widget, ASSIGNED_0, 200
order_age_widget, ASSIGNED_1, 150
order_age_widget, ASSIGNED_2, 75
order_age_widget, ASSIGNED_3, 20
order_age_widget, ASSIGNED_4, 5
order_age_widget, ASSIGNED_5, 0
order_age_widget, ASSIGNED_6, 0
order_age_widget, ASSIGNED_7, 0
order_age_widget, PENDING_0, 100
order_age_widget, PENDING_1, 10
order_age_widget, PENDING_2, 2
order_age_widget, PENDING_3, 0
order_age_widget, PENDING_4, 0
order_age_widget, PENDING_5, 0
order_age_widget, PENDING_6, 0
order_age_widget, PENDING_7, 0
		 */
		for (int k = 0; k < orderAgeRange.length; k++) {
			printLine(workspaceId + "order_age_widget", "ASSIGNED_" + k, Double.valueOf(workspaceTotalSentToday * orderAgeRange[k]).intValue());
			printLine(workspaceId + "order_age_widget", "PENDING_" + k, Double.valueOf(workspaceTotalPendingToday * orderAgeRange[k]).intValue());
		}

		printWorkspaceAlerts(workspaceId, nodeIds, "node", alertsFillRate, "fill-rate", thresholdRuleFillRateStore, thresholdRuleFillRateWarehouse);
		printWorkspaceAlerts(workspaceId, nodeIds, "node", alertsSlaRate, "sla-rate", thresholdRuleSlaRateStore, thresholdRuleSlaRateWarehouse);
		printWorkspaceAlerts(workspaceId, nodeIds, "node", alertsUtilizationRateSFS, "utilization-rate-ship", thresholdRuleUtilizationRateSFSStore, thresholdRuleUtilizationRateSFSWarehouse);
		printWorkspaceAlerts(workspaceId, nodeIds, "node", alertsUtilizationRateBOPIS, "utilization-rate-pickup", thresholdRuleUtilizationRateBOPISStore, thresholdRuleUtilizationRateBOPISWarehouse);

	}

	public static void printWorkspaceAlerts(String workspaceId, List<String> nodeIds, String obj, List<String> alertsList, String type, String ruleStore, String ruleWarehouse) {

		/*
node_alert_Warden Plaza,    fill-rate, 30|below|80|1
node_alert_Yorkdale Centre, sla-rate, 30|below|85|2
node_alert_Auro_Store_3,    sla-rate, 30|below|85|2
node_alert_Vancouver Mall,  utilization-rate-ship, 20|below|80|2
node_alert_Auro_Store_500,  utilization-rate-pickup, 20|below|80|2
node_alert_Auro_Store_198,  utilization-rate-pickup, 20|below|80|2

,,2)For each alert type, specify the list of nodes with that alert
,,Location:alerts
,,Key values: fill-rate  sla-rate  overcapacity  utilization-rate-ship  utilization-rate-pickup  progress-risk  sla-risk
,,Value format:nodeId1|nodeId2|nodeId3

alerts, fill-rate, Warden Plaza
alerts, sla-rate, Yorkdale Centre|Auro_Store_3
alerts, utilization-rate-ship, Vancouver Mall
alerts, utilization-rate-pickup, Auro_Store_500|Auro_Store_198 

alert_widget, node-alert_fill-rate, 1
alert_widget, node-alert_fill-rate_id, Warden Plaza
alert_widget, node-alert_sla-rate, 2
alert_widget, node-alert_overcapacity, 0
alert_widget, node-alert_utilization-rate-ship, 1
alert_widget, node-alert_utilization-rate-ship_id, Vancouver Mall
alert_widget, node-alert_utilization-rate-pickup, 2
alert_widget, order-alert_sla-risk, 2
alert_widget, order-alert_progress-risk, 4
		 */
		if (workspaceId.equals("")) {
			// only do this for all nodes
			StringBuilder nodes = new StringBuilder();
			for (String id : alertsList) {
				if (nodeMap.get(id).getType().equals("store")) {
					printLine(obj + "_alert_" + id, type, ruleStore);
				} else {
					printLine(obj + "_alert_" + id, type, ruleWarehouse);
				}
				if (nodes.length() > 0) {
					nodes.append(SEPARATOR);
				}
				nodes.append(id);
			}

			if (!alertsList.isEmpty()) {
				printLine("alerts", type, nodes.toString());
			}
		}

		if (!alertsList.isEmpty()) {
			List<String> alertsListCopy = new ArrayList<>(alertsList);
			alertsListCopy.retainAll(nodeIds);

			if (!alertsListCopy.isEmpty()) {
				printLine(workspaceId + "alert_widget", obj + "-alert_" + type, alertsListCopy.size());
				if (alertsListCopy.size() == 1) {
					printLine(workspaceId + "alert_widget", obj + "-alert_" + type + "_id", alertsListCopy.get(0));
				}
			}
		}
	}


	/*
node_widget_fillRate_today, nodes, Warden Plaza|Yorkdale Centre|Auro_Store_95|Auro_Store_198
node_widget_fillRate_7days, nodes, Warden Plaza|Yorkdale Centre|Auro_Store_95|Auro_Store_198
node_widget_fillRate_30days, nodes, Warden Plaza|Yorkdale Centre|Auro_Store_95|Auro_Store_198

node_widget_-fillRate_today, nodes, Auro_Store_500|Auro_Store_1|Vancouver Mall|Auro_Store_3
node_widget_-fillRate_7days, nodes, Auro_Store_500|Auro_Store_1|Vancouver Mall|Auro_Store_3
node_widget_-fillRate_30days, nodes, Auro_Store_500|Auro_Store_1|Vancouver Mall|Auro_Store_3

node_widget_utilShipFromStore_today, nodes, Auro_Store_500|Yorkdale Centre|Auro_Store_1|Auro_Store_198
node_widget_utilShipFromStore_7days, nodes, Auro_Store_198|Auro_Store_95|Vancouver Mall|Auro_Store_3
node_widget_utilShipFromStore_30days, nodes, Yorkdale Centre|Warden Plaza|Vancouver Mall|Auro_Store_1

node_widget_utilPickupInStore_today, nodes, Auro_Store_500|Yorkdale Centre|Warden Plaza|Auro_Store_198
node_widget_utilPickupInStore_7days, nodes, Auro_Store_500|Warden Plaza|Auro_Store_198|Auro_Store_3
node_widget_utilPickupInStore_30days, nodes, Auro_Store_198|Yorkdale Centre|Vancouver Mall|Auro_Store_3
	 */
	public static void printNodeListPerSortAndPeriod(String workspaceId, List<Node> workspaceNodes, String sort, String period, String method, Integer order) {
		OmsNodeComparator comp = new OmsNodeComparator(Node.class, Pair.of(method, order));
		workspaceNodes.sort(comp);

		StringBuilder nodeIds = new StringBuilder();

		int count = 0;
		for (Node node : workspaceNodes) {
			count++;
			// widget only shows 4 nodes
			if (count >= 5) {
				break;
			}
			if (nodeIds.length() > 0) {
				nodeIds.append(SEPARATOR);
			}
			nodeIds.append(node.id);
		}

		printLine(workspaceId + "node_widget_" + sort + "_" + period, "nodes", nodeIds.toString());
	}

	public static class MetricsForPeriod {
		private String id;
		private String period;

		private Metrics summary;
		private List<Metrics> metricsList;

		public MetricsForPeriod(String id, int sfsCapacity, int bopisCapacity, int days, String period) {
			this.id = id;
			this.period = period;

			// get the overall metrics for the period
			// (pass DAY_TO_START_ANOMALY + 1 for current day to get alerts set up if necessary)
			summary = new Metrics(id, sfsCapacity, bopisCapacity, days, DAY_TO_START_ANOMALY + 1);
		}

		public MetricsForPeriod(String id, int sfsCapacity, int bopisCapacity, int days, String period, List<Metrics> metricsList) {
			this.id = id;
			this.period = period;

			// get the overall metrics for the period
			// (pass DAY_TO_START_ANOMALY + 1 for current day to get alerts set up if necessary)
			this.summary = new Metrics(id, sfsCapacity, bopisCapacity, days, DAY_TO_START_ANOMALY + 1);
			this.metricsList = metricsList;
		}


		public void printInfo() {
			summary.printSummaryInfo();

			if (metricsList == null || metricsList.isEmpty()) {
				// print today info

				/*
fillRateGraph,     Warden Plaza, today, 0, 0, 0, 0, 0, 0, 5, 10, 15, 25, 40, 50, 65, 65, 72, 75, 75, 75, 75, 75, 75, 75, 75, 75
utilShipRateGraph, Warden Plaza, today, 0, 0, 0, 0, 0, 0, 5, 10, 15, 25, 40, 50, 65, 65, 72, 75, 75, 75, 77, 87, 87, 87, 87, 87
utilPickRateGraph, Warden Plaza, today, 0, 0, 0, 0, 0, 0, 5, 10, 15, 25, 40, 50, 65, 65, 72, 75, 75, 75, 80, 85, 88, 88, 88, 88

shipStoreCapGraph,     Warden Plaza, today, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200
shipStoreSentGraph,    Warden Plaza, today, 0, 0, 0, 0, 0, 0, 0, 10, 15, 5, 10, 0, 5, 5, 10, 10, 15, 20, 30, 40, 0, 0, 0, 0
shipStoreFulFillGraph, Warden Plaza, today, 0, 0, 0, 0, 0, 0, 0,  8, 10, 5,  5, 0, 1, 4,  8,  8, 15, 18, 25, 23, 0, 0, 0, 0

pickCapGraph,          Warden Plaza, today, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200
pickSentGraph,         Warden Plaza, today, 0, 0, 0, 0, 0, 0, 0, 10, 15, 5, 10, 0, 5, 5, 10, 10, 15, 20, 30, 40, 0, 0, 0, 0
pickFulFillGraph,      Warden Plaza, today, 0, 0, 0, 0, 0, 0, 0,  2, 15, 5,  2, 0, 1, 2,  7,  6, 15, 10, 15, 40, 0, 0, 0, 0
				 */				
				StringBuilder fillRateGraph = new StringBuilder();
				StringBuilder utilShipRateGraph = new StringBuilder();
				StringBuilder utilPickRateGraph = new StringBuilder();

				StringBuilder shipStoreCapGraph = new StringBuilder();
				StringBuilder shipStoreSentGraph = new StringBuilder();
				StringBuilder shipStoreFulFillGraph = new StringBuilder();

				StringBuilder pickCapGraph = new StringBuilder();
				StringBuilder pickSentGraph = new StringBuilder();
				StringBuilder pickFulFillGraph = new StringBuilder();

				for (int i = 0; i < 7; i++) {
					fillRateGraph.append(0).append(COMMA);
					utilShipRateGraph.append(0).append(COMMA);
					utilPickRateGraph.append(0).append(COMMA);

					shipStoreCapGraph.append(summary.capacitySFS).append(COMMA);
					shipStoreSentGraph.append(0).append(COMMA);
					shipStoreFulFillGraph.append(0).append(COMMA);

					pickCapGraph.append(summary.capacityBOPIS).append(COMMA);
					pickSentGraph.append(0).append(COMMA);
					pickFulFillGraph.append(0).append(COMMA);
				}
				double fillRateTotal = 0;
				double fillRateSFS = 0;
				double fillRateBOPIS = 0;

				double utilizationRateTotal = 0;
				double utilizationRateSFS = 0;
				double utilizationRateBOPIS = 0;

				int sentTotal = 0;
				int sentSFS = 0;
				int sentBOPIS = 0;

				int pendingTotal = 0;
				int pendingSFS = 0;
				int pendingBOPIS = 0;

				int fulfilledTotal = 0;
				int fulfilledSFS = 0;
				int fulfilledBOPIS = 0;

				for (int i = 7; i < 24; i++) {
					fillRateTotal += getRandomNumber(rangeTodayMetrics);
					if (fillRateTotal > summary.fillRateTotal*CHANGE_TO_PERCENTAGE) {
						fillRateTotal = summary.fillRateTotal*CHANGE_TO_PERCENTAGE;
					}
					fillRateGraph.append(fillRateTotal).append(COMMA);

					utilizationRateSFS += getRandomNumber(rangeTodayMetrics);
					if (utilizationRateSFS > summary.utilizationRateSFS*CHANGE_TO_PERCENTAGE) {
						utilizationRateSFS = summary.utilizationRateSFS*CHANGE_TO_PERCENTAGE;
					}
					utilShipRateGraph.append(utilizationRateSFS).append(COMMA);

					utilizationRateBOPIS += getRandomNumber(rangeTodayMetrics);
					if (utilizationRateBOPIS > summary.utilizationRateBOPIS*CHANGE_TO_PERCENTAGE) {
						utilizationRateBOPIS = summary.utilizationRateBOPIS*CHANGE_TO_PERCENTAGE;
					}
					utilPickRateGraph.append(utilizationRateBOPIS).append(COMMA);

					shipStoreCapGraph.append(summary.capacitySFS).append(COMMA);

					sentSFS += getRandomNumber(rangeTodayMetrics);
					if (sentSFS > summary.sentSFS) {
						sentSFS = summary.sentSFS;
					}
					shipStoreSentGraph.append(sentSFS).append(COMMA);

					fulfilledSFS += getRandomNumber(rangeTodayMetrics);
					if (fulfilledSFS > summary.fulfilledSFS) {
						fulfilledSFS = summary.fulfilledSFS;
					}
					shipStoreFulFillGraph.append(fulfilledSFS).append(COMMA);

					pickCapGraph.append(summary.capacityBOPIS).append(COMMA);

					sentBOPIS += getRandomNumber(rangeTodayMetrics);
					if (sentBOPIS > summary.sentBOPIS) {
						sentBOPIS = summary.sentBOPIS;
					}
					pickSentGraph.append(sentBOPIS).append(COMMA);

					fulfilledBOPIS += getRandomNumber(rangeTodayMetrics);
					if (fulfilledBOPIS > summary.fulfilledBOPIS) {
						fulfilledBOPIS = summary.fulfilledBOPIS;
					}
					pickFulFillGraph.append(fulfilledBOPIS).append(COMMA);
				}

				printLine("fillRateGraph", id, period, fillRateGraph);
				printLine("utilShipRateGraph", id, period, utilShipRateGraph);
				printLine("utilPickRateGraph", id, period, utilPickRateGraph);

				printLine("shipStoreCapGraph", id, period, shipStoreCapGraph);
				printLine("shipStoreSentGraph", id, period, shipStoreSentGraph);
				printLine("shipStoreFulFillGraph", id, period, shipStoreFulFillGraph);

				printLine("pickCapGraph", id, period, pickCapGraph);
				printLine("pickSentGraph", id, period, pickSentGraph);
				printLine("pickFulFillGraph", id, period, pickFulFillGraph);
			}

			// print the graph info 7days or 30days graph info
			if (metricsList != null) {
				/*
				fillRateGraph,     Warden Plaza, 30days, 79, 77, 81, 77, 87, 81, 85, 82, 81, 80, 87, 79, 86, 85, 88, 82, 91, 90, 80, 84, 85, 80, 80, 72, 85, 87, 80, 79, 77, 74
				utilShipRateGraph, Warden Plaza, 30days, 85, 82, 81, 82, 91, 90, 80, 79, 77, 81, 77, 80, 87, 79, 86, 85, 88, 87, 81, 84, 85, 80, 80, 90, 91, 89, 88, 87, 84, 92
				utilPickRateGraph, Warden Plaza, 30days, 85, 82, 81, 80, 87, 79, 86, 85, 88, 82, 81, 77, 87, 81, 84, 91, 90, 80, 79, 77, 85, 80, 80, 80, 85, 85, 87, 84, 84, 85

				shipStoreCapGraph,     Warden Plaza, 30days, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200
				shipStoreSentGraph,    Warden Plaza, 30days, 180, 170, 160, 165, 180, 190, 195, 180, 180, 160, 188, 175, 186, 175, 188, 177, 175, 180, 185, 190, 185, 186, 170, 180, 190, 170, 160, 150, 175, 185
				shipStoreFulFillGraph, Warden Plaza, 30days, 150, 120, 155, 160, 175, 188, 195, 170, 160, 158, 180, 170, 180, 170, 186, 170, 170, 175, 180, 188, 180, 175, 150, 170, 170, 155, 130, 120, 172, 183
				pickCapGraph,          Warden Plaza, 30days, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200
				pickSentGraph,         Warden Plaza, 30days, 180, 190, 195, 180, 188, 175, 186, 175, 188, 177, 175, 180, 180, 170, 160, 160, 188, 175, 190, 195, 180, 180, 170, 160, 185, 180, 190, 195, 180, 170
				pickFulFillGraph,      Warden Plaza, 30days, 150, 120, 155, 160, 175, 158, 185, 170, 160, 158, 160, 170, 180, 170, 156, 150, 170, 175, 180, 188, 180, 175, 150, 150, 180, 180, 185, 175, 160, 150

				 */				
				StringBuilder fillRateGraph = new StringBuilder();
				StringBuilder utilShipRateGraph = new StringBuilder();
				StringBuilder utilPickRateGraph = new StringBuilder();

				StringBuilder shipStoreCapGraph = new StringBuilder();
				StringBuilder shipStoreSentGraph = new StringBuilder();
				StringBuilder shipStoreFulFillGraph = new StringBuilder();

				StringBuilder pickCapGraph = new StringBuilder();
				StringBuilder pickSentGraph = new StringBuilder();
				StringBuilder pickFulFillGraph = new StringBuilder();

				for (Metrics metric : metricsList) {
					fillRateGraph.append(metric.fillRateTotal*CHANGE_TO_PERCENTAGE).append(COMMA);
					utilShipRateGraph.append(metric.utilizationRateSFS*CHANGE_TO_PERCENTAGE).append(COMMA);
					utilPickRateGraph.append(metric.utilizationRateBOPIS*CHANGE_TO_PERCENTAGE).append(COMMA);

					shipStoreCapGraph.append(metric.capacitySFS).append(COMMA);
					shipStoreSentGraph.append(metric.sentSFS).append(COMMA);
					shipStoreFulFillGraph.append(metric.fulfilledSFS).append(COMMA);

					pickCapGraph.append(metric.capacityBOPIS).append(COMMA);
					pickSentGraph.append(metric.sentBOPIS).append(COMMA);
					pickFulFillGraph.append(metric.fulfilledBOPIS).append(COMMA);
				}

				printLine("fillRateGraph", id, period, fillRateGraph);
				printLine("utilShipRateGraph", id, period, utilShipRateGraph);
				printLine("utilPickRateGraph", id, period, utilPickRateGraph);

				printLine("shipStoreCapGraph", id, period, shipStoreCapGraph);
				printLine("shipStoreSentGraph", id, period, shipStoreSentGraph);
				printLine("shipStoreFulFillGraph", id, period, shipStoreFulFillGraph);

				printLine("pickCapGraph", id, period, pickCapGraph);
				printLine("pickSentGraph", id, period, pickSentGraph);
				printLine("pickFulFillGraph", id, period, pickFulFillGraph);
			}
		}
	}

	public static class Metrics {

		private String id;
		private int days;

		private int capacitySFS;
		private int capacityBOPIS;

		public double fillRateTotal;
		private double fillRateSFS;
		private double fillRateBOPIS;

		private double utilizationRateTotal;
		public double utilizationRateSFS;
		public double utilizationRateBOPIS;

		public int sentTotal;
		public int sentSFS;
		public int sentBOPIS;

		public int pendingTotal;
		private int pendingSFS;
		private int pendingBOPIS;

		private int fulfilledTotal;
		public int fulfilledSFS;
		public int fulfilledBOPIS;

		public Metrics(String id, int sfsCapacity, int bopisCapacity, int days, int currentDay) {
			this.id = id;
			this.days = days;

			this.capacitySFS = sfsCapacity;
			this.capacityBOPIS = bopisCapacity;

			sfsCapacity = sfsCapacity * days;
			bopisCapacity = bopisCapacity * days;

			int totalCapacity = sfsCapacity + bopisCapacity;

			// utilization rate = sent / capacity
			// randomly pick a utilization rate
			// currentDay > DAY_TO_START_ANOMALY  --- anomaly only for last few days
			if (currentDay > DAY_TO_START_ANOMALY && (alertsUtilizationRateSFS.contains(id) || alertsUtilizationRateBOPIS.contains(id))) {
				utilizationRateSFS = getRandomPercentage(rangeAlertNodeUtilizationRate);
				utilizationRateBOPIS = getRandomPercentage(rangeAlertNodeUtilizationRate);
			} else {
				utilizationRateSFS = getRandomPercentage(rangeNormalNodeUtilizationRate);
				utilizationRateBOPIS = getRandomPercentage(rangeNormalNodeUtilizationRate);
			}

			// now we can figure out the sent total
			sentSFS = Double.valueOf(utilizationRateSFS * sfsCapacity).intValue();
			sentBOPIS = Double.valueOf(utilizationRateBOPIS * bopisCapacity).intValue();

			// fill rate = fulfilled / sent
			// randomly pick a fill rate
			// currentDay > DAY_TO_START_ANOMALY  --- anomaly only for last few days
			if (currentDay > DAY_TO_START_ANOMALY && alertsFillRate.contains(id)) {
				fillRateSFS = getRandomPercentage(rangeAlertNodeFillRate);
				fillRateBOPIS = getRandomPercentage(rangeAlertNodeFillRate);
			} else {
				fillRateSFS = getRandomPercentage(rangeNormalNodeFillRate);
				fillRateBOPIS = getRandomPercentage(rangeNormalNodeFillRate);
			}

			// now we can figure out the fulfilled value
			fulfilledSFS = Double.valueOf(fillRateSFS * sentSFS).intValue();
			fulfilledBOPIS = Double.valueOf(fillRateBOPIS * sentBOPIS).intValue();

			pendingSFS = sentSFS - fulfilledSFS;
			pendingBOPIS = sentBOPIS - fulfilledBOPIS;

			// now we have to calculate the total
			sentTotal = sentSFS + sentBOPIS;
			pendingTotal = pendingSFS + pendingBOPIS;
			fulfilledTotal = fulfilledSFS + fulfilledBOPIS;

			utilizationRateTotal = Double.valueOf(sentTotal)/totalCapacity;
			fillRateTotal = Double.valueOf(fulfilledTotal) / sentTotal;
		}

		/*
		node_list_Warden Plaza, maxAlertSeverity, 30
		node_list_Warden Plaza, todayPending, 50
		node_list_Warden Plaza, todayPendingByDelType/0/qty, 50
		node_list_Warden Plaza, todayPendingByDelType/1/qty, 50

		node_list_Warden Plaza_today, fillRatePerc, 75
		node_list_Warden Plaza_today, utilShipPerc, 87
		node_list_Warden Plaza_today, utilPickPerc, 88
		node_list_Warden Plaza_today, sent, 78
		node_list_Warden Plaza_today, sentByDelType/0/qty, 78
		node_list_Warden Plaza_today, sentByDelType/1/qty, 78	

		node_details_Warden Plaza_30days, fillRatePerc, 85
		node_details_Warden Plaza_30days, shipMetric/fillRatePerc, 87
		node_details_Warden Plaza_30days, shipMetric/utilRatePerc, 90
		node_details_Warden Plaza_30days, pickMetric/fillRatePerc, 86
		node_details_Warden Plaza_30days, pickMetric/utilRatePerc, 85

node_details_Warden Plaza_30days, summary/0/value, 10000
node_details_Warden Plaza_30days, summary/1/value, 7568
node_details_Warden Plaza_30days, summary/2/value, 654
node_details_Warden Plaza_30days, summary/3/value, 1569

node_details_Warden Plaza_30days, summary/0/percentage, 50.5
node_details_Warden Plaza_30days, summary/1/percentage, 38.6
node_details_Warden Plaza_30days, summary/2/percentage, 3
node_details_Warden Plaza_30days, summary/3/percentage, 7.9
		 */
		public void printSummaryInfo() {
			String location = "node_list_" + id;
			StringBuffer sb = new StringBuffer();

			if (days == 1) {

				printLine(location, "todayPending", pendingTotal);
				printLine(location, "todayPendingByDelType/0/qty", pendingSFS);
				printLine(location, "todayPendingByDelType/0/qty", pendingBOPIS);
			}

			if (days == 1) {
				location = "node_list_" + id + "_today";
			} else if (days == 7) {
				location = "node_list_" + id + "_7days";
			} else {
				location = "node_list_" + id + "_30days";
			}

			printLine(location, "fillRatePerc", fillRateTotal*CHANGE_TO_PERCENTAGE);
			printLine(location, "utilShipPerc", utilizationRateSFS*CHANGE_TO_PERCENTAGE);
			printLine(location, "utilPickPerc", utilizationRateBOPIS*CHANGE_TO_PERCENTAGE);
			printLine(location, "sent", sentTotal);
			printLine(location, "sentByDelType/0/qty", sentSFS);
			printLine(location, "sentByDelType/1/qty", sentBOPIS);

			int slaMet = Double.valueOf(getRandomPercentage(rangeSlaMet) * sentTotal).intValue();
			int slaNotMet = Double.valueOf(getRandomPercentage(rangeSlaMet) * (sentTotal - slaMet)).intValue();
			int pending = Double.valueOf(getRandomPercentage(rangePending) * (sentTotal - slaMet - slaNotMet)).intValue();
			int pickDecline = sentTotal - slaMet - slaNotMet - pending;

			printLine(location, "pickDeclinePerc", CHANGE_TO_PERCENTAGE*Double.valueOf(pickDecline)/sentTotal);

			if (days == 1) {
				location = "node_details_" + id + "_today";
			} else if (days == 7) {
				location = "node_details_" + id + "_7days";
			} else {
				location = "node_details_" + id + "_30days";
			}
			printLine(location, "fillRatePerc", fillRateTotal*CHANGE_TO_PERCENTAGE);
			printLine(location, "shipMetric/fillRatePerc", fillRateSFS*CHANGE_TO_PERCENTAGE);
			printLine(location, "shipMetric/utilRatePerc", utilizationRateSFS*CHANGE_TO_PERCENTAGE);
			printLine(location, "pickMetric/fillRatePerc", fillRateBOPIS*CHANGE_TO_PERCENTAGE);
			printLine(location, "pickMetric/utilRatePerc", utilizationRateBOPIS*CHANGE_TO_PERCENTAGE);

			/*
	node_details_Warden Plaza_30days, summary/0/value, 10000
	node_details_Warden Plaza_30days, summary/1/value, 7568
	node_details_Warden Plaza_30days, summary/2/value, 654
	node_details_Warden Plaza_30days, summary/3/value, 1569

	node_details_Warden Plaza_30days, summary/0/percentage, 50.5
	node_details_Warden Plaza_30days, summary/1/percentage, 38.6
	node_details_Warden Plaza_30days, summary/2/percentage, 3
	node_details_Warden Plaza_30days, summary/3/percentage, 7.9
			 */
			printLine(location, "summary/0/value", slaMet);
			printLine(location, "summary/1/value", slaNotMet);
			printLine(location, "summary/2/value", pending);
			printLine(location, "summary/3/value", pickDecline);

			printLine(location, "summary/0/percentage", CHANGE_TO_PERCENTAGE*Double.valueOf(slaMet)/sentTotal);
			printLine(location, "summary/1/percentage", CHANGE_TO_PERCENTAGE*Double.valueOf(slaNotMet)/sentTotal);
			printLine(location, "summary/2/percentage", CHANGE_TO_PERCENTAGE*Double.valueOf(pending)/sentTotal);
			printLine(location, "summary/3/percentage", CHANGE_TO_PERCENTAGE*Double.valueOf(pickDecline)/sentTotal);
		}
	}

	public static class Node {
		private String id;
		private String name;
		private String type;

		private int sfsCapacity;
		private int bopisCapacity;

		private MetricsForPeriod today;
		private MetricsForPeriod sevenDays;
		private MetricsForPeriod thirtyDays;

		public Node(String id, String name, String type, int sfsCapacity, int bopisCapacity) {

			this.id = id;
			this.name = name;
			this.type = type;
			this.sfsCapacity = sfsCapacity;
			this.bopisCapacity = bopisCapacity;

			today = new MetricsForPeriod(id, sfsCapacity, bopisCapacity, 1, "today");

			// get the metrics for each day in the period.
			List<Metrics> metricsList = new ArrayList<>();
			for (int i = 0; i < 30; i++) {
				metricsList.add(new Metrics(id, sfsCapacity, bopisCapacity, 1, i));
			}

			thirtyDays = new MetricsForPeriod(id, sfsCapacity, bopisCapacity, 30, "30days", metricsList);
			sevenDays = new MetricsForPeriod(id, sfsCapacity, bopisCapacity, 7, "7days", metricsList.subList(23, 30));

		}

		public void printNodeInfo() {
			today.printInfo();
			sevenDays.printInfo();
			thirtyDays.printInfo();
		}
		
		public String getType() {
			return type;
		}

		public int getTotalSent(int days) {
			if (days == 1) {
				return today.summary.sentTotal;
			} else if (days == 7) {
				return sevenDays.summary.sentTotal;
			}
			return thirtyDays.summary.sentTotal;
		}
		public int getTotalPending(int days) {
			if (days == 1) {
				return today.summary.pendingTotal;
			} else if (days == 7) {
				return sevenDays.summary.pendingTotal;
			}
			return thirtyDays.summary.pendingTotal;
		}

		public Double getFillRateToday() {
			return today.summary.fillRateTotal;
		}
		public Double getFillRateSevenDays() {
			return sevenDays.summary.fillRateTotal;
		}
		public Double getFillRateThirtyDays() {
			return thirtyDays.summary.fillRateTotal;
		}
		public Double getUtilizationRateSFSToday() {
			return today.summary.utilizationRateSFS;
		}
		public Double getUtilizationRateSFSSevenDays() {
			return sevenDays.summary.utilizationRateSFS;
		}
		public Double getUtilizationRateSFSThirtyDays() {
			return thirtyDays.summary.utilizationRateSFS;
		}
		public Double getUtilizationRateBOPISToday() {
			return today.summary.utilizationRateBOPIS;
		}
		public Double getUtilizationRateBOPISSevenDays() {
			return sevenDays.summary.utilizationRateBOPIS;
		}
		public Double getUtilizationRateBOPISThirtyDays() {
			return thirtyDays.summary.utilizationRateBOPIS;
		}		

	}

	public static void printLine(String location, String key, double value) {
		printLine(location, key, String.valueOf(value));
	}
	public static void printLine(String location, String key, int value) {
		printLine(location, key, String.valueOf(value));
	}
	public static void printLine(String location, String key, String value) {
		printLine(location, key, value, null);
	}

	public static void printLine(String location, String key, String value, StringBuilder values) {
		sb.setLength(0);
		sb.append(location).append(COMMA).append(key).append(COMMA).append(value);
		if (values != null) {
			sb.append(COMMA).append(values.toString());
		}

		//System.out.println(sb.toString());
		try {
			fileOutputStream.write(sb.toString().getBytes());
			fileOutputStream.write("\n".getBytes());
		} catch (IOException e) {
			// this is a problem
			System.out.println(e);
		}
	}

	public static double getRandomNumber(Range range) {
		return Double.valueOf(randomNumberGenerator.nextInt(range.getHigh()-range.getLow()) + range.getLow());
	}
	public static double getRandomPercentage(Range range) {
		return Double.valueOf(randomNumberGenerator.nextInt(range.getHigh()-range.getLow()) + range.getLow())/CHANGE_TO_PERCENTAGE;
	}

	public static class Range {
		private int high;
		private int low;
		public Range(int low, int high) {
			this.high = high;
			this.low = low;
		}
		/**
		 * @return the high
		 */
		public int getHigh() {
			return high;
		}
		/**
		 * @param high the high to set
		 */
		public void setHigh(int high) {
			this.high = high;
		}
		/**
		 * @return the low
		 */
		public int getLow() {
			return low;
		}
		/**
		 * @param low the low to set
		 */
		public void setLow(int low) {
			this.low = low;
		}
	}

	/**
	 * Comparator to do node sorting based on field
	 * getter and direction.
	 *
	 */
	public static class OmsNodeComparator<E> implements Comparator<E> {

		protected Class<E> clazz;

		private String field;
		private int dir;
		private Method getter = null;
		private Class<?> returnType = null;

		/**
		 * Constructor
		 *
		 * @param clazz Class of objects to compare
		 * @param sortPair Contains 1. name of field setter e.g. 'getDescription' and 2. direction 0 - asc, 1 - desc
		 */
		public OmsNodeComparator(Class<E> clazz, Pair<String, Integer> sortPair) {
			this.clazz = clazz;
			field = sortPair.getLeft();
			dir = sortPair.getRight();

			// find getter method
			Method[] allMethods = clazz.getDeclaredMethods();
			for (Method m : allMethods) {
				String name = m.getName();
				if (name.equals(field)) {
					getter = m;
					break;
				}
			}

			if (getter == null) {
				// this is a problem
				System.out.println("getter method not found " + field);
			}

			returnType = getter.getReturnType();
		}

		@Override
		public int compare(E o1, E o2) {

			int diff = 0;

			try {
				getter.setAccessible(true);
				Object val1 = getter.invoke(o1);
				Object val2 = getter.invoke(o2);

				if (val1 == val2) {
					return diff;
				}

				if (val1 == null) {
					diff = -1;
				} else if (val2 == null) {
					diff = 1;
				} else if (returnType == String.class) {
					diff = ((String)val1).compareTo((String)val2);
				} else if (returnType == Integer.class) {
					diff = ((Integer)val1).compareTo((Integer)val2);
				} else if (returnType == Long.class) {
					diff = ((Long)val1).compareTo((Long)val2);
				} else if (returnType == BigDecimal.class) {
					diff = ((BigDecimal)val1).compareTo((BigDecimal)val2);
				} else if (returnType == Date.class) {
					diff = ((Date)val1).after((Date)val2) ? 1 : 0;
				} else if (returnType == Double.class) {
					diff = ((Double)val1).compareTo((Double)val2);
				}
			} catch (InvocationTargetException | IllegalAccessException
					| IllegalArgumentException e) {
				// this is a problem
				System.out.println(e);
			}

			if (dir == 1) {
				diff *= -1;
			}

			return diff;
		}
	}

	public static class NodeInputList {
		List<NodeInput> nodes;

		public List<NodeInput> getNodes() {
			return nodes;
		}
		public void setNodes(List<NodeInput> nodes) {
			this.nodes = nodes;
		}

	}
	public static class NodeInput {
		private String id;
		private String name;
		private String type;
		private String sfsCapacity;
		private String bopisCapacity;

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

		public String getSfsCapacity() {
			return sfsCapacity;
		}
		public void setSfsCapacity(String sfsCapacity) {
			this.sfsCapacity = sfsCapacity;
		}

		public String getBopisCapacity() {
			return bopisCapacity;
		}
		public void setBopisCapacity(String bopisCapacity) {
			this.bopisCapacity = bopisCapacity;
		}
	}

	public static class WorkspaceInputList {
		List<WorkspaceInput> workspaceNodeIds;

		public List<WorkspaceInput> getWorkspaceNodeIds() {
			return workspaceNodeIds;
		}
		public void setWorkspaceNodeIds(List<WorkspaceInput> workspaceNodeIds) {
			this.workspaceNodeIds = workspaceNodeIds;
		}

	}
	public static class WorkspaceInput {
		List<String> nodeIds;

		public List<String> getNodeIds() {
			return nodeIds;
		}
		public void setNodeIds(List<String> nodeIds) {
			this.nodeIds = nodeIds;
		}

	}

	public static class AlertInputList {
		List<AlertInput> alerts;

		public List<AlertInput> getAlerts() {
			return alerts;
		}
		public void setAlerts(List<AlertInput> alerts) {
			this.alerts = alerts;
		}
	}

	public static class AlertInput {
		String type;
		List<String> nodeIds;

		public List<String> getNodeIds() {
			return nodeIds;
		}
		public void setNodeIds(List<String> nodeIds) {
			this.nodeIds = nodeIds;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

	}
}
