package org.sopt.dto.response;

public record ErrorResponse(
        String code,
        String message
) {
}
