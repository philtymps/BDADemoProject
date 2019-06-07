package com.extension.bda.service.expose;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.json.JSONObject;
import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.ibm.CallInteropServlet;
import com.ibm.sterling.afc.jsonutil.PLTJSONUtils;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDARestCall implements IBDAService {

	public static void main(String[] args) {
		BDARestCall test = new BDARestCall();
		Properties p = new Properties();
		p.setProperty("URL", "http://localhost:3000/url");
		p.setProperty("Method", "GET");
		try {
			test.setProperties(p);
			Document d = test.invoke(null, null);
			System.out.println(YFCDocument.getDocumentFor(d));
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	Properties props;
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "callRestService";
	}

	@Override
	public void setProperties(Properties props) throws Exception {
		this.props = props;
	}

	public String getUrl() {
		if(!YFCCommon.isVoid(props) && props.containsKey("URL")) {
			return this.props.getProperty("URL");
		}
		return null;
	}
	
	public String getMethod() {
		if(!YFCCommon.isVoid(props) && props.containsKey("Method")) {
			return this.props.getProperty("Method");
		}
		return "GET";
	}
	


	public String getQueryString(YFCElement eInput) {
		StringBuilder sb = new StringBuilder();
		for(Object key : this.props.keySet()) {
			if(key instanceof String && ((String) key).startsWith("QS")) {
				if(sb.length() > 0) {
					sb.append("&");
				}
				sb.append(((String) key).substring(2) + "=" + getValueForProperty(eInput, this.props.getProperty((String) key)));	
			}
		}
		return sb.toString();
	}
	
	
	public String getValueForProperty(YFCElement eInput, String sXPath) {
		String sOutput = "";
		if(!YFCCommon.isVoid(sXPath)){
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
	
	public void setHeaders (HttpURLConnection connection) {
		
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document dInput) throws Exception {
		String sURL = this.getUrl();
		System.out.println("URL: " + sURL);
		String sQueryString = null;
		JSONObject obj = null;
		if (!YFCCommon.isVoid(sURL)) {
			if(!YFCCommon.isVoid(dInput)) {
				YFCDocument docInput = YFCDocument.getDocumentFor(dInput);
				YFCElement eInput = docInput.getDocumentElement();
				obj = PLTJSONUtils.getJSONObjectFromXML(dInput.getDocumentElement(), null, null);
				sQueryString = this.getQueryString(eInput);
			}
			
			
			try {				
				if(!YFCCommon.isVoid(sQueryString)) {
					sURL += "?" + sQueryString;
				}
				System.out.println("Full URL: " + sURL);
				URL url = new URL(sURL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod(this.getMethod());
				connection.setRequestProperty("Content-Type", "application/json");
				setHeaders(connection);
				connection.setConnectTimeout(5000);
				updateConnection(env, connection);
				if(obj != null && YFCCommon.equals(getMethod(), "POST") || YFCCommon.equals(getMethod(), "PUT")) {
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
					outputStreamWriter.write(obj.toString());
					outputStreamWriter.flush();
				}
				System.out.println("Before Request");
				int responseCode = connection.getResponseCode();
				System.out.println("Response Code: " + responseCode);
				if(responseCode < 300) {
					JSONObject response = new JSONObject(new BufferedInputStream(connection.getInputStream()));
					Document output = PLTJSONUtils.getXmlFromJSON(response.toString(), null);
					return output;
				}
				return null;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return dInput;
	}
	
	protected void updateConnection(YFSEnvironment env, HttpURLConnection connection) {
		
	}

	protected Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName){
		if(!YFCCommon.isVoid(env)) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.invoke(env, sApiName, inDoc);
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!YFCCommon.isVoid(dOrderOutput)){
				return dOrderOutput;
			}
			return null;
		} else {
			if(!YFCCommon.isVoid(dTemplate)) {
				return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, "https://oms.innovationcloud.info").getDocument();
			}
			return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), null, sApiName, "https://oms.innovationcloud.info").getDocument();
		}
		
	
	}
	protected Document callService(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName){
		if(!YFCCommon.isVoid(env)) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.executeFlow(env, sApiName, inDoc);
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!YFCCommon.isVoid(dOrderOutput)){
				return dOrderOutput;
			}
			return null;
		} else {
			if(!YFCCommon.isVoid(dTemplate)) {
				return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, "https://oms.innovationcloud.info").getDocument();
			}
			return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), null, sApiName, "https://oms.innovationcloud.info").getDocument();
		}
		
	
	}
	
}
