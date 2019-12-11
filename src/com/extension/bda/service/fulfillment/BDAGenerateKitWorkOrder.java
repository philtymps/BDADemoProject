package com.extension.bda.service.fulfillment;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAGenerateKitWorkOrder implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "generateKitWorkOrder";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub

	}
	
	private static Document getOrderDetails() {
		YFCDocument dOrder = YFCDocument.createDocument("Order");
		YFCElement eOrder = dOrder.getDocumentElement();
		eOrder.setAttribute("OrderHeaderKey", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("OrderNo", "");
		YFCElement eLine = eOrder.getChildElement("OrderLines", true).createChild("OrderLine");
		eLine.setAttribute("OrderLineKey", "");
		eLine.setAttribute("OrderedQty", "");
		eLine.setAttribute("ItemGroupCode", "");
		eLine.setAttribute("KitCode", "");
		eLine.setAttribute("KitQty", "");
		YFCElement eWorkOrder = eLine.createChild("WorkOrders").createChild("WorkOrder");
		eWorkOrder.setAttribute("ServiceItemID", "");
		eWorkOrder.setAttribute("WorkOrderKey", "");
		eWorkOrder.setAttribute("Status", "");
		YFCElement eBundleParent = eLine.createChild("BundleParentLine");
		eBundleParent.setAttribute("OrderLineKey", "");
		YFCElement eItem = eLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		
		
		return dOrder.getDocument();
	}
	
	/*
	 * <WorkOrder EnterpriseCode="AuroraB2B" ItemID="NordstromDress" NodeKey="Aurora_WH1" 
			OrderLineKey="2018062616373627612100" Priority="2" ProviderOrganizationCode="AuroraB2B"
			QuantityRequested="1" Purpose="STOCK" ServiceItemGroupCode="KIT" ServiceItemID="NordstromKitting"
			ShipByDate="2018-07-05">
			<Order OrderHeaderKey="2018062616172027611758" />
			<WorkOrderComponents>
				<WorkOrderComponent ComponentQuantity="1" ItemID="AuroraWMDRS-125" Uom="EACH" />
				<WorkOrderComponent ComponentQuantity="1" ItemID="Hanger" Uom="EACH" />
				<WorkOrderComponent ComponentQuantity="1" ItemID="PBag" Uom="EACH" />
				<WorkOrderComponent ComponentQuantity="1" ItemID="Cardboards" Uom="EACH" />								
			</WorkOrderComponents>
		</WorkOrder>	
	(non-Javadoc)
	 * @see com.extension.bda.service.IBDAService#invoke(com.yantra.yfs.japi.YFSEnvironment, org.w3c.dom.Document)
	 */

	private void addComponentLines(YFCElement eWorkOrder, YFCElement eLines, YFCElement eParentLine) {
		for(YFCElement eLine : eLines.getChildren()) {
			if(!YFCCommon.isVoid(eLine.getChildElement("BundleParentLine")) && YFCCommon.equals(eParentLine.getAttribute("OrderLineKey"), eLine.getChildElement("BundleParentLine").getAttribute("OrderLineKey"))) {
				YFCElement eC = eWorkOrder.getChildElement("WorkOrderComponents", true).createChild("WorkOrderComponent");
				YFCElement eItem = eLine.getChildElement("Item");
				eC.setAttribute("ItemID", eItem.getAttribute("ItemID"));
				eC.setAttribute("Uom", eItem.getAttribute("UnitOfMeasure"));
				eC.setAttribute("ComponentQuantity", eLine.getAttribute("OrderedQty"));
			}
		}
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		
		try {
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			YFCDocument dOrder = YFCDocument.createDocument("Order");
			YFCElement eOrder = dOrder.getDocumentElement();
			eOrder.setAttribute("OrderHeaderKey", input.getDocumentElement().getAttribute("OrderHeaderKey"));
			env.setApiTemplate("getOrderDetails", getOrderDetails());
			Document outputXml = localApi.invoke(env, "getOrderDetails", dOrder.getDocument());
			
			YFCDocument dResponse = YFCDocument.getDocumentFor(outputXml);
			eOrder = dResponse.getDocumentElement();
			
			for(YFCElement eLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
				if(YFCCommon.equals(eLine.getAttribute("KitCode"), "BUNDLE")) {
					boolean hasWorkOrder = false;
					for(YFCElement eWorkOrder : eLine.getChildElement("WorkOrders", true).getChildren()) {
						if(!YFCCommon.equals(eWorkOrder.getAttribute("Status"), "1600")){
							hasWorkOrder = true;
						}							
					}
					if(!hasWorkOrder) {
						YFCElement eItem = eLine.getChildElement("Item");
						File kitServiceItems = new File(BDAServiceApi.getScriptsPath(env) + "/kitServiceItems.xml");
						if(kitServiceItems.exists()){
							YFCDocument dBundleItemList = YFCDocument.getDocumentFor(kitServiceItems);
							for (YFCElement eBundleItem : dBundleItemList.getDocumentElement().getChildren()){
								if (eItem.getAttribute("ItemID").contains(eBundleItem.getAttribute("ItemID"))){
									YFCDocument dWorkOrder = YFCDocument.createDocument("WorkOrder");
									YFCElement eWorkOrder = dWorkOrder.getDocumentElement();
									eWorkOrder.setAttribute("EnterpriseCode", eOrder.getAttribute("EnterpriseCode"));
									eWorkOrder.setAttribute("ItemID", eItem.getAttribute("ItemID"));
									eWorkOrder.setAttribute("NodeKey", eBundleItem.getAttribute("NodeKey", "Aurora_WH1"));
									eWorkOrder.setAttribute("OrderLineKey", eLine.getAttribute("OrderLineKey"));
									eWorkOrder.setAttribute("Priority", eBundleItem.getAttribute("Priority", "2"));
									eWorkOrder.setAttribute("ProviderOrganizationCode", eOrder.getAttribute("EnterpriseCode"));
									eWorkOrder.setAttribute("QuantityRequested", eLine.getAttribute("OrderedQty"));
									eWorkOrder.setAttribute("Purpose", eBundleItem.getAttribute("Purpose", "SHIP"));
									eWorkOrder.setAttribute("ServiceItemGroupCode", eBundleItem.getAttribute("ServiceItemGroupCode", "KIT"));
									eWorkOrder.setAttribute("ServiceItemID", eBundleItem.getAttribute("ServiceItemID"));
									eWorkOrder.createChild("Order").setAttribute("OrderHeaderKey", eOrder.getAttribute("OrderHeaderKey"));
									addComponentLines(eWorkOrder, eOrder.getChildElement("OrderLines", true), eLine);
									
									try {
										localApi.invoke(env, "createWorkOrder", dWorkOrder.getDocument());
									} catch (Exception l) {
										l.printStackTrace();
									}
								}
							}
						}
					}
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

}
