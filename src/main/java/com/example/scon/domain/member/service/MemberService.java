package com.example.scon.domain.member.service;

import com.example.scon.domain.auth.dto.response.GoogleAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.KakaoAccountProfileResponse;
import com.example.scon.domain.member.dto.request.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    public void signup(SignupRequestDto request);

    public void logout(String email);

    public void dupNicknameCheck(String nickname);
}
