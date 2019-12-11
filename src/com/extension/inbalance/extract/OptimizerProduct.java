package com.extension.inbalance.extract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.extraction.BDASynchronizeItems;
import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.yfc.util.YFCCommon;

public class OptimizerProduct extends BDASynchronization {

	private static ArrayList<String> brand_ids;
	
	public static int getBrandID(String manName) { 
		if(YFCCommon.isVoid(brand_ids)) {
			brand_ids = new ArrayList<String>();
		}
		if(!brand_ids.contains(manName)) {
			brand_ids.add(manName);
		}
		return brand_ids.indexOf(manName);
	}
	
	private static String header() {
		return "sku_nbr|brnd_id|corp_unt_cost_amt|cstm_clor_desc|cust_facg_styl_desc|custfacg_clor_desc|dept_nbr|dept_nm|dir_ship_ind|dma_nbr|dma_nm|dpth_um_qty|ei_styl_id|gc_ind|gft_wrap_avl_ind|gma_nbr|gma_nm|gp_cde|hazds_ind|ht_um_qty|innr_pk_qty|maj_cl_nbr|maj_cl_nm|nrf_clor_cde|outr_pk_qty|po_shrt_cyc_ind|prd_gp_ind|rplnt_ind|sea_seq_nbr|ship_aln_ind|ship_srchg_fee_amt|size_cde|size_um_cde|sku_desc|styl_desc|sub_cl_nbr|sub_cl_nm|vnd_styl_id|wd_um_qty|web_exclv_ind|web_id|whse_rplnt_ind|wt_um_cde|wt_um_qty|eff_dte|landed_cost|rtl_price|sku_status_code|exp_date|safety_stock|category|is_sfs_eligible|brk_ind|sell_indv_ind|color|style|brand_desc|wd_um_cde|dpth_um_cde|ht_um_cde|unit_weight|unit_weight_uom|unit_height|unit_height_uom|unit_length|unit_length_uom|unit_width|unit_width_uom";
	}
	
	public static void main(String[] args){
		ArrayList<OptimizerProduct> products = new ArrayList<OptimizerProduct>();
		updateDimensions(products);
		BDASynchronizeItems.writeLine("/Users/pfaiola/optimizer", "SKU_WOOSCI_AURORA.csv", header());
		for(OptimizerProduct p : products){
			p.updateItem();
			BDASynchronizeItems.writeLine("/Users/pfaiola/optimizer", "SKU_WOOSCI_AURORA.csv", p.getRecord());
			BDASynchronizeItems.writeLine("/Users/pfaiola/BontonOptRef", "refSkuProperties.json", p.getJson());
		}
		System.out.println(products.size());
	}
	
	private double getSizeMultiplier(){
		if(YFCCommon.isVoid(size)){
			return 5;
		}
		try {
			return Double.parseDouble(size);
		} catch(Exception e){
			if(size.toLowerCase().startsWith("l")){
				return 6;
			} else if(size.toLowerCase().startsWith("m")){
				return 5;
			} else if(size.toLowerCase().startsWith("s")){
				return 4;
			} else if(size.toLowerCase().startsWith("xs")){
				return 3;
			} else if(size.toLowerCase().startsWith("xl")){
				return 6;
			} else {
				return 7;
			}
		}
	}
	
	public String toString(){
		String s = skuId + ":" + description + ":" + getItemType() + "::" + getWeight() + "lbs  ::  " + getWidth() + "in x " + getHeight() + "in x " + getDepth() + "in";
		return s;
	}
	
	private double getTypeMultiplier(){
		double t = 1;
		if(YFCCommon.isVoid(itemType)){
			return 1;
		}
		if(itemType.toLowerCase().contains("bike")){
			return 50;
		} 
		if(itemType.toLowerCase().contains("shoe")){
			t = 1.5;
		} else if(itemType.toLowerCase().contains("pants")){
			t = 1.25;
		} else if(itemType.toLowerCase().contains("skirt")){
			t = .85;
		} else if(itemType.toLowerCase().contains("helmet")){
			t = 5;
		} else if (itemType.toLowerCase().contains("nut") || itemType.toLowerCase().contains("bolt")){
			t = .1;
		}
		if(itemType.toLowerCase().contains("boy") || itemType.toLowerCase().contains("girl") || itemType.toLowerCase().contains("kid") || itemType.toLowerCase().contains("child")){
			t = t * .5;
		}
		return t;
	}
	public OptimizerProduct(String skuId, String description, String itemType, String size, float weight, float width, float height,
			float depth) {
		super();
		this.skuId = skuId;
		this.itemType = itemType;
		this.description = description;
		this.size = size;
		this.weight = weight;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.rand = Math.random() * getTypeMultiplier();
	}
	
