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

/**
 * JPA Entity for the ORDER_STATUS table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "ORDER_STATUS")
public class OrderStatusEntity implements Serializable {

    private static final long serialVersionUID = 4514060025207978263L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_ORDER_STATUS", sequenceName = "SEQ_ORDER_STATUS", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_ORDER_STATUS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "ORDER_STATUS", nullable = false, length = 25)
    private String orderStatus;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;


    public OrderStatusEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatusEntity that = (OrderStatusEntity) o;

        return orderStatus.equals(that.orderStatus);

    }

    @Override
    public int hashCode() {
        return orderStatus.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderStatusEntity{" +
                "id=" + id +
                ", orderStatus='" + orderStatus + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
