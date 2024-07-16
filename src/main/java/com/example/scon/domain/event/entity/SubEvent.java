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
    @JoinColumn(name = "mainEventId", nullable = false)
    private MainEvent mainEvent;

    @Column(name = "episodeNumber", nullable = false)
    private Integer episodeNumber;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

}
