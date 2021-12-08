package com.boba.bobabuddy.framework.config.auth.firebase;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.auth.exceptions.FirebaseUserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Custom AuthenticationProvide impl to provide firebase auth functionalities.
 *
 */

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userService;

    @Autowired
    public FirebaseAuthenticationProvider(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
        try {
            UserDetails details = userService.loadUserByUsername(authenticationToken.getName());
            authenticationToken = new FirebaseAuthenticationToken(details, authentication.getCredentials(),
                    details.getAuthorities());
            return authenticationToken;
        } catch (UsernameNotFoundException e) {
            throw new FirebaseUserNotExistsException(e);
        }
    }

}
