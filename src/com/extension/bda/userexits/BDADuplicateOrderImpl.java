package com.extension.bda.userexits;

import java.util.Properties;

import org.w3c.dom.Document;

import com.custom.yantra.util.YFSUtil;
import com.extension.bda.service.IBDAService;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDADuplicateOrderImpl implements IBDAService {

	private Properties p;
	
	@Override
	public String getServiceName() {
		return "getDuplicateOrders";
	}

	@Override
	public void setProperties(Properties props) {
		p = props;
	}

	public String getDuplicateCheckingType(){
		if(p.contains("Type")){
			return (String) p.getProperty("Type");
		}
		return "simple";
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) {
		YFCDocument criteriaDoc = YFCDocument.createDocument("Order");
        YFCElement criteriaRoot = criteriaDoc.getDocumentElement();
        YFCDocument docOrder  = YFCDocument.getDocumentFor(input);
        YFCElement eleOrder = docOrder.getDocumentElement();
       
		if(getDuplicateCheckingType().equals("simple")){
		    if (YFSUtil.getDebug())
	        {
	        	System.out.println ("Entering SEGetDuplicateOrderCriteriaImpl - Input:");
	        	System.out.println (docOrder.getString());
	        }
	        
	       
	        String email = eleOrder.getAttribute("CustomerEMailID");
	        String sEnterpriseCode = eleOrder.getAttribute("EnterpriseCode");
	        String sSourceIPAddress = eleOrder.getAttribute("SourceIPAddress");
	        String sOrderName = eleOrder.getAttribute ("OrderName");
	        criteriaRoot.setAttribute("CustomerEMailID", email);
	        criteriaRoot.setAttribute("EnterpriseCode", sEnterpriseCode);
	        criteriaRoot.setAttribute("SourceIPAddress", sSourceIPAddress);
	        
	        if (!YFCCommon.isVoid(sOrderName))
	        	criteriaRoot.setAttribute("OrderName", sOrderName);
	        YFCDate fromDate = eleOrder.getDateTimeAttribute ("OrderDate");     
	        
	        if(!YFCCommon.isVoid(fromDate))
	        {
	        	// orders created within 20 seconds of each other
	        	YFCDate toDate = new YFCDate (fromDate);
	            toDate.setSeconds(toDate.getSeconds() + 10);
	            fromDate.setSeconds (fromDate.getSeconds() - 10);
	            criteriaRoot.setAttribute("OrderDateQryType", "BETWEEN");
	            criteriaRoot.setDateTimeAttribute("FromOrderDate", fromDate);
	            criteriaRoot.setDateTimeAttribute("ToOrderDate", toDate);
	        }

	        YFCElement elePriceInfo = eleOrder.getChildElement("PriceInfo");
	        if(elePriceInfo != null)
	        {
	            double totalAmount = elePriceInfo.getDoubleAttribute("TotalAmount");
	            double fromAmount = totalAmount - 2.5D;
	            if(fromAmount < 0.0D)
	                fromAmount = 0.0D;
	            double toAmount = totalAmount + 2.5D;
	            YFCElement criteriaPriceInfo = criteriaRoot.createChild("PriceInfo");
	            criteriaPriceInfo.setAttribute("TotalAmountQryType", "BETWEEN");
	            criteriaPriceInfo.setAttribute("FromTotalAmount", fromAmount);
	            criteriaPriceInfo.setAttribute("ToTotalAmount", toAmount);
	        }
	        // 
	        if (YFSUtil.getDebug())
	        {
	        	System.out.println ("Exiting SEGetDuplicateOrderCriteriaImpl - Output:");
	        	System.out.println (criteriaDoc.getString());
	        }
	        
	        
		}
		return criteriaDoc.getDocument();
		
	}

}
