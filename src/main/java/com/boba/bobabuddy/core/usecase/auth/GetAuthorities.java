package com.boba.bobabuddy.core.usecase.auth;

import com.boba.bobabuddy.infrastructure.config.auth.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 *  Class for checking authentication.
 *  TODO: check if each method works properly
 */

public class GetAuthorities {


    /**
     * Get the email of the currently authenticated user.
     * @return the email of the currently authenticated user
     */
    public static String getCurrentUser() {
        if (isAuthenticated()) {
            return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        }
        return null;
    }

    /**
     * Check if the the currently authenticated user is an admin.
     *
     * @return whether the currently authenticated user is an admin
     */
    public static boolean isAdmin() {
        if (isAuthenticated()) {
            Collection<GrantedAuthority> authorities = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
            for (GrantedAuthority g: authorities) {
                String stringAuthority = g.getAuthority();
                if (SecurityConfig.Roles.ROLE_ADMIN.equals(stringAuthority)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if there is an authentication and if the identity is a user.
     *
     * @return if there is an authentication and if the authentication has principal user
     */
    private static boolean isAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return false;
        }
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User;
    }
}
