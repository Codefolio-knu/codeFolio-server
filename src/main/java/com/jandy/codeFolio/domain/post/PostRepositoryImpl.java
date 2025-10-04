package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.present.post.dto.PostSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

        addSortToQuery(query, pageable.getSort());


        List<Post> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.countDistinct())
                .from(post)
                .leftJoin(post.skills, skill)
                .where(builder);

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

    private void addSortToQuery(JPAQuery<?> query, Sort sort) {
        if (!sort.isEmpty()) {
            List<OrderSpecifier<?>> orders = new ArrayList<>();
            PathBuilder postPath = new PathBuilder(post.getType(), post.getMetadata());

            for (Sort.Order order : sort) {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                orders.add(new OrderSpecifier(direction, postPath.get(order.getProperty())));
            }
            query.orderBy(orders.toArray(new OrderSpecifier[0]));
        }
    }
}