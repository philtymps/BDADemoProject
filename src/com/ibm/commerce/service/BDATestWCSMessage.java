package com.ibm.commerce.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.ws.ProtocolException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.yantra.interop.japi.YIFApi;
import com.yantra.scwc.utils.SCWCSoapUtils;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.log.YFCLogManager;
import com.yantra.yfc.log.YFCLogUtil;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCConfigurator;

public class BDATestWCSMessage {

	private static YFCLogCategory cat = YFCLogCategory.instance(BDATestWCSMessage.class);
	public static YIFApi api;
	private Properties _properties = null;

	public static final String   CONVERTED_STRING           = "convertedString";
	public static final String   CONTEXT_XML                = "contextXML";
	public static final String   WC_REQUEST_XML             = "wcRequestXML";
	
	private static final String _WCURLStringKey             = "wcurl";
	private static final String _WCUSERIDStringKey          = "wcuserid";
	private static final String _WCPASSWORDStringKey        = "wcpassword"; 

	private static final String _WCTrustAllCertsStringKey   = "wcTrustAllCerts"; 
	private static final String _WCHostNameVerifyStringKey  = "wcHostNameVerify"; 
	private static final String WCErrorReason 				= "Server Error";
	private static final String WCErrorReasonCode			= "No_Response_From_Server";
	private static final String WCErrorDescription		    = "No response received from WebSphere Commerce Server";


	public static void main(String[] args) throws Exception {
		BDATestWCSMessage t = new BDATestWCSMessage();
		YFCDocument dInput = YFCDocument.getDocumentFor("<_ord:GetOrder xmlns:_ord=\"http://www.ibm.com/xmlns/prod/commerce/9/order\" xmlns:_wcf=\"http://www.ibm.com/xmlns/prod/commerce/9/foundation\" xmlns:oa=\"http://www.openapplications.org/oagis/9\" xmlns:udt=\"http://www.openapplications.org/oagis/9/unqualifieddatatypes/1.1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" releaseID=\"\">" +
  "<oa:ApplicationArea xsi:type=\"_wcf:ApplicationAreaType\">" +
  "<oa:CreationDateTime xsi:type=\"udt:DateTimeType\">2016-06-20T09:38:48Z</oa:CreationDateTime>" +
    "<_wcf:BusinessContext>" +
      "<_wcf:ContextData name=\"storeId\">10201</_wcf:ContextData>" +
    "</_wcf:BusinessContext>" +
  "</oa:ApplicationArea>" +
  "<_ord:DataArea>" +
    "<oa:Get>" +
      "<oa:Expression expressionLanguage=\"_wcf:XPath\">" +
						"{_wcf.ap=IBM_Admin_SummaryList}/Order[StoreIdentifier[UniqueID='10201'] and BuyerIdentifier[ExternalIdentifier[(Email='jshmoe@example.com')]] and OrderStatus[(Status='P')]]" +
	  "</oa:Expression>" +
    "</oa:Get>" +
  "</_ord:DataArea>" +
"</_ord:GetOrder>");
		Document d = t.sendAndReceiveSOAPClient(dInput.getDocument());
		if(!YFCCommon.isVoid(d)){
			YFCDocument dOutput = YFCDocument.getDocumentFor(d);
			System.out.println(dOutput);
		}
	}
	public BDATestWCSMessage(){
		_properties = new Properties();
	}
	public Document sendAndReceiveSOAPClient(final Document inConvertedDocFromXsltNode) throws Exception
	{ 
		cat.beginTimer("SCWCIntegrationService.sendAndReceiveSOAPClient");

		cat.debug("\n\n...XML Message being sent:" + SCWCSoapUtils.printAndGetXMLString(inConvertedDocFromXsltNode, false));
		
		if (YFCLogManager.verboseEnabled) 
		{
			cat.verbose("Parameters" + YFCLogUtil.toString(inConvertedDocFromXsltNode));
		}
		
		if (cat.isDebugEnabled() && _properties != null) {
			Enumeration<?> e = _properties.propertyNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				cat.debug("Propery Name is:" + name + " AND  Value is:"	+ _properties.getProperty(name));
			}
		}
		
		String wcserverURL = _properties.getProperty(_WCURLStringKey, "http://wc8dev.ibm.com:8007/webapp/wcs/component/order/services/OrderServices");     
		String wcuserid =  _properties.getProperty(_WCUSERIDStringKey, "integrationAdmin");
		String wcpassword =  _properties.getProperty(_WCPASSWORDStringKey, "ExPl0re837sf3"); 


