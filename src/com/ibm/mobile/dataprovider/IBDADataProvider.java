package com.ibm.mobile.dataprovider;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public interface IBDADataProvider {
	public void addAdditionalData(YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute);
}
