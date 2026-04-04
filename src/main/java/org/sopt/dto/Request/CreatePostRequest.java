package org.sopt.dto.Request;

// 게시글 작성 요청 (클라이언트 → 서버)
class CreatePostRequest {
    String title;
    String content;
    String author;

    public CreatePostRequest(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}

