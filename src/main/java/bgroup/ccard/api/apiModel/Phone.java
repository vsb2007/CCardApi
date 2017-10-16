package bgroup.ccard.api.apiModel;

/**
 * Created by VSB on 16.10.2017.
 * ccardApi
 */
public class Phone {

    private String status;
    private String error_message;
    private String phone;

    public Phone(String status, String error_message, String phone) {
        this.phone = phone;
        this.status = status;
        this.error_message = error_message;
    }

    public Phone() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
