package org.sopt.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.domain.BoardType;

// 게시글 작성 요청 (클라이언트 → 서버)
public record CreatePostRequest (
        @Schema(description = "게시글 제목", example = "오늘 학식 뭐임")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 50, message = "제목은 50자를 초과할 수 없습니다.")
        String title,

        @Schema(description = "게시글 내용", example = "돈까스래")
        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @Schema(description = "사용자 ID", example = "1")
        @NotNull(message = "사용자 ID는 필수입니다.")
        Long userId,

        @Schema(
                description = "게시판 타입 (FREE, HOT, SECRET)",
                example = "FREE"
        )
        @NotNull(message = "게시판 타입은 필수입니다.")
        BoardType boardType
){}