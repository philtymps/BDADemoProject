package com.ibm.labservices.configurator.expansionhandlers;
		
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.comergent.api.apps.configurator.IExpansionHandler;
import com.comergent.api.appservices.rulesEngine.IState;

/**
 * Purpose: If Option A AND Option B is selected then select this component. Also, if Option A AND NOT Option B, then select this component.
 * 
 * Parameters: PropName1 - Name of property containing result of childgather call. (Example of property contents: "A,B,D,F")
 * 			   PropName2 - Name of property containing other list. (Example of property contents: "B,C")
 * 
 * Returns: The intersection of the contents of the property PropName1 and the contents of the property
 * of PropName2. If no intersection, returns empty string.
 * 
 * Usage: intersection(PropA, PropB, <delim>)
 * note: delim is an optional parameter >> if no delimiter is given semi-colon (;) is assumed
 * 
 * @author Julie Cappello 10/12/14
 * 
 */

public class Intersection implements IExpansionHandler {

	@Override
	public String handle(IState iState, ArrayList arraylist) {
		
		String propertyA, propertyB, delim, setA, setB;
		
		//Trim for white spaces
		Iterator<String> i = arraylist.iterator();
		if(i.hasNext()){ propertyA = i.next().trim(); }
		else{ return ""; }
		if(i.hasNext()){ propertyB = i.next().trim(); }
		else{ return ""; }
		if(i.hasNext()){ delim = i.next().trim(); }
		else { delim = ";"; }
		
		//Get contents of string properties passed
		setA = iState.getStringProperty(propertyA);
		if (setA == null){
			setA = propertyA;
		}
		setB = iState.getStringProperty(propertyB);
		if (setB == null){
			setB = propertyB;
		}
		
		return intersect(setA, setB, delim);
	}

	private String intersect(String s1, String s2, String delimeter){
		
		if(!s1.equals(null) || !s2.equals(null)){
			
			String[] set1 = s1.split(delimeter);
			String[] set2 = s2.split(delimeter);
			
			Set<String> items = new LinkedHashSet<String>(Arrays.asList(set1));
	        items.retainAll(Arrays.asList(set2));
	        
	        return setToString(items.toArray(new String[items.size()]), delimeter);
		}
		
		return null;

	}
	
	private static String setToString(String[] s, String delim){
		
		if(s.equals(null)){
			return new String("");
		}
		else{
			return StringUtils.join(s, delim);
		}
		
	}	

}