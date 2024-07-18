package com.example.scon.domain.oven.dto.response;

import com.example.scon.domain.oven.entity.Oven;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OvenListResponse {
    private Long ovenId;
    private String ovenName;
    private String ovenDetail;
    private String image;
    private int followCount;

    public Oven toEntity() {
        return Oven.builder().
                ovenId(ovenId).ovenName(ovenName).ovenDetail(ovenDetail).image(image).followCount(followCount)
                .build();
    }
}
