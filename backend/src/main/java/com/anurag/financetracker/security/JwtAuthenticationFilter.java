package com.anurag.financetracker.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("FILTER RUNNING");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            if (jwtService.isTokenValid(token)) {

                String email = jwtService.extractEmail(token);

                User user = userRepository.findByEmail(email)
                        .orElse(null);

                if (user != null) {

                    SimpleGrantedAuthority authority =
                            new SimpleGrantedAuthority("ROLE_" + user.getRole());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    java.util.List.of(authority)
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "Authenticated user: " + email +
                            " | Role: " + user.getRole()
                    );
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}