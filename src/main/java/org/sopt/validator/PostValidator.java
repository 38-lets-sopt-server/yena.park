package org.sopt.validator;

import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("제목은 50자 이하여야 합니다!");
        }
    }

    public void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다!");
        }
    }

    public void validatePost(String title, String content) {
        validateTitle(title);
        validateContent(content);
    }
}
