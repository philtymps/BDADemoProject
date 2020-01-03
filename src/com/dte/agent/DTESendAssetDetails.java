package com.dte.agent;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.japi.util.YCPBaseTaskAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfs.japi.YFSEnvironment;

public class DTESendAssetDetails extends YCPBaseTaskAgent {
	protected static YIFApi api = null;
	
	/*
	 *
	 * <?xml version="1.0" encoding="UTF-8"?>
		<TaskQueue 
		DataKey="2017111300523176633" DataType="OrderHeaderKey" HoldFlag="N"
		TaskQKey="2017111300523476638" TransactionKey="2017111223535276390">
   			<TransactionFilters Action="Get" DAYS_TO_WAIT="30" DocumentParamsKey="0001" 
   			DocumentType="0001" NumRecordsToBuffer="5000" ProcessType="ORDER_FULFILLMENT" 
   			ProcessTypeKey="ORDER_FULFILLMENT" TransactionId="DELETE_DRAFT_ORDER_AUTO.0001.ex" TransactionKey="2017111223535276390" />
		</TaskQueue>
	 */
	@Override
	public Document executeTask(YFSEnvironment env, Document doc) throws Exception {
		System.out.println("Input executeTask: " + YFCDocument.getDocumentFor(doc));
		return doc;
	}
	
	protected YIFApi getApi() throws Exception {
		if(api == null) {
			api = YIFClientFactory.getInstance().getLocalApi();
		}
    	return api;
    }

}
