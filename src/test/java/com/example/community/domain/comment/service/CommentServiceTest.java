package com.example.community.domain.comment.service;

import com.example.community.domain.board.dto.BoardRequestDto;
import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.board.service.BoardService;
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
import static org.junit.jupiter.api.Assertions.*;

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

    static Member createMember() {
        return Member.builder()
                .username("Test")
                .password("Test")
                .email("Test@Test.com")
                .build();
    }

    static Board createBoard(Member member) {
        return Board.builder()
                .title("Test")
                .content("Test")
                .member(member)
                .build();
    }

}