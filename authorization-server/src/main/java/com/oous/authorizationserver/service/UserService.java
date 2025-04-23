package com.oous.authorizationserver.service;

import com.oous.authorizationserver.domain.api.RegistrationReq;
import com.oous.authorizationserver.domain.entity.UserEntity;

public interface UserService {

    UserEntity saveAndActivate(RegistrationReq req);

    boolean existByEmail(String email);
}
