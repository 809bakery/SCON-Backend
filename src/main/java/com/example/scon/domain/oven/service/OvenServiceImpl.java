package com.example.scon.domain.oven.service;

import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import com.example.scon.domain.oven.entity.Oven;
import com.example.scon.domain.oven.repository.OvenMemberRepository;
import com.example.scon.domain.oven.repository.OvenRepository;
import com.example.scon.global.util.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OvenServiceImpl implements OvenService {

    private final MemberRepository memberRepository;
    private final OvenRepository ovenRepository;
    private final OvenMemberRepository ovenMemberRepository;


    public Page<Oven> getOvens(List<Oven> ovens, Pageable pageable) {
        return PageUtils.convertListToPage(ovens, pageable);
    }

    @Override
    public Page<Oven> listOven(String email, int page, int size) {
        // 이메일을 통해 회원 정보를 찾고, oven_member 테이블의 회원정보가 존재하는 컬럼을 찾기
        Member member = memberRepository.findByEmail(email).orElse(null);
        Page<Oven> ovenPageList = null;

        if(member != null) {
            List<Oven> ovenList = ovenRepository.findAllByMemberId(member.getMemberId());
            for(Oven oven : ovenList) {
                log.info(oven.toString());
            }
            ovenPageList = getOvens(ovenList, PageRequest.of(page, size));
        }

        return ovenPageList;
    }
}
