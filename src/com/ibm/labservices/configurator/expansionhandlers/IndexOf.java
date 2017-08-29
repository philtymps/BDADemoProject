package com.ibm.labservices.configurator.expansionhandlers;
		
import java.util.ArrayList;

import com.comergent.api.apps.configurator.IExpansionHandler;
import com.comergent.api.apps.configurator.IItemBean;
import com.comergent.api.appservices.rulesEngine.IProperty;
import com.comergent.api.appservices.rulesEngine.IState;
import com.comergent.apps.configurator.main.ConfigState;
import com.yantra.yfc.log.YFCLogCategory;

/**
 * Purpose: If Option A AND Option B is selected then select this component. Also, if Option A AND NOT Option B, then select this component.
 * 
 * Parameters: PropName - Name of property containing result of childgather call. (Example of property contents: "A,B,D,F")
 * 			   searchTerm - Name of property containing other list. (Example of property contents: "D")
 * 
 * Returns: The index of the searchTerm within the contents of the property PropName. If the searchTerm is not found,
 * returns -1. Otherwise, returns index starting at 1.
 * 
 * Usage: indexof(PropertyA, searchTerm, <delim>)
 * note: delim is an optional parameter >> if no delimiter is given semi-colon (;) is assumed
 * 
 * @author Julie Cappello 10/12/14 Moideen Reyas 12/02/2014
 * 
 */

public class IndexOf implements IExpansionHandler {

	private static final YFCLogCategory log = YFCLogCategory.instance(IndexOf.class);
	@SuppressWarnings({ "rawtypes" })
	@Override
	public String handle(IState iState, ArrayList args) {
		log.beginTimer("IndexOf handle method");
		ConfigState state = (ConfigState) iState;
		String returnString = "-1";
		if (args != null && args.size() > 1) {
			String delimitedStrPropName = (args.size() > 1) ? (String) args.get(0) : null;
			String valuePropName = (args.size() >= 2) ? (String) args.get(1) : null;
			String delimiterPropName = (args.size() == 3) ? (String) args.get(2) : null;

			String delimitedStr = "";
			String delimiter = ";";
			String valueStr = "";
			IItemBean curItem = (IItemBean)iState.getCurrentObject();
			
			
			delimitedStr = delimitedStrPropName != null ? curItem.getStringProperty(delimitedStrPropName) : null;
			delimiter = delimiterPropName != null ? curItem.getStringProperty(delimiterPropName) : null;
			valueStr = valuePropName != null ? curItem.getStringProperty(valuePropName) : null;
			
			log.debug("Property Values from Current Object:" + delimitedStr + ",Delim:" + delimiter +  ",Value:" + valueStr);
			if (delimitedStr == null || "".equals(delimitedStr)) {
				IProperty delimitedStrProp = delimitedStrPropName != null ? state.getProperty(delimitedStrPropName) : null;
				if(delimitedStrProp != null && delimitedStrProp.getValue() !=  null) {
					delimitedStr = delimitedStrProp.getValue().toString();
				} else {
					delimitedStr =  delimitedStrPropName;
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
			
			if (valueStr == null || "".equals(valueStr)) {
				IProperty valueProp = valuePropName != null ? state.getProperty(valuePropName) : null;
				if(valueProp != null && valueProp.getValue() != null) {
					valueStr = valueProp.getValue().toString();
				} else {
					valueStr = valuePropName;
				}
			}
			
			log.debug("Final Property Values from State Object:" + delimitedStr + ",Delim:" + delimiter +  ",Value:" + valueStr);
			
			if (delimitedStr != null && !"".equals(delimitedStr) && delimiter != null && !"".equals(delimiter)) {
				String[] tokens = ((String) delimitedStr).split("\\" + delimiter);
				if (tokens != null && tokens.length > 0 && valueStr != null && !"".equals(valueStr)) {
					for (int i=1; i<=tokens.length; i++) {
						if (valueStr.equals(tokens[i-1])) {
							returnString = i+"";
							break;
						}
					}
				}
			}
		}
		log.endTimer("IndexOf handle method");
		return returnString;
	}
	
	
	public static void main (String args[]) {
		ConfigState state = new ConfigState();
		IndexOf gpv = new IndexOf();
		ArrayList list = new ArrayList();
		for (int i=0; i < args.length; i++) {
			System.out.println(args[i]);
			list.add(args[i]);
		}		
		System.out.println("Position:" + gpv.handle(state, list));
	}

}