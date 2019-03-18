package com.extension.bda.service.fulfillment;

import java.io.File;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.Properties;

import org.w3c.dom.Document;

import com.ibm.CallInteropServlet;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAServiceApi {
	
	protected Properties p;
	
	public BDAServiceApi(){
		p = new Properties();
	}
	
	
	public void setProperties(Properties properties) throws Exception {
		this.p = properties;
	}
	
	
	public String getOutputLocation(){
		if (p.containsKey("OutputLocation")){
			return p.getProperty("OutputLocation");
		} else {
			return "/opt/Sterling/Fulfillment/output";
		}
	}
	
	public Object getProperty(String sProp){
		return this.p.get(sProp);
	}
	protected Document callApi(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName){
		if(!YFCCommon.isVoid(env)) {
			YIFApi localApi;
		    Document dOrderOutput = null;
			try {
				localApi = YIFClientFactory.getInstance().getLocalApi();
				if(!YFCCommon.isVoid(dTemplate)){
					env.setApiTemplate(sApiName, dTemplate);
				}			
				dOrderOutput = localApi.invoke(env, sApiName, inDoc);
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
		} else {
			return CallInteropServlet.invokeApi(YFCDocument.getDocumentFor(inDoc), YFCDocument.getDocumentFor(dTemplate), sApiName, "http://oms.omfulfillment.com").getDocument();
		}
		
	
	}
	
	public boolean writeXML(String sPath, String sFile, YFCDocument output){
		validatePath(sPath);
		FileWriter fout;
		try{
			deleteExistingFile(sPath + File.separator + sFile);
			char buffer[] = new char[output.toString().length()];
			output.toString().getChars(0,output.toString().length(), buffer, 0);
			fout = new FileWriter(sPath + File.separator + sFile);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	

	protected static void validatePath(String sFilePath){
		File temp = new File(sFilePath);
		temp.mkdirs();
	}
	
	private static void deleteExistingFile(String sFile){
		File temp = new File(sFile);
		if(temp.exists()){
			temp.delete();
		}
	}
	
}
