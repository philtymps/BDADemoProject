
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DescribePathAssistantStep complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescribePathAssistantStep">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="closed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="converted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fields" type="{urn:partner.soap.sforce.com}DescribePathAssistantField" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="info" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="layoutSection" type="{urn:partner.soap.sforce.com}DescribeLayoutSection"/>
 *         &lt;element name="picklistLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="picklistValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="won" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescribePathAssistantStep", propOrder = {
    "closed",
    "converted",
    "fields",
    "info",
    "layoutSection",
    "picklistLabel",
    "picklistValue",
    "won"
})
public class DescribePathAssistantStep {

    protected boolean closed;
    protected boolean converted;
    protected List<DescribePathAssistantField> fields;
    @XmlElement(required = true)
    protected String info;
    @XmlElement(required = true)
    protected DescribeLayoutSection layoutSection;
    @XmlElement(required = true)
    protected String picklistLabel;
    @XmlElement(required = true)
    protected String picklistValue;
    protected boolean won;

    /**
     * Gets the value of the closed property.
     * 
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Sets the value of the closed property.
     * 
     */
    public void setClosed(boolean value) {
        this.closed = value;
    }

    /**
     * Gets the value of the converted property.
     * 
     */
    public boolean isConverted() {
        return converted;
    }

    /**
     * Sets the value of the converted property.
     * 
     */
    public void setConverted(boolean value) {
        this.converted = value;
    }

    /**
     * Gets the value of the fields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescribePathAssistantField }
     * 
     * 
     */
    public List<DescribePathAssistantField> getFields() {
        if (fields == null) {
            fields = new ArrayList<DescribePathAssistantField>();
        }
        return this.fields;
    }

    /**
     * Gets the value of the info property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo(String value) {
        this.info = value;
    }

    /**
     * Gets the value of the layoutSection property.
     * 
     * @return
     *     possible object is
     *     {@link DescribeLayoutSection }
     *     
     */
    public DescribeLayoutSection getLayoutSection() {
        return layoutSection;
    }

    /**
     * Sets the value of the layoutSection property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescribeLayoutSection }
     *     
     */
    public void setLayoutSection(DescribeLayoutSection value) {
        this.layoutSection = value;
    }

    /**
     * Gets the value of the picklistLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicklistLabel() {
        return picklistLabel;
    }

    /**
     * Sets the value of the picklistLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicklistLabel(String value) {
        this.picklistLabel = value;
    }

    /**
     * Gets the value of the picklistValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicklistValue() {
        return picklistValue;
    }

    /**
     * Sets the value of the picklistValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicklistValue(String value) {
        this.picklistValue = value;
    }

    /**
     * Gets the value of the won property.
     * 
     */
    public boolean isWon() {
        return won;
    }

    /**
     * Sets the value of the won property.
     * 
     */
    public void setWon(boolean value) {
        this.won = value;
    }

}
