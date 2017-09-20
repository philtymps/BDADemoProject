/**
 * DescribeLayoutButton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.partner;

public class DescribeLayoutButton  implements java.io.Serializable {
    private boolean custom;

    private com.sforce.soap.partner.DescribeIcon[] icons;

    private java.lang.String label;

    private java.lang.String name;

    public DescribeLayoutButton() {
    }

    public DescribeLayoutButton(
           boolean custom,
           com.sforce.soap.partner.DescribeIcon[] icons,
           java.lang.String label,
           java.lang.String name) {
           this.custom = custom;
           this.icons = icons;
           this.label = label;
           this.name = name;
    }


    /**
     * Gets the custom value for this DescribeLayoutButton.
     * 
     * @return custom
     */
    public boolean isCustom() {
        return custom;
    }


    /**
     * Sets the custom value for this DescribeLayoutButton.
     * 
     * @param custom
     */
    public void setCustom(boolean custom) {
        this.custom = custom;
    }


    /**
     * Gets the icons value for this DescribeLayoutButton.
     * 
     * @return icons
     */
    public com.sforce.soap.partner.DescribeIcon[] getIcons() {
        return icons;
    }


    /**
     * Sets the icons value for this DescribeLayoutButton.
     * 
     * @param icons
     */
    public void setIcons(com.sforce.soap.partner.DescribeIcon[] icons) {
        this.icons = icons;
    }

    public com.sforce.soap.partner.DescribeIcon getIcons(int i) {
        return this.icons[i];
    }

    public void setIcons(int i, com.sforce.soap.partner.DescribeIcon _value) {
        this.icons[i] = _value;
    }


    /**
     * Gets the label value for this DescribeLayoutButton.
     * 
     * @return label
     */
    public java.lang.String getLabel() {
        return label;
    }


    /**
     * Sets the label value for this DescribeLayoutButton.
     * 
     * @param label
     */
    public void setLabel(java.lang.String label) {
        this.label = label;
    }


    /**
     * Gets the name value for this DescribeLayoutButton.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DescribeLayoutButton.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeLayoutButton)) return false;
        DescribeLayoutButton other = (DescribeLayoutButton) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.custom == other.isCustom() &&
            ((this.icons==null && other.getIcons()==null) || 
             (this.icons!=null &&
              java.util.Arrays.equals(this.icons, other.getIcons()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName())));
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
        _hashCode += (isCustom() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getIcons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIcons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIcons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DescribeLayoutButton.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DescribeLayoutButton"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custom");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "custom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("icons");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "icons"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DescribeIcon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
