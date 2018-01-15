package com.bda;

public class Utilities {

	public static boolean getBoolean(String str, boolean defaultRetValue) {
		if (isVoid(str))
			return defaultRetValue;
		str = str.toLowerCase();
		return (str.equals("true") || str.equals("y") || str.equals("yes"));
	}
	
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
}
