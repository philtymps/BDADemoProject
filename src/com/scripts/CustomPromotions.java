package com.scripts;

import java.util.Locale;

import org.w3c.dom.Document;

import com.ibm.icu.text.NumberFormat;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class CustomPromotions {

	private static double FREE_SHIPPING = 100.00;
	
	public Document getPromotions(YFSEnvironment env, Document inputDoc){
		YFCDocument dPromotions = YFCDocument.createDocument("Promotions");
		try {	
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			Document l_OutputXml = null;
			try {
				env.setApiTemplate("getOrderDetails", getOrderDetailsTemplate());
				l_OutputXml = localApi.invoke(env, "getOrderDetails", inputDoc);
				if(!YFCCommon.isVoid(l_OutputXml)){
					YFCDocument dOutput = YFCDocument.getDocumentFor(l_OutputXml);
					YFCElement eOrder = dOutput.getDocumentElement();
					boolean requiresShipping = false;
					for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
						if(YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod"), "SHP")){
							requiresShipping = true;
							break;
						}
					}
					if(requiresShipping){
						YFCElement ePriceInfo = eOrder.getChildElement("PriceInfo");
						if(!YFCCommon.isVoid(ePriceInfo)){
							double total = ePriceInfo.getDoubleAttribute("TotalAmount", 0);
							if(total < FREE_SHIPPING && total > 80){
								YFCElement ePromotion = dPromotions.getDocumentElement().createChild("Promotion");
								Locale us = new Locale("en", "us");
								NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(us);
								ePromotion.setAttribute("Description", "Spend " + currencyFormatter.format(FREE_SHIPPING - total) + " more and earn free shipping!");
							}
						}
					}
					
				}
				
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
		} catch (Exception e ) {
			System.out.println("Could not initialize the yifclient.properties.");
			System.out.println("Bad stuff happened trying to initialize the Yif Client.");
			e.printStackTrace();
		}
		return dPromotions.getDocument();
	}
	
	private Document getOrderDetailsTemplate(){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("DeliveryMethod", "");
		eOrderLine.setAttribute("CarrierServiceCode", "");
		eOrderLine.setAttribute("OrderedQty", "");
		YFCElement eOrderTotal = eOrder.createChild("PriceInfo");
		eOrderTotal.setAttribute("Currency", "");
		eOrderTotal.setAttribute("TotalAmount", "");
		return dOrder.getDocument();
	}
		
}
