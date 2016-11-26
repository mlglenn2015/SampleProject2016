package prv.mark.project.common.domain;

/**
 * Status codes enumeration.
 *
 * @author mlglenn on 11/25/2016.
 */
public enum EnumStatusCodes {

    SUCCESS(0),
    REQUEST_FAILED(1),
    UPDATE_FAILED(2),
    MESSAGING_FAILED(3),
    LOGGING_FAILED(4),
    OTHER_FAILURE(5);

    private int statudCode;

    EnumStatusCodes(int statusCode) {
        this.statudCode = statusCode;
    }

    public int getStatudCode() {
        return statudCode;
    }

}
