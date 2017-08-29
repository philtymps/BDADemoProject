/**
 * SendMailing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unica.emessage.webservices.tms.soap;

public class SendMailing  implements java.io.Serializable {
    private java.lang.String mailingCode;

    private com.unica.emessage.webservices.tms.xsd.NameValuePair[] audienceId;

    private com.unica.emessage.webservices.tms.xsd.NameValuePair[] fields;

    private java.lang.String[] cellCodes;

    private com.unica.emessage.webservices.tms.xsd.NameValuePair[] additionalOptions;

    private com.unica.emessage.webservices.tms.xsd.Attachment[] attachments;

    private com.unica.emessage.webservices.tms.xsd.NameValuePair[] trackingFields;

    private java.lang.String locale;

    public SendMailing() {
    }

    public SendMailing(
           java.lang.String mailingCode,
           com.unica.emessage.webservices.tms.xsd.NameValuePair[] audienceId,
           com.unica.emessage.webservices.tms.xsd.NameValuePair[] fields,
           java.lang.String[] cellCodes,
           com.unica.emessage.webservices.tms.xsd.NameValuePair[] additionalOptions,
           com.unica.emessage.webservices.tms.xsd.Attachment[] attachments,
           com.unica.emessage.webservices.tms.xsd.NameValuePair[] trackingFields,
           java.lang.String locale) {
           this.mailingCode = mailingCode;
           this.audienceId = audienceId;
           this.fields = fields;
           this.cellCodes = cellCodes;
           this.additionalOptions = additionalOptions;
           this.attachments = attachments;
           this.trackingFields = trackingFields;
           this.locale = locale;
    }


    /**
     * Gets the mailingCode value for this SendMailing.
     * 
     * @return mailingCode
     */
    public java.lang.String getMailingCode() {
        return mailingCode;
    }


    /**
     * Sets the mailingCode value for this SendMailing.
     * 
     * @param mailingCode
     */
    public void setMailingCode(java.lang.String mailingCode) {
        this.mailingCode = mailingCode;
    }


    /**
     * Gets the audienceId value for this SendMailing.
     * 
     * @return audienceId
     */
    public com.unica.emessage.webservices.tms.xsd.NameValuePair[] getAudienceId() {
        return audienceId;
    }


    /**
     * Sets the audienceId value for this SendMailing.
     * 
     * @param audienceId
     */
    public void setAudienceId(com.unica.emessage.webservices.tms.xsd.NameValuePair[] audienceId) {
        this.audienceId = audienceId;
    }

    public com.unica.emessage.webservices.tms.xsd.NameValuePair getAudienceId(int i) {
        return this.audienceId[i];
    }

    public void setAudienceId(int i, com.unica.emessage.webservices.tms.xsd.NameValuePair _value) {
        this.audienceId[i] = _value;
    }


    /**
     * Gets the fields value for this SendMailing.
     * 
     * @return fields
     */
    public com.unica.emessage.webservices.tms.xsd.NameValuePair[] getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this SendMailing.
     * 
     * @param fields
     */
    public void setFields(com.unica.emessage.webservices.tms.xsd.NameValuePair[] fields) {
        this.fields = fields;
    }

    public com.unica.emessage.webservices.tms.xsd.NameValuePair getFields(int i) {
        return this.fields[i];
    }

    public void setFields(int i, com.unica.emessage.webservices.tms.xsd.NameValuePair _value) {
        this.fields[i] = _value;
    }


    /**
     * Gets the cellCodes value for this SendMailing.
     * 
     * @return cellCodes
     */
    public java.lang.String[] getCellCodes() {
        return cellCodes;
    }


    /**
     * Sets the cellCodes value for this SendMailing.
     * 
     * @param cellCodes
     */
    public void setCellCodes(java.lang.String[] cellCodes) {
        this.cellCodes = cellCodes;
    }

    public java.lang.String getCellCodes(int i) {
        return this.cellCodes[i];
    }

    public void setCellCodes(int i, java.lang.String _value) {
        this.cellCodes[i] = _value;
    }


    /**
     * Gets the additionalOptions value for this SendMailing.
     * 
     * @return additionalOptions
     */
    public com.unica.emessage.webservices.tms.xsd.NameValuePair[] getAdditionalOptions() {
        return additionalOptions;
    }


    /**
     * Sets the additionalOptions value for this SendMailing.
     * 
     * @param additionalOptions
     */
    public void setAdditionalOptions(com.unica.emessage.webservices.tms.xsd.NameValuePair[] additionalOptions) {
        this.additionalOptions = additionalOptions;
    }

    public com.unica.emessage.webservices.tms.xsd.NameValuePair getAdditionalOptions(int i) {
        return this.additionalOptions[i];
    }

    public void setAdditionalOptions(int i, com.unica.emessage.webservices.tms.xsd.NameValuePair _value) {
        this.additionalOptions[i] = _value;
    }


    /**
     * Gets the attachments value for this SendMailing.
     * 
     * @return attachments
     */
    public com.unica.emessage.webservices.tms.xsd.Attachment[] getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this SendMailing.
     * 
     * @param attachments
     */
    public void setAttachments(com.unica.emessage.webservices.tms.xsd.Attachment[] attachments) {
        this.attachments = attachments;
    }

    public com.unica.emessage.webservices.tms.xsd.Attachment getAttachments(int i) {
        return this.attachments[i];
    }

    public void setAttachments(int i, com.unica.emessage.webservices.tms.xsd.Attachment _value) {
        this.attachments[i] = _value;
    }


    /**
     * Gets the trackingFields value for this SendMailing.
     * 
     * @return trackingFields
     */
    public com.unica.emessage.webservices.tms.xsd.NameValuePair[] getTrackingFields() {
        return trackingFields;
    }


    /**
     * Sets the trackingFields value for this SendMailing.
     * 
     * @param trackingFields
     */
    public void setTrackingFields(com.unica.emessage.webservices.tms.xsd.NameValuePair[] trackingFields) {
        this.trackingFields = trackingFields;
    }

    public com.unica.emessage.webservices.tms.xsd.NameValuePair getTrackingFields(int i) {
        return this.trackingFields[i];
    }

    public void setTrackingFields(int i, com.unica.emessage.webservices.tms.xsd.NameValuePair _value) {
        this.trackingFields[i] = _value;
    }


    /**
     * Gets the locale value for this SendMailing.
     * 
     * @return locale
     */
    public java.lang.String getLocale() {
        return locale;
    }


    /**
     * Sets the locale value for this SendMailing.
     * 
     * @param locale
     */
    public void setLocale(java.lang.String locale) {
        this.locale = locale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SendMailing)) return false;
        SendMailing other = (SendMailing) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mailingCode==null && other.getMailingCode()==null) || 
             (this.mailingCode!=null &&
              this.mailingCode.equals(other.getMailingCode()))) &&
            ((this.audienceId==null && other.getAudienceId()==null) || 
             (this.audienceId!=null &&
              java.util.Arrays.equals(this.audienceId, other.getAudienceId()))) &&
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              java.util.Arrays.equals(this.fields, other.getFields()))) &&
            ((this.cellCodes==null && other.getCellCodes()==null) || 
             (this.cellCodes!=null &&
              java.util.Arrays.equals(this.cellCodes, other.getCellCodes()))) &&
            ((this.additionalOptions==null && other.getAdditionalOptions()==null) || 
             (this.additionalOptions!=null &&
              java.util.Arrays.equals(this.additionalOptions, other.getAdditionalOptions()))) &&
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              java.util.Arrays.equals(this.attachments, other.getAttachments()))) &&
            ((this.trackingFields==null && other.getTrackingFields()==null) || 
             (this.trackingFields!=null &&
              java.util.Arrays.equals(this.trackingFields, other.getTrackingFields()))) &&
            ((this.locale==null && other.getLocale()==null) || 
             (this.locale!=null &&
              this.locale.equals(other.getLocale())));
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
        if (getMailingCode() != null) {
            _hashCode += getMailingCode().hashCode();
        }
        if (getAudienceId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAudienceId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAudienceId(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCellCodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCellCodes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCellCodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAdditionalOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdditionalOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdditionalOptions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAttachments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttachments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttachments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTrackingFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrackingFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrackingFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLocale() != null) {
            _hashCode += getLocale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SendMailing.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", ">sendMailing"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "mailingCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("audienceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "audienceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cellCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "cellCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "additionalOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "attachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "Attachment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "trackingFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tms.webservices.emessage.unica.com/xsd", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.tms.webservices.emessage.unica.com", "locale"));
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
