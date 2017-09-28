package bgroup.ccard.api.model;

/**
 * Created by VSB on 23.09.2017.
 * ccardApi
 */
public class CustomHttpSessionStatus {
    private Boolean auth;
    private String username;
    private String message;

    public CustomHttpSessionStatus(Boolean auth, String username, String message) {
        this.auth = auth;
        this.username = username;
        this.message = message;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
