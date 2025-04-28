package com.example.community.domain.comment.dto;

import lombok.Builder;

@Builder
public record CommentUpdateRequestDto(
        String content,
        Long memberId
) {
}
