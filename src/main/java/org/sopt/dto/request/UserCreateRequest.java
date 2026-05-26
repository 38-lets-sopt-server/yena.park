package org.sopt.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(

        @NotBlank
        String nickname,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
