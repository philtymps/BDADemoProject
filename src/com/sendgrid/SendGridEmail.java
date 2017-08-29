package com.sendgrid;

import java.io.InputStream;
import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.util.YFCXSLTransformer;
import com.yantra.util.YFCXSLTransformerImpl;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class SendGridEmail {

	private Properties properties;
	
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	public SendGridEmail(){
		properties = new Properties();
	}
	
	private String getLogin(){
		if (!YFCCommon.isVoid(getProperty("Login"))){
			return (String)getProperty("Login");
		} 
		return "isdcloud@us.ibm.com";
	}
	
	private String getPassword(){
		if (!YFCCommon.isVoid(getProperty("Password"))){
			return (String)getProperty("Password");
		} 
		return "kimbal350icecream";
	}
	
	private String getFromAddress(){
		if (!YFCCommon.isVoid(getProperty("From"))){
			return (String)getProperty("From");
		} 
		return "bda@demo.com";
	}
	
	private String getToAddress(YFCElement eInput){
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("To"))){
			return eInput.getAttribute("To");
		} else if (!YFCCommon.isVoid(getProperty("To"))){
			return (String)getProperty("To");
		} 
		return "pfaiola@us.ibm.com";
	
	}
	
	private String getSubject(YFCElement eInput){
		if(!YFCCommon.isVoid(eInput) && !YFCCommon.isVoid(eInput.getAttribute("Subject"))){
			return eInput.getAttribute("Subject");
		} else if (!YFCCommon.isVoid(getProperty("Subject"))){
			return (String)getProperty("Subject");
		} 
		return "Test";
	}
	
	private String getBody(YFCElement eInput){
		if(!YFCCommon.isVoid(eInput)){
			String temp = getTransformation(eInput);
			if(!YFCCommon.isVoid(temp)){
				return temp;
			} else {
				return eInput.toString();
			}
		} else if (!YFCCommon.isVoid(getProperty("Body"))){
			return (String)getProperty("Body");
		} 
		return "Test";
	}
	
	private String getTransformation(YFCElement eInput){
		if(!YFCCommon.isVoid(getProperty("XSL"))){
			YFCXSLTransformer trans = YFCXSLTransformerImpl.getInstance();
			InputStream xslStream = SendGridEmail.class.getResourceAsStream((String)getProperty("XSL"));
			StringBuffer sb = new StringBuffer(eInput.toString());
			return trans.transformXML((String) getProperty("XSL"), xslStream, sb);
		}
		return null;
	}
	
	public static void main(String[] args){
		SendGridEmail t = new SendGridEmail();
		t.sendGridEmail(null, YFCDocument.createDocument("Email").getDocument());
	}
	
	public Document sendGridEmail(YFSEnvironment env, Document inputDoc){
		if(!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eInput = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(getToAddress(eInput))){
				SendGrid sendgrid = new SendGrid(getLogin(), getPassword());
				SendGrid.Email email = new SendGrid.Email();
				email.addSmtpApiTo(getToAddress(eInput));
				email.setFrom(getFromAddress());
				email.setSubject(getSubject(eInput));
				email.setHtml(getBody(eInput));
				
				try{
					SendGrid.Response response = sendgrid.send(email);
					System.out.println(response.getMessage());
				} catch (SendGridException e){
					System.err.println(e);
				}
			}
		}
		return inputDoc;
	}
}
