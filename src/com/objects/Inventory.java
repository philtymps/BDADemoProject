package com.objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import com.ibm.CallInteropServlet;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;

public class Inventory {

	public Inventory(int quantity, String sNode, String sProduct) {
		super();
		this.quantity = quantity;
		this.sNode = sNode;
		this.sProduct = sProduct;
	}
	public static void main(String[] args) throws InterruptedException{
		//ArrayList<Item> items = new ArrayList<Item>();
		
		List<String> arguments = Arrays.asList(args);
		
		String csvFile = arguments.get(arguments.indexOf("-file") + 1);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\\|";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int count = 1;
            YFCDocument dItemList = YFCDocument.createDocument("Items");
            int linesRead = 0;
            while ((line = br.readLine()) != null) {
            	String[] item = line.split(cvsSplitBy);
            	linesRead++;
            	if(Order.isItemUsed(item[1].trim())){
	                if(count % 750 == 0){
	                	System.out.println("Inventory Adjustment: " + count + " after " + linesRead + " lines read");
	                	CallInteropServlet.invokeApi(dItemList, null, "adjustInventory", "http://oms.omfulfillment.com:9080");
	                	dItemList = YFCDocument.createDocument("Items");
	                }
	            	
	            	count++;
                
                	try{
                		 Inventory i =  new Inventory(Integer.parseInt(item[2].trim()), item[0].trim(), item[1].trim());
                     	 i.generateAdjustment(dItemList.getDocumentElement(), "Bonton");
                	} catch (Exception e){
                		e.printStackTrace();
                	}
            	}
               
                
            }
            
            System.out.println(CallInteropServlet.invokeApi(dItemList, null, "adjustInventory", "http://oms.omfulfillment.com:9080"));
   	      
            
        } catch (Exception e){
        	e.printStackTrace();
        }
       	
	}
	
	public void generateAdjustment(YFCElement eItemList, String sInventoryOrg){
		YFCElement eItem = eItemList.createChild("Item");
		eItem.setAttribute("ItemID", this.getsProduct());
		eItem.setAttribute("ShipNode", this.getsNode());
		eItem.setAttribute("Quantity", this.getQuantity());
		eItem.setAttribute("AdjustmentType", "ADJUSTMENT");
		eItem.setAttribute("Availability", "TRACK");
		eItem.setAttribute("OrganizationCode", sInventoryOrg);
		eItem.setAttribute("SupplyType", "ONHAND");
		eItem.setAttribute("UnitOfMeasure", "EACH");
	}
	
	private int quantity;
    private String sNode, sProduct;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getsNode() {
		return sNode;
	}
	public void setsNode(String sNode) {
		this.sNode = sNode;
	}
	public String getsProduct() {
		return sProduct;
	}
	public void setsProduct(String sProduct) {
		this.sProduct = sProduct;
	}
}
