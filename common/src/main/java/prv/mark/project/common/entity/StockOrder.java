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
import java.util.Date;

/**
 * JPA Entity for the STOCK_ORDER table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "STOCK_ORDER")
public class StockOrder implements Serializable {

    private static final long serialVersionUID = 6487242329400213976L;


    //TODO FINISH

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_STOCK_ORDER_ID", sequenceName = "SEQ_STOCK_ORDER_ID", initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "SEQ_STOCK_ORDER_ID", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORDER_DATE", nullable = true)
    private Date orderDate;

    @Basic
    @Column(name = "ORDER_STATUS", nullable = true, length = 25)
    private String orderStatus;

    @Basic
    @Column(name = "QUANTITY", nullable = true, columnDefinition = "NUMBER(5,0) DEFAULT 1")
    private Long quantity;



    public StockOrder() {};

}