	public OptimizerProduct(ResultSet rs) throws SQLException {
		super();
		this.skuId = rs.getString("ITEM_ID");
		this.itemType = rs.getString("ITEM_TYPE");
		this.description = rs.getString("SHORT_DESCRIPTION");
		this.size = rs.getString("ITEM_SIZE");
		this.weight = rs.getFloat("UNIT_WEIGHT");
		this.width = rs.getFloat("UNIT_WIDTH");
		this.height = rs.getFloat("UNIT_HEIGHT");
		this.depth = rs.getFloat("UNIT_LENGTH");
		this.manName = rs.getString("MANUFACTURER_NAME");
		this.color = rs.getString("ITEM_COLOR");
		this.itemGroupCode = rs.getString("ITEM_GROUP_CODE");
		this.isPickup = rs.getString("IS_PICKUP_ALLOWED");
		if(rs.getFloat("UNIT_COST") > 0){
			this.unit_cost = rs.getFloat("UNIT_COST");
		} else {
			this.unit_cost = rs.getFloat("LIST_PRICE") * .25;
		}
		this.list_price = rs.getFloat("LIST_PRICE");
		this.giftWrap = rs.getString("ALLOW_GIFT_WRAP");
	
		this.rand = Math.random() * getTypeMultiplier();
	}

	private String skuId, itemType, size, description, manName, color, giftWrap, itemGroupCode, isPickup;
	private double weight, width, height, depth, unit_cost, list_price;
	private double rand;
	private boolean updateRequired = false;
	
	
	private String getRecord() {
		StringBuilder sb = new StringBuilder();
		addRecord(sb, this.skuId);
		addRecord(sb, getBrandID(this.manName));
		addRecord(sb, this.unit_cost);
		addRecord(sb, this.color);
		addRecord(sb, this.size);
		addRecord(sb, this.color);
		sb.append("|||||||");
		addRecord(sb,  "N");
		addRecord(sb, YFCCommon.isVoid(giftWrap) ?  "Y" : giftWrap);
		sb.append("||");
		addRecord(sb, itemGroupCode);
		addRecord(sb, "N");
		sb.append("|1||||||");
		addRecord(sb, itemGroupCode);
		sb.append("||N||");
		addRecord(sb, size);
		sb.append("|");
		addRecord(sb, description);
		addRecord(sb, description);
		sb.append("|||||||LB||1/1/00|");
		addRecord(sb, unit_cost);
		addRecord(sb, list_price);
		sb.append("ACTIVE|12/31/00|||");
		addRecord(sb, YFCCommon.isVoid(isPickup) ? "Y" : isPickup);
		sb.append("||");
		addRecord(sb, color);
		addRecord(sb, size);
		addRecord(sb, description);
		sb.append("IN||IN|");
		addRecord(sb, weight);
		sb.append("LB|");
		addRecord(sb, height);
		sb.append("IN|");
		addRecord(sb, depth);
		sb.append("IN|");
		addRecord(sb, width);
		sb.append("IN");
		return sb.toString();
	}
	
	private static void addRecord(StringBuilder sb, String record) { 
		if(!YFCCommon.isVoid(record)) {
			sb.append(record);
		}
		sb.append("|");
	}
	
	private static void addRecord(StringBuilder sb, int record) { 
		if(!YFCCommon.isVoid(record)) {
			sb.append(record);
		}
		sb.append("|");
	}
	
