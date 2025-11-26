package com.jandy.codeFolio.domain.application;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByPostAndUser(Post post, User user);
    long countByPostAndStatus(Post post, ApplicationStatus status);
    List<Application> findAllByPost(Post post);

    @Query("SELECT a.post.id FROM Application a WHERE a.user.id = :userId")
    Page<Long> findPostIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
