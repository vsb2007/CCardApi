package bgroup.ccard.api.controller;

import bgroup.ccard.api.mapper.UserMapper;
import bgroup.ccard.api.model.CustomHttpSessionStatus;
import bgroup.ccard.api.model.Role;
import bgroup.ccard.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VSB on 22.09.2017.
 * ccardApi
 */
@RestController
public class AuthController {
    static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final String ATTRIBUTE_USER_KEY = "user";
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserMapper userMapper;

    /*
        @RequestMapping(value = {"api/auth"}, method = RequestMethod.POST)
        public CustomHttpSessionStatus login(@RequestParam(value = "username", defaultValue = "") String username,
                                             @RequestParam(value = "password", defaultValue = "null") String password) {

            User user = null;
            if (username != null && !username.equals(""))
                user = findUser(username);
            CustomHttpSessionStatus httpSessionStatus = getHttpCustomSessionStatus(user, password);
            return httpSessionStatus;
        }
    */
    @RequestMapping(value = {"api/auth"}, method = RequestMethod.POST)
    public CustomHttpSessionStatus loginJson(@RequestBody User user,HttpServletRequest request) {
        User user1 = null;
        if (user != null) {
            user1 = findUser(user.getUsername());
        }
        CustomHttpSessionStatus customHttpSessionStatus = getHttpCustomSessionStatus(user1, user.getPassword(),request);
        return customHttpSessionStatus;
    }

    private User findUser(String username) {
        User user = null;
        try {
            user = userMapper.findUserByLogin(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private CustomHttpSessionStatus getHttpCustomSessionStatus(User user, String password, HttpServletRequest request) {
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                addRole(user);
            } else user = null;
        } else {
            logger.error("\n\nUser Not Found\n\n");
        }
        CustomHttpSessionStatus httpSessionStatus = null;
        Authentication auth = null;
        if (user == null) {
            httpSessionStatus = new CustomHttpSessionStatus(false, null, null, "Не правильные логин или пароль");
            auth = new UsernamePasswordAuthenticationToken(null, null, null);
        } else {
            CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            httpSessionStatus = new CustomHttpSessionStatus(true, user.getUsername(), token, "ok");

            auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        //SecurityContextHolder.getContext().getAuthentication().
        return httpSessionStatus;
    }

    @RequestMapping(value = "/api/auth/csrf-token", method = RequestMethod.GET)
    public @ResponseBody
    CsrfToken getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return token;
    }

    private boolean addRole(User user) {
        Role r = new Role();
        r.setName("ROLE_USER");
        List<Role> roles = user.getAuthorities();
        if (roles == null) roles = new ArrayList<Role>();
        roles.add(r);
        user.setAuthorities(roles);

        return true;
    }
}
