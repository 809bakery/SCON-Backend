package com.example.scon.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverAccountProfileFullResponse {

    private Response response;

    @Data
    public static class Response {
        private String id;
        private String nickname;
        private String profile_image;
        private String email;
        private String name;
    }
}
