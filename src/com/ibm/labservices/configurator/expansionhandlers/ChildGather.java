package com.ibm.labservices.configurator.expansionhandlers;

import java.util.ArrayList;

import com.comergent.api.apps.configurator.IExpansionHandler;
import com.comergent.api.apps.configurator.IItemBean;
import com.comergent.api.appservices.rulesEngine.IProperty;
import com.comergent.api.appservices.rulesEngine.IState;
import com.comergent.api.appservices.rulesEngine.IStateEntry;
import com.comergent.apps.configurator.main.ConfigState;

/**
 * Works like out of the box Gather expanader but gathers only values from child items.
 */
public class ChildGather implements IExpansionHandler
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String handle(IState iState, ArrayList args)
	{
		ConfigState state = (ConfigState) iState;
		IItemBean curItem = (IItemBean)state.getCurrentObject();
		String curPath = curItem.getFullName() + ".";
		String ret = "";
		if (args.size() > 0)
		{
			String propName = (String) args.get(0);
			String delimiter = ";";
			if(args.size() > 1) {
				delimiter = (String) args.get(1);
			}
			ArrayList<IStateEntry> entries = state.buildMatchingEntriesList(propName);
			if (entries != null)
			{
				for(IStateEntry se : entries)
				{
					IProperty aProp = se.getProperty();
					String seName = se.getName();
					String sePath = se.getPath() + ".";
					if((seName.equals(propName)) && (sePath.startsWith(curPath)) && (!(sePath + seName).equals(curPath + "." + seName)))
					{
						if (ret.length() > 0)
						{
							ret = ret + delimiter + aProp.getValue().toString();
						}
						else
						{
							ret = aProp.getValue().toString();
						}
					}
				}
			}
		}
		return ret;
	}
}
