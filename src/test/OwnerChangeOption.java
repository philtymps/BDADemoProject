
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OwnerChangeOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OwnerChangeOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{urn:partner.soap.sforce.com}OwnerChangeOptionType"/>
 *         &lt;element name="execute" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OwnerChangeOption", propOrder = {
    "type",
    "execute"
})
public class OwnerChangeOption {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OwnerChangeOptionType type;
    protected boolean execute;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OwnerChangeOptionType }
     *     
     */
    public OwnerChangeOptionType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OwnerChangeOptionType }
     *     
     */
    public void setType(OwnerChangeOptionType value) {
        this.type = value;
    }

    /**
     * Gets the value of the execute property.
     * 
     */
    public boolean isExecute() {
        return execute;
    }

    /**
     * Sets the value of the execute property.
     * 
     */
    public void setExecute(boolean value) {
        this.execute = value;
    }

}
