package com.example.scon.domain.email.controller;

import com.example.scon.domain.email.service.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth/mail")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String e;

    @GetMapping("/send/{email}")
    public String sendEmailCode(@PathVariable String email) throws UnsupportedEncodingException {

        String code = emailService.sendEmailCode(email);

        return code;
    }

    @GetMapping("/verify/{email}/{code}")
    public void verifyEmailCode(@PathVariable String email, @PathVariable String code) {

        emailService.verifyEmailCode(email, code);
    }
}
