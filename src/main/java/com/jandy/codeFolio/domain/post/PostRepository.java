package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);

}
