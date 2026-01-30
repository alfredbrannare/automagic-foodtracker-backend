package com.automagic.foodtracker.controller.auth;

import com.automagic.foodtracker.config.JwtProperties;
import com.automagic.foodtracker.dto.response.auth.AuthResponse;
import com.automagic.foodtracker.dto.response.auth.MessageResponse;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @Value("${app.security.cookies-secure}")
    private boolean cookiesSecure;

    @Value("${app.security.cookies.same-site}")
    private String cookieSameSite;

    private static final String ACCESS_TOKEN_COOKIE = "access_token";

    @GetMapping("/csrf")
    public ResponseEntity<Void> getCsrfToken() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<MessageResponse> checkAuth(@AuthenticationPrincipal AuthenticatedUser user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal AuthenticatedUser user) {
        authService.deleteUser(user.getUserId());

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(ACCESS_TOKEN_COOKIE, "", 0).toString());

        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(ACCESS_TOKEN_COOKIE, "", 0).toString());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    // Helper Methods
    private HttpHeaders buildCookieHeaders(String accessToken, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(ACCESS_TOKEN_COOKIE,
                        accessToken,
                        jwtProperties.getExpirationInSeconds()).toString());

        return headers;
    }

    private ResponseCookie buildCookie(String name, String value, long maxAgeSeconds) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(cookiesSecure)
                .path("/")
                .sameSite(cookieSameSite)
                .maxAge(maxAgeSeconds)
                .partitioned(true)
                .build();
    }

}
