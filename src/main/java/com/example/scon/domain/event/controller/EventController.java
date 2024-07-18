package com.example.scon.domain.event.controller;

import com.example.scon.domain.event.dto.request.MainEventRequestDto;
import com.example.scon.domain.event.entity.Category;
import com.example.scon.domain.event.entity.MainEvent;
import com.example.scon.domain.event.entity.SubEvent;
import com.example.scon.domain.event.service.MainEventService;
import com.example.scon.domain.event.service.SubEventService;

import com.example.scon.global.util.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class EventController {

    private final MainEventService mainEventService;
    private final SubEventService subEventService;

    @Operation(summary = "공연 전체 리스트", description = "공연 전체 리스트")
    @GetMapping("/list")
    public ResponseEntity<?> allMainEvents(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort

    ){
        try {

            List<MainEvent> eventPage;

            if("all".equals(category)){
                eventPage = mainEventService.getAllMainEvents();

            } else {
                Category categoryEnum = Category.valueOf(category.toUpperCase());
                eventPage = mainEventService.getMainEventsByCategory(categoryEnum);
            }

            Page<MainEvent> PageList = PageUtils.convertListToPage(eventPage, PageRequest.of(page, size, Sort.by(sort)));

            return ResponseEntity.ok(PageList);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "공연 상세 조회", description = "공연 상세 조회")
    @GetMapping("/{mainEventId}")
    public ResponseEntity<?> eventDetail(@PathVariable(required = true) long mainEventId){
        try{
            MainEvent mainEvent = mainEventService.getMainEventById(mainEventId);
            return ResponseEntity.ok(mainEvent);
        } catch (Exception e){
            return exceptionHandling(e);
        }
    }

    public ResponseEntity<String> registEvent(
            @RequestBody MainEventRequestDto mainEventRequestDto
    ) {
        try{

            return ResponseEntity.ok("등록 완료");
        } catch(Exception e){
            return exceptionHandling(e);
        }
    }
    @Operation(summary = "공연 서브이벤트 조회", description = "공연 서브이벤트 조회")
    @GetMapping("/subevent/{mainEventId}")
    public ResponseEntity<?> subEventAtMainEventId(
            @PathVariable("mainEventId") long mainEventId
    ){
        try{
            List<SubEvent> subEvents = subEventService.getSubEventsByMainEventId(mainEventId);
            return ResponseEntity.ok(subEvents);
        } catch(Exception e) {
            return exceptionHandling(e);
        }

    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
