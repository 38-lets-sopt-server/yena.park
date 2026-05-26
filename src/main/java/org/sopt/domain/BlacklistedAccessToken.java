package org.sopt.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlacklistedAccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 1000)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private BlacklistedAccessToken(String token, LocalDateTime expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static BlacklistedAccessToken of(String token, LocalDateTime expiresAt) {
        return new BlacklistedAccessToken(token, expiresAt);
    }
}