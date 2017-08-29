
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DescribePathAssistant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescribePathAssistant">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="apiName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pathPicklistField" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="picklistsForRecordType" type="{urn:partner.soap.sforce.com}PicklistForRecordType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="recordTypeId" type="{urn:partner.soap.sforce.com}ID"/>
 *         &lt;element name="steps" type="{urn:partner.soap.sforce.com}DescribePathAssistantStep" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescribePathAssistant", propOrder = {
    "active",
    "apiName",
    "label",
    "pathPicklistField",
    "picklistsForRecordType",
    "recordTypeId",
    "steps"
})
public class DescribePathAssistant {

    protected boolean active;
    @XmlElement(required = true)
    protected String apiName;
    @XmlElement(required = true)
    protected String label;
    @XmlElement(required = true)
    protected String pathPicklistField;
    @XmlElement(nillable = true)
    protected List<PicklistForRecordType> picklistsForRecordType;
    @XmlElement(required = true, nillable = true)
    protected String recordTypeId;
    protected List<DescribePathAssistantStep> steps;

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the apiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * Sets the value of the apiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApiName(String value) {
        this.apiName = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the pathPicklistField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathPicklistField() {
        return pathPicklistField;
    }

    /**
     * Sets the value of the pathPicklistField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathPicklistField(String value) {
        this.pathPicklistField = value;
    }

    /**
     * Gets the value of the picklistsForRecordType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the picklistsForRecordType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPicklistsForRecordType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PicklistForRecordType }
     * 
     * 
     */
    public List<PicklistForRecordType> getPicklistsForRecordType() {
        if (picklistsForRecordType == null) {
            picklistsForRecordType = new ArrayList<PicklistForRecordType>();
        }
        return this.picklistsForRecordType;
    }

    /**
     * Gets the value of the recordTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordTypeId() {
        return recordTypeId;
    }

    /**
     * Sets the value of the recordTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordTypeId(String value) {
        this.recordTypeId = value;
    }

    /**
     * Gets the value of the steps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the steps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSteps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescribePathAssistantStep }
     * 
     * 
     */
    public List<DescribePathAssistantStep> getSteps() {
        if (steps == null) {
            steps = new ArrayList<DescribePathAssistantStep>();
        }
        return this.steps;
    }

}
