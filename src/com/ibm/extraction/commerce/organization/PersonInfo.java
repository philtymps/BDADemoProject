package com.ibm.extraction.commerce.organization;

import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public class PersonInfo {

	private String add1 = "", add2 = "", city = "", state = "", country = "", zip = "", phone = "", fax = "", email = "", firstname = "", lastname = "";
	public PersonInfo (String add1, String add2, String city, String state, String country, String zip, String phone, String fax, String email, String firstname, String lastname){
		this.add1 = add1;
		this.add2 = add2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public void createPersonInfo (String sNodeName, YFCElement eParent){
		YFCElement ePerson = eParent.createChild(sNodeName);
		if (!YFCCommon.isVoid(add1)){
			ePerson.setAttribute("AddressLine1", add1);
		}
		if (!YFCCommon.isVoid(add2)){
			ePerson.setAttribute("AddressLine2", add2);
		}
		if (!YFCCommon.isVoid(city)){
			ePerson.setAttribute("City", city);
		}
		if (!YFCCommon.isVoid(state)){
			ePerson.setAttribute("State", state);
		}
		if (!YFCCommon.isVoid(country)){
			ePerson.setAttribute("Country", country);
		}
		if (!YFCCommon.isVoid(zip)){
			ePerson.setAttribute("ZipCode", zip);
		}
		if (!YFCCommon.isVoid(phone)){
			ePerson.setAttribute("DayPhone", phone);
		}
		if (!YFCCommon.isVoid(fax)){
			ePerson.setAttribute("DayFax", fax);
		}
		if (!YFCCommon.isVoid(email)){
			ePerson.setAttribute("EMailID", email);
		}
		if (!YFCCommon.isVoid(firstname)){
			ePerson.setAttribute("FirstName", firstname);
		}
		if (!YFCCommon.isVoid(lastname)){
			ePerson.setAttribute("LastName", lastname);
		}
	}
}
