package com.instafeeling.web.config;

import com.instafeeling.web.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // check if we have the token
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (bearerToken == null || bearerToken.isBlank() || !bearerToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // check if the token is valid
        String token = bearerToken.substring(BEARER_PREFIX.length() + 1);
        if (!this.jwtUtils.validateJWT(token)){
            filterChain.doFilter(request, response);
            return;
        }

        // load the user by username
        String userId = this.jwtUtils.getSubject(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);

        // load user into the security context
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userId,"", Collections.singleton(new SimpleGrantedAuthority("")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
