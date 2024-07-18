package com.example.scon.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "IMAGE")
    private String image; // 프로필 사진

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "REPORT_COUNT")
    private int reportCount; // 신고당한 횟수

    @Column(name = "ROLE", nullable = false)
    private Role role;

    @CreatedDate
    @Column(name = "CREATED_AT",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "REFRESHTOKEN")
    private String refreshToken;

    public void addRole() { this.role = Role.SCONEE; }
    public void upgradeRole() { this.role = Role.OVENER; }
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }
    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public void destroyRefreshToken(){
        this.refreshToken = null;
    }
    public void updatePassword(String newPassword){ this.password = newPassword; }
}