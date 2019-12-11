package com.bda.sfdc.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.bda.sfdc.SFDCOrder;
import com.bda.sfdc.SFDCUtils;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.agent.server.YCPAbstractAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;

public class SFDCOrderStatusAgent extends YCPAbstractAgent {

	@SuppressWarnings("deprecation")
	@Override
	public void executeJob(YFSEnvironment env, Document executionMessage) throws Exception {
		YFCDocument dInput = YFCDocument.getDocumentFor(executionMessage);
		YFCElement eInput = dInput.getDocumentElement();
	
		JSONObject response = new JSONObject();
		JSONArray batch = new JSONArray();
		String lastDate = null;
		String sEnterpriseCode = null;
		for (YFCElement eOrder : eInput.getChildren()){
			YFCDate date = eOrder.getDateTimeAttribute("OrderDate");
			SFDCOrder order = new SFDCOrder(eOrder.getAttribute("OrderHeaderKey"), eOrder.getAttribute("OrderNo"), eOrder.getAttribute("CustomerEMailID"), eOrder.getAttribute("Status"), Float.parseFloat(eOrder.getChildElement("PriceInfo").getAttribute("TotalAmount")), new Timestamp(date.getTime()));
			
			sEnterpriseCode = eOrder.getAttribute("EnterpriseCode");
			JSONObject jOrder = new JSONObject();
			jOrder.put("method", "PATCH");
			jOrder.put("url", "v39.0/sobjects/Order__c/Order_Header_Key__c/" + eOrder.getAttribute("OrderHeaderKey"));
			jOrder.put("richInput", order.getSFDCData());
			batch.add(jOrder);
			if(!YFCCommon.isVoid(eOrder.getAttribute("StatusDate"))){
				lastDate = eOrder.getAttribute("StatusDate");
			} else {
				lastDate = date.getString("yyyy-mm-dd hh:mm:ss");
			}
			
		}
		if(batch.size() > 0){
			response.put("batchRequests", batch);
			saveLastCommittedDate(lastDate);
			SFDCUtils.batchRecords(SFDCUtils.getConfiguredAuthSite(sEnterpriseCode) + "/services/data/v39.0/composite/batch/", response.toString(), sEnterpriseCode);
		}
	}

