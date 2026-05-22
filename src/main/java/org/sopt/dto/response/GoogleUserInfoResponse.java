package org.sopt.dto.response;

public record GoogleUserInfoResponse(
        String sub,
        String email,
        String name,
        String picture

        //TODO sub랑 picture쓰는 이유
) {
}