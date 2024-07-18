package com.example.scon.domain.board.entity;

import com.example.scon.domain.event.entity.MainEvent;
import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "EVENTBOARD")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENTBOARD_ID")
    private Long eventBoardId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "MAINEVENT_ID")
    private MainEvent mainEvent;

    @Column(name = "CATEGORY", nullable = false)
    private Category category;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;

}