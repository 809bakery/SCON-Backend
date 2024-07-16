package com.example.scon.global.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/api/auth/log-in"; // "/api/log-in"을 로그인 인증 요청으로 인식하도록 함
    private static final String HTTP_METHOD = "POST"; // POST 요청을 통해서만 로그인 인증을 진행할 수 있도록 설정 (GET 방식 X)
    private static final String CONTENT_TYPE = "application/json"; // formLogin이 아닌, json 타입의 데이터를 통한 로그인을 진행하도록 설정
    private final ObjectMapper objectMapper;
    private static final String USERNAME_KEY="email"; // 로그인 요청에 담긴 아이디 키 명칭 (email이지만, username으로 설정함)
    private static final String PASSWORD_KEY="password"; // 로그인 요청에 담긴 비밀번호 키 명칭 (password)

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {

        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);   // POST 형태의 "/api/log-in" 요청이 아닐 때를 위해 처리(=GET인 경우)

        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        } // 로그인 요청이 json 타입의 데이터 형식인지 확인

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);
        String username = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // principal(username)과 credentials(password)를 통해 UsernamePasswordAuthenticationToken 생성

        return this.getAuthenticationManager().authenticate(authRequest);
        // UsernamePasswordAuthenticationToken을 AuthenticationProvider의 authenticate()의 인자로 넘겨 만든 결과물인 Authentication 객체 반환
    }
}