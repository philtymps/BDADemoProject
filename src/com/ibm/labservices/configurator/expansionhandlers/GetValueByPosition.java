package com.ibm.labservices.configurator.expansionhandlers;

import java.util.ArrayList;

import com.comergent.api.apps.configurator.IExpansionHandler;
import com.comergent.api.apps.configurator.IItemBean;
import com.comergent.api.appservices.rulesEngine.IProperty;
import com.comergent.api.appservices.rulesEngine.IState;
import com.comergent.api.appservices.rulesEngine.IStateEntry;
import com.comergent.apps.configurator.main.ConfigState;
import com.yantra.yfc.log.YFCLogCategory;

/**
 * Returns the value by position in a delimited string
 * 
 * @author Tushar Agrawal
 */
public class GetValueByPosition implements IExpansionHandler
{
	private static final YFCLogCategory log = YFCLogCategory.instance(GetValueByPosition.class);
	@SuppressWarnings({ "rawtypes" })
	@Override
	
	public String handle(IState iState, ArrayList args)
	{
		log.beginTimer("GetValueByPosition handle method");
		ConfigState state = (ConfigState) iState;
		String returnString = "";
		if (args != null && args.size() > 0)
		{
			String delimitedStrPropName = (args.size() >= 1) ? (String) args.get(0) : null;
			String delimiterPropName = (args.size() >= 2) ? (String) args.get(1) : null;
			String positionPropName = (args.size() == 3) ? (String) args.get(2) : null;
			String delimitedStr = "";
			String delimiter = "|";
			String positionStr = "1";
			IProperty delimitedStrProp = state.getProperty(delimitedStrPropName);
			IProperty delimiterProp = state.getProperty(delimiterPropName);
			IProperty positionProp = state.getProperty(positionPropName);
			
			ArrayList props = state.buildMatchingEntriesList("TTempString");
			for (int i = 0; props != null && i < props.size(); i++) {
				IStateEntry next = (IStateEntry) props.get(i);
				log.info("GVBP TTempString Occurrences:" + next.getPath() + ":" + next.getProperty().getValue());
			}
				
			if(delimitedStrProp != null && delimitedStrProp.getValue() !=  null)
			{
				delimitedStr = delimitedStrProp.getValue().toString();
			}
			else
			{
				return "";
			}
			if(delimiterProp != null && delimiterProp.getValue() != null)
			{
				delimiter = delimiterProp.getValue().toString();
			}
			else
			{
				delimiter = delimiterPropName;
			}
			
			if(positionProp != null && positionProp.getValue() != null)
			{
				log.info("GetValueByPosition Current Object:" + ((IItemBean)state.getCurrentObject()).getFullName());
				log.info("GetValueByPosition positionProp Path:" + positionProp.getName() + ":" + positionProp.getValue());
				positionStr = positionProp.getValue().toString();
			}
			else
			{
				positionStr = positionPropName;
			}
			int position = 0;
			try
			{
				position = Integer.parseInt(positionStr);
			}
			catch(Exception ex)
			{
				position = 0;
			}
			String[] tokens = ((String) delimitedStr).split("\\" + delimiter);
			returnString = (position <= tokens.length) ? tokens[position-1] : "";
		}
		log.endTimer("GetValueByPosition handle method");
		return returnString;
	}
	
	public static void main (String args[]) {
		String delimitedStr = "1;2";
		String delimiter = ";";
		int position = 2;
		String[] tokens = ((String) delimitedStr).split("\\" + delimiter);
		String returnString = (position <= tokens.length) ? tokens[position-1] : "";
		System.out.println(returnString);

	}
}
