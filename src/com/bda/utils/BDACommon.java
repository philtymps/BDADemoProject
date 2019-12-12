package com.bda.utils;

public class BDACommon {

	public static boolean isVoid(Object obj){
		if(obj == null){
			return true;
		} else if(obj instanceof String){
			String var = (String) obj;
			return var.equals("");
		}
		return false;
	}
	
	public static boolean equals(Object a, Object b){
		if(a == null && b == null){
			return true;
		} else if(a == null || b == null){
			return false;
		} else if(a instanceof String && b instanceof String){
			return a.equals(b);
		} else {
			return a == b;
		}
	}
	
	public static String getValue(Object c) {
		return (String) c;
	}
}
