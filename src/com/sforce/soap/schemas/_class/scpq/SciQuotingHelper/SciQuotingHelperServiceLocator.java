/**
 * SciQuotingHelperServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class SciQuotingHelperServiceLocator extends org.apache.axis.client.Service implements com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperService {

    public SciQuotingHelperServiceLocator() {
    }


    public SciQuotingHelperServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SciQuotingHelperServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SciQuotingHelper
    private java.lang.String SciQuotingHelper_address = "https://na15.salesforce.com/services/Soap/class/scpq/SciQuotingHelper";

    public java.lang.String getSciQuotingHelperAddress() {
        return SciQuotingHelper_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SciQuotingHelperWSDDServiceName = "SciQuotingHelper";

    public java.lang.String getSciQuotingHelperWSDDServiceName() {
        return SciQuotingHelperWSDDServiceName;
    }

    public void setSciQuotingHelperWSDDServiceName(java.lang.String name) {
        SciQuotingHelperWSDDServiceName = name;
    }

    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType getSciQuotingHelper() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SciQuotingHelper_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSciQuotingHelper(endpoint);
    }

    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType getSciQuotingHelper(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperBindingStub _stub = new com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperBindingStub(portAddress, this);
            _stub.setPortName(getSciQuotingHelperWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSciQuotingHelperEndpointAddress(java.lang.String address) {
        SciQuotingHelper_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperBindingStub _stub = new com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperBindingStub(new java.net.URL(SciQuotingHelper_address), this);
                _stub.setPortName(getSciQuotingHelperWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SciQuotingHelper".equals(inputPortName)) {
            return getSciQuotingHelper();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SciQuotingHelperService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SciQuotingHelper"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SciQuotingHelper".equals(portName)) {
            setSciQuotingHelperEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
