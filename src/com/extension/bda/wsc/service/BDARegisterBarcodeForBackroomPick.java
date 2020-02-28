package com.extension.bda.wsc.service;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.pca.bridge.YCDFoundationBridge;
import com.yantra.pca.ycd.japi.ue.YCDUpdateLocationInventoryUE;
import com.yantra.pca.ycd.utils.YCDUtils;
import com.yantra.shared.dbi.YFS_Shipment;
import com.yantra.shared.dbi.YFS_Shipment_Line;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.util.YFCUtils;
import com.yantra.ycp.core.YCPTemplateManager;
import com.yantra.ycp.core.ue.YCPUEDefaultImpl;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNode;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDoubleUtils;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;

public class BDARegisterBarcodeForBackroomPick extends BDAServiceApi implements IBDAService {
  private static YFCLogCategory cat = YFCLogCategory.instance(BDARegisterBarcodeForBackroomPick.class.getName());
  
  private static final String UPDATE_LOCATION_INVENTORY_UE_PROP = "ycd.ue.updateLocationInventory.class";
  @Override
  public String getServiceName() {
  	return "BDARegisterBarcodeForBackroomPick";
  }


  @Override
  public Document invoke(YFSEnvironment env, Document input) throws Exception {
  	// TODO Auto-generated method stub
  	return registerBarcodeForBackroomPick(env, YFCDocument.getDocumentFor(input)).getDocument();
  }
  
  public YFCDocument registerBarcodeForBackroomPick(YFSEnvironment oEnv, YFCDocument inDoc) {
    try {
      cat.beginTimer("registerBarcodeForBackroomPick");
      YFSContext ctx = (YFSContext)oEnv;
      String sProductClass = null;
      if (cat.isDebugEnabled())
        cat.debug("Input to registerBarcodeForBackroomPick:" + inDoc); 
      validateInputDocument(ctx, inDoc);
      YFCElement inputElem = inDoc.getDocumentElement();
      YFCElement shipmentElem = inputElem.getChildElement("ShipmentContextualInfo");
      YFCElement locationElem = inputElem.getChildElement("LocationContextualInfo");
      YFCElement itemContextElem = inputElem.getChildElement("ItemContextualInfo");
      if (!YFCObject.isVoid(itemContextElem))
        sProductClass = itemContextElem.getAttribute("ProductClass"); 
      YFS_Shipment oShipment = getShipment(ctx, shipmentElem);
      YFCDocument translateBarcodeOutDoc = callTranslateBarcode(ctx, inputElem);
      YFCElement translateBarcodeOutput = translateBarcodeOutDoc.getDocumentElement();
      YCDUtils.validateTranslateBarcodeOutput(ctx, translateBarcodeOutput, inputElem);
      YFS_Shipment_Line oMatchingLine = findMatchingShipmentLine(ctx, oShipment, locationElem, translateBarcodeOutput, sProductClass);
      YFCDocument templateDoc = YCPTemplateManager.getInstance().getAPITemplate(ctx, "registerBarcodeForBackroomPick", inDoc, new HashMap<>());
      YFCElement templateElem = templateDoc.getDocumentElement();
      YFCElement shipmentTemplateElem = templateElem.getChildElement("Shipment");
      callChangeShipment(ctx, oShipment, oMatchingLine);
      if (!YFCObject.isVoid(inputElem.getAttribute("InvokeUpdateInventoryUE")) && YFCObject.equals("true", inputElem.getAttribute("InvokeUpdateInventoryUE")))
        invokeUpdateLocationInventoryUE(ctx, oShipment, oMatchingLine); 
      return prepareOutputDocument(ctx, translateBarcodeOutDoc, oShipment, shipmentTemplateElem, oMatchingLine);
    } finally {
      cat.endTimer("registerBarcodeForBackroomPick");
    } 
  }
  
