/**
 * Campaign.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Campaign  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories;

    private java.lang.Double actualCost;

    private java.lang.Double amountAllOpportunities;

    private java.lang.Double amountWonOpportunities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments;

    private java.lang.Double budgetedCost;

    private java.lang.String campaignMemberRecordTypeId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult campaignMembers;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult childCampaigns;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private java.lang.String description;

    private java.util.Date endDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events;

    private java.lang.Double expectedResponse;

    private java.lang.Double expectedRevenue;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds;

    private java.lang.Double hierarchyActualCost;

    private java.lang.Double hierarchyAmountAllOpportunities;

    private java.lang.Double hierarchyAmountWonOpportunities;

    private java.lang.Double hierarchyBudgetedCost;

    private java.lang.Double hierarchyExpectedRevenue;

    private java.lang.Integer hierarchyNumberOfContacts;

    private java.lang.Integer hierarchyNumberOfConvertedLeads;

    private java.lang.Integer hierarchyNumberOfLeads;

    private java.lang.Integer hierarchyNumberOfOpportunities;

    private java.lang.Integer hierarchyNumberOfResponses;

    private java.lang.Integer hierarchyNumberOfWonOpportunities;

    private java.lang.Double hierarchyNumberSent;

    private java.lang.Boolean isActive;

    private java.lang.Boolean isDeleted;

    private java.util.Date lastActivityDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult leads;

    private java.lang.String name;

    private java.lang.Integer numberOfContacts;

    private java.lang.Integer numberOfConvertedLeads;

    private java.lang.Integer numberOfLeads;

    private java.lang.Integer numberOfOpportunities;

    private java.lang.Integer numberOfResponses;

    private java.lang.Integer numberOfWonOpportunities;

    private java.lang.Double numberSent;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner;

    private java.lang.String ownerId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Campaign parent;

    private java.lang.String parentId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares;

    private java.util.Date startDate;

    private java.lang.String status;

    private java.util.Calendar systemModstamp;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks;

    private java.lang.String type;

    public Campaign() {
    }

    public Campaign(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories,
           java.lang.Double actualCost,
           java.lang.Double amountAllOpportunities,
           java.lang.Double amountWonOpportunities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments,
           java.lang.Double budgetedCost,
           java.lang.String campaignMemberRecordTypeId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult campaignMembers,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult childCampaigns,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           java.lang.String description,
           java.util.Date endDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events,
           java.lang.Double expectedResponse,
           java.lang.Double expectedRevenue,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds,
           java.lang.Double hierarchyActualCost,
           java.lang.Double hierarchyAmountAllOpportunities,
           java.lang.Double hierarchyAmountWonOpportunities,
           java.lang.Double hierarchyBudgetedCost,
           java.lang.Double hierarchyExpectedRevenue,
           java.lang.Integer hierarchyNumberOfContacts,
           java.lang.Integer hierarchyNumberOfConvertedLeads,
           java.lang.Integer hierarchyNumberOfLeads,
           java.lang.Integer hierarchyNumberOfOpportunities,
           java.lang.Integer hierarchyNumberOfResponses,
           java.lang.Integer hierarchyNumberOfWonOpportunities,
           java.lang.Double hierarchyNumberSent,
           java.lang.Boolean isActive,
           java.lang.Boolean isDeleted,
           java.util.Date lastActivityDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult leads,
           java.lang.String name,
           java.lang.Integer numberOfContacts,
           java.lang.Integer numberOfConvertedLeads,
           java.lang.Integer numberOfLeads,
           java.lang.Integer numberOfOpportunities,
           java.lang.Integer numberOfResponses,
           java.lang.Integer numberOfWonOpportunities,
           java.lang.Double numberSent,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner,
           java.lang.String ownerId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Campaign parent,
           java.lang.String parentId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares,
           java.util.Date startDate,
           java.lang.String status,
           java.util.Calendar systemModstamp,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks,
           java.lang.String type) {
        super(
            fieldsToNull,
            id);
        this.activityHistories = activityHistories;
        this.actualCost = actualCost;
        this.amountAllOpportunities = amountAllOpportunities;
        this.amountWonOpportunities = amountWonOpportunities;
        this.attachments = attachments;
        this.budgetedCost = budgetedCost;
        this.campaignMemberRecordTypeId = campaignMemberRecordTypeId;
        this.campaignMembers = campaignMembers;
        this.childCampaigns = childCampaigns;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.description = description;
        this.endDate = endDate;
        this.events = events;
        this.expectedResponse = expectedResponse;
        this.expectedRevenue = expectedRevenue;
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
        this.feeds = feeds;
        this.hierarchyActualCost = hierarchyActualCost;
        this.hierarchyAmountAllOpportunities = hierarchyAmountAllOpportunities;
        this.hierarchyAmountWonOpportunities = hierarchyAmountWonOpportunities;
        this.hierarchyBudgetedCost = hierarchyBudgetedCost;
        this.hierarchyExpectedRevenue = hierarchyExpectedRevenue;
        this.hierarchyNumberOfContacts = hierarchyNumberOfContacts;
        this.hierarchyNumberOfConvertedLeads = hierarchyNumberOfConvertedLeads;
        this.hierarchyNumberOfLeads = hierarchyNumberOfLeads;
        this.hierarchyNumberOfOpportunities = hierarchyNumberOfOpportunities;
        this.hierarchyNumberOfResponses = hierarchyNumberOfResponses;
        this.hierarchyNumberOfWonOpportunities = hierarchyNumberOfWonOpportunities;
        this.hierarchyNumberSent = hierarchyNumberSent;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.lastActivityDate = lastActivityDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.leads = leads;
        this.name = name;
        this.numberOfContacts = numberOfContacts;
        this.numberOfConvertedLeads = numberOfConvertedLeads;
        this.numberOfLeads = numberOfLeads;
        this.numberOfOpportunities = numberOfOpportunities;
        this.numberOfResponses = numberOfResponses;
        this.numberOfWonOpportunities = numberOfWonOpportunities;
        this.numberSent = numberSent;
        this.openActivities = openActivities;
        this.opportunities = opportunities;
        this.owner = owner;
        this.ownerId = ownerId;
        this.parent = parent;
        this.parentId = parentId;
        this.processInstances = processInstances;
        this.processSteps = processSteps;
        this.shares = shares;
        this.startDate = startDate;
        this.status = status;
        this.systemModstamp = systemModstamp;
        this.tasks = tasks;
        this.type = type;
    }


    /**
     * Gets the activityHistories value for this Campaign.
     * 
     * @return activityHistories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getActivityHistories() {
        return activityHistories;
    }


    /**
     * Sets the activityHistories value for this Campaign.
     * 
     * @param activityHistories
     */
    public void setActivityHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult activityHistories) {
        this.activityHistories = activityHistories;
    }


    /**
     * Gets the actualCost value for this Campaign.
     * 
     * @return actualCost
     */
    public java.lang.Double getActualCost() {
        return actualCost;
    }


    /**
     * Sets the actualCost value for this Campaign.
     * 
     * @param actualCost
     */
    public void setActualCost(java.lang.Double actualCost) {
        this.actualCost = actualCost;
    }


    /**
     * Gets the amountAllOpportunities value for this Campaign.
     * 
     * @return amountAllOpportunities
     */
    public java.lang.Double getAmountAllOpportunities() {
        return amountAllOpportunities;
    }


    /**
     * Sets the amountAllOpportunities value for this Campaign.
     * 
     * @param amountAllOpportunities
     */
    public void setAmountAllOpportunities(java.lang.Double amountAllOpportunities) {
        this.amountAllOpportunities = amountAllOpportunities;
    }


    /**
     * Gets the amountWonOpportunities value for this Campaign.
     * 
     * @return amountWonOpportunities
     */
    public java.lang.Double getAmountWonOpportunities() {
        return amountWonOpportunities;
    }


    /**
     * Sets the amountWonOpportunities value for this Campaign.
     * 
     * @param amountWonOpportunities
     */
    public void setAmountWonOpportunities(java.lang.Double amountWonOpportunities) {
        this.amountWonOpportunities = amountWonOpportunities;
    }


    /**
     * Gets the attachments value for this Campaign.
     * 
     * @return attachments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Campaign.
     * 
     * @param attachments
     */
    public void setAttachments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the budgetedCost value for this Campaign.
     * 
     * @return budgetedCost
     */
    public java.lang.Double getBudgetedCost() {
        return budgetedCost;
    }


    /**
     * Sets the budgetedCost value for this Campaign.
     * 
     * @param budgetedCost
     */
    public void setBudgetedCost(java.lang.Double budgetedCost) {
        this.budgetedCost = budgetedCost;
    }


    /**
     * Gets the campaignMemberRecordTypeId value for this Campaign.
     * 
     * @return campaignMemberRecordTypeId
     */
    public java.lang.String getCampaignMemberRecordTypeId() {
        return campaignMemberRecordTypeId;
    }


    /**
     * Sets the campaignMemberRecordTypeId value for this Campaign.
     * 
     * @param campaignMemberRecordTypeId
     */
    public void setCampaignMemberRecordTypeId(java.lang.String campaignMemberRecordTypeId) {
        this.campaignMemberRecordTypeId = campaignMemberRecordTypeId;
    }


    /**
     * Gets the campaignMembers value for this Campaign.
     * 
     * @return campaignMembers
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getCampaignMembers() {
        return campaignMembers;
    }


    /**
     * Sets the campaignMembers value for this Campaign.
     * 
     * @param campaignMembers
     */
    public void setCampaignMembers(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult campaignMembers) {
        this.campaignMembers = campaignMembers;
    }


    /**
     * Gets the childCampaigns value for this Campaign.
     * 
     * @return childCampaigns
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getChildCampaigns() {
        return childCampaigns;
    }


    /**
     * Sets the childCampaigns value for this Campaign.
     * 
     * @param childCampaigns
     */
    public void setChildCampaigns(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult childCampaigns) {
        this.childCampaigns = childCampaigns;
    }


    /**
     * Gets the createdBy value for this Campaign.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Campaign.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Campaign.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Campaign.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Campaign.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Campaign.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the description value for this Campaign.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Campaign.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the endDate value for this Campaign.
     * 
     * @return endDate
     */
    public java.util.Date getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this Campaign.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the events value for this Campaign.
     * 
     * @return events
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getEvents() {
        return events;
    }


    /**
     * Sets the events value for this Campaign.
     * 
     * @param events
     */
    public void setEvents(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult events) {
        this.events = events;
    }


    /**
     * Gets the expectedResponse value for this Campaign.
     * 
     * @return expectedResponse
     */
    public java.lang.Double getExpectedResponse() {
        return expectedResponse;
    }


    /**
     * Sets the expectedResponse value for this Campaign.
     * 
     * @param expectedResponse
     */
    public void setExpectedResponse(java.lang.Double expectedResponse) {
        this.expectedResponse = expectedResponse;
    }


    /**
     * Gets the expectedRevenue value for this Campaign.
     * 
     * @return expectedRevenue
     */
    public java.lang.Double getExpectedRevenue() {
        return expectedRevenue;
    }


    /**
     * Sets the expectedRevenue value for this Campaign.
     * 
     * @param expectedRevenue
     */
    public void setExpectedRevenue(java.lang.Double expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }


    /**
     * Gets the feedSubscriptionsForEntity value for this Campaign.
     * 
     * @return feedSubscriptionsForEntity
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeedSubscriptionsForEntity() {
        return feedSubscriptionsForEntity;
    }


    /**
     * Sets the feedSubscriptionsForEntity value for this Campaign.
     * 
     * @param feedSubscriptionsForEntity
     */
    public void setFeedSubscriptionsForEntity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feedSubscriptionsForEntity) {
        this.feedSubscriptionsForEntity = feedSubscriptionsForEntity;
    }


    /**
     * Gets the feeds value for this Campaign.
     * 
     * @return feeds
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getFeeds() {
        return feeds;
    }


    /**
     * Sets the feeds value for this Campaign.
     * 
     * @param feeds
     */
    public void setFeeds(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult feeds) {
        this.feeds = feeds;
    }


    /**
     * Gets the hierarchyActualCost value for this Campaign.
     * 
     * @return hierarchyActualCost
     */
    public java.lang.Double getHierarchyActualCost() {
        return hierarchyActualCost;
    }


    /**
     * Sets the hierarchyActualCost value for this Campaign.
     * 
     * @param hierarchyActualCost
     */
    public void setHierarchyActualCost(java.lang.Double hierarchyActualCost) {
        this.hierarchyActualCost = hierarchyActualCost;
    }


    /**
     * Gets the hierarchyAmountAllOpportunities value for this Campaign.
     * 
     * @return hierarchyAmountAllOpportunities
     */
    public java.lang.Double getHierarchyAmountAllOpportunities() {
        return hierarchyAmountAllOpportunities;
    }


    /**
     * Sets the hierarchyAmountAllOpportunities value for this Campaign.
     * 
     * @param hierarchyAmountAllOpportunities
     */
    public void setHierarchyAmountAllOpportunities(java.lang.Double hierarchyAmountAllOpportunities) {
        this.hierarchyAmountAllOpportunities = hierarchyAmountAllOpportunities;
    }


    /**
     * Gets the hierarchyAmountWonOpportunities value for this Campaign.
     * 
     * @return hierarchyAmountWonOpportunities
     */
    public java.lang.Double getHierarchyAmountWonOpportunities() {
        return hierarchyAmountWonOpportunities;
    }


    /**
     * Sets the hierarchyAmountWonOpportunities value for this Campaign.
     * 
     * @param hierarchyAmountWonOpportunities
     */
    public void setHierarchyAmountWonOpportunities(java.lang.Double hierarchyAmountWonOpportunities) {
        this.hierarchyAmountWonOpportunities = hierarchyAmountWonOpportunities;
    }


    /**
     * Gets the hierarchyBudgetedCost value for this Campaign.
     * 
     * @return hierarchyBudgetedCost
     */
    public java.lang.Double getHierarchyBudgetedCost() {
        return hierarchyBudgetedCost;
    }


    /**
     * Sets the hierarchyBudgetedCost value for this Campaign.
     * 
     * @param hierarchyBudgetedCost
     */
    public void setHierarchyBudgetedCost(java.lang.Double hierarchyBudgetedCost) {
        this.hierarchyBudgetedCost = hierarchyBudgetedCost;
    }


    /**
     * Gets the hierarchyExpectedRevenue value for this Campaign.
     * 
     * @return hierarchyExpectedRevenue
     */
    public java.lang.Double getHierarchyExpectedRevenue() {
        return hierarchyExpectedRevenue;
    }


    /**
     * Sets the hierarchyExpectedRevenue value for this Campaign.
     * 
     * @param hierarchyExpectedRevenue
     */
    public void setHierarchyExpectedRevenue(java.lang.Double hierarchyExpectedRevenue) {
        this.hierarchyExpectedRevenue = hierarchyExpectedRevenue;
    }


    /**
     * Gets the hierarchyNumberOfContacts value for this Campaign.
     * 
     * @return hierarchyNumberOfContacts
     */
    public java.lang.Integer getHierarchyNumberOfContacts() {
        return hierarchyNumberOfContacts;
    }


    /**
     * Sets the hierarchyNumberOfContacts value for this Campaign.
     * 
     * @param hierarchyNumberOfContacts
     */
    public void setHierarchyNumberOfContacts(java.lang.Integer hierarchyNumberOfContacts) {
        this.hierarchyNumberOfContacts = hierarchyNumberOfContacts;
    }


    /**
     * Gets the hierarchyNumberOfConvertedLeads value for this Campaign.
     * 
     * @return hierarchyNumberOfConvertedLeads
     */
    public java.lang.Integer getHierarchyNumberOfConvertedLeads() {
        return hierarchyNumberOfConvertedLeads;
    }


    /**
     * Sets the hierarchyNumberOfConvertedLeads value for this Campaign.
     * 
     * @param hierarchyNumberOfConvertedLeads
     */
    public void setHierarchyNumberOfConvertedLeads(java.lang.Integer hierarchyNumberOfConvertedLeads) {
        this.hierarchyNumberOfConvertedLeads = hierarchyNumberOfConvertedLeads;
    }


    /**
     * Gets the hierarchyNumberOfLeads value for this Campaign.
     * 
     * @return hierarchyNumberOfLeads
     */
    public java.lang.Integer getHierarchyNumberOfLeads() {
        return hierarchyNumberOfLeads;
    }


    /**
     * Sets the hierarchyNumberOfLeads value for this Campaign.
     * 
     * @param hierarchyNumberOfLeads
     */
    public void setHierarchyNumberOfLeads(java.lang.Integer hierarchyNumberOfLeads) {
        this.hierarchyNumberOfLeads = hierarchyNumberOfLeads;
    }


    /**
     * Gets the hierarchyNumberOfOpportunities value for this Campaign.
     * 
     * @return hierarchyNumberOfOpportunities
     */
    public java.lang.Integer getHierarchyNumberOfOpportunities() {
        return hierarchyNumberOfOpportunities;
    }


    /**
     * Sets the hierarchyNumberOfOpportunities value for this Campaign.
     * 
     * @param hierarchyNumberOfOpportunities
     */
    public void setHierarchyNumberOfOpportunities(java.lang.Integer hierarchyNumberOfOpportunities) {
        this.hierarchyNumberOfOpportunities = hierarchyNumberOfOpportunities;
    }


    /**
     * Gets the hierarchyNumberOfResponses value for this Campaign.
     * 
     * @return hierarchyNumberOfResponses
     */
    public java.lang.Integer getHierarchyNumberOfResponses() {
        return hierarchyNumberOfResponses;
    }


    /**
     * Sets the hierarchyNumberOfResponses value for this Campaign.
     * 
     * @param hierarchyNumberOfResponses
     */
    public void setHierarchyNumberOfResponses(java.lang.Integer hierarchyNumberOfResponses) {
        this.hierarchyNumberOfResponses = hierarchyNumberOfResponses;
    }


    /**
     * Gets the hierarchyNumberOfWonOpportunities value for this Campaign.
     * 
     * @return hierarchyNumberOfWonOpportunities
     */
    public java.lang.Integer getHierarchyNumberOfWonOpportunities() {
        return hierarchyNumberOfWonOpportunities;
    }


    /**
     * Sets the hierarchyNumberOfWonOpportunities value for this Campaign.
     * 
     * @param hierarchyNumberOfWonOpportunities
     */
    public void setHierarchyNumberOfWonOpportunities(java.lang.Integer hierarchyNumberOfWonOpportunities) {
        this.hierarchyNumberOfWonOpportunities = hierarchyNumberOfWonOpportunities;
    }


    /**
     * Gets the hierarchyNumberSent value for this Campaign.
     * 
     * @return hierarchyNumberSent
     */
    public java.lang.Double getHierarchyNumberSent() {
        return hierarchyNumberSent;
    }


    /**
     * Sets the hierarchyNumberSent value for this Campaign.
     * 
     * @param hierarchyNumberSent
     */
    public void setHierarchyNumberSent(java.lang.Double hierarchyNumberSent) {
        this.hierarchyNumberSent = hierarchyNumberSent;
    }


    /**
     * Gets the isActive value for this Campaign.
     * 
     * @return isActive
     */
    public java.lang.Boolean getIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this Campaign.
     * 
     * @param isActive
     */
    public void setIsActive(java.lang.Boolean isActive) {
        this.isActive = isActive;
    }


    /**
     * Gets the isDeleted value for this Campaign.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Campaign.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the lastActivityDate value for this Campaign.
     * 
     * @return lastActivityDate
     */
    public java.util.Date getLastActivityDate() {
        return lastActivityDate;
    }


    /**
     * Sets the lastActivityDate value for this Campaign.
     * 
     * @param lastActivityDate
     */
    public void setLastActivityDate(java.util.Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }


    /**
     * Gets the lastModifiedBy value for this Campaign.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Campaign.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Campaign.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Campaign.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Campaign.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Campaign.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the leads value for this Campaign.
     * 
     * @return leads
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getLeads() {
        return leads;
    }


    /**
     * Sets the leads value for this Campaign.
     * 
     * @param leads
     */
    public void setLeads(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult leads) {
        this.leads = leads;
    }


    /**
     * Gets the name value for this Campaign.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Campaign.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the numberOfContacts value for this Campaign.
     * 
     * @return numberOfContacts
     */
    public java.lang.Integer getNumberOfContacts() {
        return numberOfContacts;
    }


    /**
     * Sets the numberOfContacts value for this Campaign.
     * 
     * @param numberOfContacts
     */
    public void setNumberOfContacts(java.lang.Integer numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }


    /**
     * Gets the numberOfConvertedLeads value for this Campaign.
     * 
     * @return numberOfConvertedLeads
     */
    public java.lang.Integer getNumberOfConvertedLeads() {
        return numberOfConvertedLeads;
    }


    /**
     * Sets the numberOfConvertedLeads value for this Campaign.
     * 
     * @param numberOfConvertedLeads
     */
    public void setNumberOfConvertedLeads(java.lang.Integer numberOfConvertedLeads) {
        this.numberOfConvertedLeads = numberOfConvertedLeads;
    }


    /**
     * Gets the numberOfLeads value for this Campaign.
     * 
     * @return numberOfLeads
     */
    public java.lang.Integer getNumberOfLeads() {
        return numberOfLeads;
    }


    /**
     * Sets the numberOfLeads value for this Campaign.
     * 
     * @param numberOfLeads
     */
    public void setNumberOfLeads(java.lang.Integer numberOfLeads) {
        this.numberOfLeads = numberOfLeads;
    }


    /**
     * Gets the numberOfOpportunities value for this Campaign.
     * 
     * @return numberOfOpportunities
     */
    public java.lang.Integer getNumberOfOpportunities() {
        return numberOfOpportunities;
    }


    /**
     * Sets the numberOfOpportunities value for this Campaign.
     * 
     * @param numberOfOpportunities
     */
    public void setNumberOfOpportunities(java.lang.Integer numberOfOpportunities) {
        this.numberOfOpportunities = numberOfOpportunities;
    }


    /**
     * Gets the numberOfResponses value for this Campaign.
     * 
     * @return numberOfResponses
     */
    public java.lang.Integer getNumberOfResponses() {
        return numberOfResponses;
    }


    /**
     * Sets the numberOfResponses value for this Campaign.
     * 
     * @param numberOfResponses
     */
    public void setNumberOfResponses(java.lang.Integer numberOfResponses) {
        this.numberOfResponses = numberOfResponses;
    }


    /**
     * Gets the numberOfWonOpportunities value for this Campaign.
     * 
     * @return numberOfWonOpportunities
     */
    public java.lang.Integer getNumberOfWonOpportunities() {
        return numberOfWonOpportunities;
    }


    /**
     * Sets the numberOfWonOpportunities value for this Campaign.
     * 
     * @param numberOfWonOpportunities
     */
    public void setNumberOfWonOpportunities(java.lang.Integer numberOfWonOpportunities) {
        this.numberOfWonOpportunities = numberOfWonOpportunities;
    }


    /**
     * Gets the numberSent value for this Campaign.
     * 
     * @return numberSent
     */
    public java.lang.Double getNumberSent() {
        return numberSent;
    }


    /**
     * Sets the numberSent value for this Campaign.
     * 
     * @param numberSent
     */
    public void setNumberSent(java.lang.Double numberSent) {
        this.numberSent = numberSent;
    }


    /**
     * Gets the openActivities value for this Campaign.
     * 
     * @return openActivities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpenActivities() {
        return openActivities;
    }


    /**
     * Sets the openActivities value for this Campaign.
     * 
     * @param openActivities
     */
    public void setOpenActivities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult openActivities) {
        this.openActivities = openActivities;
    }


    /**
     * Gets the opportunities value for this Campaign.
     * 
     * @return opportunities
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getOpportunities() {
        return opportunities;
    }


    /**
     * Sets the opportunities value for this Campaign.
     * 
     * @param opportunities
     */
    public void setOpportunities(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult opportunities) {
        this.opportunities = opportunities;
    }


    /**
     * Gets the owner value for this Campaign.
     * 
     * @return owner
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this Campaign.
     * 
     * @param owner
     */
    public void setOwner(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner) {
        this.owner = owner;
    }


    /**
     * Gets the ownerId value for this Campaign.
     * 
     * @return ownerId
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }


    /**
     * Sets the ownerId value for this Campaign.
     * 
     * @param ownerId
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }


    /**
     * Gets the parent value for this Campaign.
     * 
     * @return parent
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Campaign getParent() {
        return parent;
    }


    /**
     * Sets the parent value for this Campaign.
     * 
     * @param parent
     */
    public void setParent(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Campaign parent) {
        this.parent = parent;
    }


    /**
     * Gets the parentId value for this Campaign.
     * 
     * @return parentId
     */
    public java.lang.String getParentId() {
        return parentId;
    }


    /**
     * Sets the parentId value for this Campaign.
     * 
     * @param parentId
     */
    public void setParentId(java.lang.String parentId) {
        this.parentId = parentId;
    }


    /**
     * Gets the processInstances value for this Campaign.
     * 
     * @return processInstances
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessInstances() {
        return processInstances;
    }


    /**
     * Sets the processInstances value for this Campaign.
     * 
     * @param processInstances
     */
    public void setProcessInstances(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processInstances) {
        this.processInstances = processInstances;
    }


    /**
     * Gets the processSteps value for this Campaign.
     * 
     * @return processSteps
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getProcessSteps() {
        return processSteps;
    }


    /**
     * Sets the processSteps value for this Campaign.
     * 
     * @param processSteps
     */
    public void setProcessSteps(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult processSteps) {
        this.processSteps = processSteps;
    }


    /**
     * Gets the shares value for this Campaign.
     * 
     * @return shares
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getShares() {
        return shares;
    }


    /**
     * Sets the shares value for this Campaign.
     * 
     * @param shares
     */
    public void setShares(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult shares) {
        this.shares = shares;
    }


    /**
     * Gets the startDate value for this Campaign.
     * 
     * @return startDate
     */
    public java.util.Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this Campaign.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the status value for this Campaign.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Campaign.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the systemModstamp value for this Campaign.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Campaign.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the tasks value for this Campaign.
     * 
     * @return tasks
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getTasks() {
        return tasks;
    }


    /**
     * Sets the tasks value for this Campaign.
     * 
     * @param tasks
     */
    public void setTasks(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult tasks) {
        this.tasks = tasks;
    }


    /**
     * Gets the type value for this Campaign.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Campaign.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Campaign)) return false;
        Campaign other = (Campaign) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.activityHistories==null && other.getActivityHistories()==null) || 
             (this.activityHistories!=null &&
              this.activityHistories.equals(other.getActivityHistories()))) &&
            ((this.actualCost==null && other.getActualCost()==null) || 
             (this.actualCost!=null &&
              this.actualCost.equals(other.getActualCost()))) &&
            ((this.amountAllOpportunities==null && other.getAmountAllOpportunities()==null) || 
             (this.amountAllOpportunities!=null &&
              this.amountAllOpportunities.equals(other.getAmountAllOpportunities()))) &&
            ((this.amountWonOpportunities==null && other.getAmountWonOpportunities()==null) || 
             (this.amountWonOpportunities!=null &&
              this.amountWonOpportunities.equals(other.getAmountWonOpportunities()))) &&
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              this.attachments.equals(other.getAttachments()))) &&
            ((this.budgetedCost==null && other.getBudgetedCost()==null) || 
             (this.budgetedCost!=null &&
              this.budgetedCost.equals(other.getBudgetedCost()))) &&
            ((this.campaignMemberRecordTypeId==null && other.getCampaignMemberRecordTypeId()==null) || 
             (this.campaignMemberRecordTypeId!=null &&
              this.campaignMemberRecordTypeId.equals(other.getCampaignMemberRecordTypeId()))) &&
            ((this.campaignMembers==null && other.getCampaignMembers()==null) || 
             (this.campaignMembers!=null &&
              this.campaignMembers.equals(other.getCampaignMembers()))) &&
            ((this.childCampaigns==null && other.getChildCampaigns()==null) || 
             (this.childCampaigns!=null &&
              this.childCampaigns.equals(other.getChildCampaigns()))) &&
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
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.events==null && other.getEvents()==null) || 
             (this.events!=null &&
              this.events.equals(other.getEvents()))) &&
            ((this.expectedResponse==null && other.getExpectedResponse()==null) || 
             (this.expectedResponse!=null &&
              this.expectedResponse.equals(other.getExpectedResponse()))) &&
            ((this.expectedRevenue==null && other.getExpectedRevenue()==null) || 
             (this.expectedRevenue!=null &&
              this.expectedRevenue.equals(other.getExpectedRevenue()))) &&
            ((this.feedSubscriptionsForEntity==null && other.getFeedSubscriptionsForEntity()==null) || 
             (this.feedSubscriptionsForEntity!=null &&
              this.feedSubscriptionsForEntity.equals(other.getFeedSubscriptionsForEntity()))) &&
            ((this.feeds==null && other.getFeeds()==null) || 
             (this.feeds!=null &&
              this.feeds.equals(other.getFeeds()))) &&
            ((this.hierarchyActualCost==null && other.getHierarchyActualCost()==null) || 
             (this.hierarchyActualCost!=null &&
              this.hierarchyActualCost.equals(other.getHierarchyActualCost()))) &&
            ((this.hierarchyAmountAllOpportunities==null && other.getHierarchyAmountAllOpportunities()==null) || 
             (this.hierarchyAmountAllOpportunities!=null &&
              this.hierarchyAmountAllOpportunities.equals(other.getHierarchyAmountAllOpportunities()))) &&
            ((this.hierarchyAmountWonOpportunities==null && other.getHierarchyAmountWonOpportunities()==null) || 
             (this.hierarchyAmountWonOpportunities!=null &&
              this.hierarchyAmountWonOpportunities.equals(other.getHierarchyAmountWonOpportunities()))) &&
            ((this.hierarchyBudgetedCost==null && other.getHierarchyBudgetedCost()==null) || 
             (this.hierarchyBudgetedCost!=null &&
              this.hierarchyBudgetedCost.equals(other.getHierarchyBudgetedCost()))) &&
            ((this.hierarchyExpectedRevenue==null && other.getHierarchyExpectedRevenue()==null) || 
             (this.hierarchyExpectedRevenue!=null &&
              this.hierarchyExpectedRevenue.equals(other.getHierarchyExpectedRevenue()))) &&
            ((this.hierarchyNumberOfContacts==null && other.getHierarchyNumberOfContacts()==null) || 
             (this.hierarchyNumberOfContacts!=null &&
              this.hierarchyNumberOfContacts.equals(other.getHierarchyNumberOfContacts()))) &&
            ((this.hierarchyNumberOfConvertedLeads==null && other.getHierarchyNumberOfConvertedLeads()==null) || 
             (this.hierarchyNumberOfConvertedLeads!=null &&
              this.hierarchyNumberOfConvertedLeads.equals(other.getHierarchyNumberOfConvertedLeads()))) &&
            ((this.hierarchyNumberOfLeads==null && other.getHierarchyNumberOfLeads()==null) || 
             (this.hierarchyNumberOfLeads!=null &&
              this.hierarchyNumberOfLeads.equals(other.getHierarchyNumberOfLeads()))) &&
            ((this.hierarchyNumberOfOpportunities==null && other.getHierarchyNumberOfOpportunities()==null) || 
             (this.hierarchyNumberOfOpportunities!=null &&
              this.hierarchyNumberOfOpportunities.equals(other.getHierarchyNumberOfOpportunities()))) &&
            ((this.hierarchyNumberOfResponses==null && other.getHierarchyNumberOfResponses()==null) || 
             (this.hierarchyNumberOfResponses!=null &&
              this.hierarchyNumberOfResponses.equals(other.getHierarchyNumberOfResponses()))) &&
            ((this.hierarchyNumberOfWonOpportunities==null && other.getHierarchyNumberOfWonOpportunities()==null) || 
             (this.hierarchyNumberOfWonOpportunities!=null &&
              this.hierarchyNumberOfWonOpportunities.equals(other.getHierarchyNumberOfWonOpportunities()))) &&
            ((this.hierarchyNumberSent==null && other.getHierarchyNumberSent()==null) || 
             (this.hierarchyNumberSent!=null &&
              this.hierarchyNumberSent.equals(other.getHierarchyNumberSent()))) &&
            ((this.isActive==null && other.getIsActive()==null) || 
             (this.isActive!=null &&
              this.isActive.equals(other.getIsActive()))) &&
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
            ((this.leads==null && other.getLeads()==null) || 
             (this.leads!=null &&
              this.leads.equals(other.getLeads()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.numberOfContacts==null && other.getNumberOfContacts()==null) || 
             (this.numberOfContacts!=null &&
              this.numberOfContacts.equals(other.getNumberOfContacts()))) &&
            ((this.numberOfConvertedLeads==null && other.getNumberOfConvertedLeads()==null) || 
             (this.numberOfConvertedLeads!=null &&
              this.numberOfConvertedLeads.equals(other.getNumberOfConvertedLeads()))) &&
            ((this.numberOfLeads==null && other.getNumberOfLeads()==null) || 
             (this.numberOfLeads!=null &&
              this.numberOfLeads.equals(other.getNumberOfLeads()))) &&
            ((this.numberOfOpportunities==null && other.getNumberOfOpportunities()==null) || 
             (this.numberOfOpportunities!=null &&
              this.numberOfOpportunities.equals(other.getNumberOfOpportunities()))) &&
            ((this.numberOfResponses==null && other.getNumberOfResponses()==null) || 
             (this.numberOfResponses!=null &&
              this.numberOfResponses.equals(other.getNumberOfResponses()))) &&
            ((this.numberOfWonOpportunities==null && other.getNumberOfWonOpportunities()==null) || 
             (this.numberOfWonOpportunities!=null &&
              this.numberOfWonOpportunities.equals(other.getNumberOfWonOpportunities()))) &&
            ((this.numberSent==null && other.getNumberSent()==null) || 
             (this.numberSent!=null &&
              this.numberSent.equals(other.getNumberSent()))) &&
            ((this.openActivities==null && other.getOpenActivities()==null) || 
             (this.openActivities!=null &&
              this.openActivities.equals(other.getOpenActivities()))) &&
            ((this.opportunities==null && other.getOpportunities()==null) || 
             (this.opportunities!=null &&
              this.opportunities.equals(other.getOpportunities()))) &&
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
            ((this.processInstances==null && other.getProcessInstances()==null) || 
             (this.processInstances!=null &&
              this.processInstances.equals(other.getProcessInstances()))) &&
            ((this.processSteps==null && other.getProcessSteps()==null) || 
             (this.processSteps!=null &&
              this.processSteps.equals(other.getProcessSteps()))) &&
            ((this.shares==null && other.getShares()==null) || 
             (this.shares!=null &&
              this.shares.equals(other.getShares()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.tasks==null && other.getTasks()==null) || 
             (this.tasks!=null &&
              this.tasks.equals(other.getTasks()))) &&
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
        if (getActivityHistories() != null) {
            _hashCode += getActivityHistories().hashCode();
        }
        if (getActualCost() != null) {
            _hashCode += getActualCost().hashCode();
        }
        if (getAmountAllOpportunities() != null) {
            _hashCode += getAmountAllOpportunities().hashCode();
        }
        if (getAmountWonOpportunities() != null) {
            _hashCode += getAmountWonOpportunities().hashCode();
        }
        if (getAttachments() != null) {
            _hashCode += getAttachments().hashCode();
        }
        if (getBudgetedCost() != null) {
            _hashCode += getBudgetedCost().hashCode();
        }
        if (getCampaignMemberRecordTypeId() != null) {
            _hashCode += getCampaignMemberRecordTypeId().hashCode();
        }
        if (getCampaignMembers() != null) {
            _hashCode += getCampaignMembers().hashCode();
        }
        if (getChildCampaigns() != null) {
            _hashCode += getChildCampaigns().hashCode();
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
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getEvents() != null) {
            _hashCode += getEvents().hashCode();
        }
        if (getExpectedResponse() != null) {
            _hashCode += getExpectedResponse().hashCode();
        }
        if (getExpectedRevenue() != null) {
            _hashCode += getExpectedRevenue().hashCode();
        }
        if (getFeedSubscriptionsForEntity() != null) {
            _hashCode += getFeedSubscriptionsForEntity().hashCode();
        }
        if (getFeeds() != null) {
            _hashCode += getFeeds().hashCode();
        }
        if (getHierarchyActualCost() != null) {
            _hashCode += getHierarchyActualCost().hashCode();
        }
        if (getHierarchyAmountAllOpportunities() != null) {
            _hashCode += getHierarchyAmountAllOpportunities().hashCode();
        }
        if (getHierarchyAmountWonOpportunities() != null) {
            _hashCode += getHierarchyAmountWonOpportunities().hashCode();
        }
        if (getHierarchyBudgetedCost() != null) {
            _hashCode += getHierarchyBudgetedCost().hashCode();
        }
        if (getHierarchyExpectedRevenue() != null) {
            _hashCode += getHierarchyExpectedRevenue().hashCode();
        }
        if (getHierarchyNumberOfContacts() != null) {
            _hashCode += getHierarchyNumberOfContacts().hashCode();
        }
        if (getHierarchyNumberOfConvertedLeads() != null) {
            _hashCode += getHierarchyNumberOfConvertedLeads().hashCode();
        }
        if (getHierarchyNumberOfLeads() != null) {
            _hashCode += getHierarchyNumberOfLeads().hashCode();
        }
        if (getHierarchyNumberOfOpportunities() != null) {
            _hashCode += getHierarchyNumberOfOpportunities().hashCode();
        }
        if (getHierarchyNumberOfResponses() != null) {
            _hashCode += getHierarchyNumberOfResponses().hashCode();
        }
        if (getHierarchyNumberOfWonOpportunities() != null) {
            _hashCode += getHierarchyNumberOfWonOpportunities().hashCode();
        }
        if (getHierarchyNumberSent() != null) {
            _hashCode += getHierarchyNumberSent().hashCode();
        }
        if (getIsActive() != null) {
            _hashCode += getIsActive().hashCode();
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
        if (getLeads() != null) {
            _hashCode += getLeads().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNumberOfContacts() != null) {
            _hashCode += getNumberOfContacts().hashCode();
        }
        if (getNumberOfConvertedLeads() != null) {
            _hashCode += getNumberOfConvertedLeads().hashCode();
        }
        if (getNumberOfLeads() != null) {
            _hashCode += getNumberOfLeads().hashCode();
        }
        if (getNumberOfOpportunities() != null) {
            _hashCode += getNumberOfOpportunities().hashCode();
        }
        if (getNumberOfResponses() != null) {
            _hashCode += getNumberOfResponses().hashCode();
        }
        if (getNumberOfWonOpportunities() != null) {
            _hashCode += getNumberOfWonOpportunities().hashCode();
        }
        if (getNumberSent() != null) {
            _hashCode += getNumberSent().hashCode();
        }
        if (getOpenActivities() != null) {
            _hashCode += getOpenActivities().hashCode();
        }
        if (getOpportunities() != null) {
            _hashCode += getOpportunities().hashCode();
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
        if (getProcessInstances() != null) {
            _hashCode += getProcessInstances().hashCode();
        }
        if (getProcessSteps() != null) {
            _hashCode += getProcessSteps().hashCode();
        }
        if (getShares() != null) {
            _hashCode += getShares().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getTasks() != null) {
            _hashCode += getTasks().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Campaign.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Campaign"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activityHistories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ActivityHistories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ActualCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountAllOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AmountAllOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountWonOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AmountWonOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
        elemField.setFieldName("budgetedCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BudgetedCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignMemberRecordTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CampaignMemberRecordTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("childCampaigns");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ChildCampaigns"));
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
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
        elemField.setFieldName("expectedResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ExpectedResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expectedRevenue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ExpectedRevenue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
        elemField.setFieldName("hierarchyActualCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyActualCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyAmountAllOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyAmountAllOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyAmountWonOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyAmountWonOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyBudgetedCost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyBudgetedCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyExpectedRevenue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyExpectedRevenue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberOfContacts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberOfContacts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberOfConvertedLeads");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberOfConvertedLeads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberOfLeads");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberOfLeads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberOfOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberOfOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberOfResponses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberOfResponses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberOfWonOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberOfWonOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hierarchyNumberSent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "HierarchyNumberSent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsActive"));
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
        elemField.setFieldName("leads");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Leads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
        elemField.setFieldName("numberOfContacts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfContacts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfConvertedLeads");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfConvertedLeads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfLeads");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfLeads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfResponses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfResponses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfWonOpportunities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberOfWonOpportunities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberSent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumberSent"));
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
        elemField.setFieldName("parent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Parent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Campaign"));
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
        elemField.setFieldName("shares");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Shares"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
