package com.example.scon.domain.member.controller;

import com.example.scon.domain.member.dto.request.*;
import com.example.scon.domain.member.service.MemberService;
import com.example.scon.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@Validated
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "스콘 회원가입", description = "스콘 회원가입")
    @PostMapping("/sign-up")
    //@ResponseStatus(HttpStatus.OK)
    public void signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
    }

    @Operation(summary = "스콘 로그아웃", description = "스콘 로그아웃 시, refreshToken 삭제")
    @GetMapping("/log-out")
    public void logout() {
        memberService.logout(SecurityUtil.getLoginUsername());
    }

    @Operation(summary =  "스콘 닉네임 중복체크", description = "사용가능은 STATUS 200, 중복이면 STATUS 400")
    @GetMapping("/nickname/{nickname}")
    public void dupNicknameCheck(@PathVariable("nickname") String nickname) {
        memberService.dupNicknameCheck(nickname);
    }

    @Operation(summary =  "스콘 닉네임, 프로필 변경", description = "회원가입 시, 닉네임과 프로필 사진 최초 설정")
    @PatchMapping("/edit/info")
    public void editNicknameAndProfile(@Valid @RequestBody UpdateMemberNicknameAndProfileImgRequestDto updateMemberNicknameAndProfileImgRequestDto) {
        String email = SecurityUtil.getLoginUsername();
        memberService.editNicknameAndProfile(email, updateMemberNicknameAndProfileImgRequestDto);
    }

    @GetMapping("/test")
    public String test() {
        return SecurityUtil.getLoginUsername();
    }

    @Operation(summary =  "스콘 닉네임 변경", description = "스콘 닉네임 변경")
    @PatchMapping("/edit/nickname/{nickname}")
    public ResponseEntity<String> editNickname(@PathVariable("nickname") @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$", message = "2자 이상 8자 이하, 영어 또는 숫자 또는 한글만 입력 가능합니다.") String nickname) {
        String email = SecurityUtil.getLoginUsername();
        memberService.editNickname(nickname, email);
        return ResponseEntity.ok("성공적으로 닉네임을 변경하였습니다.");
    }

    @Operation(summary = "비밀 번호 변경", description = "스콘 비밀번호 변경(소셜은 불가)")
    @PatchMapping("/edit/password")
    public ResponseEntity<String> editPassword(@Validated @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
        String email = SecurityUtil.getLoginUsername(); // 현재 사용자 이메일 정보 받아오기
        memberService.editPassword(email, updatePasswordRequestDto.getCurrentPassword(), updatePasswordRequestDto.getNewPassword());
        return ResponseEntity.ok("성공적으로 비밀번호를 변경하였습니다.");
    }

    @Operation(summary = "프로필 사진 변경", description = "스콘 프로필 사진 변경")
    @PatchMapping("/edit/profile")
    public ResponseEntity<String> editProfile(@RequestBody UpdateProfileImgRequestDto updateProfileImgRequestDto){
        String email = SecurityUtil.getLoginUsername();
        memberService.editProfile(email, updateProfileImgRequestDto.getNewProfileImg());
        return ResponseEntity.ok("성공적으로 프로필 사진을 변경하였습니다.");
    }

    @Operation(summary = "회원 탈퇴", description = "스콘 회원 탈퇴 (소셜도 불가)")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteRequestDto deleteRequestDto){
        String email = SecurityUtil.getLoginUsername();
        memberService.deleteUser(email, deleteRequestDto.getPassword());
        return ResponseEntity.ok("회원탈퇴 성공");
    }
}
