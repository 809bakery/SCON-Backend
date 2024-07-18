package com.example.scon.domain.event.service;

import com.example.scon.domain.event.entity.SubEvent;
import com.example.scon.domain.event.repository.SubEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubEventServiceImpl implements SubEventService {

    private final SubEventRepository subEventRepository;

    @Autowired
    public SubEventServiceImpl(SubEventRepository subEventRepository) {
        this.subEventRepository = subEventRepository;
    }

    @Override
    public SubEvent getSubEventById(long id) {
        return subEventRepository.findById(id).orElse(null);
    }

    @Override
    public SubEvent saveSubEvent(SubEvent subEvent) {
        return subEventRepository.save(subEvent);
    }

    @Override
    public SubEvent updateSubEvent(SubEvent subEvent) {
        if (subEvent.getId() == null || !subEventRepository.existsById(subEvent.getId())) {
            throw new IllegalArgumentException("SubEvent with ID " + subEvent.getId() + " does not exist.");
        }
        return subEventRepository.save(subEvent);
    }

    @Override
    public void deleteSubEventById(long id) {
        subEventRepository.deleteById(id);
    }

    @Override
    public List<SubEvent> getSubEventsByMainEventId(long mainEventId) {
        return subEventRepository.findSubEventByMainEventId(mainEventId);
    }
}
