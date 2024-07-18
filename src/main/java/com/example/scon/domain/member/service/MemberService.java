package com.example.scon.domain.member.service;

import com.example.scon.domain.member.dto.request.SignupRequestDto;
import com.example.scon.domain.member.dto.request.UpdateMemberNicknameAndProfileImgRequestDto;
import com.example.scon.domain.member.entity.Member;

public interface MemberService {

    public void signup(SignupRequestDto request);

    public void logout(String email);

    public void dupNicknameCheck(String nickname);

    public void editNicknameAndProfile(String email, UpdateMemberNicknameAndProfileImgRequestDto dto);

    public void editNickname(String nickname, String email);

    public void editPassword(String email, String currentPassword, String newPassword);

    public Member validatePwd(String email, String password);

    public void editProfile(String email, String newProfileImg);

    public void deleteUser(String email, String password);
}
