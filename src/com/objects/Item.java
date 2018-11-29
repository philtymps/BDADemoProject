package com.objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibm.CallInteropServlet;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;

public class Item {
	
	public Item(String itemID, String name, String retail, String web_eligible_flg, String gmm_code, String gmm_name,
			String dmm_code, String dmm_name, String dept_code, String dept_name, String class_code, String class_name,
			String subclass_code, String subclass_name, double cost_amt) {
		super();
		this.itemID = itemID;
		this.name = name;
		this.retail = retail;
		this.web_eligible_flg = web_eligible_flg;
		this.gmm_code = gmm_code;
		this.gmm_name = gmm_name;
		this.dmm_code = dmm_code;
		this.dmm_name = dmm_name;
		this.dept_code = dept_code;
		this.dept_name = dept_name;
		this.class_code = class_code;
		this.class_name = class_name;
		this.subclass_code = subclass_code;
		this.subclass_name = subclass_name;
		this.cost_amt = cost_amt;
	}
	public static void main(String[] args) throws InterruptedException{
		//ArrayList<Item> items = new ArrayList<Item>();
		
		List<String> arguments = Arrays.asList(args);
		
		String csvFile = arguments.get(arguments.indexOf("-file") + 1);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "�";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int count = 1;
            YFCDocument dItemList = YFCDocument.createDocument("ItemList");
            while ((line = br.readLine()) != null) {
            	String[] item = line.split(cvsSplitBy);
            	if(Order.isItemUsed(item[0].trim())){
            		if(count % 500 == 0){
            			System.out.println("Installed " + count + " items");
	                	System.out.println(CallInteropServlet.invokeApi(dItemList, null, "manageItem", "http://oms.innovationcloud.info:9080"));
	         	       
	                	dItemList = YFCDocument.createDocument("ItemList");
	                }
	            	
	            	count++;
	                if(item.length > 16){
	                	try{
	                		 Item i =  new Item(item[0], Store.capitalizeString(item[3].trim()), Store.capitalizeString(item[2].trim()), item[16].trim(), item[4].trim(), 
	                          		Store.capitalizeString(item[5].trim()), item[6].trim(), Store.capitalizeString(item[7].trim()), item[8].trim(),
	                          		Store.capitalizeString(item[9].trim()), item[10].trim(), Store.capitalizeString(item[11].trim()), 
	                          		item[12].trim(), Store.capitalizeString(item[13].trim()), Double.parseDouble(item[1].trim()));
	                     	 i.getManageItem(dItemList.getDocumentElement(), "Bonton");
	                	} catch (Exception e){
	                		e.printStackTrace();
	                	}
	                } else if(item.length > 15){
	                	try{
	               		 Item i =  new Item(item[0], Store.capitalizeString(item[3].trim()), Store.capitalizeString(item[2].trim()), "Y", item[4].trim(), 
	                         		Store.capitalizeString(item[5].trim()), item[6].trim(), Store.capitalizeString(item[7].trim()), item[8].trim(),
	                         		Store.capitalizeString(item[9].trim()), item[10].trim(), Store.capitalizeString(item[11].trim()), 
	                         		item[12].trim(), Store.capitalizeString(item[13].trim()), Double.parseDouble(item[1].trim()));
	                    	 i.getManageItem(dItemList.getDocumentElement(), "Bonton");
		               	} catch (Exception e){
		               		e.printStackTrace();
		               	}
	                }
            	}
                
            }
            
            System.out.println(CallInteropServlet.invokeApi(dItemList, null, "manageItem", "http://oms.innovationcloud.info:9080"));
   	      
            
        } catch (Exception e){
        	e.printStackTrace();
        }
       
        	
		
	}
	
	private String itemID, name, retail, web_eligible_flg, gmm_code, gmm_name, dmm_code, dmm_name, dept_code, dept_name, class_code, class_name, subclass_code, subclass_name;
	private double cost_amt;
	
	public void getManageItem(YFCElement eItemList, String sCatalogOrg){
		YFCElement eItem = eItemList.createChild("Item");
		eItem.setAttribute("ItemID", this.getItemID());
		eItem.setAttribute("ItemGroupCode", "PROD");
		eItem.setAttribute("OrganizationCode", sCatalogOrg);
		eItem.setAttribute("UnitOfMeasure", "EACH");
		
		YFCElement ePrimaryInfo = eItem.createChild("PrimaryInformation");
		ePrimaryInfo.setAttribute("ShortDescription", this.getName());
		ePrimaryInfo.setAttribute("Description", this.getName());
		ePrimaryInfo.setAttribute("Status", "3000");
		
		YFCElement eClass = eItem.createChild("ClassificationCodes");
		eClass.setAttribute("StorageType", "Standard");
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRetail() {
		return retail;
	}
	public void setRetail(String retail) {
		this.retail = retail;
	}
	public String getWeb_eligible_flg() {
		return web_eligible_flg;
	}
	public void setWeb_eligible_flg(String web_eligible_flg) {
		this.web_eligible_flg = web_eligible_flg;
	}
	public String getGmm_code() {
		return gmm_code;
	}
	public void setGmm_code(String gmm_code) {
		this.gmm_code = gmm_code;
	}
	public String getGmm_name() {
		return gmm_name;
	}
	public void setGmm_name(String gmm_name) {
		this.gmm_name = gmm_name;
	}
	public String getDmm_code() {
		return dmm_code;
	}
	public void setDmm_code(String dmm_code) {
		this.dmm_code = dmm_code;
	}
	public String getDmm_name() {
		return dmm_name;
	}
	public void setDmm_name(String dmm_name) {
		this.dmm_name = dmm_name;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getClass_code() {
		return class_code;
	}
	public void setClass_code(String class_code) {
		this.class_code = class_code;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getSubclass_code() {
		return subclass_code;
	}
	public void setSubclass_code(String subclass_code) {
		this.subclass_code = subclass_code;
	}
	public String getSubclass_name() {
		return subclass_name;
	}
	public void setSubclass_name(String subclass_name) {
		this.subclass_name = subclass_name;
	}
	public double getCost_amt() {
		return cost_amt;
	}
	public void setCost_amt(double cost_amt) {
		this.cost_amt = cost_amt;
	}
}
