package bgroup.ccard.api.apiModel;

import bgroup.ccard.api.model.FioModel;

/**
 * Created by VSB on 16.10.2017.
 * ccardApi
 */
public class Fio {
    private String status;
    private String error_message;
    private FioModel userdata;

    public Fio(String status, String error_message, FioModel userdata) {
        this.status = status;
        this.error_message = error_message;
        this.userdata = userdata;
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

    public FioModel getUserdata() {
        return userdata;
    }

    public void setUserdata(FioModel userdata) {
        this.userdata = userdata;
    }
}
