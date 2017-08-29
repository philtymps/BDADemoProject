/*******************************************************************************
 * (C) Copyright 2010 Sterling Commerce, Inc.
 *******************************************************************************/

package com.ue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.omp.business.payment.YFSPaymentUtils;
import com.yantra.shared.dbi.YFS_Organization;
import com.yantra.shared.ycp.YCPFactory;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.log.YFCLogLevel;
import com.yantra.yfc.util.YFCDataBuf;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;
import com.yantra.yfs.japi.YFSExtnPaymentCollectionInputStruct;
import com.yantra.yfs.japi.YFSExtnPaymentCollectionOutputStruct;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSCollectionCreditCardUE;
import com.yantra.yfs.japi.ue.YFSCollectionDebitCardUE;

import com.yantra.yfs.japi.ue.YFSCollectionCustomerAccountUE;
import com.yantra.yfs.japi.ue.YFSCollectionOthersUE;
import com.yantra.yfs.japi.ue.YFSCollectionStoredValueCardUE;
import com.yantra.yfs.util.YFSRulesDefn;

/**
 * This class is a sample class to implement reversal of authorizations where
 * the settlement amount much match the authorization. It uses no internal
 * methods. This implementation looks for some branching logic keywords in
 * YFS_ORDER_HEADER.CUSTOMER_PO_NO. The number of days a date will be pushed
 * into the future is stored in paymentReference3; the default is 7 for normal
 * transactions, and 3 for reverseAuth related logic. If the scenario is
 * failure, hold reason will be read from paymentreference2. if the scenario is
 * HOLD_AND_SUSPEND, then optionally, paymentReference3 may instead include the
 * suspend flag value.
 * 
 * @author rspremulli
 * 
 */
