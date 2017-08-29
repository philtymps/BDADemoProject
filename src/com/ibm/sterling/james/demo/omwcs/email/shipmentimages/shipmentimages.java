package com.ibm.sterling.james.demo.omwcs.email.shipmentimages;

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

public class shipmentimages implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(shipmentimages.class);
	
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
	 * For the JB shipment emails, this code adds the image fields to the Shipment Line from the Order Details
	 * It is assumed to receive shipment detail XML from getShipmentDetails
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document addImageFields(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		logger.verbose("Hello addImageFields World");
		logger.verbose("addImageFields.InputDoc=" + XMLUtil.getXMLString(inputDoc));
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
		String strImageLocation = "";
		String strImageID = "";
		if (nlShipmentLines != null && nlShipmentLines.getLength() > 0) {
			logger.verbose("arrangeXML - True for nlShipmentLines");
			for (int i = 0; i < nlShipmentLines.getLength(); i++) {
				logger.verbose("arrangeXML - nlShipmentLines Loop: " + Integer.toString(i));
				logger.verbose("arrangeXML - getting eleShipmentLine");
				Node nodShipmentLine = nlShipmentLines.item(i);
				logger.verbose("arrangeXML.nodShipmentLine: " + XMLUtil.getXMLString(nodShipmentLine));
				Element eleShipmentLine = (Element) nodShipmentLine;	
				logger.verbose("arrangeXML.eleShipmentLine: " + XMLUtil.getXMLString(eleShipmentLine));
				/*strOrderLineKey = eleShipmentLine.getAttribute("OrderLineKey");
				logger.verbose("arrangeXML.strOrderLineKey: " + strOrderLineKey);*/
				String strItemID = eleShipmentLine.getAttribute("ItemID");
				String strUnitOfMeasure = eleShipmentLine.getAttribute("UnitOfMeasure");
				Element eleItemDetails = getItemDetails(strEnterpriseCode, strItemID, strUnitOfMeasure);
				strImageLocation = eleItemDetails.getAttribute("ImageLocation");
				strImageID = eleItemDetails.getAttribute("ImageID");
				eleShipmentLine.setAttribute("ImageLocation", strImageLocation);
				eleShipmentLine.setAttribute("ImageID", strImageID);
				logger.verbose("arrangeXML.eleShipmentLine: " + XMLUtil.getXMLString(eleShipmentLine));
			}
			logger.verbose("arrangeXML - false for nlShipmentLines");
		}
		newDoc.normalize();
		logger.verbose("arrangeXML.newDoc: " + XMLUtil.getXMLString(newDoc));
		return newDoc;
	}
		
	/* Method to get Item Details Primary Info Element for the given Order Line */
    private Element getItemDetails (String strEnterpriseCode, String strItemID, String strUnitOfMeasure) throws Exception {
    	logger.verbose("getItemDetails method");
    	logger.verbose("getItemDetails.strEnterpriseCode: " + strEnterpriseCode);
    	logger.verbose("getItemDetails.strItemID: " + strItemID);
    	logger.verbose("getItemDetails.strUnitOfMeasure: " + strUnitOfMeasure);
    	String outputItemTemplate= "<Item ItemID=''>" +
								   "<PrimaryInformation ImageLocation='' ImageID='' />" +
								   "</Item>";
    	String inputItemXML = "<Item OrganizationCode='%s' ItemID='%s' UnitOfMeasure='%s' />";
    	Document docInputItemXML = XMLUtil.getDocument(String.format(inputItemXML, strEnterpriseCode, strItemID, strUnitOfMeasure));
    	env.setApiTemplate("getItemDetails",  XMLUtil.getDocument(outputItemTemplate));
    	logger.verbose("getItemDetails.docInputItemXML: " + XMLUtil.getXMLString(docInputItemXML));		
    	Document docItemDetails = api.getItemDetails(env, docInputItemXML);
    	env.clearApiTemplate("getItemDetails");
    	logger.verbose("getItemDetails.docItemDetails: " + XMLUtil.getXMLString(docItemDetails));
    	Element elePrimaryInfo = (Element) xpathUtil.getNode(docItemDetails, "/Item/PrimaryInformation");
    	logger.verbose("getItemDetails.elePrimaryInfo: " + XMLUtil.getXMLString(elePrimaryInfo));
    	return elePrimaryInfo;
    }
    
	
	/* Method to get Item Details Primary Info Element for the given Order Line */
    private Element getItemDetailsOld (String strOrderLineKey, Element eleShipmentLine, Document newDoc) throws Exception {
    	logger.verbose("getItemDetails method");
    	Element eleItemDetails = newDoc.createElement("PrimaryInformation");
    	Boolean blOrderChild = FindChild(eleShipmentLine, "Order");
    	if (blOrderChild == true) {
    		logger.verbose("getItemDetails - True for blOrderChild");
     		NodeList nlOrderLines  = xpathUtil.getNodeList(eleShipmentLine, "/ShipmentLine/Order/OrderLines/OrderLine");     		
    		if (nlOrderLines != null && nlOrderLines.getLength() > 0) {
    			logger.verbose("getItemDetails - True for nlOrderLines");
    			for (int i = 0; i < nlOrderLines.getLength(); i++) {
    				logger.verbose("getItemDetails - nlOrderLines Loop: " + Integer.toString(i));
    				Element eleOrderLine = (Element)nlOrderLines.item(i);
    				String strEleOrderLineKey = eleOrderLine.getAttribute("OrderLineKey");
    				logger.verbose("getItemDetails.strEleOrderLineKey: " + strEleOrderLineKey + "  strOrderLineKey: " + strOrderLineKey);
    				if (strOrderLineKey.equals(strEleOrderLineKey)){
    					logger.verbose("getItemDetails - True Order Lines Match");
    					eleItemDetails = (Element) xpathUtil.getNode(eleOrderLine, "/OrderLine/ItemDetails/PrimaryInformation");
    					break;
    				}
    				logger.verbose("getItemDetails - nlOrderLines Loop End: " + Integer.toString(i));
    			}
    		}
    		logger.verbose("getItemDetails - nlOrderLines Loop False.");
     	}
    	logger.verbose("getItemDetails.eleItemDetails: " + XMLUtil.getXMLString(eleItemDetails));
    	return eleItemDetails;
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