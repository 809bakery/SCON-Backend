package com.example.scon.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRequest {

    private String code;

    private String client_id;

    private String client_secret;

    private String redirect_uri;

    private String grant_type;

}
