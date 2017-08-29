/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Order Optimizer (5737B43)
 * (C) Copyright IBM Corp. 2016.
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S.
 * Copyright Office.
 ******************************************************************************/
package com.extension.inbalance;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MongoToolException extends Exception {
	private static final long serialVersionUID = 1L;

	public MongoToolException(Throwable t) {
		super(t);
	}

	public MongoToolException(String format, Object... args) {
		super(String.format(format, args));
	}
	
	public static String getStackTraceString(final Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		Throwable causeT = t.getCause();
		if (causeT != null) {
			causeT.printStackTrace(pw);
		}
		else {
			t.printStackTrace(pw);
		}
    	
    	String errorStr=sw.toString();
    	pw.close();
    	return errorStr;
	}
}
