package com.extension.workorders;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.date.YTime;
import com.yantra.yfc.date.YTimestamp;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAGetWorkOrderAppointmentOptions implements IBDAService {
	
	public static void main(String[] args) {
		YFCDocument dInput = YFCDocument.createDocument("Promise");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("WorkOrderKey", "2020012016284629748215");
		eInput.setAttribute("ReturnMultipleSrvcSlots", "Y");
		BDAGetWorkOrderAppointmentOptions t = new BDAGetWorkOrderAppointmentOptions();
		
		Document dResponse = t.invoke(null, dInput.getDocument());
		System.out.println(YFCDocument.getDocumentFor(dResponse));
		
	}

	private Document getAppointmentOptionsTemplate(){
		YFCDocument dOutput = YFCDocument.createDocument("Promise");
		YFCElement ePromise = dOutput.getDocumentElement();
		ePromise.setAttribute("OrderHeaderKey", "");
		ePromise.setAttribute("WorkOrderKey", "");
		YFCElement eOption = ePromise.createChild("SuggestedOption").createChild("Option");
		eOption.setAttribute("FirstDate", "");
		eOption.setAttribute("HasAnyUnavailableQty", "");		
		eOption.setAttribute("LastDate", "");
		eOption.setAttribute("OptionNo", "");
		YFCElement ePromiseServiceLine = eOption.createChild("PromiseServiceLines").createChild("PromiseServiceLine");
		ePromiseServiceLine.setAttribute("OrderLineKey", "");
		ePromiseServiceLine.setAttribute("RequiredQty", "");
		ePromiseServiceLine.setAttribute("UnitOfMeasure", "");
		YFCElement eSlots = ePromiseServiceLine.createChild("Slots");
		eSlots.setAttribute("TimeZone","");
		YFCElement eSlot = eSlots.createChild("Slot");
		eSlot.setAttribute("EndTime", "");
		eSlot.setAttribute("StartTime", "");
		eSlot.setAttribute("ServiceSlotDesc", "");
		YFCElement eAvailableDate = eSlot.createChild("AvailableDates").createChild("AvailableDate");
		eAvailableDate.setAttribute("CapacityAvailable", "");
		eAvailableDate.setAttribute("Date", "");
		eAvailableDate.setAttribute("RegionServiced", "");
		YFCElement eResPoo = eAvailableDate.createChild("ResourcePools").createChild("ResourcePool");
		eResPoo.setAttribute("ResourcePoolId", "");
		eResPoo.setAttribute("ResourcePoolKey", "");
		YFCElement eAssignment = ePromiseServiceLine.createChild("Assignments").createChild("Assignment");
		eAssignment.setAttribute("ApptStartTimestamp", "");
		eAssignment.setAttribute("ApptEndTimestamp", "");
		eAssignment.setAttribute("ShipNode", "");
		eAssignment.setAttribute("ApptDate", "");
		YFCElement ePromiseLine = eOption.createChild("PromiseLines").createChild("PromiseLine");
		ePromiseLine.setAttribute("OrderLineKey", "");
		ePromiseLine.setAttribute("RequiredQty", "");
		eAssignment = ePromiseLine.createChild("Assignments").createChild("Assignment");
		eAssignment.setAttribute("InteractionNo", "");
		eAssignment.setAttribute("ProductAvailDate", "");
		eAssignment.setAttribute("Quantity", "");
		eAssignment.setAttribute("ShipNode", "");
		return dOutput.getDocument();
	}
	
	public Document invoke(YFSEnvironment env, Document inputDoc) {
		YFCDocument dOutput = YFCDocument.createDocument("Appointments");
		try {
			YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
			Document dAppointmentOptions = null;
			Document dPromise = BDAServiceApi.callApi(env,inputDoc, getAppointmentOptionsTemplate(), "getWorkOrderAppointmentOptions");
			YFCElement ePromise = YFCDocument.getDocumentFor(dPromise).getDocumentElement();
			YFCElement eOutput = dOutput.getDocumentElement();
			HashMap<String, ArrayList<YFCElement>> temp = new HashMap<String, ArrayList<YFCElement>>();
			if (!YFCCommon.isVoid(ePromise)) {
				if (!YFCCommon.isVoid(ePromise.getChildElement("SuggestedOption")) && !YFCCommon.isVoid(ePromise.getChildElement("SuggestedOption").getChildElement("Option"))) {
					YFCElement eSuggestedOption = ePromise.getChildElement("SuggestedOption").getChildElement("Option");
					eOutput.setAttribute("SuggestedApptStart", eSuggestedOption.getAttribute("FirstDate"));
					eOutput.setAttribute("SuggestedApptEnd", eSuggestedOption.getAttribute("LastDate"));
					eOutput.importNode(eSuggestedOption.getChildElement("PromiseLines", true));
					YFCElement eServiceLine = eSuggestedOption.getChildElement("PromiseServiceLines", true).getChildElement("PromiseServiceLine");
					if (!YFCCommon.isVoid(eServiceLine)) {
						eOutput.setAttribute("RequiredQty", eServiceLine.getAttribute("RequiredQty"));
						eOutput.setAttribute("UnitOfMeasure", eServiceLine.getAttribute("UnitOfMeasure"));
						YFCElement eSlots = eServiceLine.getChildElement("Slots");
						if (!YFCCommon.isVoid(eSlots)) {
							eOutput.setAttribute("TimeZone", eSlots.getAttribute("TimeZone"));
							for (YFCElement eSlot : eSlots.getChildren()) {
								for (YFCElement eAvailableDate : eSlot.getChildElement("AvailableDates", true).getChildren()){
									if (eAvailableDate.getBooleanAttribute("CapacityAvailable", false) && eAvailableDate.getBooleanAttribute("RegionServiced", false)) {
										YDate d = eAvailableDate.getYDateAttribute("Date");
										ArrayList<YFCElement> slots = temp.get(d.getDateString(null));
										if (YFCCommon.isVoid(slots)) {
											slots = new ArrayList<YFCElement>();
											temp.put(d.getString(), slots);
										}
										YFCElement eSlotClone = (YFCElement) eSlot.cloneNode(true);
										eSlotClone.removeChild(eSlotClone.getChildElement("AvailableDates"));
										eSlotClone.importNode(eAvailableDate);
										slots.add(eSlotClone);
									}
								}
							}
							YDate startDate = new YDate(true);
							if (!YFCCommon.isVoid(eInput.getChildElement("Overrides")) && !YFCCommon.isVoid(eInput.getChildElement("Overrides").getAttribute("ReqDeliveryDate"))) {
								startDate = YDate.newMutableDate(eInput.getChildElement("Overrides").getAttribute("ReqDeliveryDate"));
							}
							int id = 1;
							for (int i = 0; i < 60; i++) {
								if (temp.containsKey(startDate.getString())) {
									for (YFCElement slot : temp.get(startDate.getString())) {
										YFCElement eAppointment = eOutput.createChild("Appointment");
										eAppointment.setAttribute("Date", startDate.getString());
										eAppointment.setAttribute("SlotId", id++);
										YTime startTime = YTime.getYTime(slot.getAttribute("StartTime"));
										YTimestamp start = YTimestamp.newMutableTimestamp(startDate, startTime);
										YTime endTime = YTime.getYTime(slot.getAttribute("EndTime"));
										YTimestamp end = YTimestamp.newMutableTimestamp(startDate, endTime);

										eAppointment.setAttributes(slot.getAttributes());
										eAppointment.setAttribute("ApptStart", start);
										eAppointment.setAttribute("ApptEnd", end);
										
										YFCElement eAvailableDate = slot.getChildElement("AvailableDate");
										if(!YFCCommon.isVoid(eAvailableDate)) {
											eAppointment.setAttribute("ServiceSlotDesc", slot.getAttribute("ServiceSlotDesc"));
											eAppointment.setAttribute("ResourcePoolId", eAvailableDate.getChildElement("ResourcePools").getChildElement("ResourcePool").getAttribute("ResourcePoolId"));
											eAppointment.setAttribute("ResourcePoolKey", eAvailableDate.getChildElement("ResourcePools").getChildElement("ResourcePool").getAttribute("ResourcePoolKey"));
										}
										//eAppointment.setAttribute("FormattedAppt", startDate.getDateString(env.locale) + " (" + start.getString("hh:mm a", locale) + " - " + end.getString("hh:mm a", locale) + ")");
										//StringBuffer s = new StringBuffer(start.getString("yyyy-MM-dd'T'HH:mm:ssZ"));
										//s.insert(s.length() - 2, ":");
										//eAppointment.setAttribute("ApptStart", s.toString());
										//StringBuffer e = new StringBuffer(end.getString("yyyy-MM-dd'T'HH:mm:ssZ"));
										//e.insert(e.length() - 2, ":");
										//eAppointment.setAttribute("ApptEnd", e.toString());
									}
								}
								startDate.changeDate(1);
							}
						}
					}

				}
			}
		} catch (YFSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dOutput.getDocument();

	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getWorkOrderAppointmentOptions";
	}

	@Override
	public void setProperties(Properties props) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
