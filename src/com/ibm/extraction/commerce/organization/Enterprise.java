package com.ibm.extraction.commerce.organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class Enterprise extends Organization {

	public Enterprise(ResultSet rs, Map<String, Organization> organizations, String sServer) throws SQLException {
		super(rs, organizations, sServer);
		// TODO Auto-generated constructor stub
	}
	
	public YFCDocument getOrganizationHierarchy(){
		YFCDocument dOrg = super.getOrganizationHierarchy();
		YFCElement eOrg = dOrg.getDocumentElement();
		eOrg.setAttribute("IsEnterprise", true);
		if (YFCCommon.equals(this.getOrgCode(), this.getInventoryOrg())){
			eOrg.setAttribute("IsSeller", true);
		} else {
			eOrg.setAttribute("IsSeller", false);
		}
		eOrg.setAttribute("IsNode", false);
		
		YFCElement eEnterprise = eOrg.getChildElement("Enterprise", true);
		eEnterprise.setAttribute("EnterpriseKey", this.getOrgCode());
		eEnterprise.setAttribute("Enterprisecode", this.getOrgCode());
		eEnterprise.setAttribute("Enterprisename", this.getOrgName());
		eEnterprise.setAttribute("UseDefaultOrderStatusRules", false);
		eEnterprise.setAttribute("UseDefaultDistribution", false);
		
		
		YFCElement eOrgThemeList = eOrg.getChildElement("OrgThemeList", true);
		YFCElement eOrgTheme = eOrgThemeList.createChild("OrgTheme");
		eOrgTheme.setAttribute("ThemeID", getOrgCode());
		eOrgTheme.setAttribute("OrganizationKey", getOrgCode());
		eOrgTheme.setAttribute("IsDefault", true);
		eOrgTheme = eOrgThemeList.createChild("OrgTheme");
		eOrgTheme.setAttribute("ThemeID", "sapphire");
		eOrgTheme.setAttribute("OrganizationKey", getOrgCode());
		eOrgTheme.setAttribute("IsDefault", false);
		return dOrg;
	}
	
	public YFCDocument manageTeam(){
		YFCDocument dTeam = YFCDocument.createDocument("Team");
		YFCElement eTeam = dTeam.getDocumentElement();
		eTeam.setAttribute("TeamId", getOrgCode() + "_TEAM");
		eTeam.setAttribute("TeamKey", getOrgCode() + "_TEAM");
		eTeam.setAttribute("Description", getOrgName() + " Team");
		eTeam.setAttribute("EnterpriseAccessMode", "02");
		eTeam.setAttribute("DocumentTypeAccessMode", "01");
		eTeam.setAttribute("ShipNodeAccessMode", "01");
		eTeam.setAttribute("OrganizationCode", getOrgCode());
		YFCElement eTeamEnterprise = eTeam.getChildElement("TeamEnterpriseList", true).createChild("TeamEnterprise");
		eTeamEnterprise.setAttribute("EnterpriseOrgCode", getOrgCode());
		return dTeam;
	}
	
	public YFCDocument managePasswordPolicy(){
		
		YFCDocument dMultiApi = YFCDocument.createDocument("MultiApi");
		YFCElement eMulti = dMultiApi.getDocumentElement();
		YFCElement eApi = eMulti.createChild("API");
		eApi.setAttribute("Name", "managePasswordPolicy");
		YFCElement eTeam = eApi.createChild("Input").createChild("PasswordPolicy");
		eTeam.setAttribute("Name", getOrgCode() + " Reset");
		eTeam.setAttribute("Description", "Password Reset Policy");
		eTeam.setAttribute("PasswordPolicyKey", getOrgCode() + "_PP1");
		eTeam.setAttribute("Status", "1");
		eTeam.setAttribute("OrganizationCode", getOrgCode());
		eApi = eMulti.createChild("API");
		eApi.setAttribute("Name", "managePasswordRuleCfg");
		YFCElement ePasswordRuleCfg = eApi.createChild("Input").createChild("PasswordRuleCfg");
		ePasswordRuleCfg.setAttribute("PasswordPolicyKey", getOrgCode() + "_PP1");
		ePasswordRuleCfg.setAttribute("Params", "&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;ParamList>&#xa;    &lt;Param Name=&quot;ResetType&quot; Value=&quot;Email&quot;/>&#xa;    &lt;Param Name=&quot;CheckIntervalMinutes&quot; Value=&quot;5&quot;/>&#xa;&lt;/ParamList>&#xa;");
		ePasswordRuleCfg.setAttribute("PasswordRuleDefnKey", "20090207134125272606");
		return dMultiApi;
	}

	
}
