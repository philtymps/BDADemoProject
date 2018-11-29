/* ******************************************************************************
IBM Confidential
OCO Source Materials
IBM Sterling Selling and Fulfillment Suite
(C) Copyright IBM Corp. 2005, 2016
The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
**********************************************************************************/
package com.pierbridge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yantra.shared.dbclasses.YCS_Manifest_Ups_DtlDBHome;
import com.yantra.shared.dbclasses.YCS_Manifest_Ups_HdrDBHome;
import com.yantra.shared.dbclasses.YFS_ManifestDBHome;
import com.yantra.shared.dbclasses.YFS_Ship_NodeDBHome;
import com.yantra.shared.dbi.YCS_Manifest_Ups_Dtl;
import com.yantra.shared.dbi.YCS_Manifest_Ups_Hdr;
import com.yantra.shared.dbi.YFS_Manifest;
import com.yantra.shared.dbi.YFS_Ship_Node;
import com.yantra.shared.dbi.YFS_Shipment_Container;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.shared.ycs.YCSDefines;
import com.yantra.shared.ycs.YCSErrorDetails;
import com.yantra.shared.ycs.YCSErrors;
import com.yantra.shared.ycs.YCSException;
import com.yantra.shared.ydm.ParcelCarrierAdaptorInterface;
import com.yantra.shared.ydm.YDMConstants;
import com.yantra.shared.ydm.YDMLiterals;
import com.yantra.ycs.core.YCSSystem;
import com.yantra.ycs.util.CarrierAdaptorUtil;
import com.yantra.ycs.util.YCSPierbridgeUtil;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.date.YTimestamp;
import com.yantra.yfc.dblayer.PLTQueryBuilder;
import com.yantra.yfc.dblayer.YFCDBContext;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfc.util.YFCDateUtils;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfc.util.YFCLocale;
import com.yantra.yfc.util.YFCTimeZone;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSEnvironment;

/*
 * IBM and Sterling Commerce Confidential 
OCO Source Materials 
IBM Sterling Selling and Fulfillment Suite
( C) Copyright Sterling Commerce, an IBM Company 2008, 2011 
The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 */

public class PierbridgeAdaptor implements ParcelCarrierAdaptorInterface{
	
	private CarrierAdaptorUtil _oUtil = new CarrierAdaptorUtil();
	private String sPierbridgeAdapterkey = YCSDefines.YCS_ADAPTOR_NAME_PIERBRIDGE;
	private double dWeightTolerancePercent = Double.parseDouble(YFSSystem.getProperty("ycs.weight_tolerance_percent"));
	private double _dCODAmountWhileShip = 0;
	private double _dDeclaredValueWhileShip = 0;
	private static YFCLogCategory cat = YFCLogCategory.instance(PierbridgeAdaptor.class);
	private boolean bIsReturnLabelToBeProcessed = true;
	private boolean bIsManifestAtContainerLevel=false;	
	
	
	private void setIsManifestAtContainerLevel(){
		String svalue = YFSSystem.getProperty("yfs.manifest.manifestAtContainerLevelForDomesticParcelShipment");
		if(YFCObject.equals(svalue, YDMConstants.YDM_YES)){
			bIsManifestAtContainerLevel = true;
			cat.debug(" bIsManifestAtContainerLevel == "+bIsManifestAtContainerLevel);
		}
	}

	public Document addContainerToManifest(YFSEnvironment env, Document sterlingDoc) {
		return addContainerToManifest(env, sterlingDoc, false);
		}
		
