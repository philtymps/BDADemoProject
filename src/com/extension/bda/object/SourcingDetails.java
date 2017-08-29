package com.extension.bda.object;

import java.util.ArrayList;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class SourcingDetails {

	private String sDistributionRuleId, sFromNodeKey, sTemplateType = "";
	private ArrayList<String> nodeTypes;
	private boolean bProcureToShipAllowed = false, bSubstitutionAllowed = false, bWorkOrderCreationAllowed = false, bSmartSourcing = true, bExpandToMinimize = false;
	private int SeqNo;
	
	
	public SourcingDetails(String sSourcingRuleDetailsKey){
		
	}
	
	public SourcingDetails(YFCElement eInput){
		nodeTypes = new ArrayList<String>();
		if(YFCCommon.equals(sTemplateType, "All_Nodes_Of_Type")){
			YFCElement eNodeTypes = eInput.getChildElement("SourcingTemplate", true).getChildElement("NodeTypes", true);
			for(YFCElement eNodeType : eNodeTypes.getChildren()){
				nodeTypes.add(eNodeType.getAttribute("NodeTypeValue"));
			}		
		} else if (YFCCommon.equals(sTemplateType, "Distribution_Group")){
			sDistributionRuleId = eInput.getAttribute("DistributionRuleId");
		} else if (YFCCommon.equals(sTemplateType, "Specific_Node")){
			sFromNodeKey = eInput.getAttribute("FromNodeKey");
		}
		this.bProcureToShipAllowed = eInput.getBooleanAttribute("ProcureToShipAllowed", false);
		this.bSubstitutionAllowed = eInput.getBooleanAttribute("SubstitutionAllowed", false);
		this.bWorkOrderCreationAllowed = eInput.getBooleanAttribute("WorkOrderCreationAllowed", false);
		this.bSmartSourcing = eInput.getChildElement("SourcingTemplate", true).getChildElement("AdditionalData", true).getBooleanAttribute("IsSmartSourcingAllowed", true);
		this.bExpandToMinimize = eInput.getChildElement("SourcingTemplate", true).getChildElement("AdditionalData", true).getBooleanAttribute("ExpandToMinimizeNumOfShipments", false);;
	}
	public SourcingDetails(String sTemplateType, String sIdentifier, int SequenceNo, boolean bProcureToShipAllowed, boolean bSubstituionAllowd, boolean bWorkOrderCreationAllowed, boolean bSmartSourcing, boolean bExpandToMinimize){
		nodeTypes = new ArrayList<String>();
		if(YFCCommon.equals(sTemplateType, "All_Nodes_Of_Type")){
			nodeTypes.add(sIdentifier);
		} else if (YFCCommon.equals(sTemplateType, "Distribution_Group")){
			sDistributionRuleId = sIdentifier;
		} else if (YFCCommon.equals(sTemplateType, "Specific_Node")){
			sFromNodeKey = sIdentifier;
		}
		this.bProcureToShipAllowed = bProcureToShipAllowed;
		this.bSubstitutionAllowed = bSubstituionAllowd;
		this.bWorkOrderCreationAllowed = bWorkOrderCreationAllowed;
		this.bSmartSourcing = bSmartSourcing;
		this.bExpandToMinimize = bExpandToMinimize;
	}
	
	public YFCElement getPackageSourcing(){
		YFCDocument dSequencedSourcing = YFCDocument.createDocument("SequencedSourcing");
		YFCElement eOutput = dSequencedSourcing.getDocumentElement();
		eOutput.setAttribute("TemplateType", sTemplateType);
		eOutput.setAttribute("DistributionRuleId", sDistributionRuleId);
		eOutput.setAttribute("FromNodeKey", sFromNodeKey);
		if (!YFCCommon.isVoid(nodeTypes)){
			for (String sNodeType : nodeTypes){
				YFCElement eNodeType = eOutput.getChildElement("SourcingTemplate", true).getChildElement("NodeTypes", true).createChild("NodeType");
				eNodeType.setAttribute("NodeTypeValue", sNodeType);
			}			
		}
		eOutput.setAttribute("ProcureToShipAllowed", bProcureToShipAllowed);
		eOutput.setAttribute("SubstitutionAllowed", bSubstitutionAllowed);
		eOutput.setAttribute("WorkOrderCreationAllowed", bWorkOrderCreationAllowed);
		YFCElement eAdditionalData = eOutput.getChildElement("SourcingTemplate", true).getChildElement("AdditionalData", true);
		eAdditionalData.setAttribute("IsSmartSourcingAllowed", bSmartSourcing);
		eAdditionalData.setAttribute("ExpandToMinimizeNumOfShipments", bExpandToMinimize);
		return eOutput;
	}
	
}
