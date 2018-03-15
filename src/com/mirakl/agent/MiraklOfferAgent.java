package com.mirakl.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.mirakl.entity.MiraklOffer;
import com.mirakl.entity.MiraklOrder;
import com.mirakl.entity.MiraklShop;
import com.mirakl.entity.MiraklTranslation;
import com.mirakl.utilities.MiraklUtils;
import com.yantra.ycp.agent.server.YCPAbstractAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class MiraklOfferAgent extends YCPAbstractAgent {	
	public String getDelimiter() {
		return ";";
	}
	
	public static void main(String[] args){
		ArrayList<Document> jobs = new ArrayList<Document>();
		MiraklOfferAgent moa = new MiraklOfferAgent();
		try {
			moa.testShopUpdates(jobs);
			moa.testOfferUpdates(jobs);
			for(Document d : jobs){
				moa.executeJob(null, d);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void testOfferUpdates(ArrayList<Document> jobs) throws IOException{
		parseOfferUpdateReponse(new InputStreamReader(MiraklOfferAgent.class.getResourceAsStream("offerexport.txt")), jobs, null);
	}
	
	public void testShopUpdates(ArrayList<Document> jobs) throws IOException{
		this.parseShopUpdateResponse(new InputStreamReader(MiraklOfferAgent.class.getResourceAsStream("shops.json")), jobs, null);
	}
	
	private String apikey = "", domain = "", nodetype = "", catalogorg = "", inventoryorg = "", classification = "", defaultdistribution = "", transactionid="", translationfile = "";
	
	public String getApiKey(Document criteria) {
		if(!YFCCommon.isVoid(apikey)){
			return apikey;
		} else if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("API-KEY")){
				apikey = criteria.getDocumentElement().getAttribute("API-KEY");
				return apikey;
			}
		}
		System.out.println("Set API-KEY in your MiraklOfferAgent");
		apikey = "e67db8ef-0387-4bb3-9903-c2b323e78e19";
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
		System.out.println("Set DOMAIN in your MiraklOfferAgent");
		domain = "ibm-dev.mirakl.net";
		return domain;
	}
	
	public String getMarketPlaceNodeType(Document criteria) {
		if(!YFCCommon.isVoid(nodetype)){
			return nodetype;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("MARKETPLACE-NODETYPE")){
				nodetype = criteria.getDocumentElement().getAttribute("MARKETPLACE-NODETYPE");
				return nodetype;
			}
		}
		System.out.println("Set MARKETPLACE-NODETYPE in your MiraklOfferAgent defaulting to Marketplace");
		nodetype = "Marketplace";
		return nodetype;
	}
	
	public String getCatalogOrg(Document criteria) {
		if(!YFCCommon.isVoid(catalogorg)){
			return catalogorg;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("CATALOG-ORG")){
				catalogorg = criteria.getDocumentElement().getAttribute("CATALOG-ORG");
				return catalogorg;
			}
		}
		System.out.println("Set CATALOG-ORG in your MiraklOfferAgent defaulting to Aurora-Corp");
		catalogorg = "Aurora-Corp";
		return catalogorg;
	}
	
	public String getInventoryOrg(Document criteria) {
		if(!YFCCommon.isVoid(inventoryorg)){
			return inventoryorg;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("INVENTORY-ORG")){
				inventoryorg = criteria.getDocumentElement().getAttribute("INVENTORY-ORG");
				return inventoryorg;
			}
		}
		System.out.println("Set INVENTORY-ORG in your MiraklOfferAgent defaulting to Aurora");
		inventoryorg = "Aurora";
		return inventoryorg;
	}
	
	public String getClassification(Document criteria) {
		if(!YFCCommon.isVoid(classification)){
			return classification;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("STORAGE-TYPE")){
				classification = criteria.getDocumentElement().getAttribute("STORAGE-TYPE");
				return classification;
			}
		}
		System.out.println("Set STORAGE-TYPE in your MiraklOfferAgent defaulting to Standard");
		classification = "Standard";
		return classification;
	}
	
	public String getDefaultDistributionRule(Document criteria) {
		if(!YFCCommon.isVoid(defaultdistribution)){
			return defaultdistribution;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("DEFAULT-DISTRIBUTION")){
				defaultdistribution = criteria.getDocumentElement().getAttribute("DEFAULT-DISTRIBUTION");
				return defaultdistribution;
			}
		}
		System.out.println("Set DEFAULT-DISTRIBUTION in your MiraklOfferAgent defaulting to Aurora_Shipping_Network");
		defaultdistribution = "Aurora_Shipping_Network";
		return defaultdistribution;
	}
	
	public String getTransactionID(Document criteria) {
		if(!YFCCommon.isVoid(transactionid)){
			return transactionid;
		}
		if(!YFCCommon.isVoid(criteria)){
			if(criteria.getDocumentElement().hasAttribute("STATUS-TRANSACTION-ID")){
				transactionid = criteria.getDocumentElement().getAttribute("STATUS-TRANSACTION-ID");
				return transactionid;
			}
		}
		System.out.println("Set STATUS-TRANSACTION-ID in your MiraklOfferAgent defaulting to MiraklStatusUpdate.0005.ex");
		transactionid = "MiraklStatusUpdate.0005.ex";
		return transactionid;
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
		System.out.println("Set TRANSFORM-DOC in your MiraklOfferAgent defaulting to /opt/Sterling/Scripts/MiraklTranslate.xml");
		translationfile = "/opt/Sterling/Scripts/MiraklTranslate.xml";
		return translationfile;
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
		if(!YFCCommon.isVoid(executionMessage)){
			YFCDocument dMirakl = YFCDocument.getDocumentFor(executionMessage);
			YFCElement eRoot = dMirakl.getDocumentElement();
			
			if(executionMessage.getDocumentElement().getNodeName().equals("MiraklShopList")){
				for(YFCElement eShop : eRoot.getChildren()){
					MiraklShop o = new MiraklShop(eShop);	
					o.createShop(env);
					System.out.println("Processed: " + o.getShopName() + " (" + o.getShopID() + ")");
				}
			} else if(executionMessage.getDocumentElement().getNodeName().equals("MiraklOfferList")){
				YFCDocument dAdjustInput = YFCDocument.createDocument("Items");
				YFCDocument dManageItem = YFCDocument.createDocument("ItemList");
				YFCElement eItems = dAdjustInput.getDocumentElement();
				for(YFCElement eOffer : eRoot.getChildren()){
					MiraklOffer o = new MiraklOffer(eOffer);
					o.appendItem(dManageItem.getDocumentElement());
					o.appendInventoryAdjustment(eItems);
				}
				if(eItems.hasChildNodes()){
					System.out.println("Updating Inventory with: " + eItems);
					MiraklUtils.callApi(env, dManageItem, null, "manageItem");
					MiraklUtils.callApi(env, dAdjustInput, null, "adjustInventory");
				}
			} else if(executionMessage.getDocumentElement().getNodeName().equals("order")){
				MiraklOrder mo = new MiraklOrder(dMirakl);
				mo.updateOrder(env, eRoot.getAttribute("TransactionId"), eRoot.getAttribute("Translation"));
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
		File sfdcLastOrderCommit = new File("/opt/Sterling/Agents/MiraklOfferAgent.txt");
		if(sfdcLastOrderCommit.exists()){
			sfdcLastOrderCommit.delete();
		}
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
	
	protected void parseOfferUpdateReponse(InputStreamReader input, ArrayList<Document> jobs, Document criteria) throws IOException {
		String line = "";
		YFCDocument job = null;
		ArrayList<String> header = new ArrayList<String>();
		int count = 0;
		BufferedReader br = new BufferedReader(input);
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
					MiraklOffer mo = new MiraklOffer(header, arguments, this.getCatalogOrg(criteria), this.getInventoryOrg(criteria), getClassification(criteria));
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
		System.out.println("Added " + count + " offers for processing.");
		br.close();
	}
	protected int parseShopUpdateResponse(InputStreamReader input, ArrayList<Document> jobs, Document criteria) throws IOException{
		YFCDocument job = null;
		int count = 0;
		BufferedReader br = new BufferedReader(input);
		job = YFCDocument.createDocument("MiraklShopList");
		
		StringBuilder responseStrBuilder = new StringBuilder();
	
		String inputStr;
		while ((inputStr = br.readLine()) != null) 
			responseStrBuilder.append(inputStr);
	
		JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		JSONArray shops = (JSONArray)jsonObject.get("shops");
		for(int i = 0; i < shops.length(); i++){
			JSONObject shop = shops.getJSONObject(i);
			// System.out.println(shops.toString());
			MiraklShop ms = new MiraklShop(shop, this.getMarketPlaceNodeType(criteria), this.getCatalogOrg(criteria), this.getInventoryOrg(criteria), this.getDefaultDistributionRule(criteria));
			job.getDocumentElement().importNode(ms.getObjectXML().getDocumentElement());
		}
		
		
		if(!YFCCommon.isVoid(job)){
			jobs.add(job.getDocument());
		}
		br.close();
		System.out.println("Added " + count + " shops for processing.");
		return count;
		
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
			
			int count = parseShopUpdateResponse(new InputStreamReader(conn.getInputStream()), jobs, criteria);
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
			
			parseOfferUpdateReponse(new InputStreamReader(conn.getInputStream()), jobs, criteria);
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected int parseOrderUpdateReponse(InputStream input, ArrayList<Document> jobs, Document criteria) throws IOException, SAXException {
		//System.out.println("parseOrderUpdateResponse");
		YFCDocument dBody = YFCDocument.parse(input);
		//System.out.println(dBody);
		YFCElement eBody = dBody.getDocumentElement();
		for(YFCElement eOrder : eBody.getChildElement("orders", true).getChildren()){
			//System.out.println("Order: " + eOrder);
			Document dJob = new MiraklOrder(eOrder).getObjectXML();
			dJob.getDocumentElement().setAttribute("Translation", this.getTranslationFile(criteria));
			dJob.getDocumentElement().setAttribute("TransactionId", this.getTransactionID(criteria));
			jobs.add(dJob);
		}

		return (int) eBody.getChildElement("total_count", true).getLongNodeValue();
	}
	
	private void getOrderUpdates(Document criteria, ArrayList<Document> jobs, int offset){
		URL url;
		try {
			System.out.println("getOrderUpdates - Start");
			String date = this.getLastCommittedDate();
			if(!YFCCommon.isVoid(date)){
				url = new URL(getURL(criteria, "/api/orders?max=100&offset=" + offset + "&start_update_date=" + date));
			} else {
				url = new URL(getURL(criteria, "/api/orders?max=100&offset=" + offset ));
			}
			System.out.println("Calling: " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("Authorization", getApiKey(criteria));
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code return : " + conn.getResponseCode());
			}
			
			int count = parseOrderUpdateReponse(conn.getInputStream(), jobs, criteria);
			if(count >= 99){
				getOrderUpdates(criteria, jobs, offset + 1);
			}
			System.out.println("Added " + count + " orders for processing.");
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
			getOrderUpdates(criteria, jobs, 0);
			this.saveCurrentTime();
			MiraklOfferAgent.lastRun = System.currentTimeMillis();
		}
		
		return jobs;
	}

}
