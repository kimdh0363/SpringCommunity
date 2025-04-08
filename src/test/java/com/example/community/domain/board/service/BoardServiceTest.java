package com.example.community.domain.board.service;

import com.example.community.domain.board.dto.BoardInfoRequestDto;
import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import com.example.community.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
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
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("게시판 생성")
    @Test
    void create() {
        Member member = createMember();

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

    @DisplayName("존재하지 않는 ID을 받아오면 예외가 발생한다.")
    @Test
    void getBoardThrowExceptionCauseByNotExistId() {
        Member member = createMember();
        memberRepository.save(member);

        Board board = createBoard(member);
        boardRepository.save(board);




    }

    @DisplayName("사용자로부터 id를 받아와 단일 게시글을 조회한다.")
    @Test
    void getBoard() {
        Member member = createMember();
        memberRepository.save(member);

        Board board = createBoard(member);
        boardRepository.save(board);

        BoardInfoRequestDto boardInfoRequestDto = boardService.getBoard(board.getId());
        assertThat(boardInfoRequestDto.title()).isEqualTo("Test");
        assertThat(boardInfoRequestDto.content()).isEqualTo("Test");
        assertThat(boardInfoRequestDto.member()).isEqualTo(member);


    }

    public static Member createMember() {
        return Member.builder()
                .username("Test")
                .password("Test")
                .email("Test@Test.com")
                .build();
    }
    public static Board createBoard(Member member) {
        return Board.builder()
                .title("Test")
                .content("Test")
                .member(member)
                .build();
    }
}
