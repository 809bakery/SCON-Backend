package com.example.scon.domain.member.controller;

import com.example.scon.domain.member.dto.request.SignupRequestDto;
import com.example.scon.domain.member.service.MemberService;
import com.example.scon.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "스콘 회원가입", description = "스콘 회원가입")
    @PostMapping("/sign-up")
    //@ResponseStatus(HttpStatus.OK)
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
    }

    @Operation(summary = "스콘 로그아웃", description = "스콘 로그아웃 시, refreshToken 삭제")
    @GetMapping("/log-out")
    public void logout() {
        memberService.logout(SecurityUtil.getLoginUsername());
    }

    @GetMapping("/nickname/{nickname}")
    public void dupNicknameCheck(@PathVariable("nickname") String nickname) {
        memberService.dupNicknameCheck(nickname);
    }

    @GetMapping("/test")
    public String test() {
        return SecurityUtil.getLoginUsername();
    }
}
