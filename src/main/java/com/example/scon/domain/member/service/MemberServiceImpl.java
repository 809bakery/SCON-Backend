package com.example.scon.domain.member.service;

import com.example.scon.domain.member.dto.request.SignupRequestDto;
import com.example.scon.domain.member.dto.request.UpdateMemberNicknameAndProfileImgRequestDto;
import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import com.example.scon.global.error.ErrorCode;
import com.example.scon.global.error.type.BadRequestException;
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
    }

    @Override
    public void dupNicknameCheck(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElse(null);

        if(member != null) {
            throw new BadRequestException(
                    ErrorCode.USER_NICKNAME_DUPLICATE, "사용중인 닉네임입니다.");
        }
    }

    @Override
    public void editNicknameAndProfile(String email, UpdateMemberNicknameAndProfileImgRequestDto dto) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if(member != null) {
            if(dto.getNickname() != null) {
                member.setNickname(dto.getNickname());
            }
            if(dto.getImage() != null) {
                member.setImage(dto.getImage());
            }
            memberRepository.save(member);
        }

    }

    @Override
    public void editNickname(String nickname, String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member != null) {
            member.setNickname(nickname);
            memberRepository.save(member);
        }
    }

    @Override
    public void editPassword(String email, String currentPassword, String newPassword) {
        Member member = validatePwd(email, currentPassword);
        member.updatePassword(passwordEncoder.encode(newPassword));
    }

    public Member validatePwd(String email, String currentPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElse(null);
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new BadRequestException(
                    ErrorCode.USER_PASSWORD_MISMATCH, "비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    @Override
    public void editProfile(String email, String newProfileImg) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        member.setImage(newProfileImg);
        memberRepository.save(member);
    }


    @Override
    public void deleteUser(String email, String password) {
        Member member = validatePwd(email, password);
        memberRepository.delete(member);
    }
}
