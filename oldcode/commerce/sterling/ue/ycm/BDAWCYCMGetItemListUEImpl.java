// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WCYCMGetItemListUEImpl.java

package com.ibm.commerce.sterling.ue.ycm;

import com.ibm.commerce.catalog.facade.client.CatalogNavigationViewFacadeClient;
import com.ibm.commerce.catalog.facade.datatypes.*;
import com.ibm.commerce.foundation.client.facade.bod.AbstractBusinessObjectDocumentFacadeClient;
import com.ibm.commerce.foundation.client.util.oagis.RelationalExpression;
import com.ibm.commerce.foundation.client.util.oagis.SelectionCriteriaHelper;
import com.ibm.commerce.foundation.common.datatypes.*;
import com.ibm.commerce.foundation.common.util.logging.LoggingHelper;
import com.ibm.commerce.oagis9.datatypes.GetType;
import com.ibm.commerce.sterling.ue.BDAAbstractSterlingUserExitImpl;
import com.yantra.ycm.japi.ue.YCMGetItemListUE;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

import java.math.BigInteger;
import java.util.*;
import java.util.logging.Logger;

import javax.security.auth.callback.CallbackHandler;
import javax.xml.parsers.*;

import org.w3c.dom.*;

public class BDAWCYCMGetItemListUEImpl extends BDAAbstractSterlingUserExitImpl
    implements YCMGetItemListUE
{

    public BDAWCYCMGetItemListUEImpl()
    {
    }

    private static YFCLogCategory logger = YFCLogCategory.instance(BDAWCYCMGetItemListUEImpl.class);
    public Document getItemList(YFSEnvironment environment, Document inputDocument)
        throws YFSUserExitException
    {
    	YFCDocument dInput = YFCDocument.getDocumentFor(inputDocument);
        logger.debug("getItemList(YFSEnvironment, Document)::Input::" + dInput);
        initClientProperties(environment);
        Document getItemDocument = null;
        YFCElement itemElement = dInput.getDocumentElement();
        String organizationCode = itemElement.getAttribute("CallingOrganizationCode");
        String storeId = getStoreId(organizationCode);
        if(storeId != null && !storeId.isEmpty())
        {
            BusinessContextType businessContext = createBusinessContext(organizationCode);
            CallbackHandler callbackHandler = createCallbackHandler();
            SelectionCriteriaHelper selectionCriteriaHelper = null;
            
            
            List componentsItemList = parseSCGetItemsXMLDocument(dInput);
            CatalogNavigationViewFacadeClient catNavFacadeClient = new CatalogNavigationViewFacadeClient(businessContext, callbackHandler);
            selectionCriteriaHelper = new SelectionCriteriaHelper("/CatalogNavigationView");
            selectionCriteriaHelper.addAccessProfile("IBM_Store_CatalogEntrySearch");
            selectionCriteriaHelper.addNameValuePair(new RelationalExpression("_wcf.search.profile", "IBM_findComponentsSummary", "="));
            String catEntryExpression = createSearchIndexComponentsExpression(componentsItemList);
            selectionCriteriaHelper.addNameValuePair(new RelationalExpression("_wcf.search.expr", catEntryExpression, "="));
            String catalogId = getCatalogId(organizationCode);
            if(catalogId != null && !catalogId.isEmpty())
                selectionCriteriaHelper.addNameValuePair(new RelationalExpression("_wcf.search.filter.expr", (new StringBuilder("catalog_id:")).append(catalogId).toString(), "="));
            GetType getVerb = AbstractBusinessObjectDocumentFacadeClient.createGetVerb(selectionCriteriaHelper.getSelectionCriteriaExpression());
            getVerb.setMaxItems(BigInteger.valueOf(componentsItemList.size()));
            GetCatalogNavigationViewType get = CatalogFactory.eINSTANCE.createGetCatalogNavigationViewType();
            GetCatalogNavigationViewDataAreaType dataArea = CatalogFactory.eINSTANCE.createGetCatalogNavigationViewDataAreaType();
            dataArea.setGet(getVerb);
            get.setDataArea(dataArea);
            ShowCatalogNavigationViewType show = catNavFacadeClient.getCatalogNavigationView(get);
            List searchIndexResults = extractContentFromCatalogNavigationViewNoun(show.getDataArea());
            logger.debug(new StringBuilder("SearchIndexResults is: ").append(searchIndexResults).toString());
            getItemDocument = consumeWCResponseToSCDocument(searchIndexResults, organizationCode);
        } else {
        	logger.debug("Not a WC - Configurator request as a storeId is not specified.  Performing no operations");
        	logger.debug(convertDocumentToString(getItemDocument));
        }
        return getItemDocument;
    }

    protected List parseSCGetItemsXMLDocument(YFCDocument xmlDocument)
    {
        String METHODNAME = "parseSCGetItemsXMLDocument(Document)";
        logger.debug(METHODNAME);
        List catentryIDComponentList = new ArrayList();
        if(xmlDocument != null)
        {
            Element root = xmlDocument.getDocument().getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            nodeList = root.getElementsByTagName("Exp");
            logger.debug(new StringBuilder("Number of components: ").append(nodeList.getLength() - 1).toString());
            for(int s = 0; s < nodeList.getLength(); s++)
            {
                Node node = nodeList.item(s);
                if(node.getNodeType() == 1)
                {
                    Element elmnt = (Element)node;
                    String name = elmnt.getAttribute("Name");
                    String value = elmnt.getAttribute("Value");
                    if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
                        LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "parseSCGetItemsXMLDocument(Document)", (new StringBuilder("Component: # ")).append(s).append(" Name: ").append(name).append(" Value: ").append(value).toString());
                    catentryIDComponentList.add(value.trim());
                }
            }

        }
        return catentryIDComponentList;
    }

    protected static Document consumeWCResponseToSCDocument(List responseWC, String organizationCode)
    {
        String METHODNAME = "consumeWCResponseToSCDocument(List<CatalogEntryViewType>)";
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
        {
            Object param[] = {
                responseWC
            };
            LOGGER.entering(CLASSNAME, "consumeWCResponseToSCDocument(List<CatalogEntryViewType>)", param);
        }
        DocumentBuilderFactory docBldFactory = DocumentBuilderFactory.newInstance();
        Document documentSCResponse = null;
        try
        {
            DocumentBuilder parser = docBldFactory.newDocumentBuilder();
            documentSCResponse = parser.newDocument();
            Node root = documentSCResponse.createElement("ItemList");
            if(responseWC != null && responseWC.size() > 0)
            {
                Element itemElmt;
                for(Iterator iterator = responseWC.iterator(); iterator.hasNext(); root.appendChild(itemElmt))
                {
                    CatalogEntryViewType aCatalogEntryViewType = (CatalogEntryViewType)iterator.next();
                    itemElmt = documentSCResponse.createElement("Item");
                    itemElmt.setAttribute("ItemID", aCatalogEntryViewType.getPartNumber());
                    itemElmt.setAttribute("OrganizationCode", organizationCode);
                    itemElmt.setAttribute("UnitOfMeasure", "EACH");
                    Element primaryInfoElemt = documentSCResponse.createElement("PrimaryInformation");
                    primaryInfoElemt.setAttribute("ShortDescription", aCatalogEntryViewType.getName());
                    primaryInfoElemt.setAttribute("ExtendedDescription", aCatalogEntryViewType.getLongDescription());
                    primaryInfoElemt.setAttribute("Description", aCatalogEntryViewType.getShortDescription());
                    primaryInfoElemt.setAttribute("IsModelItem", "N");
                    itemElmt.appendChild(primaryInfoElemt);
                }

            }
            documentSCResponse.appendChild(root);
        }
        catch(ParserConfigurationException e1)
        {
            e1.printStackTrace();
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
        {
            Object param[] = {
                convertDocumentToString(documentSCResponse)
            };
            LOGGER.exiting(CLASSNAME, "consumeWCResponseToSCDocument(List<CatalogEntryViewType>)", ((Object) (param)));
        }
        return documentSCResponse;
    }

    protected static Document consumeWCCatalogEntryResponseToSCDocument(List responseWC, String organizationCode)
    {
        String METHODNAME = "consumeWCResponseToSCDocument(List<CatalogEntryViewType>)";
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
        {
            Object param[] = {
                responseWC
            };
            LOGGER.entering(CLASSNAME, "consumeWCResponseToSCDocument(List<CatalogEntryViewType>)", param);
        }
        DocumentBuilderFactory docBldFactory = DocumentBuilderFactory.newInstance();
        Document documentSCResponse = null;
        try
        {
            DocumentBuilder parser = docBldFactory.newDocumentBuilder();
            documentSCResponse = parser.newDocument();
            Node root = documentSCResponse.createElement("ItemList");
            if(responseWC != null && responseWC.size() > 0)
            {
                Element itemElmt;
                for(Iterator iter = responseWC.iterator(); iter.hasNext(); root.appendChild(itemElmt))
                {
                    CatalogEntryType catalogEntry = (CatalogEntryType)iter.next();
                    CatalogDescriptionType description = (CatalogDescriptionType)catalogEntry.getDescription().get(0);
                    itemElmt = documentSCResponse.createElement("Item");
                    itemElmt.setAttribute("ItemID", catalogEntry.getCatalogEntryIdentifier().getExternalIdentifier().getPartNumber());
                    itemElmt.setAttribute("OrganizationCode", organizationCode);
                    itemElmt.setAttribute("UnitOfMeasure", "EACH");
                    Element primaryInfoElemt = documentSCResponse.createElement("PrimaryInformation");
                    primaryInfoElemt.setAttribute("ShortDescription", description.getName());
                    primaryInfoElemt.setAttribute("Description", description.getShortDescription());
                    primaryInfoElemt.setAttribute("ExtendedDescription", description.getLongDescription());
                    itemElmt.appendChild(primaryInfoElemt);
                }

            }
            documentSCResponse.appendChild(root);
        }
        catch(ParserConfigurationException e1)
        {
            e1.printStackTrace();
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
        {
            Object param[] = {
                convertDocumentToString(documentSCResponse)
            };
            LOGGER.exiting(CLASSNAME, "consumeWCResponseToSCDocument(List<CatalogEntryViewType>)", ((Object) (param)));
        }
        return documentSCResponse;
    }

    private List extractContentFromCatalogNavigationViewNoun(ShowCatalogNavigationViewDataAreaType aDataArea)
    {
        String METHODNAME = "extractContentFromCatalogNavigationViewNoun";
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "extractContentFromCatalogNavigationViewNoun");
        List catalogEntryViewTypeList = null;
        if(aDataArea != null)
        {
            List navigationViewTypeList = aDataArea.getCatalogNavigationView();
            if(navigationViewTypeList != null && navigationViewTypeList.size() > 0)
            {
                CatalogNavigationViewType aNavigationViewType = (CatalogNavigationViewType)navigationViewTypeList.get(0);
                catalogEntryViewTypeList = aNavigationViewType.getCatalogEntryView();
                if(catalogEntryViewTypeList != null)
                {
                    if(LoggingHelper.isTraceEnabled(LOGGER))
                        LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "extractContentFromCatalogNavigationViewNoun", "Search Index returned {0} components. ", Integer.valueOf(catalogEntryViewTypeList.size()));
                } else
                if(LoggingHelper.isTraceEnabled(LOGGER))
                {
                    LOGGER.logp(LoggingHelper.DEFAULT_TRACE_LOG_LEVEL, CLASSNAME, "extractContentFromCatalogNavigationViewNoun", "Search Index returned no components. ");
                    catalogEntryViewTypeList = new ArrayList(0);
                }
            }
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "extractContentFromCatalogNavigationViewNoun", catalogEntryViewTypeList);
        return catalogEntryViewTypeList;
    }

    private String createSearchIndexComponentsExpression(List componentsEntryIdList)
    {
        String METHODNAME = "createSearchIndexComponentsExpression";
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.entering(CLASSNAME, "createSearchIndexComponentsExpression");
        StringBuilder expression = new StringBuilder();
        String seperator = " ";
        String quote = "\"";
        if(componentsEntryIdList != null && !componentsEntryIdList.isEmpty())
        {
            expression.append("partNumber_ntk");
            expression.append(":");
            if(componentsEntryIdList.size() == 1)
            {
                expression.append(quote);
                expression.append(((String)componentsEntryIdList.get(0)).toString());
                expression.append(quote);
            } else
            {
                expression.append("(");
                expression.append(quote);
                expression.append(((String)componentsEntryIdList.get(0)).toString());
                expression.append(quote);
                for(int i = 1; i < componentsEntryIdList.size(); i++)
                {
                    expression.append(seperator);
                    expression.append(quote);
                    expression.append((String)componentsEntryIdList.get(i));
                    expression.append(quote);
                }

                expression.append(")");
            }
        }
        if(LoggingHelper.isEntryExitTraceEnabled(LOGGER))
            LOGGER.exiting(CLASSNAME, "createSearchIndexComponentsExpression", expression.toString());
        return expression.toString();
    }

    private static final String CLASSNAME = com.ibm.commerce.sterling.ue.ycm.BDAWCYCMGetItemListUEImpl.class.getName();
    private static final Logger LOGGER = LoggingHelper.getLogger(com.ibm.commerce.sterling.ue.ycm.BDAWCYCMGetItemListUEImpl.class);

}
