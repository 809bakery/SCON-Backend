package com.example.scon.domain.board.dto.request;

import com.example.scon.domain.board.entity.EventBoard;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardRequestDto {

    @NotBlank
    private String content;

    public EventBoard toEntity() {
        return EventBoard.builder().
                content(content)
                .build();
    }

}

