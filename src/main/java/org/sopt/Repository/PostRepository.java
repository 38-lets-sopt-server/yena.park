package org.sopt.Repository;

import java.util.ArrayList;
import java.util.List;

class PostRepository {
    private final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public Long generateId() {
        return nextId++;
    }
}