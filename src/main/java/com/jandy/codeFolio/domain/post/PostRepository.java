package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.skills WHERE p.id IN :ids")
    List<Post> findPostsWithSkillsByIds(@Param("ids") List<Long> ids);

}
