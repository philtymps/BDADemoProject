package com.extension.sci.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.extension.sci.object.SCISalesOrder;
import com.extension.sci.services.BDASendOrderToSCI;
import com.extension.sci.util.BDAPushToSCI;
import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.agent.server.YCPAbstractAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;

public class SCIMonitorAgent extends YCPAbstractAgent {
	
	@SuppressWarnings("deprecation")
	@Override
	public void executeJob(YFSEnvironment env, Document executionMessage) throws Exception {
		YFCDocument dInput = YFCDocument.getDocumentFor(executionMessage);
		YFCElement eInput = dInput.getDocumentElement();
	
		JSONObject response = new JSONObject();
		JSONArray batch = new JSONArray();
		String lastDate = null;
		String sEnterpriseCode = null;
		BDASendOrderToSCI sendOrder = new BDASendOrderToSCI();
		

		JSONObject bulk = new JSONObject();
		bulk.put("allOrNothing", false);
		JSONArray upserts = new JSONArray();
		
		bulk.put("upserts", upserts);
		
		
		for (YFCElement eOrder : eInput.getChildren()){
			SCISalesOrder sciso = new SCISalesOrder(eOrder);
			upserts.put(sciso.getBulkObject());
			YFCDate date = eOrder.getDateTimeAttribute("OrderDate");
			sendOrder.invoke(env, eOrder);	
		}
		if(upserts.length() > 0){
			saveLastCommittedDate(env);
			sendOrder.callRequest(BDAPushToSCI.SCI_BULK_SALES_ORDER, bulk);
		}
	}

	
	private void getOrders(YFSEnvironment env, Document docCriteria, List<Document> list){
		
		YFCDocument criteriaDoc = YFCDocument.getDocumentFor(docCriteria);
		YFCElement criteriaElem = criteriaDoc.getDocumentElement();
		
		addOrdersToList(env, "0001", criteriaElem.getAttribute("EnterpriseCode"), list);
		addOrdersToList(env, "0005", criteriaElem.getAttribute("EnterpriseCode"), list);
		
	}
	
