
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApiFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApiFault">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exceptionCode" type="{urn:fault.partner.soap.sforce.com}ExceptionCode"/>
 *         &lt;element name="exceptionMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extendedErrorDetails" type="{urn:partner.soap.sforce.com}ExtendedErrorDetails" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApiFault", namespace = "urn:fault.partner.soap.sforce.com", propOrder = {
    "exceptionCode",
    "exceptionMessage",
    "extendedErrorDetails"
})
@XmlSeeAlso({
    InvalidNewPasswordFault.class,
    LoginFault.class,
    InvalidQueryLocatorFault.class,
    UnexpectedErrorFault.class,
    InvalidIdFault.class,
    ApiQueryFault.class
})
public class ApiFault {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ExceptionCode exceptionCode;
    @XmlElement(required = true)
    protected String exceptionMessage;
    @XmlElement(nillable = true)
    protected List<ExtendedErrorDetails> extendedErrorDetails;

    /**
     * Gets the value of the exceptionCode property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionCode }
     *     
     */
    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    /**
     * Sets the value of the exceptionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionCode }
     *     
     */
    public void setExceptionCode(ExceptionCode value) {
        this.exceptionCode = value;
    }

    /**
     * Gets the value of the exceptionMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * Sets the value of the exceptionMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExceptionMessage(String value) {
        this.exceptionMessage = value;
    }

    /**
     * Gets the value of the extendedErrorDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extendedErrorDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtendedErrorDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtendedErrorDetails }
     * 
     * 
     */
    public List<ExtendedErrorDetails> getExtendedErrorDetails() {
        if (extendedErrorDetails == null) {
            extendedErrorDetails = new ArrayList<ExtendedErrorDetails>();
        }
        return this.extendedErrorDetails;
    }

}
