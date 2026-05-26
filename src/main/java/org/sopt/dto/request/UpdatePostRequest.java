package org.sopt.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(

        @Size(max = 50, message = "제목은 50자 이하로 입력해주세요.")
        String title,

        @Size(max = 1000, message = "내용은 1000자 이하로 입력해주세요.")
        String content
) {

    @AssertTrue(message = "수정할 제목 또는 내용을 입력해주세요.")
    public boolean hasAnyField() {
        return title != null || content != null;
    }

    @AssertTrue(message = "제목은 공백일 수 없습니다.")
    public boolean isTitleValid() {
        return title == null || !title.isBlank();
    }

    @AssertTrue(message = "내용은 공백일 수 없습니다.")
    public boolean isContentValid() {
        return content == null || !content.isBlank();
    }
}