  private void validateInputDocument(YFSContext ctx, YFCDocument inDoc) {
    YFCElement inDocElem = null;
    if (inDoc != null)
      inDocElem = inDoc.getDocumentElement(); 
    if (inDoc == null || inDocElem == null || !YFCCommon.equals(inDocElem.getTagName(), "BarCode")) {
      YFCException ex = new YFCException("YCP0073");
      throw ex;
    } 
    YFCElement oContextualInfo = inDocElem.getChildElement("ContextualInfo");
    if (oContextualInfo == null || YFCUtils.isVoid(oContextualInfo.getAttribute("OrganizationCode"))) {
      YFCException ex = new YFCException("YFS10513");
      throw ex;
    } 
    if (YFCUtils.isVoid(inDocElem.getAttribute("BarCodeType"))) {
      YFCException ex = new YFCException("YCP0186");
      throw ex;
    } 
    if (YFCUtils.isVoid(inDocElem.getAttribute("BarCodeData"))) {
      YFCException ex = new YFCException("YCP0187");
      throw ex;
    } 
    YFCElement shipmentElem = inDocElem.getChildElement("ShipmentContextualInfo");
    if (shipmentElem == null) {
      YFCException ex = new YFCException("YDM00065");
      throw ex;
    } 
    String sShipmentKey = shipmentElem.getAttribute("ShipmentKey");
    String sShipmentNo = shipmentElem.getAttribute("ShipmentNo");
    String sShipNode = shipmentElem.getAttribute("ShipNode");
    String sSellerOrg = shipmentElem.getAttribute("SellerOrganizationCode");
    if (YFCObject.isVoid(sShipmentKey) && 
      YFCObject.isVoid(sShipmentNo) && YFCObject.isVoid(sSellerOrg) && YFCObject.isVoid(sShipNode)) {
      YFCException ex = new YFCException("YDM00065");
      throw ex;
    } 
  }
  
  private YFS_Shipment getShipment(YFSContext ctx, YFCElement shipmentElem) {
    try {
      cat.beginTimer("getShipment");
      String sShipmentKey = shipmentElem.getAttribute("ShipmentKey");
      String sShipmentNo = shipmentElem.getAttribute("ShipmentNo");
      String sShipNode = shipmentElem.getAttribute("ShipNode");
      String sSellerOrg = shipmentElem.getAttribute("SellerOrganizationCode");
      YFS_Shipment oShipment = YCDFoundationBridge.getInstance().getShipment(ctx, sShipmentKey, sShipmentNo, sShipNode, sSellerOrg);
      if (oShipment == null) {
        YFCException ex = new YFCException("YDM00289", shipmentElem.toString());
        throw ex;
      } 
      return oShipment;
    } finally {
      cat.endTimer("getShipment");
    } 
  }
  
  private YFCDocument callTranslateBarcode(YFSContext ctx, YFCElement inputElem) {
    try {
      cat.beginTimer("callTranslateBarcode");
      YFCElement itemContextElem = inputElem.getChildElement("ItemContextualInfo");
      YFCElement contextElem = inputElem.getChildElement("ContextualInfo");
      YFCDocument translateBarcodeInputDoc = YFCDocument.createDocument("BarCode");
      YFCElement translateBarcodeInput = translateBarcodeInputDoc.getDocumentElement();
      String barcodeData = inputElem.getAttribute("BarCodeData");
      if(barcodeData.indexOf(":") > -1) {
    	  ctx.setUserObject("LotNumber", barcodeData.split(":")[1]);
    	  barcodeData = barcodeData.split(":")[0];
      }
      translateBarcodeInput.setAttribute("BarCodeData", barcodeData);
      translateBarcodeInput.setAttribute("BarCodeType", inputElem.getAttribute("BarCodeType"));
      if (itemContextElem != null)
        translateBarcodeInput.createChild("ItemContextualInfo").setAttributes(itemContextElem.getAttributes()); 
      if (contextElem != null)
        translateBarcodeInput.createChild("ContextualInfo").setAttributes(contextElem.getAttributes()); 
      if (cat.isDebugEnabled())
        cat.debug("Input to translateBarcode:" + translateBarcodeInput); 
      YFCDocument translateBarcodeOutputDoc = YCDFoundationBridge.getInstance().translateBarCode((YFSEnvironment)ctx, translateBarcodeInputDoc.getDocument(), null);
      if (cat.isDebugEnabled())
        cat.debug("Output from translateBarcode:" + translateBarcodeOutputDoc); 
      return translateBarcodeOutputDoc;
    } finally {
      cat.endTimer("callTranslateBarcode");
    } 
  }
  
