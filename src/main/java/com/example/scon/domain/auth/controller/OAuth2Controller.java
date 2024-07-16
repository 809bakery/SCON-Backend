package com.example.scon.domain.auth.controller;

import com.example.scon.domain.auth.dto.request.Code;
import com.example.scon.domain.auth.dto.request.SocialCode;
import com.example.scon.domain.auth.dto.response.GoogleAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.KakaoAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.NaverAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.SocialNicknameAndProfileResponse;
import com.example.scon.domain.auth.service.OAuth2Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.security.auth.login.LoginException;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth/log-in/social")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @PostMapping("")
    public SocialNicknameAndProfileResponse requestSocialAccessToken(@RequestBody SocialCode request, HttpServletResponse response) throws LoginException {

        String social = request.getSocial();
        String code = request.getCode();
        log.info("Controller: " + social + " redirectUrl code: {}", code);

        SocialNicknameAndProfileResponse socialInfoResponse = new SocialNicknameAndProfileResponse();

        if(social.equals("google")) {
            String accessTokenGoogle = oAuth2Service.requestGoogleAccessToken(code);
            log.info("Controller: " + "accessTokenGoogle: {}", accessTokenGoogle);

            GoogleAccountProfileResponse googleAccountProfileResponse = oAuth2Service.requestGoogleAccountProfile(accessTokenGoogle);
            log.info("Controller: " + "googleAccountProfileResponse: {}", googleAccountProfileResponse);

            socialInfoResponse = oAuth2Service.checkSignupGoogle(googleAccountProfileResponse, response);

        } else if(social.equals("kakao")) {
            String accessTokenKakao = oAuth2Service.requestKakaoAccessToken(code);
            log.info("Controller: " + "accessTokenKakao: {}", accessTokenKakao);

            KakaoAccountProfileResponse kakaoAccountProfileResponse = oAuth2Service.requestKakaoAccountProfile(accessTokenKakao);
            log.info("Controller: " + "kakaoAccountProfileResponse: {}", kakaoAccountProfileResponse.toString());

            socialInfoResponse = oAuth2Service.checkSignupKakao(kakaoAccountProfileResponse, response);

        } else if(social.equals("naver")) {
            String accessTokenNaver = oAuth2Service.requestNaverAccessToken(code);
            log.info("Controller: " + "accessTokenNaver: {}", accessTokenNaver);

            NaverAccountProfileResponse naverAccountProfileResponse = oAuth2Service.requestNaverAccountProfile(accessTokenNaver);
            log.info("Controller: " + "naverAccountProfileResponse: {}", naverAccountProfileResponse.toString());

            socialInfoResponse = oAuth2Service.checkSignupNaver(naverAccountProfileResponse, response);

        } else {
            ;
        }

        return socialInfoResponse;
    }

//    // 아래부분 소셜 로그인 분리해서 구현했던 원본코드
//    @PostMapping("/google")
//    public SocialNicknameAndProfileResponse requestGoogleAccessToken(@RequestBody Code request, HttpServletResponse response) throws LoginException {
//
//        String code = request.getCode();
//        log.info("GOOGLE redirectUrl code: {}", code);
//
//        // 인가코드를 통해 액세스 토큰을 발급받음
//        String accessTokenGoogle = oAuth2Service.requestGoogleAccessToken(code);
//        log.info("accessTokenGoogle: {}", accessTokenGoogle);
//
//        // 액세스 토큰을 통해 구글 회원정보를 발급받음
//        GoogleAccountProfileResponse googleAccountProfileResponse = oAuth2Service.requestGoogleAccountProfile(accessTokenGoogle);
//        log.info("googleAccountProfileResponse: {}", googleAccountProfileResponse);
//
//        // 구글 회원정보에 담긴 이메일을 통해 가입된 회원인지 판단 필요
//        // 회원가입 진행 후 로그인 OR 로그인 -> check_signup 메서드를 통해 진행
//        SocialNicknameAndProfileResponse socialInfoResponse = oAuth2Service.checkSignupGoogle(googleAccountProfileResponse, response);
//
//        return socialInfoResponse;
//    }
//
//    @PostMapping("/kakao")
//    public SocialNicknameAndProfileResponse requestKakaoAccessToken(@RequestBody Code request, HttpServletResponse response) throws LoginException {
//
//        String code = request.getCode();
//        log.info("KAKAO redirectUrl code: {}", code);
//
//        // 인가코드를 통해 액세스 토큰을 발급받음
//        String accessTokenKakao = oAuth2Service.requestKakaoAccessToken(code);
//        log.info("accessTokenKakao: {}", accessTokenKakao);
//
//        // 액세스 토큰을 통해 카카오 회원정보를 발급받음
//        KakaoAccountProfileResponse kakaoAccountProfileResponse = oAuth2Service.requestKakaoAccountProfile(accessTokenKakao);
//        log.info("kakaoAccountProfileResponse: {}", kakaoAccountProfileResponse.toString());
//
//        // 카카오 회원정보에 담긴 이메일을 통해 가입된 회원인지 판단 필요
//        // 회원가입 진행 후 로그인 OR 로그인 -> check_signup 메서드를 통해 진행
//        SocialNicknameAndProfileResponse socialInfoResponse = oAuth2Service.checkSignupKakao(kakaoAccountProfileResponse, response);
//
//        return socialInfoResponse;
//    }
//
//    @PostMapping("/naver")
//    public SocialNicknameAndProfileResponse requestNaverAccessToken(@RequestBody Code request, HttpServletResponse response) throws LoginException {
//
//        String code = request.getCode();
//        log.info("NAVER redirectUrl code: {}", code);
//
//        // 인가코드를 통해 액세스 토큰을 발급받음
//        String accessTokenNaver = oAuth2Service.requestNaverAccessToken(code);
//        log.info("accessTokenNaver: {}", accessTokenNaver);
//
//        // 액세스 토큰을 통해 네이버 회원정보를 발급받음
//        NaverAccountProfileResponse naverAccountProfileResponse = oAuth2Service.requestNaverAccountProfile(accessTokenNaver);
//        log.info("naverAccountProfileResponse: {}", naverAccountProfileResponse.toString());
//
//        // 카카오 회원정보에 담긴 이메일을 통해 가입된 회원인지 판단 필요
//        // 회원가입 진행 후 로그인 OR 로그인 -> check_signup 메서드를 통해 진행
//        SocialNicknameAndProfileResponse socialInfoResponse = oAuth2Service.checkSignupNaver(naverAccountProfileResponse, response);
//
//        return socialInfoResponse;
//    }
}