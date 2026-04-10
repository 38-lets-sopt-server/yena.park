package org.sopt.controller;

import org.sopt.service.PostService;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            return postService.createPost(request);
        } catch (IllegalArgumentException e) {
            return new CreatePostResponse(null, "🚫 " + e.getMessage());
        }
    }
}
