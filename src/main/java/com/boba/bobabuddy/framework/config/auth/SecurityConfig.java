package com.boba.bobabuddy.framework.config.auth;

import com.boba.bobabuddy.core.service.auth.FirebaseService;
import com.boba.bobabuddy.framework.config.auth.firebase.FirebaseAuthenticationProvider;
import com.boba.bobabuddy.framework.config.auth.firebase.FirebaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuration class for spring security
 */

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    // Defines available roles in the system
    @Configuration
    public static class Roles {
        public static final String ANONYMOUS = "ANONYMOUS";
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";

        private static final String ROLE_ = "ROLE_";
        public static final String ROLE_ANONYMOUS = ROLE_ + ANONYMOUS;
        public static final String ROLE_USER = ROLE_ + USER;
        public static final String ROLE_ADMIN = ROLE_ + ADMIN;

        @Bean
        public RoleHierarchy roleHierarchy() {
            RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
            String hierarchy = String.format("%s > %s \n %s > %s", ROLE_ADMIN, ROLE_USER, ROLE_USER, ROLE_ANONYMOUS);
            roleHierarchy.setHierarchy(hierarchy);
            return roleHierarchy;
        }
    }

    // Hook up our custom UserDetail service and FirebaseAuthProvider over Spring security defaults.
    @Configuration
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

        private final UserDetailsService userService;
        private final FirebaseAuthenticationProvider firebaseProvider;

        @Autowired
        public AuthenticationSecurity(UserDetailsService userService, FirebaseAuthenticationProvider firebaseProvider) {
            this.userService = userService;
            this.firebaseProvider = firebaseProvider;
        }


        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService);
            auth.authenticationProvider(firebaseProvider);

        }
    }


    // Defines the filter chain used and web endpoint matching conditions
    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {


        private final FirebaseService firebaseService;

        @Autowired
        public ApplicationSecurity(FirebaseService firebaseService) {
            this.firebaseService = firebaseService;
        }

        @Override
        public void configure(WebSecurity web){
            web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                    "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v2/swagger.json", "/h2-console/**",
                    "/admin/user/token");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class).authorizeRequests()//
                    .mvcMatchers("/admin/**").hasAnyRole(Roles.ADMIN)
                    .mvcMatchers(HttpMethod.POST, "/users/").permitAll()
                    .mvcMatchers(HttpMethod.POST).authenticated()
                    .antMatchers("/**").permitAll()
                    .and().csrf().disable()//
                    .anonymous().authorities(Roles.ROLE_ANONYMOUS);//
        }

        private FirebaseFilter tokenAuthorizationFilter() {
            return new FirebaseFilter(firebaseService);
        }
    }
}
