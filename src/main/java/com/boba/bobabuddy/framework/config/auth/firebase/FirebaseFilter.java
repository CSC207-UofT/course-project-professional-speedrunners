package com.boba.bobabuddy.framework.config.auth.firebase;

import com.boba.bobabuddy.core.service.auth.FirebaseService;
import com.boba.bobabuddy.core.service.auth.exceptions.FirebaseTokenInvalidException;
import com.google.firebase.auth.FirebaseToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Authorization-Firebase";

    private final FirebaseService firebaseService;

    public FirebaseFilter(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String xAuth = request.getHeader(HEADER_NAME);
        if (StringUtils.isBlank(xAuth)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                FirebaseToken token = firebaseService.parseToken(xAuth);

                String userName = token.getEmail();

                Authentication auth = new FirebaseAuthenticationToken(userName, token);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            } catch (FirebaseTokenInvalidException e) {
                throw new SecurityException(e);
            }
        }
    }

}