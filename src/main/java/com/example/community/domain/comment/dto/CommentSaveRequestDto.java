package com.example.community.domain.comment.dto;

import com.example.community.domain.board.entity.Board;
import com.example.community.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record CommentSaveRequestDto(
        String content,
        Long memberId
) {
}
