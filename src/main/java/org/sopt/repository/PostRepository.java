package org.sopt.repository;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Page<Post> findAllByBoardType(BoardType boardType, Pageable pageable);

    @Query(
            value = """
            SELECT new org.sopt.dto.response.PostResponse(
                p.id,
                p.title,
                p.content,
                u.nickname,
                p.createdAt,
                p.boardType,
                COUNT(pl.id)
            )
            FROM Post p
            JOIN p.user u
            LEFT JOIN PostLike pl ON pl.post = p
            GROUP BY p.id, p.title, p.content, u.nickname, p.createdAt, p.boardType
            ORDER BY p.createdAt DESC
        """,
            countQuery = """
            SELECT COUNT(p)
            FROM Post p
        """
    )
    Page<PostResponse> findAllWithLikeCount(Pageable pageable);

    @Query(
            value = """
        SELECT *
        FROM post
        WHERE MATCH(title) AGAINST (:keyword IN NATURAL LANGUAGE MODE)
        ORDER BY created_at DESC
    """,
            nativeQuery = true
    )
    List<Post> searchByTitle(@Param("keyword") String keyword);
}