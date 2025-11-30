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

    @Query("SELECT a.post.id, a.id FROM Application a WHERE a.user.id = :userId")
    List<Object[]> findApplicationDetailsByUserId(@Param("userId") Long userId);

    @Query("SELECT a.post.id, a.id FROM Application a WHERE a.user.id = :userId")
    Page<Object[]> findAppliedPostDetailsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT a.post.id, COUNT(a.id) FROM Application a WHERE a.post.id IN :postIds AND a.status != 'REJECTED' GROUP BY a.post.id")
    List<Object[]> countApplicationsByPostIds(@Param("postIds") List<Long> postIds);
}
