/*******************************************************************************
 * (C) Copyright 2010 Sterling Commerce, Inc.
 *******************************************************************************/

package com.ue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSExtnPaymentCollectionInputStruct;
import com.yantra.yfs.japi.YFSUserExitException;

/**
 * This class may override the base class to use restricted classes.
 * 
 * @author rspremulli
 * 
 */
public class CollectionSupportingReverseAuthorizationsQAv3 extends
		CollectionSupportingReverseAuthorizationsv3 {
	protected static final SimpleDateFormat AUTH_ID_FORMAT = new SimpleDateFormat("yyyyMMddHH");
	private static String lastAuthIdTail = null;
	private int prefix = 10;
	private YFSContext oCtx;
	String ohk = null;
	private Document outDoc;

	protected void init(YFSEnvironment oEnv, YFSExtnPaymentCollectionInputStruct in) {
		super.init(oEnv, in);
		ohk = in.orderHeaderKey;
		oCtx = (YFSContext) oEnv;
		long time = oCtx.getDBDate().getTime();
		currentTime.setTimeInMillis(time);
		futureTime.setTimeInMillis(time);
		outDoc = null;
		prefix = 10;
	}

	protected String getId() {
		String authIdTail = AUTH_ID_FORMAT.format(currentTime.getTime());
		if (authIdTail.equals(lastAuthIdTail) && ohk != null) {
			while(isAuthIdUsed(getAuthId(authIdTail))){
				incrementPrefix();
			}
			return getAuthId(authIdTail);
		} else {
			lastAuthIdTail = authIdTail;
		}
		return getAuthId(authIdTail);
	}

	/**
	 * @param authIdTail
	 * @return
	 */
	private String getAuthId(String authIdTail) {
		return getPrefix() + authIdTail;
	}

	/**
	 * @throws SAXException
	 * @throws IOException
	 * @throws YFSUserExitException
	 */
	private boolean isAuthIdUsed(String authId) {
		if(isVoid(authId))
			return false;
		try {
			if(outDoc == null)
				outDoc = getDoc();
			NodeList elementsByTagName = outDoc.getElementsByTagName("ChargeTransactionDetail");
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				Element e= (Element) elementsByTagName.item(i);
				String attribute = e.getAttribute("AuthorizationID");
				if(isVoid(attribute))
					continue;
				if(authId.equals(attribute))
					return true;
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e){
			logger.error(e);
			throw new RuntimeException();
		}
		return false;
	}

	/**
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws YFSUserExitException
	 */
	private Document getDoc() throws SAXException, IOException, YFSUserExitException {
		oCtx.setApiTemplate("getOrderDetails",
				YFCDocument.parse("<Order OrderHeaderKey=\"\"><ChargeTransactionDetails><ChargeTransactionDetail AuthorizationID=\"\"/></ChargeTransactionDetails></Order>").getDocument());
		Document outDoc = invokeApi("getOrderDetails", oCtx, YFCDocument.parse(
				"<Order OrderHeaderKey=\"" + ohk + "\"/>").getDocument());
		oCtx.clearApiTemplate("getOrderDetails");
		return outDoc;
	}

	private void incrementPrefix() {
		prefix++;
	}

	private String getPrefix() {
		return String.valueOf(prefix) + "000";
	}
}
