/**
 * Account.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Account  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountContactRoles;

    private java.lang.String accountNumber;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountPartnersFrom;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountPartnersTo;

    private java.lang.String active__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories;

    private java.lang.Double annualRevenue;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult assets;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments;

    private java.lang.String billingCity;

    private java.lang.String billingCountry;

    private java.lang.Double billingLatitude;

    private java.lang.Double billingLongitude;

    private java.lang.String billingPostalCode;

    private java.lang.String billingState;

    private java.lang.String billingStreet;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contacts;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contracts;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private java.lang.String customerPriority__c;

    private java.lang.String description;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events;

    private java.lang.String fax;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories;

    private java.lang.String industry;

    private java.lang.Boolean isDeleted;

    private java.util.Date lastActivityDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account masterRecord;

    private java.lang.String masterRecordId;

    private java.lang.String name;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments;

    private java.lang.Integer numberOfEmployees;

    private java.lang.Double numberofLocations__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunityPartnersTo;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner;

    private java.lang.String ownerId;

    private java.lang.String ownership;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account parent;

    private java.lang.String parentId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult partnersFrom;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult partnersTo;

    private java.lang.String phone;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps;

    private java.lang.String rating;

    private java.util.Date SLAExpirationDate__c;

    private java.lang.String SLASerialNumber__c;

    private java.lang.String SLA__c;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares;

    private java.lang.String shippingCity;

    private java.lang.String shippingCountry;

    private java.lang.Double shippingLatitude;

    private java.lang.Double shippingLongitude;

    private java.lang.String shippingPostalCode;

    private java.lang.String shippingState;

    private java.lang.String shippingStreet;

    private java.lang.String sic;

    private java.lang.String site;

    private java.util.Calendar systemModstamp;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks;

    private java.lang.String tickerSymbol;

    private java.lang.String type;

    private java.lang.String upsellOpportunity__c;

    private java.lang.String website;

    public Account() {
    }

    public Account(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountContactRoles,
           java.lang.String accountNumber,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountPartnersFrom,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountPartnersTo,
           java.lang.String active__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories,
           java.lang.Double annualRevenue,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult assets,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments,
           java.lang.String billingCity,
           java.lang.String billingCountry,
           java.lang.Double billingLatitude,
           java.lang.Double billingLongitude,
           java.lang.String billingPostalCode,
           java.lang.String billingState,
           java.lang.String billingStreet,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contacts,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contracts,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           java.lang.String customerPriority__c,
           java.lang.String description,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events,
           java.lang.String fax,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories,
           java.lang.String industry,
           java.lang.Boolean isDeleted,
           java.util.Date lastActivityDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account masterRecord,
           java.lang.String masterRecordId,
           java.lang.String name,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments,
           java.lang.Integer numberOfEmployees,
           java.lang.Double numberofLocations__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunityPartnersTo,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner,
           java.lang.String ownerId,
           java.lang.String ownership,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account parent,
           java.lang.String parentId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult partnersFrom,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult partnersTo,
           java.lang.String phone,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps,
           java.lang.String rating,
           java.util.Date SLAExpirationDate__c,
           java.lang.String SLASerialNumber__c,
           java.lang.String SLA__c,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares,
           java.lang.String shippingCity,
           java.lang.String shippingCountry,
           java.lang.Double shippingLatitude,
           java.lang.Double shippingLongitude,
           java.lang.String shippingPostalCode,
           java.lang.String shippingState,
           java.lang.String shippingStreet,
           java.lang.String sic,
           java.lang.String site,
           java.util.Calendar systemModstamp,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks,
           java.lang.String tickerSymbol,
           java.lang.String type,
           java.lang.String upsellOpportunity__c,
           java.lang.String website) {
        super(
            fieldsToNull,
            id);
        this.accountContactRoles = accountContactRoles;
        this.accountNumber = accountNumber;
        this.accountPartnersFrom = accountPartnersFrom;
        this.accountPartnersTo = accountPartnersTo;
        this.active__c = active__c;
        this.activityHistories = activityHistories;
        this.annualRevenue = annualRevenue;
        this.assets = assets;
        this.attachments = attachments;
        this.billingCity = billingCity;
        this.billingCountry = billingCountry;
        this.billingLatitude = billingLatitude;
        this.billingLongitude = billingLongitude;
        this.billingPostalCode = billingPostalCode;
        this.billingState = billingState;
        this.billingStreet = billingStreet;
        this.cases = cases;
        this.contacts = contacts;
        this.contracts = contracts;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.customerPriority__c = customerPriority__c;
        this.description = description;
        this.events = events;
        this.fax = fax;
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
        this.feeds = feeds;
        this.histories = histories;
        this.industry = industry;
        this.isDeleted = isDeleted;
        this.lastActivityDate = lastActivityDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.masterRecord = masterRecord;
        this.masterRecordId = masterRecordId;
        this.name = name;
        this.notes = notes;
        this.notesAndAttachments = notesAndAttachments;
        this.numberOfEmployees = numberOfEmployees;
        this.numberofLocations__c = numberofLocations__c;
        this.openActivities = openActivities;
        this.opportunities = opportunities;
        this.opportunityPartnersTo = opportunityPartnersTo;
        this.owner = owner;
        this.ownerId = ownerId;
        this.ownership = ownership;
        this.parent = parent;
        this.parentId = parentId;
        this.partnersFrom = partnersFrom;
        this.partnersTo = partnersTo;
        this.phone = phone;
        this.processInstances = processInstances;
        this.processSteps = processSteps;
        this.rating = rating;
        this.SLAExpirationDate__c = SLAExpirationDate__c;
        this.SLASerialNumber__c = SLASerialNumber__c;
        this.SLA__c = SLA__c;
        this.shares = shares;
        this.shippingCity = shippingCity;
        this.shippingCountry = shippingCountry;
        this.shippingLatitude = shippingLatitude;
        this.shippingLongitude = shippingLongitude;
        this.shippingPostalCode = shippingPostalCode;
        this.shippingState = shippingState;
        this.shippingStreet = shippingStreet;
        this.sic = sic;
        this.site = site;
        this.systemModstamp = systemModstamp;
        this.tasks = tasks;
        this.tickerSymbol = tickerSymbol;
        this.type = type;
        this.upsellOpportunity__c = upsellOpportunity__c;
        this.website = website;
    }


    /**
     * Gets the accountContactRoles value for this Account.
     * 
     * @return accountContactRoles
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAccountContactRoles() {
        return accountContactRoles;
    }


    /**
     * Sets the accountContactRoles value for this Account.
     * 
     * @param accountContactRoles
     */
    public void setAccountContactRoles(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountContactRoles) {
        this.accountContactRoles = accountContactRoles;
    }


    /**
     * Gets the accountNumber value for this Account.
     * 
     * @return accountNumber
     */
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this Account.
     * 
     * @param accountNumber
     */
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the accountPartnersFrom value for this Account.
     * 
     * @return accountPartnersFrom
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAccountPartnersFrom() {
        return accountPartnersFrom;
    }


    /**
     * Sets the accountPartnersFrom value for this Account.
     * 
     * @param accountPartnersFrom
     */
    public void setAccountPartnersFrom(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountPartnersFrom) {
        this.accountPartnersFrom = accountPartnersFrom;
    }


    /**
     * Gets the accountPartnersTo value for this Account.
     * 
     * @return accountPartnersTo
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAccountPartnersTo() {
        return accountPartnersTo;
    }


    /**
     * Sets the accountPartnersTo value for this Account.
     * 
     * @param accountPartnersTo
     */
    public void setAccountPartnersTo(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult accountPartnersTo) {
        this.accountPartnersTo = accountPartnersTo;
    }


    /**
     * Gets the active__c value for this Account.
     * 
     * @return active__c
     */
    public java.lang.String getActive__c() {
        return active__c;
    }


    /**
     * Sets the active__c value for this Account.
     * 
     * @param active__c
     */
    public void setActive__c(java.lang.String active__c) {
        this.active__c = active__c;
    }


    /**
     * Gets the activityHistories value for this Account.
     * 
     * @return activityHistories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getActivityHistories() {
        return activityHistories;
    }


    /**
     * Sets the activityHistories value for this Account.
     * 
     * @param activityHistories
     */
    public void setActivityHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories) {
        this.activityHistories = activityHistories;
    }


    /**
     * Gets the annualRevenue value for this Account.
     * 
     * @return annualRevenue
     */
    public java.lang.Double getAnnualRevenue() {
        return annualRevenue;
    }


    /**
     * Sets the annualRevenue value for this Account.
     * 
     * @param annualRevenue
     */
    public void setAnnualRevenue(java.lang.Double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }


    /**
     * Gets the assets value for this Account.
     * 
     * @return assets
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAssets() {
        return assets;
    }


    /**
     * Sets the assets value for this Account.
     * 
     * @param assets
     */
    public void setAssets(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult assets) {
        this.assets = assets;
    }


    /**
     * Gets the attachments value for this Account.
     * 
     * @return attachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Account.
     * 
     * @param attachments
     */
    public void setAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the billingCity value for this Account.
     * 
     * @return billingCity
     */
    public java.lang.String getBillingCity() {
        return billingCity;
    }


    /**
     * Sets the billingCity value for this Account.
     * 
     * @param billingCity
     */
    public void setBillingCity(java.lang.String billingCity) {
        this.billingCity = billingCity;
    }


    /**
     * Gets the billingCountry value for this Account.
     * 
     * @return billingCountry
     */
    public java.lang.String getBillingCountry() {
        return billingCountry;
    }


    /**
     * Sets the billingCountry value for this Account.
     * 
     * @param billingCountry
     */
    public void setBillingCountry(java.lang.String billingCountry) {
        this.billingCountry = billingCountry;
    }


    /**
     * Gets the billingLatitude value for this Account.
     * 
     * @return billingLatitude
     */
    public java.lang.Double getBillingLatitude() {
        return billingLatitude;
    }


    /**
     * Sets the billingLatitude value for this Account.
     * 
     * @param billingLatitude
     */
    public void setBillingLatitude(java.lang.Double billingLatitude) {
        this.billingLatitude = billingLatitude;
    }


    /**
     * Gets the billingLongitude value for this Account.
     * 
     * @return billingLongitude
     */
    public java.lang.Double getBillingLongitude() {
        return billingLongitude;
    }


    /**
     * Sets the billingLongitude value for this Account.
     * 
     * @param billingLongitude
     */
    public void setBillingLongitude(java.lang.Double billingLongitude) {
        this.billingLongitude = billingLongitude;
    }


    /**
     * Gets the billingPostalCode value for this Account.
     * 
     * @return billingPostalCode
     */
    public java.lang.String getBillingPostalCode() {
        return billingPostalCode;
    }


    /**
     * Sets the billingPostalCode value for this Account.
     * 
     * @param billingPostalCode
     */
    public void setBillingPostalCode(java.lang.String billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }


    /**
     * Gets the billingState value for this Account.
     * 
     * @return billingState
     */
    public java.lang.String getBillingState() {
        return billingState;
    }


    /**
     * Sets the billingState value for this Account.
     * 
     * @param billingState
     */
    public void setBillingState(java.lang.String billingState) {
        this.billingState = billingState;
    }


    /**
     * Gets the billingStreet value for this Account.
     * 
     * @return billingStreet
     */
    public java.lang.String getBillingStreet() {
        return billingStreet;
    }


    /**
     * Sets the billingStreet value for this Account.
     * 
     * @param billingStreet
     */
    public void setBillingStreet(java.lang.String billingStreet) {
        this.billingStreet = billingStreet;
    }


    /**
     * Gets the cases value for this Account.
     * 
     * @return cases
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCases() {
        return cases;
    }


    /**
     * Sets the cases value for this Account.
     * 
     * @param cases
     */
    public void setCases(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult cases) {
        this.cases = cases;
    }


    /**
     * Gets the contacts value for this Account.
     * 
     * @return contacts
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getContacts() {
        return contacts;
    }


    /**
     * Sets the contacts value for this Account.
     * 
     * @param contacts
     */
    public void setContacts(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contacts) {
        this.contacts = contacts;
    }


    /**
     * Gets the contracts value for this Account.
     * 
     * @return contracts
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getContracts() {
        return contracts;
    }


    /**
     * Sets the contracts value for this Account.
     * 
     * @param contracts
     */
    public void setContracts(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult contracts) {
        this.contracts = contracts;
    }


    /**
     * Gets the createdBy value for this Account.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Account.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Account.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Account.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Account.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Account.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the customerPriority__c value for this Account.
     * 
     * @return customerPriority__c
     */
    public java.lang.String getCustomerPriority__c() {
        return customerPriority__c;
    }


    /**
     * Sets the customerPriority__c value for this Account.
     * 
     * @param customerPriority__c
     */
    public void setCustomerPriority__c(java.lang.String customerPriority__c) {
        this.customerPriority__c = customerPriority__c;
    }


    /**
     * Gets the description value for this Account.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Account.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the events value for this Account.
     * 
     * @return events
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getEvents() {
        return events;
    }


    /**
     * Sets the events value for this Account.
     * 
     * @param events
     */
    public void setEvents(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events) {
        this.events = events;
    }


    /**
     * Gets the fax value for this Account.
     * 
     * @return fax
     */
    public java.lang.String getFax() {
        return fax;
    }


    /**
     * Sets the fax value for this Account.
     * 
     * @param fax
     */
    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this Account.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this Account.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this Account.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this Account.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the histories value for this Account.
     * 
     * @return histories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getHistories() {
        return histories;
    }


    /**
     * Sets the histories value for this Account.
     * 
     * @param histories
     */
    public void setHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories) {
        this.histories = histories;
    }


    /**
     * Gets the industry value for this Account.
     * 
     * @return industry
     */
    public java.lang.String getIndustry() {
        return industry;
    }


    /**
     * Sets the industry value for this Account.
     * 
     * @param industry
     */
    public void setIndustry(java.lang.String industry) {
        this.industry = industry;
    }


    /**
     * Gets the isDeleted value for this Account.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Account.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the lastActivityDate value for this Account.
     * 
     * @return lastActivityDate
     */
    public java.util.Date getLastActivityDate() {
        return lastActivityDate;
    }


    /**
     * Sets the lastActivityDate value for this Account.
     * 
     * @param lastActivityDate
     */
    public void setLastActivityDate(java.util.Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }


    /**
     * Gets the lastModifiedBy value for this Account.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Account.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Account.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Account.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Account.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Account.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the masterRecord value for this Account.
     * 
     * @return masterRecord
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account getMasterRecord() {
        return masterRecord;
    }


    /**
     * Sets the masterRecord value for this Account.
     * 
     * @param masterRecord
     */
    public void setMasterRecord(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account masterRecord) {
        this.masterRecord = masterRecord;
    }


    /**
     * Gets the masterRecordId value for this Account.
     * 
     * @return masterRecordId
     */
    public java.lang.String getMasterRecordId() {
        return masterRecordId;
    }


    /**
     * Sets the masterRecordId value for this Account.
     * 
     * @param masterRecordId
     */
    public void setMasterRecordId(java.lang.String masterRecordId) {
        this.masterRecordId = masterRecordId;
    }


    /**
     * Gets the name value for this Account.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Account.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the notes value for this Account.
     * 
     * @return notes
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Account.
     * 
     * @param notes
     */
    public void setNotes(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notes) {
        this.notes = notes;
    }


    /**
     * Gets the notesAndAttachments value for this Account.
     * 
     * @return notesAndAttachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getNotesAndAttachments() {
        return notesAndAttachments;
    }


    /**
     * Sets the notesAndAttachments value for this Account.
     * 
     * @param notesAndAttachments
     */
    public void setNotesAndAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult notesAndAttachments) {
        this.notesAndAttachments = notesAndAttachments;
    }


    /**
     * Gets the numberOfEmployees value for this Account.
     * 
     * @return numberOfEmployees
     */
    public java.lang.Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }


    /**
     * Sets the numberOfEmployees value for this Account.
     * 
     * @param numberOfEmployees
     */
    public void setNumberOfEmployees(java.lang.Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }


    /**
     * Gets the numberofLocations__c value for this Account.
     * 
     * @return numberofLocations__c
     */
    public java.lang.Double getNumberofLocations__c() {
        return numberofLocations__c;
    }


    /**
     * Sets the numberofLocations__c value for this Account.
     * 
     * @param numberofLocations__c
     */
    public void setNumberofLocations__c(java.lang.Double numberofLocations__c) {
        this.numberofLocations__c = numberofLocations__c;
    }


    /**
     * Gets the openActivities value for this Account.
     * 
     * @return openActivities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpenActivities() {
        return openActivities;
    }


    /**
     * Sets the openActivities value for this Account.
     * 
     * @param openActivities
     */
    public void setOpenActivities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities) {
        this.openActivities = openActivities;
    }


    /**
     * Gets the opportunities value for this Account.
     * 
     * @return opportunities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpportunities() {
        return opportunities;
    }


    /**
     * Sets the opportunities value for this Account.
     * 
     * @param opportunities
     */
    public void setOpportunities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities) {
        this.opportunities = opportunities;
    }


    /**
     * Gets the opportunityPartnersTo value for this Account.
     * 
     * @return opportunityPartnersTo
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpportunityPartnersTo() {
        return opportunityPartnersTo;
    }


    /**
     * Sets the opportunityPartnersTo value for this Account.
     * 
     * @param opportunityPartnersTo
     */
    public void setOpportunityPartnersTo(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunityPartnersTo) {
        this.opportunityPartnersTo = opportunityPartnersTo;
    }


    /**
     * Gets the owner value for this Account.
     * 
     * @return owner
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this Account.
     * 
     * @param owner
     */
    public void setOwner(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner) {
        this.owner = owner;
    }


    /**
     * Gets the ownerId value for this Account.
     * 
     * @return ownerId
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }


    /**
     * Sets the ownerId value for this Account.
     * 
     * @param ownerId
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }


    /**
     * Gets the ownership value for this Account.
     * 
     * @return ownership
     */
    public java.lang.String getOwnership() {
        return ownership;
    }


    /**
     * Sets the ownership value for this Account.
     * 
     * @param ownership
     */
    public void setOwnership(java.lang.String ownership) {
        this.ownership = ownership;
    }


    /**
     * Gets the parent value for this Account.
     * 
     * @return parent
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account getParent() {
        return parent;
    }


    /**
     * Sets the parent value for this Account.
     * 
     * @param parent
     */
    public void setParent(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account parent) {
        this.parent = parent;
    }


    /**
     * Gets the parentId value for this Account.
     * 
     * @return parentId
     */
    public java.lang.String getParentId() {
        return parentId;
    }


    /**
     * Sets the parentId value for this Account.
     * 
     * @param parentId
     */
    public void setParentId(java.lang.String parentId) {
        this.parentId = parentId;
    }


    /**
     * Gets the partnersFrom value for this Account.
     * 
     * @return partnersFrom
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getPartnersFrom() {
        return partnersFrom;
    }


    /**
     * Sets the partnersFrom value for this Account.
     * 
     * @param partnersFrom
     */
    public void setPartnersFrom(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult partnersFrom) {
        this.partnersFrom = partnersFrom;
    }


    /**
     * Gets the partnersTo value for this Account.
     * 
     * @return partnersTo
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getPartnersTo() {
        return partnersTo;
    }


    /**
     * Sets the partnersTo value for this Account.
     * 
     * @param partnersTo
     */
    public void setPartnersTo(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult partnersTo) {
        this.partnersTo = partnersTo;
    }


    /**
     * Gets the phone value for this Account.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this Account.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the processInstances value for this Account.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this Account.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this Account.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this Account.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the rating value for this Account.
     * 
     * @return rating
     */
    public java.lang.String getRating() {
        return rating;
    }


    /**
     * Sets the rating value for this Account.
     * 
     * @param rating
     */
    public void setRating(java.lang.String rating) {
        this.rating = rating;
    }


    /**
     * Gets the SLAExpirationDate__c value for this Account.
     * 
     * @return SLAExpirationDate__c
     */
    public java.util.Date getSLAExpirationDate__c() {
        return SLAExpirationDate__c;
    }


    /**
     * Sets the SLAExpirationDate__c value for this Account.
     * 
     * @param SLAExpirationDate__c
     */
    public void setSLAExpirationDate__c(java.util.Date SLAExpirationDate__c) {
        this.SLAExpirationDate__c = SLAExpirationDate__c;
    }


    /**
     * Gets the SLASerialNumber__c value for this Account.
     * 
     * @return SLASerialNumber__c
     */
    public java.lang.String getSLASerialNumber__c() {
        return SLASerialNumber__c;
    }


    /**
     * Sets the SLASerialNumber__c value for this Account.
     * 
     * @param SLASerialNumber__c
     */
    public void setSLASerialNumber__c(java.lang.String SLASerialNumber__c) {
        this.SLASerialNumber__c = SLASerialNumber__c;
    }


    /**
     * Gets the SLA__c value for this Account.
     * 
     * @return SLA__c
     */
    public java.lang.String getSLA__c() {
        return SLA__c;
    }


    /**
     * Sets the SLA__c value for this Account.
     * 
     * @param SLA__c
     */
    public void setSLA__c(java.lang.String SLA__c) {
        this.SLA__c = SLA__c;
    }


    /**
     * Gets the shares value for this Account.
     * 
     * @return shares
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getShares() {
        return shares;
    }


    /**
     * Sets the shares value for this Account.
     * 
     * @param shares
     */
    public void setShares(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares) {
        this.shares = shares;
    }


    /**
     * Gets the shippingCity value for this Account.
     * 
     * @return shippingCity
     */
    public java.lang.String getShippingCity() {
        return shippingCity;
    }


    /**
     * Sets the shippingCity value for this Account.
     * 
     * @param shippingCity
     */
    public void setShippingCity(java.lang.String shippingCity) {
        this.shippingCity = shippingCity;
    }


    /**
     * Gets the shippingCountry value for this Account.
     * 
     * @return shippingCountry
     */
    public java.lang.String getShippingCountry() {
        return shippingCountry;
    }


    /**
     * Sets the shippingCountry value for this Account.
     * 
     * @param shippingCountry
     */
    public void setShippingCountry(java.lang.String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }


    /**
     * Gets the shippingLatitude value for this Account.
     * 
     * @return shippingLatitude
     */
    public java.lang.Double getShippingLatitude() {
        return shippingLatitude;
    }


    /**
     * Sets the shippingLatitude value for this Account.
     * 
     * @param shippingLatitude
     */
    public void setShippingLatitude(java.lang.Double shippingLatitude) {
        this.shippingLatitude = shippingLatitude;
    }


    /**
     * Gets the shippingLongitude value for this Account.
     * 
     * @return shippingLongitude
     */
    public java.lang.Double getShippingLongitude() {
        return shippingLongitude;
    }


    /**
     * Sets the shippingLongitude value for this Account.
     * 
     * @param shippingLongitude
     */
    public void setShippingLongitude(java.lang.Double shippingLongitude) {
        this.shippingLongitude = shippingLongitude;
    }


    /**
     * Gets the shippingPostalCode value for this Account.
     * 
     * @return shippingPostalCode
     */
    public java.lang.String getShippingPostalCode() {
        return shippingPostalCode;
    }


    /**
     * Sets the shippingPostalCode value for this Account.
     * 
     * @param shippingPostalCode
     */
    public void setShippingPostalCode(java.lang.String shippingPostalCode) {
        this.shippingPostalCode = shippingPostalCode;
    }


    /**
     * Gets the shippingState value for this Account.
     * 
     * @return shippingState
     */
    public java.lang.String getShippingState() {
        return shippingState;
    }


    /**
     * Sets the shippingState value for this Account.
     * 
     * @param shippingState
     */
    public void setShippingState(java.lang.String shippingState) {
        this.shippingState = shippingState;
    }


    /**
     * Gets the shippingStreet value for this Account.
     * 
     * @return shippingStreet
     */
    public java.lang.String getShippingStreet() {
        return shippingStreet;
    }


    /**
     * Sets the shippingStreet value for this Account.
     * 
     * @param shippingStreet
     */
    public void setShippingStreet(java.lang.String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }


    /**
     * Gets the sic value for this Account.
     * 
     * @return sic
     */
    public java.lang.String getSic() {
        return sic;
    }


    /**
     * Sets the sic value for this Account.
     * 
     * @param sic
     */
    public void setSic(java.lang.String sic) {
        this.sic = sic;
    }


    /**
     * Gets the site value for this Account.
     * 
     * @return site
     */
    public java.lang.String getSite() {
        return site;
    }


    /**
     * Sets the site value for this Account.
     * 
     * @param site
     */
    public void setSite(java.lang.String site) {
        this.site = site;
    }


    /**
     * Gets the systemModstamp value for this Account.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Account.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the tasks value for this Account.
     * 
     * @return tasks
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getTasks() {
        return tasks;
    }


    /**
     * Sets the tasks value for this Account.
     * 
     * @param tasks
     */
    public void setTasks(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks) {
        this.tasks = tasks;
    }


    /**
     * Gets the tickerSymbol value for this Account.
     * 
     * @return tickerSymbol
     */
    public java.lang.String getTickerSymbol() {
        return tickerSymbol;
    }


    /**
     * Sets the tickerSymbol value for this Account.
     * 
     * @param tickerSymbol
     */
    public void setTickerSymbol(java.lang.String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }


    /**
     * Gets the type value for this Account.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Account.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the upsellOpportunity__c value for this Account.
     * 
     * @return upsellOpportunity__c
     */
    public java.lang.String getUpsellOpportunity__c() {
        return upsellOpportunity__c;
    }


    /**
     * Sets the upsellOpportunity__c value for this Account.
     * 
     * @param upsellOpportunity__c
     */
    public void setUpsellOpportunity__c(java.lang.String upsellOpportunity__c) {
        this.upsellOpportunity__c = upsellOpportunity__c;
    }


    /**
     * Gets the website value for this Account.
     * 
     * @return website
     */
    public java.lang.String getWebsite() {
        return website;
    }


    /**
     * Sets the website value for this Account.
     * 
     * @param website
     */
    public void setWebsite(java.lang.String website) {
        this.website = website;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.accountContactRoles==null && other.getAccountContactRoles()==null) || 
             (this.accountContactRoles!=null &&
              this.accountContactRoles.equals(other.getAccountContactRoles()))) &&
            ((this.accountNumber==null && other.getAccountNumber()==null) || 
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            ((this.accountPartnersFrom==null && other.getAccountPartnersFrom()==null) || 
             (this.accountPartnersFrom!=null &&
              this.accountPartnersFrom.equals(other.getAccountPartnersFrom()))) &&
            ((this.accountPartnersTo==null && other.getAccountPartnersTo()==null) || 
             (this.accountPartnersTo!=null &&
              this.accountPartnersTo.equals(other.getAccountPartnersTo()))) &&
            ((this.active__c==null && other.getActive__c()==null) || 
             (this.active__c!=null &&
              this.active__c.equals(other.getActive__c()))) &&
            ((this.activityHistories==null && other.getActivityHistories()==null) || 
             (this.activityHistories!=null &&
              this.activityHistories.equals(other.getActivityHistories()))) &&
            ((this.annualRevenue==null && other.getAnnualRevenue()==null) || 
             (this.annualRevenue!=null &&
              this.annualRevenue.equals(other.getAnnualRevenue()))) &&
            ((this.assets==null && other.getAssets()==null) || 
             (this.assets!=null &&
              this.assets.equals(other.getAssets()))) &&
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              this.attachments.equals(other.getAttachments()))) &&
            ((this.billingCity==null && other.getBillingCity()==null) || 
             (this.billingCity!=null &&
              this.billingCity.equals(other.getBillingCity()))) &&
            ((this.billingCountry==null && other.getBillingCountry()==null) || 
             (this.billingCountry!=null &&
              this.billingCountry.equals(other.getBillingCountry()))) &&
            ((this.billingLatitude==null && other.getBillingLatitude()==null) || 
             (this.billingLatitude!=null &&
              this.billingLatitude.equals(other.getBillingLatitude()))) &&
            ((this.billingLongitude==null && other.getBillingLongitude()==null) || 
             (this.billingLongitude!=null &&
              this.billingLongitude.equals(other.getBillingLongitude()))) &&
            ((this.billingPostalCode==null && other.getBillingPostalCode()==null) || 
             (this.billingPostalCode!=null &&
              this.billingPostalCode.equals(other.getBillingPostalCode()))) &&
            ((this.billingState==null && other.getBillingState()==null) || 
             (this.billingState!=null &&
              this.billingState.equals(other.getBillingState()))) &&
            ((this.billingStreet==null && other.getBillingStreet()==null) || 
             (this.billingStreet!=null &&
              this.billingStreet.equals(other.getBillingStreet()))) &&
            ((this.cases==null && other.getCases()==null) || 
             (this.cases!=null &&
              this.cases.equals(other.getCases()))) &&
            ((this.contacts==null && other.getContacts()==null) || 
             (this.contacts!=null &&
              this.contacts.equals(other.getContacts()))) &&
            ((this.contracts==null && other.getContracts()==null) || 
             (this.contracts!=null &&
              this.contracts.equals(other.getContracts()))) &&
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdById==null && other.getCreatedById()==null) || 
             (this.createdById!=null &&
              this.createdById.equals(other.getCreatedById()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.customerPriority__c==null && other.getCustomerPriority__c()==null) || 
             (this.customerPriority__c!=null &&
              this.customerPriority__c.equals(other.getCustomerPriority__c()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
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
            ((this.histories==null && other.getHistories()==null) || 
             (this.histories!=null &&
              this.histories.equals(other.getHistories()))) &&
            ((this.industry==null && other.getIndustry()==null) || 
             (this.industry!=null &&
              this.industry.equals(other.getIndustry()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.lastActivityDate==null && other.getLastActivityDate()==null) || 
             (this.lastActivityDate!=null &&
              this.lastActivityDate.equals(other.getLastActivityDate()))) &&
            ((this.lastModifiedBy==null && other.getLastModifiedBy()==null) || 
             (this.lastModifiedBy!=null &&
              this.lastModifiedBy.equals(other.getLastModifiedBy()))) &&
            ((this.lastModifiedById==null && other.getLastModifiedById()==null) || 
             (this.lastModifiedById!=null &&
              this.lastModifiedById.equals(other.getLastModifiedById()))) &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            ((this.masterRecord==null && other.getMasterRecord()==null) || 
             (this.masterRecord!=null &&
              this.masterRecord.equals(other.getMasterRecord()))) &&
            ((this.masterRecordId==null && other.getMasterRecordId()==null) || 
             (this.masterRecordId!=null &&
              this.masterRecordId.equals(other.getMasterRecordId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.notes==null && other.getNotes()==null) || 
             (this.notes!=null &&
              this.notes.equals(other.getNotes()))) &&
            ((this.notesAndAttachments==null && other.getNotesAndAttachments()==null) || 
             (this.notesAndAttachments!=null &&
              this.notesAndAttachments.equals(other.getNotesAndAttachments()))) &&
            ((this.numberOfEmployees==null && other.getNumberOfEmployees()==null) || 
             (this.numberOfEmployees!=null &&
              this.numberOfEmployees.equals(other.getNumberOfEmployees()))) &&
            ((this.numberofLocations__c==null && other.getNumberofLocations__c()==null) || 
             (this.numberofLocations__c!=null &&
              this.numberofLocations__c.equals(other.getNumberofLocations__c()))) &&
            ((this.openActivities==null && other.getOpenActivities()==null) || 
             (this.openActivities!=null &&
              this.openActivities.equals(other.getOpenActivities()))) &&
            ((this.opportunities==null && other.getOpportunities()==null) || 
             (this.opportunities!=null &&
              this.opportunities.equals(other.getOpportunities()))) &&
            ((this.opportunityPartnersTo==null && other.getOpportunityPartnersTo()==null) || 
             (this.opportunityPartnersTo!=null &&
              this.opportunityPartnersTo.equals(other.getOpportunityPartnersTo()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.ownerId==null && other.getOwnerId()==null) || 
             (this.ownerId!=null &&
              this.ownerId.equals(other.getOwnerId()))) &&
            ((this.ownership==null && other.getOwnership()==null) || 
             (this.ownership!=null &&
              this.ownership.equals(other.getOwnership()))) &&
            ((this.parent==null && other.getParent()==null) || 
             (this.parent!=null &&
              this.parent.equals(other.getParent()))) &&
            ((this.parentId==null && other.getParentId()==null) || 
             (this.parentId!=null &&
              this.parentId.equals(other.getParentId()))) &&
            ((this.partnersFrom==null && other.getPartnersFrom()==null) || 
             (this.partnersFrom!=null &&
              this.partnersFrom.equals(other.getPartnersFrom()))) &&
            ((this.partnersTo==null && other.getPartnersTo()==null) || 
             (this.partnersTo!=null &&
              this.partnersTo.equals(other.getPartnersTo()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.processInstances==null && other.getProcessInstances()==null) || 
             (this.processInstances!=null &&
              this.processInstances.equals(other.getProcessInstances()))) &&
            ((this.processSteps==null && other.getProcessSteps()==null) || 
             (this.processSteps!=null &&
              this.processSteps.equals(other.getProcessSteps()))) &&
            ((this.rating==null && other.getRating()==null) || 
             (this.rating!=null &&
              this.rating.equals(other.getRating()))) &&
            ((this.SLAExpirationDate__c==null && other.getSLAExpirationDate__c()==null) || 
             (this.SLAExpirationDate__c!=null &&
              this.SLAExpirationDate__c.equals(other.getSLAExpirationDate__c()))) &&
            ((this.SLASerialNumber__c==null && other.getSLASerialNumber__c()==null) || 
             (this.SLASerialNumber__c!=null &&
              this.SLASerialNumber__c.equals(other.getSLASerialNumber__c()))) &&
            ((this.SLA__c==null && other.getSLA__c()==null) || 
             (this.SLA__c!=null &&
              this.SLA__c.equals(other.getSLA__c()))) &&
            ((this.shares==null && other.getShares()==null) || 
             (this.shares!=null &&
              this.shares.equals(other.getShares()))) &&
            ((this.shippingCity==null && other.getShippingCity()==null) || 
             (this.shippingCity!=null &&
              this.shippingCity.equals(other.getShippingCity()))) &&
            ((this.shippingCountry==null && other.getShippingCountry()==null) || 
             (this.shippingCountry!=null &&
              this.shippingCountry.equals(other.getShippingCountry()))) &&
            ((this.shippingLatitude==null && other.getShippingLatitude()==null) || 
             (this.shippingLatitude!=null &&
              this.shippingLatitude.equals(other.getShippingLatitude()))) &&
            ((this.shippingLongitude==null && other.getShippingLongitude()==null) || 
             (this.shippingLongitude!=null &&
              this.shippingLongitude.equals(other.getShippingLongitude()))) &&
            ((this.shippingPostalCode==null && other.getShippingPostalCode()==null) || 
             (this.shippingPostalCode!=null &&
              this.shippingPostalCode.equals(other.getShippingPostalCode()))) &&
            ((this.shippingState==null && other.getShippingState()==null) || 
             (this.shippingState!=null &&
              this.shippingState.equals(other.getShippingState()))) &&
            ((this.shippingStreet==null && other.getShippingStreet()==null) || 
             (this.shippingStreet!=null &&
              this.shippingStreet.equals(other.getShippingStreet()))) &&
            ((this.sic==null && other.getSic()==null) || 
             (this.sic!=null &&
              this.sic.equals(other.getSic()))) &&
            ((this.site==null && other.getSite()==null) || 
             (this.site!=null &&
              this.site.equals(other.getSite()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.tasks==null && other.getTasks()==null) || 
             (this.tasks!=null &&
              this.tasks.equals(other.getTasks()))) &&
            ((this.tickerSymbol==null && other.getTickerSymbol()==null) || 
             (this.tickerSymbol!=null &&
              this.tickerSymbol.equals(other.getTickerSymbol()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.upsellOpportunity__c==null && other.getUpsellOpportunity__c()==null) || 
             (this.upsellOpportunity__c!=null &&
              this.upsellOpportunity__c.equals(other.getUpsellOpportunity__c()))) &&
            ((this.website==null && other.getWebsite()==null) || 
             (this.website!=null &&
              this.website.equals(other.getWebsite())));
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
        if (getAccountContactRoles() != null) {
            _hashCode += getAccountContactRoles().hashCode();
        }
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        if (getAccountPartnersFrom() != null) {
            _hashCode += getAccountPartnersFrom().hashCode();
        }
        if (getAccountPartnersTo() != null) {
            _hashCode += getAccountPartnersTo().hashCode();
        }
        if (getActive__c() != null) {
            _hashCode += getActive__c().hashCode();
        }
        if (getActivityHistories() != null) {
            _hashCode += getActivityHistories().hashCode();
        }
        if (getAnnualRevenue() != null) {
            _hashCode += getAnnualRevenue().hashCode();
        }
        if (getAssets() != null) {
            _hashCode += getAssets().hashCode();
        }
        if (getAttachments() != null) {
            _hashCode += getAttachments().hashCode();
        }
        if (getBillingCity() != null) {
            _hashCode += getBillingCity().hashCode();
        }
        if (getBillingCountry() != null) {
            _hashCode += getBillingCountry().hashCode();
        }
        if (getBillingLatitude() != null) {
            _hashCode += getBillingLatitude().hashCode();
        }
        if (getBillingLongitude() != null) {
            _hashCode += getBillingLongitude().hashCode();
        }
        if (getBillingPostalCode() != null) {
            _hashCode += getBillingPostalCode().hashCode();
        }
        if (getBillingState() != null) {
            _hashCode += getBillingState().hashCode();
        }
        if (getBillingStreet() != null) {
            _hashCode += getBillingStreet().hashCode();
        }
        if (getCases() != null) {
            _hashCode += getCases().hashCode();
        }
        if (getContacts() != null) {
            _hashCode += getContacts().hashCode();
        }
        if (getContracts() != null) {
            _hashCode += getContracts().hashCode();
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
        if (getCustomerPriority__c() != null) {
            _hashCode += getCustomerPriority__c().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
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
        if (getHistories() != null) {
            _hashCode += getHistories().hashCode();
        }
        if (getIndustry() != null) {
            _hashCode += getIndustry().hashCode();
        }
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getLastActivityDate() != null) {
            _hashCode += getLastActivityDate().hashCode();
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
        if (getMasterRecord() != null) {
            _hashCode += getMasterRecord().hashCode();
        }
        if (getMasterRecordId() != null) {
            _hashCode += getMasterRecordId().hashCode();
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
        if (getNumberOfEmployees() != null) {
            _hashCode += getNumberOfEmployees().hashCode();
        }
        if (getNumberofLocations__c() != null) {
            _hashCode += getNumberofLocations__c().hashCode();
        }
        if (getOpenActivities() != null) {
            _hashCode += getOpenActivities().hashCode();
        }
        if (getOpportunities() != null) {
            _hashCode += getOpportunities().hashCode();
        }
        if (getOpportunityPartnersTo() != null) {
            _hashCode += getOpportunityPartnersTo().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getOwnerId() != null) {
            _hashCode += getOwnerId().hashCode();
        }
        if (getOwnership() != null) {
            _hashCode += getOwnership().hashCode();
        }
        if (getParent() != null) {
            _hashCode += getParent().hashCode();
        }
        if (getParentId() != null) {
            _hashCode += getParentId().hashCode();
        }
        if (getPartnersFrom() != null) {
            _hashCode += getPartnersFrom().hashCode();
        }
        if (getPartnersTo() != null) {
            _hashCode += getPartnersTo().hashCode();
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
        if (getRating() != null) {
            _hashCode += getRating().hashCode();
        }
        if (getSLAExpirationDate__c() != null) {
            _hashCode += getSLAExpirationDate__c().hashCode();
        }
        if (getSLASerialNumber__c() != null) {
            _hashCode += getSLASerialNumber__c().hashCode();
        }
        if (getSLA__c() != null) {
            _hashCode += getSLA__c().hashCode();
        }
        if (getShares() != null) {
            _hashCode += getShares().hashCode();
        }
        if (getShippingCity() != null) {
            _hashCode += getShippingCity().hashCode();
        }
        if (getShippingCountry() != null) {
            _hashCode += getShippingCountry().hashCode();
        }
        if (getShippingLatitude() != null) {
            _hashCode += getShippingLatitude().hashCode();
        }
        if (getShippingLongitude() != null) {
            _hashCode += getShippingLongitude().hashCode();
        }
        if (getShippingPostalCode() != null) {
            _hashCode += getShippingPostalCode().hashCode();
        }
        if (getShippingState() != null) {
            _hashCode += getShippingState().hashCode();
        }
        if (getShippingStreet() != null) {
            _hashCode += getShippingStreet().hashCode();
        }
        if (getSic() != null) {
            _hashCode += getSic().hashCode();
        }
        if (getSite() != null) {
            _hashCode += getSite().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getTasks() != null) {
            _hashCode += getTasks().hashCode();
        }
        if (getTickerSymbol() != null) {
            _hashCode += getTickerSymbol().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getUpsellOpportunity__c() != null) {
            _hashCode += getUpsellOpportunity__c().hashCode();
        }
        if (getWebsite() != null) {
            _hashCode += getWebsite().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Account.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountContactRoles");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountContactRoles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountPartnersFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountPartnersFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountPartnersTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountPartnersTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("active__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Active__c"));
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
        elemField.setFieldName("annualRevenue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AnnualRevenue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
        elemField.setFieldName("attachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Attachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingCity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingCity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingLatitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingLatitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingLongitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingLongitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingPostalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingPostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingStreet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BillingStreet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("contacts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contacts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contracts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contracts"));
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
        elemField.setFieldName("customerPriority__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CustomerPriority__c"));
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
        elemField.setFieldName("histories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Histories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("industry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Industry"));
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
        elemField.setFieldName("lastActivityDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastActivityDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
        elemField.setFieldName("masterRecord");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MasterRecord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
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
        elemField.setFieldName("numberOfEmployees");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfEmployees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberofLocations__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberofLocations__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
        elemField.setFieldName("opportunityPartnersTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityPartnersTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("ownership");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Ownership"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Parent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account"));
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
        elemField.setFieldName("partnersFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PartnersFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partnersTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PartnersTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("rating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Rating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SLAExpirationDate__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SLAExpirationDate__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SLASerialNumber__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SLASerialNumber__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SLA__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SLA__c"));
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
        elemField.setFieldName("shippingCity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingCity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingLatitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingLatitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingLongitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingLongitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingPostalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingPostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingStreet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ShippingStreet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Sic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("site");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Site"));
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
        elemField.setFieldName("tickerSymbol");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TickerSymbol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upsellOpportunity__c");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UpsellOpportunity__c"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("website");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Website"));
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
