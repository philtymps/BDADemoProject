package com.ibm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class CallInteropServlet {

	
	
	private static YFCDocument getFindInventoryInput(){
		YFCDocument dPromise = YFCDocument.createDocument("Promise");
		YFCElement ePromise = dPromise.getDocumentElement();
		ePromise.setAttribute("OrganizationCode", "Aurora");
		YFCElement ePromiseLine = ePromise.getChildElement("PromiseLines", true).createChild("PromiseLine");
		ePromiseLine.setAttribute("ItemID", "AuroraWMDRS-003");
		ePromiseLine.setAttribute("LineId", "L1");
		ePromiseLine.setAttribute("UnitOfMeasure", "EACH");
		ePromiseLine.setAttribute("RequiredQty", "9999999");
		return dPromise;
	}
	
	private static YFCDocument getFindInventoryTemplate(){
		YFCDocument dPromise = YFCDocument.createDocument("Promise");
		YFCElement ePromise = dPromise.getDocumentElement();
		YFCElement eSuggestedOption = ePromise.createChild("SuggestedOption");
		YFCElement eOption = eSuggestedOption.createChild("Option");
		eOption.setAttribute("HasAnyUnavailableQty", "");
		eOption.setAttribute("OptionNo", "");
		eOption.setAttribute("NodeQty", "");
		eOption.setAttribute("AvailableFromUnplannedInventory", "");
		eOption.setAttribute("FirstDate", "");
		eOption.setAttribute("LastDate", "");
		YFCElement ePromiseLine = eOption.getChildElement("PromiseLines", true).createChild("PromiseLine");
		ePromiseLine.setAttribute("LineId", "");
		ePromiseLine.setAttribute("ItemID", "");
		ePromiseLine.setAttribute("ShipNode","");
		YFCElement eAssignment = ePromiseLine.getChildElement("Assignments", true).createChild("Assignment");
		eAssignment.setAttribute("DeliveryDate", "");
		eAssignment.setAttribute("EarliestShipDate", "");
		eAssignment.setAttribute("InvQty", "");
		eAssignment.setAttribute("InventoryAvailabilityDate", "");
		eAssignment.setAttribute("Quantity", "");
		eAssignment.setAttribute("ProcuredQty", "");
		eAssignment.setAttribute("ReservedQty", "");
		eAssignment.setAttribute("QuantityFromUnplannedInventory", "");
		eAssignment.setAttribute("ShipNode", "");
		eOption = ePromise.createChild("Option");
		eOption.setAttribute("HasAnyUnavailableQty", "");
		eOption.setAttribute("OptionNo", "");
		eOption.setAttribute("NodeQty", "");
		eOption.setAttribute("AvailableFromUnplannedInventory", "");
		eOption.setAttribute("FirstDate", "");
		eOption.setAttribute("LastDate", "");
		ePromiseLine = eOption.getChildElement("PromiseLines", true).createChild("PromiseLine");
		ePromiseLine.setAttribute("LineId", "");
		ePromiseLine.setAttribute("ItemID", "");
		eAssignment = ePromiseLine.getChildElement("Assignments", true).createChild("Assignment");
		eAssignment.setAttribute("DeliveryDate", "");
		eAssignment.setAttribute("EarliestShipDate", "");
		eAssignment.setAttribute("InvQty", "");
		eAssignment.setAttribute("InventoryAvailabilityDate", "");
		eAssignment.setAttribute("Quantity", "");
		eAssignment.setAttribute("ProcuredQty", "");
		eAssignment.setAttribute("ReservedQty", "");
		eAssignment.setAttribute("QuantityFromUnplannedInventory", "");
		eAssignment.setAttribute("ShipNode", "");
		return dPromise;
		
	}
	
	public static YFCDocument invokeApi(YFCDocument dInput, YFCDocument dTemplate, String sApiName, String sServer){
		try {
			URL url = new URL(sServer + "/smcfs/interop/InteropHttpServlet");
			//System.out.println("Connecting to: " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();	
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			out.write("YFSEnvironment.progId=");
            out.write(URLEncoder.encode("ProgId"));
            out.write('&');

            out.write("YFSEnvironment.adapterName=");
            out.write(URLEncoder.encode("adapterName"));
            out.write('&');

            out.write("YFSEnvironment.systemName=");
            out.write(URLEncoder.encode("systemName"));
            out.write('&');
            
            out.write("YFSEnvironment.userId=admin&YFSEnvironment.password=password&");
            /*
             * TODO: Remove or expose... if (false) {
             * out.write("YFSEnvironment.rollbackOnly=");
             * out.write(URLEncoder.encode("Y")); out.write('&'); }
             */

            out.write("InteropApiName=");
            out.write(URLEncoder.encode(sApiName));
            out.write('&');

            if(!YFCCommon.isVoid(dTemplate)){
            	  out.write("TemplateData=");
                  out.write(URLEncoder.encode(dTemplate.getString()));
                  out.write('&');
            }
          
            
            out.write("InteropApiData=");
            // InteropEnvHelper.getInstance().addEnvToDocument(envStub, apiName,
            // apiData, _props);        


            String apiString = URLEncoder.encode(dInput.getString());
            out.write(apiString);

           /* if(_invokeAsService){
                out.write("IsFlow=");
                out.write(URLEncoder.encode("Y"));
                out.write('&');
            }*/

            out.flush();
            out.close();
            
            BufferedReader in = new BufferedReader (new InputStreamReader(conn.getInputStream()));
           
            StringBuffer sb = new StringBuffer();
            String response;
            while ((response = in.readLine()) != null){
            	sb.append(response);
            }
            in.close();
            return YFCDocument.getDocumentFor(sb.toString());
           
		} catch(MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		
		System.out.println(invokeApi(getFindInventoryInput(), getFindInventoryTemplate(), "findInventory", "http://oms.omfulfillment.com:9080"));

	}

}
