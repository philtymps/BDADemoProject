/**
 * ContentVersion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class ContentVersion  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocument contentDocument;

    private java.lang.String contentDocumentId;

    private java.lang.String contentModifiedById;

    private java.util.Calendar contentModifiedDate;

    private java.lang.Integer contentSize;

    private java.lang.String contentUrl;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private java.lang.String description;

    private java.lang.Integer featuredContentBoost;

    private java.util.Date featuredContentDate;

    private java.lang.String fileType;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject firstPublishLocation;

    private java.lang.String firstPublishLocationId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories;

    private java.lang.Boolean isDeleted;

    private java.lang.Boolean isLatest;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private java.lang.Integer negativeRatingCount;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner;

    private java.lang.String ownerId;

    private java.lang.String pathOnClient;

    private java.lang.Integer positiveRatingCount;

    private java.lang.String publishStatus;

    private java.lang.Integer ratingCount;

    private java.lang.String reasonForChange;

    private java.util.Calendar systemModstamp;

    private java.lang.String tagCsv;

    private java.lang.String title;

    private byte[] versionData;

    private java.lang.String versionNumber;

    public ContentVersion() {
    }

    public ContentVersion(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocument contentDocument,
           java.lang.String contentDocumentId,
           java.lang.String contentModifiedById,
           java.util.Calendar contentModifiedDate,
           java.lang.Integer contentSize,
           java.lang.String contentUrl,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           java.lang.String description,
           java.lang.Integer featuredContentBoost,
           java.util.Date featuredContentDate,
           java.lang.String fileType,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject firstPublishLocation,
           java.lang.String firstPublishLocationId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories,
           java.lang.Boolean isDeleted,
           java.lang.Boolean isLatest,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           java.lang.Integer negativeRatingCount,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner,
           java.lang.String ownerId,
           java.lang.String pathOnClient,
           java.lang.Integer positiveRatingCount,
           java.lang.String publishStatus,
           java.lang.Integer ratingCount,
           java.lang.String reasonForChange,
           java.util.Calendar systemModstamp,
           java.lang.String tagCsv,
           java.lang.String title,
           byte[] versionData,
           java.lang.String versionNumber) {
        super(
            fieldsToNull,
            id);
        this.contentDocument = contentDocument;
        this.contentDocumentId = contentDocumentId;
        this.contentModifiedById = contentModifiedById;
        this.contentModifiedDate = contentModifiedDate;
        this.contentSize = contentSize;
        this.contentUrl = contentUrl;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.description = description;
        this.featuredContentBoost = featuredContentBoost;
        this.featuredContentDate = featuredContentDate;
        this.fileType = fileType;
        this.firstPublishLocation = firstPublishLocation;
        this.firstPublishLocationId = firstPublishLocationId;
        this.histories = histories;
        this.isDeleted = isDeleted;
        this.isLatest = isLatest;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.negativeRatingCount = negativeRatingCount;
        this.owner = owner;
        this.ownerId = ownerId;
        this.pathOnClient = pathOnClient;
        this.positiveRatingCount = positiveRatingCount;
        this.publishStatus = publishStatus;
        this.ratingCount = ratingCount;
        this.reasonForChange = reasonForChange;
        this.systemModstamp = systemModstamp;
        this.tagCsv = tagCsv;
        this.title = title;
        this.versionData = versionData;
        this.versionNumber = versionNumber;
    }


    /**
     * Gets the contentDocument value for this ContentVersion.
     * 
     * @return contentDocument
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocument getContentDocument() {
        return contentDocument;
    }


    /**
     * Sets the contentDocument value for this ContentVersion.
     * 
     * @param contentDocument
     */
    public void setContentDocument(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocument contentDocument) {
        this.contentDocument = contentDocument;
    }


    /**
     * Gets the contentDocumentId value for this ContentVersion.
     * 
     * @return contentDocumentId
     */
    public java.lang.String getContentDocumentId() {
        return contentDocumentId;
    }


    /**
     * Sets the contentDocumentId value for this ContentVersion.
     * 
     * @param contentDocumentId
     */
    public void setContentDocumentId(java.lang.String contentDocumentId) {
        this.contentDocumentId = contentDocumentId;
    }


    /**
     * Gets the contentModifiedById value for this ContentVersion.
     * 
     * @return contentModifiedById
     */
    public java.lang.String getContentModifiedById() {
        return contentModifiedById;
    }


    /**
     * Sets the contentModifiedById value for this ContentVersion.
     * 
     * @param contentModifiedById
     */
    public void setContentModifiedById(java.lang.String contentModifiedById) {
        this.contentModifiedById = contentModifiedById;
    }


    /**
     * Gets the contentModifiedDate value for this ContentVersion.
     * 
     * @return contentModifiedDate
     */
    public java.util.Calendar getContentModifiedDate() {
        return contentModifiedDate;
    }


    /**
     * Sets the contentModifiedDate value for this ContentVersion.
     * 
     * @param contentModifiedDate
     */
    public void setContentModifiedDate(java.util.Calendar contentModifiedDate) {
        this.contentModifiedDate = contentModifiedDate;
    }


    /**
     * Gets the contentSize value for this ContentVersion.
     * 
     * @return contentSize
     */
    public java.lang.Integer getContentSize() {
        return contentSize;
    }


    /**
     * Sets the contentSize value for this ContentVersion.
     * 
     * @param contentSize
     */
    public void setContentSize(java.lang.Integer contentSize) {
        this.contentSize = contentSize;
    }


    /**
     * Gets the contentUrl value for this ContentVersion.
     * 
     * @return contentUrl
     */
    public java.lang.String getContentUrl() {
        return contentUrl;
    }


    /**
     * Sets the contentUrl value for this ContentVersion.
     * 
     * @param contentUrl
     */
    public void setContentUrl(java.lang.String contentUrl) {
        this.contentUrl = contentUrl;
    }


    /**
     * Gets the createdBy value for this ContentVersion.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this ContentVersion.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this ContentVersion.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this ContentVersion.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this ContentVersion.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this ContentVersion.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the description value for this ContentVersion.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this ContentVersion.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the featuredContentBoost value for this ContentVersion.
     * 
     * @return featuredContentBoost
     */
    public java.lang.Integer getFeaturedContentBoost() {
        return featuredContentBoost;
    }


    /**
     * Sets the featuredContentBoost value for this ContentVersion.
     * 
     * @param featuredContentBoost
     */
    public void setFeaturedContentBoost(java.lang.Integer featuredContentBoost) {
        this.featuredContentBoost = featuredContentBoost;
    }


    /**
     * Gets the featuredContentDate value for this ContentVersion.
     * 
     * @return featuredContentDate
     */
    public java.util.Date getFeaturedContentDate() {
        return featuredContentDate;
    }


    /**
     * Sets the featuredContentDate value for this ContentVersion.
     * 
     * @param featuredContentDate
     */
    public void setFeaturedContentDate(java.util.Date featuredContentDate) {
        this.featuredContentDate = featuredContentDate;
    }


    /**
     * Gets the fileType value for this ContentVersion.
     * 
     * @return fileType
     */
    public java.lang.String getFileType() {
        return fileType;
    }


    /**
     * Sets the fileType value for this ContentVersion.
     * 
     * @param fileType
     */
    public void setFileType(java.lang.String fileType) {
        this.fileType = fileType;
    }


    /**
     * Gets the firstPublishLocation value for this ContentVersion.
     * 
     * @return firstPublishLocation
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject getFirstPublishLocation() {
        return firstPublishLocation;
    }


    /**
     * Sets the firstPublishLocation value for this ContentVersion.
     * 
     * @param firstPublishLocation
     */
    public void setFirstPublishLocation(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject firstPublishLocation) {
        this.firstPublishLocation = firstPublishLocation;
    }


    /**
     * Gets the firstPublishLocationId value for this ContentVersion.
     * 
     * @return firstPublishLocationId
     */
    public java.lang.String getFirstPublishLocationId() {
        return firstPublishLocationId;
    }


    /**
     * Sets the firstPublishLocationId value for this ContentVersion.
     * 
     * @param firstPublishLocationId
     */
    public void setFirstPublishLocationId(java.lang.String firstPublishLocationId) {
        this.firstPublishLocationId = firstPublishLocationId;
    }


    /**
     * Gets the histories value for this ContentVersion.
     * 
     * @return histories
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getHistories() {
        return histories;
    }


    /**
     * Sets the histories value for this ContentVersion.
     * 
     * @param histories
     */
    public void setHistories(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult histories) {
        this.histories = histories;
    }


    /**
     * Gets the isDeleted value for this ContentVersion.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this ContentVersion.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the isLatest value for this ContentVersion.
     * 
     * @return isLatest
     */
    public java.lang.Boolean getIsLatest() {
        return isLatest;
    }


    /**
     * Sets the isLatest value for this ContentVersion.
     * 
     * @param isLatest
     */
    public void setIsLatest(java.lang.Boolean isLatest) {
        this.isLatest = isLatest;
    }


    /**
     * Gets the lastModifiedBy value for this ContentVersion.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this ContentVersion.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this ContentVersion.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this ContentVersion.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this ContentVersion.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this ContentVersion.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the negativeRatingCount value for this ContentVersion.
     * 
     * @return negativeRatingCount
     */
    public java.lang.Integer getNegativeRatingCount() {
        return negativeRatingCount;
    }


    /**
     * Sets the negativeRatingCount value for this ContentVersion.
     * 
     * @param negativeRatingCount
     */
    public void setNegativeRatingCount(java.lang.Integer negativeRatingCount) {
        this.negativeRatingCount = negativeRatingCount;
    }


    /**
     * Gets the owner value for this ContentVersion.
     * 
     * @return owner
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this ContentVersion.
     * 
     * @param owner
     */
    public void setOwner(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User owner) {
        this.owner = owner;
    }


    /**
     * Gets the ownerId value for this ContentVersion.
     * 
     * @return ownerId
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }


    /**
     * Sets the ownerId value for this ContentVersion.
     * 
     * @param ownerId
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }


    /**
     * Gets the pathOnClient value for this ContentVersion.
     * 
     * @return pathOnClient
     */
    public java.lang.String getPathOnClient() {
        return pathOnClient;
    }


    /**
     * Sets the pathOnClient value for this ContentVersion.
     * 
     * @param pathOnClient
     */
    public void setPathOnClient(java.lang.String pathOnClient) {
        this.pathOnClient = pathOnClient;
    }


    /**
     * Gets the positiveRatingCount value for this ContentVersion.
     * 
     * @return positiveRatingCount
     */
    public java.lang.Integer getPositiveRatingCount() {
        return positiveRatingCount;
    }


    /**
     * Sets the positiveRatingCount value for this ContentVersion.
     * 
     * @param positiveRatingCount
     */
    public void setPositiveRatingCount(java.lang.Integer positiveRatingCount) {
        this.positiveRatingCount = positiveRatingCount;
    }


    /**
     * Gets the publishStatus value for this ContentVersion.
     * 
     * @return publishStatus
     */
    public java.lang.String getPublishStatus() {
        return publishStatus;
    }


    /**
     * Sets the publishStatus value for this ContentVersion.
     * 
     * @param publishStatus
     */
    public void setPublishStatus(java.lang.String publishStatus) {
        this.publishStatus = publishStatus;
    }


    /**
     * Gets the ratingCount value for this ContentVersion.
     * 
     * @return ratingCount
     */
    public java.lang.Integer getRatingCount() {
        return ratingCount;
    }


    /**
     * Sets the ratingCount value for this ContentVersion.
     * 
     * @param ratingCount
     */
    public void setRatingCount(java.lang.Integer ratingCount) {
        this.ratingCount = ratingCount;
    }


    /**
     * Gets the reasonForChange value for this ContentVersion.
     * 
     * @return reasonForChange
     */
    public java.lang.String getReasonForChange() {
        return reasonForChange;
    }


    /**
     * Sets the reasonForChange value for this ContentVersion.
     * 
     * @param reasonForChange
     */
    public void setReasonForChange(java.lang.String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }


    /**
     * Gets the systemModstamp value for this ContentVersion.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this ContentVersion.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the tagCsv value for this ContentVersion.
     * 
     * @return tagCsv
     */
    public java.lang.String getTagCsv() {
        return tagCsv;
    }


    /**
     * Sets the tagCsv value for this ContentVersion.
     * 
     * @param tagCsv
     */
    public void setTagCsv(java.lang.String tagCsv) {
        this.tagCsv = tagCsv;
    }


    /**
     * Gets the title value for this ContentVersion.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this ContentVersion.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the versionData value for this ContentVersion.
     * 
     * @return versionData
     */
    public byte[] getVersionData() {
        return versionData;
    }


    /**
     * Sets the versionData value for this ContentVersion.
     * 
     * @param versionData
     */
    public void setVersionData(byte[] versionData) {
        this.versionData = versionData;
    }


    /**
     * Gets the versionNumber value for this ContentVersion.
     * 
     * @return versionNumber
     */
    public java.lang.String getVersionNumber() {
        return versionNumber;
    }


    /**
     * Sets the versionNumber value for this ContentVersion.
     * 
     * @param versionNumber
     */
    public void setVersionNumber(java.lang.String versionNumber) {
        this.versionNumber = versionNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContentVersion)) return false;
        ContentVersion other = (ContentVersion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contentDocument==null && other.getContentDocument()==null) || 
             (this.contentDocument!=null &&
              this.contentDocument.equals(other.getContentDocument()))) &&
            ((this.contentDocumentId==null && other.getContentDocumentId()==null) || 
             (this.contentDocumentId!=null &&
              this.contentDocumentId.equals(other.getContentDocumentId()))) &&
            ((this.contentModifiedById==null && other.getContentModifiedById()==null) || 
             (this.contentModifiedById!=null &&
              this.contentModifiedById.equals(other.getContentModifiedById()))) &&
            ((this.contentModifiedDate==null && other.getContentModifiedDate()==null) || 
             (this.contentModifiedDate!=null &&
              this.contentModifiedDate.equals(other.getContentModifiedDate()))) &&
            ((this.contentSize==null && other.getContentSize()==null) || 
             (this.contentSize!=null &&
              this.contentSize.equals(other.getContentSize()))) &&
            ((this.contentUrl==null && other.getContentUrl()==null) || 
             (this.contentUrl!=null &&
              this.contentUrl.equals(other.getContentUrl()))) &&
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
            ((this.featuredContentBoost==null && other.getFeaturedContentBoost()==null) || 
             (this.featuredContentBoost!=null &&
              this.featuredContentBoost.equals(other.getFeaturedContentBoost()))) &&
            ((this.featuredContentDate==null && other.getFeaturedContentDate()==null) || 
             (this.featuredContentDate!=null &&
              this.featuredContentDate.equals(other.getFeaturedContentDate()))) &&
            ((this.fileType==null && other.getFileType()==null) || 
             (this.fileType!=null &&
              this.fileType.equals(other.getFileType()))) &&
            ((this.firstPublishLocation==null && other.getFirstPublishLocation()==null) || 
             (this.firstPublishLocation!=null &&
              this.firstPublishLocation.equals(other.getFirstPublishLocation()))) &&
            ((this.firstPublishLocationId==null && other.getFirstPublishLocationId()==null) || 
             (this.firstPublishLocationId!=null &&
              this.firstPublishLocationId.equals(other.getFirstPublishLocationId()))) &&
            ((this.histories==null && other.getHistories()==null) || 
             (this.histories!=null &&
              this.histories.equals(other.getHistories()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.isLatest==null && other.getIsLatest()==null) || 
             (this.isLatest!=null &&
              this.isLatest.equals(other.getIsLatest()))) &&
            ((this.lastModifiedBy==null && other.getLastModifiedBy()==null) || 
             (this.lastModifiedBy!=null &&
              this.lastModifiedBy.equals(other.getLastModifiedBy()))) &&
            ((this.lastModifiedById==null && other.getLastModifiedById()==null) || 
             (this.lastModifiedById!=null &&
              this.lastModifiedById.equals(other.getLastModifiedById()))) &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            ((this.negativeRatingCount==null && other.getNegativeRatingCount()==null) || 
             (this.negativeRatingCount!=null &&
              this.negativeRatingCount.equals(other.getNegativeRatingCount()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.ownerId==null && other.getOwnerId()==null) || 
             (this.ownerId!=null &&
              this.ownerId.equals(other.getOwnerId()))) &&
            ((this.pathOnClient==null && other.getPathOnClient()==null) || 
             (this.pathOnClient!=null &&
              this.pathOnClient.equals(other.getPathOnClient()))) &&
            ((this.positiveRatingCount==null && other.getPositiveRatingCount()==null) || 
             (this.positiveRatingCount!=null &&
              this.positiveRatingCount.equals(other.getPositiveRatingCount()))) &&
            ((this.publishStatus==null && other.getPublishStatus()==null) || 
             (this.publishStatus!=null &&
              this.publishStatus.equals(other.getPublishStatus()))) &&
            ((this.ratingCount==null && other.getRatingCount()==null) || 
             (this.ratingCount!=null &&
              this.ratingCount.equals(other.getRatingCount()))) &&
            ((this.reasonForChange==null && other.getReasonForChange()==null) || 
             (this.reasonForChange!=null &&
              this.reasonForChange.equals(other.getReasonForChange()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.tagCsv==null && other.getTagCsv()==null) || 
             (this.tagCsv!=null &&
              this.tagCsv.equals(other.getTagCsv()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.versionData==null && other.getVersionData()==null) || 
             (this.versionData!=null &&
              java.util.Arrays.equals(this.versionData, other.getVersionData()))) &&
            ((this.versionNumber==null && other.getVersionNumber()==null) || 
             (this.versionNumber!=null &&
              this.versionNumber.equals(other.getVersionNumber())));
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
        if (getContentDocument() != null) {
            _hashCode += getContentDocument().hashCode();
        }
        if (getContentDocumentId() != null) {
            _hashCode += getContentDocumentId().hashCode();
        }
        if (getContentModifiedById() != null) {
            _hashCode += getContentModifiedById().hashCode();
        }
        if (getContentModifiedDate() != null) {
            _hashCode += getContentModifiedDate().hashCode();
        }
        if (getContentSize() != null) {
            _hashCode += getContentSize().hashCode();
        }
        if (getContentUrl() != null) {
            _hashCode += getContentUrl().hashCode();
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
        if (getFeaturedContentBoost() != null) {
            _hashCode += getFeaturedContentBoost().hashCode();
        }
        if (getFeaturedContentDate() != null) {
            _hashCode += getFeaturedContentDate().hashCode();
        }
        if (getFileType() != null) {
            _hashCode += getFileType().hashCode();
        }
        if (getFirstPublishLocation() != null) {
            _hashCode += getFirstPublishLocation().hashCode();
        }
        if (getFirstPublishLocationId() != null) {
            _hashCode += getFirstPublishLocationId().hashCode();
        }
        if (getHistories() != null) {
            _hashCode += getHistories().hashCode();
        }
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getIsLatest() != null) {
            _hashCode += getIsLatest().hashCode();
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
        if (getNegativeRatingCount() != null) {
            _hashCode += getNegativeRatingCount().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getOwnerId() != null) {
            _hashCode += getOwnerId().hashCode();
        }
        if (getPathOnClient() != null) {
            _hashCode += getPathOnClient().hashCode();
        }
        if (getPositiveRatingCount() != null) {
            _hashCode += getPositiveRatingCount().hashCode();
        }
        if (getPublishStatus() != null) {
            _hashCode += getPublishStatus().hashCode();
        }
        if (getRatingCount() != null) {
            _hashCode += getRatingCount().hashCode();
        }
        if (getReasonForChange() != null) {
            _hashCode += getReasonForChange().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getTagCsv() != null) {
            _hashCode += getTagCsv().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getVersionData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVersionData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVersionData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVersionNumber() != null) {
            _hashCode += getVersionNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContentVersion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentVersion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentDocument"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentDocumentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentDocumentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentModifiedById");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentModifiedById"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentModifiedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentModifiedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentUrl"));
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
        elemField.setFieldName("featuredContentBoost");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FeaturedContentBoost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("featuredContentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FeaturedContentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FileType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstPublishLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FirstPublishLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sObject"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstPublishLocationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FirstPublishLocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("isDeleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsDeleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isLatest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsLatest"));
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
        elemField.setFieldName("negativeRatingCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NegativeRatingCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("pathOnClient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PathOnClient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("positiveRatingCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PositiveRatingCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("publishStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PublishStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratingCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "RatingCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonForChange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ReasonForChange"));
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
        elemField.setFieldName("tagCsv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TagCsv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "VersionData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "VersionNumber"));
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
