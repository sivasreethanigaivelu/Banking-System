package com.example.AccountService.filter;

import com.example.AccountService.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("\n================ JWT FILTER =================");

        String authHeader = request.getHeader("Authorization");

        System.out.println("Request URI : " + request.getRequestURI());
        System.out.println("Authorization Header : " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No JWT token found.");
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String token = authHeader.substring(7);

            System.out.println("JWT Token : " + token);

            Claims claims = jwtUtil.extractAllClaims(token);

            System.out.println("All Claims : " + claims);

            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            Long customerId = claims.get("customerId", Long.class);

            System.out.println("Username   : " + username);
            System.out.println("Role       : " + role);
            System.out.println("CustomerId : " + customerId);

            request.setAttribute("role", role);
            request.setAttribute("customerId", customerId);

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Authentication Stored Successfully");
            System.out.println("SecurityContext : " + SecurityContextHolder.getContext().getAuthentication());

        } catch (Exception e) {
            System.out.println("JWT ERROR");
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);

        System.out.println("Response Status : " + response.getStatus());
        System.out.println("================ END JWT FILTER ================\n");
    }
}