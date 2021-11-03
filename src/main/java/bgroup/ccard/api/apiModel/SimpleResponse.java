package bgroup.ccard.api.apiModel;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SimpleResponse {

    private String status;
    private String error_message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String detailsKey;

    public SimpleResponse(String status, String error_message) {
        this.status = status;
        this.error_message = error_message;
    }

    public SimpleResponse(String status, String error_message, String detailsKey) {
        this.status = status;
        this.error_message = error_message;
        this.detailsKey = detailsKey;
    }

    public String getDetailsKey() {
        return detailsKey;
    }

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return error_message;
    }

}
