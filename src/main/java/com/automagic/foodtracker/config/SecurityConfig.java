package com.automagic.foodtracker.config;

import com.automagic.foodtracker.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${app.security.csrf-enabled}")
    private boolean csrfEnabled;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (csrfEnabled) {
            CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
            tokenRepository.setCookieName("XSRF-TOKEN");
            CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

            http
                    .csrf(csrf -> csrf
                            .csrfTokenRepository(tokenRepository)
                            .csrfTokenRequestHandler(requestHandler::handle))

                    .addFilterAfter((servletRequest, servletResponse, filterChain) -> {
                        CsrfToken token = (CsrfToken) servletRequest.getAttribute(CsrfToken.class.getName());
                        if (token != null) {
                            token.getToken();
                        }
                        filterChain.doFilter(servletRequest, servletResponse);
                    }, CsrfFilter.class);
        } else {
            http.csrf(csrf -> csrf.disable());
        }

        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/refresh", "/api/auth/logout").permitAll()
                        .requestMatchers("/api/auth/**").authenticated()
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
