package com.ibm.sterling.callcenter;

import java.util.HashMap;

import com.sterlingcommerce.ui.web.framework.context.SCUIContext;
import com.sterlingcommerce.ui.web.framework.extensions.ISCUIExtensionsPathProviderV2;
import com.yantra.yfc.util.YFCCommon;

public class PathProvider implements ISCUIExtensionsPathProviderV2 {

	@Override
	public String getExtnPathIdentifier(SCUIContext ctx) {
		if(!YFCCommon.isVoid(ctx.getCallingOrganizationCode())){
			return ctx.getCallingOrganizationCode();
		}
		return "DEFAULT";
	}

	@Override
	public HashMap<String, String> getExtnPathIdentifierHierarchy(String pathID,
			SCUIContext ctx) {
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put(pathID, "DEFAULT");
		return temp;
	}

	@Override
	public boolean isPathBasedExtnEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isPathBasedExtnEnabledForUser(SCUIContext ctx) {
		if(!YFCCommon.isVoid(ctx.getCallingOrganizationCode())){
			return !YFCCommon.equals(ctx.getCallingOrganizationCode(), "DEFAULT");
		}
		return false;
	}

}
