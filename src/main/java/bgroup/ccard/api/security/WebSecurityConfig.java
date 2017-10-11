package bgroup.ccard.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
        RequestMatcher csrfRequestMatcher = new RequestMatcher() {

            private RegexRequestMatcher requestMatcher =
                    new RegexRequestMatcher("/api/.*", null);

            @Override
            public boolean matches(HttpServletRequest request) {

                // Enable the CSRF
                if(requestMatcher.matches(request))
                    return true;

                // You can add here any other rule on the request object, returning
                // true if the CSRF must be enabled, false otherwise
                // ....

                // No CSRF for other requests
                return false;
            }

        }; // new RequestMatcher
        http
                .authorizeRequests()
                .antMatchers("/api/auth").permitAll()
                //.antMatchers("/api/greeting").permitAll()
                .anyRequest().authenticated()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache())
                .and()
                .csrf()
                .and().csrf().ignoringAntMatchers("/api/auth")
                .and().csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
        ;
    }
}