package com.ibm.sterling.james.demo.omwcs.email.orderconfirmation;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.sterling.james.demo.omwcs.Utils.CachedXPathUtil;
import com.ibm.sterling.james.demo.omwcs.Utils.XMLUtil;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class orderconfirmation implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YFCLogCategory logger = YFCLogCategory.instance(orderconfirmation.class);
	
	@Override
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	/**
	 * For the JB order confirmation email, this code re-arranges the order by delivery type and address
	 * It is assumed to receive order detail XML from getOrderFulfillmentDetails
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document addtoorderconfirmation(YFSEnvironment env, Document inputDoc) throws Exception {
		logger.verbose("Hello addtoorderconfirmation World");
		logger.verbose("addtoorderconfirmation.InputDoc=" + XMLUtil.getXMLString(inputDoc));
		xpathUtil = new CachedXPathUtil();
		/*Validate Input XML is for an Order*/
		String strCustomProcessing = validateInputXML(inputDoc);
		if (strCustomProcessing == "Y") {
			inputDoc = arrangeXML(inputDoc);
		}
		return inputDoc;
	}
	
	/* Check Order and Order Lines are included */
	private String validateInputXML(Document inputDoc) throws Exception {
		logger.verbose("validateInputXML method");
		String strCustomProcessing="N";
		
		Node nodOrder = xpathUtil.getNode(inputDoc, "/Order");
		if (nodOrder != null ) {
			logger.verbose("True for Order input element");		
		}
		Boolean blCheckOrderLines = FindChild(nodOrder, "OrderLines");
		if (blCheckOrderLines == true){
			logger.verbose("True OrderLines is set.");
			strCustomProcessing = "Y";
		}
		logger.verbose("strCustomProcessing: " + strCustomProcessing);
		return strCustomProcessing;
	}
	
	private Document arrangeXML (Document inputDoc) throws Exception {
		logger.verbose("arrangeXML method");
		Document newDoc = inputDoc;
		Node nodNewOrder = xpathUtil.getNode(newDoc, "/Order");
		/* Remove existing OrderLines Node */
		Node nodOrderLines = xpathUtil.getNode(newDoc, "/Order/OrderLines");
		logger.verbose("arrangeXML - Removing OrderLines Node");
		nodNewOrder.removeChild(nodOrderLines);
		nodNewOrder.normalize();		
		logger.verbose("arrangeXML.nodNewOrder=" + XMLUtil.getXMLString(nodNewOrder));
		/* Build new node */
		Element eleFulfillment = newDoc.createElement("FulfillmentLines");
		logger.verbose("arrangeXML.eleFulfillment=" + XMLUtil.getXMLString(eleFulfillment));
		logger.verbose("arrangeXML.nodOrderLines=" + XMLUtil.getXMLString(nodOrderLines));
		String strDeliveryMethod = "SHP";
		eleFulfillment = buildFulfillmentAddrElementShipTo (nodOrderLines, eleFulfillment, newDoc, strDeliveryMethod);
		logger.verbose("eleFulfillment: " + XMLUtil.getXMLString(eleFulfillment));
		nodNewOrder.appendChild(eleFulfillment);
		nodNewOrder.normalize();
		strDeliveryMethod = "DEL";
		eleFulfillment = buildFulfillmentAddrElementShipTo (nodOrderLines, eleFulfillment, newDoc, strDeliveryMethod);
		logger.verbose("eleFulfillment: " + XMLUtil.getXMLString(eleFulfillment));
		nodNewOrder.appendChild(eleFulfillment);
		nodNewOrder.normalize();
		strDeliveryMethod = "PICK";
		eleFulfillment = buildFulfillmentAddrElementShipTo (nodOrderLines, eleFulfillment, newDoc, strDeliveryMethod);
		logger.verbose("eleFulfillment: " + XMLUtil.getXMLString(eleFulfillment));
		nodNewOrder.appendChild(eleFulfillment);
		nodNewOrder.normalize();
		return newDoc;
	}
	
	private Element buildFulfillmentAddrElementShipTo (Node nodOrderLines, Element eleFulfillment, Document newDoc, String strDeliveryMethod) throws Exception {
		logger.verbose("buildFulfillmentAddrElementShipTo method");
		String strRecordsExist = "N";
		String strOrderLineDeliveryMethod = "";
		String strMatchedAddress = "N";
		Element eleAddresses = newDoc.createElement("Addresses");
		Element eleShipTo  = newDoc.createElement("eleShipTo");
		NodeList nlOrderLine = xpathUtil.getNodeList(nodOrderLines, "OrderLine");
		/* Iterating OrderLines to build a list of addresses to evaluate */
		if (nlOrderLine != null && nlOrderLine.getLength() > 0) {
			logger.verbose("True for OrderLine input element");
			for (int i = 0; i < nlOrderLine.getLength(); i++) {
				logger.verbose("buildFulfillmentAddrElementShipTo-OrderLine Loop: " + Integer.toString(i));
				Element elOrderLine = (Element)nlOrderLine.item(i);
				strOrderLineDeliveryMethod = elOrderLine.getAttribute("DeliveryMethod");
				logger.verbose("buildFulfillmentAddrElementShipTo - Input: " + strDeliveryMethod + "  OrderLine: " + strOrderLineDeliveryMethod);
				if (strOrderLineDeliveryMethod.equals(strDeliveryMethod)) {
					logger.verbose("buildFulfillmentAddrElementShipTo - True Delivery Method Match.");
					logger.verbose("buildFulfillmentAddrElementShipTo.strRecordsExist: " + strRecordsExist);
					if (strRecordsExist == "N") {
						logger.verbose("buildFulfillmentAddrElementShipTo-True strMatchedFulfillmentType was N.");
						strRecordsExist = "Y";
					}
					if (strDeliveryMethod == "SHP" || strDeliveryMethod == "DEL") {
						eleShipTo = (Element) xpathUtil.getNode(elOrderLine, "PersonInfoShipTo");
					}
					if (strDeliveryMethod == "PICK") {
						eleShipTo = (Element) xpathUtil.getNode(elOrderLine, "Shipnode/ShipNodePersonInfo");
					}
					strMatchedAddress = checkForAddressShipTo(eleAddresses, eleShipTo);
					logger.verbose("buildFulfillmentAddrElementShipTo.strMatchedAddress: " + strMatchedAddress);
					/* if no match, need to add address to address list */
					if (strMatchedAddress == "N") {
						logger.verbose("buildFulfillmentAddrElementShipTo.strMatchedAddress is true for N.");
						logger.verbose("buildFulfillmentAddrElementShipTo - adding personinfoshipto to address list.");
						Element eleCloneShipTo = (Element)eleShipTo.cloneNode(Boolean.TRUE);
						eleAddresses.appendChild(eleCloneShipTo);
						eleAddresses.normalize();
						logger.verbose("buildFulfillmentAddrElementShipTo.eleAddresses: " + XMLUtil.getXMLString(eleAddresses));	
					}						
				}
			}
			/* Now we have the address list, we can re-evaluate the orderlines and build the Fulfillment XML */
			logger.verbose("buildFulfillmentAddrElementShipTo.strRecordsExist: " + strRecordsExist);
			if (strRecordsExist == "Y") {
				logger.verbose("buildFulfillmentAddrElementShipTo.strRecordsExist=Y True");		
				eleFulfillment = buildFulfillmentElementShipTo (newDoc, nlOrderLine, eleFulfillment, eleAddresses, strDeliveryMethod);
			}
		}
		return eleFulfillment;
	}
	
	private Element buildFulfillmentElementShipTo (Document newDoc, NodeList nlOrderLine, Element eleFulfillment, Element eleAddresses, String strDeliveryMethod) throws Exception {
		logger.verbose("buildFulfillmentElementShipTo Method.");
		String strOrderLineDeliveryMethod = "";
		NodeList nlAddresses = eleAddresses.getChildNodes();
		if (nlAddresses != null && nlAddresses.getLength() > 0) {
			logger.verbose("buildFulfillmentElementShipTo - True for nlAddresses ");
			logger.verbose("buildFulfillmentElementShipTo - nlAddresses length is: " + Integer.toString(nlAddresses.getLength()));
			for (int i = 0; i < nlAddresses.getLength(); i++) {
				logger.verbose("buildFulfillmentElementShipTo - nlAddresses Loop: " + Integer.toString(i));
				Element eleAddress = (Element)nlAddresses.item(i);
				Element eleFulfillmentAddress = newDoc.createElement("Fulfillment");				
				logger.verbose("buildFulfillmentElementShipTo - creating Header Info");
				eleFulfillmentAddress.setAttribute("DeliveryMethod", strDeliveryMethod);
				eleFulfillmentAddress.setAttribute("Sequence", Integer.toString(i));
				logger.verbose("buildFulfillmentElementShipTo.eleFulfillmentAddress: " + XMLUtil.getXMLString(eleFulfillmentAddress));
				/* Add ShipTo Element */
				logger.verbose("buildFulfillmentElementShipTo - Adding address element");
				Element eleCloneAddress = (Element) eleAddress.cloneNode(Boolean.TRUE);
				eleFulfillmentAddress.appendChild(eleCloneAddress);
				eleFulfillmentAddress.normalize();
				logger.verbose("buildFulfillmentElementShipTo.eleFulfillmentAddress: " + XMLUtil.getXMLString(eleFulfillmentAddress));
				/* Add OrderLines that match */
				Element  eleOrderLines = newDoc.createElement("OrderLines");
				if (nlOrderLine != null && nlOrderLine.getLength() > 0) {
					logger.verbose("True for OrderLine Node List records");
					Element eleShipTo  = newDoc.createElement("eleShipTo");
					for (int j = 0; j < nlOrderLine.getLength(); j++) {
						logger.verbose("buildFulfillmentElementShipTo-OrderLine Loop:" + Integer.toString(j));
						Element elOrderLine = (Element)nlOrderLine.item(j);
						logger.verbose("buildFulfillmentElementShipTo.elOrderLine: " + XMLUtil.getXMLString(elOrderLine));
						strOrderLineDeliveryMethod = elOrderLine.getAttribute("DeliveryMethod");
						logger.verbose("buildFulfillmentElementShipTo - Input: " + strDeliveryMethod + "  OrderLine: " + strOrderLineDeliveryMethod);
						if (strOrderLineDeliveryMethod.equals(strDeliveryMethod)) {
							if (strDeliveryMethod == "SHP" || strDeliveryMethod == "DEL") {
								eleShipTo = (Element) xpathUtil.getNode(elOrderLine, "PersonInfoShipTo");
							}
							if (strDeliveryMethod == "PICK") {
								eleShipTo = (Element) xpathUtil.getNode(elOrderLine, "Shipnode/ShipNodePersonInfo");
							}
							String strMatched = matchAddress(eleAddress, eleShipTo);
							if (strMatched == "Y") {
								logger.verbose("buildFulfillmentElementShipTo - True Matched Line Address.");
								Element eleCloneOrderLine = (Element)elOrderLine.cloneNode(Boolean.TRUE);
								eleOrderLines.appendChild(eleCloneOrderLine);
								logger.verbose("buildFulfillmentElementShipTo.eleOrderLines: " + XMLUtil.getXMLString(eleOrderLines));
							}
						}
					}
					logger.verbose("buildFulfillmentElementShipTo-OrderLine Loop Ended.");				
					/* adding orderlines to fulfillment */
					logger.verbose("buildFulfillmentElementShipTo - Adding orderLines to fulfillment node.");
					eleFulfillmentAddress.appendChild(eleOrderLines);
					eleFulfillmentAddress.normalize();
					/* Add to main XML */
					logger.verbose("buildFulfillmentElementShipTo - Adding fulfillment node to fulfillment nodes.");
					eleFulfillment.appendChild(eleFulfillmentAddress);
					eleFulfillment.normalize();
				}				
				logger.verbose("buildFulfillmentElementShipTo.eleFulfillment: " + XMLUtil.getXMLString(eleFulfillment));				
			}
		} else {
			logger.verbose("buildFulfillmentElementShipTo - nlAddresses is Null.");
		}
		return eleFulfillment;
	}
		   
	/* Evaluates whether address exists in Address Element */
    private String checkForAddressShipTo (Element eleAddresses, Element eleShipTo) throws Exception {
		logger.verbose("checkForAddressShipTo Method");
		String strMatchedAddress = "N";
		logger.verbose("buildFulfillmentElementShipTo.eleAddresses: " + XMLUtil.getXMLString(eleAddresses));
		logger.verbose("buildFulfillmentElementShipTo.eleShipTo: " + XMLUtil.getXMLString(eleShipTo));
		NodeList nlAddresses = eleAddresses.getChildNodes();
		if (nlAddresses != null && nlAddresses.getLength() > 0) {
			logger.verbose("checkForAddressShipTo - True for nlAddresses");
			for (int i = 0; i < nlAddresses.getLength(); i++) {
				logger.verbose("checkForAddressShipTo - nlAddresses Loop: " + Integer.toString(i));
				Element eleAddressesShipTo = (Element)nlAddresses.item(i);
				strMatchedAddress = matchAddress(eleAddressesShipTo, eleShipTo);
				if (strMatchedAddress == "Y") {
					logger.verbose("checkForAddressShipTo - Matched address so breaking loop.");	
					break;
				}
			}
		} else {
			logger.verbose("checkForAddressShipTo - nlAddresses is Null");
		}
		logger.verbose("checkForAddressShipTo.strMatchedAddress: " + strMatchedAddress);
		return strMatchedAddress;
	}
	
	/* Method to quickly check main address fields */
	private String matchAddress (Element eleElement1, Element eleElement2) throws Exception {
		logger.verbose("checkForAddressShipTo Method");
		String strMatched = "N";
	    /* get attributes */
		String strElement1FirstName = eleElement1.getAttribute("FirstName");
		String strElement2FirstName = eleElement2.getAttribute("FirstName");
		String strElement1LastName = eleElement1.getAttribute("LastName");
		String strElement2LastName = eleElement2.getAttribute("LastName");
		String strElement1AddressLine1 = eleElement1.getAttribute("AddressLine1");
		String strElement2AddressLine1 = eleElement2.getAttribute("AddressLine1");
		String strElement1City = eleElement1.getAttribute("City");
		String strElement2City = eleElement2.getAttribute("City");
		String strElement1State = eleElement1.getAttribute("State");
		String strElement2State = eleElement2.getAttribute("State");
		String strElement1ZipCode = eleElement1.getAttribute("ZipCode");
		String strElement2ZipCode = eleElement2.getAttribute("ZipCode");
		String strElement1Country = eleElement1.getAttribute("Country");
		String strElement2Country = eleElement2.getAttribute("Country");
		logger.verbose("checkForAddressShipTo - strElement1FirstName: " + strElement1FirstName + "  strElement2FirstName: " + strElement2FirstName);
		logger.verbose("checkForAddressShipTo - strElement1LastName: " + strElement1LastName + "  strElement2LastName: " + strElement2LastName);
		logger.verbose("checkForAddressShipTo - strElement1AddressLine1: " + strElement1AddressLine1 + "  strElement2AddressLine1: " + strElement2AddressLine1);
		logger.verbose("checkForAddressShipTo - strElement1City: " + strElement1City + "  strElement2City: " + strElement2City);
		logger.verbose("checkForAddressShipTo - strElement1State: " + strElement1State + "  strElement2State: " + strElement2State);
		logger.verbose("checkForAddressShipTo - strElement1ZipCode: " + strElement1ZipCode + "  strElement2ZipCode: " + strElement2ZipCode);
		logger.verbose("checkForAddressShipTo - strElement1Country: " + strElement1Country + "  strElement2Country: " + strElement2Country);
		/* Check for Match */
		if (strElement1FirstName.equals(strElement2FirstName) && strElement1LastName.equals(strElement1LastName) && 
				strElement1AddressLine1.equals(strElement2AddressLine1) && strElement1City.equals(strElement2City) && 
				strElement1State.equals(strElement2State) && strElement1ZipCode.equals(strElement2ZipCode) && 
				strElement1Country.equals(strElement2Country)) {
			logger.verbose("checkForAddressShipTo - True for address match.");
			strMatched = "Y";
		}
		return strMatched;
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
}






























