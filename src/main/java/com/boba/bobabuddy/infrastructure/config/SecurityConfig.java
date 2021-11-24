package com.boba.bobabuddy.infrastructure.config;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.infrastructure.dao.UserJpaRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserJpaRepository repo;

    public SecurityConfig(UserJpaRepository repo) {
        this.repo = repo;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> repo
                .findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")
                ));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO configure web security
    }

}