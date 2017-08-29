package com.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.ibm.extraction.BDASynchronizeItems;
import com.objects.Store;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;

public class LoadStores {

	public static void main(String[] args){
		String file = "/Users/pfaiola/Stores.csv";
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";
		ArrayList<Store> stores = new ArrayList<Store>();
		YFCDocument dMultiApi = null;
		
		try{
			File f = new File("/Users/pfaiola/manageOrg.xml");
			if(f.exists()){
				dMultiApi = YFCDocument.getDocumentForXMLFile("/Users/pfaiola/manageOrg.xml");
			} else {
				dMultiApi = YFCDocument.createDocument("MultiApi");
			}
			br = new BufferedReader(new FileReader(file));
			int j = 0;
			YFCElement eMultiApi = dMultiApi.getDocumentElement();
			while((line = br.readLine()) != null){
				String[] storeArgs = line.split(splitBy);
				if(j >= eMultiApi.getChildren().getTotalCount()){
					stores.add(new Store(storeArgs[0], storeArgs[1] + " " + storeArgs[2], null, storeArgs[3], storeArgs[4], storeArgs[5], storeArgs[6]));
				}
				j++;
			}
			int i = 12 + eMultiApi.getChildren().getTotalCount();
			for(Store s : stores){
				YFCElement eApi = eMultiApi.createChild("API");
				eApi.setAttribute("Name", "manageOrganizationHierarchy");
				YFCElement eInput = eApi.createChild("Input");
				Thread.sleep(100);
				eInput.importNode(s.getOrganization("Auro_Store_" + i++, "Aurora-Corp").getDocumentElement());
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(dMultiApi != null){
				BDASynchronizeItems.writeXML("/Users/pfaiola", "manageOrg.xml", dMultiApi);
			}
		}
		
		
		
	}
	
}
