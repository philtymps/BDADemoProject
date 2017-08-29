/* ******************************************************************************
IBM Confidential
OCO Source Materials
IBM Sterling Selling and Fulfillment Suite
(C) Copyright IBM Corp. 2005, 2013
The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
**********************************************************************************/
package com.extension.bda.transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.w3c.dom.Document;

import com.sterlingcommerce.woodstock.util.frame.colony.ColonyUtil;
import com.yantra.shared.dbclasses.YFS_Order_HeaderDBHome;
import com.yantra.shared.dbclasses.YFS_Order_Header_HDBHome;
import com.yantra.shared.dbclasses.YFS_ShipmentDBHome;
import com.yantra.shared.dbclasses.YFS_Shipment_HDBHome;
import com.yantra.shared.dbi.YFS_Document_Params;
import com.yantra.shared.ycp.YCPFactory;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.shared.ysc.util.YSCExecuteSqlUtil;
import com.yantra.shared.ysc.util.YSCMultiSchemaHelper;
import com.yantra.ycp.core.YCPTemplateManager;
import com.yantra.yfc.date.YDate;
import com.yantra.yfc.dblayer.YFCDBException;
import com.yantra.yfc.dblayer.YFCEntityDBHome;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.core.YFSDatabaseTypes;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.util.YFSAPILiterals;
import com.yantra.yfs.util.YFSConsts;
import com.yantra.yfs.util.YFSErrors;

public class BDAGetTransactionSummary implements YFSAPILiterals, YFSErrors, YFSConsts,YFSDatabaseTypes{

    private static YFCLogCategory cat = YFCLogCategory.instance(BDAGetTransactionSummary.class);
	private HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>> oEnterpriseMap= new HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>>();
	private boolean bOrdersRequired=false;
	private boolean bOrderLinesRequired=false;
	private boolean bShipmentsRequired=false;
	private boolean bYTDOrdersRequired=false;
	private boolean bYTDOrderLinesRequired=false;
	private boolean bYTDShipmentsRequired=false;
	String pkPrefix = "";
	String pkPrefix2 = "";

	  
    public Document getTransactionSummary(YFSEnvironment oEnv,Document inputDoc) 
    {
    
    	YFCDocument inDoc = YFCDocument.getDocumentFor(inputDoc);
    	
    	YDate dStart = inDoc.getDocumentElement().getYDateAttribute("StartDate");
		validateInput(oEnv,dStart);
		
		int iYear = dStart.getFourDigitYear();
		String sYear = Integer.toString(iYear);
		if(ColonyUtil.isEnabled()){
			pkPrefix = YSCMultiSchemaHelper.getPrimaryKeyPrefix((YFSContext)oEnv) + sYear.substring(2, 4);
		}else{
			pkPrefix = sYear;
		}
		
		int iMonth = dStart.getMonth() + 1;
		YDate dEnd = YDate.newMutableDate(dStart.getDate());
		if (iMonth == 1 && dStart.getDate() == 1){
			pkPrefix2 = pkPrefix;
		} else {
			String sYear2 = Integer.toString(iYear + 1);
			if(ColonyUtil.isEnabled()){
				pkPrefix2 = YSCMultiSchemaHelper.getPrimaryKeyPrefix((YFSContext)oEnv) + sYear2.substring(2, 4);
			}else{
				pkPrefix2 = sYear2;
			}
		}
		oEnterpriseMap= new HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>>();
		String sApiName="getTransactionSummary";
		YFCDocument outDoc=YFCDocument.createDocument("getTransactionSummary");
		YFCElement rootNode=outDoc.getDocumentElement();
		rootNode.setAttribute("Year",sYear);
		

        YFCDocument sInTemplateXML = YCPTemplateManager.getInstance().getAPITemplate((YFSContext)oEnv, sApiName,null,null);
        if(sInTemplateXML == null)
            throw new YFCException(YFS_TEMPLATE_NOT_FOUND,sApiName);
        YFCElement templateRootNode = sInTemplateXML.getDocumentElement();

		YFCElement eTempTranSumLst= templateRootNode.getChildElement("TransactionSummaryList");
		if (eTempTranSumLst !=null) {
			YFCElement eTranSumLst=outDoc.createElement("TransactionSummaryList");
			rootNode.appendChild(eTranSumLst);
			appendTransactionSummary(oEnv,eTranSumLst,eTempTranSumLst, dStart);
	
		}

		return outDoc.getDocument();

    }

