package com.ibm.mobile.dataprovider;

import com.yantra.interop.japi.YIFApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAServiceDataProvider implements IBDADataProvider {

	@Override
	public void addAdditionalData(YIFApi localApi, YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute) {
		if(interestingElement != null){
			if(interestingElement.getNodeName().equals("SubFlow")){
				if (interestingElement.hasAttribute("ConfigXML") && YFCCommon.isVoid(interestingElement.getChildElement("ConfigXML"))){
					YFCDocument dConfigXml = YFCDocument.getDocumentFor(interestingElement.getAttribute("ConfigXML"));
					interestingElement.removeAttribute("ConfigXML");
					interestingElement.setAttribute("ConvertedConfigXML", "Y");
					YFCElement eConfigXML = interestingElement.createChild("ConfigXML");
					eConfigXML.importNode(dConfigXml.getDocumentElement());
					YFCElement eFlowChartXML = interestingElement.createChild("FlowChartXML");
					getAngularModel(eConfigXML.getChildElement("SubFlowConfig", true), eFlowChartXML);
				}
			}
		}
		
	}
	
	private void getAngularModel(YFCElement eConfigXml, YFCElement eACX){
	 
		int i = 0;
		for(YFCElement eNode : eConfigXml.getElementsByTagName("Node")){
			YFCElement eNodes = eACX.createChild("nodes");
			eNodes.setAttribute("name", eNode.getAttribute("NodeText"));
			eNodes.setAttribute("type", eNode.getAttribute("NodeType"));
			eNodes.setAttribute("id", eNode.getAttribute("NodeId"));
			eNodes.setAttribute("x", i * 200);
			eNodes.setAttribute("y", "100");
			if(!eNodes.getAttribute("type").equals("Start")){
				YFCElement eInputConnectors = eNodes.createChild("inputConnectors");
				eInputConnectors.setAttribute("name", "In");
			}
			if(!eNodes.getAttribute("type").equals("End")){
				if(eNodes.getAttribute("type").equals("Condition")){
					YFCElement eInputConnectors = eNodes.createChild("outputConnectors");
					eInputConnectors.setAttribute("name", "true");
					eInputConnectors = eNodes.createChild("outputConnectors");
					eInputConnectors.setAttribute("name", "false");
				} else {
					YFCElement eInputConnectors = eNodes.createChild("outputConnectors");
					eInputConnectors.setAttribute("name", "Out");
				}
				
			}
			
		}
		for(YFCElement eConnection : eConfigXml.getElementsByTagName("Link")){
			YFCElement eConnections = eACX.createChild("connections");
			YFCElement eSource = eConnections.createChild("source");
			eSource.setAttribute("nodeID", eConnection.getAttribute("FromNode"));
			eSource.setAttribute("connectorIndex", 0);
			YFCElement eDest = eConnections.createChild("dest");
			eDest.setAttribute("nodeID", eConnection.getAttribute("ToNode"));
			eDest.setAttribute("connectorIndex", 0);
		}
		
	}
	

}
