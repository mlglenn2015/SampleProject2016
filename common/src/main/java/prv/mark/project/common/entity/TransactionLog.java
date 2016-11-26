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
 * JPA Entity for the TRANSACTION_LOG table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "TRANSACTION_LOG")
public class TransactionLog implements Serializable {

    private static final long serialVersionUID = -3345464116138634617L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_TRANSACTION_LOG", sequenceName = "SEQ_TRANSACTION_LOG", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TRANSACTION_LOG", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOG_DATE_TIME", nullable = false)
    private Date logDateTime;

    @Basic
    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 25)
    private String transactionType;

    @Basic
    @Column(name = "TRANSACTION_DATA", nullable = true, length = 500)
    private String transactionData;


    public TransactionLog() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionLog that = (TransactionLog) o;

        if (!id.equals(that.id)) return false;
        if (!logDateTime.equals(that.logDateTime)) return false;
        return transactionType.equals(that.transactionType);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + logDateTime.hashCode();
        result = 31 * result + transactionType.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "id=" + id +
                ", logDateTime=" + logDateTime +
                ", transactionType='" + transactionType + '\'' +
                ", transactionData='" + transactionData + '\'' +
                '}';
    }
}