	private static void addRecord(StringBuilder sb, double record) { 
		if(!YFCCommon.isVoid(record)) {
			sb.append(record);
		}
		sb.append("|");
	}
	
	
	private void updateItem(){
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection("oms.innovationcloud.info");
			String sSql = "UPDATE OMDB.YFS_ITEM SET UNIT_WEIGHT = ?, UNIT_HEIGHT = ?, UNIT_LENGTH = ?, UNIT_WIDTH = ? WHERE ITEM_ID = ?";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ps.setDouble(1, getNewWeight());
			ps.setDouble(2, getNewDepth());
			ps.setDouble(3, getNewHeight());
			ps.setDouble(4, getNewWidth());
			ps.setString(5, getSkuId());
			if(updateRequired) {
				ps.executeUpdate();
			}
		} catch(Exception e){
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
	
	public String getJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"_id\":{\"$oid\":\"");
		sb.append(OptimizerNode.getId().toString());
		sb.append("\"},\"skuId\":\"");
		sb.append(getSkuId());
		sb.append("\",\"weight\":");
		sb.append(getWeight());
		sb.append(",\"width\":");
		sb.append(getWidth());		
		sb.append(",\"height\":");
		sb.append(getHeight());		
		sb.append(",\"depth\":");
		sb.append(getDepth());
		sb.append("}");
		return sb.toString();
	}
		
	public static void updateDimensions(ArrayList<OptimizerProduct> products){
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection("oms.innovationcloud.info");
			String sSql = "SELECT TRIM(I.ITEM_ID) ITEM_ID, I.SHORT_DESCRIPTION, I.UNIT_COST, PL.LIST_PRICE, I.IS_PICKUP_ALLOWED, I.MANUFACTURER_NAME, I.DEPARTMENT, I.ALLOW_GIFT_WRAP, I.ITEM_GROUP_CODE, I.ITEM_TYPE, I.UNIT_WEIGHT, I.UNIT_WEIGHT_UOM, I.UNIT_HEIGHT, I.UNIT_HEIGHT_UOM, I.UNIT_LENGTH, I.UNIT_LENGTH_UOM, I.UNIT_WIDTH, I.UNIT_WIDTH_UOM, A.VALUE AS ITEM_SIZE, B.VALUE AS ITEM_COLOR" 
			+ " FROM OMDB.YFS_ITEM I "
			+ " LEFT JOIN OMDB.YFS_ADDITIONAL_ATTRIBUTE A ON (A.PARENT_KEY = I.ITEM_KEY AND LOWER(A.name) LIKE '%size%') "
			+ " LEFT JOIN OMDB.YFS_ADDITIONAL_ATTRIBUTE B ON (B.PARENT_KEY = I.ITEM_KEY AND LOWER(B.name) LIKE '%color%') "
			+ " LEFT JOIN OMDB.YPM_PRICELIST_LINE PL ON (PL.ITEM_ID = I.ITEM_ID AND TRIM(PL.PRICELIST_HDR_KEY) = 'AuroraPricelist') "
			+ " WHERE ITEM_GROUP_CODE = 'PROD' AND TRIM(ORGANIZATION_CODE) = 'Aurora-Corp' AND I.IS_MODEL_ITEM <> 'Y'";
			//String sSql = "SELECT TRIM(I.ITEM_ID) ITEM_ID, I.SHORT_DESCRIPTION, I.ITEM_TYPE, I.UNIT_WEIGHT, I.UNIT_WEIGHT_UOM, I.UNIT_HEIGHT, I.UNIT_HEIGHT_UOM, I.UNIT_LENGTH, I.UNIT_LENGTH_UOM, I.UNIT_WIDTH, I.UNIT_WIDTH_UOM, A.VALUE FROM OMDB.YFS_ITEM I LEFT JOIN OMDB.YFS_ADDITIONAL_ATTRIBUTE A ON (A.PARENT_KEY = I.ITEM_KEY AND LOWER(A.name) LIKE '%size%') WHERE ITEM_GROUP_CODE = 'PROD' AND TRIM(ORGANIZATION_CODE) = 'Aurora-Corp'";
			//String sSql = "SELECT TRIM(I.ITEM_ID) ITEM_ID, I.SHORT_DESCRIPTION, I.ITEM_TYPE, I.UNIT_WEIGHT, I.UNIT_WEIGHT_UOM, I.UNIT_HEIGHT, I.UNIT_HEIGHT_UOM, I.UNIT_LENGTH, I.UNIT_LENGTH_UOM, I.UNIT_WIDTH, I.UNIT_WIDTH_UOM, A.VALUE FROM OMDB.YFS_ITEM I LEFT JOIN OMDB.YFS_ADDITIONAL_ATTRIBUTE A ON (A.PARENT_KEY = I.ITEM_KEY AND LOWER(A.name) LIKE '%size%') WHERE ITEM_GROUP_CODE = 'PROD' AND ITEM_TYPE LIKE 'Fruit%' AND IS_MODEL_ITEM <> 'Y' AND IS_SHIPPING_ALLOWED = 'Y' AND ORGANIZATION_CODE = 'B'";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				products.add(new OptimizerProduct(rs));
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

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getItemType() {
		return itemType;
	}

	public String getDescription(){
		return description;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public double getWeight(){
		return weight;
	}

	public double getNewWeight() {
		if(!YFCCommon.isVoid(weight) && weight > 0) {
			return weight;
		}
		double t = Math.round(Math.random()  * 100) / 100d;
		if (t > 10){
			t = 10;
		}
		weight = (float) t;
		updateRequired = true;
		return t;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public double getWidth(){
		return width;
	}
	public double getNewWidth() {
		if(!YFCCommon.isVoid(width) && width > 0) {
			return width;
		}
		double t = Math.round(getWeight() * rand * 10d * 100) / 100d;
		if(t > 100){
			t = 100;
		}
		width = (float) t;
		updateRequired = true;
		return t;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public double getHeight(){
		return height;
	}
	public double getNewHeight() {
		if(!YFCCommon.isVoid(height) && height > 0) {
			return height;
		}
		double t = Math.round(getWeight() * 6d * rand * 100) / 100d;
		if( t > 100){
			t = 100;
		}
		height = (float) t;
		updateRequired = true;
		return t;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public double getDepth(){
		return depth;
	}
	public double getNewDepth() {
		if(!YFCCommon.isVoid(depth) && depth > 0) {
			return depth;
		}
		double t = Math.round(getWeight() * .5d * rand * 100) / 100d;
		if (t > 100){
			t = 100;
		}
		depth = (float) t;
		updateRequired = true;
		return t;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}
}
