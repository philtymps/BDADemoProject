package com.unica.emessage.webservices.tms.soap;

public class TMSPortTypeProxy implements com.unica.emessage.webservices.tms.soap.TMSPortType {
  private String _endpoint = null;
  private com.unica.emessage.webservices.tms.soap.TMSPortType tMSPortType = null;
  
  public TMSPortTypeProxy() {
    _initTMSPortTypeProxy();
  }
  
  public TMSPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initTMSPortTypeProxy();
  }
  
  private void _initTMSPortTypeProxy() {
    try {
      tMSPortType = (new com.unica.emessage.webservices.tms.soap.TMSLocator()).getTMSSOAP11port_http();
      if (tMSPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)tMSPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)tMSPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (tMSPortType != null)
      ((javax.xml.rpc.Stub)tMSPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.unica.emessage.webservices.tms.soap.TMSPortType getTMSPortType() {
    if (tMSPortType == null)
      _initTMSPortTypeProxy();
    return tMSPortType;
  }
  
  public com.unica.emessage.webservices.tms.soap.SendMailingResponse sendMailing(com.unica.emessage.webservices.tms.soap.SendMailing parameters, java.lang.String userName_header, java.lang.String password_header) throws java.rmi.RemoteException{
    if (tMSPortType == null)
      _initTMSPortTypeProxy();
    return tMSPortType.sendMailing(parameters, userName_header, password_header);
  }
  
  
}