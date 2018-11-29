package com.pierbridge.services;

import org.w3c.dom.Document;

import com.pierbridge.PierbridgeUtilities;
import com.yantra.pca.ycd.japi.ue.YCDGetTrackingNumberURLUE;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;

public class DemoGetTrackingNoURLForPierbridge extends PierbridgeUtilities implements YCDGetTrackingNumberURLUE {

	@Override
	public Document getTrackingNumberURL(YFSEnvironment env, Document inXML) throws YFSUserExitException {
		YFCDocument dInput = YFCDocument.getDocumentFor(inXML);
		YFCDocument dOutput = YFCDocument.createDocument("TrackingNumbers");
		
		for(YFCElement eTrackingNo : dInput.getDocumentElement().getChildren()) {
			String sScac = eTrackingNo.getAttribute("SCAC");
			String sTrackingNo = eTrackingNo.getAttribute("TrackingNo");
			String sRequestNo = eTrackingNo.getAttribute("RequestNo");
			
			if(!YFCCommon.isVoid(sTrackingNo) && !YFCCommon.isVoid(sScac)) {
				String trackingUrl = "";
				try {
					trackingUrl = getSystemProperty(env, "ycs." + sScac + ".track.url");
				} catch (Exception e) {
					throw new YFSException("Missing Property");
				}
				if(!YFCCommon.isVoid(trackingUrl)) {
					throw new YFSException("Missing definition for: " + sScac);
				}
				YFCElement eOutput = dOutput.getDocumentElement().createChild("TrackingNumber");
				eOutput.setAttribute("RequestNo", sRequestNo);
				eOutput.setAttribute("URL", trackingUrl + sTrackingNo);
			} else {
				throw new YFSException("Missing either Scac or Tracking No");
			}
		}
		
		
		return dOutput.getDocument();
	}
	
	
}
