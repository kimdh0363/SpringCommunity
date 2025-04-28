package com.example.community.domain.comment.service;

import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.comment.dto.CommentInfoResponseDto;
import com.example.community.domain.comment.dto.CommentSaveRequestDto;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.comment.repository.CommentRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("댓글 정보를 받아와 저장한다.")
    @Test
    void create() {
        Member member = createMember();
        memberRepository.save(member);

        Board board = createBoard(member);
        boardRepository.save(board);

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .content("Comment_Test")
                .memberId(member.getId())
                .build();

        commentService.createComment(board.getId(),commentSaveRequestDto);

        Comment comment = commentRepository.findAll().getFirst();

        assertThat(comment.getContent()).isEqualTo(commentSaveRequestDto.content());
        assertThat(comment.getBoard().getId()).isEqualTo(board.getId());
        assertThat(comment.getMember().getId()).isEqualTo(member.getId());
    }

    @DisplayName("댓글 id를 사용해 단일 댓글 조회를 실시한다.")
    @Test
    void getComment() {
        Member member = createMember();
        memberRepository.save(member);

        Board board = createBoard(member);
        boardRepository.save(board);

        Comment comment = buildComment(board, member);
        commentRepository.save(comment);

        CommentInfoResponseDto commentInfoResponseDto = commentService.getComment(comment.getId());

        assertThat(commentInfoResponseDto.content()).isEqualTo(comment.getContent());
        assertThat(commentInfoResponseDto.boardId()).isEqualTo(board.getId());
        assertThat(commentInfoResponseDto.memberId()).isEqualTo(member.getId());
    }

    static Member createMember() {
        return Member.builder()
                .username("Test_Member_UserName")
                .password("Test_Member_Password")
                .email("Test@Test.com")
                .build();
    }

    static Board createBoard(Member member) {
        return Board.builder()
                .title("Test_Board_Title")
                .content("Test_Board_Content")
                .member(member)
                .build();
    }

    static Comment buildComment(Board board, Member member) {
        return Comment.builder()
                .content("Test_Comment_Content")
                .board(board)
                .member(member)
                .build();
    }

}