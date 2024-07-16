package com.example.scon.global.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 로그인 시 회원정보가 없다면 에러코드 401 리턴
        response.getWriter().write("fail");
        log.info("로그인에 실패했습니다");
    }
}