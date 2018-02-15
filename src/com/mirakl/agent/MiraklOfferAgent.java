package com.mirakl.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.mirakl.entity.MiraklOffer;
import com.mirakl.entity.MiraklShop;
import com.yantra.ycp.agent.server.YCPAbstractAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class MiraklOfferAgent extends YCPAbstractAgent {	
	public String getDelimiter() {
		return ";";
	}
	
	public String getApiKey(Document criteria) {
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("API-KEY")){
				return criteria.getDocumentElement().getAttribute("API-KEY");
			}
		}
		return "7467faa1-474b-4cf2-b9d0-0c44b983660e";
	}
	
	public String getDomain(Document criteria) {
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("DOMAIN")){
				return criteria.getDocumentElement().getAttribute("DOMAIN");
			}
		}
		return "ibm-dev.mirakl.net";
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

	private static long lastRun = 0;

	@Override
	public void executeJob(YFSEnvironment env, Document executionMessage) throws Exception {
		System.out.println("Execute Job");
		if(!YFCCommon.isVoid(executionMessage)){
			YFCDocument dMirakl = YFCDocument.getDocumentFor(executionMessage);
			YFCElement eRoot = dMirakl.getDocumentElement();
			
			System.out.println("Execution Message: " + eRoot);
			if(executionMessage.getDocumentElement().getNodeName().equals("MiraklShopList")){
				for(YFCElement eShop : eRoot.getChildren()){
					MiraklShop o = new MiraklShop(eShop);	
					o.createShop();
					System.out.println("Processed: " + o.getShopName() + " (" + o.getShopID() + ")");
				}
			} else if(executionMessage.getDocumentElement().getNodeName().equals("MiraklOfferList")){
				YFCDocument dAdjustInput = YFCDocument.createDocument("Items");
				YFCElement eItems = dAdjustInput.getDocumentElement();
				for(YFCElement eOffer : eRoot.getChildren()){
					MiraklOffer o = new MiraklOffer(eOffer);				
					o.appendInventoryAdjustment(eItems);
				}
				if(eItems.hasChildNodes()){
					System.out.println("Updating Inventory with: " + eItems);
					
					YFCDocument dResponse = CallInteropServlet.invokeApi(dAdjustInput, null, "adjustInventory", "http://oms.omfulfillment.com:9080");		
				}
			}
			
		}
	}

	private String getLastCommittedDate(){
		File dir = new File("/opt/Sterling/Agents");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File sfdcLastOrderCommit = new File("/opt/Sterling/Agents/MiraklOfferAgent.txt");
		if(sfdcLastOrderCommit.exists()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(sfdcLastOrderCommit.getAbsolutePath()));
				String line = br.readLine();
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
		File sfdcLastOrderCommit = new File("/opt/Sterling/Agents/MiraklOfferAgent.txt");
		if(sfdcLastOrderCommit.exists()){
			sfdcLastOrderCommit.delete();
		}
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		
		byte data[] = df.format(new Date()).getBytes();
		try {
			FileOutputStream out = new FileOutputStream("/opt/Sterling/Agents/MiraklOfferAgent.txt");
			out.write(data);
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}	
	}
	
	private void getShopUpdates(Document criteria, ArrayList<Document> jobs, int offset){
		URL url;
		try {
			System.out.println("getShopUpdates - Start");
			String date = this.getLastCommittedDate();
			if(!YFCCommon.isVoid(date)){
				url = new URL(getURL(criteria, "/api/shops?max=100&offset=" + offset + "&updated_since=" + date));
			} else {
				url = new URL(getURL(criteria, "/api/shops?max=100&offset=" + offset));
			}
			System.out.println("Calling: " + url.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", getApiKey(criteria));
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
			}
			
			String line = "";
			YFCDocument job = null;
			ArrayList<String> header = new ArrayList<String>();
			int count = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			job = YFCDocument.createDocument("MiraklShopList");
			
			StringBuilder responseStrBuilder = new StringBuilder();
		
			String inputStr;
			while ((inputStr = br.readLine()) != null) 
				responseStrBuilder.append(inputStr);
		
			conn.disconnect();
			JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
			JSONArray shops = (JSONArray)jsonObject.get("shops");
			for(int i = 0; i < shops.length(); i++){
				JSONObject shop = shops.getJSONObject(i);
				System.out.println(shops.toString());
				MiraklShop ms = new MiraklShop(shop);
				job.getDocumentElement().importNode(ms.getCreateShipNodeXML().getDocumentElement());
			}
			
			
			if(!YFCCommon.isVoid(job)){
				jobs.add(job.getDocument());
			}
			br.close();
			conn.disconnect();
			if(count >= 99){
				getShopUpdates(criteria, jobs, offset + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getOfferUpdates(Document criteria, ArrayList<Document> jobs){
		URL url;
		try {
			System.out.println("getOfferUpdates - Start");
			String date = this.getLastCommittedDate();
			if(!YFCCommon.isVoid(date)){
				url = new URL(getURL(criteria, "/api/offers/export?last_request_date=" + date));
			} else {
				url = new URL(getURL(criteria, "/api/offers/export"));
			}
			System.out.println("Calling: " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", getApiKey(criteria));
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
			}
			
			String line = "";
			YFCDocument job = null;
			ArrayList<String> header = new ArrayList<String>();
			int count = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String sContentLine = "";
			while ((line = br.readLine()) != null) {
				if(count % 200 == 0){
					if(!YFCCommon.isVoid(job)){
						jobs.add(job.getDocument());
					}
					job = YFCDocument.createDocument("MiraklOfferList");
				}
				if(header.size() == 0){
					String[] arguments = line.split(this.getDelimiter());
					for(String argument : arguments){
						header.add(argument);
					}
					count++;
				} else if(line.endsWith("\"")) {
					if(sContentLine.equals("")){
						sContentLine = line;
					} else {
						sContentLine = sContentLine + "\n" + line;
					}
					try {    // use comma as separator
						String[] arguments = sContentLine.split(this.getDelimiter());
						MiraklOffer mo = new MiraklOffer(header, arguments);
						mo.appendToElement(job.getDocumentElement());
						count++;
					} catch (Exception e){
						e.printStackTrace();
					}					
					sContentLine = "";
				} else {
					sContentLine = sContentLine + "\n" + line;
				}
				
				
			}
			if(!YFCCommon.isVoid(job)){
				jobs.add(job.getDocument());
			}
			br.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Document> getJobs(YFSEnvironment env, Document criteria, Document lastMessageCreated) throws Exception {
		ArrayList<Document> jobs = new ArrayList<Document>();
		
		if ((MiraklOfferAgent.lastRun + (1000 * 60 * 5)) < System.currentTimeMillis()){
			getShopUpdates(criteria, jobs, 0);
			getOfferUpdates(criteria, jobs);
			this.saveCurrentTime();
			MiraklOfferAgent.lastRun = System.currentTimeMillis();
			
		}
		
		return jobs;
	}

}
