package com.extension.sfdc;

import java.sql.Timestamp;
import java.util.Properties;

import org.w3c.dom.Document;

import com.bda.sfdc.SFDCOrder;
import com.extension.silverpop.SilverpopEmail;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAToSFDCOrderUpsert {

	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(BDAToSFDCOrderUpsert.class);
	
	public BDAToSFDCOrderUpsert(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public Document upsertOrderToSFDC(YFSEnvironment env, Document input){
		YFCElement eOrder = YFCDocument.getDocumentFor(input).getDocumentElement();
		if(!YFCCommon.isVoid(eOrder) && !eOrder.getBooleanAttribute("DraftOrderFlag", false)){
			try {
				YFCDate date = eOrder.getDateTimeAttribute("OrderDate");
				SFDCOrder order = new SFDCOrder(eOrder.getAttribute("OrderHeaderKey"), eOrder.getAttribute("OrderNo"), eOrder.getAttribute("CustomerEMailID"), eOrder.getAttribute("Status"), Float.parseFloat(eOrder.getChildElement("PriceInfo").getAttribute("TotalAmount")), new Timestamp(date.getTime()));
				order.sendToSFDC(eOrder.getAttribute("EnterpriseCode"));
			}catch(Exception e){
				logger.info("Errored when upserting to SFDC");
				logger.info(e.getMessage());
			}
		}
		
		
		return input;
	
	}
}
