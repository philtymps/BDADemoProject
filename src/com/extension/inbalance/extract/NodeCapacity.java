package com.extension.inbalance.extract;

public class NodeCapacity {

	private String sNode, sUOM;
	private float dStandCap, dStandExt, dOverCap, dOverExt, dUtilized;
	
	public NodeCapacity(String sNode, String sUOM, float dStandCap, float dStandExt, float dOverCap, float dOverExt,
			float dUtilized) {
		super();
		this.sNode = sNode;
		this.sUOM = sUOM;
		this.dStandCap = dStandCap;
		this.dStandExt = dStandExt;
		this.dOverCap = dOverCap;
		this.dOverExt = dOverExt;
		this.dUtilized = dUtilized;
	}
	
	public NodeCapacity(String sNode, float fCapacity, float fUtilized){
		this.sNode = sNode;
		this.dOverCap = fCapacity;
		this.dUtilized = fUtilized;
	}
	
	public String getNode() {
		return sNode;
	}
	public void setNode(String sNode) {
		this.sNode = sNode;
	}
	public String getUOM() {
		return sUOM;
	}
	public void setUOM(String sUOM) {
		this.sUOM = sUOM;
	}
	public double getStandCap() {
		return dStandCap;
	}
	public float getCapacity(){
		if(dOverCap > -1){
			return dOverCap;
		} else {
			return dStandCap;
		}
	}
	
	public float getSupCap(){
		if(dOverCap > -1){
			return dOverExt;
		} else {
			return dStandExt;
		}
	}
	
	public double getRemainingCap(){
		return getCapacity() - getUtilized();
	}
	
	public void setStandCap(float dStandCap) {
		this.dStandCap = dStandCap;
	}
	public float getStandExt() {
		return dStandExt;
	}
	public void setStandExt(float dStandExt) {
		this.dStandExt = dStandExt;
	}
	public float getOverCap() {
		return dOverCap;
	}
	public void setOverCap(float dOverCap) {
		this.dOverCap = dOverCap;
	}
	public float getOverExt() {
		return dOverExt;
	}
	public void setOverExt(float dOverExt) {
		this.dOverExt = dOverExt;
	}
	public float getUtilized() {
		return dUtilized;
	}
	public void setUtilized(float dUtilized) {
		this.dUtilized = dUtilized;
	}
	
	
	
}
