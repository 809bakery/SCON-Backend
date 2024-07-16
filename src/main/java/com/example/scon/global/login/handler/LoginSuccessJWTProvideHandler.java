package com.example.scon.global.login.handler;

import com.example.scon.domain.member.repository.MemberRepository;
import com.example.scon.global.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW) // 이 어노테이션이 없으면 RefreshToken이 DB 저장이 안됨
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        memberRepository.findByEmail(username).ifPresent(
                member -> member.updateRefreshToken(refreshToken)
        );
        // memberRepository는 JpaRepository로, Transaction을 자동 지원 / 해당 위치는 핸들러이므로 RefreshToken을 저장하는 해당 코드는 핸들러 종료 후 원상복구 됨(즉 refreshToken 저장 X)
        // 이를 막아주기 위해 이벤트 핸들러의 트랜잭션을 분리하는 @Transactional(Transactional.TxType.REQUIRES_NEW) 어노테이션을 선언 -> 해당 방법을 사용해도 되는지는 잘 모르겠음...

        log.info( "로그인에 성공합니다. username: {}", username);
        log.info( "AccessToken 을 발급합니다. AccessToken: {}", accessToken);
        log.info( "RefreshToken 을 발급합니다. RefreshToken: {}", refreshToken);
    }

    private String extractUsername(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
