package com.example.scon.domain.auth.dto.response;

import lombok.Data;

@Data
public class KakaoAccountProfileResponse {
    private Long id;
    private String nickname;
    //    private String email;
    private String profile_image;
    private String account_email;

    public KakaoAccountProfileResponse(Long id, String nickname, String profile_image, String account_email) {
        this.id = id;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.account_email = account_email;
    }

}