/**
 * UpsertQuoteAndPrimaryQuoteLines.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class UpsertQuoteAndPrimaryQuoteLines  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote;

    private java.lang.Boolean applyUpdateEvenIfTimestampUnchanged;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] quoteLines;

    public UpsertQuoteAndPrimaryQuoteLines() {
    }

    public UpsertQuoteAndPrimaryQuoteLines(
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote,
           java.lang.Boolean applyUpdateEvenIfTimestampUnchanged,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] quoteLines) {
           this.sciQuote = sciQuote;
           this.applyUpdateEvenIfTimestampUnchanged = applyUpdateEvenIfTimestampUnchanged;
           this.quoteLines = quoteLines;
    }


    /**
     * Gets the sciQuote value for this UpsertQuoteAndPrimaryQuoteLines.
     * 
     * @return sciQuote
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c getSciQuote() {
        return sciQuote;
    }


    /**
     * Sets the sciQuote value for this UpsertQuoteAndPrimaryQuoteLines.
     * 
     * @param sciQuote
     */
    public void setSciQuote(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote) {
        this.sciQuote = sciQuote;
    }


    /**
     * Gets the applyUpdateEvenIfTimestampUnchanged value for this UpsertQuoteAndPrimaryQuoteLines.
     * 
     * @return applyUpdateEvenIfTimestampUnchanged
     */
    public java.lang.Boolean getApplyUpdateEvenIfTimestampUnchanged() {
        return applyUpdateEvenIfTimestampUnchanged;
    }


    /**
     * Sets the applyUpdateEvenIfTimestampUnchanged value for this UpsertQuoteAndPrimaryQuoteLines.
     * 
     * @param applyUpdateEvenIfTimestampUnchanged
     */
    public void setApplyUpdateEvenIfTimestampUnchanged(java.lang.Boolean applyUpdateEvenIfTimestampUnchanged) {
        this.applyUpdateEvenIfTimestampUnchanged = applyUpdateEvenIfTimestampUnchanged;
    }


    /**
     * Gets the quoteLines value for this UpsertQuoteAndPrimaryQuoteLines.
     * 
     * @return quoteLines
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] getQuoteLines() {
        return quoteLines;
    }


    /**
     * Sets the quoteLines value for this UpsertQuoteAndPrimaryQuoteLines.
     * 
     * @param quoteLines
     */
    public void setQuoteLines(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] quoteLines) {
        this.quoteLines = quoteLines;
    }

    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c getQuoteLines(int i) {
        return this.quoteLines[i];
    }

    public void setQuoteLines(int i, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c _value) {
        this.quoteLines[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpsertQuoteAndPrimaryQuoteLines)) return false;
        UpsertQuoteAndPrimaryQuoteLines other = (UpsertQuoteAndPrimaryQuoteLines) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sciQuote==null && other.getSciQuote()==null) || 
             (this.sciQuote!=null &&
              this.sciQuote.equals(other.getSciQuote()))) &&
            ((this.applyUpdateEvenIfTimestampUnchanged==null && other.getApplyUpdateEvenIfTimestampUnchanged()==null) || 
             (this.applyUpdateEvenIfTimestampUnchanged!=null &&
              this.applyUpdateEvenIfTimestampUnchanged.equals(other.getApplyUpdateEvenIfTimestampUnchanged()))) &&
            ((this.quoteLines==null && other.getQuoteLines()==null) || 
             (this.quoteLines!=null &&
              java.util.Arrays.equals(this.quoteLines, other.getQuoteLines())));
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
        if (getSciQuote() != null) {
            _hashCode += getSciQuote().hashCode();
        }
        if (getApplyUpdateEvenIfTimestampUnchanged() != null) {
            _hashCode += getApplyUpdateEvenIfTimestampUnchanged().hashCode();
        }
        if (getQuoteLines() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQuoteLines());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQuoteLines(), i);
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
        new org.apache.axis.description.TypeDesc(UpsertQuoteAndPrimaryQuoteLines.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">upsertQuoteAndPrimaryQuoteLines"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sciQuote");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sciQuote"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applyUpdateEvenIfTimestampUnchanged");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "applyUpdateEvenIfTimestampUnchanged"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quoteLines");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "quoteLines"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__PrimaryQuoteLine__c"));
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
