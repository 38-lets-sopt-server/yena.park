package org.sopt.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.sopt.domain.BoardType;
import org.sopt.dto.response.PostResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final EntityManager entityManager;

    public PostRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PostResponse> searchPosts(String keyword, String nickname) {
        StringBuilder sql = new StringBuilder("""
            SELECT
                p.id,
                p.title,
                p.content,
                u.nickname,
                p.created_at,
                p.board_type,
                COUNT(pl.id) AS like_count
            FROM post p
            JOIN users u ON p.user_id = u.id
            LEFT JOIN post_likes pl ON pl.post_id = p.id
            WHERE p.deleted_at IS NULL
        """);

        if (keyword != null && !keyword.isBlank()) {
            sql.append("""
                AND MATCH(p.title) AGAINST (:keyword IN NATURAL LANGUAGE MODE)
            """);
        }

        if (nickname != null && !nickname.isBlank()) {
            sql.append("""
                AND u.nickname = :nickname
            """);
        }

        sql.append("""
            GROUP BY p.id, p.title, p.content, u.nickname, p.created_at, p.board_type
            ORDER BY p.created_at DESC
        """);

        Query query = entityManager.createNativeQuery(sql.toString());

        if (keyword != null && !keyword.isBlank()) {
            query.setParameter("keyword", keyword);
        }

        if (nickname != null && !nickname.isBlank()) {
            query.setParameter("nickname", nickname);
        }

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(row -> new PostResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        toLocalDateTime(row[4]),
                        BoardType.valueOf((String) row[5]),
                        ((Number) row[6]).longValue()
                ))
                .toList();
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }

        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime;
        }

        throw new IllegalArgumentException("지원하지 않는 날짜 타입입니다: " + value.getClass());
    }
}