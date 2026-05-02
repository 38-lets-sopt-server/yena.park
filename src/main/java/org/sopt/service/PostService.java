package org.sopt.service;
import org.sopt.common.code.ErrorStatus;
import org.sopt.domain.BoardType;
import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.NotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.repository.LikeRepository;
import org.sopt.exception.ConflictException;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;


    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            LikeRepository likeRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }


    // CREATE
    @Transactional  // 저장 → DB 변경 발생 → 트랜잭션 커밋 시 반영
    public CreatePostResponse createPost(CreatePostRequest request){
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND));

        Post post = new Post(
                request.title(),
                request.content(),
                user,
                request.boardType()
            );

            postRepository.save(post);

            return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체
    // 자유게시판 목록 화면에서 호출돼요
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(int page, int size) {
        return postRepository.findAllWithLikeCount(PageRequest.of(page, size))
                .getContent();
    }

    // READ - 단건
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    @Transactional(readOnly = true)  // 조회 전용 → 더티 체킹 안 함 → 성능 최적화
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.POST_NOT_FOUND));
        long likeCount = likeRepository.countByPost(post);
        return PostResponse.from(post, likeCount);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByBoardType(BoardType boardType) {
        return postRepository.findAllByBoardType(boardType)
                .stream()
                .map(post -> {
                    long likeCount = likeRepository.countByPost(post);
                    return PostResponse.from(post, likeCount);
                })
                .toList();
    }

    // UPDATE
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    @Transactional  // 변경 → 더티 체킹으로 save() 없이 자동 UPDATE
    public void updatePost(Long id, UpdatePostRequest request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.POST_NOT_FOUND));

        if (request.title() != null) {
            post.updateTitle(request.title());
        }

        if (request.content() != null) {
            post.updateContent(request.content());
        }
    }

    // DELETE
    // 게시글 상세에서 삭제를 누르면 호출돼요
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.POST_NOT_FOUND));

        postRepository.delete(post);
    }
    //Like
    //좋아요 추가
    @Transactional
    public void addLike(Long postId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.POST_NOT_FOUND));

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new ConflictException(ErrorStatus.LIKE_ALREADY_EXISTS);
        }

        Like like = new Like(user, post);
        likeRepository.save(like);

        post.increaseLikeCount();
    }

    //좋아요 취소
    @Transactional
    public void removeLike(Long postId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.POST_NOT_FOUND));

        if (!likeRepository.existsByUserAndPost(user, post)) {
            throw new NotFoundException(ErrorStatus.LIKE_NOT_FOUND);
        }

        likeRepository.deleteByUserAndPost(user, post);
        post.decreaseLikeCount();
    }

    public void addLikeWithRetry(Long postId, Long userId) {
        int maxRetry = 3;

        for (int i = 0; i < maxRetry; i++) {
            try {
                addLike(postId, userId);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                if (i == maxRetry - 1) {
                    throw e;
                }
            }
        }
    }

    public void removeLikeWithRetry(Long postId, Long userId) {
        int maxRetry = 3;

        for (int i = 0; i < maxRetry; i++) {
            try {
                removeLike(postId, userId);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                if (i == maxRetry - 1) {
                    throw e;
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public List<PostResponse> searchPosts(String keyword, String nickname) {
        return postRepository.searchPosts(keyword, nickname);
    }

}