package com.oous.authorizationserver.component;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OtpStore {

    record GenerationResult(String sessionId, String otp) {}

    record Config(String cookieName, String cookieDomain, int cookieMaxAge) {}

    GenerationResult generate(HttpServletResponse req);

    boolean validate(String otp, HttpServletRequest req);

    String getSessionId(HttpServletRequest response);
}
