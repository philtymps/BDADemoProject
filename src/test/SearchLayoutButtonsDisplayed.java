
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchLayoutButtonsDisplayed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchLayoutButtonsDisplayed">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="buttons" type="{urn:partner.soap.sforce.com}SearchLayoutButton" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchLayoutButtonsDisplayed", propOrder = {
    "applicable",
    "buttons"
})
public class SearchLayoutButtonsDisplayed {

    protected boolean applicable;
    protected List<SearchLayoutButton> buttons;

    /**
     * Gets the value of the applicable property.
     * 
     */
    public boolean isApplicable() {
        return applicable;
    }

    /**
     * Sets the value of the applicable property.
     * 
     */
    public void setApplicable(boolean value) {
        this.applicable = value;
    }

    /**
     * Gets the value of the buttons property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the buttons property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getButtons().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchLayoutButton }
     * 
     * 
     */
    public List<SearchLayoutButton> getButtons() {
        if (buttons == null) {
            buttons = new ArrayList<SearchLayoutButton>();
        }
        return this.buttons;
    }

}
