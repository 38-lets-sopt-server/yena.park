package org.sopt.service;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PostService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request){
            // 글쓰기 화면설계서: 제목은 필수, 최대 50자
            postValidator.validatePost(request.getTitle(), request.getContent());

            String createdAt = java.time.LocalDateTime.now().toString();

            Post post = new Post(
                    postRepository.generateId(),
                    request.getTitle(),
                    request.getContent(),
                    request.getAuthor(),
                    createdAt
            );

            postRepository.save(post);

            return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체
    // 자유게시판 목록 화면에서 호출돼요
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .toList();
    }

    // READ - 단건
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return PostResponse.from(post);
    }

    // UPDATE
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public void updatePost(Long id, UpdatePostRequest request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (request.title() != null) {
            postValidator.validateTitle(request.title());
            post.updateTitle(request.title());
        }

        if (request.content() != null) {
            postValidator.validateContent(request.content());
            post.updateContent(request.content());
        }
    }

    // DELETE
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException(id);
        }
    }
}