package com.example.scon.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverAccessTokenResponse {
    private String access_token;

    private int expires_in;

    private String scope;

    private String token_type;

    private String id_token;
}
