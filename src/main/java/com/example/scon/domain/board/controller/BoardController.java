package com.example.scon.domain.board.controller;

import com.example.scon.domain.board.dto.request.BoardRequestDto;
import com.example.scon.domain.board.service.BoardService;
import com.example.scon.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/board")
@Validated
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "기대평 작성", description = "기대평 작성")
    @PostMapping("{maineventId}/expect")
    public ResponseEntity<String> registerExpect(@PathVariable("maineventId")  int maineventId, @RequestBody BoardRequestDto boardRequestDto) {
        String email = SecurityUtil.getLoginUsername();
        boardService.registerExpect(email, maineventId, boardRequestDto);
        return ResponseEntity.ok("기대평 작성 성공");
    }

    @Operation(summary = "기대평 수정", description = "기대평 수정")
    @PatchMapping("{maineventId}/expect")
    public ResponseEntity<String> updateExpect(@PathVariable("maineventId")  int maineventId, @RequestBody BoardRequestDto boardRequestDto) {
        String email = SecurityUtil.getLoginUsername();
        boardService.updateExpect(email, maineventId, boardRequestDto);
        return ResponseEntity.ok("기대평 수정 성공");
    }

    @Operation(summary = "기대평 삭제")
    @DeleteMapping("{maineventId}/expect/{boardId}")
    public ResponseEntity<String> deleteExpect(@PathVariable("boardId")  Long boardId) {
        boardService.deleteExpect(boardId);
        return ResponseEntity.ok("기대평 삭제 성공");
    }

    @Operation(summary = "후기 작성", description = "공연이 끝난 후 후기 작성 가능")
    @PostMapping("{maineventId}/review")
    public ResponseEntity<String> registerReview(@PathVariable("maineventId")  int maineventId, @RequestBody BoardRequestDto boardRequestDto) {
        String email = SecurityUtil.getLoginUsername();
        boardService.registerView(email, maineventId, boardRequestDto);
        return ResponseEntity.ok("후기 작성 성공");
    }

    @Operation(summary = "후기 수정", description = "후기 수정")
    @PatchMapping("{maineventId}/review")
    public ResponseEntity<String> updateReview(@PathVariable("maineventId")  int maineventId, @RequestBody BoardRequestDto boardRequestDto) {
        String email = SecurityUtil.getLoginUsername();
        boardService.updateReview(email, maineventId, boardRequestDto);
        return ResponseEntity.ok("후기 수정 성공");
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("{maineventId}/review/{boardId}")
    public ResponseEntity<String> deleteReview(@PathVariable("boardId")  Long boardId) {
        boardService.deleteReview(boardId);
        return ResponseEntity.ok("후기 삭제 성공");
    }

}
