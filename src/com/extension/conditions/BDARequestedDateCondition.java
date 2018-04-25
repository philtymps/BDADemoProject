package com.extension.conditions;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.w3c.dom.Document;

import com.ibm.icu.util.Calendar;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDARequestedDateCondition implements YCPDynamicConditionEx  {

	private Map props;
	
	@Override
	public boolean evaluateCondition(YFSEnvironment env, String name, Map mapData, Document doc) {
		YIFApi localApi;
		try {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.add(Calendar.DATE, 10);
			localApi = YIFClientFactory.getInstance().getLocalApi();
			Document dOrderLine = getOrderLineDetails(localApi, env, (String) mapData.get("OrderLineKey"));
			if(!YFCCommon.isVoid(dOrderLine)){
				YFCElement eOrderLine = YFCDocument.getDocumentFor(dOrderLine).getDocumentElement();
				if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReqShipDate")) || !YFCCommon.isVoid(eOrderLine.getAttribute("ReqDeliveryDate"))){
					if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReqShipDate")) && eOrderLine.getDateAttribute("ReqShipDate").after(c.getTime())){
						return true;
					} else if(!YFCCommon.isVoid(eOrderLine.getAttribute("ReqDeliveryDate")) && eOrderLine.getDateAttribute("ReqDeliveryDate").after(c.getTime())){
						return true;
					}
				} else {
					YFCElement eOrder = eOrderLine.getChildElement("Order");
					if(!YFCCommon.isVoid(eOrder) && (!YFCCommon.isVoid(eOrder.getAttribute("ReqShipDate")) || !YFCCommon.isVoid(eOrder.getAttribute("ReqDeliveryDate")))){
						if(!YFCCommon.isVoid(eOrder.getAttribute("ReqShipDate")) && eOrder.getDateAttribute("ReqShipDate").after(c.getTime())){
							return true;
						} else if(!YFCCommon.isVoid(eOrder.getAttribute("ReqDeliveryDate")) && eOrder.getDateAttribute("ReqDeliveryDate").after(c.getTime())){
							return true;
						}
					}
				}
			}
			
		} catch (YIFClientCreationException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setProperties(Map map) {
		props = map;
	}

	private String getProperty(String sProperty, Map mapData){
		if(mapData.containsKey(sProperty)){
			return (String) mapData.get(sProperty);
		} else if(!YFCCommon.isVoid(props) && props.containsKey(sProperty)){
			return (String) props.get(sProperty);
		}
		return null;
	}
	
	private Document getOrderLineDetails(YIFApi localApi, YFSEnvironment env, String sOrderLineKey){
		YFCDocument dInput = YFCDocument.createDocument("OrderLineDetail");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrderLineKey", sOrderLineKey);
		try {
			env.setApiTemplate("getOrderLineDetails", getOrderLineDetailsTemplate());
			return localApi.getOrderLineDetails(env, dInput.getDocument());
		} catch (YFSException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Document getOrderLineDetailsTemplate(){
		YFCDocument dOrderLine = YFCDocument.createDocument("OrderLine");
		YFCElement eOrderLine = dOrderLine.getDocumentElement();
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("ReqDeliveryDate", "");
		eOrderLine.setAttribute("ReqShipDate", "");
		YFCElement eOrder = eOrderLine.createChild("Order");
		
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("ReqDeliveryDate", "");
		eOrder.setAttribute("ReqShipDate", "");
		return dOrderLine.getDocument();
	}
}
