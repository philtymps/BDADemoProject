/**
 * _case.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class _case  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account account;

    private java.lang.String accountId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Asset asset;

    private java.lang.String assetId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BusinessHours businessHours;

    private java.lang.String businessHoursId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseComments;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseContactRoles;

    private java.lang.String caseNumber;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseSolutions;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases;

    private java.util.Calendar closedDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact contact;

    private java.lang.String contactId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private java.lang.String description;

    private java.lang.String engineeringReqNumber__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories;

    private java.lang.Boolean isClosed;

    private java.lang.Boolean isClosedOnCreate;

    private java.lang.Boolean isDeleted;

    private java.lang.Boolean isEscalated;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities;

    private java.lang.String origin;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject owner;

    private java.lang.String ownerId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper._case parent;

    private java.lang.String parentId;

    private java.lang.String potentialLiability__c;

    private java.lang.String priority;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps;

    private java.lang.String product__c;

    private java.lang.String reason;

    private java.lang.String SLAViolation__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult solutions;

    private java.lang.String status;

    private java.lang.String subject;

    private java.lang.String suppliedCompany;

    private java.lang.String suppliedEmail;

    private java.lang.String suppliedName;

    private java.lang.String suppliedPhone;

    private java.util.Calendar systemModstamp;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult teamMembers;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult teamTemplateRecords;

    private java.lang.String type;

    public _case() {
    }

    public _case(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account account,
           java.lang.String accountId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Asset asset,
           java.lang.String assetId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BusinessHours businessHours,
           java.lang.String businessHoursId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseComments,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseContactRoles,
           java.lang.String caseNumber,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseSolutions,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases,
           java.util.Calendar closedDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact contact,
           java.lang.String contactId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           java.lang.String description,
           java.lang.String engineeringReqNumber__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories,
           java.lang.Boolean isClosed,
           java.lang.Boolean isClosedOnCreate,
           java.lang.Boolean isDeleted,
           java.lang.Boolean isEscalated,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities,
           java.lang.String origin,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject owner,
           java.lang.String ownerId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper._case parent,
           java.lang.String parentId,
           java.lang.String potentialLiability__c,
           java.lang.String priority,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps,
           java.lang.String product__c,
           java.lang.String reason,
           java.lang.String SLAViolation__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult solutions,
           java.lang.String status,
           java.lang.String subject,
           java.lang.String suppliedCompany,
           java.lang.String suppliedEmail,
           java.lang.String suppliedName,
           java.lang.String suppliedPhone,
           java.util.Calendar systemModstamp,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult teamMembers,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult teamTemplateRecords,
           java.lang.String type) {
        super(
            fieldsToNull,
            id);
        this.account = account;
        this.accountId = accountId;
        this.activityHistories = activityHistories;
        this.asset = asset;
        this.assetId = assetId;
        this.attachments = attachments;
        this.businessHours = businessHours;
        this.businessHoursId = businessHoursId;
        this.caseComments = caseComments;
        this.caseContactRoles = caseContactRoles;
        this.caseNumber = caseNumber;
        this.caseSolutions = caseSolutions;
        this.cases = cases;
        this.closedDate = closedDate;
        this.contact = contact;
        this.contactId = contactId;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.description = description;
        this.engineeringReqNumber__c = engineeringReqNumber__c;
        this.events = events;
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
        this.feeds = feeds;
        this.histories = histories;
        this.isClosed = isClosed;
        this.isClosedOnCreate = isClosedOnCreate;
        this.isDeleted = isDeleted;
        this.isEscalated = isEscalated;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.openActivities = openActivities;
        this.origin = origin;
        this.owner = owner;
        this.ownerId = ownerId;
        this.parent = parent;
        this.parentId = parentId;
        this.potentialLiability__c = potentialLiability__c;
        this.priority = priority;
        this.processInstances = processInstances;
        this.processSteps = processSteps;
        this.product__c = product__c;
        this.reason = reason;
        this.SLAViolation__c = SLAViolation__c;
        this.shares = shares;
        this.solutions = solutions;
        this.status = status;
        this.subject = subject;
        this.suppliedCompany = suppliedCompany;
        this.suppliedEmail = suppliedEmail;
        this.suppliedName = suppliedName;
        this.suppliedPhone = suppliedPhone;
        this.systemModstamp = systemModstamp;
        this.tasks = tasks;
        this.teamMembers = teamMembers;
        this.teamTemplateRecords = teamTemplateRecords;
        this.type = type;
    }


    /**
     * Gets the account value for this _case.
     * 
     * @return account
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account getAccount() {
        return account;
    }


    /**
     * Sets the account value for this _case.
     * 
     * @param account
     */
    public void setAccount(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account account) {
        this.account = account;
    }


    /**
     * Gets the accountId value for this _case.
     * 
     * @return accountId
     */
    public java.lang.String getAccountId() {
        return accountId;
    }


    /**
     * Sets the accountId value for this _case.
     * 
     * @param accountId
     */
    public void setAccountId(java.lang.String accountId) {
        this.accountId = accountId;
    }


    /**
     * Gets the activityHistories value for this _case.
     * 
     * @return activityHistories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getActivityHistories() {
        return activityHistories;
    }


    /**
     * Sets the activityHistories value for this _case.
     * 
     * @param activityHistories
     */
    public void setActivityHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories) {
        this.activityHistories = activityHistories;
    }


    /**
     * Gets the asset value for this _case.
     * 
     * @return asset
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Asset getAsset() {
        return asset;
    }


    /**
     * Sets the asset value for this _case.
     * 
     * @param asset
     */
    public void setAsset(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Asset asset) {
        this.asset = asset;
    }


    /**
     * Gets the assetId value for this _case.
     * 
     * @return assetId
     */
    public java.lang.String getAssetId() {
        return assetId;
    }


    /**
     * Sets the assetId value for this _case.
     * 
     * @param assetId
     */
    public void setAssetId(java.lang.String assetId) {
        this.assetId = assetId;
    }


    /**
     * Gets the attachments value for this _case.
     * 
     * @return attachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this _case.
     * 
     * @param attachments
     */
    public void setAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the businessHours value for this _case.
     * 
     * @return businessHours
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BusinessHours getBusinessHours() {
        return businessHours;
    }


    /**
     * Sets the businessHours value for this _case.
     * 
     * @param businessHours
     */
    public void setBusinessHours(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BusinessHours businessHours) {
        this.businessHours = businessHours;
    }


    /**
     * Gets the businessHoursId value for this _case.
     * 
     * @return businessHoursId
     */
    public java.lang.String getBusinessHoursId() {
        return businessHoursId;
    }


    /**
     * Sets the businessHoursId value for this _case.
     * 
     * @param businessHoursId
     */
    public void setBusinessHoursId(java.lang.String businessHoursId) {
        this.businessHoursId = businessHoursId;
    }


    /**
     * Gets the caseComments value for this _case.
     * 
     * @return caseComments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCaseComments() {
        return caseComments;
    }


    /**
     * Sets the caseComments value for this _case.
     * 
     * @param caseComments
     */
    public void setCaseComments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseComments) {
        this.caseComments = caseComments;
    }


    /**
     * Gets the caseContactRoles value for this _case.
     * 
     * @return caseContactRoles
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCaseContactRoles() {
        return caseContactRoles;
    }


    /**
     * Sets the caseContactRoles value for this _case.
     * 
     * @param caseContactRoles
     */
    public void setCaseContactRoles(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseContactRoles) {
        this.caseContactRoles = caseContactRoles;
    }


    /**
     * Gets the caseNumber value for this _case.
     * 
     * @return caseNumber
     */
    public java.lang.String getCaseNumber() {
        return caseNumber;
    }


    /**
     * Sets the caseNumber value for this _case.
     * 
     * @param caseNumber
     */
    public void setCaseNumber(java.lang.String caseNumber) {
        this.caseNumber = caseNumber;
    }


    /**
     * Gets the caseSolutions value for this _case.
     * 
     * @return caseSolutions
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCaseSolutions() {
        return caseSolutions;
    }


    /**
     * Sets the caseSolutions value for this _case.
     * 
     * @param caseSolutions
     */
    public void setCaseSolutions(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseSolutions) {
        this.caseSolutions = caseSolutions;
    }


    /**
     * Gets the cases value for this _case.
     * 
     * @return cases
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCases() {
        return cases;
    }


    /**
     * Sets the cases value for this _case.
     * 
     * @param cases
     */
    public void setCases(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases) {
        this.cases = cases;
    }


    /**
     * Gets the closedDate value for this _case.
     * 
     * @return closedDate
     */
    public java.util.Calendar getClosedDate() {
        return closedDate;
    }


    /**
     * Sets the closedDate value for this _case.
     * 
     * @param closedDate
     */
    public void setClosedDate(java.util.Calendar closedDate) {
        this.closedDate = closedDate;
    }


    /**
     * Gets the contact value for this _case.
     * 
     * @return contact
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this _case.
     * 
     * @param contact
     */
    public void setContact(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact contact) {
        this.contact = contact;
    }


    /**
     * Gets the contactId value for this _case.
     * 
     * @return contactId
     */
    public java.lang.String getContactId() {
        return contactId;
    }


    /**
     * Sets the contactId value for this _case.
     * 
     * @param contactId
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }


    /**
     * Gets the createdBy value for this _case.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this _case.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this _case.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this _case.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this _case.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this _case.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the description value for this _case.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this _case.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the engineeringReqNumber__c value for this _case.
     * 
     * @return engineeringReqNumber__c
     */
    public java.lang.String getEngineeringReqNumber__c() {
        return engineeringReqNumber__c;
    }


    /**
     * Sets the engineeringReqNumber__c value for this _case.
     * 
     * @param engineeringReqNumber__c
     */
    public void setEngineeringReqNumber__c(java.lang.String engineeringReqNumber__c) {
        this.engineeringReqNumber__c = engineeringReqNumber__c;
    }


    /**
     * Gets the events value for this _case.
     * 
     * @return events
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getEvents() {
        return events;
    }


    /**
     * Sets the events value for this _case.
     * 
     * @param events
     */
    public void setEvents(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events) {
        this.events = events;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this _case.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this _case.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this _case.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this _case.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the histories value for this _case.
     * 
     * @return histories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getHistories() {
        return histories;
    }


    /**
     * Sets the histories value for this _case.
     * 
     * @param histories
     */
    public void setHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories) {
        this.histories = histories;
    }


    /**
     * Gets the isClosed value for this _case.
     * 
     * @return isClosed
     */
    public java.lang.Boolean getIsClosed() {
        return isClosed;
    }


    /**
     * Sets the isClosed value for this _case.
     * 
     * @param isClosed
     */
    public void setIsClosed(java.lang.Boolean isClosed) {
        this.isClosed = isClosed;
    }


    /**
     * Gets the isClosedOnCreate value for this _case.
     * 
     * @return isClosedOnCreate
     */
    public java.lang.Boolean getIsClosedOnCreate() {
        return isClosedOnCreate;
    }


    /**
     * Sets the isClosedOnCreate value for this _case.
     * 
     * @param isClosedOnCreate
     */
    public void setIsClosedOnCreate(java.lang.Boolean isClosedOnCreate) {
        this.isClosedOnCreate = isClosedOnCreate;
    }


    /**
     * Gets the isDeleted value for this _case.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this _case.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the isEscalated value for this _case.
     * 
     * @return isEscalated
     */
    public java.lang.Boolean getIsEscalated() {
        return isEscalated;
    }


    /**
     * Sets the isEscalated value for this _case.
     * 
     * @param isEscalated
     */
    public void setIsEscalated(java.lang.Boolean isEscalated) {
        this.isEscalated = isEscalated;
    }


    /**
     * Gets the lastModifiedBy value for this _case.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this _case.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this _case.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this _case.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this _case.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this _case.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the openActivities value for this _case.
     * 
     * @return openActivities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpenActivities() {
        return openActivities;
    }


    /**
     * Sets the openActivities value for this _case.
     * 
     * @param openActivities
     */
    public void setOpenActivities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities) {
        this.openActivities = openActivities;
    }


    /**
     * Gets the origin value for this _case.
     * 
     * @return origin
     */
    public java.lang.String getOrigin() {
        return origin;
    }


    /**
     * Sets the origin value for this _case.
     * 
     * @param origin
     */
    public void setOrigin(java.lang.String origin) {
        this.origin = origin;
    }


    /**
     * Gets the owner value for this _case.
     * 
     * @return owner
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this _case.
     * 
     * @param owner
     */
    public void setOwner(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject owner) {
        this.owner = owner;
    }


    /**
     * Gets the ownerId value for this _case.
     * 
     * @return ownerId
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }


    /**
     * Sets the ownerId value for this _case.
     * 
     * @param ownerId
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }


    /**
     * Gets the parent value for this _case.
     * 
     * @return parent
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper._case getParent() {
        return parent;
    }


    /**
     * Sets the parent value for this _case.
     * 
     * @param parent
     */
    public void setParent(com.sforce.soap.schemas._class.scpq.SciQuotingHelper._case parent) {
        this.parent = parent;
    }


    /**
     * Gets the parentId value for this _case.
     * 
     * @return parentId
     */
    public java.lang.String getParentId() {
        return parentId;
    }


    /**
     * Sets the parentId value for this _case.
     * 
     * @param parentId
     */
    public void setParentId(java.lang.String parentId) {
        this.parentId = parentId;
    }


    /**
     * Gets the potentialLiability__c value for this _case.
     * 
     * @return potentialLiability__c
     */
    public java.lang.String getPotentialLiability__c() {
        return potentialLiability__c;
    }


    /**
     * Sets the potentialLiability__c value for this _case.
     * 
     * @param potentialLiability__c
     */
    public void setPotentialLiability__c(java.lang.String potentialLiability__c) {
        this.potentialLiability__c = potentialLiability__c;
    }


    /**
     * Gets the priority value for this _case.
     * 
     * @return priority
     */
    public java.lang.String getPriority() {
        return priority;
    }


    /**
     * Sets the priority value for this _case.
     * 
     * @param priority
     */
    public void setPriority(java.lang.String priority) {
        this.priority = priority;
    }


    /**
     * Gets the processInstances value for this _case.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this _case.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this _case.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this _case.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the product__c value for this _case.
     * 
     * @return product__c
     */
    public java.lang.String getProduct__c() {
        return product__c;
    }


    /**
     * Sets the product__c value for this _case.
     * 
     * @param product__c
     */
    public void setProduct__c(java.lang.String product__c) {
        this.product__c = product__c;
    }


    /**
     * Gets the reason value for this _case.
     * 
     * @return reason
     */
    public java.lang.String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this _case.
     * 
     * @param reason
     */
    public void setReason(java.lang.String reason) {
        this.reason = reason;
    }


    /**
     * Gets the SLAViolation__c value for this _case.
     * 
     * @return SLAViolation__c
     */
    public java.lang.String getSLAViolation__c() {
        return SLAViolation__c;
    }


    /**
     * Sets the SLAViolation__c value for this _case.
     * 
     * @param SLAViolation__c
     */
    public void setSLAViolation__c(java.lang.String SLAViolation__c) {
        this.SLAViolation__c = SLAViolation__c;
    }


    /**
     * Gets the shares value for this _case.
     * 
     * @return shares
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getShares() {
        return shares;
    }


    /**
     * Sets the shares value for this _case.
     * 
     * @param shares
     */
    public void setShares(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares) {
        this.shares = shares;
    }


    /**
     * Gets the solutions value for this _case.
     * 
     * @return solutions
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getSolutions() {
        return solutions;
    }


    /**
     * Sets the solutions value for this _case.
     * 
     * @param solutions
     */
    public void setSolutions(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult solutions) {
        this.solutions = solutions;
    }


    /**
     * Gets the status value for this _case.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this _case.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the subject value for this _case.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this _case.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the suppliedCompany value for this _case.
     * 
     * @return suppliedCompany
     */
    public java.lang.String getSuppliedCompany() {
        return suppliedCompany;
    }


    /**
     * Sets the suppliedCompany value for this _case.
     * 
     * @param suppliedCompany
     */
    public void setSuppliedCompany(java.lang.String suppliedCompany) {
        this.suppliedCompany = suppliedCompany;
    }


    /**
     * Gets the suppliedEmail value for this _case.
     * 
     * @return suppliedEmail
     */
    public java.lang.String getSuppliedEmail() {
        return suppliedEmail;
    }


    /**
     * Sets the suppliedEmail value for this _case.
     * 
     * @param suppliedEmail
     */
    public void setSuppliedEmail(java.lang.String suppliedEmail) {
        this.suppliedEmail = suppliedEmail;
    }


    /**
     * Gets the suppliedName value for this _case.
     * 
     * @return suppliedName
     */
    public java.lang.String getSuppliedName() {
        return suppliedName;
    }


    /**
     * Sets the suppliedName value for this _case.
     * 
     * @param suppliedName
     */
    public void setSuppliedName(java.lang.String suppliedName) {
        this.suppliedName = suppliedName;
    }


    /**
     * Gets the suppliedPhone value for this _case.
     * 
     * @return suppliedPhone
     */
    public java.lang.String getSuppliedPhone() {
        return suppliedPhone;
    }


    /**
     * Sets the suppliedPhone value for this _case.
     * 
     * @param suppliedPhone
     */
    public void setSuppliedPhone(java.lang.String suppliedPhone) {
        this.suppliedPhone = suppliedPhone;
    }


    /**
     * Gets the systemModstamp value for this _case.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this _case.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the tasks value for this _case.
     * 
     * @return tasks
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getTasks() {
        return tasks;
    }


    /**
     * Sets the tasks value for this _case.
     * 
     * @param tasks
     */
    public void setTasks(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks) {
        this.tasks = tasks;
    }


    /**
     * Gets the teamMembers value for this _case.
     * 
     * @return teamMembers
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getTeamMembers() {
        return teamMembers;
    }


    /**
     * Sets the teamMembers value for this _case.
     * 
     * @param teamMembers
     */
    public void setTeamMembers(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult teamMembers) {
        this.teamMembers = teamMembers;
    }


    /**
     * Gets the teamTemplateRecords value for this _case.
     * 
     * @return teamTemplateRecords
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getTeamTemplateRecords() {
        return teamTemplateRecords;
    }


    /**
     * Sets the teamTemplateRecords value for this _case.
     * 
     * @param teamTemplateRecords
     */
    public void setTeamTemplateRecords(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult teamTemplateRecords) {
        this.teamTemplateRecords = teamTemplateRecords;
    }


    /**
     * Gets the type value for this _case.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this _case.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _case)) return false;
        _case other = (_case) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.accountId==null && other.getAccountId()==null) || 
             (this.accountId!=null &&
              this.accountId.equals(other.getAccountId()))) &&
            ((this.activityHistories==null && other.getActivityHistories()==null) || 
             (this.activityHistories!=null &&
              this.activityHistories.equals(other.getActivityHistories()))) &&
            ((this.asset==null && other.getAsset()==null) || 
             (this.asset!=null &&
              this.asset.equals(other.getAsset()))) &&
            ((this.assetId==null && other.getAssetId()==null) || 
             (this.assetId!=null &&
              this.assetId.equals(other.getAssetId()))) &&
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              this.attachments.equals(other.getAttachments()))) &&
            ((this.businessHours==null && other.getBusinessHours()==null) || 
             (this.businessHours!=null &&
              this.businessHours.equals(other.getBusinessHours()))) &&
            ((this.businessHoursId==null && other.getBusinessHoursId()==null) || 
             (this.businessHoursId!=null &&
              this.businessHoursId.equals(other.getBusinessHoursId()))) &&
            ((this.caseComments==null && other.getCaseComments()==null) || 
             (this.caseComments!=null &&
              this.caseComments.equals(other.getCaseComments()))) &&
            ((this.caseContactRoles==null && other.getCaseContactRoles()==null) || 
             (this.caseContactRoles!=null &&
              this.caseContactRoles.equals(other.getCaseContactRoles()))) &&
            ((this.caseNumber==null && other.getCaseNumber()==null) || 
             (this.caseNumber!=null &&
              this.caseNumber.equals(other.getCaseNumber()))) &&
            ((this.caseSolutions==null && other.getCaseSolutions()==null) || 
             (this.caseSolutions!=null &&
              this.caseSolutions.equals(other.getCaseSolutions()))) &&
            ((this.cases==null && other.getCases()==null) || 
             (this.cases!=null &&
              this.cases.equals(other.getCases()))) &&
            ((this.closedDate==null && other.getClosedDate()==null) || 
             (this.closedDate!=null &&
              this.closedDate.equals(other.getClosedDate()))) &&
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            ((this.contactId==null && other.getContactId()==null) || 
             (this.contactId!=null &&
              this.contactId.equals(other.getContactId()))) &&
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdById==null && other.getCreatedById()==null) || 
             (this.createdById!=null &&
              this.createdById.equals(other.getCreatedById()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.engineeringReqNumber__c==null && other.getEngineeringReqNumber__c()==null) || 
             (this.engineeringReqNumber__c!=null &&
              this.engineeringReqNumber__c.equals(other.getEngineeringReqNumber__c()))) &&
            ((this.events==null && other.getEvents()==null) || 
             (this.events!=null &&
              this.events.equals(other.getEvents()))) &&
            ((this.feedSubscriptionsForEntity==null && other.getFeedSubscriptionsForEntity()==null) || 
             (this.feedSubscriptionsForEntity!=null &&
              this.feedSubscriptionsForEntity.equals(other.getFeedSubscriptionsForEntity()))) &&
            ((this.feeds==null && other.getFeeds()==null) || 
             (this.feeds!=null &&
              this.feeds.equals(other.getFeeds()))) &&
            ((this.histories==null && other.getHistories()==null) || 
             (this.histories!=null &&
              this.histories.equals(other.getHistories()))) &&
            ((this.isClosed==null && other.getIsClosed()==null) || 
             (this.isClosed!=null &&
              this.isClosed.equals(other.getIsClosed()))) &&
            ((this.isClosedOnCreate==null && other.getIsClosedOnCreate()==null) || 
             (this.isClosedOnCreate!=null &&
              this.isClosedOnCreate.equals(other.getIsClosedOnCreate()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.isEscalated==null && other.getIsEscalated()==null) || 
             (this.isEscalated!=null &&
              this.isEscalated.equals(other.getIsEscalated()))) &&
            ((this.lastModifiedBy==null && other.getLastModifiedBy()==null) || 
             (this.lastModifiedBy!=null &&
              this.lastModifiedBy.equals(other.getLastModifiedBy()))) &&
            ((this.lastModifiedById==null && other.getLastModifiedById()==null) || 
             (this.lastModifiedById!=null &&
              this.lastModifiedById.equals(other.getLastModifiedById()))) &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            ((this.openActivities==null && other.getOpenActivities()==null) || 
             (this.openActivities!=null &&
              this.openActivities.equals(other.getOpenActivities()))) &&
            ((this.origin==null && other.getOrigin()==null) || 
             (this.origin!=null &&
              this.origin.equals(other.getOrigin()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.ownerId==null && other.getOwnerId()==null) || 
             (this.ownerId!=null &&
              this.ownerId.equals(other.getOwnerId()))) &&
            ((this.parent==null && other.getParent()==null) || 
             (this.parent!=null &&
              this.parent.equals(other.getParent()))) &&
            ((this.parentId==null && other.getParentId()==null) || 
             (this.parentId!=null &&
              this.parentId.equals(other.getParentId()))) &&
            ((this.potentialLiability__c==null && other.getPotentialLiability__c()==null) || 
             (this.potentialLiability__c!=null &&
              this.potentialLiability__c.equals(other.getPotentialLiability__c()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.processInstances==null && other.getProcessInstances()==null) || 
             (this.processInstances!=null &&
              this.processInstances.equals(other.getProcessInstances()))) &&
            ((this.processSteps==null && other.getProcessSteps()==null) || 
             (this.processSteps!=null &&
              this.processSteps.equals(other.getProcessSteps()))) &&
            ((this.product__c==null && other.getProduct__c()==null) || 
             (this.product__c!=null &&
              this.product__c.equals(other.getProduct__c()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.SLAViolation__c==null && other.getSLAViolation__c()==null) || 
             (this.SLAViolation__c!=null &&
              this.SLAViolation__c.equals(other.getSLAViolation__c()))) &&
            ((this.shares==null && other.getShares()==null) || 
             (this.shares!=null &&
              this.shares.equals(other.getShares()))) &&
            ((this.solutions==null && other.getSolutions()==null) || 
             (this.solutions!=null &&
              this.solutions.equals(other.getSolutions()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.suppliedCompany==null && other.getSuppliedCompany()==null) || 
             (this.suppliedCompany!=null &&
              this.suppliedCompany.equals(other.getSuppliedCompany()))) &&
            ((this.suppliedEmail==null && other.getSuppliedEmail()==null) || 
             (this.suppliedEmail!=null &&
              this.suppliedEmail.equals(other.getSuppliedEmail()))) &&
            ((this.suppliedName==null && other.getSuppliedName()==null) || 
             (this.suppliedName!=null &&
              this.suppliedName.equals(other.getSuppliedName()))) &&
            ((this.suppliedPhone==null && other.getSuppliedPhone()==null) || 
             (this.suppliedPhone!=null &&
              this.suppliedPhone.equals(other.getSuppliedPhone()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.tasks==null && other.getTasks()==null) || 
             (this.tasks!=null &&
              this.tasks.equals(other.getTasks()))) &&
            ((this.teamMembers==null && other.getTeamMembers()==null) || 
             (this.teamMembers!=null &&
              this.teamMembers.equals(other.getTeamMembers()))) &&
            ((this.teamTemplateRecords==null && other.getTeamTemplateRecords()==null) || 
             (this.teamTemplateRecords!=null &&
              this.teamTemplateRecords.equals(other.getTeamTemplateRecords()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getAccountId() != null) {
            _hashCode += getAccountId().hashCode();
        }
        if (getActivityHistories() != null) {
            _hashCode += getActivityHistories().hashCode();
        }
        if (getAsset() != null) {
            _hashCode += getAsset().hashCode();
        }
        if (getAssetId() != null) {
            _hashCode += getAssetId().hashCode();
        }
        if (getAttachments() != null) {
            _hashCode += getAttachments().hashCode();
        }
        if (getBusinessHours() != null) {
            _hashCode += getBusinessHours().hashCode();
        }
        if (getBusinessHoursId() != null) {
            _hashCode += getBusinessHoursId().hashCode();
        }
        if (getCaseComments() != null) {
            _hashCode += getCaseComments().hashCode();
        }
        if (getCaseContactRoles() != null) {
            _hashCode += getCaseContactRoles().hashCode();
        }
        if (getCaseNumber() != null) {
            _hashCode += getCaseNumber().hashCode();
        }
        if (getCaseSolutions() != null) {
            _hashCode += getCaseSolutions().hashCode();
        }
        if (getCases() != null) {
            _hashCode += getCases().hashCode();
        }
        if (getClosedDate() != null) {
            _hashCode += getClosedDate().hashCode();
        }
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        if (getContactId() != null) {
            _hashCode += getContactId().hashCode();
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEngineeringReqNumber__c() != null) {
            _hashCode += getEngineeringReqNumber__c().hashCode();
        }
        if (getEvents() != null) {
            _hashCode += getEvents().hashCode();
        }
        if (getFeedSubscriptionsForEntity() != null) {
            _hashCode += getFeedSubscriptionsForEntity().hashCode();
        }
        if (getFeeds() != null) {
            _hashCode += getFeeds().hashCode();
        }
        if (getHistories() != null) {
            _hashCode += getHistories().hashCode();
        }
        if (getIsClosed() != null) {
            _hashCode += getIsClosed().hashCode();
        }
        if (getIsClosedOnCreate() != null) {
            _hashCode += getIsClosedOnCreate().hashCode();
        }
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getIsEscalated() != null) {
            _hashCode += getIsEscalated().hashCode();
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
        if (getOpenActivities() != null) {
            _hashCode += getOpenActivities().hashCode();
        }
        if (getOrigin() != null) {
            _hashCode += getOrigin().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getOwnerId() != null) {
            _hashCode += getOwnerId().hashCode();
        }
        if (getParent() != null) {
            _hashCode += getParent().hashCode();
        }
        if (getParentId() != null) {
            _hashCode += getParentId().hashCode();
        }
        if (getPotentialLiability__c() != null) {
            _hashCode += getPotentialLiability__c().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getProcessInstances() != null) {
            _hashCode += getProcessInstances().hashCode();
        }
        if (getProcessSteps() != null) {
            _hashCode += getProcessSteps().hashCode();
        }
        if (getProduct__c() != null) {
            _hashCode += getProduct__c().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getSLAViolation__c() != null) {
            _hashCode += getSLAViolation__c().hashCode();
        }
        if (getShares() != null) {
            _hashCode += getShares().hashCode();
        }
        if (getSolutions() != null) {
            _hashCode += getSolutions().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getSuppliedCompany() != null) {
            _hashCode += getSuppliedCompany().hashCode();
        }
        if (getSuppliedEmail() != null) {
            _hashCode += getSuppliedEmail().hashCode();
        }
        if (getSuppliedName() != null) {
            _hashCode += getSuppliedName().hashCode();
        }
        if (getSuppliedPhone() != null) {
            _hashCode += getSuppliedPhone().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getTasks() != null) {
            _hashCode += getTasks().hashCode();
        }
        if (getTeamMembers() != null) {
            _hashCode += getTeamMembers().hashCode();
        }
        if (getTeamTemplateRecords() != null) {
            _hashCode += getTeamTemplateRecords().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_case.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Case"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activityHistories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ActivityHistories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asset");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Asset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Asset"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assetId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AssetId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Attachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessHours");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BusinessHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BusinessHours"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessHoursId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BusinessHoursId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caseComments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseComments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caseContactRoles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseContactRoles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caseNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caseSolutions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseSolutions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cases");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Cases"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("closedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ClosedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContactId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("engineeringReqNumber__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EngineeringReqNumber__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("events");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Events"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("histories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Histories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isClosed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsClosed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isClosedOnCreate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsClosedOnCreate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("isEscalated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsEscalated"));
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
        elemField.setFieldName("openActivities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpenActivities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Origin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Owner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sObject"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ownerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OwnerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Parent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Case"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ParentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potentialLiability__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PotentialLiability__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Priority"));
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
        elemField.setFieldName("product__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Product__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SLAViolation__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SLAViolation__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shares");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Shares"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solutions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Solutions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppliedCompany");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SuppliedCompany"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppliedEmail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SuppliedEmail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppliedName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SuppliedName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppliedPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SuppliedPhone"));
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
        elemField.setFieldName("tasks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Tasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("teamMembers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TeamMembers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("teamTemplateRecords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TeamTemplateRecords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
