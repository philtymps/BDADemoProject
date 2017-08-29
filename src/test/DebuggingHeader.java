
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="categories" type="{urn:partner.soap.sforce.com}LogInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="debugLevel" type="{urn:partner.soap.sforce.com}DebugLevel"/>
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
    "categories",
    "debugLevel"
})
@XmlRootElement(name = "DebuggingHeader")
public class DebuggingHeader {

    protected List<LogInfo> categories;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected DebugLevel debugLevel;

    /**
     * Gets the value of the categories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogInfo }
     * 
     * 
     */
    public List<LogInfo> getCategories() {
        if (categories == null) {
            categories = new ArrayList<LogInfo>();
        }
        return this.categories;
    }

    /**
     * Gets the value of the debugLevel property.
     * 
     * @return
     *     possible object is
     *     {@link DebugLevel }
     *     
     */
    public DebugLevel getDebugLevel() {
        return debugLevel;
    }

    /**
     * Sets the value of the debugLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebugLevel }
     *     
     */
    public void setDebugLevel(DebugLevel value) {
        this.debugLevel = value;
    }

}
