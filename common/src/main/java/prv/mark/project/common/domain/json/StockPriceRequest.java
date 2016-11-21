package prv.mark.project.common.domain.json;

import java.io.Serializable;

/**
 * JSON object for stock price requests.
 *
 * @author mlglenn on 11/21/2016.
 */
public class StockPriceRequest implements Serializable {

    private static final long serialVersionUID = -6244272692119572193L;

    private String stockSymbol;


    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
