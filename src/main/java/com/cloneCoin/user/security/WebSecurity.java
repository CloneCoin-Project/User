package com.cloneCoin.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // using-generated-password 사용해서 인증 할 수 있다.
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll(); // user 로 오는 엔드포인트 허용
        http.headers().frameOptions().disable(); // h2 console 접근 하려면 이거 선언해야됨.
    }
}
