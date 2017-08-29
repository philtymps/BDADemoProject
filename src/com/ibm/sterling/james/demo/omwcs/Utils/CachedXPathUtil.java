package com.ibm.sterling.james.demo.omwcs.Utils;

import javax.xml.transform.TransformerException;

import org.apache.xpath.CachedXPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;


/**
 * 
 * This class contains utility methods for XPATH operations.
 */
public class CachedXPathUtil {

    private CachedXPathAPI xpathApi = new CachedXPathAPI();

    public CachedXPathUtil() {
    }

    /**
     * Evaluates the given Xpath and returns the corresponding node.
     * 
     * @param node document context.
     * @param xpathExp xpath that has to be evaluated.
     * @return Node if found
     * @throws TransformerException
     * 
     */
    public Node getNode(Node node, String xpathExp) throws TransformerException {

        if (null == node || null == xpathExp || 0 == xpathExp.trim().length()) {
            return null;
        }

        return xpathApi.selectSingleNode(node, xpathExp);
    }

    /**
     * Evaluates the given Xpath and returns the corresponding node list.
     * 
     * @param node document context
     * @param xpathExp xpath to be evaluated
     * @return nodelist
     * @throws TransformerException
     * 
     */
    public NodeList getNodeList(Node node, String xpathExp) throws TransformerException {

        if (null == node || null == xpathExp || 0 == xpathExp.trim().length()) {
            return null;
        }

        return xpathApi.selectNodeList(node, xpathExp);
    }

    /**
     * Evaluates the given Xpath and returns the corresponding node iterator.
     * 
     * @param node document context
     * @param xpathExp xpath to be evaluated
     * @return nodelist
     * @throws TransformerException
     * 
     */
    public NodeIterator getNodeIterator(Node node, String xpathExp) throws TransformerException {

        if (null == node || null == xpathExp || 0 == xpathExp.trim().length()) {
            return null;
        }

        return xpathApi.selectNodeIterator(node, xpathExp);
    }

    /**
     * Evaluates the given Xpath and returns the value of the corresponding
     * node.
     * 
     * @param node document context
     * @param xpathExp xpath to be evaluated
     * @return string
     * @throws TransformerException
     * 
     */
    public String getValue(Node node, String xpathExp) throws TransformerException {
        Node ret = xpathApi.selectSingleNode(node, xpathExp);
        return ret == null ? "" : ret.getNodeValue();
    }

    /**
     * Evaluates the given Xpath and returns the value of the corresponding
     * node.
     * 
     * @param node document context
     * @param xpathExp xpath to be evaluated
     * @param defValue Default Value if xpath expression not found.
     * @return string
     * @throws TransformerException
     * 
     */
    public String getValue(Node node, String xpathExp, String defValue) throws TransformerException {
        if (null == node || null == xpathExp || 0 == xpathExp.trim().length()) {
            return null;
        }

        Node ret = null;
        ret = xpathApi.selectSingleNode(node, xpathExp);
        return ret == null ? defValue : ret.getNodeValue();
    }

    /**
     * setAttributeByXpath method sets the attibute value passed in the node
     * given.
     * 
     * @param node document context
     * @param xpathExp xpath to be evaluated
     * @param val Attribute value
     * @throws TransformerException
     * 
     */
    public void setAttributeByXpath(Node node, String xpathExp, String val) throws TransformerException {
        NodeList nl = getNodeList(node, xpathExp);
        for (int i = 0; i < nl.getLength(); i++) {
            nl.item(i).setNodeValue(val);
        }
        if (nl.getLength() == 0) {
            String attribute = xpathExp.substring(xpathExp.indexOf("@") + 1);
            Node attributeNode = null;
            if (!xpathExp.startsWith("@")) {
                attributeNode = getNode(node, xpathExp.substring(0, xpathExp.indexOf("@") - 1));
            }
            else {
                attributeNode = node;
            }
            if (attributeNode != null) {
                Element element = (Element) attributeNode;
                element.setAttribute(attribute, val);
            }
        }
    }

    /**
     * importNode method copies the source node into target Node.
     * 
     * @param targetNode Target Node context
     * @param srcNode Source Node context
     * @param deep
     * 
     * @throws TransformerException
     * 
     */
    public void importNode(Node targetNode, Node srcNode, boolean deep) throws Exception {
        if (null == srcNode) {
            throw new IllegalArgumentException("Source Node is null");
        }
        if (null == targetNode) {
            throw new IllegalArgumentException("Target Node is null");
        }
        if (!(targetNode instanceof Element)) {
            throw new IllegalArgumentException("Target Node is not an Element");
        }

        Document targetDoc = targetNode.getOwnerDocument();
        Node newNd = targetDoc.importNode(srcNode, deep);

        ((Element) targetNode).appendChild(newNd);
    }

}

