package com.boba.bobabuddy.core.usecase.auth;

import com.boba.bobabuddy.infrastructure.config.auth.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class GetAuthorities{


    /**
     * Get the email of the currently authenticated user
     * TODO: handle when there is no currently authenticated user (should never happen anyways due to security filters not allowing anon to access /user methods)
     * @return the email of the currently authenticated user
     */
    public static String getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     * Get the email of the currently authenticated user
     * TODO: handle when there is no currently authenticated user (should never happen anyways due to security filters)
     *
     * @return whether the currently authenticated user is an admin
     */
    public static boolean isAdmin() {
        Collection<GrantedAuthority> authorities = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        for (GrantedAuthority g: authorities) {
            String stringAuthority = g.getAuthority();
            if (SecurityConfig.Roles.ROLE_ADMIN.equals(stringAuthority)) {
               return true;
            }
        }
        return false;
    }
}
