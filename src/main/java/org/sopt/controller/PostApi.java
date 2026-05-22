package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.common.response.BaseResponse;
import org.sopt.domain.BoardType;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
public interface PostApi {

    @Operation(
            summary = "게시글 작성",
            description = "새로운 게시글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패")
    })
    @PostMapping
    ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @RequestParam Long userId,
            @Valid @RequestBody CreatePostRequest request
    );

    @Operation(
            summary = "게시글 목록 조회",
            description = "게시글 목록을 조회합니다. boardType을 전달하면 게시판 타입별로 필터링합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 게시판 타입")
    })
    @GetMapping
    ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @Parameter(description = "페이지 번호", example = "0")
            @RequestParam int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam int size,

            @Parameter(description = "게시판 타입", example = "FREE")
            @RequestParam(required = false) BoardType boardType
    );

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id
    );


    @Operation(summary = "게시글 수정", description = "게시글 제목 또는 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PatchMapping("/{id}")
    ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request
    );

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. deletedAt이 기록됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable Long id
    );

    @Operation(summary = "게시글 좋아요 추가", description = "특정 사용자가 게시글에 좋아요를 누릅니다.")
    @PostMapping("/{postId}/likes")
    ResponseEntity<BaseResponse<Void>> addLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    );

    @Operation(summary = "게시글 좋아요 취소", description = "특정 사용자가 게시글 좋아요를 취소합니다.")
    @DeleteMapping("/{postId}/likes")
    ResponseEntity<BaseResponse<Void>> removeLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    );

    @Operation(summary = "게시글 검색", description = "키워드 또는 작성자 닉네임으로 게시글을 검색합니다.")
    @GetMapping("/search")
    ResponseEntity<BaseResponse<List<PostResponse>>> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String nickname
    );
}