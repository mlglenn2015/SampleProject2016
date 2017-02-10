package prv.mark.project.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity for the STOCK_SYMBOL table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "STOCK_SYMBOL")
public class StockSymbol implements Serializable {

    private static final long serialVersionUID = -4054807544419267346L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_STOCK_SYMBOL", sequenceName = "SEQ_STOCK_SYMBOL", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_STOCK_SYMBOL", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "TICKER_SYMBOL", nullable = false, length = 10)
    private String tickerSymbol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockSymbol that = (StockSymbol) o;

        if (!id.equals(that.id)) return false;
        return tickerSymbol.equals(that.tickerSymbol);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + tickerSymbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StockSymbol{" +
                "id=" + id +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                '}';
    }
}
