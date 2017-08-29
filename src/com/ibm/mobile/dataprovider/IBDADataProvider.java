package com.ibm.mobile.dataprovider;

import com.yantra.interop.japi.YIFApi;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public interface IBDADataProvider {
	public void addAdditionalData(YIFApi localApi, YFSEnvironment context, YFCElement apiInput, YFCElement apiOutput, YFCElement interestingElement, String sAttribute);
}