public class CollectionSupportingReverseAuthorizationsv3 implements YFSCollectionDebitCardUE,YFSCollectionCreditCardUE,
		YFSCollectionCustomerAccountUE, YFSCollectionOthersUE, YFSCollectionStoredValueCardUE {

	private static final long MILLISECONDS_PER_HOUR = 1000L * 60L * 60L;
	private static final long MILLISECONDS_PER_DAY = 1000L * 60L * 60L * 24L;
	private boolean isAuth = true;
	private boolean isPositive = true;
	protected static final SimpleDateFormat XML_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");
	protected static final SimpleDateFormat AUTHEXPD_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	protected Calendar currentTime = null;
	protected Calendar futureTime = null;
	private boolean fututeTimeSet = false;
	private YIFApi localApi = null;
	private String logicTrigger = null;
	private int numDays = 7;

	protected YFSExtnPaymentCollectionOutputStruct doLogic(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in, String branchingLogicKey)
			throws YFSUserExitException {
		logger.beginTimer("doLogic");
		try {
			init(oEnv, in);
			// Ordered for performance - there's no special logic around
			// Refunds.
			try {
				paymentType = getPaymentTypeInformation(oEnv, in);
				if (!isAuth && !isPositive) { // Refund - no special logic
					return getOutputForSimpleScenario(in);
				}
				// no way this will get hit in 7.11 - executePaymentTransactions
				// scenario
	
				if (isVoid(in.chargeTransactionKey) || isVoid(in.orderHeaderKey) || isVoid(in.orderNo)) {
					logger.debug("Multiple charge transaction processing is not supported if the order information is not passed.");
					return getOutputForSimpleScenario(in);
				}

				if ("CUSTOMER_ACCOUNT".equals(paymentType.getGroup())) {
					logger.debug("Customer account, definately simple scenario.");
					return getOutputForSimpleScenario(in);
				}
				if (paymentType.isChargeInsteadOfAuth()) {
					logger.debug("Charge instead of authorize, definately simple scenario.");
					return getOutputForSimpleScenario(in);
				}
				if ("NO_REVERSE".equals(paymentType.getReversalStrategy())) {
					logger.debug("Payment type does not require reversal, definately simple scenario.");
					numDays = 3;
					return getOutputForSimpleScenario(in);
				}
				boolean complexCreditCardLogic = !paymentType.isCardTypeEnabled() && "CREDIT_CARD".equals(paymentType.getGroup())
						&& !("VISA".equalsIgnoreCase(in.creditCardType) || isVoid(in.creditCardType));
				if (paymentType.isReuseAuth()){
				    if(!complexCreditCardLogic) numDays = 3;
				    logger.debug("Auth mismatch allowed, don't reverse");
				    return getOutputForSimpleScenario(in);
				}
				if (complexCreditCardLogic) {
					logger.debug("Only VISA credit cards require reversal, definately simple scenario.");
					if(isVoid(in.creditCardType))
						logger.warn("CardType not passed, assuming VISA (for test logic purposes)");
					return getOutputForSimpleScenario(in);
				}
			} catch (YFSUserExitException e) {
				logger.error(e);
				throw new YFSUserExitException(e.getMessage());
			}
			logger.debug("We are in a complex scenario - changing num day default to 3");
			numDays = 3;
			getOrderAuthorizationDetails(oEnv, in);
			if (isAuth && !isPositive) { // Reverse Auth - only simple scenarios
				return getOutputForReverseAuth(oEnv, in);
			}
			if (isAuth && isPositive) { // Authorization
				return getOutputForAuthorization(oEnv, in);
			}
			if (!isAuth && isPositive) { // Charge
				return getOutputForSettlement(oEnv, in);
			}
			throw new YFSUserExitException("This error is impossible.");
		} finally {
			logger.endTimer("doLogic");
		}
	}

	protected YFSExtnPaymentCollectionOutputStruct initCommonOutputStruct(
			YFSExtnPaymentCollectionInputStruct in) {
		YFSExtnPaymentCollectionOutputStruct out = new YFSExtnPaymentCollectionOutputStruct();
		out.asynchRequestProcess = "ASYNC".equalsIgnoreCase(logicTrigger);
		out.retryFlag = "N";
		out.authorizationId = in.authorizationId;
		getDateAjustment(in);
		return out;
	}

	/**
	 * @param in
	 * @return
	 */
	protected Calendar getDateAjustment(YFSExtnPaymentCollectionInputStruct in) {
		if (!fututeTimeSet) {

			try {
				numDays = Integer.parseInt(in.paymentReference3);
			} catch (NumberFormatException e) {
				// numDays;
			}
			logger.verbose("All future dates will be " + numDays + " day(s) in the future.");
			futureTime.add(Calendar.DAY_OF_YEAR, numDays);
			fututeTimeSet = true;
		}
		return futureTime;
	}

	protected void setSuccesOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out) {
		setSuccesOutputStructFields(in, out, null, null, null);
	}

	protected void setSuccesOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out, String message, String successFlag,
			String successCode) {
		if (isVoid(in.authorizationId)) {
			//need this for internal scenarios.
			in.authorizationId = getId();
			out.authorizationId = in.authorizationId;
			out.authorizationExpirationDate = AUTHEXPD_FORMAT.format(futureTime.getTime());
		} else {
			out.authorizationId = in.authorizationId;
		}
		out.authorizationAmount = in.requestAmount;
		setNewInformationOutputStructFields(in, out);
		setCreditCardTranFields(in, out, true, message, successFlag, successCode);
	}

	protected void setCreditCardTranFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out, boolean success) {
		setCreditCardTranFields(in, out, success, null, null, null);

	}

	/**
	 * @param in
	 * @param out
	 * @param message
	 * @param successFlag
	 * @param successCode
	 */
	protected void setCreditCardTranFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out, boolean success, String message,
			String successFlag, String successCode) {
		logger.beginTimer("setCreditCardTranFields");
		try {
			if (isVoid(message))
				message = "All Success: " + success;
			if (isVoid(successFlag))
				successFlag = success ? "Y" : "N";
			if (isVoid(successCode))
				successCode = success ? "100" : ("20" + (numDays % 10));


			out.authAVS = successFlag;
			out.authCode = "VYASD/asd+49asdfj";
			out.authReturnCode = successCode;
			out.authReturnFlag = successFlag;
			out.authReturnMessage = "Logic Trigger for this transaction: " + logicTrigger;
			out.authTime = null;
			out.internalReturnCode = successCode;
			out.internalReturnFlag = successFlag;
			out.internalReturnMessage = message;
			out.requestID = out.authorizationId;
			out.sCVVAuthCode = "YYY";
			out.tranAmount = out.authorizationAmount;
			out.tranRequestTime = null;
			out.tranReturnCode = successCode;
			out.tranReturnFlag = successFlag;
			String chargeTypeDesc = in.chargeType;
			if ("AUTHORIZATION".equals(chargeTypeDesc)) {
				chargeTypeDesc = "AUTH";
				if (in.requestAmount < 0d)
					chargeTypeDesc = "REV_AUTH";
			}
			String whatHappened = chargeTypeDesc + " for " + in.requestAmount + " [" + successFlag
					+ ']';
			returnMessage = (isVoid(returnMessage) ? whatHappened
					: (returnMessage + ", " + whatHappened));
			out.tranReturnMessage = returnMessage;
			out.tranType = whatHappened;
		} finally {
			logger.endTimer("setCreditCardTranFields");
		}
	}

	protected void setRetryOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out) {
		out.retryFlag = "Y";
		if ("RETRY_LATER".equalsIgnoreCase(logicTrigger)) {
			out.collectionDate = futureTime.getTime();
		} else if ("RETRY".equalsIgnoreCase(logicTrigger)) {
			out.collectionDate = currentTime.getTime();
		}

	}

	protected void setFailOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out) {
		logger.beginTimer("setFailOutputStructFields");
		try {
			if (isVoid(in.authorizationId)) {
				out.authorizationId = getId();
			} else {
				out.authorizationId = in.authorizationId;
			}
			out.holdOrderAndRaiseEvent = true;
			if ("HOLD".equalsIgnoreCase(in.customerPONo))
				out.holdReason = in.paymentReference2;
			if ("HOLD_AND_SUSPEND".equalsIgnoreCase(in.customerPONo)) {
				out.holdReason = in.paymentReference2;
				if ("B".equalsIgnoreCase(in.paymentReference3))
					out.suspendPayment = in.paymentReference3;
				else
					out.suspendPayment = "Y";
			}
			setCreditCardTranFields(in, out, false);
			setNewInformationOutputStructFields(in, out);
		} finally {
			logger.endTimer("setFailOutputStructFields");
		}
	}

	protected void setAsyncOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out) {
		out = new YFSExtnPaymentCollectionOutputStruct();
		out.asynchRequestProcess = true;
	}

	protected void setNewInformationOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct out) {
		if ("STORED_VALUE_CARD".equals(paymentType.getGroup()) && isVoid(in.svcNo)) {
			//Commenting below or else fails with OMP11054
			//out.SvcNo = getId();
			//out.DisplaySvcNo = out.SvcNo.substring(out.SvcNo.length() - 4);

		} else if ("OTHER".equals(paymentType.getGroup()) && isVoid(in.paymentReference1)) {
			out.PaymentReference1 = getId();
			out.DisplayPaymentReference1 = out.PaymentReference1.substring(out.PaymentReference1.length() - 4);

		}
		// out.PaymentReference2 = null;
		// out.PaymentReference3 = String.valueOf(numDays);

	}

	/**
	 * @return
	 */
	protected String getId() {
		String id = String.valueOf(((long) Math.random()) * (9999999999999999L));
		if (id.length() < 4)
			id = id + "1234";
		return id;
	}

	protected PaymentType getPaymentTypeInformation(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in) throws YFSUserExitException {
		logger.beginTimer("getPaymentTypeInformation");
		try {
		    
			PaymentType paymentType2;
			    paymentType2 = invokeGetPaymentTypeList(oEnv, in);
			try {
			    if("CREDIT_CARD".equals(paymentType2.getGroup()) && !isVoid(in.creditCardType)){
					if(isCardTypeConfigLevelRuleEnabled(oEnv, in)){
						paymentType2 = invokeGetPaymentCardTypeDetails(oEnv, in);
						paymentType2.setGroup("CREDIT_CARD");
				    }else{
				    	setReuseableAuthFromRule(oEnv, in, paymentType2);
				    }
			    }
			} catch (Exception ex) {
			    paymentType2 = invokeGetPaymentTypeList(oEnv, in);
			    setReuseableAuthFromRule(oEnv, in, paymentType2);
			}
			return paymentType2;
		} finally {
			logger.endTimer("getPaymentTypeInformation");
		}
	}

	private boolean isCardTypeConfigLevelRuleEnabled(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in){
    	Map<String, String> mapBufParams = new HashMap<String, String>();
    	YFCDataBuf ruleMap = new YFCDataBuf();
		YFS_Organization oOrg = YCPFactory.getInstance().getOrganization((YFSContext)oEnv, in.enterpriseCode);
		mapBufParams.put("EnterpriseCode", oOrg.getPrimary_Enterprise_Key());
		String docType = in.documentType;
		mapBufParams.put("DocumentType", isVoid(docType) ? "0001"  :docType);
		ruleMap.append(mapBufParams);
		String ruleVal = YFSPaymentUtils.getPaymentRuleValue((YFSContext)oEnv, ruleMap, YFSRulesDefn.RSFN_USE_PMNT_CARD_TYPE_CONF_LEVEL );
		if("Y".equals(ruleVal))
			return true;
		return false;
	}
	/**
	 * @author Robert Spremulli (rspremul@us.ibm.com)
	 * @param oEnv
	 * @param in
	 * @param paymentType2
	 * @throws YFSUserExitException
	 */
	private void setReuseableAuthFromRule(YFSEnvironment oEnv, YFSExtnPaymentCollectionInputStruct in,
		PaymentType paymentType2) throws YFSUserExitException {
	    YFCDocument getMultipleAuthAllowed = YFCDocument.getDocumentFor(invokeApi("getRuleDetails", oEnv, getMultipleAuthRuleInDoc(in)));
	    paymentType2.setReuseAuth(getMultipleAuthAllowed.getDocumentElement().getBooleanAttribute("RuleSetValue",false));
	}

	/**
	 * @author Robert Spremulli (rspremul@us.ibm.com)
	 * @param in
	 * @return
	 */
	private Document getMultipleAuthRuleInDoc(YFSExtnPaymentCollectionInputStruct in) {
	    YFCDocument iDoc = YFCDocument.createDocument("Rules");
	    iDoc.getDocumentElement().setAttribute("CallingOrganizationCode", in.paymentConfigOrganizationCode);
	    iDoc.getDocumentElement().setAttribute("DocumentType", in.documentType);
	    iDoc.getDocumentElement().setAttribute("RuleSetFieldName", "IS_MULTIPLE_REQ_ALLOWED");
	    return iDoc.getDocument();
	}

	/**
	 * @author Robert Spremulli (rspremul@us.ibm.com)
	 * @param oEnv
	 * @param in
	 * @throws YFSUserExitException
	 */
	private PaymentType invokeGetPaymentCardTypeDetails(YFSEnvironment oEnv, YFSExtnPaymentCollectionInputStruct in)
		throws YFSUserExitException {
	    PaymentType paymentType2;
	    Document inDoc = getInputDocumentForPaymentCardTypeDetails(in, oEnv);
	    if(getPaymentCardTypeDetailsTemplate() != null)
	    oEnv.setApiTemplate("getPaymentCardTypeDetails", getPaymentCardTypeDetailsTemplate());
	    paymentType2 = new PaymentType(invokeApi("getPaymentCardTypeDetails", oEnv, inDoc) ,true);
	    oEnv.clearApiTemplate("getPaymentCardTypeDetails");
	    return paymentType2;
	}

	/**
	 * @author Robert Spremulli (rspremul@us.ibm.com)
	 * @param oEnv
	 * @param in
	 * @return
	 * @throws YFSUserExitException
	 */
	private PaymentType invokeGetPaymentTypeList(YFSEnvironment oEnv, YFSExtnPaymentCollectionInputStruct in)
		throws YFSUserExitException {
	    PaymentType paymentType2;
	    Document inDoc = getInputDocumentForPaymentTypeList(in, oEnv);
	    oEnv.setApiTemplate("getPaymentTypeList", getPaymentTypeListTemplate());
	    paymentType2 = new PaymentType(invokeApi("getPaymentTypeList", oEnv, inDoc));
	    oEnv.clearApiTemplate("getPaymentTypeList");
	    return paymentType2;
	}

	protected Document getPaymentTypeListTemplate() {
		YFCDocument tDoc = YFCDocument.createDocument("PaymentTypeList");
		YFCElement childElement = tDoc.getDocumentElement().getChildElement("PaymentType", true);
		childElement.setAttribute("ChargeInsteadOfAuth", "");
		childElement.setAttribute("PaymentTypeGroup", "");
		childElement.setAttribute("AuthorizationReversalStrategy", "");
		return tDoc.getDocument();
	}

	protected Document getPaymentCardTypeDetailsTemplate() {
		return null;
	}
	protected Document getInputDocumentForPaymentTypeList(YFSExtnPaymentCollectionInputStruct in,
			YFSEnvironment oEnv) throws YFSUserExitException {
		YFCDocument inDoc = YFCDocument.createDocument("PaymentType");
		YFCElement inDocElem = inDoc.getDocumentElement();
		inDocElem.setAttribute("CallingOrganizationCode", getPaymentOrg(in, oEnv));
		inDocElem.setAttribute("PaymentType", in.paymentType);
		return inDoc.getDocument();
	}

	protected Document getInputDocumentForPaymentCardTypeDetails(YFSExtnPaymentCollectionInputStruct in,
			YFSEnvironment oEnv) throws YFSUserExitException {
		YFCDocument inDoc = YFCDocument.createDocument("PaymentCardType");
		YFCElement inDocElem = inDoc.getDocumentElement();
		inDocElem.setAttribute("OrganizationCode", getPaymentOrg(in, oEnv));
		inDocElem.setAttribute("PaymentType", in.paymentType);
		inDocElem.setAttribute("PaymentCardType", in.creditCardType);
		inDocElem.setAttribute("DocumentType", in.documentType);
		inDocElem.setAttribute("InheritValues", "Y");
		return inDoc.getDocument();
	}
	/**
	 * @param in
	 * @param oEnv
	 * @throws YFSUserExitException
	 */
	protected String getPaymentOrg(YFSExtnPaymentCollectionInputStruct in, YFSEnvironment oEnv)
			throws YFSUserExitException {
		return in.paymentConfigOrganizationCode;
	}

	protected YIFApi getLocalApi() throws YIFClientCreationException {
		if (localApi == null) {
			localApi = YIFClientFactory.getInstance().getLocalApi();
		}
		return localApi;
	}

	protected Document invokeApi(final String apiName, YFSEnvironment oEnv, Document inDoc)
			throws YFSUserExitException {
		logger.beginTimer("invokeApi - " + apiName);
		try {
			return getLocalApi().invoke(oEnv, apiName, inDoc);
		} catch (RemoteException e) {
			logger.error(e);
			throw new YFSUserExitException(e.getMessage());
		} catch (YFSException e) {
			logger.error(e);
			throw new YFSUserExitException(e.getMessage());
		} catch (YIFClientCreationException e) {
			logger.error(e);
			throw new YFSUserExitException(e.getMessage());

		} finally {
			logger.endTimer("invokeApi - " + apiName);
		}
	}

	protected void getOrderAuthorizationDetails(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in) throws YFSUserExitException {
		logger.beginTimer("getOrderAuthorizationDetails");
		try {
			oEnv.setApiTemplate("getOrderDetails", getAuthorizationDetailsTemplate());
			YFCDocument ohkDoc = YFCDocument.createDocument("Order");
			ohkDoc.getDocumentElement().setAttribute("OrderHeaderKey", in.orderHeaderKey);
			orderDetailsDoc = YFCDocument.getDocumentFor(invokeApi("getOrderDetails", oEnv,
					ohkDoc.getDocument()));
			oEnv.clearApiTemplate("getOrderDetails");
		} finally {
			logger.endTimer("getOrderAuthorizationDetails");
		}
	}

	protected Document getAuthorizationDetailsTemplate() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<Order AuthorizationExpirationDate=\"\" OrderHeaderKey=\"\" >");
		// buffer.append("<PaymentMethods> ");
		// buffer.append("<PaymentMethod AwaitingAuthInterfaceAmount=\"\" AwaitingChargeInterfaceAmount=\"\" BillToKey=\"\" ChargeSequence=\"\" CheckNo=\"\" CheckReference=\"\" CreditCardExpDate=\"\" CreditCardName=\"\" CreditCardNo=\"\" CreditCardType=\"\" CustomerAccountNo=\"\" CustomerPONo=\"\" DisplayCreditCardNo=\"\" DisplayCustomerAccountNo=\"\" DisplayPaymentReference1=\"\" DisplaySvcNo=\"\" FundsAvailable=\"\" GetFundsAvailableUserExitInvoked=\"\" IncompletePaymentType=\"\" MaxChargeLimit=\"\" PaymentKey=\"\" PaymentReference1=\"\" PaymentReference2=\"\" PaymentReference3=\"\" PaymentType=\"\" RequestedAuthAmount=\"\" RequestedChargeAmount=\"\" SuspendAnyMoreCharges=\"\" SvcNo=\"\" TotalAuthorized=\"\" TotalCharged=\"\" TotalRefundedAmount=\"\" UnlimitedCharges=\"\">");
		// buffer.append(" </PaymentMethod> ");
		// buffer.append("</PaymentMethods> ");
		buffer.append("<ChargeTransactionDetails TotalCredits=\"\" TotalDebits=\"\" TotalOpenAuthorizations=\"\" TotalOpenBookings=\"\" TotalTransferredIn=\"\" TotalTransferredOut=\"\"> ");
		buffer.append("<ChargeTransactionDetail AuditTransactionID=\"\" AuthorizationExpirationDate=\"\" AuthorizationID=\"\" BookAmount=\"\" ChargeTransactionKey=\"\" ChargeType=\"\" CollectionDate=\"\" CreditAmount=\"\" DebitAmount=\"\" DistributedAmount=\"\" ExecutionDate=\"\" HoldAgainstBook=\"\" OpenAuthorizedAmount=\"\" OrderHeaderKey=\"\" OrderInvoiceKey=\"\" PaymentKey=\"\" RequestAmount=\"\" SettledAmount=\"\" Status=\"\" StatusReason=\"\" TransactionDate=\"\" TransferFromOhKey=\"\" TransferToOhKey=\"\" UserExitStatus=\"\"> ");
		buffer.append("<CreditCardTransactions> ");
		buffer.append("<CreditCardTransaction AuthAmount=\"\" AuthAvs=\"\" AuthCode=\"\" AuthReturnCode=\"\" AuthReturnFlag=\"\" AuthReturnMessage=\"\" AuthTime=\"\" CVVAuthCode=\"\" ChargeTransactionKey=\"\" CreditCardTransactionKey=\"\" InternalReturnCode=\"\" InternalReturnFlag=\"\" InternalReturnMessage=\"\" ParentKey=\"\" Reference1=\"\" Reference2=\"\" RequestId=\"\" TranAmount=\"\" TranRequestTime=\"\" TranReturnCode=\"\" TranReturnFlag=\"\" TranReturnMessage=\"\" TranType=\"\"/> ");
		buffer.append("</CreditCardTransactions>");
		// buffer.append(" <PaymentMethod AwaitingAuthInterfaceAmount=\"\" AwaitingChargeInterfaceAmount=\"\" ChargeSequence=\"\" CheckNo=\"\" CheckReference=\"\" CreditCardExpDate=\"\" CreditCardName=\"\" CreditCardNo=\"\" CreditCardType=\"\" CustomerAccountNo=\"\" CustomerPONo=\"\" DisplayCreditCardNo=\"\" DisplayPaymentReference1=\"\" DisplaySvcNo=\"\" IncompletePaymentType=\"\" MaxChargeLimit=\"\" PaymentKey=\"\" PaymentReference1=\"\" PaymentReference2=\"\" PaymentReference3=\"\" PaymentType=\"\" RequestedAuthAmount=\"\" RequestedChargeAmount=\"\" SuspendAnyMoreCharges=\"\" SvcNo=\"\" TotalAltRefundedAmount=\"\" TotalAuthorized=\"\" TotalCharged=\"\" TotalRefundedAmount=\"\" UnlimitedCharges=\"\"/> ");
		buffer.append("</ChargeTransactionDetail> ");
		buffer.append("</ChargeTransactionDetails>");
		buffer.append("</Order>");
		try {
			return YFCDocument.parse(buffer.toString()).getDocument();
		} catch (SAXException e) {
			logger.error("Impossible error has occurred");
			logger.error(e);
		} catch (IOException e) {
			logger.error("Impossible error has occurred");
			logger.error(e);
		}
		return null;
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForSimpleScenario(
			YFSExtnPaymentCollectionInputStruct in) throws YFSUserExitException {
		logger.beginTimer("getOutputForSimpleScenario");
		try {

			YFSExtnPaymentCollectionOutputStruct outStruct = initCommonOutputStruct(in);
			if ("RETRY_ASAP".equalsIgnoreCase(logicTrigger)) {
				setRetryOutputStructFields(in, outStruct);
			} else if ("ASYNC".equalsIgnoreCase(logicTrigger)) {
				setAsyncOutputStructFields(in, outStruct);
			} else if ("FRAUD_DETECTED".equalsIgnoreCase(logicTrigger)) {
				setFailOutputStructFields(in, outStruct);
			} else if ("HOLD".equalsIgnoreCase(logicTrigger)) {
				setFailOutputStructFields(in, outStruct);
			} else if ("HOLD_AND_SUSPEND".equalsIgnoreCase(logicTrigger)) {
				setFailOutputStructFields(in, outStruct);
			} else if (isAuth && !isPositive && !"CUSTOMER_ACCOUNT".equals(paymentType.getGroup())) {
				// simple reverse auth for customer accounts
				setSimulatedSuccessOutputStructFields(in, outStruct);
			} else {
				setSuccesOutputStructFields(in, outStruct);
			}
			return outStruct;
		} finally {
			logger.endTimer("getOutputForSimpleScenario");
		}
	}

	protected YFCDocument formRecordExternalChargesInDoc(
			YFSExtnPaymentCollectionInputStruct authInput,
			YFSExtnPaymentCollectionOutputStruct authOutput, boolean isMarkAsExpired)
			throws YFSUserExitException {
		YFCElement parentElem = null;
		if (recordExternalChargesInDoc == null) {
			recordExternalChargesInDoc = YFCDocument.createDocument("RecordExternalCharges");
			YFCElement recElem = recordExternalChargesInDoc.getDocumentElement();
			// recElem.setAttribute("OrderHeaderKey", authInput.orderHeaderKey);
			recElem.setAttribute("AuditTransactionId", "PAYMENT_EXECUTION");
			recElem.setAttribute("ModificationReasonCode",
					(isMarkAsExpired ? "Additional information" : "Charge Failed"));
			YFCElement payMthdElem = recElem.getChildElement("PaymentMethod", true);
			parentElem = payMthdElem;
		} else {
			YFCElement payMthdElem = recordExternalChargesInDoc.getDocumentElement().getChildElement(
					"PaymentMethod");
			if (payMthdElem == null) {
				recordExternalChargesInDoc = null;
				logger.warn("This shouldn't be happening - losing old info, recreating the document");
				return formRecordExternalChargesInDoc(authInput, authOutput, isMarkAsExpired);
			}
			YFCElement listElem = payMthdElem.getChildElement("PaymentDetailsList");
			if (listElem == null) {
				listElem = payMthdElem.getChildElement("PaymentDetailsList", true);
				YFCElement childElement = payMthdElem.getChildElement("PaymentDetails");
				payMthdElem.removeChild(childElement);
				listElem.appendChild(childElement);
			}
			parentElem = listElem;
		}
		YFCElement payDtlsElem = parentElem.createChild("PaymentDetails");
		payDtlsElem.setAttribute("AuthAvs", authOutput.authAVS);
		payDtlsElem.setAttribute("AuthCode", authOutput.authCode);
		try {
			if (authOutput.authorizationExpirationDate != null)
				payDtlsElem.setAttribute(
						"AuthorizationExpirationDate",
						XML_FORMAT.format(AUTHEXPD_FORMAT.parse(authOutput.authorizationExpirationDate)));
		} catch (ParseException e) {
			logger.error("Impossible error - we couldn't parse a format we just formatted");
			logger.error(e);
			throw new YFSUserExitException(e.getMessage());
		}
		payDtlsElem.setAttribute("AuthorizationID", authOutput.authorizationId);
		payDtlsElem.setAttribute("AuthReturnCode", authOutput.authReturnCode);
		payDtlsElem.setAttribute("AuthReturnFlag", authOutput.authReturnFlag);
		payDtlsElem.setAttribute("AuthReturnMessage", authOutput.authReturnMessage);
		payDtlsElem.setAttribute("AuthTime", authOutput.authTime);
		payDtlsElem.setAttribute("ChargeType", authInput.chargeType);
		if (authOutput.collectionDate != null)
			payDtlsElem.setAttribute("CollectionDate", XML_FORMAT.format(authOutput.collectionDate));
		payDtlsElem.setAttribute("CVVAuthCode", authOutput.sCVVAuthCode);
		payDtlsElem.setAttribute("InternalReturnCode", authOutput.internalReturnCode);
		payDtlsElem.setAttribute("InternalReturnFlag", authOutput.internalReturnFlag);
		payDtlsElem.setAttribute("InternalReturnMessage", authOutput.internalReturnMessage);
		payDtlsElem.setAttribute("ProcessedAmount", authOutput.authorizationAmount);
		payDtlsElem.setAttribute("RequestAmount", authInput.requestAmount);
		payDtlsElem.setAttribute("RequestId", authOutput.requestID);
		payDtlsElem.setAttribute("TranRequestTime", authOutput.tranRequestTime);
		payDtlsElem.setAttribute("TranReturnCode", authOutput.tranReturnCode);
		payDtlsElem.setAttribute("TranReturnFlag", authOutput.tranReturnFlag);
		payDtlsElem.setAttribute("TranReturnMessage", authOutput.tranReturnMessage);
		payDtlsElem.setAttribute("TranType", authOutput.tranType);
		payDtlsElem.setAttribute("RequestProcessed", "Y");
		payDtlsElem.setAttribute("HoldAgainstBook", "N");
		if (logger.isEnabledFor(YFCLogLevel.VERBOSE)) {
			logger.verbose(recordExternalChargesInDoc.toString());
		}
		return recordExternalChargesInDoc;
	}

	/**
	 * @param in
	 * @param outStruct
	 */
	protected void setSimulatedSuccessOutputStructFields(YFSExtnPaymentCollectionInputStruct in,
			YFSExtnPaymentCollectionOutputStruct outStruct) {
		setSuccesOutputStructFields(in, outStruct, "Simulated Reverse Authorization", "Y", "110");
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForSettlement(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in) throws YFSUserExitException {
		logger.beginTimer("getOutputForSettlement");
		try {
			// if ("FAIL_CHARGE".equalsIgnoreCase(logicTrigger)) {
			// logger.debug("Simulating Failed Charge.");
			// YFSExtnPaymentCollectionOutputStruct outStruct =
			// initCommonOutputStruct(in);
			// setFailOutputStructFields(in, outStruct);
			// return outStruct;
			// }
			YFCElement ctdsElem = orderDetailsDoc.getDocumentElement().getChildElement(
					"ChargeTransactionDetails");
			if (isVoid(in.authorizationId)) {
				logger.debug("No authorization id in the request - this is a settlement with no auth.");
				return getOutputForBlindSettlement(oEnv, in, false);
			}
			double positiveAuthAmount = 0.0d;
			double reverseAuthAmount = 0.0d;
			boolean isMarkedAsExpired = true;
			for (Iterator itr = ctdsElem.getChildren(); itr.hasNext();) {
				YFCElement ctdElem = (YFCElement) itr.next();
				if (!in.authorizationId.equals(ctdElem.getAttribute("AuthorizationID"))
						|| !("AUTHORIZATION".equals(ctdElem.getAttribute("ChargeType")) || "AUTHORIZATION".equals(ctdElem.getAttribute("ChargeType")))
						|| in.chargeTransactionKey.equals(ctdElem.getAttribute("ChargeTransactionKey")))
					continue;
				Date exprDate = ctdElem.getDateAttribute("AuthorizationExpirationDate");
				Date realExprDate = getRealExprDate(exprDate, in.creditCardType);
				if (exprDate.after(currentTime.getTime())) {
					isMarkedAsExpired = false;
					double openAuthorizedAmount = ctdElem.getDoubleAttribute(
							"OpenAuthorizedAmount", 0d);
					if ("OPEN".equals(ctdElem.getAttribute("Status")))
						openAuthorizedAmount = ctdElem.getDoubleAttribute("RequestAmount", 0d);
					if (openAuthorizedAmount < 0d) {
						reverseAuthAmount += openAuthorizedAmount;
					} else {
						positiveAuthAmount += openAuthorizedAmount;
					}
				} else if (realExprDate.after(currentTime.getTime())) {
					logger.debug("'expired' authorization - past reversal window.");
					double openAuthorizedAmount = ctdElem.getDoubleAttribute(
							"OpenAuthorizedAmount", 0d);
					if ("OPEN".equals(ctdElem.getAttribute("Status")))
						openAuthorizedAmount = ctdElem.getDoubleAttribute("RequestAmount", 0d);
					if (openAuthorizedAmount < 0d) {
						reverseAuthAmount += openAuthorizedAmount;
					} else {
						positiveAuthAmount += openAuthorizedAmount;
					}
				} else {
					continue;
				}
			}
			logger.verbose("amounts!  pos: " + positiveAuthAmount + ", neg: " + reverseAuthAmount);
			if (Math.abs(positiveAuthAmount - in.requestAmount) < 0.0001d) {
				logger.debug("Handling Exact Charge Amount scenario.");
				return getOutputForExactSettlement(oEnv, in);
			} else if (positiveAuthAmount == 0d) {
				logger.debug("Handling Blind Charge Amount scenario - there was an authId, but the auth is expired.");
				return getOutputForBlindSettlement(oEnv, in, false);
			} else {
				logger.debug("Handling Mismatched Charge Amount scenario.");
				return getOutputForMismatchedSettlement(oEnv, in, positiveAuthAmount,
						reverseAuthAmount, true);
			}

		} finally {
			logger.endTimer("getOutputForSettlement");
		}
	}

	protected Date getRealExprDate(Date exprDateStored, String cardType) {
		long beginningOfAuthReversalPeriodAdjustment = (MILLISECONDS_PER_DAY * 4L)
				- (MILLISECONDS_PER_HOUR * 4L);
		Date date = new Date(exprDateStored.getTime() + beginningOfAuthReversalPeriodAdjustment);
		return date;
	}

	protected static final int REVERSE_AUTH_FOR_AUTH = 1;
	protected static final int REVERSE_AUTH_FOR_CHARGE = 1 << 2;
	protected static final int AUTH_FOR_CHARGE = 1 << 3;
	protected static final int CHARGE_FROM_AUTH = 1 << 4;

	private YFSExtnPaymentCollectionInputStruct cloneInputStruct(
			YFSExtnPaymentCollectionInputStruct originalInput, int conversionType,
			double previousAuthAmount) {
		YFSExtnPaymentCollectionOutputStruct tmpOut = new YFSExtnPaymentCollectionOutputStruct();
		tmpOut.authorizationAmount = previousAuthAmount;
		return cloneInputStruct(originalInput, conversionType, tmpOut);
	}

	/**
	 * @param originalInput
	 * @return
	 */
	protected YFSExtnPaymentCollectionInputStruct cloneInputStruct(
			YFSExtnPaymentCollectionInputStruct originalInput, int conversionType,
			YFSExtnPaymentCollectionOutputStruct previousResult) {
		YFSExtnPaymentCollectionInputStruct cloneStruct = new YFSExtnPaymentCollectionInputStruct();
		if ((conversionType == REVERSE_AUTH_FOR_CHARGE || conversionType == CHARGE_FROM_AUTH)
				&& previousResult != null) {
			cloneStruct.requestAmount = previousResult.authorizationAmount;
		} else if ((conversionType == AUTH_FOR_CHARGE)) {
			cloneStruct.requestAmount = originalInput.requestAmount;
		} else {
			cloneStruct.requestAmount = originalInput.requestAmount;
		}
		if (previousResult != null && (conversionType == CHARGE_FROM_AUTH))
			// cloneStruct.authorizationId = previousResult.authorizationId;
			cloneStruct.authorizationId = originalInput.authorizationId;
		else if (!(conversionType == AUTH_FOR_CHARGE))
			cloneStruct.authorizationId = originalInput.authorizationId;
		else {
			// blank authId - code will generate one.
		}
		if (!(conversionType == CHARGE_FROM_AUTH))
			cloneStruct.chargeType = "AUTHORIZATION";
		else
			cloneStruct.chargeType = "CHARGE";
		// straight copy
		cloneStruct.chargeTransactionKey = originalInput.chargeTransactionKey;
		cloneStruct.creditCardType = originalInput.creditCardType;
		cloneStruct.customerPONo = originalInput.customerPONo;
		cloneStruct.documentType = originalInput.documentType;
		cloneStruct.enterpriseCode = originalInput.enterpriseCode;
		cloneStruct.orderHeaderKey = originalInput.orderHeaderKey;
		cloneStruct.orderNo = originalInput.orderNo;
		cloneStruct.paymentConfigOrganizationCode = originalInput.paymentConfigOrganizationCode;
		cloneStruct.paymentReference2 = originalInput.paymentReference2;
		cloneStruct.paymentReference3 = originalInput.paymentReference3;
		cloneStruct.paymentType = originalInput.paymentType;
		cloneStruct.svcNo = originalInput.svcNo;
		cloneStruct.paymentReference1 = originalInput.paymentReference1;
		cloneStruct.creditCardNo = originalInput.creditCardNo;
		if (isVerboseLoggingEnabled()) {
			printInfoBar();
			logger.verbose("Derived input for "
					+ ((conversionType == REVERSE_AUTH_FOR_AUTH) ? "Reverse Authorization from an Authorization"
							: (conversionType == REVERSE_AUTH_FOR_CHARGE) ? "Reverse Authorization from an Authorization"
									: (conversionType == AUTH_FOR_CHARGE) ? "Authorization from a charge"
											: "Charge from an auth") + " is:\n");
			printPublicFields(cloneStruct);
			printInfoBar();
		}
		return cloneStruct;
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForBlindSettlement(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in, boolean isMarkAsExpired) {
		logger.beginTimer("getOutputForBlindSettlement");
		try {
			YFSExtnPaymentCollectionInputStruct authStruct = cloneInputStruct(in, AUTH_FOR_CHARGE,
					null);
			YFSExtnPaymentCollectionOutputStruct authOutput;
			try {
				authOutput = getOutputForAuthorization(oEnv, authStruct);
				if (isVerboseLoggingEnabled()) {
					printInfoBar();
					logger.verbose("Auth output is:");
					printPublicFields(authOutput);
					printInfoBar();
				}
				if ("FAIL_CHARGE".equalsIgnoreCase(logicTrigger)) {
					formRecordExternalChargesInDoc(authStruct, authOutput, false);
				} else if(isMarkAsExpired){
					formRecordExternalChargesInDoc(authStruct, authOutput, isMarkAsExpired);
				}
				if (authOutput.authReturnFlag.equals("N")) {
//					authOutput.authorizationId = in.authorizationId;
					return authOutput;
				} else {
					YFSExtnPaymentCollectionInputStruct chargeStruct = cloneInputStruct(authStruct,
							CHARGE_FROM_AUTH, authOutput);
					
					chargeStruct.authorizationId = in.authorizationId;
					if (isMarkAsExpired)
						chargeStruct.authorizationId = authOutput.authorizationId;
					 
					return getOutputForExactSettlement(oEnv, chargeStruct);
				}
			} catch (YFSUserExitException e) {
				logger.debug("Auth for charge failed.  Returning failure with charge information.");
				YFSExtnPaymentCollectionOutputStruct outStruct = initCommonOutputStruct(in);
				setFailOutputStructFields(in, outStruct);
				return outStruct;
			}

		} finally {
			logger.endTimer("getOutputForBlindSettlement");
		}
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForExactSettlement(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in) {
		logger.beginTimer("getOutputForExactSettlement");
		try {
			YFSExtnPaymentCollectionOutputStruct outStruct = initCommonOutputStruct(in);
			if ("FAIL_CHARGE".equalsIgnoreCase(logicTrigger)) {
				logger.debug("Simulating Failed Charge.");
				setFailOutputStructFields(in, outStruct);
			} else {
				setSuccesOutputStructFields(in, outStruct, null, null, null);
			}
			return outStruct;

		} finally {
			logger.endTimer("getOutputForExactSettlement");
		}
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForMismatchedSettlement(
			YFSEnvironment oEnv, YFSExtnPaymentCollectionInputStruct in, double authAmount,
			double reverseAuthAmount, boolean isMarkedAsExpired) throws YFSUserExitException {
		logger.beginTimer("getOutputForMismatchedSettlement");
		try {
			logger.debug("Reversing old authorization");
			double reversalNeededAmount = (reverseAuthAmount == 0d) ? -authAmount
					: (-(authAmount + reverseAuthAmount));
			if (reversalNeededAmount < 0.0001d) {
				YFSExtnPaymentCollectionInputStruct reversalInStruct = cloneInputStruct(in,
						REVERSE_AUTH_FOR_CHARGE, reversalNeededAmount);
				YFSExtnPaymentCollectionOutputStruct outputForReverseAuth = getOutputForReverseAuth(
						oEnv, reversalInStruct);
				if (isMarkedAsExpired) {
					
					 formRecordExternalChargesInDoc(reversalInStruct,
					 outputForReverseAuth, isMarkedAsExpired);
					 

				}
				if (isVerboseLoggingEnabled()) {
					printInfoBar();
					logger.verbose("Reverse Auth output is: \n");
					printPublicFields(outputForReverseAuth);
					printInfoBar();
				}
			}
			YFSExtnPaymentCollectionOutputStruct outStruct = getOutputForBlindSettlement(oEnv, in,
					isMarkedAsExpired);
			if ("FAIL_CHARGE".equalsIgnoreCase(logicTrigger) || recordExternalChargesInDoc != null) {
				outStruct.recordAdditionalTransactions = recordExternalChargesInDoc.getDocument();
			}
			return outStruct;
			// } catch (YFSUserExitException e) {
			// logger.error(e);
			// throw new YFSUserExitException(e.getMessage());
		} finally {
			logger.endTimer("getOutputForMismatchedSettlement");
		}
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForAuthorization(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in) throws YFSUserExitException {
		logger.beginTimer("getOutputForAuthorization");
		try {
			YFSExtnPaymentCollectionOutputStruct outStruct = initCommonOutputStruct(in);
			if ("FAIL_AUTH".equalsIgnoreCase(logicTrigger)) {
				logger.debug("Simulating Failed Auth.");
				setFailOutputStructFields(in, outStruct);
			} else {
				setSuccesOutputStructFields(in, outStruct, null, null, null);
			}
			return outStruct;

		} finally {
			logger.endTimer("getOutputForAuthorization");
		}
	}

	protected YFSExtnPaymentCollectionOutputStruct getOutputForReverseAuth(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in) throws YFSUserExitException {
		logger.beginTimer("getOutputForReverseAuth");
		try {
			// If we reached this point, then we are definitely reversing an
			// authorization.
			YFSExtnPaymentCollectionOutputStruct outStruct = initCommonOutputStruct(in);
			if ("FAIL_REVERSE".equalsIgnoreCase(logicTrigger)) {
				logger.debug("Simulating Failed Reverse Auth.");

				setFailOutputStructFields(in, outStruct);
			} else if ("REVERSE_WINDOW_PASSED".equalsIgnoreCase(logicTrigger)) {
				logger.debug("Simulating Reversal window passed.");
				setSimulatedSuccessOutputStructFields(in, outStruct);
			} else {
				setSuccesOutputStructFields(in, outStruct, null, null, null);
			}
			YFCElement ctdsElem = orderDetailsDoc.getDocumentElement().getChildElement("ChargeTransactionDetails");
			for (Iterator itr = ctdsElem.getChildren(); itr.hasNext();) {
				YFCElement ctdElem = (YFCElement) itr.next();
				if (in.authorizationId.equals(ctdElem.getAttribute("AuthorizationID"))){
					try {
						outStruct.authorizationExpirationDate = AUTHEXPD_FORMAT.format(XML_FORMAT.parse(ctdElem.getAttribute("AuthorizationExpirationDate")));
					} catch (ParseException e) {
						logger.error(e);
						throw new YFSUserExitException(e.getMessage());
					}
				}
			}
			return outStruct;

		} finally {
			logger.endTimer("getOutputForReverseAuth");
		}
	}

	protected void init(YFSEnvironment oEnv, YFSExtnPaymentCollectionInputStruct in) {
		isAuth = "AUTHORIZATION".equals(in.chargeType);
		isPositive = in.requestAmount > 0d;
		currentTime = Calendar.getInstance();
		futureTime = Calendar.getInstance();
		fututeTimeSet = false;
		logicTrigger = in.customerPONo;
		returnMessage = null;
	}

	protected static String[] knownTriggers = new String[] {
	// Basic scenarios
			"RETRY_ASAP", "RETRY_LATER", "FRAUD_DETECTED", "ASYNC", "HOLD", "HOLD_AND_SUSPEND",
			// Specific scenarios
			"FAIL_REVERSE", "REVERSE_WINDOW_PASSED", "FAIL_AUTH", "FAIL_CHARGE" };

	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */
	/* ******************************************************************************************** */

	// process flow methods

	public YFSExtnPaymentCollectionOutputStruct collectionCreditCard(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct inStruct) throws YFSUserExitException {
		logger.beginTimer("collectionCreditCard - Reverse Auth implementation");
		try {
			return ueImpl(oEnv, inStruct, inStruct.customerPONo);

		} finally {
			logger.endTimer("collectionCreditCard - Reverse Auth implementation");
		}
	}

	public YFSExtnPaymentCollectionOutputStruct collectionDebitCard(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct inStruct) throws YFSUserExitException {
		logger.beginTimer("collectionDebitCard - Reverse Auth implementation");
		try {
			return ueImpl(oEnv, inStruct, inStruct.customerPONo);

		} finally {
			logger.endTimer("collectionDebitCard - Reverse Auth implementation");
		}
	}

	public YFSExtnPaymentCollectionOutputStruct collectionCustomerAccount(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct inStruct) throws YFSUserExitException {
		logger.beginTimer("collectionCustomerAccount - Reverse Auth implementation");
		try {
			return ueImpl(oEnv, inStruct, inStruct.customerPONo);

		} finally {
			logger.endTimer("collectionCustomerAccount - Reverse Auth implementation");
		}
	}

	public YFSExtnPaymentCollectionOutputStruct collectionOthers(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct inStruct) throws YFSUserExitException {
		logger.beginTimer("collectionOthers - Reverse Auth implementation");
		try {
			return ueImpl(oEnv, inStruct, inStruct.customerPONo);

		} finally {
			logger.endTimer("collectionOthers - Reverse Auth implementation");
		}
	}

	public YFSExtnPaymentCollectionOutputStruct collectionStoredValueCard(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct inStruct) throws YFSUserExitException {
		logger.beginTimer("collectionStoredValueCard - Reverse Auth implementation");
		try {
			return ueImpl(oEnv, inStruct, inStruct.customerPONo);

		} finally {
			logger.endTimer("collectionStoredValueCard - Reverse Auth implementation");
		}
	}

	protected YFSExtnPaymentCollectionOutputStruct ueImpl(YFSEnvironment oEnv,
			YFSExtnPaymentCollectionInputStruct in, String branchingLogicKey)
			throws YFSUserExitException {
		if (logger.isDebugEnabled()) {
			printAsteriskBar();
			logger.debug("** Entering user exit code for: " + userExitName);
			printAsteriskBar();
		}
		try {
			// Show the user exit's input XML on the application server terminal
			if (isVerboseLoggingEnabled()) {
				printInfoBar();
				logger.verbose("User exit input is: \n");
				printPublicFields(in);
				printInfoBar();
			}
			YFSExtnPaymentCollectionOutputStruct outStruct = doLogic(oEnv, in, branchingLogicKey);
			// Show the user exit's output XML on the application server
			// terminal
			if (isVerboseLoggingEnabled()) {
				printInfoBar();
				logger.verbose("User exit output is:\n");
				printPublicFields(outStruct);
				printInfoBar();
			}
			// Return the user exit's output XML.
			return outStruct;
		} catch (Exception ex) {
			printErrorInfo(ex);
			if (!logger.isDebugEnabled()) {
				// If output level is none, simplify the exception message.
				throw new YFSUserExitException("Exception raised in the user exit code.");
			} else {
				// Otherwise, pass the root cause of the error into
				// YFSUserExitException.
				// This will allow the error to be analyzed in the Harness.
				throw new YFSUserExitException(ex.getMessage());
			}
		} finally {
			// display that the UE has ended, successfully or not.
			if (logger.isDebugEnabled()) {
				printAsteriskBar();
				logger.debug("** Exiting user exit code for: " + userExitName);
				printAsteriskBar();
			}
		}
	}

	// utility method overrides

	protected void printBar(char symbol) {
		StringBuffer line = new StringBuffer();
		for (int i = 0; i < 40; i++)
			line.append(symbol);
		logger.verbose(line.toString());
	}

	protected void printErrorInfo(String message) {
		printBar('$');
		logger.error(message);
		printBar('$');
	}

	protected void printErrorInfo(Exception ex) {
		printBar('$');
		logger.error(ex);
		printBar('$');
	}

	protected static boolean isVerboseLoggingEnabled() {
		return logger.isEnabledFor(YFCLogLevel.VERBOSE);
	}

	/**
	 * Call to print a line of '*' for blocks of information. Will automatically
	 * add whitespace before the beginning line and after the end line
	 */
	protected void printAsteriskBar() {
		printBar('*');
	}

	/**
	 * Call to print a line of '#' for blocks of information. Will automatically
	 * add whitespace before the beginning line and after the end line
	 */
	protected void printInfoBar() {
		printBar('#');
	}

	protected void printPublicFields(Object in) {
		StringBuffer buf = new StringBuffer();
		printPublicFields(in, buf, 0);
		logger.verbose(buf.toString());

	}

	private void printPublicFields(Object in, StringBuffer buf, int buffer) {
		Map fields = getPublicFields(in);
		int maxField = 0 + buffer * 2;
		for (Iterator i = fields.keySet().iterator(); i.hasNext();) {
			String tmp = (String) i.next();
			maxField = Math.max(maxField, tmp.length());
		}
		maxField++;
		for (Iterator i = fields.keySet().iterator(); i.hasNext();) {
			String key = (String) i.next();
			for (int len = 0; len < buffer * 2; len++)
				buf.append('-');
			buf.append(key);
			for (int len = key.length(); len < maxField; len++)
				buf.append(len % 2 == 0 ? '.' : ' ');
			Object object = fields.get(key);
			if (object == null || object instanceof String || object instanceof Boolean
					|| object instanceof Long || object instanceof Integer
					|| object instanceof Double)
				buf.append("= ").append(object).append('\n');
			else if (object instanceof Document)
				buf.append(YFCDocument.getDocumentFor((Document) object)).append('\n');
			else if (object instanceof Date)
				buf.append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format((Date)object)).append('\n');
			else if (object instanceof Collection) {
				Collection c = (Collection) object;
				buf.append('\n');
				for (Iterator iterator = c.iterator(); iterator.hasNext();) {
					buf.append("+---------------------------------").append('\n');
					printPublicFields(iterator.next(), buf, buffer + 1);
				}
			} else
				printPublicFields(object, buf, buffer + 1);
		}
	}

	protected Map getPublicFields(Object o) {
		Class clz = o.getClass();
		Field f[] = clz.getFields();
		List fList = Arrays.asList(f);
		Collections.sort(fList, new Comparator() {

			public int compare(Object o1, Object o2) {
				Field f1 = (Field) o1;
				Field f2 = (Field) o2;
				String s1 = f1.getName() + f1.getDeclaringClass().getName();
				String s2 = f2.getName() + f2.getDeclaringClass().getName();
				return s1.compareTo(s2);
			}

		});
		f = (Field[]) fList.toArray();
		Map m = new TreeMap();
		for (int i = 0; i < f.length; i++) {
			Field fld = f[i];
			try {
				String string = fld.getName();
				string = fld.getName() + " (" + fld.getDeclaringClass().getName() + ")";
				m.put(string, fld.get(o));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	protected boolean isVoid(String s) {
		return s == null || s.trim().equals("");
	}

	protected static YFCLogCategory logger = YFCLogCategory.instance(CollectionSupportingReverseAuthorizationsv3.class);

	protected String userExitName = CollectionSupportingReverseAuthorizationsv3.class.getName();
	private PaymentType paymentType;
	private YFCDocument orderDetailsDoc;
	private YFCDocument recordExternalChargesInDoc = null;
	/**
	 * @return the recordExternalChargesInDoc
	 */
	protected final YFCDocument getRecordExternalChargesInDoc() {
		return recordExternalChargesInDoc;
	}

	/**
	 * @param recordExternalChargesInDoc the recordExternalChargesInDoc to set
	 */
	protected final void setRecordExternalChargesInDoc(YFCDocument recordExternalChargesInDoc) {
		this.recordExternalChargesInDoc = recordExternalChargesInDoc;
	}

	private String returnMessage;

	/**
	 * @author rspremulli
	 * 
	 */
	private final class PaymentType {
		private String group = null;
		private String reversalStrategy = null;
		private boolean isChargeInsteadOfAuth = false;
		private boolean isReuseAuth = false;
		private boolean isCardTypeEnabled = false;

		private PaymentType(String group, String reversalStrategy, boolean isChargeInsteadOfAuth, boolean isReuseAuth) {
			super();
			this.group = group;
			this.reversalStrategy = reversalStrategy;
			this.isChargeInsteadOfAuth = isChargeInsteadOfAuth;
			this.isReuseAuth = isReuseAuth;
		}

		public PaymentType(Document doc) {
			this(doc, false);
		}
		public PaymentType(Document doc, boolean cardTypeEnabled) {
			this(YFCDocument.getDocumentFor(doc).getDocumentElement().getFirstChildElement());
			isCardTypeEnabled = cardTypeEnabled;
		}

		private PaymentType(YFCElement e) {
			this(e.getAttribute("PaymentTypeGroup"),
					e.getAttribute("AuthorizationReversalStrategy"), e.getBooleanAttribute(
						"ChargeInsteadOfAuth", false), e.getBooleanAttribute(
							"UseSameAuthorizationMutlipleTimes", false));
		}

		/**
		 * @return the group
		 */
		public final String getGroup() {
			return group;
		}

		/**
		 * @return the reversalStrategy
		 */
		public final String getReversalStrategy() {
			return reversalStrategy;
		}

		/**
		 * @return the isChargeInsteadOfAuth
		 */
		public final boolean isChargeInsteadOfAuth() {
			return isChargeInsteadOfAuth;
		}

		/**
		 * @author Robert Spremulli (rspremul@us.ibm.com)
		 * @return the isCardTypeEnabled
		 */
		protected final boolean isCardTypeEnabled() {
		    return isCardTypeEnabled;
		}

		/**
		 * @author Robert Spremulli (rspremul@us.ibm.com)
		 * @return the isReuseAuth
		 */
		protected final boolean isReuseAuth() {
		    return isReuseAuth;
		}

		/**
		 * @author Robert Spremulli (rspremul@us.ibm.com)
		 * @param group the group to set
		 */
		protected final void setGroup(String group) {
		    this.group = group;
		}

		/**
		 * @author Robert Spremulli (rspremul@us.ibm.com)
		 * @param isReuseAuth the isReuseAuth to set
		 */
		protected final void setReuseAuth(boolean isReuseAuth) {
		    this.isReuseAuth = isReuseAuth;
		}

	}
}

