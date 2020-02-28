package com.extension.bda.wsc.service;


import com.extension.bda.service.IBDAService;
import com.yantra.pca.bridge.YCDFoundationBridge;
import com.yantra.pca.ycd.business.YCDRegisterBarcodeForPacking;
import com.yantra.pca.ycd.utils.YCDUtils;
import com.yantra.pca.ycd.utils.YCDXMLUtils;
import com.yantra.shared.dbi.YFS_Container_Details;
import com.yantra.shared.dbi.YFS_Shipment;
import com.yantra.shared.dbi.YFS_Shipment_Line;
import com.yantra.shared.dbi.YFS_Shipment_Tag_Serial;
import com.yantra.shared.ycd.YCDErrorCodes;
import com.yantra.shared.ycp.YCPLiterals;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.shared.ycp.sim.YCPSIMApiManager;
import com.yantra.ycp.core.YCPTemplateManager;
import com.yantra.yfc.core.YFCIterable;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNode;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.ui.backend.util.APIManager;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDoubleUtils;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfc.util.YFCI18NUtils;
import com.yantra.yfc.util.YFCLocale;
import com.yantra.yfs.japi.YFSEnvironment;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;


public class BDARegisterBarcodeForPacking extends YCDRegisterBarcodeForPacking implements IBDAService {
	private static YFCLogCategory cat = YFCLogCategory.instance(BDARegisterBarcodeForPacking.class.getName());
	private boolean isNewContainer = false;
	  private Object[] unpackNotebundleArgs = new Object[4];
	  
	  private boolean isSIMEnabled = false;
	  
	  private String scannedSerialNo = "";
	  
