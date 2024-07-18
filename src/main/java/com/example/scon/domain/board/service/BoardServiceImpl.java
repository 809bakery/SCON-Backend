package com.example.scon.domain.board.service;


import com.example.scon.domain.board.dto.request.BoardRequestDto;
import com.example.scon.domain.board.entity.Category;
import com.example.scon.domain.board.entity.EventBoard;
import com.example.scon.domain.board.repository.EventBoardRepository;
import com.example.scon.domain.member.entity.Member;
import com.example.scon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final EventBoardRepository eventBoardRepository;

    @Override
    public void registerExpect(String email, int maineventId, BoardRequestDto boardRequestDto) {
        EventBoard eventBoard = boardRequestDto.toEntity();

        // 유저아이디 찾기
        Member member = memberRepository.findByEmail(email).orElse(null);

        // 이벤트 관련 레포지토리 구현 후 수정 필요
        // MainEvent me = mainEventRepository.findById(maineventId).orElse(null);

        eventBoard.setMember(member);
        eventBoard.setCategory(Category.EXPECT);
//        eventBoard.setMainEvent(me);
        eventBoardRepository.save(eventBoard);
    }

    @Override
    public void updateExpect(String email, int maineventId, BoardRequestDto boardRequestDto) {
        // 유저아이디랑 카테고리로 기대평 게시글 번호
        Member member = memberRepository.findByEmail(email).orElse(null);
        EventBoard eventBoard = eventBoardRepository.findMemberByMemberIdAndCategory(member, Category.EXPECT);
        // 수정
        eventBoard.setContent(boardRequestDto.getContent());
        eventBoardRepository.save(eventBoard);
    }

    @Override
    public void deleteExpect(Long boardId) {
        EventBoard eventBoard = eventBoardRepository.findById(boardId).orElse(null);
        if(eventBoard != null) {
            eventBoardRepository.delete(eventBoard);
        }
    }

    @Override
    public void registerView(String email, int maineventId, BoardRequestDto boardRequestDto) {
        EventBoard eventBoard = boardRequestDto.toEntity();

        // 유저아이디 찾기
        Member member = memberRepository.findByEmail(email).orElse(null);

        // 이벤트 관련 레포지토리 구현 후 수정 필요
        // MainEvent me = mainEventRepository.findById(maineventId).orElse(null);

        eventBoard.setMember(member);
        eventBoard.setCategory(Category.REVIEW);
//        eventBoard.setMainEvent(me);
        eventBoardRepository.save(eventBoard);
    }

    @Override
    public void updateReview(String email, int maineventId, BoardRequestDto boardRequestDto) {
        // 유저아이디랑 카테고리로 기대평 게시글 번호
        Member member = memberRepository.findByEmail(email).orElse(null);
        EventBoard eventBoard = eventBoardRepository.findMemberByMemberIdAndCategory(member, Category.REVIEW);
        // 수정
        eventBoard.setContent(boardRequestDto.getContent());
        eventBoardRepository.save(eventBoard);
    }

    @Override
    public void deleteReview(Long boardId) {
        EventBoard eventBoard = eventBoardRepository.findById(boardId).orElse(null);
        if(eventBoard != null) {
            eventBoardRepository.delete(eventBoard);
        }
    }
}
