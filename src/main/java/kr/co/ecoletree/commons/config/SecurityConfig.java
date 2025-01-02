/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : 이현정
 * Create Date : 2024. 11. 07
 * DESC : SecurityConfig.java
 *****************************************************************/
package kr.co.ecoletree.commons.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.ecoletree.commons.filter.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.security.Key;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void init() {
        // 비밀 키를 Base64로 디코딩하여 Key 객체로 변환합니다.
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .addFilterBefore(new JwtAuthenticationFilter(key), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .anyRequest().permitAll();
    }
}
