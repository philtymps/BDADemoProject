package com.ibm.labservices.configurator.expansionhandlers;

import java.util.ArrayList;

import com.comergent.api.apps.configurator.IExpansionHandler;
import com.comergent.api.appservices.rulesEngine.IProperty;
import com.comergent.api.appservices.rulesEngine.IState;
import com.comergent.api.appservices.rulesEngine.IStateEntry;
import com.comergent.apps.configurator.main.ConfigState;

/**
 * Works like out of the box Gather expanader but supports delimiter.
 */
public class GatherValues implements IExpansionHandler {
	public String handle(IState iState, ArrayList args) {
		ConfigState state = (ConfigState) iState;
		String ret = "";
		if (args.size() > 0) {
			String propName = (String) args.get(0);
			String delimiter = ";";
			if (args.size() > 1) {
				delimiter = (String) args.get(1);
			}
			ArrayList entries = state.buildMatchingEntriesList(propName);
			if (entries != null) {
				for (int i = 0; i < entries.size(); ++i) {
					IStateEntry cse = (IStateEntry) entries.get(i);
					IProperty aProp = cse.getProperty();
					if (ret.length() > 0) {
						ret = ret + delimiter + aProp.getValue().toString();
					}
					else {
						ret = aProp.getValue().toString();
					}
				}
			}
		}
		return ret;
	}
}