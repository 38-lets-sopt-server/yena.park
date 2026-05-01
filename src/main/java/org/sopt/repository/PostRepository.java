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
public interface PostRepository extends JpaRepository<Post, Long> ,PostRepositoryCustom{
    List<Post> findAllByBoardType(BoardType boardType);
    @Query(
            value = """
            SELECT new org.sopt.dto.response.PostResponse(
                p.id,
                p.title,
                p.content,
                u.nickname,
                p.createdAt,
                p.boardType,
                COUNT(l.id)
            )
            FROM Post p
            JOIN p.user u
            LEFT JOIN Like l ON l.post = p
            GROUP BY p.id, p.title, p.content, u.nickname, p.createdAt, p.boardType
            ORDER BY p.createdAt DESC
        """,
            countQuery = """
            SELECT COUNT(p)
            FROM Post p
        """
    )
    Page<PostResponse> findAllWithLikeCount(Pageable pageable);

    @Query("""
    SELECT p
    FROM Post p
    JOIN FETCH p.user
    WHERE p.title LIKE %:keyword%
""")
    List<Post> searchByTitle(@Param("keyword") String keyword);
}