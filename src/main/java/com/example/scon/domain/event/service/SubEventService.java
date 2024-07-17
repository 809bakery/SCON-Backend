package com.example.scon.domain.event.service;

import com.example.scon.domain.event.entity.SubEvent;

public interface SubEventService {
    SubEvent getSubEventById(long id);
    SubEvent saveSubEvent(SubEvent subEvent);
    SubEvent updateSubEvent(SubEvent subEvent);
    void deleteSubEventById(long id);
}
