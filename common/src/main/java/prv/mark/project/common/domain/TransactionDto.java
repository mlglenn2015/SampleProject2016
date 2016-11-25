package prv.mark.project.common.domain;

import java.time.LocalDateTime;

/**
 * Transaction data transfer object.
 *
 * @author mlglenn on 11/25/2016.
 */
public class TransactionDto {

    private LocalDateTime logDateTime;
    private String transactionType;
    private String transactionDetail;

    public LocalDateTime getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(LocalDateTime logDateTime) {
        this.logDateTime = logDateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(String transactionDetail) {
        this.transactionDetail = transactionDetail;
    }
}
