package com.extension.bda.service.giv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.object.DatabaseConnection;
import com.extension.bda.service.IBDAService;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetDemandOrders implements IBDAService {

	@Override
	public String getServiceName() {
		return "getDemandOrders";
	}

	@Override
	public void setProperties(Properties props) {
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		if(!YFCCommon.isVoid(input)){
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eItemDemand = dInput.getDocumentElement();
			YFCElement output = getOrdersForItemDemand(env, eItemDemand);
			if(!YFCCommon.isVoid(env.getTxnObject("serviceTemplate"))){
				YFCDocument dOrderInput = YFCDocument.createDocument("Order");
				YFCElement eOrderInput = dOrderInput.getDocumentElement();
				YFCElement eOr = eOrderInput.createChild("ComplexQuery").createChild("Or");
				boolean found = false;
				for(YFCElement eOrder : output.getChildren()){
					YFCElement eExp = eOr.createChild("Exp");
					eExp.setAttribute("Name", "OrderHeaderKey");
					eExp.setAttribute("Value", eOrder.getAttribute("OrderHeaderKey"));
					found = true;
				}
				if(found){
					env.setApiTemplate("getOrderList", (Document) env.getTxnObject("serviceTemplate"));
					try {
						YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
						Document response = localApi.invoke(env, "getOrderList", dOrderInput.getDocument());
						return response;
					} catch (Exception e){
						e.printStackTrace();
					}				
				}
			}
			return output.getOwnerDocument().getDocument();
		}
		return input;
	}

	private static YFCElement getOrdersForItemDemand(YFSEnvironment env, YFCElement eItemDemand){
		YFCDocument dOrderList = YFCDocument.createDocument("OrderList");
		if(!YFCCommon.isVoid(eItemDemand)){
			if(!YFCCommon.isVoid(eItemDemand.getAttribute("ItemID"))){
				Connection conn = null;
				
				StringBuilder sb = new StringBuilder("SELECT OH.ORDER_NO, OH.DOCUMENT_TYPE, OH.ORDER_DATE, OL.ORDER_HEADER_KEY, OH.ENTERPRISE_KEY, IDD.ORDER_LINE_KEY, ORS.STATUS, OL.ITEM_ID, OL.ORDERED_QTY, ID.DEMAND_TYPE, ID.OWNER_KEY, ID.SHIPNODE_KEY, IDD.QUANTITY, OH.BILL_TO_ID, OH.CUSTOMER_EMAILID ");
				sb.append("FROM OMDB.YFS_ORDER_RELEASE_STATUS ORS ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_LINE_SCHEDULE OLS ON OLS.ORDER_LINE_SCHEDULE_KEY = ORS.ORDER_LINE_SCHEDULE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_LINE OL ON OL.ORDER_LINE_KEY = OLS.ORDER_LINE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_DEMAND_DTLS IDD ON IDD.ORDER_LINE_KEY = OL.ORDER_LINE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_HEADER OH ON OH.ORDER_HEADER_KEY = OL.ORDER_HEADER_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_DEMAND ID ON ID.INVENTORY_DEMAND_KEY = IDD.INVENTORY_DEMAND_KEY ");
				sb.append("WHERE OL.ITEM_ID = ? ");
				if(!YFCCommon.isVoid(eItemDemand.getChildElement("ShipNodes"))){
					sb.append("AND ID.SHIPNODE_KEY in (");
					for (YFCElement eShipNode : eItemDemand.getChildElement("ShipNodes").getChildren()){
						if(eShipNode.equals(eItemDemand.getChildElement("ShipNodes", true).getFirstChildElement())){
							sb.append("'" + eShipNode.getAttribute("Node") + "'");
						} else {
							sb.append(", '" + eShipNode.getAttribute("Node") + "'");
						}					
					}
					sb.append(") ");
				}
				if(!YFCCommon.isVoid(eItemDemand.getAttribute("DistributionRuleId"))) {
					sb.append("AND ID.SHIPNODE_KEY IN (SELECT SHIPNODE_KEY FROM OMDB.YFS_ITEM_SHIP_NODE WHERE DISTRIBUTION_RULE_ID = '" + eItemDemand.getAttribute("DistributionRuleId") + "') ");
				}
				sb.append("AND ID.QUANTITY > 0 AND ORS.STATUS_QUANTITY > 0 ");
				if(!YFCCommon.isVoid(eItemDemand.getAttribute("DemandType"))){
					 sb.append("AND ID.DEMAND_TYPE = ? ");
				}
				sb.append("ORDER BY OH.ORDER_NO, OH.ENTERPRISE_KEY");
				try {
					conn = DatabaseConnection.getConnection(env);
					System.out.println(sb.toString());
					PreparedStatement ps = conn.prepareStatement(sb.toString());
					ps.setString(1, eItemDemand.getAttribute("ItemID"));
					if(!YFCCommon.isVoid(eItemDemand.getAttribute("DemandType"))){
						ps.setString(2, eItemDemand.getAttribute("DemandType"));
					}
					ResultSet rs = ps.executeQuery();
					YFCElement eOrderRecord = null;
					
					while (rs.next()){
						if(YFCCommon.isVoid(eOrderRecord) || !YFCCommon.equals(eOrderRecord.getAttribute("OrderHeaderKey"), rs.getString("ORDER_HEADER_KEY").trim())){
							eOrderRecord = dOrderList.getDocumentElement().createChild("Order");
							eOrderRecord.setAttribute("OrderHeaderKey", rs.getString("ORDER_HEADER_KEY").trim());
							eOrderRecord.setAttribute("OrderNo", rs.getString("ORDER_NO").trim());
							eOrderRecord.setAttribute("DocumentType", rs.getString("DOCUMENT_TYPE").trim());
							eOrderRecord.setAttribute("OrderDate", rs.getString("ORDER_DATE").trim());
							eOrderRecord.setAttribute("EnterpriseCode", rs.getString("ENTERPRISE_KEY").trim());
							if(!YFCCommon.isVoid(rs.getString("BILL_TO_ID"))){
								eOrderRecord.setAttribute("BillToID", rs.getString("BILL_TO_ID").trim());
							}							
							if(!YFCCommon.isVoid(rs.getString("CUSTOMER_EMAILID"))){
								eOrderRecord.setAttribute("CustomerEMailID", rs.getString("CUSTOMER_EMAILID").trim());	
							}
							
							
						}
						YFCElement eLine = null;
						for(YFCElement eOrderLine : eOrderRecord.getChildElement("OrderLines", true).getChildren()){
							if(YFCCommon.equals(eOrderLine.getAttribute("OrderLineKey"), rs.getString("ORDER_LINE_KEY").trim())){
								eLine = eOrderLine;
								break;
							}
						}
						if(YFCCommon.isVoid(eLine)){
							eLine = eOrderRecord.getChildElement("OrderLines", true).createChild("OrderLine");
							eLine.setAttribute("OrderLineKey", rs.getString("ORDER_LINE_KEY").trim());
							eLine.setAttribute("OrderedQty", rs.getString("ORDERED_QTY").trim());
						}
						YFCElement eDemand = eLine.getChildElement("Demands", true).createChild("Demand");
						eDemand.setAttribute("ShipNode", rs.getString("SHIPNODE_KEY").trim());
						eDemand.setAttribute("DemandType", rs.getString("DEMAND_TYPE").trim());
						eDemand.setAttribute("DemandQuantity", rs.getString("QUANTITY").trim());
						eDemand.setAttribute("Status", rs.getString("STATUS").trim());
					
					}
				} catch(SQLException e){
					e.printStackTrace();
				} finally {
					if(!YFCCommon.isVoid(conn)){
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}			
		}
		return dOrderList.getDocumentElement();
	}
}
