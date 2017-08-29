package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class SciQuotingHelperPortTypeProxy implements com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType {
  private String _endpoint = null;
  private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType sciQuotingHelperPortType = null;
  
  public SciQuotingHelperPortTypeProxy() {
    _initSciQuotingHelperPortTypeProxy();
  }
  
  public SciQuotingHelperPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initSciQuotingHelperPortTypeProxy();
  }
  
  private void _initSciQuotingHelperPortTypeProxy() {
    try {
      sciQuotingHelperPortType = (new com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperServiceLocator()).getSciQuotingHelper();
      if (sciQuotingHelperPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sciQuotingHelperPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sciQuotingHelperPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sciQuotingHelperPortType != null)
      ((javax.xml.rpc.Stub)sciQuotingHelperPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType getSciQuotingHelperPortType() {
    if (sciQuotingHelperPortType == null)
      _initSciQuotingHelperPortTypeProxy();
    return sciQuotingHelperPortType;
  }
  
  public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[] getOpportunityAccountPrimaryContact(java.lang.String opportunityId, java.lang.String[] opportunityFields, java.lang.String[] accountFields, java.lang.String[] primaryContactFields) throws java.rmi.RemoteException{
    if (sciQuotingHelperPortType == null)
      _initSciQuotingHelperPortTypeProxy();
    return sciQuotingHelperPortType.getOpportunityAccountPrimaryContact(opportunityId, opportunityFields, accountFields, primaryContactFields);
  }
  
  public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c upsertQuote(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityLineItem[] quoteLines) throws java.rmi.RemoteException{
    if (sciQuotingHelperPortType == null)
      _initSciQuotingHelperPortTypeProxy();
    return sciQuotingHelperPortType.upsertQuote(sciQuote, quoteLines);
  }
  
  public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c upsertQuoteAndPrimaryQuoteLines(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote, boolean applyUpdateEvenIfTimestampUnchanged, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] quoteLines) throws java.rmi.RemoteException{
    if (sciQuotingHelperPortType == null)
      _initSciQuotingHelperPortTypeProxy();
    return sciQuotingHelperPortType.upsertQuoteAndPrimaryQuoteLines(sciQuote, applyUpdateEvenIfTimestampUnchanged, quoteLines);
  }
  
  
}