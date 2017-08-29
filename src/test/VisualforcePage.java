
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VisualforcePage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VisualforcePage">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:partner.soap.sforce.com}DescribeLayoutComponent">
 *       &lt;sequence>
 *         &lt;element name="showLabel" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="showScrollbars" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="suggestedHeight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="suggestedWidth" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VisualforcePage", propOrder = {
    "showLabel",
    "showScrollbars",
    "suggestedHeight",
    "suggestedWidth",
    "url"
})
public class VisualforcePage
    extends DescribeLayoutComponent
{

    protected boolean showLabel;
    protected boolean showScrollbars;
    @XmlElement(required = true)
    protected String suggestedHeight;
    @XmlElement(required = true)
    protected String suggestedWidth;
    @XmlElement(required = true)
    protected String url;

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

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

}
