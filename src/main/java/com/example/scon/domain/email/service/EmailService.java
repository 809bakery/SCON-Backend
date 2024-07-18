package com.example.scon.domain.email.service;

import com.example.scon.global.error.ErrorCode;
import com.example.scon.global.error.type.BadRequestException;
import com.example.scon.global.util.RedisUtil;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String e;

    private final JavaMailSenderImpl mailSender;
    private final RedisUtil redisUtil;

    public String sendEmailCode(String email) throws UnsupportedEncodingException {
        log.info("전달 받은 이메일 주소 : " + email);

        //난수의 범위 111111 ~ 999999 (6자리 난수)
        Random random = new Random();
        String code = (random.nextInt(888888) + 111111) + "";

        //이메일 보낼 양식
        String setFrom = String.valueOf(new InternetAddress(e, "SCON_Admin")); //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
        String title = "회원가입 인증 이메일 입니다.";
        String content = "인증 코드는 " + code + " 입니다." +
                "<br>" +
                "해당 인증 코드를 인증 코드 확인란에 기입하여 주세요.";

        try {
            log.info("생성된 인증코드 : " + code);

            redisUtil.deleteData(code);
            redisUtil.setDataExpire(code, email, 180); // 만료시간 3분
            log.info(redisUtil.getData(code));

            MimeMessage message = mailSender.createMimeMessage(); //Spring 제공하는 mail API
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return code;
    }

    public void verifyEmailCode(String email, String code) {
        log.info("이메일 : " + email);
        log.info("확인 할 인증코드 : " + code);

        log.info(redisUtil.getData(code));

        if(redisUtil.getData(code) == null || !redisUtil.getData(code).equals(email)) {
            throw new BadRequestException(
                    ErrorCode.EMAIL_CODE_MISMATCH, "메일 인증코드가 일치하지 않습니다.");
        }
    }
}