	private void validateInput(YFSEnvironment oEnv,YDate startDate)
	{
		if (YFCCommon.isVoid(startDate)) {
            YFCException ex = new YFCException(YFSErrors.YFS_MISSING_MANDATORY_PARAMETERS);
            ex.setAttribute("StartDate", "");
			throw ex;
		}
	}

	private void appendTransactionSummary(YFSEnvironment oEnv,YFCElement eOut, YFCElement eTemplate, YDate dStart)
	{
		YFCElement eTempTranSum = eTemplate.getChildElement("TransactionSummary");
		if (eTempTranSum!=null) {
			YFCElement eTempMonthlySummaryList=eTempTranSum.getChildElement("MonthlySummaryList");
			if (eTempMonthlySummaryList!=null) {
				YFCElement eTempMonthlySummary=eTempMonthlySummaryList.getChildElement("MonthlySummary");
				if (eTempMonthlySummary != null) {
					if (eTempMonthlySummary.hasAttribute("NoOfOrdersCreated")) {
						bOrdersRequired=true;
						buildMonthlyOrderCollections(oEnv,dStart);
					}
					if (eTempMonthlySummary.hasAttribute("NoOfOrderLinesCreated")) {
						bOrderLinesRequired=true;
						buildMonthlyOrderLineCollections(oEnv,dStart);
					}
					if (eTempMonthlySummary.hasAttribute("NoOfShipmentsCreated")) {
						bShipmentsRequired=true;
						buildMonthlyShipmentCollections(oEnv,dStart);
					}
	
				}
			}
			YFCElement eTempYTDSummary=eTempTranSum.getChildElement("YTDSummary");
			if (eTempYTDSummary!=null) {
				if (eTempYTDSummary.hasAttribute("YTDNoOfOrdersCreated")) {
					bYTDOrdersRequired=true;
					if (!bOrdersRequired) //else compute the YTD from monthly
						buildMonthlyOrderCollections(oEnv,dStart);
				}
				if (eTempYTDSummary.hasAttribute("YTDNoOfOrderLinesCreated")) {
					bYTDOrderLinesRequired=true;
					if (!bOrderLinesRequired) //else compute the YTD from monthly
						buildMonthlyOrderLineCollections(oEnv,dStart);
				}
				if (eTempYTDSummary.hasAttribute("YTDNoOfShipmentsCreated")) {
					bYTDShipmentsRequired=true;
					if (!bShipmentsRequired) //else compute the YTD from monthly
						buildMonthlyShipmentCollections(oEnv,dStart);
				}
	
			}
			buildXML(oEnv,eOut, dStart);

		}
	}

	private void buildMonthlyOrderCollections(YFSEnvironment oEnv, YDate dStart)
	{

		String sSubstr="substr";
		if (((YFSContext)oEnv).getDBType(YFS_Order_HeaderDBHome.getInstance()) == SQL_SERVER) {

			sSubstr = "substring";
		}
		
	
		String sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_header_key, 5,2) MON," + sSubstr + "(order_header_key, 7,2) DA from YFS_ORDER_HEADER where order_header_key like '" +  pkPrefix + "%' group by ENTERPRISE_KEY, DOCUMENT_TYPE, " + sSubstr+ "(order_header_key, 5,2)," + sSubstr + "(order_header_key, 7,2)";
		buildCollections(oEnv, sSql, YFS_Order_HeaderDBHome.getInstance(), "ORDER", Integer.toString(dStart.getFourDigitYear()), dStart);

		sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_header_key, 5,2) MON," + sSubstr + "(order_header_key, 7,2) DA from YFS_ORDER_HEADER_H where order_header_key like '" +  pkPrefix + "%' group by ENTERPRISE_KEY, DOCUMENT_TYPE, "+sSubstr+"(order_header_key, 5,2)," + sSubstr + "(order_header_key, 7,2)";
		buildCollections(oEnv, sSql, YFS_Order_Header_HDBHome.getInstance(), "ORDER", Integer.toString(dStart.getFourDigitYear()), dStart);
		if(!pkPrefix.equals(pkPrefix2)){
			sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_header_key, 5,2) MON," + sSubstr + "(order_header_key, 7,2) DA from YFS_ORDER_HEADER where order_header_key like '" +  pkPrefix2 + "%' group by ENTERPRISE_KEY, DOCUMENT_TYPE, " + sSubstr+ "(order_header_key, 5,2)," + sSubstr + "(order_header_key, 7,2)";
			buildCollections(oEnv, sSql, YFS_Order_HeaderDBHome.getInstance(), "ORDER", Integer.toString(dStart.getFourDigitYear()+1), dStart);

			sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_header_key, 5,2) MON," + sSubstr + "(order_header_key, 7,2) DA from YFS_ORDER_HEADER_H where order_header_key like '" +  pkPrefix2 + "%' group by ENTERPRISE_KEY, DOCUMENT_TYPE, "+sSubstr+"(order_header_key, 5,2)," + sSubstr + "(order_header_key, 7,2)";
			buildCollections(oEnv, sSql, YFS_Order_Header_HDBHome.getInstance(), "ORDER", Integer.toString(dStart.getFourDigitYear()+1), dStart);
		
		}
	}
	private void buildMonthlyOrderLineCollections(YFSEnvironment oEnv, YDate dStart)
	{

		String sSubstr="substr";
		if (((YFSContext)oEnv).getDBType(YFS_Order_HeaderDBHome.getInstance()) == SQL_SERVER) {

			sSubstr = "substring";
		}
		String sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_line_key, 5,2) MON," + sSubstr + "(order_line_key, 7,2) DA from YFS_ORDER_HEADER OH, YFS_ORDER_LINE OL where order_line_key like '" +  pkPrefix + "%' AND OH.ORDER_HEADER_KEY = OL.ORDER_HEADER_KEY group by ENTERPRISE_KEY, DOCUMENT_TYPE, "+sSubstr+"(order_line_key, 5,2)," + sSubstr + "(order_line_key, 7,2)";
		buildCollections(oEnv, sSql, YFS_Order_HeaderDBHome.getInstance(), "ORDERLINE", Integer.toString(dStart.getFourDigitYear()), dStart);

		sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_line_key, 5,2) MON," + sSubstr + "(order_line_key, 7,2) DA from YFS_ORDER_HEADER_H OH, YFS_ORDER_LINE_H OL where order_line_key like '" +  pkPrefix + "%' AND OH.ORDER_HEADER_KEY = OL.ORDER_HEADER_KEY group by ENTERPRISE_KEY, DOCUMENT_TYPE, "+sSubstr+"(order_line_key, 5,2)," + sSubstr + "(order_line_key, 7,2)";
		buildCollections(oEnv, sSql, YFS_Order_Header_HDBHome.getInstance(), "ORDERLINE", Integer.toString(dStart.getFourDigitYear()), dStart);
		if(!pkPrefix.equals(pkPrefix2)){
			sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_line_key, 5,2) MON," + sSubstr + "(order_line_key, 7,2) DA from YFS_ORDER_HEADER OH, YFS_ORDER_LINE OL where order_line_key like '" +  pkPrefix2 + "%' AND OH.ORDER_HEADER_KEY = OL.ORDER_HEADER_KEY group by ENTERPRISE_KEY, DOCUMENT_TYPE, "+sSubstr+"(order_line_key, 5,2)," + sSubstr + "(order_line_key, 7,2)";
			buildCollections(oEnv, sSql, YFS_Order_HeaderDBHome.getInstance(), "ORDERLINE", Integer.toString(dStart.getFourDigitYear()+1), dStart);

			sSql="Select count(*) COUNTS, ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(order_line_key, 5,2) MON," + sSubstr + "(order_line_key, 7,2) DA from YFS_ORDER_HEADER_H OH, YFS_ORDER_LINE_H OL where order_line_key like '" +  pkPrefix2 + "%' AND OH.ORDER_HEADER_KEY = OL.ORDER_HEADER_KEY group by ENTERPRISE_KEY, DOCUMENT_TYPE, "+sSubstr+"(order_line_key, 5,2)," + sSubstr + "(order_line_key, 7,2)";
			buildCollections(oEnv, sSql, YFS_Order_Header_HDBHome.getInstance(), "ORDERLINE", Integer.toString(dStart.getFourDigitYear()+1), dStart);
			
		}
	}
	private void buildMonthlyShipmentCollections(YFSEnvironment oEnv, YDate dStart)
	{

		String sSubstr="substr";
		if (((YFSContext)oEnv).getDBType(YFS_ShipmentDBHome.getInstance()) == SQL_SERVER) {

			sSubstr = "substring";
		}
		String sSql="Select count(*) COUNTS, ENTERPRISE_CODE ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(shipment_key, 5,2) MON," + sSubstr + "(shipment_key, 7,2) DA from YFS_SHIPMENT where shipment_key like '" +  pkPrefix + "%' group by ENTERPRISE_CODE, DOCUMENT_TYPE, "+sSubstr+"(shipment_key, 5,2)," + sSubstr + "(shipment_key, 7,2)";
		buildCollections(oEnv, sSql, YFS_ShipmentDBHome.getInstance(), "SHIPMENT", Integer.toString(dStart.getFourDigitYear()), dStart);

		sSql="Select count(*) COUNTS, ENTERPRISE_CODE ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(shipment_key, 5,2) MON," + sSubstr + "(shipment_key, 7,2) DA from YFS_SHIPMENT_H where shipment_key like '" +  pkPrefix + "%' group by ENTERPRISE_CODE, DOCUMENT_TYPE, "+sSubstr+"(shipment_key, 5,2)," + sSubstr + "(shipment_key, 7,2)";
		buildCollections(oEnv, sSql, YFS_Shipment_HDBHome.getInstance(), "SHIPMENT", Integer.toString(dStart.getFourDigitYear()), dStart);
		if(!pkPrefix.equals(pkPrefix2)){
			sSql="Select count(*) COUNTS, ENTERPRISE_CODE ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(shipment_key, 5,2) MON," + sSubstr + "(shipment_key, 7,2) DA from YFS_SHIPMENT where shipment_key like '" +  pkPrefix2 + "%' group by ENTERPRISE_CODE, DOCUMENT_TYPE, "+sSubstr+"(shipment_key, 5,2)," + sSubstr + "(shipment_key, 7,2)";
			buildCollections(oEnv, sSql, YFS_ShipmentDBHome.getInstance(), "SHIPMENT",Integer.toString(dStart.getFourDigitYear()+1), dStart);

			sSql="Select count(*) COUNTS, ENTERPRISE_CODE ENTERPRISE_KEY, DOCUMENT_TYPE,"+sSubstr+"(shipment_key, 5,2) MON," + sSubstr + "(shipment_key, 7,2) DA from YFS_SHIPMENT_H where shipment_key like '" +  pkPrefix2 + "%' group by ENTERPRISE_CODE, DOCUMENT_TYPE, "+sSubstr+"(shipment_key, 5,2)," + sSubstr + "(shipment_key, 7,2)";
			buildCollections(oEnv, sSql, YFS_Shipment_HDBHome.getInstance(), "SHIPMENT",Integer.toString(dStart.getFourDigitYear()+1), dStart);
		}
	}
	private void buildCollections(YFSEnvironment oEnv,String sSql, YFCEntityDBHome anEntityDBHome, String sType, String sYear, YDate dStart)
	{
		Statement st=null;
		try {
			cat.beginTimer("buildCollections.sqlcall");
			cat.debug(sSql);
			st = YSCExecuteSqlUtil.getConnectionForEntity(((YFSContext)oEnv), anEntityDBHome).createStatement();
            ResultSet rs = st.executeQuery(sSql);
			while (rs.next()) { 
				
				int iCount=rs.getInt("COUNTS");
				String sEnterpriseCode =rs.getString("ENTERPRISE_KEY").trim();
				String sDocumentType=rs.getString("DOCUMENT_TYPE");
				String sMonth=rs.getString("MON");
				String sDay=rs.getString("DA");
				if(Integer.parseInt(sYear) == dStart.getFourDigitYear()){
					if(Integer.parseInt(sMonth) > dStart.getMonth() + 1){
						appendToCollection(oEnv,iCount,sEnterpriseCode,sDocumentType,sYear, sMonth,sType);
					} else if(Integer.parseInt(sMonth) == dStart.getMonth() + 1 && Integer.parseInt(sDay) >= dStart.getDayOfMonth()){
						appendToCollection(oEnv,iCount,sEnterpriseCode,sDocumentType,sYear, sMonth,sType);
					}
				} else if(Integer.parseInt(sYear) == dStart.getFourDigitYear() + 1){
					if(Integer.parseInt(sMonth) < dStart.getMonth() + 1){
						appendToCollection(oEnv,iCount,sEnterpriseCode,sDocumentType,sYear, sMonth,sType);
					} else if(Integer.parseInt(sMonth) == dStart.getMonth() + 1 && Integer.parseInt(sDay) < dStart.getDayOfMonth()){
						appendToCollection(oEnv,iCount,sEnterpriseCode,sDocumentType,sYear, sMonth,sType);
					}
				}				
			}
			rs.close();
		} catch (SQLException ex) { 
			 YFCDBException dbex = YFCEntityDBHome.getYFCException(ex,((YFSContext)oEnv).getDBType(anEntityDBHome));
			 dbex.setSqlStatement(sSql);
			throw dbex;
		} finally {
			cat.endTimer("buildCollections.sqlcall");
			try{
			if (st != null) 
				st.close();
			}
			catch(SQLException ex)
			{}
		}
	}
	private void appendToCollection(YFSEnvironment oEnv, int iCount, String sEnterpriseCode ,String sDocumentType, String sYear, String sMonth, String sType)
	{
		HashMap<String, HashMap<String, HashMap<String, Integer>>> oDocumentMap = oEnterpriseMap.get(sEnterpriseCode);
		if (oDocumentMap == null) {
			oDocumentMap=new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
			oEnterpriseMap.put(sEnterpriseCode,oDocumentMap);
		}

		HashMap<String, HashMap<String, Integer>> oTypeMap=oDocumentMap.get(sDocumentType);
		if (oTypeMap == null) {
			oTypeMap=new HashMap<String, HashMap<String, Integer>>();
			oDocumentMap.put(sDocumentType,oTypeMap);
		}

		HashMap<String, Integer> monthArray=oTypeMap.get(sType);
		if (monthArray == null) {
			monthArray=new HashMap<String, Integer>();
			oTypeMap.put(sType,monthArray);
		}
		
		String sKey = sYear + "-" + sMonth;
		if(monthArray.containsKey(sKey)){
			monthArray.put(sKey, monthArray.get(sKey) + iCount);
		} else {
			monthArray.put(sKey, iCount);
		}
		
		if(monthArray.containsKey("Total")){
			monthArray.put("Total", monthArray.get("Total") + iCount);
		} else {
			monthArray.put("Total", iCount);
		}

	}
	
	private void buildXML(YFSEnvironment oEnv,YFCElement eOut, YDate dStart)
	{
		for (String sEnterprise : oEnterpriseMap.keySet()){
			HashMap<String, HashMap<String, HashMap<String, Integer>>> oDocumentMap = oEnterpriseMap.get(sEnterprise);
			for (String sDocumentType : oDocumentMap.keySet()) {
				YFCElement eTranSum=eOut.getOwnerDocument().createElement("TransactionSummary");
				eOut.appendChild(eTranSum);
				eTranSum.setAttribute("EnterpriseCode",sEnterprise);
				eTranSum.setAttribute("DocumentType",sDocumentType);
				YFS_Document_Params oDocument=YCPFactory.getInstance().getDocument(((YFSContext)oEnv),sDocumentType);
				if (oDocument !=null ) {
					eTranSum.setAttribute("Description",oDocument.getDescription());
				}
				HashMap<String, HashMap<String, Integer>> oTypeMap = oDocumentMap.get(sDocumentType);
				HashMap<String, Integer> OrderMonthArray= oTypeMap.get("ORDER");
				if (OrderMonthArray == null) 
					OrderMonthArray = new HashMap<String, Integer>();
				
				HashMap<String, Integer> OrderLineMonthArray=oTypeMap.get("ORDERLINE");
				if (OrderLineMonthArray == null) 
					OrderLineMonthArray=new HashMap<String, Integer>();
				
				HashMap<String, Integer> ShipmentMonthArray=oTypeMap.get("SHIPMENT");
				if (ShipmentMonthArray == null) 
					ShipmentMonthArray=new HashMap<String, Integer>();
				
				if (bOrdersRequired || bOrderLinesRequired || bShipmentsRequired ) {
					YFCElement eMonthlySumLst=eOut.getOwnerDocument().createElement("MonthlySummaryList");
					eTranSum.appendChild(eMonthlySumLst);
					int startYear = dStart.getFourDigitYear();
					int startMonth = dStart.getMonth() + 1;
					for(int i = 0; i < 12; i++){
						String sKey = getKey(startYear, startMonth, i);
						if(!YFCCommon.equals(sKey, "Total")){
							YFCElement eMonthlySum=eOut.getOwnerDocument().createElement("MonthlySummary");
							eMonthlySumLst.appendChild(eMonthlySum);
							eMonthlySum.setAttribute("Month",sKey.split("-")[1]);
							eMonthlySum.setAttribute("Year",sKey.split("-")[0]);
							if (bOrdersRequired){
								int orders = 0;
								if(OrderMonthArray.containsKey(sKey)){
									orders = OrderMonthArray.get(sKey);
								}
								eMonthlySum.setAttribute("NoOfOrdersCreated",orders);
							}
							if (bOrderLinesRequired){
								int lines = 0;
								if(OrderLineMonthArray.containsKey(sKey)){
									lines = OrderLineMonthArray.get(sKey);
								}
								eMonthlySum.setAttribute("NoOfOrderLinesCreated",lines);
							}
							if (bShipmentsRequired){
								int shipments = 0;
								if(ShipmentMonthArray.containsKey(sKey)){
									shipments = ShipmentMonthArray.get(sKey);
								}
								eMonthlySum.setAttribute("NoOfShipmentsCreated",shipments);
							}
						}
					}
				}
				if (bYTDOrdersRequired || bYTDOrderLinesRequired || bYTDShipmentsRequired ) {
					YFCElement eYTDSum=eOut.getOwnerDocument().createElement("YTDSummary");
					eTranSum.appendChild(eYTDSum);
					if (bYTDOrdersRequired){
						int orders = 0;
						if(OrderMonthArray.containsKey("Total")){
							orders = OrderMonthArray.get("Total");
						}
						eYTDSum.setAttribute("YTDNoOfOrdersCreated",orders);
					}
					if (bYTDOrderLinesRequired){
						int lines = 0;
						if(OrderLineMonthArray.containsKey("Total")){
							lines = OrderLineMonthArray.get("Total");
						}
						eYTDSum.setAttribute("YTDNoOfOrderLinesCreated",lines);
					}
					if (bYTDShipmentsRequired){
						int shipments = 0;
						if(ShipmentMonthArray.containsKey("Total")){
							shipments = ShipmentMonthArray.get("Total");
						}
						eYTDSum.setAttribute("YTDNoOfShipmentsCreated",shipments);
					}
				}

			}
			
		}

	}
	
	private String getKey(int year, int month, int offset){
		int newMonth = month + offset;
		int newYear = year;
		if(newMonth > 12){
			newMonth = newMonth - 12;
			newYear = year + 1;
		}
		if(newMonth < 10){
			return newYear + "-0" + newMonth; 
		} 
		return newYear + "-" + newMonth;
		
	}
}
