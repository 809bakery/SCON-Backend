package com.example.scon.domain.event.service;

import com.example.scon.domain.event.entity.MainEvent;

import java.util.List;

public interface MainEventService {

    MainEvent saveMainEvent(MainEvent mainEvent);
    MainEvent getMainEventById(Long id);
    MainEvent updateMainEvent(MainEvent mainEvent);
    void deleteMainEvent(Long id);
    List<MainEvent> getAllMainEvents();
}