		if (YFCCommon.isStringVoid(wcserverURL)) {
			throw new Exception("sendAndReceiveSOAPClient::No URL is mentioned. URL can not be empty");
		}

		
		if (YFCCommon.isStringVoid(wcuserid) || YFCCommon.isStringVoid(wcpassword)) {
			throw new Exception("sendAndReceiveSOAPClient::User ID/Password can not be empty");
		}

		String sTrustAllCerts   = null;
		String sHostNameVerify  = null;
		boolean bTrustAllCerts  = false;
		boolean bHostNameVerify = false;

		java.net.URL url = new java.net.URL(wcserverURL);
		String protocol = url.getProtocol().toLowerCase();
		
		if (YFCCommon.equalsIgnoreCase("https", protocol)) {
			
			sTrustAllCerts   = _properties.getProperty(_WCTrustAllCertsStringKey);
			sHostNameVerify  = _properties.getProperty(_WCHostNameVerifyStringKey);

			if (YFCCommon.equalsIgnoreCase(sTrustAllCerts, "yes")) 
				bTrustAllCerts = true;
			else
				bTrustAllCerts = false;
			
			if (YFCCommon.equalsIgnoreCase(sHostNameVerify, "yes"))
				bHostNameVerify = true;
			else
				bHostNameVerify = false;
		}

		if (cat.isDebugEnabled()) {
			cat.debug("\n\n...USER ID=" + wcuserid);
			cat.debug("\n\n...USER_PWD=" + wcpassword);
			cat.debug("\n\n...Sending to WC Address:" + wcserverURL);
			cat.debug("\nHTTP Protocol:" + protocol);
			cat.debug("\nServer Port=" + url.getPort());
			cat.debug("\nServer Host=" + url.getHost());
		}

		Document resDoc = null; 

