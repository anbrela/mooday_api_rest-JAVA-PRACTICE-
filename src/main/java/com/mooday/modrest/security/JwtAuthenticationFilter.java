package com.mooday.modrest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooday.modrest.exception.ForbiddenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

            String uri = request.getRequestURI();
            // Excluir el endpoint de login y verificación
            if (uri.equals("/api/auth/google") || uri.equals("/api/auth/verify")) {
                chain.doFilter(request, response);
                return;
            }

            String header = request.getHeader("Authorization");
            String username = null;
            String authToken = null;

            if (header == null || !header.startsWith("Bearer ")) {
                throw new RequestRejectedException("cambieo");
            }

            if (header != null && header.startsWith("Bearer ")) {
                authToken = header.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                } catch (Exception e) {
                    throw new ForbiddenException("Invalid token");
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    var authentication = jwtTokenUtil.getAuthentication(authToken, userDetails);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new ForbiddenException("Invalid token");
                }
            }

            chain.doFilter(request, response);

    }
}
