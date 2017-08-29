
package test;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OwnerChangeOptionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OwnerChangeOptionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EnforceNewOwnerHasReadAccess"/>
 *     &lt;enumeration value="TransferOpenActivities"/>
 *     &lt;enumeration value="TransferNotesAndAttachments"/>
 *     &lt;enumeration value="TransferOthersOpenOpportunities"/>
 *     &lt;enumeration value="TransferOwnedOpenOpportunities"/>
 *     &lt;enumeration value="TransferContracts"/>
 *     &lt;enumeration value="TransferOrders"/>
 *     &lt;enumeration value="TransferContacts"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OwnerChangeOptionType")
@XmlEnum
public enum OwnerChangeOptionType {

    @XmlEnumValue("EnforceNewOwnerHasReadAccess")
    ENFORCE_NEW_OWNER_HAS_READ_ACCESS("EnforceNewOwnerHasReadAccess"),
    @XmlEnumValue("TransferOpenActivities")
    TRANSFER_OPEN_ACTIVITIES("TransferOpenActivities"),
    @XmlEnumValue("TransferNotesAndAttachments")
    TRANSFER_NOTES_AND_ATTACHMENTS("TransferNotesAndAttachments"),
    @XmlEnumValue("TransferOthersOpenOpportunities")
    TRANSFER_OTHERS_OPEN_OPPORTUNITIES("TransferOthersOpenOpportunities"),
    @XmlEnumValue("TransferOwnedOpenOpportunities")
    TRANSFER_OWNED_OPEN_OPPORTUNITIES("TransferOwnedOpenOpportunities"),
    @XmlEnumValue("TransferContracts")
    TRANSFER_CONTRACTS("TransferContracts"),
    @XmlEnumValue("TransferOrders")
    TRANSFER_ORDERS("TransferOrders"),
    @XmlEnumValue("TransferContacts")
    TRANSFER_CONTACTS("TransferContacts");
    private final String value;

    OwnerChangeOptionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OwnerChangeOptionType fromValue(String v) {
        for (OwnerChangeOptionType c: OwnerChangeOptionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
