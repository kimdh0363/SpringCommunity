package com.example.community.domain.board.service;

import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import com.example.community.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        boardRepository.deleteAllInBatch();
    }


    @Test
    void create() {
        Member member = Member.builder()
                .username("Test")
                .password("Test")
                .email("Test123@Test.com")
                .build();

        memberRepository.save(member);

        Long memberId= member.getId();

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .memberId(memberId)
                .title("Test")
                .content("Test")
                .build();
        boardService.createBoard(memberId, boardRequestDto);

        assertThat(boardRequestDto.memberId()).isEqualTo(memberId);
        assertThat(boardRequestDto.title()).isEqualTo("Test");
        assertThat(boardRequestDto.content()).isEqualTo("Test");

    }
}