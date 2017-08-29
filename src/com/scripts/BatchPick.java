package com.scripts;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

import edu.emory.mathcs.backport.java.util.Collections;

public class BatchPick {

	public BatchPick(){
		
	}
	
	
	private Document getShipmentLineListTemplate(boolean includeAttributes){
		YFCDocument dOutput = YFCDocument.createDocument("ShipmentLines");
		YFCElement eShipmentLines = dOutput.getDocumentElement();
		YFCElement eShipmentLine = eShipmentLines.createChild("ShipmentLine");
		eShipmentLine.setAttribute("ActualQuantity", "");
		eShipmentLine.setAttribute("BackroomPickedQuantity", "");
		eShipmentLine.setAttribute("ItemID", "");
		eShipmentLine.setAttribute("LevelOfService", "");
		eShipmentLine.setAttribute("UnitOfMeasure","");
		eShipmentLine.setAttribute("OrderLineKey", "");
		eShipmentLine.setAttribute("OrderHeaderKey", "");
		eShipmentLine.setAttribute("OverShipQuantity", "");
		eShipmentLine.setAttribute("PlacedQuantity", "");
		eShipmentLine.setAttribute("Quantity", "");
		eShipmentLine.setAttribute("ReceivedQuantity", "");
		eShipmentLine.setAttribute("ShipmentKey", "");
		eShipmentLine.setAttribute("ShipmentLineKey", "");
		eShipmentLine.setAttribute("ShipmentLineNo", "");
		eShipmentLine.setAttribute("ShortageQty", "");
		eShipmentLine.setAttribute("ShipmentSubLineNo", "");
		eShipmentLine.setAttribute("Status","");
		YFCElement eShipment = eShipmentLine.createChild("Shipment");
		eShipment.setAttribute("ShipNode", "");
		eShipment.setAttribute("ShipDate", "");
		eShipment.setAttribute("DeliveryMethod","");
		eShipment.setAttribute("Status", "");
		eShipment.setAttribute("ExpectedShipmentDate","");
		eShipment.setAttribute("EnterpriseCode", "");
		if(includeAttributes){
			YFCElement eOrderLine = eShipmentLine.createChild("OrderLine");
			eOrderLine.setAttribute("OrderLineKey","");
			eOrderLine.setAttribute("ItemGroupCode", "");
			YFCElement eItemDetails = eOrderLine.createChild("ItemDetails");
			eItemDetails.setAttribute("ItemID","");
			YFCElement ePrimaryInformation = eItemDetails.createChild("PrimaryInformation");
			ePrimaryInformation.setAttribute("ExtendedDisplayDescription","");
			ePrimaryInformation.setAttribute("ShortDescription","");
			ePrimaryInformation.setAttribute("ImageLocation","");
			ePrimaryInformation.setAttribute("ImageID", "");
			YFCElement eItemAttributeGroupType = eItemDetails.getChildElement("ItemAttributeGroupTypeList", true).getChildElement("ItemAttributeGroupType", true);
			eItemAttributeGroupType.setAttribute("ClassificationPurposeCode", "");
			YFCElement eItemAttributeGroup = eItemAttributeGroupType.getChildElement("ItemAttributeGroupList", true).getChildElement("ItemAttributeGroup", true);
			eItemAttributeGroup.setAttribute("ItemAttributeGroupID", "");
			YFCElement eItemAttribute = eItemAttributeGroup.getChildElement("ItemAttributeList", true).getChildElement("ItemAttribute", true);
			eItemAttribute.setAttribute("Value", "");
			eItemAttribute.setAttribute("ItemAttributeName", "");
			eItemAttribute.setAttribute("ItemAttributeDescription", "");
			YFCElement eAssignedValue = eItemAttribute.getChildElement("AssignedValueList", true).getChildElement("AssignedValue", true);
			eAssignedValue.setAttribute("Value", "");
			eAssignedValue.setAttribute("ShortDescription", "");
		}

		return dOutput.getDocument();
	}
	
	
	public Document getBatchPickDetails(YFSEnvironment env, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("BatchPick");
		YFCElement eOutput = dOutput.getDocumentElement();
		if (!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eInput = dInput.getDocumentElement();
			
			if (!YFCCommon.isVoid(eInput.getAttribute("ShipNode"))){
				HashMap<String, HashMap<String, Double>> temp = new HashMap<String, HashMap<String, Double>>();
				try {
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document shipmentLineListOutput = null;
					env.setApiTemplate("getShipmentLineList", getShipmentLineListTemplate(true));
					shipmentLineListOutput = localApi.invoke(env, "getShipmentLineList", getShipNodeShipmentList(eInput.getAttribute("ShipNode"), null, null).getDocument());
					if (!YFCCommon.isVoid(shipmentLineListOutput)){
						YFCElement eGetShipmentLinesOutput = YFCDocument.getDocumentFor(shipmentLineListOutput).getDocumentElement();
						for (YFCElement eShipmentLine : eGetShipmentLinesOutput.getChildren()){
							
							if(eShipmentLine.getChildElement("Shipment", true).getAttribute("Status").indexOf("1100.70.06.10") > -1 || eShipmentLine.getChildElement("Shipment", true).getAttribute("Status").indexOf("1100.70.06.20") > -1){
								HashMap<String, Double> items = temp.get(eShipmentLine.getAttribute("ItemID"));
								if(YFCCommon.isVoid(items)){
									items = new HashMap<String, Double>();
									temp.put(eShipmentLine.getAttribute("ItemID"), items);
								}
								String sDeliveryMethod = eShipmentLine.getChildElement("Shipment", true).getAttribute("DeliveryMethod", "SHP");
								if(items.containsKey(sDeliveryMethod)){
									double quantity = items.get(sDeliveryMethod);
									double remaining = eShipmentLine.getDoubleAttribute("Quantity", 0) - eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0);
									items.put(sDeliveryMethod, (quantity + remaining));
								} else {
									double remaining = eShipmentLine.getDoubleAttribute("Quantity", 0) - eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0);
									items.put(sDeliveryMethod, remaining);
								}
								
							}
						}
					}
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
			
				eOutput.setAttribute("UniqueItems", temp.size());
				int pickup = 0;
				int ship = 0;
				double pickupQty = 0;
				double shipQty = 0;
				for(String sItemID : temp.keySet()){
					HashMap<String, Double> methodCount = temp.get(sItemID);
					if(methodCount.containsKey("SHP")){
						ship++;
						shipQty += methodCount.get("SHP");
					}
					if(methodCount.containsKey("PICK")){
						pickup++;
						pickupQty += methodCount.get("PICK");
					}
				}
				eOutput.setAttribute("UniquePickup", pickup);
				eOutput.setAttribute("UniqueShip", ship);
				eOutput.setAttribute("PickupQty", pickupQty);
				eOutput.setAttribute("ShipQty", shipQty);
			}
		}
		return dOutput.getDocument();
	}
	
	public Document getShipmentLinesForPick(YFSEnvironment env, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("ShipmentLines");
		if (!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eInput = dInput.getDocumentElement();
			
			if (!YFCCommon.isVoid(eInput.getAttribute("ShipNode"))){
	
				try {
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document shipmentLineListOutput = null;
					env.setApiTemplate("getShipmentLineList", getShipmentLineListTemplate(true));
					shipmentLineListOutput = localApi.invoke(env, "getShipmentLineList", getShipNodeShipmentList(eInput.getAttribute("ShipNode"), null, null).getDocument());
					if (!YFCCommon.isVoid(shipmentLineListOutput)){
						YFCElement eShipmentLines = dOutput.getDocumentElement();
						YFCElement eGetShipmentLinesOutput = YFCDocument.getDocumentFor(shipmentLineListOutput).getDocumentElement();
						String sCurrentItem = "";
						String sCurrentUOM = "";
						YFCElement eCurrentLine = null;
						for (YFCElement eShipmentLine : eGetShipmentLinesOutput.getChildren()){
							if(eShipmentLine.getChildElement("Shipment", true).getAttribute("Status").indexOf("1100.70.06.10") > -1 || eShipmentLine.getChildElement("Shipment", true).getAttribute("Status").indexOf("1100.70.06.20") > -1){
								if (!YFCCommon.equals(eShipmentLine.getAttribute("ItemID"), sCurrentItem)
										|| !YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), sCurrentUOM)
										|| (!YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), "EACH") && !YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), ""))){
									eCurrentLine = (YFCElement) eShipmentLines.importNode(eShipmentLine.cloneNode(true));
									handleAttributeList(eCurrentLine);
									sCurrentItem = eShipmentLine.getAttribute("ItemID");
									sCurrentUOM = eShipmentLine.getAttribute("UnitOfMeasure");
									eCurrentLine.setAttribute("ExpectedShipmentDate", eShipmentLine.getChildElement("Shipment", true).getAttribute("ExpectedShipmentDate"));
									eCurrentLine.setAttribute("ContainsShipping",  "N");
									eCurrentLine.setAttribute("ContainsPickup", "N");
									if(YFCCommon.equals(eShipmentLine.getChildElement("Shipment", true).getAttribute("DeliveryMethod", "SHP"), "SHP")){
										eCurrentLine.setAttribute("ContainsShipping", "Y");
										if(!YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), "EACH") && !YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), "")){
											eCurrentLine.setAttribute("ShowImage", "cut");
										}
									} else if(YFCCommon.equals(eShipmentLine.getChildElement("Shipment", true).getAttribute("DeliveryMethod", "SHP"), "PICK")){
										eCurrentLine.setAttribute("ContainsPickup", "Y");
										if(!YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), "EACH") && !YFCCommon.equals(eShipmentLine.getAttribute("UnitOfMeasure"), "")){
											eCurrentLine.setAttribute("ShowImage", "donotcut");
										}
									}
								} else {
									eCurrentLine.setAttribute("Quantity", eCurrentLine.getDoubleAttribute("Quantity",0) + eShipmentLine.getDoubleAttribute("Quantity",0));
									eCurrentLine.setAttribute("BackroomPickedQuantity", eCurrentLine.getDoubleAttribute("BackroomPickedQuantity",0) + eShipmentLine.getDoubleAttribute("BackroomPickedQuantity",0));
									eCurrentLine.setAttribute("ActualQuantity", eCurrentLine.getDoubleAttribute("ActualQuantity",0) + eShipmentLine.getDoubleAttribute("ActualQuantity",0));
									eCurrentLine.setAttribute("ShortageQty", eCurrentLine.getDoubleAttribute("ShortageQty",0) + eShipmentLine.getDoubleAttribute("ShortageQty",0));
									eCurrentLine.setAttribute("OverShipQuantity", eCurrentLine.getDoubleAttribute("OverShipQuantity",0) + eShipmentLine.getDoubleAttribute("OverShipQuantity",0));
									eCurrentLine.setAttribute("PlacedQuantity", eCurrentLine.getDoubleAttribute("PlacedQuantity",0) + eShipmentLine.getDoubleAttribute("PlacedQuantity",0));
									eCurrentLine.setAttribute("ReceivedQuantity", eCurrentLine.getDoubleAttribute("ReceivedQuantity",0) + eShipmentLine.getDoubleAttribute("ReceivedQuantity",0));
									if(YFCCommon.equals(eShipmentLine.getChildElement("Shipment", true).getAttribute("DeliveryMethod", "SHP"), "SHP")){
										eCurrentLine.setAttribute("ContainsShipping", "Y");
									} else if(YFCCommon.equals(eShipmentLine.getChildElement("Shipment", true).getAttribute("DeliveryMethod", "SHP"), "PICK")){
										eCurrentLine.setAttribute("ContainsPickup", "Y");
									}
								}
								if(eShipmentLine.getChildElement("Shipment", true).getDateTimeAttribute("ExpectedShipmentDate", YFCDate.HIGH_DATE).before(eCurrentLine.getDateAttribute("ExpectedShipmentDate", YFCDate.HIGH_DATE))){
									eCurrentLine.setAttribute("ExpectedShipmentDate", eShipmentLine.getChildElement("Shipment", true).getAttribute("ExpectedShipmentDate"));
								}
							}
						}
						
					}
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
		sortElements(dOutput.getDocumentElement(), "ExpectedShipmentDate");
		return dOutput.getDocument();
	}
	
	
	private void sortElements(YFCElement eParent, String sAttribute){
		ArrayList<YFCElement> children = new ArrayList<YFCElement>();
		for(YFCElement child : eParent.getChildren()){
			children.add((YFCElement) child.cloneNode(true));
		}
		Collections.sort(children, new Comparator<YFCElement>(){
			@Override
			public int compare(YFCElement o1, YFCElement o2) {
				if(o1.getDateAttribute("ExpectedShipmentDate").before(o2.getDateAttribute("ExpectedShipmentDate")))
					return -1;
				else if(o1.getDateAttribute("ExpectedShipmentDate").after(o2.getDateAttribute("ExpectedShipmentDate")))
					return 1;
				else
					return 0;
			}
		});
		for(YFCElement eChild : eParent.getChildren()){
			eParent.removeChild(eChild);
		}
		for(int i = 0; i < children.size(); i++){
			eParent.appendChild(children.get(i));
		}
	}
	private void handleAttributeList(YFCElement eCurrentLine){
		YFCElement eAttributeGroupTypeList = eCurrentLine.getChildElement("OrderLine", true).getChildElement("ItemDetails", true).getChildElement("ItemAttributeGroupTypeList");
		if(!YFCCommon.isVoid(eAttributeGroupTypeList)){
			YFCElement eAttributeList = null;
			for (YFCElement eAttributeGroupType : eAttributeGroupTypeList.getChildren()){
				if(YFCCommon.equals(eAttributeGroupType.getAttribute("ClassificationPurposeCode"), "DISTINCT_ATTRIBUTES")){
					YFCElement eAttributeGroupList = eAttributeGroupType.getChildElement("ItemAttributeGroupList");
					if(!YFCCommon.isVoid(eAttributeGroupList)){
						for (YFCElement eAttributeGroup : eAttributeGroupList.getChildren()){
							if(YFCCommon.isVoid(eAttributeList)){
								eAttributeList = eAttributeGroup.getChildElement("ItemAttributeList", true);
							} else {
								for(YFCElement eAttribute : eAttributeGroup.getChildElement("ItemAttributeList", true).getChildren()){
									eAttributeList.importNode(eAttribute);
								}														
							}
						}												
					}
				}
			}
			YFCElement eItemDetails = eCurrentLine.getChildElement("OrderLine", true).getChildElement("ItemDetails", true);
			if (!YFCCommon.isVoid(eAttributeList)){
				eItemDetails.importNode(eAttributeList);
			}
			eItemDetails.removeChild(eAttributeGroupTypeList);
		}
	}
	
	private YFCDocument getShipNodeShipmentList(String sShipNode, String sItemID, String sUnitOfMeasure){
		YFCDocument dInput = YFCDocument.createDocument("ShipmentLine");
		YFCElement eShipmentLine = dInput.getDocumentElement();
		eShipmentLine.setAttribute("IsPickable", "N");
		eShipmentLine.setAttribute("IsPickableQryType", "NE");
		if(!YFCCommon.isVoid(sItemID)){
			eShipmentLine.setAttribute("ItemID", sItemID);
		}
		if(!YFCCommon.isVoid(sUnitOfMeasure)){
			eShipmentLine.setAttribute("UnitOfMeasure", sUnitOfMeasure);
		}
		YFCElement eShipment = eShipmentLine.createChild("Shipment");
		eShipment.setAttribute("ShipNode", sShipNode);
		eShipment.setAttribute("Status","1100.70");
		eShipment.setAttribute("StatusQryType","FLIKE");
		YFCElement eOrderBy = eShipmentLine.createChild("OrderBy");
		YFCElement eAttr = eOrderBy.createChild("Attribute");
		eAttr.setAttribute("Name", "ItemID");
		eAttr.setAttribute("Desc", "N");
		eAttr = eOrderBy.createChild("Attribute");
		eAttr.setAttribute("Name", "UnitOfMeasure");
		eAttr.setAttribute("Desc", "N");
		eAttr = eOrderBy.createChild("Attribute");
		eAttr.setAttribute("Name", "Createts");
		eAttr.setAttribute("Desc", "N");
		return dInput;
	}


	private Document getShipmentDetailsTemplate(){
		YFCDocument dShipment = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dShipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey","");
		eShipment.setAttribute("DeliveryMethod", "");
		eShipment.setAttribute("Status", "");
		YFCElement eShipmentLine = eShipment.getChildElement("ShipmentLines", true).getChildElement("ShipmentLine", true);
		eShipmentLine.setAttribute("ActualQuantity", "");
		eShipmentLine.setAttribute("BackroomPickedQuantity", "");
		eShipmentLine.setAttribute("ItemID", "");
		eShipmentLine.setAttribute("LevelOfService", "");
		eShipmentLine.setAttribute("UnitOfMeasure","");
		eShipmentLine.setAttribute("OrderLineKey", "");
		eShipmentLine.setAttribute("OrderHeaderKey", "");
		eShipmentLine.setAttribute("OverShipQuantity", "");
		eShipmentLine.setAttribute("PlacedQuantity", "");
		eShipmentLine.setAttribute("Quantity", "");
		eShipmentLine.setAttribute("ReceivedQuantity", "");
		eShipmentLine.setAttribute("ShipmentKey", "");
		eShipmentLine.setAttribute("ShipmentLineKey", "");
		eShipmentLine.setAttribute("ShipmentLineNo", "");
		eShipmentLine.setAttribute("ShortageQty", "");
		eShipmentLine.setAttribute("ShipmentSubLineNo", "");
		return dShipment.getDocument();
	}
	
	public Document completeBackroomPick(YFSEnvironment env, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("Shipment");
		YFCElement eOutput = dOutput.getDocumentElement();
		eOutput.setAttribute("Completed", false);
		if(!YFCCommon.isVoid(inputDoc)){
			YFCElement eShipmentInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			if(!YFCCommon.isVoid(eShipmentInput.getAttribute("ShipmentKey"))){
				YFCDocument dInput = YFCDocument.createDocument("Shipment");
				YFCElement eShipment = dInput.getDocumentElement();
				eShipment.setAttribute("ShipmentKey", eShipmentInput.getAttribute("ShipmentKey"));
				try {	
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document shipment = null;
					env.setApiTemplate("getShipmentDetails", getShipmentDetailsTemplate());
					shipment = localApi.invoke(env, "getShipmentDetails", dInput.getDocument());
					if (!YFCCommon.isVoid(shipment)){
						dOutput = YFCDocument.getDocumentFor(shipment);
						eOutput = dOutput.getDocumentElement();
						if(eOutput.getAttribute("Status").indexOf("1100.70.06.10") > -1 || eOutput.getAttribute("Status").indexOf("1100.70.06.20") > -1){
							boolean complete = true;
							for (YFCElement eShipmentLine : eOutput.getChildElement("ShipmentLines", true).getChildren()){
								if (eShipmentLine.getDoubleAttribute("Quantity", 0) > (eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0) + eShipmentLine.getDoubleAttribute("ShortageQty", 0))){
									complete = false;
									break;
								}
							}
							if (complete){
								if (YFCCommon.equals(eOutput.getAttribute("DeliveryMethod", "PICK"),"PICK")){
									eShipment.setAttribute("BaseDropStatus", "1100.70.06.30");
								} else {
									eShipment.setAttribute("BaseDropStatus", "1100.70.06.50");
								}								
								eShipment.setAttribute("TransactionId", "YCD_BACKROOM_PICK");
								localApi.invoke(env, "changeShipmentStatus", dInput.getDocument());
								eOutput.setAttribute("Completed", true);
							} else {
								eOutput.setAttribute("Completed", false);
							}
						} else {
							eOutput.setAttribute("Completed", true);
						}
					}
				}catch (YIFClientCreationException e) {
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
		return dOutput.getDocument();
	}
	
	public Document shortItemFromNode(YFSEnvironment env, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("ShipmentLines");
		YFCElement eOutput = dOutput.getDocumentElement();
		if (!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eInput = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(eInput.getAttribute("ShipNode")) && !YFCCommon.isVoid(eInput.getAttribute("ItemID"))){
				try {	
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document shipmentList = null;
					env.setApiTemplate("getShipmentLineList", getShipmentLineListTemplate(false));
					shipmentList = localApi.invoke(env, "getShipmentLineList", getShipNodeShipmentList(eInput.getAttribute("ShipNode"), eInput.getAttribute("ItemID"), eInput.getAttribute("UnitOfMeasure")).getDocument());
					if (!YFCCommon.isVoid(shipmentList)){
						YFCElement eGetShipmentLinesOutput = YFCDocument.getDocumentFor(shipmentList).getDocumentElement();
						for(YFCElement eShipmentLine : eGetShipmentLinesOutput.getChildren()){
							if(eShipmentLine.getChildElement("Shipment").getAttribute("Status").indexOf("1100.70.06.10") > -1 || 
									eShipmentLine.getChildElement("Shipment").getAttribute("Status").indexOf("1100.70.06.20") > -1 || 
									eShipmentLine.getChildElement("Shipment").getAttribute("Status").indexOf("1100.70.10") > -1 || 
									eShipmentLine.getChildElement("Shipment").getAttribute("Status").indexOf("1100.70.22") > -1){
								if (eShipmentLine.getDoubleAttribute("Quantity", 0) > (eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0) + eShipmentLine.getDoubleAttribute("ShortageQty", 0))){
									double shortQty = eShipmentLine.getDoubleAttribute("Quantity", 0) - eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0);
									if(shortQty > 0){
										localApi.invoke(env, "changeShipment", shortPickLine(eShipmentLine.getAttribute("ShipmentKey"), eInput.getAttribute("ShipNode"), eShipmentLine.getAttribute("ShipmentLineNo"), eInput.getAttribute("ShortageReasonCode"), shortQty, eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0)));
										eShipmentLine.setAttribute("ShortageQty", shortQty);
										eOutput.importNode(eShipmentLine);
										completeBackroomPick(env, getShipmentInput(eShipmentLine.getAttribute("ShipmentKey")));	
									}
								}
							}
						}
					}
					localApi.invoke(env, "multiApi", getInventoryAjustmentInput(eInput.getAttribute("ItemID"), eInput.getAttribute("UnitOfMeasure"), eInput.getAttribute("ShipNode")));
					
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
		return dOutput.getDocument();
	}
	
	private static Document getInventoryAjustmentInput(String sItemID, String sUnitOfMeasure, String sShipNode){
		YFCDocument dOutput = YFCDocument.createDocument("MultiApi");
		YFCElement eMultiApi = dOutput.getDocumentElement();
		YFCElement eApi = eMultiApi.createChild("API");
		eApi.setAttribute("Name", "adjustInventory");
		YFCElement eItem = eApi.createChild("Input").createChild("Items").createChild("Item");
		eItem.setAttribute("ItemID", sItemID);
		eItem.setAttribute("UnitOfMeasure", sUnitOfMeasure);
		eItem.setAttribute("ShipNode", sShipNode);
		eItem.setAttribute("SupplyType", "ONHAND");
		eItem.setAttribute("Availability", "TRACK");
		eItem.setAttribute("AdjustmentType", "ABSOLUTE");
		eItem.setAttribute("Quantity", "0");
		eApi = eMultiApi.createChild("API");
		eApi.setAttribute("Name", "monitorItemAvailability");
		YFCElement eMonitor = eApi.createChild("Input").createChild("MonitorItemAvailability");
		eMonitor.setAttribute("ItemID", sItemID);
		eMonitor.setAttribute("UnitOfMeasure", sUnitOfMeasure);
		eMonitor.setAttribute("OrganizationCode", sShipNode);
		eMonitor.setAttribute("RaiseEventOnAllAvailabilityChanges", "Y");
		return dOutput.getDocument();
	}
	public Document pickItemForShipments(YFSEnvironment env, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("ShipmentLines");
		if (!YFCCommon.isVoid(inputDoc)){
			YFCDocument dInput = YFCDocument.getDocumentFor(inputDoc);
			YFCElement eInput = dInput.getDocumentElement();
			if(!YFCCommon.isVoid(eInput.getAttribute("ShipNode")) && !YFCCommon.isVoid(eInput.getAttribute("ItemID"))){
				ArrayList<YFCElement> l = null;
				String sUnitOfMeasure = eInput.getAttribute("UnitOfMeasure");
				try {	
					YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
					Document shipmentList = null;
					env.setApiTemplate("getShipmentLineList", getShipmentLineListTemplate(false));
					shipmentList = localApi.invoke(env, "getShipmentLineList", getShipNodeShipmentList(eInput.getAttribute("ShipNode"), eInput.getAttribute("ItemID"), eInput.getAttribute("UnitOfMeasure")).getDocument());
					if (!YFCCommon.isVoid(shipmentList)){
						YFCElement eGetShipmentLinesOutput = YFCDocument.getDocumentFor(shipmentList).getDocumentElement();
						for(YFCElement eShipmentLine : eGetShipmentLinesOutput.getChildren()){
							if(eShipmentLine.getChildElement("Shipment").getAttribute("Status").indexOf("1100.70.06.10") > -1 || eShipmentLine.getChildElement("Shipment").getAttribute("Status").indexOf("1100.70.06.20") > -1){
								sUnitOfMeasure = eShipmentLine.getAttribute("UnitOfMeasure");
								if(!YFCCommon.isVoid(l)){
									if (!YFCCommon.equals(eShipmentLine.getChildElement("Shipment",true).getAttribute("DeliveryMethod", "PICK"), "PICK")){
										l.add(eShipmentLine);
									} else {
										l.add(0, eShipmentLine);
									}
								} else {
									l = new ArrayList<YFCElement>();
									l.add(eShipmentLine);
								}
							}
						}
						double pickQuantity = eInput.getDoubleAttribute("BackroomPickedQuantity", -1);
						if(pickQuantity < 0){
							if(YFCCommon.isVoid(sUnitOfMeasure) || YFCCommon.equals(sUnitOfMeasure, "EACH") || YFCCommon.equals(sUnitOfMeasure, "")){
								pickQuantity = 1;
							} else {
								pickQuantity = l.get(0).getDoubleAttribute("Quantity", 0) - (l.get(0).getDoubleAttribute("BackroomPickedQuantity", 0) + l.get(0).getDoubleAttribute("ShortageQty", 0));
							}
						}
						for(int i = 0; i < l.size(); i++){
							if (pickQuantity > 0){
								YFCElement eShipmentLine = l.get(i);
								double availableQuantity = eShipmentLine.getDoubleAttribute("Quantity",0) - (eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0) + eShipmentLine.getDoubleAttribute("ShortageQty", 0));
								if(availableQuantity > 0){
									dOutput.getDocumentElement().importNode(eShipmentLine);
									if (pickQuantity >= availableQuantity){
										localApi.invoke(env, "changeShipment", backroomPickLine(eShipmentLine.getAttribute("ShipmentKey"), eInput.getAttribute("ShipNode"), eShipmentLine.getAttribute("ShipmentLineNo"), availableQuantity + eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0)));
										pickQuantity = pickQuantity - availableQuantity;
										completeBackroomPick(env, getShipmentInput(eShipmentLine.getAttribute("ShipmentKey")));
									} else {
										localApi.invoke(env, "changeShipment", backroomPickLine(eShipmentLine.getAttribute("ShipmentKey"), eInput.getAttribute("ShipNode"), eShipmentLine.getAttribute("ShipmentLineNo"), pickQuantity + eShipmentLine.getDoubleAttribute("BackroomPickedQuantity", 0)));
										pickQuantity = 0;
									}
								}
							}
						
						}
					}
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
		return dOutput.getDocument();
	}
	
	private Document getShipmentInput(String sShipmentKey){
		YFCDocument dShipment = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dShipment.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", sShipmentKey);
		return dShipment.getDocument();
	}
	
	private Document shortPickLine(String sShipmentKey, String sShipNode, String sShipmentLineNo, String sReasonCode, double quantity, double pickedQty){
		YFCDocument dInput = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dInput.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", sShipmentKey);
		eShipment.setAttribute("ShipNode", sShipNode);
		eShipment.setAttribute("BackOrderRemovedQuantity","Y");
		YFCElement eShipmentLine = eShipment.getChildElement("ShipmentLines", true).getChildElement("ShipmentLine", true);
		eShipmentLine.setAttribute("ShortageQty", quantity);
		eShipmentLine.setAttribute("Quantity", pickedQty);
		eShipmentLine.setAttribute("ShipmentLineNo", sShipmentLineNo);
		if(!YFCCommon.isVoid(sReasonCode)){
			YFCElement eAudit = eShipment.getChildElement("ShipmentStatusAudit", true);
			eAudit.setAttribute("ReasonCode", sReasonCode);
			eAudit.setAttribute("ReasonText", "Shorting " + quantity + " of this shipment line");
			eAudit.setDateAttribute("StatusDate", new YFCDate());
		}
		return dInput.getDocument();
	}
	
	private Document backroomPickLine(String sShipmentKey, String sShipNode, String sShipmentLineNo, double quantity){
		YFCDocument dInput = YFCDocument.createDocument("Shipment");
		YFCElement eShipment = dInput.getDocumentElement();
		eShipment.setAttribute("ShipmentKey", sShipmentKey);
		eShipment.setAttribute("ShipNode", sShipNode);
		YFCElement eShipmentLine = eShipment.getChildElement("ShipmentLines", true).getChildElement("ShipmentLine", true);
		eShipmentLine.setAttribute("BackroomPickedQuantity", quantity);
		eShipmentLine.setAttribute("ShipmentLineNo", sShipmentLineNo);
		return dInput.getDocument();
	}
	
}
