package com.extension.bda.entity;

import java.rmi.RemoteException;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNode;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public abstract class BDAEntityObject extends BDATable{

	protected YFCElement eEntityData;
	
	private boolean dirty = false;
	protected void setDirty(){
		dirty = true;
	}
	protected void setClean(){
		dirty = false;
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public YFCElement getXml(){
		return eEntityData;
	}
	
	public void updateAttribute(String sAttribute, String sValue){
		eEntityData.setAttribute(sAttribute, sValue);
		setDirty();
	}
	
	public void updateAttribute(String sAttribute, int sValue){
		eEntityData.setAttribute(sAttribute, sValue);
		setDirty();
	}
	
	public YFCElement save(YFSEnvironment env){
		if(dirty){
			YFCElement eResponse = saveRecord(env, eEntityData);
			if(!YFCCommon.isVoid(eResponse)){
				dirty = false;
			}
			return eResponse;
		}
		return eEntityData;
	}
	
	protected void loadEntityData(YFCElement eInput){
		dirty = true;
		eEntityData = eInput;
	}
	
	protected void loadOneRecord(YFSEnvironment env, Map<String, String> nameValuePairs){
		dirty = false;
		eEntityData = getOneRecord(env, nameValuePairs);
	}
	
	protected void loadRecordForKey(YFSEnvironment env, int primaryKey){
		dirty = false;
		eEntityData = getRecordForKey(env, primaryKey);
	}
	
	public int getPrimaryKey(){
		return getXml().getIntAttribute(getEntity().getXmlTableKeyName());
	}
	
	protected void loadRemoteData(YFSEnvironment env, Document inDoc, Document dTemplate, String sApiName, boolean removeTop){
		YIFApi localApi;
	    Document dResponse = null;
		try {
			localApi = YIFClientFactory.getInstance().getLocalApi();
			if(!YFCCommon.isVoid(dTemplate)){
				env.setApiTemplate(sApiName, dTemplate);
			}			
			dResponse = localApi.invoke(env, sApiName, inDoc);
			if(!YFCCommon.isVoid(dResponse)){
				YFCElement eResponse = YFCDocument.getDocumentFor(dResponse).getDocumentElement();
				if(removeTop){
					YFCNode n = eEntityData.importNode(eResponse.getFirstChildElement());
					eEntityData.appendChild(n);
				} else {
					YFCNode n = eEntityData.importNode(eResponse);
					eEntityData.appendChild(n);
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
	}
}
