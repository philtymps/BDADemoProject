
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FieldDiff complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FieldDiff">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="difference" type="{urn:partner.soap.sforce.com}differenceType"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldDiff", propOrder = {
    "difference",
    "name"
})
public class FieldDiff {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected DifferenceType difference;
    @XmlElement(required = true)
    protected String name;

    /**
     * Gets the value of the difference property.
     * 
     * @return
     *     possible object is
     *     {@link DifferenceType }
     *     
     */
    public DifferenceType getDifference() {
        return difference;
    }

    /**
     * Sets the value of the difference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DifferenceType }
     *     
     */
    public void setDifference(DifferenceType value) {
        this.difference = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
