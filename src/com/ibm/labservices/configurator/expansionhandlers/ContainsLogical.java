package com.ibm.labservices.configurator.expansionhandlers;
		
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.comergent.api.apps.configurator.IExpansionHandler;
import com.comergent.api.apps.configurator.IItemBean;
import com.comergent.api.appservices.rulesEngine.IProperty;
import com.comergent.api.appservices.rulesEngine.IState;
import com.comergent.apps.configurator.main.ConfigState;
import com.yantra.yfc.log.YFCLogCategory;

/**
 * Purpose: If Option A AND Option B is selected then select this component. Also, if Option A AND NOT Option B, then select this component.
 * 
 * Parameters: PropName1 - Name of property containing result of childgather call. (Example of property contents: "A,B,D,F")
 * 			   PropName2 - Name of property containing other list. (Example of property contents: "B,-C")
 * 
 * Returns: True if the contents of the property ListA contains all elements of the contents of the property
 * of ListB that do not contain a "-" symbol and the contents of the property of ListA does not contain each 
 * of the elements of the contents of the property ListB that do contain a "-" symbol. Otherwise returns false. 
 * In other words, ListA property contents is a super set of ListB property contents. The order of the elements 
 * within the lists does not matter.
 * 
 * Usage: containslogical(PropA, PropB)
 * 
 * @author Julie Cappello 9/2/14 Moideen Reyas 12/02/2014
 * 
 */

public class ContainsLogical implements IExpansionHandler {
	
	private static final YFCLogCategory log = YFCLogCategory.instance(ContainsLogical.class);
	
	@Override
	public String handle(IState iState, ArrayList args) {
		log.beginTimer("ContainsLogical handle method");
		ConfigState state = (ConfigState) iState;	
		String propAValue = "";
		String delimiter = ";";
		String propBValue = "";

		if (args != null && args.size() > 1) {
			String propAName = (args.size() > 1) ? (String) args.get(0) : null;
			String propBName = (args.size() >= 2) ? (String) args.get(1) : null;
			String delimiterPropName = (args.size() == 3) ? (String) args.get(2) : null;

			IItemBean curItem = (IItemBean)iState.getCurrentObject();
			propAValue = propAName != null ? curItem.getStringProperty(propAName) : null;
			delimiter = delimiterPropName != null ? curItem.getStringProperty(delimiterPropName) : null;
			propBValue = propBName != null ? curItem.getStringProperty(propBName) : null;
			
			log.debug("Property Values from Current Object propAValue:" + propAValue + ",Delim:" + delimiter +  ",propBValue:" + propBValue);
			
			if (propAValue == null || "".equals(propAValue)) {
				IProperty delimitedStrProp = propAName != null ? state.getProperty(propAName) : null;
				if(delimitedStrProp != null && delimitedStrProp.getValue() !=  null) {
					propAValue = delimitedStrProp.getValue().toString();
				} else {
					propAValue =  propAName;
				}
			}
			
			if (delimiter == null || "".equals(delimiter)) {
				IProperty delimiterProp = delimiterPropName != null ? state.getProperty(delimiterPropName) : null;
				if(delimiterProp != null && delimiterProp.getValue() != null) {
					delimiter = delimiterProp.getValue().toString();
				} else {
					delimiter = delimiterPropName;
				}
				if (delimiter == null || "".equals(delimiter)) {
					delimiter = ";";
				}
			}
			
			if (propBValue == null || "".equals(propBValue)) {
				IProperty valueProp = propBName != null ? state.getProperty(propBName) : null;
				if(valueProp != null && valueProp.getValue() != null) {
					propBValue = valueProp.getValue().toString();
				} else {
					propBValue = propBName;
				}
			}			
		}
		log.debug("Final Property Values propAValue:" + propAValue + ",Delim:" + delimiter +  ",propBValue:" + propBValue);	
		
		String outPut =  checkContains(propAValue, propBValue, delimiter);		
		log.endTimer("ContainsLogical handle method");
		return outPut;
	}

	private String checkContains(String s1, String s2, String delim){
		
		//Split by semi-colon
		String[] set1 = s1.split(delim);
		String[] set2 = s2.split(delim);
		
		//Trim for white spaces
	    for(int i = 0; i < set1.length; i++){
	    	set1[i] = set1[i].trim();
	    }
	    
	    //Trim for white spaces
	    for(int i = 0; i < set2.length; i++){
	    	set2[i] = set2[i].trim();
	    }
		
		Set<String> items = new LinkedHashSet<String>(Arrays.asList(set1));
		Set<String> items2 = new LinkedHashSet<String>(Arrays.asList(set2));
		
		Set<String> notIn = new LinkedHashSet<String>();
		
		//Check if any "-" in string, if so add to "notIn" set
		Iterator<String> i = items2.iterator();
		while(i.hasNext()){
			String test = i.next();
			if(test.contains("-")){
				String temp = test.substring(1, test.length());
				notIn.add(temp);
				items2.remove(test);
			}
		}
		
		//Remove all elements of the "notIn" set from the set.
		set2 = items2.toArray(new String[items2.size()]);
		String[] removeCheck = notIn.toArray(new String[notIn.size()]);

		
		//Return true if set2 is subset of set1.
        boolean isSubset = items.containsAll(Arrays.asList(set2));
        if(isSubset){
        	boolean doesNotContain = items.removeAll(Arrays.asList(removeCheck));
        			return String.valueOf(!doesNotContain);
        }
        
        return String.valueOf(false);
	}
	
	
}