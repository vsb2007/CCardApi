package bgroup.ccard.api.apiModel;

/**
 * Created by VSB on 16.10.2017.
 * ccardApi
 */
public class Surname {

    private String status;
    private String error_message;
    private String surname;

    public Surname(String status, String error_message, String surname) {
        this.surname = surname;
        this.status = status;
        this.error_message = error_message;
    }

    public Surname() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
