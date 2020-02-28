package com.extension.bda.wsc.service;

import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.pca.bridge.YCDFoundationBridge;
import com.yantra.pca.ycd.business.YCDRegisterBarcodeForPacking;
import com.yantra.pca.ycd.utils.YCDUtils;
import com.yantra.shared.dbi.YFS_Shipment;
import com.yantra.shared.dbi.YFS_Shipment_Line;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.core.YFCIterable;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDoubleUtils;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDARegisterBarcodeForPacking extends YCDRegisterBarcodeForPacking implements IBDAService {
	private static YFCLogCategory cat = YFCLogCategory.instance(BDARegisterBarcodeForPacking.class.getName());
	private boolean isNewContainer = false;
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

}
