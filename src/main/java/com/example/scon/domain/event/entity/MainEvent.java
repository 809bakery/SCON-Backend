package com.example.scon.domain.event.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

//    @Column(name = "ovenID", nullable = false)
//    private int ovenId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ovenId", nullable = false)
//    private Oven oven;

    @Column(name = "location", length = 200, nullable = false)
    private String location;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "detail", length = 2000, nullable = false)
    private String detail;

    @Column(name = "poster", length = 300)
    private String poster;

    @Column(name = "headCount", columnDefinition = "integer default 0")
    private Integer headCount;

    @Column(name = "episodeAmount", nullable = false)
    private Integer episodeAmount;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "runningTime", nullable = false)
    private Integer runningTime;

    @Column(name = "createdAt", columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

}
