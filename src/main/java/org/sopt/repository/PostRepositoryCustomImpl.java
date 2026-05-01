package org.sopt.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.sopt.domain.QLike;
import org.sopt.domain.QPost;
import org.sopt.domain.QUser;
import org.sopt.dto.response.PostResponse;

import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QPost post = QPost.post;
    QUser user = QUser.user;
    QLike like = QLike.like;

    @Override
    public List<PostResponse> searchPosts(String keyword, String nickname) {
        return queryFactory
                .select(Projections.constructor(
                        PostResponse.class,
                        post.id,
                        post.title,
                        post.content,
                        user.nickname,
                        post.createdAt,
                        post.boardType,
                        like.id.count()
                ))
                .from(post)
                .join(post.user, user)
                .leftJoin(like).on(like.post.eq(post))
                .where(
                        titleContains(keyword),
                        nicknameEq(nickname)
                )
                .groupBy(
                        post.id,
                        post.title,
                        post.content,
                        user.nickname,
                        post.createdAt,
                        post.boardType
                )
                .fetch();
    }

    private BooleanExpression titleContains(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return post.title.contains(keyword);
    }

    private BooleanExpression nicknameEq(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return null;
        }
        return user.nickname.eq(nickname);
    }
}