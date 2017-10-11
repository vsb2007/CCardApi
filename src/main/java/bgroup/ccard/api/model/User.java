package bgroup.ccard.api.model;

import java.util.List;

public class User {

    private String username;
    private String password;
    private List<Role> authorities;

    public User(String user, String password) {
        this.username = user;
        this.password = password;

    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public boolean isUserHasRole(String roleName) {
        for (Role role : this.authorities) {
            if (role.getName().equals(roleName)) return true;
        }
        return false;
    }

}
