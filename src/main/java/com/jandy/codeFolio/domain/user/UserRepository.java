package com.jandy.codeFolio.domain.user;

import com.jandy.codeFolio.global.util.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGithubId(Long id);
    Optional<User> findByEmail(String email);
    Page<User> findAllByIsPublic(boolean isPublic, Pageable pageable);
    Page<User> findAllByIsPublicAndRole(boolean isPublic, Role role, Pageable pageable);
}
