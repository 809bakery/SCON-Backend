package com.example.scon.domain.oven.repository;

import com.example.scon.domain.oven.entity.Oven;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OvenRepository extends JpaRepository<Oven, Long> {

    @Query("SELECT o FROM Oven o WHERE o.ovenId IN (SELECT om.oven.ovenId FROM OvenMember om WHERE om.member.memberId = :memberId)")
    List<Oven> findAllByMemberId(@Param("memberId") Long memberId);
}
