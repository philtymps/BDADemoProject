package com.scripts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import com.extension.bda.object.DatabaseConnection;
import com.extension.bda.service.expose.BDAEntityApi;
import com.extension.bda.service.fulfillment.BDAServiceApi;
import com.extension.bda.service.iv.BDAClearInventory;
import com.ibm.CallInteropServlet;
import com.utilities.WSConnection;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSEnvironment;

public class GenerateAdjustInventory {
	private Properties properties;
	private static YFCLogCategory logger = YFCLogCategory.instance(GenerateAdjustInventory.class);

	public static void main(String[] args) throws IOException {
		System.out.println("Started");
		GenerateAdjustInventory t = new GenerateAdjustInventory();
		// t.removeSpecialInventory(null, null);
		YFCDocument dInput = YFCDocument.createDocument("Input");
		YFCElement eInput = dInput.getDocumentElement();
		eInput.setAttribute("CatalogOrganizationCode", "Aurora-Corp");
		eInput.setAttribute("InventoryOrganizationCode", "Aurora");
		Document dOutput = t.createStandardInventory(null, dInput.getDocument());

		if (!YFCCommon.isVoid(dOutput)) {
			YFCDocument output = YFCDocument.getDocumentFor(dOutput);
			while (!YFCCommon.isVoid(output.getDocumentElement().getChildElement("Item"))) {
				System.out.println("Output: " + output.toString());
				CallInteropServlet.invokeApi(output, null, "adjustInventory", "http://oms.innovationcloud.info:9080");
				dOutput = t.createStandardInventory(null, dInput.getDocument());
				if (!YFCCommon.isVoid(dOutput)) {
					output = YFCDocument.getDocumentFor(dOutput);
				}
			}
			System.out.println("Output: " + output.toString());
			CallInteropServlet.invokeApi(output, null, "adjustInventory", "http://oms.innovationcloud.info:9080");
		}
	}

	public void setProperties(Properties properties) throws Exception {
		this.properties = properties;
	}

	public Object getProperty(String sProp) {
		return this.properties.get(sProp);
	}

	private String getVariableFile(YFSEnvironment env) {
		if (!YFCCommon.isVoid(getProperty("variablefile"))) {
			return (String) getProperty("variablefile");
		}
		return BDAServiceApi.getScriptsPath(env) + "/variables.xml";
	}

	private HashMap<String, String> replaceVariables(YFSEnvironment env, YFCDocument dFileInput) {
		HashMap<String, String> variable = new HashMap<String, String>();
		YFCDocument temp = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
		for (YFCElement eChild : temp.getDocumentElement().getChildren()) {
			variable.put(eChild.getAttribute("Name"), eChild.getAttribute("Value"));
		}
		return variable;
	}

	public GenerateAdjustInventory() {
		properties = new Properties();
	}

