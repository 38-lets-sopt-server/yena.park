package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.common.code.SuccessStatus;
import org.sopt.common.response.BaseResponse;
import org.sopt.domain.BoardType;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController implements PostApi {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @RequestParam Long userId,
            @Valid @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request, userId);
        SuccessStatus status = SuccessStatus.POST_CREATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Override
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) BoardType boardType
    ) {
        List<PostResponse> response = postService.getAllPosts(page, size, boardType);
        SuccessStatus status = SuccessStatus.POST_READ_ALL_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Override
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        SuccessStatus status = SuccessStatus.POST_READ_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);
        SuccessStatus status = SuccessStatus.POST_UPDATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        SuccessStatus status = SuccessStatus.POST_DELETE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> addLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.addLikeWithRetry(postId, userId);
        SuccessStatus status = SuccessStatus.POST_UPDATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> removeLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.removeLikeWithRetry(postId, userId);
        SuccessStatus status = SuccessStatus.POST_UPDATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status));
    }

    @Override
    public ResponseEntity<BaseResponse<List<PostResponse>>> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String nickname
    ) {
        List<PostResponse> response = postService.searchPosts(keyword, nickname);
        SuccessStatus status = SuccessStatus.POST_READ_ALL_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }
}