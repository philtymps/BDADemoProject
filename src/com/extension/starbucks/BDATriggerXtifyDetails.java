package com.extension.starbucks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDATriggerXtifyDetails {
	private Properties properties;
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public BDATriggerXtifyDetails(){
		properties = new Properties();
	}
	
	private String getMessage(){
		if (!YFCCommon.isVoid(getProperty("Message"))){
			return (String)getProperty("Message");
		} 
		return "One of the items on your order is currently unavailable.";
	}
	
	private String getSubject(){
		if (!YFCCommon.isVoid(getProperty("Subject"))){
			return (String)getProperty("Subject");
		} 
		return "We're very sorry!";
	}
	
	private String getAction(){
		if (!YFCCommon.isVoid(getProperty("Action"))){
			return (String)getProperty("Action");
		} 
		return "URL";
	}
	
	private String getActionButton(){
		if (!YFCCommon.isVoid(getProperty("ActionButton"))){
			return (String)getProperty("ActionButton");
		} 
		return "Update Order";
	}
	
	private String getXID(){
		if(!YFCCommon.isVoid(getProperty("XID"))){
			return (String) getProperty("XID");
		} else if(!YFCCommon.isVoid(getProperty("Loginid"))){
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://api.isdemocloud.info/api/models/list/" + (String) getProperty("Loginid") + "/scdemoapp.xml");
			HttpResponse response;
			try {
				response = client.execute(request);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				YFCDocument dModels = YFCDocument.parse(rd);
				if(!YFCCommon.isVoid(dModels)){
					YFCElement eModels = dModels.getDocumentElement();
					String sXIDs = "";
					for(YFCElement eModel : eModels.getChildren()){
						if(!YFCCommon.isVoid(eModel.getAttribute("xid"))){
							if(!YFCCommon.isVoid(sXIDs)){
								sXIDs += ",";
							}
							sXIDs += eModel.getAttribute("xid");
						}
					}
					return sXIDs;
				}
			} catch (IOException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private String getURL(YFCElement eShipment){
		String sHost = "http://oms.service.isdemocloud.info:51234";
		if(!YFCCommon.isVoid(getProperty("URL"))){
			return (String)getProperty("URL");
		} else if(!YFCCommon.isVoid(getProperty("URLHost"))){
			sHost = (String) getProperty("URLHost");
		}
		String page = "/starbucks/#changeOrder";
		if(!YFCCommon.isVoid(getProperty("URLPage"))){
			page = (String) getProperty("URLPage");
		}
		String content = sHost + page;
		String sQueryString = getApplicableOrderLine(eShipment);
		if(!YFCCommon.isVoid(sQueryString)){
			content += sQueryString;
			return content;
		}
		return null;
	}
	
	private String getApplicableOrderLine(YFCElement eShipment){
		if(!YFCCommon.isVoid(eShipment)){
			for(YFCElement eShipmentLine : eShipment.getChildElement("ShipmentLines", true).getChildren()){
				if(eShipmentLine.getDoubleAttribute("ShortageQty", 0) > 0){
					return "?&OrderHeaderKey=" + eShipmentLine.getAttribute("OrderHeaderKey") + "&OrderLineKey=" + eShipmentLine.getAttribute("OrderLineKey");
				}
			}
		}
		return null;
	}
	
	private Document getShipmentDetailsTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dOutput.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", "");
		YFCElement eShipmentLine = eShipment.createChild("ShipmentLines").createChild("ShipmentLine");
		eShipmentLine.setAttribute("OrderHeaderKey", "");
		eShipmentLine.setAttribute("OrderLineKey", "");
		eShipmentLine.setAttribute("ShortageQty", "");
		eShipmentLine.setAttribute("ItemID", "");
		return dOutput.getDocument();
	}
	
	private YFCElement getShipmentLines(YFSEnvironment env, String sShipmentKey){
		YIFApi localApi;
		YFCDocument dInput = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dInput.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", sShipmentKey);
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("getShipmentDetails", getShipmentDetailsTemplate());
			Document dShipment = localApi.invoke(env, "getShipmentDetails", dInput.getDocument());
			if(!YFCCommon.isVoid(dShipment)){
				YFCDocument shipment = YFCDocument.getDocumentFor(dShipment);
				return shipment.getDocumentElement();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return eShipment;	
	}

	
	public Document getPushDetails(YFSEnvironment env, Document inputDoc){
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		YFCDocument dMessage = YFCDocument.createDocument("Message");
		YFCElement eMessage = dMessage.getDocumentElement();
		eMessage.setAttribute("Message", getMessage());
		eMessage.setAttribute("Subject", getSubject());
		eMessage.setAttribute("Action", getAction());
		eMessage.setAttribute("ActionButton", getActionButton());
		if(getAction().equals("URL")){
			eMessage.setAttribute("URL", getURL(getShipmentLines(env, dInput.getDocumentElement().getAttribute("ShipmentKey"))));
		}
		//eMessage.setAttribute("XID", getXID());
		return dMessage.getDocument();
	}
		
}