	private void addOrdersToList(YFSEnvironment env, String sDocumentType, String sEnterpriseCode, List<Document> list){
		YFCDocument input = getOrdersToProcess(env, sEnterpriseCode, sDocumentType);
		env.setApiTemplate("getOrderList", SCISalesOrder.getSalesOrderListTemplate().getDocument());
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document response = localApi.invoke(env, "getOrderList", input.getDocument());
			YFCElement eOrderList = YFCDocument.getDocumentFor(response).getDocumentElement();
			int i = 0;
			YFCDocument dResponseList = null;
		
			for (YFCElement eOrder : eOrderList.getChildren()){
				if (i % 25 == 0){
					if(!YFCCommon.isVoid(dResponseList)){
						list.add(dResponseList.getDocument());
					}				
					dResponseList = YFCDocument.createDocument("OrderList");
					dResponseList.getDocumentElement().setAttribute("DocumentType", sDocumentType);
				}
					
				if (!YFCCommon.isVoid(eOrder) && !eOrder.getBooleanAttribute("DraftOrderFlag", false)){
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
	
	private void addShipmentsToList(YFSEnvironment env, String sDocumentType, String sEnterpriseCode, List<Document> list){
		YFCDocument input = getShipmentsToProcess(env, sEnterpriseCode, sDocumentType);
		env.setApiTemplate("get", SCISalesOrder.getSalesOrderListTemplate().getDocument());
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document response = localApi.invoke(env, "getOrderList", input.getDocument());
			YFCElement eOrderList = YFCDocument.getDocumentFor(response).getDocumentElement();
			int i = 0;
			YFCDocument dResponseList = null;
		
			for (YFCElement eOrder : eOrderList.getChildren()){
				if (i % 25 == 0){
					if(!YFCCommon.isVoid(dResponseList)){
						list.add(dResponseList.getDocument());
					}				
					dResponseList = YFCDocument.createDocument("OrderList");
					dResponseList.getDocumentElement().setAttribute("DocumentType", sDocumentType);
				}
					
				if (!YFCCommon.isVoid(eOrder) && !eOrder.getBooleanAttribute("DraftOrderFlag", false)){
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

	private YFCDocument getOrdersToProcess(YFSEnvironment env, String sEnterpriseCode, String sDocumentType){
		
		String sSql = "SELECT DISTINCT RS.ORDER_HEADER_KEY, MAX(RS.STATUS_DATE) FROM OMDB.YFS_ORDER_RELEASE_STATUS RS INNER JOIN OMDB.YFS_ORDER_HEADER OH ON OH.ORDER_HEADER_KEY = RS.ORDER_HEADER_KEY WHERE TRIM(OH.ENTERPRISE_KEY) = ? AND TRIM(OH.DOCUMENT_TYPE) = ? AND RS.STATUS_DATE > ? GROUP BY RS.ORDER_HEADER_KEY ORDER BY MAX(RS.STATUS_DATE)";
		YFCDocument orderInput = YFCDocument.createDocument("Order");
		YFCElement eOr = orderInput.getDocumentElement().createChild("ComplexQuery").createChild("Or");
		
		
		Connection conn = null;
		try {
			conn = BDASynchronization.getOMSConnection(env);
			PreparedStatement ps = conn.prepareStatement(sSql);
			ps.setString(1, sEnterpriseCode);
			ps.setString(2,  sDocumentType);
			ps.setTimestamp(3, getLastCommittedDate(env));
			ResultSet rs = ps.executeQuery();
			int j = 0;
			while (rs.next()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "OrderHeaderKey");
				eExp.setAttribute("Value", rs.getString(1).trim());
				j++;
			} 
			System.out.println(sDocumentType + " Orders Found: " + j);
			if(j > 0){
				return orderInput;
			}
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
		return null;
	}
	
	private YFCDocument getShipmentsToProcess(YFSEnvironment env, String sEnterpriseCode, String sDocumentType){
		
		String sSql = "SELECT DISTINCT RS.SHIPMENT_KEY FROM OMDB.YFS_SHIPMENT WHERE TRIM(ENTERPRISE_CODE) = ? AND TRIM(DOCUMENT_TYPE) = ? AND MODIFYTS > ? ORDER BY MODIFYTS";
		YFCDocument orderInput = YFCDocument.createDocument("Shipment");
		YFCElement eOr = orderInput.getDocumentElement().createChild("ComplexQuery").createChild("Or");
		
		
		Connection conn = null;
		try {
			conn = BDASynchronization.getOMSConnection(env);
			PreparedStatement ps = conn.prepareStatement(sSql);
			ps.setString(1, sEnterpriseCode);
			ps.setString(2,  sDocumentType);
			ps.setTimestamp(3, getLastCommittedDate(env));
			ResultSet rs = ps.executeQuery();
			int j = 0;
			while (rs.next()){
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "ShipmentKey");
				eExp.setAttribute("Value", rs.getString(1).trim());
				j++;
			} 
			System.out.println(sDocumentType + " Shipments Found: " + j);
			if(j > 0){
				return orderInput;
			}
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
		return null;
	}
	
	@Override
	public List<Document> getJobs(YFSEnvironment env, Document docCriteria, Document lastMessageCreated) {
		List<Document> list = new ArrayList<Document>();
		getOrders(env, docCriteria, list);
		return list;
	}
	
	private Timestamp getLastCommittedDate(YFSEnvironment env){
		File dir = new File(BDAServiceApi.getAgentsPath(env));
		if(!dir.exists()){
			dir.mkdirs();
		}
		File sfdcLastOrderCommit = new File(BDAServiceApi.getAgentsPath(env) + "/SCILastOrderCommit.txt");
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
	
	private void saveLastCommittedDate(YFSEnvironment env){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String ts = sf.format(d);
		if(!YFCCommon.isVoid(ts)){
			File dir = new File(BDAServiceApi.getAgentsPath(env));
			if(!dir.exists()){
				dir.mkdirs();
			}
			File sfdcLastOrderCommit = new File(BDAServiceApi.getAgentsPath(env) + "/SCICLastOrderCommit.txt");
			if(sfdcLastOrderCommit.exists()){
				sfdcLastOrderCommit.delete();
			}
			byte data[] = ts.getBytes();
			try {
				FileOutputStream out = new FileOutputStream(BDAServiceApi.getAgentsPath(env) + "/SCILastOrderCommit.txt");
				out.write(data);
				out.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
	}

}
