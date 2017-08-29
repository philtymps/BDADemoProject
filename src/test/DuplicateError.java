
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DuplicateError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DuplicateError">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:partner.soap.sforce.com}Error">
 *       &lt;sequence>
 *         &lt;element name="duplicateResult" type="{urn:partner.soap.sforce.com}DuplicateResult"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DuplicateError", propOrder = {
    "duplicateResult"
})
public class DuplicateError
    extends Error
{

    @XmlElement(required = true)
    protected DuplicateResult duplicateResult;

    /**
     * Gets the value of the duplicateResult property.
     * 
     * @return
     *     possible object is
     *     {@link DuplicateResult }
     *     
     */
    public DuplicateResult getDuplicateResult() {
        return duplicateResult;
    }

    /**
     * Sets the value of the duplicateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DuplicateResult }
     *     
     */
    public void setDuplicateResult(DuplicateResult value) {
        this.duplicateResult = value;
    }

}
