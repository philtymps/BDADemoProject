package com.extension.bda.service.fulfillment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class BDASetRequestedDeliveryFromClassification extends BDAServiceApi implements IBDAService {

	@Override
	public String getServiceName() {
		return "setRequestedDeliveryDateFromClassification";
	}
	
	public YFCDocument getClassificationMapping() throws Exception {
		Object o = this.getProperty("Mapping");
		if(!YFCCommon.isVoid(o)) {
			return YFCDocument.getDocumentForXMLFile((String) o);
		}
		return null;
	}

	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		if(!YFCCommon.isVoid(getClassificationMapping())) {
			
			YFCElement eClassificationCodes = getClassificationMapping().getDocumentElement();
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eOrder = dInput.getDocumentElement();
			BDAChangeOrder changeOrder = new BDAChangeOrder(eOrder.getAttribute("OrderHeaderKey"));
			Calendar cal = Calendar.getInstance();
			
			if(!eOrder.getBooleanAttribute("DraftOrderFlag", false)) {
				for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()) {
					if(YFCCommon.isVoid(eOrderLine.getAttribute("ReqDeliveryDate")) && YFCCommon.equals(eOrderLine.getAttribute("DeliveryMethod", "SHP"), "SHP")) {
						if(!YFCCommon.isVoid(eOrderLine.getChildElement("ItemDetails", true).getChildElement("ClassificationCodes"))) {
							YFCElement eItemClass = eOrderLine.getChildElement("ItemDetails", true).getChildElement("ClassificationCodes");
							for(YFCElement eClassificationCode : eClassificationCodes.getChildren()) {
								if(YFCCommon.equals(eItemClass.getAttribute(eClassificationCode.getAttribute("CodeType")), eClassificationCode.getAttribute("CodeValue"))) {
									YFCElement eShipToAddress = getShipToAddress(eOrder, eOrderLine);
									for(YFCElement eLocation : eClassificationCode.getChildElement("Locations", true).getChildren()){
										if(!YFCCommon.isVoid(eShipToAddress) && eShipToAddress.getAttribute("ZipCode").equals(eLocation.getAttribute("ZipCode"))) {
											SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
											String sBeginDate = eLocation.getAttribute("BeginDate") + "/" + cal.get(Calendar.YEAR);
											String sEndDate = eLocation.getAttribute("EndDate") + "/" + cal.get(Calendar.YEAR);
											Calendar beginDate = Calendar.getInstance();
											beginDate.setTime(formatter.parse(sBeginDate));
											
											
											if(beginDate.after(cal)) {
												changeOrder.addLine(eOrderLine.getAttribute("OrderLineKey"), YDate.newDate(beginDate.getTimeInMillis()));
											} else {
												Calendar endDate = Calendar.getInstance();
												endDate.setTime(formatter.parse(sEndDate));
												
												if(endDate.before(cal)) {
													Calendar newbegin = Calendar.getInstance();
													newbegin.setTime(formatter.parse(eLocation.getAttribute("BeginDate") + "/" + (cal.get(Calendar.YEAR) + 1)));
													changeOrder.addLine(eOrderLine.getAttribute("OrderLineKey"), YDate.newDate(newbegin.getTimeInMillis()));
												}
											}
											
										}
									}									
								}
							}
						}					
					}
				}
			}
			
			if (!YFCCommon.isVoid(changeOrder.changeOrder())) {
				this.callApi(env, changeOrder.changeOrder(), null, "changeOrder");
			}
		}
		
		
		return input;
	}
	
	private YFCElement getShipToAddress(YFCElement eOrder, YFCElement eOrderLine) {
		if(!YFCCommon.isVoid(eOrderLine) && !YFCCommon.isVoid(eOrderLine.getChildElement("PersonInfoShipTo"))) {
			return eOrderLine.getChildElement("PersonInfoShipTo");
		}
		return eOrder.getChildElement("PersonInfoShipTo");
	}
	


	
	
}
