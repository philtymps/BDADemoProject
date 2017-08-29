/**
 * UserLicense.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class UserLicense  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private java.lang.String licenseDefinitionKey;

    private java.lang.String name;

    private java.util.Calendar systemModstamp;

    public UserLicense() {
    }

    public UserLicense(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           java.lang.String licenseDefinitionKey,
           java.lang.String name,
           java.util.Calendar systemModstamp) {
        super(
            fieldsToNull,
            id);
        this.licenseDefinitionKey = licenseDefinitionKey;
        this.name = name;
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the licenseDefinitionKey value for this UserLicense.
     * 
     * @return licenseDefinitionKey
     */
    public java.lang.String getLicenseDefinitionKey() {
        return licenseDefinitionKey;
    }


    /**
     * Sets the licenseDefinitionKey value for this UserLicense.
     * 
     * @param licenseDefinitionKey
     */
    public void setLicenseDefinitionKey(java.lang.String licenseDefinitionKey) {
        this.licenseDefinitionKey = licenseDefinitionKey;
    }


    /**
     * Gets the name value for this UserLicense.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this UserLicense.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the systemModstamp value for this UserLicense.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this UserLicense.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserLicense)) return false;
        UserLicense other = (UserLicense) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.licenseDefinitionKey==null && other.getLicenseDefinitionKey()==null) || 
             (this.licenseDefinitionKey!=null &&
              this.licenseDefinitionKey.equals(other.getLicenseDefinitionKey()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getLicenseDefinitionKey() != null) {
            _hashCode += getLicenseDefinitionKey().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserLicense.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UserLicense"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseDefinitionKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LicenseDefinitionKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemModstamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SystemModstamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
