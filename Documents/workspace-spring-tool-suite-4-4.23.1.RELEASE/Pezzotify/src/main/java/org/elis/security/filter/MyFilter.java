package org.elis.security.filter;

import java.io.IOException;

import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyFilter extends OncePerRequestFilter {

    
    private final HandlerExceptionResolver resolver;
    private final JWTUtilities jwtUtilities;
    private final UserDetailsService userDetailsService;

    public MyFilter(JWTUtilities jwtUtilities, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
                    @Lazy UserDetailsService userDetailsService) {
        this.jwtUtilities = jwtUtilities;
        this.resolver = resolver;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")
                || securityContext.getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenString = authorizationHeader.substring(7);
        String username = null;

        try {
            username = jwtUtilities.getSubject(tokenString);
            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null && jwtUtilities.isTokenValid(tokenString, userDetails)) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, userDetails, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
