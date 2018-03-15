package com.extension.bda.userexits;

import java.io.File;
import java.rmi.RemoteException;

import org.w3c.dom.Document;

import com.custom.yantra.util.YFSXMLUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSBeforeChangeOrderUE;
import com.yantra.yfs.japi.ue.YFSBeforeCreateOrderUE;

public class BDABeforeChangeOrder implements YFSBeforeChangeOrderUE, YFSBeforeCreateOrderUE {

	@Override
	public String beforeCreateOrder(YFSEnvironment env, String inXML) throws YFSUserExitException {

		try {
			return YFSXMLUtil.getXMLString (beforeCreateOrder (env, YFCDocument.createDocument (inXML).getDocument()));
		} catch (Exception e) {
			throw new YFSUserExitException (e.getMessage());
		}
	}

	@Override
	public Document beforeCreateOrder(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		setSellerCurrency(env, inDoc);		
		addExtraItem(inDoc);
		return inDoc;
	}

	@Override
	public Document beforeChangeOrder(YFSEnvironment env, Document inDoc) throws YFSUserExitException {
		addExtraItem(inDoc);
		return inDoc;
	}
	
	
	private void setSellerCurrency(YFSEnvironment env, Document inDoc){
		YFCElement eOrder = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		String sSeller = eOrder.getAttribute("SellerOrganizationCode");
		if(YFCCommon.isVoid(sSeller)){
			sSeller = eOrder.getAttribute("EnterpriseCode");
		}
		YFCElement ePriceInfo = eOrder.getChildElement("PriceInfo");
		if(YFCCommon.isVoid(ePriceInfo) || YFCCommon.isVoid(ePriceInfo.getAttribute("Currency"))){
			YFCDocument dTemplate = YFCDocument.createDocument("Organization");
			YFCElement eTemplate = dTemplate.getDocumentElement();
			eTemplate.setAttribute("OrganizationCode", "");
			eTemplate.setAttribute("LocaleCode", "");
			YFCDocument dOrg = YFCDocument.createDocument("Organization");
			YFCElement eOrg = dOrg.getDocumentElement();
			eOrg.setAttribute("OrganizationCode", sSeller);
			try {
				YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
				env.setApiTemplate("getOrganizationHierarchy", dTemplate.getDocument());
				Document dOrgOutput = localApi.invoke(env, "getOrganizationHierarchy", dOrg.getDocument());
				
				YFCDocument dLocaleIn = YFCDocument.createDocument("Locale");
				YFCElement eLocaleIn = dLocaleIn.getDocumentElement();
				eLocaleIn.setAttribute("Localecode", dOrgOutput.getDocumentElement().getAttribute("LocaleCode"));
				
				YFCDocument dLocale = YFCDocument.createDocument("LocaleList");
				dLocale.getDocumentElement().createChild("Locale").setAttribute("Currency", "");
				env.setApiTemplate("getLocaleList", dLocale.getDocument());
				Document dLocaleOut = localApi.invoke(env, "getLocaleList", dLocaleIn.getDocument());
				
				YFCDocument dLocaleList = YFCDocument.getDocumentFor(dLocaleOut);
				YFCElement eLocale = dLocaleList.getDocumentElement().getChildElement("Locale");
						
				eOrder.getChildElement("PriceInfo", true).setAttribute("Currency", eLocale.getAttribute("Currency"));
			} catch (YIFClientCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YFSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void addExtraItem(Document inDoc){
		YFCElement automaticItems = getAutomaticRelatedItems();
		if(!YFCCommon.isVoid(automaticItems) && automaticItems.hasChildNodes()){
			YFCDocument dInput = YFCDocument.getDocumentFor(inDoc);
			YFCElement eOrder = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(eOrder) && !YFCCommon.isVoid(eOrder.getChildElement("OrderLines"))){
				for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
					evaluateLine(eOrderLine, eOrder, automaticItems);
				}
			}
		}		
	}
	
	private void evaluateLine(YFCElement eOrderLine, YFCElement eOrder, YFCElement eAutomaticItems){
		if(!YFCCommon.isVoid(eOrderLine.getChildElement("Item"))){
			YFCElement eItem = eOrderLine.getChildElement("Item");
			for(YFCElement eAItem : eAutomaticItems.getChildren()){
				if(YFCCommon.equals(eItem.getAttribute("ItemID"), eAItem.getAttribute("ItemID"))){
					addRelatedItem(eOrder, eOrderLine, eAItem.getChildElement("RelatedItem"), eAItem.getAttribute("RelationshipType"));
				}
			}
		}
	}

	private void addRelatedItem(YFCElement eOrder, YFCElement eExistingLine, YFCElement eNewItem, String sRelationshipType){
		boolean found = false;
		for(YFCElement eMatchItem : eOrder.getChildElement("OrderLines").getChildren()){
			YFCElement eExistingItem = eMatchItem.getChildElement("Item");
			if(!YFCCommon.isVoid(eExistingItem) && YFCCommon.equals(eExistingItem.getAttribute("ItemID"), eNewItem.getAttribute("ItemID"))){
				found = true;
			}
		}
		if(!found){
			String sTranLineId = eExistingLine.getAttribute("TransactionLineId");
			if(YFCCommon.isVoid(sTranLineId)){
				eExistingLine.setAttribute("TransactionalLineId", Math.round(Math.random() * 10000000));
				sTranLineId = eExistingLine.getAttribute("TransactionalLineId");
			}
			YFCElement eNewOrderLine = eOrder.getChildElement("OrderLines").createChild("OrderLine");
			eNewOrderLine.setAttribute("TransactionalLineId", sTranLineId + "r");
			eNewOrderLine.setAttribute("OrderedQty", eExistingLine.getIntAttribute("OrderedQty", 1));
			YFCElement eOLItem = eNewOrderLine.createChild("Item");
			eOLItem.setAttributes(eNewItem.getAttributes());
			eNewOrderLine.setAttribute("DeliveryMethod", "SHP");
			
			YFCElement eOrderLineRelationships = eOrder.getChildElement("OrderLineRelationships", true);
			YFCElement eOrderLineRelationship = eOrderLineRelationships.createChild("OrderLineRelationship");
			eOrderLineRelationship.setAttribute("RelationshipType", sRelationshipType);
			eOrderLineRelationship.createChild("ParentLine").setAttribute("TransactionalLineId", sTranLineId);
			eOrderLineRelationship.createChild("ChildLine").setAttribute("TransactionalLineId", sTranLineId + "r");
		}
	}
	
	private YFCElement getAutomaticRelatedItems(){
		File fAutomatic = new File("/opt/Sterling/Scripts/AutomaticRelatedItems.xml");
		if(fAutomatic.exists()){
			YFCDocument dARI = YFCDocument.getDocumentForXMLFile("/opt/Sterling/Scripts/AutomaticRelatedItems.xml");
			if(!YFCCommon.isVoid(dARI)){
				return dARI.getDocumentElement();
			}
		}
		return YFCDocument.createDocument("ItemList").getDocumentElement();
	}
}