	public static Document getOrderListTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("OrderList");
		YFCElement eOrder = dOutput.getDocumentElement().createChild("Order");
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("OrderDate", "");
		eOrder.setAttribute("Status", "");
		eOrder.setAttribute("CustomerEMailID", "");
		eOrder.setAttribute("CustomerFirstName", "");
		eOrder.setAttribute("CustomerLastName", "");
		eOrder.setAttribute("CustomerPhoneNo", "");
		eOrder.setAttribute("DraftOrderFlag", "");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("Currency", "");
		ePriceInfo.setAttribute("TotalAmount", "");
		return dOutput.getDocument();
	}
	
	@Override
	public List<Document> getJobs(YFSEnvironment env, Document docCriteria, Document lastMessageCreated) {
		List<Document> list = new ArrayList<Document>();
		HashMap<String, Timestamp> orders = new HashMap<String, Timestamp>();
		ArrayList<String> sequenceOrders = new ArrayList<String>();
		YFCDocument criteriaDoc = YFCDocument.getDocumentFor(docCriteria);
		YFCElement critertiaElem = criteriaDoc.getDocumentElement();
		
		YFCDocument orderInput = YFCDocument.createDocument("Order");
		YFCElement eOr = orderInput.getDocumentElement().createChild("ComplexQuery").createChild("Or");
		
		System.out.println("Getting SFDC Order List");
		String sSql = "SELECT DISTINCT RS.ORDER_HEADER_KEY, MAX(RS.STATUS_DATE) FROM OMDB.YFS_ORDER_RELEASE_STATUS RS INNER JOIN OMDB.YFS_ORDER_HEADER OH ON OH.ORDER_HEADER_KEY = RS.ORDER_HEADER_KEY WHERE TRIM(OH.ENTERPRISE_KEY) = ? AND TRIM(OH.DOCUMENT_TYPE) = '0001' AND RS.STATUS_DATE > ? GROUP BY RS.ORDER_HEADER_KEY ORDER BY MAX(RS.STATUS_DATE)";
		
		boolean ordersFound = false;
		Connection conn = null;
		try {
			conn = BDASynchronization.getOMSConnection(env);
			PreparedStatement ps = conn.prepareStatement(sSql);
			ps.setString(1, critertiaElem.getAttribute("EnterpriseCode"));
			ps.setTimestamp(2, getLastCommittedDate(env));
			ResultSet rs = ps.executeQuery();
			int j = 0;
			while (rs.next()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "OrderHeaderKey");
				eExp.setAttribute("Value", rs.getString(1).trim());
				orders.put(rs.getString(1).trim(), rs.getTimestamp(2));
				sequenceOrders.add(rs.getString(1).trim());
				j++;
				ordersFound = true;
			} 
			System.out.println("Orders Found: " + j);
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			if (conn != null){
				try {
					conn.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}		
		
		if(ordersFound){
			try {
				env.setApiTemplate("getOrderList", getOrderListTemplate());
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				Document response = localApi.invoke(env, "getOrderList", orderInput.getDocument());
				YFCElement eOrderList = YFCDocument.getDocumentFor(response).getDocumentElement();
				int i = 0;
				
				
				HashMap<String, YFCElement> returnedOrders = new HashMap<String, YFCElement>();
				for(YFCElement eOrder : eOrderList.getChildren()){
					returnedOrders.put(eOrder.getAttribute("OrderHeaderKey").trim(), eOrder);
				}
				YFCDocument dResponseList = YFCDocument.createDocument("OrderList");
				for (String sOrderHeaderKey : sequenceOrders){
					if (i % 25 == 0){
						if(i > 0){
							list.add(dResponseList.getDocument());
							dResponseList = YFCDocument.createDocument("OrderList");
						}					
					}
					YFCElement eOrder = returnedOrders.get(sOrderHeaderKey);
					if (!YFCCommon.isVoid(eOrder) && !eOrder.getBooleanAttribute("DraftOrderFlag", false)){
						if(!YFCCommon.isVoid(orders) && orders.containsKey(sOrderHeaderKey)){
							eOrder.setAttribute("StatusDate", orders.get(sOrderHeaderKey).toString());
						}
						dResponseList.getDocumentElement().importNode(eOrder);
						i++;
					}
				}
				if(dResponseList.getDocumentElement().hasChildNodes()){
					list.add(dResponseList.getDocument());
				}				
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
	private Timestamp getLastCommittedDate(YFSEnvironment env){
		File dir = new File(BDAServiceApi.getAgentsPath(env));
		if(!dir.exists()){
			dir.mkdirs();
		}
		File sfdcLastOrderCommit = new File(BDAServiceApi.getAgentsPath(env) + "/SFDCLastOrderCommit.txt");
		if(sfdcLastOrderCommit.exists()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(sfdcLastOrderCommit.getAbsolutePath()));
				String line = br.readLine();
				if(!YFCCommon.isVoid(line)){
					return Timestamp.valueOf(line);
				}
			} catch (Exception e){
				e.printStackTrace();
				return Timestamp.valueOf("2017-01-03 00:00:00");
			}
		}
		return Timestamp.valueOf("2017-01-05 00:00:00");
	}
	
	private void saveLastCommittedDate(String ts){
		if(!YFCCommon.isVoid(ts)){
			File dir = new File("/opt/Sterling/Agents");
			if(!dir.exists()){
				dir.mkdirs();
			}
			File sfdcLastOrderCommit = new File("/opt/Sterling/Agents/SFDCLastOrderCommit.txt");
			if(sfdcLastOrderCommit.exists()){
				sfdcLastOrderCommit.delete();
			}
			byte data[] = ts.getBytes();
			try {
				FileOutputStream out = new FileOutputStream("/opt/Sterling/Agents/SFDCLastOrderCommit.txt");
				out.write(data);
				out.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
	}

}
