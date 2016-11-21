package prv.mark.project.common.domain.json;

/**
 * Abstract class for JSON responses in the REST Web Services.
 *
 * @author mlglenn on 11/21/2016.
 */
public abstract class AbstractJsonResponse {

    private RespStatus respStatus;
    private Object result;

    public RespStatus getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(RespStatus respStatus) {
        this.respStatus = respStatus;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public enum RespStatus {
        OK("OK"),
        FAIL("FAIL");
        private String status;
        RespStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }
    }
}
