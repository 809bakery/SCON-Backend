package com.example.scon.domain.oven.entity;

import com.example.scon.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "OVEN_MEMBER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OvenMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OVEN_MEMBER_ID")
    private Long id;

    @Column(name = "ROLE", nullable = false)
    private OvenRole role;

    @CreatedDate
    @Column(name = "CREATED_AT",updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "OVEN_ID")
    private Oven oven;

}
