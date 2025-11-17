package com.jandy.codeFolio.domain.application;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByPostAndUser(Post post, User user);
    long countByPostAndStatus(Post post, ApplicationStatus status);
    List<Application> findAllByPost(Post post);
}
