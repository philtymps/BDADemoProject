package com.ibm.sterling.james.demo.omwcs.shipmentOrders;

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

public class shipmentOrders implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YFCLogCategory logger = YFCLogCategory.instance(shipmentOrders.class);
	
	@Override
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	/**
	 * This class adds a new node to the shipment XML to detail the orders present in the shipment.
	 * (This avoids repetition in XSL as OM does not support for-each-group XSL 2.0)
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document addOrderList(YFSEnvironment env, Document inputDoc) throws Exception {
		logger.verbose("Hello addOrderList World");
		logger.verbose("addOrderList.InputDoc=" + XMLUtil.getXMLString(inputDoc));
		xpathUtil = new CachedXPathUtil();
		/*Validate Input XML is for an Shipment*/
		String strCustomProcessing = validateInputXML(inputDoc);
		if (strCustomProcessing == "Y") {
			inputDoc = arrangeXML(inputDoc);
		}
		return inputDoc;
	}
	
	/* Check Shipment and Shipment Lines are included */
	private String validateInputXML(Document inputDoc) throws Exception {
		logger.verbose("validateInputXML method");
		String strCustomProcessing="N";
		
		Node nodShipment = xpathUtil.getNode(inputDoc, "/Shipment");
		if (nodShipment != null ) {
			logger.verbose("True for Shipment input element");		
		}
		Boolean blCheckShipmentLines = FindChild(nodShipment, "ShipmentLines");
		if (blCheckShipmentLines == true){
			logger.verbose("True OrderLines is set.");
			strCustomProcessing = "Y";
		}
		logger.verbose("strCustomProcessing: " + strCustomProcessing);
		return strCustomProcessing;
	}
	
	private Document arrangeXML (Document inputDoc) throws Exception {
		logger.verbose("arrangeXML method");
		Document newDoc = inputDoc;
		NodeList nlShipmentLines = xpathUtil.getNodeList(newDoc, "/Shipment/ShipmentLines/ShipmentLine");	
		Element eleShipment = (Element) xpathUtil.getNode(newDoc, "/Shipment");
		String strEnterpriseCode = eleShipment.getAttribute("EnterpriseCode");
		logger.verbose("arrangeXML.strEnterpriseCode: " + strEnterpriseCode);
		String strOrderHeaderKey = "";
		/*Document docJBOrderList = XMLUtil.getDocument("<JBOrderList></JBOrderList>");
		 * Node nodJBOrderList = (xpathUtil.getNode(docJBOrderList, "JBOrderList"));*/
		Element eleJBOrderList = newDoc.createElement("JBOrderList");
		logger.verbose("arrangeXML.eleJBOrderList: " + XMLUtil.getXMLString(eleJBOrderList));
		if (nlShipmentLines != null && nlShipmentLines.getLength() > 0) {
			logger.verbose("arrangeXML - True for nlShipmentLines");
			for (int i = 0; i < nlShipmentLines.getLength(); i++) {
				logger.verbose("arrangeXML - nlShipmentLines Loop: " + Integer.toString(i));
				logger.verbose("arrangeXML - getting eleShipmentLine");
				Node nodShipmentLine = nlShipmentLines.item(i);
				logger.verbose("arrangeXML.nodShipmentLine: " + XMLUtil.getXMLString(nodShipmentLine));
				Element eleShipmentLine = (Element) nodShipmentLine;	
				logger.verbose("arrangeXML.eleShipmentLine: " + XMLUtil.getXMLString(eleShipmentLine));
				strOrderHeaderKey = eleShipmentLine.getAttribute("OrderHeaderKey");
				logger.verbose("arrangeXML.OrderHeaderKey: " + strOrderHeaderKey);
				eleJBOrderList = addOrderHeaderKey(inputDoc, eleJBOrderList, strOrderHeaderKey);
				logger.verbose("arrangeXML.nodJBOrderList: " + XMLUtil.getXMLString(eleJBOrderList));
				eleShipment.appendChild(eleJBOrderList);
			}
		} else {
			logger.verbose("arrangeXML - false for nlShipmentLines");
		}
		newDoc.normalize();
		logger.verbose("arrangeXML.newDoc: " + XMLUtil.getXMLString(newDoc));
		return newDoc;
	}
		
	/* Method to add order header key to JBOrderList */
	private Element addOrderHeaderKey (Document inputDoc, Element eleJBOrderList, String strOrderHeaderKey) 
		throws Exception {
		logger.verbose("addOrderHeaderKey method");
		Boolean blOrderHeaderKeyExists = true;
		Boolean blChildren = eleJBOrderList.hasChildNodes();
		String strAttributeName = "OrderHeaderKey";
		
		if (blChildren == true) {
			logger.verbose("True for blChildren");
			/*Check if order header is already there*/
			blOrderHeaderKeyExists = checkAttribute (eleJBOrderList, strAttributeName, strOrderHeaderKey);
		}
		if (blOrderHeaderKeyExists == false || blChildren == false) {
				logger.verbose("False for blOrderHeaderKeyExists");	
				Element eleJBOrder = (Element)inputDoc.createElement("JBOrder");
				eleJBOrder.setAttribute("OrderHeaderKey", strOrderHeaderKey);
				logger.verbose("addOrderHeaderKey.eleJBOrder: " + XMLUtil.getXMLString(eleJBOrder));
				eleJBOrderList.appendChild(eleJBOrder);
				eleJBOrderList.normalize();
		}	
		logger.verbose("addOrderHeaderKey.nodJBOrderList: " + XMLUtil.getXMLString(eleJBOrderList));	
		return eleJBOrderList;
	}
	
	/* Method to check if attribute already exists in child nodes */
	private Boolean checkAttribute (Node nodFullNode, String strAttributeName, String strAttributeValueToFind) 
		throws Exception {
		logger.verbose("checkAttribute method");
		Boolean blAttributeValueExists = false;
		Boolean blChildren = nodFullNode.hasChildNodes();
		logger.verbose("blChildren: " + blChildren.toString());
		if (blChildren == true) {
			NodeList nlChildList = nodFullNode.getChildNodes();
			if (nlChildList != null && nlChildList.getLength() > 0) {
				logger.verbose("True for nlChildList");			
				for (int i = 0; i < nlChildList.getLength(); i++) {
					logger.verbose("Iterating Childs.");
					Node nodChildNode = nlChildList.item(i);
					Element eleChildNode = (Element)nodChildNode;
					String strChildName = eleChildNode.getAttribute(strAttributeName);
					logger.verbose("strChildName: " + strChildName);
					if (strChildName.equals(strAttributeValueToFind)) {
						logger.verbose("Found Node");
						blAttributeValueExists = true;
						logger.verbose("Match found so breaking.");
						break;
					}
					blAttributeValueExists = false;
				}
			}
		}	
		return blAttributeValueExists;
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