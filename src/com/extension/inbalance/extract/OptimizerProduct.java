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

	public static void main(String[] args){
		ArrayList<OptimizerProduct> products = new ArrayList<OptimizerProduct>();
		updateDimensions(products);
		for(OptimizerProduct p : products){
			p.updateItem();
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

	private String skuId, itemType, size, description;
	private float weight, width, height, depth;
	private double rand;
	
	private void updateItem(){
		Connection dbConn = null;
		try {
			dbConn = getOMSConnection();
			String sSql = "UPDATE OMDB.YFS_ITEM SET UNIT_WEIGHT = ?, UNIT_HEIGHT = ?, UNIT_LENGTH = ?, UNIT_WIDTH = ? WHERE ITEM_ID = ?";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ps.setDouble(1, getNewWeight());
			ps.setDouble(2, getNewDepth());
			ps.setDouble(3, getNewHeight());
			ps.setDouble(4, getNewWidth());
			ps.setString(5, getSkuId());
			ps.executeUpdate();
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
			dbConn = getOMSConnection();
			String sSql = "SELECT TRIM(I.ITEM_ID) ITEM_ID, I.SHORT_DESCRIPTION, I.ITEM_TYPE, I.UNIT_WEIGHT, I.UNIT_WEIGHT_UOM, I.UNIT_HEIGHT, I.UNIT_HEIGHT_UOM, I.UNIT_LENGTH, I.UNIT_LENGTH_UOM, I.UNIT_WIDTH, I.UNIT_WIDTH_UOM, A.VALUE FROM OMDB.YFS_ITEM I LEFT JOIN OMDB.YFS_ADDITIONAL_ATTRIBUTE A ON (A.PARENT_KEY = I.ITEM_KEY AND LOWER(A.name) LIKE '%size%') WHERE ITEM_GROUP_CODE = 'PROD' AND TRIM(ORGANIZATION_CODE) = 'Bonton'";
			//String sSql = "SELECT TRIM(I.ITEM_ID) ITEM_ID, I.SHORT_DESCRIPTION, I.ITEM_TYPE, I.UNIT_WEIGHT, I.UNIT_WEIGHT_UOM, I.UNIT_HEIGHT, I.UNIT_HEIGHT_UOM, I.UNIT_LENGTH, I.UNIT_LENGTH_UOM, I.UNIT_WIDTH, I.UNIT_WIDTH_UOM, A.VALUE FROM OMDB.YFS_ITEM I LEFT JOIN OMDB.YFS_ADDITIONAL_ATTRIBUTE A ON (A.PARENT_KEY = I.ITEM_KEY AND LOWER(A.name) LIKE '%size%') WHERE ITEM_GROUP_CODE = 'PROD' AND ITEM_TYPE LIKE 'Fruit%' AND IS_MODEL_ITEM <> 'Y' AND IS_SHIPPING_ALLOWED = 'Y' AND ORGANIZATION_CODE = 'B'";
			PreparedStatement ps = dbConn.prepareStatement(sSql);
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				products.add(new OptimizerProduct(rs.getString("ITEM_ID"), rs.getString("SHORT_DESCRIPTION"), rs.getString("ITEM_TYPE"), rs.getString("VALUE"), rs.getFloat("UNIT_WEIGHT"), rs.getFloat("UNIT_WIDTH"), rs.getFloat("UNIT_LENGTH"), rs.getFloat("UNIT_HEIGHT")));
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
	
	public float getWeight(){
		return weight;
	}

	public double getNewWeight() {
		double t = Math.round(Math.random()  * 100) / 100d;
		if (t > 10){
			t = 10;
		}
		weight = (float) t;
		return t;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getWidth(){
		return width;
	}
	public double getNewWidth() {
		double t = Math.round(getWeight() * rand * 10d * 100) / 100d;
		if(t > 100){
			t = 100;
		}
		width = (float) t;
		return t;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight(){
		return height;
	}
	public double getNewHeight() {
		
		double t = Math.round(getWeight() * 6d * rand * 100) / 100d;
		if( t > 100){
			t = 100;
		}
		height = (float) t;
		return t;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getDepth(){
		return depth;
	}
	public double getNewDepth() {
		double t = Math.round(getWeight() * .5d * rand * 100) / 100d;
		if (t > 100){
			t = 100;
		}
		depth = (float) t;
		return t;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}
}
