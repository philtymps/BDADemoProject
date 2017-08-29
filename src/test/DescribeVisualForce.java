
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="includeAllDetails" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="namespacePrefix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "includeAllDetails",
    "namespacePrefix"
})
@XmlRootElement(name = "describeVisualForce")
public class DescribeVisualForce {

    protected boolean includeAllDetails;
    @XmlElement(required = true, nillable = true)
    protected String namespacePrefix;

    /**
     * Gets the value of the includeAllDetails property.
     * 
     */
    public boolean isIncludeAllDetails() {
        return includeAllDetails;
    }

    /**
     * Sets the value of the includeAllDetails property.
     * 
     */
    public void setIncludeAllDetails(boolean value) {
        this.includeAllDetails = value;
    }

    /**
     * Gets the value of the namespacePrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespacePrefix() {
        return namespacePrefix;
    }

    /**
     * Sets the value of the namespacePrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespacePrefix(String value) {
        this.namespacePrefix = value;
    }

}
