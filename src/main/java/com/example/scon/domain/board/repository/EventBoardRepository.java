package com.example.scon.domain.board.repository;

import com.example.scon.domain.board.entity.Category;
import com.example.scon.domain.board.entity.EventBoard;
import com.example.scon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventBoardRepository extends JpaRepository<EventBoard, Long> {
    Optional<EventBoard> findById(Long eventBoardId);
    // 동적 쿼리 생성
    @Query(value="select eb from EventBoard eb where eb.member = :member and eb.category = :category")
    EventBoard findMemberByMemberIdAndCategory(@Param("member") Member member, @Param("category") Category category);

}
