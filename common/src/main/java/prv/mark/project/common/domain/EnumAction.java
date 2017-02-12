package prv.mark.project.common.domain;

/**
 * Enum class for stock order types.
 *
 * Created by Owner on 11/26/2016.
 */
public enum EnumAction {

    BUY(1, "BUY"),
    SELL(2, "SELL");

    private Integer actionId;
    private String actionType;

    EnumAction(Integer actionId, String actionType) {
        this.actionId = actionId;
        this.actionType = actionType;
    }

    public Integer getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

}
