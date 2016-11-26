package prv.mark.project.common.domain;

/**
 * Created by Owner on 11/26/2016.
 */
public enum EnumOrderTypes {

    MARKET_ORDER(1, "MARKET ORDER"),
    LIMIT_ORDER(2, "LIMIT ORDER"),
    STOP_LOSS_ORDER(3, "STOP LOSS ORDER");

    private Integer orderId;
    private String orderType;

    EnumOrderTypes(Integer orderId, String orderType) {
        this.orderId = orderId;
        this.orderType = orderType;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }
}
