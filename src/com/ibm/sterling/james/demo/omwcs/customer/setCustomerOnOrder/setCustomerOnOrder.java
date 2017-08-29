package com.ibm.sterling.james.demo.omwcs.customer.setCustomerOnOrder;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.sterling.james.demo.omwcs.Utils.CachedXPathUtil;
import com.ibm.sterling.james.demo.omwcs.Utils.XMLUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class setCustomerOnOrder implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(setCustomerOnOrder.class);
	
	@Override
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	static {
		try {
			api = YIFClientFactory.getInstance().getApi();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes input a new custom service
	 * It looks up an order address to match to a customer record.
	 * As WCS 7FEP5 sends the BuyerUserID, this is also matched against the customer
	 * record and updated if required
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document onCustomerOrderUpdate(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		logger.verbose("Hello Set Customer on an Order World");
		logger.verbose("onCustomerOrderUpdate.InputDoc=" + XMLUtil.getXMLString(inputDoc));
		xpathUtil = new CachedXPathUtil();
		String strHasBillToID = validateInputXML(inputDoc);
		/* If a BillToID is present, this is the OM Customer ID */
		if (strHasBillToID == "N") {
			logger.verbose("True - No BillToID so will try and match customer");
			String strHasBuyerUserID = validateBuyerUserID(inputDoc);
			if (strHasBuyerUserID == "N") {
				logger.verbose("True - No BuyerUserID so will try and match customer calling matchCustomerNoBuyerUserID");
				inputDoc = matchCustomerNoBuyerUserID(inputDoc);
			}
			if (strHasBuyerUserID == "Y") {
				logger.verbose("True - BuyerUserID is set so will try and match customer calling matchCustomerBuyerUserID");
				inputDoc = matchCustomerBuyerUserID(inputDoc);
			}
		}
		/*inputDoc = updateCustomerOnOrder(inputDoc);*/
		return inputDoc;
	}
	
	private String validateInputXML(Document inputDoc) throws Exception {
		logger.verbose("validateInputXML method");
		String strBillToID = "";
		String strHasBillToID="N";
		
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			for (int i = 0; i < 1; i++) {
				Element elOrderXML = (Element)nlOrder.item(i);
				strBillToID = elOrderXML.getAttribute("BillToID");
			}
		}
		logger.verbose("strBillToID: " + strBillToID);
		if (strBillToID != null && strBillToID != "") {
			logger.verbose("True strBillToID is set.");
			strHasBillToID = "Y";
		}
	
		return strHasBillToID;
	}
	
	/* This checks for BuyerUserID from WCS */
	private String validateBuyerUserID(Document inputDoc) throws Exception {
		logger.verbose("validateBuyerUserID method");
		String strBuyerUserID = "";
		String strHasBuyerUserID="N";
		
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			for (int i = 0; i < 1; i++) {
				Element elOrderXML = (Element)nlOrder.item(i);
				strBuyerUserID = elOrderXML.getAttribute("BuyerUserId");
			}
		}
		logger.verbose("strBuyerUserID: " + strBuyerUserID);
		if (strBuyerUserID != null && strBuyerUserID != "") {
			logger.verbose("True strBuyerUserID is set.");
			strHasBuyerUserID = "Y";
		}
	
		return strHasBuyerUserID;
	}
	/* As there is a BuyerUserID - this must be unique in OM so we will use that record if found */
	private Document matchCustomerBuyerUserID(Document inputDoc) throws Exception {
		logger.verbose("matchCustomerBuyerUserID method");
		Document docMethod = XMLUtil.getDocument("<PersonInfo></PersonInfo>");
		Element elePersonInfo = (Element)(xpathUtil.getNode(docMethod, "PersonInfo"));
		elePersonInfo.setAttribute("MatchedPerson", "N");
		elePersonInfo.setAttribute("MatchedUserID", "N");
		elePersonInfo.setAttribute("UpdateType", "NONE");
		String strMatch = elePersonInfo.getAttribute("MatchedPerson");
		String strUpdateType = elePersonInfo.getAttribute("UpdateType");
		String strCustomerID = "";
		String strEnterpriseCode="";
		String strBuyerUserID="";
		String strFirstName = "";
		String strLastName = "";
		String strEMailID = "";
		String strAddressLine1 = "";
		String strCity = "";
		String strZipCode = "";
		String strCustomerMasterOrganizationCode = "";
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			for (int i = 0; i < 1; i++) {
				Element elOrderXML = (Element)nlOrder.item(i);
				strEnterpriseCode = elOrderXML.getAttribute("EnterpriseCode");
				Element eleOrganisation = getOrganisationAttr(strEnterpriseCode);
				strCustomerMasterOrganizationCode = eleOrganisation.getAttribute("CustomerMasterOrganizationCode");
				strBuyerUserID = elOrderXML.getAttribute("BuyerUserId");
				strFirstName = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@FirstName");
				strLastName = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@LastName");
				strEMailID = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@EMailID");
				strAddressLine1 = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@AddressLine1");
				strCity = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@City");
				strZipCode = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@ZipCode");
				logger.verbose("strEnterpriseCode: " + strEnterpriseCode);
				logger.verbose("strBuyerUserID: " + strBuyerUserID);
				logger.verbose("strFirstName: " + strFirstName);
				logger.verbose("strLastName: " + strLastName);
				logger.verbose("strEMailID: " + strEMailID);
				logger.verbose("strAddressLine1: " + strAddressLine1);
				logger.verbose("strCity: " + strCity);
				logger.verbose("strZipCode: " + strZipCode);
			}
			
			if (strEnterpriseCode != null && strEnterpriseCode != "" && strFirstName != null && strFirstName != "" && 
					strLastName != null && strLastName != "" && strEMailID != null && strEMailID != "" && 
					strAddressLine1 != null && strAddressLine1 != "" && strCity != null && strCity != "" && strZipCode != null && strZipCode != "") {
				logger.verbose("True - address variables set so will try to match address.");
				Element eleBillToInfo = (Element)xpathUtil.getNode(inputDoc, "/Order/PersonInfoBillTo");
				logger.verbose("eleBillToInfo: " + XMLUtil.getXMLString(eleBillToInfo));
				/* Setting Match Element for Customer List for PersonInfo - First trying first name, last name, Email and Zip Code 
				 * Exact Match so no ManageCustomer Updates*/
				logger.verbose("Trying to match customer by First Name, Last Name, Email and Zip Code. (matchCustomerBuyerUserID)");
				String strMatchXML = "<PersonInfo FirstName='%s' LastName='%s' EMailID='%s' ZipCode='%s'/>";
				strMatchXML = String.format(strMatchXML, strFirstName, strLastName, strEMailID, strZipCode);
				Document docCustomerList = getCustomerListBuyerUserID(strCustomerMasterOrganizationCode, strBuyerUserID, strMatchXML);
				elePersonInfo = matchAddressAttributes(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
				strMatch = elePersonInfo.getAttribute("MatchedPerson");
				logger.verbose("strMatch: " + strMatch);
				if (strMatch == "Y") {
					logger.verbose("True match customer using First Name, Last Name, Email and Zip Code. (matchCustomerBuyerUserID)");
				}
				/* Setting Match Element for Customer List for PersonInfo - Trying FirstName, LastName, and ZipCode
				 * A match will indicate an email update */
				if (strMatch == "N") {
					logger.verbose("Trying to match customer by FirstName, LastName, and ZipCode.");
					strMatchXML = "<PersonInfo FirstName='%s' LastName='%s' ZipCode='%s'/>";
					strMatchXML = String.format(strMatchXML, strFirstName, strLastName,  strZipCode);
					docCustomerList = getCustomerListBuyerUserID(strCustomerMasterOrganizationCode, strBuyerUserID, strMatchXML);
					elePersonInfo = matchAddressAttributes(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
					strMatch = elePersonInfo.getAttribute("MatchedPerson");
					logger.verbose("strMatch: " + strMatch);
					if (strMatch == "Y") {
						logger.verbose("True match customer using FirstName, LastName, and ZipCode. (matchCustomerBuyerUserID)");
					}
				}
				/* Setting Match Element for Customer List for PersonInfo - Trying FirstName, LastName, and Email
				 * A match will indicate address needs adding */
				if (strMatch == "N") {
					logger.verbose("Trying to match customer by FirstName, LastName, and Email. (matchCustomerBuyerUserID)");
					strMatchXML = "<PersonInfo FirstName='%s' LastName='%s' EMailID='%s'/>";
					strMatchXML = String.format(strMatchXML, strFirstName, strLastName,  strEMailID);
					docCustomerList = getCustomerListBuyerUserID(strCustomerMasterOrganizationCode, strBuyerUserID, strMatchXML);
					elePersonInfo = matchAddressAttributes(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
					strMatch = elePersonInfo.getAttribute("MatchedPerson");
					logger.verbose("strMatch: " + strMatch);
					if (strMatch == "Y") {
						logger.verbose("True match customer using FirstName, LastName, and Email. (matchCustomerBuyerUserID)");
					}
				}
				/* OM can only have unique UserIDs - Therefore last check is on just the User ID */
				if (strMatch == "N") {
					logger.verbose("Trying to match customer by UserID. (matchCustomerBuyerUserID)");
					strMatchXML = "<PersonInfo/>";
					docCustomerList = getCustomerListBuyerUserID(strCustomerMasterOrganizationCode, strBuyerUserID, strMatchXML);
					elePersonInfo = matchUserIDOnly(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
					strMatch = elePersonInfo.getAttribute("MatchedUserID");
					logger.verbose("strMatch: " + strMatch);
					if (strMatch == "Y") {
						logger.verbose("True match customer using User ID. (matchCustomerBuyerUserID)");
					}
				}
				/* If Match Updates */
				if (strMatch == "Y") {
					logger.verbose("True match customer updates. (matchCustomerBuyerUserID)");
					strCustomerID = elePersonInfo.getAttribute("CustomerID");
					logger.verbose("strCustomerID (matchCustomerBuyerUserID method): " + strCustomerID);
					logger.verbose("elePersonInfo (matchCustomerBuyerUserID method Before ManageCustomer): " + XMLUtil.getXMLString(elePersonInfo));
					/* If Customer Needs Updating, call manage customer first */
					strUpdateType = elePersonInfo.getAttribute("UpdateType");
					logger.verbose("strUpdateType (matchCustomerBuyerUserID method): " + strUpdateType);
					if (strUpdateType != "NONE") {
						Document docCustomerXML = getManageCustomerXML(strEnterpriseCode, inputDoc, strUpdateType, elePersonInfo);
						logger.verbose("docNewCustomerXML (matchCustomerBuyerUserID method): " + XMLUtil.getXMLString(docCustomerXML));
						Element eleCustomerOut = callManageCustomer(docCustomerXML);	
						/* Using Full Match because we should have one after updating the customer */
						elePersonInfo = CheckFullMatch(eleCustomerOut, elePersonInfo, strFirstName, strLastName,  
								strEMailID, strAddressLine1, strCity, strZipCode, strBuyerUserID);
						logger.verbose("elePersonInfo (matchCustomerBuyerUserID method After ManageCustomer): " + XMLUtil.getXMLString(elePersonInfo));
					}
					inputDoc = updateInputXML(inputDoc, elePersonInfo);
				}

				
				/* If no matches then must be new User ID so then can check addresses without User ID*/
				if (strMatch == "N") {
					logger.verbose("No match with UserID so calling matchCustomerNoBuyerUserID.");
					matchCustomerNoBuyerUserID(inputDoc);	
				}
			} else {
				logger.verbose("False - address variables not set so will NOT try to match address. (matchCustomerBuyerUserID)");
			}
		}
		
		return inputDoc;
	}
	/* No BuyerUserID variant matching on customer address */
	private Document matchCustomerNoBuyerUserID(Document inputDoc) throws Exception {
		logger.verbose("matchCustomerNoBuyerUserID method");
		Document docMethod = XMLUtil.getDocument("<PersonInfo></PersonInfo>");
		Element elePersonInfo = (Element)(xpathUtil.getNode(docMethod, "PersonInfo"));
		elePersonInfo.setAttribute("MatchedPerson", "N");
		elePersonInfo.setAttribute("UpdateType", "NONE");
		String strMatch = elePersonInfo.getAttribute("MatchedPerson");
		String strUpdateType = elePersonInfo.getAttribute("UpdateType");
		String strCustomerID = "";
		String strEnterpriseCode="";
		String strBuyerUserID="";
		String strFirstName = "";
		String strLastName = "";
		String strEMailID = "";
		String strAddressLine1 = "";
		String strCity = "";
		String strZipCode = "";
		String strCustomerMasterOrganizationCode = "";
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			for (int i = 0; i < 1; i++) {
				Element elOrderXML = (Element)nlOrder.item(i);
				strEnterpriseCode = elOrderXML.getAttribute("EnterpriseCode");
				Element eleOrganisation = getOrganisationAttr(strEnterpriseCode);
				strCustomerMasterOrganizationCode = eleOrganisation.getAttribute("CustomerMasterOrganizationCode");
				strBuyerUserID = elOrderXML.getAttribute("BuyerUserId");
				strFirstName = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@FirstName");
				strLastName = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@LastName");
				strEMailID = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@EMailID");
				strAddressLine1 = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@AddressLine1");
				strCity = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@City");
				strZipCode = xpathUtil.getValue(elOrderXML,"PersonInfoBillTo/@ZipCode");
				logger.verbose("strEnterpriseCode: " + strEnterpriseCode);
				logger.verbose("strBuyerUserID: " + strBuyerUserID);
				logger.verbose("strFirstName: " + strFirstName);
				logger.verbose("strLastName: " + strLastName);
				logger.verbose("strEMailID: " + strEMailID);
				logger.verbose("strAddressLine1: " + strAddressLine1);
				logger.verbose("strCity: " + strCity);
				logger.verbose("strZipCode: " + strZipCode);
			}
			if (strEnterpriseCode != null && strEnterpriseCode != "" && strFirstName != null && strFirstName != "" && 
					strLastName != null && strLastName != "" && strEMailID != null && strEMailID != "" && 
					strAddressLine1 != null && strAddressLine1 != "" && strCity != null && strCity != "" && strZipCode != null && strZipCode != "") {
				logger.verbose("True - address variables set so will try to match address. (matchCustomerNoBuyerUserID)");
				Element eleBillToInfo = (Element)xpathUtil.getNode(inputDoc, "/Order/PersonInfoBillTo");
				logger.verbose("eleBillToInfo: " + XMLUtil.getXMLString(eleBillToInfo));
				/* Setting Match Element for Customer List for PersonInfo - First trying first name, last name, Email and Zip Code 
				 * Exact Match so no ManageCustomer Updates unless BuyerUserID is populated and different*/
				logger.verbose("Trying to match customer by First Name, Last Name, Email and Zip Code. (matchCustomerNoBuyerUserID)");
				String strMatchXML = "<PersonInfo FirstName='%s' LastName='%s' EMailID='%s' ZipCode='%s'/>";
				strMatchXML = String.format(strMatchXML, strFirstName, strLastName, strEMailID, strZipCode);
				Document docCustomerList = getCustomerListNoBuyerUserID(strCustomerMasterOrganizationCode, strMatchXML);
				elePersonInfo = matchAddressAttributes(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
				strMatch = elePersonInfo.getAttribute("MatchedPerson");
				logger.verbose("strMatch: " + strMatch);
				if (strMatch == "Y") {
					logger.verbose("True match customer using First Name, Last Name, Email and Zip Code. (matchCustomerNoBuyerUserID)");
				}
				/* Setting Match Element for Customer List for PersonInfo - Trying FirstName, LastName, and ZipCode
				 * A match will indicate an email update */
				if (strMatch == "N") {
					logger.verbose("Trying to match customer by FirstName, LastName, and ZipCode. (matchCustomerNoBuyerUserID)");
					strMatchXML = "<PersonInfo FirstName='%s' LastName='%s' ZipCode='%s'/>";
					strMatchXML = String.format(strMatchXML, strFirstName, strLastName,  strZipCode);
					docCustomerList = getCustomerListNoBuyerUserID(strCustomerMasterOrganizationCode, strMatchXML);
					elePersonInfo = matchAddressAttributes(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
					strMatch = elePersonInfo.getAttribute("MatchedPerson");
					logger.verbose("strMatch: " + strMatch);
					if (strMatch == "Y") {
						logger.verbose("True match customer using FirstName, LastName, and ZipCode. (matchCustomerNoBuyerUserID)");
					}
				}
				/* Setting Match Element for Customer List for PersonInfo - Trying FirstName, LastName, and Email
				 * A match will indicate address needs adding */
				if (strMatch == "N") {
					logger.verbose("Trying to match customer by FirstName, LastName, and Email. (matchCustomerNoBuyerUserID)");
					strMatchXML = "<PersonInfo FirstName='%s' LastName='%s' EMailID='%s'/>";
					strMatchXML = String.format(strMatchXML, strFirstName, strLastName,  strEMailID);
					docCustomerList = getCustomerListNoBuyerUserID(strCustomerMasterOrganizationCode, strMatchXML);
					elePersonInfo = matchAddressAttributes(strEnterpriseCode, strBuyerUserID, strFirstName, strLastName, strEMailID, strAddressLine1, strCity, strZipCode, docCustomerList, elePersonInfo);
					strMatch = elePersonInfo.getAttribute("MatchedPerson");
					logger.verbose("strMatch: " + strMatch);
					if (strMatch == "Y") {
						logger.verbose("True match customer using FirstName, LastName, and Email. (matchCustomerNoBuyerUserID)");
					}
				}
				
				/* If Match Updates */
				if (strMatch == "Y") {
					logger.verbose("True match customer updates. (matchCustomerNoBuyerUserID)");
					strCustomerID = elePersonInfo.getAttribute("CustomerID");
					logger.verbose("strCustomerID (matchCustomerNoBuyerUserID method): " + strCustomerID);
					logger.verbose("elePersonInfo(matchCustomerNoBuyerUserID method Before ManageCustomer): " + XMLUtil.getXMLString(elePersonInfo));
					/* If Customer Needs Updating, call manage customer first */
					strUpdateType = elePersonInfo.getAttribute("UpdateType");
					logger.verbose("strUpdateType (matchCustomerNoBuyerUserID method): " + strUpdateType);
					if (strUpdateType != "NONE") {
						Document docCustomerXML = getManageCustomerXML(strEnterpriseCode, inputDoc, strUpdateType, elePersonInfo);
						logger.verbose("docNewCustomerXML (matchCustomerNoBuyerUserID method): " + XMLUtil.getXMLString(docCustomerXML));
						Element eleCustomerOut = callManageCustomer(docCustomerXML);	
						/* Using Full Match because we should have one after updating the customer */
						elePersonInfo = CheckFullMatch(eleCustomerOut, elePersonInfo, strFirstName, strLastName,  
								strEMailID, strAddressLine1, strCity, strZipCode, strBuyerUserID);
						logger.verbose("elePersonInfo (matchCustomerNoBuyerUserID method After ManageCustomer): " + XMLUtil.getXMLString(elePersonInfo));
					}
					inputDoc = updateInputXML(inputDoc, elePersonInfo);
				}
				
				/* If no matches then must be new */
				if (strMatch == "N") {
					logger.verbose("No matches so will need to create");
					strUpdateType = "NEW";
					Document docCustomerXML = getManageCustomerXML(strEnterpriseCode, inputDoc, strUpdateType, eleBillToInfo);
					logger.verbose("docCustomerXML (matchCustomer method): " + XMLUtil.getXMLString(docCustomerXML));
					/* Create Customer */
					Element eleCustomerOut = callManageCustomer(docCustomerXML);	
					/* Using Full Match because we should have one after updating the customer */
					elePersonInfo = CheckFullMatch(eleCustomerOut, elePersonInfo, strFirstName, strLastName,  
							strEMailID, strAddressLine1, strCity, strZipCode, strBuyerUserID);
					strMatch = elePersonInfo.getAttribute("MatchedPerson");
					logger.verbose("strMatch: " + strMatch);
					if (strMatch == "Y") {
						logger.verbose("Customer Created. So matched Person set to Y. strMatch: " + strMatch);
						inputDoc = updateInputXML(inputDoc, elePersonInfo);
					}		
				}
			} else {
				logger.verbose("False - address variables not set so will NOT try to match address.");
			}
		}
		
		return inputDoc;
	}
	
	/* Get Customer List - As there is no BuyerUserID we use the address fields*/
	private Document getCustomerListNoBuyerUserID (String strEnterpriseCode, String strMatchXML) throws Exception {
		logger.verbose("getCustomerListNoBuyerUserID method");
		String outputCustomerTemplate= "<CustomerList>" +
										GetCustomerOutputTemplate() +
										"</CustomerList>";
		String inputCustomerXML = "<Customer OrganizationCode='%s'>" +
									"<CustomerContactList>" +
									"<CustomerContact>" +
									"<CustomerAdditionalAddressList>" +
									"<CustomerAdditionalAddress>" +
									strMatchXML +
									"</CustomerAdditionalAddress>" +
									"</CustomerAdditionalAddressList>" +
									"</CustomerContact>" +
									"</CustomerContactList>" +
									"</Customer>";
		Document docInputCustomerXML = XMLUtil.getDocument(String.format(inputCustomerXML, strEnterpriseCode));
		env.setApiTemplate("getCustomerList",  XMLUtil.getDocument(outputCustomerTemplate));
		logger.verbose("docInputCustomerXML: " + XMLUtil.getXMLString(docInputCustomerXML));		
		Document docCustomerList = api.getCustomerList(env, docInputCustomerXML);
		env.clearApiTemplate("getCustomerList");
		logger.verbose("docCustomerList: " + XMLUtil.getXMLString(docCustomerList));
		return docCustomerList;
	}
	
	/* Get Customer List Using just the User ID*/
	private Document getCustomerListBuyerUserID (String strEnterpriseCode, String strBuyerUserID, String strMatchXML) throws Exception {
		logger.verbose("getCustomerListBuyerUserID method");
		String outputCustomerTemplate= "<CustomerList>" +
										GetCustomerOutputTemplate() +
										"</CustomerList>";
		String inputCustomerXML = "<Customer OrganizationCode='%s'>" +
									"<CustomerContactList>" +
									"<CustomerContact UserID='%s'>" +
									"<CustomerAdditionalAddressList>" +
									"<CustomerAdditionalAddress>" +
									strMatchXML +
									"</CustomerAdditionalAddress>" +
									"</CustomerAdditionalAddressList>" +
									"</CustomerContact>" +
									"</CustomerContactList>" +
									"</Customer>";
		Document docInputCustomerXML = XMLUtil.getDocument(String.format(inputCustomerXML, strEnterpriseCode, strBuyerUserID));
		env.setApiTemplate("getCustomerList",  XMLUtil.getDocument(outputCustomerTemplate));
		logger.verbose("docInputCustomerXML: " + XMLUtil.getXMLString(docInputCustomerXML));		
		Document docCustomerList = api.getCustomerList(env, docInputCustomerXML);
		env.clearApiTemplate("getCustomerList");
		logger.verbose("docCustomerList: " + XMLUtil.getXMLString(docCustomerList));
		return docCustomerList;
	}
	
	/* Set Customer Output Template */
	private String GetCustomerOutputTemplate () throws Exception {
		logger.verbose("GetCustomerOutputTemplate method");
		String outputCustomerTemplate = "<Customer CustomerID='' CustomerKey='' CustomerType='' OrganizationCode=''>" +
									"<CustomerContactList>" +
									"<CustomerContact CustomerContactID='' CustomerContactKey='' Title='' " +
									"FirstName='' MiddleName='' LastName='' EmailID='' UserID='' " +
									"DayPhone='' EveningPhone='' MobilePhone='' Status=''>" +										
									"<CustomerAdditionalAddressList>" +
									"<CustomerAdditionalAddress CustomerAdditionalAddressID='' isDefaultBillTo='' isDefaultShipTo=''>" +
									"<PersonInfo AddressID='' AddressLine1='' AddressLine2='' AddressLine3='' AddressLine4='' AddressLine5='' AddressLine6='' " +
									"AlternateEmailID='' Beeper='' City='' Company='' Country='' DayFaxNo='' DayPhone='' Department='' EMailID='' EveningFaxNo='' " +
									"EveningPhone='' FirstName='' IsAddressVerified='' IsCommercialAddress='' JobTitle='' LastName='' Latitude='' Longitude='' " +
									"MiddleName='' MobilePhone='' OtherPhone='' PersonID='' PersonInfoKey='' State='' Suffix='' TaxGeoCode='' Title='' ZipCode='' />" +
									"</CustomerAdditionalAddress>" +
									"</CustomerAdditionalAddressList>" +
									"<User DisplayUserID='' LoginID='' Password='' Localecode=''/>" +
									"</CustomerContact>" +
									"</CustomerContactList>" +
									"</Customer>";
		return outputCustomerTemplate;
	}

	/* Create ManageCustomer XML */
	private Document getManageCustomerXML(String strEnterpriseCode, Document inputDoc, String strUpdateType, Element elPersonXML)
		throws Exception {
		logger.verbose("getManageCustomerXML method");
		/* Checking whether user ID info is required */
		Element eleOrder = (Element)xpathUtil.getNode(inputDoc, "/Order");
		String strBuyerUserID = eleOrder.getAttribute("BuyerUserId");
		String strUserID = elPersonXML.getAttribute("UserID");
		String strSendUserIDInfo = "N";
		Element eleOrganisation = getOrganisationAttr(strEnterpriseCode);
		String strLocale = eleOrganisation.getAttribute("LocaleCode");
		String strCustomerMasterOrganizationCode = eleOrganisation.getAttribute("CustomerMasterOrganizationCode");
		if (strBuyerUserID.equalsIgnoreCase(strUserID)) {
			logger.verbose("BuyerUserID and UserID match no need to send.");
		} else {
			logger.verbose("BuyerUserID and UserID do not match.");
			strSendUserIDInfo = "Y";
		}
		if (strUpdateType == "UPDATEUSERID") {
			logger.verbose("strUpdateType means UserID needs setting. strUpdateType: " + strUpdateType);
			strSendUserIDInfo = "Y";
		}
		/* Forming XML */
		String strInputXML = "<Customer OrganizationCode='%s' CustomerType='02' CustomerID='%s'>" +
							"<CustomerContactList>" +
							"<CustomerContact CustomerContactKey='%s' ";
		if (strUpdateType == "NEW") {
			strInputXML = strInputXML + "CustomerContactID='%s' Title='%s' FirstName='%s' MiddleName='%s' LastName='%s' " +
							"EmailID='%s' DayPhone='%s' EveningPhone='%s' MobilePhone='%s' Status='10' ";
		}
		if (strUpdateType == "UPDATEEMAIL") {
			strInputXML = strInputXML + "CustomerContactID='%s' EmailID='%s' ";
		}
		if (strSendUserIDInfo == "Y") {
			strInputXML = strInputXML + "UserID='%s' ";
		}
		strInputXML = strInputXML + ">";
		if (strUpdateType != "UPDATEUSERID") {
			strInputXML = strInputXML + "<CustomerAdditionalAddressList>" +
							"<CustomerAdditionalAddress CustomerAdditionalAddressID='%s' ";
			if (strUpdateType == "NEW" || strUpdateType == "ADDADDRESS") {
				strInputXML = strInputXML + "IsBillTo='Y' IsDefaultBillTo='%s' " +
							"IsDefaultShipTo='%s' IsDefaultSoldTo='%s' IsShipTo='Y' IsSoldTo='Y' ";
			}
		
			strInputXML = strInputXML + ">" +
							"<PersonInfo />" +
							"</CustomerAdditionalAddress>" +
							"</CustomerAdditionalAddressList>";
		}
		if (strSendUserIDInfo == "Y") {
				strInputXML = strInputXML + "<User DisplayUserID='%s' LoginID='%s' Password='%s' Localecode='%s'/>";
		};							
		strInputXML = strInputXML + "</CustomerContact>" +
							"</CustomerContactList>" +
							"</Customer>";
		String strDefault = "";
		String strCustomerID = "";
		String strCustomerContactID = "";
		String strTitle = "";
		String strFirstName = "";
		String strMiddleName = "";
		String strLastName = "";
		String strEMailID = "";
		String strDayPhone = "";
		String strEveningPhone = "";
		String strMobilePhone = "";
		String strCustomerAdditionalAddressID = "";
		String strCustomerContactKey = "";
		Element eleBillToInfo = (Element)xpathUtil.getNode(inputDoc, "/Order/PersonInfoBillTo");
		Node nodNew;
		String strNodeName = "PersonInfo";
		String strBlank = "";
		logger.verbose("eleBillToInfo: " + XMLUtil.getXMLString(eleBillToInfo));
		logger.verbose("strNodeName: " + strNodeName);
		logger.verbose("strBlank: " + strBlank);
		/* New Customer Contact XML comes from BillTo Info */
		if (strUpdateType == "NEW"){
			logger.verbose("True update type NEW. strUpdateType: " + strUpdateType);
			strDefault = "Y";			
			strTitle = eleBillToInfo.getAttribute("Title");
			strFirstName = eleBillToInfo.getAttribute("FirstName");
			strMiddleName = eleBillToInfo.getAttribute("MiddleName");
			strLastName = eleBillToInfo.getAttribute("LastName");
			strEMailID = eleBillToInfo.getAttribute("EMailID");
			strDayPhone = eleBillToInfo.getAttribute("DayPhone");
			strEveningPhone = eleBillToInfo.getAttribute("EveningPhone");
			strMobilePhone = eleBillToInfo.getAttribute("MobilePhone");
			if (strBuyerUserID != null && strBuyerUserID != "") {
				logger.verbose("True for strBuyerUserID in Order XML. strBuyerUserID: " + strBuyerUserID);
				strUserID = strBuyerUserID;
			}
		}
		/* Update Customer Contact XML comes from Customer Info */
		if (strUpdateType == "ADDADDRESS" || strUpdateType == "UPDATEEMAIL" || strUpdateType == "UPDATEUSERID"){
			logger.verbose("True update type UPDATE. strUpdateType: " + strUpdateType);
			strDefault = "N";
			strCustomerID = elPersonXML.getAttribute("CustomerID");
			strCustomerContactKey = elPersonXML.getAttribute("CustomerContactKey");
			strCustomerAdditionalAddressID = "";
			strCustomerContactID = elPersonXML.getAttribute("CustomerContactID");
			strEMailID = elPersonXML.getAttribute("EMailID");
			if (strBuyerUserID != null && strBuyerUserID != "") {
				logger.verbose("True for strBuyerUserID in Order XML. strBuyerUserID: " + strBuyerUserID);
				strUserID = strBuyerUserID;
			}
		}
		/* If an email update, email must come from PersonInfoBillTo */
		if (strUpdateType == "UPDATEEMAIL"){
			strEMailID = eleBillToInfo.getAttribute("EMailID");
			strCustomerAdditionalAddressID = elPersonXML.getAttribute("CustomerAdditionalAddressID");
		}
		/* Listing Variables and replacing on String */
		logger.verbose("strDefault: " + strDefault);
		logger.verbose("strCustomerID: " + strCustomerID);
		logger.verbose("strCustomerContactKey: " + strCustomerContactKey);
		logger.verbose("strCustomerAdditionalAddressID: " + strCustomerAdditionalAddressID);
		logger.verbose("strCustomerContactID: " + strCustomerContactID);
		logger.verbose("strTitle: " + strTitle);
		logger.verbose("strFirstName: " + strFirstName);
		logger.verbose("strMiddleName: " + strMiddleName);
		logger.verbose("strLastName: " + strLastName);
		logger.verbose("strEMailID: " + strEMailID);
		logger.verbose("strUserID: " + strUserID);
		logger.verbose("strDayPhone: " + strDayPhone);
		logger.verbose("strEveningPhone: " + strEveningPhone);
		logger.verbose("strMobilePhone: " + strDayPhone);
		if (strUpdateType == "NEW" && strSendUserIDInfo == "N") {
			logger.verbose("Formatting strInputXML for NEW update. strSendUserIDInfo == N");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strCustomerContactID, strTitle, strFirstName, 
					strMiddleName, strLastName, strEMailID, strDayPhone, strEveningPhone, strMobilePhone, strCustomerAdditionalAddressID, strDefault, strDefault, strDefault);
		}
		if (strUpdateType == "NEW" && strSendUserIDInfo == "Y") {
			logger.verbose("Formatting strInputXML for NEW update. strSendUserIDInfo == Y");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strCustomerContactID, strTitle, strFirstName, 
					strMiddleName, strLastName, strEMailID, strDayPhone, strEveningPhone, strMobilePhone, strUserID, strCustomerAdditionalAddressID, strDefault, strDefault, strDefault, strBuyerUserID, strBuyerUserID, strBuyerUserID, strLocale);
		}
		if (strUpdateType == "ADDADDRESS" && strSendUserIDInfo == "N") {
			logger.verbose("Formatting strInputXML for ADDADDRESS update. strSendUserIDInfo == N");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strCustomerAdditionalAddressID, strDefault, strDefault, strDefault);
		}
		if (strUpdateType == "ADDADDRESS" && strSendUserIDInfo == "Y") {
			logger.verbose("Formatting strInputXML for ADDADDRESS update. strSendUserIDInfo == Y");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strUserID, strCustomerAdditionalAddressID, strDefault, strDefault, strDefault, strBuyerUserID, strBuyerUserID, strBuyerUserID, strLocale);
		}
		if (strUpdateType == "UPDATEEMAIL" && strSendUserIDInfo == "N") {
			logger.verbose("Formatting strInputXML for UPDATEEMAIL update. strSendUserIDInfo == N");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strCustomerContactID, strEMailID, strCustomerAdditionalAddressID);
		}
		if (strUpdateType == "UPDATEEMAIL" && strSendUserIDInfo == "Y") {
			logger.verbose("Formatting strInputXML for UPDATEEMAIL update. strSendUserIDInfo == Y");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strCustomerContactID, strEMailID, strUserID, strCustomerAdditionalAddressID, strBuyerUserID, strBuyerUserID, strBuyerUserID, strLocale);
		}
		if (strUpdateType == "UPDATEUSERID") {
			logger.verbose("Formatting strInputXML for UPDATEUSERID update.");
			strInputXML = String.format(strInputXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strBuyerUserID, strBuyerUserID, strBuyerUserID, strBuyerUserID, strLocale);
		}
		logger.verbose("strInputXML: " + strInputXML);
		/* Renaming and appending BillTo Info and PersonInfo */
		Document docNewCustomerXML = XMLUtil.getDocument(strInputXML);
		logger.verbose("docNewCustomerXML: " + XMLUtil.getXMLString(docNewCustomerXML));
		/* Only set Person for Non-UserID only updates */
		if (strUpdateType != "UPDATEUSERID") {
			logger.verbose("True Update Type is not equal to UPDATEUSERID so person info is sent.");
			Node nodPersonInfo = xpathUtil.getNode(docNewCustomerXML, "Customer/CustomerContactList/CustomerContact/CustomerAdditionalAddressList/CustomerAdditionalAddress");
			logger.verbose("nodPersonInfo: " + XMLUtil.getXMLString(nodPersonInfo));
			/* Only use PersonInfoBillTo for New or Adding an Address 
			 * This means extra Contact Info like phone may not be passed */
			if (strUpdateType == "NEW" || strUpdateType == "ADDADDRESS") {
				Document docTemp = XMLUtil.getDocumentForElement(eleBillToInfo);
				logger.verbose("docTemp: " + XMLUtil.getXMLString(docTemp));
				Node nodPersonInfoBillTo = xpathUtil.getNode(docTemp, "PersonInfoBillTo");
				logger.verbose("Before rename. nodPersonInfoBillTo: " + XMLUtil.getXMLString(nodPersonInfoBillTo));
				nodNew = docTemp.renameNode(nodPersonInfoBillTo, null, strNodeName);
			logger.verbose("After rename. nodPersonInfoBillTo: " + XMLUtil.getXMLString(nodPersonInfoBillTo));
			} else {
				if (strUpdateType == "UPDATEEMAIL") {
					logger.verbose("Updating Email as update type is UPDATEEMAIL.");
					elPersonXML.setAttribute("EMailID", strEMailID);
			}
			Document docTemp = XMLUtil.getDocumentForElement(elPersonXML);
			logger.verbose("docTemp: " + XMLUtil.getXMLString(docTemp));
			nodNew = xpathUtil.getNode(docTemp, "PersonInfo");
		}
		logger.verbose("nodNew: " + XMLUtil.getXMLString(nodNew));
				
		docNewCustomerXML =	AppendNode(docNewCustomerXML, nodPersonInfo, nodNew, strNodeName);
		}
		logger.verbose("docNewCustomerXML: " + XMLUtil.getXMLString(docNewCustomerXML));
		return docNewCustomerXML;
	}
	
	/* Call Manage Customer */
	private Element callManageCustomer(Document docInputCustomerXML) throws Exception {
		logger.verbose("callManageCustomer method");
		String strManageCustomerOutTemplate = GetCustomerOutputTemplate();
		env.setApiTemplate("manageCustomer",  XMLUtil.getDocument(strManageCustomerOutTemplate));
		logger.verbose("docInputCustomerXML: " + XMLUtil.getXMLString(docInputCustomerXML));		
		Document docCustomer = api.manageCustomer(env, docInputCustomerXML);
		env.clearApiTemplate("manageCustomer");
		logger.verbose("docCustomer: " + XMLUtil.getXMLString(docCustomer));
		Node nodCustomerOut = xpathUtil.getNode(docCustomer, "Customer");
		Element eleCustomerOut = (Element)nodCustomerOut;
		logger.verbose("eleCustomerOut (ManageCustomer Method): " + XMLUtil.getXMLString(eleCustomerOut));
		return eleCustomerOut;
	}
	
	/* Match UserID check only */
	private Element matchUserIDOnly(String strEnterpriseCode, String strBuyerUserID, String strInputFirstName, String strInputLastName, String strInputEMailID, 
			String strInputAddressLine1, String strInputCity, String strInputZipCode, Document docCustomerList, Element elePersonInfo) throws Exception {
		logger.verbose("matchUserIDOnly method");
		String strMatch = "N";
		/* Iterate Customer List looking for a match */
		NodeList nlCustomer = xpathUtil.getNodeList(docCustomerList, "/CustomerList/Customer");
		if (nlCustomer != null && nlCustomer.getLength() > 0) {
			logger.verbose("Loop for Customer Records. ");
			for (int i = 0; i < nlCustomer.getLength(); i++) {
				logger.verbose("Comparing Customer Record to Order XML.");
				Element elCustomerXML = (Element)nlCustomer.item(i);							
				/* Checking Match against BuyerUserID to get right person 
				 * If match ie User ID is present, means Address needs inserting
				 */
				elePersonInfo = CheckUserID(elCustomerXML, elePersonInfo, strInputFirstName, strInputLastName,  
						strInputEMailID, strInputAddressLine1, strInputCity, strInputZipCode, strBuyerUserID);
				/* If matched End Loop */
				strMatch = elePersonInfo.getAttribute("MatchedUserID");
				if (strMatch != null && strMatch != "N") {
					logger.verbose("strMatch is not null or Not Matched. strMatch: " + strMatch);
					logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
					elePersonInfo.setAttribute("UpdateType", "ADDADDRESS");
					logger.verbose("Ending Loop due to Match.");
					break;				
				}
				strMatch = "N";	
				logger.verbose("No Match found.");
				/* Will return MatchedPerson attribute as N so can try different address components 
				 * before deciding whether to create customer */
			}			
		} else {
			logger.verbose("No Customer List found.");
			/* Will return MatchedPerson attribute as N so can try different address components 
			 * before deciding whether to create customer */
		}
		return elePersonInfo;
	}
	
	/* Match Name and Address */
	private Element matchAddressAttributes(String strEnterpriseCode, String strBuyerUserID, String strInputFirstName, String strInputLastName, String strInputEMailID, 
			String strInputAddressLine1, String strInputCity, String strInputZipCode, Document docCustomerList, Element elePersonInfo) throws Exception {
		logger.verbose("matchAddressAttributes method");
		String strMatch = "N";
		/* Iterate Customer List looking for a match */
		NodeList nlCustomer = xpathUtil.getNodeList(docCustomerList, "/CustomerList/Customer");
		if (nlCustomer != null && nlCustomer.getLength() > 0) {
			logger.verbose("Loop for Customer Records. ");
			for (int i = 0; i < nlCustomer.getLength(); i++) {
				logger.verbose("Comparing Customer Record to Order XML. (matchAddressAttributes method)");
				Element elCustomerXML = (Element)nlCustomer.item(i);	
				logger.verbose("elCustomerXML: (matchAddressAttributes method)" + XMLUtil.getXMLString(elCustomerXML));
				/* Checking Match against First Name, Last Name, Email, Address 1, City and Zip Code */
				elePersonInfo = CheckFullMatch(elCustomerXML, elePersonInfo, strInputFirstName, strInputLastName,  
									strInputEMailID, strInputAddressLine1, strInputCity, strInputZipCode, strBuyerUserID);
				/* If matched End Loop */
				strMatch = elePersonInfo.getAttribute("MatchedPerson");
				if (strMatch != null && strMatch != "N") {
					logger.verbose("strMatch is not null or Not Matched. strMatch: " + strMatch);
					/* Need to check whether User ID needs updating */
					String strUserID = elePersonInfo.getAttribute("UserID");
					if (strUserID != null && strUserID != "") {
						logger.verbose("strUserID is not null so not not update type is NONE. strUserID: " + strUserID);
						elePersonInfo.setAttribute("UpdateType", "NONE");
					} else {
						logger.verbose("strUserID is null or not set.");
						if (strBuyerUserID != null && strBuyerUserID != "") {
							logger.verbose("strBuyerUserID is set so update type is UPDATEUSERID.");
							elePersonInfo.setAttribute("UpdateType", "UPDATEUSERID");
						} else {
							logger.verbose("strBuyerUserID is not set so update type is NONE.");
							elePersonInfo.setAttribute("UpdateType", "NONE");
						}
					}
					logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
					logger.verbose("Ending Loop due to Match. (matchAddressAttributes method)");
					break;				
				}
				/* Checking Match against First Name, Last Name, Address 1, City and Zip Code 
				 * If match, means Email needs updating
				 */
				elePersonInfo = CheckMatchExEmail(elCustomerXML, elePersonInfo, strInputFirstName, strInputLastName,  
						strInputEMailID, strInputAddressLine1, strInputCity, strInputZipCode, strBuyerUserID);
				/* If matched End Loop */
				strMatch = elePersonInfo.getAttribute("MatchedPerson");
				if (strMatch != null && strMatch != "N") {
					logger.verbose("strMatch is not null or Not Matched. strMatch: " + strMatch);
					elePersonInfo.setAttribute("UpdateType", "UPDATEEMAIL");
					logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
					logger.verbose("Ending Loop due to Match. (matchAddressAttributes method)");
					break;				
				}
				/* Checking Match against First Name, Last Name, and Email 
				 * If match, means Address needs inserting
				 */
				elePersonInfo = CheckMatchExAddress(elCustomerXML, elePersonInfo, strInputFirstName, strInputLastName,  
						strInputEMailID, strInputAddressLine1, strInputCity, strInputZipCode, strBuyerUserID);
				/* If matched End Loop */
				strMatch = elePersonInfo.getAttribute("MatchedPerson");
				if (strMatch != null && strMatch != "N") {
					logger.verbose("strMatch is not null or Not Matched. strMatch: " + strMatch);
					elePersonInfo.setAttribute("UpdateType", "ADDADDRESS");
					logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
					logger.verbose("Ending Loop due to Match. (matchAddressAttributes method)");
					break;				
				}
				strMatch = "N";	
				logger.verbose("No Match found.");
				/* Will return MatchedPerson attribute as N so can try different address components 
				 * before deciding whether to create customer */
			}			
		} else {
			logger.verbose("No Customer List found.");
			/* Will return MatchedPerson attribute as N so can try different address components 
			 * before deciding whether to create customer */
		}
		return elePersonInfo;
	}
	
	/* Checking Match against First Name, Last Name, Email, Address 1, City and Zip Code */
	private Element CheckFullMatch(Element elCustomerXML, Element elePersonInfo, String strInputFirstName, String strInputLastName, 
				String strInputEMailID, String strInputAddressLine1, String strInputCity, String strInputZipCode, String strBuyerUserID) 
				throws Exception {
		logger.verbose("CheckFullMatch method");
		/* Get Values */
		String strCustomerID = elCustomerXML.getAttribute("CustomerID");
		String strCustomerContactID = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@CustomerContactID");
		String strCustomerContactKey = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@CustomerContactKey");		
		String strUserID = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@UserID");		
		NodeList nlAddressInfo = xpathUtil.getNodeList(elCustomerXML, "CustomerContactList/CustomerContact/CustomerAdditionalAddressList/CustomerAdditionalAddress");
		if (nlAddressInfo != null && nlAddressInfo.getLength() > 0) {
			for (int i = 0; i < nlAddressInfo.getLength(); i++) {
				logger.verbose("Loop CheckFullMatch Method.");
				Element elAddressInfo = (Element)nlAddressInfo.item(i);	
				String strCustomerAdditionalAddressID = elAddressInfo.getAttribute("CustomerAdditionalAddressID");
				Element elPersonInfo = (Element)xpathUtil.getNode(elAddressInfo, "PersonInfo");
				String strFirstName = elPersonInfo.getAttribute("FirstName");
				String strLastName = elPersonInfo.getAttribute("LastName");
				String strEMailID = elPersonInfo.getAttribute("EMailID");
				String strAddressLine1 = elPersonInfo.getAttribute("AddressLine1");
				String strCity = elPersonInfo.getAttribute("City");
				String strZipCode = elPersonInfo.getAttribute("ZipCode");
				logger.verbose("strCustomerID: " + strCustomerID);
				logger.verbose("strCustomerContactKey: " + strCustomerContactKey);
				logger.verbose("strUserID: " + strUserID);
				logger.verbose("strCustomerAdditionalAddressID: " + strCustomerAdditionalAddressID);
				logger.verbose("strFirstName: " + strFirstName + " | strInputFirstName: " + strInputFirstName);
				logger.verbose("strLastName: " + strLastName + " | strInputLastName: " + strInputLastName);
				logger.verbose("strEMailID: " + strEMailID + " | strInputEMailIDName: " + strInputEMailID);
				logger.verbose("strAddressLine1: " + strAddressLine1 + " | strInputAddressLine1: " + strInputAddressLine1);
				logger.verbose("strCity: " + strCity + " | strInputCity: " + strInputCity);
				logger.verbose("strZipCode: " + strZipCode + " | strInputZipCode: " + strInputZipCode);
				/* Check Full Match */
				if (strFirstName.equalsIgnoreCase(strInputFirstName) && strLastName.equalsIgnoreCase(strInputLastName) &&
						strEMailID.equalsIgnoreCase(strInputEMailID) && strAddressLine1.equalsIgnoreCase(strInputAddressLine1) &&
						strCity.equalsIgnoreCase(strInputCity) && strZipCode.equalsIgnoreCase(strInputZipCode)) {
					logger.verbose("All Attributes Match! CustomerID is: " + strCustomerID);			
					elePersonInfo = elPersonInfo;
					/*Adding Matched attribute */
					elePersonInfo.setAttribute("MatchedPerson", "Y");
					/* Adding BuyerUserID */
					elePersonInfo.setAttribute("BuyerUserId", strBuyerUserID);
					/*Adding CustomerID attribute */
					if (strCustomerID != null && strCustomerID != "") {
						logger.verbose("Customer ID is not null");						
						logger.verbose("Adding CustomerID to Element");
						elePersonInfo.setAttribute("CustomerID", strCustomerID);					
					}
					/*Adding CustomerContactID attribute */
					if (strCustomerContactID != null && strCustomerContactID != "") {
						logger.verbose("CustomerContactID is not null");						
						logger.verbose("Adding CustomerContactID to Element");
						elePersonInfo.setAttribute("CustomerContactID", strCustomerContactID);					
					}
					/*Adding CustomerContactKey attribute */
					if (strCustomerContactKey != null && strCustomerContactKey != "") {
						logger.verbose("CustomerContactKey is not null");						
						logger.verbose("Adding CustomerContactKey to Element");
						elePersonInfo.setAttribute("CustomerContactKey", strCustomerContactKey);					
					}
					/*Adding CustomerAdditionalAddressID attribute */
					if (strCustomerAdditionalAddressID != null && strCustomerAdditionalAddressID != "") {
						logger.verbose("CustomerAdditionalAddressID is not null");						
						logger.verbose("Adding CustomerAdditionalAddressID to Element");
						elePersonInfo.setAttribute("CustomerAdditionalAddressID", strCustomerAdditionalAddressID);					
					}
					/*Adding UserID attribute */
					if (strUserID != null && strUserID != "") {
						logger.verbose("strUserID is not null");						
						logger.verbose("Adding strUserID to Element");
						elePersonInfo.setAttribute("UserID", strUserID);					
					}
				}
			}
		}
		logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
		return elePersonInfo;
	}
	
	/* Checking Match against First Name, Last Name, Address 1, City and Zip Code 
	 * If match, means Email needs updating
	 */
	private Element CheckMatchExEmail(Element elCustomerXML, Element elePersonInfo, String strInputFirstName, String strInputLastName, 
				String strInputEMailID, String strInputAddressLine1, String strInputCity, String strInputZipCode, String strBuyerUserID) 
				throws Exception {
		logger.verbose("CheckMatchExEmail method");
		/* Get Values */
		String strCustomerID = elCustomerXML.getAttribute("CustomerID");
		String strCustomerContactID = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@CustomerContactID");
		String strCustomerContactKey = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@CustomerContactKey");
		String strUserID = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@UserID");	
		NodeList nlAddressInfo = xpathUtil.getNodeList(elCustomerXML, "CustomerContactList/CustomerContact/CustomerAdditionalAddressList/CustomerAdditionalAddress");
		if (nlAddressInfo != null && nlAddressInfo.getLength() > 0) {
			for (int i = 0; i < nlAddressInfo.getLength(); i++) {
				logger.verbose("Loop CheckMatchExEmail Method.");
				Element elAddressInfo = (Element)nlAddressInfo.item(i);	
				String strCustomerAdditionalAddressID = elAddressInfo.getAttribute("CustomerAdditionalAddressID");
				Element elPersonInfo = (Element)xpathUtil.getNode(elAddressInfo, "PersonInfo");
				String strFirstName = elPersonInfo.getAttribute("FirstName");
				String strLastName = elPersonInfo.getAttribute("LastName");
				String strEMailID = elPersonInfo.getAttribute("EMailID");
				String strAddressLine1 = elPersonInfo.getAttribute("AddressLine1");
				String strCity = elPersonInfo.getAttribute("City");
				String strZipCode = elPersonInfo.getAttribute("ZipCode");
				logger.verbose("strCustomerID: " + strCustomerID);
				logger.verbose("strCustomerContactKey: " + strCustomerContactKey);
				logger.verbose("strUserID: " + strUserID);
				logger.verbose("strCustomerAdditionalAddressID: " + strCustomerAdditionalAddressID);
				logger.verbose("strFirstName: " + strFirstName + " | strInputFirstName: " + strInputFirstName);
				logger.verbose("strLastName: " + strLastName + " | strInputLastName: " + strInputLastName);
				logger.verbose("strEMailID: " + strEMailID + " | strInputEMailIDName: " + strInputEMailID);
				logger.verbose("strAddressLine1: " + strAddressLine1 + " | strInputAddressLine1: " + strInputAddressLine1);
				logger.verbose("strCity: " + strCity + " | strInputCity: " + strInputCity);
				logger.verbose("strZipCode: " + strZipCode + " | strInputZipCode: " + strInputZipCode);
				/* Check Match Excluding Email */
				if (strFirstName.equalsIgnoreCase(strInputFirstName) && strLastName.equalsIgnoreCase(strInputLastName) &&
						strAddressLine1.equalsIgnoreCase(strInputAddressLine1) &&
						strCity.equalsIgnoreCase(strInputCity) && strZipCode.equalsIgnoreCase(strInputZipCode)) {
					logger.verbose("First Name, Last Name, Address, City, Zip Match! CustomerID is: " + strCustomerID);
					logger.verbose("Need to update email address");
					elePersonInfo = elPersonInfo;
					/*Adding Matched attribute */
					elePersonInfo.setAttribute("MatchedPerson", "Y");
					/* Updating Email */
					elePersonInfo.setAttribute("EMailID", strEMailID);
					/* Adding BuyerUserID */
					elePersonInfo.setAttribute("BuyerUserId", strBuyerUserID);
					/*Adding CustomerID attribute */
					if (strCustomerID != null && strCustomerID != "") {
						logger.verbose("Customer ID is not null");						
						logger.verbose("Adding CustomerID to Element");
						elePersonInfo.setAttribute("CustomerID", strCustomerID);					
					}
					/*Adding CustomerContactID attribute */
					if (strCustomerContactID != null && strCustomerContactID != "") {
						logger.verbose("CustomerContactID is not null");						
						logger.verbose("Adding CustomerContactID to Element");
						elePersonInfo.setAttribute("CustomerContactID", strCustomerContactID);					
					}
					/*Adding CustomerContactKey attribute */
					if (strCustomerContactKey != null && strCustomerContactKey != "") {
						logger.verbose("CustomerContactKey is not null");						
						logger.verbose("Adding CustomerContactKey to Element");
						elePersonInfo.setAttribute("CustomerContactKey", strCustomerContactKey);					
					}
					/*Adding CustomerAdditionalAddressID attribute */
					if (strCustomerAdditionalAddressID != null && strCustomerAdditionalAddressID != "") {
						logger.verbose("CustomerAdditionalAddressID is not null");						
						logger.verbose("Adding CustomerAdditionalAddressID to Element");
						elePersonInfo.setAttribute("CustomerAdditionalAddressID", strCustomerAdditionalAddressID);					
					}
					/*Adding UserID attribute */
					if (strUserID != null && strUserID != "") {
						logger.verbose("strUserID is not null");						
						logger.verbose("Adding strUserID to Element");
						elePersonInfo.setAttribute("UserID", strUserID);					
					}
				}
			}
		}
		logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
		return elePersonInfo;
	}

	/* Checking Match against First Name, Last Name, Email 
	 * If match, means Address needs inserting to customer addresses
	 */
	private Element CheckMatchExAddress(Element elCustomerXML, Element elePersonInfo, String strInputFirstName, String strInputLastName, 
				String strInputEMailID, String strInputAddressLine1, String strInputCity, String strInputZipCode, String strBuyerUserID) 
				throws Exception {
		logger.verbose("CheckMatchExAddress method");
		/* Get Values */
		String strCustomerID = elCustomerXML.getAttribute("CustomerID");
		String strCustomerContactID = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@CustomerContactID");
		String strCustomerContactKey = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@CustomerContactKey");
		String strUserID = xpathUtil.getValue(elCustomerXML,"CustomerContactList/CustomerContact/@UserID");	
		NodeList nlAddressInfo = xpathUtil.getNodeList(elCustomerXML, "CustomerContactList/CustomerContact/CustomerAdditionalAddressList/CustomerAdditionalAddress");
		if (nlAddressInfo != null && nlAddressInfo.getLength() > 0) {
			for (int i = 0; i < nlAddressInfo.getLength(); i++) {
				logger.verbose("Loop CheckMatchExAddress Method.");
				Element elAddressInfo = (Element)nlAddressInfo.item(i);	
				String strCustomerAdditionalAddressID = elAddressInfo.getAttribute("CustomerAdditionalAddressID");
				Element elPersonInfo = (Element)xpathUtil.getNode(elAddressInfo, "PersonInfo");
				String strFirstName = elPersonInfo.getAttribute("FirstName");
				String strLastName = elPersonInfo.getAttribute("LastName");
				String strEMailID = elPersonInfo.getAttribute("EMailID");
				String strAddressLine1 = elPersonInfo.getAttribute("AddressLine1");
				String strCity = elPersonInfo.getAttribute("City");
				String strZipCode = elPersonInfo.getAttribute("ZipCode");
				logger.verbose("strCustomerID: " + strCustomerID);
				logger.verbose("strCustomerContactKey: " + strCustomerContactKey);
				logger.verbose("strUserID: " + strUserID);
				logger.verbose("strCustomerAdditionalAddressID: " + strCustomerAdditionalAddressID);
				logger.verbose("strFirstName: " + strFirstName + " | strInputFirstName: " + strInputFirstName);
				logger.verbose("strLastName: " + strLastName + " | strInputLastName: " + strInputLastName);
				logger.verbose("strEMailID: " + strEMailID + " | strInputEMailIDName: " + strInputEMailID);
				logger.verbose("strAddressLine1: " + strAddressLine1 + " | strInputAddressLine1: " + strInputAddressLine1);
				logger.verbose("strCity: " + strCity + " | strInputCity: " + strInputCity);
				logger.verbose("strZipCode: " + strZipCode + " | strInputZipCode: " + strInputZipCode);
				/* Check Match Excluding Email */
				if (strFirstName.equalsIgnoreCase(strInputFirstName) && strLastName.equalsIgnoreCase(strInputLastName) &&
						strEMailID.equalsIgnoreCase(strInputEMailID)) {
					logger.verbose("First Name, Last Name, Email Match! CustomerID is: " + strCustomerID);
					logger.verbose("Need to insert address");
					elePersonInfo = elPersonInfo;
					/*Adding Matched attribute */
					elePersonInfo.setAttribute("MatchedPerson", "Y");
					/* Updating Email */
					elePersonInfo.setAttribute("EMailID", strEMailID);
					/* Adding BuyerUserID */
					elePersonInfo.setAttribute("BuyerUserId", strBuyerUserID);
					/*Adding CustomerID attribute */
					if (strCustomerID != null && strCustomerID != "") {
						logger.verbose("Customer ID is not null");						
						logger.verbose("Adding CustomerID to Element");
						elePersonInfo.setAttribute("CustomerID", strCustomerID);					
					}
					/*Adding CustomerContactID attribute */
					if (strCustomerContactID != null && strCustomerContactID != "") {
						logger.verbose("CustomerContactID is not null");						
						logger.verbose("Adding CustomerContactID to Element");
						elePersonInfo.setAttribute("CustomerContactID", strCustomerContactID);					
					}
					/*Adding CustomerContactKey attribute */
					if (strCustomerContactKey != null && strCustomerContactKey != "") {
						logger.verbose("CustomerContactKey is not null");						
						logger.verbose("Adding CustomerContactKey to Element");
						elePersonInfo.setAttribute("CustomerContactKey", strCustomerContactKey);					
					}
					/*Adding CustomerAdditionalAddressID attribute */
					if (strCustomerAdditionalAddressID != null && strCustomerAdditionalAddressID != "") {
						logger.verbose("CustomerAdditionalAddressID is not null");						
						logger.verbose("Adding CustomerAdditionalAddressID to Element");
						elePersonInfo.setAttribute("CustomerAdditionalAddressID", strCustomerAdditionalAddressID);					
					}
					/*Adding UserID attribute */
					if (strUserID != null && strUserID != "") {
						logger.verbose("strUserID is not null");						
						logger.verbose("Adding strUserID to Element");
						elePersonInfo.setAttribute("UserID", strUserID);					
					}
				}
			}
		}
		logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
		return elePersonInfo;
	}

	/* Checking Match against UserID 
	 * If match, means Address needs inserting to customer addresses
	 */
	private Element CheckUserID(Element elCustomerXML, Element elePersonInfo, String strInputFirstName, String strInputLastName, 
				String strInputEMailID, String strInputAddressLine1, String strInputCity, String strInputZipCode, String strBuyerUserID) 
				throws Exception {
		logger.verbose("CheckUserID method");
		/* Get Values */
		String strCustomerID = elCustomerXML.getAttribute("CustomerID");
		String strCustomerContactID = "";
		String strCustomerContactKey = "";
		String strUserID = "";
		NodeList nlContactInfo = xpathUtil.getNodeList(elCustomerXML, "CustomerContactList/CustomerContact");
		if (nlContactInfo != null && nlContactInfo.getLength() > 0) {
			for (int i = 0; i < nlContactInfo.getLength(); i++) {
				logger.verbose("Loop CheckUserID Method.");
				Element elContactInfo = (Element)nlContactInfo.item(i);	
				strCustomerContactID = elContactInfo.getAttribute("CustomerContactID");
				strCustomerContactKey = elContactInfo.getAttribute("CustomerContactKey");
				strUserID = elContactInfo.getAttribute("UserID");				
				logger.verbose("strCustomerContactID: " + strCustomerContactID);
				logger.verbose("strCustomerContactKey: " + strCustomerContactKey);
				logger.verbose("strUserID: " + strUserID);
				/* Check Match UserID */
				if (strUserID.equalsIgnoreCase(strBuyerUserID)) {
					logger.verbose("UserID Match! CustomerID is: " + strCustomerID);
					logger.verbose("Need to insert address details for the existing customer");
					/*Adding Matched attribute */
					elePersonInfo.setAttribute("MatchedUserID", "Y");
					/* Adding BuyerUserID */
					elePersonInfo.setAttribute("BuyerUserId", strBuyerUserID);
					/*Adding CustomerID attribute */
					if (strCustomerID != null && strCustomerID != "") {
						logger.verbose("Customer ID is not null");						
						logger.verbose("Adding CustomerID to Element");
						elePersonInfo.setAttribute("CustomerID", strCustomerID);					
					}
					/*Adding CustomerContactID attribute */
					if (strCustomerContactID != null && strCustomerContactID != "") {
						logger.verbose("CustomerContactID is not null");						
						logger.verbose("Adding CustomerContactID to Element");
						elePersonInfo.setAttribute("CustomerContactID", strCustomerContactID);					
					}
					/*Adding CustomerContactKey attribute */
					if (strCustomerContactKey != null && strCustomerContactKey != "") {
						logger.verbose("CustomerContactKey is not null");						
						logger.verbose("Adding CustomerContactKey to Element");
						elePersonInfo.setAttribute("CustomerContactKey", strCustomerContactKey);					
					}
					/*Adding UserID attribute */
					if (strUserID != null && strUserID != "") {
						logger.verbose("strUserID is not null");						
						logger.verbose("Adding strUserID to Element");
						elePersonInfo.setAttribute("UserID", strUserID);					
					}
				}
			}
		}
		logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
		return elePersonInfo;
	}


