package com.scripts;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDA_GetAvailableSlots {

	
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
		YFCElement eSlots = ePromiseServiceLine.createChild("Slots");
		eSlots.setAttribute("TimeZone","");
		YFCElement eSlot = eSlots.createChild("Slot");
		eSlot.setAttribute("EndTime", "");
		eSlot.setAttribute("StartTime", "");
		YFCElement eAvailableDate = eSlot.createChild("AvailableDates").createChild("AvailableDate");
		eAvailableDate.setAttribute("CapacityAvailable", "");
		eAvailableDate.setAttribute("Date", "");
		eAvailableDate.setAttribute("RegionServiced", "");
		YFCElement eAssignment = ePromiseServiceLine.createChild("Assignments").createChild("Assignment");
		eAssignment.setAttribute("ApptStartTimestamp", "");
		eAssignment.setAttribute("ApptEndTimestamp", "");
		eAssignment.setAttribute("ShipNode", "");
		eAssignment.setAttribute("ApptDate", "");
		return dOutput.getDocument();
	}
	
	private YFCElement getJustSlot(YFCElement eSlot){
		YFCDocument dOutput = YFCDocument.createDocument("Slot");
		YFCElement eOutput = dOutput.getDocumentElement();
		eOutput.setAttributes(eSlot.getAttributes());
		return eOutput;
	}
	public Document getAvailableSlots(YFSEnvironment env, Document inputDoc){
		YFCDocument dOutput = YFCDocument.createDocument("Appointments");
		HashMap<YFCDate, ArrayList<YFCElement>> temp = new HashMap<YFCDate, ArrayList<YFCElement>>();
		YIFApi localApi;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
		
			Document dAppointmentOptions = null;
			env.setApiTemplate("getWorkOrderAppointmentOptions", getAppointmentOptionsTemplate());
			dAppointmentOptions = localApi.invoke(env, "getWorkOrderAppointmentOptions", inputDoc);
			YFCElement ePromise = YFCDocument.getDocumentFor(dAppointmentOptions).getDocumentElement();
			if(!YFCCommon.isVoid(ePromise)){
				if(!YFCCommon.isVoid(ePromise.getChildElement("SuggestedOption")) && !YFCCommon.isVoid(ePromise.getChildElement("SuggestedOption").getChildElement("Option"))){
					YFCElement eSuggestedOption = ePromise.getChildElement("SuggestedOption").getChildElement("Option");
					YFCElement eOutput = dOutput.getDocumentElement();
					eOutput.setAttribute("SuggestedApptStart", eSuggestedOption.getAttribute("FirstDate"));
					eOutput.setAttribute("SuggestedApptEnd", eSuggestedOption.getAttribute("LastDate"));
					YFCElement eSlots = eSuggestedOption.getChildElement("PormiseServiceLines", true).getChildElement("PromiseServiceLine", true).getChildElement("Slots");
					if(!YFCCommon.isVoid(eSlots)){
						eOutput.setAttribute("TimeZone", eSlots.getAttribute("TimeZone"));
						for (YFCElement eSlot : eSlots.getChildren()){
							for(YFCElement eAvailableDate : eSlot.getChildElement("AvailableDates", true).getChildren()){
								if(eAvailableDate.getBooleanAttribute("CapacityAvailable", false)){
									ArrayList<YFCElement> slots = temp.get(eAvailableDate.getDateAttribute("Date"));
									if(YFCCommon.isVoid(slots)){
										slots = new ArrayList<YFCElement>();
										temp.put(eAvailableDate.getDateAttribute("Date"), slots);
									}
									slots.add(getJustSlot(eSlot));
								}
							}
						}
						YFCElement eInput = YFCDocument.getDocumentFor(inputDoc).getDocumentElement();
						YFCDate startDate = new YFCDate(true);
						if(!YFCCommon.isVoid(eInput.getChildElement("Overrides")) && !YFCCommon.isVoid(eInput.getChildElement("Overrides").getAttribute("ReqDeliveryDate"))){
							startDate = eInput.getChildElement("Overrides").getDateAttribute("ReqDeliveryDate");
						}
						for(int i = 0; i < 60; i++){
							if(temp.containsKey(startDate)){
								for(YFCElement slot : temp.get(startDate)){
									YFCElement eAppointment = eOutput.createChild("Appointment");
									eAppointment.setAttribute("Date", startDate);
									eAppointment.setAttributes(slot.getAttributes());
								}
								startDate.changeDate(1);
							}
						}
					}
					
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
		
		return dOutput.getDocument();
	}
		
}
