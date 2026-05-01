package org.sopt.repository;

import org.sopt.dto.response.PostResponse;
import java.util.List;

public interface PostRepositoryCustom {

    List<PostResponse> searchPosts(String keyword, String nickname);
}