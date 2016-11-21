package prv.mark.project.common.domain.json;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * JSON Response object for stock price requests.
 *
 * @author mlglenn on 11/21/2016.
 */
public class StockPriceResponse extends AbstractJsonResponse implements Serializable {

    private static final long serialVersionUID = -4876427250584992595L;

    private Long id;
    private String stockSymbol;
    private BigDecimal stockPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }
}
