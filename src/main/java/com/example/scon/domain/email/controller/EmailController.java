package com.example.scon.domain.email.controller;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@RestController
@RequestMapping("/api/auth/send-mail")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class EmailController {

//    private final EmailService emailService;
    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String e;

    @GetMapping("/{email}")
    @ResponseBody
    public int emailAuth(@PathVariable String email) throws UnsupportedEncodingException {

        log.info("전달 받은 이메일 주소 : " + email);

        //난수의 범위 111111 ~ 999999 (6자리 난수)
        Random random = new Random();
        int checkNum = random.nextInt(888888)+111111;

        //이메일 보낼 양식
        String setFrom = String.valueOf(new InternetAddress(e,"SCON_Admin")); //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
        String toMail = email;
        String title = "회원가입 인증 이메일 입니다.";
        String content = "인증 코드는 " + checkNum + " 입니다." +
                "<br>" +
                "해당 인증 코드를 인증 코드 확인란에 기입하여 주세요.";

        try {
            log.info("인증코드 : " + checkNum);
            MimeMessage message = mailSender.createMimeMessage(); //Spring에서 제공하는 mail API
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkNum;
    }
    // 임시 비밀번호 발급
//    @PostMapping("/password")
//    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to(emailPostDto.getEmail())
//                .subject("[SAVIEW] 임시 비밀번호 발급")
//                .build();
//
//        emailService.sendMail(emailMessage, "password");
//
//        return ResponseEntity.ok().build();
//    }


}
