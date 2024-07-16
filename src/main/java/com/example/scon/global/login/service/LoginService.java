package com.example.scon.global.login.service;

import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
