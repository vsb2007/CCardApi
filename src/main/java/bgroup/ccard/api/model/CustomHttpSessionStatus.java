package bgroup.ccard.api.model;

import org.springframework.security.web.csrf.CsrfToken;

/**
 * Created by VSB on 23.09.2017.
 * ccardApi
 */
public class CustomHttpSessionStatus {
    private Boolean auth;
    private String username;
    private String message;
    private CsrfToken token;


    public CustomHttpSessionStatus(Boolean auth, String username, CsrfToken token, String message) {
        this.auth = auth;
        this.username = username;
        this.message = message;
        this.token = token;
    }

    public CsrfToken getToken() {
        return token;
    }

    public void setToken(CsrfToken token) {
        this.token = token;
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
