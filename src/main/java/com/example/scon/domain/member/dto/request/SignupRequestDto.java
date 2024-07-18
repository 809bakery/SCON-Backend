package com.example.scon.domain.member.dto.request;

import com.example.scon.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,15}$",
            message = "비밀번호는 8자 이상 15자 이내로 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$",
            message = "닉네임은 2자 이상 8자 이하, 영어 또는 숫자 또는 한글만 입력 가능합니다.")
    private String nickname;


    private String image;

    public Member toEntity() {
        return Member.builder().
                email(email).password(password).nickname(nickname).image(image)
                .build();
    }
}