package com.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.extension.bda.object.DatabaseConnection;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.ibm.CallInteropServlet;
import com.utilities.WSConnection;
import com.yantra.shared.ycp.YFSContext;
import com.yantra.yfc.date.YTimestamp;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class ResetEnvironment {

	public Document resetEnvironment(YFSEnvironment env, Document input){
		YFCDocument dReset = YFCDocument.createDocument("Reset");
		YFCElement eReset = dReset.getDocumentElement();
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection(env);
			File file = new File(ResetEnvironment.class.getResource("Truncate.sql").getFile());
			// FileReader reads text files in the default encoding.
	        FileReader fileReader = new FileReader(file);
	
	        // Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        String line = "";
	        
	        YFCElement eSqlList = eReset.createChild("SQLList");
	        while((line = bufferedReader.readLine()) != null) {
	        	line = line.replace(";", "");
	        	PreparedStatement ps = conn.prepareStatement(line);
	        	ps.executeQuery();
	        	YFCElement eSql = eSqlList.createChild("SQL");
	        	eSql.setNodeValue(line);
	        }   
	
	        // Always close files.
	        bufferedReader.close(); 
	        ((YFSContext)env).commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		boolean created = false;
		try{
			YFCDocument createOrder = YFCDocument.parse(ResetEnvironment.class.getResourceAsStream("multiApi_createOrder.xml"));
			YFCElement eMultiApi = createOrder.getDocumentElement();
			double t = 0;
			for(YFCElement eAPI : eMultiApi.getChildren()){
				YFCElement eOrder = eAPI.getChildElement("Input", true).getChildElement("Order");
				if(!YFCCommon.isVoid(eOrder)){
					YTimestamp tNow = YTimestamp.newTimestamp();
					tNow.addHours(t);
					eOrder.setAttribute("OrderDate", tNow);
					for(YFCElement eOrderLine : eOrder.getChildElement("OrderLines", true).getChildren()){
						tNow.addHours(.75);
						eOrderLine.setAttribute("ReqShipDate", tNow);
					}
				}
				t += .1;
			}
			Document output = BDAServiceApi.callApi(env, createOrder.getDocument(), null, "multiApi");
			YFCElement eCreateOrder = eReset.createChild("CreateOrder");
			YFCElement eInput = eCreateOrder.createChild("Input");
			eInput.importNode(createOrder);
			YFCElement eOutput = eCreateOrder.createChild("Output");
			eOutput.importNode(YFCDocument.getDocumentFor(output));
			created = true;
			((YFSContext)env).commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(created){
			try{
				YFCDocument createOrder = YFCDocument.parse(ResetEnvironment.class.getResourceAsStream("multiApi_completeOrder.xml"));
				YFCDocument dOutput = YFCDocument.getDocumentFor(BDAServiceApi.callApi(env, createOrder.getDocument(), null, "multiApi"));
				YFCElement eComplete = eReset.createChild("CompleteOrder1");
				YFCElement eInput = eComplete.createChild("Input");
				eInput.importNode(createOrder);
				YFCElement eOutput = eComplete.createChild("Output");
				eOutput.importNode(dOutput);
				((YFSContext)env).commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try{
				YFCDocument createOrder = YFCDocument.parse(ResetEnvironment.class.getResourceAsStream("multiApi_completeOrder.xml"));
				YFCDocument dOutput = YFCDocument.getDocumentFor(BDAServiceApi.callApi(env, createOrder.getDocument(), null, "multiApi"));
				YFCElement eComplete = eReset.createChild("CompleteOrder2");
				YFCElement eInput = eComplete.createChild("Input");
				eInput.importNode(createOrder);
				YFCElement eOutput = eComplete.createChild("Output");
				eOutput.importNode(dOutput);
				((YFSContext)env).commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return dReset.getDocument();
	}
}
