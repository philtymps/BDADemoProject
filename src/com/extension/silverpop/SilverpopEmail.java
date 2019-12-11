package com.extension.silverpop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.bda.Utilities;
import com.custom.yantra.xmlmapper.Converter;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.util.YFCXSLTransformer;
import com.yantra.util.YFCXSLTransformerImpl;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class SilverpopEmail {

	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(SilverpopEmail.class);
	
	public SilverpopEmail(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public void setProperty(String name, String value){
		properties.put(name, value);
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public ArrayList<String> getTagProperties(){
		ArrayList<String> keys = new ArrayList<String>();
		
		for(Object key : this.properties.keySet()){
			if(key instanceof String){
				String sKey = (String) key;
				if(sKey.toUpperCase().startsWith("TAG-")){
					keys.add(sKey);
				}
			}		
		}
		
		return keys;
	}
	
	private String getTransactURL(){
		if(!YFCCommon.isVoid(getProperty("TransactURL"))){
			return (String) getProperty("TransactURL");
		} 
		return "http://transact3.silverpop.com/XTMail";
	}
	
	private String getFromAddress(YFCElement eInput){
		if (!YFCCommon.isVoid(getProperty("FromEmail"))){
			try{
				String sXPath = (String) getProperty("FromEmail");
				if(sXPath.startsWith("xml:")){
					sXPath = sXPath.substring(4);
				}
				XPath xPath = XPathFactory.newInstance().newXPath();
				String sResponse = xPath.evaluate(sXPath, eInput.getDOMNode());
				if(!YFCCommon.isVoid(sResponse)){
					return sResponse;
				}
			} catch(Exception e){
				
			}
			return (String)getProperty("FromEmail");
		} 
		return "bda@demo.com";
	}
	
	
	private String getFromName(YFCElement eInput){
		if (!YFCCommon.isVoid(getProperty("FromName"))){
			try{
				String sXPath = (String) getProperty("FromName");
				String sResponse = getValueForProperty(eInput, sXPath);
				if(!YFCCommon.isVoid(sResponse)){
					return sResponse;
				}
			} catch(Exception e){
				
			}
			return (String)getProperty("FromName");
		} 
		return "Base Demo Asset" ;
	}
	
	private String getToAddress(YFCElement eInput){
		
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("To"))){
			return eInput.getAttribute("To");
		} else if (!YFCCommon.isVoid(getProperty("To"))){
			try{
				String sXPath = (String) getProperty("To");
				if(sXPath.startsWith("xml:")){
					sXPath = sXPath.substring(4);
				}
				XPath xPath = XPathFactory.newInstance().newXPath();
				String sResponse = xPath.evaluate(sXPath, eInput.getDOMNode());
				if(!YFCCommon.isVoid(sResponse)){
					return sResponse;
				}
			} catch(Exception e){
				
			}
			return (String)getProperty("To");
		} 
		return "optdemouser@gmail.com";
	
	}
	
	private boolean sendsEmail(){
		return Utilities.getBoolean((String)getProperty("SendEmail"), true);
	}
	
	private boolean writeEmailToFile(){
		return Utilities.getBoolean((String)getProperty("writeEmail"), true);
	}
	
	private String getWriteFilePath(){
		if(!YFCCommon.isVoid(getProperty("FilePath"))){
			return (String) getProperty("FilePath");
		} else {
			return "/opt/Sterling/Emails";
		}
	}
	
	private String getWriteFileName(YFCElement eInput){
		if (!YFCCommon.isVoid(getProperty("FileName"))){
			try{
				String sXPath = (String) getProperty("FileName");
				XPath xPath = XPathFactory.newInstance().newXPath();
				String sResponse = xPath.evaluate(sXPath, eInput.getDOMNode());
				if(!YFCCommon.isVoid(sResponse)){
					return sResponse;
				}
			} catch(Exception e){
				
			}
			return (String)getProperty("FileName");
		} 
		return "email";
	}
	
	private String getWriteFileExtn(){
		if (!YFCCommon.isVoid(getProperty("FileExtn"))){
			return (String)getProperty("FileExtn");
		} 
		return ".xml";
	}

	
	private String getSubject(YFCElement eInput){
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("Subject"))){
			return eInput.getAttribute("Subject");
		} else if (!YFCCommon.isVoid(getProperty("Subject"))){
			return getValueForProperty(eInput, (String) getProperty("Subject"));
		} 
		return "Test";
	}
	
	private void getBody(Element node, YFCElement eInput){
		Document d = node.getOwnerDocument();
		if(!YFCCommon.isVoid(eInput)){
			String temp = getTransformation(eInput);
			if(!YFCCommon.isVoid(temp)){
				node.appendChild(d.createCDATASection(temp));
			} else {
				//node.appendChild(d.createCDATASection(getDummyEmailContent()));
				node.appendChild(d.createCDATASection(eInput.toString()));
			}
		} else if (!YFCCommon.isVoid(getProperty("Body"))){
			node.appendChild(d.createCDATASection((String) getProperty("Body")));
		} else {
			node.appendChild(d.createCDATASection(getDummyEmailContent()));
		}
		
	}
	
	
	private String getDummyEmailContent(){
		String t = "<html><head></head><body><h2>Sample Email</h2><p>Test email to verify service is working</p></body></html>";
		return t;
	}
	
	
	private String getTransformation(YFCElement eInput){
		if(!YFCCommon.isVoid(getProperty("XSL"))){
			YFCXSLTransformer trans = YFCXSLTransformerImpl.getInstance();
			InputStream xslStream = SilverpopEmail.class.getResourceAsStream((String)getProperty("XSL"));
			StringBuffer sb = new StringBuffer(eInput.toString());
			return trans.transformXML((String) getProperty("XSL"), xslStream, sb);
		} else if(!YFCCommon.isVoid(getProperty("XSLFile"))){
			YFCXSLTransformer trans = YFCXSLTransformerImpl.getInstance();
			try {
				InputStream xslStream = new FileInputStream((String)getProperty("XSLFile"));
				StringBuffer sb = new StringBuffer(eInput.toString());
				return trans.transformXML(null, xslStream, sb);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	

	public Document writeToFile(YFSEnvironment env, Document inputDoc){
		
		if(writeEmailToFile()){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eInput = dInput.getDocumentElement();
			String sFileName = getWriteFilePath() + "/" + getWriteFileName(eInput) + getWriteFileExtn();
			String sContentName = getWriteFilePath() + "/" + getWriteFileName(eInput) + "_content.xml";
			
			File path = new File(getWriteFilePath());
			if(!path.exists()){
				path.mkdirs();
			}
			File f = new File(sFileName);
			int i = 0;
			while(f.exists()){
				i++;
				sFileName = getWriteFilePath() + "/" + getWriteFileName(eInput) + i + getWriteFileExtn();
				sContentName = getWriteFilePath() + "/" + getWriteFileName(eInput) + "_content" + i + ".xml";
				f = new File(sFileName);
			}
			
			YFCDocument dOutput = YFCDocument.createDocument("Output");
			YFCElement eOutput = dOutput.getDocumentElement();
			eOutput.createChild("To").setNodeValue(getToAddress(eInput));
			eOutput.createChild("From").setNodeValue(getFromAddress(eInput));
			eOutput.createChild("Subject").setNodeValue(getSubject(eInput));
			getBody((Element)eOutput.createChild("Body").getDOMNode(), eInput);
		
			String content = null;
			if(!YFCCommon.isVoid(eInput)){
				content = getTransformation(eInput);
			} else if (!YFCCommon.isVoid(getProperty("Body"))){
				content = (String) getProperty("Body");
			} else {
				content = getDummyEmailContent();
			}
			
			// write the properties into the output file
			try {
				FileOutputStream sOut = new FileOutputStream  (sFileName);
				sOut.write (content.getBytes());
				sOut.flush();
				sOut.close();
				sOut = new FileOutputStream  (sContentName);
				sOut.write (dOutput.toString().getBytes());
				sOut.flush();
				sOut.close();
			} catch (Exception e){
				e.printStackTrace();
			}
			
			return dOutput.getDocument();
		}
		return inputDoc;
	}
	
	public String getValueForProperty(YFCElement eInput, String sXPath) {
		String sOutput = "";
		if(!Utilities.isVoid(sXPath)){
			String[] words = sXPath.split(" ");
			int i = 0;
			for(String sWord : words){
				if(i > 0){
					sOutput += " ";
				}
				if(sWord.startsWith("xml:")){
					try {
						XPath xPath = XPathFactory.newInstance().newXPath();
						String sResponse = xPath.evaluate(sWord.replace("xml:", ""), eInput.getDOMNode());
						sOutput += sResponse;
					} catch (XPathExpressionException ex){
						sOutput += sWord;
					}
				} else {
					sOutput += sWord;
				}
				i++;
			}			
		}
		return sOutput;
	}
	
	private String[] getSaveColumns(){
		if(!YFCCommon.isVoid(getProperty("SAVE_COLUMNS"))){
			return ((String) getProperty("SAVE_COLUMNS")).split(",");
		}
		return new String[]{};
	}
	
	public Document sendCustomWCAEmail(YFSEnvironment env, Document inputDoc){
		if(!YFCCommon.isVoid(inputDoc)){
			writeToFile(env, inputDoc);
			if(sendsEmail()){
				YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
				YFCElement eInput = dInput.getDocumentElement();
				if(!YFCCommon.isVoid(getToAddress(eInput))){
					YFCDocument dMailing = YFCDocument.createDocument("XTMAILING");
					YFCElement eMailing = dMailing.getDocumentElement();
					YFCElement eCampaign = eMailing.createChild("CAMPAIGN_ID");
					eCampaign.setNodeValue(getCampaignID(true));
					
					if(getSaveColumns().length > 0){
						YFCElement eSaveColumns = eMailing.createChild("SAVE_COLUMNS");
						for(String col : getSaveColumns()){
							YFCElement eColumnName = eSaveColumns.createChild("COLUMN_NAME");
							eColumnName.setNodeValue(col);
						}
					}
					
					YFCElement eShowSendDetails = eMailing.createChild("SHOW_ALL_SEND_DETAIL");
					eShowSendDetails.setNodeValue(getShowAllSendDetails());
					
					YFCElement eNoRetryOnFailure = eMailing.createChild("NO_RETRY_ON_FAILURE");
					eNoRetryOnFailure.setNodeValue(getNoRetryOnFailure());
					
					
					YFCElement eRecipient = eMailing.createChild("RECIPIENT");
					YFCElement eMail = eRecipient.createChild("EMAIL");
					eMail.setNodeValue(getToAddress(eInput));
					YFCElement eBodyType = eRecipient.createChild("BODY_TYPE");
					eBodyType.setNodeValue("HTML");
					
					YFCElement ePersonalization = eRecipient.createChild("PERSONALIZATION");
					YFCElement eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("Subject");
					YFCElement eValue = ePersonalization.createChild("VALUE");
					eValue.setNodeValue(getSubject(eInput));
					
					ePersonalization = eRecipient.createChild("PERSONALIZATION");
					eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("From Name");
					eValue = ePersonalization.createChild("VALUE");
					eValue.setNodeValue(getFromName(eInput));
					
					ePersonalization = eRecipient.createChild("PERSONALIZATION");
					eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("From Address");
					eValue = ePersonalization.createChild("VALUE");
					eValue.setNodeValue(getFromAddress(eInput));
					
					for(String key : this.getTagProperties()){
						ePersonalization = eRecipient.createChild("PERSONALIZATION");
						eTagName = ePersonalization.createChild("TAG_NAME");
						eTagName.setNodeValue(key.substring(4));
						eValue = ePersonalization.createChild("VALUE");
						eValue.setNodeValue(getValueForProperty(eInput, (String) getProperty(key)));
					}
					
					System.out.println(dMailing);
					String sOutput = dMailing.toString();
					
					try {
						
						URL url = new URL(getTransactURL());
				        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				        connection.setDoOutput(true);
				        connection.setRequestMethod("POST");
				        connection.setRequestProperty("Content-Type", "application/xml");
				        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
				        // Write data
				        OutputStream os = connection.getOutputStream();
				        connection.connect();
				        os.write(sOutput.getBytes());
						StringBuffer sb = new StringBuffer();
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String res;
						while ((res = in.readLine()) != null) {
							sb.append(res);
						}
						in.close();
						connection.disconnect();
						System.out.println(YFCDocument.getDocumentFor(sb.toString()));
						return YFCDocument.getDocumentFor(sb.toString()).getDocument();
					
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		return inputDoc;
	}
	
	public Document sendCustomEmail(YFSEnvironment env, Document inputDoc){
		if(!YFCCommon.isVoid(inputDoc)){
			writeToFile(env, inputDoc);
			if(sendsEmail()){
				YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
				YFCElement eInput = dInput.getDocumentElement();
				if(!YFCCommon.isVoid(getToAddress(eInput))){
					YFCDocument dMailing = YFCDocument.createDocument("XTMAILING");
					YFCElement eMailing = dMailing.getDocumentElement();
					YFCElement eCampaign = eMailing.createChild("CAMPAIGN_ID");
					eCampaign.setNodeValue(getCampaignID(true));
					YFCElement eRecipient = eMailing.createChild("RECIPIENT");
					YFCElement eMail = eRecipient.createChild("EMAIL");
					eMail.setNodeValue(getToAddress(eInput));
					YFCElement eBodyType = eRecipient.createChild("BODY_TYPE");
					eBodyType.setNodeValue("HTML");
					
					YFCElement ePersonalization = eRecipient.createChild("PERSONALIZATION");
					YFCElement eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("Subject");
					YFCElement eValue = ePersonalization.createChild("VALUE");
					eValue.setNodeValue(getSubject(eInput));
					
					ePersonalization = eRecipient.createChild("PERSONALIZATION");
					eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("From Name");
					eValue = ePersonalization.createChild("VALUE");
					eValue.setNodeValue(getFromName(eInput));
					
					ePersonalization = eRecipient.createChild("PERSONALIZATION");
					eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("From Address");
					eValue = ePersonalization.createChild("VALUE");
					eValue.setNodeValue(getFromAddress(eInput));
					
					ePersonalization = eRecipient.createChild("PERSONALIZATION");
					eTagName = ePersonalization.createChild("TAG_NAME");
					eTagName.setNodeValue("HTMLBody");
					eValue = ePersonalization.createChild("VALUE");
					getBody((Element)eValue.getDOMNode(), eInput);
					System.out.println(dMailing);
					String sOutput = dMailing.toString();
					
					try {
						
						URL url = new URL(getTransactURL());
				        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				        connection.setDoOutput(true);
				        connection.setRequestMethod("POST");
				        connection.setRequestProperty("Content-Type", "application/xml");
				        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
				        // Write data
				        OutputStream os = connection.getOutputStream();
				        connection.connect();
				        os.write(sOutput.getBytes());
						StringBuffer sb = new StringBuffer();
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String res;
						while ((res = in.readLine()) != null) {
							sb.append(res);
						}
						in.close();
						connection.disconnect();
						System.out.println(YFCDocument.getDocumentFor(sb.toString()));
						return YFCDocument.getDocumentFor(sb.toString()).getDocument();
					
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		return inputDoc;
	}
	
	public Document sendOrderConfirmationEmail(YFSEnvironment env, Document inputDoc){
		if (!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eOrder = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(eOrder.getAttribute("CustomerEMailID"))){
				if (YFCCommon.isVoid(eOrder.getAttribute("CustomerFirstName"))){
					eOrder.setAttribute("CustomerFirstName", "Undefined");
				}
				
				try {
					YFCDocument t =  getExtendedSilverpopXml(eOrder);
					System.out.println(t);
					String sOutput = t.toString();
					URL url = new URL(getTransactURL());
			        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			        connection.setDoOutput(true);
			        connection.setRequestMethod("POST");
			        connection.setRequestProperty("Content-Type", "application/xml");
			        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
			        // Write data
			        OutputStream os = connection.getOutputStream();
			        os.write(sOutput.getBytes());
					StringBuffer sb = new StringBuffer();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String res;
					while ((res = in.readLine()) != null) {
						sb.append(res);
					}
					in.close();
					return YFCDocument.getDocumentFor(sb.toString()).getDocument();
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				YFCDocument dOutput = YFCDocument.createDocument("Output");
				YFCElement eOutput = dOutput.getDocumentElement();
				eOutput.setAttribute("Email", "");
				eOutput.setAttribute("OrderNo", eOrder.getAttribute("OrderNo"));
				return dOutput.getDocument();
			}
		} else {
			YFCDocument dOutput = YFCDocument.createDocument("Output");
			YFCElement eOutput = dOutput.getDocumentElement();
			eOutput.setAttribute("Email", "");
			eOutput.setAttribute("OrderNo", "");
			return dOutput.getDocument();
		}
		
		return null;
	}
	public Document requestOrderEmail(YFSEnvironment env, Document inputDoc) {
		if (!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eOrder = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(eOrder.getAttribute("CustomerEMailID"))){
				if (YFCCommon.isVoid(eOrder.getAttribute("CustomerFirstName"))){
					eOrder.setAttribute("CustomerFirstName", "Undefined");
				}
				
				try {
					String sOutput = getSilverPopupXml(eOrder).toString();
					URL url = new URL(getTransactURL());
			        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			        connection.setDoOutput(true);
			        connection.setRequestMethod("POST");
			        connection.setRequestProperty("Content-Type", "application/xml");
			        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
			        // Write data
			        OutputStream os = connection.getOutputStream();
			        os.write(sOutput.getBytes());
					StringBuffer sb = new StringBuffer();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String res;
					while ((res = in.readLine()) != null) {
						sb.append(res);
					}
					in.close();
					return YFCDocument.getDocumentFor(sb.toString()).getDocument();
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				YFCDocument dOutput = YFCDocument.createDocument("Output");
				YFCElement eOutput = dOutput.getDocumentElement();
				eOutput.setAttribute("Email", "");
				eOutput.setAttribute("OrderNo", eOrder.getAttribute("OrderNo"));
				return dOutput.getDocument();
			}
		} else {
			YFCDocument dOutput = YFCDocument.createDocument("Output");
			YFCElement eOutput = dOutput.getDocumentElement();
			eOutput.setAttribute("Email", "");
			eOutput.setAttribute("OrderNo", "");
			return dOutput.getDocument();
		}
		
		return null;
		
	}
	
	private String getCampaignID(boolean custom){
		if (!YFCCommon.isVoid(getProperty("CampaignID"))){
			return (String) getProperty("CampaignID");
		} else if(custom){
			return "23785879";
		}
		return "21157272";
	}
	
	private String getTransactID(){
		if (!YFCCommon.isVoid(getProperty("TransactID"))){
			return (String) getProperty("TransactID");
		}
		return "optional";
	}
	
	private String getShowAllSendDetails(){
		if (!YFCCommon.isVoid(getProperty("ShowAllSendDetails"))){
			return (String) getProperty("ShowAllSendDetails");
		} else if(!YFCCommon.isVoid(getProperty("SHOW_ALL_SEND_DETAILS"))){
			return (String) getProperty("SHOW_ALL_SEND_DETAILS");
		}
		return "true";
	}
	
	private String getNoRetryOnFailure(){
		if (!YFCCommon.isVoid(getProperty("NoRetryOnFailure"))){
			return (String) getProperty("NoRetryOnFailure");
		} else if(!YFCCommon.isVoid(getProperty("NO_RETRY_ON_FAILURE"))){
			return (String) getProperty("NO_RETRY_ON_FAILURE");
		}
		return "false";
	}
	
	private String getSubject(){
		if (!YFCCommon.isVoid(getProperty("EmailSubject"))){
			return (String) getProperty("EmailSubject");
		}
		return "StarPools Transact";
	}
	
	private YFCDocument getExtendedSilverpopXml(YFCElement eOrder) throws SAXException, IOException{
		YFCDocument dOutput = getSilverPopupXml(eOrder);
		YFCElement eRecipient = dOutput.getDocumentElement().getChildElement("RECIPIENT", true);
		createPersonalization(eRecipient, "Last_Name", eOrder.getAttribute("CustomerLastName"));
		createPersonalization(eRecipient, "AddressLine1", eOrder.getChildElement("PersonInfoBillTo", true).getAttribute("AddressLine1"));
		createPersonalization(eRecipient, "City", eOrder.getChildElement("PersonInfoBillTo", true).getAttribute("City"));
		createPersonalization(eRecipient, "State", eOrder.getChildElement("PersonInfoBillTo", true).getAttribute("State"));
		createPersonalization(eRecipient, "Country", eOrder.getChildElement("PersonInfoBillTo", true).getAttribute("Country"));	
		createPersonalization(eRecipient, "Zip_Code", eOrder.getChildElement("PersonInfoBillTo", true).getAttribute("ZipCode"));
		
		createPersonalization(eRecipient, "Order_No", eOrder.getAttribute("OrderNo"));
		createPersonalization(eRecipient, "Order_Date", eOrder.getAttribute("OrderDate"));
		createPersonalization(eRecipient, "Order_Total", eOrder.getChildElement("PriceInfo", true).getAttribute("TotalAmount"));
		//createPersonalization(eRecipient, "Order_Lines", "<![CDATA[" + eOrder.getChildElement("OrderLines", true) + "]]>");
		int i = 1;
		for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
			createPersonalization(eRecipient, "Order_Line_" + i + "_Qty", eOrderLine.getAttribute("OrderedQty"));
			createPersonalization(eRecipient, "Order_Line_" + i + "_Item_Id", eOrderLine.getChildElement("Item", true).getAttribute("ItemID"));
			createPersonalization(eRecipient, "Order_Line_" + i + "_Item_Desc", eOrderLine.getChildElement("Item", true).getAttribute("ItemShortDesc"));
			createPersonalization(eRecipient, "Order_Line_" + i + "_Line_Total", eOrderLine.getChildElement("LinePriceInfo", true).getAttribute("LineTotal"));
			i++;
		}
		return dOutput;
	}
	private YFCDocument getSilverPopupXml(YFCElement eOrder) throws SAXException, IOException{
		YFCDocument dRoot = YFCDocument.parse(SilverpopEmail.class.getResourceAsStream("SilverpopTemplate.xml"));
		YFCElement eRoot = dRoot.getDocumentElement();
		YFCElement eCampaignID = eRoot.getChildElement("CAMPAIGN_ID", true);
		eCampaignID.setNodeValue(getCampaignID(false));
		YFCElement eTransactID = eRoot.getChildElement("TRANSACT_ID", true);
		eTransactID.setNodeValue(getTransactID());
		YFCElement eShowSendDetails = eRoot.getChildElement("SHOW_ALL_SEND_DETAIL", true);
		eShowSendDetails.setNodeValue(getShowAllSendDetails());
		YFCElement eNoRetryOnFailure = eRoot.getChildElement("NO_RETRY_ON_FAILURE", true);
		eNoRetryOnFailure.setNodeValue(getNoRetryOnFailure());
		YFCElement eSaveColumns = eRoot.getChildElement("SAVE_COLUMNS", true);
		YFCElement eColumnName = eSaveColumns.getChildElement("COLUMN_NAME", true);
		eColumnName.setNodeValue("First_Name");
		YFCElement eRecipient = eRoot.getChildElement("RECIPIENT", true);
		YFCElement eEmail = eRecipient.getChildElement("EMAIL", true);
		eEmail.setNodeValue(eOrder.getAttribute("CustomerEMailID"));
		YFCElement eBodyType = eRecipient.getChildElement("BODY_TYPE", true);
		eBodyType.setNodeValue("HTML");
		for (YFCElement ePersonalization : eRecipient.getChildren("PERSONALIZATION")){
			if (ePersonalization.getChildElement("TAG_NAME").getNodeValue().equals("Subject")){
				ePersonalization.getChildElement("VALUE",true).setNodeValue(getSubject());
			} else if (ePersonalization.getChildElement("TAG_NAME").getNodeValue().equals("First_Name")){
				ePersonalization.getChildElement("VALUE",true).setNodeValue(eOrder.getAttribute("CustomerFirstName"));
			}
		}
		return dRoot;
	}
	
	private void createPersonalization(YFCElement eBodyType, String sName){
		YFCElement ePersonalization = eBodyType.createChild("PERSONALIZATION");
		YFCElement eTagName = ePersonalization.createChild("TAG_NAME");
		eTagName.setNodeValue(sName);
		YFCElement eValue = ePersonalization.createChild("VALUE");
		eValue.addXMLToNode("<HTML><h2>Transact Email Test StarPools</h2></HTML>");
	}

	
	private void createPersonalization(YFCElement eRecipient, String sName, String sValue){
		YFCElement ePersonalization = eRecipient.createChild("PERSONALIZATION");
		YFCElement eTagName = ePersonalization.createChild("TAG_NAME");
		eTagName.setNodeValue(sName);
		YFCElement eValue = ePersonalization.createChild("VALUE");
		eValue.setNodeValue(sValue);
	}
	
	
	public static void main(String[] args){
		SilverpopEmail temp = new SilverpopEmail();
		temp.setProperty("XSLFile", BDAServiceApi.getScriptsPath(null) + "/gucci_shipping.xsl");
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eOrder = dInput.getDocumentElement();
		eOrder.setAttribute("OrderNo", "1000001");
		eOrder.setAttribute("CustomerEMailID", "pfaiola@us.ibm.com");
		eOrder.setAttribute("CustomerFirstName", "Pat");
		eOrder.setAttribute("CustomerLastName", "Faiola");
		eOrder.setAttribute("OrderDate", "2015-12-22T12:31:83.442");
		YFCElement ePriceInfo = eOrder.createChild("PriceInfo");
		ePriceInfo.setAttribute("TotalAmount", "100");
		YFCElement ePersonInfoBillTo = eOrder.createChild("PersonInfoBillTo");
		ePersonInfoBillTo.setAttribute("FirstName", "Pat");
		ePersonInfoBillTo.setAttribute("LastName", "Faiola");
		ePersonInfoBillTo.setAttribute("City", "Lowell");
		ePersonInfoBillTo.setAttribute("State", "MA");
		ePersonInfoBillTo.setAttribute("ZipCode", "01852");
		ePersonInfoBillTo.setAttribute("Country", "US");
		ePersonInfoBillTo.setAttribute("AddressLine1", "200 Market St");
		YFCElement eOrderLine = eOrder.getChildElement("OrderLines", true).createChild("OrderLine");
		eOrderLine.setAttribute("OrderedQty", "1");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "19123");
		eItem.setAttribute("ItemShortDesc", "Widget1");
		YFCElement eLinePrice = eOrderLine.createChild("LinePriceInfo");
		eLinePrice.setAttribute("LineTotal", "50");
		eOrderLine = eOrder.getChildElement("OrderLines", true).createChild("OrderLine");
		eOrderLine.setAttribute("OrderedQty", "1");
		eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "19124");
		eItem.setAttribute("ItemShortDesc", "Widget2");
		eLinePrice = eOrderLine.createChild("LinePriceInfo");
		eLinePrice.setAttribute("LineTotal", "50");
		Document output = temp.sendCustomEmail(null, dInput.getDocument());
		
		YFCDocument dOutput = YFCDocument.getDocumentFor(output);
		System.out.println(dOutput);
	}
}
