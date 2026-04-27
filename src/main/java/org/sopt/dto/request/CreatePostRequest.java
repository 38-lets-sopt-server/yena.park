package org.sopt.dto.request;
import org.sopt.domain.BoardType;

// 게시글 작성 요청 (클라이언트 → 서버)
public class CreatePostRequest {
    public String title;
    public String content;
    public String author;
    private BoardType boardType;

    public CreatePostRequest(String title, String content, String author,BoardType boardType) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.boardType = boardType;
    }
    public BoardType getBoardType() {
        return boardType;
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}

