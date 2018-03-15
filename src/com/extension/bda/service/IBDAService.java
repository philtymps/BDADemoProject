package com.extension.bda.service;

import java.util.Properties;

import org.w3c.dom.Document;

import com.yantra.yfs.japi.YFSEnvironment;

public interface IBDAService {

	public String getServiceName();
	public void setProperties(Properties props);
	public Document invoke (YFSEnvironment env, Document input) throws Exception;
}
