package com.example.community.domain.board.service;

import com.example.community.domain.board.dto.BoardInfoRequestDto;
import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.dto.BoardUpdateRequestDto;
import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
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

        Long memberId = member.getId();

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .memberId(memberId)
                .title("Test")
                .content("Test")
                .build();
        boardService.createBoard(memberId, boardRequestDto);

        //Dto랑 임의의 값이랑 비교하지말고 리포지토리에서 받아와서 거시기하기

        Optional<Board> saveBoard = boardRepository.findAllByTitle(boardRequestDto.title());

        assertThat(saveBoard).isNotNull();
        assertThat(saveBoard.get().getTitle()).isEqualTo(boardRequestDto.title());
        assertThat(saveBoard.get().getContent()).isEqualTo(boardRequestDto.content());
        assertThat(saveBoard.get().getMember().getId()).isEqualTo(boardRequestDto.memberId());


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

        assertThat(boardInfoRequestDto.title()).isEqualTo(boardRequestDto.title());
        assertThat(boardInfoRequestDto.content()).isEqualTo(boardRequestDto.content());
        assertThat(boardInfoRequestDto.memberId()).isEqualTo(boardRequestDto.memberId());

    }

    @DisplayName("사용자로부터 id가 DB에 없을 때 예외가 발생한다.")
    @Test
    void getBoardThrowExceptionCausedByNullId() {
        Member member = createMember();
        memberRepository.save(member);

        BoardRequestDto boardRequestDto = createBoard(member);
        boardService.createBoard(member.getId(), boardRequestDto);


    }

    @DisplayName("수정할 정보를 입력하면 게시글의 정보가 수정된다.")
    @Test
    void updateBoard() {
        Member member = createMember();
        memberRepository.save(member);

        BoardRequestDto saveBoard = createBoard(member);
        boardService.createBoard(member.getId(), saveBoard);
        Board savedBoard = boardRepository.findAll().getFirst();

        BoardUpdateRequestDto updateRequestDto = BoardUpdateRequestDto.builder()
                .title("Test123")
                .content("Test123")
                .build();


        Board board = boardRepository.findByBoardId(savedBoard.getId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));

        boardService.updateBoard(savedBoard.getId(), member.getId(),updateRequestDto);

        Board updatedBoard = boardRepository.findByBoardId(board.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        assertThat(updatedBoard.getTitle()).isEqualTo(updateRequestDto.title());
        assertThat(updatedBoard.getContent()).isEqualTo(updateRequestDto.content());
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
