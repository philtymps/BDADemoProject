/**
 * Contact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Contact  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account account;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountContactRoles;

    private java.lang.String accountId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult assets;

    private java.lang.String assistantName;

    private java.lang.String assistantPhone;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments;

    private java.util.Date birthdate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult campaignMembers;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseContactRoles;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contractContactRoles;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contractsSigned;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private java.lang.String department;

    private java.lang.String description;

    private java.lang.Boolean doNotCall;

    private java.lang.String email;

    private java.util.Calendar emailBouncedDate;

    private java.lang.String emailBouncedReason;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult emailStatuses;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events;

    private java.lang.String fax;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds;

    private java.lang.String firstName;

    private java.lang.Boolean hasOptedOutOfEmail;

    private java.lang.Boolean hasOptedOutOfFax;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories;

    private java.lang.String homePhone;

    private java.lang.Boolean isDeleted;

    private java.lang.String languages__c;

    private java.util.Date lastActivityDate;

    private java.util.Calendar lastCURequestDate;

    private java.util.Calendar lastCUUpdateDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private java.lang.String lastName;

    private java.lang.String leadSource;

    private java.lang.String level__c;

    private java.lang.String mailingCity;

    private java.lang.String mailingCountry;

    private java.lang.Double mailingLatitude;

    private java.lang.Double mailingLongitude;

    private java.lang.String mailingPostalCode;

    private java.lang.String mailingState;

    private java.lang.String mailingStreet;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact masterRecord;

    private java.lang.String masterRecordId;

    private java.lang.String mobilePhone;

    private java.lang.String name;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunityContactRoles;

    private java.lang.String otherCity;

    private java.lang.String otherCountry;

    private java.lang.Double otherLatitude;

    private java.lang.Double otherLongitude;

    private java.lang.String otherPhone;

    private java.lang.String otherPostalCode;

    private java.lang.String otherState;

    private java.lang.String otherStreet;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner;

    private java.lang.String ownerId;

    private java.lang.String phone;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact reportsTo;

    private java.lang.String reportsToId;

    private java.lang.String salutation;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares;

    private java.util.Calendar systemModstamp;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks;

    private java.lang.String title;

    public Contact() {
    }

    public Contact(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account account,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountContactRoles,
           java.lang.String accountId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult assets,
           java.lang.String assistantName,
           java.lang.String assistantPhone,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments,
           java.util.Date birthdate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult campaignMembers,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseContactRoles,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contractContactRoles,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contractsSigned,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           java.lang.String department,
           java.lang.String description,
           java.lang.Boolean doNotCall,
           java.lang.String email,
           java.util.Calendar emailBouncedDate,
           java.lang.String emailBouncedReason,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult emailStatuses,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events,
           java.lang.String fax,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds,
           java.lang.String firstName,
           java.lang.Boolean hasOptedOutOfEmail,
           java.lang.Boolean hasOptedOutOfFax,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories,
           java.lang.String homePhone,
           java.lang.Boolean isDeleted,
           java.lang.String languages__c,
           java.util.Date lastActivityDate,
           java.util.Calendar lastCURequestDate,
           java.util.Calendar lastCUUpdateDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           java.lang.String lastName,
           java.lang.String leadSource,
           java.lang.String level__c,
           java.lang.String mailingCity,
           java.lang.String mailingCountry,
           java.lang.Double mailingLatitude,
           java.lang.Double mailingLongitude,
           java.lang.String mailingPostalCode,
           java.lang.String mailingState,
           java.lang.String mailingStreet,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact masterRecord,
           java.lang.String masterRecordId,
           java.lang.String mobilePhone,
           java.lang.String name,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunityContactRoles,
           java.lang.String otherCity,
           java.lang.String otherCountry,
           java.lang.Double otherLatitude,
           java.lang.Double otherLongitude,
           java.lang.String otherPhone,
           java.lang.String otherPostalCode,
           java.lang.String otherState,
           java.lang.String otherStreet,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner,
           java.lang.String ownerId,
           java.lang.String phone,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact reportsTo,
           java.lang.String reportsToId,
           java.lang.String salutation,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares,
           java.util.Calendar systemModstamp,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks,
           java.lang.String title) {
        super(
            fieldsToNull,
            id);
        this.account = account;
        this.accountContactRoles = accountContactRoles;
        this.accountId = accountId;
        this.activityHistories = activityHistories;
        this.assets = assets;
        this.assistantName = assistantName;
        this.assistantPhone = assistantPhone;
        this.attachments = attachments;
        this.birthdate = birthdate;
        this.campaignMembers = campaignMembers;
        this.caseContactRoles = caseContactRoles;
        this.cases = cases;
        this.contractContactRoles = contractContactRoles;
        this.contractsSigned = contractsSigned;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.department = department;
        this.description = description;
        this.doNotCall = doNotCall;
        this.email = email;
        this.emailBouncedDate = emailBouncedDate;
        this.emailBouncedReason = emailBouncedReason;
        this.emailStatuses = emailStatuses;
        this.events = events;
        this.fax = fax;
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
        this.feeds = feeds;
        this.firstName = firstName;
        this.hasOptedOutOfEmail = hasOptedOutOfEmail;
        this.hasOptedOutOfFax = hasOptedOutOfFax;
        this.histories = histories;
        this.homePhone = homePhone;
        this.isDeleted = isDeleted;
        this.languages__c = languages__c;
        this.lastActivityDate = lastActivityDate;
        this.lastCURequestDate = lastCURequestDate;
        this.lastCUUpdateDate = lastCUUpdateDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.lastName = lastName;
        this.leadSource = leadSource;
        this.level__c = level__c;
        this.mailingCity = mailingCity;
        this.mailingCountry = mailingCountry;
        this.mailingLatitude = mailingLatitude;
        this.mailingLongitude = mailingLongitude;
        this.mailingPostalCode = mailingPostalCode;
        this.mailingState = mailingState;
        this.mailingStreet = mailingStreet;
        this.masterRecord = masterRecord;
        this.masterRecordId = masterRecordId;
        this.mobilePhone = mobilePhone;
        this.name = name;
        this.notes = notes;
        this.notesAndAttachments = notesAndAttachments;
        this.openActivities = openActivities;
        this.opportunities = opportunities;
        this.opportunityContactRoles = opportunityContactRoles;
        this.otherCity = otherCity;
        this.otherCountry = otherCountry;
        this.otherLatitude = otherLatitude;
        this.otherLongitude = otherLongitude;
        this.otherPhone = otherPhone;
        this.otherPostalCode = otherPostalCode;
        this.otherState = otherState;
        this.otherStreet = otherStreet;
        this.owner = owner;
        this.ownerId = ownerId;
        this.phone = phone;
        this.processInstances = processInstances;
        this.processSteps = processSteps;
        this.reportsTo = reportsTo;
        this.reportsToId = reportsToId;
        this.salutation = salutation;
        this.shares = shares;
        this.systemModstamp = systemModstamp;
        this.tasks = tasks;
        this.title = title;
    }


    /**
     * Gets the account value for this Contact.
     * 
     * @return account
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account getAccount() {
        return account;
    }


    /**
     * Sets the account value for this Contact.
     * 
     * @param account
     */
    public void setAccount(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account account) {
        this.account = account;
    }


    /**
     * Gets the accountContactRoles value for this Contact.
     * 
     * @return accountContactRoles
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAccountContactRoles() {
        return accountContactRoles;
    }


    /**
     * Sets the accountContactRoles value for this Contact.
     * 
     * @param accountContactRoles
     */
    public void setAccountContactRoles(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountContactRoles) {
        this.accountContactRoles = accountContactRoles;
    }


    /**
     * Gets the accountId value for this Contact.
     * 
     * @return accountId
     */
    public java.lang.String getAccountId() {
        return accountId;
    }


    /**
     * Sets the accountId value for this Contact.
     * 
     * @param accountId
     */
    public void setAccountId(java.lang.String accountId) {
        this.accountId = accountId;
    }


    /**
     * Gets the activityHistories value for this Contact.
     * 
     * @return activityHistories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getActivityHistories() {
        return activityHistories;
    }


    /**
     * Sets the activityHistories value for this Contact.
     * 
     * @param activityHistories
     */
    public void setActivityHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories) {
        this.activityHistories = activityHistories;
    }


    /**
     * Gets the assets value for this Contact.
     * 
     * @return assets
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAssets() {
        return assets;
    }


    /**
     * Sets the assets value for this Contact.
     * 
     * @param assets
     */
    public void setAssets(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult assets) {
        this.assets = assets;
    }


    /**
     * Gets the assistantName value for this Contact.
     * 
     * @return assistantName
     */
    public java.lang.String getAssistantName() {
        return assistantName;
    }


    /**
     * Sets the assistantName value for this Contact.
     * 
     * @param assistantName
     */
    public void setAssistantName(java.lang.String assistantName) {
        this.assistantName = assistantName;
    }


    /**
     * Gets the assistantPhone value for this Contact.
     * 
     * @return assistantPhone
     */
    public java.lang.String getAssistantPhone() {
        return assistantPhone;
    }


    /**
     * Sets the assistantPhone value for this Contact.
     * 
     * @param assistantPhone
     */
    public void setAssistantPhone(java.lang.String assistantPhone) {
        this.assistantPhone = assistantPhone;
    }


    /**
     * Gets the attachments value for this Contact.
     * 
     * @return attachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Contact.
     * 
     * @param attachments
     */
    public void setAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the birthdate value for this Contact.
     * 
     * @return birthdate
     */
    public java.util.Date getBirthdate() {
        return birthdate;
    }


    /**
     * Sets the birthdate value for this Contact.
     * 
     * @param birthdate
     */
    public void setBirthdate(java.util.Date birthdate) {
        this.birthdate = birthdate;
    }


    /**
     * Gets the campaignMembers value for this Contact.
     * 
     * @return campaignMembers
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCampaignMembers() {
        return campaignMembers;
    }


    /**
     * Sets the campaignMembers value for this Contact.
     * 
     * @param campaignMembers
     */
    public void setCampaignMembers(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult campaignMembers) {
        this.campaignMembers = campaignMembers;
    }


    /**
     * Gets the caseContactRoles value for this Contact.
     * 
     * @return caseContactRoles
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCaseContactRoles() {
        return caseContactRoles;
    }


    /**
     * Sets the caseContactRoles value for this Contact.
     * 
     * @param caseContactRoles
     */
    public void setCaseContactRoles(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult caseContactRoles) {
        this.caseContactRoles = caseContactRoles;
    }


    /**
     * Gets the cases value for this Contact.
     * 
     * @return cases
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCases() {
        return cases;
    }


    /**
     * Sets the cases value for this Contact.
     * 
     * @param cases
     */
    public void setCases(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases) {
        this.cases = cases;
    }


    /**
     * Gets the contractContactRoles value for this Contact.
     * 
     * @return contractContactRoles
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getContractContactRoles() {
        return contractContactRoles;
    }


    /**
     * Sets the contractContactRoles value for this Contact.
     * 
     * @param contractContactRoles
     */
    public void setContractContactRoles(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contractContactRoles) {
        this.contractContactRoles = contractContactRoles;
    }


    /**
     * Gets the contractsSigned value for this Contact.
     * 
     * @return contractsSigned
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getContractsSigned() {
        return contractsSigned;
    }


    /**
     * Sets the contractsSigned value for this Contact.
     * 
     * @param contractsSigned
     */
    public void setContractsSigned(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contractsSigned) {
        this.contractsSigned = contractsSigned;
    }


    /**
     * Gets the createdBy value for this Contact.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Contact.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Contact.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Contact.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Contact.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Contact.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the department value for this Contact.
     * 
     * @return department
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this Contact.
     * 
     * @param department
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the description value for this Contact.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Contact.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the doNotCall value for this Contact.
     * 
     * @return doNotCall
     */
    public java.lang.Boolean getDoNotCall() {
        return doNotCall;
    }


    /**
     * Sets the doNotCall value for this Contact.
     * 
     * @param doNotCall
     */
    public void setDoNotCall(java.lang.Boolean doNotCall) {
        this.doNotCall = doNotCall;
    }


    /**
     * Gets the email value for this Contact.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this Contact.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the emailBouncedDate value for this Contact.
     * 
     * @return emailBouncedDate
     */
    public java.util.Calendar getEmailBouncedDate() {
        return emailBouncedDate;
    }


    /**
     * Sets the emailBouncedDate value for this Contact.
     * 
     * @param emailBouncedDate
     */
    public void setEmailBouncedDate(java.util.Calendar emailBouncedDate) {
        this.emailBouncedDate = emailBouncedDate;
    }


    /**
     * Gets the emailBouncedReason value for this Contact.
     * 
     * @return emailBouncedReason
     */
    public java.lang.String getEmailBouncedReason() {
        return emailBouncedReason;
    }


    /**
     * Sets the emailBouncedReason value for this Contact.
     * 
     * @param emailBouncedReason
     */
    public void setEmailBouncedReason(java.lang.String emailBouncedReason) {
        this.emailBouncedReason = emailBouncedReason;
    }


    /**
     * Gets the emailStatuses value for this Contact.
     * 
     * @return emailStatuses
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getEmailStatuses() {
        return emailStatuses;
    }


    /**
     * Sets the emailStatuses value for this Contact.
     * 
     * @param emailStatuses
     */
    public void setEmailStatuses(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult emailStatuses) {
        this.emailStatuses = emailStatuses;
    }


    /**
     * Gets the events value for this Contact.
     * 
     * @return events
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getEvents() {
        return events;
    }


    /**
     * Sets the events value for this Contact.
     * 
     * @param events
     */
    public void setEvents(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events) {
        this.events = events;
    }


    /**
     * Gets the fax value for this Contact.
     * 
     * @return fax
     */
    public java.lang.String getFax() {
        return fax;
    }


    /**
     * Sets the fax value for this Contact.
     * 
     * @param fax
     */
    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this Contact.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this Contact.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this Contact.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this Contact.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the firstName value for this Contact.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this Contact.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the hasOptedOutOfEmail value for this Contact.
     * 
     * @return hasOptedOutOfEmail
     */
    public java.lang.Boolean getHasOptedOutOfEmail() {
        return hasOptedOutOfEmail;
    }


    /**
     * Sets the hasOptedOutOfEmail value for this Contact.
     * 
     * @param hasOptedOutOfEmail
     */
    public void setHasOptedOutOfEmail(java.lang.Boolean hasOptedOutOfEmail) {
        this.hasOptedOutOfEmail = hasOptedOutOfEmail;
    }


    /**
     * Gets the hasOptedOutOfFax value for this Contact.
     * 
     * @return hasOptedOutOfFax
     */
    public java.lang.Boolean getHasOptedOutOfFax() {
        return hasOptedOutOfFax;
    }


    /**
     * Sets the hasOptedOutOfFax value for this Contact.
     * 
     * @param hasOptedOutOfFax
     */
    public void setHasOptedOutOfFax(java.lang.Boolean hasOptedOutOfFax) {
        this.hasOptedOutOfFax = hasOptedOutOfFax;
    }


    /**
     * Gets the histories value for this Contact.
     * 
     * @return histories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getHistories() {
        return histories;
    }


    /**
     * Sets the histories value for this Contact.
     * 
     * @param histories
     */
    public void setHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories) {
        this.histories = histories;
    }


    /**
     * Gets the homePhone value for this Contact.
     * 
     * @return homePhone
     */
    public java.lang.String getHomePhone() {
        return homePhone;
    }


    /**
     * Sets the homePhone value for this Contact.
     * 
     * @param homePhone
     */
    public void setHomePhone(java.lang.String homePhone) {
        this.homePhone = homePhone;
    }


    /**
     * Gets the isDeleted value for this Contact.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Contact.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the languages__c value for this Contact.
     * 
     * @return languages__c
     */
    public java.lang.String getLanguages__c() {
        return languages__c;
    }


    /**
     * Sets the languages__c value for this Contact.
     * 
     * @param languages__c
     */
    public void setLanguages__c(java.lang.String languages__c) {
        this.languages__c = languages__c;
    }


    /**
     * Gets the lastActivityDate value for this Contact.
     * 
     * @return lastActivityDate
     */
    public java.util.Date getLastActivityDate() {
        return lastActivityDate;
    }


    /**
     * Sets the lastActivityDate value for this Contact.
     * 
     * @param lastActivityDate
     */
    public void setLastActivityDate(java.util.Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }


    /**
     * Gets the lastCURequestDate value for this Contact.
     * 
     * @return lastCURequestDate
     */
    public java.util.Calendar getLastCURequestDate() {
        return lastCURequestDate;
    }


    /**
     * Sets the lastCURequestDate value for this Contact.
     * 
     * @param lastCURequestDate
     */
    public void setLastCURequestDate(java.util.Calendar lastCURequestDate) {
        this.lastCURequestDate = lastCURequestDate;
    }


    /**
     * Gets the lastCUUpdateDate value for this Contact.
     * 
     * @return lastCUUpdateDate
     */
    public java.util.Calendar getLastCUUpdateDate() {
        return lastCUUpdateDate;
    }


    /**
     * Sets the lastCUUpdateDate value for this Contact.
     * 
     * @param lastCUUpdateDate
     */
    public void setLastCUUpdateDate(java.util.Calendar lastCUUpdateDate) {
        this.lastCUUpdateDate = lastCUUpdateDate;
    }


    /**
     * Gets the lastModifiedBy value for this Contact.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Contact.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Contact.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Contact.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Contact.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Contact.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the lastName value for this Contact.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this Contact.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the leadSource value for this Contact.
     * 
     * @return leadSource
     */
    public java.lang.String getLeadSource() {
        return leadSource;
    }


    /**
     * Sets the leadSource value for this Contact.
     * 
     * @param leadSource
     */
    public void setLeadSource(java.lang.String leadSource) {
        this.leadSource = leadSource;
    }


    /**
     * Gets the level__c value for this Contact.
     * 
     * @return level__c
     */
    public java.lang.String getLevel__c() {
        return level__c;
    }


    /**
     * Sets the level__c value for this Contact.
     * 
     * @param level__c
     */
    public void setLevel__c(java.lang.String level__c) {
        this.level__c = level__c;
    }


    /**
     * Gets the mailingCity value for this Contact.
     * 
     * @return mailingCity
     */
    public java.lang.String getMailingCity() {
        return mailingCity;
    }


    /**
     * Sets the mailingCity value for this Contact.
     * 
     * @param mailingCity
     */
    public void setMailingCity(java.lang.String mailingCity) {
        this.mailingCity = mailingCity;
    }


    /**
     * Gets the mailingCountry value for this Contact.
     * 
     * @return mailingCountry
     */
    public java.lang.String getMailingCountry() {
        return mailingCountry;
    }


    /**
     * Sets the mailingCountry value for this Contact.
     * 
     * @param mailingCountry
     */
    public void setMailingCountry(java.lang.String mailingCountry) {
        this.mailingCountry = mailingCountry;
    }


    /**
     * Gets the mailingLatitude value for this Contact.
     * 
     * @return mailingLatitude
     */
    public java.lang.Double getMailingLatitude() {
        return mailingLatitude;
    }


    /**
     * Sets the mailingLatitude value for this Contact.
     * 
     * @param mailingLatitude
     */
    public void setMailingLatitude(java.lang.Double mailingLatitude) {
        this.mailingLatitude = mailingLatitude;
    }


    /**
     * Gets the mailingLongitude value for this Contact.
     * 
     * @return mailingLongitude
     */
    public java.lang.Double getMailingLongitude() {
        return mailingLongitude;
    }


    /**
     * Sets the mailingLongitude value for this Contact.
     * 
     * @param mailingLongitude
     */
    public void setMailingLongitude(java.lang.Double mailingLongitude) {
        this.mailingLongitude = mailingLongitude;
    }


    /**
     * Gets the mailingPostalCode value for this Contact.
     * 
     * @return mailingPostalCode
     */
    public java.lang.String getMailingPostalCode() {
        return mailingPostalCode;
    }


    /**
     * Sets the mailingPostalCode value for this Contact.
     * 
     * @param mailingPostalCode
     */
    public void setMailingPostalCode(java.lang.String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
    }


    /**
     * Gets the mailingState value for this Contact.
     * 
     * @return mailingState
     */
    public java.lang.String getMailingState() {
        return mailingState;
    }


    /**
     * Sets the mailingState value for this Contact.
     * 
     * @param mailingState
     */
    public void setMailingState(java.lang.String mailingState) {
        this.mailingState = mailingState;
    }


    /**
     * Gets the mailingStreet value for this Contact.
     * 
     * @return mailingStreet
     */
    public java.lang.String getMailingStreet() {
        return mailingStreet;
    }


    /**
     * Sets the mailingStreet value for this Contact.
     * 
     * @param mailingStreet
     */
    public void setMailingStreet(java.lang.String mailingStreet) {
        this.mailingStreet = mailingStreet;
    }


    /**
     * Gets the masterRecord value for this Contact.
     * 
     * @return masterRecord
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact getMasterRecord() {
        return masterRecord;
    }


    /**
     * Sets the masterRecord value for this Contact.
     * 
     * @param masterRecord
     */
    public void setMasterRecord(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact masterRecord) {
        this.masterRecord = masterRecord;
    }


    /**
     * Gets the masterRecordId value for this Contact.
     * 
     * @return masterRecordId
     */
    public java.lang.String getMasterRecordId() {
        return masterRecordId;
    }


    /**
     * Sets the masterRecordId value for this Contact.
     * 
     * @param masterRecordId
     */
    public void setMasterRecordId(java.lang.String masterRecordId) {
        this.masterRecordId = masterRecordId;
    }


    /**
     * Gets the mobilePhone value for this Contact.
     * 
     * @return mobilePhone
     */
    public java.lang.String getMobilePhone() {
        return mobilePhone;
    }


    /**
     * Sets the mobilePhone value for this Contact.
     * 
     * @param mobilePhone
     */
    public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


    /**
     * Gets the name value for this Contact.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Contact.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the notes value for this Contact.
     * 
     * @return notes
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Contact.
     * 
     * @param notes
     */
    public void setNotes(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes) {
        this.notes = notes;
    }


    /**
     * Gets the notesAndAttachments value for this Contact.
     * 
     * @return notesAndAttachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotesAndAttachments() {
        return notesAndAttachments;
    }


    /**
     * Sets the notesAndAttachments value for this Contact.
     * 
     * @param notesAndAttachments
     */
    public void setNotesAndAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments) {
        this.notesAndAttachments = notesAndAttachments;
    }


    /**
     * Gets the openActivities value for this Contact.
     * 
     * @return openActivities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpenActivities() {
        return openActivities;
    }


    /**
     * Sets the openActivities value for this Contact.
     * 
     * @param openActivities
     */
    public void setOpenActivities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities) {
        this.openActivities = openActivities;
    }


    /**
     * Gets the opportunities value for this Contact.
     * 
     * @return opportunities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpportunities() {
        return opportunities;
    }


    /**
     * Sets the opportunities value for this Contact.
     * 
     * @param opportunities
     */
    public void setOpportunities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities) {
        this.opportunities = opportunities;
    }


    /**
     * Gets the opportunityContactRoles value for this Contact.
     * 
     * @return opportunityContactRoles
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpportunityContactRoles() {
        return opportunityContactRoles;
    }


    /**
     * Sets the opportunityContactRoles value for this Contact.
     * 
     * @param opportunityContactRoles
     */
    public void setOpportunityContactRoles(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunityContactRoles) {
        this.opportunityContactRoles = opportunityContactRoles;
    }


    /**
     * Gets the otherCity value for this Contact.
     * 
     * @return otherCity
     */
    public java.lang.String getOtherCity() {
        return otherCity;
    }


    /**
     * Sets the otherCity value for this Contact.
     * 
     * @param otherCity
     */
    public void setOtherCity(java.lang.String otherCity) {
        this.otherCity = otherCity;
    }


    /**
     * Gets the otherCountry value for this Contact.
     * 
     * @return otherCountry
     */
    public java.lang.String getOtherCountry() {
        return otherCountry;
    }


    /**
     * Sets the otherCountry value for this Contact.
     * 
     * @param otherCountry
     */
    public void setOtherCountry(java.lang.String otherCountry) {
        this.otherCountry = otherCountry;
    }


    /**
     * Gets the otherLatitude value for this Contact.
     * 
     * @return otherLatitude
     */
    public java.lang.Double getOtherLatitude() {
        return otherLatitude;
    }


    /**
     * Sets the otherLatitude value for this Contact.
     * 
     * @param otherLatitude
     */
    public void setOtherLatitude(java.lang.Double otherLatitude) {
        this.otherLatitude = otherLatitude;
    }


    /**
     * Gets the otherLongitude value for this Contact.
     * 
     * @return otherLongitude
     */
    public java.lang.Double getOtherLongitude() {
        return otherLongitude;
    }


    /**
     * Sets the otherLongitude value for this Contact.
     * 
     * @param otherLongitude
     */
    public void setOtherLongitude(java.lang.Double otherLongitude) {
        this.otherLongitude = otherLongitude;
    }


    /**
     * Gets the otherPhone value for this Contact.
     * 
     * @return otherPhone
     */
    public java.lang.String getOtherPhone() {
        return otherPhone;
    }


    /**
     * Sets the otherPhone value for this Contact.
     * 
     * @param otherPhone
     */
    public void setOtherPhone(java.lang.String otherPhone) {
        this.otherPhone = otherPhone;
    }


    /**
     * Gets the otherPostalCode value for this Contact.
     * 
     * @return otherPostalCode
     */
    public java.lang.String getOtherPostalCode() {
        return otherPostalCode;
    }


    /**
     * Sets the otherPostalCode value for this Contact.
     * 
     * @param otherPostalCode
     */
    public void setOtherPostalCode(java.lang.String otherPostalCode) {
        this.otherPostalCode = otherPostalCode;
    }


    /**
     * Gets the otherState value for this Contact.
     * 
     * @return otherState
     */
    public java.lang.String getOtherState() {
        return otherState;
    }


    /**
     * Sets the otherState value for this Contact.
     * 
     * @param otherState
     */
    public void setOtherState(java.lang.String otherState) {
        this.otherState = otherState;
    }


    /**
     * Gets the otherStreet value for this Contact.
     * 
     * @return otherStreet
     */
    public java.lang.String getOtherStreet() {
        return otherStreet;
    }


    /**
     * Sets the otherStreet value for this Contact.
     * 
     * @param otherStreet
     */
    public void setOtherStreet(java.lang.String otherStreet) {
        this.otherStreet = otherStreet;
    }


    /**
     * Gets the owner value for this Contact.
     * 
     * @return owner
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this Contact.
     * 
     * @param owner
     */
    public void setOwner(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner) {
        this.owner = owner;
    }


    /**
     * Gets the ownerId value for this Contact.
     * 
     * @return ownerId
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }


    /**
     * Sets the ownerId value for this Contact.
     * 
     * @param ownerId
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }


    /**
     * Gets the phone value for this Contact.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this Contact.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the processInstances value for this Contact.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this Contact.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this Contact.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this Contact.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the reportsTo value for this Contact.
     * 
     * @return reportsTo
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact getReportsTo() {
        return reportsTo;
    }


    /**
     * Sets the reportsTo value for this Contact.
     * 
     * @param reportsTo
     */
    public void setReportsTo(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact reportsTo) {
        this.reportsTo = reportsTo;
    }


    /**
     * Gets the reportsToId value for this Contact.
     * 
     * @return reportsToId
     */
    public java.lang.String getReportsToId() {
        return reportsToId;
    }


    /**
     * Sets the reportsToId value for this Contact.
     * 
     * @param reportsToId
     */
    public void setReportsToId(java.lang.String reportsToId) {
        this.reportsToId = reportsToId;
    }


    /**
     * Gets the salutation value for this Contact.
     * 
     * @return salutation
     */
    public java.lang.String getSalutation() {
        return salutation;
    }


    /**
     * Sets the salutation value for this Contact.
     * 
     * @param salutation
     */
    public void setSalutation(java.lang.String salutation) {
        this.salutation = salutation;
    }


    /**
     * Gets the shares value for this Contact.
     * 
     * @return shares
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getShares() {
        return shares;
    }


    /**
     * Sets the shares value for this Contact.
     * 
     * @param shares
     */
    public void setShares(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares) {
        this.shares = shares;
    }


    /**
     * Gets the systemModstamp value for this Contact.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Contact.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the tasks value for this Contact.
     * 
     * @return tasks
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getTasks() {
        return tasks;
    }


    /**
     * Sets the tasks value for this Contact.
     * 
     * @param tasks
     */
    public void setTasks(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks) {
        this.tasks = tasks;
    }


    /**
     * Gets the title value for this Contact.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Contact.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
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
            ((this.accountContactRoles==null && other.getAccountContactRoles()==null) || 
             (this.accountContactRoles!=null &&
              this.accountContactRoles.equals(other.getAccountContactRoles()))) &&
            ((this.accountId==null && other.getAccountId()==null) || 
             (this.accountId!=null &&
              this.accountId.equals(other.getAccountId()))) &&
            ((this.activityHistories==null && other.getActivityHistories()==null) || 
             (this.activityHistories!=null &&
              this.activityHistories.equals(other.getActivityHistories()))) &&
            ((this.assets==null && other.getAssets()==null) || 
             (this.assets!=null &&
              this.assets.equals(other.getAssets()))) &&
            ((this.assistantName==null && other.getAssistantName()==null) || 
             (this.assistantName!=null &&
              this.assistantName.equals(other.getAssistantName()))) &&
            ((this.assistantPhone==null && other.getAssistantPhone()==null) || 
             (this.assistantPhone!=null &&
              this.assistantPhone.equals(other.getAssistantPhone()))) &&
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              this.attachments.equals(other.getAttachments()))) &&
            ((this.birthdate==null && other.getBirthdate()==null) || 
             (this.birthdate!=null &&
              this.birthdate.equals(other.getBirthdate()))) &&
            ((this.campaignMembers==null && other.getCampaignMembers()==null) || 
             (this.campaignMembers!=null &&
              this.campaignMembers.equals(other.getCampaignMembers()))) &&
            ((this.caseContactRoles==null && other.getCaseContactRoles()==null) || 
             (this.caseContactRoles!=null &&
              this.caseContactRoles.equals(other.getCaseContactRoles()))) &&
            ((this.cases==null && other.getCases()==null) || 
             (this.cases!=null &&
              this.cases.equals(other.getCases()))) &&
            ((this.contractContactRoles==null && other.getContractContactRoles()==null) || 
             (this.contractContactRoles!=null &&
              this.contractContactRoles.equals(other.getContractContactRoles()))) &&
            ((this.contractsSigned==null && other.getContractsSigned()==null) || 
             (this.contractsSigned!=null &&
              this.contractsSigned.equals(other.getContractsSigned()))) &&
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdById==null && other.getCreatedById()==null) || 
             (this.createdById!=null &&
              this.createdById.equals(other.getCreatedById()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.doNotCall==null && other.getDoNotCall()==null) || 
             (this.doNotCall!=null &&
              this.doNotCall.equals(other.getDoNotCall()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.emailBouncedDate==null && other.getEmailBouncedDate()==null) || 
             (this.emailBouncedDate!=null &&
              this.emailBouncedDate.equals(other.getEmailBouncedDate()))) &&
            ((this.emailBouncedReason==null && other.getEmailBouncedReason()==null) || 
             (this.emailBouncedReason!=null &&
              this.emailBouncedReason.equals(other.getEmailBouncedReason()))) &&
            ((this.emailStatuses==null && other.getEmailStatuses()==null) || 
             (this.emailStatuses!=null &&
              this.emailStatuses.equals(other.getEmailStatuses()))) &&
            ((this.events==null && other.getEvents()==null) || 
             (this.events!=null &&
              this.events.equals(other.getEvents()))) &&
            ((this.fax==null && other.getFax()==null) || 
             (this.fax!=null &&
              this.fax.equals(other.getFax()))) &&
            ((this.feedSubscriptionsForEntity==null && other.getFeedSubscriptionsForEntity()==null) || 
             (this.feedSubscriptionsForEntity!=null &&
              this.feedSubscriptionsForEntity.equals(other.getFeedSubscriptionsForEntity()))) &&
            ((this.feeds==null && other.getFeeds()==null) || 
             (this.feeds!=null &&
              this.feeds.equals(other.getFeeds()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.hasOptedOutOfEmail==null && other.getHasOptedOutOfEmail()==null) || 
             (this.hasOptedOutOfEmail!=null &&
              this.hasOptedOutOfEmail.equals(other.getHasOptedOutOfEmail()))) &&
            ((this.hasOptedOutOfFax==null && other.getHasOptedOutOfFax()==null) || 
             (this.hasOptedOutOfFax!=null &&
              this.hasOptedOutOfFax.equals(other.getHasOptedOutOfFax()))) &&
            ((this.histories==null && other.getHistories()==null) || 
             (this.histories!=null &&
              this.histories.equals(other.getHistories()))) &&
            ((this.homePhone==null && other.getHomePhone()==null) || 
             (this.homePhone!=null &&
              this.homePhone.equals(other.getHomePhone()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.languages__c==null && other.getLanguages__c()==null) || 
             (this.languages__c!=null &&
              this.languages__c.equals(other.getLanguages__c()))) &&
            ((this.lastActivityDate==null && other.getLastActivityDate()==null) || 
             (this.lastActivityDate!=null &&
              this.lastActivityDate.equals(other.getLastActivityDate()))) &&
            ((this.lastCURequestDate==null && other.getLastCURequestDate()==null) || 
             (this.lastCURequestDate!=null &&
              this.lastCURequestDate.equals(other.getLastCURequestDate()))) &&
            ((this.lastCUUpdateDate==null && other.getLastCUUpdateDate()==null) || 
             (this.lastCUUpdateDate!=null &&
              this.lastCUUpdateDate.equals(other.getLastCUUpdateDate()))) &&
            ((this.lastModifiedBy==null && other.getLastModifiedBy()==null) || 
             (this.lastModifiedBy!=null &&
              this.lastModifiedBy.equals(other.getLastModifiedBy()))) &&
            ((this.lastModifiedById==null && other.getLastModifiedById()==null) || 
             (this.lastModifiedById!=null &&
              this.lastModifiedById.equals(other.getLastModifiedById()))) &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.leadSource==null && other.getLeadSource()==null) || 
             (this.leadSource!=null &&
              this.leadSource.equals(other.getLeadSource()))) &&
            ((this.level__c==null && other.getLevel__c()==null) || 
             (this.level__c!=null &&
              this.level__c.equals(other.getLevel__c()))) &&
            ((this.mailingCity==null && other.getMailingCity()==null) || 
             (this.mailingCity!=null &&
              this.mailingCity.equals(other.getMailingCity()))) &&
            ((this.mailingCountry==null && other.getMailingCountry()==null) || 
             (this.mailingCountry!=null &&
              this.mailingCountry.equals(other.getMailingCountry()))) &&
            ((this.mailingLatitude==null && other.getMailingLatitude()==null) || 
             (this.mailingLatitude!=null &&
              this.mailingLatitude.equals(other.getMailingLatitude()))) &&
            ((this.mailingLongitude==null && other.getMailingLongitude()==null) || 
             (this.mailingLongitude!=null &&
              this.mailingLongitude.equals(other.getMailingLongitude()))) &&
            ((this.mailingPostalCode==null && other.getMailingPostalCode()==null) || 
             (this.mailingPostalCode!=null &&
              this.mailingPostalCode.equals(other.getMailingPostalCode()))) &&
            ((this.mailingState==null && other.getMailingState()==null) || 
             (this.mailingState!=null &&
              this.mailingState.equals(other.getMailingState()))) &&
            ((this.mailingStreet==null && other.getMailingStreet()==null) || 
             (this.mailingStreet!=null &&
              this.mailingStreet.equals(other.getMailingStreet()))) &&
            ((this.masterRecord==null && other.getMasterRecord()==null) || 
             (this.masterRecord!=null &&
              this.masterRecord.equals(other.getMasterRecord()))) &&
            ((this.masterRecordId==null && other.getMasterRecordId()==null) || 
             (this.masterRecordId!=null &&
              this.masterRecordId.equals(other.getMasterRecordId()))) &&
            ((this.mobilePhone==null && other.getMobilePhone()==null) || 
             (this.mobilePhone!=null &&
              this.mobilePhone.equals(other.getMobilePhone()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.notes==null && other.getNotes()==null) || 
             (this.notes!=null &&
              this.notes.equals(other.getNotes()))) &&
            ((this.notesAndAttachments==null && other.getNotesAndAttachments()==null) || 
             (this.notesAndAttachments!=null &&
              this.notesAndAttachments.equals(other.getNotesAndAttachments()))) &&
            ((this.openActivities==null && other.getOpenActivities()==null) || 
             (this.openActivities!=null &&
              this.openActivities.equals(other.getOpenActivities()))) &&
            ((this.opportunities==null && other.getOpportunities()==null) || 
             (this.opportunities!=null &&
              this.opportunities.equals(other.getOpportunities()))) &&
            ((this.opportunityContactRoles==null && other.getOpportunityContactRoles()==null) || 
             (this.opportunityContactRoles!=null &&
              this.opportunityContactRoles.equals(other.getOpportunityContactRoles()))) &&
            ((this.otherCity==null && other.getOtherCity()==null) || 
             (this.otherCity!=null &&
              this.otherCity.equals(other.getOtherCity()))) &&
            ((this.otherCountry==null && other.getOtherCountry()==null) || 
             (this.otherCountry!=null &&
              this.otherCountry.equals(other.getOtherCountry()))) &&
            ((this.otherLatitude==null && other.getOtherLatitude()==null) || 
             (this.otherLatitude!=null &&
              this.otherLatitude.equals(other.getOtherLatitude()))) &&
            ((this.otherLongitude==null && other.getOtherLongitude()==null) || 
             (this.otherLongitude!=null &&
              this.otherLongitude.equals(other.getOtherLongitude()))) &&
            ((this.otherPhone==null && other.getOtherPhone()==null) || 
             (this.otherPhone!=null &&
              this.otherPhone.equals(other.getOtherPhone()))) &&
            ((this.otherPostalCode==null && other.getOtherPostalCode()==null) || 
             (this.otherPostalCode!=null &&
              this.otherPostalCode.equals(other.getOtherPostalCode()))) &&
            ((this.otherState==null && other.getOtherState()==null) || 
             (this.otherState!=null &&
              this.otherState.equals(other.getOtherState()))) &&
            ((this.otherStreet==null && other.getOtherStreet()==null) || 
             (this.otherStreet!=null &&
              this.otherStreet.equals(other.getOtherStreet()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.ownerId==null && other.getOwnerId()==null) || 
             (this.ownerId!=null &&
              this.ownerId.equals(other.getOwnerId()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.processInstances==null && other.getProcessInstances()==null) || 
             (this.processInstances!=null &&
              this.processInstances.equals(other.getProcessInstances()))) &&
            ((this.processSteps==null && other.getProcessSteps()==null) || 
             (this.processSteps!=null &&
              this.processSteps.equals(other.getProcessSteps()))) &&
            ((this.reportsTo==null && other.getReportsTo()==null) || 
             (this.reportsTo!=null &&
              this.reportsTo.equals(other.getReportsTo()))) &&
            ((this.reportsToId==null && other.getReportsToId()==null) || 
             (this.reportsToId!=null &&
              this.reportsToId.equals(other.getReportsToId()))) &&
            ((this.salutation==null && other.getSalutation()==null) || 
             (this.salutation!=null &&
              this.salutation.equals(other.getSalutation()))) &&
            ((this.shares==null && other.getShares()==null) || 
             (this.shares!=null &&
              this.shares.equals(other.getShares()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.tasks==null && other.getTasks()==null) || 
             (this.tasks!=null &&
              this.tasks.equals(other.getTasks()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
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
        if (getAccountContactRoles() != null) {
            _hashCode += getAccountContactRoles().hashCode();
        }
        if (getAccountId() != null) {
            _hashCode += getAccountId().hashCode();
        }
        if (getActivityHistories() != null) {
            _hashCode += getActivityHistories().hashCode();
        }
        if (getAssets() != null) {
            _hashCode += getAssets().hashCode();
        }
        if (getAssistantName() != null) {
            _hashCode += getAssistantName().hashCode();
        }
        if (getAssistantPhone() != null) {
            _hashCode += getAssistantPhone().hashCode();
        }
        if (getAttachments() != null) {
            _hashCode += getAttachments().hashCode();
        }
        if (getBirthdate() != null) {
            _hashCode += getBirthdate().hashCode();
        }
        if (getCampaignMembers() != null) {
            _hashCode += getCampaignMembers().hashCode();
        }
        if (getCaseContactRoles() != null) {
            _hashCode += getCaseContactRoles().hashCode();
        }
        if (getCases() != null) {
            _hashCode += getCases().hashCode();
        }
        if (getContractContactRoles() != null) {
            _hashCode += getContractContactRoles().hashCode();
        }
        if (getContractsSigned() != null) {
            _hashCode += getContractsSigned().hashCode();
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
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDoNotCall() != null) {
            _hashCode += getDoNotCall().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getEmailBouncedDate() != null) {
            _hashCode += getEmailBouncedDate().hashCode();
        }
        if (getEmailBouncedReason() != null) {
            _hashCode += getEmailBouncedReason().hashCode();
        }
        if (getEmailStatuses() != null) {
            _hashCode += getEmailStatuses().hashCode();
        }
        if (getEvents() != null) {
            _hashCode += getEvents().hashCode();
        }
        if (getFax() != null) {
            _hashCode += getFax().hashCode();
        }
        if (getFeedSubscriptionsForEntity() != null) {
            _hashCode += getFeedSubscriptionsForEntity().hashCode();
        }
        if (getFeeds() != null) {
            _hashCode += getFeeds().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getHasOptedOutOfEmail() != null) {
            _hashCode += getHasOptedOutOfEmail().hashCode();
        }
        if (getHasOptedOutOfFax() != null) {
            _hashCode += getHasOptedOutOfFax().hashCode();
        }
        if (getHistories() != null) {
            _hashCode += getHistories().hashCode();
        }
        if (getHomePhone() != null) {
            _hashCode += getHomePhone().hashCode();
        }
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getLanguages__c() != null) {
            _hashCode += getLanguages__c().hashCode();
        }
        if (getLastActivityDate() != null) {
            _hashCode += getLastActivityDate().hashCode();
        }
        if (getLastCURequestDate() != null) {
            _hashCode += getLastCURequestDate().hashCode();
        }
        if (getLastCUUpdateDate() != null) {
            _hashCode += getLastCUUpdateDate().hashCode();
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
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getLeadSource() != null) {
            _hashCode += getLeadSource().hashCode();
        }
        if (getLevel__c() != null) {
            _hashCode += getLevel__c().hashCode();
        }
        if (getMailingCity() != null) {
            _hashCode += getMailingCity().hashCode();
        }
        if (getMailingCountry() != null) {
            _hashCode += getMailingCountry().hashCode();
        }
        if (getMailingLatitude() != null) {
            _hashCode += getMailingLatitude().hashCode();
        }
        if (getMailingLongitude() != null) {
            _hashCode += getMailingLongitude().hashCode();
        }
        if (getMailingPostalCode() != null) {
            _hashCode += getMailingPostalCode().hashCode();
        }
        if (getMailingState() != null) {
            _hashCode += getMailingState().hashCode();
        }
        if (getMailingStreet() != null) {
            _hashCode += getMailingStreet().hashCode();
        }
        if (getMasterRecord() != null) {
            _hashCode += getMasterRecord().hashCode();
        }
        if (getMasterRecordId() != null) {
            _hashCode += getMasterRecordId().hashCode();
        }
        if (getMobilePhone() != null) {
            _hashCode += getMobilePhone().hashCode();
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
        if (getOpenActivities() != null) {
            _hashCode += getOpenActivities().hashCode();
        }
        if (getOpportunities() != null) {
            _hashCode += getOpportunities().hashCode();
        }
        if (getOpportunityContactRoles() != null) {
            _hashCode += getOpportunityContactRoles().hashCode();
        }
        if (getOtherCity() != null) {
            _hashCode += getOtherCity().hashCode();
        }
        if (getOtherCountry() != null) {
            _hashCode += getOtherCountry().hashCode();
        }
        if (getOtherLatitude() != null) {
            _hashCode += getOtherLatitude().hashCode();
        }
        if (getOtherLongitude() != null) {
            _hashCode += getOtherLongitude().hashCode();
        }
        if (getOtherPhone() != null) {
            _hashCode += getOtherPhone().hashCode();
        }
        if (getOtherPostalCode() != null) {
            _hashCode += getOtherPostalCode().hashCode();
        }
        if (getOtherState() != null) {
            _hashCode += getOtherState().hashCode();
        }
        if (getOtherStreet() != null) {
            _hashCode += getOtherStreet().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getOwnerId() != null) {
            _hashCode += getOwnerId().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getProcessInstances() != null) {
            _hashCode += getProcessInstances().hashCode();
        }
        if (getProcessSteps() != null) {
            _hashCode += getProcessSteps().hashCode();
        }
        if (getReportsTo() != null) {
            _hashCode += getReportsTo().hashCode();
        }
        if (getReportsToId() != null) {
            _hashCode += getReportsToId().hashCode();
        }
        if (getSalutation() != null) {
            _hashCode += getSalutation().hashCode();
        }
        if (getShares() != null) {
            _hashCode += getShares().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getTasks() != null) {
            _hashCode += getTasks().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Contact.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contact"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountContactRoles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountContactRoles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("assets");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Assets"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assistantName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AssistantName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assistantPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AssistantPhone"));
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
        elemField.setFieldName("birthdate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Birthdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignMembers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CampaignMembers"));
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
        elemField.setFieldName("cases");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Cases"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractContactRoles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContractContactRoles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractsSigned");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContractsSigned"));
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
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("doNotCall");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "DoNotCall"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailBouncedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailBouncedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailBouncedReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailBouncedReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailStatuses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailStatuses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("fax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Fax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasOptedOutOfEmail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HasOptedOutOfEmail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasOptedOutOfFax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HasOptedOutOfFax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("homePhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HomePhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("languages__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Languages__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastActivityDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastActivityDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastCURequestDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastCURequestDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastCUUpdateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastCUUpdateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadSource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LeadSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("level__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Level__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingCity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingCity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingLatitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingLatitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingLongitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingLongitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingPostalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingPostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingStreet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailingStreet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("masterRecord");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MasterRecord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("masterRecordId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MasterRecordId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobilePhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MobilePhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("openActivities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpenActivities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Opportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opportunityContactRoles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityContactRoles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherCity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherLatitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherLatitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherLongitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherLongitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherPhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherPostalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherPostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherStreet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OtherStreet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Owner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "User"));
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
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Phone"));
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
        elemField.setFieldName("reportsTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ReportsTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportsToId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ReportsToId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salutation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Salutation"));
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
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Title"));
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
