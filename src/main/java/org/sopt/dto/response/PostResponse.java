package org.sopt.dto.response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt,
        BoardType boardType,
        Long likeCount
) {
    public static PostResponse from(Post post,Long likeCount) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt(),
                post.getBoardType(),
                likeCount
        );
    }
    public static PostResponse from(Post post) {
        return from(post, 0L);
    }
}