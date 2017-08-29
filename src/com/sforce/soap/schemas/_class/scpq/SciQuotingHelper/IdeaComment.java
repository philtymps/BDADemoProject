/**
 * IdeaComment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class IdeaComment  extends com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject  implements java.io.Serializable {
    private java.lang.String commentBody;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy;

    private java.lang.String createdById;

    private java.util.Calendar createdDate;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea idea;

    private java.lang.String ideaId;

    private java.lang.Boolean isDeleted;

    private java.lang.Boolean isHtml;

    private java.util.Calendar systemModstamp;

    private com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult votes;

    public IdeaComment() {
    }

    public IdeaComment(
           java.lang.String[] fieldsToNull,
           java.lang.String id,
           java.lang.String commentBody,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy,
           java.lang.String createdById,
           java.util.Calendar createdDate,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea idea,
           java.lang.String ideaId,
           java.lang.Boolean isDeleted,
           java.lang.Boolean isHtml,
           java.util.Calendar systemModstamp,
           com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult votes) {
        super(
            fieldsToNull,
            id);
        this.commentBody = commentBody;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.idea = idea;
        this.ideaId = ideaId;
        this.isDeleted = isDeleted;
        this.isHtml = isHtml;
        this.systemModstamp = systemModstamp;
        this.votes = votes;
    }


    /**
     * Gets the commentBody value for this IdeaComment.
     * 
     * @return commentBody
     */
    public java.lang.String getCommentBody() {
        return commentBody;
    }


    /**
     * Sets the commentBody value for this IdeaComment.
     * 
     * @param commentBody
     */
    public void setCommentBody(java.lang.String commentBody) {
        this.commentBody = commentBody;
    }


    /**
     * Gets the createdBy value for this IdeaComment.
     * 
     * @return createdBy
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this IdeaComment.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdById value for this IdeaComment.
     * 
     * @return createdById
     */
    public java.lang.String getCreatedById() {
        return createdById;
    }


    /**
     * Sets the createdById value for this IdeaComment.
     * 
     * @param createdById
     */
    public void setCreatedById(java.lang.String createdById) {
        this.createdById = createdById;
    }


    /**
     * Gets the createdDate value for this IdeaComment.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this IdeaComment.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the idea value for this IdeaComment.
     * 
     * @return idea
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea getIdea() {
        return idea;
    }


    /**
     * Sets the idea value for this IdeaComment.
     * 
     * @param idea
     */
    public void setIdea(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea idea) {
        this.idea = idea;
    }


    /**
     * Gets the ideaId value for this IdeaComment.
     * 
     * @return ideaId
     */
    public java.lang.String getIdeaId() {
        return ideaId;
    }


    /**
     * Sets the ideaId value for this IdeaComment.
     * 
     * @param ideaId
     */
    public void setIdeaId(java.lang.String ideaId) {
        this.ideaId = ideaId;
    }


    /**
     * Gets the isDeleted value for this IdeaComment.
     * 
     * @return isDeleted
     */
    public java.lang.Boolean getIsDeleted() {
        return isDeleted;
    }


    /**
     * Sets the isDeleted value for this IdeaComment.
     * 
     * @param isDeleted
     */
    public void setIsDeleted(java.lang.Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    /**
     * Gets the isHtml value for this IdeaComment.
     * 
     * @return isHtml
     */
    public java.lang.Boolean getIsHtml() {
        return isHtml;
    }


    /**
     * Sets the isHtml value for this IdeaComment.
     * 
     * @param isHtml
     */
    public void setIsHtml(java.lang.Boolean isHtml) {
        this.isHtml = isHtml;
    }


    /**
     * Gets the systemModstamp value for this IdeaComment.
     * 
     * @return systemModstamp
     */
    public java.util.Calendar getSystemModstamp() {
        return systemModstamp;
    }


    /**
     * Sets the systemModstamp value for this IdeaComment.
     * 
     * @param systemModstamp
     */
    public void setSystemModstamp(java.util.Calendar systemModstamp) {
        this.systemModstamp = systemModstamp;
    }


    /**
     * Gets the votes value for this IdeaComment.
     * 
     * @return votes
     */
    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult getVotes() {
        return votes;
    }


    /**
     * Sets the votes value for this IdeaComment.
     * 
     * @param votes
     */
    public void setVotes(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult votes) {
        this.votes = votes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdeaComment)) return false;
        IdeaComment other = (IdeaComment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.commentBody==null && other.getCommentBody()==null) || 
             (this.commentBody!=null &&
              this.commentBody.equals(other.getCommentBody()))) &&
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdById==null && other.getCreatedById()==null) || 
             (this.createdById!=null &&
              this.createdById.equals(other.getCreatedById()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.idea==null && other.getIdea()==null) || 
             (this.idea!=null &&
              this.idea.equals(other.getIdea()))) &&
            ((this.ideaId==null && other.getIdeaId()==null) || 
             (this.ideaId!=null &&
              this.ideaId.equals(other.getIdeaId()))) &&
            ((this.isDeleted==null && other.getIsDeleted()==null) || 
             (this.isDeleted!=null &&
              this.isDeleted.equals(other.getIsDeleted()))) &&
            ((this.isHtml==null && other.getIsHtml()==null) || 
             (this.isHtml!=null &&
              this.isHtml.equals(other.getIsHtml()))) &&
            ((this.systemModstamp==null && other.getSystemModstamp()==null) || 
             (this.systemModstamp!=null &&
              this.systemModstamp.equals(other.getSystemModstamp()))) &&
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
        if (getCommentBody() != null) {
            _hashCode += getCommentBody().hashCode();
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
        if (getIdea() != null) {
            _hashCode += getIdea().hashCode();
        }
        if (getIdeaId() != null) {
            _hashCode += getIdeaId().hashCode();
        }
        if (getIsDeleted() != null) {
            _hashCode += getIsDeleted().hashCode();
        }
        if (getIsHtml() != null) {
            _hashCode += getIsHtml().hashCode();
        }
        if (getSystemModstamp() != null) {
            _hashCode += getSystemModstamp().hashCode();
        }
        if (getVotes() != null) {
            _hashCode += getVotes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdeaComment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IdeaComment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commentBody");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CommentBody"));
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
        elemField.setFieldName("idea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Idea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Idea"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ideaId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IdeaId"));
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
        elemField.setFieldName("isHtml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IsHtml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
