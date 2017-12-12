package com.objects;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.CallInteropServlet;
import com.ibm.extraction.commerce.BDASynchronization;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class Store extends BDASynchronization {
	
	public static void main(String[] args) throws InterruptedException{
		ArrayList<Store> stores = new ArrayList<Store>();
		
		List<String> arguments = Arrays.asList(args);
		
		if(arguments.contains("-file")){
			String csvFile = arguments.get(arguments.indexOf("-file") + 1);
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        int count = 0;
	       
	        try {
	        	
	        
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	 try {    // use comma as separator
            		 String[] store = line.split(cvsSplitBy);
            		 count++;
            		 stores.add(new Store(store[0], capitalizeString(store[1].trim()) + ", " + store[2].trim().toUpperCase(), "", "", capitalizeString(store[1].trim()), store[2].trim(), store[3].trim(), "US", store[6].trim().toUpperCase(), store[4], store[5]));
            	 } catch (Exception e){
     	        	e.printStackTrace();
            	 }
	        }
	        } catch (Exception e){
	        	e.printStackTrace();
	        	
	        }
	        System.out.println("Loaded: " + count + " nodes");
	        
	        count = 0;
	        for(Store s : stores){
	        	count++;
	        	System.out.println(CallInteropServlet.invokeApi(s.getOrganization(s.getIndexID(), "Bonton"), null, "manageOrganizationHierarchy", "http://oms.omfulfillment.com:9080"));
	        }
	        System.out.println("Loaded: " + count + " nodes");
		} else {
			Connection dbConn = null;
			try {
				dbConn = getOMSConnection();
				String sSql = "SELECT PERSON_INFO_KEY, ADDRESS_LINE1, ADDRESS_LINE2, CITY, STATE, ZIP_CODE, COUNTRY FROM OMDB.YFS_PERSON_INFO WHERE COUNTRY = 'ES' AND LATITUDE IS NULL";
				PreparedStatement ps = dbConn.prepareStatement(sSql);
				ResultSet rs = ps.executeQuery();
				while ( rs.next() ) {
					stores.add(new Store(rs.getString("PERSON_INFO_KEY"), rs.getString("ADDRESS_LINE1"), rs.getString("ADDRESS_LINE2"), rs.getString("CITY"), rs.getString("STATE"), rs.getString("ZIP_CODE"), rs.getString("COUNTRY")));
					
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if(dbConn != null){
					try {
						dbConn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				dbConn = getOMSConnection();
				String sSql = "UPDATE OMDB.YFS_PERSON_INFO SET LATITUDE = ?, LONGITUDE = ? WHERE PERSON_INFO_KEY = ?";
				String zipLocation = "INSERT INTO OMDB.YFS_ZIP_CODE_LOCATION (ZIP_CODE_LOCATION_KEY, COUNTRY, STATE, ZIP_CODE, CITY, LATITUDE, LONGITUDE) VALUES(?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement ps = dbConn.prepareStatement(sSql);
				PreparedStatement zip = dbConn.prepareStatement(zipLocation);
				for(Store s : stores){
					try{
						if(!YFCCommon.isVoid(s.getLatitude())){
							ps.setDouble(1, Double.parseDouble(s.getLatitude()));
							ps.setDouble(2, Double.parseDouble(s.getLongitude()));
							ps.setString(3, s.getName());
							ps.executeUpdate();
						}						
						Thread.sleep(100);	
					} catch (Exception e){
						e.printStackTrace();
					}
					try {
						if(!YFCCommon.isVoid(s.getLatitude())){
							zip.setString(1, s.getName());
							zip.setString(2, s.getCountry());
							zip.setString(3, s.getState());
							zip.setString(4, s.getZipcode());
							zip.setString(5, s.getCity());
							zip.setDouble(6, Double.parseDouble(s.getLatitude()));
							zip.setDouble(7, Double.parseDouble(s.getLongitude()));
							zip.executeUpdate();
						}
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if(dbConn != null){
					try {
						dbConn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
		}
		
		
	}
	
	public static String capitalizeString(String string) {
		  char[] chars = string.toLowerCase().toCharArray();
		  boolean found = false;
		  for (int i = 0; i < chars.length; i++) {
		    if (!found && Character.isLetter(chars[i])) {
		      chars[i] = Character.toUpperCase(chars[i]);
		      found = true;
		    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
		      found = false;
		    }
		  }
		  return String.valueOf(chars);
		}

	public Store(String name, String addressLine1, String addressLine2, String city, String state, String zipcode, String country) {
		super();
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		
	}
	
	public Store(String iID, String name, String addressLine1, String addressLine2, String city, String state, String zipcode, String country, String type, String latitude, String longitude) {
		super();
		this.iID = iID;
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		this.type = type;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getNodeType(){
		if(type.equals("L")){
			return "DC";
		}
		return "Store";
	}
	
	public String getIndexID(){
		return iID;
	}
	public YFCDocument getOrganization(String index, String sEnterprise){
		YFCDocument dOrg = YFCDocument.createDocument("Organization");
		YFCElement eOrg = dOrg.getDocumentElement();
		eOrg.setAttribute("OrganizationCode", index);
		eOrg.setAttribute("ParentOrganizationCode", sEnterprise);
		eOrg.setAttribute("OrganizationName", sEnterprise + " - " + name);
		eOrg.setAttribute("CreatorOrganizationKey", "DEFAULT");
		eOrg.setAttribute("LocaleCode", getLocaleCode());
		eOrg.setAttribute("CatalogOrganizationCode", sEnterprise);
		eOrg.setAttribute("InventoryKeptExternally", "N");
		eOrg.setAttribute("InventoryOrganizationCode", sEnterprise);
		eOrg.setAttribute("InventoryPublished", "Y");
		eOrg.setAttribute("IsBuyer", "N");
		eOrg.setAttribute("IsCarrier", "N");
		eOrg.setAttribute("IsEnterprise", "N");
		eOrg.setAttribute("IsHubOrganization", "N");
		eOrg.setAttribute("IsNode", "Y");
		eOrg.setAttribute("IsSeller", "Y");
		eOrg.setAttribute("PaymentProcessingReqd", "Y");
		eOrg.setAttribute("PrimaryEnterpriseKey", sEnterprise);
		eOrg.setAttribute("RequiresChainedOrder", "N");
		eOrg.setAttribute("UCCPrefix", "000000");
		eOrg.setAttribute("PricingOrganizationCode", sEnterprise);
		eOrg.setAttribute("StoreConfigOrganizationCode", sEnterprise);
		eOrg.setAttribute("DefaultPaymentRuleId", sEnterprise + "_DEFAULT");
		eOrg.setAttribute("CapacityOrganizationCode", sEnterprise);
		eOrg.setAttribute("CustomerMasterOrganizationCode", sEnterprise);
		eOrg.setAttribute("CustomerMaintainedExternally", "N");
		YFCElement eNode = eOrg.createChild("Node");
		eNode.setAttribute("AcceptanceRequired", "N");
		eNode.setAttribute("AdvanceNotificationTime", "0");
		eNode.setAttribute("BolPrefix", "00");
		eNode.setAttribute("CanShipToDC", "Y");
		eNode.setAttribute("CanShipToOtherAddresses", "Y");
		eNode.setAttribute("CanShipToStore", "Y");
		eNode.setAttribute("DcmIntegrationRealTime", "N");
		eNode.setAttribute("DefaultDeclaredValue", "0.0");
		eNode.setAttribute("Description",  sEnterprise + " - " + name);
		eNode.setAttribute("GenerateBolNumber", "N");
		eNode.setAttribute("IdentifiedByParentAs", index);
		eNode.setAttribute("InterfaceType", "YFX");
		eNode.setAttribute("InventoryTracked", "Y");
		eNode.setAttribute("Inventorytype", "TRACK");
		eNode.setAttribute("LocaleCode", getLocaleCode());
		eNode.setAttribute("Latitude", getLatitude());
		eNode.setAttribute("Longitude", getLongitude());
		eNode.setAttribute("MaintainInventoryCost", "N");
		eNode.setAttribute("MaxConcurrentWaves", "0");
		eNode.setAttribute("MaxDaysToScheduleBefore", "0");
		eNode.setAttribute("MinNotificationTime", "0");
		eNode.setAttribute("NodeOrgCode", index);
		eNode.setAttribute("NodeType", getNodeType());
		eNode.setAttribute("ReceivingNode", "N");
		eNode.setAttribute("RequireTransferAcceptance", "N");
		eNode.setAttribute("ReturnCenterFlag", "N");
		eNode.setAttribute("ReturnsNode", "Y");
		eNode.setAttribute("SerialTracking", "N");
		eNode.setAttribute("ShipNode", index);
		eNode.setAttribute("ShipnodeKey", index);
		eNode.setAttribute("ShippingNode", "Y");
		eNode.setAttribute("SubstitutionAllowed", "Y");
		eNode.setAttribute("ThreePlNode", "N");
		if(Float.parseFloat(getLongitude()) < -115){
			eNode.setAttribute("TimeDiff", "3");
		} else if(Float.parseFloat(getLongitude()) < -103){
			eNode.setAttribute("TimeDiff", "2");
		} else if(Float.parseFloat(getLongitude()) < -81){
			eNode.setAttribute("TimeDiff", "1");
		} else {
			eNode.setAttribute("TimeDiff", "0");
		}
		eNode.setAttribute("WorkOrderCreationAllowed", "Y");
		YFCElement ePaymentRule = eOrg.createChild("PaymentRuleList").createChild("PaymentRule");
		ePaymentRule.setAttribute("CollectExternalThroughAr", "N");
		ePaymentRule.setAttribute("DefaultFlag", "N");
		ePaymentRule.setAttribute("DeferredCreditOnReturnRequired", "N");
		ePaymentRule.setAttribute("FinancialHandlingRequired", "B");
		ePaymentRule.setAttribute("InterfaceTime", "AT_COLLECT");
		ePaymentRule.setAttribute("CustomerAccountMaintainedInternally", "Y");
		ePaymentRule.setAttribute("PaymentRuleId", sEnterprise + "_DEFAULT");
		createAddress(eNode, "ShipNodePersonInfo");
		createAddress(eNode, "ContactPersonInfo");
		createAddress(eOrg, "CorporatePersonInfo");
		createAddress(eOrg, "ContactPersonInfo");
		createAddress(eOrg, "BillingPersonInfo");
		try {
			eOrg.importNode(YFCDocument.parse(Store.class.getResourceAsStream("Payments.xml")).getDocumentElement());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dOrg;
	}

	private void createUser(YFCElement eUserList, String index, String sEnterprise){
		YFCElement eUser = eUserList.createChild("User");
		eUser.setAttribute("ActivateFlag", "Y");
		eUser.setAttribute("CreatorOrganizationKey", "DEFAULT");
		eUser.setAttribute("DataSecurityGroupId", sEnterprise + "_" + index + "_TEAM");
		eUser.setAttribute("DisplayUserID", "Store_" + index + "_User");
		eUser.setAttribute("EnterpriseCode", sEnterprise);
		eUser.setAttribute("Localecode", getLocaleCode());
		eUser.setAttribute("Loginid", "Store_" + index + "_User");
		eUser.setAttribute("Password", "Store_" + index + "_User");
		eUser.setAttribute("MenuId", "SOP_MENU");
		eUser.setAttribute("Username", "Store Associate");
		eUser.setAttribute("Longdesc", "Store Associate");
		eUser.setAttribute("UsergroupKey", "SOP-CSR-KEY");
		eUser.setAttribute("Usertype", "INTERNAL");
		eUser.setAttribute("Theme", "ice");
		YFCElement eUserGroup = eUser.createChild("UserGroupLists").createChild("UserGroupList");
		eUserGroup.setAttribute("UsergroupKey", "SOP-CSR-KEY");
	}
	
	public String getLocaleCode(){
		if(Float.parseFloat(getLongitude()) < -115){
			return "en_US_PST";
		} else if(Float.parseFloat(getLongitude()) < -103){
			return "en_US_MST";
		} else if(Float.parseFloat(getLongitude()) < -81){
			return "en_US_CST";
		}
		return "en_US_EST";
	}
	
	private void createAddress(YFCElement eParent, String sNodeName){
		YFCElement eAdd = eParent.createChild(sNodeName);
		eAdd.setAttribute("AddressLine1", getAddressLine1());
		eAdd.setAttribute("AddressLine2", getAddressLine2());
		eAdd.setAttribute("City", getCity());
		eAdd.setAttribute("State", getState());
		eAdd.setAttribute("ZipCode", getZipcode());
		eAdd.setAttribute("Country", "US");
		eAdd.setAttribute("Longitude", getLongitude());
		eAdd.setAttribute("Latitude", getLatitude());
	}
	private String name, addressLine1, addressLine2, city, state, zipcode, country, type;
	private String longitude, latitude;
	private String iID;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLongitude() {
		if(longitude == null){
			try{
				getLatLong();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		if(longitude == null){
			try{
				getLatLong();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	private String getGAddress(){
		String sInput = null;
		if(country.equals("GB")){
			if(sInput == null && zipcode != null){
				sInput = zipcode.trim().replaceAll(" ","+");
			}
		}else {
			if(addressLine1 != null){
				sInput = addressLine1.trim().replaceAll(" ", "+");
			}
			if(sInput != null && city != null){
				sInput += "," + city.trim().replaceAll(" ", "+");
			}
			if(sInput != null && state != null){
				sInput += "," + state.trim().replaceAll(" ", "+");
			}
			if(sInput == null && zipcode != null){
				sInput = zipcode.trim().replaceAll(" ","+");
			}
		}
		return sInput;
	}

	private void getLatLong() throws Exception{
		int responseCode = 0;
	    String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(getGAddress(), "UTF-8") + "&components=country:" + country + "&sensor=true";
	    System.out.println("URL : "+api);
	    URL url = new URL(api);
	    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	    httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    if(responseCode == 200)
	    {
	      Document document = YFCDocument.parse(httpConnection.getInputStream()).getDocument();
	      XPathFactory xPathfactory = XPathFactory.newInstance();
	      XPath xpath = xPathfactory.newXPath();
	      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	      String status = (String)expr.evaluate(document, XPathConstants.STRING);
	      if(status.equals("OK"))
	      {
	         expr = xpath.compile("//geometry/location/lat");
	         latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         expr = xpath.compile("//geometry/location/lng");
	         longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	      }
	      else
	      {
	    	api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(getZipcode().trim().replaceAll(" ", "+")  + "&components=country:" + country.trim().replaceAll(" ","+"), "UTF-8") + "&sensor=true";
	  	    System.out.println("URL : "+api);
	  	    url = new URL(api);
	  	    httpConnection = (HttpURLConnection)url.openConnection();
	  	    httpConnection.connect();
	  	    responseCode = httpConnection.getResponseCode();
	  	    if(responseCode == 200)
	  	    {
	  	    	expr = xpath.compile("//geometry/location/lat");
		         latitude = (String)expr.evaluate(document, XPathConstants.STRING);
		         expr = xpath.compile("//geometry/location/lng");
		         longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	  	    } else{
	  	    	throw new Exception("Error from the API - response status: "+status);
	  	    }
	         
	      }
	    }
	}
}
