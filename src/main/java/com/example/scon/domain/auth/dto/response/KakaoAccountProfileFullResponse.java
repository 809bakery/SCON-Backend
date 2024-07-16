package com.example.scon.domain.auth.dto.response;

import lombok.Data;

@Data
public class KakaoAccountProfileFullResponse {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String account_email;
    }
    @Data
    public static class KakaoAccount {
        private String email;

    }
}