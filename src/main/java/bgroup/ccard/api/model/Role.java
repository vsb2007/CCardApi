package bgroup.ccard.api.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;


public class Role implements GrantedAuthority {
    static final Logger logger = LoggerFactory.getLogger(Role.class);

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return this.name;
    }
}
