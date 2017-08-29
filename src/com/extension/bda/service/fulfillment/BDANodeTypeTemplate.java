package com.extension.bda.service.fulfillment;

import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDANodeTypeTemplate extends BDAServiceApi{

	public BDANodeTypeTemplate(){
		super();
	}
	
	private String getStorageLocation(){
		if (p.containsKey("StorageLocation")){
			return p.getProperty("StorageLocation");
		} else {
			return "/opt/Sterling/Fulfillment/templates";
		}
	}

	public Document saveNodeTypeTemplate(YFSEnvironment env, Document dInput) throws Exception{
		YFCDocument input = YFCDocument.getDocumentFor(dInput);
		YFCElement eNodeType = input.getDocumentElement();
		YFCDocument dOutput = YFCDocument.createDocument("SaveNodeType");
		YFCElement eResponse = dOutput.getDocumentElement();
		if(!YFCCommon.isVoid(eNodeType) && !YFCCommon.isVoid(eNodeType.getAttribute("NodeTypeID"))){
			String sNodeType = eNodeType.getAttribute("NodeTypeID");
			YFCElement eOrganization = eNodeType.getChildElement("Organization");
			if(!YFCCommon.isVoid(eOrganization)){
				eResponse.setAttribute("Saved", writeXML(getStorageLocation(), sNodeType + "_NodeTemplate.xml", YFCDocument.getDocumentFor(eOrganization.getString())));
			}
		} else {
			eResponse.setAttribute("Message", "Nothing to Save");
		}
		return dOutput.getDocument();
	}
	
	public Document getNodeTypeTemplate(YFSEnvironment env, Document dInput) throws Exception {
		YFCDocument dOutput = YFCDocument.createDocument("Response");
		YFCElement eResponse = dOutput.getDocumentElement();
		if(!YFCCommon.isVoid(dInput)){
			YFCDocument input = YFCDocument.getDocumentFor(dInput);
			YFCElement eInput = input.getDocumentElement();
			if(!YFCCommon.isVoid(eInput.getAttribute("NodeTypeID"))){
				File template = new File(getStorageLocation() + File.separator + eInput.getAttribute("NodeTypeID") + "_NodeTemplate.xml");
				if(template.exists()){
					return YFCDocument.getDocumentForXMLFile(getStorageLocation() + File.separator + eInput.getAttribute("NodeTypeID") + "_NodeTemplate.xml").getDocument();
				} else {
					eResponse.setAttribute("Message", "No Template Exists");
				}
			} else {
				eResponse.setAttribute("Message", "No Node Type Provided");
			}
		} else {
			eResponse.setAttribute("Message", "No Input Provided");
		}
		return dOutput.getDocument();
	}
	
	public Document mergeOrganization(YFSEnvironment env, Document dInput) throws Exception {
		if (!YFCCommon.isVoid(dInput)){
			YFCDocument dOrganization = YFCDocument.getDocumentFor(dInput);
			YFCElement eOrganization = dOrganization.getDocumentElement();
		
			if(
					YFCCommon.isVoid(eOrganization.getAttribute("OrganizationCode")) || 
					YFCCommon.isVoid(eOrganization.getAttribute("OrganizationName")) || 
					YFCCommon.isVoid(eOrganization.getAttribute("LocaleCode")) ||
					YFCCommon.isVoid(eOrganization.getAttribute("PrimaryEnterpriseKey"))){
				throw new Exception("Missing Key Attribute");
			}
			
			YFCElement eNode = eOrganization.getChildElement("Node");
			
			if(YFCCommon.isVoid(eNode) || YFCCommon.isVoid(eNode.getAttribute("NodeType"))){
				throw new Exception ("Missing Node Type Definition");
			}
			
			String sNodeType = eNode.getAttribute("NodeType");
		
			
			File template = new File(getStorageLocation() + File.separator + sNodeType + "_NodeTemplate.xml");
			if(template.exists()){
				YFCDocument dTemplateOrganization = YFCDocument.getDocumentForXMLFile(getStorageLocation() + File.separator + sNodeType + "_NodeTemplate.xml");
				YFCElement eTemplateOrganization = dTemplateOrganization.getDocumentElement();
				boolean bopis = false;
				boolean boss = false;
				YFCElement eMergedOrg = mergeElements(eOrganization, eTemplateOrganization);
				if(eMergedOrg.hasAttribute("BOPIS")){
					bopis = eMergedOrg.getBooleanAttribute("BOPIS");
					eMergedOrg.removeAttribute("BOPIS");
				}
				if(eMergedOrg.hasAttribute("BOSS")){
					boss = eMergedOrg.getBooleanAttribute("BOSS");
					eMergedOrg.removeAttribute("BOSS");
				}
				validateOrganization(env, eMergedOrg);
				YFCDocument dMerged = eMergedOrg.getOwnerDocument();
				Document inputDoc = dMerged.getDocument();
				writeXML(getOutputLocation(), eOrganization.getAttribute("OrganizationCode") + "_ManageOrganizationHierarchy.xml", dMerged);
				Document dOutput = callApi(env, inputDoc, null, "manageOrganizationHierarchy");
				
				if(YFCCommon.equals(sNodeType, "Store")){
					addToDistributionGroup(env, "BDA_DSTORE_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
					if(bopis){
						addToDistributionGroup(env, "BDA_DBOPIS_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
					}
					if(boss){
						addToDistributionGroup(env, "BDA_DBOSS_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
						addToDistributionGroup(env, "BDA_DEFAULT_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
					}
				} else if (YFCCommon.equals(sNodeType, "DC")){
					addToDistributionGroup(env, "BDA_DDC_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
					addToDistributionGroup(env, "BDA_DEFAULT_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
				} else if(!YFCCommon.isVoid(sNodeType)) {
					addToDistributionGroup(env, "BDA_D" + sNodeType + "_DG", eMergedOrg.getAttribute("InventoryOrganizationCode"), eMergedOrg.getAttribute("OrganizationCode"), "0001");
				}
				return dOutput;
			} else {
				throw new Exception ("Missing Node Type Template");
			}
			
		}
		throw new Exception ("No Input Provided");
	}
	
	private void addToDistributionGroup(YFSEnvironment env, String sRuleName, String sOwnerKey, String sShipNode, String sDocumentType){
		String sDefaultStoreDG = getRuleValue(env, sRuleName, sOwnerKey, sDocumentType);
		if (!YFCCommon.isVoid(sDefaultStoreDG)){
			YFCDocument dInput = YFCDocument.createDocument("ItemShipNode");
			YFCElement eItemShipNode = dInput.getDocumentElement();
			eItemShipNode.setAttribute("DistributionRuleId", sDefaultStoreDG);
			eItemShipNode.setAttribute("ItemId", "ALL");
			eItemShipNode.setAttribute("ItemType", "ALL");
			eItemShipNode.setAttribute("OwnerKey", sOwnerKey);
			eItemShipNode.setAttribute("ShipnodeKey", sShipNode);
			eItemShipNode.setAttribute("Priority", 10d);
			eItemShipNode.setAttribute("ActiveFlag", true);
			callApi(env, dInput.getDocument(), null, "createDistribution");
		}	
	}
	
	private String getRuleValue(YFSEnvironment env, String sRuleName, String sOrganizationCode, String sDocumentType){
		YFCDocument dInput = YFCDocument.createDocument("Rules");
		YFCElement eRule = dInput.getDocumentElement();
		eRule.setAttribute("CallingOrganizationCode", sOrganizationCode);
		eRule.setAttribute("RuleSetFieldName", sRuleName);
		if(!YFCCommon.isVoid(sDocumentType)){
			eRule.setAttribute("DocumentType", sDocumentType);
		}
		Document output = callApi(env, dInput.getDocument(), null, "getRuleDetails");
		if(!YFCCommon.isVoid(output)){
			Element rule = output.getDocumentElement();
			return rule.getAttribute("RuleSetValue");
		}
		return null;
	}
	
	public static YFCElement mergeElements(YFCElement newElement, YFCElement oldElement) {
		if (newElement.getAttributes().size() > 0){
			oldElement.setAttributes(newElement.getAttributes());	
		}
		for (YFCElement newChildElement : newElement.getChildren()){
			YFCElement  oldChildElement = oldElement.getChildElement(newChildElement.getNodeName());
			if (YFCCommon.isVoid(oldChildElement)){
				oldElement.importNode(newChildElement);
			} else {
				mergeElements(newChildElement, oldChildElement);		
			}
		}
		return oldElement;
	}
	
	private YFCElement getPaymentTypeList(YFSEnvironment env, String sCallingOrg){
		YFCDocument dPaymentType = YFCDocument.createDocument("PaymentType");
		YFCElement ePaymentType = dPaymentType.getDocumentElement();
		ePaymentType.setAttribute("CallingOrganizationCode", sCallingOrg);
		Document dOutput = callApi(env, dPaymentType.getDocument(), null, "getPaymentTypeList");
		if(!YFCCommon.isVoid(dOutput)){
			return YFCDocument.getDocumentFor(dOutput).getDocumentElement();
		}
		return null;
	}
	

	private String getGAddress(YFCElement eAddress){
		String sInput = null;
		if(eAddress.getAttribute("AddressLine1") != null){
			sInput = eAddress.getAttribute("AddressLine1").replaceAll(" ", "+");
		}
		if(sInput != null && eAddress.getAttribute("City") != null){
			sInput += "," + eAddress.getAttribute("City").replaceAll(" ", "+");
		}
		if(sInput != null && eAddress.getAttribute("State") != null){
			sInput += "," + eAddress.getAttribute("State").replaceAll(" ", "+");
		}
		if(sInput == null && eAddress.getAttribute("ZipCode") != null){
			sInput = eAddress.getAttribute("ZipCode");
		}
		return sInput;
	}
	
	private void getLatLong(YFCElement eAddress) throws Exception{
		int responseCode = 0;
	    String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(getGAddress(eAddress), "UTF-8") + "&sensor=true";
	    System.out.println("URL : "+api);
	    URL url = new URL(api);
	    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	    httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    if(responseCode == 200)
	    {
	      YFCDocument document = YFCDocument.parse(httpConnection.getInputStream());
	      XPathFactory xPathfactory = XPathFactory.newInstance();
	      XPath xpath = xPathfactory.newXPath();
	      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	      String status = (String)expr.evaluate(document, XPathConstants.STRING);
	      if(status.equals("OK"))
	      {
	         expr = xpath.compile("//geometry/location/lat");
	         eAddress.setAttribute("Latitude", (String)expr.evaluate(document, XPathConstants.STRING));
	         expr = xpath.compile("//geometry/location/lng");
	         eAddress.setAttribute("Longitude", (String)expr.evaluate(document, XPathConstants.STRING));
	      }
	      else
	      {
	    	api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(eAddress.getAttribute("ZipCode"), "UTF-8") + "&sensor=true";
	  	    System.out.println("URL : "+api);
	  	    url = new URL(api);
	  	    httpConnection = (HttpURLConnection)url.openConnection();
	  	    httpConnection.connect();
	  	    responseCode = httpConnection.getResponseCode();
	  	    if(responseCode == 200)
	  	    {
		         expr = xpath.compile("//geometry/location/lat");
		         eAddress.setAttribute("Latitude", (String)expr.evaluate(document, XPathConstants.STRING));
		         expr = xpath.compile("//geometry/location/lng");
		         eAddress.setAttribute("Longitude", (String)expr.evaluate(document, XPathConstants.STRING));
		    } else{
	  	    	throw new Exception("Error from the API - response status: "+status);
	  	    }
	         
	      }
	    }
	}
	private void validateOrganization(YFSEnvironment env, YFCElement eOrganization){
		if(!YFCCommon.isVoid(eOrganization)){
			YFCElement eNode = eOrganization.getChildElement("Node", true);
			String sNodeType = eNode.getAttribute("NodeType");
			if(!YFCCommon.isVoid(eOrganization.getAttribute("OrganizationCode"))){
				updateAttribute(eOrganization, "OrganizationKey", eOrganization.getAttribute("OrganizationCode"));
			}
			updateAttribute(eOrganization, "ParentOrganizationCode", eOrganization.getAttribute("PrimaryEnterpriseKey"));
			updateAttribute(eOrganization, "InventoryOrganizationCode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eOrganization, "CatalogOrganizationCode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eOrganization, "CapacityOrganizationCode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eOrganization, "PricingOrganizationCode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eOrganization, "CustomerMasterOrganizationCode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eOrganization, "StoreConfigOrganizationCode", eOrganization.getAttribute("OrganizationCode"));
			
			
			updateAttribute(eNode, "IdentifiedByParentAs", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eNode, "NodeOrgCode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eNode, "ShipNode", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eNode, "ShipnodeKey", eOrganization.getAttribute("OrganizationCode"));
			updateAttribute(eNode, "Localecode", eOrganization.getAttribute("LocaleCode"));
			updateAttribute(eNode, "InterfaceType", "YFX");
			updateAttribute(eNode, "Inventorytype", "TRACK");
			updateAttribute(eNode, "InventoryTracked", "Y");
			
			if(!YFCCommon.isVoid(eOrganization.getChildElement("CorporatePersonInfo"))){
				try {
					getLatLong(eOrganization.getChildElement("CorporatePersonInfo"));
				} catch (Exception e){
					e.printStackTrace();
				}
				updateElement(eOrganization, eOrganization.getChildElement("CorporatePersonInfo"), "ContactPersonInfo");
				updateElement(eOrganization, eOrganization.getChildElement("CorporatePersonInfo"), "BillingPersonInfo");
				updateElement(eNode, eOrganization.getChildElement("CorporatePersonInfo"), "ShipNodePersonInfo");
				updateElement(eNode, eOrganization.getChildElement("CorporatePersonInfo"), "ContactPersonInfo");
			}
			
			if(!YFCCommon.isVoid(sNodeType)){
				updateAttribute(eOrganization, "IsNode", "Y");
				updateAttribute(eOrganization, "IsEnterprise", "N");
				updateAttribute(eOrganization, "IsHubOrganization", "N");
				updateAttribute(eOrganization, "IsCarrier", "N");
				if(YFCCommon.equals(sNodeType, "Store")){
					updateAttribute(eOrganization, "IsSeller", "Y");
					
					if(!YFCCommon.isVoid(eOrganization.getChildElement("PaymentTypeList"))){
						YFCElement ePaymentTypeList = getPaymentTypeList(env, eOrganization.getAttribute("PrimaryEnterpriseCode"));
						if(!YFCCommon.isVoid(ePaymentTypeList)){
							eOrganization.appendChild(ePaymentTypeList);
						}
					}
					
					
				} else {
					updateAttribute(eOrganization, "IsSeller", "N");
				}
			}
		}
	}
	
	private void updateAttribute(YFCElement eNode, String sAttribute, String value){
		if(!YFCCommon.isVoid(eNode)){
			if(!eNode.hasAttribute(sAttribute)){
				eNode.setAttribute(sAttribute, value);
			}
		}
	}
	
	private void updateElement(YFCElement eParentNode, YFCElement eChildNode, String sNewNodeName){
		if (!YFCCommon.isVoid(eParentNode.getChildElement(sNewNodeName))){
			for(String sAttribute : eChildNode.getAttributes().keySet()){
				updateAttribute(eParentNode.getChildElement(sNewNodeName), sAttribute, eChildNode.getAttribute(sAttribute));
			}
		} else {
			YFCElement eNewNode = eParentNode.createChild(sNewNodeName);
			eNewNode.setAttributes(eChildNode.getAttributes());
			for(YFCElement eCopy : eChildNode.getChildren()){
				eNewNode.appendChild(eCopy);
			}
		}
	}
	
}
