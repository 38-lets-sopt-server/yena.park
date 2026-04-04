package org.sopt.dto.Response;

// 게시글 작성 응답 (서버 → 클라이언트)
class CreatePostResponse {
    Long id;
    String message;

    public CreatePostResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}