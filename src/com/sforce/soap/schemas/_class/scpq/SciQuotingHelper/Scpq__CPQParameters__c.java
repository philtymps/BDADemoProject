/**
 * Scpq__CPQParameters__c.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Scpq__CPQParameters__c  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds;

    private java.lang.Boolean isDeleted;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private java.lang.String name;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject setupOwner;

    private java.lang.String setupOwnerId;

    private java.util.Calendar systemModstamp;

    private java.lang.String scpq__EnterpriseCode__c;

    private java.lang.String scpq__QuotesTabiFrameHeight__c;

    private java.lang.String scpq__ServerURL__c;

    private java.lang.Boolean scpq__ShowQuotesSidebar__c;

    private java.lang.Boolean scpq__UserSyncAllUpdates__c;

    private java.lang.Boolean scpq__UserSyncEnabled__c;

    private java.lang.String scpq__UserSyncEndpoint__c;

    private java.lang.Boolean scpq__UserSyncPrevalidateQuery__c;

    private java.lang.String scpq__UserSyncQuery__c;

    private java.lang.Double scpq__UserSyncTimeout__c;

    private java.lang.Boolean scpq__ValidateOpportuntyAccountChange__c;

    private java.lang.Boolean scpq__ValidateOpportuntyDelete__c;

    public Scpq__CPQParameters__c() {
    }

    public Scpq__CPQParameters__c(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds,
           java.lang.Boolean isDeleted,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           java.lang.String name,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject setupOwner,
           java.lang.String setupOwnerId,
           java.util.Calendar systemModstamp,
           java.lang.String scpq__EnterpriseCode__c,
           java.lang.String scpq__QuotesTabiFrameHeight__c,
           java.lang.String scpq__ServerURL__c,
           java.lang.Boolean scpq__ShowQuotesSidebar__c,
           java.lang.Boolean scpq__UserSyncAllUpdates__c,
           java.lang.Boolean scpq__UserSyncEnabled__c,
           java.lang.String scpq__UserSyncEndpoint__c,
           java.lang.Boolean scpq__UserSyncPrevalidateQuery__c,
           java.lang.String scpq__UserSyncQuery__c,
           java.lang.Double scpq__UserSyncTimeout__c,
           java.lang.Boolean scpq__ValidateOpportuntyAccountChange__c,
           java.lang.Boolean scpq__ValidateOpportuntyDelete__c) {
        super(
            fieldsToNull,
            id);
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
        this.feeds = feeds;
        this.isDeleted = isDeleted;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.name = name;
        this.processInstances = processInstances;
        this.processSteps = processSteps;
        this.setupOwner = setupOwner;
        this.setupOwnerId = setupOwnerId;
        this.systemModstamp = systemModstamp;
        this.scpq__EnterpriseCode__c = scpq__EnterpriseCode__c;
        this.scpq__QuotesTabiFrameHeight__c = scpq__QuotesTabiFrameHeight__c;
        this.scpq__ServerURL__c = scpq__ServerURL__c;
        this.scpq__ShowQuotesSidebar__c = scpq__ShowQuotesSidebar__c;
        this.scpq__UserSyncAllUpdates__c = scpq__UserSyncAllUpdates__c;
        this.scpq__UserSyncEnabled__c = scpq__UserSyncEnabled__c;
        this.scpq__UserSyncEndpoint__c = scpq__UserSyncEndpoint__c;
        this.scpq__UserSyncPrevalidateQuery__c = scpq__UserSyncPrevalidateQuery__c;
        this.scpq__UserSyncQuery__c = scpq__UserSyncQuery__c;
        this.scpq__UserSyncTimeout__c = scpq__UserSyncTimeout__c;
        this.scpq__ValidateOpportuntyAccountChange__c = scpq__ValidateOpportuntyAccountChange__c;
        this.scpq__ValidateOpportuntyDelete__c = scpq__ValidateOpportuntyDelete__c;
    }


    /**
     * Gets the createdBy value for this Scpq__CPQParameters__c.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Scpq__CPQParameters__c.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Scpq__CPQParameters__c.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Scpq__CPQParameters__c.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Scpq__CPQParameters__c.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Scpq__CPQParameters__c.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this Scpq__CPQParameters__c.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this Scpq__CPQParameters__c.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this Scpq__CPQParameters__c.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this Scpq__CPQParameters__c.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the isDeleted value for this Scpq__CPQParameters__c.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Scpq__CPQParameters__c.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the lastModifiedBy value for this Scpq__CPQParameters__c.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Scpq__CPQParameters__c.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Scpq__CPQParameters__c.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Scpq__CPQParameters__c.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Scpq__CPQParameters__c.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Scpq__CPQParameters__c.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the name value for this Scpq__CPQParameters__c.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Scpq__CPQParameters__c.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the processInstances value for this Scpq__CPQParameters__c.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this Scpq__CPQParameters__c.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this Scpq__CPQParameters__c.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this Scpq__CPQParameters__c.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the setupOwner value for this Scpq__CPQParameters__c.
     * 
     * @return setupOwner
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject getSetupOwner() {
        return setupOwner;
    }


    /**
     * Sets the setupOwner value for this Scpq__CPQParameters__c.
     * 
     * @param setupOwner
     */
    public void setSetupOwner(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject setupOwner) {
        this.setupOwner = setupOwner;
    }


    /**
     * Gets the setupOwnerId value for this Scpq__CPQParameters__c.
     * 
     * @return setupOwnerId
     */
    public java.lang.String getSetupOwnerId() {
        return setupOwnerId;
    }


    /**
     * Sets the setupOwnerId value for this Scpq__CPQParameters__c.
     * 
     * @param setupOwnerId
     */
    public void setSetupOwnerId(java.lang.String setupOwnerId) {
        this.setupOwnerId = setupOwnerId;
    }


    /**
     * Gets the systemModstamp value for this Scpq__CPQParameters__c.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Scpq__CPQParameters__c.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the scpq__EnterpriseCode__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__EnterpriseCode__c
     */
    public java.lang.String getScpq__EnterpriseCode__c() {
        return scpq__EnterpriseCode__c;
    }


    /**
     * Sets the scpq__EnterpriseCode__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__EnterpriseCode__c
     */
    public void setScpq__EnterpriseCode__c(java.lang.String scpq__EnterpriseCode__c) {
        this.scpq__EnterpriseCode__c = scpq__EnterpriseCode__c;
    }


    /**
     * Gets the scpq__QuotesTabiFrameHeight__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__QuotesTabiFrameHeight__c
     */
    public java.lang.String getScpq__QuotesTabiFrameHeight__c() {
        return scpq__QuotesTabiFrameHeight__c;
    }


    /**
     * Sets the scpq__QuotesTabiFrameHeight__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__QuotesTabiFrameHeight__c
     */
    public void setScpq__QuotesTabiFrameHeight__c(java.lang.String scpq__QuotesTabiFrameHeight__c) {
        this.scpq__QuotesTabiFrameHeight__c = scpq__QuotesTabiFrameHeight__c;
    }


    /**
     * Gets the scpq__ServerURL__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__ServerURL__c
     */
    public java.lang.String getScpq__ServerURL__c() {
        return scpq__ServerURL__c;
    }


    /**
     * Sets the scpq__ServerURL__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__ServerURL__c
     */
    public void setScpq__ServerURL__c(java.lang.String scpq__ServerURL__c) {
        this.scpq__ServerURL__c = scpq__ServerURL__c;
    }


    /**
     * Gets the scpq__ShowQuotesSidebar__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__ShowQuotesSidebar__c
     */
    public java.lang.Boolean getScpq__ShowQuotesSidebar__c() {
        return scpq__ShowQuotesSidebar__c;
    }


    /**
     * Sets the scpq__ShowQuotesSidebar__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__ShowQuotesSidebar__c
     */
    public void setScpq__ShowQuotesSidebar__c(java.lang.Boolean scpq__ShowQuotesSidebar__c) {
        this.scpq__ShowQuotesSidebar__c = scpq__ShowQuotesSidebar__c;
    }


    /**
     * Gets the scpq__UserSyncAllUpdates__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__UserSyncAllUpdates__c
     */
    public java.lang.Boolean getScpq__UserSyncAllUpdates__c() {
        return scpq__UserSyncAllUpdates__c;
    }


    /**
     * Sets the scpq__UserSyncAllUpdates__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__UserSyncAllUpdates__c
     */
    public void setScpq__UserSyncAllUpdates__c(java.lang.Boolean scpq__UserSyncAllUpdates__c) {
        this.scpq__UserSyncAllUpdates__c = scpq__UserSyncAllUpdates__c;
    }


    /**
     * Gets the scpq__UserSyncEnabled__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__UserSyncEnabled__c
     */
    public java.lang.Boolean getScpq__UserSyncEnabled__c() {
        return scpq__UserSyncEnabled__c;
    }


    /**
     * Sets the scpq__UserSyncEnabled__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__UserSyncEnabled__c
     */
    public void setScpq__UserSyncEnabled__c(java.lang.Boolean scpq__UserSyncEnabled__c) {
        this.scpq__UserSyncEnabled__c = scpq__UserSyncEnabled__c;
    }


    /**
     * Gets the scpq__UserSyncEndpoint__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__UserSyncEndpoint__c
     */
    public java.lang.String getScpq__UserSyncEndpoint__c() {
        return scpq__UserSyncEndpoint__c;
    }


    /**
     * Sets the scpq__UserSyncEndpoint__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__UserSyncEndpoint__c
     */
    public void setScpq__UserSyncEndpoint__c(java.lang.String scpq__UserSyncEndpoint__c) {
        this.scpq__UserSyncEndpoint__c = scpq__UserSyncEndpoint__c;
    }


    /**
     * Gets the scpq__UserSyncPrevalidateQuery__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__UserSyncPrevalidateQuery__c
     */
    public java.lang.Boolean getScpq__UserSyncPrevalidateQuery__c() {
        return scpq__UserSyncPrevalidateQuery__c;
    }


    /**
     * Sets the scpq__UserSyncPrevalidateQuery__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__UserSyncPrevalidateQuery__c
     */
    public void setScpq__UserSyncPrevalidateQuery__c(java.lang.Boolean scpq__UserSyncPrevalidateQuery__c) {
        this.scpq__UserSyncPrevalidateQuery__c = scpq__UserSyncPrevalidateQuery__c;
    }


    /**
     * Gets the scpq__UserSyncQuery__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__UserSyncQuery__c
     */
    public java.lang.String getScpq__UserSyncQuery__c() {
        return scpq__UserSyncQuery__c;
    }


    /**
     * Sets the scpq__UserSyncQuery__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__UserSyncQuery__c
     */
    public void setScpq__UserSyncQuery__c(java.lang.String scpq__UserSyncQuery__c) {
        this.scpq__UserSyncQuery__c = scpq__UserSyncQuery__c;
    }


    /**
     * Gets the scpq__UserSyncTimeout__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__UserSyncTimeout__c
     */
    public java.lang.Double getScpq__UserSyncTimeout__c() {
        return scpq__UserSyncTimeout__c;
    }


    /**
     * Sets the scpq__UserSyncTimeout__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__UserSyncTimeout__c
     */
    public void setScpq__UserSyncTimeout__c(java.lang.Double scpq__UserSyncTimeout__c) {
        this.scpq__UserSyncTimeout__c = scpq__UserSyncTimeout__c;
    }


    /**
     * Gets the scpq__ValidateOpportuntyAccountChange__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__ValidateOpportuntyAccountChange__c
     */
    public java.lang.Boolean getScpq__ValidateOpportuntyAccountChange__c() {
        return scpq__ValidateOpportuntyAccountChange__c;
    }


    /**
     * Sets the scpq__ValidateOpportuntyAccountChange__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__ValidateOpportuntyAccountChange__c
     */
    public void setScpq__ValidateOpportuntyAccountChange__c(java.lang.Boolean scpq__ValidateOpportuntyAccountChange__c) {
        this.scpq__ValidateOpportuntyAccountChange__c = scpq__ValidateOpportuntyAccountChange__c;
    }


    /**
     * Gets the scpq__ValidateOpportuntyDelete__c value for this Scpq__CPQParameters__c.
     * 
     * @return scpq__ValidateOpportuntyDelete__c
     */
    public java.lang.Boolean getScpq__ValidateOpportuntyDelete__c() {
        return scpq__ValidateOpportuntyDelete__c;
    }


    /**
     * Sets the scpq__ValidateOpportuntyDelete__c value for this Scpq__CPQParameters__c.
     * 
     * @param scpq__ValidateOpportuntyDelete__c
     */
    public void setScpq__ValidateOpportuntyDelete__c(java.lang.Boolean scpq__ValidateOpportuntyDelete__c) {
        this.scpq__ValidateOpportuntyDelete__c = scpq__ValidateOpportuntyDelete__c;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Scpq__CPQParameters__c)) return false;
        Scpq__CPQParameters__c other = (Scpq__CPQParameters__c) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdById==null && other.getCreatedById()==null) || 
             (this.createdById!=null &&
              this.createdById.equals(other.getCreatedById()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.feedSubscriptionsForEntity==null && other.getFeedSubscriptionsForEntity()==null) || 
             (this.feedSubscriptionsForEntity!=null &&
              this.feedSubscriptionsForEntity.equals(other.getFeedSubscriptionsForEntity()))) &&
            ((this.feeds==null && other.getFeeds()==null) || 
             (this.feeds!=null &&
              this.feeds.equals(other.getFeeds()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.lastModifiedBy==null && other.getLastModifiedBy()==null) || 
             (this.lastModifiedBy!=null &&
              this.lastModifiedBy.equals(other.getLastModifiedBy()))) &&
            ((this.lastModifiedById==null && other.getLastModifiedById()==null) || 
             (this.lastModifiedById!=null &&
              this.lastModifiedById.equals(other.getLastModifiedById()))) &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.processInstances==null && other.getProcessInstances()==null) || 
             (this.processInstances!=null &&
              this.processInstances.equals(other.getProcessInstances()))) &&
            ((this.processSteps==null && other.getProcessSteps()==null) || 
             (this.processSteps!=null &&
              this.processSteps.equals(other.getProcessSteps()))) &&
            ((this.setupOwner==null && other.getSetupOwner()==null) || 
             (this.setupOwner!=null &&
              this.setupOwner.equals(other.getSetupOwner()))) &&
            ((this.setupOwnerId==null && other.getSetupOwnerId()==null) || 
             (this.setupOwnerId!=null &&
              this.setupOwnerId.equals(other.getSetupOwnerId()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.scpq__EnterpriseCode__c==null && other.getScpq__EnterpriseCode__c()==null) || 
             (this.scpq__EnterpriseCode__c!=null &&
              this.scpq__EnterpriseCode__c.equals(other.getScpq__EnterpriseCode__c()))) &&
            ((this.scpq__QuotesTabiFrameHeight__c==null && other.getScpq__QuotesTabiFrameHeight__c()==null) || 
             (this.scpq__QuotesTabiFrameHeight__c!=null &&
              this.scpq__QuotesTabiFrameHeight__c.equals(other.getScpq__QuotesTabiFrameHeight__c()))) &&
            ((this.scpq__ServerURL__c==null && other.getScpq__ServerURL__c()==null) || 
             (this.scpq__ServerURL__c!=null &&
              this.scpq__ServerURL__c.equals(other.getScpq__ServerURL__c()))) &&
            ((this.scpq__ShowQuotesSidebar__c==null && other.getScpq__ShowQuotesSidebar__c()==null) || 
             (this.scpq__ShowQuotesSidebar__c!=null &&
              this.scpq__ShowQuotesSidebar__c.equals(other.getScpq__ShowQuotesSidebar__c()))) &&
            ((this.scpq__UserSyncAllUpdates__c==null && other.getScpq__UserSyncAllUpdates__c()==null) || 
             (this.scpq__UserSyncAllUpdates__c!=null &&
              this.scpq__UserSyncAllUpdates__c.equals(other.getScpq__UserSyncAllUpdates__c()))) &&
            ((this.scpq__UserSyncEnabled__c==null && other.getScpq__UserSyncEnabled__c()==null) || 
             (this.scpq__UserSyncEnabled__c!=null &&
              this.scpq__UserSyncEnabled__c.equals(other.getScpq__UserSyncEnabled__c()))) &&
            ((this.scpq__UserSyncEndpoint__c==null && other.getScpq__UserSyncEndpoint__c()==null) || 
             (this.scpq__UserSyncEndpoint__c!=null &&
              this.scpq__UserSyncEndpoint__c.equals(other.getScpq__UserSyncEndpoint__c()))) &&
            ((this.scpq__UserSyncPrevalidateQuery__c==null && other.getScpq__UserSyncPrevalidateQuery__c()==null) || 
             (this.scpq__UserSyncPrevalidateQuery__c!=null &&
              this.scpq__UserSyncPrevalidateQuery__c.equals(other.getScpq__UserSyncPrevalidateQuery__c()))) &&
            ((this.scpq__UserSyncQuery__c==null && other.getScpq__UserSyncQuery__c()==null) || 
             (this.scpq__UserSyncQuery__c!=null &&
              this.scpq__UserSyncQuery__c.equals(other.getScpq__UserSyncQuery__c()))) &&
            ((this.scpq__UserSyncTimeout__c==null && other.getScpq__UserSyncTimeout__c()==null) || 
             (this.scpq__UserSyncTimeout__c!=null &&
              this.scpq__UserSyncTimeout__c.equals(other.getScpq__UserSyncTimeout__c()))) &&
            ((this.scpq__ValidateOpportuntyAccountChange__c==null && other.getScpq__ValidateOpportuntyAccountChange__c()==null) || 
             (this.scpq__ValidateOpportuntyAccountChange__c!=null &&
              this.scpq__ValidateOpportuntyAccountChange__c.equals(other.getScpq__ValidateOpportuntyAccountChange__c()))) &&
            ((this.scpq__ValidateOpportuntyDelete__c==null && other.getScpq__ValidateOpportuntyDelete__c()==null) || 
             (this.scpq__ValidateOpportuntyDelete__c!=null &&
              this.scpq__ValidateOpportuntyDelete__c.equals(other.getScpq__ValidateOpportuntyDelete__c())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCreatedBy() != null) {
            _hashCode += getCreatedBy().hashCode();
        }
        if (getCreatedById() != null) {
            _hashCode += getCreatedById().hashCode();
        }
        if (getCreatedDate() != null) {
            _hashCode += getCreatedDate().hashCode();
        }
        if (getFeedSubscriptionsForEntity() != null) {
            _hashCode += getFeedSubscriptionsForEntity().hashCode();
        }
        if (getFeeds() != null) {
            _hashCode += getFeeds().hashCode();
        }
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getLastModifiedBy() != null) {
            _hashCode += getLastModifiedBy().hashCode();
        }
        if (getLastModifiedById() != null) {
            _hashCode += getLastModifiedById().hashCode();
        }
        if (getLastModifiedDate() != null) {
            _hashCode += getLastModifiedDate().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getProcessInstances() != null) {
            _hashCode += getProcessInstances().hashCode();
        }
        if (getProcessSteps() != null) {
            _hashCode += getProcessSteps().hashCode();
        }
        if (getSetupOwner() != null) {
            _hashCode += getSetupOwner().hashCode();
        }
        if (getSetupOwnerId() != null) {
            _hashCode += getSetupOwnerId().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getScpq__EnterpriseCode__c() != null) {
            _hashCode += getScpq__EnterpriseCode__c().hashCode();
        }
        if (getScpq__QuotesTabiFrameHeight__c() != null) {
            _hashCode += getScpq__QuotesTabiFrameHeight__c().hashCode();
        }
        if (getScpq__ServerURL__c() != null) {
            _hashCode += getScpq__ServerURL__c().hashCode();
        }
        if (getScpq__ShowQuotesSidebar__c() != null) {
            _hashCode += getScpq__ShowQuotesSidebar__c().hashCode();
        }
        if (getScpq__UserSyncAllUpdates__c() != null) {
            _hashCode += getScpq__UserSyncAllUpdates__c().hashCode();
        }
        if (getScpq__UserSyncEnabled__c() != null) {
            _hashCode += getScpq__UserSyncEnabled__c().hashCode();
        }
        if (getScpq__UserSyncEndpoint__c() != null) {
            _hashCode += getScpq__UserSyncEndpoint__c().hashCode();
        }
        if (getScpq__UserSyncPrevalidateQuery__c() != null) {
            _hashCode += getScpq__UserSyncPrevalidateQuery__c().hashCode();
        }
        if (getScpq__UserSyncQuery__c() != null) {
            _hashCode += getScpq__UserSyncQuery__c().hashCode();
        }
        if (getScpq__UserSyncTimeout__c() != null) {
            _hashCode += getScpq__UserSyncTimeout__c().hashCode();
        }
        if (getScpq__ValidateOpportuntyAccountChange__c() != null) {
            _hashCode += getScpq__ValidateOpportuntyAccountChange__c().hashCode();
        }
        if (getScpq__ValidateOpportuntyDelete__c() != null) {
            _hashCode += getScpq__ValidateOpportuntyDelete__c().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Scpq__CPQParameters__c.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__CPQParameters__c"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CreatedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "User"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdById");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CreatedById"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CreatedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feedSubscriptionsForEntity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FeedSubscriptionsForEntity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feeds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Feeds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDeleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsDeleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifiedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastModifiedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "User"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifiedById");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastModifiedById"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifiedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastModifiedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processInstances");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ProcessInstances"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processSteps");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ProcessSteps"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("setupOwner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SetupOwner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sObject"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("setupOwnerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SetupOwnerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemModstamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SystemModstamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__EnterpriseCode__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__EnterpriseCode__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__QuotesTabiFrameHeight__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__QuotesTabiFrameHeight__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__ServerURL__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ServerURL__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__ShowQuotesSidebar__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ShowQuotesSidebar__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UserSyncAllUpdates__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UserSyncAllUpdates__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UserSyncEnabled__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UserSyncEnabled__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UserSyncEndpoint__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UserSyncEndpoint__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UserSyncPrevalidateQuery__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UserSyncPrevalidateQuery__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UserSyncQuery__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UserSyncQuery__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UserSyncTimeout__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UserSyncTimeout__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__ValidateOpportuntyAccountChange__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ValidateOpportuntyAccountChange__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__ValidateOpportuntyDelete__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ValidateOpportuntyDelete__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
