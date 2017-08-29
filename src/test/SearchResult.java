
package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queryId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="searchRecords" type="{urn:partner.soap.sforce.com}SearchRecord" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="searchResultsMetadata" type="{urn:partner.soap.sforce.com}SearchResultsMetadata" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchResult", propOrder = {
    "queryId",
    "searchRecords",
    "searchResultsMetadata"
})
public class SearchResult {

    @XmlElement(required = true)
    protected String queryId;
    protected List<SearchRecord> searchRecords;
    @XmlElementRef(name = "searchResultsMetadata", namespace = "urn:partner.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<SearchResultsMetadata> searchResultsMetadata;

    /**
     * Gets the value of the queryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryId() {
        return queryId;
    }

    /**
     * Sets the value of the queryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryId(String value) {
        this.queryId = value;
    }

    /**
     * Gets the value of the searchRecords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchRecords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchRecords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchRecord }
     * 
     * 
     */
    public List<SearchRecord> getSearchRecords() {
        if (searchRecords == null) {
            searchRecords = new ArrayList<SearchRecord>();
        }
        return this.searchRecords;
    }

    /**
     * Gets the value of the searchResultsMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SearchResultsMetadata }{@code >}
     *     
     */
    public JAXBElement<SearchResultsMetadata> getSearchResultsMetadata() {
        return searchResultsMetadata;
    }

    /**
     * Sets the value of the searchResultsMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SearchResultsMetadata }{@code >}
     *     
     */
    public void setSearchResultsMetadata(JAXBElement<SearchResultsMetadata> value) {
        this.searchResultsMetadata = value;
    }

}
