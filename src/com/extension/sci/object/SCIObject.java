package com.extension.sci.object;

import org.json.JSONObject;

public abstract class SCIObject {

	public SCIObject(String _id, String _internalID) {
		super();
		this._id = _id;
		this._internalID = _internalID;
	}
	
	public SCIObject(String _internalID) {
		super();
		this._internalID = _internalID;
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
	
	public abstract String getBulkAPIURL();
	
	public abstract JSONObject getBulkObject();
}
