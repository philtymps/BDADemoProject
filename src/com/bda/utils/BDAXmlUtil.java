/*
 * IBM Confidential 
 *
 * OCO Source Materials
 *
 * IBM Sterling Selling and Fullfillment Suite
 *
 * (c) Copyright IBM Corp. 2001, 2016 All Rights Reserved.
 *
 * The source code for this program is not published or otherwise divested of its trade secrets,
 * irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.bda.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Utility class for manipulating and using XML Documents, Nodes.
 */
public class BDAXmlUtil {

	private static ArrayList<DocumentBuilder> docBuilderList = new ArrayList<DocumentBuilder>();

	protected BDAXmlUtil() {
	}

	private static synchronized void releaseDocumentBuilder(DocumentBuilder docBuild) {
		if (docBuild != null) {
			docBuilderList.add(docBuild);
		}
	}
	
	/**
	 * Utility method which creates a Document Builder
	 * 
	 * @return The newly created Document builder
	 * @throws ParserConfigurationException  if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 * @throws FactoryConfigurationError if the implementation is not available or cannot be instantiated.
	 */
	public static synchronized DocumentBuilder getDocumentBuilder() throws ParserConfigurationException, FactoryConfigurationError {
		DocumentBuilder docBuilder = null;
		if (docBuilderList.size() > 0) {
			docBuilder = (DocumentBuilder) docBuilderList.remove(0);
		}
		if (docBuilder == null) {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		return docBuilder;
	}

	/**
	 * Creates an Document using the given string. If it is void, it creates an empty document.
	 * 
	 * @param xmlStr
	 *            The XML string from which a Document needs to be created
	 * @return The newly created Document <br> Null in case of errors
	 */
	public static Document createFromString(String xmlStr) {
		if (BDACommon.isVoid(xmlStr))
			return null;
		DocumentBuilder docBuild = null;
		try {
			docBuild = getDocumentBuilder();
			Reader reader = new BufferedReader(new StringReader(xmlStr));
			return docBuild.parse(new InputSource(reader));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseDocumentBuilder(docBuild);
		}
		// If exception.
		return null;
	}

	/**
	 * Creates an XML document using the given file URL using the load method of Document.
	 * 
	 * @param url
	 *            A local file path or an HTTP URL
	 * @return The newly created Document <br> Null in case of errors
	 * @throws Exception IO, parsing, and exceptions DocumentBuilder
	 */
	public static Document createFromFileOrUrl(String url) throws Exception {
		DocumentBuilder docBuild = null;
		try {
			if (BDACommon.isVoid(url))
				return null;

			docBuild = getDocumentBuilder();
			Document doc = docBuild.parse(url);
			return doc;
		} finally {
			releaseDocumentBuilder(docBuild);
		}
	}

	/**
	 * Creates an XML document using from the input stream using the load method of Document.
	 * 
	 * @param inputStream
	 *            The input stream using which a document needs to be created
	 * @return The newly created Document <br> Null in case of errors
	 */
	public static Document createFromStream(InputStream inputStream) {
		if (inputStream == null)
			return null;

		DocumentBuilder docBuild = null;
		Document doc = null;
		try {
			docBuild = getDocumentBuilder();
			doc = docBuild.parse(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseDocumentBuilder(docBuild);
		}
		return doc;
	}

	/**
	 * Writes the XML document to the file. If the file exists, it overrides, else it creates a new
	 * one. UTF-8 is used as the file encoding. If the filename parameter is void, this method has
	 * no effect.
	 * 
	 * @param document
	 *            The document which needs to be written to a file
	 * @param fileName
	 *            The file name to which the document needs to be written
	 */
	public static void write2File(Document document, String fileName) throws Exception {
		write2File(document.getDocumentElement(), fileName);
	}

	/**
	 * Writes the XML node to the file. If the file exists, it overrides, else it creates a new one.
	 * UTF-8 is used as the file encoding. If filename is void,
	 * this method has no effect.
	 * 
	 * @param element
	 *            The element which needs to be written to a file
	 * @param fileName
	 *            The file name to which the document needs to be written
	 */
	public static void write2File(Element element, String fileName) throws Exception {
		if (!BDACommon.isVoid(fileName)) {
			FileWriter sw = new FileWriter(fileName);
			writeXml(element, sw, true);
		}
	}

	/**
	 * Returns the XML document as a formatted String.
	 * 
	 * @param document
	 *            The document whose formatted string representation is required
	 * @return The string representation of the document
	 */
	public static String getString(Document document) {
		if (document == null)
			return null;
		return getString(document.getDocumentElement());
	}

	/**
	 * Returns the XML element as a formatted String.
	 * 
	 * @param element
	 *            The element whose formatted string representation is required
	 * @return The string representation of the element
	 */
	public static String getString(Element element) {
		if (element == null)
			return null;
		StringWriter stringWriter = new StringWriter();
		writeXml(element, stringWriter, false);
		String retVal = stringWriter.toString();
		try {
			stringWriter.close();
		} catch (Exception e) {
			// ignore.
		}
		return retVal;
	}

	/**
	 * Utility to write XML as a formatted string to the given Writer.
	 * 
	 * @param document
	 *            The document that needs to be written into the writer
	 * @param writer
	 *            The writer to which to write the document
	 * @param closeWriter
	 *            True if the writer needs to be closed after completion
	 */
	public static void writeXml(Document document, Writer writer, boolean closeWriter) {
		if (document != null && writer != null)
			writeXml(document.getDocumentElement(), writer, closeWriter);
	}

	/**
	 * Utility to write XML as a formatted string to the given Writer.
	 * 
	 * @param element
	 *            The element that needs to be written into the writer
	 * @param writer
	 *            The writer to which to write the document
	 * @param closeWriter
	 *            True if the writer needs to be closed after completion
	 */
	public static boolean writeXml(Element element, Writer out, boolean closeWriter) {
		try {
			if (element != null && out != null) {
				TransformerFactory transfac = TransformerFactory.newInstance();
				Transformer trans = transfac.newTransformer();
				trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				trans.setOutputProperty(OutputKeys.INDENT, "yes");
		            
				//create string from xml tree
				StreamResult result = new StreamResult(out);
				DOMSource source = new DOMSource(element);
				trans.transform(source, result);
			}

			if (out != null && closeWriter) {
				out.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Utility to check if an attribute exits
	 * 
	 * @param element
	 *            The element on which the check needs to be performed
	 * @param attributeName
	 *            The name of the attribute whose existence needs to be verified
	 * @return True if attribute exists
	 */
	public static boolean hasAttribute(Element element, String attributeName) {
		return (element == null) ? false : element.hasAttribute(attributeName);
	}

	/**
	 * Get the attribute value. Returns null if attribute does not exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @return The attribute value
	 */
	public static String getAttribute(Element element, String attributeName) {
		return (element == null) ? null : element.getAttribute(attributeName);
	}

	/**
	 * Get the attribute value as int. Returns 0 if attribute value is void or
	 * if the attribute does not exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @return The attribute value as int
	 */
	public static int getIntAttribute(Element element, String attributeName) {
		return getIntAttribute(element, attributeName, 0);
	}

	/**
	 * Get the attribute value as int. Returns the default return value that is
	 * passed instead if attribute value is void or if the attribute does not
	 * exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @param defaultRetValue
	 *            The default return value to be returned if attribute value is
	 *            void or if the attribute does not exist.
	 * @return The attribute value as int
	 */
	public static int getIntAttribute(Element element, String attributeName, int defaultRetValue) {
		String val = getAttribute(element, attributeName);
		return (BDACommon.isVoid(val)) ? defaultRetValue : Integer.parseInt(val);
	}

	/**
	 * Get the attribute value as long. Returns 0 if attribute value is void or
	 * if the attribute does not exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @return The attribute value as long
	 */
	public static long getLongAttribute(Element element, String attributeName) {
		return getLongAttribute(element, attributeName, 0);
	}
	
	/**
	 * Get the attribute value as long. Returns the default return value that is
	 * passed if attribute value is void or if the attribute does not exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @param defaultRetValue
	 *            The default return value to be returned if attribute value is
	 *            void or if the attribute does not exist.
	 * @return The attribute value as long
	 */
	public static long getLongAttribute(Element element, String attributeName, long defaultRetValue) {
		String val = getAttribute(element, attributeName);
		return (BDACommon.isVoid(val)) ? defaultRetValue : Long.parseLong(val);
	}

	/**
	 * Get the attribute value as double. Returns 0 if attribute value is void
	 * or if the attribute does not exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @return The attribute value as double
	 */
	public static double getDoubleAttribute(Element element, String attributeName) {
		return getDoubleAttribute(element, attributeName, 0);
	}

	/**
	 * Get the attribute value as double. Returns the default return value that
	 * is passed if attribute value is void or if the attribute does not exist.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @param defaultRetValue
	 *            The default return value to be returned if attribute value is
	 *            void or if the attribute does not exist.
	 * @return The attribute value as double
	 */
	public static double getDoubleAttribute(Element element, String attributeName, double defaultRetValue) {
		String val = getAttribute(element, attributeName);
		return (BDACommon.isVoid(val)) ? defaultRetValue : Double.parseDouble(val);
	}


	/**
	 * Get the attribute value as boolean. Returns false if attribute value is
	 * void or if the attribute does not exist. Supported true values are "y",
	 * "yes", and "true" irrespective of case.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @return The attribute value as boolean
	 */
	public static boolean getBooleanAttribute(Element element, String attributeName) {
		String val = getAttribute(element, attributeName);
		return getBoolean(val);
	}
	
	/**
	 * Get the attribute value as boolean. Returns the default return value if attribute value is
	 * void or if the attribute does not exist. Supported true values are "y",
	 * "yes", and "true" irrespective of case.
	 * 
	 * @param element
	 *            The element from which the attribute value needs to be fetched
	 * @param attributeName
	 *            The name of the attribute whose value needs to be returned
	 * @param defaultRetValue
	 *            The default return value to be returned if attribute value is
	 *            void or if the attribute does not exist.
	 * @return The attribute value as boolean
	 */
	public static boolean getBooleanAttribute(Element element, String attributeName, boolean defaultRetValue) {
		String val = getAttribute(element, attributeName);
		return getBoolean(val,defaultRetValue);
	}

	private static boolean getBoolean(String str, boolean defaultRetValue) {
		if (BDACommon.isVoid(str))
			return defaultRetValue;
		str = str.toLowerCase();
		return (str.equals("true") || str.equals("y") || str.equals("yes"));
	}

	/**
	 * Get the boolean value of the string
	 * 
	 * @param str
	 *            The string whose boolean representation is required
	 * @return True if the string value is one of: "true", "y" or "yes", false for all other cases
	 */
	public static boolean getBoolean(String str) {
		return getBoolean(str, false);
	}

	/**
	 * Sets an attribute value. If element is null or attribute name is void, it does nothing. If
	 * attribute value is null, it sets empty string. If attribute not found, it creates one and
	 * sets value.
	 * 
	 * @param element
	 *            The element in which the attribute value needs to be set
	 * @param attributeName
	 *            The name of the attribute which needs to be set
	 * @param attributeValue
	 *            The value to be set
	 */
	public static void setAttribute(Element element, String attributeName, String attributeValue) {
		if (element != null && !BDACommon.isVoid(attributeName)) {
			element.setAttribute(attributeName, attributeValue);
		}
	}

	/**
	 * Sets an attribute value. If element is null or attribute name is void, it does nothing. If
	 * attribute not found, it creates one and sets value.
	 * 
	 * @param element
	 *            The element in which the attribute value needs to be set
	 * @param attributeName
	 *            The name of the attribute which needs to be set
	 * @param attributeValue
	 *            The value to be set
	 */
	public static void setAttribute(Element element, String attributeName, int attributeValue) {
		if (element != null && !BDACommon.isVoid(attributeName)) {
			element.setAttribute(attributeName, attributeValue + "");
		}
	}

	/**
	 * Sets an attribute value. If element is null or attribute name is void, it does nothing. If
	 * attribute not found, it creates one and sets value.
	 * 
	 * @param element
	 *            The element in which the attribute value needs to be set
	 * @param attributeName
	 *            The name of the attribute which needs to be set
	 * @param attributeValue
	 *            The value to be set
	 */
	public static void setAttribute(Element element, String attributeName, long attributeValue) {
		if (element != null && !BDACommon.isVoid(attributeName)) {
			element.setAttribute(attributeName, attributeValue + "");
		}
	}

	/**
	 * Sets an attribute value. If element is null or attribute name is void, it does nothing. If
	 * attribute not found, it creates one and sets value.
	 * 
	 * @param element
	 *            The element in which the attribute value needs to be set
	 * @param attributeName
	 *            The name of the attribute which needs to be set
	 * @param attributeValue
	 *            The value to be set
	 */
	public static void setAttribute(Element element, String attributeName, double attributeValue) {
		if (element != null && !BDACommon.isVoid(attributeName)) {
			element.setAttribute(attributeName, attributeValue + "");
		}
	}

	/**
	 * Sets an attribute value. If element is null or attribute name is void, it does nothing. If
	 * attribute not found, it creates one and sets value.
	 * 
	 * @param element
	 *            The element in which the attribute value needs to be set
	 * @param attributeName
	 *            The name of the attribute which needs to be set
	 * @param attributeValue
	 *            The value to be set
	 */
	public static void setAttribute(Element element, String attributeName, float attributeValue) {
		if (element != null && !BDACommon.isVoid(attributeName)) {
			element.setAttribute(attributeName, attributeValue + "");
		}
	}

	/**
	 * Sets an attribute value. If element is null or attribute name is void, it does nothing. If
	 * attribute not found, it creates one and sets value.
	 * 
	 * @param element
	 *            The element in which the attribute value needs to be set
	 * @param attributeName
	 *            The name of the attribute which needs to be set
	 * @param attributeValue
	 *            The value to be set
	 */
	public static void setAttribute(Element element, String attributeName, boolean attributeValue) {
		if (element != null && !BDACommon.isVoid(attributeName)) {
			element.setAttribute(attributeName, attributeValue ? "Y" : "N"); // we could also use
		}
	}

	/**
	 * Utility to copy all the attributes from the XML element fromElement to the XML element
	 * toElement. All extra attributes already existing in toElement are left as is. If either of
	 * the parameters is null the method does nothing.
	 * 
	 * @param fromElement
	 *            The element from which attributes need to copied
	 * @param toElement
	 *            The element to which attributes need to copied
	 */
	public static void setAttributes(Element fromElement, Element toElement) {
		setAttributes(fromElement, toElement, false);
	}

	/**
	 * Utility to copy all the attributes from the XML element fromElement to the XML element
	 * toElement. Depending on the value of clearToEle, existing attributes of toElement are removed
	 * before copying to avoid having extra attributes. If either fromElement or toElement is null
	 * it does nothing.
	 * 
	 * @param fromElement
	 *            The element from which attributes need to copied
	 * @param toElement
	 *            The element to which attributes need to copied
	 * @param clearToElement
	 *            True to clear all the attributes of toElement before copying the attributes from
	 *            the fromElement
	 */
	public static void setAttributes(Element fromElement, Element toElement, boolean clearToElement) {
		if (fromElement != null && toElement != null) {
			if (clearToElement) {
				removeAttributes(toElement);
			}

			NamedNodeMap map = fromElement.getAttributes();
			for (int i = 0; i < map.getLength(); i++) {
				toElement.setAttribute(map.item(i).getNodeName(), map.item(i).getNodeValue());
			}
		}
	}

	private static void removeAttributes(Element element) {
		/*
		 * Remove all the attributes of an element. Just to be safe, make a copy of attr names and
		 * then delete one by one.
		 */
		NamedNodeMap attrs = element.getAttributes();
		String[] names = new String[attrs.getLength()];
		for (int i = 0; i < names.length; i++) {
			names[i] = attrs.item(i).getNodeName();
		}
		for (int i = 0; i < names.length; i++) {
			attrs.removeNamedItem(names[i]);
		}

	}

	/**
	 * Get the iterator of all children of Element type. <br> <b> Note: </b> <br> Only immediate
	 * children of the element are considered
	 * 
	 * @param element
	 *            The element whose child elements are required
	 * @return An iterator of all child elements
	 */
	public static Iterator<Element> getChildren(Element element) {
		ArrayList<Element> list = new ArrayList<Element>();
		if (element != null && element.hasChildNodes()) {
			NodeList childList = element.getChildNodes();
			for (int i = 0; i < childList.getLength(); i++) {
				if (childList.item(i) instanceof Element) {
					list.add((Element) childList.item(i));
				}
			}
		}
		return list.iterator();
	}

	/**
	 * Creates a child element under the parent element with given child name. Returns the newly
	 * created child element. This method returns null if either parent is null or child name is
	 * void.
	 * 
	 * @param parentElement
	 *            The parent element
	 * @param childName
	 *            The node name of child to be created
	 * @return The newly created child element
	 */
	public static Element createChild(Element parentElement, String childName) {
		Element child = null;
		if (parentElement != null && !BDACommon.isVoid(childName)) {
			child = parentElement.getOwnerDocument().createElement(childName);
			parentElement.appendChild(child);
		}
		return child;
	}

	/**
	 * Imports an element including the subtree from another document under the parent element.
	 * Returns the newly created child element. This method returns null if either parent or element
	 * to be imported is null.
	 * 
	 * @param parentEle
	 *            The parent element
	 * @param ele2beImported
	 *            The element to be imported
	 * @return The newly created child element
	 */
	public static Element importElement(Element parentEle, Element ele2beImported) {
		Element child = null;
		if (parentEle != null && ele2beImported != null) {
			child = (Element) parentEle.getOwnerDocument().importNode(ele2beImported, true);
			parentEle.appendChild(child);
		}
		return child;
	}

	/**
	 * Imports an element including the subtree from another document under the parent element.
	 * Returns the newly created child element. This method returns null if either parent or element
	 * to be imported is null.
	 * 
	 * @param parentDoc
	 *            The parent document
	 * @param ele2beImported
	 *            The element to be imported
	 * @return The newly created child element
	 */
	public static Element importElement(Document parentDoc, Element ele2beImported) {
		Element child = null;
		if (parentDoc != null && ele2beImported != null) {
			child = (Element) parentDoc.importNode(ele2beImported, true);
			parentDoc.appendChild(child);
		}
		return child;
	}

	/**
	 * Gets the child element with the given name. If not found returns null. This method returns
	 * null if either parent is null or child name is void.
	 * 
	 * @param parentEle
	 *            The parent element
	 * @param childName
	 *            The node name of the child to be fetched
	 * @return The child element
	 */
	public static Element getChildElement(Element parentEle, String childName) {
		return (getChildElement(parentEle, childName, false));
	}

	/**
	 * Gets the child element with the given name. <br>If a child element is not found:
	 * <ul>
	 * <li> A new element will be created if "createIfNotExists" is true. </li>
	 * <li> Null will be returned if "createIfNotExists" is false. </li>
	 * </ul>
	 * 
	 * @param parentEle
	 *            The parent element
	 * @param childName
	 *            The node name of the child
	 * @param createIfNotExists
	 *            True to create a child with the given node name if not found
	 * @return The child element or Null if either parent is null or child name is void.
	 */
	public static Element getChildElement(Element parentEle, String childName, boolean createIfNotExists) {

		if (parentEle != null && !BDACommon.isVoid(childName)) {
			for (Node n = parentEle.getFirstChild(); n != null; n = n.getNextSibling()) {
				if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(childName)) {
					return (Element) n;
				}
			}

			// Did not find the element, create it if createIfNotExists is true
			// else return null;
			if (createIfNotExists) {
				Element child = createChild(parentEle, childName);
				return (child);
			}
		}
		return null;
	}

	/**
	 * Gets the first child element if it exists. If not found returns null. This method returns
	 * null if parent is null.
	 * 
	 * @param parentEle
	 *            The parent element
	 * @return The first child element
	 */
	public static Element getFirstChildElement(Element parentEle) {
		return getFirstOrLastChildElement(parentEle, true);
	}

	/**
	 * Gets the last child element if it exists. If not found returns null. This method returns null
	 * if parent is null.
	 * 
	 * @param parentEle
	 *            The parent element
	 * @return The last child element
	 */
	public static Element getLastChildElement(Element parentEle) {
		return getFirstOrLastChildElement(parentEle, false);
	}

	/**
	 * Gets the first or last child element if it exists depending on the flag. If not found returns
	 * null. This method returns null if parent is null.
	 */
	private static Element getFirstOrLastChildElement(Element parentEle, boolean first) {
		Element child = null;
		if (parentEle != null) {
			NodeList list = parentEle.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) instanceof Element) {
					child = (Element) list.item(i);
					if (first)
						break;
				}
			}
		}
		return child;
	}

	/**
	 * Utility method which creates a document given the document element node name
	 * 
	 * @param rootElementName
	 *            The node name of the document element to be created
	 * @return A new Document containing a root element with the name
	 */
	public static Document createDocument(String rootElementName) {
		DocumentBuilder docBuild = null;
		Document doc = null;
		try {
			docBuild = getDocumentBuilder();
			doc = docBuild.newDocument();
			Element root = doc.createElement(rootElementName);
			doc.appendChild(root);
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} finally {
			releaseDocumentBuilder(docBuild);
		}
		return doc;
	}

	/**
	 * Utility method which creates an empty document
	 * 
	 * @return A new Document
	 */
	public static Document createDocument() {
		DocumentBuilder docBuild = null;
		Document doc = null;
		try {
			docBuild = getDocumentBuilder();
			doc = docBuild.newDocument();			
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} finally {
			releaseDocumentBuilder(docBuild);
		}
		return doc;
	}

	/**
	 * Creates a new Document from the specified element
	 * 
	 * @param element
	 *            The element to be copied
	 * @return The document element of the newly created document
	 */
	public static Element getCopy(Element element) {
		if (BDACommon.isVoid(element))
			return null;

		DocumentBuilder docBuild = null;
		Document doc = null;
		try {
			docBuild = getDocumentBuilder();
			doc = docBuild.newDocument();
			doc.appendChild(doc.importNode(element, true));
			return doc.getDocumentElement();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} finally {
			releaseDocumentBuilder(docBuild);
		}
		return null;
	}

	/**
	 * Utility to get a list of child elements of a particular type
	 * 
	 * @param element
	 *            The parent element
	 * @param childName
	 *            The node name of the child
	 * @return List of child elements
	 */
	public static ArrayList<Element> getChildren(Element element, String childName) {
		if (BDACommon.isVoid(childName)) {
			return null;
		}
		ArrayList<Element> list = new ArrayList<Element>();
		if (element != null && element.hasChildNodes()) {
			NodeList childList = element.getChildNodes();
			for (int i = 0; i < childList.getLength(); i++) {
				if (childList.item(i) instanceof Element && childName.equals(childList.item(i).getNodeName())) {
					list.add((Element) childList.item(i));
				}
			}
		}
		return list;
	}
	
    /**
     * Utlity to merge Elements. The new child elements of fromElement(source Element) is 
     * imported into the toElement (target Element). If the same child element already exists
     * the attributes are merged.
     * The new attributes of fromElement(source element) is also added into the toElement 
     * (target Element). If the attribute already exists based on checkHasAttributeInTarget 
     * the attribute is overridden to the attribute value in fromElement (source Element). 
     * If checkHasAttributeInTarget is true the attribute is not overridden otherwise 
     * it is overriden.
     * 
     * @param fromElement is the source element
     * @param toElement is the target element
     * @param checkHasAttributeInTarget is true if the attribute need NOT be overridden.
     */
    public static void mergeElement(Element fromElement, Element toElement, boolean checkHasAttributeInTarget) {
		if ( fromElement == null )
			return;
		mergeAttributes(fromElement, toElement, checkHasAttributeInTarget);
		for (Iterator<Element> i = getChildren(fromElement); i.hasNext() ; ) {
			Element schild = i.next();
			Element dchild = getChildElement(toElement, schild.getTagName());
			if ( dchild != null ) {
				mergeElement(schild,dchild,checkHasAttributeInTarget);
			} else {
			    importElement(toElement, schild);
			}
		}
	}

	/**
     * This method takes care of merging the attributes in the fromElement (source Element) 
     * to the attributes in the toElement (target Element). 
     * The new attributes of fromElement(source element) is also added into the toElement 
     * (target Element). If the attribute already exists based on checkHasAttributeInTarget 
     * the attribute is overridden to the attribute value in fromElement (source Element). 
     * If checkHasAttributeInTarget is true the attribute is not overridden otherwise 
     * it is overriden.
     * 
     * @param fromEle is the source element
     * @param toEle is the target element
     * @param checkHasAttributeInTarget is true if the attribute need NOT be overridden.
	 */
	public static void mergeAttributes(Element fromEle, Element toEle, boolean checkHasAttributeInTarget) {
		if (fromEle != null && toEle != null) {
			NamedNodeMap map = fromEle.getAttributes();
			for (int i=0; i<map.getLength(); i++) {
			    if(checkHasAttributeInTarget) {
				    boolean hasAttribute = toEle.hasAttribute(map.item(i).getNodeName());
				    if(!hasAttribute) {
				        toEle.setAttribute(map.item(i).getNodeName(), map.item(i).getNodeValue());
				    }
			    } else {
			        toEle.setAttribute(map.item(i).getNodeName(), map.item(i).getNodeValue());
			    }
			}
		}
	}

	/**
	 * Utility to get a list of Element objects by evaluating expression starting from the context of the input element.
	 * 
	 * 
	 * @param element
	 * 			The parent element
	 * @param expression
	 * 			A string expression that gives the target element path from the context of the parent element
	 * @return List of child elements after evaluating the expression
	 */
	public static ArrayList<Element> getElements(Element element, String expression) {
		if (BDACommon.isVoid(expression)) {
			return null;
		}
		ArrayList<Element> list = null;
		int stindex = 0;
		expression = (stindex = expression.indexOf(':')) > -1 ? expression.substring(stindex) : expression;
		String[] expr = expression.split("/");
		int len = expr.length;
		for (int j = 0; j < len; j++) {
			if (BDACommon.isVoid(expr[j])) {
				continue;
			}
			if (element == null) {
				return list;
			}
			if (j < len - 1) {
				element = getChildElement(element, expr[j]);
			} else {
				list = getChildren(element, expr[j]);
			}
		}
		return list;
	}

	/**
	 * Utility to Get list of Element object by evaluating element expression, attribute name and 
	 * attribute value starting from the context of the input element.
	 * 
	 * 
	 * @param element
	 * 			The parent element
	 * @param expression
	 * 			A string expression that gives the target element path from the context of the parent element
	 * @param attrName
	 * 			The name of the attribute that must be present in the target element
	 * @param value
	 * 			The value of the attribute
	 * @return List of child elements after evaluating the expression and which contain the attribute and value
	 * 
	 */
	public static ArrayList<Element> getElementsByAttribute(Element element, String expression, String attrName, String value) {
		ArrayList<Element> list = getElements(element, expression);
		if (list != null) {
			for (ListIterator<Element> listIter = list.listIterator(); listIter.hasNext();) {
				Element elem = listIter.next();
				if (elem.hasAttribute(attrName)) {
					String attrValue = elem.getAttribute(attrName);
					if (!BDACommon.equals(attrValue, value)) {
						listIter.remove();
					}
				} else {
					listIter.remove();
				}
			}
		}
		return list;
	}
	
	/**
	 * Utility to Get a Element object by evaluating element expression, attribute name and 
	 * attribute value starting from the context of the input element.
	 * 
	 * 
	 * @param element
	 * 			The parent element
	 * @param expression
	 * 			A string expression that gives the target element path from the context of the parent element
	 * @param attrName
	 * 			The name of the attribute that must be present in the target element
	 * @param value
	 * 			The value of the attribute
	 * @return List of child elements after evaluating the expression and which contain the attribute and value
	 * 
	 */
	public static Element getElementByAttribute(Element element, String expression, String attrName, String value) {
		ArrayList<Element> list = getElements(element, expression);
		if (list != null) {
			for (ListIterator<Element> listIter = list.listIterator(); listIter.hasNext();) {
				Element elem = listIter.next();
				if (elem.hasAttribute(attrName)) {
					String attrValue = elem.getAttribute(attrName);
					if (BDACommon.equals(attrValue, value)) {
						return elem;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get the list of all children of Element type. <br> <b> Note: </b> <br> Only immediate children of the element
	 * are considered
	 * 
	 * @param parent
	 *            The parent whose child elements are required
	 * @return An list of all child elements
	 */

	public static List<Element> getChildrenList(Element parent) {
		List<Element> children = new ArrayList<Element>();
		if (parent != null) {
			NodeList list = parent.getChildNodes();
			if (list != null) {
				for (int i = 0; i < list.getLength(); i++) {
					Node n = list.item(i);
					if (n instanceof Element) {
						children.add((Element) n);
					}
				}
			}
		}
		return children;
	}

	/**
	 * Gets the child element by given attribute name and corresponding there value. If not found returns null. This
	 * method returns null if either parent is null attribute no exists.
	 * 
	 * @param parent
	 *            The parent element
	 * @param attributeName
	 *            The attribute name of the child to be fetched
	 * @param attributeValue
	 *            The attribute Value of the child to be fetched
	 * @return The child element
	 */

	public static Element getChildElementBy(Element parent, String attributeName, String attributeValue) {
		Iterator<Element> children = getChildren(parent);
		while (children.hasNext()) {
			Element child = (Element) children.next();
			if (attributeValue.equals(child.getAttribute(attributeName)))
				return child;
		}
		return null;
	}

	/**
	 * 
	 * Utility method to remove a node from the parent node.
	 * 
	 * @param node the node to be remove from DOM heirarchy.
	 */
	public static void removeNode(Node node) {
		Node parentNode = node.getParentNode();
		if (parentNode != null) {
			parentNode.removeChild(node);
		}
	}



		/**
  	      * @param fromElement is the source element
  	      * @param toElement is the target element
  	      * @param checkHasAttributeInTarget is true if the attribute need NOT be overridden.
  	      * @param dbMap Contains, Key is the repeating element node name and Value is the unique key attribute name of that node name under toElement.
  	      */
  	     //236492
  	     public static void mergeElement(Element fromElement, Element toElement,boolean checkHasAttributeInTarget, Map <String, String> dbMap) {
  	                 if (fromElement == null)
  	                         return;
  	                 mergeAttributes(fromElement, toElement, checkHasAttributeInTarget);
  	                 for (Iterator<Element> i = getChildren(fromElement); i.hasNext();) {
  	                         Element schild = i.next();
  	                         Element dchild = getChildElementByAttributeAndElement(toElement,schild,dbMap);
  	                         if (dchild != null) {
  	                                 mergeElement(schild, dchild, checkHasAttributeInTarget, dbMap);
  	                         } else {
  	                                 importElement(toElement, schild);
  	                         }
  	                 }
  	         }
  	 
  	         private static Element getChildElementByAttributeAndElement(
  	                 Element parentEle, Element schild, Map<String, String> dbMap) {
  	                 String childName = schild.getTagName();
  	                 if (schild.hasAttributes() && dbMap.containsKey(childName)) {
  	                         String attrName = dbMap.get(childName);
  	                         String attrValue = schild.getAttribute(attrName);
  	                         if (parentEle != null && !BDACommon.isVoid(childName)) {
  	                                 for (Node n = parentEle.getFirstChild(); n != null; n = n.getNextSibling()) {
  	                                         if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(childName)) {
  	                                                 if (hasSameAttribute((Element) n, attrName, attrValue)) {
  	                                                         return (Element) n;
  	                                                 }
  	                                         }
  	                                 }
  	                         }
  	                         return null;
  	                 } else {
  	                         return getChildElement(parentEle, childName);
  	                 }
  	         }
  	 
  	         private static boolean hasSameAttribute(Element dchild, String attrName, String attrValue) {
  	                 if (dchild.hasAttributes()) {
  	                         if (attrValue.equals(dchild.getAttribute(attrName))){
  	                                 return true;
  	                         }
  	                 }
  	                 return false;
  	         }
	
	
	
	public static Document getCopyWithComment(Document document, String commentString){
		if (BDACommon.isVoid(document))
			return null;

		DocumentBuilder docBuild = null;
		Document copy = null;
		try {
			docBuild = getDocumentBuilder();
			copy = docBuild.newDocument();
			copy.setStrictErrorChecking(false);
			Comment comment = copy.createComment(commentString);
			copy.appendChild(comment);
			copy.appendChild(copy.importNode(document.getDocumentElement(), true));
//			copy.insertBefore(comment,copy.getDocumentElement());
			return copy;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} finally {
			releaseDocumentBuilder(docBuild);
		}
		return null;
		
	}
	
	public static void main(String[] args) {
//		Document doc = createFromString("<a a='b'><b c='z'><m /></b></a>");
//		System.out.println(getString(doc));
//		System.out.println(getString(getXpathElement(doc.getDocumentElement(), "b")));
//		System.out.println(getString((Element) getXpathNodes(doc.getDocumentElement(), "//m").item(0)));
	}
}
