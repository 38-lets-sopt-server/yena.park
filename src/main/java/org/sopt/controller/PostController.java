package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.domain.BoardType;
import org.springframework.web.bind.annotation.RequestParam;
import org.sopt.common.code.SuccessStatus;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @Operation(
            summary = "게시글 작성",
            description = "새로운 게시글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        SuccessStatus status = SuccessStatus.POST_CREATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Operation(summary = "게시글 전체 조회", description = "게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @Parameter(description = "페이지 번호", example = "0")
            @RequestParam int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam int size
    ) {
        List<PostResponse> response = postService.getAllPosts(page,size);
        SuccessStatus status = SuccessStatus.POST_READ_ALL_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        SuccessStatus status = SuccessStatus.POST_READ_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Operation(summary = "게시판 타입별 게시글 조회", description = "FREE, HOT, SECRET 게시판 타입별로 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 타입 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 게시판 타입")
    })
    @GetMapping("/boards/{boardType}")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostsByBoardType(
            @PathVariable BoardType boardType
    ) {
        List<PostResponse> response = postService.getPostsByBoardType(boardType);
        SuccessStatus status = SuccessStatus.POST_READ_ALL_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, response));
    }

    @Operation(summary = "게시글 수정", description = "게시글 제목 또는 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);
        SuccessStatus status = SuccessStatus.POST_UPDATE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, null));
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. deletedAt이 기록됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        SuccessStatus status = SuccessStatus.POST_DELETE_SUCCESS;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.of(status, null));
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<BaseResponse<Void>> addLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.addLikeWithRetry(postId, userId);
        return ResponseEntity.ok(BaseResponse.of(SuccessStatus.POST_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<BaseResponse<Void>> removeLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.removeLikeWithRetry(postId, userId);
        return ResponseEntity.ok(BaseResponse.of(SuccessStatus.POST_UPDATE_SUCCESS, null));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<PostResponse>>> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String nickname
    ) {
        List<PostResponse> response = postService.searchPosts(keyword, nickname);
        return ResponseEntity.ok(
                BaseResponse.of(SuccessStatus.POST_READ_ALL_SUCCESS, response)
        );
    }
}