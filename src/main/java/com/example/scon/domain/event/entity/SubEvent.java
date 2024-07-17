package com.example.scon.domain.event.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SubEvent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_EVENT_ID", nullable = false)
    private MainEvent mainEvent;

    @Column(name = "EPISODE_NUMBER", nullable = false)
    private Integer episodeNumber;

    @Column(name = "TIME", nullable = false)
    private LocalDateTime time;

}
