package com.extension.ilog;

import org.w3c.dom.Document;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfs.japi.YFSEnvironment;

import oms.OptimizationServiceInvoker;


public class SampleClient {
	public static void main(String[] args) {
		SampleClient t = new SampleClient();
		t.calliLogOptimizationService(null, YFCDocument.createDocument("Temp").getDocument());
	}
	
	public Document calliLogOptimizationService(YFSEnvironment env, Document inputDoc){
		String odmapp = getRemoteOdmapp();
		String mapperClass = "com.extension.ilog.MockOmsToOptimizerMapper";
		String reverseMapperClass = "com.extension.ilog.MockOptimizerToOmsMapper";
		try {
			OptimizationServiceInvoker osi = new OptimizationServiceInvoker();
			osi.run(odmapp, mapperClass, reverseMapperClass, true);

			//osi.run(odmapp, mapperClass, reverseMapperClass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputDoc;
	}
	
	public static String getLocalodmapp(){
		return "/opt/Sterling/Scripts/conf/order-fulfillment.odmapp";
	}
	
	public static String getRemoteOdmapp(){
		return "/opt/Sterling/Scripts/conf/OrderManagementOptimizationSoft.odmapp";
	}
}
