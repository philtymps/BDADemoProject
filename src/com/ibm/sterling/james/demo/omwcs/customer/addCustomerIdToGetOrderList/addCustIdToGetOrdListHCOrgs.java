package com.ibm.sterling.james.demo.omwcs.customer.addCustomerIdToGetOrderList;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.sterling.james.demo.omwcs.Utils.CachedXPathUtil;
import com.ibm.sterling.james.demo.omwcs.Utils.XMLUtil;
import com.ue.DemoCreateCustomerForOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class addCustIdToGetOrdListHCOrgs implements YIFCustomApi{

	private Properties properties;
	private static YIFApi api;
	private YFSEnvironment env;
	private static YFCLogCategory logger = YFCLogCategory.instance(addCustIdToGetOrdListHCOrgs.class);
	
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
	 * This version has the Excludes the Enterprise and Document Type so picks up all orders including returns
	 * @param env
	 * @param inputDoc
	 * @return
	 * @throws Exception
	 */
	
	public Document addCustomerIdHardCodedOrgs(YFSEnvironment env, Document inputDoc) throws Exception {
		this.env = env;
		YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
		logger.verbose("addCustomerId.InputDoc=" + dInput);
		/* If a BuyerUserId is present, then we can look up the customer ID */
		if (validateInputXML(dInput)) {
			logger.verbose("True - Has BuyerUserId and passes check for complex queries.");
			String strCustomerID = DemoCreateCustomerForOrder.getCustomerID(env, dInput.getDocumentElement(), false);
			if (!YFCCommon.isVoid(strCustomerID)){
				logger.verbose("True - CustomerID is set");
				YFCElement eOrder = dInput.getDocumentElement();
				YFCElement eComplex = eOrder.getChildElement("ComplexQuery", true);
				eComplex.setAttribute("Operator", "AND");
				YFCElement eOr = eComplex.getChildElement("Or", true);
				YFCElement eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "BuyerUserId");
				eExp.setAttribute("Value", eOrder.getAttribute("BuyerUserId"));
				eOrder.removeAttribute("BuyerUserId");
				if (eOrder.hasAttribute("BuyerUserIdQryType")){
					eOrder.removeAttribute("BuyerUserIdQryType");
				}
				eExp = eOr.createChild("Exp");
				eExp.setAttribute("Name", "BillToID");
				eExp.setAttribute("Value", strCustomerID);
			}
		}

		return dInput.getDocument();
	}
	
	/* Check BuyerUserID is set and there is not a Complex Order Query as WCS queries individual Orders followed by generic) */
	private boolean validateInputXML(YFCDocument inputDoc) throws Exception {
		logger.verbose("validateInputXML method");
		YFCElement eOrder = inputDoc.getDocumentElement();
		boolean output = (eOrder.getNodeName().equals("Order") && !YFCCommon.isVoid(eOrder.getAttribute("BuyerUserId")) && YFCCommon.isVoid(eOrder.getChildElement("ComplexQuery")));
		logger.verbose("validateInputXML Response: " + output);
		return output;
	}
}