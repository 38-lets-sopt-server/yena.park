package org.sopt.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "post_likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "post_id"})
        }
)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected PostLike() {
    }

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}