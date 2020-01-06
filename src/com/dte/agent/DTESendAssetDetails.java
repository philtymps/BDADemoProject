package com.dte.agent;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.ycp.japi.util.YCPBaseTaskAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class DTESendAssetDetails extends YCPBaseTaskAgent {
	
	/*
	 *
	 * <?xml version="1.0" encoding="UTF-8"?>
		<TaskQueue 
		DataKey="2017111300523176633" DataType="OrderHeaderKey" HoldFlag="N"
		TaskQKey="2017111300523476638" TransactionKey="2017111223535276390">
   			<TransactionFilters Action="Get" DAYS_TO_WAIT="30" DocumentParamsKey="0001" 
   			DocumentType="0001" NumRecordsToBuffer="5000" ProcessType="ORDER_FULFILLMENT" 
   			ProcessTypeKey="ORDER_FULFILLMENT" TransactionId="DELETE_DRAFT_ORDER_AUTO.0001.ex" TransactionKey="2017111223535276390" />
		</TaskQueue>
	 */
	
	@Override
	public Document executeTask(YFSEnvironment env, Document doc) throws Exception {
		YFCDocument dTaskQueue = YFCDocument.getDocumentFor(doc);
		YFCElement eTaskQueue = dTaskQueue.getDocumentElement();
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrderHeaderKey", eTaskQueue.getAttribute("DataKey"));
		Document dOrder = BDAServiceApi.callApi(env, dInput.getDocument(), getOrderDetailTemplate(), "getCompleteOrderDetails");
		
		System.out.println("Input executeTask: " + YFCDocument.getDocumentFor(doc));
		deleteTask(env, eTaskQueue.getAttribute("TaskQKey"));
		return doc;
	}
	
	private Document getOrderDetailTemplate() {
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderNo", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		eOrder.setAttribute("CustomerEMailID", "");
		eOrder.setAttribute("CustomerFirstName", "");
		eOrder.setAttribute("CustomerLastName", "");
		eOrder.setAttribute("OrderType", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("OrderedQty", "");
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("SubLineNo", "");
		eOrderLine.setAttribute("ItemGroupCode", "");
		eOrderLine.setAttribute("KitCode", "");
		eOrderLine.setAttribute("Status", "");
		eOrderLine.setAttribute("StatusQuantity", "");
		YFCElement eItemDetails = eOrderLine.createChild("ItemDetails");
		eItemDetails.setAttribute("ItemID", "");
		eItemDetails.setAttribute("UnitOfMeasure", "");
		eItemDetails.setAttribute("ItemKey", "");
		YFCElement ePrimaryInfo = eItemDetails.createChild("PrimaryInformation");
		ePrimaryInfo.setAttribute("ShortDescription", "");
		ePrimaryInfo.setAttribute("ItemType", "");
		ePrimaryInfo.setAttribute("KitCode", "");
		ePrimaryInfo.setAttribute("ManufacturerName", "");
		YFCElement eAsset = eItemDetails.createChild("AssetList").createChild("Asset");
		eAsset.setAttribute("AssetID", "");
		eAsset.setAttribute("Type", "");
		eAsset.setAttribute("ContentID", "");
		eAsset.setAttribute("Description", "");
		eAsset.setAttribute("Label", "");
		eAsset.setAttribute("ContentLocation", "");
		return dOrder.getDocument();
	}
	
	private boolean deleteTask(YFSEnvironment env, String taskQKey) {
		YFCDocument dInput = YFCDocument.createDocument("TaskQueue");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("TaskQKey", taskQKey);
		eInput.setAttribute("Operation", "Delete");
		try {
			BDAServiceApi.callApi(env, dInput.getDocument(), null, "manageTaskQueue");
		} catch(Exception e) {
			return false;
		}
		return true;
	}

}
