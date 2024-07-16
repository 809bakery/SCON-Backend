package com.example.scon.domain.member.dto.request;

import com.example.scon.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String email;

    private String password;

    private String nickname;

    private String image;

    public Member toEntity() {
        return Member.builder().
                email(email).password(password).nickname(nickname).image(image)
                .build();
    }
}