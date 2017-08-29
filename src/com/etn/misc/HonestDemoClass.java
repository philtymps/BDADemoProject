package com.etn.misc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

public class HonestDemoClass {

	private  YIFApi oApi;
	
	public HonestDemoClass() throws YIFClientCreationException {
		
		this.oApi = YIFClientFactory.getInstance().getLocalApi();
	}

	public Document addReservation(YFSEnvironment env,Document docInXML) throws Exception{
		
		Document reserveItemInventoryOP = null;
		if(docInXML != null)
		{
				Element ipElement = docInXML.getDocumentElement() ;
				String strReservationMonth = ipElement.getAttribute("Segment");
				String strReservationOrg = ipElement.getAttribute("ShipNode");
				
				//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
				//Date date_jsp =  dateFormat.parse(strReservationDate);
				//String strReservationMonth = new SimpleDateFormat("MMMM").format(date_jsp);
				
				//System.out.println("ReservationDate and For Organization====" +strReservationOrg + "----"+strReservationMonth );
				
				String strReservationID = strReservationOrg + "-" + strReservationMonth;
				System.out.println("Reservation ID in input XML=====" +strReservationID );
				
				ipElement.setAttribute("ReservationID", strReservationID);
				
				
				reserveItemInventoryOP = oApi.invoke(env,"reserveItemInventory", docInXML);
				
		}
				
				
				
	
		return reserveItemInventoryOP;
	}

	
	public Document modifyReservation(YFSEnvironment env,Document docInXML) throws Exception{
		
		Document multiApiOP = null;
		
	
		if(docInXML != null)
		{
			
				Element ipElement = docInXML.getDocumentElement();
				String strFromMonth = ipElement.getAttribute("FromMonth");
				String strFromOrg = ipElement.getAttribute("FromOrg");
				String strToOrg = ipElement.getAttribute("ToOrg");
				String strToMonth = ipElement.getAttribute("ToMonth");
				String strQty= ipElement.getAttribute("Quantity");
				String strItemID= ipElement.getAttribute("ItemID");
				String strUOM= ipElement.getAttribute("UnitOfMeasure");
				String strOrgCode= ipElement.getAttribute("OrganizationCode");
				
				String strReservationID_1 = strFromOrg + "-" + strFromMonth;
				
				String strReservationID_2 = strToOrg + "-" + strToMonth;
				
				YFCDocument yfcMultiApiDoc = YFCDocument.createDocument("MultiApi");
				YFCElement eleMultiApi = yfcMultiApiDoc.getDocumentElement();
				
				YFCElement eleAPI_1 = eleMultiApi.createChild("API");
				eleAPI_1.setAttribute("Name", "reserveItemInventory");
				
				YFCElement eleAPI_1_Input = eleAPI_1.createChild("Input");
				YFCElement eleAPI_1_Input_ReserveItemInventory = eleAPI_1_Input.createChild("ReserveItemInventory");
				
				eleAPI_1_Input_ReserveItemInventory.setAttribute("QtyToBeCancelled", strQty);
				eleAPI_1_Input_ReserveItemInventory.setAttribute("ReservationID", strReservationID_1);
				eleAPI_1_Input_ReserveItemInventory.setAttribute("ShipNode", strFromOrg);
				eleAPI_1_Input_ReserveItemInventory.setAttribute("ItemID", strItemID);
				eleAPI_1_Input_ReserveItemInventory.setAttribute("UnitOfMeasure", strUOM);
				eleAPI_1_Input_ReserveItemInventory.setAttribute("OrganizationCode", strOrgCode);
				eleAPI_1_Input_ReserveItemInventory.setAttribute("Segment", strFromMonth);
				
				// Form API-2
				YFCElement eleAPI_2 = eleMultiApi.createChild("API");
				eleAPI_2.setAttribute("Name", "reserveItemInventory");
				
				YFCElement eleAPI_2_Input = eleAPI_2.createChild("Input");
				YFCElement eleAPI_2_Input_ReserveItemInventory = eleAPI_2_Input.createChild("ReserveItemInventory");
				
				eleAPI_2_Input_ReserveItemInventory.setAttribute("QtyToBeReserved", strQty);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("ReservationID", strReservationID_2);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("ShipNode", strToOrg);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("ItemID", strItemID);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("UnitOfMeasure", strUOM);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("OrganizationCode", strOrgCode);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("Segment", strToMonth);
				eleAPI_2_Input_ReserveItemInventory.setAttribute("CheckInventory", "N");
				
				
				//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
				//Date date_jsp =  dateFormat.parse(strReservationDate);
				//String strReservationMonth = new SimpleDateFormat("MMMM").format(date_jsp);
				
				System.out.println("MultiAPI Input=====" + yfcMultiApiDoc.toString());
				
				multiApiOP = oApi.invoke(env,"multiApi", yfcMultiApiDoc.getDocument());
		}
				
				
				
	
		return multiApiOP;
	}

	


	
}
