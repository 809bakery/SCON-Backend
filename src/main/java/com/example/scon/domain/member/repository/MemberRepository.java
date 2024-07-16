package com.example.scon.domain.member.repository;

import com.example.scon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String Email);
    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByNickname(String nickname);
}
