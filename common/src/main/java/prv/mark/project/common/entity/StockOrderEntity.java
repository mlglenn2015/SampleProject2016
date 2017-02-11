package prv.mark.project.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * JPA Entity for the STOCK_ORDER table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "STOCK_ORDER")
public class StockOrderEntity implements Serializable {

    private static final long serialVersionUID = 6487242329400213976L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_STOCK_ORDER", sequenceName = "SEQ_STOCK_ORDER", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_STOCK_ORDER", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "STOCK_SYMBOL", nullable = false, length = 10)
    private String stockSymbol;

    @Basic
    @Column(name = "ACTION", nullable = false, length = 4)
    private String action;

    @Basic
    @Column(name = "QUANTITY", nullable = false, columnDefinition = "NUMBER(5,0) DEFAULT 1")
    private Long quantity;

    @Basic
    @Column(name = "PRICE", nullable = false, columnDefinition = "NUMBER(10,2) DEFAULT 1")
    private BigDecimal price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORDER_DATE", nullable = false)
    private Date orderDate;

    @Basic
    @Column(name = "ORDER_TYPE", nullable = false, length = 25)
    private String orderType;

    @Basic
    @Column(name = "ORDER_STATUS", nullable = false, length = 25)
    private String orderStatus;


    public StockOrderEntity() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockOrderEntity that = (StockOrderEntity) o;

        if (!id.equals(that.id)) return false;
        if (!stockSymbol.equals(that.stockSymbol)) return false;
        if (!action.equals(that.action)) return false;
        if (!quantity.equals(that.quantity)) return false;
        if (!price.equals(that.price)) return false;
        if (!orderDate.equals(that.orderDate)) return false;
        if (!orderType.equals(that.orderType)) return false;
        return orderStatus.equals(that.orderStatus);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + stockSymbol.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + orderDate.hashCode();
        result = 31 * result + orderType.hashCode();
        result = 31 * result + orderStatus.hashCode();
        return result;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "StockOrderEntity{" +
                "id=" + id +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", action='" + action + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orderDate=" + orderDate +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
