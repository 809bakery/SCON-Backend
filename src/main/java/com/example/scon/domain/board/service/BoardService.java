package com.example.scon.domain.board.service;

import com.example.scon.domain.board.dto.request.BoardRequestDto;

public interface BoardService {
    public void registerExpect(String email, int maineventId, BoardRequestDto boardRequestDto);

    public void updateExpect(String email, int maineventId, BoardRequestDto boardRequestDto);

    public void deleteExpect(Long boardId);

    public void registerView(String email, int maineventId, BoardRequestDto boardRequestDto);

    public void updateReview(String email, int maineventId, BoardRequestDto boardRequestDto);

    public void deleteReview(Long boardId);
}
