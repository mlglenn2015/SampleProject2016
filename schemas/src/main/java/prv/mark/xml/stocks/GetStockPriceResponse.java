//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.14 at 07:24:24 PM EST 
//


package prv.mark.xml.stocks;

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
 *         &lt;element name="quote" type="{http://prv.mark.project/stocks}StockQuote" minOccurs="0"/>
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
        "quote"
})
@XmlRootElement(name = "GetStockPriceResponse")
public class GetStockPriceResponse {

    protected StockQuote quote;

    /**
     * Gets the value of the quote property.
     *
     * @return
     *     possible object is
     *     {@link StockQuote }
     *
     */
    public StockQuote getQuote() {
        return quote;
    }

    /**
     * Sets the value of the quote property.
     *
     * @param value
     *     allowed object is
     *     {@link StockQuote }
     *
     */
    public void setQuote(StockQuote value) {
        this.quote = value;
    }

}

