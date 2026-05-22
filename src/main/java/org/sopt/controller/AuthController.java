package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.GoogleLoginRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.common.code.SuccessStatus;
import org.sopt.dto.response.TokenResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        TokenResponse tokens = authService.login(email, password);

        return ResponseEntity.ok(
                BaseResponse.of(SuccessStatus.LOGIN_SUCCESS, tokens)
        );
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long userId = Long.parseLong(authentication.getName());
        UserResponse user = authService.getUserById(userId);

        return ResponseEntity.ok(
                BaseResponse.of(SuccessStatus.USER_INFO_READ_SUCCESS, user)
        );

    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            Authentication authentication,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        Long userId = Long.parseLong(authentication.getName());

        String accessToken = authorizationHeader.substring("Bearer ".length()).trim();

        authService.logout(userId, accessToken);

        return ResponseEntity.ok(
                BaseResponse.of(SuccessStatus.LOGOUT_SUCCESS, null)
        );
    }

    @Operation(summary = "구글 인가 화면으로 리다이렉트")
    @GetMapping("/google/authorize")
    public void redirectToGoogle(
            @RequestParam String redirectUri,
            HttpServletResponse response
    ) throws IOException {
        String googleAuthorizeUrl = authService.getGoogleAuthorizeUrl(redirectUri);
        response.sendRedirect(googleAuthorizeUrl);
    }

    @Operation(summary = "구글 소셜 로그인")
    @PostMapping("/google")
    public ResponseEntity<BaseResponse<TokenResponse>> googleLogin(
            @RequestBody GoogleLoginRequest request
    ) {
        TokenResponse tokens = authService.googleLogin(
                request.code(),
                request.redirectUri()
        );

        return ResponseEntity.ok(
                BaseResponse.of(SuccessStatus.LOGIN_SUCCESS, tokens)
        );
    }

}