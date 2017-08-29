/**
 * Scpq__PrimaryQuoteLine__c.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Scpq__PrimaryQuoteLine__c  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
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

    private java.lang.Double scpq__Discount__c;

    private java.lang.String scpq__ItemID__c;

    private java.lang.Double scpq__LineNo__c;

    private java.lang.Double scpq__LineTotal__c;

    private java.lang.String scpq__OpportunityId__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity scpq__OpportunityId__r;

    private java.lang.Double scpq__ParentLineNo__c;

    private java.lang.String scpq__ProductName__c;

    private java.lang.Double scpq__Quantity__c;

    private java.lang.String scpq__UnitOfMeasure__c;

    private java.lang.Double scpq__UnitPrice__c;

    public Scpq__PrimaryQuoteLine__c() {
    }

    public Scpq__PrimaryQuoteLine__c(
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
           java.lang.Double scpq__Discount__c,
           java.lang.String scpq__ItemID__c,
           java.lang.Double scpq__LineNo__c,
           java.lang.Double scpq__LineTotal__c,
           java.lang.String scpq__OpportunityId__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity scpq__OpportunityId__r,
           java.lang.Double scpq__ParentLineNo__c,
           java.lang.String scpq__ProductName__c,
           java.lang.Double scpq__Quantity__c,
           java.lang.String scpq__UnitOfMeasure__c,
           java.lang.Double scpq__UnitPrice__c) {
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
        this.scpq__Discount__c = scpq__Discount__c;
        this.scpq__ItemID__c = scpq__ItemID__c;
        this.scpq__LineNo__c = scpq__LineNo__c;
        this.scpq__LineTotal__c = scpq__LineTotal__c;
        this.scpq__OpportunityId__c = scpq__OpportunityId__c;
        this.scpq__OpportunityId__r = scpq__OpportunityId__r;
        this.scpq__ParentLineNo__c = scpq__ParentLineNo__c;
        this.scpq__ProductName__c = scpq__ProductName__c;
        this.scpq__Quantity__c = scpq__Quantity__c;
        this.scpq__UnitOfMeasure__c = scpq__UnitOfMeasure__c;
        this.scpq__UnitPrice__c = scpq__UnitPrice__c;
    }


    /**
     * Gets the attachments value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return attachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param attachments
     */
    public void setAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the createdBy value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the isDeleted value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the lastModifiedBy value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the name value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the notes value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return notes
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param notes
     */
    public void setNotes(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes) {
        this.notes = notes;
    }


    /**
     * Gets the notesAndAttachments value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return notesAndAttachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotesAndAttachments() {
        return notesAndAttachments;
    }


    /**
     * Sets the notesAndAttachments value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param notesAndAttachments
     */
    public void setNotesAndAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments) {
        this.notesAndAttachments = notesAndAttachments;
    }


    /**
     * Gets the processInstances value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the systemModstamp value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the scpq__Discount__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__Discount__c
     */
    public java.lang.Double getScpq__Discount__c() {
        return scpq__Discount__c;
    }


    /**
     * Sets the scpq__Discount__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__Discount__c
     */
    public void setScpq__Discount__c(java.lang.Double scpq__Discount__c) {
        this.scpq__Discount__c = scpq__Discount__c;
    }


    /**
     * Gets the scpq__ItemID__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__ItemID__c
     */
    public java.lang.String getScpq__ItemID__c() {
        return scpq__ItemID__c;
    }


    /**
     * Sets the scpq__ItemID__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__ItemID__c
     */
    public void setScpq__ItemID__c(java.lang.String scpq__ItemID__c) {
        this.scpq__ItemID__c = scpq__ItemID__c;
    }


    /**
     * Gets the scpq__LineNo__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__LineNo__c
     */
    public java.lang.Double getScpq__LineNo__c() {
        return scpq__LineNo__c;
    }


    /**
     * Sets the scpq__LineNo__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__LineNo__c
     */
    public void setScpq__LineNo__c(java.lang.Double scpq__LineNo__c) {
        this.scpq__LineNo__c = scpq__LineNo__c;
    }


    /**
     * Gets the scpq__LineTotal__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__LineTotal__c
     */
    public java.lang.Double getScpq__LineTotal__c() {
        return scpq__LineTotal__c;
    }


    /**
     * Sets the scpq__LineTotal__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__LineTotal__c
     */
    public void setScpq__LineTotal__c(java.lang.Double scpq__LineTotal__c) {
        this.scpq__LineTotal__c = scpq__LineTotal__c;
    }


    /**
     * Gets the scpq__OpportunityId__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__OpportunityId__c
     */
    public java.lang.String getScpq__OpportunityId__c() {
        return scpq__OpportunityId__c;
    }


    /**
     * Sets the scpq__OpportunityId__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__OpportunityId__c
     */
    public void setScpq__OpportunityId__c(java.lang.String scpq__OpportunityId__c) {
        this.scpq__OpportunityId__c = scpq__OpportunityId__c;
    }


    /**
     * Gets the scpq__OpportunityId__r value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__OpportunityId__r
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity getScpq__OpportunityId__r() {
        return scpq__OpportunityId__r;
    }


    /**
     * Sets the scpq__OpportunityId__r value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__OpportunityId__r
     */
    public void setScpq__OpportunityId__r(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity scpq__OpportunityId__r) {
        this.scpq__OpportunityId__r = scpq__OpportunityId__r;
    }


    /**
     * Gets the scpq__ParentLineNo__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__ParentLineNo__c
     */
    public java.lang.Double getScpq__ParentLineNo__c() {
        return scpq__ParentLineNo__c;
    }


    /**
     * Sets the scpq__ParentLineNo__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__ParentLineNo__c
     */
    public void setScpq__ParentLineNo__c(java.lang.Double scpq__ParentLineNo__c) {
        this.scpq__ParentLineNo__c = scpq__ParentLineNo__c;
    }


    /**
     * Gets the scpq__ProductName__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__ProductName__c
     */
    public java.lang.String getScpq__ProductName__c() {
        return scpq__ProductName__c;
    }


    /**
     * Sets the scpq__ProductName__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__ProductName__c
     */
    public void setScpq__ProductName__c(java.lang.String scpq__ProductName__c) {
        this.scpq__ProductName__c = scpq__ProductName__c;
    }


    /**
     * Gets the scpq__Quantity__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__Quantity__c
     */
    public java.lang.Double getScpq__Quantity__c() {
        return scpq__Quantity__c;
    }


    /**
     * Sets the scpq__Quantity__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__Quantity__c
     */
    public void setScpq__Quantity__c(java.lang.Double scpq__Quantity__c) {
        this.scpq__Quantity__c = scpq__Quantity__c;
    }


    /**
     * Gets the scpq__UnitOfMeasure__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__UnitOfMeasure__c
     */
    public java.lang.String getScpq__UnitOfMeasure__c() {
        return scpq__UnitOfMeasure__c;
    }


    /**
     * Sets the scpq__UnitOfMeasure__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__UnitOfMeasure__c
     */
    public void setScpq__UnitOfMeasure__c(java.lang.String scpq__UnitOfMeasure__c) {
        this.scpq__UnitOfMeasure__c = scpq__UnitOfMeasure__c;
    }


    /**
     * Gets the scpq__UnitPrice__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @return scpq__UnitPrice__c
     */
    public java.lang.Double getScpq__UnitPrice__c() {
        return scpq__UnitPrice__c;
    }


    /**
     * Sets the scpq__UnitPrice__c value for this Scpq__PrimaryQuoteLine__c.
     * 
     * @param scpq__UnitPrice__c
     */
    public void setScpq__UnitPrice__c(java.lang.Double scpq__UnitPrice__c) {
        this.scpq__UnitPrice__c = scpq__UnitPrice__c;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Scpq__PrimaryQuoteLine__c)) return false;
        Scpq__PrimaryQuoteLine__c other = (Scpq__PrimaryQuoteLine__c) obj;
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
            ((this.scpq__Discount__c==null && other.getScpq__Discount__c()==null) || 
             (this.scpq__Discount__c!=null &&
              this.scpq__Discount__c.equals(other.getScpq__Discount__c()))) &&
            ((this.scpq__ItemID__c==null && other.getScpq__ItemID__c()==null) || 
             (this.scpq__ItemID__c!=null &&
              this.scpq__ItemID__c.equals(other.getScpq__ItemID__c()))) &&
            ((this.scpq__LineNo__c==null && other.getScpq__LineNo__c()==null) || 
             (this.scpq__LineNo__c!=null &&
              this.scpq__LineNo__c.equals(other.getScpq__LineNo__c()))) &&
            ((this.scpq__LineTotal__c==null && other.getScpq__LineTotal__c()==null) || 
             (this.scpq__LineTotal__c!=null &&
              this.scpq__LineTotal__c.equals(other.getScpq__LineTotal__c()))) &&
            ((this.scpq__OpportunityId__c==null && other.getScpq__OpportunityId__c()==null) || 
             (this.scpq__OpportunityId__c!=null &&
              this.scpq__OpportunityId__c.equals(other.getScpq__OpportunityId__c()))) &&
            ((this.scpq__OpportunityId__r==null && other.getScpq__OpportunityId__r()==null) || 
             (this.scpq__OpportunityId__r!=null &&
              this.scpq__OpportunityId__r.equals(other.getScpq__OpportunityId__r()))) &&
            ((this.scpq__ParentLineNo__c==null && other.getScpq__ParentLineNo__c()==null) || 
             (this.scpq__ParentLineNo__c!=null &&
              this.scpq__ParentLineNo__c.equals(other.getScpq__ParentLineNo__c()))) &&
            ((this.scpq__ProductName__c==null && other.getScpq__ProductName__c()==null) || 
             (this.scpq__ProductName__c!=null &&
              this.scpq__ProductName__c.equals(other.getScpq__ProductName__c()))) &&
            ((this.scpq__Quantity__c==null && other.getScpq__Quantity__c()==null) || 
             (this.scpq__Quantity__c!=null &&
              this.scpq__Quantity__c.equals(other.getScpq__Quantity__c()))) &&
            ((this.scpq__UnitOfMeasure__c==null && other.getScpq__UnitOfMeasure__c()==null) || 
             (this.scpq__UnitOfMeasure__c!=null &&
              this.scpq__UnitOfMeasure__c.equals(other.getScpq__UnitOfMeasure__c()))) &&
            ((this.scpq__UnitPrice__c==null && other.getScpq__UnitPrice__c()==null) || 
             (this.scpq__UnitPrice__c!=null &&
              this.scpq__UnitPrice__c.equals(other.getScpq__UnitPrice__c())));
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
        if (getScpq__Discount__c() != null) {
            _hashCode += getScpq__Discount__c().hashCode();
        }
        if (getScpq__ItemID__c() != null) {
            _hashCode += getScpq__ItemID__c().hashCode();
        }
        if (getScpq__LineNo__c() != null) {
            _hashCode += getScpq__LineNo__c().hashCode();
        }
        if (getScpq__LineTotal__c() != null) {
            _hashCode += getScpq__LineTotal__c().hashCode();
        }
        if (getScpq__OpportunityId__c() != null) {
            _hashCode += getScpq__OpportunityId__c().hashCode();
        }
        if (getScpq__OpportunityId__r() != null) {
            _hashCode += getScpq__OpportunityId__r().hashCode();
        }
        if (getScpq__ParentLineNo__c() != null) {
            _hashCode += getScpq__ParentLineNo__c().hashCode();
        }
        if (getScpq__ProductName__c() != null) {
            _hashCode += getScpq__ProductName__c().hashCode();
        }
        if (getScpq__Quantity__c() != null) {
            _hashCode += getScpq__Quantity__c().hashCode();
        }
        if (getScpq__UnitOfMeasure__c() != null) {
            _hashCode += getScpq__UnitOfMeasure__c().hashCode();
        }
        if (getScpq__UnitPrice__c() != null) {
            _hashCode += getScpq__UnitPrice__c().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Scpq__PrimaryQuoteLine__c.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__PrimaryQuoteLine__c"));
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
        elemField.setFieldName("scpq__Discount__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__Discount__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__ItemID__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ItemID__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__LineNo__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__LineNo__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__LineTotal__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__LineTotal__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
        elemField.setFieldName("scpq__ParentLineNo__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ParentLineNo__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__ProductName__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__ProductName__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__Quantity__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__Quantity__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UnitOfMeasure__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UnitOfMeasure__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scpq__UnitPrice__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__UnitPrice__c"));
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
