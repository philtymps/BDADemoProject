package com.extension.sci.object;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.json.JSONObject;

import com.yantra.yfc.date.YDate;
import com.yantra.yfc.util.YFCCommon;

public abstract class SCIObject {

	private HashMap<String, String> strings;
	private HashMap<String, YDate> dates;
	private HashMap<String, Double> doubles;
	private HashMap<String, Boolean> booleans;

	public SCIObject(String _id, String _internalID) {
		super();
		this._id = _id;
		this._internalID = _internalID;
		
		strings = new HashMap<String, String>();
		dates = new HashMap<String, YDate>();
		doubles = new HashMap<String, Double>();
		booleans = new HashMap<String, Boolean>();
	}
	
	public SCIObject(String _id) {
		super();
		this._id = _id;
		
		strings = new HashMap<String, String>();
		dates = new HashMap<String, YDate>();
		doubles = new HashMap<String, Double>();
		booleans = new HashMap<String, Boolean>();
	}

	private String _id, _internalID;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_internalID() {
		return _internalID;
	}

	public void set_internalID(String _internalID) {
		this._internalID = _internalID;
	}
	
	protected void setString(String sKey, String sValue){
		strings.put(sKey, sValue);
	}
	
	protected void setDouble(String sKey, Double sValue){
		doubles.put(sKey, sValue);
	}
	
	protected void setDate(String sKey, YDate sValue){
		dates.put(sKey, sValue);
	}
	
	protected void setBoolean(String sKey, boolean sValue){
		booleans.put(sKey, sValue);
	}
	
	protected String getString(String key){
		return strings.get(key);
	}
	protected YDate getDate(String key){
		return dates.get(key);
	}
	protected Double getDouble(String key){
		return doubles.get(key);
	}
	protected Boolean getBoolean(String key){
		return booleans.get(key);
	}
	public abstract String getBulkAPIURL();

	public JSONObject getBulkObject() {
		JSONObject obj = new JSONObject();
		obj.put("_id", this.get_id());
		for(String key : strings.keySet()){
			if(!YFCCommon.isVoid(strings.get(key))){
				obj.put(key, strings.get(key));
			}			
		}
		for(String key : dates.keySet()){
			if(!YFCCommon.isVoid(dates.get(key))){
				obj.put(key, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(dates.get(key)));
			}
		}
		for(String key : doubles.keySet()){
			if(!YFCCommon.isVoid(doubles.get(key))){
				obj.put(key, doubles.get(key));
			}			
		}
		for(String key: booleans.keySet()){
			obj.put(key, booleans.get(key) ? "Yes" : "No");
		}
		return obj;
	}

	
}
