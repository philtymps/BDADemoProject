package com.extension.bda.service.iv;

import org.w3c.dom.Document;

import com.yantra.yfs.japi.YFSEnvironment;

public class RunInventoryRequests implements Runnable {

	private static boolean running = false;
	public static synchronized boolean isRunning() {
		return running;
	}
	public static synchronized void setRunning(boolean value) {
		running = value;
	}
	
	private YFSEnvironment env;
	private Document dResponse;
	public RunInventoryRequests(YFSEnvironment env, Document dInput) {
		this.env = env;
		this.dResponse = dInput;
	}
	@Override
	public void run() {
		RunInventoryRequests.setRunning(true);
		BDAClearInventory.wipeData(env, dResponse);
		RunInventoryRequests.setRunning(false);
	}

}
