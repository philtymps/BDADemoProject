package com.ue;

import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.pca.ycd.japi.ue.YCDGetTrackingNumberURLUE;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

public class DemoGetTrackingNumberURLUE implements YCDGetTrackingNumberURLUE {
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(DemoGetTrackingNumberURLUE.class);
	
	public DemoGetTrackingNumberURLUE(){
		properties = new Properties();
	}
	
	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public Object getProperty(String sProp){
		return this.properties.get(sProp);
	}
	
	private String getTrackingURL(){
		if (!YFCCommon.isVoid(getProperty("URL"))){
			return (String) getProperty("URL");
		}
		return "http://oms.omfulfillment.com/bps_shipments/";
	}
	
	private YFCElement eGetRelease(YFSEnvironment env, String sOrderReleaseKey){
		try {
			YFCDocument dInput = YFCDocument.createDocument("OrderReleaseDetail");
			dInput.getDocumentElement().setAttribute("OrderReleaseKey", sOrderReleaseKey);
			YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
			env.setApiTemplate("getOrderReleaseDetails", getOrderReleaseDetails());
			Document l_OutputXml = localApi.invoke(env, "getOrderReleaseDetails", dInput.getDocument());
			YFCElement output = YFCDocument.getDocumentFor(l_OutputXml).getDocumentElement();
			return output;
		} catch(Exception yex) {
			logger.info("The error thrown was: " );    
			logger.info(yex.toString());
        } 
		return null;
	}
	
	private Document getOrderReleaseDetails(){
		YFCDocument dOutput = YFCDocument.createDocument("OrderRelease");
		YFCElement eOrderRelease = dOutput.getDocumentElement();
		eOrderRelease.getAttribute("OrderDate", "");
		eOrderRelease.getAttribute("OrderType", "");
		eOrderRelease.getAttribute("CarrierServiceCode", "");
		eOrderRelease.getAttribute("SCAC", "");
		YFCElement ePersonInfoShipTo = eOrderRelease.createChild("PersonInfoShipTo");
		ePersonInfoShipTo.setAttribute("AddressLine1", "");
		ePersonInfoShipTo.setAttribute("City", "");
		ePersonInfoShipTo.setAttribute("State", "");
		ePersonInfoShipTo.setAttribute("ZipCode", "");
		ePersonInfoShipTo.setAttribute("Country", "");
		ePersonInfoShipTo.setAttribute("FirstName", "");
		ePersonInfoShipTo.setAttribute("LastName", "");
		YFCElement eShipNodePersonInfo = eOrderRelease.createChild("ShipNodePersonInfo");
		eShipNodePersonInfo.setAttribute("AddressLine1", "");
		eShipNodePersonInfo.setAttribute("City", "");
		eShipNodePersonInfo.setAttribute("State", "");
		eShipNodePersonInfo.setAttribute("ZipCode", "");
		eShipNodePersonInfo.setAttribute("Country", "");
		eShipNodePersonInfo.setAttribute("FirstName", "");
		eShipNodePersonInfo.setAttribute("LastName", "");
		return dOutput.getDocument();
	}
	@Override
	public Document getTrackingNumberURL(YFSEnvironment env, Document input) throws YFSUserExitException {
		YFCDocument dOutput = YFCDocument.createDocument("TrackingNumbers");
		YFCElement eOutput = dOutput.getDocumentElement();
		
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		for (YFCElement eTrackingNumber : eInput.getChildren()){
			YFCElement eOutputTracking = eOutput.createChild("TrackingNumber");
			eOutputTracking.setAttribute("RequestNo", eTrackingNumber.getAttribute("RequestNo"));
			if (!YFCCommon.isVoid(eTrackingNumber.getAttribute("TrackingNo")) && eTrackingNumber.getAttribute("TrackingNo").startsWith("1B")){
				YFCElement eRelease = eGetRelease(env, eTrackingNumber.getAttribute("TrackingNo").substring(2));
				YFCElement ePersonInfo = eRelease.getChildElement("PersonInfoShipTo");
				if (!YFCCommon.isVoid(eRelease)){
					eOutputTracking.setAttribute("URL", getTrackingURL() + "1?tracking_no=" + eTrackingNumber.getAttribute("TrackingNo") + "&city=" + ePersonInfo.getAttribute("City") + "&state=" + ePersonInfo.getAttribute("State") + "&zipcode=" + ePersonInfo.getAttribute("ZipCode") + "&country=" + ePersonInfo.getAttribute("Country") + "&name=" + ePersonInfo.getAttribute("FirstName") + " " + ePersonInfo.getAttribute("LastName") + "&orderdate=" + eRelease.getAttribute("OrderDate") + "&carrier=" + ePersonInfo.getAttribute("CarrierServiceCode"));
				}
			} else if (!YFCCommon.isVoid(eTrackingNumber.getAttribute("TrackingNo")) && eTrackingNumber.getAttribute("TrackingNo").startsWith("JD")){
				eOutputTracking.setAttribute("URL", "https://www.myyodel.co.uk/tracking/" + eTrackingNumber.getAttribute("TrackingNo"));
			} else if (!YFCCommon.isVoid(eTrackingNumber.getAttribute("TrackingNo"))){
				eOutputTracking.setAttribute("URL", getTrackingURL() + "1?tracking_no=" + eTrackingNumber.getAttribute("TrackingNo"));
			}
		}
		return dOutput.getDocument();
	}

}
