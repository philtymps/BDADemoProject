/**
 * Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unica.emessage.webservices.tms.xsd;

public class Response  implements java.io.Serializable {
    private com.unica.emessage.webservices.tms.xsd.AdvisoryMessage[] advisoryMessages;

    private java.lang.String apiVersion;

    private java.lang.Integer statusCode;

    private java.lang.Integer STATUS_ERROR;

    private java.lang.Integer STATUS_SUCCESS;

    private java.lang.Integer STATUS_WARNING;

    public Response() {
    }

    public Response(
           com.unica.emessage.webservices.tms.xsd.AdvisoryMessage[] advisoryMessages,
           java.lang.String apiVersion,
           java.lang.Integer statusCode,
           java.lang.Integer STATUS_ERROR,
           java.lang.Integer STATUS_SUCCESS,
           java.lang.Integer STATUS_WARNING) {
           this.advisoryMessages = advisoryMessages;
           this.apiVersion = apiVersion;
           this.statusCode = statusCode;
           this.STATUS_ERROR = STATUS_ERROR;
           this.STATUS_SUCCESS = STATUS_SUCCESS;
           this.STATUS_WARNING = STATUS_WARNING;
    }


    /**
     * Gets the advisoryMessages value for this Response.
     * 
     * @return advisoryMessages
     */
    public com.unica.emessage.webservices.tms.xsd.AdvisoryMessage[] getAdvisoryMessages() {
        return advisoryMessages;
    }


    /**
     * Sets the advisoryMessages value for this Response.
     * 
     * @param advisoryMessages
     */
    public void setAdvisoryMessages(com.unica.emessage.webservices.tms.xsd.AdvisoryMessage[] advisoryMessages) {
        this.advisoryMessages = advisoryMessages;
    }

    public com.unica.emessage.webservices.tms.xsd.AdvisoryMessage getAdvisoryMessages(int i) {
        return this.advisoryMessages[i];
    }

    public void setAdvisoryMessages(int i, com.unica.emessage.webservices.tms.xsd.AdvisoryMessage _value) {
        this.advisoryMessages[i] = _value;
    }


    /**
     * Gets the apiVersion value for this Response.
     * 
     * @return apiVersion
     */
    public java.lang.String getApiVersion() {
        return apiVersion;
    }


    /**
     * Sets the apiVersion value for this Response.
     * 
     * @param apiVersion
     */
    public void setApiVersion(java.lang.String apiVersion) {
        this.apiVersion = apiVersion;
    }


    /**
     * Gets the statusCode value for this Response.
     * 
     * @return statusCode
     */
    public java.lang.Integer getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this Response.
     * 
     * @param statusCode
     */
    public void setStatusCode(java.lang.Integer statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the STATUS_ERROR value for this Response.
     * 
     * @return STATUS_ERROR
     */
    public java.lang.Integer getSTATUS_ERROR() {
        return STATUS_ERROR;
    }


    /**
     * Sets the STATUS_ERROR value for this Response.
     * 
     * @param STATUS_ERROR
     */
    public void setSTATUS_ERROR(java.lang.Integer STATUS_ERROR) {
        this.STATUS_ERROR = STATUS_ERROR;
    }


    /**
     * Gets the STATUS_SUCCESS value for this Response.
     * 
     * @return STATUS_SUCCESS
     */
    public java.lang.Integer getSTATUS_SUCCESS() {
        return STATUS_SUCCESS;
    }


    /**
     * Sets the STATUS_SUCCESS value for this Response.
     * 
     * @param STATUS_SUCCESS
     */
    public void setSTATUS_SUCCESS(java.lang.Integer STATUS_SUCCESS) {
        this.STATUS_SUCCESS = STATUS_SUCCESS;
    }


    /**
     * Gets the STATUS_WARNING value for this Response.
     * 
     * @return STATUS_WARNING
     */
    public java.lang.Integer getSTATUS_WARNING() {
        return STATUS_WARNING;
    }


    /**
     * Sets the STATUS_WARNING value for this Response.
     * 
     * @param STATUS_WARNING
     */
    public void setSTATUS_WARNING(java.lang.Integer STATUS_WARNING) {
        this.STATUS_WARNING = STATUS_WARNING;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Response)) return false;
        Response other = (Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.advisoryMessages==null && other.getAdvisoryMessages()==null) || 
             (this.advisoryMessages!=null &&
              java.util.Arrays.equals(this.advisoryMessages, other.getAdvisoryMessages()))) &&
            ((this.apiVersion==null && other.getApiVersion()==null) || 
             (this.apiVersion!=null &&
              this.apiVersion.equals(other.getApiVersion()))) &&
            ((this.statusCode==null && other.getStatusCode()==null) || 
             (this.statusCode!=null &&
              this.statusCode.equals(other.getStatusCode()))) &&
            ((this.STATUS_ERROR==null && other.getSTATUS_ERROR()==null) || 
             (this.STATUS_ERROR!=null &&
              this.STATUS_ERROR.equals(other.getSTATUS_ERROR()))) &&
            ((this.STATUS_SUCCESS==null && other.getSTATUS_SUCCESS()==null) || 
             (this.STATUS_SUCCESS!=null &&
              this.STATUS_SUCCESS.equals(other.getSTATUS_SUCCESS()))) &&
            ((this.STATUS_WARNING==null && other.getSTATUS_WARNING()==null) || 
             (this.STATUS_WARNING!=null &&
              this.STATUS_WARNING.equals(other.getSTATUS_WARNING())));
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
        if (getAdvisoryMessages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdvisoryMessages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdvisoryMessages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getApiVersion() != null) {
            _hashCode += getApiVersion().hashCode();
        }
        if (getStatusCode() != null) {
            _hashCode += getStatusCode().hashCode();
        }
        if (getSTATUS_ERROR() != null) {
            _hashCode += getSTATUS_ERROR().hashCode();
        }
        if (getSTATUS_SUCCESS() != null) {
            _hashCode += getSTATUS_SUCCESS().hashCode();
        }
        if (getSTATUS_WARNING() != null) {
            _hashCode += getSTATUS_WARNING().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("advisoryMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "advisoryMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "AdvisoryMessage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apiVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "apiVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "statusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS_ERROR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "STATUS_ERROR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS_SUCCESS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "STATUS_SUCCESS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS_WARNING");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "STATUS_WARNING"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
