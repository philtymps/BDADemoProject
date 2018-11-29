package com.extension.sci.object;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yantra.yfc.date.YDate;

public abstract class SCIObject {

	private JSONObject _obj;
	
	public SCIObject() {
		_obj = new JSONObject();
	}
	
		
	protected void setString(String sKey, String sValue){
		_obj.put(sKey, sValue);
	}
	
	protected void setDouble(String sKey, Double sValue){
		_obj.put(sKey, sValue);
	}
	
	protected void setDate(String sKey, YDate sValue){
		_obj.put(sKey, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(sValue));
	}
	
	protected void setBoolean(String sKey, boolean sValue){
		_obj.put(sKey, sValue ? "Yes" : "No");
	}
	
	protected void addToArray(String sKey, JSONObject obj) {
		if(!_obj.has(sKey)) {
			_obj.put(sKey, new JSONArray());
		}
		((JSONArray) _obj.get(sKey)).put(obj);
	}
	
	protected String getString(String key){
		return (String) _obj.get(key);
	}
	protected YDate getDate(String key){
		return (YDate) _obj.get(key);
	}
	protected Double getDouble(String key){
		return (Double) _obj.get(key);
	}
	protected Boolean getBoolean(String key){
		return (Boolean) _obj.get(key);
	}
	
	protected JSONArray getArray(String key) {
		return (JSONArray) _obj.getJSONArray(key);
	}
	
	public abstract String getBulkAPIURL();

	public JSONObject getBulkObject() {
		return _obj;
	}

	
}
