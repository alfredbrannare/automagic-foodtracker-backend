package com.automagic.foodtracker.security;

import com.automagic.foodtracker.config.JwtProperties;
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

    @Value("${app.oauth2.target-url}")
    private String redirectUri;

    @Value("${app.security.cookies-secure}")
    private boolean cookiesSecure;

    @Value("${app.security.cookies.same-site}")
    private String cookieSameSite;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String sub = null;
        if (oAuth2User.getAttribute("sub") != null) {
            sub = oAuth2User.getAttribute("sub");
        } else if (oAuth2User.getAttribute("id") != null) {
            sub = oAuth2User.getAttribute("id");
        } else {
            sub = oAuth2User.getName();
        }

        String token = jwtUtil.generateToken(sub);

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
