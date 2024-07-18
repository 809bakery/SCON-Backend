package com.example.scon.domain.oven.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "OVEN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Oven {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OVEN_ID")
    private Long ovenId;

    @Column(name = "OVEN_NAME", nullable = false)
    private String ovenName;

    @Column(name = "OVEN_DETAIL")
    private String ovenDetail;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "FOLLOW_COUNT")
    private int followCount = 0; // 팔로워 수

    @CreatedDate
    @Column(name = "CREATED_AT",updatable = false)
    private LocalDateTime createdAt;
}
