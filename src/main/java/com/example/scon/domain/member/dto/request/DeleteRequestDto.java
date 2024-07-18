package com.example.scon.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteRequestDto {
    @NotBlank
    private String password;
}
