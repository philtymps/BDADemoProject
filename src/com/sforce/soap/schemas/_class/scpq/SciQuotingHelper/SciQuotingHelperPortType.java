/**
 * SciQuotingHelperPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public interface SciQuotingHelperPortType extends java.rmi.Remote {
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[] getOpportunityAccountPrimaryContact(java.lang.String opportunityId, java.lang.String[] opportunityFields, java.lang.String[] accountFields, java.lang.String[] primaryContactFields) throws java.rmi.RemoteException;
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c upsertQuote(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityLineItem[] quoteLines) throws java.rmi.RemoteException;
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c upsertQuoteAndPrimaryQuoteLines(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote, boolean applyUpdateEvenIfTimestampUnchanged, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] quoteLines) throws java.rmi.RemoteException;
}
