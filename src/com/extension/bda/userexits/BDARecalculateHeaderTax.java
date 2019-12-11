package com.extension.bda.userexits;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSExtnHeaderTaxCalculationInputStruct;
import com.yantra.yfs.japi.YFSExtnLineTaxCalculationInputStruct;
import com.yantra.yfs.japi.YFSExtnTaxBreakup;
import com.yantra.yfs.japi.YFSExtnTaxCalculationOutStruct;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSRecalculateHeaderTaxUE;
import com.yantra.yfs.japi.ue.YFSRecalculateLineTaxUE;

public class BDARecalculateHeaderTax implements YFSRecalculateHeaderTaxUE, YFSRecalculateLineTaxUE {

	private YFCElement taxDetails = null;
	
	private YFCElement getTaxDetails(YFSEnvironment env){
		if(YFCCommon.isVoid(taxDetails)){
			YFCDocument dTD = YFCDocument.getDocumentForXMLFile(BDAServiceApi.getScriptsPath(env) + "/TaxDetails.xml");
			if(!YFCCommon.isVoid(dTD)){
				taxDetails = dTD.getDocumentElement();
				return taxDetails;
			} else {
				taxDetails = YFCDocument.createDocument("TaxDetails").getDocumentElement();
			}
		}
		return taxDetails;
	}
	
	public Document clearTaxCache(YFSEnvironment env, Document input){
		this.taxDetails = null;
		return input;
	}
	
	
	
	@Override
	public YFSExtnTaxCalculationOutStruct recalculateHeaderTax(YFSEnvironment env,
			YFSExtnHeaderTaxCalculationInputStruct input) throws YFSUserExitException {
		YFSExtnTaxCalculationOutStruct output = new YFSExtnTaxCalculationOutStruct();
		if(input.taxExemptFlag.equals("Y")){
			output.tax = 0;
			output.taxPercentage = 0;
			output.colTax = new ArrayList<YFSExtnTaxBreakup>();
		}
		return output;
	}

	@Override
	public YFSExtnTaxCalculationOutStruct recalculateLineTax(YFSEnvironment env,
			YFSExtnLineTaxCalculationInputStruct input) throws YFSUserExitException {
		YFSExtnTaxCalculationOutStruct output = new YFSExtnTaxCalculationOutStruct();
		List<YFSExtnTaxBreakup> taxes = new ArrayList<YFSExtnTaxBreakup>();
		output.tax = 0;
		output.taxPercentage = 0;
		output.colTax = taxes;
		if(!YFCCommon.equals(input.taxExemptFlag, "Y")){
			YFCElement td = this.getTaxDetails(env);

			YFCElement eExemptList = td.getChildElement("TaxExempt");
			for (YFCElement eItem : eExemptList.getChildren()){
				if(YFCCommon.equals(eItem.getAttribute("ItemID"), input.itemId)){
					return output;
				}
			}
				
			
			for(YFCElement detail : td.getChildren()){
				if(YFCCommon.equals(detail.getAttribute("Country"), input.shipToCountry)){
					if(detail.hasChildNodes()){
						for(YFCElement d2 : detail.getChildren()){
							if(d2.hasAttribute("State") && YFCCommon.equals(d2.getAttribute("State"), input.shipToState)){
								if(d2.hasChildNodes()){
									for(YFCElement d3 : detail.getChildren()){
										if(d3.hasAttribute("City") && YFCCommon.equals(d3.getAttribute("City"), input.shipToCity)){
											output.taxPercentage = d3.getDoubleAttribute("DefaultTax");
											output.tax = (input.unitPrice * input.lineQty) * (output.taxPercentage / 100);
											addSalesTax(output, taxes);
											return output;
										}
									}									
								} 
								output.taxPercentage = d2.getDoubleAttribute("DefaultTax");
								output.tax = (input.unitPrice * input.lineQty) * (output.taxPercentage / 100);
								addSalesTax(output, taxes);
								return output;
							} 
						}
					}
					output.taxPercentage = detail.getDoubleAttribute("DefaultTax");
					output.tax = (input.unitPrice * input.lineQty) * (output.taxPercentage / 100);
					addSalesTax(output, taxes);
					return output;
				}
			}
		}
		
		return output;
	}

	private void addSalesTax(YFSExtnTaxCalculationOutStruct output, List<YFSExtnTaxBreakup> taxes){
		YFSExtnTaxBreakup tb = new YFSExtnTaxBreakup();
		tb.chargeCategory = "SalesTax";
		tb.chargeName = "Sales Tax";
		tb.tax = output.tax;
		tb.taxName = "SalesTax";
		tb.taxPercentage = output.taxPercentage;
		tb.taxableFlag = "Y";
		taxes.add(tb);
	}
}
