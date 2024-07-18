package com.example.scon.domain.event.dto.request;

import com.example.scon.domain.event.entity.Category;
import com.example.scon.domain.event.entity.MainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainEventRequestDto {

    private String location;

    private String title;

    private Category category;

    private String detail;

    private String poster;

    private Integer headCount;

    private Integer episodeAmount;

    private Integer cost;

    private Integer runningTime;

    private String ovenName;

    public MainEvent toEntity(){
        return MainEvent.builder()
                //.oven(oven)
                .location(location)
                .title(title)
                .category(category)
                .detail(detail)
                .poster(poster)
                .headCount(headCount)
                .episodeAmount(episodeAmount)
                .cost(cost)
                .runningTime(runningTime)
                .build();
    }
}
