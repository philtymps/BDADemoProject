/**
 * GetOpportunityAccountPrimaryContact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class GetOpportunityAccountPrimaryContact  implements java.io.Serializable {
    private java.lang.String opportunityId;

    private java.lang.String[] opportunityFields;

    private java.lang.String[] accountFields;

    private java.lang.String[] primaryContactFields;

    public GetOpportunityAccountPrimaryContact() {
    }

    public GetOpportunityAccountPrimaryContact(
           java.lang.String opportunityId,
           java.lang.String[] opportunityFields,
           java.lang.String[] accountFields,
           java.lang.String[] primaryContactFields) {
           this.opportunityId = opportunityId;
           this.opportunityFields = opportunityFields;
           this.accountFields = accountFields;
           this.primaryContactFields = primaryContactFields;
    }


    /**
     * Gets the opportunityId value for this GetOpportunityAccountPrimaryContact.
     * 
     * @return opportunityId
     */
    public java.lang.String getOpportunityId() {
        return opportunityId;
    }


    /**
     * Sets the opportunityId value for this GetOpportunityAccountPrimaryContact.
     * 
     * @param opportunityId
     */
    public void setOpportunityId(java.lang.String opportunityId) {
        this.opportunityId = opportunityId;
    }


    /**
     * Gets the opportunityFields value for this GetOpportunityAccountPrimaryContact.
     * 
     * @return opportunityFields
     */
    public java.lang.String[] getOpportunityFields() {
        return opportunityFields;
    }


    /**
     * Sets the opportunityFields value for this GetOpportunityAccountPrimaryContact.
     * 
     * @param opportunityFields
     */
    public void setOpportunityFields(java.lang.String[] opportunityFields) {
        this.opportunityFields = opportunityFields;
    }

    public java.lang.String getOpportunityFields(int i) {
        return this.opportunityFields[i];
    }

    public void setOpportunityFields(int i, java.lang.String _value) {
        this.opportunityFields[i] = _value;
    }


    /**
     * Gets the accountFields value for this GetOpportunityAccountPrimaryContact.
     * 
     * @return accountFields
     */
    public java.lang.String[] getAccountFields() {
        return accountFields;
    }


    /**
     * Sets the accountFields value for this GetOpportunityAccountPrimaryContact.
     * 
     * @param accountFields
     */
    public void setAccountFields(java.lang.String[] accountFields) {
        this.accountFields = accountFields;
    }

    public java.lang.String getAccountFields(int i) {
        return this.accountFields[i];
    }

    public void setAccountFields(int i, java.lang.String _value) {
        this.accountFields[i] = _value;
    }


    /**
     * Gets the primaryContactFields value for this GetOpportunityAccountPrimaryContact.
     * 
     * @return primaryContactFields
     */
    public java.lang.String[] getPrimaryContactFields() {
        return primaryContactFields;
    }


    /**
     * Sets the primaryContactFields value for this GetOpportunityAccountPrimaryContact.
     * 
     * @param primaryContactFields
     */
    public void setPrimaryContactFields(java.lang.String[] primaryContactFields) {
        this.primaryContactFields = primaryContactFields;
    }

    public java.lang.String getPrimaryContactFields(int i) {
        return this.primaryContactFields[i];
    }

    public void setPrimaryContactFields(int i, java.lang.String _value) {
        this.primaryContactFields[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOpportunityAccountPrimaryContact)) return false;
        GetOpportunityAccountPrimaryContact other = (GetOpportunityAccountPrimaryContact) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.opportunityId==null && other.getOpportunityId()==null) || 
             (this.opportunityId!=null &&
              this.opportunityId.equals(other.getOpportunityId()))) &&
            ((this.opportunityFields==null && other.getOpportunityFields()==null) || 
             (this.opportunityFields!=null &&
              java.util.Arrays.equals(this.opportunityFields, other.getOpportunityFields()))) &&
            ((this.accountFields==null && other.getAccountFields()==null) || 
             (this.accountFields!=null &&
              java.util.Arrays.equals(this.accountFields, other.getAccountFields()))) &&
            ((this.primaryContactFields==null && other.getPrimaryContactFields()==null) || 
             (this.primaryContactFields!=null &&
              java.util.Arrays.equals(this.primaryContactFields, other.getPrimaryContactFields())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getOpportunityId() != null) {
            _hashCode += getOpportunityId().hashCode();
        }
        if (getOpportunityFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOpportunityFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOpportunityFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAccountFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccountFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccountFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrimaryContactFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrimaryContactFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrimaryContactFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetOpportunityAccountPrimaryContact.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">getOpportunityAccountPrimaryContact"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opportunityId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "opportunityId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opportunityFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "opportunityFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "accountFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryContactFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "primaryContactFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
