package com.pierbridge;

import java.rmi.RemoteException;
import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class PierbridgeUtilities {

	public String getSystemProperty(YFSEnvironment env, String propertyName) {
		YFCDocument inputTemplate = YFCDocument.getDocumentFor("<GetProperty PropertyName=\"" + propertyName + "\"/>");
		YFCDocument outputTemplate = YFCDocument.getDocumentFor("<GetProperty PropertyValue=\"\" />");
		try	{
			Document propdoc = callApi(env, inputTemplate.getDocument(), outputTemplate.getDocument(), "getProperty", true);
			if (!YFCCommon.isVoid(propdoc)) {
				return propdoc.getDocumentElement().getAttribute("PropertyValue");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName, boolean isApi){
		YIFApi localApi;
	    Document dOrderOutput = null;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			if(!YFCCommon.isVoid(dTemplate)){
				env.setApiTemplate(sApiName, dTemplate);
			}			
			if(isApi) {
				dOrderOutput = localApi.invoke(env, sApiName, inDoc);
			} else {
				dOrderOutput = localApi.executeFlow(env, sApiName, inDoc);
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
			return dOrderOutput;
		}
		return null;
	}
	

}
