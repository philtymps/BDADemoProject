package com.extension.inbalance.extract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.bson.types.ObjectId;

import com.extension.inbalance.OptimizerCostOptions;
import com.ibm.extraction.BDASynchronizeItems;
import com.ibm.extraction.commerce.BDASynchronization;

public class OptimizerNode extends BDASynchronization {

	public OptimizerNode(String nodeId, String shipNodeType, String cutOffTime, String timezone, String nodeDesc,
			String lat, String longitude, String nodeType, boolean useForFulfillment, int zipCode) {
		super();
		this.nodeId = nodeId;
		this.shipNodeType = shipNodeType;
		this.cutOffTime = cutOffTime;
		this.timezone = timezone;
		this.nodeDesc = nodeDesc;
		this.lat = lat;
		this.longitude = longitude;
		this.nodeType = nodeType;
		this.useForFulfillment = useForFulfillment;
		this.zipCode = zipCode;
	}

	private String nodeId, shipNodeType, cutOffTime, timezone, nodeDesc, lat, longitude, nodeType;
	private HashMap<String, NodeCapacity> capMap;
	private boolean useForFulfillment = false;
	private int zipCode;
	
	public NodeCapacity getCapacity(String dayOfWeek){
		if(capMap == null){
			capMap = new HashMap<String, NodeCapacity>();
			SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
			ArrayList<String> array = new ArrayList<String>();
			array.add(this.getNodeId());
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			for(int i = 0; i < 7; i++){
				//capMap.put(formatter.format(c.getTime()), OptimizerCostOptions.getCapacityInfo(array, i).get(getNodeId()));
				c.add(Calendar.DATE, 1);
			}
		}
		return capMap.get(dayOfWeek);
	}
	
