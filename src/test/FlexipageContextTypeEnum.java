
package test;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlexipageContextTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FlexipageContextTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENTITYNAME"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FlexipageContextTypeEnum")
@XmlEnum
public enum FlexipageContextTypeEnum {

    ENTITYNAME;

    public String value() {
        return name();
    }

    public static FlexipageContextTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
