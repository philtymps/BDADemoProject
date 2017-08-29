package com.extension.xtify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class TriggerXtify {

	private Properties properties;
	private Properties xProperties;
	private static YFCLogCategory logger = YFCLogCategory.instance(TriggerXtify.class);
	private static long lastPushAttempt = 0;
	
	public static void main(String[] args) {		
		TriggerXtify temp = new TriggerXtify();
		/*YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("ShipmentKey", "20140724103542857564_S");
		eOrder.setAttribute("OrderNo", "WC_24008");
		eOrder.setAttribute("EnterpriseCode", "AuroraB2B");
		eOrder.setAttribute("DocumentType", "0001");*/
		//String sXid = "5411cc571fde007c80a41e08";
		//String sXid = "53d05baa1fde007c80cd115d";
		String sXid = "564627575f43e08a81e6b3ee";
		
		//String sXid = "546ea3031fde007c80f1063e";
		try {
			//temp.callRESTConservatively(sXid, temp.getTag(), "Store Picking Required", "There are orders in the store that require picking");
			//JSONObject payload = null;
			JSONObject payload = new JSONObject();
			payload.put("OrderHeaderKey", "201503111340291765231");
			//temp.callRESTConservatively(sXid, null, "Store Picking Required", "This should not appear", payload);
			//temp.callRichRest(sXid, null, "Get your Riding Kit Today!", "RICH", "<img src=\"http://23.246.224.110/interactdemoofferimages/RidingKitInbox.png\" />", "View Offer", payload);
			temp.callUrlRest(null, temp.getTag(), "Test Push", "This is to test the Xtify Service.", "URL", "http://google.com", "Update Order", payload);
		} catch(JSONException e){
			e.printStackTrace();
		}

		//temp.sendCustomerPickupMessage(null, dOrder.getDocument());	
		
		
	}
	
	public TriggerXtify(){
		xProperties = new Properties();
		properties = new Properties();
		try {
			xProperties.load(TriggerXtify.class.getResourceAsStream("xtify.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private String getApp(){
		if (!YFCCommon.isVoid(getProperty("XTIFY_APP_KEY"))){
			return (String)getProperty("XTIFY_APP_KEY");
		} else if (xProperties.containsKey("XTIFY_APP_KEY")){
			return xProperties.getProperty("XTIFY_APP_KEY");
		}
		return "";
	}
	
	private String getUrl(){
		if (!YFCCommon.isVoid(getProperty("XTIFY_REST_URL"))){
			return (String)getProperty("XTIFY_REST_URL");
		} else if (xProperties.containsKey("XTIFY_REST_URL")){
			return xProperties.getProperty("XTIFY_REST_URL");
		}
		return "";
	}
	
	private String getApi(){
		if (!YFCCommon.isVoid(getProperty("XTIFY_API_KEY"))){
			return (String)getProperty("XTIFY_API_KEY");
		} else if (xProperties.containsKey("XTIFY_API_KEY")){
			return xProperties.getProperty("XTIFY_API_KEY");
		}
		return "";
	}
	
	private String getDefaultXID(){
		if (!YFCCommon.isVoid(getProperty("DefaultXID"))){
			return (String)getProperty("DefaultXID");
		} else if (xProperties.containsKey("DefaultXID")){
			return xProperties.getProperty("DefaultXID");
		}
		return "53d05baa1fde007c80cd115d";
	}

	private String getServer(){
		if (!YFCCommon.isVoid(getProperty("server"))){
			return (String)getProperty("server");
		} else if (xProperties.containsKey("server")){
			return xProperties.getProperty("server");
		}
		return "http://oms.omfulfillment.com:9080";
	}

	public String getXID(YFCElement eOrder) throws YIFClientCreationException{
		
		if (!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode")) && !YFCCommon.isVoid(eOrder.getAttribute("BillToID"))){
			YFCDocument input = YFCDocument.createDocument("Customer");
			YFCElement eCustomer = input.getDocumentElement();
			eCustomer.setAttribute("CustomerID", eOrder.getAttribute("BillToID"));
			eCustomer.setAttribute("CallingOrganizationCode", eOrder.getAttribute("EnterpriseCode"));
			try {
				YFCDocument dOutput = CallInteropServlet.invokeApi(input, getCustomerListTemplate(), "getCustomerList", getServer());
				YFCElement eOutput = dOutput.getDocumentElement();
				for (YFCElement eCust : eOutput.getChildren()){
					if (!YFCCommon.isVoid(eCust.getAttribute("ExternalCustomerID"))){
						return eCust.getAttribute("ExternalCustomerID");
					}
				}
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
		}
		return getDefaultXID();
	}
	
	public String getTag(){
		if (!YFCCommon.isVoid(getProperty("Tag"))){
			return (String)getProperty("Tag");
		} else if (xProperties.containsKey("Tag")){
			return xProperties.getProperty("Tag");
		}
		return null;	
	}
	
	public YFCDocument getOrderForShipment(String sShipmentKey) throws YIFClientCreationException{
		YFCDocument dOutput = null;
		YFCDocument input = YFCDocument.createDocument("Order");
		YFCElement eOrder = input.getDocumentElement();
		YFCElement eShipment = eOrder.createChild("Shipment");
		if (!YFCCommon.isVoid(sShipmentKey)){
			eShipment.setAttribute("ShipmentKey", sShipmentKey);
			try {
				dOutput = CallInteropServlet.invokeApi(input, getOrderListTemplate(), "getOrderList", getServer());
				if (!YFCCommon.isVoid(dOutput)){
					YFCDocument dOrder = YFCDocument.createDocument("Order");
					if (!YFCCommon.isVoid(dOutput)){
						YFCElement eOrderList = dOutput.getDocumentElement();
						if (!YFCCommon.isVoid(eOrderList.getFirstChildElement())){
							YFCElement eO = dOrder.getDocumentElement();
							eO.setAttributes(eOrderList.getFirstChildElement().getAttributes());
							return dOrder;
						}
					}
				}
			} catch(Exception yex) {
	        	System.out.println("The error thrown was: " );    
	        	System.out.println(yex.toString());
	            yex.printStackTrace();
	        } 
		}
		
		return null;
	}
	
	public static YFCDocument getOrderListTemplate(){
		YFCDocument output = YFCDocument.createDocument("OrderList");
		YFCElement eOrderList = output.getDocumentElement();
		YFCElement eOrder = eOrderList.createChild("Order");
		eOrder.setAttribute("OrderHeaderKey","");
		eOrder.setAttribute("OrderNo","");
		eOrder.setAttribute("EnterpriseCode","");
		eOrder.setAttribute("DocumentType","");
		eOrder.setAttribute("PaymentStatus", "");
		eOrder.setAttribute("MinOrderStatus","");
		eOrder.setAttribute("MaxOrderStatus", "");
		eOrder.setAttribute("BillToID","");
		eOrder.setAttribute("BuyerUserID","");
		eOrder.setAttribute("CustomerContactID", "");
		eOrder.setAttribute("CustomerEMailID", "");
		eOrder.setAttribute("CustomerFirstName", "");
		eOrder.setAttribute("CustomerLastName", "");
		return output;
	}
	
	public static YFCDocument getOrderDetailsTemplate(){
		YFCDocument output = YFCDocument.createDocument("Order");
		YFCElement eOrder = output.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("OrderNo","");
		eOrder.setAttribute("EnterpriseCode","");
		eOrder.setAttribute("DocumentType","");
		return output;
	}
	public static YFCDocument getCustomerListTemplate(){
		YFCDocument output = YFCDocument.createDocument("CustomerList");
		YFCElement eCustomerList = output.getDocumentElement();
		YFCElement eCustomer = eCustomerList.createChild("Customer");
		eCustomer.setAttribute("CustomerID","");
		eCustomer.setAttribute("OrganizationCode","");
		eCustomer.setAttribute("ExternalCustomerID","");
		YFCElement eContact = eCustomer.getChildElement("CustomerContactList", true).getChildElement("CustomerContact", true);
		eContact.setAttribute("CustomerContactID", "");
		eContact.setAttribute("UserID", "");
		return output;
	}
	
	public Document sendCustomerPickupMessage(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			YFCElement eShipment = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			String sXID;
			try {
				YFCDocument dOrder = this.getOrderForShipment(eShipment.getAttribute("ShipmentKey"));
				if (!YFCCommon.isVoid(dOrder)){
					YFCElement eOrder = dOrder.getDocumentElement();
					sXID = getXID(eOrder);
					JSONObject payload = new JSONObject();
					if(!YFCCommon.isVoid(eOrder.getAttribute("OrderHeaderKey"))){
						payload.put("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
					}
					if(!YFCCommon.isVoid(eOrder.getAttribute("DocumentType"))){
						payload.put("DocumentType", eOrder.getAttribute("DocumentType"));
					}
					if(!YFCCommon.isVoid(eOrder.getAttribute("OrderNo"))){
						payload.put("OrderNo", eOrder.getAttribute("OrderNo"));
					}
					if(!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode"))){
						payload.put("EnterpriseCode", eOrder.getAttribute("EnterpriseCode"));
					}
					callREST(sXID, null, "Ready for Pickup", "Order " + eOrder.getAttribute("OrderNo") + " is ready to be picked up in the store.", payload);
					return inputDoc;
				}
				
			} catch (YIFClientCreationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Document sendShipmentConfirmationMessage(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			YFCElement eShipment = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			String sXID;
			try {
				YFCDocument dOrder = this.getOrderForShipment(eShipment.getAttribute("ShipmentKey"));
				if (!YFCCommon.isVoid(dOrder)){
					YFCElement eOrder = dOrder.getDocumentElement();
					sXID = getXID(eOrder);
					JSONObject payload = new JSONObject();
					if(!YFCCommon.isVoid(eOrder.getAttribute("OrderHeaderKey"))){
						payload.put("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
					}
					if(!YFCCommon.isVoid(eOrder.getAttribute("DocumentType"))){
						payload.put("DocumentType", eOrder.getAttribute("DocumentType"));
					}
					if(!YFCCommon.isVoid(eOrder.getAttribute("OrderNo"))){
						payload.put("OrderNo", eOrder.getAttribute("OrderNo"));
					}
					if(!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode"))){
						payload.put("EnterpriseCode", eOrder.getAttribute("EnterpriseCode"));
					}
					callREST(sXID, null, "Shipment Confirmation", "Order " + eOrder.getAttribute("OrderNo") + " has successfully shipped.", payload);
					return inputDoc;
				}
			} catch (YIFClientCreationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}	
	
	public Document sendConservativeXtifyPush(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			String sXID = null;
			String sHasTag = null;
			String sNoTag = null;
			try {
				YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
				if (!YFCCommon.isVoid(eInput)){
					if(!YFCCommon.isVoid(eInput.getAttribute("XID"))){
						sXID = eInput.getAttribute("XID");
					}
					if (!YFCCommon.isVoid(eInput.getAttribute("HasTag"))){
						sHasTag = eInput.getAttribute("HasTag");
					}
					String sMessage = "There are items in the store that need to be picked for fulfillment.";
					if (!YFCCommon.isVoid(eInput.getAttribute("Message"))){
						sMessage = eInput.getAttribute("Message");
					} 
					String sSubject = "Order Processed";
					if (!YFCCommon.isVoid(eInput.getAttribute("Subject"))){
						sSubject = eInput.getAttribute("Subject");
					}
					this.callRESTConservatively(sXID, sHasTag, sSubject, sMessage, null);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}	
		return inputDoc;
	}
	
	public Document sendQuoteApprovalPush(YFSEnvironment env, Document inputDoc){
		if(!YFCCommon.isVoid(inputDoc)){
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			
			if(!YFCCommon.isVoid(eInput.getAttribute("OrderHeaderKey"))){
				YFCDocument dApiInput = YFCDocument.createDocument("Order");
				YFCElement eApiInput = dApiInput.getDocumentElement();
				eApiInput.setAttribute("OrderHeaderKey", eInput.getAttribute("OrderHeaderKey"));
				YFCDocument dOutput = CallInteropServlet.invokeApi(dApiInput, getOrderDetailsTemplate(), "getOrderDetails", getServer());
				YFCElement eOrder = dOutput.getDocumentElement();
				String sXID = null;
				String sHasTag = null;
				String sNoTag = null;
				try {
					if (!YFCCommon.isVoid(eInput)){
						if(!YFCCommon.isVoid(eInput.getAttribute("XID"))){
							sXID = eInput.getAttribute("XID");
						}
						if (!YFCCommon.isVoid(eInput.getAttribute("HasTag"))){
							sHasTag = eInput.getAttribute("HasTag");
						}
						String sMessage = "There is a quote that requires your review.";
						if (!YFCCommon.isVoid(eInput.getAttribute("Message"))){
							sMessage = eInput.getAttribute("Message");
						} 
						String sSubject = "Quote Approval Required";
						if (!YFCCommon.isVoid(eInput.getAttribute("Subject"))){
							sSubject = eInput.getAttribute("Subject");
						}
						JSONObject payload = new JSONObject();
						if(!YFCCommon.isVoid(eOrder.getAttribute("OrderHeaderKey"))){
							payload.put("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
						}
						if(!YFCCommon.isVoid(eOrder.getAttribute("DocumentType"))){
							payload.put("DocumentType", eOrder.getAttribute("DocumentType"));
						}
						if(!YFCCommon.isVoid(eOrder.getAttribute("OrderNo"))){
							payload.put("OrderNo", eOrder.getAttribute("OrderNo"));
						}
						if(!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode"))){
							payload.put("EnterpriseCode", eOrder.getAttribute("EnterpriseCode"));
						}
						this.callRESTConservatively(sXID, sHasTag, sSubject, sMessage, payload);
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return inputDoc;
	}
	
	public Document sendXtifyPush(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			String sXID = null;
			String sHasTag = null;
			String sNoTag = null;
			try {
				YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
				if (!YFCCommon.isVoid(eInput)){
					if(!YFCCommon.isVoid(eInput.getAttribute("XID"))){
						sXID = eInput.getAttribute("XID");
					}
					if (!YFCCommon.isVoid(eInput.getAttribute("HasTag"))){
						sHasTag = eInput.getAttribute("HasTag");
					}
					String sMessage = "One of the items on your order is currently unavailable.";
					if (!YFCCommon.isVoid(eInput.getAttribute("Message"))){
						sMessage = eInput.getAttribute("Message");
					} else if (!YFCCommon.isVoid(eInput.getAttribute("OrderNo"))){
						sMessage = "Order " + eInput.getAttribute("OrderNo") + " from Greenwheels is in process.";
					}
					
					String sSubject = "We're very sorry!";
					if (!YFCCommon.isVoid(eInput.getAttribute("Subject"))){
						sSubject = eInput.getAttribute("Subject");
					}
					String sURL = "http://oms.service.isdemocloud.info:51234/starbucks/#changeOrder&OrderHeaderKey=&OrderLineKey=";
					if(!YFCCommon.isVoid(eInput.getAttribute("URL"))){
						sURL = eInput.getAttribute("URL");
					}
					String sActionButton = "Update Order";
					if(!YFCCommon.isVoid(eInput.getAttribute("ActionButton"))){
						sActionButton = eInput.getAttribute("ActionButton");
					}
					String sAction = "URL";
					if(!YFCCommon.isVoid(eInput.getAttribute("Action"))){
						sAction = eInput.getAttribute("Action");
					}
					if(!YFCCommon.isVoid(eInput.getAttribute("URL"))){
						callUrlRest(sXID, sHasTag, sSubject, sMessage, sAction, sURL, sActionButton, null);
					}
					
					return inputDoc;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Document sendOrderConfirmation(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			String sXID = null;
			String sHasTag = null;
			String sNoTag = null;
			try {
				YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
				if (!YFCCommon.isVoid(eInput)){
					if(!YFCCommon.isVoid(eInput.getAttribute("XID"))){
						sXID = eInput.getAttribute("XID");
					}
					if (!YFCCommon.isVoid(eInput.getAttribute("HasTag"))){
						sHasTag = eInput.getAttribute("HasTag");
					}
					String sMessage = "<img src=\"http://23.246.224.110/interactdemoofferimages/RidingKitInbox.png\" />";
					if (!YFCCommon.isVoid(eInput.getAttribute("Message"))){
						sMessage = eInput.getAttribute("Message");
					} else if (!YFCCommon.isVoid(eInput.getAttribute("OrderNo"))){
						sMessage = "Order " + eInput.getAttribute("OrderNo") + " from Greenwheels is in process.";
					}
					String sSubject = "Get your Riding Kit Today!";
					if (!YFCCommon.isVoid(eInput.getAttribute("Subject"))){
						sSubject = eInput.getAttribute("Subject");
					}
					String sActionButton = "View Offer";
					if(!YFCCommon.isVoid(eInput.getAttribute("ActionButton"))){
						sActionButton = eInput.getAttribute("ActionButton");
					}
					String sAction = "RICH";
					if(!YFCCommon.isVoid(eInput.getAttribute("Action"))){
						sAction = eInput.getAttribute("Action");
					}
					callRichRest(sXID, sHasTag, sSubject, sAction, sMessage, sActionButton, null);
					
					return inputDoc;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;	
	}
	
	public Document sendRichNotification(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			String sXID;
			try {
				YFCDocument dOrder = YFCDocument.getDocumentFor(inputDoc);
				if (!YFCCommon.isVoid(dOrder)){
					YFCElement eOrder = dOrder.getDocumentElement();
					sXID = getXID(eOrder);
					callRichRest(sXID, getTag(), "Get your Riding Kit Today!", "RICH", "<img src=\"http://23.246.224.110/interactdemoofferimages/RidingKitInbox.png\" />", "View Offer", null);
					return inputDoc;
				}
			} catch (YIFClientCreationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;	
		
	}
	
	public void callRichRest(String XID, String hasTag, String sSubject, String sAction, String sActionData, String sLabel, JSONObject payload) throws JSONException{
		JSONObject json = new JSONObject();
		JSONObject content = new JSONObject();
		JSONObject action = new JSONObject();
		JSONObject rich = new JSONObject();
		try {
			json.put("apiKey", getApi());
			json.put("appKey", getApp());	
			if (!YFCCommon.isVoid(XID)){
				JSONArray xids = new JSONArray();
				xids.put(XID);
				json.put("xids", xids);
			}
			if(!YFCCommon.isVoid(hasTag)){
				JSONArray hasTags = new JSONArray();
				hasTags.put(hasTag);
				json.put("hasTags", hasTags);
			}
			
			json.put("sendAll", "true");
			json.put("inboxOnly", "true");
			content.put("message", sSubject);
			content.put("sound", "ding.caf");
			content.put("badge", "+1");
			action.put("type", sAction);
			//action.put("data", "");
			action.put("label", sSubject);
			content.put("action", action);
			rich.put("subject", sSubject);
			rich.put("message", sActionData);
			JSONObject richAction = new JSONObject();
			richAction.put("data", sActionData);
			richAction.put("type", "WEB");
			richAction.put("label", sLabel);
			rich.put("action", richAction);
			content.put("rich", rich);
			if(!YFCCommon.isVoid(payload)){
				content.put("payload", payload.toString());
			}
			json.put("content", content);
			System.out.println(json.toString());
			URL url = new URL(getUrl());
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("Content-Length",  String.valueOf(json.toString().length()));
	        OutputStream os = connection.getOutputStream();
	        os.write(json.toString().getBytes());
			//post.setEntity(new StringEntity(json.toString()));
			//post.setHeader("Content-Type", "application/json");
			//HttpResponse response = client.execute(post);
	        StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String res;
			logger.debug("-----return from JSON post------");
			while ((res = in.readLine()) != null) {
				sb.append(res);
				System.out.println(res);
				logger.debug(res);
			}
			logger.debug("--------end JSON post-----------");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callUrlRest(String XID, String hasTag, String sSubject, String sMessage, String sAction, String sActionData, String sLabel, JSONObject payload) throws JSONException{
		JSONObject json = new JSONObject();
		JSONObject content = new JSONObject();
		JSONObject action = new JSONObject();
		try {
			json.put("apiKey", getApi());
			json.put("appKey", getApp());	
			if (!YFCCommon.isVoid(XID)){
				JSONArray xids = new JSONArray();
				xids.put(XID);
				json.put("xids", xids);
			}
			if(!YFCCommon.isVoid(hasTag)){
				JSONArray hasTags = new JSONArray();
				hasTags.put(hasTag);
			//	json.put("hasTags", hasTags);
			}
			
			json.put("sendAll", "true");
			content.put("subject", sSubject);
			content.put("message", sMessage);
			content.put("sound", "ding.caf");
			action.put("type", sAction);
			action.put("data", sActionData);
			action.put("label", sLabel);
			content.put("action", action);
			if(!YFCCommon.isVoid(payload)){
				content.put("payload", payload.toString());
			}
			json.put("content", content);	
			URL url = new URL(getUrl());
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("Content-Length",  String.valueOf(json.toString().length()));
	        OutputStream os = connection.getOutputStream();
	        os.write(json.toString().getBytes());
	        StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String res;
			
			logger.debug("-----return from JSON post------");
			while ((res = in.readLine()) != null) {
				sb.append(res);
				System.out.println(res);
				logger.debug(res);
			}
			logger.debug("--------end JSON post-----------");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callRESTConservatively(String XID, String hasTag, String sSubject, String sMessage, JSONObject payload) throws JSONException {
		if((TriggerXtify.lastPushAttempt + 180000l) < (System.currentTimeMillis())){
			callREST(XID, hasTag, sSubject, sMessage, payload);
			TriggerXtify.lastPushAttempt = System.currentTimeMillis();
		}
	}
	public void callREST(String XID, String hasTag, String sSubject, String sMessage, JSONObject payload) throws JSONException {
		
	//HttpClient client = HttpClientBuilder.create().build();
	//	HttpPost post = new HttpPost(getUrl());
		JSONObject json = new JSONObject();
		JSONObject content = new JSONObject();
		JSONObject action = new JSONObject();
		JSONArray xids = new JSONArray();
		try {
			json.put("apiKey", getApi());
			json.put("appKey", getApp());	
			if(!YFCCommon.isVoid(XID)){
				xids.put(XID);
				json.put("xids", xids);
			}
			if(!YFCCommon.isVoid(hasTag)){
				JSONArray hasTags = new JSONArray();
				hasTags.put(hasTag);
				json.put("hasTags", hasTags);
			}
			json.put("sendAll", "true");
			content.put("subject", sSubject);
			content.put("message", sMessage);
			content.put("sound", "ding.caf");
			action.put("type", "DEFAULT");
			content.put("action", action);
			if(!YFCCommon.isVoid(payload)){
				content.put("payload", payload.toString());
			}
			json.put("content", content);	
			URL url = new URL(getUrl());
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("Content-Length",  String.valueOf(json.toString().length()));
	        OutputStream os = connection.getOutputStream();
	        os.write(json.toString().getBytes());
			//post.setEntity(new StringEntity(json.toString()));
			//post.setHeader("Content-Type", "application/json");
			//HttpResponse response = client.execute(post);
	        StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String res;
			logger.debug("-----return from JSON post------");
			while ((res = in.readLine()) != null) {
				sb.append(res);
				System.out.println(res);
				logger.debug(res);
			}
			logger.debug("--------end JSON post-----------");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
