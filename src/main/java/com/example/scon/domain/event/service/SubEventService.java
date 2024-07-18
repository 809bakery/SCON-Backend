package com.example.scon.domain.event.service;

import com.example.scon.domain.event.entity.SubEvent;

import java.util.List;

public interface SubEventService {
    SubEvent getSubEventById(long id);
    SubEvent saveSubEvent(SubEvent subEvent);
    SubEvent updateSubEvent(SubEvent subEvent);
    void deleteSubEventById(long id);
    List<SubEvent> getSubEventsByMainEventId(long mainEventId);

}
