package com.extension.gucci;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.object.DatabaseConnection;
import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.ibm.CallInteropServlet;
import com.yantra.yfc.date.YTimestamp;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class GucciStealInventory implements IBDAService {


	private Properties p;
	
	public GucciStealInventory(){
		p = new Properties();
	}
	
	public void setProperties(Properties properties){
		this.p = properties;
	}
	
	public Object getProperty(String sProp){
		return this.p.get(sProp);
	}
	public static void main(String[] args){
		
	}
	
	public Document getOrdersToStealFrom(YFSEnvironment env, Document input){
		YFCDocument dOrderList = YFCDocument.createDocument("OrderList");
		if(!YFCCommon.isVoid(input)){
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eInput = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(eInput.getAttribute("ItemID"))){
				Connection conn = null;
				StringBuilder sb = new StringBuilder("SELECT OH.PRIORITY_CODE, OL.ORDER_HEADER_KEY, OL.ORDER_LINE_KEY, ID.SHIPNODE_KEY AS SHIP_NODE, ID.DEMAND_TYPE, ID.QUANTITY AS DEMAND_QUANTITY, OLS.EXPECTED_SHIPMENT_DATE, ORS.STATUS, SUP.QUANTITY AS SUPPLY_QUANTITY ");
				sb.append("FROM OMDB.YFS_ORDER_HEADER OH ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_LINE OL ON OL.ORDER_HEADER_KEY = OH.ORDER_HEADER_KEY ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_RELEASE_STATUS ORS ON ORS.ORDER_LINE_KEY = OL.ORDER_LINE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_ORDER_LINE_SCHEDULE OLS ON OLS.ORDER_LINE_SCHEDULE_KEY = ORS.ORDER_LINE_SCHEDULE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_ITEM II ON (II.ITEM_ID = OL.ITEM_ID AND II.UOM = OL.UOM) ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_DEMAND_DTLS IDD ON IDD.ORDER_LINE_KEY = OL.ORDER_LINE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_DEMAND ID ON ID.INVENTORY_DEMAND_KEY = IDD.INVENTORY_DEMAND_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_SUPPLY SUP ON SUP.INVENTORY_ITEM_KEY = II.INVENTORY_ITEM_KEY AND OLS.SHIP_NODE = SUP.SHIPNODE_KEY ");
				sb.append("INNER JOIN OMDB.YFS_INVENTORY_CONSIDERATIONS IC ON IC.DEMAND_TYPE = ID.DEMAND_TYPE AND IC.SUPPLY_TYPE = SUP.SUPPLY_TYPE ");
				sb.append("WHERE II.ITEM_ID = ? AND ORS.STATUS < ? AND ORS.STATUS_QUANTITY > 0 AND ID.QUANTITY > 0");
				if(!YFCCommon.isVoid(eInput.getAttribute("SupplyType"))){
					sb.append("AND SUP.SUPPLY_TYPE = ? ");
				}
				try {
					conn = DatabaseConnection.getConnection(env);
					PreparedStatement ps = conn.prepareStatement(sb.toString());
					ps.setString(1, eInput.getAttribute("ItemID"));
					if(!YFCCommon.isVoid(eInput.getAttribute("StatusLessThan"))){
						ps.setString(2, eInput.getAttribute("StatusLessThan"));
					} else {
						ps.setString(2, "3700");
					}
					if(!YFCCommon.isVoid(eInput.getAttribute("SupplyType"))){
						ps.setString(3, eInput.getAttribute("SupplyType"));
					}
					System.out.println(sb.toString());
					ResultSet rs = ps.executeQuery();
					while (rs.next()){
						YFCElement eOrderRecord = dOrderList.getDocumentElement().createChild("OrderRecord");
						eOrderRecord.setAttribute("OrderHeaderKey", rs.getString("ORDER_HEADER_KEY"));
						eOrderRecord.setAttribute("PriorityCode", rs.getString("PRIORITY_CODE"));
						eOrderRecord.setAttribute("OrderLineKey", rs.getString("ORDER_LINE_KEY"));
						eOrderRecord.setAttribute("ShipNode", rs.getString("SHIP_NODE"));
						eOrderRecord.setAttribute("DemandType", rs.getString("DEMAND_TYPE"));
						eOrderRecord.setAttribute("DemandQuantity", rs.getString("DEMAND_QUANTITY"));
						YTimestamp ts = YTimestamp.newMutableTimestamp(rs.getTimestamp("EXPECTED_SHIPMENT_DATE"));
						eOrderRecord.setAttribute("ExpectedShipmentDate", ts);
						eOrderRecord.setAttribute("ReleaseStatus", rs.getString("STATUS"));
						eOrderRecord.setAttribute("SupplyQuantity", rs.getString("SUPPLY_QUANTITY"));
					}
				} catch(SQLException e){
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
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
		return dOrderList.getDocument();
	}
	
	public Document stealInventoryFromLine(YFSEnvironment env, Document input){
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("OrderHeaderKey")) && !YFCCommon.isVoid(eInput.getAttribute("OrderLineKey")) && !YFCCommon.isVoid(eInput.getAttribute("HoldType"))){
			YFCDocument dApiInput = YFCDocument.createDocument("Order");
			YFCElement eOrder = dApiInput.getDocumentElement();
			eOrder.setAttribute("OrderHeaderKey", eInput.getAttribute("OrderHeaderKey"));
			YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
			eOrderLine.setAttribute("OrderLineKey", eInput.getAttribute("OrderLineKey"));
			YFCElement eOrderHold = eOrderLine.createChild("OrderHoldTypes").createChild("OrderHoldType");
			eOrderHold.setAttribute("HoldType", eInput.getAttribute("HoldType"));
			eOrderHold.setAttribute("Status", "1100");
			BDAServiceApi.callApi(env, dApiInput.getDocument(), null, "changeOrder");
			
			return BDAServiceApi.callApi(env, dApiInput.getDocument(), null, "unScheduleOrder");
		}
		return YFCDocument.createDocument("Order").getDocument();
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "stealInventoryFromLine";
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		// TODO Auto-generated method stub
		return stealInventoryFromLine(env, input);
	}
	
	
}
