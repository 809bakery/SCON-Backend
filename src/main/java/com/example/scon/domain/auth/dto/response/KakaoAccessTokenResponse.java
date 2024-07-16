package com.example.scon.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoAccessTokenResponse {

    private String access_token;
    private int expires_in;
//    private String scope;
    private String token_type;
//    private String id_token;
    private String refresh_token;
    private String refresh_token_expires_in;
}
