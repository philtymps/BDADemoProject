package com.extension.bda.service.fulfillment;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDACreateTransfers extends BDAServiceApi{

		public BDACreateTransfers(){
			super();
		}
		
		public Document createTransferRules(YFSEnvironment env, Document docInput) throws Exception {
			YFCDocument dInput = YFCDocument.getDocumentFor(docInput);
			YFCElement eInput = dInput.getDocumentElement();
			
			if(validateInput(env, eInput)){
				YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
				YFCElement eMultiApi = dMultiApi.getDocumentElement();
				for(YFCElement eTransferNode : eInput.getChildElement("TransferNodes", true).getChildren()){
					createTransferRelationship(eMultiApi, eInput.getDoubleAttribute("DefaultTransitTime"), eInput.getBooleanAttribute("WeekendTransfers", true), eTransferNode.getAttribute("FromNode"), eTransferNode.getAttribute("ToNode"), eInput.getAttribute("TranSchCostPerUnit"), eInput.getAttribute("CostFactorCurrency"));
				}
				this.writeXML(getOutputLocation(), "TransferRule" + eInput.getAttribute("DistributionRuleId") + ".xml", dMultiApi);
				return callApi(env, dMultiApi.getDocument(), null, "multiApi");
			}
			
			throw new Exception ("Invalid Input Provided");
		}
		
		private Document getDistributionRuleListTemplate(){
			YFCDocument dOutput = YFCDocument.createDocument("DistributionRuleList");
			YFCElement eDistributionRule = dOutput.getDocumentElement().createChild("DistributionRule");
			eDistributionRule.setAttribute("DistributionRuleId", "");
			eDistributionRule.setAttribute("OwnerKey", "");
			YFCElement eItemShipNode = eDistributionRule.createChild("ItemShipNodes").createChild("ItemShipNode");
			eItemShipNode.setAttribute("ShipnodeKey", "");
			return dOutput.getDocument();
		}
		
		private boolean validateInput(YFSEnvironment env, YFCElement eInput){
			if(YFCCommon.isVoid(eInput.getAttribute("DefaultTransitTime"))) {
				return false;
			}
			
			if(!YFCCommon.isVoid(eInput.getAttribute("CallingOrganizationCode")) && !YFCCommon.isVoid(eInput.getAttribute("DistributionRuleId"))){
				YFCDocument dInput = YFCDocument.createDocument("DistributionRule");
				YFCElement eDistributionRule = dInput.getDocumentElement();
				eDistributionRule.setAttribute("CallingOrganizationCode", eInput.getAttribute("CallingOrganizationCode"));
				eDistributionRule.setAttribute("DistributionRuleId", eInput.getAttribute("DistributionRuleId"));
				Document dDRL = callApi(env, dInput.getDocument(), getDistributionRuleListTemplate(), "getDistributionRuleList");
				YFCElement eResponse = YFCDocument.getDocumentFor(dDRL).getDocumentElement();
				YFCElement eTransferNodes = eInput.getChildElement("TransferNodes", true);
				
				for(YFCElement eDR : eResponse.getChildren()){
					YFCElement eItemShipNodes = eDR.getChildElement("ItemShipNodes", true);
					
					ArrayList<String> aNodes = new ArrayList<String>();
				
					for(YFCElement eItemShipNode : eItemShipNodes.getChildren()){
						aNodes.add(eItemShipNode.getAttribute("ShipnodeKey"));
						if(!YFCCommon.isVoid(eInput.getAttribute("DistributionCenter"))){
							YFCElement eTransferNode = eTransferNodes.createChild("TransferNode");
							eTransferNode.setAttribute("ToNode", eItemShipNode.getAttribute("ShipnodeKey"));
							eTransferNode.setAttribute("FromNode", eInput.getAttribute("DistributionCenter"));
						}
					}
					
					for(int i = 0; i < (aNodes.size() - 1); i++){
						for(int j = i + 1; j < aNodes.size(); j++){
							YFCElement eTransferNode = eTransferNodes.createChild("TransferNode");
							eTransferNode.setAttribute("ToNode", aNodes.get(i));
							eTransferNode.setAttribute("FromNode", aNodes.get(j));
							eTransferNode = eTransferNodes.createChild("TransferNode");
							eTransferNode.setAttribute("ToNode", aNodes.get(j));
							eTransferNode.setAttribute("FromNode", aNodes.get(i));
						}
					}
					
				}
					
			}
			YFCElement eTransferNodes = eInput.getChildElement("TransferNodes");
			if(!YFCCommon.isVoid(eTransferNodes)){
				System.out.println(eInput);
				return eTransferNodes.hasChildNodes();
			}
			
			return false;
			
		}
		
		private void createTransferRelationship(YFCElement eMultiApi, double defaultTransitTime, boolean weekendTransfers, String sFromNode, String sToNode, String sCostUnit, String sCostCurrency){
			YFCElement eApi = eMultiApi.createChild("API");
			eApi.setAttribute("Name", "manageNodeTransferSchedule");
			YFCElement eInput = eApi.createChild("Input").createChild("NodeTransferSchedule");
			eInput.setAttribute("EffectiveFromDate", YDate.LOW_DATE);
			eInput.setAttribute("EffectiveToDate", YDate.HIGH_DATE);
			eInput.setAttribute("DefaultTransitTime", defaultTransitTime);
			eInput.setAttribute("FridayShip", true);
			eInput.setAttribute("ThursdayShip", true);
			eInput.setAttribute("WednesdayShip", true);
			eInput.setAttribute("TuesdayShip", true);
			eInput.setAttribute("MondayShip", true);
			eInput.setAttribute("SaturdayShip", weekendTransfers);
			eInput.setAttribute("SundayShip", weekendTransfers);
			eInput.setAttribute("FromNode", sFromNode);
			eInput.setAttribute("ToNode", sToNode);
			if(!YFCCommon.isVoid(sCostUnit) && !YFCCommon.isVoid(sCostCurrency)){
				eInput.setAttribute("CostFactorCurrency", sCostCurrency);
				eInput.setAttribute("TranSchCostPerUnit", sCostUnit);
			}
		}
}
