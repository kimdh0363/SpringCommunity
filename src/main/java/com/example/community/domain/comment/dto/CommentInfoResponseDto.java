package com.example.community.domain.comment.dto;

import lombok.Builder;

@Builder
public record CommentInfoResponseDto(
        String content,
        Long boardId,
        Long memberId
) {
}
