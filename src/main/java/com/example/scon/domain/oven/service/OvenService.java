package com.example.scon.domain.oven.service;

import com.example.scon.domain.oven.dto.response.OvenListResponse;
import com.example.scon.domain.oven.entity.Oven;
import org.springframework.data.domain.Page;

public interface OvenService {

    Page<Oven> listOven(String email, int page, int size);

}
