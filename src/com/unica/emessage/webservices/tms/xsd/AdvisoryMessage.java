/**
 * AdvisoryMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unica.emessage.webservices.tms.xsd;

public class AdvisoryMessage  implements java.io.Serializable {
    private java.lang.String detailMessage;

    private java.lang.String message;

    private java.lang.Integer messageCode;

    private java.lang.Integer statusLevel;

    private java.lang.Integer STATUS_LEVEL_ERROR;

    private java.lang.Integer STATUS_LEVEL_INFO;

    private java.lang.Integer STATUS_LEVEL_WARNING;

    public AdvisoryMessage() {
    }

    public AdvisoryMessage(
           java.lang.String detailMessage,
           java.lang.String message,
           java.lang.Integer messageCode,
           java.lang.Integer statusLevel,
           java.lang.Integer STATUS_LEVEL_ERROR,
           java.lang.Integer STATUS_LEVEL_INFO,
           java.lang.Integer STATUS_LEVEL_WARNING) {
           this.detailMessage = detailMessage;
           this.message = message;
           this.messageCode = messageCode;
           this.statusLevel = statusLevel;
           this.STATUS_LEVEL_ERROR = STATUS_LEVEL_ERROR;
           this.STATUS_LEVEL_INFO = STATUS_LEVEL_INFO;
           this.STATUS_LEVEL_WARNING = STATUS_LEVEL_WARNING;
    }


    /**
     * Gets the detailMessage value for this AdvisoryMessage.
     * 
     * @return detailMessage
     */
    public java.lang.String getDetailMessage() {
        return detailMessage;
    }


    /**
     * Sets the detailMessage value for this AdvisoryMessage.
     * 
     * @param detailMessage
     */
    public void setDetailMessage(java.lang.String detailMessage) {
        this.detailMessage = detailMessage;
    }


    /**
     * Gets the message value for this AdvisoryMessage.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this AdvisoryMessage.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the messageCode value for this AdvisoryMessage.
     * 
     * @return messageCode
     */
    public java.lang.Integer getMessageCode() {
        return messageCode;
    }


    /**
     * Sets the messageCode value for this AdvisoryMessage.
     * 
     * @param messageCode
     */
    public void setMessageCode(java.lang.Integer messageCode) {
        this.messageCode = messageCode;
    }


    /**
     * Gets the statusLevel value for this AdvisoryMessage.
     * 
     * @return statusLevel
     */
    public java.lang.Integer getStatusLevel() {
        return statusLevel;
    }


    /**
     * Sets the statusLevel value for this AdvisoryMessage.
     * 
     * @param statusLevel
     */
    public void setStatusLevel(java.lang.Integer statusLevel) {
        this.statusLevel = statusLevel;
    }


    /**
     * Gets the STATUS_LEVEL_ERROR value for this AdvisoryMessage.
     * 
     * @return STATUS_LEVEL_ERROR
     */
    public java.lang.Integer getSTATUS_LEVEL_ERROR() {
        return STATUS_LEVEL_ERROR;
    }


    /**
     * Sets the STATUS_LEVEL_ERROR value for this AdvisoryMessage.
     * 
     * @param STATUS_LEVEL_ERROR
     */
    public void setSTATUS_LEVEL_ERROR(java.lang.Integer STATUS_LEVEL_ERROR) {
        this.STATUS_LEVEL_ERROR = STATUS_LEVEL_ERROR;
    }


    /**
     * Gets the STATUS_LEVEL_INFO value for this AdvisoryMessage.
     * 
     * @return STATUS_LEVEL_INFO
     */
    public java.lang.Integer getSTATUS_LEVEL_INFO() {
        return STATUS_LEVEL_INFO;
    }


    /**
     * Sets the STATUS_LEVEL_INFO value for this AdvisoryMessage.
     * 
     * @param STATUS_LEVEL_INFO
     */
    public void setSTATUS_LEVEL_INFO(java.lang.Integer STATUS_LEVEL_INFO) {
        this.STATUS_LEVEL_INFO = STATUS_LEVEL_INFO;
    }


    /**
     * Gets the STATUS_LEVEL_WARNING value for this AdvisoryMessage.
     * 
     * @return STATUS_LEVEL_WARNING
     */
    public java.lang.Integer getSTATUS_LEVEL_WARNING() {
        return STATUS_LEVEL_WARNING;
    }


    /**
     * Sets the STATUS_LEVEL_WARNING value for this AdvisoryMessage.
     * 
     * @param STATUS_LEVEL_WARNING
     */
    public void setSTATUS_LEVEL_WARNING(java.lang.Integer STATUS_LEVEL_WARNING) {
        this.STATUS_LEVEL_WARNING = STATUS_LEVEL_WARNING;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdvisoryMessage)) return false;
        AdvisoryMessage other = (AdvisoryMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.detailMessage==null && other.getDetailMessage()==null) || 
             (this.detailMessage!=null &&
              this.detailMessage.equals(other.getDetailMessage()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.messageCode==null && other.getMessageCode()==null) || 
             (this.messageCode!=null &&
              this.messageCode.equals(other.getMessageCode()))) &&
            ((this.statusLevel==null && other.getStatusLevel()==null) || 
             (this.statusLevel!=null &&
              this.statusLevel.equals(other.getStatusLevel()))) &&
            ((this.STATUS_LEVEL_ERROR==null && other.getSTATUS_LEVEL_ERROR()==null) || 
             (this.STATUS_LEVEL_ERROR!=null &&
              this.STATUS_LEVEL_ERROR.equals(other.getSTATUS_LEVEL_ERROR()))) &&
            ((this.STATUS_LEVEL_INFO==null && other.getSTATUS_LEVEL_INFO()==null) || 
             (this.STATUS_LEVEL_INFO!=null &&
              this.STATUS_LEVEL_INFO.equals(other.getSTATUS_LEVEL_INFO()))) &&
            ((this.STATUS_LEVEL_WARNING==null && other.getSTATUS_LEVEL_WARNING()==null) || 
             (this.STATUS_LEVEL_WARNING!=null &&
              this.STATUS_LEVEL_WARNING.equals(other.getSTATUS_LEVEL_WARNING())));
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
        if (getDetailMessage() != null) {
            _hashCode += getDetailMessage().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getMessageCode() != null) {
            _hashCode += getMessageCode().hashCode();
        }
        if (getStatusLevel() != null) {
            _hashCode += getStatusLevel().hashCode();
        }
        if (getSTATUS_LEVEL_ERROR() != null) {
            _hashCode += getSTATUS_LEVEL_ERROR().hashCode();
        }
        if (getSTATUS_LEVEL_INFO() != null) {
            _hashCode += getSTATUS_LEVEL_INFO().hashCode();
        }
        if (getSTATUS_LEVEL_WARNING() != null) {
            _hashCode += getSTATUS_LEVEL_WARNING().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdvisoryMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "AdvisoryMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "detailMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "messageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "statusLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS_LEVEL_ERROR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "STATUS_LEVEL_ERROR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS_LEVEL_INFO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "STATUS_LEVEL_INFO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS_LEVEL_WARNING");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "STATUS_LEVEL_WARNING"));
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
