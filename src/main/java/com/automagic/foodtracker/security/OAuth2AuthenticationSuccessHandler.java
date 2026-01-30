package com.automagic.foodtracker.security;

import com.automagic.foodtracker.config.JwtProperties;
import com.automagic.foodtracker.entity.User;
import com.automagic.foodtracker.repository.user.UserRepository;
import com.automagic.foodtracker.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    @Value("${app.oauth2.target-url}")
    private String redirectUri;

    @Value("${app.security.cookies-secure}")
    private boolean cookiesSecure;

    @Value("${app.security.cookies.same-site}")
    private String cookieSameSite;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        String token = jwtUtil.generateToken(user.getId());

        ResponseCookie cookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(cookiesSecure)
                .path("/")
                .sameSite(cookieSameSite)
                .partitioned(true)
                .maxAge(jwtProperties.getExpirationInSeconds())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
}
