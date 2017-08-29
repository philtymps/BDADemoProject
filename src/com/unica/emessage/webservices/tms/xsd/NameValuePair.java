/**
 * NameValuePair.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unica.emessage.webservices.tms.xsd;

public class NameValuePair  implements java.io.Serializable {
    private java.lang.String name;

    private java.util.Calendar valueAsDate;

    private java.lang.Double valueAsNumeric;

    private java.lang.String valueAsString;

    private java.lang.String valueDataType;

    private java.lang.String DATA_TYPE_DATETIME;

    private java.lang.String DATA_TYPE_NUMERIC;

    private java.lang.String DATA_TYPE_STRING;

    public NameValuePair() {
    }

    public NameValuePair(
           java.lang.String name,
           java.util.Calendar valueAsDate,
           java.lang.Double valueAsNumeric,
           java.lang.String valueAsString,
           java.lang.String valueDataType,
           java.lang.String DATA_TYPE_DATETIME,
           java.lang.String DATA_TYPE_NUMERIC,
           java.lang.String DATA_TYPE_STRING) {
           this.name = name;
           this.valueAsDate = valueAsDate;
           this.valueAsNumeric = valueAsNumeric;
           this.valueAsString = valueAsString;
           this.valueDataType = valueDataType;
           this.DATA_TYPE_DATETIME = DATA_TYPE_DATETIME;
           this.DATA_TYPE_NUMERIC = DATA_TYPE_NUMERIC;
           this.DATA_TYPE_STRING = DATA_TYPE_STRING;
    }


    /**
     * Gets the name value for this NameValuePair.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this NameValuePair.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the valueAsDate value for this NameValuePair.
     * 
     * @return valueAsDate
     */
    public java.util.Calendar getValueAsDate() {
        return valueAsDate;
    }


    /**
     * Sets the valueAsDate value for this NameValuePair.
     * 
     * @param valueAsDate
     */
    public void setValueAsDate(java.util.Calendar valueAsDate) {
        this.valueAsDate = valueAsDate;
    }


    /**
     * Gets the valueAsNumeric value for this NameValuePair.
     * 
     * @return valueAsNumeric
     */
    public java.lang.Double getValueAsNumeric() {
        return valueAsNumeric;
    }


    /**
     * Sets the valueAsNumeric value for this NameValuePair.
     * 
     * @param valueAsNumeric
     */
    public void setValueAsNumeric(java.lang.Double valueAsNumeric) {
        this.valueAsNumeric = valueAsNumeric;
    }


    /**
     * Gets the valueAsString value for this NameValuePair.
     * 
     * @return valueAsString
     */
    public java.lang.String getValueAsString() {
        return valueAsString;
    }


    /**
     * Sets the valueAsString value for this NameValuePair.
     * 
     * @param valueAsString
     */
    public void setValueAsString(java.lang.String valueAsString) {
        this.valueAsString = valueAsString;
    }


    /**
     * Gets the valueDataType value for this NameValuePair.
     * 
     * @return valueDataType
     */
    public java.lang.String getValueDataType() {
        return valueDataType;
    }


    /**
     * Sets the valueDataType value for this NameValuePair.
     * 
     * @param valueDataType
     */
    public void setValueDataType(java.lang.String valueDataType) {
        this.valueDataType = valueDataType;
    }


    /**
     * Gets the DATA_TYPE_DATETIME value for this NameValuePair.
     * 
     * @return DATA_TYPE_DATETIME
     */
    public java.lang.String getDATA_TYPE_DATETIME() {
        return DATA_TYPE_DATETIME;
    }


    /**
     * Sets the DATA_TYPE_DATETIME value for this NameValuePair.
     * 
     * @param DATA_TYPE_DATETIME
     */
    public void setDATA_TYPE_DATETIME(java.lang.String DATA_TYPE_DATETIME) {
        this.DATA_TYPE_DATETIME = DATA_TYPE_DATETIME;
    }


    /**
     * Gets the DATA_TYPE_NUMERIC value for this NameValuePair.
     * 
     * @return DATA_TYPE_NUMERIC
     */
    public java.lang.String getDATA_TYPE_NUMERIC() {
        return DATA_TYPE_NUMERIC;
    }


    /**
     * Sets the DATA_TYPE_NUMERIC value for this NameValuePair.
     * 
     * @param DATA_TYPE_NUMERIC
     */
    public void setDATA_TYPE_NUMERIC(java.lang.String DATA_TYPE_NUMERIC) {
        this.DATA_TYPE_NUMERIC = DATA_TYPE_NUMERIC;
    }


    /**
     * Gets the DATA_TYPE_STRING value for this NameValuePair.
     * 
     * @return DATA_TYPE_STRING
     */
    public java.lang.String getDATA_TYPE_STRING() {
        return DATA_TYPE_STRING;
    }


    /**
     * Sets the DATA_TYPE_STRING value for this NameValuePair.
     * 
     * @param DATA_TYPE_STRING
     */
    public void setDATA_TYPE_STRING(java.lang.String DATA_TYPE_STRING) {
        this.DATA_TYPE_STRING = DATA_TYPE_STRING;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NameValuePair)) return false;
        NameValuePair other = (NameValuePair) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.valueAsDate==null && other.getValueAsDate()==null) || 
             (this.valueAsDate!=null &&
              this.valueAsDate.equals(other.getValueAsDate()))) &&
            ((this.valueAsNumeric==null && other.getValueAsNumeric()==null) || 
             (this.valueAsNumeric!=null &&
              this.valueAsNumeric.equals(other.getValueAsNumeric()))) &&
            ((this.valueAsString==null && other.getValueAsString()==null) || 
             (this.valueAsString!=null &&
              this.valueAsString.equals(other.getValueAsString()))) &&
            ((this.valueDataType==null && other.getValueDataType()==null) || 
             (this.valueDataType!=null &&
              this.valueDataType.equals(other.getValueDataType()))) &&
            ((this.DATA_TYPE_DATETIME==null && other.getDATA_TYPE_DATETIME()==null) || 
             (this.DATA_TYPE_DATETIME!=null &&
              this.DATA_TYPE_DATETIME.equals(other.getDATA_TYPE_DATETIME()))) &&
            ((this.DATA_TYPE_NUMERIC==null && other.getDATA_TYPE_NUMERIC()==null) || 
             (this.DATA_TYPE_NUMERIC!=null &&
              this.DATA_TYPE_NUMERIC.equals(other.getDATA_TYPE_NUMERIC()))) &&
            ((this.DATA_TYPE_STRING==null && other.getDATA_TYPE_STRING()==null) || 
             (this.DATA_TYPE_STRING!=null &&
              this.DATA_TYPE_STRING.equals(other.getDATA_TYPE_STRING())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getValueAsDate() != null) {
            _hashCode += getValueAsDate().hashCode();
        }
        if (getValueAsNumeric() != null) {
            _hashCode += getValueAsNumeric().hashCode();
        }
        if (getValueAsString() != null) {
            _hashCode += getValueAsString().hashCode();
        }
        if (getValueDataType() != null) {
            _hashCode += getValueDataType().hashCode();
        }
        if (getDATA_TYPE_DATETIME() != null) {
            _hashCode += getDATA_TYPE_DATETIME().hashCode();
        }
        if (getDATA_TYPE_NUMERIC() != null) {
            _hashCode += getDATA_TYPE_NUMERIC().hashCode();
        }
        if (getDATA_TYPE_STRING() != null) {
            _hashCode += getDATA_TYPE_STRING().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NameValuePair.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "NameValuePair"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueAsDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "valueAsDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueAsNumeric");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "valueAsNumeric"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueAsString");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "valueAsString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueDataType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "valueDataType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATA_TYPE_DATETIME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "DATA_TYPE_DATETIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATA_TYPE_NUMERIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "DATA_TYPE_NUMERIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATA_TYPE_STRING");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "DATA_TYPE_STRING"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
