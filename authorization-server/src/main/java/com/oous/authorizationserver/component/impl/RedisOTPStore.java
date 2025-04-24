package com.oous.authorizationserver.component.impl;

import com.oous.authorizationserver.component.OtpStore;
import com.oous.authorizationserver.domain.response.response.information.SessionCookieNotFoundException;
import com.oous.authorizationserver.util.CryptoUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class RedisOTPStore implements OtpStore {

    private static final String SESSION_ID_TO_OTP = "otp_store:session_id_to_otp:";

    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> store;
    private final Config config;

    public RedisOTPStore(StringRedisTemplate redisTemplate, ValueOperations<String, String> store, Config config) {
        this.redisTemplate = redisTemplate;
        this.store = redisTemplate.opsForValue();
        this.config = new Config(config.cookieName(), config.cookieDomain(), config.cookieMaxAge());
    }

    @Override
    public GenerationResult generate(HttpServletResponse req) {
        return null;
    }

    @Override
    public boolean validate(String otp, HttpServletRequest req) {
        return false;
    }

    @Override
    public String getSessionId(HttpServletRequest request) {
        Cookie sessionCookie = Arrays.stream(request.getCookies())
                .filter(item -> config.cookieName().equals(item.getName()))
                .findFirst()
                .orElseThrow(() -> new SessionCookieNotFoundException("error.session.cookie.not.found"));

        return sessionCookie.getValue();
    }

    private String generateSessionId() {
        UUID uuid = UUID.randomUUID();
        String salt = RandomStringUtils.randomAlphanumeric(8);
        return new String(Hex.encode(CryptoUtils.pbkdf(
                uuid.toString(),
                salt.getBytes(StandardCharsets.UTF_8),
                256,
                2048
        )));
    }
}
