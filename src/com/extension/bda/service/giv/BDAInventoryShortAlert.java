package com.extension.bda.service.giv;

import java.sql.Array;
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

public class BDAInventoryShortAlert implements IBDAService {

	@Override
	public String getServiceName() {
		return "inventoryShortAlert";
	}

	@Override
	public void setProperties(Properties props) {
		
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eShortage = dInput.getDocumentElement();
		if(!YFCCommon.isVoid(eShortage) && !YFCCommon.isVoid(eShortage.getChildElement("ShipNodes")) && !YFCCommon.isVoid(eShortage.getChildElement("ShipNodes").getChildElement("ShipNode"))){
			YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
			YFCElement eMultiApi = dMultiApi.getDocumentElement();
			boolean callMultiApi = false;
			if(eShortage.getBooleanAttribute("RaiseAlert") || eShortage.getBooleanAttribute("NotifyCustomers")){
				YFCElement eOrders = getOrdersForItem(eShortage);
				if(eShortage.getBooleanAttribute("RaiseAlert")){
					for(YFCElement eOrder : eOrders.getChildren()){
						createAlert(eMultiApi, eOrder, eShortage);
						callMultiApi = true;
					}
				}
				if(eShortage.getBooleanAttribute("NotifyCustomers")){
					
				}
			}
			if(eShortage.getBooleanAttribute("RemoveInventoryFromNode")){
				YFCElement eApi = eMultiApi.createChild("API");
				eApi.setAttribute("Name", "adjustInventory");
				YFCElement eItems = eApi.createChild("Input").createChild("Items");
				for(YFCElement eShipNode : eShortage.getChildElement("ShipNodes", true).getChildren()){
					clearInventorySupply(eItems, eShortage, eShipNode.getAttribute("Node"));
					callMultiApi = true;
				}
				
			}
			if(callMultiApi){
				
				try {
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document l_OutputXml = localApi.invoke(env, "multiApi", dMultiApi.getDocument());
					return l_OutputXml;
				} catch(Exception yex) {
					yex.printStackTrace();
		        } 
			}
		}
		
	
		return input;
	}
	
