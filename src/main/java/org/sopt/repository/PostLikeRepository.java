package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostLike;
import org.sopt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserAndPost(User user, Post post);
    long countByPost(Post post);

    void deleteByUserAndPost(User user, Post post);

    Optional<PostLike> findByUserAndPost(User user, Post post);
}
