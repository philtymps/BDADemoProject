package com.extension.unica;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.json.JSONException;
import org.w3c.dom.Document;

import com.unicacorp.interact.api.BatchResponse;
import com.unicacorp.interact.api.Command;
import com.unicacorp.interact.api.CommandImpl;
import com.unicacorp.interact.api.NameValuePair;
import com.unicacorp.interact.api.NameValuePairImpl;
import com.unicacorp.interact.api.Offer;
import com.unicacorp.interact.api.OfferList;
import com.unicacorp.interact.api.Response;
import com.unicacorp.interact.api.rest.RestClientConnector;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class UnicaPromotion {
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(UnicaPromotion.class);
	
	public static void main(String[] args) throws IOException, JSONException {  
		
		UnicaPromotion up = new UnicaPromotion();
		System.out.println(YFCDocument.getDocumentFor(up.testUnicaPromotion()));
		
	}
	
	public UnicaPromotion(){
		properties = new Properties();
	}
	
	private static Command createStartSessionCommand(String icName, double customerId) throws JSONException {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_STARTSESSION);
		cmd.setInteractiveChannel(icName);
		cmd.setAudienceLevel("Individual");
		cmd.setAudienceID(new NameValuePairImpl[] {new NameValuePairImpl("Indiv_id", NameValuePair.DATA_TYPE_NUMERIC, customerId)});
		//cmd.setEventParameters(new NameValuePairImpl[] {new NameValuePairImpl("score", NameValuePair.DATA_TYPE_NUMERIC, Double.valueOf("5"))});
		cmd.setDebug(true);
		cmd.setRelyOnExistingSession(false);
		return cmd;
	}
	
	private static Command createGetOffersCommand(String ipName, int numberRequested) throws JSONException {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_GETOFFERS);
		cmd.setInteractionPoint(ipName);
		cmd.setNumberRequested(numberRequested);
		return cmd;
	}

	private static Command createGetProfileCommand() throws JSONException {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_GETPROFILE);
		return cmd;
	}
	
	private static Command createEndSessionCommand() throws JSONException {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_ENDSESSION);
		return cmd;
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private String getUnicaURL(){
		if (!YFCCommon.isVoid(getProperty("UnicaURL"))){
			return (String) getProperty("UnicaURL");
		}
		return "http://unica.softlayer.com:7001/interact";
		//return "http://192.155.237.139:7001/interact";
		//return "http://173.192.233.155:7001/interact";
	}
	
	private int getNumberOfRequests(){
		if (!YFCCommon.isVoid(getProperty("NumberOfOffers"))){
			return Integer.valueOf((String)getProperty("NumberOfOffers"));
		}
		return 2;
	}
	
	private String geticName(){
		if (!YFCCommon.isVoid(getProperty("icName"))){
			return (String)getProperty("icName");
		}
		return "emmscdemo";
		//return "emmscb2b";
	}
	
	private String getipName(){
		if (!YFCCommon.isVoid(getProperty("ipName"))){
			return (String)getProperty("ipName");
		}
		return "Store";
		//return "WebStore";
	}
	
	private double getCustomerID(String sBillToID){
		if (YFCCommon.isVoid(sBillToID)){
			return 0;
		}
		try {
			YFCDocument customerMap = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/Unica/customerMap.xml");
			if (!YFCCommon.isVoid(customerMap)){
				for (YFCElement eCustomer : customerMap.getDocumentElement().getChildren()){
					if (YFCCommon.equals(eCustomer.getAttribute("CustomerID"), sBillToID)){
						return eCustomer.getDoubleAttribute("IndivID");
					}
				}
			}
		} catch (Exception e){
			
		}
		return 1;
	}
	
	private Response[] callUnica(String sBillToID) throws JSONException, RemoteException{
		
		String url = getUnicaURL();
		url += "/servlet/RestServlet";
		String sessionId = String.valueOf(System.currentTimeMillis());
		String icName = geticName();
		String ipName = getipName();
		int numberRequested = getNumberOfRequests();

		List<Command> cmds = new ArrayList<Command>();
		cmds.add(0, createStartSessionCommand(icName, getCustomerID(sBillToID)));
		cmds.add(1, createGetOffersCommand(ipName, numberRequested));
		cmds.add(2, createGetProfileCommand());
		cmds.add(3, createEndSessionCommand());
		
		RestClientConnector.initialize();
		RestClientConnector connector = new RestClientConnector(url);
		BatchResponse response = connector.executeBatch(sessionId, cmds.toArray(new Command[0]), null, null);
		Response[] output = response.getResponses();
		return output;
	}
	
	private void splitImageAttribute(YFCElement eOffer, String sAttributeName, String sAttributeValue){
		if (!YFCCommon.isVoid(sAttributeValue)){
			if (sAttributeValue.indexOf('/') > -1){
				eOffer.setAttribute(sAttributeName + "_Location", sAttributeValue.substring(0,sAttributeValue.lastIndexOf('/')));	
				eOffer.setAttribute(sAttributeName + "_Image", sAttributeValue.substring(sAttributeValue.lastIndexOf('/') + 1));
			}
		}
	}
	
	private Document convertResponseIntoXml(Response offerResponse, Response profileResponse){
		YFCDocument dUnicaOffer = YFCDocument.createDocument("UnicaOffer");
		YFCElement eUnicaOffer = dUnicaOffer.getDocumentElement();
		
		YFCElement eOfferList = eUnicaOffer.createChild("OfferList");
		OfferList offList = offerResponse.getOfferList();
		if (!YFCCommon.isVoid(offList)){
			eOfferList.setAttribute("InteractionPointName", offList.getInteractionPointName());
			eOfferList.setAttribute("DefaultString", offList.getDefaultString());
			eOfferList.setAttribute("NumberOfRecords", offList.getRecommendedOffers().length);
			for (Offer off : offList.getRecommendedOffers()){
				YFCElement eOffer = eOfferList.createChild("Offer");
				eOffer.setAttribute("OfferName", off.getOfferName());
				eOffer.setAttribute("Description", off.getDescription());
				eOffer.setAttribute("TreatmentCode", off.getTreatmentCode());
				eOffer.setAttribute("Score", off.getScore());
				for (NameValuePair add : off.getAdditionalAttributes()){
					eOffer.setAttribute(add.getName(), add.getValueAsString());
					if (add.getName().contains("Image")){
						splitImageAttribute(eOffer, add.getName(), add.getValueAsString());
					}
				}
			}
			
			YFCElement eProfile = eUnicaOffer.createChild("Profile");
			for (NameValuePair attr : profileResponse.getProfileRecord()){
				eProfile.setAttribute(attr.getName(), attr.getValueAsString());
			}
		}		
		return dUnicaOffer.getDocument();
	}
	
	public Document testUnicaPromotion(){
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dInput.getDocumentElement();
		eOrder.setAttribute("DocumentType", "0001");
		return getUnicaPromotion(null,dInput.getDocument());
	}
	
	private static YFCDocument getShipmentTemplate(){
		YFCDocument dShipment = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dShipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", "");
		YFCElement eShipmentLine = eShipment.createChild("ShipmentLines").createChild("ShipmentLine");
		eShipmentLine.setAttribute("ItemID","");
		eShipmentLine.setAttribute("OrderHeaderKey","");
		YFCElement eOrder = eShipmentLine.createChild("Order");
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("BillToID", "");
		eOrder.setAttribute("BuyerUserID", "");
		eOrder.setAttribute("CustomerContactID", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("OrderHeaderKey", "");
		return dShipment;
		
	}
	
	public static boolean validateUrl(String sURL){
		boolean res = false;
		if (!YFCCommon.isVoid(sURL)){
            if( !sURL.startsWith( "http" ) )
            {
            	sURL = "http://" + sURL;
            }
            HttpURLConnection conn = null;
            try {
            	conn = (HttpURLConnection) new URL(sURL).openConnection();
            	conn.setRequestMethod("GET");
            	conn.setConnectTimeout(4000);
            	conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)");
            	int code = conn.getResponseCode();
            	  char[] codeArray = new Integer( code ).toString().toCharArray();
                  if( codeArray[0] == '2' || codeArray[0] == '3' || codeArray[0] == '4' ){
                      res = true;
                  }
            } catch (Exception e){
            	e.printStackTrace();
            } finally {
            	conn.disconnect();
            	conn = null;
            }
		}
		return res;
	}
	public Document getUnicaPromotion(YFSEnvironment env, Document inputDoc){
		YFCDocument output = YFCDocument.createDocument("UnicaOffer");
		String url = getUnicaURL();
		url += "/servlet/RestServlet";
		if (!YFCCommon.isVoid(inputDoc) && validateUrl(url)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			if (YFCCommon.equals(dInput.getDocumentElement().getNodeName(), "Shipment")){
				YFCElement eShipment = dInput.getDocumentElement();
				if (!YFCCommon.isVoid(eShipment.getAttribute("ShipmentKey"))){
					try {
						YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
						env.setApiTemplate("getShipmentDetails", getShipmentTemplate().getDocument());
						Document l_OutputXml = localApi.invoke(env, "getShipmentDetails", dInput.getDocument());
						
						YFCDocument dShipmentDetails = YFCDocument.getDocumentFor(l_OutputXml);
						if (!YFCCommon.isVoid(dShipmentDetails)){
							YFCElement eShipmentOutput = dShipmentDetails.getDocumentElement();
							YFCElement eShipmentLines = eShipmentOutput.getChildElement("ShipmentLines", true);
							for (YFCElement eShipmentLine : eShipmentLines.getChildren()){
								YFCElement eOrder = eShipmentLine.getChildElement("Order");
								try {
									Response[] temp  = callUnica(eOrder.getAttribute("BillToID"));
									return convertResponseIntoXml(temp[1],temp[2]);
								} catch(JSONException e){
									e.printStackTrace();
								} catch(RemoteException e){
									e.printStackTrace();
								}
							}
						}
					} catch(Exception yex) {
			        	System.out.println("The error thrown was: " );    
			        	System.out.println(yex.toString());
			            yex.printStackTrace();
			        } 
					
				}
			} else if (YFCCommon.equals(dInput.getDocumentElement().getNodeName(), "Order")){
				YFCElement eOrder = dInput.getDocumentElement();
				if (YFCCommon.equals(eOrder.getAttribute("DocumentType"),"0001")){
					try {
						Response[] temp  = callUnica(eOrder.getAttribute("BillToID"));
						return convertResponseIntoXml(temp[1],temp[2]);
					} catch(JSONException e){
						e.printStackTrace();
					} catch(RemoteException e){
						e.printStackTrace();
					}
				}
			}
		}
		return output.getDocument();
	}
}
