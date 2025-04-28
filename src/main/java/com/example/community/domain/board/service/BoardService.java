package com.example.community.domain.board.service;

import com.example.community.domain.board.dto.BoardInfoResponseDto;
import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.dto.BoardUpdateRequestDto;
import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void createBoard(Long memberId, BoardRequestDto boardRequestDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        // 기능 로직
        // 제목, 내용을 입력한다.
        Board board = Board.builder()
                .title(boardRequestDto.title())
                .content(boardRequestDto.content())
                .member(member)
                .build();

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public BoardInfoResponseDto getBoard(Long boardId) {

        Board board = findBoardById(boardId);

        return BoardInfoResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .memberId(board.getMember().getId())
                .build();

    }

    @Transactional
    public void updateBoard(Long boardId, Long memberId, BoardUpdateRequestDto updateRequestDto) {
        Board board = findBoardById(boardId);

        if(!Objects.equals(board.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("수정권한이 있는 회원이 아닙니다.");
        }

        board.updateBoard(updateRequestDto.title(),
                updateRequestDto.content());
    }

    @Transactional
    public void deleteBoard(Long boardId, Long memberId) {

        Board board = findBoardById(boardId);

        if(!Objects.equals(board.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("삭제권한이 있는 회원이 아닙니다.");
        }

        boardRepository.delete(board);
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 게시글이 아닙니다."));
    }
}
