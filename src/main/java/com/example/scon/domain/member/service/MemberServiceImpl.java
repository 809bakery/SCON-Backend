package com.example.scon.domain.member.service;

import com.example.scon.domain.auth.dto.response.GoogleAccountProfileResponse;
import com.example.scon.domain.auth.dto.response.KakaoAccountProfileResponse;
import com.example.scon.domain.member.dto.request.SignupRequestDto;
import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import com.example.scon.global.error.ErrorCode;
import com.example.scon.global.error.type.BadRequestException;
import com.example.scon.global.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        Member newMember = signupRequestDto.toEntity();
        log.info("New member: {}", newMember); // 회원가입 정보

        newMember.encodePassword(passwordEncoder);
        log.info("New password: {}", newMember.getPassword()); // 비밀번호 암호화

        newMember.addRole();
        log.info("new Role: {}", newMember.getRole()); // 일반 사용자(스코니) 역할 부여

        memberRepository.save(newMember);
    }

    @Override
    public void logout(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        log.info(email);
        if(member != null) {
            member.setRefreshToken(null);
            log.info(member.getRefreshToken());
            memberRepository.save(member);
            log.info(member.getRefreshToken());
        }

        // member가 null 이면 회원정보가 존재하지 않는 유효하지 않은 토큰 요청이기에 에러를 발생.
        // 에러코드 부분은 이후 ErrorCode.class를 생성하여 전역으로 다룰 예정.
        // 현재는 무조건 유효한 토큰이라는 가정으로 구현함.
    }

    @Override
    public void dupNicknameCheck(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElse(null);

        if(member != null) {
            throw new BadRequestException(
                    ErrorCode.USER_NICKNAME_DUPLICATE, "사용중인 닉네임입니다.");
        }

    }
}
