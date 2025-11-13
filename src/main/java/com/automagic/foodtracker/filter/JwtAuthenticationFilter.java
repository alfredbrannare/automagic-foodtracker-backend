package com.automagic.foodtracker.filter;

import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("=== JWT Filter Debug ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Cookies present: " + (request.getCookies() != null));

        String token = null;

        if (request.getCookies() != null) {
            System.out.println("Number of cookies: " + request.getCookies().length);
            for (Cookie cookie : request.getCookies()) {
                System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue().substring(0, Math.min(20, cookie.getValue().length())) + "...");
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null && jwtUtil.validateToken(token)) {
            System.out.println("Token valid! UserId: " + jwtUtil.extractUserId(token));
            String userId = jwtUtil.extractUserId(token);

            AuthenticatedUser principal = new AuthenticatedUser(userId);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            System.out.println("No valid token found");
        }

        filterChain.doFilter(request, response);
    }
}
