
package test;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GrammaticalNumber.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GrammaticalNumber">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Singular"/>
 *     &lt;enumeration value="Plural"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GrammaticalNumber")
@XmlEnum
public enum GrammaticalNumber {

    @XmlEnumValue("Singular")
    SINGULAR("Singular"),
    @XmlEnumValue("Plural")
    PLURAL("Plural");
    private final String value;

    GrammaticalNumber(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GrammaticalNumber fromValue(String v) {
        for (GrammaticalNumber c: GrammaticalNumber.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
