package com.example.scon.domain.oven.repository;

import com.example.scon.domain.oven.entity.OvenMember;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvenMemberRepository extends JpaRepository<OvenMember, Long> {

}
