
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="includeOnlyEntitiesWithTabs" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "includeOnlyEntitiesWithTabs"
})
@XmlRootElement(name = "describeSearchableEntities")
public class DescribeSearchableEntities {

    protected boolean includeOnlyEntitiesWithTabs;

    /**
     * Gets the value of the includeOnlyEntitiesWithTabs property.
     * 
     */
    public boolean isIncludeOnlyEntitiesWithTabs() {
        return includeOnlyEntitiesWithTabs;
    }

    /**
     * Sets the value of the includeOnlyEntitiesWithTabs property.
     * 
     */
    public void setIncludeOnlyEntitiesWithTabs(boolean value) {
        this.includeOnlyEntitiesWithTabs = value;
    }

}
