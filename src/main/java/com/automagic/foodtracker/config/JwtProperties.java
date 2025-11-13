package com.automagic.foodtracker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private long expiration;
    private long refreshExpiration;

    public long getExpirationInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(this.expiration);
    }

    public long getRefreshExpirationInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(this.refreshExpiration);
    }

}
