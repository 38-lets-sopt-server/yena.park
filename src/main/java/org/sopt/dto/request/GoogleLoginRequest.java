package org.sopt.dto.request;

public record GoogleLoginRequest(
        String code,
        String redirectUri
) {
}