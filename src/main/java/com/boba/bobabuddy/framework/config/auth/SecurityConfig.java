package com.boba.bobabuddy.framework.config.auth;

import com.boba.bobabuddy.core.service.auth.FirebaseService;
import com.boba.bobabuddy.framework.config.auth.firebase.FirebaseAuthenticationProvider;
import com.boba.bobabuddy.framework.config.auth.firebase.FirebaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

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

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {


        private final FirebaseService firebaseService;

        @Autowired
        public ApplicationSecurity(FirebaseService firebaseService) {
            this.firebaseService = firebaseService;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
//            web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
//                    "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v2/swagger.json", "/h2-console/**",
//                    "/admin/user/token", "/**");
            web.ignoring().antMatchers("/**");
        }

//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class).authorizeRequests()//
//                    .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole(Roles.ADMIN)
//                    .antMatchers(HttpMethod.GET, "/**").authenticated()//
//                    .antMatchers(HttpMethod.PUT, "/**").authenticated()//
//                    .antMatchers(HttpMethod.POST, "/**").hasAnyRole(Roles.ADMIN)
//                    .and().csrf().disable()//
//                    .anonymous().authorities(Roles.ROLE_ANONYMOUS);//
//        }

        private FirebaseFilter tokenAuthorizationFilter() {
            return new FirebaseFilter(firebaseService);
        }
    }
}