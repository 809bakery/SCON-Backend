package com.example.scon.domain.oven.controller;

import com.example.scon.domain.member.service.MemberService;
import com.example.scon.domain.oven.dto.response.OvenListResponse;
import com.example.scon.domain.oven.entity.Oven;
import com.example.scon.domain.oven.service.OvenService;
import com.example.scon.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/oven")
public class OvenController {

    private final MemberService memberService;
    private final OvenService ovenService;

    @GetMapping("/list")
    public Page<Oven> listOven(@RequestParam int page, @RequestParam int size) {
        String email = SecurityUtil.getLoginUsername();

        return ovenService.listOven(email, page, size);

    }
}
