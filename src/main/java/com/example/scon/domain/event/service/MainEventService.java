package com.example.scon.domain.event.service;

import com.example.scon.domain.event.entity.Category;
import com.example.scon.domain.event.entity.MainEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MainEventService {

    MainEvent saveMainEvent(MainEvent mainEvent);
    MainEvent getMainEventById(Long id);
    MainEvent updateMainEvent(MainEvent mainEvent);
    void deleteMainEvent(Long id);
    List<MainEvent> getAllMainEvents();
    Page<MainEvent> getAllMainEvents(Pageable pageable);
    Page<MainEvent> getMainEventsByCategory(Category category, Pageable pageable);
    List<MainEvent> getMainEventsByCategory(Category category);
    MainEvent createMainEvent(MainEvent mainEvent);
}
