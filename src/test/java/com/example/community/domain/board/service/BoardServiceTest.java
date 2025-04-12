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

import java.util.Optional;

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

        //Dto랑 임의의 값이랑 비교하지말고 리포지토리에서 받아와서 거시기하기

        assertThat(boardRequestDto.memberId()).isEqualTo(memberId);
        assertThat(boardRequestDto.title()).isEqualTo("Test");
        assertThat(boardRequestDto.content()).isEqualTo("Test");

    }




    //id가 없을 때의 예외, 널값일때의 예외, id가 존재하지 않을때의 예외 다 만들어리.
    @DisplayName("사용자로부터 id를 받아와 단일 게시글을 조회한다.")
    @Test
    void getBoard() {
        Member member = createMember();
        memberRepository.save(member);

        BoardRequestDto boardRequestDto = createBoard(member);
        boardService.createBoard(member.getId(), boardRequestDto);

        Board board = boardRepository.findAll().getFirst();

        BoardInfoRequestDto boardInfoRequestDto = boardService.getBoard(board.getId());

        assertThat(boardInfoRequestDto.title()).isEqualTo("Test-Title");
        assertThat(boardInfoRequestDto.content()).isEqualTo("Test-Content");
        assertThat(boardInfoRequestDto.memberId()).isEqualTo(member.getId());


    }

    public static Member createMember() {
        return Member.builder()
                .username("Test")
                .password("Test")
                .email("Test@Test.com")
                .build();
    }
    public static BoardRequestDto createBoard(Member member) {
        return BoardRequestDto.builder()
                .memberId(member.getId())
                .title("Test-Title")
                .content("Test-Content")
                .build();
    }
}
