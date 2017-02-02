//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.30 at 08:58:08 PM EST 
//


package prv.mark.project.stockservice.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import prv.mark.project.stockservice.common.schemas.RequestHeader;


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
 *         &lt;element name="head" type="{http://project.mark.prv/stockservice/common/schemas}RequestHeader"/>
 *         &lt;element name="tickerSymbol" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "head",
    "tickerSymbol"
})
@XmlRootElement(name = "getStockPriceRequest")
public class GetStockPriceRequest {

    @XmlElement(required = true)
    protected RequestHeader head;
    @XmlElement(required = true)
    protected String tickerSymbol;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader }
     *     
     */
    public RequestHeader getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader }
     *     
     */
    public void setHead(RequestHeader value) {
        this.head = value;
    }

    /**
     * Gets the value of the tickerSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Sets the value of the tickerSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTickerSymbol(String value) {
        this.tickerSymbol = value;
    }

}