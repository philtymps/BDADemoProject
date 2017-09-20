/**
 * DescribeSearchLayoutResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.partner;

public class DescribeSearchLayoutResult  implements java.io.Serializable {
    private java.lang.String label;

    private int limitRows;

    private com.sforce.soap.partner.DescribeColumn[] searchColumns;

    public DescribeSearchLayoutResult() {
    }

    public DescribeSearchLayoutResult(
           java.lang.String label,
           int limitRows,
           com.sforce.soap.partner.DescribeColumn[] searchColumns) {
           this.label = label;
           this.limitRows = limitRows;
           this.searchColumns = searchColumns;
    }


    /**
     * Gets the label value for this DescribeSearchLayoutResult.
     * 
     * @return label
     */
    public java.lang.String getLabel() {
        return label;
    }


    /**
     * Sets the label value for this DescribeSearchLayoutResult.
     * 
     * @param label
     */
    public void setLabel(java.lang.String label) {
        this.label = label;
    }


    /**
     * Gets the limitRows value for this DescribeSearchLayoutResult.
     * 
     * @return limitRows
     */
    public int getLimitRows() {
        return limitRows;
    }


    /**
     * Sets the limitRows value for this DescribeSearchLayoutResult.
     * 
     * @param limitRows
     */
    public void setLimitRows(int limitRows) {
        this.limitRows = limitRows;
    }


    /**
     * Gets the searchColumns value for this DescribeSearchLayoutResult.
     * 
     * @return searchColumns
     */
    public com.sforce.soap.partner.DescribeColumn[] getSearchColumns() {
        return searchColumns;
    }


    /**
     * Sets the searchColumns value for this DescribeSearchLayoutResult.
     * 
     * @param searchColumns
     */
    public void setSearchColumns(com.sforce.soap.partner.DescribeColumn[] searchColumns) {
        this.searchColumns = searchColumns;
    }

    public com.sforce.soap.partner.DescribeColumn getSearchColumns(int i) {
        return this.searchColumns[i];
    }

    public void setSearchColumns(int i, com.sforce.soap.partner.DescribeColumn _value) {
        this.searchColumns[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeSearchLayoutResult)) return false;
        DescribeSearchLayoutResult other = (DescribeSearchLayoutResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel()))) &&
            this.limitRows == other.getLimitRows() &&
            ((this.searchColumns==null && other.getSearchColumns()==null) || 
             (this.searchColumns!=null &&
              java.util.Arrays.equals(this.searchColumns, other.getSearchColumns())));
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
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        _hashCode += getLimitRows();
        if (getSearchColumns() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchColumns());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchColumns(), i);
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
        new org.apache.axis.description.TypeDesc(DescribeSearchLayoutResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DescribeSearchLayoutResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitRows");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "limitRows"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchColumns");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "searchColumns"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DescribeColumn"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
