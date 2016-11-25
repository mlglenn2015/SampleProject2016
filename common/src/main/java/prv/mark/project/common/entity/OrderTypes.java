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
 * JPA Entity for the ORDER_TYPES table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "ORDER_TYPES")
public class OrderTypes implements Serializable {

    private static final long serialVersionUID = -3130128895783673541L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_ORDER_TYPES", sequenceName = "SEQ_ORDER_TYPES", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_ORDER_TYPES", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "ORDER_TYPE", nullable = false, length = 25)
    private String orderType;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;


    public OrderTypes() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTypes that = (OrderTypes) o;

        return orderType.equals(that.orderType);

    }

    @Override
    public int hashCode() {
        return orderType.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderTypes{" +
                "id=" + id +
                ", orderType='" + orderType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
