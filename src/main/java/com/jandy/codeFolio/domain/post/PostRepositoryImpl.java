package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.present.post.dto.PostSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jandy.codeFolio.domain.post.QPost.post;
import static com.jandy.codeFolio.domain.skill.QSkill.skill;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findPostsByConditions(PostSearchCondition condition, Pageable
            pageable) {

        BooleanBuilder builder = createWhereCondition(condition);

        JPAQuery<Post> query = queryFactory
                .selectFrom(post)
                .leftJoin(post.skills, skill)
                .where(builder)
                .groupBy(post.id);

        if (condition.getSortType() != null) {
            switch (condition.getSortType()) {
                case DEADLINE_ASC:
                    query.orderBy(post.endDate.asc());
                    break;
                case CREATED_DESC:
                    query.orderBy(post.createdAt.desc());
                    break;
            }
        } else {
            query.orderBy(post.createdAt.desc());
        }

        QueryResults<Post> results = query.fetchResults();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.countDistinct())
                .from(post)
                .leftJoin(post.skills, skill)
                .where(builder);

        List<Post> content = results.getResults();
        Long total = countQuery.fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    private BooleanBuilder createWhereCondition(PostSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getCapacity() != null) {
            builder.and(post.capacity.eq(condition.getCapacity()));
        }

        if (condition.getSkillIds() != null && !condition.getSkillIds().isEmpty()) {
            builder.and(skill.id.in(condition.getSkillIds()));
        }

        return builder;
    }
}