package com.example.scon.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverAccountProfileResponse {

    private String id;
    private String nickname;
    private String profile_image;
    private String accountEmail;
    private String name;
    /*
    id, nickname, profile_image, accountEmail, name
     */
}
