package org.sopt.service;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.validator.PostValidator;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private List<Post> postList = new ArrayList<>(); // 임시 저장소 (나중에 DB로 교체됨)
    private Long nextId = 1L;
    private final PostValidator postValidator = new PostValidator();


    // CREATE ✅ 같이 구현
    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    public CreatePostResponse createPost(CreatePostRequest request){
            // 글쓰기 화면설계서: 제목은 필수, 최대 50자
            postValidator.validatePost(request.getTitle(), request.getContent());

            String createdAt = java.time.LocalDateTime.now().toString();
            Post post = new Post(nextId++, request.getTitle(), request.getContent(), request.getAuthor(), createdAt);
            postList.add(post);
            return new CreatePostResponse(post.getId(), "✅ 게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    // 자유게시판 목록 화면에서 호출돼요
    public void readAllPosts() {
        // TODO: postList가 비어있으면 "등록된 게시글이 없습니다." 출력
        // TODO: 비어있지 않으면 모든 게시글의 getInfo()를 순서대로 출력
        if (postList.isEmpty()) {
            System.out.println("등록된 게시글이 없습니다.");
            return;
        }
        for (Post post : postList) {
            System.out.println(post.getInfo());
            }
    }

    // READ - 단건 📝 과제
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    public void readPost(Long id) {
        // TODO: postList에서 id가 일치하는 게시글을 찾아 getInfo() 출력
        // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
        for (Post post : postList) {
            if (post.getId().equals(id)) {
                System.out.println(post.getInfo());
                return;
            }
        }
        throw new PostNotFoundException();
    }

    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public void updatePost(Long id, String newTitle, String newContent) {
            for (Post post : postList) {
                if (post.getId().equals(id)) {
                    postValidator.validatePost(newTitle, newContent);

                    post.update(newTitle, newContent);
                    System.out.println("게시글 수정 완료!");
                    return;
                }
            }
        throw new PostNotFoundException();

    }

    // DELETE 📝 과제
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public void deletePost(Long id) {
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId().equals(id)) {
                postList.remove(i);
                System.out.println("삭제 완료!");
                return;
            }
        }
        throw new PostNotFoundException();

    }
}