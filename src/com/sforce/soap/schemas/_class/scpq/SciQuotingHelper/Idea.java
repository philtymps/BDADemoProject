/**
 * Idea.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class Idea  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private java.lang.String body;

    private java.lang.String categories;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult comments;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Community community;

    private java.lang.String communityId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private java.lang.Boolean isDeleted;

    private java.lang.Boolean isHtml;

    private java.lang.Boolean isLocked;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.IdeaComment lastComment;

    private java.util.Calendar lastCommentDate;

    private java.lang.String lastCommentId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy;

    private java.lang.String lastModifiedById;

    private java.util.Calendar lastModifiedDate;

    private java.lang.Integer numComments;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea parentIdea;

    private java.lang.String parentIdeaId;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.RecordType recordType;

    private java.lang.String recordTypeId;

    private java.lang.String status;

    private java.util.Calendar systemModstamp;

    private java.lang.String title;

    private java.lang.Double voteScore;

    private java.lang.Double voteTotal;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult votes;

    public Idea() {
    }

    public Idea(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           java.lang.String body,
           java.lang.String categories,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult comments,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Community community,
           java.lang.String communityId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           java.lang.Boolean isDeleted,
           java.lang.Boolean isHtml,
           java.lang.Boolean isLocked,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.IdeaComment lastComment,
           java.util.Calendar lastCommentDate,
           java.lang.String lastCommentId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy,
           java.lang.String lastModifiedById,
           java.util.Calendar lastModifiedDate,
           java.lang.Integer numComments,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea parentIdea,
           java.lang.String parentIdeaId,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.RecordType recordType,
           java.lang.String recordTypeId,
           java.lang.String status,
           java.util.Calendar systemModstamp,
           java.lang.String title,
           java.lang.Double voteScore,
           java.lang.Double voteTotal,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult votes) {
        super(
            fieldsToNull,
            id);
        this.body = body;
        this.categories = categories;
        this.comments = comments;
        this.community = community;
        this.communityId = communityId;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.isDeleted = isDeleted;
        this.isHtml = isHtml;
        this.isLocked = isLocked;
        this.lastComment = lastComment;
        this.lastCommentDate = lastCommentDate;
        this.lastCommentId = lastCommentId;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedDate = lastModifiedDate;
        this.numComments = numComments;
        this.parentIdea = parentIdea;
        this.parentIdeaId = parentIdeaId;
        this.recordType = recordType;
        this.recordTypeId = recordTypeId;
        this.status = status;
        this.systemModstamp = systemModstamp;
        this.title = title;
        this.voteScore = voteScore;
        this.voteTotal = voteTotal;
        this.votes = votes;
    }


    /**
     * Gets the body value for this Idea.
     * 
     * @return body
     */
    public java.lang.String getBody() {
        return body;
    }


    /**
     * Sets the body value for this Idea.
     * 
     * @param body
     */
    public void setBody(java.lang.String body) {
        this.body = body;
    }


    /**
     * Gets the categories value for this Idea.
     * 
     * @return categories
     */
    public java.lang.String getCategories() {
        return categories;
    }


    /**
     * Sets the categories value for this Idea.
     * 
     * @param categories
     */
    public void setCategories(java.lang.String categories) {
        this.categories = categories;
    }


    /**
     * Gets the comments value for this Idea.
     * 
     * @return comments
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this Idea.
     * 
     * @param comments
     */
    public void setComments(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult comments) {
        this.comments = comments;
    }


    /**
     * Gets the community value for this Idea.
     * 
     * @return community
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Community getCommunity() {
        return community;
    }


    /**
     * Sets the community value for this Idea.
     * 
     * @param community
     */
    public void setCommunity(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Community community) {
        this.community = community;
    }


    /**
     * Gets the communityId value for this Idea.
     * 
     * @return communityId
     */
    public java.lang.String getCommunityId() {
        return communityId;
    }


    /**
     * Sets the communityId value for this Idea.
     * 
     * @param communityId
     */
    public void setCommunityId(java.lang.String communityId) {
        this.communityId = communityId;
    }


    /**
     * Gets the createdBy value for this Idea.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this Idea.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this Idea.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this Idea.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this Idea.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this Idea.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the isDeleted value for this Idea.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this Idea.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the isHtml value for this Idea.
     * 
     * @return isHtml
     */
    public java.lang.Boolean getIsHtml() {
        return isHtml;
    }


    /**
     * Sets the isHtml value for this Idea.
     * 
     * @param isHtml
     */
    public void setIsHtml(java.lang.Boolean isHtml) {
        this.isHtml = isHtml;
    }


    /**
     * Gets the isLocked value for this Idea.
     * 
     * @return isLocked
     */
    public java.lang.Boolean getIsLocked() {
        return isLocked;
    }


    /**
     * Sets the isLocked value for this Idea.
     * 
     * @param isLocked
     */
    public void setIsLocked(java.lang.Boolean isLocked) {
        this.isLocked = isLocked;
    }


    /**
     * Gets the lastComment value for this Idea.
     * 
     * @return lastComment
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.IdeaComment getLastComment() {
        return lastComment;
    }


    /**
     * Sets the lastComment value for this Idea.
     * 
     * @param lastComment
     */
    public void setLastComment(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.IdeaComment lastComment) {
        this.lastComment = lastComment;
    }


    /**
     * Gets the lastCommentDate value for this Idea.
     * 
     * @return lastCommentDate
     */
    public java.util.Calendar getLastCommentDate() {
        return lastCommentDate;
    }


    /**
     * Sets the lastCommentDate value for this Idea.
     * 
     * @param lastCommentDate
     */
    public void setLastCommentDate(java.util.Calendar lastCommentDate) {
        this.lastCommentDate = lastCommentDate;
    }


    /**
     * Gets the lastCommentId value for this Idea.
     * 
     * @return lastCommentId
     */
    public java.lang.String getLastCommentId() {
        return lastCommentId;
    }


    /**
     * Sets the lastCommentId value for this Idea.
     * 
     * @param lastCommentId
     */
    public void setLastCommentId(java.lang.String lastCommentId) {
        this.lastCommentId = lastCommentId;
    }


    /**
     * Gets the lastModifiedBy value for this Idea.
     * 
     * @return lastModifiedBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getLastModifiedBy() {
        return lastModifiedBy;
    }


    /**
     * Sets the lastModifiedBy value for this Idea.
     * 
     * @param lastModifiedBy
     */
    public void setLastModifiedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    /**
     * Gets the lastModifiedById value for this Idea.
     * 
     * @return lastModifiedById
     */
    public java.lang.String getLastModifiedById() {
        return lastModifiedById;
    }


    /**
     * Sets the lastModifiedById value for this Idea.
     * 
     * @param lastModifiedById
     */
    public void setLastModifiedById(java.lang.String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }


    /**
     * Gets the lastModifiedDate value for this Idea.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this Idea.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the numComments value for this Idea.
     * 
     * @return numComments
     */
    public java.lang.Integer getNumComments() {
        return numComments;
    }


    /**
     * Sets the numComments value for this Idea.
     * 
     * @param numComments
     */
    public void setNumComments(java.lang.Integer numComments) {
        this.numComments = numComments;
    }


    /**
     * Gets the parentIdea value for this Idea.
     * 
     * @return parentIdea
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea getParentIdea() {
        return parentIdea;
    }


    /**
     * Sets the parentIdea value for this Idea.
     * 
     * @param parentIdea
     */
    public void setParentIdea(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea parentIdea) {
        this.parentIdea = parentIdea;
    }


    /**
     * Gets the parentIdeaId value for this Idea.
     * 
     * @return parentIdeaId
     */
    public java.lang.String getParentIdeaId() {
        return parentIdeaId;
    }


    /**
     * Sets the parentIdeaId value for this Idea.
     * 
     * @param parentIdeaId
     */
    public void setParentIdeaId(java.lang.String parentIdeaId) {
        this.parentIdeaId = parentIdeaId;
    }


    /**
     * Gets the recordType value for this Idea.
     * 
     * @return recordType
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.RecordType getRecordType() {
        return recordType;
    }


    /**
     * Sets the recordType value for this Idea.
     * 
     * @param recordType
     */
    public void setRecordType(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.RecordType recordType) {
        this.recordType = recordType;
    }


    /**
     * Gets the recordTypeId value for this Idea.
     * 
     * @return recordTypeId
     */
    public java.lang.String getRecordTypeId() {
        return recordTypeId;
    }


    /**
     * Sets the recordTypeId value for this Idea.
     * 
     * @param recordTypeId
     */
    public void setRecordTypeId(java.lang.String recordTypeId) {
        this.recordTypeId = recordTypeId;
    }


    /**
     * Gets the status value for this Idea.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Idea.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the systemModstamp value for this Idea.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this Idea.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the title value for this Idea.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Idea.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the voteScore value for this Idea.
     * 
     * @return voteScore
     */
    public java.lang.Double getVoteScore() {
        return voteScore;
    }


    /**
     * Sets the voteScore value for this Idea.
     * 
     * @param voteScore
     */
    public void setVoteScore(java.lang.Double voteScore) {
        this.voteScore = voteScore;
    }


    /**
     * Gets the voteTotal value for this Idea.
     * 
     * @return voteTotal
     */
    public java.lang.Double getVoteTotal() {
        return voteTotal;
    }


    /**
     * Sets the voteTotal value for this Idea.
     * 
     * @param voteTotal
     */
    public void setVoteTotal(java.lang.Double voteTotal) {
        this.voteTotal = voteTotal;
    }


    /**
     * Gets the votes value for this Idea.
     * 
     * @return votes
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getVotes() {
        return votes;
    }


    /**
     * Sets the votes value for this Idea.
     * 
     * @param votes
     */
    public void setVotes(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult votes) {
        this.votes = votes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Idea)) return false;
        Idea other = (Idea) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.body==null && other.getBody()==null) || 
             (this.body!=null &&
              this.body.equals(other.getBody()))) &&
            ((this.categories==null && other.getCategories()==null) || 
             (this.categories!=null &&
              this.categories.equals(other.getCategories()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.community==null && other.getCommunity()==null) || 
             (this.community!=null &&
              this.community.equals(other.getCommunity()))) &&
            ((this.communityId==null && other.getCommunityId()==null) || 
             (this.communityId!=null &&
              this.communityId.equals(other.getCommunityId()))) &&
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdById==null && other.getCreatedById()==null) || 
             (this.createdById!=null &&
              this.createdById.equals(other.getCreatedById()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.isHtml==null && other.getIsHtml()==null) || 
             (this.isHtml!=null &&
              this.isHtml.equals(other.getIsHtml()))) &&
            ((this.isLocked==null && other.getIsLocked()==null) || 
             (this.isLocked!=null &&
              this.isLocked.equals(other.getIsLocked()))) &&
            ((this.lastComment==null && other.getLastComment()==null) || 
             (this.lastComment!=null &&
              this.lastComment.equals(other.getLastComment()))) &&
            ((this.lastCommentDate==null && other.getLastCommentDate()==null) || 
             (this.lastCommentDate!=null &&
              this.lastCommentDate.equals(other.getLastCommentDate()))) &&
            ((this.lastCommentId==null && other.getLastCommentId()==null) || 
             (this.lastCommentId!=null &&
              this.lastCommentId.equals(other.getLastCommentId()))) &&
            ((this.lastModifiedBy==null && other.getLastModifiedBy()==null) || 
             (this.lastModifiedBy!=null &&
              this.lastModifiedBy.equals(other.getLastModifiedBy()))) &&
            ((this.lastModifiedById==null && other.getLastModifiedById()==null) || 
             (this.lastModifiedById!=null &&
              this.lastModifiedById.equals(other.getLastModifiedById()))) &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            ((this.numComments==null && other.getNumComments()==null) || 
             (this.numComments!=null &&
              this.numComments.equals(other.getNumComments()))) &&
            ((this.parentIdea==null && other.getParentIdea()==null) || 
             (this.parentIdea!=null &&
              this.parentIdea.equals(other.getParentIdea()))) &&
            ((this.parentIdeaId==null && other.getParentIdeaId()==null) || 
             (this.parentIdeaId!=null &&
              this.parentIdeaId.equals(other.getParentIdeaId()))) &&
            ((this.recordType==null && other.getRecordType()==null) || 
             (this.recordType!=null &&
              this.recordType.equals(other.getRecordType()))) &&
            ((this.recordTypeId==null && other.getRecordTypeId()==null) || 
             (this.recordTypeId!=null &&
              this.recordTypeId.equals(other.getRecordTypeId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.voteScore==null && other.getVoteScore()==null) || 
             (this.voteScore!=null &&
              this.voteScore.equals(other.getVoteScore()))) &&
            ((this.voteTotal==null && other.getVoteTotal()==null) || 
             (this.voteTotal!=null &&
              this.voteTotal.equals(other.getVoteTotal()))) &&
            ((this.votes==null && other.getVotes()==null) || 
             (this.votes!=null &&
              this.votes.equals(other.getVotes())));
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
        if (getBody() != null) {
            _hashCode += getBody().hashCode();
        }
        if (getCategories() != null) {
            _hashCode += getCategories().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getCommunity() != null) {
            _hashCode += getCommunity().hashCode();
        }
        if (getCommunityId() != null) {
            _hashCode += getCommunityId().hashCode();
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
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getIsHtml() != null) {
            _hashCode += getIsHtml().hashCode();
        }
        if (getIsLocked() != null) {
            _hashCode += getIsLocked().hashCode();
        }
        if (getLastComment() != null) {
            _hashCode += getLastComment().hashCode();
        }
        if (getLastCommentDate() != null) {
            _hashCode += getLastCommentDate().hashCode();
        }
        if (getLastCommentId() != null) {
            _hashCode += getLastCommentId().hashCode();
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
        if (getNumComments() != null) {
            _hashCode += getNumComments().hashCode();
        }
        if (getParentIdea() != null) {
            _hashCode += getParentIdea().hashCode();
        }
        if (getParentIdeaId() != null) {
            _hashCode += getParentIdeaId().hashCode();
        }
        if (getRecordType() != null) {
            _hashCode += getRecordType().hashCode();
        }
        if (getRecordTypeId() != null) {
            _hashCode += getRecordTypeId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getVoteScore() != null) {
            _hashCode += getVoteScore().hashCode();
        }
        if (getVoteTotal() != null) {
            _hashCode += getVoteTotal().hashCode();
        }
        if (getVotes() != null) {
            _hashCode += getVotes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Idea.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Idea"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("body");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Body"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Categories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("community");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Community"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Community"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communityId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CommunityId"));
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
        elemField.setFieldName("isDeleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsDeleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isHtml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsHtml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isLocked");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsLocked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastComment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastComment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IdeaComment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastCommentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastCommentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastCommentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LastCommentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("numComments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NumComments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentIdea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ParentIdea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Idea"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentIdeaId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ParentIdeaId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "RecordType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "RecordType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "RecordTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voteScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "VoteScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voteTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "VoteTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("votes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Votes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult"));
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
