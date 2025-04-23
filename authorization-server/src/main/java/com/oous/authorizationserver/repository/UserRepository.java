package com.oous.authorizationserver.repository;

import com.oous.authorizationserver.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.StyledEditorKit;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);
}