	  private String tagSerialKey = "";
	private String lotNumber = null;
	
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProperties(Properties props) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCElement inputElem = YFCDocument.getDocumentFor(input).getDocumentElement();
		String barcodeData = inputElem.getAttribute("BarCodeData");
		if(barcodeData.indexOf(":") > -1) {
			lotNumber = barcodeData.split(":")[1];
			barcodeData = barcodeData.split(":")[0];
			inputElem.setAttribute("BarCodeData", barcodeData);
		}
		return registerBarcodeForPacking(env, inputElem.getOwnerDocument()).getDocument();
	}
	
	  public YFCDocument registerBarcodeForPacking(YFSEnvironment oEnv, YFCDocument inDoc) {
		    try {
		      cat.beginTimer("registerBarcodeForPack");
		      YFSContext ctx = (YFSContext)oEnv;
		      if (cat.isVerboseEnabled())
		        cat.verbose("Input to registerBarcodeForPack:" + inDoc); 
		      YCDUtils.validateInputDocument(ctx, inDoc);
		      setIsSIMEnabled(ctx, inDoc);
		      YFCElement inputElem = inDoc.getDocumentElement();
		      YFCElement shipmenContextualInfoElem = inputElem.getChildElement("ShipmentContextualInfo");
		      String action = inputElem.getAttribute("Action");
		      String sProductClass = null;
		      if (YFCObject.isVoid(action) || YFCObject.equals(action, "PACK")) {
		        inputElem.setAttribute("Action", "PACK");
		        action = "PACK";
		      } else if (YFCObject.equals(action, "UNPACK")) {
		        action = "UNPACK";
		      } else {
		        YFCException ex = new YFCException("YFS10423");
		        ex.setAttribute("Action", action);
		        throw ex;
		      } 
		      YFCElement itemContextElem = inputElem.getChildElement("ItemContextualInfo");
		      if (!YFCObject.isVoid(itemContextElem))
		        sProductClass = itemContextElem.getAttribute("ProductClass"); 
		      YFS_Shipment oShipment = YCDUtils.getShipment(ctx, shipmenContextualInfoElem);
		      YFCDocument translateBarcodeOutDoc = YCDUtils.callTranslateBarcode(ctx, inputElem);
		      YFCElement translateBarcodeOutput = translateBarcodeOutDoc.getDocumentElement();
		      YCDUtils.validateTranslateBarcodeOutput(ctx, translateBarcodeOutput, inputElem, this.isSIMEnabled);
		      YFS_Shipment_Line oMatchingLine = findMatchingShipmentLine(ctx, oShipment, translateBarcodeOutput, action, sProductClass);
		      YFCDocument templateDoc = YCPTemplateManager.getInstance().getAPITemplate(ctx, "registerBarcodeForPacking", inDoc, new HashMap<>());
		      YFCElement templateElem = templateDoc.getDocumentElement();
		      YFCElement ouputTemplate = (YFCElement)templateElem.cloneNode(true);
		      callChangeShipment(ctx, oShipment, oMatchingLine, shipmenContextualInfoElem, action, translateBarcodeOutput);
		      YFCDocument outputDoc = prepareOutputDocument(ctx, translateBarcodeOutDoc, oShipment.getShipment_Key(), oMatchingLine.getShipment_Line_Key(), templateElem, shipmenContextualInfoElem);
		      YFCElement note = inputElem.getChildElement("Note");
		      if (!YFCElement.isVoid(note)) {
		        String reasonCode = note.getAttribute("ReasonCode");
		        String noteText = note.getAttribute("NoteText");
		        if ("YCD_UNPACK_REASON".equals(reasonCode))
		          noteText = getUnpackNoteText(oEnv, noteText, outputDoc.getDocumentElement()); 
		        YCDFoundationBridge.getInstance().recordReasonNote((YFSContext)oEnv, "YFS_ORDER_HEADER", oMatchingLine.getOrder_Header_Key(), reasonCode, noteText);
		      } 
		      YCDXMLUtils.removeExtraEntities(outputDoc.getDocumentElement(), ouputTemplate);
		      if (cat.isVerboseEnabled())
		        cat.verbose("Output from registerBarcodeForPacking:" + outputDoc); 
		      return outputDoc;
		    } finally {
		      cat.endTimer("registerBarcodeForPack");
		    } 
		  }
	  
	  private YFS_Shipment_Line findMatchingShipmentLine(YFSContext ctx,YFS_Shipment oShipment,YFCElement translateBarcodeOutput,String action,String productClass){
			YFS_Shipment_Line oMatchingLine = null;
			boolean scannedItemPresent = false;
			boolean isQuantityValid =false;
			try{
				cat.beginTimer("findMatchingShipmentLine");
				YFCElement translation = translateBarcodeOutput.getChildElement(YCPLiterals.YCP_BARCODE_TRANSLATIONS).getChildElement(YCPLiterals.YCP_BARCODE_TRANSLATION);
				YFCElement itemContElem = translation.getChildElement(YCPLiterals.YCP_BARCODE_ITEM_CONTEXTUAL_INFO);
				String sItemId = itemContElem.getAttribute(YCPLiterals.YCP_BARCODE_SOURCE_ITEM);
				String sProductClass = itemContElem.getAttribute(YCPLiterals.YCP_COND_PRODUCT_CLASS);
				sProductClass = itemContElem.getAttribute(YCPLiterals.YCP_COND_PRODUCT_CLASS);
				if(YFCObject.isVoid(sProductClass)){
					sProductClass = productClass ;
				}
				String sUom = itemContElem.getAttribute("InventoryUOM");
				String sKitCode = itemContElem.getAttribute("KitCode");
				double dQty = itemContElem.getDoubleAttribute(YFS_Shipment_Line.QUANTITY);
				if(!YFCDoubleUtils.greaterThan(dQty,0)){
					dQty = 1; //defaulting to 1 if a positive value is not returned.
					itemContElem.setDoubleAttribute(YFS_Shipment_Line.QUANTITY,dQty);
				}

				List<YFS_Shipment_Line> lineList = oShipment.getShipmentLineList();
				for(Iterator<YFS_Shipment_Line> ite = lineList.iterator();ite.hasNext();){

					YFS_Shipment_Line oLine = ite.next();
					double quantity = oLine.getQuantity();
					double packedQuantity =oLine.getPackedQuantity();
					double unpackedQuantity = quantity - packedQuantity;

					if(!YFCObject.equals(oLine.getItem_Id(),sItemId)){
						continue;
					}

					if(!YFCObject.isVoid(sProductClass) && !YFCObject.equals(oLine.getProduct_Class(),sProductClass)){
						continue;
					}

					if(!YFCObject.isVoid(sUom) && !YFCObject.equals(oLine.getUom(),sUom)){
						continue;
					}
					if(!YFCObject.isVoid(sKitCode) && !YFCObject.equals(oLine.getKit_Code(),sKitCode)){
						continue;
					}

					scannedItemPresent = true;
					if(!YFCObject.isVoid(oLine.getQuantity())  && action == "PACK"){
						if (!YFCDoubleUtils.greaterThanEqualTo(unpackedQuantity,dQty)){
							continue;
						}
					}

					else if (!YFCObject.isVoid(oLine.getPackedQuantity()) && action == "UNPACK"){
						if (!YFCDoubleUtils.lessThanEqualTo(dQty, packedQuantity)){
							continue;
						}
					}
					isQuantityValid = true;

					oMatchingLine = oLine;

					YCDUtils.appendShipmentLineInformationToOutput(ctx,oMatchingLine,translation);
					break;

				}

				if(oMatchingLine == null && !scannedItemPresent){
					YFCException ex = new YFCException(YCDErrorCodes.YCD_BARCODE_SCANNED_ITEM_DOES_NOT_BELONG_TO_SHIPMENT);
					ex.setAttribute(YCPLiterals.YCP_BARCODE_SOURCE_ITEM,sItemId);
					ex.setAttribute("UnitOfMeasure",sUom);
					throw ex;
				}else if(oMatchingLine == null && scannedItemPresent && !isQuantityValid && action=="PACK"){
					YFCException ex = new YFCException(YCDErrorCodes.YCD_BARCODE_PACKED_QUANTITY_EXCEEDS_LINE_QUANTITY);
					ex.setAttribute(YCPLiterals.YCP_BARCODE_SOURCE_ITEM,sItemId);
					ex.setAttribute("UnitOfMeasure",sUom);
					ex.setAttribute("ScannedQuantity",Double.toString(dQty));
					throw ex;
				}else if(oMatchingLine == null && scannedItemPresent && !isQuantityValid && action=="UNPACK"){
					YFCException ex = new YFCException(YCDErrorCodes.YCD_BARCODE_UNPACKED_QUANTITY_EXCEEDS_LINE_QUANTITY);
					ex.setAttribute(YCPLiterals.YCP_BARCODE_SOURCE_ITEM,sItemId);
					ex.setAttribute("UnitOfMeasure",sUom);
					ex.setAttribute("ScannedQuantity",Double.toString(dQty));
					throw ex;
				}
				else{
					return oMatchingLine;
				}

			}finally{
				cat.endTimer("findMatchingShipmentLine");
			}
		}
	  
	  
	  private void setIsSIMEnabled(YFSContext ctx, YFCDocument inDoc) {
	    YFCElement oContextualInfo = inDoc.getDocumentElement().getChildElement("ContextualInfo");
	    String inputNode = oContextualInfo.getAttribute("Node");
	    if (!YFCObject.isVoid(inputNode))
	      this.isSIMEnabled = YCPSIMApiManager.getInstance().isSIMEnabled(ctx, inputNode); 
	  }
	  
	  private String getUnpackNoteText(YFSEnvironment env, String unpackReason, YFCElement output) {
	    String noteText = "";
	    this.unpackNotebundleArgs[3] = unpackReason;
	    YFCElement translations = output.getChildElement("Translations");
	    if (!YFCElement.isVoid(translations)) {
	      YFCElement translation = translations.getChildElement("Translation");
	      if (!YFCElement.isVoid(translation)) {
	        YFCElement itemInfo = translation.getChildElement("ItemContextualInfo");
	        if (!YFCElement.isVoid(itemInfo)) {
	          this.unpackNotebundleArgs[0] = itemInfo.getAttribute("ItemID");
	          this.unpackNotebundleArgs[1] = itemInfo.getAttribute("Quantity");
	        } 
	      } 
	    } 
	    YFCLocale alocale = getLoggedInUserYFCLocale(env);
	    noteText = MessageFormat.format(YFCI18NUtils.getString("Unpack_Item_Note_Text", alocale), this.unpackNotebundleArgs);
	    cat.verbose("Note :" + noteText);
	    return noteText;
	  }
	  
	  private YFCLocale getLoggedInUserYFCLocale(YFSEnvironment env) {
	    YFCLocale alocale = null;
	    if (env.getUserId() != null) {
	      try {
	        YFCDocument dGetUserHierarchy = YFCDocument.createDocument("User");
	        YFCElement eGetUserHierarchy = dGetUserHierarchy.getDocumentElement();
	        eGetUserHierarchy.setAttribute("Loginid", env.getUserId());
	        YFCElement apiElem = YFCDocument.createDocument("API").getDocumentElement();
	        apiElem.setAttribute("Name", "getUserHierarchy");
	        YFCElement template = YFCDocument.createDocument("User").getDocumentElement();
	        template.setAttribute("Localecode", "");
	        YFCElement userDataElement = APIManager.getInstance().invokeAPI(env, apiElem, eGetUserHierarchy, template);
	        alocale = YFCLocale.getYFCLocale(userDataElement.getAttribute("Localecode"));
	      } catch (Exception e) {
	        alocale = YFCLocale.getDefaultLocale();
	      } 
	    } else {
	      alocale = YFCLocale.getDefaultLocale();
	    } 
	    return alocale;
	  }
	  
		  
	  private void createTagSerialInput(YFCElement contDetailElem, YFCElement lineElem) {
	    YFCElement shpTagSerial = contDetailElem.createChild("ShipmentTagSerials").createChild("ShipmentTagSerial");
	    shpTagSerial.setAttribute("SerialNo", this.scannedSerialNo);
	    shpTagSerial.setAttribute("Quantity", "1");
	    shpTagSerial.setAttribute("Action", "Create");
	    YFCElement lineShpTagSerial = lineElem.createChild("ShipmentTagSerials").createChild("ShipmentTagSerial");
	    lineShpTagSerial.setAttribute("Action", "Delete");
	    lineShpTagSerial.setAttribute("ShipmentTagSerialKey", this.tagSerialKey);
	  }
	  
	private YFCElement callChangeShipment(YFSContext ctx, YFS_Shipment oShipment, YFS_Shipment_Line oMatchingLine, YFCElement shipmentElem, String action, YFCElement translateBarcodeOutput) {
	    try {
	      cat.beginTimer("callChangeShipment");
	      YFCDocument changeShipmentInputDoc = YFCDocument.createDocument("Shipment");
	      YFCElement changeShipmentElem = changeShipmentInputDoc.getDocumentElement();
	      changeShipmentElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
	      changeShipmentElem.setAttribute("IsPackProcessComplete", "N");
	      YFCElement lineElem = changeShipmentElem.createChild("ShipmentLines").createChild("ShipmentLine");
	      lineElem.setAttribute("ShipmentLineKey", oMatchingLine.getShipment_Line_Key());
	      YFCElement contElem = changeShipmentElem.createChild("Containers").createChild("Container");
	      YFCElement contDetailElem = contElem.createChild("ContainerDetails").createChild("ContainerDetail");
	      contElem.setAttribute("IsPackProcessComplete", "N");
	      contElem.setDoubleAttribute("ActualWeight", 0.0D);
	      String contScm = shipmentElem.getAttribute("ContainerScm");
	      String shipmentContKey = shipmentElem.getAttribute("ShipmentContainerKey");
	      YFCElement itemContElem = translateBarcodeOutput.getChildElement("Translations").getChildElement("Translation").getChildElement("ItemContextualInfo");
	      double dQty = itemContElem.getDoubleAttribute("Quantity");
	      if (!YFCDoubleUtils.greaterThan(dQty, 0.0D)) {
	        dQty = 1.0D;
	        itemContElem.setDoubleAttribute("Quantity", dQty);
	      } 
	      String sTrackingNo = "";
	      String containerNo = "";
	      if (!YFCObject.isVoid(contScm) && YFCObject.isVoid(shipmentContKey)) {
	        this.isNewContainer = true;
	        contElem.setAttribute("ContainerScm", contScm);
	        contElem.setAttribute("ContainerType", "Case");
	        contDetailElem.setAttribute("ShipmentLineKey", oMatchingLine.getShipment_Line_Key());
	        contDetailElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
	        if (YFCObject.equals(action, "PACK")) {
	          contDetailElem.setAttribute("QuantityPlaced", dQty);
	          contDetailElem.setAttribute("Quantity", dQty);
	          if(!YFCCommon.isVoid(lotNumber)) {
	        	  YFCElement eTag = contDetailElem.getChildElement("ShipmentTagSerials", true).createChild("ShipmentTagSerial");
	        	  eTag.setAttribute("LotNumber", lotNumber);
	        	  eTag.setAttribute("Quantity", dQty);
	          }
	        } else if (YFCObject.equals(action, "UNPACK")) {
	          YFCException ex = new YFCException("YCD00078");
	          ex.setAttribute("ContainerScm", contScm);
	          throw ex;
	        } 
	      } else if (!YFCObject.isVoid(shipmentContKey)) {
	        YFCDocument getShipmentContainerDetailsInputDoc = YFCDocument.createDocument("Container");
	        YFCElement getShipmentContainerDetailsInputElem = getShipmentContainerDetailsInputDoc.getDocumentElement();
	        getShipmentContainerDetailsInputElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
	        getShipmentContainerDetailsInputElem.setAttribute("ShipmentContainerKey", shipmentContKey);
	        YFCDocument getShipmentContainerDetailsTemplateDoc = YFCDocument.createDocument("Container");
	        YFCElement getShipmentContainerDetailsTemplate = getShipmentContainerDetailsTemplateDoc.getDocumentElement();
	        getShipmentContainerDetailsTemplate.setAttribute("ShipmentContainerKey", "");
	        getShipmentContainerDetailsTemplate.setAttribute("ContainerNo", "");
	        getShipmentContainerDetailsTemplate.setAttribute("TrackingNo", "");
	        YFCElement containerDetailElem = getShipmentContainerDetailsTemplate.createChild("ContainerDetails").createChild("ContainerDetail");
	        containerDetailElem.setAttribute("ContainerDetailsKey", "");
	        containerDetailElem.setAttribute("ShipmentLineKey", "");
	        containerDetailElem.setAttribute("Quantity", "");
	        containerDetailElem.setAttribute("QuantityPlaced", "");
	        if (cat.isVerboseEnabled()) {
	          cat.verbose("Input to getShipmentContainerDetails:" + getShipmentContainerDetailsInputElem);
	          cat.verbose("Template to getShipmentContainerDetails:" + getShipmentContainerDetailsTemplate);
	        } 
	        YCDFoundationBridge.getInstance();
	        YFCDocument getShipmentContainerDetailsOutDoc = YCDFoundationBridge.invokeAPI((YFSEnvironment)ctx, "getShipmentContainerDetails", getShipmentContainerDetailsInputDoc, getShipmentContainerDetailsTemplateDoc);
	        if (YFCObject.isVoid(getShipmentContainerDetailsOutDoc)) {
	          YFCException ex = new YFCException("YCD00074");
	          ex.setAttribute("ShipmentContainerKey", shipmentContKey);
	          throw ex;
	        } 
	        if (cat.isVerboseEnabled())
	          cat.verbose("Output from getShipmentContainerDetails:" + getShipmentContainerDetailsOutDoc); 
	        YFCElement getShipmentContainerDetailsOutElem = getShipmentContainerDetailsOutDoc.getDocumentElement();
	        sTrackingNo = getShipmentContainerDetailsOutElem.getAttribute("TrackingNo");
	        containerNo = getShipmentContainerDetailsOutElem.getAttribute("ContainerNo");
	        //this.unpackNotebundleArgs[2] = containerNo;
	        YFCIterable<YFCElement> ContainerDetailItr = getShipmentContainerDetailsOutElem.getChildElement("ContainerDetails").getChildren();
	        boolean ContainerDetailsKeyFound = false;
	        while (ContainerDetailItr.hasNext()) {
	          YFCElement ContainerDetailElement = (YFCElement)ContainerDetailItr.next();
	          if (YFCObject.equals(ContainerDetailElement.getAttribute("ShipmentLineKey"), oMatchingLine.getShipment_Line_Key())) {
	            contDetailElem.setAttribute("ContainerDetailsKey", ContainerDetailElement.getAttribute("ContainerDetailsKey"));
	            contElem.setAttribute("ContainerNo", getShipmentContainerDetailsOutElem.getAttribute("ContainerNo"));
	            contElem.setAttribute("ShipmentContainerKey", shipmentContKey);
	            contDetailElem.setAttribute("ShipmentLineKey", oMatchingLine.getShipment_Line_Key());
	            contDetailElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
	            if (YFCObject.equals(action, "PACK")) {
	              double quantityPlaced = ContainerDetailElement.getDoubleAttribute("Quantity") + dQty;
	              contDetailElem.setAttribute("QuantityPlaced", quantityPlaced);
	              contDetailElem.setAttribute("Quantity", quantityPlaced);
	              if(!YFCCommon.isVoid(lotNumber)) {
		        	  YFCElement eTag = contDetailElem.getChildElement("ShipmentTagSerials", true).createChild("ShipmentTagSerial");
		        	  eTag.setAttribute("LotNumber", lotNumber);
		        	  eTag.setAttribute("Quantity", quantityPlaced);
		          }
	            } else if (YFCObject.equals(action, "UNPACK")) {
	              double quantityPlaced = ContainerDetailElement.getDoubleAttribute("Quantity") - dQty;
	              contDetailElem.setAttribute("QuantityPlaced", quantityPlaced);
	              contDetailElem.setAttribute("Quantity", quantityPlaced);
	              if(!YFCCommon.isVoid(lotNumber)) {
		        	  YFCElement eTag = contDetailElem.getChildElement("ShipmentTagSerials", true).createChild("ShipmentTagSerial");
		        	  eTag.setAttribute("LotNumber", lotNumber);
		        	  eTag.setAttribute("Quantity", quantityPlaced);
		          }
	              if (YFCDoubleUtils.equal(quantityPlaced, 0.0D)) {
	                contDetailElem.removeAttribute("QuantityPlaced");
	                contDetailElem.removeAttribute("Quantity");
	                contDetailElem.setAttribute("Action", "Delete");
	              } 
	            } 
	            ContainerDetailsKeyFound = true;
	            break;
	          } 
	        } 
	        if (!ContainerDetailsKeyFound) {
	          contElem.setAttribute("ShipmentContainerKey", shipmentContKey);
	          contDetailElem.setAttribute("ShipmentLineKey", oMatchingLine.getShipment_Line_Key());
	          contDetailElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
	          if (YFCObject.equals(action, "PACK")) {
	            contDetailElem.setAttribute("QuantityPlaced", dQty);
	            contDetailElem.setAttribute("Quantity", dQty);
	            if(!YFCCommon.isVoid(lotNumber)) {
	        	  YFCElement eTag = contDetailElem.getChildElement("ShipmentTagSerials", true).createChild("ShipmentTagSerial");
	        	  eTag.setAttribute("LotNumber", lotNumber);
	        	  eTag.setAttribute("Quantity", dQty);
	            }
	          } else if (YFCObject.equals(action, "UNPACK")) {
	            YFCException ex = new YFCException("YCD00075");
	            throw ex;
	          } 
	        } 
	      } 
	      YFCDocument changeShipmentTemplateDoc = YFCDocument.createDocument("Shipment");
	      changeShipmentTemplateDoc.getDocumentElement().setAttribute("ShipmentKey", "");
	      if (cat.isVerboseEnabled())
	        cat.verbose("Input to changeShipment:" + changeShipmentElem); 
	      YCDFoundationBridge.getInstance();
	      YFCDocument changeShipmentOutDoc = YCDFoundationBridge.invokeAPI((YFSEnvironment)ctx, "changeShipment", changeShipmentInputDoc, changeShipmentTemplateDoc);
	      if (cat.isVerboseEnabled())
	        cat.verbose("Output from changeShipment:" + changeShipmentOutDoc); 
	      if (!YFCObject.isVoid(sTrackingNo) && "Y".equalsIgnoreCase(oShipment.getScac_Integration_Required())) {
	        YFCDocument voidTrackingNumberInputDoc = YFCDocument.createDocument("Container");
	        YFCElement voidTrackingNumberInputElem = voidTrackingNumberInputDoc.getDocumentElement();
	        voidTrackingNumberInputElem.setAttribute("ContainerNo", containerNo);
	        voidTrackingNumberInputElem.setAttribute("ContainerScm", contScm);
	        voidTrackingNumberInputElem.setAttribute("ShipmentContainerKey", shipmentContKey);
	        voidTrackingNumberInputElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
	        YFCDocument voidTrackingNumberTemplateDoc = YFCDocument.createDocument("Container");
	        YCDFoundationBridge.getInstance();
	        YCDFoundationBridge.invokeAPI((YFSEnvironment)ctx, "voidTrackingNo", voidTrackingNumberInputDoc, voidTrackingNumberTemplateDoc);
	      } 
	      return changeShipmentOutDoc.getDocumentElement();
	    } finally {
	      cat.endTimer("callChangeShipment");
	    } 
	  }
	private boolean serialAlreadyPacked(YFSContext ctx, YFS_Shipment oShipment) {
	    List<YFS_Shipment_Line> lineList = oShipment.getShipmentLineList();
	    for (Iterator<YFS_Shipment_Line> ite = lineList.iterator(); ite.hasNext(); ) {
	      List<YFS_Container_Details> ctlDtl = ((YFS_Shipment_Line)ite.next()).getContainerDetailList();
	      for (Iterator<YFS_Container_Details> oIter = ctlDtl.iterator(); oIter.hasNext(); ) {
	        List<YFS_Shipment_Tag_Serial> oContainerDetailTagSerialList = ((YFS_Container_Details)oIter.next()).getShipmentTagSerialList();
	        for (Iterator<YFS_Shipment_Tag_Serial> shpTagItr = oContainerDetailTagSerialList.iterator(); shpTagItr.hasNext();) {
	          if (this.scannedSerialNo.equals(((YFS_Shipment_Tag_Serial)shpTagItr.next()).getSerial_No()))
	            return true; 
	        } 
	      } 
	    } 
	    return false;
	  }
	  
	  private void massageInstructionTextforShipmentLine(YFSContext ctx, YFCElement elemShipmentLine, YFS_Shipment_Line oShipmentLine) {
	    cat.beginTimer("YCDRegisterBarcodeForPacking.massageInstructionTextforShipmentLine");
	    if (!YFCCommon.isVoid(elemShipmentLine)) {
	      boolean ispackInstructionfound = false;
	      YFCElement instructionsElem = elemShipmentLine.getChildElement("Instructions");
	      if (!YFCCommon.isVoid(instructionsElem)) {
	        YFCIterable<YFCElement> it = instructionsElem.getChildren("Instruction");
	        while (it.hasNext()) {
	          YFCElement elemInstruction = (YFCElement)it.next();
	          String instructionType = elemInstruction.getAttribute("InstructionType");
	          if (!YFCCommon.isVoid(instructionType) && YFCCommon.equals(instructionType, "PACK", false)) {
	            YFCElement elementInstruction = (YFCElement)elemInstruction.cloneNode(true);
	            YFCDocument instructionsDoc = YFCDocument.createDocument("Instructions");
	            YFCElement instructionsElement = instructionsDoc.getDocumentElement();
	            instructionsElement.importNode((YFCNode)elementInstruction);
	            instructionsElem.getParentNode().removeChild((YFCNode)instructionsElem);
	            elemShipmentLine.importNode((YFCNode)instructionsElement);
	            ispackInstructionfound = true;
	            break;
	          } 
	          instructionsElem.removeChild((YFCNode)elemInstruction);
	        } 
	      } 
	      if (!ispackInstructionfound) {
	        YFCElement instructionListElem = null;
	        YFCElement orderLineElem = elemShipmentLine.getChildElement("OrderLine");
	        if (!YFCCommon.isVoid(orderLineElem)) {
	          YFCElement itemDetailsElem = orderLineElem.getChildElement("ItemDetails");
	          if (!YFCCommon.isVoid(itemDetailsElem))
	            instructionListElem = itemDetailsElem.getChildElement("ItemInstructionList"); 
	          if (!YFCCommon.isVoid(instructionListElem)) {
	            YFCIterable<YFCElement> it = instructionListElem.getChildren("ItemInstruction");
	            while (it.hasNext()) {
	              YFCElement elemInstruction = (YFCElement)it.next();
	              String instructionType = elemInstruction.getAttribute("InstructionType");
	              if (!YFCCommon.isVoid(instructionType) && YFCCommon.equals(instructionType, "PACK", false)) {
	                YFCElement instructionElement = instructionsElem.createChild("Instruction");
	                instructionElement.setAttributes(elemInstruction.getAttributes());
	                ispackInstructionfound = true;
	                break;
	              } 
	            } 
	          } 
	        } 
	      } 
	    } 
	    cat.endTimer("YCDRegisterBarcodeForPacking.massageInstructionTextforShipmentLine");
	  }
	  
	  private YFCDocument prepareOutputDocument(YFSContext ctx, YFCDocument translateBarcodeOutDoc, String sShipmentKey, String sShipmentLineKey, YFCElement templateElem, YFCElement shipmenContextualInfoElem) {
	    YFCElement shipmentTemplateElem = templateElem.getChildElement("Shipment");
	    if (shipmentTemplateElem == null)
	      return translateBarcodeOutDoc; 
	    YFCElement shipmentLineTemplateElem = shipmentTemplateElem.getChildElement("ShipmentLine");
	    YFCElement containerDetailsTemplateElem = shipmentLineTemplateElem.getChildElement("ContainerDetail");
	    shipmentTemplateElem.removeChild((YFCNode)shipmentLineTemplateElem);
	    YFCElement contDetailsOutput = null;
	    YFS_Shipment shipmentObj = YCDFoundationBridge.getInstance().getShipment(ctx, sShipmentKey);
	    YFS_Shipment_Line shipmentLineObj = shipmentObj.getShipmentLine(sShipmentLineKey);
	    String shipmentContainerKey = shipmenContextualInfoElem.getAttribute("ShipmentContainerKey");
	    List<YFS_Container_Details> contList = shipmentLineObj.getContainerDetailList();
	    if (!YFCObject.isVoid(containerDetailsTemplateElem))
	      if (this.isNewContainer) {
	        String containerScm = shipmenContextualInfoElem.getAttribute("ContainerScm");
	        for (Iterator<YFS_Container_Details> ite = contList.iterator(); ite.hasNext(); ) {
	          YFS_Container_Details ocontDetails = ite.next();
	          if (YFCObject.equals(containerScm, ocontDetails.getShipmentContainer().getContainer_Scm())) {
	            contDetailsOutput = ocontDetails.getXML(YFCDocument.createDocument("ContainerDetails"), containerDetailsTemplateElem);
	            break;
	          } 
	        } 
	      } else {
	        for (Iterator<YFS_Container_Details> ite = contList.iterator(); ite.hasNext(); ) {
	          YFS_Container_Details ocontDetails = ite.next();
	          if (YFCObject.equals(shipmentContainerKey, ocontDetails.getShipment_Container_Key())) {
	            contDetailsOutput = ocontDetails.getXML(YFCDocument.createDocument("ContainerDetails"), containerDetailsTemplateElem);
	            break;
	          } 
	        } 
	      }  
	    YFCElement shipmentOutput = shipmentObj.getXML(YFCDocument.createDocument("Shipment"), shipmentTemplateElem);
	    YFCElement shipmentLineOutput = shipmentLineObj.getXML(YFCDocument.createDocument("ShipmentLine"), shipmentLineTemplateElem);
	    massageInstructionTextforShipmentLine(ctx, shipmentLineOutput, shipmentLineObj);
	    if (!YFCObject.isVoid(contDetailsOutput))
	      shipmentLineOutput.importNode((YFCNode)contDetailsOutput); 
	    shipmentOutput.importNode((YFCNode)shipmentLineOutput);
	    translateBarcodeOutDoc.getDocumentElement().importNode((YFCNode)shipmentOutput);
	    if (this.isSIMEnabled && !YFCObject.isVoid(this.scannedSerialNo))
	      translateBarcodeOutDoc.getDocumentElement().setAttribute("ScannedSerialNo", this.scannedSerialNo); 
	    return translateBarcodeOutDoc;
	  }
}
