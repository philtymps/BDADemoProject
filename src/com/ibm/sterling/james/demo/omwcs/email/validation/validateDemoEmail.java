package com.ibm.sterling.james.demo.omwcs.email.validation;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibm.sterling.james.demo.omwcs.Utils.CachedXPathUtil;
import com.ibm.sterling.james.demo.omwcs.Utils.XMLUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class validateDemoEmail implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(validateDemoEmail.class);
	
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
	 * Takes input as order or shipment
	 * Checks the email address for Order/CustomerEMailID and Shipment/BillToAddress/EMailID
	 * against the user IDs created for DemoEmailList organization
	 * If true, adds an attribute SendCustomEmail to node for evaluation.
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document validateDemoEmailAddress(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		xpathUtil = new CachedXPathUtil();
		logger.verbose("Hello validate email World");
		String strDemoEmailOrg = "DemoEmailList";
		/* Check DemoEmailList organization Exists */
		Boolean blOrgExists = validateOrgExists(strDemoEmailOrg);
		if (blOrgExists == true) {
			Element eleEmail = validateOrderXML(inputDoc);
			String strElementType = eleEmail.getAttribute("ElementType");
			String strHasCustomerEmail = eleEmail.getAttribute("HasEmail");
			if (strElementType == "ORDER") {
				logger.verbose("validateDemoEmail - True for Order Element.");
				if (strHasCustomerEmail == "Y") {
					logger.verbose("validateDemoEmail - True strHasCustomerEmail is Y so is an Order.");
					/* This means it is an Order so will get Node and add custom attribute */
					Boolean blMatch = checkEmailUser(eleEmail, strDemoEmailOrg);
					if (blMatch == true) {
						logger.verbose("validateDemoEmail - True First part of email is a valid user.");
						inputDoc = addEmailAttributeToOrder(inputDoc, "Y");
					} else {
						logger.verbose("validateDemoEmail - Not a valid email user.");
						inputDoc = addEmailAttributeToOrder(inputDoc, "N");
					}
				} else {
					logger.verbose("validateDemoEmail - True for Order Element but no email so no updates.");
				}
			} else {
					eleEmail = validateShipmentXML(inputDoc);
					strElementType = eleEmail.getAttribute("ElementType");
					strHasCustomerEmail = eleEmail.getAttribute("HasEmail");
					if (strElementType == "SHIPMENT") {
						if (strHasCustomerEmail == "Y") {
							logger.verbose("validateDemoEmail - True strHasCustomerEmail is Y so is a shipment.");
							/* This means it is an Shipment so will get Node and add custom attribute */
							Boolean blMatch = checkEmailUser(eleEmail, strDemoEmailOrg);
							if (blMatch == true) {
								logger.verbose("validateDemoEmail - True First part of email is a valid user.");
								inputDoc = addEmailAttributeToShipment(inputDoc, "Y");
							} else {
								logger.verbose("validateDemoEmail - Not a valid email user.");
								inputDoc = addEmailAttributeToShipment(inputDoc, "N");
							}
						} else {
							logger.verbose("validateDemoEmail - Not an Order or Shipment.");
						}
					} else {
						logger.verbose("validateDemoEmail - False for Order or Shipment Element.");
					}
			}
		} else {
			logger.verbose("validateDemoEmail - No match for email enterprise. strDemoEmailOrg: " + strDemoEmailOrg);
		}
		
		return inputDoc;
	}
	
	private boolean validateOrgExists(String strOrganizationCode) throws Exception {
		logger.verbose("validateOrgExists method");
		Boolean blMatch = false;
		Document docOrganizationList=getOrganizationList ();
		logger.verbose("validateOrgExists: docOrganizationList: " + XMLUtil.getXMLString(docOrganizationList));
		logger.verbose("Trying to get NodeList: Organization");
		NodeList nlOrganization = xpathUtil.getNodeList(docOrganizationList, "/OrganizationList/Organization");
		if (nlOrganization != null && nlOrganization.getLength() > 0) {
			logger.verbose("Loop for Organization Records. ");
			for (int i = 0; i < nlOrganization.getLength(); i++) {
				logger.verbose("Comparing OrganizationCode to  strOrganizationCode: " + strOrganizationCode);
				Element elOrganization = (Element)nlOrganization.item(i);	
				logger.verbose("elOrganization: (validateOrgExists method)" + XMLUtil.getXMLString(elOrganization));
				String strOrganizationCodeXML = elOrganization.getAttribute("OrganizationCode");
				logger.verbose("strOrganizationCodeXML: " + strOrganizationCodeXML + "  |  " + " strOrganizationCode: " + strOrganizationCode);
				if (strOrganizationCodeXML.equals(strOrganizationCode)) {
					logger.verbose("True organization match. Setting blMatch to true and breaking loop.");
					blMatch = true;
					break;
				}				
			}
		}
		return blMatch;
	}
	
	private Document getOrganizationList () throws Exception {
		logger.verbose("getOrganizationList method");
		String outputOrgTemplate= "<OrganizationList><Organization OrganizationCode=''/></OrganizationList>";
		String inputOrgXML = "<Organization><OrgRoleList><OrgRole RoleKey='ENTERPRISE'/></OrgRoleList></Organization>";
		Document docInputOrgXML = XMLUtil.getDocument(inputOrgXML);
		env.setApiTemplate("getOrganizationList",  XMLUtil.getDocument(outputOrgTemplate));
		logger.verbose("docInputOrgXML: " + XMLUtil.getXMLString(docInputOrgXML));		
		Document docOrganizationList = api.getOrganizationList(env, docInputOrgXML);
		env.clearApiTemplate("getOrganizationList");
		logger.verbose("Returned docOrganizationList: " + XMLUtil.getXMLString(docOrganizationList));
		return docOrganizationList;		
	}
	
	private Element validateOrderXML(Document inputDoc) throws Exception {
		logger.verbose("validateOrderXML method");
		String strCustomerEMailID = "";
		String strEmailOut = "<Email></Email>";
		Document docEmailOut = XMLUtil.getDocument(strEmailOut);
		logger.verbose("validateOrderXML - docEmailOut: " + XMLUtil.getXMLString(docEmailOut));
		Element eleEmail = (Element)xpathUtil.getNode(docEmailOut, "/Email");
		logger.verbose("validateOrderXML - eleEmail: " + XMLUtil.getXMLString(eleEmail));
		eleEmail.setAttribute("HasEmail", "NOTORDER");	
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			for (int i = 0; i < 1; i++) {
				Element elOrderXML = (Element)nlOrder.item(i);
				strCustomerEMailID = elOrderXML.getAttribute("CustomerEMailID");
				logger.verbose("strCustomerEMailID: " + strCustomerEMailID);
				eleEmail.setAttribute("ElementType", "ORDER");
				if (strCustomerEMailID != null && strCustomerEMailID != "") {
					logger.verbose("True strCustomerEMailID is set.");
					eleEmail.setAttribute("HasEmail", "Y");
					eleEmail.setAttribute("Email", strCustomerEMailID);
				} else {
					eleEmail.setAttribute("HasEmail", "N");
				}
			}
		}
		logger.verbose("validateOrderXML- return eleEmail;: " + XMLUtil.getXMLString(eleEmail));
		return eleEmail;
	}
		
		private Element validateShipmentXML(Document inputDoc) throws Exception {
			logger.verbose("validateInputXML method");
			String strCustomerEMailID = "";
			String strEmailOut = "<Email></Email>";
			Document docEmailOut = XMLUtil.getDocument(strEmailOut);
			Element eleEmail = (Element)xpathUtil.getNode(docEmailOut, "/Email");
			eleEmail.setAttribute("HasEmail", "NOTSHIPMENT");
			NodeList nlShipment = xpathUtil.getNodeList(inputDoc, "/Shipment");
			if (nlShipment != null && nlShipment.getLength() > 0) {
				logger.verbose("True for Shipment input element");
				eleEmail.setAttribute("ElementType", "SHIPMENT");
				for (int i = 0; i < 1; i++) {
					Element elShipmentXML = (Element)nlShipment.item(i);					
					strCustomerEMailID=xpathUtil.getValue(elShipmentXML,"BillToAddress/@EMailID");
					logger.verbose("strCustomerEMailID: " + strCustomerEMailID);
					if (strCustomerEMailID != null && strCustomerEMailID != "") {
						logger.verbose("True strCustomerEMailID is set.");
						eleEmail.setAttribute("HasEmail", "Y");
						eleEmail.setAttribute("Email", strCustomerEMailID);
					} else {
						eleEmail.setAttribute("HasEmail", "N");
					}
				}		
			}
			logger.verbose("validateShipmentXML- eleEmail: " + XMLUtil.getXMLString(eleEmail));
			return eleEmail;
		}
		
		private Document addEmailAttributeToOrder(Document inputDoc, String strSetValue) throws Exception {
			logger.verbose("addEmailAttributeToOrder method");
			Element eleOrder = (Element)xpathUtil.getNode(inputDoc, "/Order");
			eleOrder.setAttribute("CustomSendEmail", strSetValue);
			logger.verbose("eleOrder: " + XMLUtil.getXMLString(eleOrder));
			return inputDoc;
		}
		
		private Document addEmailAttributeToShipment(Document inputDoc, String strSetValue) throws Exception {
			logger.verbose("addEmailAttributeToShipment method");
			Element eleShipment = (Element)xpathUtil.getNode(inputDoc, "/Shipment");
			eleShipment.setAttribute("CustomSendEmail", strSetValue);
			logger.verbose("eleShipment: " + XMLUtil.getXMLString(eleShipment));
			return inputDoc;
		}
		
		private Document getValidEmailPrefixList (String strEnterpriseCode) throws Exception {
			logger.verbose("getValidEmailPrefixList method");
			String outputUserTemplate= "<UserList><User Loginid='' Username=''/></UserList>";
			String inputUserXML = "<User EnterpriseCode='%s'/>";
			Document docInputUserXML = XMLUtil.getDocument(String.format(inputUserXML, strEnterpriseCode));
			env.setApiTemplate("getUserList",  XMLUtil.getDocument(outputUserTemplate));
			logger.verbose("docInputOrgXML: " + XMLUtil.getXMLString(docInputUserXML));		
			Document docUserList = api.getUserList(env, docInputUserXML);
			env.clearApiTemplate("getUserList");
			logger.verbose("docUserList: " + XMLUtil.getXMLString(docUserList));
			return docUserList;		
		}
		
		private boolean checkEmailUser(Element eleEmail, String strOrganizationCode) throws Exception {
			logger.verbose("checkEmailUser method");
			Boolean blMatch = false;
			String strCustomerEmail = eleEmail.getAttribute("Email");
			logger.verbose("strCustomerEmail: " + strCustomerEmail);
			int intIndex = strCustomerEmail.indexOf('@');
			String strCustomer = strCustomerEmail.substring(0, intIndex);
			logger.verbose("strCustomer: " + strCustomer);
			Document docUserList=getValidEmailPrefixList (strOrganizationCode);
			NodeList nlUsers = xpathUtil.getNodeList(docUserList, "/UserList/User");
			if (nlUsers != null && nlUsers.getLength() > 0) {				
				logger.verbose("Loop for User Records. ");
				for (int i = 0; i < nlUsers.getLength(); i++) {
					logger.verbose("Looping. Comparing User to strCustomer: " + strCustomer);
					Element elUser = (Element)nlUsers.item(i);	
					logger.verbose("elUser: (checkEmailUser method)" + XMLUtil.getXMLString(elUser));
					String strUser = elUser.getAttribute("Username");
					logger.verbose("strUser: " + strUser + "  |  " + " strCustomer: " + strCustomer);
					if (strUser.equals(strCustomer)) {
						logger.verbose("True user match. Setting blMatch to true and breaking loop.");
						blMatch = true;
						break;
					}				
				}
				logger.verbose("No User Match.");
			}
			
			return blMatch;
		}
		
}
	

	

	

































