package com.example.scon.domain.event.controller;

import com.example.scon.domain.event.dto.request.MainEventRequestDto;
import com.example.scon.domain.event.entity.MainEvent;
import com.example.scon.domain.event.service.MainEventService;
import com.example.scon.domain.event.service.SubEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class EventController {

    private final MainEventService mainEventService;
    private final SubEventService subEventService;

    @GetMapping("/list")
    public ResponseEntity<?> allMainEvents(
//            @RequestParam(required = false) String category,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "default") String sort
    ){
        try {
            List<MainEvent> list = mainEventService.getAllMainEvents();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(list);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    static class ResponseBody {
//        private String status;
//        private List<MainEvent> content;
//        private PageInfo pageInfo;
//
//        public ResponseBody(String status, List<MainEvent> content, PageInfo pageInfo) {
//            this.status = status;
//            this.content = content;
//            this.pageInfo = pageInfo;
//        }
//
//        // Getters and setters
//        // Implement as needed
//    }
//
//    static class PageInfo {
//        private int lastpage;
//        private int nextpage;
//
//        public PageInfo(int lastpage, int nextpage) {
//            this.lastpage = lastpage;
//            this.nextpage = nextpage;
//        }
//    }
}
