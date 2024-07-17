package com.example.scon.domain.event.service;

import com.example.scon.domain.event.entity.MainEvent;
import com.example.scon.domain.event.repository.MainEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainEventServiceImpl implements MainEventService {

    private MainEventRepository mainEventRepository;

    @Autowired
    public MainEventServiceImpl(MainEventRepository mainEventRepository) {
        this.mainEventRepository = mainEventRepository;
    }

    @Override
    @Transactional
    public MainEvent saveMainEvent(MainEvent mainEvent) {
        return mainEventRepository.save(mainEvent);
    }

    @Override
    public MainEvent getMainEventById(Long id) {
        return mainEventRepository.findById(id).orElse(null);
    }

    @Override
    public MainEvent updateMainEvent(MainEvent mainEvent) {
        if (mainEvent.getId() == null || !mainEventRepository.existsById(mainEvent.getId())) {
            throw new IllegalArgumentException("SubEvent with ID " + mainEvent.getId() + " does not exist.");
        }
        return mainEventRepository.save(mainEvent);
    }

    @Override
    public void deleteMainEvent(Long id) {
        mainEventRepository.deleteById(id);
    }

    @Override
    public List<MainEvent> getAllMainEvents() {
        return mainEventRepository.findAll();
    }
}
