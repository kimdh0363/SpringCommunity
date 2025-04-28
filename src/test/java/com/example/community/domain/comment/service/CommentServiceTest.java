package com.example.community.domain.comment.service;

import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.comment.dto.CommentDeleteRequestDto;
import com.example.community.domain.comment.dto.CommentInfoResponseDto;
import com.example.community.domain.comment.dto.CommentSaveRequestDto;
import com.example.community.domain.comment.dto.CommentUpdateRequestDto;
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

    @DisplayName("수정할 정보를 입력하면 댓글 수정이 된다")
    @Test
    void updateComment() {
        Member member = createMember();
        memberRepository.save(member);

        Board board = createBoard(member);
        boardRepository.save(board);

        Comment saveComment = buildComment(board, member);
        commentRepository.save(saveComment);

        CommentUpdateRequestDto updateRequestDto = CommentUpdateRequestDto.builder()
                .content("Test123_Comment_Content")
                .memberId(member.getId())
                .build();

        commentService.updateComment(saveComment.getId(), board.getId(), updateRequestDto);

        Comment updatedComment = commentRepository.findAll().getFirst();

        assertThat(updatedComment.getContent()).isEqualTo(updateRequestDto.content());
    }

    @DisplayName("댓글의 정보를 받아와 삭제를 진행한다")
    @Test
    void deleteComment() {
        Member member = createMember();
        memberRepository.save(member);

        Board board = createBoard(member);
        boardRepository.save(board);

        Comment saveComment = buildComment(board, member);
        commentRepository.save(saveComment);

        CommentDeleteRequestDto commentDeleteRequestDto = CommentDeleteRequestDto.builder()
                .memberId(member.getId())
                .build();

        commentService.deleteComment(saveComment.getId(), board.getId(), commentDeleteRequestDto);

        assertThat(commentRepository.findById(saveComment.getId())).isEmpty();
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