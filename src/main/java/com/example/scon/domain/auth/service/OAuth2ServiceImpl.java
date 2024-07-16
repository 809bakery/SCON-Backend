package com.example.scon.domain.auth.service;

import com.example.scon.domain.auth.dto.request.AccessTokenRequest;
import com.example.scon.domain.auth.dto.response.*;
import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import com.example.scon.global.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2ServiceImpl implements OAuth2Service{

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // google-login config
    @Value("${spring.oauth2.google.accesstoken}")
    private String accessTokenUrl_google;

    @Value("${spring.oauth2.google.clientid}")
    private String client_id_google;

    @Value("${spring.oauth2.google.client_secret}")
    private String client_secret_google;

    @Value("${spring.oauth2.google.redirect_uri}")
    private String redirect_uri_google;

    @Value("${spring.oauth2.google.grant_type}")
    private String grant_type_google;

    // kakao-login config
    @Value("${spring.oauth2.kakao.accesstoken}")
    private String accessTokenUrl_kakao;

    @Value("${spring.oauth2.kakao.clientid}")
    private String client_id_kakao;

    @Value("${spring.oauth2.kakao.client_secret}")
    private String client_secret_kakao;

    @Value("${spring.oauth2.kakao.redirect_uri}")
    private String redirect_uri_kakao;

    @Value("${spring.oauth2.kakao.grant_type}")
    private String grant_type_kakao;

    // naver-login config
    @Value("${spring.oauth2.naver.accesstoken}")
    private String accessTokenUrl_naver;

    @Value("${spring.oauth2.naver.clientid}")
    private String client_id_naver;

    @Value("${spring.oauth2.naver.client_secret}")
    private String client_secret_naver;

    @Value("${spring.oauth2.naver.redirect_uri}")
    private String redirect_uri_naver;

    @Value("${spring.oauth2.naver.grant_type}")
    private String grant_type_naver;

    // 구글 소셜 로그인 Start
    @Override
    public String requestGoogleAccessToken(String code) throws LoginException {

        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<AccessTokenRequest> httpEntity = new HttpEntity<>(
                new AccessTokenRequest(decodedCode, client_id_google, client_secret_google, redirect_uri_google, grant_type_google),
                headers
        );
        log.info("Google access token request: {}", httpEntity.toString());

        GoogleAccessTokenResponse response = restTemplate.exchange(
                accessTokenUrl_google, HttpMethod.POST, httpEntity, GoogleAccessTokenResponse.class
        ).getBody();
        log.info("Google access token response: {}", response);

        return Optional.ofNullable(response)
                .orElseThrow(() -> new LoginException("NOT_FOUND_GOOGLE_ACCESS_TOKEN_RESPONSE"))
                .getAccess_token();
    }

    @Override
    public GoogleAccountProfileResponse requestGoogleAccountProfile(String accessToken) {
        String profileUrl = "https://www.googleapis.com/userinfo/v2/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<AccessTokenRequest> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(profileUrl, HttpMethod.GET, httpEntity, GoogleAccountProfileResponse.class)
                .getBody();
    }

    @Override
    public SocialNicknameAndProfileResponse checkSignupGoogle(GoogleAccountProfileResponse googleAccountProfileResponse, HttpServletResponse response) {
        log.info("googleAccountProfileResponse.getMail(): {}", googleAccountProfileResponse.getEmail());

        Member member = memberRepository.findByEmail(googleAccountProfileResponse.getEmail()).orElse(null);

        Member newMember = new Member();
        String accessToken;
        String refreshToken;

        SocialNicknameAndProfileResponse socialInfoResponse = new SocialNicknameAndProfileResponse();

        if(member == null) { // 회원가입 후 로그인을 위한 토큰 발급
            String email = googleAccountProfileResponse.getEmail();
            String nickname = googleAccountProfileResponse.getName();
            String profileImgUrl = googleAccountProfileResponse.getPicture();
            newMember.setEmail(email);
            newMember.setPassword(passwordEncoder.encode(email)); // 비밀번호 형식적으로 등록 필요
            newMember.setNickname("구글_" + nickname);
            newMember.setImage(profileImgUrl);
            newMember.addRole();

            accessToken = jwtService.createAccessToken(googleAccountProfileResponse.getEmail());
            refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            newMember.setRefreshToken(refreshToken);

            log.info("회원가입 완료!");
            log.info( "구글 소셜 로그인 성공. email: {}", googleAccountProfileResponse.getEmail());
            log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
            log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);

            memberRepository.save(newMember);

            socialInfoResponse.setExisted(false);
            socialInfoResponse.setNickname(newMember.getNickname());
            socialInfoResponse.setImage(newMember.getImage());
        }
        else { // 이미 회원 존재, 바로 로그인 진행
            accessToken = jwtService.createAccessToken(googleAccountProfileResponse.getEmail());
            refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            member.setRefreshToken(refreshToken);

            log.info("이미 존재하는 회원!");
            log.info( "구글 소셜 로그인 성공. email: {}", googleAccountProfileResponse.getEmail());
            log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
            log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);

            socialInfoResponse.setExisted(true);
            socialInfoResponse.setNickname(member.getNickname());
            socialInfoResponse.setImage(member.getImage());
        }

        return socialInfoResponse;
    }
    // 구글 소셜 로그인 End


    // 카카오 소셜 로그인 Start
    @Override
    public String requestKakaoAccessToken(String code) throws LoginException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grant_type_kakao);
        map.add("client_id", client_id_kakao);
        map.add("client_secret", client_secret_kakao);
        map.add("redirect_uri", redirect_uri_kakao);
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        KakaoAccessTokenResponse response = restTemplate.exchange(
                accessTokenUrl_kakao, HttpMethod.POST, httpEntity, KakaoAccessTokenResponse.class
        ).getBody();

        return Optional.ofNullable(response)
                .orElseThrow(() -> new LoginException("NOT_FOUND_KAKAO_ACCESS_TOKEN_RESPONSE"))
                .getAccess_token();
    }

    @Override
    public KakaoAccountProfileResponse requestKakaoAccountProfile(String accessToken) {
        String profileUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        try {
            KakaoAccountProfileFullResponse profileResponse = restTemplate.exchange(
                    profileUrl, HttpMethod.GET, httpEntity, KakaoAccountProfileFullResponse.class
            ).getBody();

            log.info("Profile response: {}", profileResponse);

            Long id = profileResponse.getId();
            String nickname = profileResponse.getProperties().getNickname();
            String profileImage = profileResponse.getProperties().getProfile_image();
            String accountEmail = profileResponse.getKakao_account().getEmail();

            return new KakaoAccountProfileResponse(id, nickname, profileImage, accountEmail);
        } catch (HttpClientErrorException e) {
            log.error("Error requesting Kakao Account Profile: {}", e.getStatusCode());
            log.error("Response Body: {}", e.getResponseBodyAsString());
            throw new RuntimeException("KAKAO_ACCOUNT_PROFILE_REQUEST_FAILED");
        }
    }

    @Override
    public SocialNicknameAndProfileResponse checkSignupKakao(KakaoAccountProfileResponse kakaoAccountProfileResponse, HttpServletResponse response) {
        log.info("KakaoAccountProfileResponse.getMail(): {}", kakaoAccountProfileResponse.getAccount_email());

        Member member = memberRepository.findByEmail(kakaoAccountProfileResponse.getAccount_email()).orElse(null);

        Member newMember = new Member();
        String accessToken;
        String refreshToken;

        SocialNicknameAndProfileResponse socialInfoResponse = new SocialNicknameAndProfileResponse();

        if(member == null) { // 회원가입 후 로그인을 위한 토큰 발급
            String email = kakaoAccountProfileResponse.getAccount_email();
            String nickname = kakaoAccountProfileResponse.getNickname();
            String profileImgUrl = kakaoAccountProfileResponse.getProfile_image();
            newMember.setEmail(email);
            newMember.setPassword(passwordEncoder.encode(email)); // 비밀번호 형식적으로 등록 필요
            newMember.setNickname("카카오_"+nickname);
            newMember.setImage(profileImgUrl);
            newMember.addRole();

            accessToken = jwtService.createAccessToken(kakaoAccountProfileResponse.getAccount_email());
            refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            newMember.setRefreshToken(refreshToken);

            log.info("회원가입 완료!");
            log.info( "카카오 소셜 로그인 성공. email: {}", kakaoAccountProfileResponse.getAccount_email());
            log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
            log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);

            memberRepository.save(newMember);

            socialInfoResponse.setExisted(false);
            socialInfoResponse.setNickname(newMember.getNickname());
            socialInfoResponse.setImage(newMember.getImage());
        }
        else { // 이미 회원 존재, 바로 로그인 진행
            accessToken = jwtService.createAccessToken(kakaoAccountProfileResponse.getAccount_email());
            refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            member.setRefreshToken(refreshToken);

            log.info("이미 존재하는 회원!");
            log.info( "구글 소셜 로그인 성공. email: {}", kakaoAccountProfileResponse.getAccount_email());
            log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
            log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);

            socialInfoResponse.setExisted(true);
            socialInfoResponse.setNickname(member.getNickname());
            socialInfoResponse.setImage(member.getImage());
        }

        return socialInfoResponse;
    }
    // 카카오 소셜 로그인 End


    // 네이버 소셜 로그인 Start
    @Override
    public String requestNaverAccessToken(String code) throws LoginException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grant_type_naver);
        map.add("client_id", client_id_naver);
        map.add("client_secret", client_secret_naver);
        map.add("redirect_uri", redirect_uri_naver);
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        NaverAccessTokenResponse response = restTemplate.exchange(
                accessTokenUrl_naver, HttpMethod.POST, httpEntity, NaverAccessTokenResponse.class
        ).getBody();

        return Optional.ofNullable(response)
                .orElseThrow(() -> new LoginException("NOT_FOUND_NAVER_ACCESS_TOKEN_RESPONSE"))
                .getAccess_token();
    }

    @Override
    public NaverAccountProfileResponse requestNaverAccountProfile(String accessToken) {
        String profileUrl = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        try {
            NaverAccountProfileFullResponse profileResponse = restTemplate.exchange(
                    profileUrl, HttpMethod.GET, httpEntity, NaverAccountProfileFullResponse.class
            ).getBody();

            log.info("Profile response: {}", profileResponse);

            String id = profileResponse.getResponse().getId();
            String nickname = profileResponse.getResponse().getNickname();
            String profile_image = profileResponse.getResponse().getProfile_image();
            String accountEmail = profileResponse.getResponse().getEmail();
            String name = profileResponse.getResponse().getName();

            return new NaverAccountProfileResponse(id, nickname, profile_image, accountEmail, name);
        } catch (HttpClientErrorException e) {
            log.error("Error requesting NAVER Account Profile: {}", e.getStatusCode());
            log.error("Response Body: {}", e.getResponseBodyAsString());
            throw new RuntimeException("NAVER_ACCOUNT_PROFILE_REQUEST_FAILED");
        }
    }

    @Override
    public SocialNicknameAndProfileResponse checkSignupNaver(NaverAccountProfileResponse naverAccountProfileResponse, HttpServletResponse response) {
//        log.info("KakaoAccountProfileResponse.getMail(): {}", NaverAccountProfileResponse.getAccount_email());

        Member member = memberRepository.findByEmail(naverAccountProfileResponse.getAccountEmail()).orElse(null);

        Member newMember = new Member();
        String accessToken;
        String refreshToken;

        SocialNicknameAndProfileResponse socialInfoResponse = new SocialNicknameAndProfileResponse();

        if(member == null) { // 회원가입 후 로그인을 위한 토큰 발급
            String email = naverAccountProfileResponse.getAccountEmail();
            String nickname = naverAccountProfileResponse.getNickname();
            String profileImgUrl = naverAccountProfileResponse.getProfile_image();
            newMember.setEmail(email);
            newMember.setPassword(passwordEncoder.encode(email)); // 비밀번호 형식적으로 등록 필요
            newMember.setNickname("네이버_" + nickname);
            newMember.setImage(profileImgUrl);
            newMember.addRole();

            accessToken = jwtService.createAccessToken(naverAccountProfileResponse.getAccountEmail());
            refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            newMember.setRefreshToken(refreshToken);

            log.info("회원가입 완료!");
            log.info( "네이버 소셜 로그인 성공. email: {}", naverAccountProfileResponse.getAccountEmail());
            log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
            log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);

            memberRepository.save(newMember);

            socialInfoResponse.setExisted(false);
            socialInfoResponse.setNickname(newMember.getNickname());
            socialInfoResponse.setImage(newMember.getImage());
        }
        else { // 이미 회원 존재, 바로 로그인 진행
            accessToken = jwtService.createAccessToken(naverAccountProfileResponse.getAccountEmail());
            refreshToken = jwtService.createRefreshToken();
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            member.setRefreshToken(refreshToken);

            log.info("이미 존재하는 회원!");
            log.info( "네이버 소셜 로그인 성공. email: {}", naverAccountProfileResponse.getAccountEmail());
            log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
            log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);

            socialInfoResponse.setExisted(true);
            socialInfoResponse.setNickname(member.getNickname());
            socialInfoResponse.setImage(member.getImage());
        }

        return socialInfoResponse;
    }
    // 네이버 소셜 로그인 End

}
