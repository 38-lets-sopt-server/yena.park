package org.sopt.dto.response;

/**
 * Google OAuth userinfo API 응답 DTO
 *
 * sub: Google 계정의 고유 식별자, 서비스 내 providerId로 사용
 * picture: Google 프로필 이미지 URL, 사용자 프로필 이미지로 활용 가능
 */

public record GoogleUserInfoResponse(
        String sub,
        String email,
        String name,
        String picture

        //TODO sub랑 picture쓰는 이유
) {
}