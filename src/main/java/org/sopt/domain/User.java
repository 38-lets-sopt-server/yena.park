package org.sopt.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // "user"는 SQL 예약어라 테이블명을 변경해요
public class User {

    @Id //PK 붙이기
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 ID
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String password;

    protected User() {}

    public User(String nickname, String email,String password) {
        this.nickname = nickname;
        this.email = email;
        this.password =password;
    }

    public Long getId() {
        return this.id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public static User googleUser(String nickname, String email) {
        return new User(nickname, email, null);
    }
}
