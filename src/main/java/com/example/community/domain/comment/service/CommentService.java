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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void createComment(Long boardId, CommentSaveRequestDto commentSaveRequestDto) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Member member = memberRepository.findById(commentSaveRequestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Comment comment = Comment.builder()
                .content(commentSaveRequestDto.content())
                .board(board)
                .member(member)
                .build();

        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public CommentInfoResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 댓글이 아닙니다."));

        return CommentInfoResponseDto.builder()
                .content(comment.getContent())
                .boardId(comment.getBoard().getId())
                .memberId(comment.getMember().getId())
                .build();
    }

    @Transactional
    public void updateComment(Long commentId, Long boardId, CommentUpdateRequestDto updateRequestDto) {

        memberRepository.findById(updateRequestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 아닙니다."));

        boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 게시글이 아닙니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("수정하고자 하는 댓글이 존재하지 않습니다."));

        if (!Objects.equals(comment.getMember().getId(), updateRequestDto.memberId())) {
            throw new IllegalArgumentException("수정권한을 가지고 있는 회원이 아닙니다.");
        }

        comment.updateComment(updateRequestDto.content());
    }

    public void deleteComment(Long commentId, Long boardId, CommentDeleteRequestDto commentDeleteRequestDto) {
        memberRepository.findById(commentDeleteRequestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 아닙니다."));

        boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 게시글이 아닙니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 댓글이 아닙니다."));

        if (!Objects.equals(comment.getMember().getId(), commentDeleteRequestDto.memberId())) {
            throw new IllegalArgumentException("삭제권한이 있는 회원이 아닙니다.");
        }

        commentRepository.delete(comment);
    }
}
