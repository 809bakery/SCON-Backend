package com.example.scon.domain.event.repository;

import com.example.scon.domain.event.entity.SubEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubEventRepository extends JpaRepository<SubEvent, Long> {

    // List<SubEvent> findSubEventByMainEvent();

}
