package prv.mark.project.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * JPA Entity for the STOCK_PRICE table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "STOCK_PRICE")
public class StockPrice implements Serializable {

    private static final long serialVersionUID = 8663315086156386511L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_STOCK_PRICE", sequenceName = "SEQ_STOCK_PRICE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_STOCK_PRICE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "STOCK_SYMBOL", nullable = false, length = 10)
    private String stockSymbol;

    @Basic
    @Column(name = "CURRENT_PRICE", nullable = false, columnDefinition = "NUMBER(10,2) DEFAULT 1")
    private BigDecimal currentPrice;


    public StockPrice() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockPrice that = (StockPrice) o;

        return stockSymbol.equals(that.stockSymbol);

    }

    @Override
    public int hashCode() {
        return stockSymbol.hashCode();
    }

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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "StockPrice{" +
                "id=" + id +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
