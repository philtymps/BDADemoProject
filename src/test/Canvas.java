
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Canvas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Canvas">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:partner.soap.sforce.com}DescribeLayoutComponent">
 *       &lt;sequence>
 *         &lt;element name="displayLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="showLabel" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="showScrollbars" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="suggestedHeight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="suggestedWidth" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Canvas", propOrder = {
    "displayLocation",
    "referenceId",
    "showLabel",
    "showScrollbars",
    "suggestedHeight",
    "suggestedWidth"
})
public class Canvas
    extends DescribeLayoutComponent
{

    @XmlElement(required = true)
    protected String displayLocation;
    @XmlElement(required = true)
    protected String referenceId;
    protected boolean showLabel;
    protected boolean showScrollbars;
    @XmlElement(required = true)
    protected String suggestedHeight;
    @XmlElement(required = true)
    protected String suggestedWidth;

    /**
     * Gets the value of the displayLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayLocation() {
        return displayLocation;
    }

    /**
     * Sets the value of the displayLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayLocation(String value) {
        this.displayLocation = value;
    }

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceId(String value) {
        this.referenceId = value;
    }

    /**
     * Gets the value of the showLabel property.
     * 
     */
    public boolean isShowLabel() {
        return showLabel;
    }

    /**
     * Sets the value of the showLabel property.
     * 
     */
    public void setShowLabel(boolean value) {
        this.showLabel = value;
    }

    /**
     * Gets the value of the showScrollbars property.
     * 
     */
    public boolean isShowScrollbars() {
        return showScrollbars;
    }

    /**
     * Sets the value of the showScrollbars property.
     * 
     */
    public void setShowScrollbars(boolean value) {
        this.showScrollbars = value;
    }

    /**
     * Gets the value of the suggestedHeight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuggestedHeight() {
        return suggestedHeight;
    }

    /**
     * Sets the value of the suggestedHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuggestedHeight(String value) {
        this.suggestedHeight = value;
    }

    /**
     * Gets the value of the suggestedWidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuggestedWidth() {
        return suggestedWidth;
    }

    /**
     * Sets the value of the suggestedWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuggestedWidth(String value) {
        this.suggestedWidth = value;
    }

}
