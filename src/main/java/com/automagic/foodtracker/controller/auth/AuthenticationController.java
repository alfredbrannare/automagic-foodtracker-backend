package com.automagic.foodtracker.controller.auth;

import com.automagic.foodtracker.config.JwtProperties;
import com.automagic.foodtracker.dto.request.auth.LoginRequest;
import com.automagic.foodtracker.dto.request.auth.RefreshRequest;
import com.automagic.foodtracker.dto.request.auth.RegisterRequest;
import com.automagic.foodtracker.dto.response.auth.AuthResponse;
import com.automagic.foodtracker.dto.response.auth.MessageResponse;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.auth.AuthService;
import jakarta.validation.Valid;
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

    @Value("${app.security.cookie-same-site}")
    private String cookieSameSite;

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    @GetMapping("/csrf")
    public ResponseEntity<Void> getCsrfToken() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<MessageResponse> checkAuth() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);

        HttpHeaders headers = buildCookieHeaders(authResponse.getAccessToken(), authResponse.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);

        HttpHeaders headers = buildCookieHeaders(response.getAccessToken(), response.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new MessageResponse("Login successfull"));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal AuthenticatedUser user) {
        authService.deleteUser(user.getUserId());

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(ACCESS_TOKEN_COOKIE, "", 0).toString());

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(REFRESH_TOKEN_COOKIE, "", 0).toString());

        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout() {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(ACCESS_TOKEN_COOKIE, "", 0).toString());

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(REFRESH_TOKEN_COOKIE, "", 0).toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new MessageResponse("Logout successfull"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<MessageResponse> refreshToken(@CookieValue(name = REFRESH_TOKEN_COOKIE) String refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken);

        HttpHeaders headers = buildCookieHeaders(response.getAccessToken(), response.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new MessageResponse("Refresh successfull"));
    }

    // Helper Methods
    private HttpHeaders buildCookieHeaders(String accessToken, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(ACCESS_TOKEN_COOKIE,
                        accessToken,
                        jwtProperties.getExpirationInSeconds()).toString());

        headers.add(HttpHeaders.SET_COOKIE,
                buildCookie(REFRESH_TOKEN_COOKIE,
                        refreshToken,
                        jwtProperties.getRefreshExpirationInSeconds()).toString());

        return headers;
    }

    private ResponseCookie buildCookie(String name, String value, long maxAgeSeconds) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(cookiesSecure)
                .path("/")
                .sameSite(cookieSameSite)
                .maxAge(maxAgeSeconds)
                .build();
    }

}