		public Document addContainerToManifest(YFSEnvironment env, Document sterlingDoc, boolean bShipRelease) {
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		cat.debug(" inElem == "+inElem);

		YFCElement eReturnShippingLabels = inElem.getChildElement("ReturnShippingLabels");
		if(!YFCObject.isVoid(eReturnShippingLabels)){
			inElem.removeChild(eReturnShippingLabels);
		}
		YFCElement pldElem = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		String sCarrier = getCarrier(inElem);
		_oUtil.validateCarrier((YFSContext)env, inElem);
		try {
			_oUtil.validateManifestNumber((YFSContext)env, pldElem.getAttribute("ManifestNumber"), pldElem.getAttribute("ShipperAccountNumber"), 
					getCarrier(inElem), pldElem.getDateAttribute("PickupDate"), pldElem.getAttribute("ShipNode"));
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			if((!CarrierAdaptorUtil.isVoid(pldElem.getAttribute("ManifestNumber"))) && 
					(YFCObject.equals(ex.getLastErrorCode(), YCSErrors.YCS_ERR_INVALID_MANIFEST_NUMBER))){
				// 202603 -- user switch issue -- planning to open new manifest on YCS side 
				YFCDocument inDocForOpenManifest = YFCDocument.createDocument(YFS_Manifest.MANIFEST_XMLNAME);
				YFCElement inElemForOpenManifest = inDocForOpenManifest.getDocumentElement();
				inElemForOpenManifest.setAttribute(YCSDefines.YCS_XML_CARRIER, inElem.getAttribute(YCSDefines.YCS_XML_CARRIER));
				inElemForOpenManifest.setAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER, pldElem.getAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER));
				inElemForOpenManifest.setAttribute("ShipNode", pldElem.getAttribute("ShipNode"));
				inElemForOpenManifest.setAttribute(YCSDefines.YCS_XML_PICKUP_DATE, pldElem.getAttribute(YCSDefines.YCS_XML_PICKUP_DATE));
				inElemForOpenManifest.setAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER, pldElem.getAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER));
				inElemForOpenManifest.setAttribute(YCSDefines.YCS_XML_PICKUP_SUMMARY_NUMBER, pldElem.getAttribute(YCSDefines.YCS_XML_PICKUP_SUMMARY_NUMBER));
				
				this.openManifest(env, inDocForOpenManifest.getDocument());
			}else{
				throw new YFCException(e);
			}
		}
		
		boolean bIsLastPackage = false;
		String sIsLastPackageInShipment = pldElem.getAttribute("IsLastPackageInShipment");
		if(YFCObject.equals(sIsLastPackageInShipment, "Y")){
			bIsLastPackage = true;
		}
		
		if(bIsLastPackage){
			// This is so that I can map this attribute to CloseShipment attribute on PB
			pldElem.setAttribute("IsLastPackageInShipment", "True");
		}
		
		
		YFCElement existingPackage = handleUpdatesBeforePostInAddContainerToManifest((YFSContext)env, inElem, bShipRelease);
		boolean bIsShipmentLevelIntg = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL).getBooleanAttribute("IsShipmentLevelIntg");
		Document oApiOutDocOfShip = null;
		if(bIsShipmentLevelIntg) {
			oApiOutDocOfShip = handleShipmentLevelIntg((YFSContext)env, inElem);
		}
		
		if(!YFCObject.isVoid(existingPackage)){
			YFCElement oOutPldElem = existingPackage.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL, true);
			oOutPldElem.setAttribute(YFS_Shipment_Container.COD_AMOUNT,_dCODAmountWhileShip);
			oOutPldElem.setAttribute("DeclaredValueInsurance",_dDeclaredValueWhileShip);
			// when shipping already existing package is being manifested.
			//Checking for PierbridgeShipToHold and bShipToRelease
			if((!(inElem.getChildElement("PackageLevelDetail").getBooleanAttribute("PierbridgeShipToHold"))) && !bShipRelease){
				return existingPackage.getOwnerDocument().getDocument();
			}
			cat.debug("Enable sending of ShipRequest once more.");
			
		}
		Document returnDocFromShip = null;
		if(YFCObject.isVoid(oApiOutDocOfShip) && !bIsShipmentLevelIntg){
			Document pbInXMLForShip = convertSterlingDocToPierBridgeXMLForTransaction("Ship", (YFSContext)env, sterlingDoc);
			returnDocFromShip = post(env, pbInXMLForShip, "Ship", sterlingDoc);
			oApiOutDocOfShip = convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDocFromShip, "Ship", sCarrier);
			cat.debug(" oApiOutDocOfShip == "+YFCDocument.getDocumentFor(oApiOutDocOfShip));
		}
		
		if(YFCObject.isVoid(oApiOutDocOfShip)){
			oApiOutDocOfShip = YFCDocument.createDocument(YCSDefines.YCS_XML_SHIP_CARTON_C).getDocument();
		}
		if(!YFCObject.isVoid(returnDocFromShip)){
			doInterUpdates((YFSContext)env, YFCDocument.getDocumentFor(oApiOutDocOfShip).getDocumentElement(), YFCDocument.getDocumentFor(returnDocFromShip).getDocumentElement()
					,YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement(), false);
		}else{
			if(bIsLastPackage && bIsShipmentLevelIntg){
				// do nothing because its shipment level integration and its last package, so I have already sent all the packages and have done 
				// updates already. 
			}else{
				doInterUpdates((YFSContext)env, YFCDocument.getDocumentFor(oApiOutDocOfShip).getDocumentElement(), null
						,YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement(), false);
			}
		}
		
		
		handleReturnTransaction(env, oApiOutDocOfShip, eReturnShippingLabels, sCarrier, pldElem);
		return oApiOutDocOfShip;
		
	}
	
	
	private void doInterUpdates(YFSContext oEnv, YFCElement apiOutElemOfShip, YFCElement returnElemFromPB, YFCElement inElemOfShip, boolean bIsAfterShippingAllPackages) {
		
		String sTrackingNo = apiOutElemOfShip.getAttribute("TrackingNumber");
		//removing print buffer from out XML - 201129 
		StringBuffer sBuf = new StringBuffer();
		if (!YFCObject.isVoid(apiOutElemOfShip.getAttribute("PrintBuffer"))){
			sBuf = new StringBuffer(apiOutElemOfShip.getAttribute("PrintBuffer"));
			apiOutElemOfShip.setAttribute("PrintBuffer", " ");
			cat.debug(" Made the print buffer vanish......");
		}
		
		String sOutXML = apiOutElemOfShip.getString();
		YFCElement oPldElem = inElemOfShip.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		String sIsForShip = oPldElem.getAttribute("IsForShip");
		String sIsForPrint = oPldElem.getAttribute("IsForPrint");
		String sTrackingNumber = "";
		if(!YFCObject.isVoid(returnElemFromPB)){
			YFCElement oPackageElem = returnElemFromPB.getChildElement("Packages").getChildElement("Package");
			if(!YFCObject.isVoid(oPackageElem)){
				sTrackingNumber = oPackageElem.getChildElement("WayBillNumber").getNodeValue();
			}else{
				YFCException e = new YFCException("Carrier Integration Failed"+" And no tracking number was generated. ");
				e.setAttribute("IsCarrierServerError", "Y");
				throw e;
			}
			
		}
		YCS_Manifest_Ups_Dtl oManifestUpsDtl = YCS_Manifest_Ups_Dtl.newInstance();
		oManifestUpsDtl.readXML(oPldElem);
		oManifestUpsDtl.setPackage_Tracking_Number(sTrackingNumber);
		oManifestUpsDtl.setManifested_Flag("Y");
		if(YFCObject.equals(sIsForPrint, "Y")) {
			oManifestUpsDtl.setInput_Xml(inElemOfShip.getString());
			oManifestUpsDtl.setOutput_Xml(sOutXML);
			// Set the manifested flag to 'P' - Printed.
			oManifestUpsDtl.setManifested_Flag("P"); 
		}else if(YFCObject.equals(sIsForShip, "N")) {
			oManifestUpsDtl.setInput_Xml(inElemOfShip.getString());
			oManifestUpsDtl.setOutput_Xml(sOutXML);
			if(!bIsAfterShippingAllPackages){
				oManifestUpsDtl.setManifested_Flag("N");
			}
		}
		
		if(YFCObject.equals(sIsForPrint, "Y") && YFCObject.equals(sIsForShip, "N")){
			oManifestUpsDtl.setManifested_Flag("Q"); // if both print and not for ship, set manifested as Q. This happens for shipment level print during wave release.
		}
		
		//resetting print buffer to out XML - 201129 
		apiOutElemOfShip.setAttribute("PrintBuffer", sBuf.toString());
		printPrintBufferToFile(oEnv, sTrackingNo, sBuf, inElemOfShip);
		
		// Update the returned Tracking# as well as MSN# into this table.
		YCS_Manifest_Ups_DtlDBHome.getInstance().insert(oEnv,oManifestUpsDtl);
		
		
		YFCElement oOutPldElem = apiOutElemOfShip.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL, true);
		oOutPldElem.setAttribute(YFS_Shipment_Container.COD_AMOUNT,_dCODAmountWhileShip);
		oOutPldElem.setAttribute("DeclaredValueInsurance",_dDeclaredValueWhileShip);
	
	}
	

	//dumping print buffer for corresponding tracking number to file - 201129
	private void printPrintBufferToFile(YFSContext env, String trackingNo, StringBuffer buf, YFCElement inElemOfShip) {
		cat.debug("Systen will now try to save PrintBuffer to a file.");
		if(!YFCObject.isVoid(buf) && buf.length()>0){
			//determine file name
			String sFileName = "";
            if(!YFCObject.isVoid(trackingNo)){
            	sFileName = trackingNo+".DAT";
            }else{
                  YFCElement oPldElem = inElemOfShip.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
                  sFileName = oPldElem.getAttribute("Carrier") + oPldElem.getAttribute("ShipID") + ".DAT";
            }
            //write to file sFileName.DAT
			try {
				_oUtil.writeToFile(buf.toString(), YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sFileName);
				cat.warn("Writing PrintBuffer to file name : "+sFileName+" in the XML DUMP directory.");
			} catch (Exception e) {
				e.printStackTrace();
				YFCException ex = new YFCException(e);
				ex.setAttribute("TrackingNo", trackingNo);
				throw ex;
			}
		}
	}


	private Document handleShipmentLevelIntg(YFSContext context, YFCElement inElem) {
		YFCElement oPldElem = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		String sIsLastPackageInShipment = "N";
		sIsLastPackageInShipment = oPldElem.getAttribute(YCSDefines.YCS_IS_LAST_PACKAGE_IN_SHIPMENT);

		if(!YFCObject.equals(sIsLastPackageInShipment, "Y") && !YFCObject.equals(sIsLastPackageInShipment, "True")) {
			sIsLastPackageInShipment = "N";
		}
		if(YFCObject.equals(sIsLastPackageInShipment, "Y") || YFCObject.equals(sIsLastPackageInShipment, "True")) {
			return shipAllPackages(context, inElem);
		}else{
			return null;
		}
		
	}


	@SuppressWarnings("unchecked")
	private Document shipAllPackages(YFSContext oEnv, YFCElement inElem) {
		YFCElement oPldElem = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		String sCarrier = inElem.getAttribute(YCSDefines.YCS_XML_CARRIER);
		String sShipmentNumber = oPldElem.getAttribute(YCSDefines.YCS_XML_SHIPMENT_NUMBER);
		Map<Integer, YCS_Manifest_Ups_Dtl> _oPackageMap = new HashMap<Integer, YCS_Manifest_Ups_Dtl>();
		
		PLTQueryBuilder builder = new PLTQueryBuilder();
		builder.appendString("CARRIER", PLTQueryBuilder.EQUALS, sCarrier);
		builder.appendAND();
		builder.appendString("MANIFEST_NUMBER", PLTQueryBuilder.EQUALS, oPldElem.getAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER));
		builder.appendAND();
		builder.appendString("SHIPMENT_NUMBER", PLTQueryBuilder.EQUALS, sShipmentNumber);
		builder.appendAND();
		builder.appendStringForIN("MANIFESTED_FLAG", new String[]{"N", "Q"});
		builder.appendOrderBy("CREATETS ASC");
		List<YCS_Manifest_Ups_Dtl> oManifestUpsDtlList = YCS_Manifest_Ups_DtlDBHome.getInstance().listWithWhere( oEnv, builder, Integer.MAX_VALUE);
		
		YFCElement oPBShipInputElem = null;
		boolean bFirstPackage = true;
		int packageNum = 0;
		for(Iterator<YCS_Manifest_Ups_Dtl> i = oManifestUpsDtlList.iterator(); i.hasNext();){
			YCS_Manifest_Ups_Dtl oManifestUpsDtl = (YCS_Manifest_Ups_Dtl)i.next();
			if(YFCObject.equals(oManifestUpsDtl.getManifested_Flag(), "N")){
				oManifestUpsDtl.setManifested_Flag("Y");
			}else if(YFCObject.equals(oManifestUpsDtl.getManifested_Flag(), "Q")){
				oManifestUpsDtl.setManifested_Flag("P");
			}
			
			YCS_Manifest_Ups_DtlDBHome.getInstance().update(oEnv, oManifestUpsDtl);
			
			_oPackageMap.put(packageNum, oManifestUpsDtl);
			
			YFCElement oPackageInpElem = YFCDocument.getDocumentFor(oManifestUpsDtl.getInput_Xml()).getDocumentElement();
			
			YFCElement oPldElemOfPackage = oPackageInpElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
			oPldElemOfPackage.removeAttribute("IsForShip");
			oPldElemOfPackage.removeAttribute("IsForPrint");
			oPldElemOfPackage.setAttribute(YCS_Manifest_Ups_Dtl.PACKAGE_TRACKING_NUMBER, oManifestUpsDtl.getPackage_Tracking_Number());
			oPackageInpElem.setAttribute("AppendShipmentDocumentElem", "Y");
			
			
			Document pbDoc = convertSterlingDocToPBForShip(oEnv, oPackageInpElem.getOwnerDocument().getDocument());
			YFCElement oPackInpElem = YFCDocument.getDocumentFor(pbDoc).getDocumentElement();
			
			
			YFCElement oPBOnePackagePackagesElem = oPackInpElem.getChildElement("Packages");
			
			if(bFirstPackage) {
				bFirstPackage = false;
				oPBShipInputElem = oPackInpElem;
			}else{
				YFCElement oPBInputPackagesElem = oPBShipInputElem.getChildElement("Packages");
				oPBInputPackagesElem.importNode(oPBOnePackagePackagesElem.getChildElement("Package"));
			}
			
			packageNum++;
		}
		inElem.setAttribute("AppendShipmentDocumentElem", "Y");
		Document currentPackageDoc = convertSterlingDocToPierBridgeXMLForTransaction("Ship", oEnv, inElem.getOwnerDocument().getDocument());
		YFCElement currentPackageElem = YFCDocument.getDocumentFor(currentPackageDoc).getDocumentElement();
		YFCElement currentPackagePackagesElem = currentPackageElem.getChildElement("Packages");
		
		if(YFCObject.isVoid(oPBShipInputElem)){
			oPBShipInputElem = currentPackageElem;
		}
		YFCElement oPBInputPackagesElem = oPBShipInputElem.getChildElement("Packages");
		oPBInputPackagesElem.importNode(currentPackagePackagesElem.getChildElement("Package"));
		
 		Document returnDocFromShip = post(oEnv, oPBShipInputElem.getOwnerDocument().getDocument(), "Ship", inElem.getOwnerDocument().getDocument());
		convertPierbridgeXMLToSterlingXMLForTransaction(oEnv, returnDocFromShip, "Ship", sCarrier);
		
		YFCElement returnElemFromShip = YFCDocument.getDocumentFor(returnDocFromShip).getDocumentElement();
		
		YFCElement returnPackagesElemFromShip = returnElemFromShip.getChildElement("Packages");
		
		int packageCounter = 0;
		YFCElement currentPackageReturnElem = null;
		// i need first package element so that i can set the master tracking number.
		String trackingNumOfFirstPackage = null;
		
		for(Iterator i=returnElemFromShip.getElementsByTagName("Package").iterator(); i.hasNext();){
			YFCElement packageElem = (YFCElement)i.next();
			if(!i.hasNext()){
				//This is the current packageElem
				currentPackageReturnElem = packageElem;
				cat.debug(" currentPackageReturnElem == "+currentPackageReturnElem);
				break;
			}
			String sTrackingNumber = packageElem.getChildElement("WayBillNumber").getNodeValue();

			if(YFCCommon.equals(packageCounter, 0)){
				trackingNumOfFirstPackage = sTrackingNumber;
			}
			YCS_Manifest_Ups_Dtl oManifestDtl = _oPackageMap.get(packageCounter);
			if(!YFCObject.isVoid(oManifestDtl)){
				oManifestDtl.setPackage_Tracking_Number(sTrackingNumber);
				YCS_Manifest_Ups_DtlDBHome.getInstance().update(oEnv, oManifestDtl);
				String sContainerNo = oManifestDtl.getShip_ID();
				new YCSPierbridgeUtil().updateShipmentContainerWithTrackingNumber(oEnv, sContainerNo, sTrackingNumber);
			}
			packageCounter++;
		}

		
		YFCElement newReturnPackagesElem = YFCDocument.createDocument().createElement("Packages");
		newReturnPackagesElem.importNode(currentPackageReturnElem);
		returnElemFromShip.removeChild(returnPackagesElemFromShip);
		returnElemFromShip.importNode(newReturnPackagesElem);
		
		Document oApiOutDocOfShipOfOnePackage = convertPierbridgeXMLToSterlingXMLForTransaction(oEnv, returnElemFromShip.getOwnerDocument().getDocument(), "Ship", sCarrier);
		YFCElement oApiOutElemOfShipOfOnePackage = YFCDocument.getDocumentFor(oApiOutDocOfShipOfOnePackage).getDocumentElement();
		if(!oPldElem.getBooleanAttribute("IsDomesticShipment")){
			oApiOutElemOfShipOfOnePackage.setAttribute("MasterTrackingNumber", trackingNumOfFirstPackage);
		}
		
		
		cat.debug(" oApiOutElemOfShipOfOnePackage == "+oApiOutElemOfShipOfOnePackage);
		
		doInterUpdates(oEnv, oApiOutElemOfShipOfOnePackage, returnElemFromShip, inElem, true);
		
		return oApiOutDocOfShipOfOnePackage;
	}


	private YFCElement handleUpdatesBeforePostInAddContainerToManifest(YFSContext oEnv, YFCElement inElem, boolean bShipRelease){
		
		YFCElement oPldElem = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		_dCODAmountWhileShip = oPldElem.getDoubleAttribute(YFS_Shipment_Container.COD_AMOUNT);
		_dDeclaredValueWhileShip = oPldElem.getDoubleAttribute("DeclaredValueInsurance");
		
		boolean bIsShipmentLevelIntg = oPldElem.getBooleanAttribute("IsShipmentLevelIntg");
		if(bIsShipmentLevelIntg) {
			oPldElem.setAttribute("IsForShip", "N");
		}else {
			oPldElem.setAttribute("IsForShip", "Y");
		}
		
		double codAmt = oPldElem.getDoubleAttribute("CODAmount");
		if(codAmt > 0  &&  (YFCCommon.isVoid(oPldElem.getAttribute("CODInd")))){
			oPldElem.setAttribute("CODInd", "1");
		}
		
		String sIsForShip = oPldElem.getAttribute("IsForShip");
		if(!YFCObject.equals(sIsForShip, "N")) {
			sIsForShip = "Y";
		}
		oPldElem.setAttribute("IsForShip", sIsForShip);
		
		String sIsForPrint = oPldElem.getAttribute("IsForPrint");
		if(!YFCObject.equals(sIsForPrint, "Y")) {
			sIsForPrint = "N";
		}
		oPldElem.setAttribute("IsForPrint", sIsForPrint);
		
		// Our format is YYYYMMDD and Pierbridge wants it in YYYY-MM-DD format.
		YTimestamp oPickupDate = oPldElem.getYTimestampAttribute("PickupDate");
		YTimestamp oPBPickUpDate = YTimestamp.newTimestamp(oPickupDate);
		
		if(YFCObject.isVoid(oPBPickUpDate)){
			oPBPickUpDate = oEnv.getDBDate();
		}
		String pbFormatDate = oPBPickUpDate.getString("yyyy-MM-dd");
		oPldElem.setAttribute("PickupDate", pbFormatDate);
		
		boolean bCODsplserviceSelected = false;
		
		/*
		 * Special Services special handling required.
		 */
		YFCElement  eSpecialService = inElem.getChildElement("SpecialServices");
		if(!YFCObject.isVoid(eSpecialService)){
			for(Iterator<YFCElement> i = eSpecialService.getChildren(); i.hasNext(); ){
				YFCElement splSrvcElem = i.next();
				String SpecialService= splSrvcElem.getAttribute("Service");
				if(YFCObject.equals(SpecialService, "SATDELI")){
					oPldElem.setAttribute("SatDeliveryInd", "1");
				}
				if(YFCObject.equals(SpecialService, "SUNDELI")){
					oPldElem.setAttribute("SunDeliveryInd", "1");
				}
				if(YFCObject.equals(SpecialService, YCSDefines.YCS_FEDX_COD_SPECIAL_SERVICE)){
					bCODsplserviceSelected = true;
				}
			}
		}
		
		if(!bCODsplserviceSelected){
			
			cat.debug("Since TAGLESS COD special service is not selected, we are not going to send the COD Amount to Carrier Server");
			oPldElem.removeAttribute("CODAmount");
			oPldElem.removeAttribute("CODInd");
		}


		/* In case of TPB, Third party account is in ID_AcctNumber field, but Billing account is mapped to ConsigneeUPSAcctNumber 
		 * Defect 179235*/
		String sFreightTerms = oPldElem.getAttribute(YCSDefines.YCS_XML_SHIPMENT_CHARGE_TYPE);
		if(YFCObject.equals(sFreightTerms, "TPB")){
			YFCElement altPartyRecord = inElem.getChildElement("AlternatePartyRecord");
			if(!YFCObject.isVoid(altPartyRecord)){
				String thirdpartAccNum = altPartyRecord.getAttribute("ID_AcctNumber");
				oPldElem.setAttribute("ConsigneeUPSAcctNumber", thirdpartAccNum);					
			}
		}
		
		/*
		 * Hazmat fix
		 */
		YFCElement accessorialElement = inElem.getChildElement("AccessorialRecord");
		if(!YFCObject.isVoid(accessorialElement.getAttribute("HazMat")) && YFCObject.equals(accessorialElement.getAttribute("HazMat"), "1")){
			accessorialElement.setAttribute("HazMat", "True");
		}else{
			accessorialElement.setAttribute("HazMat", "False");
		}
		
		YFCElement existingShipmentPackage = null;
		YCS_Manifest_Ups_Dtl oManifestUpsDtl = checkIfPackageAlreadyExists(oEnv, inElem);
		if(oManifestUpsDtl != null) {
			existingShipmentPackage = shipExistingPackage(oEnv, inElem, oManifestUpsDtl, bShipRelease);
			return existingShipmentPackage;
		}
		
		return null;
	}
	
	private YFCElement shipExistingPackage(YFSContext ctx, YFCElement eEle, YCS_Manifest_Ups_Dtl oManifestUpsDtl, boolean bShipRelease){
		bIsReturnLabelToBeProcessed = false;
		
		// here we need to call ShipRelease if Hold is True...
		if(eEle.getChildElement("PackageLevelDetail").getBooleanAttribute("PierbridgeShipToHold")){
			
			//we are storing the PickupDate in proper format in a string here for future reconversion. 
			YFCElement oPldElem = eEle.getChildElement("PackageLevelDetail");
			YTimestamp oShipDate = oPldElem.getYTimestampAttribute("PickupDate");
			YTimestamp oPBShipDate = YTimestamp.newTimestamp(oShipDate);
			if(YFCObject.isVoid(oPBShipDate)){
				oPBShipDate = ctx.getDBDate();
			}
			String pbFormatDate = oPBShipDate.getString("yyyy-MM-dd");
			
			//we check here if the call is from GetTrackingNo or AdContainerToManifest. 
			//bShipRelease is passed as true from the former and false from latter.
			cat.debug("Do we call ShipRelease here? "+bShipRelease);
			//Conditionalize the call.
			if(bShipRelease){
				cat.debug(" Pierbridge ship to hold is true.... so am about to call Ship release");				
				Document pbInXML = convertSterlingDocToPierBridgeXMLForTransaction("ShipRelease", (YFSContext)ctx, eEle.getOwnerDocument().getDocument());
				Document returnDoc = post(ctx, pbInXML, "ShipRelease", eEle.getOwnerDocument().getDocument());
				convertPierbridgeXMLToSterlingXMLForTransaction(ctx, returnDoc, "ShipRelease", eEle.getAttribute("Carrier"));
			}
			
			// this is being done to convert back MM/dd/yyyy into the standard yyyy-MM-dd.
			oPldElem.setAttribute("PickupDate", pbFormatDate);
		}
		String sOutputXML = oManifestUpsDtl.getOutput_Xml();
		oManifestUpsDtl.setManifested_Flag("Y");
		YCS_Manifest_Ups_DtlDBHome.getInstance().update(ctx, oManifestUpsDtl);
		return YFCDocument.getDocumentFor(sOutputXML).getDocumentElement();
	}
	
	@SuppressWarnings("unchecked")
	private YCS_Manifest_Ups_Dtl checkIfPackageAlreadyExists(YFSContext ctx, YFCElement eEle){
		YFCElement oPldElem = eEle.getChildElement("PackageLevelDetail");
		if(YFCObject.isVoid(oPldElem.getAttribute("PackageTrackingNumber"))) {
			return null;
		}
		PLTQueryBuilder builder = new PLTQueryBuilder();
		builder.appendString("PACKAGE_TRACKING_NUMBER", PLTQueryBuilder.EQUALS, oPldElem.getAttribute("PackageTrackingNumber")); 
		List oManifestUpsDtlList = YCS_Manifest_Ups_DtlDBHome.getInstance().listWithWhere( ctx, builder ,1);
		YCS_Manifest_Ups_Dtl oManifestUpsDtl = null;
		if( oManifestUpsDtlList.size() > 0) {
			oManifestUpsDtl = (YCS_Manifest_Ups_Dtl)oManifestUpsDtlList.get(0);
			
			// If package exists, then Manifested flag can only be 'P'.			
			if(!YFCObject.equals(oManifestUpsDtl.getManifested_Flag(), "P")) {
				YCSException ex = new YCSException(YCSErrors.YCS_PACKAGE_IS_ALREADY_MANIFESTED);
				ex.setAttribute(YCSErrors.YCS_PACKAGE_IS_ALREADY_MANIFESTED,"The Package is already manifested");
				throw ex ;
			}else {
				YFCElement oElemFromDB = YFCDocument.getDocumentFor(oManifestUpsDtl.getInput_Xml()).getDocumentElement();
				if(isPackageModified(ctx, eEle, oElemFromDB)) {
					YCSException ex = new YCSException(YCSErrors.YCS_PACKAGE_MODIFIED_AFTER_LABEL_PRINT);
					ex.setAttribute(YCSErrors.YCS_PACKAGE_MODIFIED_AFTER_LABEL_PRINT,"The Package is modified after label print");
					throw ex ;
				}
			}
		}
		
		return oManifestUpsDtl;	
	}
	
	private boolean isPackageModified(YFSContext ctx, YFCElement oNewXML, YFCElement oOldXML){
		YFCElement oOldPldElem = oOldXML.getChildElement("PackageLevelDetail");
		YFCElement oNewPldElem = oNewXML.getChildElement("PackageLevelDetail");
		
		cat.debug(" oOldPldElem == "+oOldPldElem);
		cat.debug(" oNewPldElem == "+oNewPldElem);
		
		// Check to make sure if the package contents have changed since it was printed previously.
		
		double oOldWeight = oOldPldElem.getDoubleAttribute("PackageActualWeight");
		double oNewWeight = oNewPldElem.getDoubleAttribute("PackageActualWeight");
		if(Math.abs(oOldWeight - oNewWeight) > oOldWeight * dWeightTolerancePercent  / 100) {
			return true;
		}
		if(isPackageAttrModified(oOldPldElem, oNewPldElem, "ConsigneeAddress1")) {
			return true;
		}
		if(isPackageAttrModified(oOldPldElem, oNewPldElem, "ConsigneeAddress2")) {
			return true;
		}
		if(isPackageAttrModified(oOldPldElem, oNewPldElem, "ConsigneeCity")) {
			return true;
		}
		if(!oNewPldElem.getBooleanAttribute("PierbridgeShipToHold")){
			if(isPackageAttrModified(oOldPldElem, oNewPldElem, "PickupDate")) {
				return true;
			}
		}
		
		
		return false;		
	}
	
	
	private boolean isPackageAttrModified(YFCElement oNewElem, YFCElement oOldElem, String sAttrName){
		if(YFCObject.equals(oOldElem.getAttribute(sAttrName), oNewElem.getAttribute(sAttrName))) {
			return false;
		}
		
		return true;
	}

	private void handleReturnTransaction(YFSEnvironment env, Document apiOutDocOfShip, YFCElement returnShippingLabels, String sCarrier, YFCElement pldElement) {
		
		YFCDocument apiOutDoc = YFCDocument.getDocumentFor(apiOutDocOfShip);
		YFCElement apiOutElem = apiOutDoc.getDocumentElement();
		String sForwardTrackingNumber = apiOutElem.getAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER);
		
		cat.debug(" apiOutElem == "+apiOutElem);
		
		YFCDocument oRetInDoc = YFCDocument.createDocument("UPSPLD");
		YFCElement oInDocElem = oRetInDoc.getDocumentElement();
		oInDocElem.setAttribute(YDMLiterals.YDM_CARRIER, sCarrier);
		
		YFCElement ePackageLevelDetailelem = oInDocElem.createChild(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		ePackageLevelDetailelem.setAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER, sForwardTrackingNumber);
		ePackageLevelDetailelem.setAttribute("IsDomesticShipment", pldElement.getAttribute("IsDomesticShipment"));
		if(!YFCObject.isVoid(returnShippingLabels)){
			oInDocElem.importNode(returnShippingLabels);
		}else{
			return;
		}
		
		Document returnDoc = this.printReturnLabel(env, oRetInDoc.getDocument());
		
		YFCElement returnElem = YFCDocument.getDocumentFor(returnDoc).getDocumentElement();
		YFCElement returnTrackingElem = returnElem.getChildElement("ReturnTrackingDetails");
		if(!YFCObject.isVoid(returnTrackingElem)){
			apiOutElem.importNode(returnTrackingElem);
		}
		
		cat.debug(" apiOutElem == "+apiOutElem);
	}

	public Document removeContainerFromManifest(YFSEnvironment env, Document sterlingDoc) {
		
		YCS_Manifest_Ups_Dtl oManifestDtl = doValidationsBeforeRemoveContainerFromManifest((YFSContext)env, YFCDocument.getDocumentFor(sterlingDoc));
		if(YFCObject.equals(oManifestDtl.getManifested_Flag(),"N")){
			YCS_Manifest_Ups_DtlDBHome.getInstance().delete((YFCDBContext) env , oManifestDtl);
			return YFCDocument.createDocument(YCSDefines.YCS_XML_DELETEPACKAGE).getDocument();
		}
		Document oApiOutDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_DELETEPACKAGE).getDocument();
		if(!YFCObject.isVoid(oManifestDtl.getPackage_Tracking_Number())){
			YFCElement inElem = beforePostInRemoveContainerFromManifest((YFSContext)env, oManifestDtl, sterlingDoc);

			YFCElement inElem1 = YFCDocument.createDocument("DelPackage").getDocumentElement();
			inElem1.setAttributes(inElem.getAttributes());
			String sCarrier = getCarrier(inElem);
			Document pbInXML = convertSterlingDocToPierBridgeXMLForTransaction("Void", (YFSContext)env, inElem1.getOwnerDocument().getDocument());
			Document returnDoc = post(env, pbInXML, "Void", inElem1.getOwnerDocument().getDocument());
			oApiOutDoc = convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDoc, "Void", sCarrier);

		}
				
		YCS_Manifest_Ups_DtlDBHome.getInstance().delete((YFCDBContext) env , oManifestDtl);
		return oApiOutDoc;
	}
	
	private YFCElement beforePostInRemoveContainerFromManifest(YFSContext oEnv, YCS_Manifest_Ups_Dtl oManifestDtl, Document sterlingDoc) {
		//We need to pass MSN# that CS had returned during ShipCarton. MSN# is saved in
		// ShipID field.
		YFCDocument oNewDoc = YFCDocument.createDocument("New");
		YFCElement oManiDetailElem = oManifestDtl.getXML(oNewDoc, null, YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement().getNodeName());
		
		return oManiDetailElem;
	}
	
	private YCS_Manifest_Ups_Dtl doValidationsBeforeRemoveContainerFromManifest(YFSContext oEnv, YFCDocument sterlingInDoc) {
		YFCElement sterlingInElem = sterlingInDoc.getDocumentElement();
		String sCarrier = sterlingInElem.getAttribute(YCSDefines.YCS_XML_CARRIER);        
		
		PLTQueryBuilder builder = new PLTQueryBuilder();
		builder.appendString("CARRIER", PLTQueryBuilder.EQUALS, sCarrier);
		
		if(!YFCObject.isVoid(sterlingInElem.getAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER))){
			builder.appendAND();
			builder.appendString("PACKAGE_TRACKING_NUMBER", PLTQueryBuilder.EQUALS, sterlingInElem.getAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER));
		}else{
			builder.appendAND();
			builder.appendString("SHIP_ID", PLTQueryBuilder.EQUALS, sterlingInElem.getAttribute("ShipID"));
		}
		
		List<YCS_Manifest_Ups_Dtl> containersFound = YCS_Manifest_Ups_DtlDBHome.getInstance().listWithWhere(oEnv, builder, 1);
		
		if(containersFound.size()==0){
			throw new YCSException(YCSErrorDetails.YCS_ERR_NO_RECORD_FOUND_FROM_DBASE);
		}
		
		YCS_Manifest_Ups_Dtl oManiDetail  = (YCS_Manifest_Ups_Dtl)containersFound.get(0);
		return oManiDetail;
	}

	public Document openManifest(YFSEnvironment env, Document sterlingDoc) {
		YFCDocument inDoc = YFCDocument.getDocumentFor(sterlingDoc);
		YFCElement inElem = inDoc.getDocumentElement();
		
		String Carrier = inElem.getAttribute(YCSDefines.YCS_XML_CARRIER);
		String ManifestNum = inElem.getAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER);
        String PickUpDate = inElem.getAttribute(YCSDefines.YCS_XML_PICKUP_DATE);
        
        String ShipperAcctNum=inElem.getAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER);
		String PickupSumNum =inElem.getAttribute(YCSDefines.YCS_XML_PICKUP_SUMMARY_NUMBER);
		
		YCS_Manifest_Ups_Hdr oManiHdr = doValidationsBeforeOpenManifest((YFSContext)env, inDoc);
		if(!YFCObject.isVoid(oManiHdr)){
			oManiHdr.setStatus(YCSDefines.YCS_MANIFEST_HDR_OPEN);
			if(!YFCObject.isVoid(PickupSumNum)){
				oManiHdr.setPickUp_Summary_No(PickupSumNum);
			}
			YCS_Manifest_Ups_HdrDBHome.getInstance().update((YFCDBContext) env , oManiHdr );
			
		}else{
			YFCDocument oinXMLforInsertDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_OPEN_MANIFEST);
	        YFCElement  eHdrRecordEle = oinXMLforInsertDoc.getDocumentElement();
	        YCS_Manifest_Ups_Hdr oHdrRec = YCS_Manifest_Ups_Hdr.newInstance();    
	        eHdrRecordEle.setAttribute(YCSDefines.YCS_XML_CARRIER, Carrier);
	        eHdrRecordEle.setAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER, ManifestNum);
	        eHdrRecordEle.setAttribute("Status", YCSDefines.YCS_MANIFEST_HDR_OPEN);
	        eHdrRecordEle.setAttribute("PickupDate",PickUpDate);
	        eHdrRecordEle.setAttribute(YCSDefines.YCS_XML_PICKUP_SUMMARY_NUMBER, PickupSumNum);
	        eHdrRecordEle.setAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER,ShipperAcctNum);
	        
	        oHdrRec.readXML(eHdrRecordEle); 
	        oHdrRec.setStatus("O");
	        
	        YCS_Manifest_Ups_HdrDBHome.getInstance().insert((YFCDBContext) env,oHdrRec);
		}
		return YFCDocument.createDocument("Manifest").getDocument();
	}
	
	private YCS_Manifest_Ups_Hdr doValidationsBeforeOpenManifest(YFSContext oEnv, YFCDocument sterlingInDoc) {
		YCS_Manifest_Ups_Hdr oManiHdr = null;
		YFCElement inElem = sterlingInDoc.getDocumentElement();
		String sCarrier = inElem.getAttribute(YCSDefines.YCS_XML_CARRIER);
		
		if(YFCObject.isVoid(sCarrier)){
			throw new YCSException(YCSErrors.YCS_ERR_CARRIER_IS_NULL);
		}
		
		String ManifestNum = inElem.getAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER);
        String PickUpDate = inElem.getAttribute(YCSDefines.YCS_XML_PICKUP_DATE);
        
        String ShipperAcctNum=inElem.getAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER);
        
        if( YFCObject.isVoid(ManifestNum)){              
            throw new YCSException(YCSErrors.YCS_ERR_MANIFESTNUM_CANNOT_BE_NULL);            
        }
        if( YFCObject.isVoid(PickUpDate)){ 
        	/*
        	 * needs to be tested ...187296
        	 */
        	//PickUpDate = oEnv.getDBDate().toString().substring(0, 8);
        	PickUpDate = getNodeTimeStamp(oEnv, inElem.getAttribute("ShipNode")).toString().substring(0,8);
        }
        if( YFCObject.isVoid(ShipperAcctNum)){
            throw new YCSException(YCSErrors.YCS_ERR_INVALID_SHIPPERACCOUNTNUMBER);            
        }
        
       
        List<YCS_Manifest_Ups_Hdr> aManiHdrList = null;
        String  sStatus = YCSDefines.YCS_MANIFEST_HDR_OPEN ;
        
        PLTQueryBuilder builder = new PLTQueryBuilder();
        getWhereForMenifestHdr(builder, sCarrier, PickUpDate, ShipperAcctNum, sStatus);
        
       
        aManiHdrList  = YCS_Manifest_Ups_HdrDBHome.getInstance().listWithWhere( oEnv, builder ,10000) ;
        if( aManiHdrList.size() > 0){
            oManiHdr  = (YCS_Manifest_Ups_Hdr)aManiHdrList.get(0);
            if( !ManifestNum.equals(oManiHdr.getManifest_Number()))
            {                    
				YCSException e = new YCSException (YCSErrors.YCS_ERR_OPEN_MANIFEST_EXISTS_FOR_CARRIER);
				e.setAttribute(YCSDefines.YCS_XML_CARRIER , sCarrier);
				e.setAttribute(YCS_Manifest_Ups_Hdr.MANIFEST_NUMBER, oManiHdr.getManifest_Number());
				throw e;
            }    
        }
        
        builder = new PLTQueryBuilder();
        getWhereForMenifest(builder, sCarrier, ManifestNum, ShipperAcctNum);

        aManiHdrList = new ArrayList<YCS_Manifest_Ups_Hdr>();
        aManiHdrList  = YCS_Manifest_Ups_HdrDBHome.getInstance().listWithWhere( oEnv, builder ,1) ;
        if( aManiHdrList.size() > 0){
        	oManiHdr  = (YCS_Manifest_Ups_Hdr)aManiHdrList.get(0);
        }
        if(!YFCObject.isVoid(oManiHdr)){
        	if(YCSDefines.YCS_MANIFEST_HDR_UPLOADED.equals( oManiHdr.getStatus())){
            	throw new YCSException(YCSErrors.YCS_ERR_CANNOT_SCAN_INTO_UPLOADED_MANIFEST);
            }else{
            	
            	if(YCSDefines.YCS_MANIFEST_HDR_CLOSED.equals(oManiHdr.getStatus())){
            		/*
            		 * Needs testing.. changing to node's TS ...187296
            		 */
            		//YFCDate pickUpdate = oEnv.getDBDate();
            		YFCDate pickUpdate = getNodeTimeStamp(oEnv, inElem.getAttribute("ShipNode"));
            		YFCDate manifestPickUpDate = oManiHdr.getPickup_Date();
            		if(manifestPickUpDate.before(pickUpdate)){
            			throw new YCSException(YCSErrors.YCS_ERR_MANIFESTNUMBER_IS_CLOSED_FOR_AN_EARLIER_DATE);
            		}
            	}
            	oManiHdr.setStatus(YCSDefines.YCS_MANIFEST_HDR_OPEN);
            	if(!YFCObject.equals(PickUpDate, oManiHdr.getPickup_Date().toString().substring(0,8))){
            		throw new YCSException(YCSErrors.YCS_ERR_DIFFERENT_PICKUP_DATE);
            	}
            }
        }
        
        return oManiHdr;
	}
	
	public YTimestamp getNodeTimeStamp(YFSContext oEnv, String sNodeKey){
        YFS_Ship_Node oShipNode = YFS_Ship_NodeDBHome.getInstance().selectWithPK(oEnv, sNodeKey);
        if(!YFCObject.isVoid(oShipNode)){
        	 YFCLocale oLocale = YFCLocale.getYFCLocale(oShipNode.getLocalecode());
        	 return YFCTimeZone.getTimeZoneTimestamp(oEnv.getDBDate(),oLocale.getTimezone());     
        }else{
        	return oEnv.getDBDate();
        }
          
        
    }
	
	PLTQueryBuilder getWhereForMenifestHdr(PLTQueryBuilder builder,
			String Carrier, String PickUpDate, String ShipperAcctNum, String sStatus) {
		builder.appendString("CARRIER", PLTQueryBuilder.EQUALS, Carrier);
		builder.appendAND();
		builder.append("PICKUP_DATE = ");
		builder.append(YCS_Manifest_Ups_HdrDBHome.formatTimestamp(YDate.newDate(PickUpDate)));
		builder.appendAND();
		builder.appendString("STATUS", PLTQueryBuilder.EQUALS, sStatus);
		builder.appendAND();
		builder.appendString("SHIPPER_ACCOUNT_NUMBER", PLTQueryBuilder.EQUALS, ShipperAcctNum);
		return builder;
	}
	
	PLTQueryBuilder getWhereForMenifest(PLTQueryBuilder builder,
			String Carrier, String manifestNum, String shipperAcctNum) {
		builder.appendString("CARRIER", PLTQueryBuilder.EQUALS, Carrier);
		builder.appendAND();
		builder.appendString("SHIPPER_ACCOUNT_NUMBER", PLTQueryBuilder.EQUALS, shipperAcctNum);
		builder.appendAND();
		builder.appendString("MANIFEST_NUMBER", PLTQueryBuilder.EQUALS, manifestNum);
		return builder;
	}

	private String getPBManifestIdentifier(YFSContext oEnv, Document sterlingDoc) {
		
		String identifier = "";
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = getCarrier(inElem);
		cat.debug(" came here ... this is getting EODList identifier "+inElem);
		
		YDate ManifestShipDate = null;
		String sManifestNum = inElem.getAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER);
		String shipperAcctNum = inElem.getAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER);
		if (YFCObject.isVoid(sCarrier)) {
			throw new YCSException(YCSErrors.YCS_ERR_CARRIER_IS_NULL);
		}
		if (YFCObject.isVoid(shipperAcctNum)) {
			throw new YCSException(YCSErrors.YCS_ERR_INVALID_SHIPPERACCOUNTNUMBER);
		}
		if (YFCObject.isVoid(sManifestNum)) {
			throw new YCSException(YCSErrors.YCS_ERR_MANIFESTNUM_CANNOT_BE_NULL);
		}
		PLTQueryBuilder bldr = new PLTQueryBuilder();
		PLTQueryBuilder getManifestbuilder = getWhereClauseForManifest(bldr, sCarrier, sManifestNum, shipperAcctNum);
		YFS_Manifest oManifest = YFS_ManifestDBHome.getInstance().selectWithWhere(oEnv, getManifestbuilder);
		if (!YFCObject.isVoid(oManifest)) {
			YTimestamp yManifestShipDate = oManifest.getManifest_Date();
			ManifestShipDate = yManifestShipDate.getYDate();
			cat.debug("ManifestDate is " + ManifestShipDate);
			if (YFCObject.isVoid(ManifestShipDate)) {
				cat.debug("ManifestDate is blank returing blank identifier");
				return "";
			}
		}
			
		Document pbInXMLForEODList = convertSterlingDocToPierBridgeXMLForTransaction("EndOfDayList", oEnv, sterlingDoc);
		Document returnDoc = post(oEnv, pbInXMLForEODList, "EndOfDayList", sterlingDoc);
		
		if(YFCObject.isVoid(returnDoc)){
			return identifier;
		}
		//Eliminating Manifests in the PB list returned that are not from today. Returning only the identifier.
		YFCDocument returnDocFromPB = YFCDocument.getDocumentFor(returnDoc);
		YFCElement returnElem = returnDocFromPB.getDocumentElement();

		YFCNodeList<YFCElement> manifestElems = returnElem.getElementsByTagName("Manifest");
		int numManifests = manifestElems.getLength();
		//432012
		if(numManifests>0){ 
			boolean foundManifest = false;
			boolean nCheckedAll = false;
			while(!foundManifest && manifestElems.getLength()>0 && !nCheckedAll){
				for(int i = numManifests; i>0; i--){
					YFCElement manifestElem = manifestElems.item(i-1);
					YFCElement shipDateElem = manifestElem.getChildElement("ShipDate");
						String shipDate = shipDateElem.getNodeValue();
						YDate shipdateTS = YDate.newDate(shipDate);
						//432012 fix start
						if(YFCDateUtils.EQ(ManifestShipDate,shipdateTS, false)){ //432012 fix end
						foundManifest = true;
							YFCElement identifierElem = manifestElem.getChildElement("Identifier");
							cat.debug(" Identifier found in XML == "+identifierElem.getNodeValue());
							identifier = identifierElem.getNodeValue();
							break;
						}
						if(i<=1){
						nCheckedAll = true;
					}
				}				
			}
		}
		
		return identifier;
		
		}

	public Document closeManifest(YFSEnvironment env, Document sterlingDoc) {
		
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		setIsManifestAtContainerLevel();
		boolean bIgnoreIntegrationErrors = inElem.getBooleanAttribute("IgnoreIntegrationErrors",false);
		if(cat.isDebugEnabled()){
			cat.debug(" closeManifest: inElem ==> "+inElem);
			cat.debug(" IgnoreIntegrationErrors ==> "+bIgnoreIntegrationErrors);
		}
		String sCarrier = getCarrier(inElem);
		// First we will call EndOfDayList, and get the open manifest number for Carrier. With that number, we will call EndOfDayAction
		List<YCS_Manifest_Ups_Hdr> oManiHdrList = doValidationsBeforeCloseManifest(env, inElem);
		
		if(!bIsManifestAtContainerLevel){
			deleteUnManifestedCartons((YFSContext)env, inElem, oManiHdrList);
		}
		
		String sManifestIndentifier = getPBManifestIdentifier((YFSContext)env, sterlingDoc);
		
		Document pbInXMLForEODAction = convertSterlingDocToPierBridgeXMLForTransaction("EndOfDayAction", (YFSContext) env, sterlingDoc);
		YFCElement pbInElem = YFCDocument.getDocumentFor(pbInXMLForEODAction).getDocumentElement();
		YFCElement manifestIdentifierEle = pbInElem.createChild("Identifier");
		manifestIdentifierEle.setNodeValue(sManifestIndentifier);
		
		YFCElement dateClosedElem = pbInElem.createChild("DateClosed");
		YTimestamp today = ((YFSContext)env).getDBDate();
		String sToday = today.getString("yyyy-MM-dd");
		dateClosedElem.setNodeValue(sToday);
		
		Document returnDoc = post(env, pbInXMLForEODAction, "EndOfDayAction", sterlingDoc);
		Document outDoc = convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDoc, "EndOfDayAction", sCarrier);
		
		for(Iterator<YCS_Manifest_Ups_Hdr> i = oManiHdrList.iterator(); i.hasNext();){
			YCS_Manifest_Ups_Hdr oManiHdr = i.next();
			oManiHdr.setStatus(YCSDefines.YCS_MANIFEST_HDR_CLOSED);
			cat.debug(" aManiHdr == "+oManiHdr.getShipper_Account_Number());
			YCS_Manifest_Ups_HdrDBHome.getInstance().update((YFSContext)env , oManiHdr );
		}
		
		
		return outDoc;
	}
	
	private void deleteUnManifestedCartons(YFSContext ctx, YFCElement inElem, List<YCS_Manifest_Ups_Hdr> oManiHdrList) {
		for(Iterator<YCS_Manifest_Ups_Hdr> itr = oManiHdrList.iterator(); itr.hasNext();){
			YCS_Manifest_Ups_Hdr oManiHdr = itr.next();
			PLTQueryBuilder builder = new PLTQueryBuilder();
	    	getWhereForManifestDtlsWithFlag(builder, oManiHdr);
			List<YCS_Manifest_Ups_Dtl> oManifestUpsDtlList = YCS_Manifest_Ups_DtlDBHome.getInstance().listWithWhere( ctx, builder, Integer.MAX_VALUE);
			
			for(Iterator<YCS_Manifest_Ups_Dtl> i = oManifestUpsDtlList.iterator(); i.hasNext();){
				YCS_Manifest_Ups_Dtl oManifestUpsDtl = (YCS_Manifest_Ups_Dtl)i.next();
				YFCDocument doc = YFCDocument.createDocument("DelPackage");
				YFCElement oOutDocElem = doc.getDocumentElement();
				if(!YFCObject.isVoid(oManifestUpsDtl.getPackage_Tracking_Number())){
					oOutDocElem.setAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER,oManifestUpsDtl.getPackage_Tracking_Number());
				}
				oOutDocElem.setAttribute(YCSDefines.YCS_XML_CARRIER, oManifestUpsDtl.getCarrier());
				cat.debug(" oOutDocElem == "+oOutDocElem);
				this.removeContainerFromManifest(ctx, doc.getDocument());
				
//				YCS_Manifest_Ups_DtlDBHome.getInstance().delete(ctx, oManifestUpsDtl);
			}
		}
    	
	}
	
	PLTQueryBuilder getWhereForManifestDtlsWithFlag(PLTQueryBuilder builder,
			YCS_Manifest_Ups_Hdr oManiHdr) {
		builder.appendString("MANIFEST_NUMBER", PLTQueryBuilder.EQUALS, oManiHdr.getManifest_Number());
		builder.appendAND();
		builder.appendString("CARRIER", PLTQueryBuilder.EQUALS, oManiHdr.getCarrier());
		builder.appendAND();
		builder.appendString("SHIPPER_ACCOUNT_NUMBER", PLTQueryBuilder.EQUALS, oManiHdr.getShipper_Account_Number());
		builder.appendAND();
		builder.appendString("MANIFESTED_FLAG", PLTQueryBuilder.EQUALS, "P");
		return builder;
	}


	private List<YCS_Manifest_Ups_Hdr> doValidationsBeforeCloseManifest(YFSEnvironment env, YFCElement inElem) {
		String Carrier = inElem.getAttribute(YCSDefines.YCS_XML_CARRIER);
		String ManifestNum = inElem.getAttribute(YCSDefines.YCS_XML_MANIFEST_NUMBER);
		String ShipperAcctNum = inElem.getAttribute(YCSDefines.YCS_XML_SHIPPER_ACCOUNT_NUMBER);
		String PickupSumNum = inElem.getAttribute(YCSDefines.YCS_XML_PICKUP_SUMMARY_NUMBER);
		YCS_Manifest_Ups_Hdr oManiHdr = null;
		
		if(YFCObject.isVoid(Carrier)){
			throw new YCSException(YCSErrors.YCS_ERR_CARRIER_IS_NULL);
		}
		if(YFCObject.isVoid(ManifestNum)){
			throw new YCSException(YCSErrors.YCS_ERR_MANIFESTNUM_CANNOT_BE_NULL);
		}    
		if(YFCObject.isVoid(ShipperAcctNum)){  
			throw new YCSException(YCSErrors.YCS_ERR_INVALID_SHIPPERACCOUNTNUMBER );
		}
		
		PLTQueryBuilder builder = new PLTQueryBuilder();
		builder.appendString("CARRIER", PLTQueryBuilder.EQUALS, Carrier);
		builder.appendAND();
		builder.appendString("MANIFEST_NUMBER", PLTQueryBuilder.EQUALS, ManifestNum);
		List<YCS_Manifest_Ups_Hdr> aManiHdrList  = YCS_Manifest_Ups_HdrDBHome.getInstance().listWithWhere( (YFCDBContext) env, builder , 100) ;
		
		if( aManiHdrList.size() > 0){
			cat.debug(" no of records on maniHdrList == "+aManiHdrList.size());
			for(Iterator<YCS_Manifest_Ups_Hdr> i = aManiHdrList.iterator(); i.hasNext();){
				oManiHdr  = i.next();
				if(YCSDefines.YCS_MANIFEST_HDR_UPLOADED.equals( oManiHdr.getStatus())){                   
					throw new YCSException(YCSErrors.YCS_ERR_CANNOT_SCAN_INTO_UPLOADED_MANIFEST);
				}else if ( YCSDefines.YCS_MANIFEST_HDR_CLOSED.equals(oManiHdr.getStatus())){                   
					throw new YCSException(YCSErrors.YCS_ERR_MANIFEST_CLOSED);
				}
				/*
				 * needs to be tested well... manifest date is now supposed to be in node's TS ...187296
				 */
				else if ( YCSDefines.YCS_MANIFEST_HDR_OPEN.equals(oManiHdr.getStatus())){
					 if(oManiHdr.getPickup_Date().after(getNodeTimeStamp((YFSContext)env, inElem.getAttribute("ShipNode")))){
						 throw new YCSException(YCSErrors.YCS_ERR_INVALID_PICKUPDATE);
					 }
				}
				if(YFCObject.isVoid(PickupSumNum)){
					PickupSumNum= oManiHdr.getPickUp_Summary_No();
				}else{
					oManiHdr.setPickUp_Summary_No(PickupSumNum);
				}
			}
			
		}else{             
			throw new YCSException(YCSErrors.YCS_ERR_INVALID_MANIFEST_NUMBER);
		}
		
		
		return aManiHdrList;
	}

	public Document getFreightCharge(YFSEnvironment env, Document sterlingDoc) {
		
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = getCarrier(inElem);
		Document pbInXML = convertSterlingDocToPierBridgeXMLForTransaction("Rate", (YFSContext)env, sterlingDoc);
		Document returnDoc = post(env, pbInXML, "Rate", sterlingDoc);
		Document oApiOutDoc = convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDoc, "Rate", sCarrier);
		return oApiOutDoc;
	}

	public Document convertPierbridgeXMLToSterlingXMLForTransaction(YFSEnvironment env, Document returnDoc, String sTransaction, String carrier){
		//bIgnoreIntegrationErrors is False by default for all api's other than closeManifest.
		return convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDoc, sTransaction, carrier, false);
	}
	public Document convertPierbridgeXMLToSterlingXMLForTransaction(YFSEnvironment env, Document returnDoc, String sTransaction, String carrier,  boolean bIgnoreIntegrationErrors) {
		boolean isReturnTransaction = YFCObject.equals(sTransaction, "Return");
		if(cat.isDebugEnabled()){
			cat.debug("Ignore Integration Errors "+bIgnoreIntegrationErrors);
		}
		handleErrors(returnDoc, isReturnTransaction, bIgnoreIntegrationErrors);

		Document outXML = null;
		if(YFCObject.equals(sTransaction, "Rate")){
			outXML = convertPBXMLtoSterlingXMLForRateTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "Void")){
			outXML = convertPBXMLtoSterlingXMLForVoidTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "EndOfDayList")){
			outXML = convertPBXMLtoSterlingXMLForEndOfDayListTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "EndOfDayAction")){
			outXML = convertPBXMLtoSterlingXMLForEndOfDayActionTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "Print")){
			outXML = convertPBXMLtoSterlingXMLForPrintTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "Ship")){
			outXML = convertPBXMLtoSterlingXMLForShipTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "Return")){
			outXML = convertPBXMLtoSterlingXMLForReturnTransaction(env, returnDoc, carrier, sTransaction);
		}else if(YFCObject.equals(sTransaction, "ShipRelease")){
			outXML = convertPBXMLtoSterlingXMLForShipReleaseTransaction(env, returnDoc, carrier, sTransaction);
		}
		
		return outXML;
	}

	private Document convertPBXMLtoSterlingXMLForReturnTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {

		YFCDocument outDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_SHIP_CARTON_C);
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		return outXML;
	}

	private Document convertPBXMLtoSterlingXMLForShipTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {
		cat.debug(" convertPBXMLtoSterlingXMLForShipTransaction ");
		YFCDocument outDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_SHIP_CARTON_C);
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		
		YFCDocument out = YFCDocument.getDocumentFor(outXML);
		YFCElement outElem = out.getDocumentElement();
		cat.debug(" outElem == "+outElem);
		try{
			outElem.getDoubleAttribute("NetCharge");
		}catch(YFCException e){
			cat.debug(" now setting net charge as 0");
			outElem.setDoubleAttribute("NetCharge", 0);
		}
		
		if(YFCCommon.isVoid(outElem.getAttribute("PierbridgeLabelURL"))) {
			YFCElement response = YFCDocument.getDocumentFor(returnDoc).getDocumentElement();
			YFCElement eGuid = response.getChildElement("Packages", true).getChildElement("Package", true).getChildElement("Labels", true).getChildElement("Output", true).getChildElement("Guid");
			if(!YFCCommon.isVoid(eGuid) && !YFCCommon.isVoid(eGuid.getNodeValue())) {
				outElem.setAttribute("PierbridgeLabelURL", "&use=print&guid=" + eGuid.getNodeValue());
			}
		}
		cat.debug(" after net charge fix == "+outElem);
		return outXML;
	}

	private Document convertPBXMLtoSterlingXMLForPrintTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {
		
		YFCDocument outDoc = YFCDocument.createDocument("Print");
		
		YFCDocument dResponse = YFCDocument.getDocumentFor(returnDoc);
		YFCElement eResponse = dResponse.getDocumentElement();
		
		YFCElement eGuid = eResponse.getChildElement("Outputs", true).getChildElement("Output", true).getChildElement("Guid", true);
		if(!YFCCommon.isVoid(eGuid) && !YFCCommon.isVoid(eGuid.getNodeName())) {
			YFCElement eOutput = outDoc.getDocumentElement();
			eOutput.setAttribute("PierbridgeLabelURL", "&use=print&guid=" + eGuid.getNodeValue());
		}
		return outDoc.getDocument();
	}

	private Document convertPBXMLtoSterlingXMLForEndOfDayActionTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {

		YFCDocument outDoc = YFCDocument.createDocument("Manifest");
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		return outXML;
	}

	private Document convertPBXMLtoSterlingXMLForEndOfDayListTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {
		YFCDocument outDoc = YFCDocument.createDocument("Manifest");
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		return outXML;
	}

	private Document convertPBXMLtoSterlingXMLForVoidTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {
		YFCDocument outDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_DELETEPACKAGE);
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		return outXML;
	}

	private Document convertPBXMLtoSterlingXMLForRateTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {
		
		YFCDocument outDoc = YFCDocument.createDocument("FreightCharge");
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		return outXML;
	}
	
	private Document convertPBXMLtoSterlingXMLForShipReleaseTransaction(YFSEnvironment env, Document returnDoc, String sCarrier, String sTransaction) {

		YFCDocument outDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_SHIP_CARTON_C);
		Document outXML = outDoc.getDocument();
		_oUtil.createOutputXMLForTransaction((YFSContext)env, sCarrier, sTransaction, "Reply", returnDoc, outXML, sPierbridgeAdapterkey);
		return outXML;
	}

	public Document convertSterlingDocToPierBridgeXMLForTransaction(String sTransaction, YFSContext oEnv, Document sterlingDoc) {
		Document pbInXML = null;
		if(YFCObject.equals(sTransaction, "Rate")){
			pbInXML = convertSterlingDocToPBForRate(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "Void")){
			pbInXML = convertSterlingDocToPBForVoid(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "EndOfDayList")){
			pbInXML = convertSterlingDocToPBForEndOfDayList(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "EndOfDayAction")){
			pbInXML = convertSterlingDocToPBForEndOfDayAction(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "Print")){
			pbInXML = convertSterlingDocToPBForPrint(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "Ship")){
			pbInXML = convertSterlingDocToPBForShip(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "Return")){
			pbInXML = convertSterlingDocToPBForReturn(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "ShipRelease")){
			pbInXML = convertSterlingDocToPBForShipRelease(oEnv, sterlingDoc);
		}else if(YFCObject.equals(sTransaction, "EndOfDayGet")){
			pbInXML = convertSterlingDocToPBForEndOfDayGet(oEnv, sterlingDoc);
		}
		return pbInXML;
	}
	
	private Document convertSterlingDocToPBForShipRelease(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeShipReleaseRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		YFCElement oPldElem = inElem.getChildElement("PackageLevelDetail");
		YTimestamp oShipDate = oPldElem.getYTimestampAttribute("PickupDate");
		YTimestamp oPBShipDate = YTimestamp.newTimestamp(oShipDate);
		if(YFCObject.isVoid(oPBShipDate)){
			oPBShipDate = env.getDBDate();
		}
		String pbFormatDate = oPBShipDate.getString("MM/dd/yyyy");
		oPldElem.setAttribute("PickupDate", pbFormatDate);
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "ShipRelease", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		return pbDoc.getDocument();
	}

	private Document convertSterlingDocToPBForReturn(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeReturnRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();

		String sPBPrinterID = "";
		String sThermalPrinterID = "";
		YFCElement retLabelElement = null;
		YFCElement retLabelsElement = inElem.getChildElement("ReturnShippingLabels");
		if(!YFCObject.isVoid(retLabelsElement)){
			retLabelElement = retLabelsElement.getChildElement("ReturnShippingLabel");
			sThermalPrinterID = retLabelElement.getAttribute("ThermalLabelPrinterID");
		}
		
		cat.debug(" while return, sThermalPrinterID == "+sThermalPrinterID);
		
		YFCElement eConnParams = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement().getChildElement("ConnectionParameters",true);
		String sPierbridgeDBUrl = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBURL"); 
		String sPierbridgeDBUserName = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBUserName");
		String sPierbridgeDBPassword = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBPassword");
		
		if(!YFCObject.isVoid(sThermalPrinterID)){
			sPBPrinterID = new YCSPierbridgeUtil().getPrinterIDFromPath(env, sThermalPrinterID, sPierbridgeDBUrl, sPierbridgeDBUserName, sPierbridgeDBPassword);
			cat.debug(" sPBPrinterID == "+sPBPrinterID);
		}
		retLabelElement.setAttribute("ThermalLabelPrinterID", sPBPrinterID);
		String sCarrier = inElem.getAttribute("Carrier");
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "Return", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		return pbDoc.getDocument();
	}

	private Document convertSterlingDocToPBForShip(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeShipRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		
		String sCarrier = inElem.getAttribute("Carrier");
		YFCElement packageElem = inElem.getChildElement("PackageLevelDetail");
		String sWeightUOM = "";
		if(!YFCObject.isVoid(packageElem)){
			sWeightUOM = packageElem.getAttribute("UOMWeight");
		}
		
		YFCNodeList<YFCElement> commRecords = inElem.getElementsByTagName("CommodityRecord");
		YFCDocument tempPBDoc = YFCDocument.createDocument("PierbridgeShipRequest");
		// This document will have the list of all contents other than the first
		YFCElement tempPBElement = tempPBDoc.getDocumentElement();
		int length = commRecords.getLength();
		if(length>1){
			for(int i=length-1;i>0; i--){
				YFCDocument tempPBInDoc = YFCDocument.createDocument("PierbridgeShipRequest");
				YFCElement commRecordOtherThanFirst = YFCDocument.createDocument("UPSPLD").getDocumentElement();
				YFCElement packageLevelElem = commRecordOtherThanFirst.createChild("PackageLevelDetail");
				packageLevelElem.setAttribute("UOMWeight", sWeightUOM);
				YFCElement commRec = (YFCElement)commRecords.item(i);
				if(i>0){
					commRecordOtherThanFirst.importNode(inElem.removeChild(commRec));
					_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "Ship", commRecordOtherThanFirst.getOwnerDocument().getDocument(), tempPBInDoc.getDocument(), sPierbridgeAdapterkey);
					YFCElement tempPBInElement = tempPBInDoc.getDocumentElement();
					YFCElement contentElement = tempPBInElement.getElementsByTagName("Content").item(0);
					tempPBElement.importNode(contentElement);
				}
			}
		}
				
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "Ship", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		// the pbDoc here has only one content, one SKU. rest all in tempPBDoc, so add them
		
		YFCElement pbElement = pbDoc.getDocumentElement();
		
		YFCElement contentsElement = pbElement.getElementsByTagName("Contents").item(0);
		YFCNodeList<YFCElement> contentElemRecords = tempPBElement.getElementsByTagName("Content");
		length = contentElemRecords.getLength();
		for(int i=length-1;i>=0; i--){
			YFCElement contentElem  = (YFCElement)contentElemRecords.item(i);
			contentsElement.importNode(tempPBElement.removeChild(contentElem));
		}
		return pbDoc.getDocument();
	}

	private Document convertSterlingDocToPBForPrint(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgePrintRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		String sPBPrinterID = "";
		String sThermalPrinterID = inElem.getAttribute("ThermalLabelPrinterID");
		
		cat.debug(" while reprint, sThermalPrinterID == "+sThermalPrinterID);
		
		YFCElement eConnParams = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement().getChildElement("ConnectionParameters",true);
		String sPierbridgeDBUrl = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBURL"); 
		String sPierbridgeDBUserName = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBUserName");
		String sPierbridgeDBPassword = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBPassword");
		
		if(!YFCObject.isVoid(sThermalPrinterID)){
			sPBPrinterID = new YCSPierbridgeUtil().getPrinterIDFromPath(env, sThermalPrinterID, sPierbridgeDBUrl, sPierbridgeDBUserName, sPierbridgeDBPassword);
			cat.debug(" sPBPrinterID == "+sPBPrinterID);
		}
		
		inElem.setAttribute("ThermalLabelPrinterID", sPBPrinterID);
		
		String sIsForReturn = inElem.getAttribute("IsForReturn");
		boolean bIsForReturn = false;
		if(!YFCObject.isVoid(sIsForReturn) && YFCObject.equals(sIsForReturn, "Y")){
			bIsForReturn = true;
		}
		
		
		String sIsForPickupSummary = inElem.getAttribute("IsForPickupSummary");
		boolean bIsForPickUpSummary = false;
		if(!YFCObject.isVoid(sIsForPickupSummary) && YFCObject.equals(sIsForPickupSummary, "Y")){
			bIsForPickUpSummary = true;
		}
		
		//thermal printer ID can be used from input is we are doing PICKUP_SUMMARY label type. 
		if(bIsForPickUpSummary && YFCObject.isVoid(inElem.getAttribute("ThermalLabelPrinterID"))){
			inElem.setAttribute("ThermalLabelPrinterID", sThermalPrinterID);
		}
		
		if(bIsForPickUpSummary){
			// first check if EndOfDayID is closed...
			
			YFCDocument manifestDoc = YFCDocument.createDocument("Manifest");
			YFCElement manifestElem = manifestDoc.getDocumentElement();
			manifestElem.setAttribute("Carrier", sCarrier);
			
			YFCElement pbreferences = inElem.getChildElement("PierbridgeReferences");
			if(!YFCObject.isVoid(pbreferences)){
				String sEndOfDayID = pbreferences.getAttribute("EndOfDayID");
				manifestElem.setAttribute("PierbridgeEndOfDayID", sEndOfDayID);
			}
			manifestElem.setAttribute("ShipperAccountNumber", inElem.getAttribute("ShipperAccountNumber"));
			
			Document pbInXML = convertSterlingDocToPierBridgeXMLForTransaction("EndOfDayGet", (YFSContext)env, manifestDoc.getDocument());
					
			// This loop will wait for the Finished status from PB before proceeding. 
			String sWaitInterval = YFSSystem.getProperty("ycs.pierbridge.pickupsummary.wait_interval");
			String sMaxNoOfRetries = YFSSystem.getProperty("ycs.pierbridge.pickupsummary.max_no_of_retries");
			boolean bIsServerManifestClosed = false;
			cat.debug("Wait Interval:"+sWaitInterval+" Max No Of Retries:"+sMaxNoOfRetries);
			for(int i=0;i<Integer.parseInt(sMaxNoOfRetries);i++){
				synchronized (this){
					cat.debug("Inside wait loop."+i);
					try{
						wait(Long.parseLong(sWaitInterval)*1000);
					}catch(InterruptedException e1){
						e1.printStackTrace();
					}
				}
				cat.debug("Posting to PB Server for End Of Day State.");
				Document returnDoc = post(env, pbInXML, "EndOfDayGet", manifestDoc.getDocument());
				YFCDocument outXMLDoc = YFCDocument.getDocumentFor(returnDoc);
				YFCNodeList<YFCElement> stateElems = outXMLDoc.getElementsByTagName("State");
				
				for(Iterator<YFCElement> iter = stateElems.iterator(); iter.hasNext();){
					YFCElement stateElem = (YFCElement)iter.next();
					bIsServerManifestClosed = YFCObject.equals("Finished", stateElem.getNodeValue());
					if(bIsServerManifestClosed){
						cat.debug("Finished state found. Exiting all wait loops and proceeding with PickUp Summary Print.");
						break;
					} else {
						cat.debug("Finished state not found. Checking next element.");
					}
				}
				
				if (bIsServerManifestClosed){
					break;
				}
			}
			if(!bIsServerManifestClosed){
				YFCException ex = new YFCException("Manifest is still not closed on Carrier server.");
				ex.setAttribute(YFCException.ERROR_DESCRIPTION, "Cannot print Pickup Summary as the Manifest is still not closed on Carrier server.");
				ex.setAttribute(YFCException.ERROR_MESSAGE, "End Of Day process is not Finished. Please retry later or increase wait intervals.");
				ex.setAttribute("IsCarrierServerError", "N");
				throw ex;
			}
		}
			
		
		
		Document carrierInDoc = pbDoc.getDocument();
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "Print", sterlingDoc, carrierInDoc, sPierbridgeAdapterkey);
		
		YFCDocument pbConvDoc = YFCDocument.getDocumentFor(carrierInDoc);
		YFCElement pbInElem = pbConvDoc.getDocumentElement();
		YFCNodeList<YFCElement> outputTypeElems = pbInElem.getElementsByTagName("OutputType");
		
		if(bIsForReturn){
			for(Iterator<YFCElement> i=outputTypeElems.iterator(); i.hasNext();){
				YFCElement outTypeElem = i.next();
				outTypeElem.setNodeValue("4");
			}
		}
		
		if(bIsForPickUpSummary){
			for(Iterator<YFCElement> i=outputTypeElems.iterator(); i.hasNext();){
				YFCElement outTypeElem = i.next();
				outTypeElem.setNodeValue("10");
			}
		}
		
		YFCNodeList<YFCElement> printerIdElems = pbInElem.getElementsByTagName("PrinterID");
		for(Iterator<YFCElement> i=printerIdElems.iterator(); i.hasNext();){
			YFCElement printerIdElem = i.next();
			printerIdElem.setNodeValue(sPBPrinterID);
		}
		cat.debug(" pbInElem == "+pbInElem);
		return pbInElem.getOwnerDocument().getDocument();
	}

	private Document convertSterlingDocToPBForEndOfDayAction(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeEndOfDayActionRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "EndOfDayAction", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		return pbDoc.getDocument();
	}

	private Document convertSterlingDocToPBForEndOfDayList(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeEndOfDayListRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "EndOfDayList", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		return pbDoc.getDocument();
	}
	
	private Document convertSterlingDocToPBForEndOfDayGet(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeEndOfDayGetRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "EndOfDayGet", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		return pbDoc.getDocument();
	}

	private Document convertSterlingDocToPBForVoid(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeVoidRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "Void", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		return pbDoc.getDocument();
	}

	private Document convertSterlingDocToPBForRate(YFSContext env, Document sterlingDoc) {
		YFCDocument pbDoc = YFCDocument.createDocument("PierbridgeRateRequest");
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String sCarrier = inElem.getAttribute("Carrier");
		
		YFCElement shipmentElem = inElem.getChildElement("Shipment");
		String sService = shipmentElem.getAttribute("Service");
		//String sElecCode = getElectronicCode(env, sCarrier, sService);
		//shipmentElem.setAttribute("Service", sElecCode);
		
		shipmentElem.setAttribute("Service", sService);
		
		_oUtil.createInputXMLForCarrierForTransaction(env, sCarrier, "Rate", sterlingDoc, pbDoc.getDocument(), sPierbridgeAdapterkey);
		
		YFCElement eConnParams = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		String defaultRateUser = null;
		if(!YFCObject.isVoid(eConnParams)){
			defaultRateUser = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDefaultRateRequestUser");
		}
		if(YFCObject.isVoid(defaultRateUser)){
			defaultRateUser = YFSSystem.getProperty("ycs.pierbridge.default.rate.request.user");
		}
		//String defaultRateUser = YFSSystem.getProperty("ycs.pierbridge.default.rate.request.user");

		YFCNodeList<YFCElement> userElems = pbDoc.getElementsByTagName("UserName");
		for(Iterator<YFCElement> i = userElems.iterator(); i.hasNext();){
			YFCElement userElem = (YFCElement)i.next();
			userElem.setNodeValue(defaultRateUser);
		}
		
		return pbDoc.getDocument();
	}
	
	
	
	PLTQueryBuilder getWhereForSCAC(PLTQueryBuilder builder,String sSCAC, String sCarrierServiceKey) {
		builder.appendString("CARRIER_SERVICE_KEY", PLTQueryBuilder.EQUALS, sCarrierServiceKey);
		builder.appendAND();
		builder.appendString("SCAC_KEY", PLTQueryBuilder.EQUALS, sSCAC);
		return builder;
	}
	
	public Document beforePost(YFSEnvironment env, Document carrierInXML, String sTransaction){
		return carrierInXML;
	}
	
	public Document afterPost(YFSEnvironment env, Document carrierOutXML, String sTransaction){
		return carrierOutXML;
	}
	
	public Document post(YFSEnvironment env, Document carrierInXML, String sTransaction, Document sterlingInXML){
		carrierInXML = beforePost(env, carrierInXML, sTransaction);
		
		YFCDocument carrierInDoc = YFCDocument.getDocumentFor(carrierInXML);
		System.out.println("Request Document: " + carrierInDoc);
		YFCElement carrierInElem = carrierInDoc.getDocumentElement();
		
		YFCElement sterlingInElem = YFCDocument.getDocumentFor(sterlingInXML).getDocumentElement();
		
		String sCarrier = getCarrier(sterlingInElem);
		Document carrierOutXML = null;
		
		System.out.println("Pierbridge Transaction: " + sTransaction);
		if(YFCObject.equals(sTransaction, "Rate")){
			carrierOutXML = postForRateTransaction(env, carrierInElem, sCarrier, sterlingInElem);
		}else if (YFCObject.equals(sTransaction, "Void")){
			carrierOutXML = postForVoidTransaction(env, carrierInElem, sterlingInElem, sCarrier);
		}else if (YFCObject.equals(sTransaction, "EndOfDayList")){
			carrierOutXML = postForEndOfDayListTransaction(env, carrierInElem, sterlingInElem, sCarrier);
		}else if (YFCObject.equals(sTransaction, "EndOfDayAction")){
			carrierOutXML = postForEndOfDayActionTransaction(env, carrierInElem, sterlingInElem, sCarrier);
		}else if (YFCObject.equals(sTransaction, "Print")){
			carrierOutXML = postForPrintTransaction(env, carrierInElem, sterlingInElem, sCarrier);
		}else if (YFCObject.equals(sTransaction, "Ship")){
			carrierOutXML = postForShipTransaction(env, carrierInElem, sCarrier, sterlingInElem);
		}else if (YFCObject.equals(sTransaction, "Return")){
			carrierOutXML = postForReturnTransaction(env, carrierInElem, sCarrier, sterlingInElem);
		}else if (YFCObject.equals(sTransaction, "ShipRelease")){
			carrierOutXML = postForShipReleaseTransaction(env, carrierInElem, sterlingInElem, sCarrier);
		}else if (YFCObject.equals(sTransaction, "EndOfDayGet")){
			carrierOutXML = postForEndOfDayGetTransaction(env, carrierInElem, sterlingInElem, sCarrier);
		}
		
		carrierOutXML = afterPost(env, carrierOutXML, sTransaction);
		System.out.println("Response Document: " + YFCDocument.getDocumentFor(carrierOutXML));
		return carrierOutXML;
	}
	
	private String getPBServerURL(YFCElement sterlingInElem){
		YFCElement eConnParams = sterlingInElem.getChildElement("ConnectionParameters",true);
		String sPierbridgeURL = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBServerURL"); 
		if (YFCObject.isVoid(sPierbridgeURL)){
			sPierbridgeURL = YFSSystem.getProperty("ycs.pierbridge.server.url") ;
		}
		return sPierbridgeURL;
	}
	
	private Document postForPrintTransaction(YFSEnvironment env, YFCElement carrierInElem, YFCElement sterlingInElem, String carrier) {
		final String carrierInString = carrierInElem.getString() ;
		String sDumpFileName = carrier + sterlingInElem.getAttribute("TrackingNumber")+".txt";
		
		try {
			_oUtil.writeToFile("\n\nPrint Request XML :\n" + carrierInString,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nPrint Response XML :\n" + sCSReply, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sDumpFileName);
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}
	
	private Document postForEndOfDayGetTransaction(YFSEnvironment env, YFCElement carrierInElem, YFCElement sterlingInElem, String carrier) {
		final String carrierInString = carrierInElem.getString() ;
		String sDumpFileName = carrier + sterlingInElem.getAttribute("PierbridgeEndOfDayID")+".txt";
		
		try {
			_oUtil.writeToFile("\n\nEndOfDayGet Request XML :\n" + carrierInString,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nEndOfDayGet Response XML :\n" + sCSReply,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sDumpFileName);
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}


	private Document postForEndOfDayActionTransaction(YFSEnvironment env, YFCElement carrierInElem, YFCElement sterlingInElem, String carrier) {
		final String carrierInString = carrierInElem.getString() ;
		String sDumpFileName = carrier + sterlingInElem.getAttribute("ManifestNumber")+".txt";
		
		try {
			_oUtil.writeToFile("\n\nEODAction Request XML :\n" + carrierInString,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nEODAction Response XML :\n" + sCSReply,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sDumpFileName);
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}


	private Document postForEndOfDayListTransaction(YFSEnvironment env, YFCElement carrierInElem, YFCElement sterlingInElem, String sCarrier) {
		
		final String carrierInString = carrierInElem.getString() ;
		String sCSDumpFileName = sCarrier +"EODList"+ sterlingInElem.getAttribute("Carrier") + ".txt";
		
		try {
			_oUtil.writeToFile("\n\nEndOfDayList Request XML :\n" + carrierInString,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sCSDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nEndOfDayList Response XML :\n\n" + sCSReply,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sCSDumpFileName);
			
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}


	private Document postForVoidTransaction(YFSEnvironment env, YFCElement carrierInElem, YFCElement sterlingInElem, String sCarrier) {
		final String carrierInString = carrierInElem.getString() ;
		String sCSDumpFileName = sCarrier + sterlingInElem.getAttribute("PackageTrackingNumber") + ".txt";
		
		try {
			_oUtil.writeToFile("\n\nVoid Request XML :\n" + carrierInString,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sCSDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nVoid Response XML :\n\n" + sCSReply,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sCSDumpFileName);
			
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}


	private Document postForReturnTransaction(YFSEnvironment env, YFCElement carrierInElem, String sCarrier, YFCElement sterlingInElem) {
		final String carrierInString = carrierInElem.getString();
		
		String sCSDumpFileName = sCarrier +"ReturnLabel"+ sterlingInElem.getChildElement("ReturnShippingLabels").getChildElement("ReturnShippingLabel").getAttribute(YCS_Manifest_Ups_Dtl.SHIP_ID)+".txt";
		try {
			_oUtil.writeToFile("\n\nReturn Request XML :\n" + carrierInString, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sCSDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nReturnt Response XML :\n\n" + sCSReply, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sCSDumpFileName);
			
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			e.printStackTrace();
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}

	private Document postForShipTransaction(YFSEnvironment env, YFCElement carrierInElem, String sCarrier, YFCElement sterlingInElem) {
		
		
		YFCElement extraFieldsElem = sterlingInElem.getChildElement(YCSDefines.YCS_EXTRAFIELDS_RECORD);
		if(!YFCObject.isVoid(extraFieldsElem)){
			String sPBPrinteID = " ";
			String sThermalPrinterID = extraFieldsElem.getAttribute("ThermalLabelPrinterID");
			
			cat.debug(" sThermalPrinterID == "+sThermalPrinterID);
			YFCElement eConnParams = sterlingInElem.getChildElement("ConnectionParameters",true);
			String sPierbridgeDBUrl = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBURL"); 
			String sPierbridgeDBUserName = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBUserName");
			String sPierbridgeDBPassword = eConnParams.getChildElement("PierbridgeParams",true).getAttribute("PBDBPassword");
			
			
			if(!YFCObject.isVoid(sThermalPrinterID)){
				sPBPrinteID = new YCSPierbridgeUtil().getPrinterIDFromPath(env, sThermalPrinterID, sPierbridgeDBUrl, sPierbridgeDBUserName, sPierbridgeDBPassword);
				YFCNodeList<YFCElement> printerIDElems = carrierInElem.getElementsByTagName("PrinterID");
				for(Iterator<YFCElement> i = printerIDElems.iterator(); i.hasNext();){
					YFCElement elem = (YFCElement)i.next();
					elem.setNodeValue(sPBPrinteID);
				}
			}
		}
		// defect 175976
		final String carrierInString = carrierInElem.getString();
		
		String sCSDumpFileName = sCarrier + sterlingInElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL).getAttribute(YCS_Manifest_Ups_Dtl.SHIP_ID)+".txt";
		try {
			_oUtil.writeToFile("\n\nShipment Request XML :\n" + carrierInString, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sCSDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			
			
			_oUtil.writeToFile("\n\nShipment Response XML :\n\n" + sCSReply, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sCSDumpFileName);
			
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			e.printStackTrace();
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}
	
	private String sendandReceiveBuffer(String input, String sUrl) throws Exception {
		try {
			URL url = new URL(sUrl.replaceAll(" ", "%20"));
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Length",  String.valueOf(input.length()));
	        // Write data
	        OutputStream os = connection.getOutputStream();
	        os.write(input.getBytes());
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String res;
			while ((res = in.readLine()) != null) {
				sb.append(res);
			}
			in.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return "";

	}

	private Document postForRateTransaction(YFSEnvironment env, YFCElement carrierInElem, String sCarrier, YFCElement sterlingInElem) {
		
		final String carrierInString = carrierInElem.getString() ;
		String sCSDumpFileName = sCarrier + "RATE"+".txt";
		try {
			_oUtil.writeToFile("\n\nRate Request XML :\n" + carrierInString, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sCSDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nRate Response XML :\n\n" + sCSReply, YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY), sCSDumpFileName);
			
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}	
		
	}
	
	private Document postForShipReleaseTransaction(YFSEnvironment env, YFCElement carrierInElem, YFCElement sterlingInElem, String sCarrier) {
		
		final String carrierInString = carrierInElem.getString() ;
		cat.debug(" sterlingInElem == "+sterlingInElem);
		String sCSDumpFileName = sCarrier + sterlingInElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL).getAttribute("ExternalReference1")+"ShipRelease" +".txt";
		
		try {
			_oUtil.writeToFile("\n\nShipRelease Request XML :\n" + carrierInString,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sCSDumpFileName);
			String sCSReply = sendandReceiveBuffer(carrierInString, getPBServerURL(sterlingInElem));
			_oUtil.writeToFile("\n\nShipRelease Response XML :\n\n" + sCSReply,YCSSystem.getProperty(YCSDefines.YCS_XML_DUMP_DIRECTORY),sCSDumpFileName);
			
			YFCDocument oCarrierOutDoc = YFCDocument.getDocumentFor(sCSReply);
			return oCarrierOutDoc.getDocument();
			
		} catch (Exception e) {
			YFCException ex = new YFCException(e);
			throw ex;
		}
	}

	private String getCarrier(YFCElement rootElem){
		return rootElem.getAttribute(YCSDefines.YCS_XML_CARRIER); 
	}

	public void cleanup() {
		
	}

	public Document printCarrierLabel(YFSEnvironment env, Document sterlingDoc) {
		
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		YFCElement oPldElem = inElem.getChildElement("PackageLevelDetail");
		oPldElem.setAttribute("IsForPrint", "Y");
		
		// this is the path for getTrackingNoAndPrintLabel, bShipRelease is true.
			return this.addContainerToManifest(env, sterlingDoc, true);
	}

	public Document reprintCarrierLabel(YFSEnvironment env, Document sterlingDoc) {
		
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		cat.debug(" inElem == "+inElem);
		String sCarrier = getCarrier(inElem);
		Document pbInXML = convertSterlingDocToPierBridgeXMLForTransaction("Print", (YFSContext)env, sterlingDoc);
		Document returnDoc = post(env, pbInXML, "Print", sterlingDoc);
		Document oApiOutDoc = convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDoc, "Print", sCarrier);
		return oApiOutDoc;
	}

	public Document printReturnLabel(YFSEnvironment env, Document sterlingDoc) {
		
		
		YFCElement inElem = YFCDocument.getDocumentFor(sterlingDoc).getDocumentElement();
		YFCElement packageElem = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		YFCElement returnLabelsElem = inElem.getChildElement("ReturnShippingLabels");
		String sCarrier = getCarrier(inElem);
		YFCDocument outDoc = YFCDocument.createDocument(YCSDefines.YCS_XML_SHIP_CARTON_C);
		
		if(YFCObject.isVoid(returnLabelsElem)){
			return outDoc.getDocument();
		}
		
		YFCElement outElem = outDoc.getDocumentElement();
		
		boolean bIsReturnLabelIntegrationRequired = isReturnLabelIntegrationRequired(inElem, returnLabelsElem);
		
		if(bIsReturnLabelIntegrationRequired && bIsReturnLabelToBeProcessed){
			YFCElement ReturnTrackingDetails =  outElem.createChild("ReturnTrackingDetails");
			int numReturnLabels = 0;
			YFCNodeList<YFCElement> returnLabels = returnLabelsElem.getElementsByTagName("ReturnShippingLabel");
			for(Iterator<YFCElement> i=returnLabels.iterator(); i.hasNext();){
				YFCElement returnLabel = (YFCElement)i.next();
				numReturnLabels++;
				
				String sReturnTrackingNumber = returnLabel.getAttribute("TrackingNumber");
				String sBuf = returnLabel.getAttribute("ReturnPrintBuffer");
				String sURL = "";
				YFCElement ReturnTrackingDetail = ReturnTrackingDetails.createChild("ReturnTrackingDetail");
				
				if(returnLabel.getBooleanAttribute("DoNotExitAPI")){
					String returnCarrier = returnLabel.getAttribute("Carrier");
					String sDescriptionOfGoods = returnLabel.getAttribute("DescriptionOfGoods");
					if(YFCObject.isVoid(sDescriptionOfGoods)){
						returnLabel.setAttribute("DescriptionOfGoods", "Package");
					}
					returnLabel.setAttribute("PackagingType", "02");
					
					// Our format is YYYYMMDD and Pierbridge wants it in YYYY-MM-DD format.
					YTimestamp oPickupDate = returnLabel.getYTimestampAttribute("PickupDate"); 
					YTimestamp oPBPickUpDate = YTimestamp.newTimestamp(oPickupDate);
					if(YFCObject.isVoid(oPBPickUpDate)){
						oPBPickUpDate = ((YFSContext)env).getDBDate();
					}
					String pbFormatDate = oPBPickUpDate.getString("yyyy-MM-dd");
					returnLabel.setAttribute("PickupDate", pbFormatDate);
					
					
					YFCDocument upspldReturnDoc = YFCDocument.createDocument("UPSPLD");
					YFCElement upspldRetElem = upspldReturnDoc.getDocumentElement();
					upspldRetElem.setAttribute("Carrier", returnCarrier);
					if(!YFCObject.isVoid(packageElem)){
						upspldRetElem.importNode(packageElem);
					}
					YFCElement upspldRetsElem = upspldRetElem.createChild("ReturnShippingLabels");
					upspldRetsElem.importNode(returnLabel);
					cat.debug(" upspldRetElem == "+upspldRetElem);
					
					Document pbInXML = convertSterlingDocToPierBridgeXMLForTransaction("Return", (YFSContext)env, upspldReturnDoc.getDocument());
					Document returnDoc = post(env, pbInXML, "Return", upspldReturnDoc.getDocument());
					Document oApiOutDoc = null;
					try{
						oApiOutDoc = convertPierbridgeXMLToSterlingXMLForTransaction(env, returnDoc, "Return", sCarrier);
					}catch(YFCException e){
						String error = e.getAttribute("IsCarrierServerError");
						if(YFCObject.equals(error, "Y")){
							YFCElement ePackageLevelDetail = inElem.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
							if(!YFCObject.isVoid(ePackageLevelDetail)){
								//integration required only for return labels. hence no need of unmanifesting
								YFCDocument doc = YFCDocument.createDocument("DelPackage");
								YFCElement oOutDocElem = doc.getDocumentElement();
								oOutDocElem.setAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER,ePackageLevelDetail.getAttribute(YCSDefines.YCS_XML_TRACKING_NUMBER));
								oOutDocElem.setAttribute(YCSDefines.YCS_XML_CARRIER, sCarrier);
								this.removeContainerFromManifest(env, doc.getDocument());
							}
						}
						e.setAttribute("Cause", "(Error occured during return label generation. Container not manifested)");
						throw e;
					}
					
					YFCElement apiOutElem = YFCDocument.getDocumentFor(oApiOutDoc).getDocumentElement();
					
					
					YFCNodeList<YFCElement> returnTrackingDetailElem = apiOutElem.getElementsByTagName("ReturnTrackingDetail");
					if(!YFCObject.isVoid(returnTrackingDetailElem)){
						YFCElement returnElem = returnTrackingDetailElem.item(0);
						if(!YFCObject.isVoid(returnElem)){
							sReturnTrackingNumber = returnElem.getAttribute("ReturnTrackingNumber");
							sBuf = returnElem.getAttribute("ReturnPrintBuffer");
							sURL = returnElem.getAttribute("PierbridgeReturnLabelURL");

						}
					}
					
				}
				ReturnTrackingDetail.setAttribute("ReturnTrackingNumber",sReturnTrackingNumber);
				ReturnTrackingDetail.setAttribute("ReturnPrintBuffer",sBuf);
				ReturnTrackingDetail.setAttribute("PierbridgeReturnLabelURL", sURL);
				
			}
			ReturnTrackingDetails.setAttribute("NumberOfReturnTrackingNumbers",numReturnLabels);
		}
		cat.debug(" outDoc after printReturnLabel == " +outDoc);
		return outDoc.getDocument();
	}
	
	
	private boolean isDomesticShipment (YFCElement eEle){
		YFCElement oPldElem = eEle.getChildElement(YCSDefines.YCS_PACKAGE_LEVEL_DETAIL);
		if(YFCObject.isVoid(oPldElem))//implies integration is required only for return labels
			return true;
		return oPldElem.getBooleanAttribute("IsDomesticShipment");
	}
	
	private boolean isReturnLabelIntegrationRequired(YFCElement eEle, YFCElement eReturnShippingLabels){ 
		if(!isDomesticShipment(eEle))
			return false;
		if(YFCObject.isVoid(eReturnShippingLabels))
			return false;
		if(!eReturnShippingLabels.getBooleanAttribute("IsCarrierIntegrationRequired"))
			return false;
		if(YFCObject.isVoid(eReturnShippingLabels.getAttribute(YCSDefines.YCS_XML_CARRIER)))
			return false;
		return true;
	}
	
	private void handleErrors(Document apiOutDoc, boolean isReturnTransaction, boolean bIgnoreIntegrationErrors) {
		YFCDocument outXMLDoc = YFCDocument.getDocumentFor(apiOutDoc);
		YFCElement outElem = outXMLDoc.getDocumentElement();
		
		YFCNodeList<YFCElement> statusElems = outXMLDoc.getElementsByTagName("Status");
		
		for(Iterator<YFCElement> i = statusElems.iterator(); i.hasNext();){
			YFCElement statusElem = (YFCElement)i.next();
			YFCElement codeElem = statusElem.getChildElement("Code");
			YFCElement descriptionElem = statusElem.getChildElement("Description");
			String sDesc = descriptionElem.getNodeValue();
			
			if(YFCObject.isVoid(sDesc) || sDesc.length()<=0){
				sDesc = " There might have been errors on Carrier Server side, incorrect or incomplete configurations or connection parameters. ";
			}
			
			String sCode = codeElem.getNodeValue();
			int iCode = Integer.parseInt(sCode);
			if(iCode == 0){
				YFCElement errorsElem = outElem.createChild("Errors");
				YFCElement errorElem = errorsElem.createChild("Error");
				errorElem.setAttribute(YFCException.ERROR_DESCRIPTION, sDesc);
				
				YFCException ex = new YFCException(" Carrier Integration failed ");
				if(!isReturnTransaction){
					ex.setAttribute(YFCException.ERROR_DESCRIPTION, sDesc);
				}else{
					ex.setAttribute(YFCException.ERROR_DESCRIPTION, sDesc+" - error occured while return label generation. Container was not manifested. Please destroy any label generated while current manifest process. ");
				}
				
				ex.setAttribute(YFCException.ERROR_MESSAGE, sDesc);
				ex.setAttribute("IsCarrierServerError", "Y");
				if (!bIgnoreIntegrationErrors){
					throw ex;
				}else{
					cat.debug("Carrier Server Returned : "+ex);
					ex.printStackTrace();
					
				}
			}
		}

	}
	
	PLTQueryBuilder getWhereClauseForManifest(PLTQueryBuilder builder,
			String Carrier, String manifestNum, String shipperAcctNum) {
		builder.appendString("SCAC", PLTQueryBuilder.EQUALS, Carrier);
		builder.appendAND();
		builder.appendString("SHIPPER_ACCOUNT_NO", PLTQueryBuilder.EQUALS, shipperAcctNum);
		builder.appendAND();
		builder.appendString("MANIFEST_NO", PLTQueryBuilder.EQUALS, manifestNum);
		return builder;
	}

}