  private YFS_Shipment_Line findMatchingShipmentLine(YFSContext ctx, YFS_Shipment oShipment, YFCElement locationElem, YFCElement translateBarcodeOutput, String productClass) {
    try {
      cat.beginTimer("findMatchingShipmentLine");
      YFCElement translation = translateBarcodeOutput.getChildElement("Translations").getChildElement("Translation");
      YFCElement itemContElem = translation.getChildElement("ItemContextualInfo");
      String sItemId = itemContElem.getAttribute("ItemID");
      String sUom = itemContElem.getAttribute("InventoryUOM");
      String sKitCode = itemContElem.getAttribute("KitCode");
      double dQty = itemContElem.getDoubleAttribute("Quantity");
      if (!YFCDoubleUtils.greaterThan(dQty, 0.0D)) {
        dQty = 1.0D;
        itemContElem.setDoubleAttribute("Quantity", dQty);
      } 
      ctx.setUserObject("LotPickQty", dQty);
      String sPickLocation = (locationElem != null) ? locationElem.getAttribute("LocationId") : "";
      YFS_Shipment_Line oMatchingLine = null;
      List<YFS_Shipment_Line> lineList = oShipment.getShipmentLineList();
      for (Iterator<YFS_Shipment_Line> ite = oShipment.getShipmentLineList().iterator(); ite.hasNext(); ) {
        YFS_Shipment_Line oLine = ite.next();
        if (!YFCObject.equals(oLine.getItem_Id(), sItemId))
          continue; 
        if (!YFCObject.isVoid(sUom) && !YFCObject.equals(oLine.getUom(), sUom))
          continue; 
        if (!YFCObject.isVoid(sKitCode) && !YFCObject.equals(oLine.getKit_Code(), sKitCode))
          continue; 
        if (!YFCObject.isVoid(sPickLocation) && !YFCObject.equals(sPickLocation, oLine.getPick_Location()))
          continue; 
        oMatchingLine = oLine;
        double d = (oMatchingLine.getBackroom_Picked_Qty() == null) ? 0.0D : oMatchingLine.getBackroom_Picked_Qty().doubleValue();
        if (YFCDoubleUtils.greaterThanEqualTo(oMatchingLine.getQuantity(), d + dQty)) {
          oMatchingLine.setBackroom_Picked_Qty(d + dQty);
          appendShipmentLineInformationToOutput(ctx, oMatchingLine, translation);
          return oMatchingLine;
        } 
      } 
      if (oMatchingLine == null) {
        YFCException yFCException = new YFCException("YCD00066");
        yFCException.setAttribute("ItemID", sItemId);
        yFCException.setAttribute("UnitOfMeasure", sUom);
        throw yFCException;
      } 
      YFCException ex = new YFCException("YCD00067");
      ex.setAttribute("ItemID", sItemId);
      ex.setAttribute("UnitOfMeasure", sUom);
      ex.setAttribute("ScannedQUantity", Double.toString(dQty));
      double dBackroomPickedQty = (oMatchingLine.getBackroom_Picked_Qty() == null) ? 0.0D : oMatchingLine.getBackroom_Picked_Qty().doubleValue();
      ex.setAttribute("BackroomPickedQty", Double.toString(dBackroomPickedQty));
      ex.setAttribute("Quantity", Double.toString(oMatchingLine.getQuantity()));
      throw ex;
    } finally {
      cat.endTimer("findMatchingShipmentLine");
    } 
  }
  
  private void appendShipmentLineInformationToOutput(YFSContext ctx, YFS_Shipment_Line oMatchingLine, YFCElement translation) {
    YFCElement shipmentContextElem = translation.getChildElement("ShipmentContextualInfo", true);
    YFS_Shipment oShipment = oMatchingLine.getShipment();
    shipmentContextElem.setAttribute("SellerOrganizationCode", oShipment.getSeller_Organization_Code());
    shipmentContextElem.setAttribute("ShipNode", oShipment.getShipnode_Key());
    shipmentContextElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
    shipmentContextElem.setAttribute("ShipmentNo", oShipment.getShipment_No());
    shipmentContextElem.setAttribute("ShipmentLineKey", oMatchingLine.getShipment_Line_Key());
  }
  
  private YFCElement callChangeShipment(YFSContext ctx, YFS_Shipment oShipment, YFS_Shipment_Line oMatchingLine) {
    try {
      cat.beginTimer("callChangeShipment");
      YFCDocument changeShipmentInputDoc = YFCDocument.createDocument("Shipment");
      YFCElement changeShipmentElem = changeShipmentInputDoc.getDocumentElement();
      changeShipmentElem.setAttribute("ShipmentKey", oShipment.getShipment_Key());
      YFCElement lineElem = changeShipmentElem.createChild("ShipmentLines").createChild("ShipmentLine");
      lineElem.setAttribute("ShipmentLineKey", oMatchingLine.getShipment_Line_Key());
      lineElem.setAttribute("BackroomPickedQty", oMatchingLine.getBackroom_Picked_Qty());
      if(!YFCCommon.isVoid(ctx.getUserObject("LotNumber"))) {
    	  YFCElement eShipmentTagSerial = lineElem.getChildElement("ShipmentTagSerials", true).createChild("ShipmentTagSerial");
    	  eShipmentTagSerial.setAttribute("LotNumber", (String) ctx.getUserObject("LotNumber"));
    	  eShipmentTagSerial.setAttribute("Quantity", (double) ctx.getUserObject("LotPickQty"));
    	  ctx.removeUserObject("LotNumber");
    	  ctx.removeUserObject("LotPickQty");    	  
      }
      YFCDocument changeShipmentTemplateDoc = YFCDocument.createDocument("Shipment");
      YFCElement changeShipmentTemplate = changeShipmentTemplateDoc.getDocumentElement();
      changeShipmentTemplate.setAttribute("ShipmentKey", "");
      if (cat.isDebugEnabled()) {
        cat.debug("Input to changeShipment:" + changeShipmentElem);
        cat.debug("Template to changeShipment:" + changeShipmentTemplateDoc);
      } 
      YCDFoundationBridge.getInstance();
      YFCDocument changeShipmentOutDoc = YCDFoundationBridge.invokeAPI((YFSEnvironment)ctx, "changeShipment", changeShipmentInputDoc, changeShipmentTemplateDoc);
      if (cat.isDebugEnabled())
        cat.debug("Output from changeShipment:" + changeShipmentOutDoc); 
      return changeShipmentOutDoc.getDocumentElement();
    } finally {
      cat.endTimer("callChangeShipment");
    } 
  }
  
