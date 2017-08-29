/**
 * TMS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unica.emessage.webservices.tms.soap;

public interface TMS extends javax.xml.rpc.Service {
    public java.lang.String getTMSSOAP11port_httpAddress();

    public com.unica.emessage.webservices.tms.soap.TMSPortType getTMSSOAP11port_http() throws javax.xml.rpc.ServiceException;

    public com.unica.emessage.webservices.tms.soap.TMSPortType getTMSSOAP11port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
