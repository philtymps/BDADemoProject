package com.ibm.sterling.james.demo.omwcs.chainedOrder;

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

public class validateAwaitChainedOrder implements YIFCustomApi{

	private Properties properties;
	private CachedXPathUtil xpathUtil;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(validateAwaitChainedOrder.class);
	
	@Override
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	static {
		try {
			api = YIFClientFactory.getInstance().getApi();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes input as order 
	 * Checks the order line min order status for 1600 awaiting chained order
	 * If true, adds an attribute CustomCreateChained to node for evaluation.
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document validateLineStatus(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		xpathUtil = new CachedXPathUtil();
		logger.verbose("Hello validate awaiting chained order World");
		Boolean blOrderXML = validateOrderXML(inputDoc);
		String strStatusToCheck = "1600";
		logger.verbose("validateLineStatus - blOrderXML: " + Boolean.toString(blOrderXML));
		if (blOrderXML == true) {
			logger.verbose("validateLineStatus - True for Order Line Element.");
			Boolean blMatch = checkLineStatus(inputDoc, strStatusToCheck);
			logger.verbose("validateLineStatus - blMatch: " + Boolean.toString(blMatch));
			if (blMatch == true) {
				logger.verbose("validateDemoEmail - True Status Match.");
				inputDoc = addEmailAttributeToOrder(inputDoc, "Y");
			} else {
				logger.verbose("validateDemoEmail - False Status Match.");
				inputDoc = addEmailAttributeToOrder(inputDoc, "N");
			}				
		} else {					
			logger.verbose("validateDemoEmail - False for Order Match");
		}		
		return inputDoc;
	}
	
	
	private Boolean validateOrderXML(Document inputDoc) throws Exception {
		logger.verbose("validateOrderXML method");
		Boolean blOrder = false;
		logger.verbose("validateOrderXML - blOrder: " + Boolean.toString(blOrder));
		NodeList nlOrder = xpathUtil.getNodeList(inputDoc, "/Order");
		if (nlOrder != null && nlOrder.getLength() > 0) {
			logger.verbose("True for Order input element");
			NodeList nlOrderLines =  xpathUtil.getNodeList(inputDoc, "/Order/OrderLines/OrderLine");
			if (nlOrderLines != null && nlOrderLines.getLength() > 0) {
				logger.verbose("True for OrderLine input element");
				blOrder = true;
			} else {
				logger.verbose("False for OrderLine input element");
			}
		} else {
			logger.verbose("False for Order input element");
		}
		logger.verbose("validateOrderXML- return blOrder;: " + Boolean.toString(blOrder));
		return blOrder;
	}
		
		private Document addEmailAttributeToOrder(Document inputDoc, String strSetValue) throws Exception {
			logger.verbose("addEmailAttributeToOrder method");
			Element eleOrder = (Element)xpathUtil.getNode(inputDoc, "/Order");
			eleOrder.setAttribute("CustomCreateChained", strSetValue);
			logger.verbose("eleOrder: " + XMLUtil.getXMLString(eleOrder));
			return inputDoc;
		}
		
		private boolean checkLineStatus(Document inputDoc, String strMinLineStatus) throws Exception {
			logger.verbose("checkLineStatus method");
			Boolean blMatch = false;
			NodeList nlOrderLines = xpathUtil.getNodeList(inputDoc, "/Order/OrderLines/OrderLine");
			if (nlOrderLines != null && nlOrderLines.getLength() > 0) {				
				logger.verbose("Loop for Order Line Records. ");
				for (int i = 0; i < nlOrderLines.getLength(); i++) {
					logger.verbose("Looping. Comparing Line Status to strMinLineStatus: " + strMinLineStatus);
					Element elOrderLine = (Element)nlOrderLines.item(i);	
					logger.verbose("elOrderLine: (checkEmailUser method)" + XMLUtil.getXMLString(elOrderLine));
					String strStatus = elOrderLine.getAttribute("MinLineStatus");
					strStatus = strStatus.substring(0, 4);
					logger.verbose("strStatus: " + strStatus);
					if (strStatus.equals(strMinLineStatus)) {
						logger.verbose("True status match. Setting blMatch to true and breaking loop.");
						blMatch = true;
						break;
					}				
				}
				logger.verbose("No Status Match.");
			}
			
			return blMatch;
		}
		
}
	

	

	

































