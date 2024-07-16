package com.example.scon.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialNicknameAndProfileResponse {

    private boolean isExisted;

    private String nickname;

    private String image;

}
