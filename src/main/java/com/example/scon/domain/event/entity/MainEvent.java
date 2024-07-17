package com.example.scon.domain.event.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MainEvent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MainEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ovenId", nullable = false)
//    private Oven oven;

    @Column(name = "LOCATION", length = 200, nullable = false)
    private String location;

    @Column(name = "TITLE", length = 100, nullable = false)
    private String title;

    @Column(name = "CATEGORY", length = 50, nullable = false)
    private String category;

    @Column(name = "DETAIL", length = 2000, nullable = false)
    private String detail;

    @Column(name = "POSTER", length = 300)
    private String poster;

    @Column(name = "HEAD_COUNT", columnDefinition = "integer default 0")
    private Integer headCount;

    @Column(name = "EPISODE_AMOUNT", nullable = false)
    private Integer episodeAmount;

    @Column(name = "COST", nullable = false)
    private Integer cost;

    @Column(name = "RUNNING_TIME", nullable = false)
    private Integer runningTime;

    @Column(name = "CREATED_AT", columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "mainEvent", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<SubEvent> content;
}
