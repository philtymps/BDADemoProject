package com.extension.bda.service.fulfillment;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAManageDistributions  extends BDAServiceApi{


	public Document manageDistributions(YFSEnvironment env, Document docInput) {
		YFCDocument dInput = YFCDocument.getDocumentFor(docInput);
		YFCElement eItemShipNodes = dInput.getDocumentElement();
		YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
		YFCElement eMultiApi = dMultiApi.getDocumentElement();
		boolean run = false;
		for (YFCElement eCreateItemShipNode : eItemShipNodes.getChildElement("Create", true).getChildren()){
			YFCElement eApi = eMultiApi.createChild("API");
			eApi.setAttribute("Name", "createDistribution");
			YFCElement eItemShipNode = eApi.createChild("Input").createChild("ItemShipNode");
			eItemShipNode.setAttribute("DistributionRuleId", eCreateItemShipNode.getAttribute("DistributionRuleId"));
			eItemShipNode.setAttribute("ItemId", "ALL");
			eItemShipNode.setAttribute("ItemType", "ALL");
			eItemShipNode.setAttribute("ActiveFlag", true);
			eItemShipNode.setAttribute("Priority", 10d);
			eItemShipNode.setAttribute("OwnerKey", eItemShipNodes.getAttribute("OrganizationCode"));
			eItemShipNode.setAttribute("ShipnodeKey", eCreateItemShipNode.getAttribute("ShipnodeKey"));
			run = true;
		}
		for (YFCElement eCreateItemShipNode : eItemShipNodes.getChildElement("Delete", true).getChildren()){
			YFCElement eApi = eMultiApi.createChild("API");
			eApi.setAttribute("Name", "deleteDistribution");
			YFCElement eItemShipNode = eApi.createChild("Input").createChild("ItemShipNode");
			eItemShipNode.setAttribute("DistributionRuleId", eCreateItemShipNode.getAttribute("DistributionRuleId"));
			eItemShipNode.setAttribute("ItemId", "ALL");
			eItemShipNode.setAttribute("OwnerKey", eItemShipNodes.getAttribute("OrganizationCode"));
			eItemShipNode.setAttribute("ShipnodeKey", eCreateItemShipNode.getAttribute("ShipnodeKey"));
			run = true;
		}
		if(run){
			return callApi(env, dMultiApi.getDocument(), null, "multiApi");
		}
		
		return dMultiApi.getDocument();
	}
}
