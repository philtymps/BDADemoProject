package com.extension.bda.service.promotions;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGetPreviouslyViewedItems implements IBDAService {

	private Properties props;
	public void setProperties(Properties props) { 
		this.props = props;
	}
	
	public String getFileName(YFSEnvironment env) {
		if(!YFCCommon.isVoid(this.props.getProperty("FileName"))) {
			return this.props.getProperty("FileName");
		}
		return BDAServiceApi.getScriptsPath(env) + "/recentlyViewedItems.xml";
	}
	public Document getPreviouslyViewedItems(YFSEnvironment env, Document inDoc){
		YFCDocument dOutput = YFCDocument.createDocument("RecommendedItemList");
		YFCElement eRecommendedItemList = dOutput.getDocumentElement();

		int i = 0;
		
		if(!YFCCommon.isVoid(inDoc)){
			YFCElement eLastViewed = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
			System.out.println("Previously Viewed input: " + eLastViewed);
			
			File recentlyViewed = new File(getFileName(env));
			if(recentlyViewed.exists()){
				ArrayList<String> emailAddress = getEmailForCustomer(env, eLastViewed);
				YFCDocument dCustomers = YFCDocument.getDocumentForXMLFile(getFileName(env));
				for (YFCElement eCustomer : dCustomers.getDocumentElement().getChildren()){
					if (emailAddress.contains(eCustomer.getAttribute("Email"))){
						for(YFCElement eItem : eCustomer.getChildElement("ItemList").getChildren()){
							eRecommendedItemList.importNode(eItem);
							i++;
						}
					}
				}
			}
		}
		
		eRecommendedItemList.setAttribute("TotalItemList", i);
		eRecommendedItemList.setAttribute("TotalNumberOfRecords", i);	
		return dOutput.getDocument();
	}
	
	public static YFCElement getCustomerDetails(YFSEnvironment env, YFCElement eLastViewed){
		try {
			YFCDocument dInput = YFCDocument.createDocument("Customer");
			dInput.getDocumentElement().setAttribute("CustomerKey", eLastViewed.getChildElement("Customer").getAttribute("CustomerKey"));
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			YFCDocument dTemplate = YFCDocument.createDocument("Customer");
			dTemplate.getDocumentElement().setAttribute("CustomerID", "");
			dTemplate.getDocumentElement().setAttribute("CustomerType", "");
			YFCElement eContactT = dTemplate.getDocumentElement().createChild("CustomerContactList").createChild("CustomerContact");
			eContactT.setAttribute("CustomerContactID", "");
			eContactT.setAttribute("DayPhone", "");
			eContactT.setAttribute("EmailID", "");
			eContactT.setAttribute("UserID", "");
			env.setApiTemplate("getCustomerDetails", dTemplate.getDocument());
			Document l_OutputXml = localApi.invoke(env, "getCustomerDetails", dInput.getDocument());
			YFCElement eResponse = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
			return eResponse;
			
		} catch(Exception yex) {
			yex.printStackTrace();
        } 
		return null;
	}

	public static ArrayList<String> getEmailForCustomer(YFSEnvironment env, YFCElement eLastViewed){
		ArrayList<String> emails = new ArrayList<String>();
		YFCElement eResponse = BDAGetPreviouslyViewedItems.getCustomerDetails(env, eLastViewed);
		if(!YFCCommon.isVoid(eResponse)){
			for(YFCElement eContact : eResponse.getChildElement("CustomerContactList", true).getChildren()){
				if(!YFCCommon.isVoid(eContact.getAttribute("EmailID"))){
					emails.add(eContact.getAttribute("EmailID"));
				}					
			}
		}
		return emails;
	}
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getPreviouslyViewedItems";
	}


	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		return getPreviouslyViewedItems(env, input);
	}
	
}
