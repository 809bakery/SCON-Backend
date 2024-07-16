package com.example.scon.domain.auth.service;


import com.example.scon.domain.auth.dto.response.GoogleAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.KakaoAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.NaverAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.SocialNicknameAndProfileResponse;
import jakarta.servlet.http.HttpServletResponse;

import javax.security.auth.login.LoginException;

public interface OAuth2Service {

    public String requestGoogleAccessToken(String code) throws LoginException;

    public GoogleAccountProfileResponse requestGoogleAccountProfile(String accessToken);

    public SocialNicknameAndProfileResponse checkSignupGoogle(GoogleAccountProfileResponse googleAccountProfileResponse, HttpServletResponse response);

    public String requestKakaoAccessToken(String code) throws LoginException;

    public KakaoAccountProfileResponse requestKakaoAccountProfile(String accessToken);

    public SocialNicknameAndProfileResponse checkSignupKakao(KakaoAccountProfileResponse kakaoAccountProfileResponse, HttpServletResponse response);

    public String requestNaverAccessToken(String code) throws LoginException;

    public NaverAccountProfileResponse requestNaverAccountProfile(String accessToken);

    public SocialNicknameAndProfileResponse checkSignupNaver(NaverAccountProfileResponse naverAccountProfileResponse, HttpServletResponse response);
}