	public static void main(String[] args){
		ArrayList<OptimizerNode> nodes = new ArrayList<OptimizerNode>();
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection("oms.innovationcloud.info");
			String sSql = "	SELECT TRIM(S.SHIP_NODE), P.ZIP_CODE, S.NODE_TYPE, L.TIMEZONE, S.DESCRIPTION, P.LATITUDE, P.LONGITUDE, S.NODE_TYPE FROM OMDB.YFS_SHIP_NODE S INNER JOIN OMDB.YFS_PERSON_INFO P ON P.PERSON_INFO_KEY = S.SHIP_NODE_ADDRESS_KEY INNER JOIN OMDB.YFS_LOCALE L ON L.LOCALECODE = S.LOCALECODE WHERE S.OWNER_KEY = 'Bonton'";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				nodes.add(new OptimizerNode(rs.getString(1), rs.getString(3), "12:00", rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), true, rs.getInt(2)));
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
		if(!nodes.isEmpty()){
			for(OptimizerNode n : nodes){
				BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "bodBacklog.json", n.getBodBacklog());
				BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "refNodePropertiesV4.json", n.getJson());
				BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "nodeBalancingConstants.json", n.getNodeBalanceConstants());
				BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "capacityConsumption.json", n.getCapCons());
				BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "derivedCapacity.json", n.getDerivedCap(365));
				BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "refNodeCarrierModeAvail.json", n.getNodeCarrierModes());
				
			}
		}
	}

	public String getCapCons(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(getId().toString());
		sb.append("\"},\"nodeId\":\"");
		sb.append(getNodeId());
		sb.append("\",\"capConsumption\":0.0}");
		return sb.toString();
	}
	
	public String getDerivedCap(int days){
		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
		for(int i = 0; i < days; i++){
			NodeCapacity cap = getCapacity(formatter.format(c.getTime()));
			if(getNodeType().equals("Store")){
				sb.append(getDerivedCapDay(c.getTime(), Math.round(cap.getCapacity())));
			} else {
				sb.append(getDerivedCapDay(c.getTime(), Math.round(cap.getCapacity())));
			}
			if(days - 1 != i){
				sb.append("\n");
				c.add(Calendar.DATE, 1);
			}
		
		}
		return sb.toString();
		
	}
	
	private String getBodBacklog(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(getId().toString());
		sb.append("\"},\"nodeId\":\"");
		sb.append(getNodeId());
		sb.append("\",\"date\":null,\"bodBacklog\":NumberInt(0)}");
		return sb.toString();
	}
	
	private String getNodeBalanceConstants(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(getId().toString());
		sb.append("\"},\"nodeId\":\"");
		sb.append(getNodeId());
		sb.append("\",\"date\":null,\"threshold\":0.7,\"overCapacityCost\":0.5,\"sacrifice\":0.9}");
		return sb.toString();
	}
	private String getDerivedCapDay(Date d, int quantity){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(getId().toString());
		sb.append("\"},\"nodeId\":\"");
		sb.append(getNodeId());
		sb.append("\",\"date\":{\"$date\":\"");
		sb.append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(d) + ".000Z");
		sb.append("\"},\"derivedCapacity\":NumberInt(");
		sb.append(quantity);
		sb.append(")}");
		return sb.toString();
	}
	
	public String getNodeCarrierModes(){
		StringBuilder sb = new StringBuilder();
		if(getNodeType().equals("Store")){
			//if(Double.parseDouble(getLongitude()) < -115d){
				for(int i = 50; i < 55; i++){
					sb.append(getNodeCarrierMode(i));
					if(54 != i){
						sb.append("\n");
					}
				}
			//} else {
				for(int i = 0; i < 5; i++){
					sb.append(getNodeCarrierMode(i));
					if(4 != i){
						sb.append("\n");
					}
				}
			//}
		} else {
			for(int i = 0; i < 5; i++){
				sb.append(getNodeCarrierMode(i));
				sb.append("\n");
			}
			for(int i = 50; i < 55; i++){
				sb.append(getNodeCarrierMode(i));
				sb.append("\n");
			}
			sb.append(getNodeCarrierMode(150));
			sb.append("\n");
			sb.append(getNodeCarrierMode(200));
		}
		
		return sb.toString();
		
	}
	
	private String getNodeCarrierMode(int mode){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(getId().toString());
		sb.append("\"},\"nodeId\":\"");
		sb.append(getNodeId());
		sb.append("\",\"carrierMode\":NumberInt(");
		sb.append(mode);
		sb.append(")}");
		return sb.toString();
	}
	
	public static ObjectId getId() {
		return new ObjectId();
	}
	
	public String getJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(getId().toString());
		sb.append("\"},\"nodeId\":\"");
		sb.append(getNodeId());
		sb.append("\",\"zipCode\":\"");
		sb.append(getZipCode());
		sb.append("\",\"shipNodeType\":\"");
		sb.append(getShipNodeType());
		sb.append("\",\"cutOffTime\":\"");
		sb.append(getCutOffTime());
		sb.append("\",\"timezone\":\"");
		sb.append(getTimezone());
		sb.append("\",\"nodeDesc\":\"");
		sb.append(getNodeDesc());
		sb.append("\",\"lat\":");
		sb.append(getLat());
		sb.append(",\"long\":");
		sb.append(getLongitude());
		sb.append(",\"nodeType\":\"");
		sb.append(getNodeType());
		sb.append("\",\"useForFulfillment\":\"");
		sb.append(getUseForFulfillment());
		sb.append("\"}");
		return sb.toString();
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getShipNodeType() {
		return shipNodeType;
	}

	public void setShipNodeType(String shipNodeType) {
		this.shipNodeType = shipNodeType;
	}

	public String getCutOffTime() {
		return cutOffTime;
	}

	public void setCutOffTime(String cutOffTime) {
		this.cutOffTime = cutOffTime;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getNodeDesc() {
		return nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public boolean isUseForFulfillment() {
		return useForFulfillment;
	}

	public String getUseForFulfillment(){
		if(isUseForFulfillment()){
			return "Y";
		}
		return "N";
	}
	public void setUseForFulfillment(boolean useForFulfillment) {
		this.useForFulfillment = useForFulfillment;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
}