/* Building New XML */
private Document updateInputXML(Document inputDoc, Element elePersonInfo) 
			throws Exception {
	logger.verbose("updateInputXML method");
	
	/* This is the for the Name comparison when deciding which nodes to replace - Assume always must have a bill To*/
	Node nodOriginal = xpathUtil.getNode(inputDoc, "Order/PersonInfoBillTo");
	/* */

	Node nodOrder = xpathUtil.getNode(inputDoc, "Order");
	logger.verbose("nodOrder: " + XMLUtil.getXMLString(nodOrder));
	/* Updating Header Fields */
	Document docUpdateOrderOut = updateOrderHeader(nodOrder, elePersonInfo);
	String strXPathStartString = "Order/";
	/* Updating Order BillTo */
	String strNodeName = "PersonInfoBillTo";
	docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrder, strNodeName, elePersonInfo, nodOriginal);
	/* Updating Order ShipTo (if found) */
	strNodeName = "PersonInfoShipTo";
	docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrder, strNodeName, elePersonInfo, nodOriginal);
	/* Updating Order PersonInfoMarkFor (if found) */
	strNodeName = "PersonInfoMarkFor";
	docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrder, strNodeName, elePersonInfo, nodOriginal);
	/* Updating Order PersonInfoContact (if found) */
	strNodeName = "PersonInfoContact";
	docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrder, strNodeName, elePersonInfo, nodOriginal);
	
	/* Updating OrderLines */
	Node nodOrderOut = xpathUtil.getNode(inputDoc, "Order");
	Element eleOrderOut = (Element)nodOrderOut;
	String strCustomerID = eleOrderOut.getAttribute("ShipToID");
	logger.verbose("strCustomerID (eleOrderOut): " + strCustomerID);
	strXPathStartString = "Order/OrderLines/OrderLine/";	
	NodeList nlOrderLines = xpathUtil.getNodeList(inputDoc, "Order/OrderLines/OrderLine");
	if (nlOrderLines != null && nlOrderLines.getLength() > 0) {
		logger.verbose("True for Order/OrderLines/OrderLine element");
		for (int i = 0; i < nlOrderLines.getLength(); i++) {
			Node nodOrderLine = nlOrderLines.item(i);
			Element eleOrderLine = (Element)nodOrderLine;
			logger.verbose("nodOrderLine: " + XMLUtil.getXMLString(nodOrderLine));
			/* Setting OrderLine ShipTo */
			eleOrderLine.setAttribute("ShipToID", strCustomerID);
			/* Updating Order ShipTo (if found) */
			strNodeName = "PersonInfoShipTo";
			docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrderLine, strNodeName, elePersonInfo, nodOriginal);
			/* Updating Order PersonInfoMarkFor (if found) */
			strNodeName = "PersonInfoMarkFor";
			docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrderLine, strNodeName, elePersonInfo, nodOriginal);			
		
		}
	}
		
	/* Test Code */	
	logger.verbose("elePersonInfo (updateInputXML Method): " + XMLUtil.getXMLString(elePersonInfo));

	return docUpdateOrderOut;
	
	}
	/* Generating User ID - Not used anymore*/
	private Element setUserID (Element elePersonInfo, String strEnterpriseCode) throws Exception {
		logger.verbose("setUserID method.");
		String strFirstName = elePersonInfo.getAttribute("FirstName");
		String strLastName = elePersonInfo.getAttribute("LastName");
		String strCustomerID = elePersonInfo.getAttribute("CustomerID");
		String strCustomerContactKey = elePersonInfo.getAttribute("CustomerContactKey");
		logger.verbose("strFirstName: " + strFirstName);
		logger.verbose("strLastName: " + strLastName);
		logger.verbose("strCustomerID: " + strCustomerID);
		logger.verbose("strCustomerContactKey: " + strCustomerContactKey);
		String strUserID = strFirstName.substring(0,1).toUpperCase() + strLastName.substring(0,1).toUpperCase();
		if (strCustomerID.length() > 4 || strCustomerID.length() == 4) {
			strUserID = strUserID + strCustomerID.substring((strCustomerID.length()-3), (strCustomerID.length()));
			logger.verbose("CustomerID substring: " + strCustomerID.substring((strCustomerID.length()-3), (strCustomerID.length())));
		}
		if (strCustomerID.length() < 4) {
			strUserID = strUserID + strCustomerID;
		}
		logger.verbose("strUserID: " + strUserID);
		
		if (strUserID != null && strUserID.length() > 0) {
			String strManageCustomerOutTemplate = GetCustomerOutputTemplate();
			String strInputCustomerXML = "<Customer OrganizationCode='%s' CustomerType='02' CustomerID='%s'>" +
											"<CustomerContactList>" +
											"<CustomerContact CustomerContactKey='%s' UserID='%s' >" +
											"<User DisplayUserID='%s' LoginID='%s' Password='%s' Localecode='%s'/>" +
											"</CustomerContact>" +
											"</CustomerContactList>" +
											"</Customer>";
			Element eleOrganisation = getOrganisationAttr(strEnterpriseCode);
			String strLocale = eleOrganisation.getAttribute("LocaleCode");
			String strCustomerMasterOrganizationCode = eleOrganisation.getAttribute("CustomerMasterOrganizationCode");
			strInputCustomerXML = String.format(strInputCustomerXML, strCustomerMasterOrganizationCode, strCustomerID, strCustomerContactKey, strUserID, strUserID, strUserID, strUserID, strLocale);
			Document docInputCustomerXML = XMLUtil.getDocument(strInputCustomerXML);
			env.setApiTemplate("manageCustomer",  XMLUtil.getDocument(strManageCustomerOutTemplate));
			logger.verbose("docInputCustomerXML: " + XMLUtil.getXMLString(docInputCustomerXML));		
			Document docCustomer = api.manageCustomer(env, docInputCustomerXML);
			env.clearApiTemplate("manageCustomer");
			logger.verbose("docCustomer: " + XMLUtil.getXMLString(docCustomer));
			elePersonInfo.setAttribute("UserID", strUserID);
		}
		
		return elePersonInfo;
	}
	
	/* Get Organisation Locale and Customer Organization*/
	private Element getOrganisationAttr (String strEnterpriseCode) throws Exception {
		logger.verbose("getOrganisationAttr method");
		String strInputXML = "<Organization OrganizationCode='%s'/>";
		strInputXML = String.format(strInputXML, strEnterpriseCode);
		String strOutputTemplate = "<Organization OrganizationCode='' LocaleCode='' CustomerMasterOrganizationCode=''/>";
		Document docInputXML = XMLUtil.getDocument(strInputXML);
		env.setApiTemplate("getOrganizationHierarchy",  XMLUtil.getDocument(strOutputTemplate));
		logger.verbose("docInputCustomerXML: " + XMLUtil.getXMLString(docInputXML));		
		Document docOutput = api.getOrganizationHierarchy(env, docInputXML);
		env.clearApiTemplate("getOrganizationHierarchy");
		logger.verbose("docOutput: " + XMLUtil.getXMLString(docOutput));
		Node nodOrganisation = xpathUtil.getNode(docOutput, "Organization");
		Element eleOrganisation = (Element)nodOrganisation;
		logger.verbose("docOutput: " + XMLUtil.getXMLString(eleOrganisation));
		return eleOrganisation;			
	}


	/* Method to update Order Header Fields */
	private Document updateOrderHeader (Node nodOrder, Element elePersonInfo) 
			throws Exception {
		logger.verbose("updateOrderHeader method");
		logger.verbose("elePersonInfo (updateOrderHeader Method): " + XMLUtil.getXMLString(elePersonInfo));
		/* Getting Header Information */
		String strCustomerID = elePersonInfo.getAttribute("CustomerID");
		String strCustomerContactID = elePersonInfo.getAttribute("CustomerContactID");
		String strPersonInfoKey = elePersonInfo.getAttribute("PersonInfoKey");
		String strEMailID = elePersonInfo.getAttribute("EMailID");
		Element elOrder = (Element)nodOrder;
		logger.verbose("elOrder: " + XMLUtil.getXMLString(elOrder));
		/* Only update BuyerUserID if it is not set */
		String strBuyerUserID = elOrder.getAttribute("BuyerUserId");
		if (strBuyerUserID != null && strBuyerUserID != "") {
			logger.verbose("BuyerUserId is set on order. No need to update.");
		} else {
			logger.verbose("BuyerUserId is not set on order. Need to update.");
			strBuyerUserID = elePersonInfo.getAttribute("UserID");
			elOrder.setAttribute("BuyerUserId", strBuyerUserID);	
		}
		/* Other Order Header Updates */
		logger.verbose("Updating Order Header Attributes");
		elOrder.setAttribute("BillToID", strCustomerID);
		elOrder.setAttribute("BillToKey", strPersonInfoKey);
		elOrder.setAttribute("CustomerContactID", strCustomerContactID);
		elOrder.setAttribute("CustomerEMailID", strEMailID);
		elOrder.setAttribute("ShipToID", strCustomerID);
		elOrder.setAttribute("ShipToKey", strPersonInfoKey);	
		Document docUpdateOrderOut = XMLUtil.getDocumentForElement(elOrder);
		logger.verbose("docUpdateOrderOut: " + XMLUtil.getXMLString(docUpdateOrderOut));
		return docUpdateOrderOut;
	}

	/* Method to update Address Nodes */
	private Document UpdateAddressNode (String strXPathStartString, Document inputDoc, Document docUpdateOrderOut, Node nodXML, 
					String strNodeName, Element elePersonInfo, Node nodCompareNode) throws Exception {
		logger.verbose("UpdateAddressNode method");
		logger.verbose("elePersonInfo (UpdateAddressNode Method): " + XMLUtil.getXMLString(elePersonInfo));
		logger.verbose("Updating " + strNodeName + ".");
		String strOrigFirstName = XMLUtil.getAttribute((Element)nodCompareNode, "FirstName");
		String strOrigLastName =  XMLUtil.getAttribute((Element)nodCompareNode, "LastName");
		String strOrigZipCode =  XMLUtil.getAttribute((Element)nodCompareNode, "ZipCode");
		Boolean blChild = FindChild(nodXML, strNodeName);
		logger.verbose("blChild: " + blChild.toString());
		if (blChild == true) {
			Node nodOriginal = xpathUtil.getNode(inputDoc, strXPathStartString + strNodeName);
			logger.verbose("nodOriginal: " + XMLUtil.getXMLString(nodOriginal));
			strOrigFirstName = XMLUtil.getAttribute((Element)nodOriginal, "FirstName");
			strOrigLastName =  XMLUtil.getAttribute((Element)nodOriginal, "LastName");
			strOrigZipCode =  XMLUtil.getAttribute((Element)nodOriginal, "ZipCode");
			/* Removing attributes added earlier */
			elePersonInfo.removeAttribute("CustomerID");
			elePersonInfo.removeAttribute("CustomerContactID");
			elePersonInfo.removeAttribute("CustomerContactKey");
			elePersonInfo.removeAttribute("CustomerAdditionalAddressID");
			elePersonInfo.removeAttribute("MatchedPerson");
			elePersonInfo.removeAttribute("MatchedUserID");
			elePersonInfo.removeAttribute("UpdateType");
			elePersonInfo.removeAttribute("UserID");
			elePersonInfo.removeAttribute("BuyerUserId");
			Document docTemp = XMLUtil.getDocumentForElement(elePersonInfo);
			Node nodPersonInfo = xpathUtil.getNode(docTemp, "PersonInfo");
			Node nodNew = docTemp.renameNode(nodPersonInfo, null, strNodeName);
			logger.verbose("nodNew: " + XMLUtil.getXMLString(nodNew));
			String strNewFirstName = XMLUtil.getAttribute((Element)nodNew, "FirstName");
			String strNewLastName = XMLUtil.getAttribute((Element)nodNew, "LastName");
			String strNewZipCode = XMLUtil.getAttribute((Element)nodNew, "ZipCode");
			logger.verbose("strOrigFirstName: " + strOrigFirstName);
			logger.verbose("strOrigLastName: " + strOrigLastName);
			logger.verbose("strOrigZipCode: " + strOrigZipCode);
			logger.verbose("strNewFirstName: " + strNewFirstName);
			logger.verbose("strNewLastName: " + strNewLastName);
			logger.verbose("strNewZipCode: " + strNewZipCode);
			if (strOrigFirstName.equals(strNewFirstName) && strOrigLastName.equals(strNewLastName) && strOrigZipCode.equals(strNewZipCode)) {
				logger.verbose("First Name, Last Name, and Zip Code match the compare XML so will update.");
				logger.verbose("Calling node replace.");
				docUpdateOrderOut = AppendNode(inputDoc, nodXML, nodNew, strNodeName );
				logger.verbose("docUpdateOrderOut: " + XMLUtil.getXMLString(docUpdateOrderOut));
			} else {
				logger.verbose("First Name, Last Name, and Zip Code do not match the compare XML so skipping.");
			}
		} else {
			logger.verbose("No " + strNodeName + " Found. " );
		}
		return docUpdateOrderOut;
	}

	
	/* Method to check if a child node exists in a document */
	private Boolean FindChild (Node nodFullNode, String strNodeToFind) 
		throws Exception {
		logger.verbose("ChildExists method");
		Boolean blChildExists = false;
		Boolean blChildren = nodFullNode.hasChildNodes();
		logger.verbose("blChildren: " + blChildren.toString());
		if (blChildren == true) {
			NodeList nlChildList = nodFullNode.getChildNodes();
			if (nlChildList != null && nlChildList.getLength() > 0) {
				logger.verbose("True for nlChildList");			
				for (int i = 0; i < nlChildList.getLength(); i++) {
					logger.verbose("Iterating Childs.");
					Node nodChildNode = nlChildList.item(i);
					String strChildName = nodChildNode.getNodeName();
					logger.verbose("strChildName: " + strChildName);
					if (strChildName.equals(strNodeToFind)) {
						logger.verbose("Found Node");
						blChildExists = true;
						logger.verbose("Match found so breaking.");
						break;
					}
					blChildExists = false;
				}
			}
		}	
		return blChildExists;
	}

	/* Method to replace a node in an existing document */
	private Document AppendNode(Document docImport, Node nodFullNode, Node nodNewNode, String strNodeToReplace) 
			throws Exception {
		logger.verbose("AppendNode method");
		Node nodClone = nodNewNode.cloneNode(Boolean.TRUE);
		logger.verbose("New Node cloned. nodClone: " + XMLUtil.getXMLString(nodClone));
		Boolean blChildren = nodFullNode.hasChildNodes();
		logger.verbose("blChildren: " + blChildren.toString());
		if (blChildren == true) {
			NodeList nlChildList = nodFullNode.getChildNodes();
			if (nlChildList != null && nlChildList.getLength() > 0) {
				logger.verbose("True for nlChildList");			
				for (int i = 0; i < nlChildList.getLength(); i++) {
					logger.verbose("Iterating Childs.");
					Node nodChildNode = nlChildList.item(i);
					String strChildName = nodChildNode.getNodeName();
					logger.verbose("strChildName: " + strChildName);
					if (strChildName.equals(strNodeToReplace)) {
						logger.verbose("Found Node To Replace.");
						Element parentElement = (Element)nodChildNode.getParentNode();
						nodChildNode.getParentNode().removeChild(nodChildNode);
						parentElement.normalize();
						logger.verbose("Node removed. nodFullNode: " + XMLUtil.getXMLString(nodFullNode));
						docImport.adoptNode(nodClone);
						parentElement.appendChild(nodClone);
						logger.verbose("New Node Appended nodFullNode: " + XMLUtil.getXMLString(nodFullNode));
						logger.verbose("Match found so breaking.");
						break;
					}			
				}
			}
		} else {
			logger.verbose("No Children found so can't replace.");	
		}
		
		return docImport;
	}



}