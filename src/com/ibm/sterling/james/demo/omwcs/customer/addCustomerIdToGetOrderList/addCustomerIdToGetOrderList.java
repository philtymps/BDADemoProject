package com.ibm.sterling.james.demo.omwcs.customer.addCustomerIdToGetOrderList;

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

public class addCustomerIdToGetOrderList implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(addCustomerIdToGetOrderList.class);
	
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
	 * It looks up the customer ID for a BuyerUserID and adds it to the WCS GetOrderList Query
	 * The WESB Mediation is set to call the new service instead of the base GetOrderList
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document addCustomerId(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		logger.verbose("Hello Amend GetOrderList World");
		logger.verbose("addCustomerId.InputDoc=" + XMLUtil.getXMLString(inputDoc));
		xpathUtil = new CachedXPathUtil();
		String strCustomProcessing = validateInputXML(inputDoc);
		/* If a BuyerUserId is present, then we can look up the customer ID */
		if (strCustomProcessing == "Y") {
			logger.verbose("True - Has BuyerUserId and passes check for complex queries.");
			String strCustomerID = getCustomerID(inputDoc);
			if (strCustomerID != "0000") {
				logger.verbose("True - CustomerID is set");
				inputDoc = getAmendedXML(inputDoc, strCustomerID);
			}
		}

		return inputDoc;
	}
	
	/* Check BuyerUserID is set and there is not a Complex Order Query as WCS queries individual Orders followed by generic) */
	private String validateInputXML(Document inputDoc) throws Exception {
		logger.verbose("validateInputXML method");
		String strBuyerUserID = "";
		String strCustomProcessing="N";
		
		Node nodOrder = xpathUtil.getNode(inputDoc, "/Order");
		if (nodOrder != null ) {
			logger.verbose("True for Order input element");
			Element elOrderXML = (Element)nodOrder;
			strBuyerUserID = elOrderXML.getAttribute("BuyerUserId");
		}
		logger.verbose("strBuyerUserID: " + strBuyerUserID);
		if (strBuyerUserID != null && strBuyerUserID != "") {
			logger.verbose("True strBuyerUserID is set.");
			strCustomProcessing = "Y";
		}
		Boolean blCheckComplex = FindChild(nodOrder, "ComplexQuery");
		if (blCheckComplex == true){
			logger.verbose("True ComplexQuery is set.");
			strCustomProcessing = "N";
		}
		logger.verbose("strCustomProcessing: " + strCustomProcessing);
		return strCustomProcessing;
	}
	
	/* Get Customer ID*/
	private String getCustomerID(Document inputDoc) throws Exception {
		logger.verbose("getCustomerID method");
		String strCustomerID = "0000";
		String strBuyerUserID = "";
		String strUserID = "";
		String strEnterpriseCode = "";
		
		Node nodOrder = xpathUtil.getNode(inputDoc, "/Order");
		if (nodOrder != null) {
			logger.verbose("True for Order input element");
			Element elOrderXML = (Element)nodOrder;
			strEnterpriseCode = elOrderXML.getAttribute("EnterpriseCode");
			strBuyerUserID = elOrderXML.getAttribute("BuyerUserId");
		}
		logger.verbose("strBuyerUserID: " + strBuyerUserID);
		logger.verbose("strEnterpriseCode: " + strEnterpriseCode);
		
		if ((strEnterpriseCode != "" && strEnterpriseCode != null) && (strBuyerUserID != "" && strBuyerUserID != null)) {
			logger.verbose("True for EnterpriseCode and BuyerUserId");
			Document docGetCustomerList = getCustomerListBuyerUserID(strEnterpriseCode, strBuyerUserID);
			NodeList nlCustomer = xpathUtil.getNodeList(docGetCustomerList, "/CustomerList/Customer");
			if (nlCustomer != null && nlCustomer.getLength() > 0) {
				logger.verbose("True for Customer input element");
				for (int j = 0; j < 1; j++) {
					logger.verbose("Customer Loop.");
					Element elCustomer = (Element)nlCustomer.item(j);
					strCustomerID = elCustomer.getAttribute("CustomerID");
					strUserID = xpathUtil.getValue(elCustomer,"CustomerContactList/CustomerContact/@UserID");
					logger.verbose("strCustomerID: " + strCustomerID);
					logger.verbose("strUserID: " + strUserID);
					if (strBuyerUserID.equalsIgnoreCase(strUserID)){
						logger.verbose("True for Customer Match.");
						break;
					}
				}
			}
		}
		
		return strCustomerID;
	}
	
	/* Get Customer List Using just the User ID*/
	private Document getCustomerListBuyerUserID (String strEnterpriseCode, String strBuyerUserID) throws Exception {
		logger.verbose("getCustomerListBuyerUserID method");
		String outputCustomerTemplate= "<CustomerList>" +
										GetCustomerOutputTemplate() +
										"</CustomerList>";
		String inputCustomerXML = "<Customer OrganizationCode='%s'>" +
									"<CustomerContactList>" +
									"<CustomerContact UserID='%s'>" +
									"<CustomerAdditionalAddressList>" +
									"<CustomerAdditionalAddress>" +
									"</CustomerAdditionalAddress>" +
									"</CustomerAdditionalAddressList>" +
									"</CustomerContact>" +
									"</CustomerContactList>" +
									"</Customer>";
		Element eleOrganisation = getOrganisationAttr(strEnterpriseCode);
		String strCustomerMasterOrganizationCode = eleOrganisation.getAttribute("CustomerMasterOrganizationCode");
		Document docInputCustomerXML = XMLUtil.getDocument(String.format(inputCustomerXML, strCustomerMasterOrganizationCode, strBuyerUserID));
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
	
	/* Form GetOrderList XML */
	private Document getAmendedXML (Document inputDoc, String strCustomerID) throws Exception {
		logger.verbose("getAmendedXML method");
		String strGetOrderListXML = "<Order EnterpriseCode='%s' DocumentType='%s' ReadFromHistory='%s' DraftOrderFlag='%s'>" +	
									"<ComplexQuery Operator='AND'>" +
									"<Or>" +
									"<Exp Name='BuyerUserId' QryType='EQ' Value='%s'/>" +
									"<Exp Name='BillToID' QryType='EQ' Value='%s'/>" +
									"</Or>" +
									"</ComplexQuery>" +
									"<OrderBy></OrderBy>" +
									"</Order>";
		Node nodOrder = xpathUtil.getNode(inputDoc, "/Order");
		Element eleOrder = (Element)nodOrder;
		String strBuyerUserID = eleOrder.getAttribute("BuyerUserId");
		String strEnterpriseCode = eleOrder.getAttribute("EnterpriseCode");
		String strDocumentType = eleOrder.getAttribute("DocumentType");
		String strReadFromHistory = eleOrder.getAttribute("ReadFromHistory");
		String strDraftOrderFlag = eleOrder.getAttribute("DraftOrderFlag");
		logger.verbose("strBuyerUserID: " + strBuyerUserID);
		Document docGetOrderListXML = XMLUtil.getDocument(String.format(strGetOrderListXML, strEnterpriseCode, strDocumentType, strReadFromHistory, strDraftOrderFlag, strBuyerUserID, strCustomerID));
		logger.verbose("docGetOrderListXML: " + XMLUtil.getXMLString(docGetOrderListXML));
		eleOrder.removeAttribute("BuyerUserId");
		logger.verbose("Removed BuyerUserId. docGetOrderListXML: " + XMLUtil.getXMLString(docGetOrderListXML));
		String strReplaceNode = "OrderBy";
		Boolean blCheckOrderBy = FindChild(nodOrder, strReplaceNode);
		if (blCheckOrderBy == true){
			Node nodGetOrderListXMLOrder = xpathUtil.getNode(docGetOrderListXML, "/Order");
			Node nodInputDocXMLOrderBy = xpathUtil.getNode(inputDoc, "/Order/OrderBy");
			docGetOrderListXML = ReplaceNode(docGetOrderListXML, nodGetOrderListXMLOrder, nodInputDocXMLOrderBy, strReplaceNode);			
		}
		logger.verbose("docGetOrderListXML: " + XMLUtil.getXMLString(docGetOrderListXML));
		return docGetOrderListXML;
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
	private Document ReplaceNode(Document docImport, Node nodFullNode, Node nodNewNode, String strNodeToReplace) 
			throws Exception {
		logger.verbose("ReplaceNode method");
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
	
	/* Get Organisation Locale and Customer Organization*/
	private Element getOrganisationAttr (String strEnterpriseCode) throws Exception {
		logger.verbose("getLocaleOrganisation method");
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
	
}