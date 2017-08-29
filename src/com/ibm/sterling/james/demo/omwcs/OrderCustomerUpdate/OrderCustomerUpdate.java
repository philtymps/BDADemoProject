package com.ibm.sterling.james.demo.omwcs.OrderCustomerUpdate;

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

public class OrderCustomerUpdate implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(OrderCustomerUpdate.class);
	
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
	 * It changes the address on an order
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document onCustomerOrderUpdate(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		logger.verbose("Hello Customer Order Update World");
		logger.verbose("onCustomerOrderUpdate.InputDoc=" + XMLUtil.getXMLString(inputDoc));
		xpathUtil = new CachedXPathUtil();
		inputDoc = updateCustomerOnOrder(inputDoc);
		return inputDoc;
	}
	
	private Document updateCustomerOnOrder(Document inputDoc) throws Exception {
		logger.verbose("updateCustomerOnOrder method");
		String strInvalidOutput;
		String strValidXML = "N";
		String strErrorCustomer = "";
		String strErrorOrder = "";
		String strOrderNo = "1";
		String strEnterpriseCode = "1";
		String strDocumentType = "1";
		String strCustomerID = "1";
		/* Validating input XML is 
		 * <Order OrderNo="" EnterpriseCode="" DocumentType="" CustomerID="" />
		 */
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			for (int i = 0; i < 1; i++) {
				Element elOrderXML = (Element)nlOrder.item(i);
				strOrderNo = elOrderXML.getAttribute("OrderNo");
				strEnterpriseCode = elOrderXML.getAttribute("EnterpriseCode");
				strDocumentType = elOrderXML.getAttribute("DocumentType");
				strCustomerID = elOrderXML.getAttribute("CustomerID");
				
				if (strOrderNo != null &&  strEnterpriseCode != null  &&  strDocumentType != null  &&  strCustomerID != null
						&& strOrderNo != "" &&  strEnterpriseCode != ""  &&  strDocumentType != ""  &&  strCustomerID != "") {
					logger.verbose("OrderNo, EnterpriseCode, DocumentType, CustomerID are all set");
					logger.verbose("strOrderNo= " + strOrderNo);
					logger.verbose("strEnterpriseCode= " + strEnterpriseCode);
					logger.verbose("strDocumentType= " + strDocumentType);
					logger.verbose("strCustomerID= " + strCustomerID);
					strValidXML = "Y";
				} else {
						logger.verbose("Order Element - OrderNo, EnterpriseCode, DocumentType, CustomerID NOT all set");
						strValidXML = "N";
						
				}
			}
		}
		
		if (strValidXML == "Y") {
			Element elePersonInfo = getCustomer(strCustomerID, strEnterpriseCode);
			Document docOrder = getOrder(strOrderNo, strEnterpriseCode, strDocumentType);
			strErrorCustomer = elePersonInfo.getAttribute("ERROR");
			strErrorOrder = xpathUtil.getValue(docOrder, "/Order/@ERROR");
			String strOrderStatus = xpathUtil.getValue(docOrder, "/Order/@MinOrderStatus");
			logger.verbose("strErrorCustomer: "  + strErrorCustomer);
			logger.verbose("strErrorOrder: "  + strErrorOrder);
			logger.verbose("strOrderStatus: "  + strOrderStatus);
			int intStatusCheck = strOrderStatus.compareTo("2000");
			logger.verbose("intStatusCheck: "  + Integer.toString(intStatusCheck));
			if (strErrorCustomer != "Y" && strErrorOrder != "Y" && intStatusCheck < 0) {
			/*if (strErrorCustomer != "Y" && strErrorOrder != "Y") {*/
				Document DocUpdatedOrder = updateOrderXML(docOrder, elePersonInfo);
				inputDoc = callChangeOrder(DocUpdatedOrder);			
			}
		}
		
		if (strValidXML == "N") {
			logger.verbose("Invalid Input XML.");
			strInvalidOutput="<Error Text='Invalid Input XML. Must be format:'><Order OrderNo='' EnterpriseCode='' DocumentType='' CustomerID='' /></Error>";
			inputDoc = XMLUtil.getDocument(String.format(strInvalidOutput));
		}
		if (strValidXML == "Y" && strErrorCustomer == "Y") {
			logger.verbose("Invalid Input XML.");
			strInvalidOutput="<Error Text='Invalid Customer.'></Error>";
			inputDoc = XMLUtil.getDocument(String.format(strInvalidOutput));
		}
		if (strValidXML == "Y" && strErrorOrder == "Y") {
			logger.verbose("Invalid Order.");
			strInvalidOutput="<Error Text='Invalid Order.'></Error>";
			inputDoc = XMLUtil.getDocument(String.format(strInvalidOutput));
		}
				
		return inputDoc;
	}
	/* Getting Customer Element */
	private Element getCustomer (String strCustomerID, String strEnterpriseCode) throws Exception {
		logger.verbose("getCustomer method.");
		Document docTemp = XMLUtil.getDocument("<PersonInfo ERROR='Y' ></PersonInfo>");
		Element elePersonInfo = (Element)xpathUtil.getNode(docTemp, "/PersonInfo");
		logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));	
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
		String inputCustomerXML = "<Customer CustomerID='%s' OrganizationCode='%s'/>";
		Document docCustomerXML = XMLUtil.getDocument(String.format(inputCustomerXML, strCustomerID, strEnterpriseCode));
		env.setApiTemplate("getCustomerDetails",  XMLUtil.getDocument(outputCustomerTemplate));
		logger.verbose("docCustomerXML: " + XMLUtil.getXMLString(docCustomerXML));		
		Document docCustomer = api.getCustomerDetails(env, docCustomerXML);
		env.clearApiTemplate("getCustomerDetails");
		/* Get Customer XML */
		Node nodCustomer = xpathUtil.getNode(docCustomer, "/Customer");
		Element eleCustomer = (Element)nodCustomer;
		logger.verbose("eleCustomer: " + XMLUtil.getXMLString(eleCustomer));	
		String strCustomerContactID = xpathUtil.getValue(eleCustomer,"CustomerContactList/CustomerContact/@CustomerContactID");
		String strUserID = xpathUtil.getValue(eleCustomer,"CustomerContactList/CustomerContact/@UserID");			
		NodeList nlPersonInfo = xpathUtil.getNodeList(docCustomer, "Customer/CustomerContactList/CustomerContact/CustomerAdditionalAddressList/CustomerAdditionalAddress/PersonInfo");
		if (nlPersonInfo != null && nlPersonInfo.getLength() > 0) {
			logger.verbose("True for Customer/CustomerContactList/CustomerContact/CustomerAdditionalAddressList/CustomerAdditionalAddress/PersonInfo element");
			/* only using first PersonInfo for update */
			for (int i = 0; i < 1; i++) {
				logger.verbose("Finding right customer PersonInfo element.");
				elePersonInfo = (Element)nlPersonInfo.item(i);
				logger.verbose("Adding CustomerID, CustomerContactID, UserID to PersonInfo. ");
				elePersonInfo.setAttribute("CustomerID", strCustomerID);
				elePersonInfo.setAttribute("CustomerContactID", strCustomerContactID);
				elePersonInfo.setAttribute("UserID", strUserID);
			}
		}
		logger.verbose("elePersonInfo: " + XMLUtil.getXMLString(elePersonInfo));
		return elePersonInfo;
	}
	
	/* Getting Order Document */
	private Document getOrder (String strOrderNo, String strEnterpriseCode, String strDocumentType) throws Exception {
		logger.verbose("getOrder method.");
		Document docOrder = XMLUtil.getDocument("<Order ERROR='Y'></Order>");
		String strOutputOrderXML = "<Order OrderNo='' EnterpriseCode='' DocumentType='' ShipToID='' MinOrderStatus=''>" +
		   							"<OrderLines>" +
		   							"<OrderLine OrderLineKey='' ShipToID='' ShipToKey='' MinLineStatus=''>" +
		   							"<PersonInfoShipTo PersonInfoKey=''/>" +
		   							"</OrderLine>" +
		   							"</OrderLines>" +
		   							"<PersonInfoShipTo PersonInfoKey=''/>" + 
		   							"<PersonInfoBillTo PersonInfoKey=''/>" +
		   							"<PersonInfoMarkFor PersonInfoKey=''/>" + 
		   							"<PersonInfoContact PersonInfoKey=''/>" +
		   							"</Order>";
		String strInputOrderXML = "<Order OrderNo='%s' EnterpriseCode='%s' DocumentType='%s'/>";
		Document docInputOrderXML = XMLUtil.getDocument(String.format(strInputOrderXML, strOrderNo, strEnterpriseCode, strDocumentType));
		logger.verbose("docInputOrderXML: " + XMLUtil.getXMLString(docInputOrderXML));
		env.setApiTemplate("getOrderDetails",  XMLUtil.getDocument(strOutputOrderXML));
		docOrder = api.getOrderDetails(env, docInputOrderXML);
		env.clearApiTemplate("getOrderDetails");
		logger.verbose("docOrderDetails: " + XMLUtil.getXMLString(docOrder));
		
		return docOrder;
	}
	/* Building New XML */
	private Document updateOrderXML(Document inputDoc, Element elePersonInfo) 
				throws Exception {
		logger.verbose("updateInputXML method");
		
		/* This is the for the Name comparison when deciding which nodes to replace - Assume always must have a bill To*/
		Node nodOriginal = xpathUtil.getNode(inputDoc, "Order/PersonInfoBillTo");
		/* */

		Node nodOrder = xpathUtil.getNode(inputDoc, "Order");
		logger.verbose("nodOrder: " + XMLUtil.getXMLString(nodOrder));
		Element elOrder = (Element)nodOrder;
		String strMinOrderStatus = elOrder.getAttribute("MinOrderStatus");
		/* Updating Header Fields */
		Document docUpdateOrderOut = updateOrderHeader(nodOrder, elePersonInfo);
		String strXPathStartString = "Order/";
		/* Updating Order BillTo */
		String strNodeName = "PersonInfoBillTo";
		docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrder, strNodeName, elePersonInfo, nodOriginal);
		/* Updating Order ShipTo (if found) */
		int intStatusCheck = strMinOrderStatus.compareTo("2000");
		logger.verbose("strMinOrderStatus: " + strMinOrderStatus);
		logger.verbose("intStatusCheck: " + Integer.toString(intStatusCheck));
		if (intStatusCheck < 0){
			strNodeName = "PersonInfoShipTo";
			docUpdateOrderOut = UpdateAddressNode (strXPathStartString, inputDoc, docUpdateOrderOut, nodOrder, strNodeName, elePersonInfo, nodOriginal);
		}
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
				String strStatus = eleOrderLine.getAttribute("MinLineStatus");
				int intLineStatus = strStatus.compareTo("2000");
				logger.verbose("strStatus: " + strStatus);
				logger.verbose("intLineStatus: " + Integer.toString(intLineStatus));
				if (intLineStatus < 0){
					logger.verbose("intLineStatus < 0 is true");
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
		}
			
		/* Test Code */	
		logger.verbose("elePersonInfo (updateInputXML Method): " + XMLUtil.getXMLString(elePersonInfo));

		return docUpdateOrderOut;
		
		}
		
		/* Method to update Order Header Fields */
		private Document updateOrderHeader (Node nodOrder, Element elePersonInfo) 
				throws Exception {
			logger.verbose("updateOrderHeader method");
			/* Getting Header Information */
			String strCustomerID = elePersonInfo.getAttribute("CustomerID");
			String strCustomerContactID = elePersonInfo.getAttribute("CustomerContactID");
			String strPersonInfoKey = elePersonInfo.getAttribute("PersonInfoKey");
			String strEMailID = elePersonInfo.getAttribute("EMailID");
			String strBuyerUserID = elePersonInfo.getAttribute("UserID");
			Element elOrder = (Element)nodOrder;
			logger.verbose("elOrder: " + XMLUtil.getXMLString(elOrder));
			String strMinOrderStatus = elOrder.getAttribute("MinOrderStatus");
			/* Order Header Updates */
			logger.verbose("Updating Order Header Attributes");
			elOrder.setAttribute("BillToID", strCustomerID);
			elOrder.setAttribute("BillToKey", strPersonInfoKey);
			elOrder.setAttribute("CustomerContactID", strCustomerContactID);
			elOrder.setAttribute("CustomerEMailID", strEMailID);
			int intStatusCheck = strMinOrderStatus.compareTo("2000");
			logger.verbose("strMinOrderStatus: " + strMinOrderStatus);
			logger.verbose("intStatusCheck: " + Integer.toString(intStatusCheck));
			if (intStatusCheck < 0){
				logger.verbose("intStatusCheck < 0 is True");
				elOrder.setAttribute("ShipToID", strCustomerID);
				elOrder.setAttribute("ShipToKey", strPersonInfoKey);	
			}
			elOrder.setAttribute("BuyerUserId", strBuyerUserID);	
			Document docUpdateOrderOut = XMLUtil.getDocumentForElement(elOrder);
			logger.verbose("docUpdateOrderOut: " + XMLUtil.getXMLString(docUpdateOrderOut));
			return docUpdateOrderOut;
		}

		/* Method to update Address Nodes */
		private Document UpdateAddressNode (String strXPathStartString, Document inputDoc, Document docUpdateOrderOut, Node nodXML, 
						String strNodeName, Element elePersonInfo, Node nodCompareNode) throws Exception {
			logger.verbose("UpdateAddressNode method");
			logger.verbose("Updating " + strNodeName + ".");
			String strOrigFirstName = XMLUtil.getAttribute((Element)nodCompareNode, "FirstName");
			String strOrigLastName =  XMLUtil.getAttribute((Element)nodCompareNode, "LastName");
			String strOrigZipCode =  XMLUtil.getAttribute((Element)nodCompareNode, "ZipCode");
			Boolean blChild = FindChild(nodXML, strNodeName);
			logger.verbose("blChild: " + blChild.toString());
			if (blChild == true) {
				Node nodOriginal = xpathUtil.getNode(inputDoc, strXPathStartString + strNodeName);
				logger.verbose("nodOriginal: " + XMLUtil.getXMLString(nodOriginal));
				/* Removing attributes added earlier */
				elePersonInfo.removeAttribute("CustomerID");
				elePersonInfo.removeAttribute("CustomerContactID");
				elePersonInfo.removeAttribute("UserID");
				Document docTemp = XMLUtil.getDocumentForElement(elePersonInfo);
				Node nodPersonInfo = xpathUtil.getNode(docTemp, "PersonInfo");
				Node nodNew = docTemp.renameNode(nodPersonInfo, null, strNodeName);
				logger.verbose("nodNew: " + XMLUtil.getXMLString(nodNew));
				String strNewFirstName = XMLUtil.getAttribute((Element)nodOriginal, "FirstName");
				String strNewLastName = XMLUtil.getAttribute((Element)nodOriginal, "LastName");
				String strNewZipCode = XMLUtil.getAttribute((Element)nodOriginal, "ZipCode");
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
		
		private Document callChangeOrder (Document docInputDoc) throws Exception {
			logger.verbose("callChangeOrder Method.");	
			String strOutputOrderXML = "<Order OrderNo='' EnterpriseCode='' DocumentType='' BillToID='' BillToKey='' CustomerContactID='' CustomerEMailID='' ShipToID='' ShipToKey='' BuyerUserId=''>" +
										"<OrderLines>" +
										"<OrderLine OrderLineKey='' ShipToID=''>" +
										"<PersonInfoShipTo/>" +
										"</OrderLine>" +
										"</OrderLines>" +
										"<PersonInfoShipTo/>" +
										"<PersonInfoBillTo/>" +
										"<PersonInfoMarkFor/>" +
										"<PersonInfoContact/>" +
										"</Order>";
			env.setApiTemplate("changeOrder",  XMLUtil.getDocument(strOutputOrderXML));
			Document docUpdateOrderOut = api.changeOrder(env, docInputDoc);
			env.clearApiTemplate("changeOrder");
			logger.verbose("docUpdateOrderOut: " + XMLUtil.getXMLString(docUpdateOrderOut));
			return docUpdateOrderOut;
		}

}