  private void invokeUpdateLocationInventoryUE(YFSContext ctx, YFS_Shipment oShipment, YFS_Shipment_Line oMatchingLine) {
    try {
      cat.beginTimer("invokeUpdateLocationInventoryUE");
      YFCElement invokeUEInputElement = getUpdateInventoryUEInput(oShipment, oMatchingLine);
      if (cat.isDebugEnabled())
        cat.debug("Input to UpdateLocationInventoryUE:" + invokeUEInputElement); 
      YCDUpdateLocationInventoryUE ue = (YCDUpdateLocationInventoryUE)YCDFoundationBridge.getInstance().getUserExit(ctx, "ycd.ue.updateLocationInventory.class", oShipment
          .getEnterprise_Code(), oShipment.getDocument_Type());
      if (YFCObject.isNull(ue)) {
        cat.warn("No user exit defined for updateLocationInventoryUE. Skipping update location inventory.");
      } else {
        try {
          YCPUEDefaultImpl proxy = (YCPUEDefaultImpl)ue;
          proxy.setDefaultErrorCode("YCD00030");
          ue.updateLocationInventory((YFSEnvironment)ctx, invokeUEInputElement.getOwnerDocument().getDocument());
        } catch (YFSUserExitException e) {
          cat.error("Exception in the execution of invokeUpdateLocationInventoryUE method of YCDRegisterBarcodeForBackroomPick");
        } 
      } 
    } finally {
      cat.endTimer("invokeUpdateLocationInventoryUE");
    } 
  }
  
  private YFCElement getUpdateInventoryUEInput(YFS_Shipment oShipment, YFS_Shipment_Line oMatchingLine) {
    YFCElement updateLocationInventoryUEInput = YFCDocument.createDocument("UpdateLocationInventory").getDocumentElement();
    updateLocationInventoryUEInput.setAttribute("EnterpriseCode", oShipment.getEnterprise_Code());
    updateLocationInventoryUEInput.setAttribute("Node", oShipment.getShipnode_Key());
    YFCElement inventoryElement = updateLocationInventoryUEInput.createChild("InventoryList").createChild("Inventory");
    inventoryElement.setAttribute("Quantity", oMatchingLine.getQuantity());
    YFCElement invItemElement = inventoryElement.createChild("InventoryItem");
    invItemElement.setAttribute("ItemID", oMatchingLine.getItem_Id());
    invItemElement.setAttribute("ProductClass", oMatchingLine.getProduct_Class());
    invItemElement.setAttribute("UnitOfMeasure", oMatchingLine.getUom());
    return updateLocationInventoryUEInput;
  }
  
  private YFCDocument prepareOutputDocument(YFSContext ctx, YFCDocument translateBarcodeOutDoc, YFS_Shipment oShipment, YFCElement shipmentTemplateElem, YFS_Shipment_Line oMatchingLine) {
    if (shipmentTemplateElem == null)
      return translateBarcodeOutDoc; 
    YFCElement shipmentOutput = oShipment.getXML(translateBarcodeOutDoc, shipmentTemplateElem);
    YFS_Shipment shipmentObj = YCDFoundationBridge.getInstance().getShipment(ctx, shipmentOutput.getAttribute("ShipmentKey"));
    YFS_Shipment_Line shipmentLineObj = shipmentObj.getShipmentLine(oMatchingLine.getShipment_Line_Key());
    YFCElement shipmentLineTemplateElem = shipmentTemplateElem.getChildElement("ShipmentLine");
    YFCElement shipmentLineOutput = shipmentLineObj.getXML(YFCDocument.createDocument("ShipmentLine"), shipmentLineTemplateElem);
    shipmentOutput.importNode((YFCNode)shipmentLineOutput);
    translateBarcodeOutDoc.getDocumentElement().importNode((YFCNode)shipmentOutput);
    return translateBarcodeOutDoc;
  }


}
