/**
 * TMSPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unica.emessage.webservices.tms.soap;

public interface TMSPortType extends java.rmi.Remote {
    public com.unica.emessage.webservices.tms.soap.SendMailingResponse sendMailing(com.unica.emessage.webservices.tms.soap.SendMailing parameters, java.lang.String userName_header, java.lang.String password_header) throws java.rmi.RemoteException;
}
