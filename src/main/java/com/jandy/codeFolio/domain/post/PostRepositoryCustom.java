package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.present.post.dto.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findPostsByConditions(PostSearchCondition condition, Pageable pageable);
}
