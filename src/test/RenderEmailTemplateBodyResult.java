
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RenderEmailTemplateBodyResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RenderEmailTemplateBodyResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errors" type="{urn:partner.soap.sforce.com}RenderEmailTemplateError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mergedBody" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RenderEmailTemplateBodyResult", propOrder = {
    "errors",
    "mergedBody",
    "success"
})
public class RenderEmailTemplateBodyResult {

    protected List<RenderEmailTemplateError> errors;
    @XmlElement(required = true, nillable = true)
    protected String mergedBody;
    protected boolean success;

    /**
     * Gets the value of the errors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RenderEmailTemplateError }
     * 
     * 
     */
    public List<RenderEmailTemplateError> getErrors() {
        if (errors == null) {
            errors = new ArrayList<RenderEmailTemplateError>();
        }
        return this.errors;
    }

    /**
     * Gets the value of the mergedBody property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMergedBody() {
        return mergedBody;
    }

    /**
     * Sets the value of the mergedBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMergedBody(String value) {
        this.mergedBody = value;
    }

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

}
