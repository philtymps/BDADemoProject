package com.ue;

import java.rmi.RemoteException;
import java.util.Map;

import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSGetOrderNoUE;
import com.yantra.yfs.japi.ue.YFSGetReceiptNoUE;

public class GetOrderNoUE implements YFSGetOrderNoUE, YFSGetReceiptNoUE {
	private static YIFApi api;
	private static YFCLogCategory logger = YFCLogCategory.instance(GetOrderNoUE.class);

	static {
		try {
			api = YIFClientFactory.getInstance().getApi();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is to generate an order number. It gets the last created
	 * order from getOrderList where the prefix is OMS, RET, TEMP, PO, TRAN,
	 * MAST, QUOT
	 * 
	 * @param env
	 * @return
	 * @throws Exception
	 */

	public String getOrderNo(YFSEnvironment env, @SuppressWarnings("rawtypes") Map inMap) {
		String strDocumentType = (String) (inMap.get("DocumentType"));
		String strHighestOrderNo = getNewOrderNo(env, strDocumentType, (String) (inMap.get("EnterpriseCode")));
		if (!YFCCommon.isVoid(strHighestOrderNo)){
			System.out.println("getOrderNo.newOrderNo: " + strHighestOrderNo);
			return strHighestOrderNo;
		}
		return null;
	}

	/* This gets the last order no for the defined order prefixes */
	private String getNewOrderNo(YFSEnvironment env, String sDocumentType, String sEnterpriseCode) {
		logger.verbose("getHighestOrderNo method");
		
		YFCElement eOrder = YFCDocument.createDocument("Order").getDocumentElement();
		eOrder.setAttribute("DocumentType", sDocumentType);
		eOrder.setAttribute("OrderNo", getDocumentTypePrefix(sDocumentType));
		eOrder.setAttribute("EnterpriseCode", sEnterpriseCode);
		eOrder.setAttribute("OrderNoQryType", "FLIKE");
		eOrder.setAttribute("MaximumRecords", "1");
		YFCElement eAttr = eOrder.createChild("OrderBy").createChild("Attribute");
		eAttr.setAttribute("Name", "OrderHeaderKey");
		eAttr.setAttribute("Desc", "Y");
		String strOrderNo =  getDocumentTypePrefix(sDocumentType) + "1000000";
		YFCElement eTemplate = YFCDocument.createDocument("OrderList").getDocumentElement();
		YFCElement eOTemplate = eTemplate.createChild("Order");
		eOTemplate.setAttribute("DocumentType", "");
		eOTemplate.setAttribute("OrderNo", "");
		eOTemplate.setAttribute("EnterpriseCode", "");
		eOTemplate.setAttribute("OrderHeaderKey", "");
		
		env.setApiTemplate("getOrderList", eTemplate.getOwnerDocument().getDocument());
		Document docOrderList;
		try {
			docOrderList = api.getOrderList(env, eOrder.getOwnerDocument().getDocument());
		} catch (YFSException e) {
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		env.clearApiTemplate("getOrderList");
		if (!YFCCommon.isVoid(docOrderList)){
			YFCElement docOrder = YFCDocument.getDocumentFor(docOrderList).getDocumentElement().getChildElement("Order");
			if (!YFCCommon.isVoid(docOrder) && !YFCCommon.isVoid(docOrder.getAttribute("OrderNo"))){
				String orderNo = docOrder.getAttribute("OrderNo");
				String number = orderNo.substring(orderNo.length() - 7, orderNo.length());
				int i = Integer.parseInt(number) + 1;
				strOrderNo = getDocumentTypePrefix(sDocumentType) + i;
			}
		}
		return strOrderNo;
	}

	private String getNewReceiptNo(YFSEnvironment env, String sDocumentType){
		YFCElement eInput = YFCDocument.createDocument("Receipt").getDocumentElement();
		eInput.setAttribute("DocumentType", sDocumentType);
		eInput.setAttribute("ReceiptNo", getDocumentTypePrefix(sDocumentType));
		eInput.setAttribute("ReceiptNoQryType", "FLIKE");
		YFCElement eAttr = eInput.createChild("OrderBy").createChild("Attribute");
		eAttr.setAttribute("Name", "ReceiptHeaderKey");
		eAttr.setAttribute("Desc", "Y");
		
		String sReceiptNo =  getDocumentTypePrefix(sDocumentType) + "1000000";
		YFCElement eTemplate = YFCDocument.createDocument("ReceiptList").getDocumentElement();
		YFCElement eOTemplate = eTemplate.createChild("Receipt");
		eOTemplate.setAttribute("DocumentType", "");
		eOTemplate.setAttribute("ReceiptNo", "");
		eOTemplate.setAttribute("ReceivingNode", "");
		eOTemplate.setAttribute("ReceiptHeaderKey", "");
		env.setApiTemplate("getReceiptList", eTemplate.getOwnerDocument().getDocument());
		Document dReceiptList;
		try {
			dReceiptList = api.getReceiptList(env, eInput.getOwnerDocument().getDocument());
		} catch (YFSException e) {
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		env.clearApiTemplate("getReceiptList");
		if (!YFCCommon.isVoid(dReceiptList)){
			YFCElement eReceipt = YFCDocument.getDocumentFor(dReceiptList).getDocumentElement().getChildElement("Receipt");
			if (!YFCCommon.isVoid(eReceipt) && !YFCCommon.isVoid(eReceipt.getAttribute("ReceiptNo"))){
				String receiptNo = eReceipt.getAttribute("ReceiptNo");
				String number = receiptNo.substring(receiptNo.length() - 7, receiptNo.length());
				int i = Integer.parseInt(number) + 1;
				sReceiptNo = getDocumentTypePrefix(sDocumentType) + i;
			}
		}
		return sReceiptNo;
	}
	
	private String getDocumentTypePrefix(String strDocumentType){
		if (strDocumentType.equals("0001")) {
			return "OM";
		} else if (strDocumentType.equals("0003")) {
			return "RET";
		} else if (strDocumentType.equals("0004")) {
			return "TP";
		} else if (strDocumentType.equals("0005")) {
			return "PO";
		} else if (strDocumentType.equals("0006")) {
			return "TR";
		} else if (strDocumentType.equals("0007")) {
			return "MS";
		} else if (strDocumentType.equals("0015")) {
			return "QT";
		}
		return "NA";
	}

	@Override
	public String getReceiptNo(YFSEnvironment env, Document input) throws YFSUserExitException {
		String strDocumentType = YFCDocument.getDocumentFor(input).getDocumentElement().getAttribute("DocumentType");
		String strHighestReceiptNo = getNewReceiptNo(env, strDocumentType);
		if (!YFCCommon.isVoid(strHighestReceiptNo)){
			System.out.println("getOrderNo.getReceiptNo: " + strHighestReceiptNo);
			return strHighestReceiptNo;
		}
		return null;
	}
}