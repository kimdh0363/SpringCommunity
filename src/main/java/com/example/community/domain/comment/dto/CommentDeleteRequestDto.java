package com.example.community.domain.comment.dto;

import lombok.Builder;

@Builder
public record CommentDeleteRequestDto(
        Long memberId
) {
}
