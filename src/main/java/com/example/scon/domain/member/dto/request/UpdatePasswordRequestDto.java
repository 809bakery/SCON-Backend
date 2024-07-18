package com.example.scon.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
public class UpdatePasswordRequestDto {
    @NotBlank
    private String currentPassword;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,15}$",
            message = "비밀번호는 8자 이상 15자 이내로 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String newPassword;
}
