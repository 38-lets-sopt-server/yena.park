package org.sopt.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.BoardType;
import org.springframework.web.bind.annotation.RequestParam;
import org.sopt.common.code.SuccessStatus;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        SuccessStatus status = SuccessStatus.POST_CREATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.of(status, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<PostResponse> response = postService.getAllPosts(page,size);
        SuccessStatus status = SuccessStatus.POST_READ_ALL_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.of(status, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        SuccessStatus status = SuccessStatus.POST_READ_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.of(status, response));
    }

    @GetMapping("/boards/{boardType}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsByBoardType(
            @PathVariable BoardType boardType
    ) {
        List<PostResponse> response = postService.getPostsByBoardType(boardType);
        SuccessStatus status = SuccessStatus.POST_READ_ALL_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.of(status, response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);
        SuccessStatus status = SuccessStatus.POST_UPDATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.of(status, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        SuccessStatus status = SuccessStatus.POST_DELETE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.of(status, null));
    }
}