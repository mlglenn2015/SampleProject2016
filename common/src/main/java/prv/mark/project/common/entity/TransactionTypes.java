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
 * JPA Entity for the TRANSACTION_TYPES table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "TRANSACTION_TYPES")
public class TransactionTypes implements Serializable {

    private static final long serialVersionUID = -9036028513949225562L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_TRANSACTION_TYPES", sequenceName = "SEQ_TRANSACTION_TYPES", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TRANSACTION_TYPES", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 25)
    private String transactionType;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;


    public TransactionTypes() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionTypes that = (TransactionTypes) o;

        return transactionType.equals(that.transactionType);

    }

    @Override
    public int hashCode() {
        return transactionType.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransactionTypes{" +
                "id=" + id +
                ", transactionType='" + transactionType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
