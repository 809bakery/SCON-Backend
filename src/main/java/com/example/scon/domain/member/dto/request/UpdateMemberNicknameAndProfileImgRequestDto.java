package com.example.scon.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMemberNicknameAndProfileImgRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$",
            message = "닉네임은 2자 이상 8자 이하, 영어 또는 숫자 또는 한글만 입력 가능합니다.")
    private String nickname;

    private String image;
}
