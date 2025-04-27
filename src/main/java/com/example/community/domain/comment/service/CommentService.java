package com.example.community.domain.comment.service;

import com.example.community.domain.board.entity.Board;
import com.example.community.domain.board.repository.BoardRepository;
import com.example.community.domain.comment.dto.CommentSaveRequestDto;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.comment.repository.CommentRepository;
import com.example.community.domain.member.entity.Member;
import com.example.community.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void createComment(Long boardId, CommentSaveRequestDto commentSaveRequestDto) {

        Board board  = boardRepository.findById(boardId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Member member = memberRepository.findById(commentSaveRequestDto.memberId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        Comment comment = Comment.builder()
                .content(commentSaveRequestDto.content())
                .board(board)
                .member(member)
                .build();

        commentRepository.save(comment);
    }
}
