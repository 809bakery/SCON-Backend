package com.example.scon.domain.event.repository;

import com.example.scon.domain.event.entity.Category;
import com.example.scon.domain.event.entity.MainEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainEventRepository extends JpaRepository<MainEvent, Long> {
    @Override
    List<MainEvent> findAll();
    List<MainEvent> findByTitleContaining(String title);

    MainEvent findById(long id);

    Page<MainEvent> findAll(Pageable pageable);
    Page<MainEvent> findByTitleContaining(String title, Pageable pageable);
    Page<MainEvent> findByCategory(Category category, Pageable pageable);

    List<MainEvent> findByCategory(Category category);
}
