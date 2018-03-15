package com.mirakl.shop.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.mirakl.entity.MiraklOrder;
import com.yantra.ycp.agent.server.YCPAbstractAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class MiraklShopAgent extends YCPAbstractAgent {

private String apikey = "", domain = "", translationfile = "";
	
	public String getApiKey(Document criteria) {
		if(!YFCCommon.isVoid(apikey)){
			return apikey;
		} else if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("API-KEY")){
				apikey = criteria.getDocumentElement().getAttribute("API-KEY");
				return apikey;
			}
		}
		System.out.println("Set API-KEY in your MiraklShopAgent");
		apikey = "44cfe66f-6ef9-4091-b76b-c777a94b64a1";
		return apikey;
	}
	
	public String getDomain(Document criteria) {
		if(!YFCCommon.isVoid(domain)){
			return domain;
		} else if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("DOMAIN")){
				domain = criteria.getDocumentElement().getAttribute("DOMAIN");
				return domain;
			}
		}
		System.out.println("Set DOMAIN in your MiraklShopAgent");
		domain = "ibm-dev.mirakl.net";
		return domain;
	}
	
	public String getTranslationFile(Document criteria) {
		if(!YFCCommon.isVoid(translationfile)){
			return translationfile;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("TRANSFORM-DOC")){
				translationfile = criteria.getDocumentElement().getAttribute("TRANSFORM-DOC");
				return translationfile;
			}
		}
		System.out.println("Set TRANSFORM-DOC in your MiraklShopAgent defaulting to /opt/Sterling/Scripts/MiraklTranslate.xml");
		translationfile = "/opt/Sterling/Scripts/MiraklTranslate.xml";
		return translationfile;
	}
	
	protected String getMiraklShopDirectory(Document criteria){
		if(criteria.getDocumentElement().hasAttribute("DIRECTORY")){
			return criteria.getDocumentElement().getAttribute("DIRECTORY");
		}
		return "/opt/Sterling/Scripts/MiraklShopData";
	}
	
	private String getURL(Document criteria, String sRestPath){
		if(!sRestPath.startsWith("/")){
			sRestPath = "/" + sRestPath;
		}
		if(getDomain(criteria).startsWith("http")){
			return getDomain(criteria) + sRestPath;
		}
		return "https://" + getDomain(criteria) + sRestPath;
	}

	
	@Override
	public void executeJob(YFSEnvironment env, Document executionMessage) throws Exception {
		
		YFCDocument dInput = YFCDocument.getDocumentFor(executionMessage);
		String sInput = dInput.getString();
		
		if(dInput.getDocumentElement().getNodeName().equals("body")){
			updateInventory(sInput);
		} else if(dInput.getDocumentElement().getNodeName().equals("Order")){
			YFCElement eOrder = dInput.getDocumentElement();
			if(YFCCommon.equals(eOrder.getAttribute("Action"), "Validate")){
				MiraklOrder mo = new MiraklOrder(eOrder);
			}
		}
		
		
	}
	
	private void updateInventory(String sInput) throws Exception {
		YFCDocument dResponse = null;
		URL url;
		url = new URL(getURL(null, "/api/offers"));
		
		System.out.println("Mirakl Input: " + sInput);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", getApiKey(null));
		conn.setRequestProperty("Content-Type", "application/xml");
		conn.setRequestProperty("Accept", "application/xml");
		conn.setRequestProperty("Content-Length",  String.valueOf(sInput.length()));
		conn.setDoOutput(true);
		
		OutputStream os = conn.getOutputStream();
		os.write(sInput.getBytes());
		
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String res;
		while ((res = in.readLine()) != null) {
			sb.append(res);
		}		
		in.close();
		
		
		if(conn.getResponseCode() == 204){
			conn.disconnect();
		} else if(conn.getResponseCode() >= 300) {
			conn.disconnect();
			throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
		}
		
		try {
			if(!YFCCommon.isVoid(sb.toString())){
				dResponse = YFCDocument.getDocumentFor(sb.toString());
				System.out.println("Mirakl Response: " + dResponse);
			}
		
		} catch (Exception e) {
			System.out.println("Response is not a document");
			System.out.println(sb.toString());
		}
		conn.disconnect();
	}
	
	private String getLastCommittedDate(){
		File dir = new File("/opt/Sterling/Agents");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File sfdcLastOrderCommit = new File("/opt/Sterling/Agents/MiraklShopAgent.txt");
		if(sfdcLastOrderCommit.exists()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(sfdcLastOrderCommit.getAbsolutePath()));
				String line = br.readLine();
				br.close();
				if(!YFCCommon.isVoid(line)){
					
					return line;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void saveCurrentTime(){
		System.out.println("Save Current Time");
		File dir = new File("/opt/Sterling/Agents");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File sfdcLastOrderCommit = new File("/opt/Sterling/Agents/MiraklShopAgent.txt");
		if(sfdcLastOrderCommit.exists()){
			sfdcLastOrderCommit.delete();
		}
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);
		
		byte data[] = df.format(new Date()).getBytes();
		try {
			FileOutputStream out = new FileOutputStream("/opt/Sterling/Agents/MiraklShopAgent.txt");
			out.write(data);
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}	
	}

	private void getOrdersForValidation(Document criteria, ArrayList<Document> jobs, int offset){
		URL url;
		try {
			System.out.println("getOrderUpdates - Start");
			String date = this.getLastCommittedDate();
			if(!YFCCommon.isVoid(date)){
				url = new URL(getURL(criteria, "/api/orders?order_state_codes=WAITING_ACCEPTANCE&max=100&offset=" + offset + "&start_update_date=" + date));
			} else {
				url = new URL(getURL(criteria, "/api/orders?order_state_codes=WAITING_ACCEPTANCE&max=100&offset=" + offset ));
			}
			System.out.println("Calling: " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", getApiKey(criteria));
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
			}
			
			int count = parseOrderReponse(new InputStreamReader(conn.getInputStream()), jobs, criteria);
			if(count >= 99){
				getOrdersForValidation(criteria, jobs, offset + 1);
			}
			System.out.println("Added " + count + " orders for processing.");
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected int parseOrderReponse(InputStreamReader input, ArrayList<Document> jobs, Document criteria) throws IOException {
		BufferedReader br = new BufferedReader(input);
		StringBuilder responseStrBuilder = new StringBuilder();
		
		String inputStr;
		while ((inputStr = br.readLine()) != null) 
			responseStrBuilder.append(inputStr);
	
		JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		JSONArray orders = (JSONArray)jsonObject.get("orders");
		
		for(int i = 0; i < orders.length(); i++){
			Document dJob = new MiraklOrder(orders.getJSONObject(i)).getObjectXML();
			dJob.getDocumentElement().setAttribute("Translation", this.getTranslationFile(criteria));
			dJob.getDocumentElement().setAttribute("Action", "Validate");
			jobs.add(dJob);
		}
		
		br.close();
		return orders.length();
	}
			
	@Override
	public List getJobs(YFSEnvironment env, Document criteria, Document lastMessageCreated) throws Exception {

		ArrayList<Document> jobs = new ArrayList<Document>();
		System.out.println("Get Jobs");
	
		String sDirectory = getMiraklShopDirectory(criteria);
		File f = new File(sDirectory);
		YFCDocument dRoot = YFCDocument.createDocument("body");
		
		boolean found = false;
		if(f.exists() && f.isDirectory()){
			for(File child : f.listFiles()){
				System.out.println("Reading: " + child.getName());
				if(!child.isDirectory() && child.getName().endsWith(".xml")){
					
					YFCDocument dFile = YFCDocument.getDocumentFor(child);
					YFCElement eOffers = dRoot.getDocumentElement().getChildElement("offers", true);
					eOffers.importNode(dFile.getDocumentElement());
					found = true;
					child.delete();
				}
			}
		}
		
		if(found){
			jobs.add(dRoot.getDocument());
		}
		
		getOrdersForValidation(criteria, jobs, 0);
		
		return jobs;
	}

	
	
}
