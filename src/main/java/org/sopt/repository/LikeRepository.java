package org.sopt.repository;

import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndPost(User user, Post post);
    long countByPost(Post post);

    void deleteByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndPost(User user, Post post);
}