		try 
		{      	
			String convertedString = SCWCSoapUtils.convertXMLDocToString(inConvertedDocFromXsltNode, true);
			cat.debug("\n\n.......After xslt conversion, Request XML String is:\n\n" + convertedString);
			String wcRequestXML = convertedString ; 

			if (YFCCommon.equalsIgnoreCase("https", protocol)) 
			{
				resDoc = sendXMLStringInSOAP(wcserverURL, wcRequestXML, wcuserid, wcpassword,bTrustAllCerts, bHostNameVerify);
			}
			else
			{
				resDoc = sendXMLStringInSOAP(wcserverURL, wcRequestXML, wcuserid, wcpassword,false,false);
			}
			
			if (resDoc != null) {
//				String responseStr = SCWCSoapUtils.convertXMLDocToString(resDoc, true);
//				responseStr = "<ResponseData>\n" + responseStr	+ "</ResponseData>";
//				resDoc = SCWCSoapUtils.convertXmlStringToDocument(responseStr);
				if (YFCLogManager.verboseEnabled) {
					cat.verbose("SOAP REQUEST DOC" + YFCLogUtil.toString(resDoc));
				}
			} else {
				resDoc = SCWCSoapUtils.createErrorDocument(WCErrorReason, WCErrorReasonCode, WCErrorDescription);
			}
		}
		catch(Exception e) {
//			SCWCSoapUtils.handleException(e);
			throw e;
		}
		finally 		{
			cat.endTimer("SCWCIntegrationService.sendAndReceiveSOAPClient");
		}
		return resDoc;
	}
	
	public static Document sendXMLStringInSOAP(String WCserverURL, String WCpayloadXMLStr, String WCuserid, String WCpassword, boolean bTrustAllCerts, boolean bHostNameVerify) 
			throws SAXException,TransformerException,ParserConfigurationException,SOAPException,MalformedURLException,ProtocolException,IOException,Exception
			{ 
		Document doc    = null;
		OutputStreamWriter wout = null;
		InputStream inpsFromWC  = null;
		HttpsURLConnection sslCon = null;
		HttpURLConnection   con    =  null;
		String connectionTimeoutVal = YFCConfigurator.getInstance().getProperty("scwc.server.connection.timeout");
		String readTimeoutVal = YFCConfigurator.getInstance().getProperty("scwc.server.read.timeout");
		int connectionTimeout = 10 * 1000;
		int readTimeout = 15 * 1000;
		if(!YFCCommon.isVoid(connectionTimeoutVal)) {
			try{
				connectionTimeout =  Integer.valueOf(connectionTimeoutVal);
			}catch(NumberFormatException nfe){				
			}
		}		
		if(!YFCCommon.isVoid(readTimeoutVal)) {
			try{
				readTimeout =  Integer.valueOf(readTimeoutVal);
			}catch(NumberFormatException nfe){				
			}
		}		
		
		try
		{
			cat.debug("\n\n\n .....Sending Soap Request to:" + WCserverURL);
			cat.debug("\n[REQUEST]:\n");
			URL url       = new URL(WCserverURL);
			String  protocol = url.getProtocol().toLowerCase();
			int     port     = url.getPort();
			cat.debug("\nHTTP Protocol:"+ protocol);
			cat.debug("Server Port="+ port);
			cat.debug("Server Host="+ url.getHost());

			if ( url.getProtocol().equalsIgnoreCase("https"))
			{

				sslCon = (HttpsURLConnection)url.openConnection();
				if (bHostNameVerify == false)
				{
					sslCon.setHostnameVerifier
					(
							new HostnameVerifier()
							{
								public boolean verify( String Hostname, SSLSession  session )
								{
									cat.debug("HostnameVerifier: verify urlhostname:"+ Hostname);
									return( true );
								}
							}
							);
				}
				con = sslCon;
			}
			else
			{
				con = (HttpURLConnection) url.openConnection();
			}

			con.setDoOutput(true);
			con.setDoInput(true); 
			con.setRequestMethod("POST");
			//connectionToWC.addRequestProperty("SOAPAction", url.toExternalForm());
			con.setRequestProperty("SOAPAction", WCserverURL);		
			con.setConnectTimeout(connectionTimeout);
			con.setReadTimeout(readTimeout);
			cat.debug("Server connection timeout set to: " + connectionTimeout + " milliseconds.");
			cat.debug("Server read timeout set to: " + readTimeout + " milliseconds.");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
			con.setRequestProperty("Connection", "keep-alive");// keep-alive interacts with the SSL closure process. 
			con.setRequestProperty("Cache-Control", "no-cache, no-store");
			con.setAllowUserInteraction(true);

			OutputStream out = con.getOutputStream();
			wout = new OutputStreamWriter(out, "UTF-8"); 

			if ( url.getProtocol().equalsIgnoreCase("https"))
			{
				SCWCSoapUtils.dumpServerCert(sslCon);
			}
			//soapMsg.writeTo(out); //THis call blocks until it receives the returned SOAPMessage.  
			SOAPMessage soapMsg =   SCWCSoapUtils.buildSOAPMsgFromXMLString(WCpayloadXMLStr, WCuserid, WCpassword);
			if(cat.isDebugEnabled()){
				soapMsg.writeTo(System.out);
			}

			String reqXML = SCWCSoapUtils.getXMLStringFromSOAPMsg(soapMsg);//createXMLStringFromSOAPMsg(soapMsg);
			wout.write(reqXML);
			wout.flush();


			// READ RESPONSE.
			SOAPMessage soapResponse;
			try
			{
				inpsFromWC = con.getInputStream();
				soapResponse = MessageFactory.newInstance().createMessage(new MimeHeaders(), inpsFromWC);
			}
			catch(IOException e) 
			{
//				soapResponse = MessageFactory.newInstance().createMessage( new MimeHeaders(), 
//						con.getErrorStream());
//				e.printStackTrace();
				throw e;
			}

			doc = SCWCSoapUtils.convertSOAPMsgBodyToXMLDoc(soapResponse);
			
		
			//return response  content
			cat.debug("\n\n.....Returning response from remote JVM: WC...\n\n");
			return doc;

		}
		catch (RuntimeException ex) 
		{
			//handleException(ex);
			throw ex;
		} 
		catch (MalformedURLException e)
		{
			//handleException(e);
			throw e;
		}
		catch(SOAPException soe) 
		{
			//handleException(soe);
			throw soe;
		}
		catch (IOException e) 
		{
			//handleException(e);
			throw e;
		}catch (Exception e) {
			//handleException(e);
			throw e;
		}
		finally
		{
			//close the connection, set all objects to null
			try
			{
				if (wout != null)       wout.close();
				if (inpsFromWC != null) inpsFromWC.close();
				con.disconnect();
				wout = null;
				con = null;
				sslCon = null;
			}
			catch(IOException e) {throw e; }
		}
	}

}
