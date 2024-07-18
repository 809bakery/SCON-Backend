package com.example.scon.domain.event.repository;

import com.example.scon.domain.event.entity.MainEvent;
import com.example.scon.domain.event.entity.SubEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubEventRepository extends JpaRepository<SubEvent, Long> {

    List<SubEvent> findByMainEventId(long mainEventId);
    @Query("SELECT s FROM SubEvent s WHERE s.mainEvent.id = :mainEventId")
    List<SubEvent> findSubEventByMainEventId(@Param("mainEventId") Long mainEventId);

}
