/**
 * Scpq__SciQuote__c.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Scpq__SciQuote__c  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments;

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

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps;

    private java.util.Calendar systemModstamp;

    private java.lang.String scpq__OpportunityId__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity scpq__OpportunityId__r;

    private java.lang.String scpq__OrderHeaderKey__c;

    private java.lang.Boolean scpq__Primary__c;

    private java.lang.String scpq__QuoteId__c;

    private java.lang.Double scpq__QuoteValueAfterDiscounts__c;

    private java.lang.Double scpq__QuoteValueBeforeDiscounts__c;

    private java.util.Calendar scpq__SciLastModifiedDate__c;

    private java.lang.String scpq__Status__c;

    private java.lang.Double scpq__TotalDiscountPercent__c;

    public Scpq__SciQuote__c() {
    }

    public Scpq__SciQuote__c(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments,
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
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps,
           java.util.Calendar systemModstamp,
           java.lang.String scpq__OpportunityId__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity scpq__OpportunityId__r,
           java.lang.String scpq__OrderHeaderKey__c,
           java.lang.Boolean scpq__Primary__c,
           java.lang.String scpq__QuoteId__c,
           java.lang.Double scpq__QuoteValueAfterDiscounts__c,
           java.lang.Double scpq__QuoteValueBeforeDiscounts__c,
           java.util.Calendar scpq__SciLastModifiedDate__c,
           java.lang.String scpq__Status__c,
           java.lang.Double scpq__TotalDiscountPercent__c) {
        super(
            fieldsToNull,
            id);
        this.attachments = attachments;
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
        this.notes = notes;
        this.notesAndAttachments = notesAndAttachments;
        this.processInstances = processInstances;
        this.processSteps = processSteps;
        this.systemModstamp = systemModstamp;
        this.scpq__OpportunityId__c = scpq__OpportunityId__c;
        this.scpq__OpportunityId__r = scpq__OpportunityId__r;
        this.scpq__OrderHeaderKey__c = scpq__OrderHeaderKey__c;
        this.scpq__Primary__c = scpq__Primary__c;
        this.scpq__QuoteId__c = scpq__QuoteId__c;
        this.scpq__QuoteValueAfterDiscounts__c = scpq__QuoteValueAfterDiscounts__c;
        this.scpq__QuoteValueBeforeDiscounts__c = scpq__QuoteValueBeforeDiscounts__c;
        this.scpq__SciLastModifiedDate__c = scpq__SciLastModifiedDate__c;
        this.scpq__Status__c = scpq__Status__c;
        this.scpq__TotalDiscountPercent__c = scpq__TotalDiscountPercent__c;
    }


    /**
     * Gets the attachments value for this Scpq__SciQuote__c.
     * 
     * @return attachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Scpq__SciQuote__c.
     * 
     * @param attachments
     */
    public void setAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the createdBy value for this Scpq__SciQuote__c.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Scpq__SciQuote__c.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Scpq__SciQuote__c.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Scpq__SciQuote__c.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Scpq__SciQuote__c.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Scpq__SciQuote__c.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this Scpq__SciQuote__c.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this Scpq__SciQuote__c.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this Scpq__SciQuote__c.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this Scpq__SciQuote__c.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the isDeleted value for this Scpq__SciQuote__c.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Scpq__SciQuote__c.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the lastModifiedBy value for this Scpq__SciQuote__c.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Scpq__SciQuote__c.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Scpq__SciQuote__c.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Scpq__SciQuote__c.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Scpq__SciQuote__c.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Scpq__SciQuote__c.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the name value for this Scpq__SciQuote__c.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Scpq__SciQuote__c.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the notes value for this Scpq__SciQuote__c.
     * 
     * @return notes
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Scpq__SciQuote__c.
     * 
     * @param notes
     */
    public void setNotes(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes) {
        this.notes = notes;
    }


    /**
     * Gets the notesAndAttachments value for this Scpq__SciQuote__c.
     * 
     * @return notesAndAttachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotesAndAttachments() {
        return notesAndAttachments;
    }


    /**
     * Sets the notesAndAttachments value for this Scpq__SciQuote__c.
     * 
     * @param notesAndAttachments
     */
    public void setNotesAndAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments) {
        this.notesAndAttachments = notesAndAttachments;
    }


    /**
     * Gets the processInstances value for this Scpq__SciQuote__c.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this Scpq__SciQuote__c.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this Scpq__SciQuote__c.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this Scpq__SciQuote__c.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the systemModstamp value for this Scpq__SciQuote__c.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Scpq__SciQuote__c.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the scpq__OpportunityId__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__OpportunityId__c
     */
    public java.lang.String getScpq__OpportunityId__c() {
        return scpq__OpportunityId__c;
    }


    /**
     * Sets the scpq__OpportunityId__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__OpportunityId__c
     */
    public void setScpq__OpportunityId__c(java.lang.String scpq__OpportunityId__c) {
        this.scpq__OpportunityId__c = scpq__OpportunityId__c;
    }


    /**
     * Gets the scpq__OpportunityId__r value for this Scpq__SciQuote__c.
     * 
     * @return scpq__OpportunityId__r
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity getScpq__OpportunityId__r() {
        return scpq__OpportunityId__r;
    }


    /**
     * Sets the scpq__OpportunityId__r value for this Scpq__SciQuote__c.
     * 
     * @param scpq__OpportunityId__r
     */
    public void setScpq__OpportunityId__r(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity scpq__OpportunityId__r) {
        this.scpq__OpportunityId__r = scpq__OpportunityId__r;
    }


    /**
     * Gets the scpq__OrderHeaderKey__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__OrderHeaderKey__c
     */
    public java.lang.String getScpq__OrderHeaderKey__c() {
        return scpq__OrderHeaderKey__c;
    }


    /**
     * Sets the scpq__OrderHeaderKey__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__OrderHeaderKey__c
     */
    public void setScpq__OrderHeaderKey__c(java.lang.String scpq__OrderHeaderKey__c) {
        this.scpq__OrderHeaderKey__c = scpq__OrderHeaderKey__c;
    }


    /**
     * Gets the scpq__Primary__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__Primary__c
     */
    public java.lang.Boolean getScpq__Primary__c() {
        return scpq__Primary__c;
    }


    /**
     * Sets the scpq__Primary__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__Primary__c
     */
    public void setScpq__Primary__c(java.lang.Boolean scpq__Primary__c) {
        this.scpq__Primary__c = scpq__Primary__c;
    }


    /**
     * Gets the scpq__QuoteId__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__QuoteId__c
     */
    public java.lang.String getScpq__QuoteId__c() {
        return scpq__QuoteId__c;
    }


    /**
     * Sets the scpq__QuoteId__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__QuoteId__c
     */
    public void setScpq__QuoteId__c(java.lang.String scpq__QuoteId__c) {
        this.scpq__QuoteId__c = scpq__QuoteId__c;
    }


    /**
     * Gets the scpq__QuoteValueAfterDiscounts__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__QuoteValueAfterDiscounts__c
     */
    public java.lang.Double getScpq__QuoteValueAfterDiscounts__c() {
        return scpq__QuoteValueAfterDiscounts__c;
    }


    /**
     * Sets the scpq__QuoteValueAfterDiscounts__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__QuoteValueAfterDiscounts__c
     */
    public void setScpq__QuoteValueAfterDiscounts__c(java.lang.Double scpq__QuoteValueAfterDiscounts__c) {
        this.scpq__QuoteValueAfterDiscounts__c = scpq__QuoteValueAfterDiscounts__c;
    }


    /**
     * Gets the scpq__QuoteValueBeforeDiscounts__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__QuoteValueBeforeDiscounts__c
     */
    public java.lang.Double getScpq__QuoteValueBeforeDiscounts__c() {
        return scpq__QuoteValueBeforeDiscounts__c;
    }


    /**
     * Sets the scpq__QuoteValueBeforeDiscounts__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__QuoteValueBeforeDiscounts__c
     */
    public void setScpq__QuoteValueBeforeDiscounts__c(java.lang.Double scpq__QuoteValueBeforeDiscounts__c) {
        this.scpq__QuoteValueBeforeDiscounts__c = scpq__QuoteValueBeforeDiscounts__c;
    }


    /**
     * Gets the scpq__SciLastModifiedDate__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__SciLastModifiedDate__c
     */
    public java.util.Calendar getScpq__SciLastModifiedDate__c() {
        return scpq__SciLastModifiedDate__c;
    }


    /**
     * Sets the scpq__SciLastModifiedDate__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__SciLastModifiedDate__c
     */
    public void setScpq__SciLastModifiedDate__c(java.util.Calendar scpq__SciLastModifiedDate__c) {
        this.scpq__SciLastModifiedDate__c = scpq__SciLastModifiedDate__c;
    }


    /**
     * Gets the scpq__Status__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__Status__c
     */
    public java.lang.String getScpq__Status__c() {
        return scpq__Status__c;
    }


    /**
     * Sets the scpq__Status__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__Status__c
     */
    public void setScpq__Status__c(java.lang.String scpq__Status__c) {
        this.scpq__Status__c = scpq__Status__c;
    }


    /**
     * Gets the scpq__TotalDiscountPercent__c value for this Scpq__SciQuote__c.
     * 
     * @return scpq__TotalDiscountPercent__c
     */
    public java.lang.Double getScpq__TotalDiscountPercent__c() {
        return scpq__TotalDiscountPercent__c;
    }


    /**
     * Sets the scpq__TotalDiscountPercent__c value for this Scpq__SciQuote__c.
     * 
     * @param scpq__TotalDiscountPercent__c
     */
    public void setScpq__TotalDiscountPercent__c(java.lang.Double scpq__TotalDiscountPercent__c) {
        this.scpq__TotalDiscountPercent__c = scpq__TotalDiscountPercent__c;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Scpq__SciQuote__c)) return false;
        Scpq__SciQuote__c other = (Scpq__SciQuote__c) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              this.attachments.equals(other.getAttachments()))) &&
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
            ((this.notes==null && other.getNotes()==null) || 
             (this.notes!=null &&
              this.notes.equals(other.getNotes()))) &&
            ((this.notesAndAttachments==null && other.getNotesAndAttachments()==null) || 
             (this.notesAndAttachments!=null &&
              this.notesAndAttachments.equals(other.getNotesAndAttachments()))) &&
            ((this.processInstances==null && other.getProcessInstances()==null) || 
             (this.processInstances!=null &&
              this.processInstances.equals(other.getProcessInstances()))) &&
            ((this.processSteps==null && other.getProcessSteps()==null) || 
             (this.processSteps!=null &&
              this.processSteps.equals(other.getProcessSteps()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.scpq__OpportunityId__c==null && other.getScpq__OpportunityId__c()==null) || 
             (this.scpq__OpportunityId__c!=null &&
              this.scpq__OpportunityId__c.equals(other.getScpq__OpportunityId__c()))) &&
            ((this.scpq__OpportunityId__r==null && other.getScpq__OpportunityId__r()==null) || 
             (this.scpq__OpportunityId__r!=null &&
              this.scpq__OpportunityId__r.equals(other.getScpq__OpportunityId__r()))) &&
            ((this.scpq__OrderHeaderKey__c==null && other.getScpq__OrderHeaderKey__c()==null) || 
             (this.scpq__OrderHeaderKey__c!=null &&
              this.scpq__OrderHeaderKey__c.equals(other.getScpq__OrderHeaderKey__c()))) &&
            ((this.scpq__Primary__c==null && other.getScpq__Primary__c()==null) || 
             (this.scpq__Primary__c!=null &&
              this.scpq__Primary__c.equals(other.getScpq__Primary__c()))) &&
            ((this.scpq__QuoteId__c==null && other.getScpq__QuoteId__c()==null) || 
             (this.scpq__QuoteId__c!=null &&
              this.scpq__QuoteId__c.equals(other.getScpq__QuoteId__c()))) &&
            ((this.scpq__QuoteValueAfterDiscounts__c==null && other.getScpq__QuoteValueAfterDiscounts__c()==null) || 
             (this.scpq__QuoteValueAfterDiscounts__c!=null &&
              this.scpq__QuoteValueAfterDiscounts__c.equals(other.getScpq__QuoteValueAfterDiscounts__c()))) &&
            ((this.scpq__QuoteValueBeforeDiscounts__c==null && other.getScpq__QuoteValueBeforeDiscounts__c()==null) || 
             (this.scpq__QuoteValueBeforeDiscounts__c!=null &&
              this.scpq__QuoteValueBeforeDiscounts__c.equals(other.getScpq__QuoteValueBeforeDiscounts__c()))) &&
            ((this.scpq__SciLastModifiedDate__c==null && other.getScpq__SciLastModifiedDate__c()==null) || 
             (this.scpq__SciLastModifiedDate__c!=null &&
              this.scpq__SciLastModifiedDate__c.equals(other.getScpq__SciLastModifiedDate__c()))) &&
            ((this.scpq__Status__c==null && other.getScpq__Status__c()==null) || 
             (this.scpq__Status__c!=null &&
              this.scpq__Status__c.equals(other.getScpq__Status__c()))) &&
            ((this.scpq__TotalDiscountPercent__c==null && other.getScpq__TotalDiscountPercent__c()==null) || 
             (this.scpq__TotalDiscountPercent__c!=null &&
              this.scpq__TotalDiscountPercent__c.equals(other.getScpq__TotalDiscountPercent__c())));
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
        if (getAttachments() != null) {
            _hashCode += getAttachments().hashCode();
        }
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
        if (getNotes() != null) {
            _hashCode += getNotes().hashCode();
        }
        if (getNotesAndAttachments() != null) {
            _hashCode += getNotesAndAttachments().hashCode();
        }
        if (getProcessInstances() != null) {
            _hashCode += getProcessInstances().hashCode();
        }
        if (getProcessSteps() != null) {
            _hashCode += getProcessSteps().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getScpq__OpportunityId__c() != null) {
            _hashCode += getScpq__OpportunityId__c().hashCode();
        }
        if (getScpq__OpportunityId__r() != null) {
            _hashCode += getScpq__OpportunityId__r().hashCode();
        }
        if (getScpq__OrderHeaderKey__c() != null) {
            _hashCode += getScpq__OrderHeaderKey__c().hashCode();
        }
        if (getScpq__Primary__c() != null) {
            _hashCode += getScpq__Primary__c().hashCode();
        }
        if (getScpq__QuoteId__c() != null) {
            _hashCode += getScpq__QuoteId__c().hashCode();
        }
        if (getScpq__QuoteValueAfterDiscounts__c() != null) {
            _hashCode += getScpq__QuoteValueAfterDiscounts__c().hashCode();
        }
        if (getScpq__QuoteValueBeforeDiscounts__c() != null) {
            _hashCode += getScpq__QuoteValueBeforeDiscounts__c().hashCode();
        }
        if (getScpq__SciLastModifiedDate__c() != null) {
            _hashCode += getScpq__SciLastModifiedDate__c().hashCode();
        }
        if (getScpq__Status__c() != null) {
            _hashCode += getScpq__Status__c().hashCode();
        }
        if (getScpq__TotalDiscountPercent__c() != null) {
            _hashCode += getScpq__TotalDiscountPercent__c().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Scpq__SciQuote__c.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Attachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("notes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Notes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notesAndAttachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NotesAndAttachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("systemModstamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SystemModstamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__OpportunityId__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__OpportunityId__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__OpportunityId__r");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__OpportunityId__r"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Opportunity"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__OrderHeaderKey__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__OrderHeaderKey__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__Primary__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__Primary__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__QuoteId__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__QuoteId__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__QuoteValueAfterDiscounts__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__QuoteValueAfterDiscounts__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__QuoteValueBeforeDiscounts__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__QuoteValueBeforeDiscounts__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__SciLastModifiedDate__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciLastModifiedDate__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__Status__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__Status__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__TotalDiscountPercent__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__TotalDiscountPercent__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
