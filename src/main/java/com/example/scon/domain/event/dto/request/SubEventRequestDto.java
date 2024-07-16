package com.example.scon.domain.event.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubEventRequestDto {
    private Long mainEventId;

    private Integer episodeNumber;

    private LocalDateTime time;
}
