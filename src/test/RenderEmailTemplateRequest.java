
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RenderEmailTemplateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RenderEmailTemplateRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="templateBodies" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="10"/>
 *         &lt;element name="whatId" type="{urn:partner.soap.sforce.com}ID" minOccurs="0"/>
 *         &lt;element name="whoId" type="{urn:partner.soap.sforce.com}ID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RenderEmailTemplateRequest", propOrder = {
    "templateBodies",
    "whatId",
    "whoId"
})
public class RenderEmailTemplateRequest {

    @XmlElement(required = true)
    protected List<String> templateBodies;
    protected String whatId;
    protected String whoId;

    /**
     * Gets the value of the templateBodies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateBodies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateBodies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTemplateBodies() {
        if (templateBodies == null) {
            templateBodies = new ArrayList<String>();
        }
        return this.templateBodies;
    }

    /**
     * Gets the value of the whatId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhatId() {
        return whatId;
    }

    /**
     * Sets the value of the whatId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhatId(String value) {
        this.whatId = value;
    }

    /**
     * Gets the value of the whoId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhoId() {
        return whoId;
    }

    /**
     * Sets the value of the whoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhoId(String value) {
        this.whoId = value;
    }

}
