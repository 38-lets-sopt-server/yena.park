package org.sopt.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.BlacklistedAccessToken;
import org.sopt.domain.RefreshToken;
import org.sopt.domain.User;
import org.sopt.dto.response.GoogleTokenResponse;
import org.sopt.dto.response.GoogleUserInfoResponse;
import org.sopt.dto.response.TokenResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.repository.BlacklistedAccessTokenRepository;
import org.sopt.repository.RefreshTokenRepository;
import org.sopt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final BlacklistedAccessTokenRepository blacklistedAccessTokenRepository;

    private final WebClient webClient;

    @Value("${oauth.google.client-id}")
    private String googleClientId;

    @Value("${oauth.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth.google.authorize-uri}")
    private String googleAuthorizeUri;

    @Value("${oauth.google.token-uri}")
    private String googleTokenUri;

    @Value("${oauth.google.user-info-uri}")
    private String googleUserInfoUri;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;



    public UserResponse loginWithCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return UserResponse.from(user);
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        UserResponse user = loginWithCredentials(email, password);

        String accessToken = jwtService.generateAccessToken(user.id(), user.email());
        String refreshToken = jwtService.generateRefreshToken(user.id());

        refreshTokenRepository.deleteByMemberId(user.id());
        refreshTokenRepository.save(
                RefreshToken.of(user.id(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return UserResponse.from(user);
    }

    @Transactional
    public void logout(Long userId, String accessToken) {
        refreshTokenRepository.deleteByMemberId(userId);

        LocalDateTime expiresAt = jwtService.getExpiresAt(accessToken);

        blacklistedAccessTokenRepository.save(
                BlacklistedAccessToken.of(accessToken, expiresAt)
        );
    }

    public String getGoogleAuthorizeUrl(String redirectUri) {
        return UriComponentsBuilder.fromUriString(googleAuthorizeUri)
                .queryParam("client_id", googleClientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email profile")
                .build()
                .toUriString();
    }

    @Transactional
    public TokenResponse googleLogin(String code, String redirectUri) {
        GoogleTokenResponse googleToken = webClient.post()
                .uri(googleTokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("code", code)
                        .with("client_id", googleClientId)
                        .with("client_secret", googleClientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("grant_type", "authorization_code"))
                .retrieve()
                .bodyToMono(GoogleTokenResponse.class)
                .block();

        if (googleToken == null || googleToken.access_token() == null) {
            throw new IllegalArgumentException("구글 Access Token 발급에 실패했습니다.");
        }

        GoogleUserInfoResponse googleUserInfo = webClient.get()
                .uri(googleUserInfoUri)
                .headers(headers -> headers.setBearerAuth(googleToken.access_token()))
                .retrieve()
                .bodyToMono(GoogleUserInfoResponse.class)
                .block();

        if (googleUserInfo == null || googleUserInfo.email() == null) {
            throw new IllegalArgumentException("구글 사용자 정보를 가져오지 못했습니다.");
        }

        User user = userRepository.findByEmail(googleUserInfo.email())
                .orElseGet(() -> userRepository.save(
                        User.googleUser(
                                googleUserInfo.name(),
                                googleUserInfo.email()
                        )
                ));

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        refreshTokenRepository.deleteByMemberId(user.getId());
        refreshTokenRepository.save(
                RefreshToken.of(user.getId(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }


}