	public Document removeSpecialInventory(YFSEnvironment env, Document input) throws IOException {
		YFCDocument dOutput = YFCDocument.createDocument("Output");
		YFCElement eOutput = dOutput.getDocumentElement();
		YFCDocument dVariables = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
		if (!YFCCommon.isVoid(dVariables)) {
			BDAEntityApi entity = new BDAEntityApi();
			YFCDocument dBaseRule = YFCDocument.createDocument("BaseRule");
			YFCElement eBaseRule = dBaseRule.getDocumentElement();
			eBaseRule.setAttribute("ApiName", "getBaseRules");
			eBaseRule.setAttribute("BaseRulesKey", "IV_1");
			Document dResponse = entity.invoke(env, dBaseRule.getDocument());
			if (!YFCCommon.isVoid(dResponse.getDocumentElement().getAttribute("RuleSetValue"))
					&& dResponse.getDocumentElement().getAttribute("RuleSetValue").indexOf("2") > -1) {
				BDAClearInventory bdaci = new BDAClearInventory();
				try {
					dOutput = YFCDocument.getDocumentFor(bdaci.invoke(env, null));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				HashMap<String, String> vars = replaceVariables(env, dVariables);
				Connection conn = DatabaseConnection.getConnection(env);
				String sStatement = "DELETE FROM " + DatabaseConnection.getDBSchema(env)
						+ ".YFS_INVENTORY_SUPPLY WHERE INVENTORY_ITEM_KEY IN (SELECT INVENTORY_ITEM_KEY FROM "
						+ DatabaseConnection.getDBSchema(env) + ".YFS_INVENTORY_ITEM WHERE ITEM_ID = ?)";
				try {
					PreparedStatement ps = conn.prepareStatement(sStatement);
					for (String itemid : vars.values()) {
						YFCElement eItem = eOutput.createChild("Item");
						eItem.setAttribute("ItemID", itemid);
						ps.setString(1, itemid);
						ps.executeUpdate();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return dOutput.getDocument();
	}

	public Document createStandardInventory(YFSEnvironment env, Document input) throws IOException {
		YFCDocument dOutput = YFCDocument.createDocument("Items");
		YFCElement eOutput = dOutput.getDocumentElement();
		if (!YFCCommon.isVoid(input)) {
			YFCDocument dInput = YFCDocument.getDocumentFor(input);
			YFCElement eInput = dInput.getDocumentElement();
			eInput.setAttribute("DistRuleId", "Aurora_Shipping_Network");
			if (!YFCCommon.isVoid(eInput.getAttribute("CatalogOrganizationCode"))
					&& !YFCCommon.isVoid(eInput.getAttribute("InventoryOrganizationCode"))) {
				createStandardInventoryForOrg(env, eOutput, eInput, eInput.getAttribute("CatalogOrganizationCode"),
						eInput.getAttribute("InventoryOrganizationCode"));
			}
		}
		return dOutput.getDocument();
	}

	private void createStandardInventoryForOrg(YFSEnvironment env, YFCElement eOutput, YFCElement eInput,
			String sCatOrgCode, String sInventoryOrg) throws IOException {
		YFCDocument dVariables = YFCDocument.getDocumentForXMLFile(getVariableFile(env));
		if (!YFCCommon.isVoid(dVariables)) {
			HashMap<String, String> vars = replaceVariables(env, dVariables);
			YFCElement eShipNodes;
			if (!YFCCommon.isVoid(eInput.getAttribute("DistRuleId"))) {
				eShipNodes = getOptimizerShipNodes(env, eInput, sInventoryOrg);
			} else {
				eShipNodes = getShipNodes(env, eInput, sInventoryOrg);
			}

			
			String sStatement = "SELECT DISTINCT TRIM(I.ITEM_ID) ITEM_ID, TRIM(I.UOM) UOM FROM OMDB.YFS_ITEM I WHERE TRIM(I.ORGANIZATION_CODE) = ? AND I.ITEM_ID LIKE 'SAMS%' AND I.ITEM_GROUP_CODE = 'PROD' AND I.ITEM_ID NOT IN (SELECT II.ITEM_ID FROM OMDB.YFS_INVENTORY_ITEM II INNER JOIN OMDB.YFS_INVENTORY_SUPPLY IS ON IS.INVENTORY_ITEM_KEY = II.INVENTORY_ITEM_KEY WHERE II.ORGANIZATION_CODE = ? GROUP BY II.ITEM_ID HAVING SUM(IS.QUANTITY) > 0)";
			Connection conn = null;
			try {
				conn = DatabaseConnection.getConnection(env);
				PreparedStatement ps = conn.prepareStatement(sStatement);
				ps.setString(1, sCatOrgCode);
				ps.setString(2, sInventoryOrg);
				ResultSet rso = ps.executeQuery();
				int count = 0;
				while (rso.next()) {
					if (count > 800) {
						break;
					}
					if (!vars.containsValue(rso.getString("ITEM_ID"))) {
						for (YFCElement eShipNode : eShipNodes.getChildren()) {
							createAdjustmentRecords(eOutput, rso.getString("ITEM_ID"),
									eShipNode.getAttribute("ShipNode"), rso.getString("UOM"));
							count++;
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private YFCElement getShipNodesLF(YFSEnvironment env, YFCElement eInput, String sOrgCode) throws IOException {
		YFCDocument dShipNodes = YFCDocument.createDocument("ShipNodes");
		YFCElement eShipNodes = dShipNodes.getDocumentElement();
		if (!YFCCommon.isVoid(eInput)) {
			if (!YFCCommon.isVoid(eInput.getChildElement("ShipNodes"))) {
				return eInput.getChildElement("ShipNodes");
			} else {
				if (!YFCCommon.isVoid(eInput.getAttribute("ShipNode"))) {
					YFCElement eShipNode = eShipNodes.createChild("ShipNode");
					eShipNode.setAttribute("ShipNode", eInput.getAttribute("ShipNode"));
				} else {
					String sStatement = "SELECT DISTINCT TRIM(SHIPNODE_KEY) SHIPNODE_KEY FROM OMDB.YFS_SHIP_NODE WHERE SHIPNODE_KEY LIKE '%SG%' OR SHIPNODE_KEY LIKE '%VN%' OR SHIPNODE_KEY LIKE '%TH%'";
					Connection conn = null;
					try {
						conn = DatabaseConnection.getConnection(env);
						PreparedStatement ps = conn.prepareStatement(sStatement);
						// ps.setString(1, sOrgCode);
						ResultSet rso = ps.executeQuery();
						while (rso.next()) {
							YFCElement eShipNode = eShipNodes.createChild("ShipNode");
							eShipNode.setAttribute("ShipNode", rso.getString("SHIPNODE_KEY"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}
		return eShipNodes;
	}

	private YFCElement getOptimizerShipNodes(YFSEnvironment env, YFCElement eInput, String sOrgCode)
			throws IOException {
		YFCDocument dShipNodes = YFCDocument.createDocument("ShipNodes");
		YFCElement eShipNodes = dShipNodes.getDocumentElement();
		if (!YFCCommon.isVoid(eInput)) {
			if (!YFCCommon.isVoid(eInput.getChildElement("ShipNodes"))) {
				return eInput.getChildElement("ShipNodes");
			} else {
				if (!YFCCommon.isVoid(eInput.getAttribute("ShipNode"))) {
					YFCElement eShipNode = eShipNodes.createChild("ShipNode");
					eShipNode.setAttribute("ShipNode", eInput.getAttribute("ShipNode"));
				} else {
					String sStatement = "SELECT DISTINCT TRIM(SHIPNODE_KEY) SHIPNODE_KEY FROM OMDB.YFS_ITEM_SHIP_NODE WHERE TRIM(DISTRIBUTION_RULE_ID) = ?";
					Connection conn = null;
					try {
						conn = DatabaseConnection.getConnection(env);
						PreparedStatement ps = conn.prepareStatement(sStatement);
						ps.setString(1, eInput.getAttribute("DistRuleId"));
						ResultSet rso = ps.executeQuery();
						while (rso.next()) {
							YFCElement eShipNode = eShipNodes.createChild("ShipNode");
							eShipNode.setAttribute("ShipNode", rso.getString("SHIPNODE_KEY"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}
		return eShipNodes;
	}

	private YFCElement getShipNodes(YFSEnvironment env, YFCElement eInput, String sOrgCode) throws IOException {
		YFCDocument dShipNodes = YFCDocument.createDocument("ShipNodes");
		YFCElement eShipNodes = dShipNodes.getDocumentElement();
		if (!YFCCommon.isVoid(eInput)) {
			if (!YFCCommon.isVoid(eInput.getChildElement("ShipNodes"))) {
				return eInput.getChildElement("ShipNodes");
			} else {
				if (!YFCCommon.isVoid(eInput.getAttribute("ShipNode"))) {
					YFCElement eShipNode = eShipNodes.createChild("ShipNode");
					eShipNode.setAttribute("ShipNode", eInput.getAttribute("ShipNode"));
				} else {
					String sStatement = "SELECT DISTINCT TRIM(SHIPNODE_KEY) SHIPNODE_KEY FROM OMDB.YFS_ITEM_SHIP_NODE WHERE PRIORITY IN (1,2,5,7) AND TRIM(OWNER_KEY) in (SELECT TRIM(INVENTORY_ORGANIZATION_CODE) FROM OMDB.YFS_ORGANIZATION WHERE TRIM(ORGANIZATION_CODE) = ?)";
					Connection conn = null;
					try {
						conn = DatabaseConnection.getConnection(env);
						PreparedStatement ps = conn.prepareStatement(sStatement);
						ps.setString(1, sOrgCode);
						ResultSet rso = ps.executeQuery();
						while (rso.next()) {
							YFCElement eShipNode = eShipNodes.createChild("ShipNode");
							eShipNode.setAttribute("ShipNode", rso.getString("SHIPNODE_KEY"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}
		return eShipNodes;
	}

	private void createAdjustmentRecords(YFCElement eItems, String sItemID, String sNode, String sUnitOfMeasure) {
		YFCElement eItem = eItems.createChild("Item");
		eItem.setAttribute("AdjustmentType", "ADJUSTMENT");
		eItem.setAttribute("Availability", "TRACK");
		eItem.setAttribute("ItemID", sItemID);
		eItem.setAttribute("Quantity", Math.round(Math.random() * 20));
		eItem.setAttribute("ShipNode", sNode);
		eItem.setAttribute("SupplyType", "ONHAND");
		eItem.setAttribute("UnitOfMeasure", sUnitOfMeasure);
	}

}
