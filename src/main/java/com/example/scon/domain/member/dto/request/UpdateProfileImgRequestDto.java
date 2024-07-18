package com.example.scon.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileImgRequestDto {
    @NotBlank
    private String newProfileImg;
}
