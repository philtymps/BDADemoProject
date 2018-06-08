package com.extension.bda.service.giv;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.service.IBDAService;
import com.scripts.CompleteOrder;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class BDAGetMyAlerts implements IBDAService {

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "getMyAlerts";
	}

	@Override
	public void setProperties(Properties props) {
		// TODO Auto-generated method stub
		
	}
	
	private static Document getExceptionsForUserTemplate() {
		YFCDocument dOutput = YFCDocument.createDocument("InboxList");
		YFCElement eInbox = dOutput.getDocumentElement().createChild("Inbox");
		eInbox.setAttribute("ActiveFlag", "");
		eInbox.setAttribute("AssignedToUserId", "");
		eInbox.setAttribute("AutoResolvedFlag", "");
		eInbox.setAttribute("Description", "");
		eInbox.setAttribute("DetailDescription", "");
		eInbox.setAttribute("EnterpriseKey", "");
		eInbox.setAttribute("ExceptionType", "");
		eInbox.setAttribute("ExpirationDays", "");
		eInbox.setAttribute("InboxKey", "");
		eInbox.setAttribute("InboxType", "");
		eInbox.setAttribute("Priority", "");
		eInbox.setAttribute("QueueId", "");
		eInbox.setAttribute("QueueKey", "");
		eInbox.setAttribute("OrderLineKey", "");
		eInbox.setAttribute("OrderHeaderKey", "");
		eInbox.setAttribute("ShipnodeKey", "");
		eInbox.setAttribute("Status", "");
		eInbox.setAttribute("BillToID", "");
		
		return dOutput.getDocument();
	}
	
	private static Document getQueueListTemplate() {
		YFCDocument dOutput = YFCDocument.createDocument("QueueList");
		YFCElement eQueue = dOutput.getDocumentElement().createChild("Queue");
		eQueue.setAttribute("QueueKey", "");
		eQueue.setAttribute("QueueId", "");
		eQueue.setAttribute("QueueName", "");
		eQueue.setAttribute("QueueDescription", "");
		eQueue.setAttribute("Priority", "");
		eQueue.setAttribute("OwnerKey", "");
		eQueue.setAttribute("QueueGroup", "");
		return dOutput.getDocument();
	}
	
	private YFCDocument getExceptionsForUser(YFSEnvironment env, String sUserID) throws YIFClientCreationException, YFSException, RemoteException {
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("getExceptionListForUser",  getExceptionsForUserTemplate());
		
		YFCDocument dInput = YFCDocument.createDocument("getExceptionListForUser");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("UserId", sUserID);
		Document response = localApi.invoke(env, "getExceptionListForUser", dInput.getDocument());
		return YFCDocument.getDocumentFor(response);
	}

	private YFCDocument getQueueList(YFSEnvironment env, String sOrganizationCode, String sUserID) throws YIFClientCreationException, YFSException, RemoteException {
		YIFApi localApi = YIFClientFactory.getInstance().getLocalApi();
		env.setApiTemplate("getQueueList",  getQueueListTemplate());
		
		YFCDocument dInput = YFCDocument.createDocument("getQueueList");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("CallingOrganizationCode", sOrganizationCode);
		YFCElement eUser = eInput.createChild("QueueSubscriptionList").createChild("QueueSubscription").createChild("User");
		eUser.setAttribute("Loginid", sUserID);
		Document response = localApi.invoke(env, "getQueueList", dInput.getDocument());
		return YFCDocument.getDocumentFor(response);
	}
	
	@Override
	public Document invoke(YFSEnvironment env, Document input) throws Exception {
		YFCDocument dInput = YFCDocument.getDocumentFor(input);
		YFCElement eInput = dInput.getDocumentElement();
		if(!YFCCommon.isVoid(eInput.getAttribute("CallingOrganizationCode")) && !YFCCommon.isVoid(eInput.getAttribute("UserID"))) {
			YFCDocument dInboxList = this.getExceptionsForUser(env, eInput.getAttribute("UserID"));
			YFCDocument dQueueList = this.getQueueList(env, eInput.getAttribute("CallingOrganizationCode"), eInput.getAttribute("UserID"));
	
			if(!YFCCommon.isVoid(dQueueList)) {
				if(!YFCCommon.isVoid(dInboxList)) {
					HashMap<String, YFCElement> queueMap = new HashMap<String, YFCElement>();
					for(YFCElement eQueue : dQueueList.getDocumentElement().getChildren()) {
						queueMap.put(eQueue.getAttribute("QueueKey"), eQueue);
						eQueue.setAttribute("InboxCount", "0");
					}
					
					for(YFCElement eInbox : dInboxList.getDocumentElement().getChildren()) {
						YFCElement eQueue = queueMap.get(eInbox.getAttribute("QueueKey"));
						eQueue.getChildElement("InboxList", true).importNode(eInbox);
						eQueue.setAttribute("InboxCount", (eQueue.getIntAttribute("InboxCount", 0) + 1));
					}
				}
				return dQueueList.getDocument();
			}
		}		
		return YFCDocument.createDocument("QueueList").getDocument();
	}

}
