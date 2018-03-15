package com.mirakl.utilities;

import java.io.File;
import java.io.FileWriter;
import java.rmi.RemoteException;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class MiraklUtils {

	public static YFCDocument callApi(YFSEnvironment env, YFCDocument inDoc, YFCDocument dTemplate, String sApiName){
		YIFApi localApi;
	    Document dOrderOutput = null;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			if(!YFCCommon.isVoid(dTemplate)){
				env.setApiTemplate(sApiName, dTemplate.getDocument());
			}			
			//System.out.println("Calling: " + sApiName);
			//System.out.println("Input: " + inDoc);
			dOrderOutput = localApi.invoke(env, sApiName, inDoc.getDocument());
			if(!YFCCommon.isVoid(dOrderOutput) && !YFCCommon.isVoid(dOrderOutput.getDocumentElement()) && !YFCCommon.isVoid(dOrderOutput.getDocumentElement().getNodeName())){
				if(dOrderOutput.getDocumentElement().getNodeName().startsWith("Error")){
					System.out.println("Error Returned on " + sApiName);
					System.out.println("Input: " + inDoc);
					System.out.println("Response: " + YFCDocument.getDocumentFor(dOrderOutput));
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
		if(!YFCCommon.isVoid(dOrderOutput)){
			return YFCDocument.getDocumentFor(dOrderOutput);
		}
		return null;
	}
	
	public static boolean organizationExists(String sOrgCode, YFSEnvironment env){
		YFCDocument dInput = YFCDocument.createDocument("Organization");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrganizationCode", sOrgCode);
		YFCDocument dTemplate = YFCDocument.createDocument("OrganizationList");
		YFCElement eTemplate = dTemplate.getDocumentElement();
		eTemplate.createChild("Organization").setAttribute("OrganizationCode","");
		
		YFCDocument dResponse = MiraklUtils.callApi(env, dInput, dTemplate, "getOrganizationList");
		if(!YFCCommon.isVoid(dResponse)){
			YFCElement eResponse = dResponse.getDocumentElement();
			for(YFCElement eChild : eResponse.getChildren()){
				if(YFCCommon.equals(eChild.getAttribute("OrganizationCode"), sOrgCode)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static YFCElement getExistingOrder(YFSEnvironment env, String sOrderHeaderKey) {
		YFCDocument dInput = YFCDocument.createDocument("Order");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OrderHeaderKey", sOrderHeaderKey);
		YFCDocument dTemplate = YFCDocument.createDocument("OrderList");
		YFCElement eTemplate = dTemplate.getDocumentElement();
		YFCElement eOrder = eTemplate.createChild("Order");
		eOrder.setAttribute("OrderHeaderKey","");
		eOrder.setAttribute("Status", "");
		eOrder.setAttribute("SellerOrganizationCode", "");
		eOrder.setAttribute("DocumentType", "");
		eOrder.setAttribute("EnterpriseCode", "");
		eOrder.setAttribute("OrderNo", "");
		YFCElement eOrderLine = eOrder.createChild("OrderLines").createChild("OrderLine");
		eOrderLine.setAttribute("OrderLineKey", "");
		eOrderLine.setAttribute("PrimeLineNo", "");
		eOrderLine.setAttribute("SubLineNo", "");
		eOrderLine.setAttribute("Status", "");
		YFCElement eItem = eOrderLine.createChild("Item");
		eItem.setAttribute("ItemID", "");
		eItem.setAttribute("UnitOfMeasure", "");
		eItem.setAttribute("ProductClass", "");
		YFCElement eOrderStatus = eOrderLine.createChild("OrderStatuses").createChild("OrderStatus");
		eOrderStatus.setAttribute("Status", "");
		eOrderStatus.setAttribute("StatusQty", "");
		eOrderStatus.setAttribute("StatusDate", "");
		eOrderStatus.setAttribute("OrderReleaseKey", "");
		
		YFCDocument dResponse = MiraklUtils.callApi(env, dInput, dTemplate, "getOrderList");
		if(!YFCCommon.isVoid(dResponse)){
			YFCElement eResponse = dResponse.getDocumentElement();
			for(YFCElement eChild : eResponse.getChildren()){
				if(YFCCommon.equals(eChild.getAttribute("OrderHeaderKey"), sOrderHeaderKey)) {
					return eChild;
				}
			}
		}
		return null;
	}
	
	public static boolean distributionExists(String sDistributionRule, String sOrgCode, String sShipNode, YFSEnvironment env){
		YFCDocument dInput = YFCDocument.createDocument("ItemShipNode");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("OwnerKey", sOrgCode);
		eInput.setAttribute("DistributionRuleId", sDistributionRule);
		eInput.setAttribute("ShipnodeKey", sShipNode);
		YFCDocument dTemplate = YFCDocument.createDocument("ItemShipNodeList");
		YFCElement eTemplate = dTemplate.getDocumentElement();
		eTemplate.createChild("ItemShipNode").setAttribute("ItemshipnodeKey","");
		
		YFCDocument dResponse = MiraklUtils.callApi(env, dInput, dTemplate, "getDistributionList");
		if(!YFCCommon.isVoid(dResponse)){
			YFCElement eResponse = dResponse.getDocumentElement();
			return eResponse.hasChildNodes();
		}
		return false;
	}
	
	
	public static boolean writeXML(String sPath, String sFile, YFCDocument output){
		validatePath(sPath);
		FileWriter fout;
		try{
			deleteExistingFile(sPath + File.separator + sFile);
			char buffer[] = new char[output.toString().length()];
			output.toString().getChars(0,output.toString().length(), buffer, 0);
			fout = new FileWriter(sPath + File.separator + sFile);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	

	protected static void validatePath(String sFilePath){
		File temp = new File(sFilePath);
		temp.mkdirs();
	}
	
	private static void deleteExistingFile(String sFile){
		File temp = new File(sFile);
		if(temp.exists()){
			temp.delete();
		}
	}
}
