package org.sopt.dto.response;

public record GoogleTokenResponse(
        String access_token,
        String token_type,
        Integer expires_in,
        String scope,
        String id_token
) {
}