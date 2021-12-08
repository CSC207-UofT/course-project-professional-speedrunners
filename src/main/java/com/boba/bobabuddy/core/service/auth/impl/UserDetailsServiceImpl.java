package com.boba.bobabuddy.core.service.auth.impl;

import com.boba.bobabuddy.core.domain.Role;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.user.FindUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * custom implementation of UserDetailsService used for fetching user information for Spring security
 * Instead of having our User entity implement UserDetail interface we opted to construct a Simple Spring security
 * Implementation of UserDetail object with information fetched from our own FindUser service.
 */

@Service("UserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FindUserService findUser;

    @Autowired
    public UserDetailsServiceImpl(FindUserService findUser) {
        this.findUser = findUser;
    }

    private static List<GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map((s) -> new SimpleGrantedAuthority(s.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        try {
            user = findUser.findByEmail(email);
        } catch (ResourceNotFoundException e){
            throw new UsernameNotFoundException("cannot find user", e);
        }
        final boolean enabled = true;
        final boolean accountNonExpired = true;
        final boolean credentialsNonExpired = true;
        final boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), "", enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, getAuthorities(user.getRoles()));
    }
}
