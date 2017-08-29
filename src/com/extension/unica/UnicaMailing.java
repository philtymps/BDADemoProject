package com.extension.unica;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.xtify.TriggerXtify;
import com.ibm.CallInteropServlet;
import com.unica.emessage.webservices.tms.soap.SendMailingResponse;
import com.unica.emessage.webservices.tms.soap.TMSSOAP11BindingStub;
import com.unica.emessage.webservices.tms.xsd.AdvisoryMessage;
import com.unica.emessage.webservices.tms.soap.SendMailing;
import com.unica.emessage.webservices.tms.xsd.NameValuePair;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;


public class UnicaMailing {
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(UnicaMailing.class);
	
	public UnicaMailing(){
		properties = new Properties();
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException{
		UnicaMailing mail = new UnicaMailing();
		//mail.callUnicaEmail(mail.getPersonalizedFields(getOrder()), "ThankYou");
		//mail.callUnicaEmail(mail.getPersonalizedFields(getOrder()), "Ready");
		mail.callUnicaEmail(mail.getPersonalizedFields(getOrder()), "Discount");
	}
	
	private String getServer(){
		if (!YFCCommon.isVoid(getProperty("server"))){
			return (String)getProperty("server");
		}
		return "http://oms.omfulfillment.com:9080";
	}
	
	private static YFCElement getOrder(){
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("CustomerEMailID", "pfaiola@us.ibm.com");
		eOrder.setAttribute("CustomerFirstName", "Pat");
		eOrder.setAttribute("CustomerLastName", "Faiola");
		eOrder.setAttribute("OrderNo", "OM12345");
		eOrder.setAttribute("EnterpriseCode", "greenwheels");
		return eOrder;
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private String getTMSUserName(){
		if (!YFCCommon.isVoid(getProperty("UserName"))){
			return (String) getProperty("UserName");
		}
		return "ibmus02";
	}
	
	private String getTMSPassword(){
		if (!YFCCommon.isVoid(getProperty("Password"))){
			return (String) getProperty("Password");
		}
		return "oHem8Uy3u2t5Q7Cic32M";
	}
	
	private String getMailingCode(){
		if (!YFCCommon.isVoid(getProperty("MailingCode"))){
			return (String) getProperty("MailingCode");
		}
		return "TxTest";
	}
	
	private double getIndivdId(){
		if (!YFCCommon.isVoid(getProperty("IndivdId"))){
			return Double.valueOf((String)getProperty("IndivdId"));
		}
		return 2021D;
	}
	
	private URL getTMSEndPoint() throws MalformedURLException{
		if (!YFCCommon.isVoid(getProperty("URL"))){
			return new URL((String) getProperty("URL"));
		}
		return new URL("http://tms-em.unicaondemand.com/emessageds/services/TMS");
	}
	
	private NameValuePair[] getPersonalizedFields(YFCElement eOrder){
		ArrayList<NameValuePair> temp = new ArrayList<NameValuePair>();
		if (!YFCCommon.isVoid(eOrder.getAttribute("CustomerEMailID"))){
			temp.add(createStringPair("Email", eOrder.getAttribute("CustomerEMailID")));
			temp.add(createStringPair("emailaddress", eOrder.getAttribute("CustomerEMailID")));
			temp.add(createStringPair("emailAddress", eOrder.getAttribute("CustomerEMailID")));
		}
		if (!YFCCommon.isVoid(eOrder.getAttribute("CustomerFirstName"))){
			temp.add(createStringPair("FirstName", eOrder.getAttribute("CustomerFirstName")));
			temp.add(createStringPair("first_name", eOrder.getAttribute("CustomerFirstName")));
		}
		if (!YFCCommon.isVoid(eOrder.getAttribute("CustomerLastName"))){
			temp.add(createStringPair("LastName", eOrder.getAttribute("CustomerLastName")));
		}
		if (!YFCCommon.isVoid(eOrder.getAttribute("OrderNo"))){
			temp.add(createStringPair("OrderNo", eOrder.getAttribute("OrderNo")));
		}
		if (!YFCCommon.isVoid(eOrder.getAttribute("EnterpriseCode"))){
			temp.add(createStringPair("EnterpriseCode", eOrder.getAttribute("EnterpriseCode")));
		}
		NameValuePair[] response = new NameValuePair[temp.size()];
		for (int i = 0; i < temp.size(); i++){
			response[i] = temp.get(i);
		}
		return response;
	}
	
	private NameValuePair createStringPair(String sName, String sValue){
		NameValuePair pair = new NameValuePair();
		pair.setName(sName);
		pair.setValueDataType("string");
		pair.setValueAsString(sValue);
		return pair;
	}
	
	private boolean callUnicaEmail(NameValuePair[] personalizedFields, String sMailingCode) throws RemoteException, MalformedURLException{
		NameValuePair[] audienceId = new NameValuePair[1];
        
        NameValuePair nvp = new NameValuePair();
        nvp.setName("Indiv_Id");
        nvp.setValueDataType("numeric");
        nvp.setValueAsNumeric(getIndivdId());
        
        audienceId[0] = nvp;
        
        // personalized fields: each personalized field is a name value pair, so again we use the
        // custom type "NameValuePair".  For this example, we want to send two personalized fields (emailAddress, gender)

        // Cell code
        String[] cellCodes = { "CC243935" };
        
        // Additional Options - this is a name value pair again - but for now send as null
        NameValuePair[] additionalOptions = null;
        
        // locale - rely on default by setting as null;
        String locale = null;
        
        /**
         * Calling the Method:
         * 1) set up a connection object with the URL of the TMS webservice
         * 2) Construct the required security header with the authentication credentials
         * 3) Construct the method and Set the parameters
         * 4) Make the call
         * 5) Process the response
         */
        
        // connection object
        TMSSOAP11BindingStub stub = new TMSSOAP11BindingStub(getTMSEndPoint(), null);
        
        // authentication: the TMS web service requires the client to submit user and pw info via soap headers.
        // the headers should look like the following:
        // <ns2:userName xmlns:ns2="http://soap.tms.webservices.emessage.unica.com">MyTMSUserName</ns2:userName>
        // <ns2:password xmlns:ns2="http://soap.tms.webservices.emessage.unica.com">MyTMSPassword</ns2:password>
        // The following shows how to add such headers using the Axis2 client.


        // construct the method and set the parameters that were defined above
        SendMailing req = new SendMailing();

        req.setMailingCode(sMailingCode);
        req.setAudienceId(audienceId);
        req.setFields(personalizedFields);
        req.setCellCodes(cellCodes);
        req.setAdditionalOptions(additionalOptions);
        req.setLocale(locale);

        // make the call
        SendMailingResponse response = stub.sendMailing(req, getTMSUserName(), getTMSPassword());

        // process the response - a customType Response is returned
        // all responses come back with a top level code that indicates whether or not the request was
        // successful (0) or a warning (1) or error (2) occurred.  If the request was not successful, the client code
        // should log/alert the issue, and possibly retry the request depending on the issue
        if(response.get_return().getStatusCode()==0)
        {
            System.out.println("Request to TMS successful");
            return true;
        }
        else // an error or warning occurred - print out the message contained in the customType AdvisoryMessage - there may be more than one
        {
            AdvisoryMessage[] messages = response.get_return().getAdvisoryMessages();
            for(AdvisoryMessage message: messages)
            {
                System.out.println("message:"+message.getMessage());
                System.out.println("message detail:"+message.getDetailMessage());
            }
            
        }
        return false;
	}
	
	public YFCDocument getOrderForShipment(String sShipmentKey) throws YIFClientCreationException{
		YFCDocument dOutput = null;
		YFCDocument input = YFCDocument.createDocument("Order");
		YFCElement eOrder = input.getDocumentElement();
		YFCElement eShipment = eOrder.createChild("Shipment");
		if (!YFCCommon.isVoid(sShipmentKey)){
			eShipment.setAttribute("ShipmentKey", sShipmentKey);
			try {
				dOutput = CallInteropServlet.invokeApi(input, TriggerXtify.getOrderListTemplate(), "getOrderList", getServer());
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
	
	public Document sendCustomerReadyEmail(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();		
			try {
				if (!YFCCommon.isVoid(eInput.getAttribute("ShipmentKey"))){
					YFCDocument dOrder = getOrderForShipment(eInput.getAttribute("ShipmentKey"));
					if (!YFCCommon.isVoid(dOrder)){
						YFCElement eOrder = dOrder.getDocumentElement();
						if (!YFCCommon.isVoid(eOrder.getAttribute("CustomerEMailID"))){
							callUnicaEmail(getPersonalizedFields(eOrder), "Ready");				
						}
					}					
				}
				
			} catch(RemoteException e){
				e.printStackTrace();
			} catch (YIFClientCreationException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
	
		return inputDoc;
	}
	
	public Document sendShipmentCompleteEmail(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();		
			try {
				if (!YFCCommon.isVoid(eInput.getAttribute("ShipmentKey"))){
					YFCDocument dOrder = getOrderForShipment(eInput.getAttribute("ShipmentKey"));
					if (!YFCCommon.isVoid(dOrder)){
						YFCElement eOrder = dOrder.getDocumentElement();
						if (!YFCCommon.isVoid(eOrder.getAttribute("CustomerEMailID"))){
							callUnicaEmail(getPersonalizedFields(eOrder), "Discount");				
						}
					}
				}
				
			} catch(RemoteException e){
				e.printStackTrace();
			} catch (YIFClientCreationException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return inputDoc;	
	}
}
