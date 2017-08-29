
package test;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="flexiPages" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="contexts" type="{urn:partner.soap.sforce.com}FlexipageContext" maxOccurs="unbounded" minOccurs="0"/>
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
    "flexiPages",
    "contexts"
})
@XmlRootElement(name = "describeFlexiPages")
public class DescribeFlexiPages {

    @XmlElement(required = true)
    protected List<String> flexiPages;
    protected List<FlexipageContext> contexts;

    /**
     * Gets the value of the flexiPages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flexiPages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlexiPages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFlexiPages() {
        if (flexiPages == null) {
            flexiPages = new ArrayList<String>();
        }
        return this.flexiPages;
    }

    /**
     * Gets the value of the contexts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contexts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContexts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlexipageContext }
     * 
     * 
     */
    public List<FlexipageContext> getContexts() {
        if (contexts == null) {
            contexts = new ArrayList<FlexipageContext>();
        }
        return this.contexts;
    }

}
