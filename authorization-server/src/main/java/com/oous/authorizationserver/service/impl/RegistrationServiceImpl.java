package com.oous.authorizationserver.service.impl;

import com.oous.authorizationserver.domain.api.RegistrationReq;
import com.oous.authorizationserver.domain.response.response.information.InformationException;
import com.oous.authorizationserver.service.RegistrationService;
import com.oous.authorizationserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    @Override
    public void register(RegistrationReq req, HttpServletResponse response) {
        if (userService.existByEmail(req.getEmail())){
            throw InformationException.builder("error.json.processing").build();
        }
    }

    @Override
    public void checkOtp(String otp, HttpServletRequest request) {

    }
}
