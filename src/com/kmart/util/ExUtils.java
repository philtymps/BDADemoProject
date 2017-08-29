
package com.kmart.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;


public class ExUtils {

  // initialize logger
  private static final YFCLogCategory LOGGER = YFCLogCategory.instance(ExUtils.class);

  private ExUtils() {

  }

  /**
   * @param env
   * @param apiName
   * @param inXMLDoc
   * @return
   * @throws Exception
   * @description invoke API within the system
   */
  public static Document invokeAPI(YFSEnvironment env, String apiName, Document inXMLDoc, Document inTemplate) {
    try {
      LOGGER.verbose("Entering invokeAPI method");
      if (!YFCCommon.isVoid(inTemplate)) {
        env.setApiTemplate(apiName, inTemplate);
      }
      YIFApi yifApi = YIFClientFactory.getInstance().getLocalApi();
      Document outXML = yifApi.invoke(env, apiName, inXMLDoc);
      env.clearApiTemplate(apiName);
      LOGGER.verbose("Exiting invokeAPI method");
      return outXML;
    }  catch (YIFClientCreationException e) {
      LOGGER.error("yifclientcreation exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    } catch (YFSException e) {
      LOGGER.error("yfsexception exception thrown ", e);
      throw e;
    } catch (RemoteException e) {
      LOGGER.error("remote exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    }
  }

  /**
   *
   * This method is used to invoke any Sterling API.
   *
   * @param env : YFSEnvironment variable.
   * @param apiName : Name of the API to be invoked.
   * @param inputDoc : Input document to be passed to the API.
   * @param templateDoc : Template document to be passed to the API.
   * @return
   *
   */
  public static YFCDocument invokeAPI(YFSEnvironment env, String apiName, YFCDocument inputDoc, YFCDocument templateDoc) {
    Document inTemplate=null;
    Document inXMLDoc = inputDoc.getDocument();
    if (!YFCCommon.isVoid(templateDoc)) {
      inTemplate = templateDoc.getDocument();
    }
    Document outDoc = invokeAPI(env, apiName, inXMLDoc, inTemplate);
    if (!YFCCommon.isVoid(outDoc)) {
      return YFCDocument.getDocumentFor(outDoc);
    } else {
      return null;
    }
  }

  /**
   * Calls a YIF api
   *
   * @param env YFS env
   * @param input Input xml
   * @param apiName String api name
   * @param templateName String template name
   * @return Result xml on calling the sterling api
   * @throws Exception if the api can't be called
   */
  public static Document invokeAPI(YFSEnvironment env, Document input, String apiName, String templateName) {
    Document outputDoc = null;

    if (!isEmpty(templateName)) {
      env.setApiTemplate(apiName, templateName);
    }

    try{
      YIFApi api = YIFClientFactory.getInstance().getApi();
      outputDoc = api.invoke(env, apiName, input);
    } catch (YIFClientCreationException e) {
      LOGGER.error("yifclientcreation exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    } catch (YFSException e) {
      LOGGER.error("yfsexception exception thrown ", e);
      throw e;
    } catch (RemoteException e) {
      LOGGER.error("remote exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    }
    env.clearApiTemplate(apiName);

    return outputDoc;
  }

	/**
	 * @param env
	 * @param serviceName
	 * @param inXMLDoc
	 * @return
	 * @description invoke service within the system
	 */
	public static Document invokeService(YFSEnvironment env, String serviceName, Document inXMLDoc)  {
		try {
			LOGGER.verbose("Entering invokeService method");
			YIFApi oApi = YIFClientFactory.getInstance().getLocalApi();
			LOGGER.verbose("Exiting invokeService method");
			return oApi.executeFlow(env, serviceName, inXMLDoc);
		} catch (YIFClientCreationException e) {
			LOGGER.error("yifclientcreation exception thrown ", e);
			YFCException ex = new YFCException(e.getMessage());
			ex.setStackTrace(e.getStackTrace());
			throw ex;
		} catch (YFSException e) {
			LOGGER.error("yfsexception exception thrown ", e);
			throw e;
		} catch (YFCException e) {
			LOGGER.error("yfcexception exception thrown ", e);
			throw e;
		} catch (RemoteException e) {
			LOGGER.error("remote exception thrown ", e);
			YFCException ex = new YFCException(e.getMessage());
			ex.setStackTrace(e.getStackTrace());
			throw ex;
		}
	}

  /**
   *
   * This method is used to invoke any custom service.
   *
   * @param env : YFSEnvironment variable
   * @param serviceName : Name of the service to be invoked.
   * @param inXMLDoc : Input XML to be passed to the service.
   * @return : Output of the service.
   *
   */
  public static YFCDocument invokeService(YFSEnvironment env, String serviceName, YFCDocument inXMLDoc) {
    if (YFCCommon.isVoid(inXMLDoc)) {
      throw new YFSException("Input Document cannot be null.");
    }
    Document doc = invokeService(env, serviceName, inXMLDoc.getDocument());
    if(!YFCCommon.isVoid(doc)){
      return YFCDocument.getDocumentFor(doc);
    }
    return null;
  }

  /**
   * @param env
   * @param orderHeaderKey
   * @param templateDoc
   * @return
   * @throws Exception
   */
  

  /**
   * @param orderHeaderKey
   * @return
   * @throws GCException
   */
  

  /**
   * @param env
   * @param apiName
   * @param inXMLDoc
   * @return
   * @throws Exception
   * @description invoke API within the system
   */
  public static Document invokeAPI(YFSEnvironment env, String apiName, Document inXMLDoc) {
    LOGGER.verbose("Entering invokeAPI method");
    YIFApi yifApi = null;
    Document outXML = null;
    try {
      yifApi = YIFClientFactory.getInstance().getLocalApi();
      outXML = yifApi.invoke(env, apiName, inXMLDoc);
    } catch (YIFClientCreationException e) {
      LOGGER.error("yifclientcreation exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    } catch (YFSException e) {
      LOGGER.error("yfsexception exception thrown ", e);
      throw e;
    } catch (RemoteException e) {
      LOGGER.error("remote exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    }
    LOGGER.verbose("Exiting invokeAPI method");
    return outXML;
  }

  /**
   *
   * Description of isAffirmative Return true for Y / true / 1 else false
   *
   * @param value
   * @return
   *
   */
  public static boolean isAffirmative(String value) {

    if (value == null) {
      return false;
    } else if ("Y".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value) || "1".equals(value)) {
      return true;
    }
    return false;
  }

  /**
   *
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    return ((str == null) || (str.trim().length() == 0));
  }

  /**
   *
   * Description of getItemList Gives output for getItemList
   *
   * @param env
   * @param sItemID
   * @param sUOM
   * @param sOrganizationCode
   * @return
   * @throws Exception
   *
   */
 

  /**
  *
  * Description of getItemList Gives output for getItemList with IgnoreIsSoldSeparately="Y" 
  *
  * @param env
  * @param sItemID
  * @param sUOM
  * @param sOrganizationCode
  * @return
  * @throws Exception
  *
  */


 
  /**
   *
   * Description of getItemList Gives output for getItemList
   *
   * @param env
   * @param sItemID
   * @param sUOM
   * @param sOrganizationCode
   * @return
   * @throws Exception
   *
   */
 
  

  /**
   * To get the document from relative path
   *
   * @param path
   * @return
   * @throws Exception
   */
  public static Document getDocumentFromPath(String path) {
    YFCDocument templateDoc;
    try {
      templateDoc = YFCDocument.parse(ExUtils.class.getResourceAsStream(path));
      if (templateDoc != null) {
        return templateDoc.getDocument();
      } else {
        return null;
      }
    } catch (SAXException e) {
      LOGGER.error("SAXException exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    } catch (IOException e) {
      LOGGER.error("IOException exception thrown ", e);
      YFCException ex = new YFCException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    }
  }

  /**
   * this method return the array of length 2 . In the first index, it stores the rounded(Rounding
   * mode is half-Up) value In the second index, it stores the difference of original number and
   * rounded value.
   *
   * @param dbNumber
   * @param decPrecision
   * @return
   */
  public static double[] splitNumberForDecimalPrecision(double dbNumber, int decPrecision) {
    DecimalFormat twoDForm = new DecimalFormat("#.##");
    // converting to dwo
    // digit format as
    // per as the
    // assumption that
    // input value will
    // alwasy be having 2
    // digit decimal.
    // to handle NaN values. NaN is returned when 0.0/0.0
    if (Double.isNaN(dbNumber) || Double.isInfinite(dbNumber)) {
      dbNumber = 0.00;
    }
    dbNumber = Double.valueOf(twoDForm.format(dbNumber));
    double[] arraySplitNumberForDecimalPrecision = new double[2];
    BigDecimal bd = new BigDecimal(dbNumber);
    double dbNumberVal = dbNumber;
    dbNumberVal = bd.setScale(decPrecision, RoundingMode.HALF_UP).doubleValue();
    arraySplitNumberForDecimalPrecision[0] = dbNumberVal;
    arraySplitNumberForDecimalPrecision[1] = dbNumber - dbNumberVal;
    return arraySplitNumberForDecimalPrecision;
  }

  /**
   * Format the value to display amount in format ".00"
   *
   * @param dAmount
   * @param decPrecision
   * @return
   * @throws Exception
   */
  public static BigDecimal formatNumberForDecimalPrecision(Double dAmount, int decPrecision) {
    LOGGER.verbose("Inside formatChargeAmount");
    BigDecimal dTmp = new BigDecimal(dAmount).setScale(decPrecision, RoundingMode.HALF_EVEN);
    LOGGER.debug("formatted value: " + dTmp);
    LOGGER.verbose("End formatChargeAmount");
    return dTmp;
  }

  /**
   *
   * @param env
   * @param orderHeaderKey
   * @param templateName
   * @return
   * @throws GCException
   */
 

  
  /**
   *
   * @param codeType
   * @param orgCode
   * @return
   * @throws YIFClientCreationException
   * @throws RemoteException
   * @throws YFSException
   */
 

    

  /**
   * Sorted based on CodeValue
   * @param codeType
   * @param orgCode
   * @return
   * @throws YIFClientCreationException
   * @throws RemoteException
   * @throws YFSException
   */

    


 

  


 
  /**
   * This method is developed as a part of GC-Phase2. This Method renames the Nodes Tag Name in the
   * complete inDoc.
   *
   * @param doc
   * @param fromTag
   * @param toTag
   */
  public static void changeTagName(Document doc, String fromTag, String toTag) {
    LOGGER.beginTimer("GCCommonUtil.changeTagName");
    NodeList nodes = doc.getElementsByTagName(fromTag);
    for (int i = 0; i < nodes.getLength(); i++) {
      if (nodes.item(i) instanceof Element) {
        Element elem = (Element) nodes.item(i);
        doc.renameNode(elem, elem.getNamespaceURI(), toTag);
      }
    }
    LOGGER.endTimer("GCCommonUtil.changeTagName");
  }
  /**
   * @param env
   * @param orderHeaderKey
   * @param templateDoc
   * @return
   * @throws Exception
   */
  

  /**
   * This method returns attribute value as integer
   *
   * @param elem
   * @param attribute
   * @return
   */
  public static int getIntAttribute(YFCElement elem, String attribute) {
    Double d = elem.getDoubleAttribute(attribute);
    return d.intValue();
  }

  /**
   * This util method update Existing Map
   *
   * @param map
   * @param key
   * @param value
   * @return
   */
  public static Map<String, List<String>> updateMap(Map<String, List<String>> map, String key, String value) {
    if (!map.containsKey(value)) {
      List<String> dependentList = new ArrayList<String>();
      dependentList.add(key);
      map.put(value, dependentList);
    } else {
      List<String> dependentList = map.get(value);
      dependentList.add(key);
      map.put(value, dependentList);
    }
    return map;
  }

  /**
   * This Util method Update Existing Map
   *
   * @param map
   * @param key
   * @param value
   * @return
   */
  public static Map<String, List<Integer>> updateMap(Map<String, List<Integer>> map, String key, int value) {
    if (map.containsKey(key)) {
      map.get(key).add(value);
    } else {
      List<Integer> values = new ArrayList<Integer>();
      values.add(value);
      map.put(key, values);
    }
    return map;
  }

  /**
   *
   * Description of getCommonCodeListByTypeAndValue
   *
   * @param env
   * @param codeType
   * @param orgCode
   * @param codeValue
   * @return
   *
   */
 

  


  /**
   *
   * @param yfsEnv
   * @param sldapDN
   * @return
   */

 

 

 

 
}
