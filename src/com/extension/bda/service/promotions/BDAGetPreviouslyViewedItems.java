package com.extension.bda.service.promotions;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetPreviouslyViewedItems implements IBDAService {

	public Document getPreviouslyViewedItems(YFSEnvironment env, Document inDoc){
		YFCElement eInput = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		System.out.println("Previously Viewed input: " + eInput);
		
		YFCDocument dCustomers = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/recentlyViewedItems.xml");
		YFCDocument dOutput = YFCDocument.createDocument("RecommendedItemList");
		YFCElement eRecommendedItemList = dOutput.getDocumentElement();

		int i = 0;
		for (YFCElement eCustomer : dCustomers.getDocumentElement().getChildren()){
			if (eCustomer.getAttribute("Email").equals("cassie.smith@gmail.com")){
				for(YFCElement eItem : eCustomer.getChildElement("ItemList").getChildren()){
					eRecommendedItemList.importNode(eItem);
					i++;
				}
			}
		}
		eRecommendedItemList.setAttribute("TotalItemList", i);
		eRecommendedItemList.setAttribute("TotalNumberOfRecords", i);	
		
		return dOutput.getDocument();
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getPreviouslyViewedItems";
	}

	@Override
	public void setProperties(Properties props) {
	
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		return getPreviouslyViewedItems(env, input);
	}
	
}
