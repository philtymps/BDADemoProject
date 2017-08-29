package com.ibm.extraction.commerce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCDate;

public class Association {

	public Association(ResultSet rs, Item item) throws SQLException{
		this.item = item;
		if (!YFCCommon.isVoid(rs.getString("RANK"))){
			this.sPriority = rs.getString("RANK");
		}
		if (!YFCCommon.isVoid(rs.getString("MASSOCTYPE_ID"))){
			this.sAssociationType = rs.getString("MASSOCTYPE_ID");
		}
		if (!YFCCommon.isVoid(rs.getString("QUANTITY"))){
			this.sQuantity = rs.getString("QUANTITY");
		}
		fromDate = YFCDate.LOW_DATE;
		toDate = YFCDate.HIGH_DATE;
	}
	
	public void createAssociation(YFCElement eAssociationList){
		YFCElement eAssociation = eAssociationList.createChild("Association");
		eAssociation.setAttribute("AssociatedQuantity", this.sQuantity);
		eAssociation.setAttribute("AssociatedType", this.sAssociationType);
		eAssociation.setAttribute("Priority", this.sPriority);
		eAssociation.setDateAttribute("EffectiveFrom",fromDate);
		eAssociation.setDateAttribute("EffectiveTi", toDate);
		YFCElement eItem = eAssociation.createChild("Item");
		eItem.setAttribute("ItemKey", item.getItemKey());
	}
	
	private String sPriority = "0.0";
	private String sAssociationType = "";
	private String sQuantity = "1.0";
	private YFCDate fromDate, toDate;
	private Item item;
	
}
