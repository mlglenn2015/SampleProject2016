package prv.mark.project.common.domain;

/**
 * Enumeration for transaction types.
 *
 * @author mlglenn 11/26/2016.
 */
public enum EnumTransactionTypes {

    STOCK_PRICE_INQUIRY(0, "STOCK PRICE INQUIRY"),
    STOCK_PURCHASE(1, "STOCK PURCHASE"),
    STOCK_SALE(2, "STOCK SALE");

    private Integer transactionTypeIndex;
    private String transactionTypeDesc;

    EnumTransactionTypes(Integer transactionTypeIndex, String transactionTypeDesc) {
        this.transactionTypeIndex = transactionTypeIndex;
        this.transactionTypeDesc = transactionTypeDesc;
    }

    public Integer getTransactionTypeIndex() {
        return transactionTypeIndex;
    }

    public String getTransactionTypeDesc() {
        return transactionTypeDesc;
    }
}
