package com.extension.bda.service.fulfillment;

import java.util.HashMap;

import org.w3c.dom.Document;
import com.extension.bda.entity.BDAEntityServices;
import com.extension.bda.entity.FulfillmentDetails;
import com.extension.bda.entity.FulfillmentPackage;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.ycp.core.YCPEntityApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNode;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDAFulfillmentPackageServices extends BDAServiceApi {

	public static void main(String[] args){
		BDAFulfillmentPackageServices fps = new BDAFulfillmentPackageServices();
		YFCDocument dInput = YFCDocument.createDocument("FulfillmentPackage");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrganizationCode", "Aurora");
		fps.getFulfillmentPackageList(null, dInput.getDocument());
		
	}
	public Document getFulfillmentPackageList(YFSEnvironment env, Document docInput) {
		return BDAEntityServices.getObjectList(env, docInput, "BDA_FULFILLMENT_PACKAGE");
	}
	
	public Document getFulfillmentPackage(YFSEnvironment env, Document docInput){
		if(!YFCCommon.isVoid(docInput)){
			YFCElement eInput = YFCDocument.getDocumentFor(docInput).getDocumentElement();
			FulfillmentPackage fp = null;
			if(eInput.hasAttribute("FulfillmentPackageKey")){
				fp = new FulfillmentPackage(env, eInput.getIntAttribute("FulfillmentPackageKey"));
			} else if(eInput.hasAttribute("OrganizationCode") && eInput.hasAttribute("FulfillmentType")){
				fp = new FulfillmentPackage(env, eInput.getAttribute("OrganizationCode"), eInput.getAttribute("FulfillmentType"));
			}
			if(fp != null){
				fp.loadSourcingRules(env);
				fp.loadAllocationRule(env);
				return fp.getXml().getOwnerDocument().getDocument();
			}
		}
		return null;
	}
	
	protected static Document manageSourcingRuleTemplate(){
		try{
			YFCDocument dDoc = YFCDocument.parse(BDAFulfillmentPackageServices.class.getResourceAsStream("manageSourcingRuleTemplate.xml"));
			return dDoc.getDocument();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Document saveFulfillmentPackage(YFSEnvironment env, Document docInput){
		if (!YFCCommon.isVoid(docInput)){
			YFCElement eInput = YFCDocument.getDocumentFor(docInput).getDocumentElement();
			FulfillmentPackage fp = null;
			HashMap<Integer, FulfillmentDetails> fds = new HashMap<Integer, FulfillmentDetails>();
			if(!YFCCommon.isVoid(eInput.getAttribute("FulfillmentPackageKey"))){
				fp = new FulfillmentPackage(env, eInput.getIntAttribute("FulfillmentPackageKey"));
			} else {
				YFCDocument dFulfillmentType = YFCDocument.createDocument("CommonCode");
				YFCElement eFulfillmentType = dFulfillmentType.getDocumentElement();
				eFulfillmentType.setAttribute("OrganizationCode", eInput.getAttribute("OrganizationCode"));
				eFulfillmentType.setAttribute("CodeType", "FULFILLMENT_TYPE");
				String id = eInput.getAttribute("PackageName").toUpperCase().replaceAll(" ", "_");
				if(id.length() > 24){
					id = id.substring(0, 23);
				}
				eFulfillmentType.setAttribute("CodeValue", id);
				eFulfillmentType.setAttribute("CodeShortDescription", eInput.getAttribute("PackageName"));
				Document dResponse = callApi(env, dFulfillmentType.getDocument(), null, "manageCommonCode");
				YFCElement eResponse = YFCDocument.getDocumentFor(dResponse).getDocumentElement();
				fp = new FulfillmentPackage(eInput.getAttribute("OrganizationCode"), eResponse.getAttribute("CodeValue"), eInput.getAttribute("PackageName"), eInput.getAttribute("AllocationRuleKey"), eInput.getAttribute("SourcingConfigKey"));
				fp.save(env);
			}
			
			
			if(!YFCCommon.isVoid(eInput.getChildElement("AllocationRule"))){
				YFCElement eAR = eInput.getChildElement("AllocationRule");
				if(!YFCCommon.isVoid(fp.getXml().getAttribute("AllocationRuleKey"))){
					eAR.setAttribute("AllocationRuleKey", fp.getXml().getAttribute("AllocationRuleKey"));
				} else {
					String id = eInput.getAttribute("PackageName").toUpperCase().replaceAll(" ", "_");
					if(id.length() > 24){
						id = id.substring(0, 23);
					}
					eAR.setAttribute("AllocationRuleId", id);
					eAR.setAttribute("OrganizationCode", eInput.getAttribute("OrganizationCode"));
					eAR.setAttribute("Description", eInput.getAttribute("PackageName"));

				
				}
				YFCDocument dAll = YFCDocument.createDocument();
				YFCNode n = dAll.importNode(eAR, true);
				dAll.appendChild(n);
				
				try {
					YCPEntityApi api = YCPEntityApi.getInstance();
					YFCDocument dResponse = api.invoke((YFSContext) env, "manageAllocationRule", dAll );
					if(!YFCCommon.isVoid(dResponse) && !YFCCommon.isVoid(dResponse.getDocumentElement().getAttribute("AllocationRuleKey"))){
						fp.updateAttribute("AllocationRuleKey", dResponse.getDocumentElement().getAttribute("AllocationRuleKey"));
					}			
				} catch (Exception e){
					System.out.println("Error Saving Allocation Rule");
				}
				
			}
			
			if(!YFCCommon.isVoid(eInput.getChildElement("SourcingRuleHeader"))){
				YFCElement eSRH = eInput.getChildElement("SourcingRuleHeader");
				eSRH.setAttribute("FulfillmentType", fp.getXml().getAttribute("FulfillmentType").trim());
				eSRH.setAttribute("OrganizationCode", fp.getXml().getAttribute("OrganizationCode").trim());
				if(YFCCommon.isVoid(eSRH.getAttribute("ItemGroupCode"))){
					eSRH.setAttribute("ItemGroupCode", "PROD");
				}
				if(YFCCommon.isVoid(eSRH.getAttribute("Purpose"))){
					eSRH.setAttribute("Purpose", "SOURCING");
				}
				YFCElement eSRDs = eSRH.getChildElement("SourcingRuleDetails", true);
				eSRDs.setAttribute("Reset", "Y");
				int seq = 1;
				for (YFCElement eSRD : eSRDs.getChildren()){
					eSRD.setAttribute("SeqNo", seq);
					fds.put(seq, new FulfillmentDetails(eSRD, seq, fp.getPrimaryKey()));
					seq++;
				}
				YFCDocument dSourcingRuleHeader = YFCDocument.createDocument();
				YFCNode n = dSourcingRuleHeader.importNode(eSRH, true);
				dSourcingRuleHeader.appendChild(n);
				Document dOutput = callApi(env, dSourcingRuleHeader.getDocument(), manageSourcingRuleTemplate(), "manageSourcingRule");
				eSRH = YFCDocument.getDocumentFor(dOutput).getDocumentElement();
				fp.updateAttribute("SourcingRuleHeaderKey", eSRH.getAttribute("SourcingRuleHeaderKey"));
				for (YFCElement eSRD : eSRH.getChildElement("SourcingRuleDetails", true).getChildren()){
					fds.get(eSRD.getIntAttribute("SeqNo")).updateSourcingRuleDetailKey(eSRD.getAttribute("SourcingRuleDetailKey"));
				}
				for (FulfillmentDetails fd : fds.values()){
					fd.save(env);
				}
			}
			fp.save(env);
			return fp.getXml().getOwnerDocument().getDocument();
		}
		
		return null;
	}
}
