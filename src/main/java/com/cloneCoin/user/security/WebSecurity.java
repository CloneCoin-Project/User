package com.cloneCoin.user.security;

import com.cloneCoin.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment env;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // using-generated-password 사용해서 인증 할 수 있다.
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeRequests().antMatchers("/actuator/**", "/welcome").permitAll();
//        http.authorizeRequests()
//                                .antMatchers("/**")
//                                .hasIpAddress("172.30.1.23")
//                                .permitAll()
//                                .and()
//                                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable(); // h2 console 접근 하려면 이거 선언해야됨.
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(), userService, env);
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인증과 관련된 권한 부여
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
