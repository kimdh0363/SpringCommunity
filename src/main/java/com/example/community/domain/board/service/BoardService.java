package com.example.community.domain.board.service;

import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
