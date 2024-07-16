package com.example.scon.global.jwt.filter;

import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import com.example.scon.global.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final String NO_CHECK_URL = "/api/auth/log-in";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return; // 로그인 요청인 경우, JWT 토큰 인증과정이 필요 없으므로 SercurityCofing에서 다음 필터로 지정한 UsernamePasswordAuthenticationFilter로 넘김
        }

        String refreshToken = jwtService
                .extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);
        // RefreshToken이 만료되었으면 null

        if(refreshToken != null){ // RefreshToken 유효 시, AccessToken 재발급
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return; // RefreshToken 인증 완료 후 AccessToken 재발급이기 때문에 인증처리
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
        // Access Token 인증 요청
        // Access Token 유효 시, username 정보를 추출. 추출 성공 시, 해당 회원을 DB에서 찾고 인증처리
        // 인증처리의 반환 값은 스프링 시큐리티에서 제공해주는 SecurityContextHoler의 Authentication 객체(=NullAuthoritiesMapper)
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .flatMap(jwtService::extractEmail)
                .flatMap(memberRepository::findByEmail)
                .ifPresent(this::saveAuthentication);
        filterChain.doFilter(request,response);
    }

    private void saveAuthentication(Member member) {
        UserDetails user = User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authoritiesMapper.mapAuthorities(user.getAuthorities()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken).ifPresent(
                member -> jwtService.sendAccessToken(response, jwtService.createAccessToken(member.getEmail()))
        );
    }
}