	private void clearInventorySupply(YFCElement eItems, YFCElement eShortage, String sNode){
		
		String psql = "SELECT SUP.QUANTITY, SUP.SHIPNODE_KEY FROM OMDB.YFS_INVENTORY_SUPPLY SUP INNER JOIN OMDB.YFS_INVENTORY_ITEM II ON II.INVENTORY_ITEM_KEY = SUP.INVENTORY_ITEM_KEY WHERE II.ITEM_ID = ? AND SUP.SHIPNODE_KEY = ?";
		Connection conn = null;
		try {
			
			conn = DatabaseConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(psql);
			ps.setString(1, eShortage.getAttribute("ItemID"));
			ps.setString(2, sNode);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				YFCElement eItem = eItems.createChild("Item");
				eItem.setAttribute("AdjustmentType", "ADJUSTMENT");
				eItem.setAttribute("Availability", "TRACK");
				eItem.setAttribute("ItemID", eShortage.getAttribute("ItemID"));
				eItem.setAttribute("UnitOfMeasure", eShortage.getAttribute("UnitOfMeasure"));
				if(!YFCCommon.isVoid(eShortage.getAttribute("ProductClass")) && !YFCCommon.equals(eShortage.getAttribute("ProductClass"), "undefined")){
					eItem.setAttribute("ProductClass", eShortage.getAttribute("ProductClass"));
				}
				eItem.setAttribute("Quantity", rs.getDouble("QUANTITY") * -1);
				eItem.setAttribute("ShipNode", sNode);
				if(!YFCCommon.isVoid(eShortage.getAttribute("SupplyType"))){
					eItem.setAttribute("SupplyType", eShortage.getAttribute("SupplyType"));
				} else {
					eItem.setAttribute("SupplyType", "ONHAND");
				}
			}
			
		
		}catch(SQLException | ClassNotFoundException e){
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
	private void createAlert(YFCElement eMultiApi, YFCElement eOrder, YFCElement eShortage){
		YFCElement eApi = eMultiApi.createChild("API");
		eApi.setAttribute("Name", "createException");
		YFCElement eInbox = eApi.createChild("Input").createChild("Inbox");
		eInbox.setAttribute("ActiveFlag", "Y");
		eInbox.setAttribute("AutoResolveFlag", "N");
		if(eOrder.hasAttribute("BillToID")){
			eInbox.setAttribute("BillToID", eOrder.getAttribute("BillToID"));
		}
		if(!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode"))){
			eInbox.setAttribute("EnterpriseKey", eOrder.getAttribute("EnterpriseCode"));
			eInbox.setAttribute("OwnerKey", eOrder.getAttribute("EnterpriseCode"));
		}
		if(!YFCCommon.isVoid(eShortage.getAttribute("QueueKey"))){
			eInbox.setAttribute("QueueKey", eShortage.getAttribute("QueueKey"));
		}
		if(!YFCCommon.isVoid(eShortage.getAttribute("ExceptionType"))){
			eInbox.setAttribute("ExceptionType", eShortage.getAttribute("ExceptionType"));
		} else if(YFCCommon.isVoid(eShortage.getAttribute("InboxType"))) {
			eInbox.setAttribute("ExceptionType", "SOP_INVENTORY_SHORTAGE");
		} else {
			eInbox.setAttribute("InboxType", eShortage.getAttribute("InboxType"));
		}
		eInbox.setAttribute("ItemId", eShortage.getAttribute("ItemID"));
		eInbox.setAttribute("Description", eShortage.getAttribute("ReasonCode"));
		eInbox.setAttribute("DetailDescription", eShortage.getAttribute("ReasonContent"));
		eInbox.setAttribute("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
		eInbox.setAttribute("OrderNo", eOrder.getAttribute("OrderNo"));
		eInbox.setAttribute("Priority", 1);
		if(!YFCCommon.isVoid(eShortage.getAttribute("QueueId"))){
			eInbox.setAttribute("QueueId", eShortage.getAttribute("QueueId"));
		}
		if(!YFCCommon.isVoid(eShortage.getAttribute("AssignToUserId"))){
			eInbox.setAttribute("AssignedToUserId", eShortage.getAttribute("AssignToUserId"));
		}
	}
	private YFCElement getOrdersForItem(YFCElement eShortage){
		YFCDocument dOrderList = YFCDocument.createDocument("OrderList");
		if(!YFCCommon.isVoid(eShortage)){
			if(!YFCCommon.isVoid(eShortage.getAttribute("ItemID")) && !YFCCommon.isVoid(eShortage.getChildElement("ShipNodes", true).getChildElement("ShipNode"))){
				Connection conn = null;
				
				StringBuilder sb = new StringBuilder("SELECT OH.ORDER_NO, OL.ORDER_HEADER_KEY, OH.ENTERPRISE_KEY, OL.ORDER_LINE_KEY, ORS.STATUS, OL.ITEM_ID, OL.ORDERED_QTY, ID.DEMAND_TYPE, ID.OWNER_KEY, ID.SHIPNODE_KEY, ID.QUANTITY, OH.BILL_TO_ID, OH.CUSTOMER_EMAILID ");
				sb.append("FROM OMDB.YFS_ORDER_RELEASE_STATUS ORS ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_LINE_SCHEDULE OLS ON OLS.ORDER_LINE_SCHEDULE_KEY = ORS.ORDER_LINE_SCHEDULE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_LINE OL ON OL.ORDER_LINE_KEY = OLS.ORDER_LINE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_HEADER OH ON OH.ORDER_HEADER_KEY = OL.ORDER_HEADER_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_ITEM II ON (II.ITEM_ID = OL.ITEM_ID AND II.UOM = OL.UOM) ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_DEMAND ID ON (ID.INVENTORY_ITEM_KEY = II.INVENTORY_ITEM_KEY AND OLS.SHIP_NODE  = ID.SHIPNODE_KEY) ");
				sb.append("WHERE OL.ITEM_ID = ? AND ID.SHIPNODE_KEY in (");
				for (YFCElement eShipNode : eShortage.getChildElement("ShipNodes").getChildren()){
					if(eShipNode.equals(eShortage.getChildElement("ShipNodes", true).getFirstChildElement())){
						sb.append("'" + eShipNode.getAttribute("Node") + "'");
					} else {
						sb.append(", '" + eShipNode.getAttribute("Node") + "'");
					}					
				}
				sb.append(") AND OH.DOCUMENT_TYPE = '0001' AND II.ORGANIZATION_CODE = ?");
				try {
					conn = DatabaseConnection.getConnection();
					PreparedStatement ps = conn.prepareStatement(sb.toString());
					ps.setString(1, eShortage.getAttribute("ItemID"));
					ps.setString(2, eShortage.getAttribute("InventoryOrganizationCode"));
					ResultSet rs = ps.executeQuery();
					while (rs.next()){
						YFCElement eOrderRecord = dOrderList.getDocumentElement().createChild("OrderRecord");
						eOrderRecord.setAttribute("OrderHeaderKey", rs.getString("ORDER_HEADER_KEY").trim());
						eOrderRecord.setAttribute("OrderNo", rs.getString("ORDER_NO").trim());
						eOrderRecord.setAttribute("EnterpriseCode", rs.getString("ENTERPRISE_KEY").trim());
						eOrderRecord.setAttribute("OrderLineKey", rs.getString("ORDER_LINE_KEY").trim());
						eOrderRecord.setAttribute("ShipNode", rs.getString("SHIPNODE_KEY").trim());
						eOrderRecord.setAttribute("DemandType", rs.getString("DEMAND_TYPE").trim());
						eOrderRecord.setAttribute("DemandQuantity", rs.getString("QUANTITY").trim());
						eOrderRecord.setAttribute("Status", rs.getString("STATUS").trim());
						eOrderRecord.setAttribute("BillToID", rs.getString("BILL_TO_ID").trim());
						eOrderRecord.setAttribute("CustomerEMailID", rs.getString("CUSTOMER_EMAILID").trim());
						eOrderRecord.setAttribute("OrderedQty", rs.getString("ORDERED_QTY").trim());
					}
				} catch(SQLException | ClassNotFoundException e){
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
